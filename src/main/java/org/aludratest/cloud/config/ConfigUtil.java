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

import java.util.Arrays;

/**
 * Utility methods to deal with Preferences objects.
 * 
 * @author falbrech
 * 
 */
public final class ConfigUtil {

	private ConfigUtil() {
	}

	/**
	 * Checks if the two given Preferences trees differ. This is the case when:
	 * <ul>
	 * <li>The set of keys of the Preferences nodes are not equal,</li>
	 * <li>At least one value of a key differs in the nodes,</li>
	 * <li>The set of child node names of the Preferences nodes are not equal,</li>
	 * <li>or at least one of the child nodes differs in both nodes (recursion).</li>
	 * </ul>
	 * 
	 * @param node1
	 *            First Preferences node.
	 * @param node2
	 *            Second Preferences node to compare to the first one.
	 * 
	 * @return <code>true</code> if the nodes differ as described above, <code>false</code> otherwise.
	 */
	public static boolean differs(Preferences node1, Preferences node2) {
		// quick check (optimization)
		if (node1 == node2) {
			return false;
		}

		// compare child node set
		String[] myChildNodes = node1.getChildNodeNames();
		String[] otherChildNodes = node2.getChildNodeNames();
		if (myChildNodes.length != otherChildNodes.length) {
			return true;
		}
		Arrays.sort(myChildNodes);
		Arrays.sort(otherChildNodes);
		if (!Arrays.equals(myChildNodes, otherChildNodes)) {
			return true;
		}

		// same for keys
		String[] myKeyNames = node1.getKeyNames();
		String[] otherKeyNames = node2.getKeyNames();
		if (myKeyNames.length != otherKeyNames.length) {
			return true;
		}
		Arrays.sort(myKeyNames);
		Arrays.sort(otherKeyNames);
		if (!Arrays.equals(myKeyNames, otherKeyNames)) {
			return true;
		}

		// now for the values
		for (String key : myKeyNames) {
			String val1 = node1.getStringValue(key);
			String val2 = node2.getStringValue(key);
			if (val1 == null ? val2 != null : !val1.equals(val2)) {
				return true;
			}
		}

		// dive into child nodes
		for (String nodeName : node1.getChildNodeNames()) {
			Preferences leftNode = node1.getChildNode(nodeName);
			Preferences rightNode = node2.getChildNode(nodeName);
			if (rightNode == null || differs(leftNode, rightNode)) {
				return true;
			}
		}
		
		// no difference detected
		return false;
	}

	/**
	 * Clears the given mutable target Preferences node and copies all keys and child nodes (recursive) of the source Preferences
	 * node to the target. After this operation, <code>differs(source, target)</code> will return <code>false</code>.
	 * 
	 * @param source
	 *            Source Preferences to copy from.
	 * @param target
	 *            Target mutable Preferences to copy to. Existing keys and nodes in the target will be deleted before copying is
	 *            done.
	 */
	public static void copyPreferences(Preferences source, MutablePreferences target) {
		// cleanup target first
		for (String key : target.getKeyNames()) {
			target.removeKey(key);
		}
		for (String node : target.getChildNodeNames()) {
			target.removeChildNode(node);
		}

		for (String key : source.getKeyNames()) {
			target.setValue(key, source.getStringValue(key));
		}
		for (String node : source.getChildNodeNames()) {
			// recursion
			copyPreferences(source.getChildNode(node), target.createChildNode(node));
		}
	}

}
