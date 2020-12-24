package com.marta.admin.business;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.actions.DispatchAction;

import com.marta.admin.dao.ManageAddCompanytoTMADAO;
import com.marta.admin.dao.ManagePartnerRegistrationDAO;
import com.marta.admin.dto.BcwtCompanyStatusDTO;
import com.marta.admin.dto.BcwtManageAddCompanytoTMADTO;
import com.marta.admin.dto.BcwtManagePartnerRegistrationDTO;
import com.marta.admin.dto.BcwtPartnerRegistrationRequestDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;

public class ManageAddCompanytoTMABusiness extends DispatchAction{
	
	final String ME = "ManageAddCompanytoTMA";
	/**
	 * Method to get CompanyTypes Details.
	 * 
	 * @return companyTypes
	 * @throws MartaException
	 */
	public List getCompanyTypes()throws MartaException {
	
		final String MY_NAME = ME + " getAddCompanytoTMACompanyTypes : ";
		BcwtsLogger.debug(MY_NAME + " getting Add Company to TMA CompanyTypes Details ");
	
		  
		
		ManageAddCompanytoTMADAO manageAddCompanytoTMADAO
		= new ManageAddCompanytoTMADAO();
		
		List companyTypeList = null;
		try {
			
			companyTypeList = manageAddCompanytoTMADAO.getCompanyTypes();
			BcwtsLogger.info(MY_NAME + " got Manage Partner Registration Status Details:");	
	
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Manage Partner" +
				" Registration Status details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		
		return companyTypeList;
	}
	
	/**
	 * Method to get ManageAddCompanytoTMA Details.
	 * 
	 * @return manageAddCompanytoTMADetailsList
	 * @throws MartaException
	 */
	
	public List getManageAddCompanytoTMADetails()throws MartaException {
		
		
		final String MY_NAME = ME + " getAddCompanytoTMACompanyTypes : ";
		BcwtsLogger.debug(MY_NAME + " getting Add Company to TMA CompanyTypes Details ");
		
		ManageAddCompanytoTMADAO manageAddCompanytoTMADAO
		= new ManageAddCompanytoTMADAO();
		List manageAddCompanytoTMADetailsList = null;
		
		
		try {
			manageAddCompanytoTMADetailsList = manageAddCompanytoTMADAO.
			getManageAddCompanytoTMADAODetails();
			BcwtsLogger.info(MY_NAME + " got Manage Partner Registration Details:");					
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Manage Partner Registration details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		return manageAddCompanytoTMADetailsList;
	
		
	}
	/**
	 * Method to get SearchAddCompanytoTMA Details.
	 * 
	 * @return searchAddCompanytoTMADetailsList
	 * @throws MartaException
	 */
	
	public List getSearchAddCompanytoTMADetails(BcwtManageAddCompanytoTMADTO
			manageAddCompanytoTMADTO)throws MartaException {
	
	
		final String MY_NAME = ME + " getAddCompanytoTMACompanyTypes : ";
		BcwtsLogger.debug(MY_NAME + " getting Add Company to TMA CompanyTypes Details ");
		
		ManageAddCompanytoTMADAO searchAddCompanytoTMADAO 
		= new ManageAddCompanytoTMADAO();
	        List searchAddCompanytoTMADetailsList = null;
         
	        try {
	        	searchAddCompanytoTMADetailsList = new ArrayList();
	        	searchAddCompanytoTMADetailsList   = (List)searchAddCompanytoTMADAO 
					.getSearchAddCompanytoTMADetails(manageAddCompanytoTMADTO);
				BcwtsLogger.info(MY_NAME + " got Search Partner Registration Details:");

			}catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Exception in getting Search " +
						"Partner Registration details :"+ e.getMessage());
					throw new MartaException(PropertyReader
						.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
				}
				return searchAddCompanytoTMADetailsList;
		
		
	}	
	/**
	 * Method to get Company Registration Request Details.
	 * 
	 * @return bcwtManageAddCompanytoTMADTO
	 * @throws MartaException
	 */
	public BcwtManageAddCompanytoTMADTO getCompanyRegistrationRequestDetails(String regReqId)			
	throws MartaException {
	final String MY_NAME = ME + " getPartnerRegistrationDetails: ";
	BcwtsLogger.debug(MY_NAME + " getting Partner Registration Details ");

	ManageAddCompanytoTMADAO searchAddCompanytoTMADAO 
	= new ManageAddCompanytoTMADAO();
	
	BcwtManageAddCompanytoTMADTO bcwtManageAddCompanytoTMADTO = null;
	try {
		bcwtManageAddCompanytoTMADTO =
			searchAddCompanytoTMADAO .getCompanyRegistrationRequestDetails(regReqId);
		
		BcwtsLogger.info(MY_NAME + " got Manage Partner Registration Details:");
	}catch (Exception e) {
		BcwtsLogger.error(MY_NAME + " Exception in getting Manage" +
			" Partner Registration details :"+ e.getMessage());
		throw new MartaException(PropertyReader
			.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
	}
	return bcwtManageAddCompanytoTMADTO;
}


        /**
         * Method to get TmaName Details.
         * 
         * @return TmaNames
         * @throws MartaException
         */
		public List getTmaName()throws MartaException {
			
			final String MY_NAME = ME + " getAddCompanytoTMACompanyTypes : ";
			BcwtsLogger.debug(MY_NAME + " getting Add Company to TMA CompanyTypes Details ");
		
			  
			
			ManageAddCompanytoTMADAO manageAddCompanytoTMADAO
			= new ManageAddCompanytoTMADAO();
			
			List tmaNameList = null;
			try {
				
				tmaNameList = manageAddCompanytoTMADAO.getTmaName();
				BcwtsLogger.info(MY_NAME + " got Manage Partner Registration Status Details:");	
		
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Exception in getting Manage Partner" +
					" Registration Status details :"
						+ e.getMessage());
				throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
			}
			
			return tmaNameList;
		}  
		/**
         * Method to add Company Details.
         * 
         * @return add Company
         * @throws MartaException
         */	
		public boolean addCompany(BcwtManageAddCompanytoTMADTO bcwtManageAddCompanytoTMADTO)
				throws MartaException {
			final String MY_NAME = ME + " addCompany : ";
			BcwtsLogger.debug(MY_NAME + " getting Add Company to TMA CompanyTypes Details ");
			boolean isAdded = false;
			  
			
			ManageAddCompanytoTMADAO manageAddCompanytoTMADAO
			= new ManageAddCompanytoTMADAO();
			
			
			try {
				isAdded = manageAddCompanytoTMADAO.addCompany(bcwtManageAddCompanytoTMADTO);
				BcwtsLogger.info(MY_NAME + " Added  Company Registration Details:");
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Exception in Adding Manage" +
						" Company Registration details :"+ e.getMessage());
				   throw new MartaException(PropertyReader
						.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
			}
			return isAdded;
		}

	
}