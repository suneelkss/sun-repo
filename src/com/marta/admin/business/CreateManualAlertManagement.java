package com.marta.admin.business;

import com.marta.admin.dao.CreateAlertDAO;
import com.marta.admin.dto.BcwtAlertDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class CreateManualAlertManagement {
	
	final String ME = "CreateManualAlertManagement: ";

	public boolean addOrUpdateAlertMessage(BcwtAlertDTO bcwtAlertDTO) throws MartaException {
		final String MY_NAME = ME + " addOrUpdateAlertMessage: ";
		BcwtsLogger.debug(MY_NAME + " adding and updating alerts");
		boolean isAdded = false;
		CreateAlertDAO alertDAO = null;
		try {
			alertDAO = new CreateAlertDAO();
			isAdded = alertDAO.addOrUpdateAlertMessage(bcwtAlertDTO);
			BcwtsLogger.info(MY_NAME + "added alert message" );
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in adding alert message :"
					+ e.getMessage());
			
		}
		return isAdded;
	}
	//to get alert list
	public List getAlertList(String alertId,HttpServletRequest request) throws MartaException {
		final String MY_NAME = ME + " getAlertList: ";
		BcwtsLogger.debug(MY_NAME + " getting alert list");
		CreateAlertDAO alertDAO = null;
		List alertList = null;
		try {
			alertDAO = new CreateAlertDAO();
			alertList = alertDAO.getAlertList(alertId,request);
			BcwtsLogger.info(MY_NAME + "getting alert list" );
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting alert list :"
					+ e.getMessage());
			
		} 
		return alertList;
	}
	//	to remove alert message
	public boolean removeAlertMessage(String alertId) throws MartaException {
		final String MY_NAME = ME + " removeAlertMessage: ";
		BcwtsLogger.debug(MY_NAME + " removing alert message");
		CreateAlertDAO alertDAO = null;
		boolean isDeleted = false;
		try {
			alertDAO = new CreateAlertDAO();
			isDeleted = alertDAO.removeAlertMessage(alertId);
			BcwtsLogger.info(MY_NAME + "removing alert message" );
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in removing alert message :"
					+ e.getMessage());
			
		} 
		return isDeleted;
	}
//	to display alert message in welcome page
	public List displayAlerts(String patronId,String curDate) throws MartaException {
		final String MY_NAME = ME + " removeAlertMessage: ";
		BcwtsLogger.debug(MY_NAME + " removing alert message");
		CreateAlertDAO alertDAO = null;
		List alertList =null;
		try {
			alertDAO = new CreateAlertDAO();
			alertList = alertDAO.displayAlerts(patronId,curDate);
			BcwtsLogger.info(MY_NAME + "removing alert message" );
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in removing alert message :"
					+ e.getMessage());
			
		} 
		return alertList;
	}

}
