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
package org.aludratest.cloud.app;

import java.util.List;

import org.aludratest.cloud.config.ConfigException;
import org.aludratest.cloud.config.ConfigManager;
import org.aludratest.cloud.config.MainPreferences;
import org.aludratest.cloud.manager.ResourceManager;
import org.aludratest.cloud.module.ResourceModule;
import org.aludratest.cloud.resource.ResourceType;
import org.aludratest.cloud.resource.user.ResourceTypeAuthorizationStore;
import org.aludratest.cloud.resource.writer.ResourceWriterFactory;
import org.aludratest.cloud.resourcegroup.ResourceGroupManager;
import org.aludratest.cloud.user.UserDatabase;
import org.aludratest.cloud.user.admin.UserDatabaseRegistry;

/**
 * Core class representing the currently running AludraTest Cloud Manager Application. Retrieve an instance via
 * <code>getInstance()</code>. Use the instance to access all types of application-global information and objects.
 * 
 * @author falbrech
 * 
 */
public abstract class CloudManagerApp {

	protected static CloudManagerApp instance;

	protected CloudManagerApp() {
	}

	/**
	 * Retrieves the one and only instance of the currently running Cloud Manager Application. There can be only one instance per
	 * VM.
	 * 
	 * @return The one and only instance of the currently running Cloud Manager Application.
	 */
	public static final CloudManagerApp getInstance() {
		return instance;
	}

	/**
	 * Starts the Application using the given Main Preferences structure. The starter should have attached listeners to this
	 * Preferences object to e.g. persist the Preferences when they are changed.
	 * 
	 * @param preferences
	 *            Preferences to use as main preferences of the application.
	 * 
	 * @throws ConfigException
	 *             If the configuration contained in the Preferences is invalid.
	 */
	public abstract void start(MainPreferences preferences) throws ConfigException;

	/**
	 * Returns <code>true</code> if the application is currently running.
	 * 
	 * @return <code>true</code> if the application is currently running, <code>false</code> otherwise.
	 */
	public abstract boolean isStarted();

	/**
	 * Shuts the application down.
	 */
	public abstract void shutdown();

	/**
	 * Returns the Configuration Manager of the application which can be used to modify the Main Preferences of the application.
	 * 
	 * @return The Configuration Manager of the application.
	 */
	public abstract ConfigManager getConfigManager();

	/**
	 * Returns the Resource Writer Factory which can be used to serialize Resource information to responses to client resource
	 * requests.
	 * 
	 * @param resourceType
	 *            Resource Type for which to retrieve a Resource Writer Factory.
	 * 
	 * @return The Resource Writer Factory for this resource type, or <code>null</code> if no compatible writer is registered.
	 */
	public abstract ResourceWriterFactory getResourceWriterFactory(ResourceType resourceType);

	/**
	 * Returns the registry of User Databases. This can be used when source and name of a user is known to retrieve the user
	 * object.
	 * 
	 * @return The registry of User Databases.
	 */
	public abstract UserDatabaseRegistry getUserDatabaseRegistry();

	/**
	 * Returns the currently active user database which is used to perform authentication checks for resource requests.
	 * 
	 * @return The currently active user database.
	 */
	public abstract UserDatabase getSelectedUserDatabase();

	/**
	 * Returns the store for resource type authorizations. This can be queried for the authorizations assigned to a given user.
	 * 
	 * @return The store for resource type authorizations.
	 */
	public abstract ResourceTypeAuthorizationStore getResourceTypeAuthorizationStore();

	/**
	 * Returns the manager for all existing resource groups. This can be used to iterate over existing resource groups, or to
	 * retrive an admin interface and add / remove / rename groups.
	 * 
	 * @return The Resource Group Manager of the application.
	 */
	public abstract ResourceGroupManager getResourceGroupManager();

	/**
	 * Returns an unsorted list of all Resource Modules known to this application.
	 * 
	 * @return An unsorted list of all Resource Modules known to this application.
	 */
	public abstract List<ResourceModule> getAllResourceModules();

	/**
	 * Returns the resource manager of the application. This is the core component to put resource requests to. It manages all
	 * resources, the request queue, and other information.
	 * 
	 * @return The resource manager of the application.
	 */
	public abstract ResourceManager getResourceManager();

	/**
	 * Returns the basic configuration properties of the AludraTest Cloud Manager application. The returned reference should
	 * <b>not</b> be stored, as a new object could be returned when the user reconfigures the application.
	 * 
	 * @return The basic configuration properties of the AludraTest Cloud Manager application.
	 */
	public abstract CloudManagerAppConfig getBasicConfiguration();

	/**
	 * Returns the resource module for the given resource type.
	 * 
	 * @param resourceType
	 *            Resource type to retrieve the resource module for.
	 * 
	 * @return The resource module for the given resource type, or <code>null</code> if no module is registered for this resource
	 *         type.
	 */
	public final ResourceModule getResourceModule(ResourceType resourceType) {
		for (ResourceModule module : getAllResourceModules()) {
			if (module.getResourceType().equals(resourceType)) {
				return module;
			}
		}
		return null;
	}

	/**
	 * Returns the resource module for the given resource type name.
	 * 
	 * @param resourceTypeName
	 *            Name of the resource type to retrieve the resource module for.
	 * 
	 * @return The resource module for the given resource type, or <code>null</code> if no module is registered for this resource
	 *         type.
	 */
	public final ResourceModule getResourceModule(String resourceTypeName) {
		for (ResourceModule module : getAllResourceModules()) {
			if (module.getResourceType().getName().equals(resourceTypeName)) {
				return module;
			}
		}
		return null;
	}

}
