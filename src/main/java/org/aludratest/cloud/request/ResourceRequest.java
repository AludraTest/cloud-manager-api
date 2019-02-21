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
package org.aludratest.cloud.request;

import java.util.Map;

import org.aludratest.cloud.manager.ManagedResourceRequest;
import org.aludratest.cloud.resource.ResourceType;
import org.aludratest.cloud.user.User;

/**
 * Interface of a request for a resource of a given type. All attributes of a request could be taken into account by management
 * classes (Resource Manager, Resource Group, Resource Module) when e.g. the priority of a request has to be determined. <br>
 * Objects of this type represent the immutable request itself, not the managed, possibly waiting request, which is represented by
 * objects of type {@link ManagedResourceRequest}.
 * 
 * @author falbrech
 * 
 */
public interface ResourceRequest {

	/**
	 * Returns the user which requested a resource.
	 * 
	 * @return The user which requested a resource, never <code>null</code>.
	 */
	public User getRequestingUser();

	/**
	 * Returns the requested resource type.
	 * 
	 * @return The requested resource type, never <code>null</code>.
	 */
	public ResourceType getResourceType();

	/**
	 * Returns the "nice level" of this request. As in Unix, values from -19 to +20 are valid. The lower the level, the higher the
	 * priority of the request - within the set of all requests issued by the same user!
	 * 
	 * @return The "nice level" of this request.
	 */
	public int getNiceLevel();

	/**
	 * Returns the "job name" of this request - a free-text element to be filled by the user, which can be used to identify
	 * requests in management UI.
	 * 
	 * @return The "job name" of this request, or <code>null</code>.
	 */
	public String getJobName();

	/**
	 * Returns custom arbitrary attributes of this request. There are no default keys used; implementors can use any keys and
	 * values applicable for their resource or management implementations.
	 * 
	 * @return Custom arbitrary attributes of this request, maybe an empty map, but never <code>null</code>.
	 */
	public Map<String, Object> getCustomAttributes();

}
