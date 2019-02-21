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

/**
 * The configuration settings valid for the AludraTest Cloud Manager application core. An instance of this interface can be
 * retrieved by getting the {@link CloudManagerAppConfig} object via Dependency Injection and querying it for the current
 * settings.
 *
 * @author falbrech
 *
 */
/**
 * @author falbrech
 *
 */
public interface CloudManagerAppSettings {

	/**
	 * Returns the name of the host AludraTest Cloud Manager is running on. This can be used in generated URLs.
	 *
	 * @return The name of the host AludraTest Cloud Manager is running on.
	 */
	public String getHostName();

	/**
	 * Returns <code>true</code> if an HTTP proxy shall be used for accessing the internet.
	 *
	 * @return <code>true</code> if an HTTP proxy shall be used for accessing the internet, <code>false</code> otherwise.
	 *
	 * @see #getProxyHost()
	 * @see #getProxyPort()
	 * @see #getBypassProxyRegexp()
	 *
	 */
	public boolean isUseProxy();

	/**
	 * Returns the host of the HTTP proxy to use to access the internet, if any. Configuration value should only be used if
	 * {@link #isUseProxy()} returned <code>true</code>.
	 *
	 * @return The host of the HTTP proxy to use to access the internet, or <code>null</code>.
	 */
	public String getProxyHost();

	/**
	 * Returns the TCP port of the HTTP proxy to use to access the internet. Configuration value should only be used if
	 * {@link #isUseProxy()} returned <code>true</code>.
	 *
	 * @return The TCP port of the HTTP proxy to use to access the internet.
	 */
	public int getProxyPort();

	/**
	 * Returns a regular expression to match host names against. Any host name matching this regular expression should <b>not</b>
	 * be accessed through the configured HTTP proxy, but directly. Configuration value should only be used if
	 * {@link #isUseProxy()} returned <code>true</code>.
	 *
	 * @return A regular expression for hosts to bypass the HTTP proxy for, or <code>null</code>. Treat <code>null</code> and an
	 *         empty String as "do not bypass proxy for ANY host".
	 */
	public String getBypassProxyRegexp();

	/**
	 * Returns the name of the user authentication source, as it is stored in the application's configuration. This may or may not
	 * refer to a valid user database from the application's user database registry.
	 *
	 * @return The name of the user authentication source, as it is stored in the application's configuration.
	 */
	public String getUserAuthenticationSource();

}
