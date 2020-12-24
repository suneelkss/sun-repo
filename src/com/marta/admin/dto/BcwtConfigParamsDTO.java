package com.marta.admin.dto;

/**
 * This DTO is used to store the configuration parameters. 
 * @author admin
 *
 */

public class BcwtConfigParamsDTO  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer configid;
    private String configname;
    private String paramvalue;
    private String defaultvalue;
    private String activestatus;
	
    /**
	 * @return the activestatus
	 */
	public String getActivestatus() {
		return activestatus;
	}
	/**
	 * @param activestatus the activestatus to set
	 */
	public void setActivestatus(String activestatus) {
		this.activestatus = activestatus;
	}
	/**
	 * @return the configid
	 */
	public Integer getConfigid() {
		return configid;
	}
	/**
	 * @param configid the configid to set
	 */
	public void setConfigid(Integer configid) {
		this.configid = configid;
	}
	/**
	 * @return the configname
	 */
	public String getConfigname() {
		return configname;
	}
	/**
	 * @param configname the configname to set
	 */
	public void setConfigname(String configname) {
		this.configname = configname;
	}
	/**
	 * @return the defaultvalue
	 */
	public String getDefaultvalue() {
		return defaultvalue;
	}
	/**
	 * @param defaultvalue the defaultvalue to set
	 */
	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}
	/**
	 * @return the paramvalue
	 */
	public String getParamvalue() {
		return paramvalue;
	}
	/**
	 * @param paramvalue the paramvalue to set
	 */
	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}

    
    
   }