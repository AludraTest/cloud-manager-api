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
 * Resource request event signaling that a resource request has been received by the resource manager and is considered to be
 * valid (e.g. sufficient privileges for requested resource). This event can be queried for the {@link ManagedResourceRequest}
 * created by the manager.
 * 
 * @author falbrech
 *
 */
public class ResourceRequestReceivedEvent extends ManagedResourceRequestEvent {

	private static final long serialVersionUID = -4152108907886355099L;

	public ResourceRequestReceivedEvent(ManagedResourceRequest managedRequest) {
		super(managedRequest);
	}

}
