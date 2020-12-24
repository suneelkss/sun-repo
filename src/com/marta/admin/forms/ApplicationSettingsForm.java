package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class ApplicationSettingsForm extends ValidatorActionForm{

	private String configId;
    private String configName;
    private String paramValue;
    private String defaultValue;
    private String activeStatus;
    private String editConfigId;
    private String newSecurityKey;
    
    
	public String getEditConfigId() {
		return editConfigId;
	}
	public void setEditConfigId(String editConfigId) {
		this.editConfigId = editConfigId;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	/**
	 * @return the newSecurityKey
	 */
	public String getNewSecurityKey() {
		return newSecurityKey;
	}
	/**
	 * @param newSecurityKey the newSecurityKey to set
	 */
	public void setNewSecurityKey(String newSecurityKey) {
		this.newSecurityKey = newSecurityKey;
	}

}
