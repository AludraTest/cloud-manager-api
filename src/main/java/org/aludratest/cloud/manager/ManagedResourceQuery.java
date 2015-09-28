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

import org.aludratest.cloud.request.ResourceRequest;
import org.aludratest.cloud.resource.Resource;
import org.joda.time.DateTime;

/**
 * A resource request which is managed within a ResourceManager. The resource manager enriches the request with timestamp
 * information and the received resource, as soon as one has been assigned.
 * 
 * @author falbrech
 * 
 */
public interface ManagedResourceQuery {

	/**
	 * Returns the request which is managed by this query.
	 * 
	 * @return The request which is managed by this query.
	 */
	public ResourceRequest getRequest();

	/**
	 * Returns the resource which has been reserved for use by this query, if any. May be <code>null</code> if the query is still
	 * waiting to receive a resource.
	 * 
	 * @return The resource which has been reserved for use by this query, or <code>null</code>.
	 */
	public Resource getReceivedResource();

	/**
	 * Returns the date and time when this query was created, i.e. the request has been enqueued in the Resource Manager. This is
	 * never <code>null</code>.
	 * 
	 * @return The date and time when this query was created.
	 */
	public DateTime getEnqueueStartTime();

	/**
	 * Returns the date and time when this query received a resource. This is <code>null</code> as long as no resource has been
	 * reserved for this query.
	 * 
	 * @return The date and time when this query received a resource, or <code>null</code>.
	 */
	public DateTime getResourceReceivedTime();

	/**
	 * Returns the date and time when this query released the received resource. This is <code>null</code> as long as the query is
	 * waiting for a resource, or is working with the resource.
	 * 
	 * @return The date and time when this query released the received resource, or <code>null</code>.
	 */
	public DateTime getResourceReleasedTime();

}
