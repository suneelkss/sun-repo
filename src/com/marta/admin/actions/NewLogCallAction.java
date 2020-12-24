package com.marta.admin.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.marta.admin.business.NewLogCallManagement;
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

/**
 * Action class for new log call
 * @author Raj
 *
 */
public class NewLogCallAction extends DispatchAction {
	final String ME = "NewLogCallAction: ";
	
	
	/**
	 * Action to display new log call page
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
		final String MY_NAME = ME + "displayNewLogCallPage: ";
		BcwtsLogger.debug(MY_NAME
				+ "getting new log page");
		LoggingCallsForm loggingCallsForm = (LoggingCallsForm) form;
		HttpSession session = request.getSession(true);
		String returnValue = "newLogCall";		
		try {
			String adminName = "";
			if(session.getAttribute(Constants.USER_INFO)!=null){
				BcwtPatronDTO bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				
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
				if(!Util.isBlankOrNull(bcwtPatronDTO.getPhonenumber())){
					loggingCallsForm.setAdministratorPhoneNumber(bcwtPatronDTO.getPhonenumber());
				}
			}
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_NEW_LOG_CALL);
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while displaying new call log page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		form.reset(mapping,request);
		return mapping.findForward(returnValue);
	}
	
	
	/**
	 * Action to add new log details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward addNewLogCall(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "addNewLogCall: ";
		BcwtsLogger.debug(MY_NAME
				+ "adding new log call details");
		LoggingCallsForm loggingCallsForm = (LoggingCallsForm) form;
		HttpSession session = request.getSession(true);
		String returnValue = "";
		BcwtPatronDTO bcwtPatronDTO = null;		
		try {
			String adminName = "";		 
			String callerUserType = "";
			String callerUserName = "";
			boolean isAdded = false;
			String fileName = "";
			String archivalFileName = "";
			
			NewLogCallManagement newLogCallManagement = new NewLogCallManagement();
			
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
			}
			
			if(Util.equalsIgnoreCase(bcwtPatronDTO.getEmailid(), loggingCallsForm.getUserName())){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.ADD_NEW_LOG_ERROR_MESSAGE));
				request.setAttribute(Constants.SUCCESS, "false");
				returnValue = "newLogCall";
			}
			
			if(!Util.isBlankOrNull(loggingCallsForm.getUserName())){				
				callerUserName = loggingCallsForm.getUserName();
			}
			
			if(!Util.isBlankOrNull(loggingCallsForm.getUserType())){				
				callerUserType = loggingCallsForm.getUserType();
			}
			
			if(Util.isBlankOrNull(returnValue)){
				
				BcwtLogDTO resultBcwtLogDTO = newLogCallManagement.getCallerDetails(callerUserName,callerUserType);
				if(resultBcwtLogDTO.getPatronId()==null){
					returnValue = "newLogCall";
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.INVALID_USER_NAME));
					request.setAttribute(Constants.SUCCESS, "false");
					return mapping.findForward(returnValue);
				}
				if(!Util.isBlankOrNull(resultBcwtLogDTO.getUserType())){
					if(Util.equalsIgnoreCase(loggingCallsForm.getUserType(), resultBcwtLogDTO.getUserType())){
						
						if(!Util.isBlankOrNull(bcwtPatronDTO.getEmailid())){
							resultBcwtLogDTO.setAdministratorEmail(bcwtPatronDTO.getEmailid());
						}
						
						if(null!=bcwtPatronDTO.getPatronid()){
							resultBcwtLogDTO.setAdminPatronId(bcwtPatronDTO.getPatronid().toString());
						}
						
						if(!Util.isBlankOrNull(bcwtPatronDTO.getRole())){
							resultBcwtLogDTO.setAdminPatronTypeId(bcwtPatronDTO.getRole());
						}
						
						if(null!=bcwtPatronDTO.getPhonenumber()){
							resultBcwtLogDTO.setAdministratorPhoneNumber(bcwtPatronDTO.getPhonenumber());
						}
						
						if(!Util.isBlankOrNull(bcwtPatronDTO.getFirstname())){
							adminName = bcwtPatronDTO.getFirstname();
						}
						
						if(!Util.isBlankOrNull(bcwtPatronDTO.getLastname())){				
							adminName = adminName + " " +bcwtPatronDTO.getLastname();
						}
						
						if(!Util.isBlankOrNull(adminName)){
							resultBcwtLogDTO.setAdministratorUserName(adminName);
						}
						
						if(!Util.isBlankOrNull(loggingCallsForm.getNote())){				
							resultBcwtLogDTO.setNote(loggingCallsForm.getNote());
						}
						
						if(!Util.isBlankOrNull(loggingCallsForm.getReasonForCall())){				
							resultBcwtLogDTO.setReasonForCall(loggingCallsForm.getReasonForCall());
						}
						
						if(!Util.isBlankOrNull(loggingCallsForm.getUserName())){				
							resultBcwtLogDTO.setUserName(loggingCallsForm.getUserName());
						}
						
						if(!Util.isBlankOrNull(loggingCallsForm.getUserType())){				
							resultBcwtLogDTO.setUserType(loggingCallsForm.getUserType());
						}
						
						if(!Util.isBlankOrNull(loggingCallsForm.getDateOfCall())){				
							resultBcwtLogDTO.setDateOfCall(loggingCallsForm.getDateOfCall());
						}	
						resultBcwtLogDTO.setTimeStampForCall(String.valueOf(Util.getCurrentDateAndTime()));
						
						
						if (!Util.isBlankOrNull(loggingCallsForm.getLogFile().getFileName())) {
							FormFile logFile = loggingCallsForm.getLogFile();
							fileName = logFile.getFileName();
							InputStream inputStream = logFile.getInputStream();
							archivalFileName = getArchivalFileName(fileName);
							BcwtConfigParamsDTO configParamDTO = (BcwtConfigParamsDTO) ConfigurationCache.configurationValues
									.get(Constants.LOG_CALLS_ARCHIVE_DIR);
							String archivalFolder = configParamDTO
									.getParamvalue();
							System.out.println("Logged Call Dir: "+configParamDTO
									.getParamvalue());
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
									+ ".txt");
							File archivalFile = new File(archivalFolder + File.separator
									+ archivalFileName + ".txt");						
							inputStream = new FileInputStream(archivalFile);							
							BcwtsLogger
									.debug(MY_NAME
											+ " processing the batch file, calling the business method ");
						}						
						if(!Util.isBlankOrNull(fileName)){				
							resultBcwtLogDTO.setUploadedFileName(fileName);
						}
						
						if(!Util.isBlankOrNull(archivalFileName)){				
							resultBcwtLogDTO.setSavedFileName(archivalFileName);
						}
						isAdded = newLogCallManagement.addLogCall(resultBcwtLogDTO);
						
						if(isAdded){
							request.setAttribute(Constants.MESSAGE, PropertyReader
									.getValue(Constants.NEW_LOG_CALL_ADDED));
							request.setAttribute(Constants.SUCCESS, "true");
						}else{
							request.setAttribute(Constants.MESSAGE,PropertyReader
									.getValue(Constants.UNABLE_TO_LOG_NEW_LOG_CALL));
							request.setAttribute(Constants.SUCCESS, "true");
						}
						
					}else{
						returnValue = "newLogCall";
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.INVALID_USER_TYPE));
						request.setAttribute(Constants.SUCCESS, "false");
						return mapping.findForward(returnValue);
					}
				}
				returnValue = "newLogCall";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while adding new call log:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}		
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Display log call page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayLogPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayLogPage: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for logging calls page");
		HttpSession session = request.getSession(true);
		LoggingCallsForm loggingCallsForm=(LoggingCallsForm)form;
		String returnValue = null;
		try {
			Long roleId = new Long(0);
			loggingCallsForm.setLogType(Constants.IS);		
			loggingCallsForm.setHiddenLogType(Constants.IS);
			if(session.getAttribute(Constants.USER_INFO)!=null){
				BcwtPatronDTO bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				roleId = bcwtPatronDTO.getBcwtusertypeid();
			}
			returnValue = "loggingCalls";
			LogManagement logManagement = new LogManagement();
			
			List logDetailsList = logManagement.getCallLogDetails(Constants.IS,roleId.toString());
			session.setAttribute(Constants.LOG_LIST, logDetailsList);			
			session.setAttribute("SHOW_OTHERS_DIV","SHOW_OTHERS_DIV");
			session.removeAttribute("SHOW_DATA_GRID");
			session.removeAttribute("CREATE_DIV");
			session.removeAttribute(Constants.IS_CREATE_SHOW);
			session.removeAttribute("VIEW_GRID_DIV");
			session.removeAttribute("VIEW_PATRON_DIV");
			session.setAttribute(Constants.LOGGING_CALLS, returnValue);
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
	 * Method to get archival file name
	 * @param fileName
	 * @return
	 */
	private String getArchivalFileName(String fileName) {
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.BATCH_PROCESS_ARCHIVAL_FILE_DATE_FORMAT);
		return fileName+"_"+sdf.format(new Date());
	}
	
}