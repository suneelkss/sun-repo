package com.marta.admin.business;

import java.util.HashMap;

import com.marta.admin.dto.BcwtConfigParamsDTO;


public class ConfigurationCache {
	
	public static HashMap configurationValues = null;
	
	static {
		configurationValues = new HashMap();
	}

	public static BcwtConfigParamsDTO getConfigurationValues(String configName){
		return (BcwtConfigParamsDTO) configurationValues.get(configName);
	}
	
	public static void setConfigurationValues(String configName, BcwtConfigParamsDTO bcwtConfigParamsDTO) {
		if(configurationValues == null){
			configurationValues = new HashMap();
		}
		configurationValues.put(configName, bcwtConfigParamsDTO);		
	}	

	public static HashMap getConfigurationValues(){
		return configurationValues;
	}
	
	public static String getParamValue(String configName){
		return getConfigurationValues(configName).getParamvalue();
	}

}
