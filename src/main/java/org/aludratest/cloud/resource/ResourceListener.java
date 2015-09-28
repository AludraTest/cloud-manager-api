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
 * Interface for listeners which want to be notified about state changes of resources.
 * 
 * @author falbrech
 * 
 */
public interface ResourceListener {

	/**
	 * Notifies the listener that the state of the given resource has changed.
	 * 
	 * @param resource
	 *            Resource whose state has changed.
	 * @param previousState
	 *            Previous state of the resource.
	 * @param newState
	 *            New, current state of the resource.
	 */
	public void resourceStateChanged(Resource resource, ResourceState previousState, ResourceState newState);

}
