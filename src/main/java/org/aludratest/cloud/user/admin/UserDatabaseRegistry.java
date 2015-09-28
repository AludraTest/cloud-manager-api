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
package org.aludratest.cloud.user.admin;

import java.util.List;

import org.aludratest.cloud.user.UserDatabase;

/**
 * A User Database Registry holds the list of all registered User Databases (e.g. file based, LDAP) available for selection as
 * main source for user authentication and user lists.
 * 
 * @author falbrech
 * 
 */
public interface UserDatabaseRegistry {

	/**
	 * Plexus role of this component.
	 */
	public static final String ROLE = UserDatabaseRegistry.class.getName();

	/**
	 * The list of all registered User Databases available for selection as main source for authentication and user lists.
	 * 
	 * @return The list of all registered User Databases available for selection as main source for authentication and user lists.
	 */
	public List<UserDatabase> getAllUserDatabases();

	/**
	 * Returns the user database with the given source name.
	 * 
	 * @param sourceName
	 *            Source name to return matching user database for.
	 * 
	 * @return The user database with the given source name, or <code>null</code> if no user database was found.
	 */
	public UserDatabase getUserDatabase(String sourceName);

}
