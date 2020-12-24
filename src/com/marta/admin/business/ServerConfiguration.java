package com.marta.admin.business;

import java.util.ArrayList;
import java.util.List;

import com.marta.admin.dao.ServerConfigDAO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;

/**
 * This business class is used to get configuration details. * 
 * @author admin
 *
 */

public class ServerConfiguration {

	final String ME = "ServerConfiguration: ";
	
	/**
	 * Method to get server configuration parameter list.
	 * 
	 * @return
	 * @throws MartaException
	 */
	public List getServerConfigParamList()
		throws MartaException {
	
		final String MY_NAME = ME + "getServerConfigParamList: ";
		BcwtsLogger.debug(MY_NAME);
		
		ArrayList  serverConfigParameters = null;
		ServerConfigDAO serverConfigDAO = new ServerConfigDAO();
		try {
			BcwtsLogger.debug(MY_NAME
					+ "start calling DAO to get the server config param list");
			serverConfigParameters = (ArrayList) serverConfigDAO
					.getServerConfigParamList();
			BcwtsLogger.info(MY_NAME
					+ "returning the server config param list to servlet");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw new MartaException("eroor");
		}
		
		return serverConfigParameters;
	}
	
	/**
	 * Method to get server config value against parameter name.
	 * 
	 * @param paramName
	 * @return
	 * @throws MartaException
	 */
	public String getConfigValue(String paramName)
		throws MartaException {

		final String MY_NAME = ME + "getConfigValue():";
		
		String paramValue = "";
		ServerConfigDAO serverConfigDAO = new ServerConfigDAO();
		try {
			paramValue = serverConfigDAO.getConfigValue(paramName);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw new MartaException("Error while getting config param value '"+ paramName +"'");
		}
		
		return paramValue;
	}
}
