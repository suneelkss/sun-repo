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
import com.marta.admin.dto.BcwtManagePartnerRegistrationDTO;
import com.marta.admin.dto.BcwtPartnerRegistrationRequestDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ManageAddCompanytoTMAForm;
import com.marta.admin.forms.ManagePartnerRegistrationForm;
import com.marta.admin.forms.PartnerRegistrationRequestForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;
public class ManageAddCompanytoTMAAction extends DispatchAction{
	
	final String ME = "ManageAddCompanytoTMAAction: ";
	
	/**
	 * Display Add Company TO TMA details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returnPath
	 * @throws MartaException
	 */	
	public ActionForward displayManageAddCompanytoTMA(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "manageAddCompanytoTMAAction: ";
		BcwtsLogger.debug(MY_NAME+ "Populated data for tma");
		String returnValue = "addCompanytoTMA";
		List statusList = null;
		HttpSession session = request.getSession(true);
		try{
			
			returnValue = "addCompanytoTMA";
			
			ManageAddCompanytoTMABusiness manageAddCompanytoTMABusiness
								= new ManageAddCompanytoTMABusiness();
			
			statusList = new ArrayList();			
			statusList  = manageAddCompanytoTMABusiness.getCompanyTypes();
			session.setAttribute("COMPANY_TYPE_LIST", statusList);			
				
			List managePartnerDetailsList = new ArrayList();
			
			managePartnerDetailsList = manageAddCompanytoTMABusiness
			.getManageAddCompanytoTMADetails();
			
			
			
			request.setAttribute(Constants.MANAGE_ADD_COMPANY_TO_TMA_LIST,
				managePartnerDetailsList);
			session.setAttribute(Constants.MANAGE_ADD_COMPANY_TO_TMA_LIST,
				managePartnerDetailsList);
			
			
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME+" Exception while populated data " +
				"for partners registration "+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);			
	}	
	/**
	 * Display Add Company To TMA search details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returnPath
	 * @throws MartaException
	 */
	public ActionForward displaySearchAddCompanytoTMA(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

			final String MY_NAME = ME + "searchAddCompanytoTMA: ";
			BcwtsLogger.debug(MY_NAME+ "Populated search data for Tma");
			String returnValue = null;
			HttpSession session = request.getSession(true);
			List statusList = new ArrayList();
			try{
				ManageAddCompanytoTMABusiness searchAddCompanytoTMABusiness 						
				= new ManageAddCompanytoTMABusiness();
							
				statusList  = searchAddCompanytoTMABusiness.getCompanyTypes();
				session.setAttribute("COMPANY_TYPE_LIST", statusList);	
				
				returnValue = "addCompanytoTMA";
				
				ManageAddCompanytoTMAForm searchForm =
										(ManageAddCompanytoTMAForm)form;
				
				BcwtManageAddCompanytoTMADTO manageAddCompanytoTMADTO 
										= new BcwtManageAddCompanytoTMADTO();
				
				if (!Util.isBlankOrNull(searchForm.getCompanyName())) {
					manageAddCompanytoTMADTO .setCompanyName
						(searchForm.getCompanyName());						
				}
				if (!Util.isBlankOrNull(searchForm.getCompanyType())) {
					manageAddCompanytoTMADTO.setCompanyType
						(searchForm.getCompanyType());						
				}
				if (!Util.isBlankOrNull(searchForm.getNextfareCompanyId())) {
					manageAddCompanytoTMADTO.setNextfareCompanyId
						(searchForm.getNextfareCompanyId());						
				}
				
				
				
				
				List serachPartnerDetailsList = new ArrayList();
				serachPartnerDetailsList =(List)searchAddCompanytoTMABusiness 
					.getSearchAddCompanytoTMADetails(manageAddCompanytoTMADTO);
				
				
				
				session.setAttribute(Constants.MANAGE_ADD_COMPANY_TO_TMA_LIST,
						serachPartnerDetailsList);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, ">> My Account >> Add Company to TMA");
			}catch(Exception e){
				BcwtsLogger.error(MY_NAME+" Exception while populated  search data " +
						"for partners registration "+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			
			return mapping.findForward(returnValue);				
		}	

		
  }

