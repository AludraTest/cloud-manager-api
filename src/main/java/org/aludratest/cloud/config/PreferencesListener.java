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
 * Listener interface for changes on a MainPreferences object. Usually, this listener is notified as soon as some code changes the
 * Main Preferences using {@link ConfigManager#applyConfig(Preferences, MainPreferences)}.
 * 
 * @author falbrech
 * 
 */
public interface PreferencesListener {

	/**
	 * Notifies the listener that the Preferences object which is listened at is about to change. This change can include change
	 * of serveral keys, including addition or removal, and also addition or removal of child nodes. <br>
	 * The listener should verify that the new configuration is acceptable and, if not, throw a {@link ConfigException} describing
	 * what is wrong with the new configuration.
	 * 
	 * @param oldPreferences
	 *            Current state of the changing Preferences node.
	 * @param newPreferences
	 *            Proposed (but not yet applied) new state of the Preferences node.
	 * 
	 * @throws ConfigException
	 *             If the listener wants to indicate that this change would not be acceptable. The message of the exception should
	 *             be human-readable and describe what is wrong with the new configuration.
	 */
	public void preferencesAboutToChange(Preferences oldPreferences, Preferences newPreferences) throws ConfigException;

	/**
	 * Notifies the listener that the Preferences object which is listened at has changed. This change can include change of
	 * serveral keys, including addition or removal, and also addition or removal of child nodes. <br>
	 * The listener should apply the configuration if it is relevant for him. If the configuration could not be applied
	 * successfully, a {@link ConfigException} should be thrown, describing what went wrong when applying the configuration (can
	 * include another exception as a root cause).
	 * 
	 * @param oldPreferences
	 *            Previous state of the changed Preferences node.
	 * @param newPreferences
	 *            Applied new state of the Preferences node.
	 * 
	 * @throws ConfigException
	 *             If the listener could not apply the configuration. The message of the exception should be human-readable and
	 *             describe what went wrong during applying the configuration. This should include an exception as a root cause
	 *             when applicable.
	 */
	public void preferencesChanged(Preferences oldPreferences, MainPreferences newPreferences) throws ConfigException;

}
