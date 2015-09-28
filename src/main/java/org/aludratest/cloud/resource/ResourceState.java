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
 * Enumeration of the possible states of a {@link Resource}. Technically, a Resource can switch from any state to any other state,
 * although some transitions should not occur for normal resources (e.g. from READY to CONNECTED).
 * 
 * @author falbrech
 * 
 */
public enum ResourceState {

	/**
	 * The resource is disconnected. The system is waiting for the resource to change its state to <code>CONNECTED</code> or
	 * <code>READY</code>. The resource can switch to this state at any time, when a lost connection is detected.
	 */
	DISCONNECTED,

	/**
	 * The resource is connected, but not (yet) ready to use. This could e.g. indicate some initialization still going on. From
	 * this state, it could switch to <code>DISCONNECTED</code> or <code>READY</code>.
	 */
	CONNECTED,

	/**
	 * The resource is ready to use. From this state, usually it should only switch to <code>IN_USE</code> state when usage of the
	 * resource starts.
	 */
	READY,

	/**
	 * The resource is currently in use. After use, it can switch to <code>READY</code> if it can immediately be used again, or to
	 * <code>CONNECTED</code> if a (re-)initialization is required.
	 */
	IN_USE,

	/**
	 * The resource is in an error state from which cannot recover by itself. An administrative access to the resource has to be
	 * performed. The resource can switch to this state from any other state.
	 */
	ERROR

}
