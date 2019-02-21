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
package org.aludratest.cloud.resourcegroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.aludratest.cloud.config.ConfigException;
import org.aludratest.cloud.config.ConfigManager;
import org.aludratest.cloud.config.Configurable;
import org.aludratest.cloud.config.MainPreferences;
import org.aludratest.cloud.config.Preferences;
import org.aludratest.cloud.config.admin.ConfigurationAdmin;
import org.aludratest.cloud.resource.AbstractResourceCollection;
import org.aludratest.cloud.resource.Resource;
import org.aludratest.cloud.resource.ResourceCollection;
import org.aludratest.cloud.resource.ResourceType;
import org.aludratest.cloud.user.admin.UserDatabaseRegistry;

/**
 * Abstract base class for resource groups having a static (but configurable) set of resources, and optionally a set of users
 * authorized for using this group.
 * 
 * @author falbrech
 * @param <R>
 *            Type of resources provided by the group.
 * 
 */
public abstract class AbstractStaticResourceGroup<R extends Resource> extends AbstractAuthorizingResourceGroup implements
		Configurable {

	static final String PREFS_RESOURCES_NODE = "resources";

	private List<R> resources = new ArrayList<R>();

	private StaticResourceCollection resourceCollection = new StaticResourceCollection();

	protected AbstractStaticResourceGroup(ResourceType resourceType, ConfigManager configManager,
			UserDatabaseRegistry userDatabaseRegistry) {
		super(resourceType, configManager, userDatabaseRegistry);
	}

	@Override
	public ResourceCollection<R> getResourceCollection() {
		return resourceCollection;
	}

	/**
	 * Moves the given resource in the internal list of resources one step up or down. This can be made public e.g. for GUI
	 * elements to allow configurators to re-sort the resources.
	 * 
	 * @param resource
	 *            Resource to move.
	 * @param up
	 *            If <code>true</code>, resource is moved one index to the top in the list, if not already the first element. If
	 *            <code>false</code>, element is moved one index down in the list, if not already the last element.
	 */
	protected final synchronized void moveResourceInList(R resource, boolean up) {
		if (!resources.contains(resource)) {
			return;
		}

		int index = resources.indexOf(resource);
		if (up && index > 0) {
			resources.remove(index);
			resources.add(index - 1, resource);
		}
		else if (!up && index < resources.size() - 1) {
			resources.remove(index);
			resources.add(index + 1, resource);
		}
	}

	protected void addResource(R resource) {
		synchronized (this) {
			resources.add(resource);
		}

		fireResourceAdded(resource);
	}

	protected void removeResource(R resource) {
		synchronized (this) {
			resources.remove(resource);
		}

		fireResourceRemoved(resource);
	}

	@Override
	protected void configure(MainPreferences preferences) throws ConfigException {
		super.configure(preferences);

		// check add / removal of resources
		final List<R> newResources = buildResourcesList(preferences);

		List<R> oldResources;
		synchronized (this) {
			oldResources = new ArrayList<R>(resources);
		}

		for (R r : oldResources) {
			if (!newResources.contains(r)) {
				removeResource(r);
			}
		}

		for (R r : newResources) {
			if (!oldResources.contains(r)) {
				addResource(r);
			}
		}

		// ensures that resource order is same as in config
		synchronized (this) {
			Collections.sort(resources, new Comparator<R>() {
				@Override
				public int compare(R o1, R o2) {
					return newResources.indexOf(o1) - newResources.indexOf(o2);
				}
			});
		}
	}

	@Override
	public void validateConfiguration(Preferences preferences) throws ConfigException {
		super.validateConfiguration(preferences);

		Preferences resources = preferences.getChildNode(PREFS_RESOURCES_NODE);
		if (resources != null) {
			for (String node : resources.getChildNodeNames()) {
				validateResourceConfig(resources.getChildNode(node));
			}
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T extends ConfigurationAdmin> T getAdminInterface(Class<T> ifaceClass) {
		if (ifaceClass == StaticResourceGroupAdmin.class) {
			return (T) createStaticResourceGroupAdmin(getPreferences());
		}

		return super.getAdminInterface(ifaceClass);
	}

	protected abstract void validateResourceConfig(Preferences resourceConfig) throws ConfigException;

	protected abstract R createResourceFromPreferencesElement(Preferences resourceConfig) throws ConfigException;

	protected abstract StaticResourceGroupAdmin<?> createStaticResourceGroupAdmin(MainPreferences preferences);

	protected final void fireResourceAdded(R resource) {
		resourceCollection.doFireResourceAdded(resource);
	}

	protected final void fireResourceRemoved(R resource) {
		resourceCollection.doFireResourceRemoved(resource);
	}

	private List<R> buildResourcesList(Preferences config) throws ConfigException {
		Preferences resPrefs = config.getChildNode(PREFS_RESOURCES_NODE);
		if (resPrefs == null) {
			return Collections.emptyList();
		}

		List<R> result = new ArrayList<R>();

		List<Integer> ids = new ArrayList<Integer>();

		for (String node : resPrefs.getChildNodeNames()) {
			if (node.matches("[0-9]{1,10}")) {
				ids.add(Integer.valueOf(node));
			}
		}

		Collections.sort(ids);

		for (Integer id : ids) {
			result.add(createResourceFromPreferencesElement(resPrefs.getChildNode(id.toString())));
		}

		return result;
	}

	private class StaticResourceCollection extends AbstractResourceCollection<R> {

		@Override
		public Iterator<R> iterator() {
			List<R> ls;
			synchronized (AbstractStaticResourceGroup.this) {
				ls = new ArrayList<R>(resources);
			}

			return ls.iterator();
		}

		@Override
		public int getResourceCount() {
			synchronized (AbstractStaticResourceGroup.this) {
				return resources.size();
			}
		}

		@Override
		public boolean contains(Resource resource) {
			synchronized (AbstractStaticResourceGroup.this) {
				return resources.contains(resource);
			}
		}

		private void doFireResourceAdded(R resource) {
			fireResourceAdded(resource);
		}

		private void doFireResourceRemoved(R resource) {
			fireResourceRemoved(resource);
		}
	}

}
