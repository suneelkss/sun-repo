package com.marta.admin.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Validator {
	
	
	
	
	
	private String getFileURLWithSuffix(String fileURL, int appendType) {
		String newFileURL = "";
		String suffixString = "";
		
		if (appendType == includedSuffixIndex) {
			suffixString = includeSuffixString;
		} else if (appendType == excludedSuffixIndex) {
			suffixString = excludeSuffixString;
		} else if (appendType == reportSuffixIndex) {
			suffixString = reportSuffixString;
		}
		
		int lastIndexOfDot = fileURL.lastIndexOf(".");
		String fileExtn = fileURL.substring(lastIndexOfDot);
		String filePath = fileURL.substring(0, lastIndexOfDot);
		newFileURL = filePath + suffixString + fileExtn;
		return newFileURL;
	}
	
	private List getMandatoryFieldIndexes(
			String[] mandatoryFields,
			String[] headerData
	) {
		List mandatoryFieldIndexes = new ArrayList();
		String mandatoryValue;
		String headerValue;
		
		for (int mandatoryIndex = 0; mandatoryIndex < mandatoryFields.length; mandatoryIndex++) {
			mandatoryValue = mandatoryFields[mandatoryIndex];
			if (mandatoryValue == null) {
				continue;
			}
			for (int headerIndex = 0; headerIndex < headerData.length; headerIndex++) {
				headerValue = headerData[headerIndex];
				if (headerValue == null) {
					continue;
				}
				
				if (headerValue.equalsIgnoreCase(mandatoryValue)) {
					mandatoryFieldIndexes.add(new Integer(headerIndex));
					break;
				}
			}
		}
		
		return mandatoryFieldIndexes;
	}
	
	private String getSeperator(String headerLine) {
		
		if (headerLine.indexOf(",") > (-1)) {
			return ",";
		} else if (headerLine.indexOf("\t") > (-1)) {
			return "\t";
		} else if (headerLine.indexOf("|") > (-1)) {
			return "\\|";
		}
		
		return null;
	}
	
	private String trimSeperator(String seperator) {
		if (seperator != null) {
			if (seperator.equals("\\|")) {
				return "|";
			} else {
				return seperator;
			}
		}
		
		return null;
	}
	
	private static final String includeSuffixString = "_included";
	private static final String excludeSuffixString = "_excluded";
	private static final String reportSuffixString = "_report";
	
	private static final int includedSuffixIndex = 0;
	private static final int excludedSuffixIndex = 1;
	private static final int reportSuffixIndex = 2;
	
	private static final String includedString = "Included";
	private static final String excludedString = "Excluded";
	 
}
