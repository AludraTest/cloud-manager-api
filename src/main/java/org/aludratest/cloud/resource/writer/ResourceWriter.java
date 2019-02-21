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

import org.aludratest.cloud.resource.Resource;

/**
 * Base interface for classes being able to somehow "serialize" the information about a resource.
 * 
 * @author falbrech
 * 
 */
public interface ResourceWriter {

	/**
	 * Indicates if this writer can serialize the given resource. Usually, this checks the type of the resource to be one of the
	 * supported types for this writer.
	 * 
	 * @param resource
	 *            Resource to check.
	 * @return <code>true</code> if this writer is able to serialize the given resource, <code>false</code> otherwise.
	 */
	public boolean canWrite(Resource resource);

}
