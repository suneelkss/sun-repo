package com.marta.admin.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.business.LogManagement;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BcwtLogDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.LoggingCallsForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.FileUtil;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class LogManagementAction extends DispatchAction {
	final String ME = "LogManagementAction: ";

	String dispatchTo = "";

	String currentPath = "";
	String fromDate = null;
	boolean isInteger = false;
	
	public ActionForward displayLogPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayLogPage: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for logging calls page");
		HttpSession session = request.getSession(true);
		LoggingCallsForm loggingCallsForm=(LoggingCallsForm)form;
		String returnValue = null;
		BcwtPatronDTO bcwtPatronDTO = null;
		Long roleId = new Long(0);		
		//Getting values from the form
		fromDate = loggingCallsForm.getFromDate();
		if (null != request.getParameter("isInteger")
				&& request.getParameter("isInteger").equalsIgnoreCase(
						"true")) {
			isInteger = true;
			request.setAttribute("isInteger", String.valueOf(isInteger));
		}
		try {
			
			loggingCallsForm.setLogType(Constants.IS);		
			loggingCallsForm.setHiddenLogType(Constants.IS);
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				roleId = bcwtPatronDTO.getBcwtusertypeid();
			}
			returnValue = "loggingCalls";
			LogManagement logManagement = new LogManagement();
			
			List logDetailsList = logManagement.getCallLogDetails(Constants.IS,roleId.toString());
			
			request.setAttribute(Constants.LOG_LIST, logDetailsList);
			session.setAttribute(Constants.LOG_LIST, logDetailsList);			
			session.setAttribute("SHOW_OTHERS_DIV","SHOW_OTHERS_DIV");
			session.removeAttribute("SHOW_DATA_GRID");
			session.removeAttribute("CREATE_DIV");
			session.removeAttribute(Constants.IS_CREATE_SHOW);
			session.removeAttribute("VIEW_GRID_DIV");
			session.removeAttribute("VIEW_PATRON_DIV");
			session.setAttribute(Constants.LOGGING_CALLS, returnValue);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, ">> My Account >>  Logging Calls");
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting state/secretquestion information :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Action for display grid page for IS,GBS and PS
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayGridPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayLogPage: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for logging calls page");
		HttpSession session = request.getSession(true);
		LoggingCallsForm loggingCallsForm=(LoggingCallsForm)form;
		String returnValue = null;
		String userType = null;
		BcwtPatronDTO bcwtPatronDTO = null;
		String patronType = null;
		List recentCallLogDetails = new ArrayList();
//		 Getting values from the form
		fromDate = loggingCallsForm.getFromDate();
		if (null != request.getParameter("isInteger")
				&& request.getParameter("isInteger").equalsIgnoreCase(
						"true")) {
			isInteger = true;
			request.setAttribute("isInteger", String.valueOf(isInteger));
		}
		try {
			LogManagement logManagement = new LogManagement();
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronType = bcwtPatronDTO.getBcwtusertypeid().toString();
			}
			
			if(null != request.getParameter("userType")){
				userType = request.getParameter("userType");
				loggingCallsForm.setHiddenLogType(userType);
			}if(!Util.isBlankOrNull(loggingCallsForm.getHiddenLogType())){
				userType = loggingCallsForm.getHiddenLogType();
			}else{
				userType = Constants.IS;
			}
			
			returnValue = "loggingCalls";
			logManagement = new LogManagement();
			recentCallLogDetails = logManagement.getCallLogDetails(userType, patronType);
			request.setAttribute(Constants.LOG_LIST, recentCallLogDetails);
			session.setAttribute(Constants.LOG_LIST, recentCallLogDetails);
			session.removeAttribute(Constants.IS_SHOW);
			session.removeAttribute(Constants.IS_CREATE_SHOW);
			session.setAttribute(Constants.LOGGING_CALLS, returnValue);
		    session.setAttribute("SHOW_OTHERS_DIV", "SHOW_OTHERS_DIV");
       	    session.removeAttribute(Constants.IS_SHOW);
			session.setAttribute(Constants.IS_CREATE_SHOW, "true");
			session.removeAttribute("CREATE_DIV");
			session.removeAttribute("VIEW_PATRON_DIV");
				
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting state/secretquestion information :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.removeAttribute("VIEW_GRID_DIV");
		return mapping.findForward(returnValue);
	}
	
	public ActionForward displayPartnerGridPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayPartnerLogPage: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for logging calls page");
		HttpSession session = request.getSession(true);
		LoggingCallsForm loggingCallsForm=(LoggingCallsForm)form;
		String returnValue = null;
		String userType = null;
		BcwtLogDTO bcwtLogDTO = null;
		BcwtPatronDTO bcwtPatronDTO = null;
		String patronType = null;
		String createType=null;
