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

import java.util.Set;

import org.aludratest.cloud.resource.ResourceType;
import org.aludratest.cloud.resource.writer.ResourceWriterFactory;

public interface ResourceModuleRegistry {

	/**
	 * Returns the resource module for the given resource type name.
	 *
	 * @param resourceTypeName
	 *            Name of the resource type to retrieve the resource module for.
	 *
	 * @return The resource module for the given resource type, or <code>null</code>
	 *         if no module is registered for this resource type.
	 */
	default public ResourceModule getResourceModule(String resourceTypeName) {
		return getAllResourceModules().stream().filter(m -> m.getResourceType().getName().equals(resourceTypeName))
				.findFirst().orElse(null);
	}

	/**
	 * Returns the resource module for the given resource type.
	 *
	 * @param resourceType
	 *            Resource type to retrieve the resource module for.
	 *
	 * @return The resource module for the given resource type, or <code>null</code>
	 *         if no module is registered for this resource type.
	 */
	default public ResourceModule getResourceModule(ResourceType resourceType) {
		return getAllResourceModules().stream().filter(m -> m.getResourceType().equals(resourceType)).findFirst()
				.orElse(null);
	}

	/**
	 * Returns the Resource Writer Factory which can be used to serialize Resource
	 * information to responses to client resource requests.
	 *
	 * @param resourceType
	 *            Resource Type for which to retrieve a Resource Writer Factory.
	 *
	 * @return The Resource Writer Factory for this resource type, or
	 *         <code>null</code> if no compatible writer is registered.
	 */
	default public ResourceWriterFactory getResourceWriterFactory(ResourceType resourceType) {
		ResourceModule module = getResourceModule(resourceType);
		return module == null ? null : module.getResourceWriterFactory();
	}

	/**
	 * Returns all Resource Modules known to this application.
	 *
	 * @return A set containing all Resource Modules known to this application,
	 *         maybe empty, but never <code>null</code>.
	 */
	public Set<? extends ResourceModule> getAllResourceModules();


}
