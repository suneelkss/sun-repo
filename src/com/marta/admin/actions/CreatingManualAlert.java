package com.marta.admin.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Iterator;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.CreateManualAlertManagement;
import com.marta.admin.dto.BcwtAlertDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ManualAlertForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class CreatingManualAlert extends DispatchAction {
	
	final String ME = "CreatingManualAlert: ";
		
		public ActionForward displayManualAlert(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "displayManualAlert: ";
			BcwtsLogger.debug(MY_NAME
					+ "Populated data for partnerss");
			HttpSession session = request.getSession(true);
			String returnValue = "createManualAlert";
			String alertId = null;
			CreateManualAlertManagement alertManagement = null;
			try {
				alertManagement = new CreateManualAlertManagement();
				List alertList = alertManagement.getAlertList(alertId,request);
				session.setAttribute(Constants.ALERT_MESSAGE_LIST, alertList);
				request.setAttribute(Constants.ALERT_MESSAGE_LIST, alertList);
				session.removeAttribute(Constants.IS_SHOW);
				session.removeAttribute(Constants.IS_EDIT_SHOW);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_CREATE_MANUAL_ALERT);
				returnValue = "createManualAlert";
				
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while getting information for displaying unlock page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);
		}
	    /*
	     * Method to add Alerts
	     */
		public ActionForward addManualAlert(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "addManualAlert: ";
			BcwtsLogger.debug(MY_NAME
					+ "add alert for admin");
			HttpSession session = request.getSession(true);
			ManualAlertForm alertForm = (ManualAlertForm) form; 
			String returnValue = "createManualAlert";
			BcwtAlertDTO bcwtAlertDTO = null;
			BcwtPatronDTO bcwtPatronDTO = null;
			CreateManualAlertManagement alertManagement = null;
			Long patronId = new Long(0);
			boolean isAdded = false; 
			String alertId = null;
			List alertList = null;
			try {
				if(session.getAttribute(Constants.USER_INFO)!=null){
					bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					patronId = bcwtPatronDTO.getPatronid();
				}
				if(alertForm != null){
					bcwtAlertDTO = new BcwtAlertDTO();
					if(patronId !=null){
						bcwtAlertDTO.setPatronId(patronId);
					}
					if(alertForm.getAlertTitle() !=null &&
							!Util.isBlankOrNull(alertForm.getAlertTitle())){
						bcwtAlertDTO.setAlertTitle(alertForm.getAlertTitle());
					}else{
						bcwtAlertDTO.setAlertTitle(" ");
					}
					if(alertForm.getAlertMessage() !=null &&
							!Util.isBlankOrNull(alertForm.getAlertMessage())){
						bcwtAlertDTO.setAlertMessage(alertForm.getAlertMessage());
					}else{
						bcwtAlertDTO.setAlertMessage(" ");
					}
					if(alertForm.getDateToDiscontinueAlert() !=null &&
							!Util.isBlankOrNull(alertForm.getDateToDiscontinueAlert())){
						bcwtAlertDTO.setDateToDiscontinueAlert(alertForm.getDateToDiscontinueAlert());
					}else{
						bcwtAlertDTO.setDateToDiscontinueAlert(" ");
					}
					if(alertForm.getDateToReleaseAlert() !=null &&
							!Util.isBlankOrNull(alertForm.getDateToReleaseAlert())){
						bcwtAlertDTO.setDateToReleaseAlert(alertForm.getDateToReleaseAlert());
					}else{
						bcwtAlertDTO.setDateToReleaseAlert(" ");
					}
				}
				alertManagement = new CreateManualAlertManagement();
				isAdded = alertManagement.addOrUpdateAlertMessage(bcwtAlertDTO);
				if(isAdded){
					alertList = alertManagement.getAlertList(alertId,request);
					session.setAttribute(Constants.ALERT_MESSAGE_LIST, alertList);
					session.removeAttribute(Constants.IS_SHOW);
					session.removeAttribute(Constants.IS_EDIT_SHOW);
					session.setAttribute(Constants.ALERT_STATUS, "true");
					request.setAttribute(Constants.MESSAGE,
							PropertyReader
									.getValue(Constants.ALERT_MESSAGE_ADDED_SUCESS));
				}else{
					session.removeAttribute(Constants.ALERT_STATUS);
					session.removeAttribute(Constants.IS_SHOW);
					session.removeAttribute(Constants.IS_EDIT_SHOW);
					request.setAttribute(Constants.MESSAGE,
							PropertyReader
									.getValue(Constants.ALERT_MESSAGE_ADDED_FAILURE));
				}
				returnValue = "createManualAlert";
				
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while getting information for displaying unlock page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);
		}
		//to display edit values
		
		public ActionForward displayEditAlertData(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "displayEditAlertData: ";
			BcwtsLogger.debug(MY_NAME
					+ "edit alert for admin");
			HttpSession session = request.getSession(true);
			ManualAlertForm alertForm = (ManualAlertForm) form; 
			String returnValue = "createManualAlert";
			BcwtAlertDTO bcwtAlertDTO = null;
			BcwtPatronDTO bcwtPatronDTO = null;
			CreateManualAlertManagement alertManagement = null;
			Long patronId = new Long(0);
			boolean isAdded = false; 
			String alertId = null;
			List displayAlertList = null;
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			DateFormat formatter1 = new SimpleDateFormat(Constants.DATE_FORMAT);
			try {
				if(session.getAttribute(Constants.USER_INFO)!=null){
					bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					patronId = bcwtPatronDTO.getPatronid();
				}
				if(request.getParameter("alertId")!=null &&
						!Util.isBlankOrNull(request.getParameter("alertId").toString())){
					alertId = request.getParameter("alertId").toString();
				}
				alertManagement = new CreateManualAlertManagement();
				List alertList = alertManagement.getAlertList(alertId,request);
				if(alertList != null){
					for(Iterator iter = alertList.iterator();iter.hasNext();){
						bcwtAlertDTO = (BcwtAlertDTO) iter.next();
						if(bcwtAlertDTO.getAlertTitle()  != null &&
								!Util.isBlankOrNull(bcwtAlertDTO.getAlertTitle())){
							alertForm.setEditAlertTitle(bcwtAlertDTO.getAlertTitle());
						}else{
							alertForm.setEditAlertTitle(" ");
						}
						if(bcwtAlertDTO.getDateToReleaseAlert()  != null &&
								!Util.isBlankOrNull(bcwtAlertDTO.getDateToReleaseAlert())){
							Date releaseDate = formatter1.parse(bcwtAlertDTO.getDateToReleaseAlert());
							alertForm.setEditDateToReleaseAlert(formatter1.format(releaseDate));
						}else{
							alertForm.setEditDateToReleaseAlert(" ");
						}
						if(bcwtAlertDTO.getDateToDiscontinueAlert()  != null &&
								!Util.isBlankOrNull(bcwtAlertDTO.getDateToDiscontinueAlert())){
							Date discontinueDate = formatter1.parse(bcwtAlertDTO.getDateToDiscontinueAlert());
							alertForm.setEditDateToDiscontinueAlert(formatter1.format(discontinueDate));
						}else{
							alertForm.setEditDateToDiscontinueAlert(" ");
						}
						if(bcwtAlertDTO.getAlertMessage()  != null &&
								!Util.isBlankOrNull(bcwtAlertDTO.getAlertMessage())){
							alertForm.setEditAlertMessage(bcwtAlertDTO.getAlertMessage());
						}else{
							alertForm.setEditAlertMessage(" ");
						}
						if(alertId != null){
							alertForm.setEditManualAlertId(alertId);
						}
					}
				}
				request.setAttribute("ManualAlertForm", alertForm);
				String displayalertId = null;
				displayAlertList = alertManagement.getAlertList(displayalertId,request);
				session.setAttribute(Constants.ALERT_MESSAGE_LIST, displayAlertList);
				session.setAttribute(Constants.IS_EDIT_SHOW, "true");
				session.removeAttribute(Constants.IS_SHOW);
				returnValue = "createManualAlert";
				
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while getting information for displaying unlock page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);
		}
		  /*
	     * Method to edit Alerts
	     */
		public ActionForward updateManualAlert(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "updateManualAlert: ";
			BcwtsLogger.debug(MY_NAME
					+ "update alert for admin");
			HttpSession session = request.getSession(true);
			ManualAlertForm alertForm = (ManualAlertForm) form; 
			String returnValue = "createManualAlert";
			BcwtAlertDTO bcwtAlertDTO = null;
			BcwtPatronDTO bcwtPatronDTO = null;
			CreateManualAlertManagement alertManagement = null;
			Long patronId = new Long(0);
			boolean isUpdate = false; 
			String alertId = null;
			List alertList = null;
			try {
				if(session.getAttribute(Constants.USER_INFO)!=null){
					bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					patronId = bcwtPatronDTO.getPatronid();
				}
				if(alertForm != null){
					bcwtAlertDTO = new BcwtAlertDTO();
					if(patronId !=null){
						bcwtAlertDTO.setPatronId(patronId);
					}
					if(alertForm.getEditAlertTitle() !=null &&
							!Util.isBlankOrNull(alertForm.getEditAlertTitle())){
						bcwtAlertDTO.setAlertTitle(alertForm.getEditAlertTitle());
					}else{
						bcwtAlertDTO.setAlertTitle(" ");
					}
					if(alertForm.getEditAlertMessage() !=null &&
							!Util.isBlankOrNull(alertForm.getEditAlertMessage())){
						bcwtAlertDTO.setAlertMessage(alertForm.getEditAlertMessage());
					}else{
						bcwtAlertDTO.setAlertMessage(" ");
					}
					if(alertForm.getEditDateToDiscontinueAlert() !=null &&
							!Util.isBlankOrNull(alertForm.getEditDateToDiscontinueAlert())){
						bcwtAlertDTO.setDateToDiscontinueAlert(alertForm.getEditDateToDiscontinueAlert());
					}else{
						bcwtAlertDTO.setDateToDiscontinueAlert(" ");
					}
					if(alertForm.getEditDateToReleaseAlert() !=null &&
							!Util.isBlankOrNull(alertForm.getEditDateToReleaseAlert())){
						bcwtAlertDTO.setDateToReleaseAlert(alertForm.getEditDateToReleaseAlert());
					}else{
						bcwtAlertDTO.setDateToReleaseAlert(" ");
					}
					if(alertForm.getEditManualAlertId() !=null &&
							!Util.isBlankOrNull(alertForm.getEditManualAlertId())){
						bcwtAlertDTO.setManualAlertId(alertForm.getEditManualAlertId());
					}else{
						bcwtAlertDTO.setManualAlertId(" ");
					}
				}
				alertManagement = new CreateManualAlertManagement();
				isUpdate = alertManagement.addOrUpdateAlertMessage(bcwtAlertDTO);
				if(isUpdate){
					alertList = alertManagement.getAlertList(alertId,request);
					session.setAttribute(Constants.ALERT_MESSAGE_LIST, alertList);
					session.removeAttribute(Constants.IS_SHOW);
					session.removeAttribute(Constants.IS_EDIT_SHOW);
					session.setAttribute(Constants.ALERT_STATUS, "true");
					request.setAttribute(Constants.MESSAGE,
							PropertyReader
									.getValue(Constants.ALERT_MESSAGE_UPDATE_SUCESS));
				}else{
					session.removeAttribute(Constants.ALERT_STATUS);
					session.removeAttribute(Constants.IS_SHOW);
					session.removeAttribute(Constants.IS_EDIT_SHOW);
					request.setAttribute(Constants.MESSAGE,
							PropertyReader
									.getValue(Constants.ALERT_MESSAGE_UPDATE_FAILURE));
				}
				returnValue = "createManualAlert";
				
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while updating alert Message details:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);
		}
		/*
	     * Method to delete Alerts
	     */
		public ActionForward deleteAlertData(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "deleteAlertData: ";
			BcwtsLogger.debug(MY_NAME
					+ "delete alert for admin");
			HttpSession session = request.getSession(true);
			ManualAlertForm alertForm = (ManualAlertForm) form; 
			String returnValue = "createManualAlert";
			BcwtAlertDTO bcwtAlertDTO = null;
			BcwtPatronDTO bcwtPatronDTO = null;
			CreateManualAlertManagement alertManagement = null;
			Long patronId = new Long(0);
			boolean isdeleted = false; 
			String alertId = null;
			List alertList = null;
			try {
				if(session.getAttribute(Constants.USER_INFO)!=null){
					bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					patronId = bcwtPatronDTO.getPatronid();
				}
				if(request.getParameter("alertId") != null &&
						!Util.isBlankOrNull(request.getParameter("alertId"))){
					alertId = request.getParameter("alertId").toString();
					
				}
				alertManagement = new CreateManualAlertManagement();
				isdeleted = alertManagement.removeAlertMessage(alertId);
				String disAlertId = null;
				alertList = alertManagement.getAlertList(disAlertId,request);
				session.setAttribute(Constants.ALERT_MESSAGE_LIST, alertList);
				session.removeAttribute(Constants.IS_SHOW);
				session.removeAttribute(Constants.IS_EDIT_SHOW);
				session.setAttribute(Constants.ALERT_STATUS, "true");
				if(isdeleted){
					request.setAttribute(Constants.MESSAGE,
							PropertyReader
									.getValue(Constants.ALERT_MESSAGE_REMOVED_SUCESS));
				}else{
					session.removeAttribute(Constants.ALERT_STATUS);
					request.setAttribute(Constants.MESSAGE,
							PropertyReader
									.getValue(Constants.ALERT_MESSAGE_REMOVED_FAILURE));
				}
				returnValue = "createManualAlert";
				
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while removing alert Message details:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);
		}
		//to display alert div
		public ActionForward displayAddDiv(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "displayAddDiv: ";
			BcwtsLogger.debug(MY_NAME
					+ "display alert for admin");
			HttpSession session = request.getSession(true);
			String returnValue = "createManualAlert";
			String disAlertId = null;
			CreateManualAlertManagement alertManagement = null;
			
			try {
				alertManagement = new CreateManualAlertManagement();
				List alertList = alertManagement.getAlertList(disAlertId,request);
				session.setAttribute(Constants.ALERT_MESSAGE_LIST, alertList);
				session.setAttribute(Constants.IS_SHOW,"true");
				session.removeAttribute(Constants.IS_EDIT_SHOW);
				returnValue = "createManualAlert";
				
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while displaying alert Message add div:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);
		}
//		to display welcome alert
		public ActionForward displayWelcomeAlert(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "displayWelcomeAlert: ";
			BcwtsLogger.debug(MY_NAME
					+ "display alert for admin in welcome page");
			HttpSession session = request.getSession(true);
			String returnValue = "welcome";
			CreateManualAlertManagement alertManagement = null;
			String currentDate = null;
			BcwtPatronDTO bcwtPatronDTO = null;
			String patronId = null;
			try {
				if(session.getAttribute(Constants.USER_INFO)!=null){
					bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					patronId = bcwtPatronDTO.getPatronid().toString();
				}
				alertManagement = new CreateManualAlertManagement();
				currentDate = Util.getCurrentDate();
				List alertList = alertManagement.displayAlerts(patronId, currentDate);
				session.setAttribute(Constants.ALERT_MESSAGE_LIST_WELCOMEPAGE, alertList);
				returnValue = "welcome";
				
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while displaying alert Message in Welcome Page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);
		}
		
		
		
}
