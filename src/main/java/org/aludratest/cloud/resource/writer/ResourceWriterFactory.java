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
package org.aludratest.cloud.resource.writer;

/**
 * Interface for factories for resource writers. Each factory implementation can be asked for a resource writer of a given type.
 * The factory may or may not provide a matching writer implementation.
 * 
 * @author falbrech
 * 
 */
public interface ResourceWriterFactory {

	/**
	 * Plexus role of this component.
	 */
	public static final String ROLE = ResourceWriterFactory.class.getName();

	/**
	 * Returns a resource writer implementation for the given writer interface able to serialize resources to which this factory
	 * belongs to, or <code>null</code> if no implementation for the given writer interface is available.
	 * 
	 * @param resourceWriterClass
	 *            Writer interface to return an implementation for, e.g. <code>XmlResourceWriter.class</code> or
	 *            <code>JSONResourceWriter.class</code>.
	 * 
	 * @return An implementation for the given writer interface, or <code>null</code> if no such implementation is available in
	 *         this factory.
	 */
	public <T extends ResourceWriter> T getResourceWriter(Class<T> resourceWriterClass);

}
