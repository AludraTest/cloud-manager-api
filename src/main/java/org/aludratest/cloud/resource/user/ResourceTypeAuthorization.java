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
package org.aludratest.cloud.resource.user;


/**
 * Authorization for access to a single resource type. Implementations of this interface are thought to be immutable.
 * 
 * @author falbrech
 * 
 */
public interface ResourceTypeAuthorization {

	/**
	 * Returns the maximum number of resources of the associated type the user may have in use at any given point in time.
	 * 
	 * @return The maximum number of resources of the associated type the user may have in use at any given point in time.
	 */
	public int getMaxResources();

	/**
	 * Returns the "nice level" for this user regarding the associated resource type. Valid values range from -19 to +20. Users
	 * with a lower nice level will more likely receive a resource which becomes available.
	 * 
	 * @return The nice level for this user regarding the associated resource type.
	 */
	public int getNiceLevel();

}
