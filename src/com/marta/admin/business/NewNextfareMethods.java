package com.marta.admin.business;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.rpc.ServiceException;

import com.cubic.cts.webservices.client.HotlistApi;
import com.cubic.cts.webservices.client.HotlistApiPort;
import com.cubic.cts.webservices.client.HotlistApi_Impl;

import com.cubic.ncs.webservices.WSAddressVO;
import com.cubic.ncs.webservices.WSNameVO;
import com.cubic.ncs.webservices.WSPhoneVO;
import com.cubic.ncs.webservices.autoload.AutoloadApiPort;
import com.cubic.ncs.webservices.autoload.AutoloadApi_Impl;
import com.cubic.ncs.webservices.autoload.WSDirectedAutoloadRequestVO;
import com.cubic.ncs.webservices.autoload.WSDirectedAutoloadResponseVO;
import com.cubic.ncs.webservices.cardprodmgmt.WSReplaceCardRequestVO;
import com.cubic.ncs.webservices.cardprodmgmt.WSRequestCardReplacementRequestVO;
import com.cubic.ncs.webservices.client.CustomerServiceApi;
import com.cubic.ncs.webservices.client.CustomerServiceApiPort;
import com.cubic.ncs.webservices.client.CustomerServiceApi_Impl;
import com.cubic.ncs.webservices.client.PatronOrderApi;
import com.cubic.ncs.webservices.client.PatronOrderApiPort;
import com.cubic.ncs.webservices.client.PatronOrderApi_Impl;
import com.cubic.ncs.webservices.custserv.WSCreditCardReferenceVO;
import com.cubic.ncs.webservices.custserv.WSGetBillingInfoRequestVO;
import com.cubic.ncs.webservices.custserv.WSGetBillingInfoResponseVO;
import com.cubic.ncs.webservices.custserv.WSUpdateBillingInfoRequestVO;
import com.cubic.ncs.webservices.custserv.WSUpdateCardRegistrationRequestVO;
import com.cubic.ncs.webservices.hotlist.WSGetHotlistStateRequestVO;
import com.cubic.ncs.webservices.hotlist.WSGetHotlistStateResponseVO;
import com.cubic.ncs.webservices.hotlist.WSHotlistCardRequestVO;
import com.cubic.ncs.webservices.hotlist.WSRemoveFromHotlistRequestVO;
import com.cubic.ncs.webservices.ppb.PpbApi;
import com.cubic.ncs.webservices.ppb.PpbApiPort;
import com.cubic.ncs.webservices.ppb.PpbApi_Impl;
import com.cubic.ncs.webservices.ppb.WSAddNewMemberRequestVO;
import com.cubic.ncs.webservices.ppb.WSChangeMemberStatusRequestVO;
import com.cubic.ncs.webservices.ppb.WSCustomerPK;
import com.cubic.ncs.webservices.ppb.WSMemberBenefitRequestVO;
import com.cubic.ncs.webservices.ppb.WSMemberPK;
import com.cubic.ncs.webservices.ppb.WSUpdateMemberRequestVO;
import com.marta.admin.dao.ListCardsDAO;
import com.marta.admin.dto.ListCardDTO;


import com.marta.admin.dto.BatchProcessDataDTO;
import com.marta.admin.dto.CustomerBenefitDetailsDTO;
import com.marta.admin.dto.MemberDTO;


