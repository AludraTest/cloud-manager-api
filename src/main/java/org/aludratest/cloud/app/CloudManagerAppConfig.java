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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.aludratest.cloud.config.Configurable;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * The basic configuration object of the AludraTest Cloud Manager application. The singleton instance of this interface can be
 * retrieved via Dependency Injection.
 *
 * @author falbrech
 *
 */
public interface CloudManagerAppConfig extends Configurable {

	/**
	 * Retrieves the currently valid settings of the application. The settings can change at any time, so the returned reference
	 * should not be stored for a longer time.
	 *
	 * @return The currently valid settings of the application, never <code>null</code>.
	 */
	public CloudManagerAppSettings getCurrentSettings();


	/**
	 * Builds and returns a ProxySelector which can be used for HTTP connections which use a proxy according to the application's
	 * settings. Changes in the settings are reflected dynamically, so for each call to <code>select()</code> of the returned
	 * ProxySelector, the current settings are retrieved and used.
	 *
	 * @return A ProxySelector which can be used for HTTP connections, never <code>null</code>.
	 */
	default ProxySelector buildProxySelector() {
		return new ProxySelector() {
			@Override
			public List<Proxy> select(URI uri) {
				CloudManagerAppSettings settings = getCurrentSettings();

				if (!settings.isUseProxy() || StringUtils.isEmpty(settings.getProxyHost())) {
					return Collections.emptyList();
				}

				if (!StringUtils.isEmpty(settings.getBypassProxyRegexp())) {
					try {
						Pattern p = Pattern.compile(settings.getBypassProxyRegexp());
						Matcher m = p.matcher(uri.getHost());
						if (m.matches()) {
							return Collections.emptyList();
						}
					}
					catch (PatternSyntaxException e) {
						// this should be caught by application's configure, and not occur.
					}
				}

				int proxyPort = settings.getProxyPort();
				if (proxyPort <= 0 || proxyPort > 65535) {
					proxyPort = 80;
				}
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(settings.getProxyHost(), proxyPort));
				return Collections.singletonList(proxy);
			}

			@Override
			public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
				LogFactory.getLog(CloudManagerAppConfig.class).error("Configured HTTP proxy seems to be invalid", ioe);
			}
		};

	}

}
