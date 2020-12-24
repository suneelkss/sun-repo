package com.marta.admin.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FileUtil {

	public static List getFileContentAsLines(String fileUrl) throws Exception {

		if (fileUrl == null) {
			return null;
		}

		int lastIndexOfDot = fileUrl.lastIndexOf(".");
		String fileExtn = fileUrl.substring(lastIndexOfDot);

		if (fileExtn.equalsIgnoreCase(Constants.XML_FILE_EXTN)) {
			return getXMLFileContentAsLines(fileUrl);
		}

		List fileContentsAsLines = new ArrayList();

		BufferedReader is = new BufferedReader(new FileReader(fileUrl));
		String currentLineData = "";
		while((currentLineData = is.readLine()) != null)
		{
			if(!currentLineData.equals("")){
				fileContentsAsLines.add(currentLineData);
			}
		}
		return fileContentsAsLines;
	}

	public static void writeToFile(String fileContent, String filePath) throws Exception {
		BufferedWriter bufferedFileWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filePath), ENCODING));
		bufferedFileWriter.write(fileContent);
		bufferedFileWriter.close();
	}
	
	public static void writeToFileWithoutEncoding(String fileContent, String filePath) throws Exception {
		BufferedWriter bufferedFileWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filePath)));
		bufferedFileWriter.write(fileContent);
		bufferedFileWriter.close();
	}
	
	/**
	 * This method writes the contents received on the inputstream 
	 * to the file specified
	 * 
	 * @param inputStream The input stream
	 * @param filePath The file path / url
	 * @throws Exception
	 */
	public static void writeToFile(InputStream inputStream, String filePath) throws Exception {
		File file = new File(filePath);
		OutputStream out = new FileOutputStream(file);
	    byte buf[] = new byte[1024];
	    int len;
	    while((len=inputStream.read(buf))>0) {
		    out.write(buf,0,len);
	    }
	    out.close();
	    inputStream.close();
	}

	public static List getXMLFileContentAsLines(String xmlfile) throws Exception {
		List valueList = new ArrayList();
		try {

			File file = new File(xmlfile);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			String rowValues = "";
			NodeList rowNode = doc.getElementsByTagName(Constants.XML_ROW_NODE);
			for (int r = 0; r < rowNode.getLength(); r++) {
				rowValues = "";
				Node firstNode = rowNode.item(r);
				Element firstElemnt = (Element) firstNode;
				NodeList colNode = firstElemnt.getElementsByTagName(Constants.XML_DATA_NODE);
				for (int c = 0; c < colNode.getLength(); c++) {
					Element element = (Element) colNode.item(c);
					NodeList childList = element.getChildNodes();
					rowValues += childList.item(0).getNodeValue().trim() + Constants.COMMA_DELIMITER;
				}

				rowValues = rowValues.substring(0,rowValues.length()-1);
				valueList.add(rowValues);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return valueList;
	}

	public static long getFileSize(String filename) {

	    File file = new File(filename);

	    if (!file.exists() || !file.isFile()) {
	        return -1;
	    }

	    //Here we get the actual size
	    return file.length();
	  }

	private static final String ENCODING = "ISO8859_1";
}
