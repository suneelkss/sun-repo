package com.marta.admin.business;

import java.util.List;

import org.apache.struts.actions.DispatchAction;
import com.marta.admin.dao.ManagePartnerRegistrationDAO;
import com.marta.admin.dto.BcwtCompanyStatusDTO;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BcwtManagePartnerRegistrationDTO;
import com.marta.admin.dto.BcwtPartnerRegistrationRequestDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.SendMail;
import com.marta.admin.utils.Util;

public class ManagePartnerRegistrationBusiness extends DispatchAction{
		
	final String ME = "ManagePartnerRegistration";
	
	public List getStatus()throws MartaException {
		final String MY_NAME = ME + " getPartnerRegistrationStatus : ";
		BcwtsLogger.debug(MY_NAME + " getting Partner Registration Status Details ");
		
		ManagePartnerRegistrationDAO managePartnerRegistrationDAO
						= new ManagePartnerRegistrationDAO();
		List statusList = null;
		
		try {	
			statusList = managePartnerRegistrationDAO.getStatus();
			BcwtsLogger.info(MY_NAME + " got Manage Partner Registration Status Details:");					
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Manage Partner" +
				" Registration Status details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		
		return statusList;
	}
	
	public List getManagePartnerRegistrationDetails()throws MartaException {
		final String MY_NAME = ME + " getPartnerRegistrationDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting Partner Registration Details ");
		ManagePartnerRegistrationDAO managePartnerRegistrationDAO
							= new ManagePartnerRegistrationDAO();
		List managePartnerRegistrationDetailsList = null;
		
		try {
		//	managePartnerRegistrationDetailsList = new ArrayList();
			managePartnerRegistrationDetailsList = managePartnerRegistrationDAO.
				getManagePartnerRegistrationDetails();
			BcwtsLogger.info(MY_NAME + " got Manage Partner Registration Details:");					
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Manage Partner Registration details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		return managePartnerRegistrationDetailsList;
	}
	
	public List getSearchPartnerRegistrationDetails(BcwtManagePartnerRegistrationDTO
		managePartnerRegistrationDTO)throws MartaException {
		final String MY_NAME = ME + " getSearchPartnerRegistrationDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting Search Partner Registration Details ");

		ManagePartnerRegistrationDAO searchPartnerRegistrationDAO 
			= new ManagePartnerRegistrationDAO();
		List searchPartnerRegistrationDetailsList = null;

		try {
		//	searchPartnerRegistrationDetailsList = new ArrayList();
			searchPartnerRegistrationDetailsList   = (List)searchPartnerRegistrationDAO
				.getSerachPartnerRegistrationDetails(managePartnerRegistrationDTO);
			BcwtsLogger.info(MY_NAME + " got Search Partner Registration Details:");

		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Search " +
				"Partner Registration details :"+ e.getMessage());
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		return searchPartnerRegistrationDetailsList;
	}
	
	public List getPartnerRegistrationRequestDetails(String regReqId)			
		throws MartaException {
		final String MY_NAME = ME + " getPartnerRegistrationDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting Partner Registration Details ");

		ManagePartnerRegistrationDAO partnerRegistrationDAO 
			= new ManagePartnerRegistrationDAO();
		List partnerRegistrationDetailsList = null;
		
