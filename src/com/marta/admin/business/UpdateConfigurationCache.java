package com.marta.admin.business;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.hibernate.BcwtConfigParams;
import com.marta.admin.utils.BcwtsLogger;

public class UpdateConfigurationCache {
	
	final String ME = "UpdateConfigurationCache :: ";
	
	public void updateConfigurationCache() throws Exception {
		final String MY_NAME = ME + " updateConfigurationCache: ";
		BcwtsLogger.debug(MY_NAME);
		
		ServerConfiguration serverConfiguration = new ServerConfiguration();
		
		try {
			List configValues = serverConfiguration.getServerConfigParamList();
			
			if(null != configValues && !configValues.isEmpty()){
				for (Iterator iter = configValues.iterator(); iter.hasNext();) {
					BcwtConfigParams bcwtConfigParams = (BcwtConfigParams) iter.next();
					BcwtConfigParamsDTO bcwtConfigParamsDTO = new BcwtConfigParamsDTO();
					BeanUtils.copyProperties(bcwtConfigParamsDTO, bcwtConfigParams);
					ConfigurationCache.setConfigurationValues(bcwtConfigParamsDTO.getConfigname().toString(), bcwtConfigParamsDTO);
				}
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in Updating Configuration Cache");
		}		
	}
}
