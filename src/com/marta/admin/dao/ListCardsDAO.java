package com.marta.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dao.MartaBaseDAO;
import com.marta.admin.dao.NextFareConnection;
import com.marta.admin.dto.BcwtsPartnerIssueDTO;
import com.marta.admin.dto.BenefitDetailsDTO;
import com.marta.admin.dto.CustomerBenefitDetailsDTO;
import com.marta.admin.dto.ListCardsCompanyIdDTO;
import com.marta.admin.dto.ListMemberCardDTO;
import com.marta.admin.dto.MemberBenefitDetailsDTO;
import com.marta.admin.dto.MemberDTO;
import com.marta.admin.dto.ListCardDTO;
import com.marta.admin.forms.ListCardForm;
import com.marta.admin.hibernate.BcwtHotListCardDetails;
import com.marta.admin.hibernate.BcwtPartnerNewCard;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.hibernate.BcwtsPartnerIssue;
import com.marta.admin.hibernate.PartnerAdminDetails;
import com.marta.admin.hibernate.PartnerCompanyInfo;
import com.marta.admin.hibernate.PartnerHotlistHistory;
import com.marta.admin.hibernate.UpassSchools;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.MartaQueries;
import com.marta.admin.utils.Util;
import com.marta.admin.hibernate.UpassActivateList;
import com.marta.admin.hibernate.UpassNewCard;





public class ListCardsDAO extends MartaBaseDAO {

	final String ME = "ListCardsDAO: ";

