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
 * Interface for collections of resources. Usually you can query a resource group for its resource collection. Resource
 * collections can change at any time, so they allow you to add listeners which are notified when resources are added or removed.
 * Also, you can iterate over all resources in the resource collection and ask for the count of resources currently present in the
 * collection, and query for the existence of a given resource in the collection.
 * 
 * @author falbrech
 * 
 * @param <R>
 *            Type of the resources contained in the resource collection.
 */
public interface ResourceCollection<R extends ResourceStateHolder> extends Iterable<R> {

	/**
	 * Adds a new listener to this resource collection. The listener will be notified when resources are added to or removed from
	 * this collection.
	 * 
	 * @param listener
	 *            Listener to add. If an equal listener is already registred, this method does nothing.
	 */
	public void addResourceCollectionListener(ResourceCollectionListener listener);

	/**
	 * Removes the given listener from this resource collection.
	 * 
	 * @param listener
	 *            Listener to remove.
	 */
	public void removeResourceCollectionListener(ResourceCollectionListener listener);

	/**
	 * Returns the current resource count of this collection.
	 * 
	 * @return The current resource count of this collection.
	 */
	public int getResourceCount();

	/**
	 * Returns <code>true</code> if this resource collection contains the given resource, and <code>false</code> otherwise.
	 * 
	 * @param resource
	 *            Resource to check presense of in this collection.
	 * 
	 * @return <code>true</code> if this resource collection contains the given resource, and <code>false</code> otherwise.
	 */
	public boolean contains(Resource resource);

}
