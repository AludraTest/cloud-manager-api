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
 * A "usable" resource is a resource which is used just by calling {@link #startUsing()} and {@link #stopUsing()}.
 * 
 * @author falbrech
 * 
 */
public interface UsableResource extends Resource {

	/**
	 * Starts using the resource. Sets the state of the resource to {@link ResourceState#IN_USE}.
	 */
	public void startUsing();

	/**
	 * Stops using the resource. Usually sets the state of the resource to {@link ResourceState#READY}, but depending on resource
	 * implementation, could also be {@link ResourceState#CONNECTED} (e.g. when an auto re-initialization is in progress).
	 */
	public void stopUsing();

}
