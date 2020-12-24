package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.dto.NegativeListCardsDTO;
import com.marta.admin.forms.NegativeListCardForm;
import com.marta.admin.hibernate.BcwtOrder;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.hibernate.BcwtPatronPaymentCards;
import com.marta.admin.utils.Base64EncodeDecodeUtil;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.SimpleProtector;
import com.marta.admin.utils.Util;

public class NegativeListCardsDAO extends MartaBaseDAO {
	public List getNegativeListCardDetails(
			NegativeListCardForm negativeListCardForm, String patronid)
			throws Exception {
		final String MY_NAME = "getNegativeListCardDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		String query = null;
		List isOrderInfoList = new ArrayList();
		String patronName = null;
		String expiryDate = null;
		String editcreditCardNo = "xxxx xxxx xxxx ";
		String newKeyValue = null;
		ApplicationSettingsDAO applicationSettingsDAO = new ApplicationSettingsDAO();
		try {
			session = getSession();
			transaction = session.beginTransaction();
			query = " select"
					+ " bcwtPatron.firstname, bcwtPatron.lastname, bcwtPatronPaymentCards.creditcardno, "
					+ " bcwtPatronPaymentCards.expirymonth, bcwtPatronPaymentCards.expiryyear, bcwtPatronAddress.zip, "
					+ " bcwtPatronPaymentCards.negativeliststatus,bcwtPatron.patronid,bcwtPatronPaymentCards.patronpaymentcardid "
					+ " from"
					+ " BcwtPatronPaymentCards bcwtPatronPaymentCards, BcwtPatron bcwtPatron,"
					+ " BcwtPatronAddress bcwtPatronAddress "
					+ " where"
					+ " bcwtPatron.patronid = bcwtPatronPaymentCards.bcwtpatron.patronid "
					+ " and bcwtPatronAddress.patronaddressid = bcwtPatronPaymentCards.bcwtpatronaddress.patronaddressid";

			if (!Util.isBlankOrNull(negativeListCardForm.getPatronFirstName())) {
				query = query
						+ " and UPPER(bcwtPatron.firstname) like '%"
						+ negativeListCardForm.getPatronFirstName()
								.toUpperCase() + "%'";
			}
			if (!Util.isBlankOrNull(negativeListCardForm.getPatronLastName())) {
				query = query
						+ " and UPPER(bcwtPatron.lastname) like '%"
						+ negativeListCardForm.getPatronLastName()
								.toUpperCase() + "%'";
			}			
			List queryList = session.createQuery(query).list();
			
			newKeyValue  = ConfigurationCache.getConfigurationValues
			(Constants.CREDIT_CARD_SECURITY_KEY).getParamvalue();
	
			newKeyValue = Base64EncodeDecodeUtil.decodeString(newKeyValue);
	
			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator ite = queryList.iterator(); ite.hasNext();) {
					NegativeListCardsDTO negativeListCardsDTO = new NegativeListCardsDTO();
					Object[] obj = (Object[]) ite.next();

					if (obj[0] != null) {
						negativeListCardsDTO.setPatronFirstName(obj[0]
								.toString());
					}
					if (obj[1] != null) {
						negativeListCardsDTO.setPatronLastName(obj[1]
								.toString());
					}
					if (!Util.isBlankOrNull(negativeListCardsDTO
							.getPatronFirstName())) {
						patronName = negativeListCardsDTO.getPatronFirstName();
					}
					if (!Util.isBlankOrNull(negativeListCardsDTO
							.getPatronLastName())) {
						patronName = patronName + " "
								+ negativeListCardsDTO.getPatronLastName();
						negativeListCardsDTO.setPatronName(patronName);
					}
					if (obj[2] != null) {						
						try{
						negativeListCardsDTO.setCreditCardNo(editcreditCardNo
								.concat(SimpleProtector.decrypt(
										obj[2].toString(), newKeyValue)
										.substring(12)));	
						}catch(Exception e){
							BcwtsLogger.error(MY_NAME
									+ Util.getFormattedStackTrace(e));
							negativeListCardsDTO.setCreditCardNo("Invalid Number");
							
							
						}
					}
					if (obj[3] != null) {
						negativeListCardsDTO.setExpiryMonth(obj[3].toString());
					}

					if (obj[4] != null) {
						negativeListCardsDTO.setExpiryYear(obj[4].toString());
					}

					if (!Util.isBlankOrNull(negativeListCardsDTO
							.getExpiryMonth())) {
						expiryDate = negativeListCardsDTO.getExpiryMonth();
					}

					if (!Util.isBlankOrNull(negativeListCardsDTO
							.getExpiryYear())) {
						expiryDate = expiryDate + " "
								+ negativeListCardsDTO.getExpiryYear();
						negativeListCardsDTO.setExpiryDate(expiryDate);
					}

					if (obj[5] != null) {
						negativeListCardsDTO.setZipCode(obj[5].toString());
					}
					if (obj[6] != null) {
						negativeListCardsDTO.setNegativeListStatus(obj[6]
								.toString());
					}
					if (obj[7] != null) {
						negativeListCardsDTO.setPatronId(obj[7].toString());
					}
					if (obj[8] != null) {
						negativeListCardsDTO
								.setPaymentCardId(obj[8].toString());
					}

					isOrderInfoList.add(negativeListCardsDTO);
				}
			}			
			session.flush();
			session.close();
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(e));
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		return isOrderInfoList;
	}

	public NegativeListCardsDTO populateNegativeListViewDetails(String orderId)
			throws Exception {

		final String MY_NAME = "getAdminList: ";
		BcwtsLogger.debug(MY_NAME + "getting admin details started");
		Session session = null;
		Transaction transaction = null;
		BcwtPatron bcwtPatron = null;
		BcwtOrder bcwtorder = null;
		List viewList = null;
		List orderList = null;
		NegativeListCardsDTO negativeListCardsDTO = null;
		String patronName = null;
		String expiryDate = null;
		BcwtConfigParamsDTO configParamDTO = ConfigurationCache
				.getConfigurationValues(Constants.IS_MARTA_ENV);
		String editpcreditCardNo = "xxxx xxxx xxxx ";
		List logListToIterate = new ArrayList();
		BcwtOrder bcwtOrder2 = null;
		String newKeyValue = null;
		ApplicationSettingsDAO applicationSettingsDAO = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			applicationSettingsDAO = new ApplicationSettingsDAO();

			orderList = new ArrayList();

			Integer order = Integer.valueOf(orderId);

			String query = " select "
					+ " bcwtPatron.firstname,bcwtPatron.lastname,bcwtPatronPaymentCards.negativeliststatus, "
					+ " bcwtPatronPaymentCards.creditcardno,bcwtPatronPaymentCards.expirymonth, bcwtPatronPaymentCards.expiryyear "
					+ " from "
					+ " BcwtPatronPaymentCards bcwtPatronPaymentCards, BcwtPatron bcwtPatron"
					+ " where"
					+ " bcwtPatronPaymentCards.patronpaymentcardid = '"
					+ order
					+ "' and bcwtPatron.patronid = bcwtPatronPaymentCards.bcwtpatron.patronid ";

			List queryList = session.createQuery(query).list();
			
			newKeyValue  = ConfigurationCache.getConfigurationValues
						(Constants.CREDIT_CARD_SECURITY_KEY).getParamvalue();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator ite = queryList.iterator(); ite.hasNext();) {
					negativeListCardsDTO = new NegativeListCardsDTO();
					Object[] obj = (Object[]) ite.next();
					if (obj[0] != null) {
						negativeListCardsDTO.setPatronFirstName(obj[0]
								.toString());
					}
					if (obj[1] != null) {
						negativeListCardsDTO.setPatronLastName(obj[1]
								.toString());
					}
					if (!Util.isBlankOrNull(negativeListCardsDTO
							.getPatronFirstName())) {
						patronName = negativeListCardsDTO.getPatronFirstName();
					}
					if (!Util.isBlankOrNull(negativeListCardsDTO
							.getPatronLastName())) {
						patronName = patronName + " "
								+ negativeListCardsDTO.getPatronLastName();
						negativeListCardsDTO.setPatronName(patronName);
					}
					if (obj[2] != null) {
						negativeListCardsDTO.setNegativeListStatus(obj[2]
								.toString());
					}
					if (obj[3] != null) {
						
						negativeListCardsDTO.setCreditCardNo(editpcreditCardNo
								.concat(SimpleProtector.decrypt(
										obj[3].toString(), Base64EncodeDecodeUtil.decodeString(newKeyValue))
										.substring(12)));			

					}
					if (obj[4] != null) {
						negativeListCardsDTO.setExpiryMonth(obj[4].toString());
					}

					if (obj[5] != null) {
						negativeListCardsDTO.setExpiryYear(obj[5].toString());
					}

					if (!Util.isBlankOrNull(negativeListCardsDTO
							.getExpiryMonth())) {
						expiryDate = negativeListCardsDTO.getExpiryMonth();
					}

					if (!Util.isBlankOrNull(negativeListCardsDTO
							.getExpiryYear())) {
						expiryDate = expiryDate + " "
								+ negativeListCardsDTO.getExpiryYear();
						negativeListCardsDTO.setExpiryDate(expiryDate);
					}

				}

			}

			session.flush();
			session.close();

			BcwtsLogger.info(MY_NAME + "retriving into the DB");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		return negativeListCardsDTO;
	}

	public boolean updateNegativeListCardsDetails(
			NegativeListCardsDTO negativeListCardsDTO) throws Exception {

		final String MY_NAME = " updateNegativeListCardsDetails: ";
		BcwtsLogger.debug(MY_NAME + " updating Negative List Cards ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		// BcwtOrder bcwtorder=null;
		// BcwtPatronPaymentCards bcwtPatronPaymentCards=null;

		BcwtSearchDTO SearchDTO = new BcwtSearchDTO();
		String patronName = null;
		// BcwtConfigParams bcwtConfigParams = null;
		BcwtPatronPaymentCards bcwtPatronPaymentCards = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			// bcwtConfigParams = getBcwtConfigParams(configId);
			patronName = negativeListCardsDTO.getPatronName();
			String query = "from BcwtPatronPaymentCards bcwtPatronPaymentCards where "
					+ "bcwtPatronPaymentCards.patronpaymentcardid = '"
					+ negativeListCardsDTO.getPaymentCardId() + "'";

			bcwtPatronPaymentCards = (BcwtPatronPaymentCards) session
					.createQuery(query).uniqueResult();
			transaction.commit();
			session.flush();
			session.close();
			if (bcwtPatronPaymentCards != null) {
				if (!Util.isBlankOrNull(negativeListCardsDTO
						.getNegativeListStatus())) {
					bcwtPatronPaymentCards
							.setNegativeliststatus(negativeListCardsDTO
									.getNegativeListStatus());
				}
				session = getSession();
				transaction = session.beginTransaction();
				session.update(bcwtPatronPaymentCards);
				isUpdated = true;
				transaction.commit();
				session.flush();
				session.close();
			}
			BcwtsLogger.info(MY_NAME
					+ "Application settings details updated to database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron details :"
					+ Util.getFormattedStackTrace(e));
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}
	
	

}
