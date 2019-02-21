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

import java.util.Iterator;

import org.aludratest.cloud.request.ResourceRequest;
import org.aludratest.cloud.resourcegroup.ResourceGroupManager;

/**
 * A Resource Manager is one of the core objects of AludraTest Cloud Manager. It manages incoming resource requests and uses the
 * Resource Group Manager to find resources requested by the requests. An implementation usually starts up one or more Threads and
 * attaches listeners to the Resource Group Manager, the resource groups, and the resources, so it has a lifecycle.
 * {@link #start(ResourceGroupManager)} is called by the application when the Resource Manager shall start up the internal Thread
 * and attach to the resources, and {@link #shutdown()} is called when the Resource Manager shall stop the internal Thread and
 * detach from the resources. <br>
 * Note that the Resource Manager implementation is also responsible for firing resource request related application events.
 * 
 * @author falbrech
 * 
 * @see org.aludratest.cloud.event.ResourceRequestEvent
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
	 * resources for the request. When no resources are available, the request should be queued. The future inside the returned
	 * managed resource request object can be used to wait for a resource to become available, or to check if it is already
	 * available.
	 * 
	 * @param request
	 *            Resource request to handle.
	 * @return The managed resource request, including a Future for accessing the assigned resource (or waiting for it).
	 * 
	 * @throws ResourceManagerException
	 *             If it is already clear that this request cannot be handled at all, e.g. due to no matching resources existing
	 *             in the system, or insufficient privileges of the requesting user.
	 */
	public ManagedResourceRequest handleResourceRequest(ResourceRequest request) throws ResourceManagerException;

	/**
	 * Shuts the resource manager down. Attached listeners must be detached from other objects, e.g. resources and resource
	 * groups.
	 */
	public void shutdown();

	/**
	 * Returns all currently managed requests, including waiting, running, finished, and orphaned ones (the latter two only until
	 * they are removed automatically after some time).
	 * 
	 * @return A (possibly empty) iterator for all currently managed requests, never <code>null</code>.
	 */
	public Iterator<? extends ManagedResourceRequest> getManagedRequests();

}
