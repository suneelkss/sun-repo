package com.marta.admin.dto;

import java.io.Serializable;

public class ListCardDTO implements Serializable {

	private String companyId;
	private String memberId;
	private String firstName;
	private String lastName;
	private String serialNumber;
	private String confirmSerialNumber;
	private String newSerialNumber;
	private String phoneNumber;
	private String emailId;
	private String benefitId;
	private String benefitName;
	private String notes;
	private String customerId;
	private String customerMemberId;
	private String action;
	
	// For Threshold
	private String thresholdCashValue;
	private String thresholdLimit;

	// To show benefit status on mouse over in datagrid
	private StringBuffer benefitDetails = new StringBuffer();

	// To activate/deactivate benefit
	private String hotlistFlag;
	private String unhotlistFlag;
	private String companyName;
	
	//nextfare companyid
	private String nextfareCompanyId;
	private String companyType;
	private String companyStatus;
	
	//to display tma companys
	private String tmaId;
	private String tmaName;
	private String tmaUserName;
	private String tmaEmail;
	
	private String adminName;
	
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
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
	public String getNextfareCompanyId() {
		return nextfareCompanyId;
	}
	public void setNextfareCompanyId(String nextfareCompanyId) {
		this.nextfareCompanyId = nextfareCompanyId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public StringBuffer getBenefitDetails() {
		return benefitDetails;
	}
	public void setBenefitDetails(StringBuffer benefitDetails) {
		this.benefitDetails = benefitDetails;
	}
	public String getBenefitId() {
		return benefitId;
	}
	public void setBenefitId(String benefitId) {
		this.benefitId = benefitId;
	}
	public String getBenefitName() {
		return benefitName;
	}
	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getConfirmSerialNumber() {
		return confirmSerialNumber;
	}
	public void setConfirmSerialNumber(String confirmSerialNumber) {
		this.confirmSerialNumber = confirmSerialNumber;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerMemberId() {
		return customerMemberId;
	}
	public void setCustomerMemberId(String customerMemberId) {
		this.customerMemberId = customerMemberId;
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
	public String getHotlistFlag() {
		return hotlistFlag;
	}
	public void setHotlistFlag(String hotlistFlag) {
		this.hotlistFlag = hotlistFlag;
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
	public String getNewSerialNumber() {
		return newSerialNumber;
	}
	public void setNewSerialNumber(String newSerialNumber) {
		this.newSerialNumber = newSerialNumber;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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
	public String getUnhotlistFlag() {
		return unhotlistFlag;
	}
	public void setUnhotlistFlag(String unhotlistFlag) {
		this.unhotlistFlag = unhotlistFlag;
	}
}
