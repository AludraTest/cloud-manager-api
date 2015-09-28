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

import org.aludratest.cloud.user.User;

/**
 * Interface for mutable resource type authorization configurations. Use an implementation of this interface to modify the
 * authorization configuration of a resource type and pass it back to the {@link ResourceTypeAuthorizationStore}.
 * 
 * @author falbrech
 * 
 */
public interface MutableResourceTypeAuthorizationConfig extends ResourceTypeAuthorizationConfig {

	/**
	 * Adds the given user and authorization to the authorization configuration.
	 * 
	 * @param user
	 *            User to add to the authorization configuration.
	 * @param authorization
	 *            Authorization for the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If there already is an authorization set for the user. Use
	 *             {@link #editUserAuthorization(User, ResourceTypeAuthorization)} in that case instead.
	 */
	public void addUser(User user, ResourceTypeAuthorization authorization) throws IllegalArgumentException;

	/**
	 * Removes the given user from this authorization configuration.
	 * 
	 * @param user
	 *            User to remove from the authorization configuration.
	 * @throws IllegalArgumentException
	 *             If the user is not known to this authorization configuration.
	 */
	public void removeUser(User user) throws IllegalArgumentException;

	/**
	 * Replaces the authorization for an already authorized user with a new one.
	 * 
	 * @param user
	 *            User whose authorization shall be replaced.
	 * @param newAuthorization
	 *            New authorization for the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If the user is not knotn to this authorization configuration. User
	 *             {@link #addUser(User, ResourceTypeAuthorization)} in that case instead.
	 */
	public void editUserAuthorization(User user, ResourceTypeAuthorization newAuthorization) throws IllegalArgumentException;

}
