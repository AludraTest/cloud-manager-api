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


/**
 * Interface for user objects. A user object is stateless and does <b>not</b> reflect a concrete, logged in user. It only reflects
 * information about a user, like an entry in a user dictionary.
 * 
 * @author falbrech
 * 
 */
public interface User {

	/**
	 * The attribute key for the user's preferred language. A user object may or may not have this attribute set.
	 */
	public final static String USER_ATTRIBUTE_LANGUAGE = "language";

	/**
	 * The attribute key for the user's e-mail address. A user object may or may not have this attribute set.
	 */
	public final static String USER_ATTRIBUTE_EMAIL = "email_address";

	/**
	 * Returns the user name of the user.
	 * 
	 * @return The user name of the user.
	 */
	public String getName();

	/**
	 * Returns the attribute keys which are defined for this user.
	 * 
	 * @return A (possibly empty) array of attribute keys defined for this user, never <code>null</code>.
	 */
	public String[] getDefinedUserAttributes();

	/**
	 * Returns the attribute of this user for the given attribute key.
	 * 
	 * @param attributeKey
	 *            Key of the attribute to return the value for.
	 * 
	 * @return The value of the given attribute for this user, or <code>null</code> if the attribute is not set for this user.
	 */
	public String getUserAttribute(String attributeKey);

	/**
	 * The source identifier of the user database responsible for this user object.
	 * 
	 * @see UserDatabase#getSource()
	 * 
	 * @return The source identifier of the user database responsible for this user object, never <code>null</code>.
	 */
	public String getSource();

}
