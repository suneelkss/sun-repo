package com.marta.admin.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class ListCardForm extends ValidatorActionForm {
	
	private String companyId;
	private String firstName;
	private String lastName;
	private String memberId;
	private String customerMemberId;
	private String serialNumber;
	private String newSerialNumber;
	private String confirmSerialNumber;
	private String emailId;
	private String phoneNumber;
	private String benefitId;
	private String benefitName;
	private String notes;

	//For Threshold
	private String thresholdCashValue;
	private String thresholdLimit;
	
	//for user type in list cards 
	private String userType;
	
	//for search
	private String companyName;
	private String companyType;
	private String companyStatus;
	
	private String tmaId;
	private String tmaName;
	private String tmaUserName;
	private String tmaEmail;
	
	private String nextfareCompanyId;
	
	private String hiddenPath;
	private String adminName;
	private String requestData;
	
	
	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getHiddenPath() {
		return hiddenPath;
	}

	public void setHiddenPath(String hiddenPath) {
		this.hiddenPath = hiddenPath;
	}

	public String getNextfareCompanyId() {
		return nextfareCompanyId;
	}

	public void setNextfareCompanyId(String nextfareCompanyId) {
		this.nextfareCompanyId = nextfareCompanyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getTmaEmail() {
		return tmaEmail;
	}

	public void setTmaEmail(String tmaEmail) {
		this.tmaEmail = tmaEmail;
	}

	public String getTmaId() {
		return tmaId;
	}

	public void setTmaId(String tmaId) {
		this.tmaId = tmaId;
	}

	public String getTmaName() {
		return tmaName;
	}

	public void setTmaName(String tmaName) {
		this.tmaName = tmaName;
	}

	public String getTmaUserName() {
		return tmaUserName;
	}

	public void setTmaUserName(String tmaUserName) {
		this.tmaUserName = tmaUserName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.companyId = null;
		this.firstName = null;
		this.lastName = null;
		this.memberId = null;
		this.customerMemberId = null;
		this.serialNumber = null;
		this.emailId = null;
		this.phoneNumber = null;	
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getBenefitId() {
		return benefitId;
	}

	public void setBenefitId(String benefitId) {
		this.benefitId = benefitId;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getConfirmSerialNumber() {
		return confirmSerialNumber;
	}

	public void setConfirmSerialNumber(String confirmSerialNumber) {
		this.confirmSerialNumber = confirmSerialNumber;
	}

	public String getNewSerialNumber() {
		return newSerialNumber;
	}

	public void setNewSerialNumber(String newSerialNumber) {
		this.newSerialNumber = newSerialNumber;
	}

	public String getThresholdCashValue() {
		return thresholdCashValue;
	}

	public void setThresholdCashValue(String thresholdCashValue) {
		this.thresholdCashValue = thresholdCashValue;
	}

	public String getThresholdLimit() {
		return thresholdLimit;
	}

	public void setThresholdLimit(String thresholdLimit) {
		this.thresholdLimit = thresholdLimit;
	}

	public String getBenefitName() {
		return benefitName;
	}

	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}

	public String getCustomerMemberId() {
		return customerMemberId;
	}

	public void setCustomerMemberId(String customerMemberId) {
		this.customerMemberId = customerMemberId;
	}

}
