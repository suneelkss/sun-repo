package com.marta.admin.utils;

import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.apache.xerces.parsers.*;
import org.apache.xml.serialize.*;

/**
 * This class contains utility methods that would Parse the Xml String to
 * Document and fetch the root node and sub nodes from the xml doc
 * 
 * @author Dhayalan.G
 */

public class XMLParsingUtil {
	/**
	 * This method returns the value of a XML attribute. If the given attribute
	 * cannot be located, it returns null.
	 * 
	 * @param topNode
	 *            the node under which to check for the attribute
	 * @param attrName
	 *            the name of the attribute
	 * @return the value of the attribute
	 */
	public static String getXmlAttrValue(Node topNode, String attrName) {
		String attrValue = null;

		NamedNodeMap atts = topNode.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			String attName = att.getNodeName();
			if (attName.equalsIgnoreCase(attrName)) {
				attrValue = att.getNodeValue();
				return attrValue.trim();
			}
		}
		return attrValue;
	}

	/**
	 * This method returns the value of a XML node. If the given node cannot be
	 * located, it returns null.
	 * 
	 * @param topNode
	 *            the node under which to check for the subnode
	 * @param nodeName
	 *            the name of the subnode
	 * @return the value of the subnode
	 */
	public static String getXmlNodeValue(Node topNode, String nodeName) {
		String reqValue = null;

		NodeList children = topNode.getChildNodes();
		for (int iChild = 0; iChild < children.getLength(); iChild++) {
			Node reqdNode = children.item(iChild);
			if (reqdNode.getNodeName().equalsIgnoreCase(nodeName)) {
				NodeList childNodes = reqdNode.getChildNodes();
				if (childNodes.getLength() > 0) {
					// Actual value is available in this node.
					Node textNode = childNodes.item(0);
					reqValue = textNode.getNodeValue();
					return reqValue.trim();
				}
			}
		}
		return reqValue;
	}

	/**
	 * This method converts a XML String into a Document object
	 * 
	 * @param msg
	 *            the string that is to be converted into Document
	 * @return the Document object
	 * @exception IOException
	 *                if not able to create the streams
	 * @exception SAXException
	 *                if not able to parse the XML
	 */
	public static Document stringToDocument(String msg) throws SAXException,
			IOException {
		Document xmlDocument = null;
		if (msg != null) {
			DOMParser parser = new DOMParser();
			ByteArrayInputStream bais = new ByteArrayInputStream(msg.getBytes());
			InputSource is = new InputSource((InputStream) bais);
			parser.parse(is);
			xmlDocument = parser.getDocument();
		}
		return xmlDocument;
	}

	/**
	 * This method converts a Document object into a XML String
	 * 
	 * @param document
	 *            the Document that is to be converted into String
	 * @return the String object
	 * @exception IOException
	 *                if not able to create the streams
	 * @exception SAXException
	 *                if not able to parse the XML
	 */
	public static String documentToString(Document document) throws IOException {
		String xmlString = null;
		if (document != null) {
			OutputFormat format = new OutputFormat(document);
			StringWriter strOut = new StringWriter();
			XMLSerializer XMLSerial = new XMLSerializer(strOut, format);
			XMLSerial.serialize(document.getDocumentElement());
			xmlString = strOut.toString();
		}
		return xmlString;
	}

}
