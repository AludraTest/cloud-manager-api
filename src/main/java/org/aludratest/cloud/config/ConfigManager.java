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
 * Component responsible for modifying the Main Preferences of an application. Retrive this component from
 * <code>CloudManagerApp.getInstance().getConfigManager()</code> if you want to modify a part of the Main Preferences.
 * 
 * @author falbrech
 * 
 */
public interface ConfigManager {

	/**
	 * Plexus role of this component.
	 */
	public static final String ROLE = ConfigManager.class.getName();

	/**
	 * Applies the given new Configuration to a Preferences object of the main configuration tree.
	 * 
	 * @param newConfig
	 *            New configuration to apply.
	 * @param mainConfig
	 *            Node of the main configuration tree, which had been passed to
	 *            {@link Configurable#setPreferences(MainPreferences)} of a Configurable object.
	 * 
	 * @throws ConfigException
	 *             If the configuration could not be applied.
	 * @throws IllegalArgumentException
	 *             If <code>mainConfig</code> is not a node of the main configuration tree.
	 */
	public void applyConfig(Preferences newConfig, MainPreferences mainConfig) throws ConfigException, IllegalArgumentException;

}
