package com.marta.admin.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.marta.admin.business.AssociatePartnersBussiness;
import com.marta.admin.business.CreateManualAlertManagement;
import com.marta.admin.business.LogManagement;
import com.marta.admin.business.ManageMenus;
import com.marta.admin.business.ManagePartnerRegistrationBusiness;
import com.marta.admin.business.PatronManagement;
import com.marta.admin.business.ReportManagement;
import com.marta.admin.business.UnlockAccount;
import com.marta.admin.business.RequestToHotList;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtReportDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.LoginForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;
/**
 * 
 * @author Administrator
 *
 */
public class MultipleUserControlAction extends Action {
	
	final String ME = "MultipleUserControlAction";
	
	PatronManagement patronManagement = new PatronManagement();

	
	/**
	 * Method to login patron when this patron is already in active
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String MY_NAME = ME + " execute: ";
		BcwtsLogger
				.debug(MY_NAME
						+ "Check the user action and redirect to login page or landing page");

		String userAction = "";
		Long patronId = new Long(0);
		String returnPath = "";
		BcwtPatronDTO bcwtPatronDTO = null;
		LoginForm loginForm = (LoginForm)form;
		PatronManagement patronManagement = new PatronManagement();
		List regionList = new ArrayList();
		String currentDate = null;
		String patronTypeId=null;
		String emailId=null;
		CreateManualAlertManagement alertManagement = null;
		List alertList = null;
		ManagePartnerRegistrationBusiness managePartnerRegistrationBusiness = null;
		List partnerList = null;
		LogManagement logManagement=null;
		List recentList=null;
		UnlockAccount unlockAccount = null;
		RequestToHotList requestToHotList = null;
		List recentlyHotListCardList = null;

		AssociatePartnersBussiness associatePartnersBussiness=null;
		List searchList=null;
		List lockedList=null;
		if (null != request.getParameter("userAction")) {
			userAction = request.getParameter("userAction");
		}

		HttpSession session = request.getSession(true);
		ServletContext context = session.getServletContext();

		Map usersMap = (Map) context.getAttribute(Constants.MARTACONTEXTMAP);

		if (null != request.getParameter("patronId")) {
			patronId = Long.valueOf(request.getParameter("patronId"));
		}
		if (null != request.getParameter("patronTypeId")) {
			patronTypeId = request.getParameter("patronTypeId");
		}
		if (null != request.getParameter("emailId")) {
			emailId = request.getParameter("emailId");
		}
		
		
		BcwtsLogger.info(MY_NAME + " User action is" + userAction);
		
		if (userAction.equalsIgnoreCase("resetPrevSession")) {
			
			String sessionIdFromClearSessionPage = request.getParameter("sessionTxt");
			if (null != usersMap && null != patronId
					&& usersMap.containsKey(patronId)) {
				
				try {
					bcwtPatronDTO = patronManagement
							.getPatronDetailsById(patronId);
				} catch (Exception e) {
					BcwtsLogger.error(MY_NAME + e.getMessage());
					e.printStackTrace();
				}
				session.setAttribute(Constants.USER_INFO, bcwtPatronDTO);
				patronTypeId = bcwtPatronDTO.getBcwtusertypeid().toString();
				
				//Added Order Summary in Welcome page - senthil raja
				List ordersCountList = getOrderCount();
				session.setAttribute(Constants.ORDERS_COUNT_LIST, ordersCountList);
				
//				code for Manual alerts display by maheswari starts here
				currentDate = Util.getCurrentDate();
				alertManagement = new CreateManualAlertManagement();
				alertList = alertManagement.displayAlerts(patronId.toString(), currentDate);
				session.setAttribute(Constants.ALERT_MESSAGE_LIST_WELCOMEPAGE, alertList);
				//code for Manual alerts display by maheswari ends here
				
				//code for Recent Registartion Request display by Selvi starts here
				managePartnerRegistrationBusiness = new ManagePartnerRegistrationBusiness();
				partnerList = managePartnerRegistrationBusiness
											.getWelcomePartnerRegistrationListDetails();
				 session.setAttribute(Constants.PARTNER_REGISTRATION_REQUEST_WELCOME,
						 													partnerList);
				
				//code for Recent Registartion Request display by Selvi ends here
				
				//code for Logging Calls display by Sowjanya starts here
				//currentDate = Util.getCurrentDate();
				logManagement = new LogManagement();
				recentList = logManagement.displayRecentLogList(patronId.toString(),patronTypeId,emailId);
				session.setAttribute(Constants.RECENT_LOGCALL_LIST_WELCOMEPAGE, recentList);
				//code for Logging Calls display by Sowjanya ends here
				
				//code for Recently Locked Accounts Details in Welcome page by Jagadeesan starts here
				//unlockAccount = new UnlockAccount();
				//recentlyLockedAccountList = unlockAccount.getRecentlyLockedAccounts();
				//session.setAttribute(Constants.LOCKED_USER_DETAILS, recentlyLockedAccountList);
				//code for Recently Locked Accounts Details in Welcome page by Jagadeesan ends here
				
//				code for Logging Calls display by Subba Reddy starts here
				//currentDate = Util.getCurrentDate();
				associatePartnersBussiness = new AssociatePartnersBussiness();
				searchList = associatePartnersBussiness.displayPartnerSearchList(patronId.toString(),patronTypeId,emailId);
				session.setAttribute(Constants.PARTNER_SEARCH_LIST_WELCOMEPAGE, recentList);
				//code for Logging Calls display by Sowjanya ends here
				
				//code for Locked Calls display by sowjanya starts here
				unlockAccount = new UnlockAccount();
				lockedList = unlockAccount.displayLockedCalls(patronTypeId.toString());
				session.setAttribute(Constants.LOCKED_LIST_WELCOMEPAGE, lockedList);
				//code for Locked Calls display by sowjanya ends here
				
//				code for recently hotlisting card List by jagadeesan starts here
				requestToHotList = new RequestToHotList();
				recentlyHotListCardList = requestToHotList.getRecentlyHotlistCardList();
				session.setAttribute(Constants.RECENT_HOTLIST_CARDLIST_WELCOMEPAGE, recentlyHotListCardList);
				//code for recently hotlisting card List by jagadeesan ends here
				
//				 set region to session
				usersMap.put(patronId, sessionIdFromClearSessionPage);
				context.setAttribute(Constants.MARTACONTEXTMAP, usersMap);				
				returnPath = updateAndGetCardDetails(bcwtPatronDTO,session,request);
				boolean isGBSUserWithoutSecretQuestion = patronManagement
				.getSecurityQuestionDetails(patronId);
				if(!isGBSUserWithoutSecretQuestion){
					session.setAttribute("SHOW_SECRET_QUESTION", "true");
					List secretQuestionList = patronManagement.getSecretQuestions("");										
					session.setAttribute(Constants.SECRET_QUESTIONS_LIST,
							secretQuestionList);
				}
			}
		}
		if (userAction.equalsIgnoreCase("cancelCurrSession")) {
			returnPath ="loginpage";
		}
		
//		Setting the user name into Cookies.
		if(!Util.isBlankOrNull(loginForm.getRememberMe()) 
				&& loginForm.getRememberMe().equals(Constants.YES)){
			
			if (returnPath.equals("welcome")){
				
				BcwtsLogger.debug(MY_NAME + " Setting the email address "+bcwtPatronDTO.getEmailid()+" into Cookie");
				
				Cookie emailAddressCookie = new Cookie(Constants.EMAIL_ID, bcwtPatronDTO.getEmailid());
				//emailAddressCookie.setDomain(".breezecard.com");	// *.company.com, but not *.web.company.com
				//emailAddressCookie.setPath("/");					// All pages
				response.addCookie(emailAddressCookie);				// Add the cookie to the HTTP headers
				
				BcwtsLogger.info(MY_NAME + " The email address "+bcwtPatronDTO.getEmailid()+" has been set into Cookie");
				
			}
			
		}else{
			//Remove the email from cookie.
			BcwtsLogger.debug(MY_NAME + " Removing the email address "+bcwtPatronDTO.getEmailid()+" from Cookie");
			
			Cookie emailAddressCookie = new Cookie(Constants.EMAIL_ID, "");
			emailAddressCookie.setMaxAge(0); 
			response.addCookie(emailAddressCookie);
			
			BcwtsLogger.info(MY_NAME + " Removed the email address "+bcwtPatronDTO.getEmailid()+" from Cookie");
		}
		
		return mapping.findForward(returnPath);
	}
	
	/**
	 * Method to update patron details in db whne user login resetted
	 * @param bcwtPatronDTO
	 * @param session
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws IllegalArgumentException
	 * @throws MartaException
	 */
	public String updateAndGetCardDetails(BcwtPatronDTO bcwtPatronDTO,
			HttpSession session,HttpServletRequest request) throws ParseException,
			IllegalArgumentException, MartaException {

		String returnValue = "";
		boolean isAutoGenerated = false;
		List breezeCardsList = new ArrayList();
		List breezeCardLVBList = new ArrayList();
		Map cardDetailsMap = new HashMap();
		String lastLogin = "";
		int loginCount = 0;
		int loginCountDb = 0;
		Map menuMap = new HashMap();
		Map stateMap = new HashMap();
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
			
			/*** MARTA UI - Review Changes (start) ***/
			
			/*SimpleDateFormat formatterLogin = new SimpleDateFormat(
					"EEE, d MMM yyyy HH:mm:ss");*/
			SimpleDateFormat formatterLogin = new SimpleDateFormat("MMM dd, yyyy");
			
			/*** MARTA UI - Review Changes (end) ***/
			
			Date currentTime = (Date) formatterDate.parse(bcwtPatronDTO
					.getLastlogintime());
			
			lastLogin = formatterLogin.format(currentTime);
			session.setAttribute(Constants.LAST_LOGIN, lastLogin);
		}

