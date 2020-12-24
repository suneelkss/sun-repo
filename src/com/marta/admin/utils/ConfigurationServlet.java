package com.marta.admin.utils;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.BeanUtils;

import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.business.ServerConfiguration;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.hibernate.BcwtConfigParams;
import com.marta.admin.utils.BcwtsLogger;

public class ConfigurationServlet extends HttpServlet{
	
	
	/**
	 * Constructor of the object.
	 */
	public ConfigurationServlet() {
		super();
	}
	/**
	 * Initialization of the servlet. 
	 *
	 * @throws ServletException if an error occure
	 */
	public void init(ServletConfig config) throws ServletException {		
		super.init(config);		
		try {
			ServerConfiguration serverConfiguration = new ServerConfiguration();
			ConfigurationCache configurationCache = new ConfigurationCache();
			List configValues = serverConfiguration.getServerConfigParamList();
			if(null != configValues && !configValues.isEmpty()){
				for (Iterator iter = configValues.iterator(); iter.hasNext();) {
					BcwtConfigParams bcwtConfigParams = (BcwtConfigParams) iter.next();
					BcwtConfigParamsDTO bcwtConfigParamsDTO = new BcwtConfigParamsDTO();
					BeanUtils.copyProperties(bcwtConfigParamsDTO, bcwtConfigParams);
					configurationCache.setConfigurationValues(bcwtConfigParamsDTO.getConfigname().toString(), bcwtConfigParamsDTO);
				}
			}
		} catch (Exception e) {			
			BcwtsLogger.error("Exception in ConfigurationServlet.init()" + e.getMessage());
		}
	}
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); 
		try{
			
		} catch (Exception e) {			
			BcwtsLogger.error("Exception in ConfigurationServlet.destroy()" + e.getMessage());
		}
	}

}
