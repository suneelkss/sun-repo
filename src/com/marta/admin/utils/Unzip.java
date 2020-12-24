package com.marta.admin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {

	private static final String FILESEPARATOR = File.separator;
	
	private int noOfFiles = 0;

	public  String storeZipStream(InputStream inputStream, String dir)
			throws IOException {

		ZipInputStream zis = new ZipInputStream(inputStream);
		ZipEntry entry = null;
		//int countEntry = 0;
		if (!dir.endsWith(FILESEPARATOR))
			dir += FILESEPARATOR;
		String fileName = "";
		// check inputStream is ZIP or not
		if ((entry = zis.getNextEntry()) != null) {
			do {
				String entryName = entry.getName();
				// Directory Entry should end with FileSeparator
				if (!entry.isDirectory()) {
					// Directory will be created while creating file with in it.
					fileName = dir + entryName;
					createFile(zis, fileName);
					noOfFiles++;
				}
			} while ((entry = zis.getNextEntry()) != null);
			System.out.println("No of files Extracted : " + noOfFiles);

		} else {
			throw new IOException("Given file is not a Compressed one");
		}
		return fileName;
	}

	public  void createFile(InputStream is, String absoluteFileName)
			throws IOException {
		File f = new File(absoluteFileName);

		if (!f.getParentFile().exists())
			f.getParentFile().mkdirs();
		OutputStream out = new FileOutputStream(absoluteFileName);
		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = is.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		// Close the streams
		out.close();
	}

	/*
	 * public static void main(String args[]) throws Exception {
	 * 
	 * 
	 * FileInputStream zis = new FileInputStream(new File("c:\\pwd.zip"));
	 * 
	 * storeZipStream(zis, "c:\\pwd"); }
	 */
	public  String processZip(String fileUrl)
			throws IOException {
		FileInputStream zis = new FileInputStream(new File(fileUrl));
		String dest = fileUrl.substring(0,fileUrl.lastIndexOf("."));
		noOfFiles = 0;
		String fileName = storeZipStream(zis, dest);
		String newFileURL = "";
		if(noOfFiles == 1){
			newFileURL = fileName;
		}
		return newFileURL;
	}

}
