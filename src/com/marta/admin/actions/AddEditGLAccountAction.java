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
import com.marta.admin.business.AddEditGLAccount;
import com.marta.admin.dto.AddEditGLAccountDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.AddEditGLAccountForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;


public class AddEditGLAccountAction extends DispatchAction{

	   final String ME = "AddEditGLAccountAction: ";
	   
	    /**
	     * Method to show all GL Accounts List
	     * 
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws MartaException
	     */
		public ActionForward displayAddEditGLAccountPage(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {

			final String MY_NAME = ME + "addEditGLAccount: ";
			BcwtsLogger.debug(MY_NAME + "gathering pre populated data for add edit GL account page");
			String returnValue = "addEditGLAccount";
			AddEditGLAccount addEditGLAccount = new AddEditGLAccount();
			HttpSession session = request.getSession(true);
			String userName = "";
			try {
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);			
				List addEditGLAccountList = addEditGLAccount.getAddEditGLAccountList();
				session.setAttribute("ADD_EDIT_GLACCOUNT_LIST", addEditGLAccountList);
				session.removeAttribute("SHOW_ADD_EDIT_GLACCOUNT_DIV");
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Error while displaying add edit GL Account page :"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
				e.printStackTrace();
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_ADD_EDIT_GL_ACCOUNT);
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
		public ActionForward updateAddEditGLAccountDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "updateAddEditGLAccountDetails: ";
			BcwtsLogger.debug(MY_NAME
					+ "to update add edit GL Account details");
			HttpSession session = request.getSession(true);
			String returnValue = "addEditGLAccount";			
			AddEditGLAccount addEditGLAccount = null;
			AddEditGLAccountDTO addEditGLAccountDTO = null ;
			AddEditGLAccountForm addEditGLAccountForm = (AddEditGLAccountForm)form;		
			boolean isUpdate = false;

			try {
				List addEditGLAccountList = new ArrayList();
				addEditGLAccount = new AddEditGLAccount();
				addEditGLAccountDTO = new AddEditGLAccountDTO();				
				if(addEditGLAccountForm.getEditProductAccountNumberId() != null ){
					addEditGLAccountDTO.setEditProductAccountNumberId(addEditGLAccountForm.getEditProductAccountNumberId());
				}
				if(addEditGLAccountForm.getEditChartOfAccountsId() != null ){
					addEditGLAccountDTO.setEditChartOfAccountsId(addEditGLAccountForm.getEditChartOfAccountsId());
				}
				if(addEditGLAccountForm.getEditCodeCombinationId() != null ){
					addEditGLAccountDTO.setEditCodeCombinationId(addEditGLAccountForm.getEditCodeCombinationId());
				}
				if(addEditGLAccountForm.getEditFareInstrumentId() != null){
					addEditGLAccountDTO.setEditFareInstrumentId(addEditGLAccountForm.getEditFareInstrumentId());
				}
				if(addEditGLAccountForm.getEditProductName() != null ){
					addEditGLAccountDTO.setEditProductName(addEditGLAccountForm.getEditProductName());
				}
				if(addEditGLAccountForm.getEditSalesAccount() != null ){
					addEditGLAccountDTO.setEditSalesAccount(addEditGLAccountForm.getEditSalesAccount());
				}
				isUpdate = addEditGLAccount.updateAddEditGLAccountDetails(addEditGLAccountDTO);
				if(isUpdate){
					request.setAttribute(Constants.MESSAGE,PropertyReader
							.getValue(Constants.ADD_EDIT_GL_ACCOUNT_UPDATE_SUCCESS_MESSAGE));
					request.setAttribute(Constants.SUCCESS, "true");
					addEditGLAccountList = addEditGLAccount.getAddEditGLAccountList(); 					
					session.setAttribute("ADD_EDIT_GLACCOUNT_LIST", addEditGLAccountList);
				}else{
					request.setAttribute(Constants.MESSAGE,PropertyReader
							.getValue(Constants.ADD_EDIT_GL_ACCOUNT_UPDATE_FAILURE_MESSAGE));
					request.setAttribute(Constants.SUCCESS, "false");
				}
				session.removeAttribute("SHOW_ADD_EDIT_GLACCOUNT_DIV");
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while updating view edit products details:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_ADD_EDIT_GL_ACCOUNT);
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
		public ActionForward populateAddEditGLAccountDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "getViewEditProductsDetails: ";
			BcwtsLogger.debug(MY_NAME + "get view edit product details");
			HttpSession session = request.getSession(true);
			AddEditGLAccountForm addEditGLAccountForm = (AddEditGLAccountForm)form;
			AddEditGLAccountDTO addEditGLAccountDTO = null ;
			AddEditGLAccount addEditGLAccount = null ;
			String returnValue = "addEditGLAccount";
			String productAccountNoId = "";

			if(!Util.isBlankOrNull(request.getParameter("editProductAccountNumberId"))){
				productAccountNoId = request.getParameter("editProductAccountNumberId").toString();
			}
			try {
				Long productAccountNumberId = new Long(productAccountNoId);
				addEditGLAccount = new AddEditGLAccount();
				addEditGLAccountForm.setEditProductAccountNumberId(productAccountNumberId.toString());
				addEditGLAccountDTO = addEditGLAccount.getAddEditGLAccountDetails(productAccountNumberId);
				
				if(null != addEditGLAccountDTO.getEditChartOfAccountsId()){
					addEditGLAccountForm.setEditChartOfAccountsId(addEditGLAccountDTO.getEditChartOfAccountsId());
				}
				if(null != addEditGLAccountDTO.getEditCodeCombinationId()){
					addEditGLAccountForm.setEditCodeCombinationId(addEditGLAccountDTO.getEditCodeCombinationId());
				}
				if(null != addEditGLAccountDTO.getEditProductAccountNumberId()){
					addEditGLAccountForm.setEditProductAccountNumberId(addEditGLAccountDTO.getEditProductAccountNumberId());
				}
				if(null != addEditGLAccountDTO.getEditProductName()){
					addEditGLAccountForm.setEditProductName(addEditGLAccountDTO.getEditProductName());
				}
				if(null != addEditGLAccountDTO.getEditFareInstrumentId()){
					addEditGLAccountForm.setEditFareInstrumentId(addEditGLAccountDTO.getEditFareInstrumentId());
				}
				if(null != addEditGLAccountDTO.getEditSalesAccount()){
					addEditGLAccountForm.setEditSalesAccount(addEditGLAccountDTO.getEditSalesAccount());
				}
				session.setAttribute("SHOW_ADD_EDIT_GLACCOUNT_DIV", "SHOW_ADD_EDIT_GLACCOUNT_DIV");
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME
						+ " Exception while populating add/edit GL Account details :"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_ADD_EDIT_GL_ACCOUNT);
			return mapping.findForward(returnValue);		
		}
		
