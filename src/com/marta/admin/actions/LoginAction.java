package com.marta.admin.actions;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.marta.admin.business.AssociatePartnersBussiness;
import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.business.CreateManualAlertManagement;
import com.marta.admin.business.LogManagement;
import com.marta.admin.business.ManageMenus;
import com.marta.admin.business.ManagePartnerRegistrationBusiness;
import com.marta.admin.business.PatronManagement;
import com.marta.admin.business.ReportManagement;
import com.marta.admin.business.RequestToHotList;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtReportDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.LoginForm;
import com.marta.admin.utils.Base64EncodeDecodeUtil;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.SendMail;
import com.marta.admin.utils.Util;

import com.marta.admin.business.UnlockAccount;

public class LoginAction extends Action {

	final String ME = "LoginAction: ";

	int loginCount = 0;

	int loginCountDb = 0;

	String isLocked = "no";

	String lastLogin = "";

	PatronManagement patronManagement = new PatronManagement();

	
	/**
	 * Method to login the users
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws MartaException {
		final String MY_NAME = ME + "execute: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		LoginForm loginValues = (LoginForm) form;
		String emailId = loginValues.getEmail();
		String password = loginValues.getPassword();

		ActionMessages message = new ActionMessages();
		Long patronId = new Long(0);
		String passwordDb = "";
		String activeStatus = "";
		String returnValue = "";
		List regionList = new ArrayList();
		HttpSession session = request.getSession(true);
		String currentDate = null; 
		String patronTypeId=null;
		//String emailId=null;
		CreateManualAlertManagement alertManagement = null;
		List alertList = null;
		LogManagement logManagement=null;
		List recentList = null;
		AssociatePartnersBussiness associatePartnersBussiness=null;
		List searchList=null;
		ManagePartnerRegistrationBusiness managePartnerRegistrationBusiness = null;
		List welcomePartnerDetailsList = null;
		UnlockAccount unlockAccount = null;
		RequestToHotList requestToHotList = null;
		//List recentlyLockedAccountsList = null ;
		List lockedList=null;
		List recentlyHotListCardList = null;
		
		try {
			BcwtsLogger.debug(MY_NAME + "get Patron information with emailId "
					+ emailId);
			if (null != emailId && null != password) {
				BcwtPatronDTO bcwtPatronDTO = patronManagement
						.getPatronDetails(emailId);
				if (null != bcwtPatronDTO) {

					patronId = bcwtPatronDTO.getPatronid();
					patronTypeId = bcwtPatronDTO.getBcwtusertypeid().toString();
					if (bcwtPatronDTO.getPatronid() != null) {
						activeStatus = bcwtPatronDTO.getActivestatus();
						isLocked = bcwtPatronDTO.getLockstatus();
						// To check the user activestatus
						if (!Util.isBlankOrNull(activeStatus)
								&& activeStatus
										.equalsIgnoreCase(Constants.ACTIVE_STATUS)) {

							// //To check the user lock status
							if (!Util.isBlankOrNull(isLocked)
									&& isLocked.equals(Constants.NO)) {

								// To check the given password with dbpassword
								passwordDb = Base64EncodeDecodeUtil
										.decodeString(bcwtPatronDTO
												.getPatronpassword());
								loginCountDb = bcwtPatronDTO.getLogincount()
										.intValue();
								if (!Util.isBlankOrNull(passwordDb)
										&& !Util.isBlankOrNull(password)
										&& password.equals(passwordDb)) {
									// Validate the contex map for the user
									returnValue = validateContextMap(session,
											request, response, patronId);
									if (!returnValue.equals("loginSuccess")) {
										return mapping.findForward(returnValue);
									}
									// set USER_INFO TO SESSION
									session.setAttribute(Constants.USER_INFO,
											bcwtPatronDTO);
									
									//code for Manage Partner Registration Request by Selvi starts here
									
									 managePartnerRegistrationBusiness = new ManagePartnerRegistrationBusiness();
									 welcomePartnerDetailsList = managePartnerRegistrationBusiness
																.getWelcomePartnerRegistrationListDetails();
									 session.setAttribute(Constants.PARTNER_REGISTRATION_REQUEST_WELCOME,
																welcomePartnerDetailsList);
									//code for Manage Partner Registration Request by Selvi ends here
									
									// Added Order Summary in Welcome page - senthil raja
									List ordersCountList = getOrderCount();
									session.setAttribute(Constants.ORDERS_COUNT_LIST, ordersCountList);
									
									//code for Manual alerts display by maheswari starts here
									currentDate = Util.getCurrentDate();
									alertManagement = new CreateManualAlertManagement();
									alertList = alertManagement.displayAlerts(patronId.toString(), currentDate);
									session.setAttribute(Constants.ALERT_MESSAGE_LIST_WELCOMEPAGE, alertList);
									//code for Manual alerts display by maheswari ends here
									
									//code for Recently Locked Accounts List by Jagadeesan Starts Here
									//recentlyLockedAccountsList = new ArrayList();
									//unlockAccount = new UnlockAccount();
									//recentlyLockedAccountsList = unlockAccount.getRecentlyLockedAccounts();
									//session.setAttribute(Constants.LOCKED_USER_DETAILS, recentlyLockedAccountsList);
									//code for Recently Locked Accounts List by Jagadeesan Ends Here
									
//									//code for Logging Calls display by Sowjanya starts here
									//currentDate = Util.getCurrentDate();
									
									if(Util.equalsIgnoreCase(bcwtPatronDTO.getRole(), Constants.MARTA_SUPER_ADMIN)
										|| Util.equalsIgnoreCase(bcwtPatronDTO.getRole(), Constants.MARTA_SUPER_ADMIN)){
										logManagement = new LogManagement();
										recentList = logManagement.displayRecentLogList(patronId.toString(),patronTypeId,emailId);
										session.setAttribute(Constants.RECENT_LOGCALL_LIST_WELCOMEPAGE, recentList);
									}
									
									//code for Logging Calls display by Sowjanya ends here
									
									//code for Locked Calls display by Sowjanya starts here
									unlockAccount = new UnlockAccount();
									lockedList = unlockAccount.displayLockedCalls(patronTypeId.toString());
									session.setAttribute(Constants.LOCKED_LIST_WELCOMEPAGE, lockedList);
									//code for Locked Calls display by Sowjanya ends here
									
									//code for recently hotlisting card List by jagadeesan starts here
									requestToHotList = new RequestToHotList();
									recentlyHotListCardList = requestToHotList.getRecentlyHotlistCardList();
									session.setAttribute(Constants.RECENT_HOTLIST_CARDLIST_WELCOMEPAGE, recentlyHotListCardList);
									//code for recently hotlisting card List by jagadeesan ends here
									
//									//code for Partner Search Fields display by Subba Reddy starts here
									//currentDate = Util.getCurrentDate();
									associatePartnersBussiness = new AssociatePartnersBussiness();
									searchList = associatePartnersBussiness.displayPartnerSearchList(patronId.toString(),patronTypeId,emailId);
									session.setAttribute(Constants.PARTNER_SEARCH_LIST_WELCOMEPAGE, searchList);
									//code for Partner Search Fields display by Subba Reddy ends here
									// set region to session
									session
											.setAttribute(
													Constants.REGIONDTOLIST,
													regionList);
									returnValue = updateAndGetCardDetails(
											bcwtPatronDTO, session, request
													.getContextPath());
									//GET secretQuestionAnswer for GBS user
									boolean isGBSUserWithoutSecretQuestion = patronManagement
									.getSecurityQuestionDetails(patronId);
									if(!isGBSUserWithoutSecretQuestion){
										session.setAttribute("SHOW_SECRET_QUESTION", "true");
										List secretQuestionList = patronManagement.getSecretQuestions("");										
										session.setAttribute(Constants.SECRET_QUESTIONS_LIST,
												secretQuestionList);
									}

								} else {
									// insert logincount
									
									 if(bcwtPatronDTO.getRole().equalsIgnoreCase(Constants.MARTA_SUPREME_ADMIN)||
											 bcwtPatronDTO.getRole().equalsIgnoreCase(Constants.MARTA_IT_ADMIN))
									 {
										 patronManagement
											.updatePatronDetails(bcwtPatronDTO);
									message
											.add(
													"Invalid user",
													new ActionMessage(
															"BCWTS_PATRON_LOGIN_SUPREM_ADMIN_FAILUR_MESSAGE"));
									saveMessages(request, message);
									request
											.setAttribute(
													Constants.MESSAGE,
													PropertyReader
															.getValue(Constants.BCWTS_PATRON_LOGIN_SUPREM_ADMIN_FAILUR_MESSAGE));
									BcwtsLogger
											.info("Password does not match with the DB ");
									returnValue = "loginFailure";
										 
									 }
									 else
									 {
									 
									 
									 
									//String checkPatronTypeId = bcwtPatron.getBcwtpatrontype().getPatrontypeid().toString();
									//if()
									if (loginCountDb < 3) {
										loginCount = 1 + loginCountDb;
										bcwtPatronDTO
												.setLogincount(new Integer(
														loginCount));
										patronManagement
												.updatePatronDetails(bcwtPatronDTO);
										message
												.add(
														"Invalid user",
														new ActionMessage(
																"BCWTS_PATRON_LOGIN_FAILUR_MESSAGE"));
										saveMessages(request, message);
										request
												.setAttribute(
														Constants.MESSAGE,
														PropertyReader
																.getValue(Constants.BCWTS_PATRON_LOGIN_FAILUR_MESSAGE));
										BcwtsLogger
												.info("Password does not match with the DB ");
										returnValue = "loginFailure";
									} else {
										// lock the user
										String tempPassword = Util.generatePassword(6);
										bcwtPatronDTO.setLockstatus(Constants.YES);
										bcwtPatronDTO.setLogincount(new Integer(0));
										bcwtPatronDTO.setIsautogenerated("yes");
										bcwtPatronDTO.setPatronpassword(Base64EncodeDecodeUtil.encodeString(tempPassword));
										patronManagement
												.updatePatronDetails(bcwtPatronDTO);
				/*						String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
										
										ConfigurationCache configurationCache = new ConfigurationCache();
										BcwtConfigParamsDTO smtpPathDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
												.get("SMTP_SERVER_PATH");
										BcwtsLogger.debug(MY_NAME + "smtp path : "
												+ smtpPathDTO.getParamvalue());
										BcwtConfigParamsDTO fromDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
												.get("WEBMASTER_MAIL_ID");
										BcwtsLogger.debug(MY_NAME + "From Id : "
												+ fromDTO.getParamvalue());
										BcwtConfigParamsDTO mailportDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
												.get(Constants.MAIL_PORT);
										BcwtConfigParamsDTO mailprotocolDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
												.get(Constants.MAIL_PROTOCOL);
										String content = PropertyReader
												.getValue(Constants.FORGOT_PWD_MAIL_CONTENT);
										if (null != bcwtPatronDTO
												&& null != bcwtPatronDTO.getFirstname()){
											content = content.replaceAll("\\$USERNAME", bcwtPatronDTO.getFirstname().trim());
										}else{
											content = content.replaceAll("\\$USERNAME", "Subscriber");
										}
										content = content.replaceAll("\\$PASSWORD", tempPassword);					
										String imgPath = basePath+Constants.MARTA_LOGO;
										String imageContent = "<img src='"+imgPath+"'/>";			
										content = content.replaceAll("\\$LOGOIMAGE", imageContent);
										content = content.replaceAll("\\$PWDCONDITION", PropertyReader.getValue(Constants.PASSWORD_CONDITION));
										BcwtsLogger.debug(MY_NAME + "content :" + content);
										String subject = PropertyReader
												.getValue(Constants.FORGOT_PWD_MAIL_SUBJECT);

										BcwtsLogger.debug(MY_NAME + "subject :" + subject
												+ "userEmail :" + emailId);
										try {

											SendMail.sendMail(fromDTO.getParamvalue(), emailId,
													content, smtpPathDTO.getParamvalue(), subject,
													"", mailportDTO.getParamvalue(),
													mailprotocolDTO.getParamvalue());
											BcwtsLogger.info(MY_NAME + "Mail sent sucess");
										} catch (Exception e) {
											e.printStackTrace();
										}
										*/
										// update DB the new Password
										message
												.add(
														"Invalid user",
														new ActionMessage(
																"BCWTS_PATRON_ACCOUNT_LOCKED_AFTER_LOGIN_MESSAGE="));
										saveMessages(request, message);
										request
												.setAttribute(
														Constants.MESSAGE,
														PropertyReader
																.getValue(Constants.PATRON_LOCKEDAFTERLOGIN));
										BcwtsLogger
												.info("Password does not match with the DB ");
										returnValue = "loginFailure";

									}
									 }
								}
							} else {
								// user locked message
								message
										.add(
												"Invalid user",
												new ActionMessage(
														"BCWTS_PATRON_ACCOUNT_ACTIVATED_MESSAGE"));
								saveMessages(request, message);
								request
										.setAttribute(
												Constants.MESSAGE,
												PropertyReader
														.getValue(Constants.PATRON_LOCKMESSAGES));
								BcwtsLogger.info("User is in locked state ");
								returnValue = "loginFailure";
							}
						} else {
							// user is not active
							message.add("Invalid user", new ActionMessage(
									"patron.NotActive"));
							saveMessages(request, message);
							request
									.setAttribute(
											Constants.MESSAGE,
											PropertyReader
													.getValue(Constants.BCWTS_PATRON_ACCOUNT_ACTIVATED_MESSAGE));
							BcwtsLogger.info("User is not activated ");
							returnValue = "loginFailure";
						}
					}
					// }
				} else {
					BcwtsLogger.info("Password does not match with the DB");

					message.add("Invalid user", new ActionMessage(
							"BCWTS_PATRON_EMAIL_INCORRECT_MESSAGE"));
					saveMessages(request, message);
					request
							.setAttribute(
									Constants.MESSAGE,
									PropertyReader
											.getValue(Constants.BCWTS_PATRON_EMAIL_INCORRECT_MESSAGE));
					returnValue = "loginFailure";
				}
			}
