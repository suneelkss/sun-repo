/**
 * 
 */
package com.marta.admin.exceptions;

/**
 * @author dhayalan
 *
 */
public class MartaMajorException extends MartaException {

    public MartaMajorException( String errMsg) {
       super(errMsg);
      // Misc.log.majorError(this, "");
       System.out.println("BullsEyeMajorException:"+errMsg);
    }
    public MartaMajorException(String errMsg,String errorCode) {
	     super(errMsg,errorCode);
	   //  Misc.log.majorError(this, "");
	     System.out.println("BullsEyeMajorException:"+errMsg+":"+errorCode);
    }
}
