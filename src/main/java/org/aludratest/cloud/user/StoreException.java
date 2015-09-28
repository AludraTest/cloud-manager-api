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
 * Exceptions of this type signal I/O problems with user-related stores, e.g. User Databases.
 * 
 * @author falbrech
 * 
 */
public class StoreException extends Exception {

	private static final long serialVersionUID = -36041025367770801L;

	/**
	 * Constructs a new exception of this type.
	 * 
	 * @param message
	 *            Exception message.
	 * @param cause
	 *            Root cause of the exception, e.g. an <code>IOException</code> or <code>SQLException</code>.
	 */
	public StoreException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception of this type.
	 * 
	 * @param message
	 *            Exception message.
	 */
	public StoreException(String message) {
		super(message);
	}

}
