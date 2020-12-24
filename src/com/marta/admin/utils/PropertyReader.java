package com.marta.admin.utils;

import java.util.Properties;

/**
 * This class is used handle the properties file.  
 * @author admin
 *
 */
public final class PropertyReader {

	public static String fileName = "com/marta/admin/resources/marta_en.properties";

	private static Properties prop;
	static {
		try {
			prop = new Properties();
			prop.load(new PropertyReader().getClass().getClassLoader()
					.getResourceAsStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get the value of a property.
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		String value = null;
		if ((prop != null) && (key != null)) {
			if(!Util.isBlankOrNull(prop.getProperty(key)))
			value = prop.getProperty(key).trim();
		}
		
		return value;
	}
	
	public static void main(String[] args) {
	 System.out.println(PropertyReader.getValue("UPLOAD_METHOD_NAME"));
	}
}