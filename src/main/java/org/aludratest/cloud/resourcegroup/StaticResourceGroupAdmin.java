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

import org.aludratest.cloud.config.admin.AbstractConfigNodeBased;
import org.aludratest.cloud.config.admin.ConfigurationAdmin;

/**
 * Configuration administration interface for resource groups serving a set of static resources. It allows for adding and removing
 * resources to / from the group.
 * 
 * @author falbrech
 * 
 * @param <T>
 *            Type of configuration objects which represent the static resources.
 */
public interface StaticResourceGroupAdmin<T extends AbstractConfigNodeBased> extends ConfigurationAdmin {

	/**
	 * Adds a new resource to the resource group.
	 * 
	 * @return The configuration object for the new resource, whose methods should be used to describe the resource.
	 */
	public T addResource();

	/**
	 * Removes the given resource from the resource group.
	 * 
	 * @param resource
	 *            Configuration object describing the resource to remove. Must have been returned from {@link #addResource()} or
	 *            {@link #getConfiguredResources()}.
	 */
	public void removeResource(T resource);

	/**
	 * Returns all configuration objects representing all resources of the resource group.
	 * 
	 * @return All configuration objects representing all resources of the resource group.
	 */
	public Iterable<T> getConfiguredResources();

	/**
	 * Moves the given resource up in the list of resources in the resource group. As this results in a new configuration object
	 * representing the resource, the passed reference must not be stored, but the result of this method must be used for further
	 * operations on the resource.
	 * 
	 * @param resource
	 *            Configuration object describing the resource to move up. Must have been returned from {@link #addResource()} or
	 *            {@link #getConfiguredResources()}.
	 * @return New configuration object describing the moved resource.
	 */
	public T moveUpResource(T resource);

	/**
	 * Moves the given resource down in the list of resources in the resource group. As this results in a new configuration object
	 * representing the resource, the passed reference must not be stored, but the result of this method must be used for further
	 * operations on the resource.
	 * 
	 * @param resource
	 *            Configuration object describing the resource to move down. Must have been returned from {@link #addResource()}
	 *            or {@link #getConfiguredResources()}.
	 * @return New configuration object describing the moved resource.
	 */
	public T moveDownResource(T resource);

}