		// insert logincount
		if (loginCountDb != 0) {
			loginCount = 0;
			bcwtPatronDTO.setLogincount(new Integer(loginCount));
			patronManagement.updatePatronDetails(bcwtPatronDTO);
		}
//		 get the menus associtaed to the patron type
		ManageMenus manageMenus = new ManageMenus();
		try {
			if (null != context.getAttribute(Constants.MENU_MAP)) {
				menuMap = (HashMap) context.getAttribute(Constants.MENU_MAP);
			}
			if (null != menuMap.get(bcwtPatronDTO.getBcwtusertypeid())) {
				menuList = (ArrayList)menuMap.get(bcwtPatronDTO.getBcwtusertypeid());
			}else{
				menuList = manageMenus.getMenusForPatronType(bcwtPatronDTO
						.getBcwtusertypeid(),request.getContextPath());
				menuMap.put(bcwtPatronDTO.getBcwtusertypeid(), menuList);
				context.setAttribute(Constants.MENU_MAP,menuMap);
				
			}
			session.setAttribute(Constants.MENU_LIST, menuList);
		} catch (MartaException e) {
			BcwtsLogger.error("Exception while getting menus", e);
		}

		// get carddetails
		
//		 For GBS User,fetch its child and its parent ids
		
			String gbsUserHierarchicalIds = patronManagement
					.fetchHierarchicalId(bcwtPatronDTO.getPatronid(),bcwtPatronDTO.getBcwtusertypeid().intValue());
			if(null != gbsUserHierarchicalIds && !gbsUserHierarchicalIds.equals("")){
				gbsUserHierarchicalIds = gbsUserHierarchicalIds +","+bcwtPatronDTO.getPatronid();
				session.setAttribute(Constants.GBS_SUPER_ADMIN_IDS, gbsUserHierarchicalIds);
			}else{
				session.setAttribute(Constants.GBS_SUPER_ADMIN_IDS, bcwtPatronDTO.getPatronid().toString());
			}
		
//		 to get state information
		try {
			stateMap = patronManagement.getState();
			session.setAttribute(Constants.STATE_INFO_MAP, stateMap);
		} catch (Exception e) {
			BcwtsLogger.error("Error while getting state information in : "
					+ e.getMessage());
		}
//		get the User permission from Database and keep in session for futher use
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
			List productList = new ArrayList();
			List patronCardList = new ArrayList();
			bcwtPatronDTO.setLastlogintime(formatter.format(currentTime));
			patronManagement.updatePatronDetails(bcwtPatronDTO);
			session.removeAttribute("isAutoGenerated");
			returnValue = "welcome";
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
