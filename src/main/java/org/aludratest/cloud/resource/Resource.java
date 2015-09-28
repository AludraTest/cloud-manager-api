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
package org.aludratest.cloud.resource;

/**
 * The basic type of AludraTest Cloud Manager. A resource is some arbitrary object having
 * <ul>
 * <li>a <b>state</b>,</li>
 * <li>a <b>type</b>, and</li>
 * <li><b>state changes</b> from one state to another</li>.
 * </ul>
 * Listeners can be registered to keep track of resource state changes. <br>
 * Clients who will finally receive a Resource object when querying for one will probably have to cast it to a more concrete type
 * to be able to work with the resource. <br>
 * It is important that implementors of this interface also implement clean <code>equals()</code> and <code>hashCode()</code>
 * methods, as several management algorithms depend on these.
 * 
 * @author falbrech
 * 
 */
public interface Resource extends ResourceStateHolder {

	/**
	 * Returns the type of this resource. The type can e.g. be queried for a technical name.
	 * 
	 * @return The type of this resource, never <code>null</code>. Several resources of the same type can (and will) return the
	 *         <b>same</b> object instance here.
	 */
	public ResourceType getResourceType();

	/**
	 * Adds a listener which will be notified about state changes of this resource. If an equal listener is already registered,
	 * calling this method has no effect.
	 * 
	 * @param listener
	 *            Listener to register with this resource.
	 */
	public void addResourceListener(ResourceListener listener);

	/**
	 * Removes the given listener from the list of registered listeners.
	 * 
	 * @param listener
	 *            Listener to remove.
	 */
	public void removeResourceListener(ResourceListener listener);

}
