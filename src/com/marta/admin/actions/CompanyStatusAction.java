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
import com.marta.admin.dto.BcwtCompanyStatusDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.CompanyStatusForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;

public class CompanyStatusAction extends DispatchAction {

	final String ME = "CompanyStatusAction: ";
	
	public ActionForward displayCompanyStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {			
			final String MY_NAME = ME + "companyStatusAction: ";
			BcwtsLogger.debug(MY_NAME+ "Populated data for CompanyStatus Details");
			String returnValue = null;
			List companyStatusList = null;
			HttpSession session = request.getSession(true);
			
			try{
				returnValue = "companyStatus";
				ManagePartnerRegistrationBusiness managePartnerRegistrationBusiness 
									= new ManagePartnerRegistrationBusiness();	
				companyStatusList = new ArrayList();			
				companyStatusList  = managePartnerRegistrationBusiness.getCompanyStatusDetails();
				
				session.setAttribute(Constants.COMPANY_STATUS_LIST, companyStatusList);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, ">> My Account >> Manage Company Status");
				
			}catch(Exception e){
				BcwtsLogger.error(MY_NAME+" Exception while populated data " +
					"for company status "+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);			
		}	
	public ActionForward displayCompanyStatusInformation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {			
			final String MY_NAME = ME + "companyStatusAction: ";
			BcwtsLogger.debug(MY_NAME+ "Populated data for CompanyStatus Details");
			String companyName = null;
			String returnValue = null;
			List companyStatusInformationList = null;
			List companyStatusList = null;			
			HttpSession session = request.getSession(true);
			
			try{
				
				companyName = request.getParameter("companyName");
				returnValue = "companyStatusInformation";
				ManagePartnerRegistrationBusiness managePartnerRegistrationBusiness 
									= new ManagePartnerRegistrationBusiness();
				
				companyStatusList = managePartnerRegistrationBusiness.getCompanyStatus();
				session.setAttribute("STATUS_LIST", companyStatusList);		
				
				companyStatusInformationList  = managePartnerRegistrationBusiness.getCompanyStatusInformationDetails(companyName);
				
				session.setAttribute(Constants.COMPANY_STATUS_LIST, companyStatusInformationList);			
				
			}catch(Exception e){
				BcwtsLogger.error(MY_NAME+" Exception while populated data " +
					"for company status "+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);			
		}
	public ActionForward addCompanyAdminInfoAndAdminDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {	
		
		final String MY_NAME = ME + "addCompanyStatusAction: ";
		BcwtsLogger.debug(MY_NAME+ "add data from Company Status Details");
		String returnValue = null;
		boolean isUpdated = false;		
		CompanyStatusForm companyStatusForm = null;
		HttpSession session = request.getSession(true);
		ManagePartnerRegistrationBusiness managePartnerRegistrationBusiness  = null;
		
		try{			
			companyStatusForm = (CompanyStatusForm)form;				
			BcwtCompanyStatusDTO companyStatusDTO = null;
			String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath()
			+ "/";
			if(null != session.getAttribute("COMPANY_STATUS_LIST")){
				List companyDetailsList =(List) session.getAttribute("COMPANY_STATUS_LIST");
				for (Iterator iter = companyDetailsList.iterator(); iter.hasNext();) {
					companyStatusDTO = (BcwtCompanyStatusDTO) iter.next();
				}
			}
			companyStatusDTO.setConfirmNextFareId(companyStatusForm.getConfirmNextFareId());
			companyStatusDTO.setCompanyStatus(companyStatusForm.getCompanyStatus());
			 
			managePartnerRegistrationBusiness = new ManagePartnerRegistrationBusiness();			
			isUpdated = managePartnerRegistrationBusiness.
							addCompanyStatusInformationDetails(companyStatusDTO , basePath);	
			
			BcwtsLogger.debug(MY_NAME + "isUpdated= " + isUpdated);
			
			if(isUpdated == true){
				request.setAttribute(Constants.MESSAGE,
						PropertyReader.getValue
							(Constants.PARTNER_ADMIN_DETAIL_AND_INFO_RECORD_ADDED));
				request.setAttribute(Constants.SUCCESS,"true");
				returnValue = "companyStatusBack";
			}
			else{
				request.setAttribute(Constants.MESSAGE,
						PropertyReader
								.getValue(Constants.PARTNER_ADMIN_DETAIL_AND_INFO_RECORD_ADD_FAILED));
				returnValue = "companyStatusInformation";
			}
			
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME+" Exception while populated data " +
				"for company status "+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		return mapping.findForward(returnValue);
	}
}