	public String getCustomerBenefitNameforBenefitId(String companyId,String benefitid) throws Exception {
		final String MY_NAME = ME + "getbenefit name for : "+companyId +" and benefit "+benefitid;
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String benefitname = "";
		PartnerCompanyInfo partnerCompanyInfo = new PartnerCompanyInfo();
		
		try {
			
			partnerCompanyInfo =
					 getNextFareCustomerId(companyId);
			String nextfareId = partnerCompanyInfo.getNextfareCompanyId();
			
		BcwtsLogger.debug("nextfare id "+nextfareId);	
			
			String query = "select  cbd.benefit_name, op.short_desc, op.fare_instrument_category_id from nextfare_main.customer_benefit_definition cbd,    "
					+ "      nextfare_main.fare_instrument fi, nextfare_main.fare_instrument_category op  where  cbd.fare_instrument_id = fi.fare_instrument_id "    
                    + "       and fi.fare_instrument_category_id = op.fare_instrument_category_id "
					+ " and cbd.customer_id = '"+nextfareId+"' "
					+ " and cbd.benefit_id = '"+benefitid+"'";




			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}

			while(rs.next()){
           String region = "";
				
				if(rs.getString(2).startsWith("CSC"))
					region = "MARTA";
				else
					region = rs.getString(2);
				
				
				benefitname = rs.getString(1)+"/"+region+"/"+rs.getString(3);
				
			}
			
			
			
			BcwtsLogger.info( "got benefit name"+ benefitname);
		
		} catch (Exception ex) {
		
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		} 
		
		
		return benefitname;
	}
	
	/**
	 * To add new card
	 *
	 * @param bcwtPartnerNewCard
	 * @return isAdded
	 * @throws Exception
	 */
	public boolean addNewCard(BcwtPartnerNewCard bcwtPartnerNewCard) throws Exception {
		final String MY_NAME = ME + "addNewCard: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.save(bcwtPartnerNewCard);
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "new card added");
			isAdded = true;
		} catch (Exception ex) {
			isAdded = false;
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isAdded;
	}

	public PartnerCompanyInfo getNextFareCustomerId(String companyId) throws Exception {
		final String MY_NAME = ME + "getNextFareCustomerId: ";
		BcwtsLogger.info(MY_NAME + "for companyid:" +companyId );
		Transaction transaction = null;
		Session session = null;
		PartnerCompanyInfo partnerCompanyInfo = new PartnerCompanyInfo();

		try {
			session = getSession();
			transaction = session.beginTransaction();

		/*	String query = "from PartnerCompanyInfo partnerCompanyInfo" +
					" where partnerCompanyInfo.companyId = (select partnerAdminDetails.com from PartnerAdminDetails partnerAdminDetails ompanyId from p'" + companyId + "'";
			*/
			String query =  " from PartnerCompanyInfo partnerCompanyInfo where partnerCompanyInfo.companyId = (select partnerAdminDetails.partnerCompanyInfo.companyId from PartnerAdminDetails partnerAdminDetails where partnerAdminDetails.partnerId = '" + companyId + "')";

			partnerCompanyInfo = (PartnerCompanyInfo)
								session.createQuery(query).uniqueResult();

			transaction.commit();
			session.clear();
			BcwtsLogger.info(MY_NAME + "got getNextFareCustomerId");
		} catch (Exception ex) {
			ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));

			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		return partnerCompanyInfo;
	}

	public PartnerCompanyInfo getCustomerId(String companyId) throws Exception {
		final String MY_NAME = ME + "getNextFareCustomerId: ";
		BcwtsLogger.info(MY_NAME + "for companyid:" +companyId );
		Transaction transaction = null;
		Session session = null;
		PartnerCompanyInfo partnerCompanyInfo = new PartnerCompanyInfo();

		try {
			session = getSession();
			transaction = session.beginTransaction();

		/*	String query = "from PartnerCompanyInfo partnerCompanyInfo" +
					" where partnerCompanyInfo.companyId = (select partnerAdminDetails.com from PartnerAdminDetails partnerAdminDetails ompanyId from p'" + companyId + "'";
			*/
			String query =  " from PartnerCompanyInfo partnerCompanyInfo where partnerCompanyInfo.companyId = (select partnerAdminDetails.partnerCompanyInfo.companyId from PartnerAdminDetails partnerAdminDetails where partnerAdminDetails.partnerId = '" + companyId + "')";

			partnerCompanyInfo = (PartnerCompanyInfo)
								session.createQuery(query).uniqueResult();

			transaction.commit();
			session.clear();
			BcwtsLogger.info(MY_NAME + "got getNextFareCustomerId");
		} catch (Exception ex) {
			ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));

			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		return partnerCompanyInfo;
	}





	/**
	 * To delete the new card
	 *
	 * @param serialNo
	 * @return isDeleted
	 * @throws Exception
	 */
	public boolean deleteNewCard(String serialNo) throws Exception {
		final String MY_NAME = ME + "deleteNewCard: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isDeleted = false;
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();

			Query query = session.createQuery("delete from BcwtPartnerNewCard " +
					"where serialnumber = '" + serialNo + "'");

			query.executeUpdate();
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "card deleted successfully");
			isDeleted = true;
		} catch (Exception ex) {
			isDeleted = false;
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isDeleted;
	}

	/**
	 * To save Hotlisted card details
	 *
	 * @param bcwtHotListCardDetails
	 * @return isSuccess
	 * @throws Exception
	 */
	public boolean saveHotlistedCards(ListCardDTO listCardsDTO, Long partnerId) throws Exception {
		final String MY_NAME = ME + "saveHotlistedCards: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isSuccess = false;
		Session session = null;
		Transaction transaction = null;

	    PartnerCompanyInfo partnerCompanyinfo = new PartnerCompanyInfo();
	    PartnerAdminDetails partnerAdmindetails = new PartnerAdminDetails();
	    BcwtHotListCardDetails bcwtHotListCardDetails = null;

		try {
			String serialNumber = listCardsDTO.getSerialNumber();
			bcwtHotListCardDetails = getBcwtHotListCardDetails(serialNumber);

			if(bcwtHotListCardDetails == null) {
				if(listCardsDTO != null) {
					partnerCompanyinfo.setCompanyId(Long.valueOf(listCardsDTO.getCompanyId()));
					partnerCompanyinfo.setCompanyName(listCardsDTO.getCompanyName());
					partnerAdmindetails.setPartnerId(partnerId);

					bcwtHotListCardDetails = new BcwtHotListCardDetails();
					bcwtHotListCardDetails.setPartnerCompanyinfo(partnerCompanyinfo);
					bcwtHotListCardDetails.setPartnerAdmindetails(partnerAdmindetails);
					bcwtHotListCardDetails.setCompanyname(listCardsDTO.getCompanyName());
					bcwtHotListCardDetails.setMemberid(Long.valueOf(listCardsDTO.getMemberId()));
					bcwtHotListCardDetails.setCustomermemberid(listCardsDTO.getCustomerMemberId());
					bcwtHotListCardDetails.setSerialnumber(listCardsDTO.getSerialNumber());
					bcwtHotListCardDetails.setFirstname(listCardsDTO.getFirstName());
					bcwtHotListCardDetails.setLastname(listCardsDTO.getLastName());
					bcwtHotListCardDetails.setHotlisteddate(Util.getSystemdate());
					bcwtHotListCardDetails.setPointofsale(Constants.PS_HOTLIST);
					bcwtHotListCardDetails.setAdminname(listCardsDTO.getAdminName());
				}
			} else {
				bcwtHotListCardDetails.setMemberid(Long.valueOf(listCardsDTO.getMemberId()));
				bcwtHotListCardDetails.setCustomermemberid(listCardsDTO.getCustomerMemberId());
				partnerCompanyinfo.setCompanyId(Long.valueOf(listCardsDTO.getCompanyId()));
				partnerCompanyinfo.setCompanyName(listCardsDTO.getCompanyName());
				partnerAdmindetails.setPartnerId(partnerId);
				bcwtHotListCardDetails.setPartnerAdmindetails(partnerAdmindetails);
				bcwtHotListCardDetails.setHotlisteddate(Util.getSystemdate());
				bcwtHotListCardDetails.setPointofsale(Constants.PS_HOTLIST);
				bcwtHotListCardDetails.setAdminname(listCardsDTO.getAdminName());
			}

			session = getSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(bcwtHotListCardDetails);
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Hotlisted card details added");
			isSuccess = true;
		} catch (Exception ex) {
			isSuccess = false;
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isSuccess;
	}

	/**
	 * To get BcwtHotListCardDetails object
	 *
	 * @param serialNumber
	 * @return bcwtHotListCardDetails
	 * @throws Exception
	 */
	public BcwtHotListCardDetails getBcwtHotListCardDetails(String serialNumber) throws Exception {
		final String MY_NAME = ME + "getBcwtHotListCardDetails: ";
		BcwtsLogger.info(MY_NAME);
		Transaction transaction = null;
		Session session = null;
		BcwtHotListCardDetails bcwtHotListCardDetails = new BcwtHotListCardDetails();

		try {
			session = getSession();
			transaction = session.beginTransaction();

			String query = "from BcwtHotListCardDetails bcwtHotListCardDetails" +
					" where bcwtHotListCardDetails.serialnumber= '" + serialNumber + "'";

			bcwtHotListCardDetails = (BcwtHotListCardDetails)
								session.createQuery(query).uniqueResult();

			transaction.commit();
			session.clear();
			BcwtsLogger.info(MY_NAME + "got BcwtHotListCardDetails");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		return bcwtHotListCardDetails;
	}

	/**
	 * To get BcwtHotListCardDetails object
	 *
	 * @param serialNumber
	 * @return bcwtHotListCardDetails
	 * @throws Exception
	 */
	public PartnerHotlistHistory getPartnerHotlistHistory(String serialNumber) throws Exception {
		final String MY_NAME = ME + "getPartnerHotlistHistory: ";
		BcwtsLogger.info(MY_NAME);
		Transaction transaction = null;
		Session session = null;
		PartnerHotlistHistory partnerHotlistHistory = new PartnerHotlistHistory();

		try {
			session = getSession();
			transaction = session.beginTransaction();

			String query = "from PartnerHotlistHistory partnerHotlistHistory" +
					" where partnerHotlistHistory.cardNumber= '" + serialNumber + "'";

			partnerHotlistHistory = (PartnerHotlistHistory)
								session.createQuery(query).uniqueResult();

			transaction.commit();
			session.clear();
			BcwtsLogger.info(MY_NAME + "got PartnerHotlistHistory");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		return partnerHotlistHistory;
	}

	/**
	 * To delete the hotlist history
	 *
	 * @param serialNo
	 * @return isDeleted
	 * @throws Exception
	 */
	public boolean deleteHotlistHistory(String serialNo) throws Exception {
		final String MY_NAME = ME + "deleteHotlistHistory: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isDeleted = false;
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("delete from PartnerHotlistHistory " +
					"where cardNumber = '" + serialNo + "'");
			query.executeUpdate();
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "HotlistHistory(card) deleted successfully");
			isDeleted = true;
		} catch (Exception ex) {
			isDeleted = false;
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isDeleted;
	}

	/**
	 * To get new card details.
	 *
	 * @return newCardDetailsList
	 * @throws Exception
	 */
	public List getNewCardDetails() throws Exception {
		final String MY_NAME = ME + "getNewCardDetails: ";
		BcwtsLogger.debug(MY_NAME);
		List newCardDetailsList = new ArrayList();
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtPartnerNewCard bcwtPartnerNewCard";

			List queryList = session.createQuery(query).list();

			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				BcwtPartnerNewCard bcwtPartnerNewCard = (BcwtPartnerNewCard) iter.next();
				ListCardDTO listCardsDTO = new ListCardDTO();
				listCardsDTO.setCustomerMemberId(bcwtPartnerNewCard.getCustomermemberid().toString());
				listCardsDTO.setSerialNumber(bcwtPartnerNewCard.getSerialnumber());
				listCardsDTO.setFirstName(bcwtPartnerNewCard.getFirstname());
				listCardsDTO.setLastName(bcwtPartnerNewCard.getLastname());
				listCardsDTO.setEmailId(bcwtPartnerNewCard.getEmail());
				listCardsDTO.setPhoneNumber(bcwtPartnerNewCard.getPhoneno());
				listCardsDTO.setBenefitId(bcwtPartnerNewCard.getBenefitid().toString());
				listCardsDTO.setAction("New Card");

				newCardDetailsList.add(listCardsDTO);
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got new card details list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return newCardDetailsList;
	}
	
	public BcwtsPartnerIssueDTO updateIssue(BcwtsPartnerIssueDTO bcwtsPartnerIssueDTO)
			throws Exception {
		final String MY_NAME = ME + "updateIssue: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		Long patronAddressId = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			
			String query = "from BcwtsPartnerIssue where bcwtspartnerissueid="+bcwtsPartnerIssueDTO.getBcwtspartnerissueid();
			BcwtsPartnerIssue bcwtsPartnerIssue = (BcwtsPartnerIssue) session.createQuery(query).uniqueResult();
			BcwtPatron bcwtPatron = new BcwtPatron();
			bcwtPatron.setPatronid(bcwtsPartnerIssueDTO.getAdminid());
			bcwtsPartnerIssue.setResolution(bcwtsPartnerIssueDTO.getResolution());
			bcwtsPartnerIssue.setAdminid(bcwtPatron);
			bcwtsPartnerIssue.setClosedate(Util.getSystemdate());
			bcwtsPartnerIssue.setIssuestatus("Replied");
			
			
			
			
			session.update(bcwtsPartnerIssue);
			BeanUtils.copyProperties(bcwtsPartnerIssueDTO, bcwtsPartnerIssue);
			transaction.commit();
			// session.clear();
			session.flush();
			isUpdated = true;
			BcwtsLogger.info(MY_NAME + "Issue update to database ");
		} catch (Exception ex) {
			isUpdated = false;
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		 return bcwtsPartnerIssueDTO;
	}
	
	
	public List getTMAIssueList() throws Exception {
		final String MY_NAME = ME + "getTMAIssueList: ";
		BcwtsLogger.debug(MY_NAME);
		List<BcwtsPartnerIssueDTO> bcwtsPartnerIssueList = new ArrayList<BcwtsPartnerIssueDTO>();
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtsPartnerIssue bcwtsPartnerIssue";

			List<BcwtsPartnerIssue> queryList = session.createQuery(query).list();

			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				BcwtsPartnerIssue bcwtsPartnerIssue = (BcwtsPartnerIssue) iter.next();
				BcwtsPartnerIssueDTO bcwtsPartnerIssueDTO = new BcwtsPartnerIssueDTO();
				BeanUtils.copyProperties(bcwtsPartnerIssueDTO,bcwtsPartnerIssue);

				bcwtsPartnerIssueList.add(bcwtsPartnerIssueDTO);
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got new card details list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return bcwtsPartnerIssueList;
	}
	

	/**
	 * To get hotlist history details.
	 *
	 * @return hotlistHistoryDetails
	 * @throws Exception
	 */
	public List getHotlistHistoryDetails() throws Exception {
		final String MY_NAME = ME + "getHotlistHistoryDetails: ";
		BcwtsLogger.debug(MY_NAME);
		List hotlistHistoryDetails = new ArrayList();
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from PartnerHotlistHistory partnerHotlistHistory";

			List queryList = session.createQuery(query).list();

			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				PartnerHotlistHistory partnerHotlistHistory = (PartnerHotlistHistory) iter.next();
				ListCardDTO listCardsDTO = new ListCardDTO();
				listCardsDTO.setMemberId(partnerHotlistHistory.getMemberId());
				listCardsDTO.setSerialNumber(partnerHotlistHistory.getCardNumber());
				listCardsDTO.setFirstName(partnerHotlistHistory.getFirstName());
				listCardsDTO.setLastName(partnerHotlistHistory.getLastName());
				listCardsDTO.setEmailId(partnerHotlistHistory.getEmail());
				listCardsDTO.setPhoneNumber(partnerHotlistHistory.getPhone());
				listCardsDTO.setBenefitId(partnerHotlistHistory.getBenefitId());
				listCardsDTO.setHotlistFlag(partnerHotlistHistory.getHotlistFlag());
				listCardsDTO.setUnhotlistFlag(partnerHotlistHistory.getUnhotlistFlag());
				listCardsDTO.setCompanyName(partnerHotlistHistory.getCompanyName());

				hotlistHistoryDetails.add(listCardsDTO);
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got hotlist history details list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return hotlistHistoryDetails;
	}

	public boolean activateDeactivateBenefit(UpassActivateList upassActivateList) throws Exception {
		final String MY_NAME = ME + "activateDeactivateBenefit: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isSuccess = false;
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(upassActivateList);
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Benefit activated/deactivated successfully");
			isSuccess = true;
		} catch (Exception ex) {
			isSuccess = false;
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isSuccess;
	}



	/**
	 * To activate/deactivate benefit
	 *
	 * @param partnerHotlistHistory
	 * @return isSuccess
	 * @throws Exception
	 */
	public boolean activateDeactivateBenefit(PartnerHotlistHistory partnerHotlistHistory) throws Exception {
		final String MY_NAME = ME + "activateDeactivateBenefit: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isSuccess = false;
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(partnerHotlistHistory);
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Benefit activated/deactivated successfully");
			isSuccess = true;
		} catch (Exception ex) {
			isSuccess = false;
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isSuccess;
	}

	public UpassActivateList getUpassActivateList(String serialNumber) throws Exception {
		final String MY_NAME = ME + "UpassActivateList ";
		BcwtsLogger.info(MY_NAME);
		Transaction transaction = null;
		Session session = null;
		UpassActivateList upassActivateList = new UpassActivateList();

		try {
			session = getSession();
			transaction = session.beginTransaction();

			String query = "from UpassActivateList upassActivateList" +
					" where upassActivateList.serialnumber= '" + serialNumber + "'";

			upassActivateList = (UpassActivateList)
								session.createQuery(query).uniqueResult();

			transaction.commit();
			session.clear();
			BcwtsLogger.info(MY_NAME + "got upassActivateList");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		return upassActivateList;
	}




	/**
	 * Get card details by Raj.
	 *
	 * @param psListCardsForm
	 * @return cardDetailsList
	 * @throws Exception
	 */
	public List getCardDetails(ListCardForm listCardForm) throws Exception {
		final String MY_NAME = ME + "getCardDetails: ";

		BcwtsLogger.debug(MY_NAME+"for NextFare Id: "+listCardForm.getNextfareCompanyId());
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List cardDetailsList = new ArrayList();

		try {
			String query = "select" +
			" m.MEMBER_ID, m.FIRST_NAME, m.LAST_NAME, m.SERIAL_NBR, p.PHONE_NUMBER, m.EMAIL, m.CUSTOMER_MEMBER_ID, m.PHONE_ID" +
	   " from" +
	   		" nextfare_main.MEMBER m, nextfare_main.PHONE p" +
	   " where" +
	   		" m.benefit_status_id !='3' and p.PHONE_ID = m.PHONE_ID and m.CUSTOMER_ID = " +  listCardForm.getNextfareCompanyId();

		/*	String query = "select" +
								" MEMBER_ID, FIRST_NAME, LAST_NAME, SERIAL_NBR, PHONE_ID, EMAIL, CUSTOMER_MEMBER_ID" +
						   " from" +
						   		" nextfare_main.MEMBER" +
						   " where" +
						   		" CUSTOMER_ID = " + listCardForm.getCompanyId();*/

			if(!Util.isBlankOrNull(listCardForm.getFirstName())){
				query = query + " and lower(FIRST_NAME) like '" +
				listCardForm.getFirstName().trim().toLowerCase() + "%'";
			}
			if(!Util.isBlankOrNull(listCardForm.getLastName())){
				query = query + " and lower(LAST_NAME) like '" +
				listCardForm.getLastName().trim().toLowerCase() + "%'";
			}
			if(!Util.isBlankOrNull(listCardForm.getMemberId())){
				query = query + " and MEMBER_ID = " + listCardForm.getMemberId().trim();
			}
			if(!Util.isBlankOrNull(listCardForm.getCustomerMemberId())){
				query = query + " and lower(CUSTOMER_MEMBER_ID) like '" + listCardForm.getCustomerMemberId().trim().toLowerCase() + "%'";
			}
			if(!Util.isBlankOrNull(listCardForm.getSerialNumber())){
				query = query + " and lower(SERIAL_NBR) like '" +
				listCardForm.getSerialNumber().trim().toLowerCase() + "%'";
			}
			if(!Util.isBlankOrNull(listCardForm.getEmailId())){
				query = query + " and lower(EMAIL) like '" +
				listCardForm.getEmailId().trim().toLowerCase() + "%'";
			}

			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}

			while(rs.next()){
				ListCardDTO listCardDTO = new ListCardDTO();
				listCardDTO.setMemberId(rs.getString("MEMBER_ID"));
				listCardDTO.setFirstName(rs.getString("FIRST_NAME"));
				listCardDTO.setLastName(rs.getString("LAST_NAME"));
				listCardDTO.setSerialNumber(rs.getString("SERIAL_NBR"));
				listCardDTO.setPhoneNumber(rs.getString("PHONE_NUMBER"));
				listCardDTO.setEmailId(rs.getString("EMAIL"));
				listCardDTO.setCustomerMemberId(rs.getString("CUSTOMER_MEMBER_ID"));
				listCardDTO.setCustomerId(listCardForm.getNextfareCompanyId());
				listCardDTO.setCompanyId(listCardForm.getNextfareCompanyId());
				listCardDTO.setNextfareCompanyId(listCardForm.getNextfareCompanyId());
				cardDetailsList.add(listCardDTO);
			}
			BcwtsLogger.info(MY_NAME + "got card details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return cardDetailsList;
	}

	/**
	 * Get benefit details with status.
	 *
	 * @param psListCardsForm
	 * @return benefitDetailsList
	 * @throws Exception
	 */
	public List getBenefitDetails(ListCardForm listCardForm) throws Exception {
		final String MY_NAME = ME + "getBenefitDetails: ";
		BcwtsLogger.debug(MY_NAME);
		List customerBenefitList = null;
		List memberBenefitList = null;
		List benefitDetailsList = new ArrayList();
		List hotlistHistoryDetailsList = new ArrayList();
		BcwtsLogger.info("Getting benefit Details");
		try {
			BcwtsLogger.debug("nextfare id"+listCardForm.getNextfareCompanyId()+"member id"+listCardForm.getMemberId());
			String customerId = listCardForm.getNextfareCompanyId();
			String memberId = listCardForm.getMemberId();
			String customerMemberId = listCardForm.getCustomerMemberId();
			if(!Util.isBlankOrNull(customerId) && !Util.isBlankOrNull(memberId)) {
				customerBenefitList = getCustomerBenefitDetails(customerId);
				memberBenefitList = //getMemberBenefitDetails(customerId, customerMemberId);
						getBenefitDetailsForMember(customerId, customerMemberId);
			}
			hotlistHistoryDetailsList = getHotlistHistoryDetails();
			for (Iterator iterator = memberBenefitList.iterator(); iterator.hasNext();) {
				MemberBenefitDetailsDTO memberBenefitDetailsDTO = (MemberBenefitDetailsDTO) iterator.next();
				BenefitDetailsDTO benefitDetailsDTO = new BenefitDetailsDTO();
				benefitDetailsDTO.setCustomerId(customerId);
				benefitDetailsDTO.setMemberId(memberId);
				benefitDetailsDTO.setBenefitId(memberBenefitDetailsDTO.getBenefitId());
				benefitDetailsDTO.setBenefitName(memberBenefitDetailsDTO.getBenefitname());
				benefitDetailsDTO.setCompanyName(memberBenefitDetailsDTO.getCompanyname());
			//	benefitDetailsDTO.setFareIntrId(customerBenefitDetailsDTO.getFare_instrument_id());
			//	benefitDetailsDTO.setRegion(customerBenefitDetailsDTO.getBenefitRegion());
				
				if(memberBenefitDetailsDTO.getStatusId().equals("1"))
				benefitDetailsDTO.setBenefitStatus(Constants.ACTIVE_STATUS);
				else
					benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);
				if(hotlistHistoryDetailsList != null && !hotlistHistoryDetailsList.isEmpty()){
					for (Iterator iter = hotlistHistoryDetailsList.iterator(); iter.hasNext();) {
						ListCardDTO psListCardsDTO = (ListCardDTO) iter.next();
						if(psListCardsDTO.getMemberId().equalsIgnoreCase(memberId) &&
							psListCardsDTO.getBenefitId().equalsIgnoreCase(benefitDetailsDTO.getBenefitId()) &&
							psListCardsDTO.getCompanyName().equalsIgnoreCase(benefitDetailsDTO.getCompanyName())){
								if(psListCardsDTO.getHotlistFlag().equalsIgnoreCase(Constants.YES)){
									benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);
								
								} else if(psListCardsDTO.getUnhotlistFlag().equalsIgnoreCase(Constants.YES)){
									benefitDetailsDTO.setBenefitStatus(Constants.ACTIVE_STATUS);
								
								}
								break;
						}
					}
				}
				benefitDetailsList.add(benefitDetailsDTO);
			//	BcwtsLogger.info(MY_NAME + "got benefit details for"+benefitDetailsDTO.getMemberId()+"Status is:"+benefitDetailsDTO.getBenefitStatus()+"benefit Region"+benefitDetailsDTO.getRegion());
			}
			
		/*	if(customerBenefitList != null && !customerBenefitList.isEmpty()
					&& memberBenefitList != null) {
				for (Iterator iter = customerBenefitList.iterator(); iter.hasNext();) {
					CustomerBenefitDetailsDTO customerBenefitDetailsDTO = (CustomerBenefitDetailsDTO) iter.next();
					BenefitDetailsDTO benefitDetailsDTO = new BenefitDetailsDTO();
					benefitDetailsDTO.setCustomerId(customerId);
					benefitDetailsDTO.setMemberId(memberId);
					benefitDetailsDTO.setBenefitId(customerBenefitDetailsDTO.getBenefitId());
					benefitDetailsDTO.setBenefitName(customerBenefitDetailsDTO.getBenefitName());
					benefitDetailsDTO.setCompanyName(customerBenefitDetailsDTO.getCompanyName());
					benefitDetailsDTO.setFareIntrId(customerBenefitDetailsDTO.getFare_instrument_id());
					benefitDetailsDTO.setRegion(customerBenefitDetailsDTO.getBenefitRegion());
					if(memberBenefitList.size() < 1){
						BcwtsLogger.debug("Empty: "+memberBenefitList.size());
						benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);
					} else {
						for (Iterator iterator = memberBenefitList.iterator(); iterator.hasNext();) {
							MemberBenefitDetailsDTO memberBenefitDetailsDTO = (MemberBenefitDetailsDTO) iterator.next();
							if(checkHotlist(memberBenefitDetailsDTO.getSerial_nbr())){
								benefitDetailsDTO.setHotlisted(true);
								BcwtsLogger.debug("HotlistedCard:"+memberBenefitDetailsDTO.getSerial_nbr());
								benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);
								break;
							}
							else{
								benefitDetailsDTO.setHotlisted(false);
							if(customerBenefitDetailsDTO.getBenefitId().
									equalsIgnoreCase(memberBenefitDetailsDTO.getBenefitId()) && memberBenefitDetailsDTO.getBenifitActionId().equals("2") ){
								BcwtsLogger.debug("ActiveStatus");
								benefitDetailsDTO.setBenefitStatus(Constants.ACTIVE_STATUS);
								break;
							} else {
								BcwtsLogger.debug("InActiveStatus");
								benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);//Constants.IN_ACTIVE_STATUS);
							}
							}
						}
					}

					if(benefitDetailsDTO.getFareIntrId().equalsIgnoreCase(Constants.ANNUAL_PASS_BENEFIT_ID)) {
						if(hotlistHistoryDetailsList != null && !hotlistHistoryDetailsList.isEmpty()){
							for (Iterator iterator = hotlistHistoryDetailsList.iterator(); iterator.hasNext();) {
								PSListCardsDTO psListCardsDTO = (PSListCardsDTO) iterator.next();
								if(psListCardsDTO.getMemberId().equalsIgnoreCase(memberId) &&
									psListCardsDTO.getBenefitId().equalsIgnoreCase(benefitDetailsDTO.getBenefitId()) &&
									psListCardsDTO.getCompanyName().equalsIgnoreCase(benefitDetailsDTO.getCompanyName())){
										if(psListCardsDTO.getHotlistFlag().equalsIgnoreCase(Constants.YES)){
											benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);
											benefitDetailsDTO.setQueueStatus("In the queue to be deactivated for next month");
										} else if(psListCardsDTO.getUnhotlistFlag().equalsIgnoreCase(Constants.YES)){
											benefitDetailsDTO.setBenefitStatus(Constants.ACTIVE_STATUS);
											benefitDetailsDTO.setQueueStatus("In the queue to be activated for next month");
										}
										break;
								}
							}
						}
					}

					benefitDetailsList.add(benefitDetailsDTO);
					BcwtsLogger.info(MY_NAME + "got benefit details for"+benefitDetailsDTO.getMemberId()+"Status is:"+benefitDetailsDTO.getBenefitStatus()+"benefit Region"+benefitDetailsDTO.getRegion());
				}
			}*/
			BcwtsLogger.info(MY_NAME + "got benefit details for");
		} catch (Exception ex) {
			
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
	
		return benefitDetailsList;	

		/*	if(customerBenefitList != null && !customerBenefitList.isEmpty()
					&& memberBenefitList != null) {
				for (Iterator iter = customerBenefitList.iterator(); iter.hasNext();) {
					CustomerBenefitDetailsDTO customerBenefitDetailsDTO = (CustomerBenefitDetailsDTO) iter.next();
					BenefitDetailsDTO benefitDetailsDTO = new BenefitDetailsDTO();
					benefitDetailsDTO.setCustomerId(customerId);
					benefitDetailsDTO.setMemberId(memberId);
					benefitDetailsDTO.setBenefitId(customerBenefitDetailsDTO.getBenefitId());
					benefitDetailsDTO.setBenefitName(customerBenefitDetailsDTO.getBenefitName());
					benefitDetailsDTO.setCompanyName(customerBenefitDetailsDTO.getCompanyName());
					benefitDetailsDTO.setFareIntrId(customerBenefitDetailsDTO.getFare_instrument_id());
					BcwtsLogger.debug("Out Side Empty"+benefitDetailsDTO.getFareIntrId());

					if(memberBenefitList.isEmpty()){
						BcwtsLogger.debug("empty list");
						benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);
					} else {

						for (Iterator iterator = memberBenefitList.iterator(); iterator.hasNext();) {
							MemberBenefitDetailsDTO memberBenefitDetailsDTO = (MemberBenefitDetailsDTO) iterator.next();
							BcwtsLogger.debug("Inside for:"+customerBenefitDetailsDTO.getBenefitId()+" member:"+memberBenefitDetailsDTO.getBenefitId());
							if(checkHotlist(memberBenefitDetailsDTO.getSerialNbr())){
								BcwtsLogger.debug("HotlistedCard:"+memberBenefitDetailsDTO.getSerialNbr());
								benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);
								break;
							}else{
							if(customerBenefitDetailsDTO.getBenefitId().
									equalsIgnoreCase(memberBenefitDetailsDTO.getBenefitId())){
								if(!benefitDetailsDTO.getFareIntrId().equals("30721")){
									BcwtsLogger.debug("Inside if +"+benefitDetailsDTO.getFareIntrId());
									if(memberBenefitDetailsDTO.getStatusId().equals("1"))
									benefitDetailsDTO.setBenefitStatus(Constants.ACTIVE_STATUS);
									else
									benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);
								}
								benefitDetailsDTO.setBenefitStatus(Constants.ACTIVE_STATUS);
								break;
							} else {
								benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);
							}
							}
						}
					}

					if(benefitDetailsDTO.getBenefitName().equalsIgnoreCase(Constants.ANNUAL_PASS_BENEFIT_NAME)) {
						if(hotlistHistoryDetailsList != null && !hotlistHistoryDetailsList.isEmpty()){
							for (Iterator iterator = hotlistHistoryDetailsList.iterator(); iterator.hasNext();) {
								ListCardDTO listCardDTO = (ListCardDTO) iterator.next();
								if(listCardDTO.getMemberId().equalsIgnoreCase(memberId) &&
										listCardDTO.getBenefitId().equalsIgnoreCase(benefitDetailsDTO.getBenefitId()) &&
										listCardDTO.getCompanyName().equalsIgnoreCase(benefitDetailsDTO.getCompanyName())){
										if(listCardDTO.getHotlistFlag().equalsIgnoreCase(Constants.YES)){
											benefitDetailsDTO.setBenefitStatus(Constants.IN_ACTIVE_STATUS);
										} else if(listCardDTO.getUnhotlistFlag().equalsIgnoreCase(Constants.YES)){
											benefitDetailsDTO.setBenefitStatus(Constants.ACTIVE_STATUS);
										}
										break;
								}
							}
						}
					}

					benefitDetailsList.add(benefitDetailsDTO);
				}


			}
			BcwtsLogger.info(MY_NAME + "got benefit details");
			} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return benefitDetailsList;*/
	}


	public boolean checkHotlist(String serialNbr){
		   String MY_NAME = "checkHotlist";
		   boolean flag = true;
		    Connection con = null;
			PreparedStatement stmt=null;
			ResultSet rs=null;


			try{
				con = NextFareConnection.getConnection();

				String query="SELECT HOTLIST_ACTION from NEXTFARE_MAIN.FARE_MEDIA_INVENTORY where SERIAL_NBR=?";
				stmt = con.prepareStatement(query);
				stmt.setString(1, serialNbr);

				rs= stmt.executeQuery();

				while(rs.next()){
					//if(rs.getString(1).equals("0"))
						if(Util.isBlankOrNull(rs.getString(1))){
						  flag = false;
						}
						if(rs.getString(1).equals("0")) {
						 flag = false;

					   }
			}

			}
			catch(Exception ex){
				BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));

			}
			finally{
				try{

				rs.close();
				stmt.close();

				NextFareConnection.closeConnection(con);
				BcwtsLogger.debug(MY_NAME + " Resources closed");
				}
				catch(SQLException ex){
					BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
				}
			}

		   return flag;
	   }





	/**
	 * Get customer benefit details by Raj.
	 *
	 * @param customerId
	 * @return customerBenefitList
	 * @throws Exception
	 */
	public List getCustomerBenefitDetails(String customerId) throws Exception {
		final String MY_NAME = ME + "getBenefitDetails: "+customerId;
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List customerBenefitList = new ArrayList();

		try {
			String query = "select" +
								" custbene.CUSTOMER_ID, custbene.BENEFIT_ID, custbene.BENEFIT_NAME," +
								" cust.ORG_NAME, custbene.FARE_INSTRUMENT_ID" +
						   " from" +
						   		" nextfare_main.CUSTOMER_BENEFIT_DEFINITION custbene," +
						   		" nextfare_main.CUSTOMER cust" +
						   " where" +
						   		" custbene.CUSTOMER_ID = cust.CUSTOMER_ID" +
						   		" and custbene.BENEFIT_STATUS_ID = " + Constants.BENEFIT_STATUS_ID_ONE +
						   		" and cust.CUSTOMER_ID = " + customerId +
						   		" and custbene.CUSTOMER_ID = " + customerId;

			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}

			while(rs.next()){
				CustomerBenefitDetailsDTO benefitDetailsDTO = new CustomerBenefitDetailsDTO();
				benefitDetailsDTO.setCustomerId(rs.getString("CUSTOMER_ID"));
				benefitDetailsDTO.setBenefitId(rs.getString("BENEFIT_ID"));
				benefitDetailsDTO.setBenefitName(rs.getString("BENEFIT_NAME"));
				benefitDetailsDTO.setCompanyName(rs.getString("ORG_NAME"));
				benefitDetailsDTO.setFare_instrument_id(rs.getString("FARE_INSTRUMENT_ID"));

				customerBenefitList.add(benefitDetailsDTO);
			}
			BcwtsLogger.info(MY_NAME + "got benefit details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return customerBenefitList;
	}
	
	public List getBenefitDetailsForMember(String customer_id,String member_id) throws Exception {
		final String MY_NAME = ME + "getBenefitDetailsForMember: ";
		BcwtsLogger.debug(MY_NAME
				+ " getting breeze card details for the given patron id ");
		
		Session session = null;
		Transaction transaction = null;
		List productDetails = new ArrayList();
		/* String serialNO = cardId.toString(); */
		Connection con = null;
		try {
			// NextWareConnection nextWareConnection = new NextWareConnection();
			
			con = NextFareConnection.getConnection();
			Statement stmt = con.createStatement();
			String query = "  select m.member_id,m.serial_nbr,fareinst.FARE_INSTRUMENT_ID,fareinst.short_desc, faretran.FIRST_USE_DTM,  faretran.ACTUAL_RIDES_REMAINING, faretran.ACTUAL_SV_REMAINING/100, faretran.expiry_dtm,  "
             +"  cbd.benefit_name, cbd.benefit_id, cc.org_name from nextfare_main.fare_media_product_state  faretran, nextfare_main.fare_instrument  fareinst, "
             + " nextfare_main.customer cc, nextfare_main.customer_benefit_definition cbd,  nextfare_main.member m " 
             +"  where faretran.FARE_INSTRUMENT_ID = fareinst.FARE_INSTRUMENT_ID "  
             +"  and faretran.SERIAL_NBR = m.SERIAL_NBR "
             +"  and cbd.fare_instrument_id = fareinst.FARE_INSTRUMENT_ID "
             +"  and cbd.customer_id = cc.customer_id "  
             +"  and cbd.customer_id = '"+customer_id+"' "
             +"  and m.customer_id = cbd.customer_id "
             +"  		 and m.CUSTOMER_MEMBER_ID = '"+member_id+"' "
             +"   order by fareinst.short_desc";
			BcwtsLogger.debug("Query "+query);
			ResultSet rs = stmt
					.executeQuery(query);
			if (rs != null) {
				//BcwtProductDTO productDTO = null;
			
				while (rs.next()) {
					MemberBenefitDetailsDTO memberBenefitDetailsDTO = new MemberBenefitDetailsDTO();
					memberBenefitDetailsDTO.setCustomerId(customer_id);
					memberBenefitDetailsDTO.setMemberId(rs.getString("MEMBER_ID"));
					memberBenefitDetailsDTO.setBenefitId(rs.getString("BENEFIT_ID"));
					memberBenefitDetailsDTO.setSerialNbr(rs.getString("SERIAL_NBR"));
					memberBenefitDetailsDTO.setBenefitname(rs.getString("BENEFIT_NAME"));
					memberBenefitDetailsDTO.setCompanyname(rs.getString("ORG_NAME"));
					

					
					
					if(Util.greaterThenToday(rs.getDate("EXPIRY_DTM"))){
						if(rs.getInt("ACTUAL_RIDES_REMAINING") > 0)
							memberBenefitDetailsDTO.setStatusId("1");
						else
							memberBenefitDetailsDTO.setStatusId("0");
					}else
						memberBenefitDetailsDTO.setStatusId("0");
					productDetails.add(memberBenefitDetailsDTO);	
				}
			}
			BcwtsLogger
					.info(MY_NAME + "got breeze card details from nextware DB ");
		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME + e.getMessage());
			throw e;
		} finally {
			NextFareConnection.closeConnection(con);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		
		return productDetails;
	}
	/**
	 * Get member benefit details.
	 *
	 * @param customerId
	 * @param memberId
	 * @return memberBenefitList
	 * @throws Exception
	 */
	private List getMemberBenefitDetails(String customerId, String memberId) throws Exception {
		final String MY_NAME = ME + "getBenefitDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List memberBenefitList = new ArrayList();
		BcwtsLogger.debug("get member benefit details for customer"+customerId+" and member"+memberId);
		try {
			String query = "select" +
			" mb.CUSTOMER_ID, mb.MEMBER_ID, mb.BENEFIT_ID, mb.BENEFIT_ACTION_ID, m.SERIAL_NBR, m.BENEFIT_STATUS_ID" +
		" from" +
			" nextfare_main.MEMBER_BENEFIT mb, nextfare_main.MEMBER m " +
		" where" +
			" M.CUSTOMER_MEMBER_ID = '" + memberId +
			"' and mb.CUSTOMER_ID = '" + customerId+
					"' and MB.MEMBER_ID = M.MEMBER_ID";

			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}

			while(rs.next()){
				MemberBenefitDetailsDTO memberBenefitDetailsDTO = new MemberBenefitDetailsDTO();
				memberBenefitDetailsDTO.setCustomerId(rs.getString("CUSTOMER_ID"));
				memberBenefitDetailsDTO.setMemberId(rs.getString("MEMBER_ID"));
				memberBenefitDetailsDTO.setBenefitId(rs.getString("BENEFIT_ID"));
				memberBenefitDetailsDTO.setSerialNbr(rs.getString("SERIAL_NBR"));
				memberBenefitDetailsDTO.setStatusId(rs.getString("BENEFIT_STATUS_ID"));

				memberBenefitList.add(memberBenefitDetailsDTO);
			}
			BcwtsLogger.info(MY_NAME + "got benefit details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return memberBenefitList;
	}





	/**
	 * To get member benefit details by company Id
	 * by Raj.
	 *
	 * @param companyId
	 * @return memberBenefitList
	 * @throws Exception
	 */
	public List getMemberBenefitDetailsByCompanyId(String companyId) throws Exception {
		final String MY_NAME = ME + "getMemberBenefitDetailsByCompanyId: ";
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List memberBenefitList = new ArrayList();

		try {
			String query = "select" +
								" CUSTOMER_ID, MEMBER_ID, BENEFIT_ID" +
							" from" +
								" nextfare_main.MEMBER_BENEFIT" +
							" where" +
								" CUSTOMER_ID = " + companyId;

			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}

			while(rs.next()){
				MemberBenefitDetailsDTO memberBenefitDetailsDTO = new MemberBenefitDetailsDTO();
				memberBenefitDetailsDTO.setCustomerId(rs.getString("CUSTOMER_ID"));
				memberBenefitDetailsDTO.setMemberId(rs.getString("MEMBER_ID"));
				memberBenefitDetailsDTO.setBenefitId(rs.getString("BENEFIT_ID"));

				memberBenefitList.add(memberBenefitDetailsDTO);
			}

			BcwtsLogger.info(MY_NAME + "got member benefit details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		return memberBenefitList;
	}

	/**
	 * To get the member details
	 *
	 * @param serialNumber
	 * @return memberBenefitList
	 * @throws Exception
	 */
	public List getMemberDetails(String serialNumber) throws Exception {
		final String MY_NAME = ME + "getMemberDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List memberBenefitList = new ArrayList();

		try {
			String query = "select" +
								" CUSTOMER_ID, MEMBER_ID, CUSTOMER_MEMBER_ID" +
							" from" +
								" NEXTFARE_MAIN.MEMBER" +
							" where" +
								" SERIAL_NBR = " + serialNumber;

			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}

			while(rs.next()){
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setCustomerid(rs.getString("CUSTOMER_ID"));
				memberDTO.setMemberid(rs.getString("MEMBER_ID"));
				memberDTO.setCustomermemberid(rs.getString("CUSTOMER_MEMBER_ID"));

				memberBenefitList.add(memberDTO);
			}
			BcwtsLogger.info(MY_NAME + "got member details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return memberBenefitList;
	}

	/**
	 * To check whether member id exist or not
	 *
	 * @param memberId
	 * @return isExist
	 * @throws Exception
	 */
	public boolean isMemberIdExist(String memberId, String companyId) throws Exception {
		final String MY_NAME = ME + "isMemberIdExist: ";
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isExist = false;

		try {
			String query = "select MEMBER_ID from NEXTFARE_MAIN.MEMBER" +
							" where CUSTOMER_MEMBER_ID = '" + memberId + "'" +
							" and CUSTOMER_ID = " + companyId;

			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}

			while(rs.next()){
				isExist = true;
				break;
			}
			BcwtsLogger.info(MY_NAME + "isExist= " + isExist);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isExist;
	}

	/**
	 * To check whether serial no exist or not
	 *
	 * @param serialNumber
	 * @return isExist
	 * @throws Exception
	 */
	public boolean isSerialNoExist(String serialNumber) throws Exception {
		final String MY_NAME = ME + "isSerialNoExist: ";
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isExist = false;

		try {
			String query = "select SERIAL_NBR from NEXTFARE_MAIN.FARE_MEDIA_INVENTORY " +
								"where SERIAL_NBR = " + serialNumber;

			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}

			while(rs.next()){
				isExist = true;
				break;
			}
			BcwtsLogger.info(MY_NAME + "isExist= " + isExist);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isExist;
	}

	/**
	 * To check whether the card is hotlisted or not
	 *
	 * @param serialNumber
	 * @return isHotlisted
	 * @throws Exception
	 */
	public boolean isCardHotlisted(String serialNumber) throws Exception {
		final String MY_NAME = ME + "isCardHotlisted: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		boolean isHotlisted = false;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtHotListCardDetails " +
								"where serialnumber = '" + serialNumber + "'";

			List queryList = session.createQuery(query).list();

			if(queryList != null && !queryList.isEmpty()){
				isHotlisted = true;
			} else {
				isHotlisted = false;
			}

			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "isHotlisted= " + isHotlisted);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isHotlisted;
	}

	/**
	 * To see if the card is associated with any other customer
	 *
	 * @param serialNumber
	 * @param customerId
	 * @return isCardAssociated
	 * @throws Exception
	 */
	public boolean isCardAssociated(String serialNumber, String customerId, String memberId) throws Exception {
		final String MY_NAME = ME + "isCardAssociated: ";
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isCardAssociated = false;

		try {

			String query = "select SERIAL_NBR from NEXTFARE_MAIN.MEMBER" +
							" where SERIAL_NBR = " + serialNumber +
							" and ((CUSTOMER_ID != " + customerId + "and CUSTOMER_MEMBER_ID != '" + memberId + "' )" +
								" or (CUSTOMER_ID = " + customerId + " and CUSTOMER_MEMBER_ID != '" + memberId + "' ))";

			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}

			while(rs.next()){
				isCardAssociated = true;
				break;
			}
			BcwtsLogger.info(MY_NAME + "isCardAssociated= " + isCardAssociated);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isCardAssociated;
	}

	public boolean isCorrectRiderClass(String serialNumber, String customerId) throws SQLException {
		final String MY_NAME = ME + "isCorrectRiderClass: ";
		BcwtsLogger.debug(MY_NAME+"For Serial Number: "+serialNumber+" for Customer id "+customerId);
		Connection nextFareConnection = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		boolean isCorrectRiderClass = false;
		int riderClass1 = 0;
		int riderClass2 = 0;
		try {

			String query1 = "select FMI.RIDER_CLASSIFICATION " +
					"from NEXTFARE_MAIN.FARE_MEDIA_INVENTORY " +
					"fmi where fmi.serial_nbr ='"+serialNumber+"'";

			String query2 = "select  FI.RIDER_CLASS " +
					" from nextfare_main.customer_benefit_definition cbd, nextfare_main.fare_instrument fi " +
					" where CBD.BENEFIT_STATUS_ID = '1' and FI.FARE_INSTRUMENT_ID = CBD.FARE_INSTRUMENT_ID and  cbd.customer_id = "+customerId;

			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {

				stmt1 = nextFareConnection.createStatement();
				rs1 =   stmt1.executeQuery(query1);
				while(rs1.next()){
					riderClass1 = rs1.getInt(1);
					BcwtsLogger.debug("Rider Class 1 "+riderClass1);
				}

				stmt2 = nextFareConnection.createStatement();
				rs2 =   stmt2.executeQuery(query2);
				while(rs2.next()){
					riderClass2 = rs2.getInt(1);
					BcwtsLogger.debug("Rider Class 2 "+riderClass2);
				}
			}

			if(riderClass1 == riderClass2)
				isCorrectRiderClass = true;

			BcwtsLogger.info(MY_NAME + "isCorrectRiderClass= " + isCorrectRiderClass);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));

		} finally {
			rs1.close();
			stmt1.close();
			rs2.close();
			stmt2.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
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
	public boolean isCardExistInPartnerNewCardTable(String serialNumber) throws Exception {
		final String MY_NAME = ME + "isCardExistInPartnerNewCardTable: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		boolean isCardExist = false;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtPartnerNewCard where serialnumber = '" + serialNumber + "'";

			List queryList = session.createQuery(query).list();

			if(queryList != null && !queryList.isEmpty()){
				isCardExist = true;
			} else {
				isCardExist = false;
			}

			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "isCardExist= " + isCardExist);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			BcwtsLogger.debug(MY_NAME + " Resources closed");
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
	public boolean isCardExistInPartnerHistoryTable(String serialNumber) throws Exception {
		final String MY_NAME = ME + "isCardExistInPartnerHistoryTable: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		boolean isCardExist = false;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from PartnerHotlistHistory where cardNumber = '" + serialNumber + "'";

			List queryList = session.createQuery(query).list();

			if(queryList != null && !queryList.isEmpty()){
				isCardExist = true;
			} else {
				isCardExist = false;
			}

			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "isCardExist= " + isCardExist);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isCardExist;
	}

	/**
	 * To get Company ID
	 *
	 * @param ListForms
	 * @return companyIdList
	 * @throws Exception
	 */
	public List getCompanyId(ListCardForm listCardForm) throws Exception {
		final String MY_NAME = ME + "getCompanyId: ";
		BcwtsLogger.debug(MY_NAME);
		List companyIdList = null;
		List queryList = null;
		List memberIdList = null;
		ListCardsCompanyIdDTO cardDTO = null;
		Session session = null;
		Transaction transaction = null;
		List partnerMemberList = null;
		ListMemberCardDTO listMemberCardDTO = null;
		try {
			companyIdList = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			if(null != listCardForm.getCompanyId() &&
					!Util.isBlankOrNull(listCardForm.getCompanyId().toString())){
				String Query = MartaQueries.GET_MEMBERID_LIST_CARDS_QUERY ;
				Query = Query + " and partnerAdmin.partnerCompanyInfo.companyId = "+listCardForm.getCompanyId().toString();
				partnerMemberList = session.createQuery(Query).list();
				if(partnerMemberList !=null && !partnerMemberList.isEmpty()){
					for(Iterator ite = partnerMemberList.iterator();ite.hasNext();){
						cardDTO = new ListCardsCompanyIdDTO();
						Object[] member = (Object[]) ite.next();
						if(member[3] !=null &&
								!Util.isBlankOrNull(member[3].toString())){
							cardDTO.setMemberId(member[3].toString());
						}
						cardDTO.setCompanyId(listCardForm.getCompanyId());
						if(member[4] !=null &&
								!Util.isBlankOrNull(member[4].toString())){
							cardDTO.setCompanyName(member[4].toString());
						}
						if(member[5] !=null &&
								!Util.isBlankOrNull(member[5].toString())){
							cardDTO.setNextfareCompanyId(member[5].toString());
						}
						companyIdList.add(cardDTO);
					}
				}

			}if(null != listCardForm.getTmaId() &&
					!Util.isBlankOrNull(listCardForm.getTmaId().toString())){
				String query = MartaQueries.GET_LIST_CARDS_COMPANYID_QUERY ;
				query = query + " and tmaInfo.tmaId = "+listCardForm.getTmaId().toString();
				queryList = session.createQuery(query).list();
				if(queryList !=null && !queryList.isEmpty()){
					for(Iterator iter = queryList.iterator();iter.hasNext();){

						Object element[] = (Object[]) iter.next();

						if(element[4] != null &&
								!Util.isBlankOrNull(element[4].toString())){
							memberIdList = getMemberId(element[4].toString());
							if(null != memberIdList && !memberIdList.isEmpty()){
								for(Iterator iter1 = memberIdList.iterator();iter1.hasNext();){
									cardDTO = new ListCardsCompanyIdDTO();
									listMemberCardDTO = (ListMemberCardDTO) iter1.next();
									cardDTO.setMemberId(listMemberCardDTO.getMemberId());
									if(element[0] != null &&
											!Util.isBlankOrNull(element[0].toString())){
										cardDTO.setCompanyId(element[0].toString());
									}
									if(element[1] != null &&
											!Util.isBlankOrNull(element[1].toString())){
										cardDTO.setNextfareCompanyId(element[1].toString());
										listCardForm.setNextfareCompanyId(element[1].toString());
									}
									if(element[2] !=null &&
											!Util.isBlankOrNull(element[2].toString())){
										cardDTO.setCompanyName(element[2].toString());
									}
									if(element[3] != null &&
											!Util.isBlankOrNull(element[3].toString())){
										cardDTO.setTmaName(element[3].toString());
									}
									companyIdList.add(cardDTO);
								}

							}

						}



					}
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "retrived companyid");

		} catch (Exception ex) {

			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return companyIdList;
	}

	private List getMemberId(String companyId)throws Exception {
		final String MY_NAME = ME + "getPartnerDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List memberIdList = null;
		List returnMemberId = null;
		try {
			returnMemberId = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			String query1 = MartaQueries.GET_MEMBERID_LIST_CARDS_QUERY ;
			if(companyId != null && !Util.isBlankOrNull(companyId)){
				query1 = query1 + " and partnerAdmin.partnerCompanyInfo.companyId = "+companyId;
				memberIdList = session.createQuery(query1).list();
				if(memberIdList !=null && !memberIdList.isEmpty()){
					for(Iterator it = memberIdList.iterator();it.hasNext();){
						ListMemberCardDTO listMemberCardDTO = new ListMemberCardDTO();
						Object[] member = (Object[]) it.next();
						if(member[3] !=null &&
								!Util.isBlankOrNull(member[3].toString())){
							listMemberCardDTO.setMemberId(member[3].toString());
						}
						listMemberCardDTO.setCompanyId(companyId);
						returnMemberId.add(listMemberCardDTO);
					}
				}
			}


			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Got partnerDetails List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		return returnMemberId;
	}


	/**
	 * To get Partner Details
	 *
	 * @param listCardForm
	 * @return partnerDetailsList
	 * @throws Exception
	 */
	public List getPartnerDetails(ListCardForm listCardForm)
	throws Exception {
		final String MY_NAME = ME + "getPartnerDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List partnerDetailsList = new ArrayList();

		try {
			session = getSession();
			transaction = session.beginTransaction();

			String query =
					"select" +
						" partnerCompanyInfo.companyId," +
						" partnerCompanyInfo.nextfareCompanyId,"+
						" partnerCompanyInfo.companyName," +
						" partnerCompanyInfo.partnerCompanyType.companyType," +
						" partnerCompanyInfo.partnerCompanyStatus.companyStatus" +
					" from" +
						" PartnerCompanyInfo partnerCompanyInfo" +
					" where" +
						" partnerCompanyInfo.partnerCompanyStatus.companyStatusId = " + Constants.COMPANY_STATUS_ACTIVE;


			if(!Util.isBlankOrNull(listCardForm.getCompanyId())){
				query = query + " and partnerCompanyInfo.companyId = " + listCardForm.getCompanyId();
			}
			if(!Util.isBlankOrNull(listCardForm.getCompanyName())){
				query = query + " and upper(partnerCompanyInfo.companyName) like '%" + listCardForm.getCompanyName().toUpperCase() + "%'";
			}
			if(!Util.isBlankOrNull(listCardForm.getCompanyType())){
				query = query + " and partnerCompanyInfo.partnerCompanyType.companyTypeId = " + listCardForm.getCompanyType();
			}
			if(!Util.isBlankOrNull(listCardForm.getCompanyStatus())){
				query = query + " and partnerCompanyInfo.partnerCompanyStatus.companyStatusId = " + listCardForm.getCompanyStatus();
			}

			List queryList= session.createQuery(query).list();

			if(queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					ListCardDTO listCardDTO = new ListCardDTO();
					if(element[0] != null) {
						listCardDTO.setCompanyId(element[0].toString());
					}
					if(element[1] != null) {
						listCardDTO.setNextfareCompanyId(element[1].toString());
					}
					if(element[2] != null) {
						listCardDTO.setCompanyName(element[2].toString());
					}
					if(element[3] != null) {
						listCardDTO.setCompanyType(element[3].toString());
					}
					if(element[4] != null) {
						listCardDTO.setCompanyStatus(element[4].toString());
					}

					partnerDetailsList.add(listCardDTO);
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got partnerDetails List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return partnerDetailsList;
	}

	/**
	 * To get TMA Partner Details
	 *
	 * @param deactivatePartnerAccountForm
	 * @return tmaPartnerDetailsList
	 * @throws Exception
	 */
	public List getTMAPartnerDetails(ListCardForm listCardForm)
	throws Exception {
		final String MY_NAME = ME + "getTMAPartnerDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List tmaPartnerDetailsList = new ArrayList();

		try {
			session = getSession();
			transaction = session.beginTransaction();

			String query =
					"select" +
						" tmaInformation.tmaId," +
						" tmaInformation.tmaName," +
						" tmaInformation.tmaUsername," +
						" tmaInformation.tmaEmail" +
					" from" +
						" TmaInformation tmaInformation" +
					" where" +
						" tmaInformation.tmaPasswordStatus = '" + Constants.TMA_PASSWORD_STATUS_ACTIVE + "'";

			List queryList= session.createQuery(query).list();

			if(queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					ListCardDTO listCardDTO = new ListCardDTO();
					if(element[0] != null) {
						listCardDTO.setTmaId(element[0].toString());
					}
					if(element[1] != null) {
						listCardDTO.setTmaName(element[1].toString());
					}
					if(element[2] != null) {
						listCardDTO.setTmaUserName(element[2].toString());
					}
					if(element[3] != null) {
						listCardDTO.setTmaEmail(element[3].toString());
					}

					tmaPartnerDetailsList.add(listCardDTO);
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got TMA partnerDetails List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return tmaPartnerDetailsList;
	}
	/**
	 * To get TmaCompanyList Details
	 *
	 * @param listCardForm
	 * @return tmaCompanyList
	 * @throws Exception
	 */
	public List getTmaCompanyList(String tmaId)
	throws Exception {
		final String MY_NAME = ME + "getTmaCompanyList: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List tmaCompanyList = new ArrayList();

		try {
			session = getSession();
			transaction = session.beginTransaction();

			String query = MartaQueries.GET_TMA_COMPANYLIST_QUERY ;
			query = query + " and tmaInfo.tmaId = "+tmaId;
			List queryList= session.createQuery(query).list();

			if(queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					ListCardDTO listCardDTO = new ListCardDTO();
					if(element[0] != null) {
						listCardDTO.setCompanyId(element[0].toString());
					}
					if(element[1] != null) {
						listCardDTO.setNextfareCompanyId(element[1].toString());
					}
					if(element[2] != null) {
						listCardDTO.setCompanyName(element[2].toString());
					}
					if(element[3] != null) {
						listCardDTO.setTmaName(element[3].toString());
					}


					tmaCompanyList.add(listCardDTO);
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got partnerDetails List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return tmaCompanyList;
	}

	/**
	 * To get TMA member benefit details by company Id.
	 *
	 * @param companyId
	 * @return memberBenefitList
	 * @throws Exception
	 */
	public List getTmaMemberBenefitDetailsByCompanyId(String companyId,String memberId) throws Exception {
		final String MY_NAME = ME + "getTmaMemberBenefitDetailsByCompanyId: ";
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List memberBenefitList = new ArrayList();

		try {
			String query = "select" +
								" CUSTOMER_ID, MEMBER_ID, BENEFIT_ID" +
							" from" +
								" nextfare_main.MEMBER_BENEFIT" +
							" where" +
								" CUSTOMER_ID = " + companyId+" and MEMBER_ID ="+memberId;

			nextFareConnection = NextFareConnection.getConnection();

			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}

			while(rs.next()){
				MemberBenefitDetailsDTO memberBenefitDetailsDTO = new MemberBenefitDetailsDTO();
				memberBenefitDetailsDTO.setCustomerId(rs.getString("CUSTOMER_ID"));
				memberBenefitDetailsDTO.setMemberId(rs.getString("MEMBER_ID"));
				memberBenefitDetailsDTO.setBenefitId(rs.getString("BENEFIT_ID"));

				memberBenefitList.add(memberBenefitDetailsDTO);
			}

			BcwtsLogger.info(MY_NAME + "got TMA member benefit details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		return memberBenefitList;
	}

	public MemberDTO getMemberId(String customerMemberid, String customerId) throws Exception {
		final String MY_NAME = ME + "getMembrID: ";
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		Statement stmt = null;
		ResultSet rs = null;

		MemberDTO memberDTO = new MemberDTO();
		try {
			String query = "select" +
								" MEMBER_ID, PHONE_ID"  +
							" from" +
								" nextfare_main.MEMBER" +
							" where" +
								" CUSTOMER_MEMBER_ID = '" + customerMemberid +
								"' and CUSTOMER_ID = " + customerId;

			nextFareConnection = NextFareConnection.getConnection();

				stmt = nextFareConnection.createStatement();

					rs = stmt.executeQuery(query);


			while(rs.next()){
				memberDTO.setMemberid(rs.getString("MEMBER_ID"));
				memberDTO.setPhoneid(rs.getString("PHONE_ID"));
			}
			rs.close();
			stmt.close();
			nextFareConnection.close();


			BcwtsLogger.info(MY_NAME + "got Member Id ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
		/*	rs.close();
			stmt.close();
			nextFareConnection.close();*/
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}


		return memberDTO;
	}

	public UpassSchools getSchoolByNextfareId(String nextfareId) throws Exception {
		final String MY_NAME = ME + "getSchoolByNextfareId for company id "+nextfareId;
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		UpassSchools upassschool = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from UpassSchools upassschool where upassschool.nextfareidfaculty = '"+nextfareId+"'" +
					" or upassschool.nextfareidstudent ='"+nextfareId+"'";

			upassschool = (UpassSchools) session.createQuery(query).uniqueResult();

			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + " end");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return upassschool;
	}

	public boolean addNewUpassCard(UpassNewCard upassNewCard) throws Exception {
		final String MY_NAME = ME + "addNewCard: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();

			session.save(upassNewCard);
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "new card added");
			isAdded = true;

		} catch (Exception ex) {
			isAdded = false;
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isAdded;
	}


}
