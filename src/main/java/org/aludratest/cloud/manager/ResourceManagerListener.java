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

import org.aludratest.cloud.resource.Resource;

/**
 * Listeners of this type are informed about request related changes in Resource Managers.
 * 
 * @author falbrech
 * 
 */
public interface ResourceManagerListener {

	/**
	 * Informs the listener that a request has been enqueued within the Resource Manager. This method is <b>always</b> called,
	 * even when a resource is available for the request, in which case {@link #resourceAvailable(ManagedResourceQuery, Resource)}
	 * will immediately be called afterwards.
	 * 
	 * @param request
	 *            Request which has been received and enqueued by the Resource Manager.
	 */
	public void requestEnqueued(ManagedResourceQuery request);

	/**
	 * Signals that a resource has become available for an enqueued request. Listener should not block for long, but start
	 * asynchronous working with the resource. If the listener is not able to work with the resource (e.g. not originator of the
	 * request, or the client has disconnected in the meantime in a client-server enviroment), it must return <code>false</code>.
	 * If all listeners return <code>false</code>, the manager treats the request as cancelled and looks for another matching
	 * request.
	 * 
	 * @param request
	 *            Request for which a resource has become available and assigned.
	 * @param availableResource
	 *            Resource which has become available and assigned.
	 * @return <code>true</code> if the listener was originator of the request and starts working with it, <code>false</code>
	 *         otherwise.
	 */
	public boolean resourceAvailable(ManagedResourceQuery request, Resource availableResource);

	/**
	 * Informs the listener that a request has released the given resource. This may mean that the request is done working with
	 * the resource, or that there was no listener returning <code>true</code> from
	 * {@link #resourceAvailable(ManagedResourceQuery, Resource)} when a resource was ready for use by a request (e.g. client
	 * connection has been aborted, so request was "stale").
	 * 
	 * @param request
	 *            Request which released a resource.
	 * @param releasedResource
	 *            Resource which was assigned to the request.
	 */
	public void resourceReleased(ManagedResourceQuery request, Resource releasedResource);

	/**
	 * Informs the listener that an error has occurred for the given request. A common case is that there are no resources
	 * available for the request, not even blocked ones, so there is no chance that this request will <b>ever</b> receive a
	 * resource. This could e.g. be the case when the requesting user has no access to any resource groups. <br>
	 * The request is automatically removed from the internal queue of the Resource Manager.
	 * 
	 * @param request
	 *            Request for which an error has occurred.
	 * @param errorMessage
	 *            Error message, describing the problem.
	 * @param cause
	 *            Exception object, further describing the problem.
	 */
	public void requestError(ManagedResourceQuery request, String errorMessage, Throwable cause);

}
