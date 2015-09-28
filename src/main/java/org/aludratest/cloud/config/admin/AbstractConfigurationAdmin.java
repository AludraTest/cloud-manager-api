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

import org.aludratest.cloud.app.CloudManagerApp;
import org.aludratest.cloud.config.ConfigException;
import org.aludratest.cloud.config.ConfigUtil;
import org.aludratest.cloud.config.MainPreferences;
import org.aludratest.cloud.config.MutablePreferences;
import org.aludratest.cloud.config.Preferences;
import org.aludratest.cloud.config.SimplePreferences;

/**
 * Abstract base implementation for Configuration Admin objects. This base implementation takes a given node of the
 * MainPreferences tree, creates a temporary memory-based Preferences tree to work on, and writes the configuration back to the
 * MainPreferences on {@link #commit()}. Subclasses can add read and write methods which all must work on the Preferences returned
 * by {@link #getPreferences()}, and implement {@link #validateConfig(Preferences)} to ensure validity of a given configuration.
 * 
 * @author falbrech
 * 
 */
public abstract class AbstractConfigurationAdmin implements ConfigurationAdmin {

	private MainPreferences mainPreferences;

	private SimplePreferences workingPreferences;

	private boolean committed;

	/**
	 * Creates a new configuration admin instance which works on the given node of the MainPreferences tree.
	 * 
	 * @param mainPreferences
	 *            Node of the MainPreferences tree to work on.
	 */
	protected AbstractConfigurationAdmin(MainPreferences mainPreferences) {
		this.mainPreferences = mainPreferences;
		workingPreferences = new SimplePreferences(null);
		ConfigUtil.copyPreferences(mainPreferences, workingPreferences);
	}

	@Override
	public void commit() throws ConfigException {
		assertNotCommitted();

		validateConfig(workingPreferences);

		// copy values back to original preferences
		CloudManagerApp.getInstance().getConfigManager().applyConfig(workingPreferences, mainPreferences);

		committed = true;
	}

	protected abstract void validateConfig(Preferences preferences) throws ConfigException;

	protected final MutablePreferences getPreferences() {
		return workingPreferences;
	}

	protected final void assertNotCommitted() throws IllegalStateException {
		if (committed) {
			throw new IllegalStateException("This admin interface is already committed.");
		}
	}

}
