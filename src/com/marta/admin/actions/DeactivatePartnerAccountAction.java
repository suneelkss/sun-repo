package com.marta.admin.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.business.DeactivatePartnerAccount;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.DeactivatePartnerAccountForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class DeactivatePartnerAccountAction extends DispatchAction {
	
	final String ME = "DeactivatePartnerAccountAction :: "; 
	DeactivatePartnerAccount deactivatePartnerAccount = new DeactivatePartnerAccount();
	
	public ActionForward showSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final String MY_NAME = ME + "showSearch: ";
		BcwtsLogger.info(MY_NAME);
		String returnValue = "deactivatePartnerAccount";
		
		BcwtPatronDTO loginPatronDTO = null;
		String userName = null;
		String patronId = null;
		
		try {
			DeactivatePartnerAccountForm deactivatePartnerAccountForm = (DeactivatePartnerAccountForm) form;
			
			HttpSession session = request.getSession(true);
			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();
					userName = loginPatronDTO.getFirstname() + " "
							+ loginPatronDTO.getLastname();
				}
			}
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
			
			if(!Util.isBlankOrNull(deactivatePartnerAccountForm.getAccountType())) {
				session.setAttribute(Constants.ACCOUNT_TYPE, deactivatePartnerAccountForm.getAccountType());
			} else {
				session.setAttribute(Constants.ACCOUNT_TYPE, Constants.ACCOUNT_TYPE_PARTNER);
			}			
			
			List companyTypeList = deactivatePartnerAccount.getCompanyTypeAsLabelValueBean();
			if(companyTypeList != null && !companyTypeList.isEmpty()) {
				session.setAttribute("COMPANY_TYPE_LABEL_VALUE_BEAN_LIST", companyTypeList);
			} else {
				throw new MartaException(MY_NAME + "companyType labelValueBean List cannot be null");
			}
			
			session.setAttribute("SHOW_DATA_GRID", Constants.NO);			
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> My Account >> Deactivate TMA/Company Account"); 	
			form.reset(mapping, request);
		}catch(Exception e) {
			BcwtsLogger.error(MY_NAME + "Exception : "	+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			BcwtsLogger.debug(ME + " returnValue" + returnValue);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward searchPartnerAccounts(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final String MY_NAME = ME + "searchPartnerAccounts: ";
		BcwtsLogger.info(MY_NAME);
		String returnValue = "deactivatePartnerAccount";
		
		BcwtPatronDTO loginPatronDTO = null;
		String userName = null;
		String patronId = null;
		String accountType = null;
		
		try {
			DeactivatePartnerAccountForm deactivatePartnerAccountForm = (DeactivatePartnerAccountForm) form;
			
			HttpSession session = request.getSession(true);
			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();
					userName = loginPatronDTO.getFirstname() + " "
							+ loginPatronDTO.getLastname();
				}
			}
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
			
			accountType = deactivatePartnerAccountForm.getAccountType();
			if(!Util.isBlankOrNull(accountType)) {
				request.setAttribute(Constants.ACCOUNT_TYPE, accountType);
			} else {
				request.setAttribute(Constants.ACCOUNT_TYPE, Constants.ACCOUNT_TYPE_PARTNER);
			}
			
			List partnerDetailsList = null;
			if(!Util.isBlankOrNull(accountType)) {
				if(accountType.equalsIgnoreCase(Constants.ACCOUNT_TYPE_PARTNER)){
					partnerDetailsList = deactivatePartnerAccount.getPartnerDetails(deactivatePartnerAccountForm);
				} else if(accountType.equalsIgnoreCase(Constants.ACCOUNT_TYPE_TMA)){
					partnerDetailsList  = deactivatePartnerAccount.getTMAPartnerDetails(deactivatePartnerAccountForm);
				}				
			} else {
				throw new MartaException(MY_NAME + "accountType cannot be null");
			}			
			
			session.setAttribute("PARTNER_DETAILS_LIST", partnerDetailsList);
			
			session.setAttribute("SHOW_DATA_GRID", Constants.YES);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> My Account >> Deactivate TMA/Company Account");	
		}catch(Exception e) {
			BcwtsLogger.error(MY_NAME + "Exception : "	+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			BcwtsLogger.debug(ME + " returnValue" + returnValue);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward deactivateCompany(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final String MY_NAME = ME + "deactivateCompany: ";
		BcwtsLogger.info(MY_NAME);
		String returnValue = "deactivateCompany";
		
		BcwtPatronDTO loginPatronDTO = null;
		String userName = null;
		String patronId = null;
		String companyId = null;
		boolean isDeactivated = false;
		
		try {
			HttpSession session = request.getSession(true);
			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();
					userName = loginPatronDTO.getFirstname() + " "
							+ loginPatronDTO.getLastname();
				}
			}
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
			
			companyId = request.getParameter("companyId");
			if(Util.isBlankOrNull(companyId)) {
				throw new MartaException(MY_NAME + "companyId cannot be null");
			} 
						
			isDeactivated = deactivatePartnerAccount.deactivateCompany(companyId);
						
			if(isDeactivated){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.COMPANY_DEACTIVATE_SUCCESS_MESSAGE));
				request.setAttribute(Constants.SUCCESS, "true");
			} else {
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.COMPANY_DEACTIVATE_FAILURE_MESSAGE));
			}
		}catch(Exception e) {
			BcwtsLogger.error(MY_NAME + "Exception : "	+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			BcwtsLogger.debug(ME + " returnValue" + returnValue);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward deactivateTMA(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final String MY_NAME = ME + "deactivateTMA: ";
		BcwtsLogger.info(MY_NAME);
		String returnValue = "deactivateTMA";
		
		BcwtPatronDTO loginPatronDTO = null;
		String userName = null;
		String patronId = null;
		String tmaId = null;
		boolean isDeactivated = false;
		
		try {
			HttpSession session = request.getSession(true);
			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();
					userName = loginPatronDTO.getFirstname() + " "
							+ loginPatronDTO.getLastname();
				}
			}
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
			
			tmaId = request.getParameter("tmaId");
			if(Util.isBlankOrNull(tmaId)) {
				throw new MartaException(MY_NAME + "tmaId cannot be null");
			} 
			
			isDeactivated = deactivatePartnerAccount.deactivateTMA(tmaId);
			
			if(isDeactivated){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.TMA_DEACTIVATE_SUCCESS_MESSAGE));
				request.setAttribute(Constants.SUCCESS, "true");
			} else {
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.TMA_DEACTIVATE_FAILURE_MESSAGE));
			}			
		}catch(Exception e) {
			BcwtsLogger.error(MY_NAME + "Exception : "	+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			BcwtsLogger.debug(ME + " returnValue" + returnValue);
		}
		return mapping.findForward(returnValue);
	}

}