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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aludratest.cloud.config.MutablePreferences;
import org.aludratest.cloud.config.Preferences;

/**
 * Abstract base class for configuration objects based on Preferences nodes. Subclasses of this class are used for lists of
 * configuration elements, e.g. for the static resources managed by a <code>StaticResourceGroupAdmin</code>. <br>
 * Subclasses should perform all their operations (read and write) on the underlying Preferences node returned by
 * {@link #getConfigNode()}.
 * 
 * @author falbrech
 * 
 */
public abstract class AbstractConfigNodeBased implements Comparable<AbstractConfigNodeBased> {

	private MutablePreferences parentNode;

	private int id;

	/**
	 * Creates a new configuration object based on the configuration contained in the given child node number of the given parent
	 * node.
	 * 
	 * @param parentNode
	 *            Parent node for all configuration elements of this type.
	 * @param id
	 *            ID defining the node to use for this configuration object.
	 */
	public AbstractConfigNodeBased(MutablePreferences parentNode, int id) {
		this.parentNode = parentNode;
		this.id = id;
	}

	/**
	 * Retrieves the next free ID in the given Preferences node, that is, the lowest <i>i</i> (starting with 0) where no sub-node
	 * exists which is named <code>prefix + i</code>.
	 * 
	 * @param node
	 *            Parent Preferences node to check.
	 * @param prefix
	 *            Prefix to use for child nodes, may be the empty string.
	 * 
	 * @return The next free ID which could be used as a new child configuration node.
	 */
	public static int getNextId(Preferences node, String prefix) {
		Pattern p = Pattern.compile(prefix + "([0-9]+)");
		int maxId = 0;

		for (String nodeName : node.getChildNodeNames()) {
			Matcher m = p.matcher(nodeName);
			if (m.matches()) {
				int id = Integer.valueOf(m.group(1));
				if (id > maxId) {
					maxId = id;
				}
			}
		}

		return maxId + 1;
	}

	/**
	 * Returns the ID of this configuration object, that is, the number of the child configuration node belonging to this
	 * configuration object.
	 * 
	 * @return The ID of this configuration object.
	 */
	public final int getId() {
		return id;
	}

	@Override
	public final int compareTo(AbstractConfigNodeBased o) {
		return id - o.id;
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}

		AbstractConfigNodeBased node = (AbstractConfigNodeBased) obj;
		return node.id == id;
	}

	@Override
	public final int hashCode() {
		return id;
	}

	/**
	 * Returns the Preferences node which contains the configuration for this configuration object. All operations of this
	 * configuration object should work on this node.
	 * 
	 * @return The Preferences node which contains the configuration for this configuration object, never <code>null</code>.
	 */
	protected final MutablePreferences getConfigNode() {
		return parentNode.createChildNode(getConfigNodeName(id));
	}

	/**
	 * Returns the node name to use for the given ID. Subclasses can add a prefix to the ID, or just return a String
	 * representation of the ID value.
	 * 
	 * @param id
	 *            ID to calculate the node name for.
	 * 
	 * @return The name of the node to use for the given ID, never <code>null</code>.
	 */
	protected abstract String getConfigNodeName(int id);

}
