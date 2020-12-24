package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.TmaEditQueueDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.hibernate.BcwtPartnerNewCard;
import com.marta.admin.hibernate.PartnerBatchDetails;
import com.marta.admin.hibernate.PartnerBatchDetailsId;
import com.marta.admin.hibernate.PartnerHotlistHistory;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;

public class TmaEditQueueDAO extends MartaBaseDAO {

	final String ME = "TmaEditQueueDAO: ";

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
		TmaEditQueueDTO tmaEditQueueDTO = null;
	//	BcwtsLogger.info("for company "+);
		try {
			session = getSession();
			transaction = session.beginTransaction();
	//		String query = "from BcwtPartnerNewCard bcwtPartnerNewCard where bcwtPartnerNewCard.companyname in (select partnercompanyinfo.companyName from PartnerCompanyInfo partnercompanyinfo where partnercompanyinfo.companyId in ("+companyIdStr+"))";
			String query = "from BcwtPartnerNewCard bcwtPartnerNewCard";
			
			List queryList = session.createQuery(query).list();
         
			if(queryList != null && !queryList.isEmpty()){
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					BcwtPartnerNewCard bcwtPartnerNewCard = (BcwtPartnerNewCard) iter.next();
					tmaEditQueueDTO = new TmaEditQueueDTO();
					tmaEditQueueDTO.setId(bcwtPartnerNewCard.getPartnernewcardid().toString());
				
					tmaEditQueueDTO.setBreezecardid(bcwtPartnerNewCard.getSerialnumber());
					tmaEditQueueDTO.setCompanyname(bcwtPartnerNewCard.getCompanyname());
					tmaEditQueueDTO.setCustomermemberid(bcwtPartnerNewCard.getCustomermemberid());
					tmaEditQueueDTO.setAction(Constants.TMA_NEWCARD);
					tmaEditQueueDTO.setActiontype(1);
					newCardDetailsList.add(tmaEditQueueDTO);
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got new card details list from DB "+newCardDetailsList.size());
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return newCardDetailsList;
	}


	/**
	 * To get hotlist card details.
	 *
	 * @return hotlistCardDetailsList
	 * @throws Exception
	 */
	public List getHotlistCardDetails() throws Exception {
		final String MY_NAME = ME + "getHotlistCardDetails: ";
		BcwtsLogger.debug(MY_NAME);
		List hotlistCardDetailsList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		TmaEditQueueDTO tmaEditQueueDTO = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from PartnerHotlistHistory partnerHotlistHistory";
			List queryList = session.createQuery(query).list();

			if(queryList != null && !queryList.isEmpty()){
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					PartnerHotlistHistory partnerHotlistHistory = (PartnerHotlistHistory) iter.next();
					tmaEditQueueDTO = new TmaEditQueueDTO();
					tmaEditQueueDTO.setId(partnerHotlistHistory.getSrNo().toString());
					tmaEditQueueDTO.setBreezecardid(partnerHotlistHistory.getCardNumber());
					tmaEditQueueDTO.setCompanyname(partnerHotlistHistory.getCompanyName());
					tmaEditQueueDTO.setCustomermemberid(partnerHotlistHistory.getMemberId());
					if(partnerHotlistHistory.getHotlistFlag().equalsIgnoreCase("yes"))
					  tmaEditQueueDTO.setAction("Deactivate");
					else
						tmaEditQueueDTO.setAction("Activate");
					tmaEditQueueDTO.setActiontype(0);
					hotlistCardDetailsList.add(tmaEditQueueDTO);
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got hotlist card details list from DB ");
		} catch (Exception ex) {
			//ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return hotlistCardDetailsList;
	}

	/**
	 * To get hotlist card details.
	 *
	 * @return hotlistCardDetailsList
	 * @throws Exception
	 */
	public List getBatchDetails() throws Exception {
		final String MY_NAME = ME + "getBatchDetails: ";
		BcwtsLogger.debug(MY_NAME);
		List partnerBatchDetailsList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		TmaEditQueueDTO tmaEditQueueDTO = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();

			String query = "select partnerBatchDetails.cardSerialNo, partnerCompanyInfo.companyName,partnerBatchDetails.customerMemberId, partnerBatchDetails.benefitactionid from PartnerBatchDetails partnerBatchDetails, PartnerCompanyInfo partnerCompanyInfo where partnerBatchDetails.partnerId = partnerCompanyInfo.companyId";

			/*String query = " from PartnerBatchDetails partnerBatchDetails where " +
					" partnerBatchDetails.partnerId in (select partnerAdminDetails.partnerId from " +
					" PartnerAdminDetails partnerAdminDetails where " +
					" partnerAdminDetails.partnerCompanyInfo.companyId in ("+companyIdStr+"))";	*/


			List queryList = session.createQuery(query).list();

			if(queryList != null && !queryList.isEmpty()){
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
				//	PartnerBatchDetails partnerBatchDetails = (PartnerBatchDetails) iter.next();
				//	PartnerBatchDetailsId partnerBatchDetailsId = new PartnerBatchDetailsId();
					tmaEditQueueDTO = new TmaEditQueueDTO();
				//	partnerBatchDetailsId = partnerBatchDetails.getId();
			        tmaEditQueueDTO.setId(element[0].toString());
					//	System.out.println("id: "+partnerBatchDetails.getId().toString());
					tmaEditQueueDTO.setBreezecardid(element[0].toString());
					tmaEditQueueDTO.setCompanyname(element[1].toString());
					tmaEditQueueDTO.setCustomermemberid(element[2].toString());
					tmaEditQueueDTO.setAction(element[3].toString());
					tmaEditQueueDTO.setActiontype(0);
					partnerBatchDetailsList.add(tmaEditQueueDTO);
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got BatchDetails details list from DB ");
		} catch (Exception ex) {
			ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return partnerBatchDetailsList;
	}


	/**
	 * method to remove hotlist card
	 * @param id
	 * @return
	 * @throws MartaException
	 */
	public boolean removeHotlistCard(Long id)throws Exception{
		final String MY_NAME = ME + "removeHotlistCard():";
		BcwtsLogger.debug(MY_NAME);
		boolean isDeleted = false;
		Session session = null;
		Transaction transaction = null;
		PartnerHotlistHistory  partnerHotlistHistory = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			partnerHotlistHistory = (PartnerHotlistHistory) session.load
				(PartnerHotlistHistory.class,id);
			session.delete(partnerHotlistHistory);
			transaction.commit();
			isDeleted = true;

		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME
					+ " Exception in Deleting Hotlist details :"
					+ e.getMessage());
			throw e;
		}
		return isDeleted;
	}

	/**
	 * method to remove new card
	 * @param id
	 * @return
	 * @throws MartaException
	 */
	public boolean removeNewCard(Long id)throws Exception{
		final String MY_NAME = ME + "removeNewCard():";
		BcwtsLogger.debug(MY_NAME);
		boolean isDeleted = false;
		Session session = null;
		Transaction transaction = null;
		BcwtPartnerNewCard bcwtPartnerNewCard = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			bcwtPartnerNewCard = (BcwtPartnerNewCard) session.load
				(BcwtPartnerNewCard.class,id);
			session.delete(bcwtPartnerNewCard);
			transaction.commit();
			isDeleted = true;

		} catch (Exception e) {

			BcwtsLogger.error(MY_NAME
					+ " Exception in Deleting New card details :"
					+ e.getMessage());
			throw e;
		}
		return isDeleted;
	}

	/**
	 * method to remove batch card
	 * @param id
	 * @return
	 * @throws MartaException
	 */
	public boolean removeBatchCard(Long id,String action)throws Exception{
		final String MY_NAME = ME + "removeBatchCard():";
		BcwtsLogger.debug(MY_NAME);
		boolean isDeleted = false;
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();


			String sql = "delete from PartnerBatchDetails  where  cardSerialNo ='0"+id+"'" +
					" and benefitactionid = '"+action+"'";

			Query query = session.createQuery(sql);
			int row = query.executeUpdate();
			System.out.println("row: "+row);
			transaction.commit();
			session.flush();
			isDeleted = true;

		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME
					+ " Exception in Deleting Partner Batch details :"
					+ e.getMessage());
			throw e;
		}
		return isDeleted;
	}

}