//		 Getting values from the form
		fromDate = loggingCallsForm.getFromDate();
		if (null != request.getParameter("isInteger")
				&& request.getParameter("isInteger").equalsIgnoreCase(
						"true")) {
			isInteger = true;
			request.setAttribute("isInteger", String.valueOf(isInteger));
		}
		try {
			
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronType = bcwtPatronDTO.getBcwtusertypeid().toString();
			}
			if(null != request.getParameter("userType")){
				userType = request.getParameter("userType");
				//System.out.println("usertype"+userType);
			
			returnValue = "loggingCalls";
			LogManagement logManagement = new LogManagement();
			
			//String utype=(String)logDetailsList.get(2);
            //System.out.println(""+utype);
			List partnerDetailsList=logManagement.getPartnerCallLogDetails(userType,patronType);
			request.setAttribute(Constants.LOG_LIST, partnerDetailsList);
			session.setAttribute(Constants.LOG_LIST, partnerDetailsList);
			session.removeAttribute(Constants.IS_SHOW);
			session.removeAttribute(Constants.IS_CREATE_SHOW);
			session.setAttribute(Constants.LOGGING_CALLS, returnValue);
			}else if(null != request.getParameter("createType")){
				returnValue = "loggingCalls";
				createType = request.getParameter("createType");
				userType=loggingCallsForm.getUserTypeName();
				//System.out.println("create"+createType);
				//System.out.println("view"+createType);
				LogManagement logManagement = new LogManagement();
				List partnerDetailsList=logManagement.getCallLogDetails(userType, patronType);
				for (Iterator iter = partnerDetailsList.iterator();iter.hasNext();) {
					bcwtLogDTO=(BcwtLogDTO) iter.next();
	               	loggingCallsForm.setAdministratorEmail(bcwtLogDTO.getAdministratorEmail());
	            	loggingCallsForm.setAdministratorPhoneNumber(bcwtLogDTO.getAdministratorPhoneNumber());
	            	loggingCallsForm.setAdministratorUserName(bcwtLogDTO.getAdministratorUserName());
	            	loggingCallsForm.setReasonForCall(bcwtLogDTO.getReasonForCall());
	            	loggingCallsForm.setDateOfCall(String.valueOf(bcwtLogDTO.getDateOfCall()));
	            	loggingCallsForm.setTimeStampForCall(String.valueOf(bcwtLogDTO.getTimeStampForCall()));
	            	loggingCallsForm.setNote(bcwtLogDTO.getNote());
				}
				request.setAttribute("LoggingCallsForm", loggingCallsForm);
				request.setAttribute(Constants.LOG_LIST, partnerDetailsList);
				session.setAttribute(Constants.LOG_LIST, partnerDetailsList);
			}
				else {
				loggingCallsForm.setCompanyName(bcwtLogDTO.getCompanyName());
            	loggingCallsForm.setCompanyId(bcwtLogDTO.getCompanyId());
			}
           	    session.removeAttribute(Constants.IS_SHOW);
           	    session.setAttribute("SHOW_OTHERS_DIV", "SHOW_OTHERS_DIV");
				session.setAttribute(Constants.IS_CREATE_SHOW, "true");
				//session.setAttribute("CREATE_GRID_DIV", "CREATE_GRID_DIV");
				session.removeAttribute("CREATE_DIV");
				session.removeAttribute("VIEW_PATRON_DIV");
				
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting state/secretquestion information :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.removeAttribute("VIEW_GRID_DIV");
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Action to populate list of calls made by the selected user
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward populateLogViewDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "populateLogViewDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for users log Account");
		HttpSession session = request.getSession(true);
		String returnValue = "loggingCallsHistory";
		LoggingCallsForm loggingCallsForm = (LoggingCallsForm) form;
		LogManagement logManagement = new LogManagement();
		String parentPatronId = "";
		String patronTypeId = "";
		String emailId="";
		String patronType="";
		String displayPatronId = "";
		BcwtLogDTO bcwtLogDTO = null;
		BcwtPatronDTO bcwtPatronDTO=null;
		List viewList=new ArrayList();
		String userType="";
		try {
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				parentPatronId = bcwtPatronDTO.getPatronid().toString();
			}
			
			loggingCallsForm.setHiddenLogType(Constants.IS);
			if(request.getParameter("patronId")!=null && 
					! Util.isBlankOrNull(request.getParameter("patronId"))){
				displayPatronId=request.getParameter("patronId");
				loggingCallsForm.setHiddenPatronId(displayPatronId);
			}else{
				displayPatronId = loggingCallsForm.getHiddenPatronId();
			}
			
			
			if(request.getParameter("patronTypeId")!=null && 
					! Util.isBlankOrNull(request.getParameter("patronTypeId"))){
				patronTypeId=request.getParameter("patronTypeId");
				loggingCallsForm.setHiddenPatronTypeId(patronTypeId);
			}else{
				patronTypeId = loggingCallsForm.getHiddenPatronTypeId();
			}
			
			
			if(request.getParameter("emailId")!=null && 
					! Util.isBlankOrNull(request.getParameter("emailId"))){
				emailId=request.getParameter("emailId");
				loggingCallsForm.setHiddenEmailId(emailId);
			}else{
				emailId = loggingCallsForm.getHiddenEmailId();
			}			
			
			if(Util.equalsIgnoreCase(patronTypeId, Constants.IS)){
				userType = Constants.IS;
			}
			
			if(Util.equalsIgnoreCase(patronTypeId, Constants.GBS_ADMIN)
					|| Util.equalsIgnoreCase(patronTypeId, Constants.GBS_READONLY) 
					|| Util.equalsIgnoreCase(patronTypeId, Constants.GBS_SUPER_ADMIN)){
				userType = Constants.GBS_SUPER_ADMIN;
			}
			
			
			
			List viewDetailsList=logManagement.populateLogViewDetails
				(displayPatronId,patronTypeId,emailId,userType);		
			//request.setAttribute("LoggingCallsForm", loggingCallsForm);
			session.removeAttribute(Constants.VIEW_USER_DETAILS);
			//request.setAttribute(Constants.VIEW_USER_DETAILS, viewDetailsList);
			session.setAttribute(Constants.VIEW_USER_DETAILS, viewDetailsList);			
			session.setAttribute("VIEW_GRID_DIV", "VIEW_GRID_DIV");
			session.removeAttribute("VIEW_PATRON_DIV");
			
			//session.removeAttribute("CREATE_DIV");			
			//session.removeAttribute("VIEW_PATRON_DIV");
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_LOGGING_CALL_HISTORY);
	
		} catch (MartaException e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);

	}
	
	public ActionForward populatePartnerViewDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "populatePartnerViewDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for users Partner Account");
		HttpSession session = request.getSession(true);
		String returnValue = "loggingCallsHistory";
		LoggingCallsForm loggingCallsForm = (LoggingCallsForm) form;
		LogManagement logManagement = new LogManagement();
		String patronId = "";
		String patronTypeId = "";
		String emailId="";
		String patronType="";
		String displayPatronId = "";
		BcwtLogDTO bcwtLogDTO = null;
		BcwtPatronDTO bcwtPatronDTO=null;
		List viewList=null;
		try {
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId = bcwtPatronDTO.getPatronid().toString();
			}
			
			if(request.getParameter("patronId")!=null && 
					! Util.isBlankOrNull(request.getParameter("patronId"))){
				displayPatronId=request.getParameter("patronId");
				loggingCallsForm.setHiddenPatronId(displayPatronId);
			}else{
				displayPatronId = loggingCallsForm.getHiddenPatronId();
			}
			
			if(request.getParameter("patronTypeId")!=null && 
					! Util.isBlankOrNull(request.getParameter("patronTypeId"))){
				patronTypeId=request.getParameter("patronTypeId");
				loggingCallsForm.setHiddenPatronTypeId(patronTypeId);
			}else{
				patronTypeId = loggingCallsForm.getHiddenPatronTypeId();
			}
			
			if(request.getParameter("emailId")!=null && 
					! Util.isBlankOrNull(request.getParameter("emailId"))){
				emailId=request.getParameter("emailId");
				loggingCallsForm.setHiddenEmailId(emailId);
			}else{
				emailId = loggingCallsForm.getHiddenEmailId();
			}
			loggingCallsForm.setHiddenLogType(Constants.SUPER_ADMIN);
			List viewDetailsList = null;
			if(!Util.isBlankOrNull(displayPatronId)) {				
				viewDetailsList = logManagement.populatePartnerViewDetails(displayPatronId,patronTypeId,emailId);
			}
			
			session.removeAttribute(Constants.VIEW_USER_DETAILS);			
			//request.setAttribute(Constants.VIEW_PARTNER_USER_DETAILS, viewDetailsList);
			if(viewDetailsList != null) {
				session.setAttribute(Constants.VIEW_USER_DETAILS, viewDetailsList);
			} 
			//session.removeAttribute("VIEW_GRID_DIV");
			session.setAttribute("VIEW_GRID_DIV", "VIEW_GRID_DIV");
			session.removeAttribute("CREATE_DIV");			
			session.removeAttribute("VIEW_PATRON_DIV");
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_LOGGING_CALL_HISTORY);
	
		} catch (MartaException e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);

	}

	public ActionForward addLogPatronDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "addLogPatronDetails: ";
		BcwtsLogger.debug(MY_NAME + "add log patron details");
		HttpSession session = request.getSession(true);
		LoggingCallsForm loggingCallsForm = (LoggingCallsForm) form;
		String returnValue = "loggingCalls";
		BcwtPatronDTO logDTOInSession = null;
		Long patronId = new Long(0);
		Long patronTypeId = new Long(0);
		Long adminpatronTypeId=new Long(0);
		Long adminpatronId=new Long(0);
		String checkPatronTypeId = "";
		String patronIdStr = "";
		BcwtLogDTO bcwtLogDTO = new BcwtLogDTO();
		LogManagement logManagement = new LogManagement();
		BcwtPatronDTO bcwtPatronDTO = null;
		String patronType = null;
		List recentCallLogDetails = new ArrayList();
		String adminEmail = null;
		String adminPhoneNumber = null;
		String adminName = null;
		String fileName = null;
		String archivalFileName = null;
		
		try {
			
			LoggingCallsForm loggingCallsFormFromSession = (LoggingCallsForm) 
								session.getAttribute("loggingCallsForm");
			
			loggingCallsForm.setPatronId(Constants.ZERO);
			checkPatronTypeId = session.getAttribute("PatronTypeID").toString();
			patronIdStr = session.getAttribute("PatronID").toString();
			//signUpForm.setParentpatronid(Constants.MARTA_SUPER_ADMIN );
			if (session.getAttribute(Constants.USER_INFO) != null) {
				logDTOInSession = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId=logDTOInSession.getPatronid();
				patronType = logDTOInSession.getBcwtusertypeid().toString();
				adminEmail = logDTOInSession.getEmailid();
				adminPhoneNumber = logDTOInSession.getPhonenumber();
				adminName = logDTOInSession.getFirstname() +" "+ logDTOInSession.getLastname();
			}
			
			if(checkPatronTypeId!=null){
				bcwtLogDTO.setPatronTypeId(checkPatronTypeId);
			}
			
			if(patronIdStr!=null){
				bcwtLogDTO.setPatronId(patronIdStr);
			}
			
			if(!Util.isBlankOrNull(adminEmail)){
				bcwtLogDTO.setAdministratorEmail(adminEmail);
			}
			if(!Util.isBlankOrNull(adminPhoneNumber)){
				bcwtLogDTO.setAdministratorPhoneNumber(adminPhoneNumber);
			}
			if(!Util.isBlankOrNull(adminName)){
				bcwtLogDTO.setAdministratorUserName(adminName);
			}
			if(!Util.isBlankOrNull(loggingCallsForm.getReasonForCall())){
				bcwtLogDTO.setReasonForCall(loggingCallsForm.getReasonForCall());
			}			
			if(!Util.isBlankOrNull(loggingCallsForm.getNote())){
				bcwtLogDTO.setNote(loggingCallsForm.getNote());
			}
			if(patronType!=null){
				bcwtLogDTO.setAdminPatronTypeId(patronType);
			}
			if(patronId!=null){
				bcwtLogDTO.setAdminPatronId(patronId.toString());
			}
			
			session.removeAttribute("loggingCallsForm");
			
			if (!Util.isBlankOrNull(loggingCallsForm.getLogFile().getFileName())) {
				FormFile logFile = loggingCallsForm.getLogFile();
				InputStream inputStream = null;
				fileName = logFile.getFileName();
				try {
					inputStream = logFile.getInputStream();
				} catch (Exception e) {					
				}
				
				archivalFileName = getArchivalFileName(fileName);
				BcwtConfigParamsDTO configParamDTO = (BcwtConfigParamsDTO) ConfigurationCache.configurationValues
						.get(Constants.LOG_CALLS_ARCHIVE_DIR);
				String archivalFolder = configParamDTO
						.getParamvalue();
				BcwtsLogger
						.debug(MY_NAME
								+ " saving the word file to archival folder ");
				try {
					File directoryFile = new File(archivalFolder);
					if (!directoryFile.exists()) {
						directoryFile.mkdirs();
					}
					FileUtil.writeToFile(inputStream,
							archivalFolder + File.separator + archivalFileName
									+ ".txt");
				} catch (Exception e) {
					e.printStackTrace();
				}
				BcwtsLogger.debug(MY_NAME
						+ " logged call file saved. File:  "
						+ archivalFolder + archivalFileName
						+ ".xls");
				File archivalFile = new File(archivalFolder + File.separator
						+ archivalFileName + ".txt");
				double fileSize = getFileSize(archivalFile
						.getAbsolutePath());
				BcwtsLogger
						.debug(MY_NAME + "fileSize= " + fileSize);
				try {
					inputStream = new FileInputStream(archivalFile);
				} catch (Exception e) {					
				}
				BcwtsLogger
						.debug(MY_NAME
								+ " processing the batch file, calling the business method ");
			}
			
			if(!Util.isBlankOrNull(fileName)){				
				bcwtLogDTO.setUploadedFileName(fileName);
			}
			
			if(!Util.isBlankOrNull(archivalFileName)){				
				bcwtLogDTO.setSavedFileName(archivalFileName);
			}
			
			boolean addStatus = logManagement.addLogPatron(bcwtLogDTO);
			if(addStatus){
				if(checkPatronTypeId.equalsIgnoreCase(Constants.SUPER_ADMIN) || checkPatronTypeId.equalsIgnoreCase(Constants.ADMIN) 
						|| checkPatronTypeId.equalsIgnoreCase(Constants.READ_ONLY) || checkPatronTypeId.equalsIgnoreCase(Constants.BATCHPROCESS)){					
					recentCallLogDetails=logManagement.getCallLogDetails(Constants.SUPER_ADMIN,patronType);
					request.setAttribute(Constants.LOG_LIST, recentCallLogDetails);
				}if(checkPatronTypeId.equalsIgnoreCase(Constants.IS)){					
					recentCallLogDetails=logManagement.getCallLogDetails(Constants.IS, patronType);
					request.setAttribute(Constants.LOG_LIST, recentCallLogDetails);
				}if(checkPatronTypeId.equalsIgnoreCase(Constants.GBS_SUPER_ADMIN) || checkPatronTypeId.equalsIgnoreCase(Constants.GBS_ADMIN)
						|| checkPatronTypeId.equalsIgnoreCase(Constants.GBS_READONLY)){					
					recentCallLogDetails=logManagement.getCallLogDetails(Constants.GBS_SUPER_ADMIN, patronType);
					request.setAttribute(Constants.LOG_LIST, recentCallLogDetails);
				}
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.NEW_LOG_CALL_ADDED));
				request.setAttribute(Constants.SUCCESS, "true");
			}else {
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.UNABLE_TO_LOG_NEW_LOG_CALL));
				request.setAttribute(Constants.SUCCESS, "false");
			}
			returnValue = "loggingCalls";
			session.removeAttribute("VIEW_GRID_DIV");
			session.removeAttribute("CREATE_DIV");
			session.removeAttribute("VIEW_PATRON_DIV");
	
		} catch (MartaException e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while adding add log Patron information :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);

	}
	/**
	 * Action to populate user details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward populateViewUserDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "populateViewUserDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for users view log Account");
		HttpSession session = request.getSession(true);
		String returnValue = "loggingCallsHistory";
		LoggingCallsForm loggingCallsForm = (LoggingCallsForm) form;
		LogManagement logManagement = new LogManagement();
		String logcallId = null;
		BcwtLogDTO bcwtLogDTO = null;
		try {
			
			if(null!=request.getParameter("viewPatronId")){
			logcallId = request.getParameter("viewPatronId").toString();
			}
			/*
			if(patronId!=null){
				session.setAttribute("unlockPatronId", patronId);
			}
			if(session.getAttribute("unlockPatronId")!=null){
				patronId = (String) session.getAttribute("unlockPatronId");
			}*/
			
			bcwtLogDTO = logManagement.populateViewLogUserDetails(logcallId);
			
			/*if(!Util.isBlankOrNull(bcwtLogDTO.getPatronId())){
				loggingCallsForm.setPatronId(bcwtLogDTO.getPatronId());
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getDateOfCall())){
				loggingCallsForm.setDateOfCall(bcwtLogDTO.getDateOfCall());
			}
			if(!Util.isBlankOrNull(bcwtLogDTO.getTimeStampForCall())){
				loggingCallsForm.setTimeStampForCall(bcwtLogDTO.getTimeStampForCall());
			}
			if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorEmail())){
				loggingCallsForm.setAdministratorEmail(bcwtLogDTO.getAdministratorEmail());
			}
			if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorPhoneNumber())){
				loggingCallsForm.setAdministratorPhoneNumber(bcwtLogDTO.getAdministratorPhoneNumber());
			}
			if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorUserName())){
				loggingCallsForm.setAdministratorUserName(bcwtLogDTO.getAdministratorUserName());
			}
			if(!Util.isBlankOrNull(bcwtLogDTO.getReasonForCall())){
				loggingCallsForm.setReasonForCall(bcwtLogDTO.getReasonForCall());
			}
			if(!Util.isBlankOrNull(bcwtLogDTO.getNote())){
				loggingCallsForm.setNote(bcwtLogDTO.getNote());
			}*/
			session.setAttribute(Constants.VIEW_SHOW_USER_DETAILS, bcwtLogDTO);
			request.setAttribute(Constants.VIEW_SHOW_USER_DETAILS, bcwtLogDTO);
			//session.removeAttribute(Constants.VIEW_SHOW_USER_DETAILS);
			
			//returnValue = "loggingCalls";
			session.removeAttribute("CREATE_DIV");
			//session.setAttribute("CREATE_DIV","CREATE_DIV");
			session.setAttribute("VIEW_PATRON_DIV", "VIEW_PATRON_DIV");
			//session.removeAttribute("VIEW_GRID_DIV");
			
			
			
		} catch (MartaException e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying view log page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);

	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward showCreateDiv(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "showCreateDiv: ";
		BcwtsLogger.debug(MY_NAME + "");
		HttpSession session = request.getSession(true);
		String returnValue = "loggingCalls";
		String patronTypeId = "";
		String logInPatronType = "";
		String patronId = "";
		BcwtPatronDTO bcwtPatronDTO = null;
		String adminName = "";
		LoggingCallsForm loggingCallsForm = (LoggingCallsForm) form;
		try {
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId = bcwtPatronDTO.getPatronid().toString();
				logInPatronType = bcwtPatronDTO.getBcwtusertypeid().toString();
			}
			if(!Util.isBlankOrNull(bcwtPatronDTO.getFirstname())){
				adminName = bcwtPatronDTO.getFirstname();
			}
			
			if(!Util.isBlankOrNull(bcwtPatronDTO.getLastname())){				
				adminName = adminName + " " +bcwtPatronDTO.getLastname();
			}
			
			if(!Util.isBlankOrNull(adminName)){
				loggingCallsForm.setAdministratorUserName(adminName);
			}
			if(!Util.isBlankOrNull(bcwtPatronDTO.getEmailid())){
				loggingCallsForm.setAdministratorEmail(bcwtPatronDTO.getEmailid());
			}
			
			if(!Util.isBlankOrNull(bcwtPatronDTO.getBcwtusertypeid().toString())){
				loggingCallsForm.setAdminpatronTypeId(bcwtPatronDTO.getBcwtusertypeid().toString());
			}
			
			if(!Util.isBlankOrNull(bcwtPatronDTO.getPhonenumber())){
				loggingCallsForm.setAdministratorPhoneNumber(bcwtPatronDTO.getPhonenumber());
			}
			
			if(!Util.isBlankOrNull(bcwtPatronDTO.getPatronid().toString())){
				loggingCallsForm.setAdminpatronId(bcwtPatronDTO.getPatronid().toString());
			}
			
			session.setAttribute("loggingCallsForm", loggingCallsForm);
			
			if(request.getParameter("patronTypeId")!=null && 
					! Util.isBlankOrNull(request.getParameter("patronTypeId"))){
				patronTypeId=request.getParameter("patronTypeId");
			}
			if(request.getParameter("patronId")!=null && 
					! Util.isBlankOrNull(request.getParameter("patronId"))){
				patronId=request.getParameter("patronId");
			}
			
			
			if(!Util.isBlankOrNull(patronTypeId) && !Util.isBlankOrNull(logInPatronType)){
				LogManagement logManagement = new LogManagement();
				if(patronTypeId.equalsIgnoreCase(Constants.SUPER_ADMIN) || patronTypeId.equalsIgnoreCase(Constants.ADMIN) 
						|| patronTypeId.equalsIgnoreCase(Constants.READ_ONLY) || patronTypeId.equalsIgnoreCase(Constants.BATCHPROCESS)){
					
					List partnerDetailsList=logManagement.getCallLogDetails(Constants.SUPER_ADMIN,logInPatronType);
					request.setAttribute(Constants.LOG_LIST, partnerDetailsList);
				}if(patronTypeId.equalsIgnoreCase(Constants.IS)){
					List isUserDetailsList = new ArrayList();
					isUserDetailsList=logManagement.getCallLogDetails(Constants.IS, logInPatronType);
					request.setAttribute(Constants.LOG_LIST, isUserDetailsList);
				}if(patronTypeId.equalsIgnoreCase(Constants.GBS_SUPER_ADMIN) || patronTypeId.equalsIgnoreCase(Constants.GBS_ADMIN)
						|| patronTypeId.equalsIgnoreCase(Constants.GBS_READONLY)){
					List gbsUserDetailsList = new ArrayList();
					gbsUserDetailsList=logManagement.getCallLogDetails(Constants.GBS_SUPER_ADMIN, logInPatronType);
					request.setAttribute(Constants.LOG_LIST, gbsUserDetailsList);
				}
			}
			loggingCallsForm.setPatronTypeId(patronTypeId);
			session.setAttribute("PatronTypeID", patronTypeId);
			session.setAttribute("PatronID", patronId);
			request.setAttribute("LoggingCallsForm", loggingCallsForm);
			session.setAttribute("CREATE_DIV","CREATE_DIV");
			session.removeAttribute("VIEW_PATRON_DIV");
			session.removeAttribute("VIEW_GRID_DIV");
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying view log page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		form.reset(mapping,request);
		return mapping.findForward(returnValue);
	}
	
	public ActionForward displayWelcomeLogCall(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayWelcomeLogCall: ";
		BcwtsLogger.debug(MY_NAME
				+ "display recent call for admin in welcome page");
		HttpSession session = request.getSession(true);
		String returnValue = "welcome";
		LogManagement logManagement = null;
		//String currentDate = null;
		BcwtPatronDTO bcwtPatronDTO = null;
		String patronId = null;
		String patronTypeId=null;
		String emailId=null;
		try {
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId = bcwtPatronDTO.getPatronid().toString();
			}
			logManagement = new LogManagement();
			//currentDate = Util.getCurrentDate();
			List recentList = logManagement.displayRecentLogList(patronId,patronTypeId,emailId);
			session.setAttribute(Constants.RECENT_LOGCALL_LIST_WELCOMEPAGE, recentList);
			returnValue = "welcome";
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while displaying Recent Call in Welcome Page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward closeViewDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "closeViewDetails: ";
		BcwtsLogger.debug(MY_NAME + "");
		HttpSession session = request.getSession(true);
		String returnValue = "loggingCalls";
		try {
			
			//session.setAttribute("CREATE_DIV","CREATE_DIV");
			session.removeAttribute("VIEW_PATRON_DIV");
			//session.removeAttribute("VIEW_GRID_DIV");
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for closing view log page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}

	public ActionForward addNewCall(ActionMapping mapping, 
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException{
		final String MY_NAME = ME + "addLogPatronDetails: ";
		BcwtsLogger.debug(MY_NAME + "add log patron details");
		HttpSession session = request.getSession(true);
		LoggingCallsForm loggingCallsForm = (LoggingCallsForm) form;
		String returnValue = "showNewCall";
		BcwtPatronDTO logDTOInSession = null;
		Long patronId = new Long(0);
		Long patronTypeId = new Long(0);
		Long adminpatronTypeId=new Long(0);
		Long adminpatronId=new Long(0);
		String checkPatronTypeId = "";
		String patronIdStr = "";
		BcwtLogDTO bcwtLogDTO = new BcwtLogDTO();
		LogManagement logManagement = new LogManagement();
		BcwtPatronDTO bcwtPatronDTO = null;
		String patronType = null;
		
		try {
			
			LoggingCallsForm loggingCallsFormFromSession = (LoggingCallsForm) 
								session.getAttribute("loggingCallsForm");
			
			//loggingCallsForm.setPatronId(Constants.ZERO);
			//checkPatronTypeId = session.getAttribute("PatronTypeID").toString();
			//patronIdStr = session.getAttribute("PatronID").toString();
			
			//signUpForm.setParentpatronid(Constants.MARTA_SUPER_ADMIN );
			if (session.getAttribute(Constants.USER_INFO) != null) {
				logDTOInSession = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId=logDTOInSession.getPatronid();
				patronType = logDTOInSession.getBcwtusertypeid().toString();
				}
			loggingCallsForm.setAdminpatronTypeId(logDTOInSession.getBcwtusertypeid().toString());
			loggingCallsForm.setAdminpatronId(logDTOInSession.getPatronid().toString());
			loggingCallsForm.setPatronTypeId(logDTOInSession.getBcwtusertypeid().toString());
			loggingCallsForm.setPatronId(logDTOInSession.getPatronid().toString());
			session.setAttribute("loggingCallsForm", loggingCallsForm);
			
		  /*if(checkPatronTypeId!=null){
				bcwtLogDTO.setPatronTypeId(checkPatronTypeId);
			}
			
			if(patronIdStr!=null){
				bcwtLogDTO.setPatronId(patronIdStr);
			}*/
			
			if(!Util.isBlankOrNull(loggingCallsForm.getAdministratorEmail())){

				bcwtLogDTO.setAdministratorEmail(loggingCallsForm.getAdministratorEmail());

			}
			if(!Util.isBlankOrNull(loggingCallsForm.getAdministratorPhoneNumber())){

				bcwtLogDTO.setAdministratorPhoneNumber(loggingCallsForm.getAdministratorPhoneNumber());

			}
			if(!Util.isBlankOrNull(loggingCallsForm.getAdministratorUserName())){

				bcwtLogDTO.setAdministratorUserName(loggingCallsForm.getAdministratorUserName());

			}
			if(!Util.isBlankOrNull(loggingCallsForm.getPatronTypeId())){
				bcwtLogDTO.setPatronTypeId(loggingCallsForm.getPatronTypeId());				
			}
			if(!Util.isBlankOrNull(loggingCallsForm.getPatronId())){
				bcwtLogDTO.setPatronId(loggingCallsForm.getPatronId());				
			}

			if(!Util.isBlankOrNull(loggingCallsForm.getReasonForCall())){

				bcwtLogDTO.setReasonForCall(loggingCallsForm.getReasonForCall());

			}
		
			if (!Util.isBlankOrNull(loggingCallsForm.getDateOfCall())) {

				bcwtLogDTO.setDateOfCall(loggingCallsForm
								.getDateOfCall());
			}	
			
			
			if (!Util.isBlankOrNull(loggingCallsForm.getTimeStampForCall())) {

				bcwtLogDTO.setTimeStampForCall(loggingCallsForm
								.getTimeStampForCall());

			}
			if(!Util.isBlankOrNull(loggingCallsForm.getNote())){

				bcwtLogDTO.setNote(loggingCallsForm.getNote());
			}
			if(!Util.isBlankOrNull(loggingCallsForm.getUserName())){

				bcwtLogDTO.setUserName(loggingCallsForm.getUserName());
			}
			if(!Util.isBlankOrNull(loggingCallsForm.getUserType())){

				bcwtLogDTO.setUserType(loggingCallsForm.getUserType());
			}
			if(logDTOInSession != null && !Util.isBlankOrNull(logDTOInSession.getBcwtusertypeid().toString())){

				bcwtLogDTO.setAdminPatronTypeId(logDTOInSession.getBcwtusertypeid().toString());
			}
			if(logDTOInSession != null && !Util.isBlankOrNull(logDTOInSession.getPatronid().toString())){

				bcwtLogDTO.setAdminPatronId(logDTOInSession.getPatronid().toString());
			}
			
			session.removeAttribute("loggingCallsForm");
			bcwtLogDTO.setTimeStampForCall(String.valueOf(Util.getCurrentDateAndTime()));
			bcwtLogDTO.setDateOfCall(String.valueOf(Util.getCurrentDateAndTime()));
			
			boolean addStatus = logManagement.getaddNewCall(bcwtLogDTO);
			if (addStatus) {
				
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.USER_ADD_SUCESS_MESAAGE));
				request.setAttribute(Constants.SUCCESS, "true");
			} else {
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.PATRON_ADD_FAILURE_MESAAGE));
			}
			request.setAttribute("USER_EMAIL", loggingCallsForm.getEmailid());
			returnValue = "showNewCall";
	
		} catch (MartaException e) {
			
			BcwtsLogger.error(MY_NAME
					+ " Exception while adding  new Call :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);

	}
	
	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayNewLogCallPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayLogPage: ";
		String returnValue = "showNewCall";
		LoggingCallsForm loggingCallsForm = (LoggingCallsForm) form;
		HttpSession session = request.getSession(true);
		BcwtPatronDTO logDTOInSession = null;
		Long patronId = new Long(0);
		Long patronTypeId = new Long(0);
		Long adminpatronTypeId=new Long(0);
		Long adminpatronId=new Long(0);
		String checkPatronTypeId = "";
		String patronIdStr = "";
		BcwtLogDTO bcwtLogDTO = new BcwtLogDTO();
		LogManagement logManagement = new LogManagement();
		BcwtPatronDTO bcwtPatronDTO = null;
		String patronType = null;
		try {
			if (session.getAttribute(Constants.USER_INFO) != null) {
				logDTOInSession = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId=logDTOInSession.getPatronid();
				patronType = logDTOInSession.getBcwtusertypeid().toString();
				//patronTypeId = logDTOInSession
				}
			loggingCallsForm.setAdministratorEmail(logDTOInSession.getEmailid());
			loggingCallsForm.setAdministratorPhoneNumber(logDTOInSession.getPhonenumber());
			loggingCallsForm.setAdministratorUserName(logDTOInSession.getEmailid());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mapping.findForward(returnValue);
	}
	
	
	
	/**
	 * To check for the actions
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward checkAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		String returnValue = null;
		String logType = null;
		ActionForward actionForward = null;
		LoggingCallsForm loggingCallsForm = (LoggingCallsForm) form;
		try {
			if(!Util.isBlankOrNull(loggingCallsForm.getHiddenLogType())){
				logType = loggingCallsForm.getHiddenLogType();
			}
			if(!Util.isBlankOrNull(logType)){
				if(Util.equalsIgnoreCase(logType, Constants.SUPER_ADMIN)){
					actionForward = populatePartnerViewDetails(mapping, form, request, response);
				}else{
					actionForward = populateLogViewDetails(mapping, form, request, response);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}
	
	
	/**
	 * Generate document for log call
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward generateDocument(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "generateDocument: ";
		BcwtsLogger.debug(MY_NAME
				+ "to generate document");
		HttpSession session = request.getSession(true);
		LoggingCallsForm loggingCallsForm=(LoggingCallsForm)form;
		String returnValue = "loggingCallsHistory";
		String uploadedFileName = null;
		String savedFileName = null;
		LogManagement logManagement = null;
		
		try {
			BcwtLogDTO bcwtLogDTO = new BcwtLogDTO();
			logManagement = new LogManagement();
			
			if(session.getAttribute(Constants.VIEW_SHOW_USER_DETAILS)!=null){
				bcwtLogDTO = (BcwtLogDTO) session.getAttribute(Constants.VIEW_SHOW_USER_DETAILS);
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getSavedFileName())){
				savedFileName = bcwtLogDTO.getSavedFileName() + ".txt";				
			}
			BcwtConfigParamsDTO configParamDTO = (BcwtConfigParamsDTO) 
			ConfigurationCache.configurationValues.get(Constants.LOG_CALLS_ARCHIVE_DIR);
			String fileLocation = configParamDTO.getParamvalue();;
			StringBuffer contents  = logManagement.generateDocument(savedFileName,fileLocation);
			
		responseGenerate("text/plain", savedFileName, fileLocation, response, contents);
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while generating document :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	
	private void responseGenerate(String contentype, String savedFileName,
			String fileLocation, HttpServletResponse response,
			StringBuffer contents) throws IOException {
		final String MY_NAME = "ResponseGenerate: ";
		BcwtsLogger.info(MY_NAME + "entering into ResponseGenerate method");
		try {
			BufferedInputStream buf = null;
			ServletOutputStream op = response.getOutputStream();
			op = response.getOutputStream();
			File myfile = new File(fileLocation+File.separator + savedFileName);
			response.setContentType(contentype);
			response.addHeader("Content-Disposition", "attachment; filename="
					+ fileLocation+File.separator + savedFileName);
			response.setContentLength((int) myfile.length());
			FileInputStream input = new FileInputStream(myfile);
			buf = new BufferedInputStream(input);
			int readBytes = 0;
			while ((readBytes = buf.read()) != -1)
				op.write(readBytes);
			op.close();
			buf.close();
			BcwtsLogger.info(MY_NAME + "response generated");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private String getArchivalFileName(String fileName) {
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.BATCH_PROCESS_ARCHIVAL_FILE_DATE_FORMAT);
		return fileName+"_"+sdf.format(new Date());
	}
	
	private double getFileSize(String filename) {
	    File file = new File(filename);
	    if (!file.exists() || !file.isFile()) {
	      return -1;
	    }
	    //Here we get the actual size
	    return file.length();
	}
	
	
	
	
	
}
	
	
	
	