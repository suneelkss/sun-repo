package com.marta.admin.business;


import com.marta.admin.dao.NewLogCallDAO;
import com.marta.admin.dto.BcwtLogDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.PropertyReader;

/**
 * Bussiness class for new log call
 * @author Raj
 *
 */
public class NewLogCallManagement {
	
	final String ME = "NewLogCallManagement";
	
	public BcwtLogDTO getCallerDetails(String callerUserName,String callerUserType)
		throws MartaException{		
		final String MY_NAME = ME + " getCallerDetails: ";
		BcwtsLogger.debug(MY_NAME + " to get caller details ");	
		BcwtLogDTO resultBcwtLogDTO = null;
		try {
			NewLogCallDAO newLogCallDAO = new NewLogCallDAO();			
			resultBcwtLogDTO = newLogCallDAO.getCallerDetails(callerUserName,callerUserType);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting log call details:"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ADMIN_USER_LIST_FIND));
		}		
		return resultBcwtLogDTO;		
	}
	
	
	/**
	 * Method to log New call 
	 * @param bcwtLogDTO
	 * @return
	 * @throws MartaException
	 */
	public boolean addLogCall(BcwtLogDTO bcwtLogDTO)	
		throws MartaException{
	
		final String MY_NAME = ME + " addNewLogCall: ";
		BcwtsLogger.debug(MY_NAME + " to add new log call details ");
		boolean isAdded = false;
		try {
			NewLogCallDAO newLogCallDAO = new NewLogCallDAO();			
			isAdded = newLogCallDAO.addLogCall(bcwtLogDTO);
		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME + " Exception in ading new log details:"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(Constants.UNABLE_TO_LOG_NEW_LOG_CALL));
		}		
		return isAdded;		
	}
}