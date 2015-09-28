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

import java.util.List;

/**
 * Component which manages all resource groups of AludraTest Cloud Manager.
 * 
 * @author falbrech
 * 
 */
public interface ResourceGroupManager {

	/**
	 * Plexus role of this component.
	 */
	public static final String ROLE = ResourceGroupManager.class.getName();

	/**
	 * Returns an array of IDs of all groups currently registered in this Resource Group Manager.
	 * 
	 * @return An array of IDs of all groups currently registered in this Resource Group Manager, possibly empty, but never
	 *         <code>null</code>.
	 */
	public int[] getAllResourceGroupIds();

	/**
	 * Returns the resource group which is registered with the given ID.
	 * 
	 * @param groupId
	 *            Registration ID of the group.
	 * 
	 * @return The registered resource group, or <code>null</code> if no group is registered with this ID.
	 */
	public ResourceGroup getResourceGroup(int groupId);

	/**
	 * Returns the configured name of the resource group with the given ID.
	 * 
	 * @param groupId
	 *            Registration ID of the group.
	 * 
	 * @return The configured name of the group, or <code>null</code> if no group is registered with this ID.
	 */
	public String getResourceGroupName(int groupId);

	/**
	 * Returns the list of group natures which could be added to the given group (using a {@link ResourceGroupManagerAdmin}).
	 * 
	 * @param groupId
	 *            Registration ID of the group.
	 * 
	 * @return (Possibly empty) list of natures available for the given group.
	 */
	public List<ResourceGroupNature> getAvailableNaturesFor(int groupId);

	/**
	 * Returns the concrete nature association for the given group and nature name.
	 * 
	 * @param groupId
	 *            Registration ID of the group.
	 * @param nature
	 *            Name of the nature to return the association for.
	 * 
	 * @return The association of the given nature with the given group, or <code>null</code> if the nature has not been
	 *         associated with the group.
	 */
	public ResourceGroupNatureAssociation getNatureAssociation(int groupId, String nature);

	/**
	 * Registers a listener in this Resource Group Manager which is informed when resource groups are added to or removed from
	 * this Resource Group Manager.
	 * 
	 * @param listener
	 *            Listener to register in this Resource Group Manager. If an equal listener is already registered, this method
	 *            does nothing.
	 */
	public void addResourceGroupManagerListener(ResourceGroupManagerListener listener);

	/**
	 * Removes a listener from this Resource Group Manager.
	 * 
	 * @param listener
	 *            Listener to remove from this Resource Group Manager.
	 */
	public void removeResourceGroupManagerListener(ResourceGroupManagerListener listener);

}
