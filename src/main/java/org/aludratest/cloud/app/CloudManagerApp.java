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

import org.aludratest.cloud.config.ConfigException;

/**
 * Interface representing the currently running AludraTest Cloud Manager Application. You should rarely need to inject an instance
 * of this class into your objects.
 *
 * @author falbrech
 */
public abstract class CloudManagerApp {

	protected CloudManagerApp() {
	}

	/**
	 * Starts the Application.
	 *
	 * @throws ConfigException
	 *             If the persisted configuration of the application is invalid.
	 */
	public abstract void start() throws ConfigException;

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

}