//			 Setting the user name into Cookies.
			if(!Util.isBlankOrNull(loginValues.getRememberMe()) 
					&& loginValues.getRememberMe().equals(Constants.YES)){
				
				if (returnValue.equals("loginSuccess")) {
					
					BcwtsLogger.debug(MY_NAME + " Setting the email address "+emailId+" into Cookie");
					
					Cookie emailAddressCookie = new Cookie(Constants.EMAIL_ID, emailId);
					//emailAddressCookie.setDomain(".breezecard.com");	// *.company.com, but not *.web.company.com
					//emailAddressCookie.setPath("/");					// All pages
					response.addCookie(emailAddressCookie);				// Add the cookie to the HTTP headers
					
					BcwtsLogger.info(MY_NAME + " The email address "+emailId+" has been set into Cookie");
				}
				
			}else{
				//Remove the email from cookie.
				BcwtsLogger.debug(MY_NAME + " Removing the email address "+emailId+" from Cookie");
				
				Cookie emailAddressCookie = new Cookie(Constants.EMAIL_ID, "");
				emailAddressCookie.setMaxAge(0); 
				response.addCookie(emailAddressCookie);
				
				BcwtsLogger.info(MY_NAME + " Removed the email address "+emailId+" from Cookie");
			}
			
		} catch (Exception e) {
			BcwtsLogger.error("Error while logging in : " + e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}

	/**
	 * Method to set user details in context
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @param patronId
	 * @return
	 * @throws IOException
	 */
	private String validateContextMap(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			Long patronId) throws IOException {

		ServletContext context = session.getServletContext();
		String returnValue = "loginSuccess";
		if (context.getAttribute(Constants.MARTACONTEXTMAP) == null) {
			context.setAttribute(Constants.MARTACONTEXTMAP, new HashMap());
		}
		Map usersMap = (Map) context.getAttribute(Constants.MARTACONTEXTMAP);

		if (null != usersMap && usersMap.containsKey(patronId)) {
			String oldSessionId = (String) usersMap.get(patronId);
			String newSessionId = session.getId();
			if (!oldSessionId.equals(newSessionId)) {
				request.setAttribute("patronId", patronId);
				request.setAttribute("sessionId", newSessionId);
				returnValue = "sessionClear";
			}
		} else {
			usersMap.put(patronId, session.getId());
			context.setAttribute(Constants.MARTACONTEXTMAP, usersMap);
		}

		// Multiple user login control Ends
		return returnValue;

	}

	/**
	 * Method to update the patron details
	 * 
	 * @param bcwtPatronDTO
	 * @param session
	 * @param contextName
	 * @return
	 * @throws ParseException
	 * @throws IllegalArgumentException
	 * @throws MartaException
	 */
	public String updateAndGetCardDetails(BcwtPatronDTO bcwtPatronDTO,
			HttpSession session, String contextName) throws ParseException,
			IllegalArgumentException, MartaException {

		String returnValue = "";
		boolean isAutoGenerated = false;
		Map stateMap = new HashMap();
		Map menuMap = new HashMap();
		List menuList = null;
		ServletContext context = session.getServletContext();

		// whether the user has autogenerated password or not?
		if (!Util.isBlankOrNull(bcwtPatronDTO.getIsautogenerated())
				&& bcwtPatronDTO.getIsautogenerated().equalsIgnoreCase(
						Constants.YES)) {
			isAutoGenerated = true;
		}

		// set lastlogin
		if (!Util.isBlankOrNull(bcwtPatronDTO.getLastlogintime())) {
			SimpleDateFormat formatterDate = new SimpleDateFormat(
					"MM-dd-yyyy hh:mm:ss");

			/** * MARTA UI - Review Changes (start) ** */

			/*
			 * SimpleDateFormat formatterLogin = new SimpleDateFormat( "EEE, d
			 * MMM yyyy HH:mm:ss");
			 */

			SimpleDateFormat formatterLogin = new SimpleDateFormat(
					"MMM dd, yyyy");

			/** * MARTA UI - Review Changes (end) ** */

			Date currentTime = (Date) formatterDate.parse(bcwtPatronDTO
					.getLastlogintime());
			lastLogin = Util.dateFormatToDisplay(currentTime);
			session.setAttribute(Constants.LAST_LOGIN, lastLogin);
		}

		// insert logincount
		if (loginCountDb != 0) {
			loginCount = 0;
			bcwtPatronDTO.setLogincount(new Integer(loginCount));
			patronManagement.updatePatronDetails(bcwtPatronDTO);
		}
		// get the menus associtaed to the patron type
		ManageMenus manageMenus = new ManageMenus();
		try {
			if (null != context.getAttribute(Constants.MENU_MAP)) {
				menuMap = (HashMap) context.getAttribute(Constants.MENU_MAP);
			}
			if (null != menuMap.get(bcwtPatronDTO.getBcwtusertypeid())) {
				menuList = (ArrayList) menuMap.get(bcwtPatronDTO
						.getBcwtusertypeid());
			} else {
				menuList = manageMenus.getMenusForPatronType(bcwtPatronDTO
						.getBcwtusertypeid(), contextName);
				menuMap.put(bcwtPatronDTO.getBcwtusertypeid(), menuList);
				context.setAttribute(Constants.MENU_MAP, menuMap);

			}
			session.setAttribute(Constants.MENU_LIST, menuList);
		} catch (MartaException e) {
			BcwtsLogger.error("Exception while getting menus", e);
		}
		// For GBS User,fetch its child and its parent ids
		
			String gbsUserHierarchicalIds = patronManagement
					.fetchHierarchicalId(bcwtPatronDTO.getPatronid(),bcwtPatronDTO.getBcwtusertypeid().intValue());
			if(null != gbsUserHierarchicalIds && !gbsUserHierarchicalIds.equals("")){
				session.setAttribute(Constants.GBS_ADMIN_IDS,gbsUserHierarchicalIds);
				gbsUserHierarchicalIds = gbsUserHierarchicalIds +","+bcwtPatronDTO.getPatronid();
				session.setAttribute(Constants.GBS_SUPER_ADMIN_IDS, gbsUserHierarchicalIds);
				
			}else{
				session.setAttribute(Constants.GBS_SUPER_ADMIN_IDS, bcwtPatronDTO.getPatronid().toString());
			}
		
		// to get state information
		try {
			stateMap = patronManagement.getState();
			session.setAttribute(Constants.STATE_INFO_MAP, stateMap);
		} catch (Exception e) {
			BcwtsLogger.error("Error while getting state information in : "
					+ e.getMessage());
		}
		//get the User permission from Database and keep in session for futher use
		try {
			List rolePermission = patronManagement.getRolePermission();
			session.setAttribute(Constants.ROLE_PERMISSION, rolePermission);
		} catch (Exception e) {
			BcwtsLogger.error("Error while getting state information in : "
					+ e.getMessage());
		}
		if (isAutoGenerated) {
			session.setAttribute("isAutoGenerated", "true");
			returnValue = "changePassword";
		} else {
			// update lastlogintime
			/*
			 * Timestamp currentTime = new Timestamp(System
			 * .currentTimeMillis());
			 */
			SimpleDateFormat formatter = new SimpleDateFormat(
					"MM-dd-yyyy hh:mm:ss");
			Calendar cal = Calendar.getInstance();
			Date currentTime = cal.getTime();
			bcwtPatronDTO.setLastlogintime(formatter.format(currentTime));
			patronManagement.updatePatronDetails(bcwtPatronDTO);
			session.removeAttribute("isAutoGenerated");
			returnValue = "loginSuccess";// success with module
		}
		return returnValue;

	}
	
	private List getOrderCount() throws Exception {
		
		List noOfOrdersList = new ArrayList();
		BcwtReportDTO bcwtReportDTO = null;
		String fromDate = null;
		String toDate = null;
		int noOfPending = 0;
		int noOfFulfilled = 0;
		int noOfReturned = 0;
		
		try {
			 ReportManagement reportManagement = new ReportManagement();
			 
			 //For IS
			 bcwtReportDTO = new BcwtReportDTO();
			 noOfPending = reportManagement.getNumberOfPendingOrders(Constants.ORDER_TYPE_IS, fromDate, toDate);
			 bcwtReportDTO.setNoOfPending(String.valueOf(noOfPending));
			 noOfFulfilled = reportManagement.getNumberOfFulfilledOrders(Constants.ORDER_TYPE_IS, fromDate, toDate);
			 bcwtReportDTO.setNoOfFulfilled(String.valueOf(noOfFulfilled));
			 noOfReturned = reportManagement.getNumberOfReturnedOrders(Constants.ORDER_TYPE_IS, fromDate, toDate);
			 bcwtReportDTO.setNoOfReturned(String.valueOf(noOfReturned));
			 bcwtReportDTO.setOrderType(Constants.ORDER_TYPE_IS);
			 noOfOrdersList.add(bcwtReportDTO);
			 
			 //	For GBS
			 bcwtReportDTO = new BcwtReportDTO();
			 noOfPending = reportManagement.getNumberOfPendingOrders(Constants.ORDER_TYPE_GBS, fromDate, toDate);
			 bcwtReportDTO.setNoOfPending(String.valueOf(noOfPending));
			 noOfFulfilled = reportManagement.getNumberOfFulfilledOrders(Constants.ORDER_TYPE_GBS, fromDate, toDate);
			 bcwtReportDTO.setNoOfFulfilled(String.valueOf(noOfFulfilled));
			 noOfReturned = reportManagement.getNumberOfReturnedOrders(Constants.ORDER_TYPE_GBS, fromDate, toDate);
			 bcwtReportDTO.setNoOfReturned(String.valueOf(noOfReturned));
			 bcwtReportDTO.setOrderType(Constants.ORDER_TYPE_GBS);
			 noOfOrdersList.add(bcwtReportDTO);
			 
			 // For PS
			 bcwtReportDTO = new BcwtReportDTO();
			 noOfPending = reportManagement.getNumberOfPendingOrders(Constants.ORDER_TYPE_PS, fromDate, toDate);
			 bcwtReportDTO.setNoOfPending(String.valueOf(noOfPending));
			 noOfFulfilled = reportManagement.getNumberOfFulfilledOrders(Constants.ORDER_TYPE_PS, fromDate, toDate);
			 bcwtReportDTO.setNoOfFulfilled(String.valueOf(noOfFulfilled));
			 noOfReturned = reportManagement.getNumberOfReturnedOrders(Constants.ORDER_TYPE_PS, fromDate, toDate);
			 bcwtReportDTO.setNoOfReturned(String.valueOf(noOfReturned));
			 bcwtReportDTO.setOrderType(Constants.ORDER_TYPE_PS);
			 noOfOrdersList.add(bcwtReportDTO);

		} catch (Exception e) {
			BcwtsLogger.error("Exception in getNumberOfOrders() " + e.getMessage());
			throw e;
		}
		return noOfOrdersList;
	}
	
}