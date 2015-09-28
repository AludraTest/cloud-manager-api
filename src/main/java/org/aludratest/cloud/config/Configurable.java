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

import org.aludratest.cloud.config.admin.ConfigurationAdmin;

/**
 * Interface for objects being able to be configured. Implementing objects can be configured using
 * {@link #setPreferences(MainPreferences)}. Implementors should attach listeners to the given MainPreferences object and
 * reconfigure themselves when configuration changes. <br>
 * Additionally, implementors can provide Configuration Administration interfaces which provide transaction-based operations on
 * the Preferences of the object.
 * 
 * @author falbrech
 * 
 */
public interface Configurable {

	/**
	 * Fills in this Configurable's default configuration into the given mutable Preferences structure.
	 * 
	 * @param preferences
	 *            Preferences (most probably empty) to fill with this Configurable's default values.
	 */
    public void fillDefaults(MutablePreferences preferences);

	/**
	 * Validates the given configuration. If there are configuration errors which cannot be changed automatically, a
	 * ConfigException is thrown.
	 * 
	 * @param preferences
	 *            Preferences to validate.
	 * @throws ConfigException
	 *             If the configuration is invalid. The exception's message should be human-readable to e.g. present it in a web
	 *             interface.
	 */
	public void validateConfiguration(Preferences preferences) throws ConfigException;

	/**
	 * Sets this Configurable's main Preferences location. The Configurable should:
	 * <ul>
	 * <li>(Re)configure itself based on the current Preferences values. This should include comparing the current internal state
	 * to the new configuration and only apply changes really necessary,</li>
	 * <li>Attach listeners to the Preferences object to listen for Preferences changes,</li>
	 * <li>Detach listeners from previous Preferences object, if any.</li>
	 * </ul>
	 * 
	 * @param preferences
	 *            Main Preferences location for this Configurable.
	 * 
	 * @throws ConfigException
	 *             If the configuration could not be applied to this Configurable for whatever reason.
	 */
	public void setPreferences(MainPreferences preferences) throws ConfigException;

	/**
	 * Returns an interface to work on the Configuration of this Configurable. The interface should:
	 * <ul>
	 * <li>Copy the Configurable's current Preferences into a memory object,</li>
	 * <li>offer methods to work on this memory object,</li>
	 * <li>on <code>commit()</code>, first validate the new Configuration using validateConfiguration(), and then</li>
	 * <li>copy the working configuration copy back into the main Configuration location, using the ConfigManager of the current
	 * CloudManagerApplication instance.</li>
	 * </ul>
	 * 
	 * @param ifaceClass
	 *            Interface of the Configuration Admin to retrieve.
	 * 
	 * @return The Configuration Admin implementing the given interface, or <code>null</code> if no such admin interface is
	 *         available for this Configurable.
	 */
	public <T extends ConfigurationAdmin> T getAdminInterface(Class<T> ifaceClass);

}
