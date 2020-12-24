package com.marta.admin.business;

import java.util.List;
import java.util.Iterator;

import com.marta.admin.business.NewNextfareMethods;
import com.marta.admin.dao.ListCardsDAO;
import com.marta.admin.dao.UpassDAO;
import com.marta.admin.dto.BcwtsPartnerIssueDTO;
import com.marta.admin.dto.CustomerBenefitDetailsDTO;
import com.marta.admin.dto.ListCardDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.DeactivatePartnerAccountForm;
import com.marta.admin.forms.ListCardForm;
import com.marta.admin.hibernate.BcwtPartnerNewCard;
import com.marta.admin.hibernate.PartnerHotlistHistory;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;
import com.marta.admin.hibernate.UpassActivateList;
import com.marta.admin.hibernate.UpassAdmins;
import com.marta.admin.hibernate.UpassSchools;
import com.marta.admin.hibernate.UpassNewCard;

public class ListCardsManagement {
	
	final String ME = "PSListCardsManagement: ";
	ListCardsDAO listCardsDAO = new ListCardsDAO();

	/**
	 * Get card details.
	 * 
	 * @param psListCardsForm
	 * @return cardDetailsList
	 * @throws MartaException
	 */
	public List getCardDetails(ListCardForm listCardForm) throws MartaException {
		final String MY_NAME = ME + "getCardDetails():";
		BcwtsLogger.debug(MY_NAME + " getting card details");
	
		List cardDetailsList = null;	
				
		try {
			cardDetailsList = listCardsDAO.getCardDetails(listCardForm);
			BcwtsLogger.info(MY_NAME + " got card details" + cardDetailsList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting card details :" + e.getMessage());
			throw new MartaException("Unable to find card details");
		}	
		return cardDetailsList;
	}
	
	public List getTMAIssueList() throws MartaException {
		final String MY_NAME = ME + "getTMAIssueList():";
		BcwtsLogger.debug(MY_NAME + " getting issue list");
	
		List<BcwtsPartnerIssueDTO> issueList = null;	
				
		try {
			issueList = listCardsDAO.getTMAIssueList();
			BcwtsLogger.info(MY_NAME + " got issue list" + issueList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting issue list :" + Util.getFormattedStackTrace(e));
			throw new MartaException("Unable to find issue list");
		}	
		return issueList;
	}
	
	
	public List getSchoolDetails() throws MartaException {
		final String MY_NAME = ME + "getSchoolDetails():";
		BcwtsLogger.debug(MY_NAME + " getting School details");
	    UpassDAO upassDao = new UpassDAO();
		List schoolList = null;	
				
		try {
			schoolList = upassDao.getUpassSchools();
			BcwtsLogger.info(MY_NAME + " got card details" + schoolList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting school details :" + e.getMessage());
			throw new MartaException("Unable to find school details");
		}	
		return schoolList;
	}
	
	
	/**
	 * To Get Member Benefit Details By CompanyId
	 * 
	 * @param companyId
	 * @return memberBenefitDetailsList
	 * @throws MartaException
	 */
	public List getMemberBenefitDetailsByCompanyId(String companyId) throws MartaException {
		final String MY_NAME = ME + "getMemberBenefitDetailsByCompanyId():";
		BcwtsLogger.debug(MY_NAME);
	
		List memberBenefitDetailsList = null;	
				
		try {
			memberBenefitDetailsList = listCardsDAO.getMemberBenefitDetailsByCompanyId(companyId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Member Benefit details :" + e.getMessage());
			throw new MartaException("Unable to find Member Benefit details");
		}	
		return memberBenefitDetailsList;
	}
	
	/**
	 * To Get Customer Benefit Details
	 * 
	 * @param customerId
	 * @return customerBenefitDetailsList
	 * @throws MartaException
	 */
	public List getCustomerBenefitDetails(String customerId) throws MartaException {
		final String MY_NAME = ME + "getCustomerBenefitDetails():";
		BcwtsLogger.debug(MY_NAME);
	
		List customerBenefitDetailsList = null;	
				
		try {
			customerBenefitDetailsList = listCardsDAO.getCustomerBenefitDetails(customerId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Customer Benefit details :" + e.getMessage());
			throw new MartaException("Unable to find Customer Benefit details");
		}	
		return customerBenefitDetailsList;
	}
	
	/**
	 * Update card details.
	 * 
	 * @param psListCardsDTO
	 * @return isUpdated
	 * @throws MartaException
	 */
	public boolean updateMemberInfo(ListCardDTO ListCardDTO) throws MartaException {
		final String MY_NAME = ME + "updateMemberInfo():";
		BcwtsLogger.debug(MY_NAME + " updateMemberInfo");
	
		boolean isUpdated = false;
				
		try {			
			isUpdated = NewNextfareMethods.updateMemberInfo(ListCardDTO);
			BcwtsLogger.info(MY_NAME + " isUpdated= " + isUpdated);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in updating member info :" + e.getMessage());
			throw new MartaException("Unable to update member info");
		}	
		return isUpdated;
	}
	
	/**
	 * Get benefit details.
	 * 
	 * @param psListCardsForm
	 * @return benefitDetailsList
	 * @throws MartaException
	 */
	public List getBenefitDetails(ListCardForm listCardForm) throws MartaException {
		final String MY_NAME = ME + "getBenefitsForTheCard():";
		BcwtsLogger.debug(MY_NAME + " getBenefitsForTheCard");
	
		List benefitDetailsList = null;	
				
		try {			
			benefitDetailsList = listCardsDAO.getBenefitDetails(listCardForm);			
			BcwtsLogger.info(MY_NAME + " got benefit details" + benefitDetailsList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting benefit details :" + e.getMessage());
			throw new MartaException("Unable to find benefit details");
		}	
		return benefitDetailsList;
	}
	
	
	public boolean activateUpassBenefit(ListCardDTO  psListCardsDTO) throws MartaException {
		final String MY_NAME = ME + "activateBenefit():";
		BcwtsLogger.debug(MY_NAME + " activateBenefit");
		PartnerHotlistHistory partnerHotlistHistory = null;
		com.marta.admin.hibernate.UpassActivateList upassActivateList = new UpassActivateList();
		String annualPass = null;
		boolean isActivated = false;
	
		CustomerBenefitDetailsDTO benefitDetailsDTO = null;
		UpassSchools upassschool = null;
		try {
	
			upassActivateList = listCardsDAO.getUpassActivateList(psListCardsDTO.getSerialNumber());
				
				if(upassActivateList == null) {
					upassActivateList = new UpassActivateList();
					upassActivateList.setSerialnumber(psListCardsDTO.getSerialNumber());
					upassActivateList.setActivatestatus(psListCardsDTO.getAction());
					upassActivateList.setRequestDate(Util.getSystemdate());
					upassActivateList.setFirstname(psListCardsDTO.getFirstName());
					upassActivateList.setLastname(psListCardsDTO.getLastName());
					upassActivateList.setMemberid(psListCardsDTO.getMemberId());
					upassActivateList.setEmail(psListCardsDTO.getEmailId());
					upassActivateList.setPhonenumber(psListCardsDTO.getPhoneNumber());
					upassActivateList.setBenefitid(psListCardsDTO.getBenefitId());
					upassActivateList.setNfsid(psListCardsDTO.getCompanyId());
					upassActivateList.setCustomermemberid(psListCardsDTO.getCustomerMemberId());
					upassschool = listCardsDAO.getSchoolByNextfareId(psListCardsDTO.getCompanyId());
					
					UpassAdmins upassadmin = new UpassAdmins();
					upassadmin.setUpassadminid(new Long(999));
					upassActivateList.setUpassadmin(upassadmin);
					upassActivateList.setUpassschool(upassschool);
				} else {
					upassActivateList.setActivatestatus(Constants.YES);
					upassActivateList.setRequestDate(Util.getSystemdate());
					
				}
				
				isActivated = listCardsDAO.activateDeactivateBenefit(upassActivateList);
		
						
			BcwtsLogger.info(MY_NAME + " isActivated= " + isActivated);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in activating benefit :" + Util.getFormattedStackTrace(e));
			throw new MartaException("Unable to activate benefit");
		}

		return isActivated;
	}
	
	
	/**
	 * Activate Benefit.
	 * 
	 * @param psListCardsDTO
	 * @return isActivated
	 * @throws MartaException
	 */
	public boolean activateBenefit(ListCardDTO listCardDTO) throws MartaException {
		final String MY_NAME = ME + "activateBenefit():";
		BcwtsLogger.debug(MY_NAME + " activateBenefit");
		PartnerHotlistHistory partnerHotlistHistory = null;
		boolean isActivated = false;
		CustomerBenefitDetailsDTO benefitDetailsDTO = null;
		
		try {
			
			List custBenefitList = getCustomerBenefitDetails(listCardDTO.getCustomerId());
			for(Iterator iter = custBenefitList.iterator();iter.hasNext();){
				benefitDetailsDTO = (CustomerBenefitDetailsDTO) iter.next();
			
			if(benefitDetailsDTO.getFare_instrument_id().equals(Constants.ANNUAL_PASS_BENEFIT_ID)) {
				partnerHotlistHistory = listCardsDAO.getPartnerHotlistHistory(listCardDTO.getSerialNumber());
				
				if(partnerHotlistHistory == null) {
					partnerHotlistHistory = new PartnerHotlistHistory();
					partnerHotlistHistory.setCardNumber(listCardDTO.getSerialNumber());
					partnerHotlistHistory.setHotlistFlag(Constants.NO);
					partnerHotlistHistory.setUnhotlistFlag(Constants.YES);
					partnerHotlistHistory.setHotlistReqDate(null);
					partnerHotlistHistory.setUnhotlistReqDate(Util.getSystemdate());
					partnerHotlistHistory.setFirstName(listCardDTO.getFirstName());
					partnerHotlistHistory.setLastName(listCardDTO.getLastName());
					partnerHotlistHistory.setMemberId(listCardDTO.getMemberId());
					partnerHotlistHistory.setEmail(listCardDTO.getEmailId());
					partnerHotlistHistory.setPhone(listCardDTO.getPhoneNumber());
					partnerHotlistHistory.setBenefitId(listCardDTO.getBenefitId());
					partnerHotlistHistory.setCompanyName(listCardDTO.getCompanyName());
				} else {
					partnerHotlistHistory.setHotlistFlag(Constants.NO);
					partnerHotlistHistory.setUnhotlistFlag(Constants.YES);
					partnerHotlistHistory.setHotlistReqDate(null);
					partnerHotlistHistory.setUnhotlistReqDate(Util.getSystemdate());
				}
				
				isActivated = listCardsDAO.activateDeactivateBenefit(partnerHotlistHistory);
			} /*else {
				isActivated = NewNextfareMethods.removeHotlist(listCardDTO.getSerialNumber());
			}*/
			}			
			BcwtsLogger.info(MY_NAME + " isActivated= " + isActivated);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in activating benefit :" + e.getMessage());
			throw new MartaException("Unable to activate benefit");
		}	
		return isActivated;
	}
	
	/**
	 * DeActivate Benefit.
	 * 
	 * @param psListCardsDTO
	 * @return isDeActivated
	 * @throws MartaException
	 */
	public boolean deactivateBenefit(ListCardDTO listCardDTO) throws MartaException {
		final String MY_NAME = ME + "deactivateBenefit():";
		BcwtsLogger.debug(MY_NAME + " deactivateBenefit");
		PartnerHotlistHistory partnerHotlistHistory = null;
		boolean isDeActivated = false;
		CustomerBenefitDetailsDTO benefitDetailsDTO = null;
		
		try {
			List custBenefitList = getCustomerBenefitDetails(listCardDTO.getCustomerId());		
			for(Iterator iter = custBenefitList.iterator();iter.hasNext();){
				benefitDetailsDTO = (CustomerBenefitDetailsDTO) iter.next();
			
			if(benefitDetailsDTO.getFare_instrument_id().equals(Constants.ANNUAL_PASS_BENEFIT_ID)) {
				
				partnerHotlistHistory = listCardsDAO.getPartnerHotlistHistory(listCardDTO.getSerialNumber());
				
				if(partnerHotlistHistory == null) {	
					partnerHotlistHistory = new PartnerHotlistHistory();
					partnerHotlistHistory.setCardNumber(listCardDTO.getSerialNumber());
					partnerHotlistHistory.setHotlistFlag(Constants.YES);
					partnerHotlistHistory.setUnhotlistFlag(Constants.NO);
					partnerHotlistHistory.setHotlistReqDate(Util.getSystemdate());
					partnerHotlistHistory.setUnhotlistReqDate(null);
					partnerHotlistHistory.setFirstName(listCardDTO.getFirstName());
					partnerHotlistHistory.setLastName(listCardDTO.getLastName());
					partnerHotlistHistory.setMemberId(listCardDTO.getMemberId());
					partnerHotlistHistory.setEmail(listCardDTO.getEmailId());
					partnerHotlistHistory.setPhone(listCardDTO.getPhoneNumber());
					partnerHotlistHistory.setBenefitId(listCardDTO.getBenefitId());
					partnerHotlistHistory.setCompanyName(listCardDTO.getCompanyName());
				} else {
					partnerHotlistHistory.setHotlistFlag(Constants.YES);
					partnerHotlistHistory.setUnhotlistFlag(Constants.NO);
					partnerHotlistHistory.setHotlistReqDate(Util.getSystemdate());
					partnerHotlistHistory.setUnhotlistReqDate(null);
				}
				
				isDeActivated = listCardsDAO.activateDeactivateBenefit(partnerHotlistHistory);
			}/* else {
				isDeActivated = NextFareMethods.deactivateBenefit(listCardDTO);
			}*/
			}
			BcwtsLogger.info(MY_NAME + " isDeActivated= " + isDeActivated);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in deactivating benefit :" + e.getMessage());
			throw new MartaException("Unable to deactivate benefit");
		}	
		return isDeActivated;
	}
	
	/**
	 * Replace Cards (Serial No).
	 * 
	 * @param psListCardsDTO
	 * @return isDeActivated
	 * @throws MartaException
	 */
	public boolean replaceCards(ListCardDTO listCardDTO) throws MartaException {
		final String MY_NAME = ME + "replaceCards():";
		BcwtsLogger.debug(MY_NAME + " replaceCards");
	
		boolean isReplaced = false;
				
		try {			
			isReplaced = NewNextfareMethods.replaceSerialNumber(listCardDTO);		
			BcwtsLogger.info(MY_NAME + " isReplaced= " + isReplaced);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in replacing card :" + e.getMessage());
			throw new MartaException("Unable to replace card");
		}	
		return isReplaced;
	}	
		
	/**
	 * Hotlist Cards
	 * 
	 * @param psListCardsDTO
	 * @return isHotlisted
	 * @throws MartaException
	 */
	public boolean hotlistCards(ListCardDTO listCardDTO) throws MartaException {
		final String MY_NAME = ME + "hotlistCards():";
		BcwtsLogger.debug(MY_NAME + " hotlistCards");
	
		boolean isHotlisted = false;
				
		try {			
			isHotlisted = NewNextfareMethods.hotlistCards(listCardDTO);
			BcwtsLogger.info(MY_NAME + " isHotlisted= " + isHotlisted);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in hotlisting card :" + e.getMessage());
			throw new MartaException("Unable to hotlist card");
		}	
		return isHotlisted;
	}	
	
	/**
	 * To save hotlisted cards details
	 * 
	 * @param psListCardsDTO
	 * @return isSuccess
	 * @throws MartaException
	 */
	public boolean saveHotlistedCards(ListCardDTO listCardDTO, Long partnerId) throws MartaException {
		final String MY_NAME = ME + "saveHotlistedCards():";
		BcwtsLogger.debug(MY_NAME + " saveHotlistedCards");
		boolean isSuccess = false;
				
		try {		
			isSuccess = listCardsDAO.saveHotlistedCards(listCardDTO, partnerId);
			BcwtsLogger.info(MY_NAME + " isSuccess= " + isSuccess);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while saving hotlisted cards :" + e.getMessage());
			throw new MartaException("Unable to save hotlisted card");
		}	
		return isSuccess;
	}
	
	public BcwtsPartnerIssueDTO updateIssue(BcwtsPartnerIssueDTO bcwtsPartnerIssueDTO) throws MartaException {
		final String MY_NAME = ME + "updateIssue():";
		BcwtsLogger.debug(MY_NAME + " updateIssue");
		boolean isSuccess = false;
		BcwtsPartnerIssueDTO bcwtsPartnerIssueDTOReply = new BcwtsPartnerIssueDTO();		
		try {		
			bcwtsPartnerIssueDTOReply = listCardsDAO.updateIssue(bcwtsPartnerIssueDTO);
			BcwtsLogger.info(MY_NAME + " isSuccess= " + isSuccess);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while updateIssue :" + e.getMessage());
			throw new MartaException("Unable to updateIssue");
		}	
		return bcwtsPartnerIssueDTOReply;
	}
	
	
	/**
	 * Add Threshold
	 * 
	 * @param psListCardsDTO
	 * @return isThresholdAdded
	 * @throws MartaException
	 */
	public boolean addThreshold(ListCardDTO listCardDTO) throws MartaException {
		final String MY_NAME = ME + "addThreshold():";
		BcwtsLogger.debug(MY_NAME + " addThreshold");
	
		boolean isThresholdAdded = false;
				
		try {			
		//	isThresholdAdded = NextFareMethods.addThreshold(listCardDTO);
			BcwtsLogger.info(MY_NAME + " isThresholdAdded= " + isThresholdAdded);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in adding Threshold :" + e.getMessage());
			throw new MartaException("Unable to add Threshold");
		}	
		return isThresholdAdded;
	}	
	
	/**
	 * Add Recurring
	 * 
	 * @param psListCardsDTO
	 * @return isRecurringAdded
	 * @throws MartaException
	 */
	public boolean addRecurring(ListCardDTO listCardDTO) throws MartaException {
		final String MY_NAME = ME + "addRecurring():";
		BcwtsLogger.debug(MY_NAME + " addRecurring");
	
		boolean isRecurringAdded = false;
				
		try {			
		//	isRecurringAdded = NextFareMethods.addRecurring(listCardDTO);
			BcwtsLogger.info(MY_NAME + " isRecurringAdded= " + isRecurringAdded);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in adding Recurring :" + e.getMessage());
			throw new MartaException("Unable to add Recurring");
		}	
		return isRecurringAdded;
	}	
	
	/**
	 * Add new card
	 * 
	 * @param psListCardsDTO
	 * @return isCardAdded
	 * @throws MartaException
	 */
	public boolean addNewCard(ListCardDTO listCardDTO) throws MartaException {
		final String MY_NAME = ME + "addNewCard():";
		BcwtsLogger.debug(MY_NAME + " addNewCard");
	
		boolean isCardAdded = false;
		BcwtPartnerNewCard bcwtPartnerNewCard = new BcwtPartnerNewCard();
		
		try {
			bcwtPartnerNewCard.setCustomermemberid(listCardDTO.getCustomerMemberId());
			bcwtPartnerNewCard.setSerialnumber(listCardDTO.getNewSerialNumber());
			bcwtPartnerNewCard.setFirstname(listCardDTO.getFirstName());
			bcwtPartnerNewCard.setLastname(listCardDTO.getLastName());
			bcwtPartnerNewCard.setEmail(listCardDTO.getEmailId());
			bcwtPartnerNewCard.setPhoneno(listCardDTO.getPhoneNumber());
			bcwtPartnerNewCard.setBenefitid(Long.valueOf(listCardDTO.getBenefitId()));
	
			bcwtPartnerNewCard.setCompanyid(Long.valueOf(listCardDTO.getCompanyId()));
			bcwtPartnerNewCard.setCompanyname(listCardDTO.getCompanyName());
	
			isCardAdded = listCardsDAO.addNewCard(bcwtPartnerNewCard);
			
			BcwtsLogger.info(MY_NAME + " isCardAdded= " + isCardAdded);
		} catch (Exception e) {
			isCardAdded = false;
			BcwtsLogger.error(MY_NAME + " Exception in adding new card :" + e.getMessage());
			throw new MartaException("Unable to add new card");
		}	
		return isCardAdded;
	}	
	
	/**
	 * To check whether member id exist or not
	 * 
	 * @param memberId
	 * @return isExist
	 * @throws MartaException
	 */
	public boolean isMemberIdExist(String memberId, String companyId) throws MartaException {
		final String MY_NAME = ME + "isMemberIdExist():";
		BcwtsLogger.debug(MY_NAME);
		boolean isExist = false;
				
		try {			
			isExist = listCardsDAO.isMemberIdExist(memberId, companyId);
			BcwtsLogger.info(MY_NAME + " isExist= " + isExist);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while checking member Id :" + e.getMessage());
			throw new MartaException("Unable to check Member Id in Member table");
		}	
		return isExist;
	}	
	
	/**
	 * To check whether serial no exist or not
	 * 
	 * @param serialNumber
	 * @return isExist
	 * @throws MartaException
	 */
	public boolean isSerialNoExist(String serialNumber) throws MartaException {
		final String MY_NAME = ME + "isSerialNoExist():";
		BcwtsLogger.debug(MY_NAME);
		boolean isExist = false;
				
		try {			
			isExist = listCardsDAO.isSerialNoExist(serialNumber);
			BcwtsLogger.info(MY_NAME + " isExist= " + isExist);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while checking serial number :" + e.getMessage());
			throw new MartaException("Unable to check Serial Number");
		}	
		return isExist;
	}
	
	/**
	 * To check whether the card is hotlisted or not
	 * 
	 * @param serialNumber
	 * @return isHotlisted
	 * @throws MartaException
	 */
	public boolean isCardHotlisted(String serialNumber) throws MartaException {
		final String MY_NAME = ME + "isCardHotlisted():";
		BcwtsLogger.debug(MY_NAME);
		boolean isHotlisted = false; 
				
		try {			
			isHotlisted = listCardsDAO.isCardHotlisted(serialNumber);
			BcwtsLogger.info(MY_NAME + " isHotlisted= " + isHotlisted);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while checking serial number :" + e.getMessage());
			throw new MartaException("Unable to check Serial Number");
		}	
		return isHotlisted;
	}	
	
	/**
	 *  To see if the card is associated with any other customer
	 * 
	 * @param serialNumber
	 * @param customerId
	 * @return isCardAssociated
	 * @throws MartaException
	 */
	public boolean isCardAssociated(String serialNumber, String customerId, String memberId) 
	throws MartaException {
		final String MY_NAME = ME + "isCardAssociated():";
		BcwtsLogger.debug(MY_NAME);
		boolean isCardAssociated = false; 
				
		try {			
			isCardAssociated = listCardsDAO.isCardAssociated(serialNumber, customerId, memberId);
			BcwtsLogger.info(MY_NAME + " isCardAssociated= " + isCardAssociated);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while checking card association :" + e.getMessage());
			throw new MartaException("Unable to check Serial Number for card association");
		}	
		return isCardAssociated;
	}
	
	public boolean isCorrectRiderClass(String serialNumber, String customerId) 
	throws MartaException {
		final String MY_NAME = ME + "isCorrectRiderClass():";
		BcwtsLogger.debug(MY_NAME);
		boolean isCorrectRiderClass = false; 
				
		try {			
			isCorrectRiderClass = listCardsDAO.isCorrectRiderClass(serialNumber, customerId);
			BcwtsLogger.info(MY_NAME + " isCorrectRiderClass= " + isCorrectRiderClass);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while checking rider class :" + e.getMessage());
			throw new MartaException("Unable to check Serial Number for card association");
		}	
		return isCorrectRiderClass;
	}
	
	
	
	/**
	 * To check whether the card is exist in BCWTPARTNERNEWCARD Table
	 * 
	 * @param serialNumber
	 * @return isCardExist
	 * @throws Exception
	 */
	public boolean isCardExistInPartnerNewCardTable(String serialNumber) throws MartaException {
		final String MY_NAME = ME + "isCardExistInPartnerNewCardTable():";
		BcwtsLogger.debug(MY_NAME);
		boolean isCardExist = false;
				
		try {			
			isCardExist = listCardsDAO.isCardExistInPartnerNewCardTable(serialNumber);
			BcwtsLogger.info(MY_NAME + " isCardExist= " + isCardExist);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while checking card in BCWTPARTNERNEWCARD Table :" + e.getMessage());
			throw new MartaException("Unable to check card in BCWTPARTNERNEWCARD Table");
		}	
		return isCardExist;
	}
	
	/**
	 * To check whether the card is exist in PARTNER_HOTLIST_HISTORY Table
	 * 
	 * @param serialNumber
	 * @return isCardExist
	 * @throws Exception
	 */
	public boolean isCardExistInPartnerHistoryTable(String serialNumber) throws MartaException {
		final String MY_NAME = ME + "isCardExistInPartnerHistoryTable():";
		BcwtsLogger.debug(MY_NAME);
		boolean isCardExist = false;
				
		try {			
			isCardExist = listCardsDAO.isCardExistInPartnerHistoryTable(serialNumber);
			BcwtsLogger.info(MY_NAME + " isCardExist= " + isCardExist);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while checking card in PARTNER_HOTLIST_HISTORY Table :" + e.getMessage());
			throw new MartaException("Unable to check card in PARTNER_HOTLIST_HISTORY Table");
		}	
		return isCardExist;
	}	
	
	/**
	 * To check whether the card is exist in PARTNER_HOTLIST_HISTORY Table
	 * 
	 * @param serialNumber
	 * @return isCardExist
	 * @throws Exception
	 */
	public List getNewCardDetails() throws MartaException {
		final String MY_NAME = ME + "getNewCardDetails():";
		BcwtsLogger.debug(MY_NAME);
		List newCardDetailsList = null;
				
		try {			
			newCardDetailsList = listCardsDAO.getNewCardDetails();
			BcwtsLogger.info(MY_NAME + " List Card size is : " + newCardDetailsList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while fetching bcwtpartnernewcard Table :" + e.getMessage());
			throw new MartaException("Unable to fetching bcwtpartnernewcard Table");
		}	
		return newCardDetailsList;
	}
	/**
	 * Get Company details.
	 * 
	 * @param listCardForm
	 * @return companyIdList
	 * @throws MartaException
	 */
	public List getCompanyID(ListCardForm listCardForm) throws MartaException {
		final String MY_NAME = ME + "getCompanyID():";
		BcwtsLogger.debug(MY_NAME + " getting Company details");
	
		List companyIdList = null;	
				
		try {
			companyIdList = listCardsDAO.getCompanyId(listCardForm);
			BcwtsLogger.info(MY_NAME + " got company details" + companyIdList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting company details :" + e.getMessage());
			throw new MartaException("Unable to find company details");
		}	
		return companyIdList;
	}
	
	/**
	 * Get Partner 
	 */
	public List getPartnerDetails(ListCardForm listCardForm)
	throws MartaException {
		final String MY_NAME = ME + " getPartnerDetails: ";
		BcwtsLogger.debug(MY_NAME);
		List partnerDetailsList = null;
	
		try {
			partnerDetailsList = listCardsDAO.getPartnerDetails(listCardForm);
			
			if(partnerDetailsList != null){
			    BcwtsLogger.info(MY_NAME + " got partnerDetails List: " + partnerDetailsList.size());
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting partnerDetails List: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting partnerDetails List");
		}
		return partnerDetailsList;
	}
	/*
	 * to get TMA Details
	 * 
	 */
	public List getTMAPartnerDetails(ListCardForm listCardForm)
	throws MartaException {
		final String MY_NAME = ME + " getTMAPartnerDetails: ";
		BcwtsLogger.debug(MY_NAME);
		List tmaPartnerDetailsList = null;
	
		try {
			tmaPartnerDetailsList = listCardsDAO.getTMAPartnerDetails(listCardForm);
			
			if(tmaPartnerDetailsList != null){
			    BcwtsLogger.info(MY_NAME + " got partnerDetails List: " + tmaPartnerDetailsList.size());
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting partnerDetails List: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting partnerDetails List");
		}
		return tmaPartnerDetailsList;
	}
	
	/*
	 * to get TMA Details
	 * 
	 */
	public List getTmaCompanyList(String tmaId)
	throws MartaException {
		final String MY_NAME = ME + " getTmaCompanyList: ";
		BcwtsLogger.debug(MY_NAME);
		List tmaCompanyList = null;
	
		try {
			tmaCompanyList = listCardsDAO.getTmaCompanyList(tmaId);
			
			if(tmaCompanyList != null){
			    BcwtsLogger.info(MY_NAME + " got partnerDetails List: " + tmaCompanyList.size());
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting partnerDetails List: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting partnerDetails List");
		}
		return tmaCompanyList;
	}
	/**
	 * To Get Member Benefit Details By CompanyId
	 * 
	 * @param companyId
	 * @return memberBenefitDetailsList
	 * @throws MartaException
	 */
	public List getTmaMemberBenefitDetailsByCompanyId(String companyId,String memberId) throws MartaException {
		final String MY_NAME = ME + "getTmaMemberBenefitDetailsByCompanyId():";
		BcwtsLogger.debug(MY_NAME);
	
		List memberBenefitDetailsList = null;	
				
		try {
			memberBenefitDetailsList = listCardsDAO.getTmaMemberBenefitDetailsByCompanyId(companyId, memberId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Member Benefit details :" + e.getMessage());
			throw new MartaException("Unable to find Member Benefit details");
		}	
		return memberBenefitDetailsList;
	}
	

	public boolean isUpass(String customerId){
		boolean isUpass = true;
		
		try {
			UpassSchools upassSchool = listCardsDAO.getSchoolByNextfareId(customerId);
			
			if(Util.isBlankOrNull(upassSchool.getSchoolname())){
				isUpass=false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			BcwtsLogger.error(Util.getFormattedStackTrace(e));
		}
		
		return isUpass;
	}
	
	public boolean addUpassNewCard(ListCardDTO  psListCardsDTO) throws MartaException {
		final String MY_NAME = ME + "addNewCard";
		BcwtsLogger.debug(MY_NAME + " addNewCard");

		boolean isCardAdded = false;
		UpassNewCard upassNewCard = new UpassNewCard();
		
		try {
			upassNewCard.setCustomermemberid(psListCardsDTO.getCustomerMemberId());
			upassNewCard.setSerialnumber(psListCardsDTO.getNewSerialNumber());
			upassNewCard.setFirstname(psListCardsDTO.getFirstName());
			upassNewCard.setLastname(psListCardsDTO.getLastName());
			upassNewCard.setEmail(psListCardsDTO.getEmailId());
			upassNewCard.setPhoneno(psListCardsDTO.getPhoneNumber());
			upassNewCard.setBenefitid(Long.valueOf(psListCardsDTO.getBenefitId()));

			upassNewCard.setNextfareid(Long.valueOf(psListCardsDTO.getCompanyId()));
			upassNewCard.setSchoolname("School");

			isCardAdded = listCardsDAO.addNewUpassCard(upassNewCard);
			
			BcwtsLogger.info(MY_NAME + " isCardAdded= " + isCardAdded);
		} catch (Exception e) {
			isCardAdded = false;
			BcwtsLogger.error(MY_NAME + " Exception in adding new card :" + e.getMessage());
			throw new MartaException("Unable to add new card");
		}	
		return isCardAdded;
	}	
	

}
