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
package org.aludratest.cloud.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Useful methods for dealing with Java Server Faces.
 * 
 * @author falbrech
 * 
 */
public class JSFUtil {

	private JSFUtil() {
	}

	/**
	 * Finds the first <code>UIParameter</code> child of the given UIComponent which has a parameter name <i>paramName</i>.
	 * Returns the value of this child.
	 * 
	 * @param component
	 *            UIComponent to search for UIParameter children.
	 * @param paramName
	 *            Name of parameter to search for.
	 * 
	 * @return Value of found parameter, or <code>null</code> if not found.
	 */
	public static Object getParamValue(UIComponent component, String paramName) {
		for (UIComponent child : component.getChildren()) {
			if (child instanceof UIParameter) {
				UIParameter param = (UIParameter) child;
				if (paramName.equals(param.getName())) {
					return param.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Evaluates the given EL expression within the given Faces Context.
	 * 
	 * @param context
	 *            Faces Context to evaluate EL expression in.
	 * @param expression
	 *            EL expression to evaluate.
	 * 
	 * @param expectedType
	 *            Expected return type.
	 * 
	 * @return The result of the EL expression evaluation.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getExpressionValue(FacesContext context, String expression, Class<T> expectedType) {
		return (T) context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(), expression, expectedType).getValue(context.getELContext());
	}

	/**
	 * Finds the URL of a "module-specific resource". This searches for a resource named
	 * <code>/META-INF/resources/acm/<i>moduleName</i>/<i>resourceName</i></code> in
	 * <ol>
	 * <li>the External Context of the Faces Context,</li>
	 * <li>the current Thread's context Class loader, or, if there is no context Class loader,</li>
	 * <li>the Class loader which loaded this class.</li>
	 * </ol>
	 * 
	 * @param context
	 *            Faces Context to use.
	 * @param moduleName
	 *            Module name, e.g. "selenium"
	 * @param resourceName
	 *            Resource name, e.g. "configSection.xhtml".
	 * 
	 * @return The URL to the module-specific resource, or <code>null</code> if not found.
	 */
	public static URL findModuleSpecificResource(FacesContext context, String moduleName, String resourceName) {
		ExternalContext extContext = context.getExternalContext();

		// check META-INF/resources/acm/<moduleName> first. As specified in Servlet spec, ServletContext
		// and thus ExternalContext will prepend META-INF/resources automatically
		String resourceFileName = "/acm/" + moduleName + "/" + resourceName;

		URL url;
		try {
			url = extContext.getResource(resourceFileName);
		}
		catch (MalformedURLException e) {
			return null;
		}
		if (url == null) {
			// now try ClassLoader
			resourceFileName = "/META-INF/resources" + resourceFileName;
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			if (cl == null) {
				cl = JSFUtil.class.getClassLoader();
			}
			return cl.getResource(resourceFileName);
		}
		return url;
	}

	/**
	 * Creates a Faces Message with Severity ERROR and using the given message as summary and detail.
	 * 
	 * @param message
	 *            Error Message.
	 * 
	 * @return Faces Message with Severity ERROR and using the given message as summary and detail.
	 */
	public static FacesMessage createErrorMessage(String message) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
	}

	/**
	 * Creates a Faces Message with Severity INFO and using the given message as summary and detail.
	 * 
	 * @param message
	 *            Info Message.
	 * 
	 * @return Faces Message with Severity INFO and using the given message as summary and detail.
	 */
	public static FacesMessage createInfoMessage(String message) {
		return new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
	}

	/**
	 * Returns the Client ID of the "current component" of the current Faces Context. The current component is the component which
	 * is returned by the EL Expression <code>#{component}</code>, just now evaluated in the Faces Context returned by
	 * <code>FacesContext.getCurrentInstance()</code>. This is useful for assigning error messages to a specific component from
	 * Java code.
	 * 
	 * @return The Client ID of the "current component" of the current Faces Context, or <code>null</code> if there is no
	 *         "current component".
	 */
	public static String getCurrentComponentClientId() {
		UIComponent c = getExpressionValue(FacesContext.getCurrentInstance(), "#{component}", UIComponent.class);
		return c == null ? null : c.getClientId();
	}

}
