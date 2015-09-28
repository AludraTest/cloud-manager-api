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
 * The interface for resource types. A resource type provides some type safety when dealing with resources. It can be queried for
 * a technical name. As all resources of the same type will most probably have the same resource type, it is common practice to
 * let implementing classes hide their constructor, but provide a public static instance to be returned by resources of this type.
 * 
 * @author falbrech
 * 
 */
public interface ResourceType {

	/**
	 * Returns a technical name for this resource type, e.g. "selenium". This technical name is used by AludraTest Cloud Manager
	 * for many purposes, e.g. when building internal maps or looking up web resources. <br>
	 * All instances of the same resource type class must return the same value.
	 * 
	 * @return A technical name for this resource type, never <code>null</code>.
	 */
	public String getName();

}
