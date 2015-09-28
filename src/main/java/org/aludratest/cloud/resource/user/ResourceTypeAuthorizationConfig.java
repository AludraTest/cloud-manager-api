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
package org.aludratest.cloud.resource.user;

import java.util.List;

import org.aludratest.cloud.user.User;

/**
 * Complete authorization configuration for a single resource type. Contains authorization information for all users which are
 * authorized for this resource type.
 * 
 * @author falbrech
 * 
 */
public interface ResourceTypeAuthorizationConfig {

	/**
	 * Returns the authorization information for the given user regarding this resource type, or <code>null</code> if the user is
	 * not authorized for this resource type at all.
	 * 
	 * @param user
	 *            User to retrieve authorization information for.
	 * 
	 * @return Authorization information for the user, or <code>null</code>.
	 */
	public ResourceTypeAuthorization getResourceTypeAuthorizationForUser(User user);

	/**
	 * Returns all users which are authorized for this resource type.
	 * 
	 * @return All users which are authorized for this resource type, possibly an empty list, but never <code>null</code>.
	 */
	public List<User> getConfiguredUsers();

}
