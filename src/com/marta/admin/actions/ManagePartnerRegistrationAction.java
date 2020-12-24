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

import com.marta.admin.business.ManagePartnerRegistrationBusiness;
import com.marta.admin.dto.BcwtManagePartnerRegistrationDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ManagePartnerRegistrationForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.Util;


public class ManagePartnerRegistrationAction extends DispatchAction  {
	
	final String ME = "ManagePartnerRegistrationAction: ";

	public ActionForward displayManagePartnerRegistration(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "managePartnerRegistration: ";
		BcwtsLogger.debug(MY_NAME+ "Populated data for partners");
		String returnValue = null;
		List statusList = null;
		List managePartnerDetailsList = null;		
		HttpSession session = request.getSession(true);
		
		try{
		
			returnValue = "managePartnerRegistration";
			ManagePartnerRegistrationBusiness managePartnerRegistrationBusiness 
								= new ManagePartnerRegistrationBusiness();
			
			statusList = new ArrayList();			
			statusList  = managePartnerRegistrationBusiness.getStatus();
			session.setAttribute("STATUS_LIST", statusList);
			
			managePartnerDetailsList = new ArrayList();			
			managePartnerDetailsList = managePartnerRegistrationBusiness
				.getManagePartnerRegistrationDetails();			
			session.setAttribute(Constants.MANAGE_PARTNER_REGISTRATION_LIST,
				managePartnerDetailsList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, ">> My Account >> Manage Partner Registration Status");
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME+" Exception while populated data " +
				"for partners registration "+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);			
	}	
	
	public ActionForward displaySearchPartnerRegistration(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "searchPartnerRegistration: ";
		BcwtsLogger.debug(MY_NAME+ "Populated search data for partners");
		String returnValue = null;
		List serachPartnerDetailsList = null;
		HttpSession session = request.getSession(true);
		
		try{
		
			returnValue = "managePartnerRegistration";
			
			ManagePartnerRegistrationForm searchForm =
									(ManagePartnerRegistrationForm)form;
			
			BcwtManagePartnerRegistrationDTO managePartnerRegistrationDTO 
									= new BcwtManagePartnerRegistrationDTO();
			
			if (!Util.isBlankOrNull(searchForm.getCompanyName())) {
				managePartnerRegistrationDTO.setCompanyName
					(searchForm.getCompanyName());						
			}
			if (!Util.isBlankOrNull(searchForm.getFirstName())) {
				managePartnerRegistrationDTO.setFirstName
					(searchForm.getFirstName());						
			}
			if (!Util.isBlankOrNull(searchForm.getLastName())) {
				managePartnerRegistrationDTO.setLastName
					(searchForm.getLastName());						
			}
			if (!Util.isBlankOrNull(searchForm.getRegistrationStatus())) {
				managePartnerRegistrationDTO.setRegistrationStatus
					(searchForm.getRegistrationStatus());						
			}
			if (!Util.isBlankOrNull(searchForm.getStartDate())) {
				managePartnerRegistrationDTO.setStartDate
					(searchForm.getStartDate());						
			}
			if (!Util.isBlankOrNull(searchForm.getEndDate())) {
				managePartnerRegistrationDTO.setEndDate
					(searchForm.getEndDate());						
			}
			
			ManagePartnerRegistrationBusiness searchPartnerRegistrationBusiness 						
				= new ManagePartnerRegistrationBusiness();
			
			serachPartnerDetailsList = new ArrayList();
			serachPartnerDetailsList =(List)searchPartnerRegistrationBusiness 
				.getSearchPartnerRegistrationDetails(managePartnerRegistrationDTO);	
			
			session.setAttribute(Constants.MANAGE_PARTNER_REGISTRATION_LIST,
					serachPartnerDetailsList);
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME+" Exception while populated  search data " +
					"for partners registration "+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		return mapping.findForward(returnValue);				
	}	
}