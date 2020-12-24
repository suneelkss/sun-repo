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

import com.marta.admin.business.ManageAddCompanytoTMABusiness;
import com.marta.admin.business.ManagePartnerRegistrationBusiness;
import com.marta.admin.dto.BcwtManageAddCompanytoTMADTO;
import com.marta.admin.dto.BcwtPartnerRegistrationRequestDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ManageAddCompanytoTMAForm;
import com.marta.admin.forms.PartnerRegistrationRequestForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class ManageAddCompanyTOTMARegistrationAction extends DispatchAction{
	
	final String ME = "ManageAddCompanyTOTMARegistrationAction: ";
	/**
	 * Display Add Company TO TMA Registration details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returnPath
	 * @throws MartaException
	 */
	public ActionForward displayCompanyRegistrationRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		final String MY_NAME = ME + "partnerRegistrationRequest: ";
		BcwtsLogger.debug(MY_NAME+ "Populated data for partners");
		String returnValue = "addCompanyRegistration";
	
		List statusList = null;
		HttpSession session = request.getSession(true);
		
		try {
			returnValue = "addCompanyRegistration";
			
			
			
			ManageAddCompanytoTMABusiness manageAddCompanytoTMABusiness
			= new ManageAddCompanytoTMABusiness();
			
			statusList = new ArrayList();
			
			statusList  = manageAddCompanytoTMABusiness.getTmaName();
			session.setAttribute("TMA_NAME_LIST", statusList);
		
           }catch(Exception e){
	       BcwtsLogger.error(MY_NAME+" Exception while populated data " +
				"for partners registration "+ e.getMessage());
	      returnValue = ErrorHandler.handleError(e, "", request, mapping);
          }

          return mapping.findForward(returnValue);				
            }	

		
	/**
	 * Action to display selected company details.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
		public ActionForward displayAddCompanyRegistrationRequest(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
				final String MY_NAME = ME + "CompanyRegistrationRequest: ";
				BcwtsLogger.debug(MY_NAME+ "Populated data for  " +
						"Company Registration Request");
				HttpSession session =request.getSession(true);
				String returnValue = null;
				

				String companyId = null;
				List statusList = new ArrayList();
				ManageAddCompanytoTMABusiness manageAddCompanytoTMABusiness = null;
				BcwtManageAddCompanytoTMADTO manageAddCompanytoTMADTO = null;
				try{
					manageAddCompanytoTMABusiness = new ManageAddCompanytoTMABusiness();
					statusList  = manageAddCompanytoTMABusiness.getTmaName();		
					session.setAttribute("TMA_NAME_LIST", statusList);			
					
					if(!Util.isBlankOrNull(request.getParameter("companyId").toString())){
						companyId = request.getParameter("companyId").toString();
						
					}
					
					manageAddCompanytoTMADTO = manageAddCompanytoTMABusiness.getCompanyRegistrationRequestDetails(companyId);
					
					if(manageAddCompanytoTMADTO!=null){
						manageAddCompanytoTMADTO.setCompanyId(companyId);
						
						session.setAttribute("ADD_COMPANY_REGISTRATION_REQUEST_LIST",manageAddCompanytoTMADTO);
						request.setAttribute("ADD_COMPANY_REGISTRATION_REQUEST_LIST",manageAddCompanytoTMADTO);
						returnValue = "addCompanyRegistration";
					}else{
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.COMPANY_DONT_HAVE_PARTNER));
						request.setAttribute(Constants.SUCCESS, "false");
						returnValue = "addCompanytoTMA";			
					}
					
				}catch(Exception e){
					BcwtsLogger.error(MY_NAME+" Exception while added data " +
								"into database "+ e.getMessage());
					returnValue = ErrorHandler.handleError(e, "", request, mapping);
				}
				
				return mapping.findForward(returnValue);				
		}
		/**
		 * Display Add Company details To Database.
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return returnPath
		 * @throws MartaException
		 */	
		public ActionForward addCompany(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "addCompany: ";
			BcwtsLogger.debug(MY_NAME+ "Populated data for  " +
					"Company Registration Request");
			HttpSession session =request.getSession(true);
			String returnValue = null;
			boolean isAdded = false;
			ManageAddCompanytoTMAForm manageAddCompanytoTMAForm = (ManageAddCompanytoTMAForm) form;
			BcwtManageAddCompanytoTMADTO bcwtManageAddCompanytoTMADTO = null;
			
			try {
				if(session.getAttribute("ADD_COMPANY_REGISTRATION_REQUEST_LIST")!=null){
					bcwtManageAddCompanytoTMADTO = (BcwtManageAddCompanytoTMADTO)
					session.getAttribute("ADD_COMPANY_REGISTRATION_REQUEST_LIST");
					
				
					if(manageAddCompanytoTMAForm.getTmaName()!=null){
						bcwtManageAddCompanytoTMADTO.setTmaName(manageAddCompanytoTMAForm.getTmaName());
					}
					ManageAddCompanytoTMABusiness manageAddCompanytoTMABusiness 
							= new ManageAddCompanytoTMABusiness();
					
					isAdded = manageAddCompanytoTMABusiness.addCompany(bcwtManageAddCompanytoTMADTO);
					List serachPartnerDetailsList = new ArrayList();
					if(isAdded){
						BcwtManageAddCompanytoTMADTO manageAddCompanytoTMADTO 
							= new BcwtManageAddCompanytoTMADTO();
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.COMPANY_ADDED_TO_TMA));
						request.setAttribute(Constants.SUCCESS, "true");
						serachPartnerDetailsList = manageAddCompanytoTMABusiness 
						.getSearchAddCompanytoTMADetails(manageAddCompanytoTMADTO);
						session.setAttribute(Constants.MANAGE_ADD_COMPANY_TO_TMA_LIST,
								serachPartnerDetailsList);						
						returnValue = "addCompanytoTMA";
					}else{
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.UNABLE_TO_ADD_COMPANY_TO_TMA));
						request.setAttribute(Constants.SUCCESS, "false");
						returnValue = "addCompanyRegistration";
					}					
				}
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME+" Exception while added data " +
						"into database "+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);
		}
  }

