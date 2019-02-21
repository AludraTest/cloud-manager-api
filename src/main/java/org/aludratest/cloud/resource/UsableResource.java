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
 * A "usable" resource is a resource which is used just by calling
 * {@link #startUsing()} and {@link #stopUsing()}. Also, it allows for
 * registering listeners which are notified when the resource is detected as
 * "orphaned", i.e. altough {@link #stopUsing()} has not been called, it has not
 * been used for a (usually resource-type specific configured) time. A listener
 * should call {@link #stopUsing()} to force setting the resource back to e.g.
 * state <code>READY</code> and trigger all other required actions (e.g. mark a
 * pending request as orphaned, find a new request for this resource etc.) <br>
 * Note that the "orphaned detection" implementation is optional; if a resource
 * type does not offer this functionality, the listeners need not to be
 * notified.
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

	/**
	 * Invokes the given listener as soon as this resource is detected to be
	 * "orphaned", i.e., has not been used for a specific amount of time, although
	 * being "in use", according to its state. Note that this is an optional
	 * feature; a listener may never be called for some types of resources. <br>
	 * This method has no effect when an identical listener is already registered.
	 *
	 * @param orphanedListener
	 *            Listener to notify when this resource is detected to be
	 *            "orphaned".
	 */
	public void addOrphanedListener(OrphanedListener orphanedListener);

	/**
	 * Removes the given listener from the list of listeners being notified when
	 * this resource is "orphaned".
	 *
	 * @param orphanedListener
	 *            Listener to remove from the list of listeners.
	 *
	 * @see #addOrphanedListener(Runnable)
	 */
	public void removeOrphanedListener(OrphanedListener orphanedListener);

}