		try {			
			partnerRegistrationDetailsList =
				partnerRegistrationDAO.getPartnerRegistrationRequestDetails(regReqId);
			
			BcwtsLogger.info(MY_NAME + " got Manage Partner Registration Details:");
		}catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Manage" +
				" Partner Registration details :"+ e.getMessage());
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		return partnerRegistrationDetailsList;
	}
	
	
	public boolean addPartnerRegistrationRequestDetails(BcwtPartnerRegistrationRequestDTO
			partnerRegistrationDTO)throws Exception{
		final String MY_NAME = ME + " addPartnerRegistrationDetails: ";
		BcwtsLogger.debug(MY_NAME + " Adding Partner Registration Details ");
		boolean isUpdated = false;
		ManagePartnerRegistrationDAO addpartnerRegistrationDAO 
			= new ManagePartnerRegistrationDAO();
		
		try {
			isUpdated = addpartnerRegistrationDAO.
					addPartnerRegistrationRequestDetails(partnerRegistrationDTO);
			BcwtsLogger.info(MY_NAME + " Added Manage Partner Registration Details:");
			
		}catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in Adding Manage" +
				" Partner Registration details :"+ e.getMessage());
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		return isUpdated;
	}
	
	public List getWelcomePartnerRegistrationListDetails()throws MartaException {
		final String MY_NAME = ME + " getWelcomeRegistrationListDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting Welcome Page Partner " +
				"Registration Details ");
		ManagePartnerRegistrationDAO welcomePartnerRegistrationDAO 
				= new ManagePartnerRegistrationDAO();
		List welcomePartnerRegistrationDetailsList = null;

		try {
			welcomePartnerRegistrationDetailsList = welcomePartnerRegistrationDAO.
							getWelcomePartnerRegistrationListDetails();
			BcwtsLogger.info(MY_NAME + " got Welcome Page Manage " +
				"Partner Registration Details:");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Welcome Page Manage" +
				" Partner Registration details :"+ e.getMessage());
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		
		return welcomePartnerRegistrationDetailsList;
	}
	public List getCompanyStatusDetails()			
									throws MartaException {
		final String MY_NAME = ME + " getCompanyStatusDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting Company Status Details ");
	
		ManagePartnerRegistrationDAO companyStatusDAO 
							= new ManagePartnerRegistrationDAO();
		List companyStatusDetailsList = null;
		
		try {
		
			companyStatusDetailsList =
							companyStatusDAO.getCompanyStatusDetails();
			
			BcwtsLogger.info(MY_NAME + " got Manage Partner Registration Details:");
		}catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Manage" +
							" Partner Registration details :"+ e.getMessage());
			throw new MartaException(PropertyReader
							.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		return companyStatusDetailsList;
	}
	public List getCompanyStatusInformationDetails(String companyName)
											throws MartaException {
		final String MY_NAME = ME + " getCompanyStatusInformationDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting Company Status Information Details ");
	
		ManagePartnerRegistrationDAO companyStatusDAO 
							= new ManagePartnerRegistrationDAO();
		List companyStatusInformationList = null;
		
		try {
			companyStatusInformationList =
							companyStatusDAO.getCompanyStatusInformationDetails(companyName);
			
			BcwtsLogger.info(MY_NAME + " got Company Status Information Details:");
		}catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Company" +
							" Status Information details :"+ e.getMessage());
			throw new MartaException(PropertyReader
							.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		return companyStatusInformationList;
	}
	
	public List getCompanyStatus()throws MartaException {
		final String MY_NAME = ME + " getCompanyStatus : ";
		BcwtsLogger.debug(MY_NAME + " getting Company Status Details ");
		
		ManagePartnerRegistrationDAO managePartnerRegistrationDAO
						= new ManagePartnerRegistrationDAO();
		List companyStatusList = null;
		
		try {
			companyStatusList = managePartnerRegistrationDAO.getCompanyStatus();
			BcwtsLogger.info(MY_NAME + " got Company Status Details:");					
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Company" +
					" Status List details  from DB:"
					+ e.getMessage());
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		
		return companyStatusList;
	}
	
	public boolean addCompanyStatusInformationDetails(BcwtCompanyStatusDTO companyStatusDTO,String basePath)
					throws MartaException{
		final String MY_NAME = ME + " addCompanyStatusInformation: ";
		BcwtsLogger.debug(MY_NAME + " Adding Company Status Information Details ");
		boolean isUpdated = false;
		String activeStatus = null;		
		String sendMail = null;
		ManagePartnerRegistrationDAO addCompanyStatusInformationDAO = null;
								
		
		try {
			addCompanyStatusInformationDAO = new ManagePartnerRegistrationDAO();
			activeStatus = companyStatusDTO.getCompanyStatus();		
			isUpdated = addCompanyStatusInformationDAO.
						addCompanyStatusInformationDetails(companyStatusDTO);
			BcwtsLogger.info(MY_NAME + " Added Company Status Information  Details:");
			
			if(isUpdated == true && activeStatus.equalsIgnoreCase("203") ){
				BcwtsLogger.debug(MY_NAME + "starting sending mail....");
				ConfigurationCache configurationCache = new ConfigurationCache();
				BcwtConfigParamsDTO smtpPathDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
				.get("SMTP_SERVER_PATH");
				BcwtsLogger.debug(MY_NAME + "smtp path : "
						+ smtpPathDTO.getParamvalue());
				BcwtConfigParamsDTO fromDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
				.get("WEBMASTER_MAIL_ID");
				BcwtsLogger.debug(MY_NAME + "From Id : "
						+ fromDTO.getParamvalue());
				BcwtConfigParamsDTO mailportDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
				.get(Constants.MAIL_PORT);
				BcwtConfigParamsDTO mailprotocolDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
				.get(Constants.MAIL_PROTOCOL);
				String content = PropertyReader
				.getValue(Constants.NEW_COMPANY_STATUS_MAIL_CONTENT);
				String imgPath = basePath + Constants.MARTA_LOGO;
				String imageContent = "<img src='" + imgPath + "'/>";
				content = content.replaceAll("\\$LOGOIMAGE", imageContent);
				String subject = PropertyReader
				.getValue(Constants.NEW_COMPANY_STATUS_MAIL_SUBJECT);

				BcwtsLogger.debug(MY_NAME + "subject :" + subject
						+ "userEmail :" + companyStatusDTO.getEmail());
				try {

					SendMail.sendMail(fromDTO.getParamvalue(), companyStatusDTO.getEmail(),
							content, smtpPathDTO.getParamvalue(), subject,
							"", mailportDTO.getParamvalue(),
							mailprotocolDTO.getParamvalue());
					BcwtsLogger.info(MY_NAME + "Mail sent sucess");					
				} catch (Exception e) {
					BcwtsLogger.error(MY_NAME + " Exception while sending mail :"
							+ Util.getFormattedStackTrace(e));
					
				}
				
			}
		}catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in Adding Company  " +
				" Status Information details :"+ Util.getFormattedStackTrace(e));
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		return isUpdated;
	}	
}
