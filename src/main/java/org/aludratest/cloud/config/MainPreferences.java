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
 * The MainPreferences Preferences model the "main" Preferences of the application, which are the global, always-valid, persisted
 * Preferences to use for the application. All configurable elements of the application have their own node within this
 * MainPreferences tree. <br>
 * Main Preferences offer some operations special to this tree. You can add listeners to nodes of this tree which are informed in
 * case of configuration changes. You can create "virtual" child nodes by calling {@link #getOrCreateChildNode(String)} (for
 * non-existing nodes) which are only persisted as soon as content is added to them. <br>
 * There is no way to modify the MainPreferences tree directly (apart from the virtual nodes). To modify the MainPreferences tree,
 * you have to call <code>CloudManagerApp.getConfigManager().applyConfig()</code>.
 * 
 * @author falbrech
 * 
 */
public interface MainPreferences extends Preferences {

	/**
	 * Adds a Preferences Listener to this Preferences object, which will be informed about changes within the Preferences. If an
	 * equal listener is already registered, it is not added again.
	 * 
	 * @param listener
	 *            Listener to add to this Preferences object.
	 */
	public void addPreferencesListener(PreferencesListener listener);

	/**
	 * Removes a Preferences from this Preferences object.
	 * 
	 * @param listener
	 *            Listener to remove from this Preferences object.
	 */
	public void removePreferencesListener(PreferencesListener listener);

	@Override
	public MainPreferences getParent();

	@Override
	public MainPreferences getChildNode(String name);

	/**
	 * This method returns the child node with the given name, or creates a new empty child node if no child node with that name
	 * exists. This method does <b>not</b> inform listeners about Preferences change. This allows composite Configurable objects
	 * to create configuration nodes for their child objects and pass them to their <code>setPreferences()</code> method.
	 * Nevertheless, these newly created nodes cannot be assumed to be persistent until the <code>ConfigManager</code> has been
	 * used to apply a configuration within the newly created node.
	 * 
	 * @param name
	 *            Name of the child Preferences node to retrieve or create.
	 * 
	 * @return The existing or newly create child node, never <code>null</code>.
	 */
	public MainPreferences getOrCreateChildNode(String name);

}
