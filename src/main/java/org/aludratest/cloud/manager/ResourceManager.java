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
package org.aludratest.cloud.manager;

import java.util.List;

import org.aludratest.cloud.request.ResourceRequest;
import org.aludratest.cloud.resourcegroup.ResourceGroupManager;

/**
 * A Resource Manager is one of the core objects of AludraTest Cloud Manager. It manages incoming resource requests and uses the
 * Resource Group Manager to find resources requested by the requests. An implementation usually starts up one or more Threads and
 * attaches listeners to the Resource Group Manager, the resource groups, and the resources, so it has a lifecycle.
 * {@link #start(ResourceGroupManager)} is called by the application when the Resource Manager shall start up the internal Thread
 * and attach to the resources, and {@link #shutdown()} is called when the Resource Manager shall stop the internal Thread and
 * detach from the resources.
 * 
 * @author falbrech
 * 
 */
public interface ResourceManager {

	/**
	 * The Plexus role for this component.
	 */
	public static final String ROLE = ResourceManager.class.getName();

	/**
	 * Starts the resource manager, using the given Resource Group Manager for finding and attaching listeners to resources.
	 * 
	 * @param resourceGroupManager
	 *            Resource Group Manager to use.
	 */
	public void start(ResourceGroupManager resourceGroupManager);

	/**
	 * Handles an incoming resource request. This method must return immediately, but trigger an asynchronous check for available
	 * resources for the request. When no resources are available, the request should be queued.
	 * 
	 * @param request
	 *            Resource request to handle.
	 */
	public void handleResourceRequest(ResourceRequest request);

	/**
	 * Shuts the resource manager down. Attached listeners must be detached from other objects, e.g. resources and resource
	 * groups.
	 */
	public void shutdown();

	/**
	 * Returns the current total size of the internal queue of requests of this resource manager.
	 * 
	 * @return The current total size of the internal queue of requests of this resource manager.
	 */
	public int getTotalQueueSize();

	/**
	 * Attaches a listener to this Resource Manager. The listener will be informed when request related changes occur, e.g. a
	 * request has been enqueued, or a resource is available for the request.
	 * 
	 * @param listener
	 *            Listener to attach to this Resource Manager. If an equal listener is already attached, this method does nothing.
	 */
	public void addResourceManagerListener(ResourceManagerListener listener);

	/**
	 * Detaches a listener from this Resource Manager.
	 * 
	 * @param listener
	 *            Listener to detach from this Resource Manager.
	 */
	public void removeResourceManagerListener(ResourceManagerListener listener);

	/**
	 * Returns a list of the resource queries which are currently in use, i.e. a resource has been assigned and the client is
	 * currently using the resource.
	 * 
	 * @return A (possibly empty) list of running resource queries, never <code>null</code>.
	 */
	public List<? extends ManagedResourceQuery> getAllRunningQueries();

}
