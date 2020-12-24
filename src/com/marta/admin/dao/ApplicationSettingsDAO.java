package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.ApplicationSettingsDTO;
import com.marta.admin.hibernate.BcwtConfigParams;
import com.marta.admin.hibernate.BcwtPatronPaymentCards;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.SimpleProtector;
import com.marta.admin.utils.Util;

/**
 * 
 * @author Administrator
 *
 */
public class ApplicationSettingsDAO extends MartaBaseDAO{
	
	final String ME = "ApplicationSettingsDAO: ";

	/**
	 * Method to get application settings details list
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getApplicationSettingsList()
			throws Exception {
			
			final String MY_NAME = ME + "getApplicationSettingsList: ";
			BcwtsLogger.debug(MY_NAME);
			Session session = null;
			Transaction transaction = null;
			List applicationSettingsParameters = new ArrayList();
			List applicationSettingsList = new ArrayList();
			ApplicationSettingsDTO applicationSettingsDTO = null;
			
			try {
				BcwtsLogger.debug(MY_NAME + "Get Session");
				session = getSession();
				BcwtsLogger.debug(MY_NAME + " Got Session");
				transaction = session.beginTransaction();
				BcwtsLogger.debug(MY_NAME + " Execute Query");
				String query = "from BcwtConfigParams";
				applicationSettingsParameters = session.createQuery(query).list();
				
				for (Iterator iter = applicationSettingsParameters.iterator(); iter
						.hasNext();) {
					BcwtConfigParams bcwtConfigParams = (BcwtConfigParams) iter.next();				
					if (bcwtConfigParams != null) {
						applicationSettingsDTO = new ApplicationSettingsDTO();
						applicationSettingsDTO.setActiveStatus(bcwtConfigParams.getActivestatus());
						applicationSettingsDTO.setConfigName(bcwtConfigParams.getConfigname());
						applicationSettingsDTO.setParamValue(bcwtConfigParams.getParamvalue());
						applicationSettingsDTO.setDefaultValue(bcwtConfigParams.getDefaultvalue());
						applicationSettingsDTO.setConfigId(bcwtConfigParams.getConfigid().toString());
						applicationSettingsList.add(applicationSettingsDTO);
				}
			}
			transaction.commit();
			session.flush();			
			BcwtsLogger.info(MY_NAME
					+ "Got server ConfigParam list and whose size is "
						+ applicationSettingsParameters.size());
			} catch (Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;
			
			} finally {
				closeSession(session, transaction);
			}
			return applicationSettingsList;
}
	/**
	 * Method to get application settings details for particular configId
	 * 
	 * @param configId
	 * @return
	 * @throws Exception
	 */
	public ApplicationSettingsDTO getApplicationSettingsDetails(Long configId)throws Exception{
		
		final String MY_NAME = ME + " getApplicationSettingsDetailsList: ";
		BcwtsLogger.debug(MY_NAME + " to get application details list ");

		Transaction transaction = null;
		Session session = null;
		ApplicationSettingsDTO applicationSettingsDTO = null;
		
		try {

			BcwtConfigParams bcwtConfigParams = new BcwtConfigParams();
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			String query= "from " +
								"BcwtConfigParams " +
						  "where " +
						  		"configid = "+configId;
			
			bcwtConfigParams = (BcwtConfigParams)session.createQuery(query).uniqueResult();
	
			if(bcwtConfigParams!=null){
				applicationSettingsDTO = new ApplicationSettingsDTO();
				if(null!=bcwtConfigParams.getParamvalue()){					
					applicationSettingsDTO.setParamValue(bcwtConfigParams.getParamvalue());
				}
				if(null!=bcwtConfigParams.getDefaultvalue()){					
					applicationSettingsDTO.setDefaultValue(bcwtConfigParams.getDefaultvalue());
				}
				if(null!=bcwtConfigParams.getActivestatus()){
					applicationSettingsDTO.setActiveStatus(bcwtConfigParams.getActivestatus());
				}
				if(null != bcwtConfigParams.getConfigname()){
					applicationSettingsDTO.setConfigName(bcwtConfigParams.getConfigname());
				}
			}
			transaction.commit();
			session.flush();
			session.close();
		}
		 catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		return applicationSettingsDTO;
	}
	/**
	 * to update application settings for particular configId
	 * 
	 * @param updateApplicationSettingsDTO
	 * @return
	 * @throws Exception
	 */
	public boolean updateApplicationSettingsDetails(ApplicationSettingsDTO updateApplicationSettingsDTO) 
														throws Exception {
		
		final String MY_NAME = ME + " updateApplicationSettingsDetails: ";
		BcwtsLogger.debug(MY_NAME + " updating application settings ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		BcwtConfigParams bcwtConfigParams = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			Long configId = new Long(updateApplicationSettingsDTO.getConfigId());
			String query = "from " +
								"BcwtConfigParams bcwtConfigParams " +
						   "where " +
						   		"bcwtConfigParams.configid= '"+ configId +"' ";
			bcwtConfigParams = (BcwtConfigParams)session.createQuery(query).uniqueResult();

			if(updateApplicationSettingsDTO.getParamValue()!=null ){
				bcwtConfigParams.setParamvalue(updateApplicationSettingsDTO.getParamValue());				
			}
			if(updateApplicationSettingsDTO.getDefaultValue()!=null){
				bcwtConfigParams.setDefaultvalue(updateApplicationSettingsDTO.getDefaultValue());				
			}
			if(updateApplicationSettingsDTO.getActiveStatus()!=null){
				bcwtConfigParams.setActivestatus(updateApplicationSettingsDTO.getActiveStatus());				
			}
			session.update(bcwtConfigParams);
			isUpdated = true;
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Application settings details updated to database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating application settings details :"
					+ e.getMessage());
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}
	
