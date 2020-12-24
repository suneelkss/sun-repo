package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.hibernate.BcwtConfigParams;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.utils.BcwtsLogger;


public class ServerConfigDAO extends MartaBaseDAO{

	final String ME = "ServerConfigDAO: ";
	/**
	 * This will get all congiguration paramaeters
	 * @return List
	 * @throws Exception
	 */
	public List getServerConfigParamList()
			throws Exception {

		final String MY_NAME = ME + "getServerConfigParamList: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List serverConfigParamList = new ArrayList();
		BcwtConfigParams bcwtConfigParams = new BcwtConfigParams();
		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			serverConfigParamList = session.createQuery(
					"from BcwtConfigParams order by configid").list();
			for (Iterator iter = serverConfigParamList.iterator(); iter
					.hasNext();) {
				bcwtConfigParams = (BcwtConfigParams) iter.next();

			}
			transaction.commit();
			session.flush();			
			BcwtsLogger.info(MY_NAME
					+ "Got server ConfigParam list and whose size is "
					+ serverConfigParamList.size());
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;

		} finally {
			closeSession(session, transaction);
			
		}
		return serverConfigParamList;
	}
	/**
	 * To get Patron details
	 * @return List
	 * @throws Exception
	 */
	public List getPatronDetails()
		throws Exception {
	
	final String MY_NAME = ME + "getPatronDetails: ";
	BcwtsLogger.debug(MY_NAME);
	Session session = null;
	Transaction transaction = null;
	List patronList = new ArrayList();
	BcwtPatron bcwtPatron = new BcwtPatron();
	try {
		BcwtsLogger.debug(MY_NAME + "Get Session");
		session = getSession();
		BcwtsLogger.debug(MY_NAME + " Got Session");
		transaction = session.beginTransaction();
		BcwtsLogger.debug(MY_NAME + " Execute Query");
		patronList = session.createQuery(
				"from BcwtPatron order by patronid").list();
		for (Iterator iter = patronList.iterator(); iter
				.hasNext();) {
			bcwtPatron = (BcwtPatron) iter.next();
	
		}
		transaction.commit();
		session.flush();		
		BcwtsLogger.info(MY_NAME
				+ "Got Patron list and whose size is "
				+ patronList.size());
	} catch (Exception ex) {
		BcwtsLogger.error(MY_NAME + ex.getMessage());
		throw ex;
	
	} finally {
		closeSession(session, transaction);
		
	}
	return patronList;
}
	/**
	 * To get configuration parameter value
	 * @param configName
	 * @return String
	 * @throws Exception
	 */
	public String getConfigValue(String configName) throws Exception {
		final String MY_NAME = ME + "getConfigValue: " + configName;
		String paramValue = "";
		BcwtsLogger.info(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		try {
			BcwtsLogger.debug(MY_NAME + " Get Session");
			session = getSession();
			transaction = session.beginTransaction();
			String sqlQuery = null;
			
			sqlQuery = "from BcwtConfigParams bcwtConfigParams where configname=:configname";
			BcwtConfigParams configParams = (BcwtConfigParams) session.createQuery(
					sqlQuery).setParameter("configname", configName)
					.uniqueResult();
			if (null != configParams.getParamvalue()
					&& !configParams.getParamvalue().equals("")) {
				paramValue = configParams.getParamvalue();
				BcwtsLogger.info(MY_NAME
						+ " Got ConfigValue for param name's param value"
						+ configName + " -- " + paramValue);
			} else {
				paramValue = configParams.getDefaultvalue();
				BcwtsLogger.info(MY_NAME
						+ " Got ConfigValue for param name's default value "
						+ configName + " -- " + paramValue);
			}
			transaction.commit();
			session.clear();

		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.info(MY_NAME + " Resources closed");
		}
		return paramValue;
	}
}
