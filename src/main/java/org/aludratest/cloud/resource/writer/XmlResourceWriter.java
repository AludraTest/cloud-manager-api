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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.aludratest.cloud.resource.Resource;

/**
 * Writer interface for serializing resources to XML.
 * 
 * @author falbrech
 * 
 */
public interface XmlResourceWriter extends ResourceWriter {

	/**
	 * Writes the given resource to XML. Method should start with <code>writer.writeStartElement("resource")</code> and end with
	 * <code>writer.writeEndElement()</code> for this resource element.
	 * 
	 * @param resource
	 *            Resource to serialize to XML.
	 * @param writer
	 *            Writer to write the XML to.
	 * 
	 * @throws XMLStreamException
	 *             If an error occurs when writing to the XML Stream Writer.
	 */
	public void writeToXml(Resource resource, XMLStreamWriter writer) throws XMLStreamException;

}
