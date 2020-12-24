package com.marta.admin.business;

import java.util.List;
import org.apache.struts.actions.DispatchAction;
import com.marta.admin.dao.ApplicationSettingsDAO;
import com.marta.admin.dto.ApplicationSettingsDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;

/**
 * 
 * @author Administrator
 *
 */
public class ApplicationSettings extends DispatchAction{

	final String ME = "ApplicationSettingsAction: ";
	
	/**
	 * To get application settings list
	 * 
	 * @return
	 * @throws MartaException
	 */
	public List getApplicationSettingsList() throws MartaException{
		
		final String MY_NAME = ME + " getApplicationSettingsList: ";
		BcwtsLogger.debug(MY_NAME + " getting application settings list ");
		List applicationSettingsList = null;
		try {
			ApplicationSettingsDAO applicationSettingsDAO = new ApplicationSettingsDAO();
			applicationSettingsList = applicationSettingsDAO.getApplicationSettingsList();
			BcwtsLogger.info(MY_NAME + " got application settings List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting application settings list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.APPLICATION_SETTINGS_LIST_FIND));
		}
		return applicationSettingsList;	
	}
	/**
	 * method to get application settings details for particular configId
	 * 
	 * @param configId
	 * @return
	 * @throws MartaException
	 */
	public ApplicationSettingsDTO getApplicationSettingsDetails(Long configId)throws MartaException{
		
		final String MY_NAME = ME + " getApplicationSettingsDetailsList: ";
		BcwtsLogger.debug(MY_NAME + " to get application settings detail list ");
		ApplicationSettingsDTO applicationSettingsDTO = null;
		ApplicationSettingsDAO applicationSettingsDAO = null;
		try {
			applicationSettingsDAO = new ApplicationSettingsDAO();
			applicationSettingsDTO = applicationSettingsDAO.getApplicationSettingsDetails(configId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting the application settings detail :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.APPLICATION_SETTINGS_LIST_FIND));
		}
		return applicationSettingsDTO;
	}
	/**
	 * method to update application settings details 
	 * 
	 * @param applicationSettingsDTO
	 * @return
	 * @throws MartaException
	 */
	public boolean updateApplicationSettingsDetails(ApplicationSettingsDTO applicationSettingsDTO)
									throws MartaException{
		
		final String MY_NAME = ME + " updateApplicationSettingsDetails: ";
		BcwtsLogger.debug(MY_NAME + " to update application settings detail ");
		ApplicationSettingsDAO applicationSettingsDAO = null;
		boolean isUpdate  = false;
		try {
			applicationSettingsDAO = new ApplicationSettingsDAO();
			isUpdate = applicationSettingsDAO.updateApplicationSettingsDetails(applicationSettingsDTO);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in updating application settings details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.APPLICATION_SETTINGS_UPDATE));
		}
		return isUpdate;
	}
	/**
	 * method to reset param value to default value for particular configId
	 * 
	 * @param configId
	 * @return
	 * @throws MartaException
	 */
	public boolean resetParamValue(Long configId) throws MartaException{
		final String MY_NAME = ME + " resetParamValue: ";
		BcwtsLogger.debug(MY_NAME + " to reset param value in application settings ");
		ApplicationSettingsDAO applicationSettingsDAO = null;
		boolean isUpdate  = false;
		try {
			applicationSettingsDAO = new ApplicationSettingsDAO();
    	    isUpdate = applicationSettingsDAO.resetParamValue(configId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in reset param value :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.APPLICATION_SETTINGS_UPDATE));
		}
		return isUpdate;
	}
	
	/**
	 * 
	 * @param configId
	 * @return
	 * @throws MartaException
	 */
	public String getCurrentCreditCardKey(String configId) throws MartaException{
		final String MY_NAME = ME + " getCurrentCreditCardKey: ";
		BcwtsLogger.debug(MY_NAME + " getting Current credit card key");
		ApplicationSettingsDAO applicationSettingsDAO = null;
		
		String keyValue  = null;
		try {
			applicationSettingsDAO = new ApplicationSettingsDAO();
			keyValue = applicationSettingsDAO.getCurrentCreditCardKey(configId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in reset param value :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.APPLICATION_SETTINGS_UPDATE));
		}
		return keyValue;
	}
	
	/**
	 * 
	 * @param configId
	 * @return
	 * @throws MartaException
	 */
	public boolean updateCreditCardNumbers(String currentSecurityKey,String newSecurityKey)
		throws MartaException{
		final String MY_NAME = ME + " updateCreditCardNumbers: ";
		BcwtsLogger.debug(MY_NAME + " updating Credit card numbers");
		ApplicationSettingsDAO applicationSettingsDAO = null;
		boolean isCreditCardsUpdated = false;
		try {
			applicationSettingsDAO = new ApplicationSettingsDAO();
			
			isCreditCardsUpdated = applicationSettingsDAO.updateCreditCardNumbers(currentSecurityKey,newSecurityKey);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in reset param value :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.APPLICATION_SETTINGS_UPDATE));
		}
		return isCreditCardsUpdated;
	}
}
