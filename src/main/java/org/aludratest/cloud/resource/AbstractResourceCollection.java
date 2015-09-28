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
 * Abstract base implementation for resource collections. Holds the collection of listeners and offers protected
 * <code>fire*</code> methods to fire events.
 * 
 * @author falbrech
 * 
 * @param <R>
 *            Type of the resources held by the resource collection.
 */
public abstract class AbstractResourceCollection<R extends ResourceStateHolder> implements ResourceCollection<R> {

	private List<ResourceCollectionListener> listeners = new ArrayList<ResourceCollectionListener>();

	@Override
	public synchronized void addResourceCollectionListener(ResourceCollectionListener listener) {
		listeners.add(listener);
	}

	@Override
	public synchronized void removeResourceCollectionListener(ResourceCollectionListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notifies all listeners that the given resource has been added to the resource collection.
	 * 
	 * @param resource
	 *            Resource which has been added to the resource collection.
	 */
	protected final void fireResourceAdded(Resource resource) {
		List<ResourceCollectionListener> ls;
		synchronized (this) {
			ls = new ArrayList<ResourceCollectionListener>(listeners);
		}
		for (ResourceCollectionListener listener : ls) {
			listener.resourceAdded(resource);
		}
	}

	/**
	 * Notifies all listeners that the given resource has been removed from the resource collection.
	 * 
	 * @param resource
	 *            Resource which has been removed from the resource collection.
	 */
	protected final void fireResourceRemoved(Resource resource) {
		List<ResourceCollectionListener> ls;
		synchronized (this) {
			ls = new ArrayList<ResourceCollectionListener>(listeners);
		}
		for (ResourceCollectionListener listener : ls) {
			listener.resourceRemoved(resource);
		}
	}

}
