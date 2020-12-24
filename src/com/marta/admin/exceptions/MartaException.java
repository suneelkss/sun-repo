package com.marta.admin.exceptions;

/**
 * @author dhayalan
 *  This class defines the MartaException class which is the super class
 *  for all Marta exceptions.
 */

public class MartaException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	String errorCode="";
	
    public MartaException(String errMsg) {
       super(errMsg);
    }
    public MartaException(MartaException e) {
    	super(e.getMessage());
    	this.errorCode = e.getErrorCode();
     }
    public MartaException(String errMsg,String errorCode) {
       super(errMsg);
       this.errorCode = errorCode;
    }
    public String getErrorCode(){
       return errorCode;
    }
}
