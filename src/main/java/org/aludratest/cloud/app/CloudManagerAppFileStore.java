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
package org.aludratest.cloud.app;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface for an arbitrary store to load and store named files from / to. An instance of this store can be retrieved via
 * Dependency Injection. <br>
 * An implementation may or may not write to the local file system, but must ensure that file contents are persisted between two
 * invocations of the Java VM.
 *
 * @author falbrech
 *
 */
public interface CloudManagerAppFileStore {

	/**
	 * Returns the contents of a named file within this store as a byte stream.
	 *
	 * @param fileName
	 *            Name of the file. May contain forward slashes, which may or may not result in subdirectory creation (depending
	 *            on file store implementation). This should be the exact name also used in a call to
	 *            {@link #saveFile(String, InputStream)}.
	 *
	 * @return The contents of the file, as a (possibly empty) byte stream, or <code>null</code> if the file does not exist within
	 *         this store. The caller is responsible for closing this stream.
	 *
	 * @throws IOException
	 *             If any I/O related exception occurs when trying to get a stream on the file's contents.
	 */
	public InputStream openFile(String fileName) throws IOException;

	/**
	 * Checks if a file with the given name exists within this store.
	 *
	 * @param fileName
	 *            Name of the file.
	 *
	 * @return <code>true</code> if the file exists within this store, <code>false</code> otherwise.
	 *
	 */
	public boolean existsFile(String fileName);


	/**
	 * Stores a file with the given name within this store. Replaces the contents of the file if a file with same name already
	 * exists.
	 *
	 * @param fileName
	 *            Name of the file. May contain forward slashes, which may or may not result in subdirectory creation (depending
	 *            on file store implementation).
	 * @param contents
	 *            InputStream with the contents to write to the file. The caller is responsible for closing this stream after this
	 *            method returns.
	 *
	 * @throws IOException
	 *             If any I/O related exception occurs when trying to write the file or reading the contents from the input
	 *             stream.
	 */
	public void saveFile(String fileName, InputStream contents) throws IOException;

}
