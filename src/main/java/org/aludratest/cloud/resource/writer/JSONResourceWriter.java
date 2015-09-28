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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Writer interface for serializing resources to JSON.
 * 
 * @author falbrech
 * 
 */
public interface JSONResourceWriter extends ResourceWriter {

	/**
	 * Creates a JSON object representing the given resource.
	 * 
	 * @param resource
	 *            Resource to create a JSON object for.
	 * 
	 * @return A JSON object representing the given resource.
	 * @throws JSONException
	 *             If the JSON object could not be created for some reason.
	 */
	public JSONObject writeToJSON(Resource resource) throws JSONException;

}
