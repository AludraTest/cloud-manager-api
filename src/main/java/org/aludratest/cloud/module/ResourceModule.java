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

import org.aludratest.cloud.resource.ResourceType;
import org.aludratest.cloud.resource.writer.ResourceWriterFactory;
import org.aludratest.cloud.resourcegroup.ResourceGroup;

/**
 * A resource module describes a type of resources being managed by the AludraTest Cloud Manager. It knows how to create resource
 * groups suitable for the resource type, and how to serialize resources of this type to different formats. Also, it provides the
 * logic to apply when, from a set of all idle resources, the set of available resources for a request shall be determined. <br>
 * You should rarely implement this interface directly. Better subclass the abstract base implementation
 * {@link AbstractResourceModule}.
 *
 * @author falbrech
 *
 */
public interface ResourceModule {

	/**
	 * Returns the type of resources described by this resource module.
	 *
	 * @return The type of resources described by this resource module, never <code>null</code>.
	 */
	public ResourceType getResourceType();

	/**
	 * Returns a human-readable display name for this resource module, e.g. "Selenium Resources".
	 *
	 * @return A human-readable display name for this resource module, never <code>null</code>.
	 */
	public String getDisplayName();

	/**
	 * Creates a new resource group suitable for resources of the resource type of this module.
	 *
	 * @return A new resource group suitable for resources of the resource type of this module, never <code>null</code>.
	 */
	public ResourceGroup createResourceGroup();

	/**
	 * Returns a resource writer factory which is able to serialize resources of the module's resource type to different formats.
	 *
	 * @return A resource writer factory for resources of this module's resource type, never <code>null</code>.
	 */
	public ResourceWriterFactory getResourceWriterFactory();

	/**
	 * Is called by the framework when the application is shutting down. This allows the resource module to free any used
	 * management resources etc.
	 */
	public void handleApplicationShutdown();

}
