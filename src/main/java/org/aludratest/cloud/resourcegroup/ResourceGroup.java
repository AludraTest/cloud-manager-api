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

import org.aludratest.cloud.resource.ResourceCollection;
import org.aludratest.cloud.resource.ResourceStateHolder;
import org.aludratest.cloud.resource.ResourceType;

/**
 * Interface for resource groups. A resource group is one of the top-level elements of AludraTest Cloud Manager. It contains
 * resources of a given type and is managed by the Resource Group Manager. The resource group manager also maintains the metadata
 * for the group (name, internal ID, configuration).
 * 
 * @author falbrech
 * 
 */
public interface ResourceGroup {

	/**
	 * Returns the type of resources provided by this group.
	 * 
	 * @return The type of resources provided by this group.
	 */
	public ResourceType getResourceType();

	/**
	 * Returns the collection of resources provided by this group. This reference itself usually never changes, but the contents
	 * may change. You can add listeners to the collection to be informed about changes.
	 * 
	 * @return The collection of resources provided by this group, possibly empty (at least at the moment), but never
	 *         <code>null</code>.
	 */
	public ResourceCollection<? extends ResourceStateHolder> getResourceCollection();

}
