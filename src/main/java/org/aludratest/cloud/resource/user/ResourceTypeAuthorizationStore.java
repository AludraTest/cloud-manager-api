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
package org.aludratest.cloud.resource.user;

import org.aludratest.cloud.resource.ResourceType;
import org.aludratest.cloud.user.StoreException;

/**
 * Interface for stores being able to load and persist resource type authorization configurations.
 * 
 * @author falbrech
 * 
 */
public interface ResourceTypeAuthorizationStore {

	/**
	 * Plexus role of this component.
	 */
	public static final String ROLE = ResourceTypeAuthorizationStore.class.getName();

	/**
	 * Loads the authorization configuration for the given resource type.
	 * 
	 * @param resourceType
	 *            Resource type to load the authorization configuration for.
	 * 
	 * @return The authorization configuration for the given resource type, or <code>null</code> if no authorization confguration
	 *         is stored for the given resource type.
	 * 
	 * @throws StoreException
	 *             If any exception occurs when loading the configuration from the persistent storage.
	 */
	public ResourceTypeAuthorizationConfig loadResourceTypeAuthorizations(ResourceType resourceType)
			throws StoreException;

	/**
	 * Saves the given authorization configuration for the given resource type.
	 * 
	 * @param resourceType
	 *            Resource type to save the authorization configuration for.
	 * @param authorizations
	 *            Authorization configuration for the given resource type. This replaces any persisted authorization configuration
	 *            for the given resource type.
	 * 
	 * @throws StoreException
	 *             If any exception occurs when writing the configuration to the persistent storage.
	 */
	public void saveResourceTypeAuthorizations(ResourceType resourceType, ResourceTypeAuthorizationConfig authorizations)
			throws StoreException;

}
