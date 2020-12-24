package com.marta.admin.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.ManagePartnerRegistrationBusiness;
import com.marta.admin.dto.BcwtPartnerRegistrationRequestDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.PartnerRegistrationRequestForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class PartnerRegistrationRequestAction extends DispatchAction{

	final String ME = "PartnerRegistrationRequestAction: ";
	
	public ActionForward displayPartnerRegistrationRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "partnerRegistrationRequest: ";
		BcwtsLogger.debug(MY_NAME+ "Populated data for partners");
		String returnValue = null;
		String regReqId = null;
		String reqNo = null;
		List partnerDetailsList = null;
		PartnerRegistrationRequestForm PartnerRequestform =null;
	
		try {	
			HttpSession session = request.getSession(true);			
			regReqId = request.getParameter("requestId");			
			if(!Util.isBlankOrNull(regReqId)){				
				ManagePartnerRegistrationBusiness partnerRegistrationBusiness 
						= new ManagePartnerRegistrationBusiness();
				partnerDetailsList = new ArrayList();
				partnerDetailsList = partnerRegistrationBusiness 
										.getPartnerRegistrationRequestDetails(regReqId);				
				session.setAttribute(Constants.PARTNER_REGISTRATION_REQUEST_LIST,partnerDetailsList);
				returnValue = "partnerRegistrationRequest";
			}			
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME+" Exception while populated data " +
						"for partners registration "+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		return mapping.findForward(returnValue);				
	}	
	
	public ActionForward displayAddPartnerRegistrationRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "PartnerRegistrationRequest: ";
		BcwtsLogger.debug(MY_NAME+ "Populated data for  " +
				"Partner Registration Request");
		HttpSession session =request.getSession(true);
		String returnValue = null;
		boolean isUpdated = false;
		PartnerRegistrationRequestForm partnerRegistrationRequestForm = null;
		BcwtPartnerRegistrationRequestDTO partnerRegistrationDTO = null;
		try{
				
			ManagePartnerRegistrationBusiness partnerRegistrationBusiness 
											= new ManagePartnerRegistrationBusiness();
			partnerRegistrationRequestForm = (PartnerRegistrationRequestForm)form;
			
			if(null != partnerRegistrationRequestForm 
					&& !partnerRegistrationRequestForm.equals("")) {
					if(null != session.getAttribute("PARTNER_REGISTRATION_REQUEST_LIST")){
					List partnerDetailsList =(List) session.getAttribute("PARTNER_REGISTRATION_REQUEST_LIST");
					for (Iterator iter = partnerDetailsList.iterator(); iter.hasNext();) {
						partnerRegistrationDTO = (BcwtPartnerRegistrationRequestDTO) iter.next();					
					}
				}
				if(null != partnerRegistrationRequestForm.getNewStatus()
							&& !partnerRegistrationRequestForm.getNewStatus().equals("")){
					partnerRegistrationDTO.setNewStatus(partnerRegistrationRequestForm.getNewStatus());
				}
				if(null != partnerRegistrationRequestForm.getMartaAdminNote().trim()
						&& !partnerRegistrationRequestForm.getMartaAdminNote().trim().equals("")){
					partnerRegistrationDTO.setMartaAdminNote(partnerRegistrationRequestForm.getMartaAdminNote());
			}
			isUpdated = partnerRegistrationBusiness 
					 .addPartnerRegistrationRequestDetails(partnerRegistrationDTO);
			BcwtsLogger.debug(MY_NAME + "isUpdated= " + isUpdated);
			if(isUpdated == true){
				request.setAttribute(Constants.MESSAGE,
						PropertyReader
								.getValue(Constants.PARTNER_REGISTRATION_RECORD_ADDED));
				request.setAttribute(Constants.SUCCESS, "true");
				returnValue = "managePartnerRegistration";
			}
			else{
				request.setAttribute(Constants.MESSAGE,
						PropertyReader
								.getValue(Constants.PARTNER_REGISTRATION_RECORD_ADD_FAILED));
				returnValue = "partnerRegistrationRequest";
			}
			}
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME+" Exception while added data " +
						"into database "+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		return mapping.findForward(returnValue);				
	}	
	
	public ActionForward displayWelcomePartnerRegistrationRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "welcomePartnerRegistrationRequest: ";
		BcwtsLogger.debug(MY_NAME+ "Populated  recent data for Welcome Page " +
				"Partner Registration Request");
		String returnValue = null;
		List partnerDetailsList = null;
		HttpSession session = request.getSession(true);
		
		try {		
			returnValue = "welcome";
			ManagePartnerRegistrationBusiness partnerRegistrationBusiness 
					= new ManagePartnerRegistrationBusiness();
			
		//	partnerDetailsList = new ArrayList();
			partnerDetailsList = partnerRegistrationBusiness 
									.getWelcomePartnerRegistrationListDetails();			
			session.setAttribute(Constants.PARTNER_REGISTRATION_REQUEST_LIST,partnerDetailsList);
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME+" Exception while populated data " +
						"for partners registration "+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		return mapping.findForward(returnValue);				
	}	
}
