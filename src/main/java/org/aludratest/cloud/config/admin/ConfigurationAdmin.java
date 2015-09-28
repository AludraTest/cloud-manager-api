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
package org.aludratest.cloud.config.admin;

import org.aludratest.cloud.config.ConfigException;
import org.aludratest.cloud.config.ConfigManager;
import org.aludratest.cloud.config.Configurable;
import org.aludratest.cloud.config.MainPreferences;
import org.aludratest.cloud.config.Preferences;

/**
 * Interface for objects being able to modify the configuration of a Configurable. See
 * {@link Configurable#getAdminInterface(Class)} for details.
 * 
 * @author falbrech
 * 
 */
public interface ConfigurationAdmin {

	/**
	 * Commits all changes performed in this admin object. On a call to <code>commit()</code>,
	 * <ul>
	 * <li>The Configurable's {@link Configurable#validateConfiguration(Preferences)} method should be called to verify the new
	 * configuration created by the admin object,</li>
	 * <li>The {@link MainPreferences} object assigned to the Configurable should be updated (e.g. using the application's
	 * {@link ConfigManager}),</li>
	 * <li>The Configurable should be reconfigured to reflect the changes to its configuration.</li>
	 * </ul>
	 * 
	 * @throws ConfigException
	 *             If the configuration built with this admin object is invalid or could not be applied.
	 */
	public void commit() throws ConfigException;

}
