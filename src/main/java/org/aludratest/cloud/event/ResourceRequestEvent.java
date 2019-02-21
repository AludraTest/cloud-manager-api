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

import org.aludratest.cloud.request.ResourceRequest;
import org.springframework.context.ApplicationEvent;

/**
 * Base class for resource request related events. Listeners can be implemented using the Spring annotation
 * <code>@EventListener</code>.
 * 
 * @author falbrech
 *
 */
public abstract class ResourceRequestEvent extends ApplicationEvent {

	private static final long serialVersionUID = 8367429787469453832L;

	public ResourceRequestEvent(ResourceRequest request) {
		super(request);
	}

	public ResourceRequest getRequest() {
		return (ResourceRequest) getSource();
	}

}
