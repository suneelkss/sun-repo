/**
 * 
 */
package com.marta.admin.utils;

import org.apache.log4j.Logger;

/**
 * This class is used for group bulk sales module Logging
 *
 */
public class BcwtsLogger {
	static Logger martaLogger = Logger.getLogger(BcwtsLogger.class.getName());


    public static void debug(String message) {
    	martaLogger.debug(message);
    }

    public static void info(String message) {
    	martaLogger.info(message);
    }

    public static void warn(String message) {
    	martaLogger.warn(message);
    }

    public static void error(String message) {
    	martaLogger.error(message);
    }

    public static void error(String message, Throwable exp) {
    	martaLogger.error(message, exp);
    }

    public static void fatal(String message) {
    	martaLogger.fatal(message);
    }
    
    public static void console(String message) {
    	martaLogger.info(message);
    }   

}
