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
package org.aludratest.cloud.rest;


/**
 * Component marker interface for REST (JAX-RS) classes. Implementing classes must be annotated with JAX-RS annotations. <br>
 * Consider extending {@link AbstractRestConnector} instead of implementing this interface, to have useful JSON helper methods
 * available.
 * 
 * @author falbrech
 * 
 */
public interface RestConnector {

	/**
	 * Plexus role of this component.
	 */
	public static final String ROLE = RestConnector.class.getName();

	/**
	 * Constant for JSON content type.
	 */
	public static final String JSON_TYPE = "application/json";

	/**
	 * Constant for form data content type.
	 */
	public static final String FORM_TYPE = "application/x-www-form-urlencoded";

}
