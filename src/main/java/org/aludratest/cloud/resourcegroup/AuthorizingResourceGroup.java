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

import org.aludratest.cloud.user.User;

/**
 * Interface for resource groups being able to limit the access to their resources to some users.
 * 
 * @author falbrech
 * 
 */
public interface AuthorizingResourceGroup extends ResourceGroup {

	/**
	 * Returns <code>true</code> if the feature of limiting access to users is active for this group, <code>false</code>
	 * otherwise.
	 * 
	 * @return <code>true</code> if the feature of limiting access to users is active for this group, <code>false</code>
	 *         otherwise.
	 */
	public boolean isLimitingUsers();

	/**
	 * Returns <code>true</code> if the given user is allowed to use the resources in this resource group, and <code>false</code>
	 * otherwise. If {@link #isLimitingUsers()} returns <code>false</code>, this method always returns <code>true</code>.
	 * 
	 * @param user
	 *            User to check access to this resource group of.
	 * @return <code>true</code> if the given user is allowed to use the resources in this resource group, and <code>false</code>
	 *         otherwise.
	 */
	public boolean isUserAuthorized(User user);

}