import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class NewNextfareMethods {

	public static boolean isHotlisted(String serialNumber) {

		boolean res = false;
		BcwtsLogger.info("Checking Hotlist Status for Card: " + serialNumber);
		try {
			String wsdlUrl = PropertyReader.getValue("QA_HOTLIST");
			HotlistApi service = new HotlistApi_Impl(wsdlUrl);
			HotlistApiPort port = service.getHotlistApiPort();
			WSGetHotlistStateRequestVO requestVo = new WSGetHotlistStateRequestVO();
			requestVo.setSerialNumber(serialNumber);
			WSGetHotlistStateResponseVO responseVo = new WSGetHotlistStateResponseVO();
			responseVo = port.getHotlistState(requestVo);

			res = responseVo.isHotlisted();

		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error("Error: " + Util.getFormattedStackTrace(e));

		}

		BcwtsLogger.info("Is Hotlist :" + res);
		return res;

	}

	public static String hotlistCard(String serialNumber) {

		// NextFareLogger.info(Class + "." + method + "Hotlisting card:"
		// + serialNumber);
		HotlistApiPort port = null;
		String response = null;
		BcwtsLogger.debug("Method to check card is hotlisted");
		if (!isHotlisted(serialNumber)) {
			try {
				String wsdlUrl = PropertyReader.getValue("QA_HOTLIST");
				WSHotlistCardRequestVO wSHotlistCardRequestVO  = new WSHotlistCardRequestVO ();
				wSHotlistCardRequestVO.setSerialNumber(serialNumber);
				wSHotlistCardRequestVO.setHotlistAction(new Integer(2));
				
				HotlistApi service = new HotlistApi_Impl(wsdlUrl);
				port = service.getHotlistApiPort();
				port.hotlistCard(wSHotlistCardRequestVO);
				
			} catch (RemoteException re) {
				// TODO Auto-generated catch block

				BcwtsLogger
						.error("Exception in checking card is hotlisted or not"
								+ Util.getFormattedStackTrace(re));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				BcwtsLogger
						.error("Exception in checking card is hotlisted or not"
								+ Util.getFormattedStackTrace(e));
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				BcwtsLogger
						.error("Exception in checking card is hotlisted or not"
								+ Util.getFormattedStackTrace(e));
			}
			response = "Card has been hotlisted";
		} else {

			response = "Card is already hotlisted";
		}

		BcwtsLogger.info("card status :" + response);
		return response;

	}

	public static boolean hotlistCards(ListCardDTO psListCardsDTO) {
		final String method = "hotlistCards";
		boolean isHotlisted = false;

		try {
			String response = hotlistCard(psListCardsDTO.getSerialNumber());
			BcwtsLogger.debug(method + "response= " + response);
			isHotlisted = true;
		} catch (Exception e) {
			isHotlisted = false;
			BcwtsLogger.error(method + "Exception in hotlisting card: "
					+ e.getMessage());
		}
		return isHotlisted;
	}

	public static String removeHotlist(String cardnumber) {
		String resp = "AlreadyHotlisted";
		Hashtable env = new Hashtable();
		BcwtsLogger.info("Class: NextFareMethods Method: removeHotlist: "
				+ cardnumber);
		try {
			String wsdlUrl = PropertyReader.getValue("QA_HOTLIST");
			HotlistApi service = new HotlistApi_Impl(wsdlUrl);
			HotlistApiPort port = service.getHotlistApiPort();
			WSRemoveFromHotlistRequestVO requestVo = new WSRemoveFromHotlistRequestVO();
			requestVo.setSerialNumber(cardnumber);
			port.removeFromHotlist(requestVo);

		} catch (Exception e) {
			resp = "Exception while Unhotlisted";
			BcwtsLogger.error("removeHotlist "
					+ "Exception while disassociateBreezeCard: "
					+ Util.getFormattedStackTrace(e));
		}
		return resp;
	}

	public static boolean addNewCard(ListCardDTO listCardsDTO) {

		BcwtsLogger.info("Class:NextFareMethods  Method:" + "addNewCard"
				+ "for Company ID:" + listCardsDTO.getCompanyId());

		boolean isCardAdded = false;

		ListCardDTO pslistCardsDAO = new ListCardDTO();
		
		

		try {

   
			String wsdlUrl = PropertyReader.getValue("QA_PREPAID");
			//String wsdlUrl = "http://10.200.0.150:7021/web-services/PPBApi?WSDL";
			PpbApi service = new PpbApi_Impl(wsdlUrl);
			PpbApiPort port = service.getPpbApiPort();

			WSAddNewMemberRequestVO wSAddNewMemberRequestVO = new WSAddNewMemberRequestVO();

			WSMemberBenefitRequestVO benefitElections1 = new WSMemberBenefitRequestVO();

			benefitElections1.setBenefitDeliveryTypeId(new Integer(2));
			benefitElections1.setBenefitElectionId(new Integer(2));
			benefitElections1.setBenefitId(new Integer(listCardsDTO.getBenefitId()));

			WSMemberBenefitRequestVO[] benefitElections = new WSMemberBenefitRequestVO[1];
			benefitElections[0] = benefitElections1;

			wSAddNewMemberRequestVO.setBenefitElections(benefitElections);

			WSCustomerPK customerPK = new WSCustomerPK();
			customerPK.setCustomerId(new Integer(listCardsDTO.getCustomerId()));

			wSAddNewMemberRequestVO.setCustomerPK(customerPK);
			wSAddNewMemberRequestVO.setEmail(listCardsDTO.getEmailId());
			wSAddNewMemberRequestVO.setEmployeeNumber(listCardsDTO
					.getCustomerMemberId());

			WSNameVO name = new WSNameVO();
			name.setFirstName(listCardsDTO.getFirstName());
			name.setLastName(listCardsDTO.getLastName());

			wSAddNewMemberRequestVO.setName(name);

			WSPhoneVO phone = new WSPhoneVO();
			phone.setNumber(listCardsDTO.getPhoneNumber());
			phone.setType("W");
			wSAddNewMemberRequestVO.setPhone(phone);

			wSAddNewMemberRequestVO.setSerialNumber(listCardsDTO
					.getSerialNumber());

			port.addNewMember(wSAddNewMemberRequestVO);
			isCardAdded = true;

		} catch (Exception e) {
			isCardAdded = false;
			BcwtsLogger.error("Exception while adding new card: "
					+ Util.getFormattedStackTrace(e));
		}

		return isCardAdded;
	}

	private static MemberDTO getMemberId(String customerMemberId,
			String customerId) {
		BcwtsLogger.info("For member"+customerMemberId+"and company id"+customerId);
		MemberDTO memberDTO = new MemberDTO();
		ListCardsDAO psListCardsDAO = new ListCardsDAO();
		try {
			memberDTO = psListCardsDAO.getMemberId(customerMemberId, customerId);

		} catch (Exception e) {

			BcwtsLogger.error(Util.getFormattedStackTrace(e));
		}
		return memberDTO;
	}

	public static boolean updateMemberInfo(ListCardDTO psListCardsDTO) {
		final String method = "updateMemberInfo";
		boolean isUpdated = false;

		BcwtsLogger.info("Class:NextfareMethods Method:" + method);

	
		MemberDTO memberDTO = new MemberDTO();

		try {

			List customerBenefitList = new ArrayList();
			CustomerBenefitDetailsDTO benefitDetailsDTO = new CustomerBenefitDetailsDTO();
			ListCardsDAO listCardsDAO = new ListCardsDAO();
			customerBenefitList = listCardsDAO
					.getCustomerBenefitDetails(psListCardsDTO.getCustomerId());
			for (Iterator iter = customerBenefitList.iterator(); iter.hasNext();) {
				benefitDetailsDTO = (CustomerBenefitDetailsDTO) iter.next();
				
			}
			memberDTO = getMemberId(psListCardsDTO.getCustomerMemberId(),
					psListCardsDTO.getCustomerId());
		//	int memberId = Integer.parseInt(memberDTO.getMemberid());
			String wsdlUrl = PropertyReader.getValue("QA_PREPAID");
			//String wsdlUrl = "http://10.200.0.150:7021/web-services/PPBApi?WSDL";
			PpbApi service = new PpbApi_Impl(wsdlUrl);
			PpbApiPort port = service.getPpbApiPort();

			WSUpdateMemberRequestVO requestVO = new WSUpdateMemberRequestVO();

			WSMemberBenefitRequestVO benefitElections1 = new WSMemberBenefitRequestVO();

			benefitElections1.setBenefitDeliveryTypeId(new Integer(2));
			benefitElections1.setBenefitElectionId(new Integer(2));
			benefitElections1.setBenefitId(new Integer(benefitDetailsDTO.getBenefitId()));

			WSMemberBenefitRequestVO[] benefitElections = new WSMemberBenefitRequestVO[1];
			benefitElections[0] = benefitElections1;

			requestVO.setBenefitElections(benefitElections);

			WSMemberPK memberPK = new WSMemberPK();

			memberPK.setCustomerId(new Integer(psListCardsDTO.getCustomerId()));
			memberPK.setMemberId(new Integer(memberDTO.getMemberid()));
			requestVO.setMemberPK(memberPK);

			requestVO.setEmail(psListCardsDTO.getEmailId());

			WSNameVO name = new WSNameVO();
			name.setFirstName(psListCardsDTO.getFirstName());
			name.setLastName(psListCardsDTO.getLastName());
			requestVO.setName(name);

			WSPhoneVO phone = new WSPhoneVO();
			phone.setNumber(psListCardsDTO.getPhoneNumber());
			phone.setType("W");
			requestVO.setPhone(phone);

			requestVO.setSerialNumber(psListCardsDTO.getSerialNumber());
			

			port.updateMember(requestVO);
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			BcwtsLogger.error(method + Util.getFormattedStackTrace(e));

		}

		return isUpdated;
	}

	public boolean deleteCardMember(BatchProcessDataDTO batchProcessDataDTO) {
		boolean successFlag = true;
		BcwtsLogger.info("Class:NextFareMethods method:deleteCardMember  ");

		ListCardsDAO temp = new ListCardsDAO();
		ArrayList memberDetailsList = new ArrayList();
		MemberDTO memberDTO = new MemberDTO();
		Integer REASON_CODE_FOR_DELETE_MEMBER = new Integer(6);

		try {
			String wsdlUrl = PropertyReader.getValue("QA_PREPAID");
			//String wsdlUrl = "http://10.200.0.150:7021/web-services/PPBApi?WSDL";
			PpbApi service = new PpbApi_Impl(wsdlUrl);
			PpbApiPort port = service.getPpbApiPort();

			memberDetailsList = (ArrayList) temp
					.getMemberDetails(batchProcessDataDTO.getCardID());
			for (Iterator it = memberDetailsList.iterator(); it.hasNext();) {
				memberDTO = (MemberDTO) it.next();

				WSMemberPK memberPK = new WSMemberPK();
				memberPK.setCustomerId(new Integer(memberDTO.getCustomerid()));
				memberPK.setMemberId(new Integer(memberDTO.getMemberid()));

				WSChangeMemberStatusRequestVO requestVO = new WSChangeMemberStatusRequestVO();

				requestVO.setMemberPK(memberPK);

				requestVO.setReasonCode(REASON_CODE_FOR_DELETE_MEMBER);
				requestVO.setNotes(batchProcessDataDTO.getNotes());
				port.terminateMember(requestVO);
			}

		} catch (Exception e) {
			successFlag = false;
			BcwtsLogger.error("deleteCardMember"
					+ "Exception in updating member info"
					+ Util.getFormattedStackTrace(e));
		}

		return successFlag;
	}

	

	
	public static boolean replaceSerialNumber(ListCardDTO psListCardsDTO) {
		final String method = "replaceSerialNumber";
		boolean isReplaced = false;
		BcwtsLogger.info(method);
		BcwtsLogger.info("Old Number: "+psListCardsDTO.getSerialNumber());
		BcwtsLogger.info("New Number: "+psListCardsDTO.getNewSerialNumber());
		String wsdlUrl = PropertyReader.getValue("QA_PATRONORDER");
		
	    PatronOrderApi service;
		
	    try {
			
	    	service = new PatronOrderApi_Impl(wsdlUrl);
			PatronOrderApiPort port = service.getPatronOrderApiPort();

			WSRequestCardReplacementRequestVO requestVO = new WSRequestCardReplacementRequestVO();
			requestVO.setSerialNumber(psListCardsDTO.getSerialNumber());
			requestVO.setReasonCode(new Integer(7));

			WSReplaceCardRequestVO replaceRequestVo = new WSReplaceCardRequestVO();
			replaceRequestVo.setNewSerialNumber(psListCardsDTO.getNewSerialNumber());
			replaceRequestVo.setPreviousSerialNumber(psListCardsDTO.getSerialNumber());

		port.requestCardReplacement(requestVO);

		port.replaceCard(replaceRequestVo);
			isReplaced = true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		
			BcwtsLogger.error("IOException while replacing card: "+Util.getFormattedStackTrace(e));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			
		
			BcwtsLogger.error("Service Exception while replacing card: "+Util.getFormattedStackTrace(e));
		}
		
	   
		
		
		return isReplaced;
	}

	public boolean replaceSerialNumberBatch(
			BatchProcessDataDTO batchProcessDataDTO) {
		final String method = "replaceSerialNumber";
		boolean isReplaced = false;
		 PatronOrderApi service;
		BcwtsLogger.info(method);
		try{
			String wsdlUrl = PropertyReader.getValue("PATRONORDER");
			service = new PatronOrderApi_Impl(wsdlUrl);
			PatronOrderApiPort port = service.getPatronOrderApiPort();

			WSRequestCardReplacementRequestVO requestVO = new WSRequestCardReplacementRequestVO();
			requestVO.setSerialNumber(batchProcessDataDTO.getCardID());
			requestVO.setReasonCode(new Integer(7));

			WSReplaceCardRequestVO replaceRequestVo = new WSReplaceCardRequestVO();
			replaceRequestVo.setNewSerialNumber(batchProcessDataDTO.getReplaceCardID());
			replaceRequestVo.setPreviousSerialNumber(batchProcessDataDTO.getCardID());

			port.requestCardReplacement(requestVO);

			port.replaceCard(replaceRequestVo);
		
			isReplaced = true;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			isReplaced = false;
			BcwtsLogger.error("IOException while replacing card: "+Util.getFormattedStackTrace(e));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			isReplaced = false;
			BcwtsLogger.error("Service Exception while replacing card: "+Util.getFormattedStackTrace(e));
		}
	   
		return isReplaced;
	}

	/******************************** SUNIL METHODS UPDATED *********************************************************/

	public static void AddAnnualPass_Threshold(String breezecardnumber) {

		try {

			String wsdlUrl = PropertyReader.getValue("QA_AUTOLOAD");
			AutoloadApi_Impl service;
			service = new AutoloadApi_Impl(wsdlUrl);
			AutoloadApiPort port = service.getAutoloadApiPort();

			WSDirectedAutoloadRequestVO autoloadRequestVO = new WSDirectedAutoloadRequestVO();
			autoloadRequestVO.setAutoloadActionId(new Integer(1));

			autoloadRequestVO.setFareInstrumentId(new Long(30721));
			autoloadRequestVO.setSerialNumber(breezecardnumber);
			WSDirectedAutoloadResponseVO responseVo = new WSDirectedAutoloadResponseVO();
			responseVo = port.addDirectedAutoload(autoloadRequestVO);

			System.out.println("Threshold Autolad Setup Done successfully : "
					+ responseVo.getAutoloadEventId() + "For Cardnumber  :"
					+ breezecardnumber);
		} catch (Exception e) {
			// e.printStackTrace();
			BcwtsLogger.error("AddAnnualPass_Threshold"

			+ Util.getFormattedStackTrace(e));
			System.out
					.println("Threshold Autolad Setup Failed For Cardnumber  :"
							+ breezecardnumber);
		}
	}

	
	/****************************************************************************************/
	
	public static void main(String[] args){
		//updateMemberInfoForReplace();
		//replaceSerialNumber();
	}

}
