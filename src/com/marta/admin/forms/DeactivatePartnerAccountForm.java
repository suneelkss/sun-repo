package com.marta.admin.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class DeactivatePartnerAccountForm extends ValidatorActionForm {

	private String accountType;
	
	private String companyId;
	private String companyName;
	private String companyType;
	private String companyStatus;
	
	private String tmaId;
	private String tmaName;
	private String tmaUserName;
	private String tmaEmail;
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.companyId = null;
		this.tmaId = null;
	}
	
	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the companyStatus
	 */
	public String getCompanyStatus() {
		return companyStatus;
	}
	/**
	 * @param companyStatus the companyStatus to set
	 */
	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}
	/**
	 * @return the companyType
	 */
	public String getCompanyType() {
		return companyType;
	}
	/**
	 * @param companyType the companyType to set
	 */
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	/**
	 * @return the tmaEmail
	 */
	public String getTmaEmail() {
		return tmaEmail;
	}
	/**
	 * @param tmaEmail the tmaEmail to set
	 */
	public void setTmaEmail(String tmaEmail) {
		this.tmaEmail = tmaEmail;
	}
	/**
	 * @return the tmaId
	 */
	public String getTmaId() {
		return tmaId;
	}
	/**
	 * @param tmaId the tmaId to set
	 */
	public void setTmaId(String tmaId) {
		this.tmaId = tmaId;
	}
	/**
	 * @return the tmaName
	 */
	public String getTmaName() {
		return tmaName;
	}
	/**
	 * @param tmaName the tmaName to set
	 */
	public void setTmaName(String tmaName) {
		this.tmaName = tmaName;
	}
	/**
	 * @return the tmaUserName
	 */
	public String getTmaUserName() {
		return tmaUserName;
	}
	/**
	 * @param tmaUserName the tmaUserName to set
	 */
	public void setTmaUserName(String tmaUserName) {
		this.tmaUserName = tmaUserName;
	}
	
	
}