package com.marta.admin.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailIdValidation {

	private static final String VALID_PREFIX =  "Valid_"; 
	private static final String INVALID_PREFIX =  "Invalid_";
	private static final String SEPERATOR =  ",";
	private static final String INVALID_MSG = "Invalid Email Id";
	private static final String INVALID_DOMAIN = "Invalid Domain";
	private static final String LINE_SEPERATOR = "\r\n";
	
	/**
	 * Method to check whether the email is valid / not.
	 * @param emailId
	 * @return
	 */
	public static boolean isValidEmailId(String emailId) {
		
	   String regExPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}" +
	                          "\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\" +
	                          ".)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	   Pattern pattern = Pattern.compile(regExPattern);
	   Matcher matcher = pattern.matcher(emailId);
	   if(matcher.find()){
		   return true;
	   } else {
		   return false;
	   }
		
	}
	
	/**
	 * Method to check the mail id.
	 * 
	 * @param filePath
	 * @param fileWithExtn
	 * @throws Exception
	 */
	public static void checkValidOrInvalidEmailId
				(String filePath, String fileWithExtn) throws Exception {
		
		String fileURL = filePath + File.separator + fileWithExtn;
		StringBuffer invalidString = new StringBuffer();
		StringBuffer validString = new StringBuffer();
		try {
			List values = FileUtil.getFileContentAsLines(fileURL);
			for (Iterator iter = values.iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				String[] emailId = element.split(SEPERATOR);
				if(isValidEmailId(emailId[0])) {
					try {
						if(EmailValidation.isAddressValid(emailId[0])) {
							validString.append(element + LINE_SEPERATOR);
						} else {
							invalidString.append(element + SEPERATOR + 
									INVALID_MSG + LINE_SEPERATOR);
						}
					} catch (Exception ex) {
						if(ex.getMessage().equalsIgnoreCase("1")) {
							invalidString.append(element + SEPERATOR + 
									INVALID_DOMAIN + LINE_SEPERATOR);
						} else {
							invalidString.append(element + SEPERATOR + 
									ex.getMessage() + LINE_SEPERATOR);		
						}
						
					}
				} else {
					invalidString.append(element + SEPERATOR + 
							INVALID_MSG + LINE_SEPERATOR);
				}				
			}
			if(invalidString.length() > 0) {
				String invalidURL = filePath + File.separator + INVALID_PREFIX + fileWithExtn;
				FileUtil.writeToFile(invalidString.toString(), invalidURL);
			}
			if(validString.length() > 0) {
				String validURL = filePath + File.separator + VALID_PREFIX + fileWithExtn;
				FileUtil.writeToFile(validString.toString(), validURL);
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
	}
	
	public static void main(String[] args) throws Exception {		
		EmailIdValidation.checkValidOrInvalidEmailId("C:\\test", "sample.txt");
	}
}
