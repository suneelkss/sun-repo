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

import com.marta.admin.business.UnlockAccount;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtUnlockAccountDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.UnlockForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class UnlockAccountAction extends DispatchAction {
	
	final String ME = "UnlockAccountAction: ";
	
	/**
	 * Action to show all locked account list
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayUnlockAccountPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayUnlockAccountPage: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for unlocking Account");
		HttpSession session = request.getSession(true);
		String returnValue = "displayUnlockAccountPage";
		UnlockAccount unlockAccount = new UnlockAccount();
		String roleId = "";
		BcwtPatronDTO bcwtPatronDTO = null;
		List adminList = new ArrayList();
		
		try {
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);				
				roleId = bcwtPatronDTO.getRole();				
			}
			
			adminList = unlockAccount.getAdminList(roleId);			
			session.setAttribute(Constants.SEARCH_LIST_ADMIN, adminList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_UNLOCK_ACCOUNT);
			returnValue = "displayUnlockAccountPage";
		} catch (MartaException e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.removeAttribute(Constants.LOCKED_USER_DETAILS);
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
	public ActionForward populateUnlockUserDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "populateUnlockUsersDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for users unlocking Account");
		HttpSession session = request.getSession(true);
		String returnValue = "displayUnlockAccountPage";
		UnlockForm unlockForm = (UnlockForm) form;
		UnlockAccount unlockAccount = new UnlockAccount();
		String patronId = "";
		BcwtUnlockAccountDTO bcwtUnlockAccountDTO = null;
		try {
			patronId = request.getParameter("unlockPatronId").toString();
			/*
			if(patronId!=null){
				session.setAttribute("unlockPatronId", patronId);
			}
			if(session.getAttribute("unlockPatronId")!=null){
				patronId = (String) session.getAttribute("unlockPatronId");
			}*/
			
			bcwtUnlockAccountDTO = unlockAccount.populateLockedUserDetails(patronId);
			
			if(!Util.isBlankOrNull(bcwtUnlockAccountDTO.getPatronId())){
				unlockForm.setPatronIdToUnlock(bcwtUnlockAccountDTO.getPatronId());
			}
			if(!Util.isBlankOrNull(bcwtUnlockAccountDTO.getAdminFname())){
				unlockForm.setFirstNameUnlock(bcwtUnlockAccountDTO.getAdminFname());
			}
			if(!Util.isBlankOrNull(bcwtUnlockAccountDTO.getAdminLname())){
				unlockForm.setLastNameUnlock(bcwtUnlockAccountDTO.getAdminLname());
			}
			if(!Util.isBlankOrNull(bcwtUnlockAccountDTO.getAdminEmail())){
				unlockForm.setMailIdUnlock(bcwtUnlockAccountDTO.getAdminEmail());
			}
			if(!Util.isBlankOrNull(bcwtUnlockAccountDTO.getNotes())){
				unlockForm.setNotes(bcwtUnlockAccountDTO.getNotes());
			}
			if(!Util.isBlankOrNull(bcwtUnlockAccountDTO.getUnlockPassword())){
				unlockForm.setUnlockedPassword(bcwtUnlockAccountDTO.getUnlockPassword());
			}
			
			session.setAttribute(Constants.LOCKED_USER_DETAILS, bcwtUnlockAccountDTO);
			returnValue = "displayUnlockAccountPage";
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_UNLOCK_ACCOUNT);
		} catch (MartaException e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
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
	
	public ActionForward searchLockedAdmins(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "searchLockedAdmin: ";
		BcwtsLogger.debug(MY_NAME
				+ "To search for perticular admin");
		HttpSession session = request.getSession(true);
		String returnValue = "displayUnlockAccountPage";
		UnlockForm unlockForm = (UnlockForm) form;
		UnlockAccount unlockAccount = new UnlockAccount();
		String patronId = "";
		String userName = "";
		String roleId = "";
		List searchedAdminList = null;
		BcwtUnlockAccountDTO bcwtUnlockAccountDTO = null;
		BcwtPatronDTO bcwtPatronDTO =  new BcwtPatronDTO();
		try {
			
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = bcwtPatronDTO.getPatronid().toString();
				roleId = bcwtPatronDTO.getRole();
				userName = bcwtPatronDTO.getFirstname() + " "
						+ bcwtPatronDTO.getLastname();
			}
			bcwtUnlockAccountDTO = new BcwtUnlockAccountDTO();
			unlockForm.setPatronIdToUnlock(bcwtUnlockAccountDTO.getPatronId());
			
			if(null != unlockForm.getAdminFname()){
				bcwtUnlockAccountDTO.setAdminFname(unlockForm.getAdminFname());
			}
			if(null != unlockForm.getAdminLname()){
				bcwtUnlockAccountDTO.setAdminLname(unlockForm.getAdminLname());
			}
			if(null != unlockForm.getAdminEmail()){
				bcwtUnlockAccountDTO.setAdminEmail(unlockForm.getAdminEmail());
			}
			if(null != unlockForm.getAdminPhoneNumber()){
				bcwtUnlockAccountDTO.setAdminPhoneNumber(unlockForm.getAdminPhoneNumber());
			}
			searchedAdminList = unlockAccount.searchLockedAdmins
				(bcwtUnlockAccountDTO,patronId,roleId);
			request.setAttribute(Constants.SEARCH_LIST_ADMIN, searchedAdminList);
			session.setAttribute(Constants.SEARCH_LIST_ADMIN, searchedAdminList);
			returnValue = "displayUnlockAccountPage";
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_UNLOCK_ACCOUNT);
		} catch (MartaException e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.removeAttribute(Constants.LOCKED_USER_DETAILS);
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
	public ActionForward unlockLockedAdmin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "unlockLockedAdmin: ";
		BcwtsLogger.debug(MY_NAME
				+ "unlocking the locked user Account");
		HttpSession session = request.getSession(true);
		String returnValue = "displayUnlockAccountPage";
		UnlockForm unlockForm = (UnlockForm) form;
		UnlockAccount unlockAccount = new UnlockAccount();
		BcwtPatronDTO bcwtPatronDTO = new BcwtPatronDTO();
		String roleId = "";
		boolean isUnlocked = false;
		try {	
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);				
				roleId = bcwtPatronDTO.getRole();				
			}
			
			if(!Util.isBlankOrNull(unlockForm.getFirstNameUnlock())){
				bcwtPatronDTO.setFirstname(unlockForm.getFirstNameUnlock());
			}
			if(!Util.isBlankOrNull(unlockForm.getLastNameUnlock())){
				bcwtPatronDTO.setLastname(unlockForm.getLastNameUnlock());
			}
			if(!Util.isBlankOrNull(unlockForm.getMailIdUnlock())){
				bcwtPatronDTO.setEmailid(unlockForm.getMailIdUnlock());
			}
			if(!Util.isBlankOrNull(unlockForm.getPatronIdToUnlock())){
				bcwtPatronDTO.setPatronid(new Long(unlockForm.getPatronIdToUnlock()));
			}
			if(!Util.isBlankOrNull(unlockForm.getNotes())){
				bcwtPatronDTO.setNotes(unlockForm.getNotes());
			}else{
				bcwtPatronDTO.setNotes("");
			}
			if(!Util.isBlankOrNull(unlockForm.getUnlockedPassword())){
				bcwtPatronDTO.setPatronpassword(unlockForm.getUnlockedPassword());
			}
			bcwtPatronDTO.setLockstatus(Constants.NO);
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
			isUnlocked = unlockAccount.unlockLockedAdmin(bcwtPatronDTO,basePath);
			if(isUnlocked){		
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.UNLOCK_SUCESS_MESSAGE));
				request.setAttribute(Constants.SUCCESS, "true");
				List adminList = unlockAccount.getAdminList(roleId);
				request.setAttribute(Constants.SEARCH_LIST_ADMIN, adminList);
				session.setAttribute(Constants.SEARCH_LIST_ADMIN, adminList);
			}else{
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.UNLOCK_FAILURE_MESSAGE));
				
			}
			returnValue = "displayUnlockAccountPage";
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_UNLOCK_ACCOUNT);
		} catch (MartaException e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.removeAttribute(Constants.LOCKED_USER_DETAILS);
		return mapping.findForward(returnValue);
	}
	

	/**
	 * Action to show recently locked accounts in welcome page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayWelcomeLockedCall(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayWelcomeLockedCall: ";
		BcwtsLogger.debug(MY_NAME
				+ "display locked call for admin in welcome page");
		HttpSession session = request.getSession(true);
		String returnValue = "welcome";
		UnlockAccount unlockAccount = null;
		BcwtPatronDTO bcwtPatronDTO = null;
		String patronId = null;
		String patronTypeId=null;
		try {
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId = bcwtPatronDTO.getPatronid().toString();
			}
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronTypeId = bcwtPatronDTO.getBcwtusertypeid().toString();
			}
			unlockAccount = new UnlockAccount();
			List lockedList= unlockAccount.displayLockedCalls(patronTypeId);
			session.setAttribute(Constants.LOCKED_LIST_WELCOMEPAGE, lockedList);
			returnValue = "welcome";
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while displaying locked call in Welcome Page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
}
