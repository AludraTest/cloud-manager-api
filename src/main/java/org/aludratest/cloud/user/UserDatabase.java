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
package org.aludratest.cloud.user;

import java.util.Iterator;

import org.databene.commons.Filter;

/**
 * A User Database is responsible for authenticating users and providing the full list of available users.
 * 
 * @author falbrech
 * 
 */
public interface UserDatabase {

	/**
	 * Plexus role of this component.
	 */
	public static final String ROLE = UserDatabase.class.getName();

	/**
	 * Returns the "source" identifier of this user database. This is defined by the implementation of the user database. Common
	 * values would be "local" for a local file-base user database, or "ldap" for an Active Directory based user database. <br>
	 * The source should be as unique as possible, so there should be no two different implementations of the UserDatabase
	 * interface returning user objects with the same source identifier. <br>
	 * When registering the user database implementation using Plexus, this identifier must also be used as the "hint" of the
	 * component.
	 * 
	 * @return The source identifier of this user database, never <code>null</code>, and never an empty string.
	 */
	public String getSource();

	/**
	 * Returns an iterator which can be used to iterate over the list of users available in this User Database. An optional filter
	 * can be provided to limit the result set.
	 * 
	 * @param userFilter
	 *            Filter to use to limit the returned result set, or <code>null</code> to not apply a filter.
	 * 
	 * @return An iterator over the (optionally filtered) set of users available in this User Database, possibly not providing any
	 *         elements, but never <code>null</code>.
	 * @throws StoreException
	 *             If any I/O exception occurs when querying the underlying persistence store.
	 */
	public Iterator<User> getAllUsers(Filter<User> userFilter) throws StoreException;

	/**
	 * Performs an authentication check for given user name / password combination.
	 * 
	 * @param userName
	 *            User name.
	 * @param password
	 *            Password for given user name.
	 * 
	 * @return The user with the given user name, if the password was correct for the given user name, or <code>null</code> if
	 *         password was incorrect or no such user exists in this User Database.
	 * 
	 * @throws StoreException
	 *             If any I/O exception occurs when querying the underlying persistence store.
	 */
	public User authenticate(String userName, String password) throws StoreException;

	/**
	 * Finds the user with the given user name in this User Database.
	 * 
	 * @param userName
	 *            User name to search for.
	 * @return The found user object, or <code>null</code> if no such user exists in this User Database.
	 * @throws StoreException
	 *             If any I/O exception occurs when querying the underlying persistence store.
	 */
	public User findUser(String userName) throws StoreException;

	/**
	 * Indicates if this user database is read only, e.g. when using an LDAP directory.
	 * 
	 * @return <code>true</code> if calls for creating, deleting or modifying a user will result in an
	 *         <code>UnsupportedOperationException</code>, <code>false</code> otherwise.
	 */
	public boolean isReadOnly();

	/**
	 * Deletes the given user object from this User Database.
	 * 
	 * @param user
	 *            User to delete from this user database.
	 * @throws StoreException
	 *             If any I/O exception occurs when reading from or writing to the underlying persistence store.
	 * @throws UnsupportedOperationException
	 *             If modification of this User Database is not possible (check with {@link #isReadOnly()}).
	 */
	public void delete(User user) throws StoreException;

	/**
	 * Creates a new user in this User Database.
	 * 
	 * @param userName
	 *            User name of the user to create.
	 * @return The newly created user object, which can be used to change the user's password and the attributes.
	 * @throws IllegalArgumentException
	 *             If a user with the given user name already exists in this User Database.
	 * @throws StoreException
	 *             If any I/O exception occurs when reading from or writing to the underlying persistence store.
	 * @throws UnsupportedOperationException
	 *             If modification of this User Database is not possible (check with {@link #isReadOnly()}).
	 */
	public User create(String userName) throws IllegalArgumentException, StoreException;

	/**
	 * Changes the password of the given user in this User Database.
	 * 
	 * @param user
	 *            User whose password shall be changed.
	 * @param newPassword
	 *            New password for the user.
	 * @throws StoreException
	 *             If any I/O exception occurs when reading from or writing to the underlying persistence store.
	 * @throws UnsupportedOperationException
	 *             If modification of this User Database is not possible (check with {@link #isReadOnly()}).
	 */
	public void changePassword(User user, String newPassword) throws StoreException;

	/**
	 * Modifies an attribute of the given user.
	 * 
	 * @param user
	 *            User object.
	 * @param attributeKey
	 *            Key of the attribute to modify.
	 * @param newAttributeValue
	 *            New value for the attribute.
	 * @throws IllegalArgumentException
	 *             If the attribute key is not supported by this User Database (see {@link #supportsUserAttribute(String)}).
	 * @throws StoreException
	 *             If any I/O exception occurs when reading from or writing to the underlying persistence store.
	 * @throws UnsupportedOperationException
	 *             If modification of this User Database is not possible (check with {@link #isReadOnly()}).
	 */
	public void modifyUserAttribute(User user, String attributeKey, String newAttributeValue) throws IllegalArgumentException,
			StoreException;

	/**
	 * Determines if a given attribute key is supported by this User Database (for modification purposes). Some implementations
	 * may only support some attribute keys, depending on their underlying implementation.
	 * 
	 * @param attributeKey
	 *            Attribute key.
	 * @return <code>true</code> if the attribute is supported within this User Database, <code>false</code> otherwise. Read-only
	 *         User Databases always will return <code>false</code>.
	 */
	public boolean supportsUserAttribute(String attributeKey);

}
