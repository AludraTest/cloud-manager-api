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

import org.aludratest.cloud.config.ConfigException;
import org.aludratest.cloud.config.admin.ConfigurationAdmin;
import org.aludratest.cloud.resource.ResourceType;

/**
 * Interface for administering a Resource Group Manager. You can retrieve this interface from Resource Group Manager
 * implementations which also implement the <code>Configurable</code> interface.
 * 
 * @author falbrech
 * 
 */
public interface ResourceGroupManagerAdmin extends ConfigurationAdmin {

	/**
	 * Renames the resource group with the given registration ID.
	 * 
	 * @param id
	 *            Registration ID of the group.
	 * @param newName
	 *            New name for the resource group.
	 * 
	 * @throws ConfigException
	 *             If the new name is invalid, e.g. already used by another resource group.
	 */
	public void renameResourceGroup(int id, String newName) throws ConfigException;

	/**
	 * Creates a new resource group and registers it in the resource manager.
	 * 
	 * @param resourceType
	 *            Type of resources to be provided by the resource group.
	 * @param name
	 *            Name of the new resource group.
	 * 
	 * @return New registration ID of the group.
	 * 
	 * @throws ConfigException
	 *             If the resulting Manager configuration would be invalid, e.g. name already used by another resource group.
	 */
	public int createResourceGroup(ResourceType resourceType, String name) throws ConfigException;

	/**
	 * Removes the given resource group from the resource manager.
	 * 
	 * @param id
	 *            Registration ID of the group.
	 * 
	 * @throws ConfigException
	 *             If deletion would result in an invalid Manager configuration.
	 */
	public void deleteResourceGroup(int id) throws ConfigException;

	/**
	 * Moves the given group in the ranking of groups up or down. This affects how the groups are returned from iterating methods
	 * of the resource group manager.
	 * 
	 * @param id
	 *            ID of the group to re-rank.
	 * @param up
	 *            If <code>true</code>, the group is moved up (its rank is decreased), otherwise, the group is moved down.
	 */
	public void moveGroup(int id, boolean up);

	/**
	 * Adds the given nature to the resource group with the given ID. Does nothing if the group already has this nature.
	 * 
	 * @param groupId
	 *            ID of the resource group to add the nature to.
	 * @param natureName
	 *            Name of the group nature to add to the group.
	 * 
	 * @throws IllegalArgumentException
	 *             If no group nature with the given name exists.
	 */
	public void addGroupNature(int groupId, String natureName);

	/**
	 * Removes the given nature from the resource group with the given ID. Does nothing if the group does not have this nature.
	 * 
	 * @param groupId
	 *            ID of the resource group to remove the nature from.
	 * @param natureName
	 *            Name of the group nature to remove from the group.
	 * 
	 * @throws IllegalArgumentException
	 *             If no group nature with the given name exists.
	 */
	public void removeGroupNature(int groupId, String natureName);

	/**
	 * Returns the presently configured (not necessarily activated, i.e. committed) natures for the given resource group.
	 * 
	 * @param groupId
	 *            ID of the resource group to return the currently configured natures for.
	 * 
	 * @return The currently configured natures for the given resource group.
	 */
	public List<String> getGroupNatures(int groupId);

}
