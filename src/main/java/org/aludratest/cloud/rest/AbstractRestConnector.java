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

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for REST connectors. Provides some utility classes for a uniform handling of REST requests.
 * 
 * @author falbrech
 * 
 */
public abstract class AbstractRestConnector implements RestConnector {
	
	private Logger logger;

	/**
	 * Returns the logger to use to log REST connector specific information.
	 * 
	 * @return The logger to use to log REST connector specific information.
	 */
	protected final Logger getLogger() {
		if (logger == null) {
			logger = LoggerFactory.getLogger(getClass());
		}
		return logger;
	}

	/**
	 * Wraps the given JSON object in a result object and builds a Response object. The response will contain a JSON object which
	 * contains a field with name <code>result</code> and the passed JSON object as value.
	 * 
	 * @param result
	 *            JSON object to wrap in a result object.
	 * 
	 * @return A Response object with status <code>OK</code> and the wrapping object as content.
	 */
	protected final Response wrapResultObject(JSONObject result) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("result", result);
			return Response.ok().entity(obj.toString()).build();
		}
		catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Wraps the given JSON object in a result object and builds a Response object with the given HTTP status code. The response
	 * will contain a JSON object which contains a field with name <code>result</code> and the passed JSON object as value.
	 * 
	 * @param result
	 *            JSON object to wrap in a result object.
	 * @param responseCode
	 *            HTTP status code to use as status of the Response.
	 * 
	 * @return A Response object with the given HTTP status code and the wrapping object as content.
	 */
	protected final Response wrapResultObject(JSONObject result, int responseCode) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("result", result);
			return Response.status(responseCode).entity(obj.toString()).build();
		}
		catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a Response carrying a standard JSON error object describing the given Throwable. The response will carry the status
	 * code <code>SC_BAD_REQUEST</code> (400).
	 * 
	 * @param cause
	 *            Throwable to wrap in a JSON error object.
	 * 
	 * @return Response object carrying the JSON error object and with HTTP status code <code>SC_BAD_REQUEST</code> (400).
	 */
	protected final Response createErrorObject(Throwable cause) {
		return createErrorObject(cause.getMessage(), cause, HttpServletResponse.SC_BAD_REQUEST);
	}

	/**
	 * Creates a Response carrying a standard JSON error object describing the given Throwable. The response will carry the given
	 * status code.
	 * 
	 * @param cause
	 *            Throwable to wrap in a JSON error object.
	 * @param responseCode
	 *            HTTP status code to use for the Response object.
	 * 
	 * @return Response object carrying the JSON error object and with given HTTP status code.
	 */
	protected final Response createErrorObject(Throwable cause, int responseCode) {
		return createErrorObject(cause.getMessage(), cause, responseCode);
	}

	/**
	 * Creates a Response carrying a standard JSON error object describing the given Throwable and providing the given error
	 * message. The response will carry the status code <code>SC_BAD_REQUEST</code> (400).
	 * 
	 * @param message
	 *            Error message to set in the JSON error object.
	 * @param cause
	 *            Throwable to wrap in a JSON error object.
	 * 
	 * @return Response object carrying the JSON error object and with HTTP status code <code>SC_BAD_REQUEST</code> (400).
	 */
	protected final Response createErrorObject(String message, Throwable cause) {
		return createErrorObject(message, cause, HttpServletResponse.SC_BAD_REQUEST);
	}

	/**
	 * Creates a Response carrying a standard JSON error object describing the given Throwable and providing the given error
	 * message. The response will carry the given HTTP status code.
	 * 
	 * @param message
	 *            Error message to set in the JSON error object.
	 * @param cause
	 *            Throwable to wrap in a JSON error object.
	 * @param responseCode
	 *            HTTP status code to use for the Response object.
	 * 
	 * @return Response object carrying the JSON error object and with given HTTP status code.
	 */
	protected final Response createErrorObject(String message, Throwable cause, int responseCode) {
		JSONObject obj = new JSONObject();
		try {
			JSONObject error = new JSONObject();
			error.put("message", message);
			if (cause != null) {
				// TODO could be more verbose
				error.put("exceptionClass", cause.getClass().getName());
			}
			obj.put("error", error);
			return Response.status(responseCode).entity(obj.toString()).build();
		}
		catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

}
