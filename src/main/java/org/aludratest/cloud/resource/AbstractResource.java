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

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for resources. The listener infrastructure is implemented here, allowing subclasses to call
 * {@link #fireResourceStateChanged(ResourceState, ResourceState)} to notify all listeners about state changes.
 * 
 * @author falbrech
 */
public abstract class AbstractResource implements Resource {

	private List<ResourceListener> listeners = new ArrayList<ResourceListener>();

	@Override
	public final void addResourceListener(ResourceListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public final void removeResourceListener(ResourceListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notifies all listeners about a state change of this resource. No check is performed that oldState and newState are not
	 * equal; if they are, the listeners are still notified.
	 * 
	 * @param oldState
	 *            Previous state of this resource.
	 * @param newState
	 *            New state of this resource.
	 */
	protected final void fireResourceStateChanged(ResourceState oldState, ResourceState newState) {
		// copy listeners list to allow listeners deregistering themselves during event without ConcurrentModificationException
		List<ResourceListener> listeners = new ArrayList<ResourceListener>(this.listeners);

		for (ResourceListener listener : listeners) {
			listener.resourceStateChanged(this, oldState, newState);
		}
	}

}
