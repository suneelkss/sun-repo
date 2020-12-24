package com.marta.admin.exceptions;

/**
 * This class is used to handle the exceptions.
 * @author dahyalan
 * 
 *
 */
public class MartaCriticalException extends MartaException {
    
	private static final long serialVersionUID = 1L;
	public MartaCriticalException( String errMsg) {
       super(errMsg);
       //Misc.log.criticalError(this, "");
       System.out.println("BullsEyeCriticalException:"+errMsg);
    }
    public MartaCriticalException(String errMsg,String errorCode) {
             super(errMsg,errorCode);
          //   Misc.log.majorError(this, "");
             System.out.println("BullsEyeCriticalException:"+errMsg+":"+errorCode);
    }
}
