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

import org.aludratest.cloud.config.admin.ConfigurationAdmin;
import org.aludratest.cloud.user.StoreException;
import org.aludratest.cloud.user.User;

/**
 * Configuration administration interface for resource groups being able to limit the access to their resources to some users.
 * 
 * @author falbrech
 * 
 */
public interface AuthorizingResourceGroupAdmin extends ConfigurationAdmin {

	/**
	 * Activates or deactivates the feature of limiting access to the resource group based on user information.
	 * 
	 * @param limitUsers
	 *            <code>true</code> to activate the feature, <code>false</code> otherwise.
	 */
	public void setLimitingUsers(boolean limitUsers);

	/**
	 * Adds a user to the list of users which are allowed to access the resources of the group.
	 * 
	 * @param user
	 *            User to add to the list of users which are allowed to access the resources of the group.
	 */
	public void addAuthorizedUser(User user);

	/**
	 * Removes a user from the list of users which are allowed to access the resources of the group.
	 * 
	 * @param user
	 *            User to remove from the list of users which are allowed to access the resources of the group.
	 */
	public void removeAuthorizedUser(User user);

	/**
	 * Returns the list of users which are <i>configured</i> to have access to the resource group. The feature for limiting the
	 * access to only these users may still be deactivated, so this is not always the list of users having effective access to the
	 * group.
	 * 
	 * @return List of users which are <i>configured</i> to have access to the resource group.
	 * 
	 * @throws StoreException
	 *             If the user objects could not be loaded from the current User database.
	 */
	public List<User> getConfiguredAuthorizedUsers() throws StoreException;

}
