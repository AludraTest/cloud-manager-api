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
package org.aludratest.cloud.resourcegroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aludratest.cloud.app.CloudManagerApp;
import org.aludratest.cloud.config.ConfigException;
import org.aludratest.cloud.config.Configurable;
import org.aludratest.cloud.config.MainPreferences;
import org.aludratest.cloud.config.MutablePreferences;
import org.aludratest.cloud.config.Preferences;
import org.aludratest.cloud.config.PreferencesListener;
import org.aludratest.cloud.config.admin.AbstractConfigurationAdmin;
import org.aludratest.cloud.config.admin.ConfigurationAdmin;
import org.aludratest.cloud.resource.ResourceType;
import org.aludratest.cloud.user.StoreException;
import org.aludratest.cloud.user.User;
import org.aludratest.cloud.user.UserDatabase;
import org.aludratest.cloud.user.admin.UserDatabaseRegistry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Abstract base implementation for resource groups which can limit access to their resources to some users. Authorization
 * configuration is read from group Preferences, and can be administered using the {@link AuthorizingResourceGroupAdmin} interface
 * retrieved via {@link #getAdminInterface(Class)}.
 * 
 * @author falbrech
 * 
 */
public abstract class AbstractAuthorizingResourceGroup implements AuthorizingResourceGroup, Configurable {

	private static final String PREFS_LIMIT_USERS_KEY = "limitUsers";

	private static final String PREFS_USERS_KEY = "users";

	private static final String JSON_USER_NAME = "name";

	private static final String JSON_USER_SOURCE = "source";

	private boolean limitUsers;

	private List<JSONObject> allowedUsers = new ArrayList<JSONObject>();

	private MainPreferences preferences;

	private ResourceType resourceType;

	protected AbstractAuthorizingResourceGroup(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	@Override
	public final ResourceType getResourceType() {
		return resourceType;
	}

	@Override
	public void validateConfiguration(Preferences preferences) throws ConfigException {
		String users = preferences.getStringValue(PREFS_USERS_KEY);
		try {
			if (users != null && !"".equals(users.trim())) {
				JSONObject obj = new JSONObject(users);
				JSONArray arr = obj.getJSONArray("users");
				for (int i = 0; i < arr.length(); i++) {
					arr.getJSONObject(i);
				}
			}
		}
		catch (JSONException e) {
			throw new ConfigException("Invalid user JSON data", e);
		}
	}

	@Override
	public void fillDefaults(MutablePreferences preferences) {
		// no defaults here.
	}

	@Override
	public final void setPreferences(MainPreferences preferences) throws ConfigException {
		if (this.preferences != null) {
			this.preferences.removePreferencesListener(preferencesListener);

		}
		this.preferences = preferences;
		preferences.addPreferencesListener(preferencesListener);
		configure(preferences);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ConfigurationAdmin> T getAdminInterface(Class<T> ifaceClass) {
		if (ifaceClass == AuthorizingResourceGroupAdmin.class) {
			return (T) new AuthorizingAdmin();
		}

		return null;
	}

	protected void configure(MainPreferences preferences) throws ConfigException {
		loadUserConfigFromPreferences(preferences);
	}

	protected final MainPreferences getPreferences() {
		return preferences;
	}

	private void loadUserConfigFromPreferences(Preferences preferences) throws ConfigException {
		// load which users may access this resource group (if any)
		limitUsers = preferences.getBooleanValue(PREFS_LIMIT_USERS_KEY, false);
		allowedUsers.clear();

		String users = preferences.getStringValue(PREFS_USERS_KEY);
		try {
			if (users != null && !"".equals(users.trim())) {
				JSONObject obj = new JSONObject(users);
				JSONArray arr = obj.getJSONArray("users");
				for (int i = 0; i < arr.length(); i++) {
					allowedUsers.add(arr.getJSONObject(i));
				}
			}
		}
		catch (JSONException e) {
			throw new ConfigException("Invalid user JSON data", e);
		}
	}

	@Override
	public boolean isLimitingUsers() {
		return limitUsers;
	}

	@Override
	public boolean isUserAuthorized(User user) {
		if (!limitUsers) {
			return true;
		}

		String userName = user.getName();
		String source = user.getSource();

		boolean found = false;
		for (JSONObject obj : allowedUsers) {
			try {
				if (obj.has(JSON_USER_NAME) && obj.has(JSON_USER_SOURCE) && userName.equals(obj.get(JSON_USER_NAME))
						&& source.equals(obj.get(JSON_USER_SOURCE))) {
					found = true;
					break;
				}
			}
			catch (JSONException e) {
				// ignore this entry
			}
		}

		return found;
	}

	private class AuthorizingAdmin extends AbstractConfigurationAdmin implements AuthorizingResourceGroupAdmin {

		protected AuthorizingAdmin() {
			super(preferences);
		}

		@Override
		public void setLimitingUsers(boolean limitUsers) {
			assertNotCommitted();
			getPreferences().setValue(PREFS_LIMIT_USERS_KEY, limitUsers);
		}

		@Override
		public void addAuthorizedUser(User user) {
			assertNotCommitted();
			JSONObject newUser = new JSONObject();

			String users = getPreferences().getStringValue(PREFS_USERS_KEY);
			try {
				newUser.put(JSON_USER_NAME, user.getName());
				newUser.put(JSON_USER_SOURCE, user.getSource());

				JSONObject obj;
				if (users != null && !"".equals(users.trim())) {
					obj = new JSONObject(users);
					JSONArray arr = obj.getJSONArray("users");
					boolean found = false;
					for (int i = 0; !found && i < arr.length(); i++) {
						if (equals(arr.getJSONObject(i), newUser)) {
							found = true;
						}
					}
					if (!found) {
						arr.put(newUser);
					}
				}
				else {
					obj = new JSONObject();
					JSONArray arr = new JSONArray();
					arr.put(newUser);
					obj.put("users", arr);
				}
				getPreferences().setValue("users", obj.toString());
			}
			catch (JSONException e) {
				// ignore
			}
		}

		private boolean equals(JSONObject o1, JSONObject o2) throws JSONException {
			return o1.getString(JSON_USER_NAME).equals(o2.getString(JSON_USER_NAME))
					&& o1.getString(JSON_USER_SOURCE).equals(o2.getString(JSON_USER_SOURCE));
		}

		@Override
		public void removeAuthorizedUser(User user) {
			assertNotCommitted();
			String users = getPreferences().getStringValue("users");
			try {
				if (users != null && !"".equals(users.trim())) {
					JSONObject obj = new JSONObject(users);
					JSONArray arr = obj.getJSONArray("users");

					int index = -1;
					for (int i = 0; i < arr.length() && index == -1; i++) {
						JSONObject userObj = arr.getJSONObject(i);
						if (userObj.has(JSON_USER_NAME) && userObj.has(JSON_USER_SOURCE)
								&& user.getName().equals(userObj.get(JSON_USER_NAME))
								&& user.getSource().equals(userObj.get(JSON_USER_SOURCE))) {
							index = i;
						}
					}
					if (index > -1) {
						JSONArray newArr = new JSONArray();
						for (int i = 0; i < arr.length(); i++) {
							if (i != index) {
								newArr.put(arr.getJSONObject(i));
							}
						}
						obj.put("users", newArr);
						getPreferences().setValue("users", obj.toString());
					}
				}
			}
			catch (JSONException e) {
				// ignore
			}
		}

		@Override
		public List<User> getConfiguredAuthorizedUsers() throws StoreException {
			assertNotCommitted();
			String users = getPreferences().getStringValue("users");

			if (users == null || "".equals(users.trim())) {
				return Collections.emptyList();
			}

			UserDatabaseRegistry registry = CloudManagerApp.getInstance().getUserDatabaseRegistry();

			try {
				JSONObject obj = new JSONObject(users);
				JSONArray arr = obj.getJSONArray("users");

				List<User> result = new ArrayList<User>();

				for (int i = 0; i < arr.length(); i++) {
					JSONObject userObj = arr.getJSONObject(i);
					if (userObj.has(JSON_USER_NAME) && userObj.has(JSON_USER_SOURCE)) {
						UserDatabase db = registry.getUserDatabase(userObj.getString(JSON_USER_SOURCE));
						if (db != null) {
							User user = db.findUser(userObj.getString(JSON_USER_NAME));
							if (user != null) {
								result.add(user);
							}
						}
					}
				}

				return result;
			}
			catch (JSONException e) {
				return Collections.emptyList();
			}
		}

		@Override
		protected void validateConfig(Preferences preferences) throws ConfigException {
			validateConfiguration(preferences);
		}
	}

	private PreferencesListener preferencesListener = new PreferencesListener() {
		@Override
		public void preferencesChanged(Preferences oldPreferences, MainPreferences newPreferences) throws ConfigException {
			configure(newPreferences);
		}

		@Override
		public void preferencesAboutToChange(Preferences oldPreferences, Preferences newPreferences) throws ConfigException {
			validateConfiguration(newPreferences);
		}
	};
}
