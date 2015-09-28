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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aludratest.cloud.config.ConfigUtil;
import org.aludratest.cloud.config.MutablePreferences;
import org.aludratest.cloud.config.Preferences;
import org.aludratest.cloud.config.SimplePreferences;

/**
 * Managing class for the child nodes of a Preferences node which form a list of configuration elements, e.g. the Selenium host
 * list or Selenium Resource Groups. It cares about instantaneous update of the underlying node structure when adds and removes
 * are performed.
 * 
 * @author falbrech
 * 
 * @param <T>
 *            Type of the class caring about single elements of the list. This class must extends AbstractConfigNodeBased.
 */
public class ConfigNodeBasedList<T extends AbstractConfigNodeBased> extends AbstractList<T> {

	private Class<T> configClass;

	private MutablePreferences preferences;

	private String prefix;

	private int maxId = -1;

	/**
	 * Creates a new managing class for a list of Configuration objects derived from given Preferences.
	 * 
	 * @param preferences
	 *            Preferences to work on.
	 * @param prefix
	 *            Prefix to use for child node names.
	 * @param configClass
	 *            Class of the configuration objects to use for managing single nodes.
	 * 
	 * @throws SecurityException
	 *             When Constructor for configuration object class could not be accesssed.
	 * @throws NoSuchMethodException
	 *             When Constructor <code>(MutablePreferences, int)</code> is not found in configuration object class.
	 * @throws InstantiationException
	 *             When configuration objects could not be instantiated.
	 * @throws IllegalAccessException
	 *             When constructor may not be accessed.
	 * @throws InvocationTargetException
	 *             When constructor of configuration objects threw an exception.
	 */
	public ConfigNodeBasedList(MutablePreferences preferences, String prefix, Class<T> configClass) throws SecurityException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		this.preferences = preferences;
		this.prefix = prefix;
		this.configClass = configClass;

		// to assert constructor existence etc.
		createObject(0);

		tidyUp(0);
	}

	private T createObject(int id) throws SecurityException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		Constructor<T> cstr = configClass.getConstructor(MutablePreferences.class, int.class);
		return cstr.newInstance(preferences, id);
	}

	private void tidyUp(int startIndex) {
		maxId = -1;
		// build a list as they are now in config
		try {
			List<T> elements = buildFromConfig(preferences, prefix, configClass);

			for (int i = startIndex; i < elements.size(); i++) {
				if (elements.get(i).getId() > i) {
					T t = createObject(i);
					Preferences source = elements.get(i).getConfigNode();
					MutablePreferences target = t.getConfigNode();
					ConfigUtil.copyPreferences(source, target);
					// delete source
					preferences.removeChildNode(elements.get(i).getConfigNodeName(elements.get(i).getId()));
				}
			}
			maxId = elements.size() - 1;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T extends AbstractConfigNodeBased> List<T> buildFromConfig(MutablePreferences parentNode, String prefix,
			Class<T> configClass) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		// get constructor
		Constructor<T> cstr = configClass.getConstructor(MutablePreferences.class, int.class);

		Pattern p = Pattern.compile(prefix + "([0-9]+)");

		List<T> result = new ArrayList<T>();

		for (String nodeName : parentNode.getChildNodeNames()) {
			Matcher m = p.matcher(nodeName);
			if (m.matches()) {
				T obj = cstr.newInstance(parentNode, Integer.valueOf(m.group(1)));
				result.add(obj);
			}
		}

		Collections.sort(result);
		return result;
	}

	@Override
	public T get(int index) {
		try {
			return createObject(index);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int size() {
		return maxId + 1;
	}

	/**
	 * Adds a new configuration object to the list of configuration objects. Immediately creates the appropriate Preferences child
	 * node.
	 * 
	 * @return The new configuration object, which can be used to modify the child Preferences node.
	 */
	public T addElement() {
		preferences.createChildNode(prefix + (++maxId));
		try {
			return createObject(maxId);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Moves the configuration object with the given index in the list of configuration objects. This is implemented by swapping
	 * contents of two Preferences child nodes.
	 * 
	 * @param index
	 *            Index of configuration object to move.
	 * @param up
	 *            If <code>true</code>, move configuration up in list, otherwise, move it down.
	 */
	public void moveElement(int index, boolean up) {
		// copy-paste all config
		int targetIndex = up ? (index - 1) : (index + 1);
		if (targetIndex < 0 || targetIndex > preferences.getChildNodeNames().length - 1) {
			return;
		}

		MutablePreferences source = preferences.createChildNode(prefix + index);
		MutablePreferences target = preferences.createChildNode(prefix + targetIndex);
		SimplePreferences buf = new SimplePreferences(null);
		ConfigUtil.copyPreferences(source, buf);
		ConfigUtil.copyPreferences(target, source);
		ConfigUtil.copyPreferences(buf, target);
	}

	@Override
	public T remove(int index) {
		try {
			preferences.removeChildNode(createObject(index).getConfigNodeName(index));
			tidyUp(index);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		// never ever work with that reference, so return null, although against spec
		return null;
	}

	@Override
	public void clear() {
		for (int i = 0; i <= maxId; i++) {
			preferences.removeChildNode(prefix + i);
		}
		maxId = -1;
	}

}
