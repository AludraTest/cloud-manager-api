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
 * Interface for listeners which are informed about changes in a resource collection.
 * 
 * @author falbrech
 * 
 */
public interface ResourceCollectionListener {

	/**
	 * Notifies the listener that a resource has been added to the resource collection.
	 * 
	 * @param resource
	 *            Newly added resource.
	 */
	public void resourceAdded(Resource resource);

	/**
	 * Notifies the listener that a resource has been removed from the resource collection.
	 * 
	 * @param resource
	 *            Removed resource.
	 */
	public void resourceRemoved(Resource resource);

}
