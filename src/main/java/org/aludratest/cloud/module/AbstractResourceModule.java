/*
 * Copyright (C) 2010-2015 AludraTest.org and the contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aludratest.cloud.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.aludratest.cloud.app.CloudManagerApp;
import org.aludratest.cloud.request.ResourceRequest;
import org.aludratest.cloud.resource.Resource;
import org.aludratest.cloud.resource.ResourceStateHolder;
import org.aludratest.cloud.resourcegroup.AuthorizingResourceGroup;
import org.aludratest.cloud.resourcegroup.ResourceGroup;
import org.aludratest.cloud.resourcegroup.ResourceGroupManager;
import org.aludratest.cloud.user.User;

/**
 * Abstract base implementation for the Resource Module interface. Its main purpose is a default implementation of the
 * {@link #getAvailableResources(ResourceRequest, Set)} method, which e.g. checks user rights, when involved resource groups have
 * such limitations. This implementation splits the applicable resources by resource group and delegates sorting to protected
 * methods which can be overridden by subclasses to e.g. implement a round-robin-per-group instead of a "stack based" resource
 * assignment algorithm.
 * 
 * @author falbrech
 * 
 */
public abstract class AbstractResourceModule implements ResourceModule {

	@Override
	public List<? extends Resource> getAvailableResources(ResourceRequest request, Set<? extends Resource> idleResources) {
		// remove resources where user is not authorized for
		CloudManagerApp app = CloudManagerApp.getInstance();
		ResourceGroupManager groupManager = app.getResourceGroupManager();

		User user = request.getRequestingUser();

		// find resources per group, to sort them per group
		List<Integer> freeGroups = new ArrayList<Integer>();
		Map<Integer, List<Resource>> groupResources = new TreeMap<Integer, List<Resource>>();
		Map<Integer, ResourceGroup> groups = new HashMap<Integer, ResourceGroup>();

		AtomicInteger intBuf = new AtomicInteger();
		for (Resource res : idleResources) {
			// find associated group
			ResourceGroup group = findResourceGroup(res, groupManager, intBuf);
			if (group != null) {
				groups.put(Integer.valueOf(intBuf.get()), group);
				boolean add = false;
				boolean free;
				if (group instanceof AuthorizingResourceGroup) {
					AuthorizingResourceGroup authGroup = (AuthorizingResourceGroup) group;
					free = !authGroup.isLimitingUsers();
					add = free || authGroup.isUserAuthorized(user);
				}
				else {
					free = true;
					add = true;
				}

				if (add) {
					List<Resource> ls = groupResources.get(Integer.valueOf(intBuf.get()));
					if (ls == null) {
						groupResources.put(Integer.valueOf(intBuf.get()), ls = new ArrayList<Resource>());
					}
					ls.add(res);
				}

				if (free) {
					freeGroups.add(Integer.valueOf(intBuf.get()));
				}
			}
		}

		// sort resources per group
		for (Map.Entry<Integer, List<Resource>> entry : groupResources.entrySet()) {
			ResourceGroup group = groups.get(entry.getKey());
			sortAvailableGroupResources(request, group, entry.getValue());
		}

		// now build huge lists
		List<Resource> authorizedResources = new ArrayList<Resource>();
		for (Map.Entry<Integer, List<Resource>> entry : groupResources.entrySet()) {
			if (!freeGroups.contains(entry.getKey())) {
				authorizedResources.addAll(entry.getValue());
			}
		}

		List<Resource> freeResources = new ArrayList<Resource>();
		for (Map.Entry<Integer, List<Resource>> entry : groupResources.entrySet()) {
			if (freeGroups.contains(entry.getKey())) {
				freeResources.addAll(entry.getValue());
			}
		}

		return Collections.unmodifiableList(sortAvailableResources(request, authorizedResources, freeResources));
	}

	/**
	 * Sorts the resources available in the given group by their preferability for use by the given request. The default
	 * implementation just sorts the resources according to their position within the group, meaning that resources listed first
	 * are used most often. Subclasses can override to e.g. implement a round-robin algorithm.
	 * 
	 * @param request
	 *            Resource request for which the preferred-to-use resources have to be determined.
	 * @param group
	 *            Resource group the listed resources come from.
	 * @param availableResources
	 *            Resources of the given resource group which are available and have been determined to be applicable for the
	 *            given request.
	 */
	protected void sortAvailableGroupResources(ResourceRequest request, ResourceGroup group, List<Resource> availableResources) {
		final List<Resource> groupResources = new ArrayList<Resource>();
		for (ResourceStateHolder rsh : group.getResourceCollection()) {
			groupResources.add((Resource) rsh);
		}

		Collections.sort(availableResources, new Comparator<Resource>() {
			@Override
			public int compare(Resource r1, Resource r2) {
				return groupResources.indexOf(r1) - groupResources.indexOf(r2);
			}
		});
	}

	/**
	 * Performs a last global sort of the given resources, according to their preferred use for the given request. The default
	 * implementation just concatenates the two given lists, as usually one would expect that a user gets resources from groups
	 * where he is explicitly authenticated for, and only if no such resources are available, resources from "free" groups are
	 * used. Subclasses can override to change this behaviour.
	 * 
	 * @param request
	 *            Request to determine preferred resource for.
	 * @param authorizedResources
	 *            Already sorted-per-group list of resources from groups which limit the access to some users (including the
	 *            request's one).
	 * @param freeResources
	 *            Already sorted-per-group list of resources from groups without user-based limitation of access.
	 * 
	 * @return A list with the resources preferred to be used by the given request, the most preferred resource first.
	 */
	protected List<Resource> sortAvailableResources(ResourceRequest request, List<Resource> authorizedResources,
			List<Resource> freeResources) {
		List<Resource> result = new ArrayList<Resource>();
		result.addAll(authorizedResources);
		result.addAll(freeResources);
		return result;
	}

	private ResourceGroup findResourceGroup(Resource resource, ResourceGroupManager groupManager, AtomicInteger outGroupId) {
		for (int groupId : groupManager.getAllResourceGroupIds()) {
			ResourceGroup group = groupManager.getResourceGroup(groupId);
			if (group.getResourceCollection().contains(resource)) {
				outGroupId.set(groupId);
				return group;
			}
		}

		return null;
	}

	@Override
	public void handleApplicationShutdown() {
		// subclasses can override to add handling
	}

}
