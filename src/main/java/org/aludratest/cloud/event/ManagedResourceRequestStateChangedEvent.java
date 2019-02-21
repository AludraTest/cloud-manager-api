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
package org.aludratest.cloud.event;

import org.aludratest.cloud.manager.ManagedResourceRequest;

/**
 * Event for managed requests which state changed. As the state may have changed again asynchronously in parallel, you can query
 * the old and new state of the managed request using the appropriate getters of this event.
 * 
 * @author falbrech
 *
 */
public class ManagedResourceRequestStateChangedEvent extends ManagedResourceRequestEvent {

	private static final long serialVersionUID = 2580986105790184029L;

	private ManagedResourceRequest.State oldState;
	private ManagedResourceRequest.State newState;

	public ManagedResourceRequestStateChangedEvent(ManagedResourceRequest managedRequest, ManagedResourceRequest.State oldState,
			ManagedResourceRequest.State newState) {
		super(managedRequest);
		this.oldState = oldState;
		this.newState = newState;
	}

	public ManagedResourceRequest.State getOldState() {
		return oldState;
	}

	public ManagedResourceRequest.State getNewState() {
		return newState;
	}

}
