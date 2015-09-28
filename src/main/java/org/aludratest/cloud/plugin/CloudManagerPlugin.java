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
package org.aludratest.cloud.plugin;

/**
 * Interface for generic AludraTest Cloud Manager Plugins. Plugins may contribute to the main menu and to the configuration page.
 * For specialized resource types, implement a ResourceModule instead. <br>
 * For additions to existing group types, consider implementing a <code>ResourceGroupNature</code>. <br>
 * This class is merely an "indicator" that a plugin exists. Use Plexus to register your plugin, using a non-default "hint". The
 * hint is the key of your plugin which is used to retrieve the resources of your plugin:
 * <ul>
 * <li>/META-INF/resources/acm/<i>&lt;hint></i>/configSection.xhtml - the configuration section, if any,</li>
 * <li>/META-INF/resources/acm/<i>&lt;hint></i>/menuItems.xhtml - the main menu items, if any.</li>
 * </ul>
 * 
 * @author falbrech
 * 
 */
public interface CloudManagerPlugin {

	/**
	 * Plexus role of this component.
	 */
	public static final String ROLE = CloudManagerPlugin.class.getName();

	/**
	 * Returns a display name of this plugin.
	 * 
	 * @return A human-readable display name of this plugin.
	 */
	public String getDisplayName();

	/**
	 * Is called when the AludraTest Cloud Manager application has been started.
	 */
	public void applicationStarted();

	/**
	 * Is called when the AludraTest Cloud Manager application is about to stop.
	 */
	public void applicationStopped();

}
