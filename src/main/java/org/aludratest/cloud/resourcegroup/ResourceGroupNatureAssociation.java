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

/**
 * Interface for concrete associations of resource group natures to resource groups. Implementations may also implement the
 * <code>Configurable</code> interface, and the resource group manager will care about providing them with configuration location. <br>
 * The resource group manager will call <code>init()</code> as soon as an association is configured and associated to a group.
 * This may be during application start-up or after association through the user (configuration). <br>
 * <code>detach()</code> is called when the associated resource group is deleted, or the nature is removed from the resource group
 * by the user. It is <b>not</b> called when the application exits, so do not rely on it to be called to stop any threads or
 * similar.
 * 
 * @author falbrech
 * 
 */
public interface ResourceGroupNatureAssociation {

	/**
	 * Called by the framework when the resource group is initialized, e.g. during application start, or when the association is
	 * initially created for the resource group.
	 */
	public void init();

	/**
	 * Called when the association is removed from the resource group. This is not called when application exits!
	 */
	public void detach();

}
