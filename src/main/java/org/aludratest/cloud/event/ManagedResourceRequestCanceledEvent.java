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
 * Event that signals that the given request has been canceled by external action (e.g. user requested a cancellation).
 * 
 * @author falbrech
 *
 */
public class ManagedResourceRequestCanceledEvent extends ManagedResourceRequestEvent {

	private static final long serialVersionUID = 5417042156328032597L;

	public ManagedResourceRequestCanceledEvent(ManagedResourceRequest managedRequest) {
		super(managedRequest);
	}

}
