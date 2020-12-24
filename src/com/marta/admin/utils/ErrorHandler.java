package com.marta.admin.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.marta.admin.exceptions.MartaCriticalException;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.exceptions.MartaMajorException;
import com.marta.admin.exceptions.MartaMinorException;

/**
 * This util class is used for error handler.
 * @author Administrator
 * 
 */
public class ErrorHandler {
	
	/**
	 * Method for error handler.
	 * 
	 * @param e
	 * @param contextMsg
	 * @param request
	 * @param mapping
	 * @return
	 */
	public static String handleError(Exception e, String contextMsg,HttpServletRequest request, ActionMapping mapping) {

		boolean isFatalError = false;

		String errorMessage = "" ;
		String forwardPage = "globalerror"; 
		try {

			if (e instanceof MartaMinorException) {

				errorMessage = (String) ((MartaException) e)
						.getErrorCode();
				request.setAttribute(Constants.WARNING, errorMessage);
				

			} else if (e instanceof MartaMajorException) {
				errorMessage = (String) ((MartaException) e)
						.getErrorCode();
				request.setAttribute(Constants.MESSAGE, errorMessage);

			} else if (e instanceof MartaCriticalException) {
				errorMessage = (String) ((MartaException) e)
						.getErrorCode();
				request.setAttribute(Constants.MESSAGE, errorMessage); 
				isFatalError = true;

			} else {
				errorMessage = e.getMessage();
				request.setAttribute(Constants.MESSAGE, errorMessage);
			}
			if(errorMessage!=null && !errorMessage.equals("")){
				forwardPage = mapping.getInput();
				if(null== forwardPage){
					forwardPage = "globalerror";
				}
			}
		} finally {
			if (isFatalError) {
				forwardPage = "globalerror";
			}
		}		
		return forwardPage;
	}
}
