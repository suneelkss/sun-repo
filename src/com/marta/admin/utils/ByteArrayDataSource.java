package com.marta.admin.utils;

import java.io.*;
import javax.activation.*;

public class ByteArrayDataSource implements DataSource {
	private byte[] data; // data

	private String type; // content-type

	/**
	 * Method to create a datasource from an input.
	 * 
	 * @param is
	 * @param type
	 */
	public ByteArrayDataSource(InputStream is, String type) {
		this.type = type;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int ch;

			while ((ch = is.read()) != -1)
				// XXX - must be made more efficient by
				// doing buffered reads, rather than one byte reads
				os.write(ch);
			data = os.toByteArray();

		} catch (IOException ioex) {
		}
	}
	
	/**
	 * Method to create a byte array from a byte array.
	 * 
	 * @param data
	 * @param type
	 */
	public ByteArrayDataSource(byte[] data, String type) {
		this.data = data;
		this.type = type;
	}
	
	/**
	 * Method to create a datasource from a String.
	 * 
	 * @param data
	 * @param type
	 */
	public ByteArrayDataSource(String data, String type) {
		try {
			// Assumption that the string contains only ASCII
			// characters! Otherwise just pass a charset into this
			// constructor and use it in getBytes()
			this.data = data.getBytes("iso-8859-1");
		} catch (UnsupportedEncodingException uex) {
		}
		this.type = type;
	}

	/**
	 * Return an InputStream for the data. Note - a new stream must be returned
	 * each time.
	 */
	public InputStream getInputStream() throws IOException {
		if (data == null)
			throw new IOException("no data");
		return new ByteArrayInputStream(data);
	}

	/**
	 * Method to get output stream.
	 */
	public OutputStream getOutputStream() throws IOException {
		throw new IOException("cannot do this");
	}

	/**
	 * Method to get content type.
	 */
	public String getContentType() {
		return type;
	}

	/**
	 * Method to get name.
	 */
	public String getName() {
		return "dummy";
	}
}