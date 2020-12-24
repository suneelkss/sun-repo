/**
 * 
 */
package com.marta.admin.exceptions;

/**
 * @author dhayalan
 *
 */
public class MartaMinorException extends MartaException {

    public MartaMinorException( String errMsg) {
       super(errMsg);
      // Misc.log.minorError(this, "");
       System.out.println("BullsEyeMinorException:"+errMsg);
    }
    public MartaMinorException(String errMsg,String errorCode) {
	     super(errMsg,errorCode);
	     System.out.println("BullsEyeMinorException:"+errMsg+":"+errorCode);
	   //  Misc.log.minorError(this, "");
	     System.out.println();
    }
}
