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
package org.aludratest.cloud.config;

/**
 * Describes an exception where a configuration for a {@link Configurable} is invalid or could not be applied. The exception could
 * carry an optional <code>property</code> attribute, describing the exact name of the configuration property responsible for the
 * exception, if the error condition can be tracked down to a single property.
 * 
 * @author falbrech
 * 
 */
public class ConfigException extends Exception {

	private static final long serialVersionUID = -6853507882930876434L;

	private String property;

	/**
	 * Constructs a new ConfigException with given message and cause.
	 * 
	 * @param message
	 *            Message for the exception.
	 * @param cause
	 *            Root cause of the exception.
	 */
	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new ConfigException with given message.
	 * 
	 * @param message
	 *            Message for the exception.
	 */
	public ConfigException(String message) {
		super(message);
	}

	/**
	 * Constructs a new ConfigException with given message and cause, specifying the name of the configuration property causing
	 * this exception.
	 * 
	 * @param message
	 *            Message for the exception.
	 * @param property
	 *            Name of configuration property causing the exception.
	 * @param cause
	 *            Root cause of the exception.
	 */
	public ConfigException(String message, String property, Throwable cause) {
		super(message, cause);
		this.property = property;
	}

	/**
	 * Constructs a new ConfigException with given message, specifying the name of the configuration property causing this
	 * exception.
	 * 
	 * @param message
	 *            Message for the exception.
	 * @param property
	 *            Name of configuration property causing the exception.
	 */
	public ConfigException(String message, String property) {
		super(message);
		this.property = property;
	}

	/**
	 * Returns the name of the configuration property causing this exception, in case this could be determined.
	 * 
	 * @return The name of the configuration property causing this exception, or <code>null</code>.
	 */
	public String getProperty() {
		return property;
	}

}