	/**
	 * Method to reset param value to default value for particular configId
	 * 
	 * @param configId
	 * @return
	 * @throws Exception
	 */
	public boolean resetParamValue(Long configId) throws Exception {
		
		final String MY_NAME = ME + " resetParamValue: ";
		BcwtsLogger.debug(MY_NAME + " resetting param value in application settings ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		BcwtConfigParams bcwtConfigParams = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from " +
								"BcwtConfigParams bcwtConfigParams " +
							"where " +
								"bcwtConfigParams.configid= '"+ configId +"' ";
			bcwtConfigParams = (BcwtConfigParams)session.createQuery(query).uniqueResult();
			
			if(bcwtConfigParams != null ){
				if(bcwtConfigParams.getDefaultvalue() != null ){
					bcwtConfigParams.setParamvalue(bcwtConfigParams.getDefaultvalue());
				}
			}
			session.update(bcwtConfigParams);
			isUpdated = true;
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "reset the param value to database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in resetting param value in application settings :"
					+ e.getMessage());
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}
	
	/**
	 * 
	 * @param configId
	 * @return
	 * @throws Exception
	 */
	public boolean updateCreditCardNumbers(String currentSecurityKey,
			String newSecurityKey) throws Exception {
		
		final String MY_NAME = ME + " updateCreditCardNumbers: ";
		BcwtsLogger.debug(MY_NAME + " updating credit card numbers ");
		boolean isCreditCardsUpdated = false;
		Session session = null;
		Transaction transaction = null;
		BcwtConfigParams bcwtConfigParams = null;
		String keyValue = null;
		String decryptedCreditCardNumber = null;
			
		try {
			List getPatronPaymentCardsList = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtPatronPaymentCards";
			getPatronPaymentCardsList = session.createQuery(query).list();
			
			for (Iterator iter = getPatronPaymentCardsList.iterator(); iter.hasNext();){
				BcwtPatronPaymentCards bcwtPatronPaymentCards = (BcwtPatronPaymentCards) iter.next();
				
				decryptedCreditCardNumber = SimpleProtector.decrypt(bcwtPatronPaymentCards.getCreditcardno(),
						currentSecurityKey);
				bcwtPatronPaymentCards.setCreditcardno(SimpleProtector.encrypt
						(decryptedCreditCardNumber, newSecurityKey));
				session.save(bcwtPatronPaymentCards);
			}
			isCreditCardsUpdated = true;
			transaction.commit();
			session.flush();
			session.close();
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating credit card numbers :"
					+ e.getMessage());
			throw e;
		}
		return isCreditCardsUpdated;	
	}
		
	/**
	 * Method to get current key value
	 * @param configId
	 * @return
	 * @throws Exception
	 */
	public String getCurrentCreditCardKey(String configId) throws Exception {
		
		final String MY_NAME = ME + " getCurrentCreditCardKey: ";
		BcwtsLogger.debug(MY_NAME + " getting current key value from config params ");		
		Session session = null;
		Transaction transaction = null;
		BcwtConfigParams bcwtConfigParams = null;
		String keyValue = null;
		
		try {			
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from " +
								"BcwtConfigParams bcwtConfigParams " +
						   "where " +
								"bcwtConfigParams.configid= '"+ Long.valueOf(configId) +"' ";
			bcwtConfigParams = (BcwtConfigParams)session.createQuery(query).uniqueResult();
			
			if(!Util.isBlankOrNull(bcwtConfigParams.getParamvalue())){
				keyValue = bcwtConfigParams.getParamvalue();
			}
			transaction.commit();
			session.flush();
			session.close();
		}
		catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in getting current credit card key :"
					+ e.getMessage());
			throw e;
		}
		return keyValue;
	}
	
	
	/**
	 * 
	 * @param confirName
	 * @return
	 * @throws Exception
	 */
	/*
	public String getCreditCardKeyValueByConfigName(String configName) 
		throws Exception{
		
		final String MY_NAME = ME + " getCreditCardKeyValueByConfigName: ";
		BcwtsLogger.debug(MY_NAME + " getting key value from config params by config name");		
		Session session = null;
		Transaction transaction = null;
		BcwtConfigParams bcwtConfigParams = null;
		String keyValue = null;
		
		try {			
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from " +
								"BcwtConfigParams bcwtConfigParams " +
						   "where " +
								"bcwtConfigParams.configname = '"+ configName +"' ";
			bcwtConfigParams = (BcwtConfigParams)session.createQuery(query).uniqueResult();
			
			if(!Util.isBlankOrNull(bcwtConfigParams.getParamvalue())){
				keyValue = bcwtConfigParams.getParamvalue();
			}
			transaction.commit();
			session.flush();
			session.close();
		}
		catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in getting current credit card key :"
					+ e.getMessage());
			throw e;
		}
		return keyValue;
	}
	*/
}
