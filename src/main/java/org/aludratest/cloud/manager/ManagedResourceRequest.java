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
package org.aludratest.cloud.manager;

import java.time.ZonedDateTime;
import java.util.concurrent.Future;

import org.aludratest.cloud.request.ResourceRequest;
import org.aludratest.cloud.resource.Resource;

public interface ManagedResourceRequest {

	public static enum State {
		WAITING, READY, WORKING, FINISHED, ORPHANED
	}

	public State getState();

	public ResourceRequest getRequest();

	public ZonedDateTime getCreationTimestamp();

	/**
	 * Returns how long this request has not been "touched", e.g. re-queried by a
	 * client.
	 *
	 * @return The time how long this request has not been "touched", in
	 *         milliseconds.
	 */
	public long getIdleTimeMs();

	/**
	 * Returns how long this request had to wait until it received a resource. If it
	 * has not received a resource yet, this returns the time difference from
	 * creation until now.
	 *
	 * @return The wait time of this request, in milliseconds.
	 */
	public long getWaitTimeMs();

	public void markOrphaned();

	public Future<Resource> getResourceFuture();

}
