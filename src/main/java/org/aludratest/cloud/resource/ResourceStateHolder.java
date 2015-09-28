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
 * Interface for objects (resources or wrapper types) providing the state of a resource. Only used for some wrapper techniques;
 * you should never implement this interface directly.
 * 
 * @author falbrech
 * 
 */
public interface ResourceStateHolder {

	/**
	 * Returns the state of the resource.
	 * 
	 * @return The state of the resource, never <code>null</code>.
	 */
	public ResourceState getState();

}
