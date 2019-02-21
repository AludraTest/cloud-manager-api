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

import org.aludratest.cloud.config.ConfigException;
import org.aludratest.cloud.config.ConfigManager;
import org.aludratest.cloud.config.Preferences;
import org.aludratest.cloud.config.admin.AbstractConfigNodeBased;
import org.aludratest.cloud.config.admin.AbstractConfigurationAdmin;
import org.aludratest.cloud.config.admin.ConfigNodeBasedList;
import org.aludratest.cloud.resource.Resource;

/**
 * Abstract base implementation for the {@link StaticResourceGroupAdmin} interface. It reads and writes information about static
 * group resources to / from the Group Preferences, using a subclass of <code>AbstractConfigNodeBased</code> for Preferences
 * management for single entries. <br>
 * Subclasses will have to provide their own implementation of <code>AbstractConfigNodeBased</code>.
 * 
 * @author falbrech
 * 
 * @param <R>
 *            Type of resources being described by the static configuration objects.
 * @param <T>
 *            Type of static configuration objects describing the resources.
 */
public abstract class AbstractStaticResourceGroupConfigurationAdmin<R extends Resource, T extends AbstractConfigNodeBased>
		extends AbstractConfigurationAdmin implements StaticResourceGroupAdmin<T> {

	private AbstractStaticResourceGroup<R> group;
	
	private ConfigNodeBasedList<T> resourcesList;

	private Class<T> elementClass;

	protected AbstractStaticResourceGroupConfigurationAdmin(AbstractStaticResourceGroup<R> group, Class<T> elementClass,
			ConfigManager configManager) {
		super(group.getPreferences(), configManager);
		this.group = group;
		this.elementClass = elementClass;
		try {
			resourcesList = new ConfigNodeBasedList<T>(getPreferences().createChildNode(
					AbstractStaticResourceGroup.PREFS_RESOURCES_NODE), "", elementClass);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T addResource() {
		return resourcesList.addElement();
	}

	@Override
	public void removeResource(T resource) {
		resourcesList.remove(resource);
	}

	@Override
	public Iterable<T> getConfiguredResources() {
		return Collections.unmodifiableList(new ArrayList<T>(resourcesList));
	}

	@Override
	public T moveUpResource(T resource) {
		int index = resourcesList.indexOf(resource);
		if (index > 0) {
			resourcesList.moveElement(index, true);
			return resourcesList.get(index - 1);
		}
		return resource;
	}

	@Override
	public T moveDownResource(T resource) {
		int index = resourcesList.indexOf(resource);
		if (index < resourcesList.size() - 1) {
			resourcesList.moveElement(index, false);
			return resourcesList.get(index + 1);
		}
		return resource;
	}

	/**
	 * Checks if two configuration objects are equal in terms of the resource they describe.
	 * 
	 * @param configuredResource1
	 *            First configuration object.
	 * @param configuredResource2
	 *            Second configuration object.
	 * 
	 * @return <code>true</code> if both Configuration objects describe the same resource, <code>false</code> otherwise.
	 */
	protected abstract boolean equals(T configuredResource1, T configuredResource2);

	@Override
	protected void validateConfig(Preferences preferences) throws ConfigException {
		ConfigNodeBasedList<T> list;
		try {
			list = new ConfigNodeBasedList<T>(getPreferences().createChildNode(AbstractStaticResourceGroup.PREFS_RESOURCES_NODE),
					"", elementClass);
		}
		catch (Exception e) {
			// should not happen cause same as in constructor, but OK...
			throw new ConfigException("Could not check resource entries", e.getCause());
		}

		// check if some resources are double
		List<T> checkeds = new ArrayList<T>();
		for (T t1 : list) {
			for (T t2 : checkeds) {
				if (equals(t1, t2)) {
					throw new ConfigException("There are at least two equal resources configured.");
				}
			}
			checkeds.add(t1);
		}

		group.validateConfiguration(preferences);
	}

}
