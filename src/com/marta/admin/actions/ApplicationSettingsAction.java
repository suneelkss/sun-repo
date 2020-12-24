package com.marta.admin.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.ApplicationSettings;
import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.dto.ApplicationSettingsDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ApplicationSettingsForm;
import com.marta.admin.utils.Base64EncodeDecodeUtil;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

/**
 * Action for application settings
 * 
 * @author Jagadeesan
 *
 */
public class ApplicationSettingsAction extends DispatchAction{
	
	   final String ME = "ApplicationSettingsAction: ";
	   
	   /**
	    * To show application settings details
	    * 
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    * @throws MartaException
	    */
	public ActionForward displayApplicationSettingsPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "applicationSettings: ";
		BcwtsLogger.debug(MY_NAME + "gathering pre populated data for application settings page");
		String returnValue = "applicationSettings";
		ApplicationSettingsForm applicationSettingsForm = (ApplicationSettingsForm) form;
		ApplicationSettings applicationSettings = new ApplicationSettings();
		ApplicationSettingsDTO applicationSettingsDTO = new ApplicationSettingsDTO();
		HttpSession session = request.getSession(true);

		String userName = "";
		try {
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
			if(!Util.isBlankOrNull(applicationSettingsForm.getConfigName())){
				applicationSettingsDTO.getParamValue();
			}
			if (!Util.isBlankOrNull(applicationSettingsForm.getParamValue())) {
				applicationSettingsDTO.getDefaultValue();
			}
			if (!Util.isBlankOrNull(applicationSettingsForm.getDefaultValue())) {
				applicationSettingsDTO.getActiveStatus();
			}			
			List applicationSettingsList = applicationSettings.getApplicationSettingsList();
			session.setAttribute(Constants.APPLICATION_SETTINGS_LIST, applicationSettingsList);
			session.removeAttribute(Constants.SHOW_EDIT_DIV);
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while displaying application settings details page :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_APPLICATION_SETTINGS);
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Method to update application settings for particular config name
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward updateApplicationSettingsDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "updateApplicationSettingsDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "to update application settings details");
		HttpSession session = request.getSession(true);
		String returnValue = "applicationSettings";
		ApplicationSettings applicationSettings = null;
		ApplicationSettingsDTO applicationSettingsDTO = null;
		String currentKeyValue = null;
		String newKeyValue = null;
		ApplicationSettingsForm applicationSettingsForm = (ApplicationSettingsForm)form;
		boolean isUpdate = false;
		List applicationSettingsList = new ArrayList();
		boolean isCreditCardsUpdated = false;
		try {
			applicationSettings = new ApplicationSettings();
			applicationSettingsDTO = new ApplicationSettingsDTO();
			
			if(Util.equalsIgnoreCase(applicationSettingsForm.getConfigName(),Constants.CREDIT_CARD_SECURITY_KEY)){
				if(Util.equalsIgnoreCase(String.valueOf(applicationSettingsForm.getNewSecurityKey().length()),
						String.valueOf(Constants.BREEZE_CARD_SERIAL_NUMBER_LENGTH))){
					currentKeyValue = Base64EncodeDecodeUtil.decodeString(ConfigurationCache.getConfigurationValues
							(Constants.CREDIT_CARD_SECURITY_KEY).getParamvalue());
					
					if(applicationSettingsForm.getParamValue() != null){
						newKeyValue= applicationSettingsForm.getNewSecurityKey();
						
						isCreditCardsUpdated = applicationSettings.updateCreditCardNumbers
								(currentKeyValue,newKeyValue);
						if(!isCreditCardsUpdated){
							request.setAttribute(Constants.MESSAGE,"Problem in encryption process");
							request.setAttribute(Constants.SUCCESS, "false");
							return mapping.findForward(returnValue);
						}
					}
				}else{
					request.setAttribute(Constants.MESSAGE,"New Security key value should have 16 characters");
					request.setAttribute(Constants.SUCCESS, "false");
					return mapping.findForward(returnValue);
				}
			}

			if(applicationSettingsForm.getEditConfigId() != null){
				applicationSettingsDTO.setConfigId(applicationSettingsForm.getEditConfigId());
			}
			
			if(applicationSettingsForm.getParamValue() != null){				
				applicationSettingsDTO.setParamValue(applicationSettingsForm.getParamValue());				
			}
			
			if(applicationSettingsForm.getDefaultValue() != null){				
				applicationSettingsDTO.setDefaultValue(applicationSettingsForm.getDefaultValue());
			}
			
			if(applicationSettingsForm.getActiveStatus() != null){
				applicationSettingsDTO.setActiveStatus(applicationSettingsForm.getActiveStatus());
			}
			
			if(!Util.isBlankOrNull(applicationSettingsForm.getNewSecurityKey())){
				applicationSettingsDTO.setDefaultValue(Base64EncodeDecodeUtil.encodeString
						(applicationSettingsForm.getNewSecurityKey()));
				applicationSettingsDTO.setParamValue(Base64EncodeDecodeUtil.encodeString
						(applicationSettingsForm.getNewSecurityKey()));
			}
				
			
			isUpdate = applicationSettings.updateApplicationSettingsDetails(applicationSettingsDTO);
			if(isUpdate){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.APPLICATION_SETTINGS_SUCCESS));
				request.setAttribute(Constants.SUCCESS, "true");
				applicationSettingsList = applicationSettings.getApplicationSettingsList();
				session.setAttribute(Constants.APPLICATION_SETTINGS_LIST, applicationSettingsList);
			}else{
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.APPLICATION_SETTINGS_FAILURE));
				request.setAttribute(Constants.SUCCESS, "false");
			}
			
			session.removeAttribute(Constants.SHOW_EDIT_DIV);
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while updating application settings details:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_APPLICATION_SETTINGS);
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Method to get application settings detail for particular config name
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward getData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "getData: ";
		BcwtsLogger.debug(MY_NAME + "get application settings details");
		HttpSession session = request.getSession(true);
		ApplicationSettingsForm applicationSettingsForm = (ApplicationSettingsForm) form;
		String returnValue = "applicationSettings";
		ApplicationSettingsDTO applicationSettingsDTO = null;
		ApplicationSettings applicationSettings = null;
			
		try {
			String confidIdStr = request.getParameter("configId").toString();
			Long configId = new Long(confidIdStr);
			applicationSettings = new ApplicationSettings();
			applicationSettingsForm.setEditConfigId(configId.toString());
			applicationSettingsDTO = applicationSettings.getApplicationSettingsDetails(configId);
			
			if(null != applicationSettingsDTO.getParamValue()){
				applicationSettingsForm.setParamValue(applicationSettingsDTO.getParamValue());
			}
			if(null != applicationSettingsDTO.getDefaultValue()){
				applicationSettingsForm.setDefaultValue(applicationSettingsDTO.getDefaultValue());
			}
			if(null != applicationSettingsDTO.getActiveStatus()){
				applicationSettingsForm.setActiveStatus(applicationSettingsDTO.getActiveStatus());
			}
			if(null != applicationSettingsDTO.getConfigName()){
				applicationSettingsForm.setConfigName(applicationSettingsDTO.getConfigName());
				if(Util.equalsIgnoreCase(applicationSettingsDTO.getConfigName(), 
						Constants.CREDIT_CARD_SECURITY_KEY)){
							session.setAttribute(Constants.SHOW_SECURITY_KEY_DIV,"true");	
							session.removeAttribute(Constants.SHOW_OTHERS_DIV);
							
				}else{
					session.setAttribute(Constants.SHOW_OTHERS_DIV,"true");
					session.removeAttribute(Constants.SHOW_SECURITY_KEY_DIV);
				}
			}	
			
			session.setAttribute(Constants.SHOW_EDIT_DIV, applicationSettingsDTO);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while populating application settings details:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_APPLICATION_SETTINGS);
		return mapping.findForward(returnValue);		
	}
	/**
	 * Method to reset param value
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward resetParamValue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "resetParamValue: ";
		BcwtsLogger.debug(MY_NAME + "reset param value ");
		HttpSession session = request.getSession(true);
		ApplicationSettingsForm applicationSettingsForm = (ApplicationSettingsForm) form;
		List applicationSettingsList = null ;
		applicationSettingsList = new ArrayList();
		String returnValue = "applicationSettings";
		ApplicationSettings applicationSettings = null;
		boolean isUpdate = false;
		try {
			String confidIdStr = request.getParameter("configId").toString();
			Long configId = new Long(confidIdStr);
			applicationSettings = new ApplicationSettings();
			applicationSettingsForm.setEditConfigId(configId.toString());
			
			isUpdate = applicationSettings.resetParamValue(configId);
			
			if(isUpdate == true){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.RESET_PARAM_VALUE_SUCCESS));
				request.setAttribute(Constants.SUCCESS, "true");
				applicationSettingsList = applicationSettings.getApplicationSettingsList();
				session.setAttribute(Constants.APPLICATION_SETTINGS_LIST, applicationSettingsList);
			}else{
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.RESET_PARAM_VALUE_FAILURE));
				request.setAttribute(Constants.SUCCESS, "false");
			}
			session.removeAttribute(Constants.SHOW_EDIT_DIV);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while resetting param value to default value :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_APPLICATION_SETTINGS);
		return mapping.findForward(returnValue);		
	}
	
	/**
	 * 
	 * @param configId
	 * @return
	 * @throws Exception
	 */
	/*
	private String getCurrentCreditCardKey(String configId)throws Exception{
		
		final String MY_NAME = ME + "getCurrentCreditCardKey: ";
		BcwtsLogger.debug(MY_NAME + "getting Current credit card key");
		String keyValue = null;
		ApplicationSettings applicationSettings = null;
		try {
			applicationSettings = new ApplicationSettings();
			keyValue = applicationSettings.getCurrentCreditCardKey(configId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while getting param value :"
					+ e.getMessage());		
		}
		
		return keyValue;
		
	}
	*/
}
