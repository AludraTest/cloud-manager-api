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
 * Interface for listener which are notified about changes in a Resource Group Manager.
 * 
 * @author falbrech
 * 
 */
public interface ResourceGroupManagerListener {

	/**
	 * Notifies the listener that the given resource group has been added to the Resource Group Manager.
	 * 
	 * @param group
	 *            Added group.
	 */
	public void resourceGroupAdded(ResourceGroup group);

	/**
	 * Notifies the listener that the given resource group has been removed from the Resource Group Manager.
	 * 
	 * @param group
	 *            Group which has been removed.
	 */
	public void resourceGroupRemoved(ResourceGroup group);

}
