package com.marta.admin.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.business.ManageAdminBussiness;

import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.MartaAdminManagmentForm;

import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;


public class ManageAdminAction extends DispatchAction{
	
	DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	
	final String ME = "ManageAdminAction: ";
	
	/**
	 * Action to display marta admin page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayMartaAdminPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayMartaAdminPage: ";
		BcwtsLogger.debug(MY_NAME
				+ "getting the list of users");
		HttpSession session = request.getSession(true);
		String returnValue = "manageMartaAdmin";
		List userList = new ArrayList();
		System.out.println("SMTP_SERVER_PATH" +ConfigurationCache.configurationValues.get("SMTP_SERVER_PATH"));
		try {
			Long patronId = new Long(0);
			String roleId = "";
			if(session.getAttribute(Constants.USER_INFO)!=null){
				BcwtPatronDTO bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId = bcwtPatronDTO.getPatronid();
				roleId = bcwtPatronDTO.getRole();
			}
			ManageAdminBussiness manageAdminBussiness = new ManageAdminBussiness();
			userList = manageAdminBussiness.getUserDetailList(patronId,roleId);
			session.setAttribute(Constants.ADMIN_USER_LIST, userList);	
			session.removeAttribute("SHOW_ADD_DIV");
			session.removeAttribute("SHOW_EDIT_DIV");
			session.removeAttribute("CHECK_USER_NAME");	
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying marta admin page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_MANAGE_MARTA_ADMIN);
		
		return mapping.findForward(returnValue);
	} 
	
	/**
	 * Action to get patron details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward getPatronDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "getPatronDetail: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for unlocking Account");
		HttpSession session = request.getSession(true);
		String returnValue = "manageMartaAdmin";		
		
		
		try {
			MartaAdminManagmentForm manageAdminForm = (MartaAdminManagmentForm) form;			
			Long patronId = new Long(0);
			String patronIdStr = request.getParameter("patronId").toString();
			patronId = new Long(patronIdStr);
			ManageAdminBussiness manageAdminBussiness = new ManageAdminBussiness();
			BcwtPatronDTO bcwtPatronDTO = manageAdminBussiness.getPatronDetails(patronId); 
			
			manageAdminForm.setUserId(patronId.toString());
			
			if(null!=bcwtPatronDTO.getFirstname()){					
				manageAdminForm.setEditFirstName(bcwtPatronDTO.getFirstname());
			}
			
			if(null!=bcwtPatronDTO.getLastname()){					
				manageAdminForm.setEditLastName(bcwtPatronDTO.getLastname());
			}
			
			if(null!=bcwtPatronDTO.getMiddlename()){					
				manageAdminForm.setEditMiddleName(bcwtPatronDTO.getMiddlename());
			}
			
			if(null!=bcwtPatronDTO.getEmailid()){					
				manageAdminForm.setEditEmail(bcwtPatronDTO.getEmailid());
			}
			
			if(null!=bcwtPatronDTO.getPhonenumber()){					
				manageAdminForm.setEditPhoneNumberOne(bcwtPatronDTO.getPhonenumber().substring(0, 3));
				manageAdminForm.setEditPhoneNumberTwo(bcwtPatronDTO.getPhonenumber().substring(3, 6));
				manageAdminForm.setEditPhoneNumberThree(bcwtPatronDTO.getPhonenumber().substring(6));
			}
			
			if(null!=bcwtPatronDTO.getRole()){					
				manageAdminForm.setEditRoleName(bcwtPatronDTO.getRole());
			}
			
			if(null!=bcwtPatronDTO.getDateofbirth()){				
				manageAdminForm.setEditDateOfBirth(Util.convertDateToString(bcwtPatronDTO.getDateofbirth()));
			}
			manageAdminForm.setRoleName("");
			session.setAttribute("SHOW_EDIT_DIV", "SHOW_EDIT_DIV");
			session.removeAttribute("SHOW_ADD_DIV");				
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while displaying marta admin page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_MANAGE_MARTA_ADMIN);
		
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Action to add patron details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward addPatron(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "addPatron: ";
		BcwtsLogger.debug(MY_NAME
				+ "to add a patron user");
		HttpSession session = request.getSession(true);
		String returnValue = "manageMartaAdmin";
		
		
		try {			 			 
			Long parentPatronId = new Long(0);
			Long patronId = new Long(0);
			String roleId = "";
			List userList = new ArrayList();
			String check = "";
			MartaAdminManagmentForm manageAdminForm = (MartaAdminManagmentForm) form;			
			ManageAdminBussiness manageAdminBussiness = new ManageAdminBussiness();
			String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath()
			+ "/";
			if(session.getAttribute(Constants.USER_INFO)!=null){
				BcwtPatronDTO loginPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				parentPatronId = loginPatronDTO.getPatronid();
				patronId = loginPatronDTO.getPatronid();
				roleId = loginPatronDTO.getRole();
			}
			BcwtPatronDTO bcwtPatronDTO = new BcwtPatronDTO();
			
			if(parentPatronId!=null){
				bcwtPatronDTO.setParentpatronid(parentPatronId);				
			}
			
			if(!Util.isBlankOrNull(manageAdminForm.getFirstName())){
				bcwtPatronDTO.setFirstname(manageAdminForm.getFirstName());
			}
			
			if(!Util.isBlankOrNull(manageAdminForm.getLastName())){
				bcwtPatronDTO.setLastname(manageAdminForm.getLastName());
			}
			
			if(!Util.isBlankOrNull(manageAdminForm.getMiddleName())){
				bcwtPatronDTO.setMiddlename(manageAdminForm.getMiddleName());
			}
			
			if(!Util.isBlankOrNull(manageAdminForm.getEmail())){
				bcwtPatronDTO.setEmailid(manageAdminForm.getEmail());
			}
			if(manageAdminForm.getPhoneNumberOne() != null 
					&& manageAdminForm.getPhoneNumberTwo() != null
					&& manageAdminForm.getPhoneNumberThree() != null ){
				bcwtPatronDTO.setPhonenumber(manageAdminForm.getPhoneNumberOne()+
						manageAdminForm.getPhoneNumberTwo()+
						manageAdminForm.getPhoneNumberThree());
			}			
			if(!Util.isBlankOrNull(manageAdminForm.getDateOfBirth())){
				bcwtPatronDTO.setDateofbirth(Util.convertStringToDate(manageAdminForm.getDateOfBirth()));
			}
			
			if(!Util.isBlankOrNull(manageAdminForm.getRoleName())){
				bcwtPatronDTO.setRole(manageAdminForm.getRoleName());
			}
			
			bcwtPatronDTO.setLockstatus(Constants.NO);	
			bcwtPatronDTO.setActivestatus(Constants.ONE);
			bcwtPatronDTO.setIsautogenerated(Constants.YES);
			bcwtPatronDTO.setLogincount(new Integer(Constants.ZERO));			
			check = manageAdminBussiness.addPatron(bcwtPatronDTO,basePath);
			
			if(Util.equalsIgnoreCase(check, Constants.SUCCESS)){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.ADMIN_USER_ADD_SUCCESS_MESSAGE));
				request.setAttribute(Constants.SUCCESS, "true");				
				userList = manageAdminBussiness.getUserDetailList(patronId,roleId);
				session.setAttribute(Constants.ADMIN_USER_LIST, userList);
			}
			if(Util.equalsIgnoreCase(check, Constants.WARNING)){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.ADMIN_USER_ALREADY_EXISTS));
				request.setAttribute(Constants.SUCCESS, "false");
			}
			if(check == ""){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.ADMIN_USER_ADD_FAILURE_MESSAGE));
				request.setAttribute(Constants.SUCCESS, "false");
			}
			
			form.reset(mapping, request);
			session.removeAttribute("SHOW_ADD_DIV");
			session.removeAttribute("SHOW_EDIT_DIV");
			session.removeAttribute("CHECK_USER_NAME");
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while displaying marta admin page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_MANAGE_MARTA_ADMIN);
		
		return mapping.findForward(returnValue);
	}
	
	
	/**
	 * Action to update patron details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward updatePatron(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "updatePatron: ";
		BcwtsLogger.debug(MY_NAME
				+ "to update a patron user");
		HttpSession session = request.getSession(true);
		String returnValue = "manageMartaAdmin";
		
		
		try {			
			MartaAdminManagmentForm manageAdminForm = (MartaAdminManagmentForm) form;
			boolean isUpdate = false;
			Long patronId = new Long(0);
			String roleId = "";
			List userList = new ArrayList();
			ManageAdminBussiness manageAdminBussiness = new ManageAdminBussiness();
			
			if(session.getAttribute(Constants.USER_INFO)!=null){
				BcwtPatronDTO loginPatronDTO  = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);				
				patronId = loginPatronDTO.getPatronid();
				roleId = loginPatronDTO.getRole();
			}
			BcwtPatronDTO bcwtPatronDTO = new BcwtPatronDTO();
			
			if(manageAdminForm.getUserId()!=null){
				bcwtPatronDTO.setPatronid(new Long(manageAdminForm.getUserId()));
			}
			
			if(manageAdminForm.getEditFirstName()!=null){
				bcwtPatronDTO.setFirstname(manageAdminForm.getEditFirstName());
			}
			
			if(manageAdminForm.getEditLastName()!=null){
				bcwtPatronDTO.setLastname(manageAdminForm.getEditLastName());
			}
			
			if(manageAdminForm.getEditMiddleName()!=null){
				bcwtPatronDTO.setMiddlename(manageAdminForm.getEditMiddleName());
			}
			
			if(manageAdminForm.getEditEmail()!=null){
				bcwtPatronDTO.setEmailid(manageAdminForm.getEditEmail());
			}
			
			if(manageAdminForm.getEditPhoneNumberOne()!=null && 
					manageAdminForm.getEditPhoneNumberTwo() != null &&
					manageAdminForm.getEditPhoneNumberThree() != null ){
				bcwtPatronDTO.setPhonenumber(manageAdminForm.getEditPhoneNumberOne()+
						manageAdminForm.getEditPhoneNumberTwo()+
						manageAdminForm.getEditPhoneNumberThree());
			}
			
			
			if(manageAdminForm.getEditDateOfBirth()!=null){
				bcwtPatronDTO.setDateofbirth(Util.convertStringToDate(manageAdminForm.getEditDateOfBirth()));
			}
			
			if(manageAdminForm.getEditRoleName()!=null){
				bcwtPatronDTO.setRole(manageAdminForm.getEditRoleName());
			}
			
			isUpdate = manageAdminBussiness.updatePatron(bcwtPatronDTO);
			if(isUpdate){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.ADMIN_USER_UPDATE_SUCCESS_MESSAGE));
				request.setAttribute(Constants.SUCCESS, "true");
				userList = manageAdminBussiness.getUserDetailList(patronId,roleId);
				session.setAttribute(Constants.ADMIN_USER_LIST, userList);
			}else {
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.ADMIN_USER_UPADTE_FAILURE_MESSAGE));
				request.setAttribute(Constants.SUCCESS, "false");
			}
			session.removeAttribute("CHECK_USER_NAME");	
			session.removeAttribute("SHOW_EDIT_DIV");
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while displaying marta admin page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_MANAGE_MARTA_ADMIN);
		
		return mapping.findForward(returnValue);
	}
	
	
	/**
	 * Action to delete a user
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward deletePatron(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "deletePatron: ";
		BcwtsLogger.debug(MY_NAME
				+ "to delete a patron user");
		HttpSession session = request.getSession(true);
		String returnValue = "manageMartaAdmin";
		
		try {
			Long patronId = new Long(0);			 
			boolean isDeleted = false;			
			List userList = new ArrayList();
			String roleId = "";
			if(session.getAttribute(Constants.USER_INFO)!=null){
				BcwtPatronDTO loginPatronDTO  = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId = loginPatronDTO.getPatronid();
				roleId = loginPatronDTO.getRole();
			}
			String patronIdStr = request.getParameter("deletePatronId").toString();
			patronId = new Long(patronIdStr);
			ManageAdminBussiness manageAdminBussiness = new ManageAdminBussiness();			
			isDeleted = manageAdminBussiness.deletePatron(patronId);
			
			if(isDeleted){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.ADMIN_USER_DELETED_SUCCESS_MESSAGE));
				request.setAttribute(Constants.SUCCESS, "true");
				userList = manageAdminBussiness.getUserDetailList(patronId,roleId);
				session.setAttribute(Constants.ADMIN_USER_LIST, userList);
			}else {
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.ADMIN_USER_DELETED_FAILURE_MESSAGE));
				request.setAttribute(Constants.SUCCESS, "false");
			}
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while deleting a patron admin user:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_MANAGE_MARTA_ADMIN);
		
		session.removeAttribute("SHOW_EDIT_DIV");
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
	public ActionForward searchAdminUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "deletePatron: ";
		BcwtsLogger.debug(MY_NAME
				+ "to delete a patron user");
		HttpSession session = request.getSession(true);
		String returnValue = "manageMartaAdmin";
		
		try {			
			Long patronId = new Long(0);
			String roleId = "";			 
			MartaAdminManagmentForm manageAdminForm = (MartaAdminManagmentForm) form;
			List searchedAdminList = new ArrayList();			 
			BcwtSearchDTO bcwtSearchDTO = new BcwtSearchDTO();
			ManageAdminBussiness manageAdminBussiness = new ManageAdminBussiness();
			
			if(session.getAttribute(Constants.USER_INFO)!=null){
				BcwtPatronDTO loginPatronDTO  = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId = loginPatronDTO.getPatronid();
				roleId = loginPatronDTO.getRole();
			}
			
			if(!Util.isBlankOrNull(manageAdminForm.getSearchEmail())){
				bcwtSearchDTO.setAdminSearchEmail(manageAdminForm.getSearchEmail());				
			}
			
			if(!Util.isBlankOrNull(manageAdminForm.getSearchFirstName())){
				bcwtSearchDTO.setAdminSearchFirstName(manageAdminForm.getSearchFirstName());				
			}
			
			if(!Util.isBlankOrNull(manageAdminForm.getSearchLastName())){
				bcwtSearchDTO.setAdminSearchLastName(manageAdminForm.getSearchLastName());				
			}
			
			if(!Util.isBlankOrNull(manageAdminForm.getSearchMiddleName())){
				bcwtSearchDTO.setAdminSearchMiddleName(manageAdminForm.getSearchMiddleName());				
			}
			searchedAdminList = manageAdminBussiness.searchAdminUser(bcwtSearchDTO,patronId,roleId);
			session.removeAttribute(Constants.ADMIN_USER_LIST);
			session.setAttribute(Constants.ADMIN_USER_LIST, searchedAdminList);	
			session.removeAttribute("CHECK_USER_NAME");	
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while deleting a patron admin user:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_MANAGE_MARTA_ADMIN);
		session.removeAttribute("SHOW_EDIT_DIV");
		
		return mapping.findForward(returnValue);
	}	
	
	
	/**
	 * Action to check user name
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward checkUserName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "checkUserName: ";
		BcwtsLogger.debug(MY_NAME
				+ "to check user name already exist or not");
		HttpSession session = request.getSession(true);
		String returnValue = "manageMartaAdmin";		
		try {			
			String userName = null;
			boolean isAvailable = false;
			String checkUserName = null;			
			ManageAdminBussiness manageAdminBussiness = new ManageAdminBussiness();
			if(!Util.isBlankOrNull(request.getParameter("userName").toString())){
				userName = request.getParameter("userName");
			}
			isAvailable = manageAdminBussiness.checkUserName(userName);
			if(isAvailable){
				checkUserName = "User Name Exists";
				session.setAttribute("CHECK_USER_NAME", checkUserName);				
			}else{
				checkUserName = "";
				session.setAttribute("CHECK_USER_NAME", checkUserName);	
			}
			session.setAttribute("SHOW_ADD_DIV","SHOW_ADD_DIV");	
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while checking admin user name:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_MANAGE_MARTA_ADMIN);
		
		return mapping.findForward(returnValue);
	}
}