		/**
		 * Method to add GL Account
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward addAccountDetail(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "addAccountDetail: ";
			BcwtsLogger.debug(MY_NAME
					+ "to add GL Account details");
			HttpSession session = request.getSession(true);
			String returnValue = "addEditGLAccount";			
			AddEditGLAccount addEditGLAccount = null ;
			AddEditGLAccountDTO addEditGLAccountDTO = null ;
			AddEditGLAccountForm addEditGLAccountForm = (AddEditGLAccountForm)form;
			boolean isAdded=false;
			List addEditGLAccountList = new ArrayList();

			try {				
				addEditGLAccountDTO = new AddEditGLAccountDTO();
				addEditGLAccount = new AddEditGLAccount();

				if(addEditGLAccountForm.getFareInstrumentId() != null ){
					addEditGLAccountDTO.setFareInstrumentId(addEditGLAccountForm.getFareInstrumentId());
				}
				if(addEditGLAccountForm.getProductName() != null ){
					addEditGLAccountDTO.setProductName(addEditGLAccountForm.getProductName());
				}
				if(addEditGLAccountForm.getChartOfAccountsId() != null ){
					addEditGLAccountDTO.setChartOfAccountsId(addEditGLAccountForm.getChartOfAccountsId());
				}
				if(addEditGLAccountForm.getCodeCombinationId() != null ){
					addEditGLAccountDTO.setCodeCombinationId(addEditGLAccountForm.getCodeCombinationId());
				}
				if(addEditGLAccountForm.getSalesAccount() != null ){
					addEditGLAccountDTO.setSalesAccount(addEditGLAccountForm.getSalesAccount());
				}
				isAdded = addEditGLAccount.addAccountDetail(addEditGLAccountDTO);				
				if(isAdded){					
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.ADD_EDIT_GL_ACCOUNT_ADD_SUCCESS_MESSAGE));										
					request.setAttribute(Constants.SUCCESS, "true");
					
					addEditGLAccountList = addEditGLAccount.getAddEditGLAccountList(); 					
					session.setAttribute("ADD_EDIT_GLACCOUNT_LIST", addEditGLAccountList);
				}else{
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.ADD_EDIT_GL_ACCOUNT_ADD_FAILURE_MESSAGE));
									
					request.setAttribute(Constants.SUCCESS, "false");
				}				
				session.removeAttribute("SHOW_ADD_EDIT_GLACCOUNT_DIV");
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while adding GL Account details:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}			
			return mapping.findForward(returnValue);
		}
}
