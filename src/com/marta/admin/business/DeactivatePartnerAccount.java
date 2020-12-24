package com.marta.admin.business;

import java.util.Iterator;
import java.util.List;

import com.marta.admin.dao.DeactivatePartnerAccountDAO;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.DeactivatePartnerAccountForm;
import com.marta.admin.hibernate.PartnerAdminDetails;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;

public class DeactivatePartnerAccount {
	
	final String ME = "DeactivatePartnerAccount :: ";
	
	DeactivatePartnerAccountDAO deactivatePartnerAccountDAO = new DeactivatePartnerAccountDAO();
	
	/**
	 * To get partner details
	 * 
	 * @param deactivatePartnerAccountForm
	 * @return partnerDetailsList
	 * @throws MartaException
	 */
	public List getPartnerDetails(DeactivatePartnerAccountForm deactivatePartnerAccountForm)
	throws MartaException {
		final String MY_NAME = ME + " getPartnerDetails: ";
		BcwtsLogger.debug(MY_NAME);
		List partnerDetailsList = null;

		try {
			partnerDetailsList = deactivatePartnerAccountDAO.getPartnerDetails(deactivatePartnerAccountForm);
			
			if(partnerDetailsList != null){
			    BcwtsLogger.info(MY_NAME + " got partnerDetails List: " + partnerDetailsList.size());
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting partnerDetails List: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting partnerDetails List");
		}
		return partnerDetailsList;
	}

	/**
	 * To get TMA Partner details
	 * 
	 * @param deactivatePartnerAccountForm
	 * @return tmaPartnerDetailsList
	 * @throws MartaException
	 */
	public List getTMAPartnerDetails(DeactivatePartnerAccountForm deactivatePartnerAccountForm)
	throws MartaException {
		final String MY_NAME = ME + " getTMAPartnerDetails: ";
		BcwtsLogger.debug(MY_NAME);
		List tmaPartnerDetailsList = null;

		try {
			tmaPartnerDetailsList = deactivatePartnerAccountDAO.getTMAPartnerDetails(deactivatePartnerAccountForm);
			if(tmaPartnerDetailsList != null){
			    BcwtsLogger.info(MY_NAME + " got TMA partnerDetails List: " + tmaPartnerDetailsList.size());
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting TMA partnerDetails List: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting TMA partnerDetails List");
		}
		return tmaPartnerDetailsList;
	}
	
	/**
	 * To get company type
	 * 
	 * @return companyTypeList
	 * @throws MartaException
	 */
	public List getCompanyTypeAsLabelValueBean() throws MartaException {
		final String MY_NAME = ME + " getCompanyTypeAsLabelValueBean: ";
		BcwtsLogger.debug(MY_NAME);
		List companyTypeList = null;

		try {
			companyTypeList = deactivatePartnerAccountDAO.getCompanyTypeAsLabelValueBean();
			if(companyTypeList != null){
			    BcwtsLogger.info(MY_NAME + " got companyType List: " + companyTypeList.size());
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting companyType List: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting companyType List");
		}
		return companyTypeList;
	}
	
	/**
	 * To deactivate company
	 * @param companyId
	 * @return isDeactivated
	 * @throws MartaException
	 */
	public boolean deactivateCompany(String companyId) throws MartaException {
		final String MY_NAME = ME + " deactivateCompany: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isDeactivated = false;
		boolean isStatusFlagUpdated = false;
		List cardSerialNumberList = null;
		List partnerAdminDetailsList = null;
		
		try {
			
			cardSerialNumberList = deactivatePartnerAccountDAO.getCardSerialNumber(companyId);
			
			// Hotlist cards
			if(cardSerialNumberList != null && !cardSerialNumberList.isEmpty()){
				BcwtConfigParamsDTO configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
				if(configParamDTO.getParamvalue().equalsIgnoreCase(Constants.YES)){
					for (Iterator iter = cardSerialNumberList.iterator(); iter.hasNext();) {
						String serialNumber = (String) iter.next();
						
						try {
							NewNextfareMethods.hotlistCard(serialNumber);
						}catch(Exception e) {
							BcwtsLogger.error(MY_NAME + "Exception in Hotlisting Card, Serial No: " + serialNumber);
							BcwtsLogger.error(MY_NAME + " Exception: " + Util.getFormattedStackTrace(e));
							throw new MartaException(MY_NAME + "Exception in Hotlisting card, Serial No: " + serialNumber);
						}
					}
				}
			} else {
				BcwtsLogger.debug(MY_NAME + "No Card Serial Number(s) found for company: " + companyId);			
			}	
			
			partnerAdminDetailsList = deactivatePartnerAccountDAO.getPartnerAdminDetails(companyId);
			
			// Update Status Flag
			if(partnerAdminDetailsList != null && !partnerAdminDetailsList.isEmpty()){
				for (Iterator iter = partnerAdminDetailsList.iterator(); iter.hasNext();) {
					PartnerAdminDetails partnerAdminDetails = (PartnerAdminDetails) iter.next();
					
					String partnerId = partnerAdminDetails.getPartnerId().toString();
					isStatusFlagUpdated = deactivatePartnerAccountDAO.updateStatusFlag(partnerId, Constants.STATUS_FLAG_BARRED);
					
					if(!isStatusFlagUpdated){
						BcwtsLogger.error(MY_NAME + "statusFlag cannot be updated for partnerId: " + partnerId);							
						throw new MartaException(MY_NAME + "statusFlag cannot be updated for partnerId: " + partnerId);						
					}
				}
			}
			
			// Update Company Status
			isDeactivated = deactivatePartnerAccountDAO.updateCompanyStatus(companyId, Constants.COMPANY_STATUS_HOTLISTED);
					
		    BcwtsLogger.info(MY_NAME + " isDeactivated: " + isDeactivated);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while deactivating company: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception while deactivating company");
		}
		return isDeactivated;
	}

	/**
	 * To deactivate TMA
	 * @param tmaId
	 * @return isDeactivated
	 * @throws MartaException
	 */
	public boolean deactivateTMA(String tmaId) throws MartaException {
		final String MY_NAME = ME + " deactivateTMA: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isCompanyDeactivated = false;
		boolean isTmaDeactivated = false;
		List companyIdList = null;
		
		try {
			companyIdList = deactivatePartnerAccountDAO.getCompanyId(tmaId);
			
			if(companyIdList != null && !companyIdList.isEmpty()){
				for (Iterator iter = companyIdList.iterator(); iter.hasNext();) {
					String companyId = (String) iter.next();
					
					isCompanyDeactivated = deactivateCompany(companyId);
					
					if(!isCompanyDeactivated){
						BcwtsLogger.error(MY_NAME + "company cannot be deactivated, companyId= " + companyId);
						throw new MartaException(MY_NAME + "company cannot be deactivated, companyId= " + companyId);
					}
				}
			} else {
				BcwtsLogger.debug(MY_NAME + "No Company(s) found for TMA: " + tmaId);
			}

			// Update TMA password status
			isTmaDeactivated = deactivatePartnerAccountDAO.updateTmaPasswordStatus(tmaId, Constants.TMA_PASSWORD_STATUS_BARRED);
						
		    BcwtsLogger.info(MY_NAME + " isDeactivated: " + isTmaDeactivated);
		} catch (Exception e) {
			isTmaDeactivated = false;
			BcwtsLogger.error(MY_NAME + " Exception while deactivating TMA: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception while deactivating TMA");
		}
		return isTmaDeactivated;
	}
}