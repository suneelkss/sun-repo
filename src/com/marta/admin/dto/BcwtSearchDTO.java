/**
 * 
 */
package com.marta.admin.dto;

import java.util.Date;

/**
 * @author yuvakumar
 *
 */
public class BcwtSearchDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private Long patronid;
	private String orderStatus;
	private String orderStatusId;
	private String accountOrderedStatus;
	private String orderNumber;
	private String orderType;
	private String memberFirstName;
	private String memberLastName;
	private String companyName;
	private String companyID;
	private String partnerFirstName;
	private String partnerLastName;
	private String emailid;
	private String patronFirstName;
	private String patronMiddleName;
	private String patronLastName;
	private String cardSerialNumber;
	private String breezeCardName;
	private String activeStatus;
	private String breezecardid;
	private String breezeCardSerialNumber;
	private String adminSearchEmail;
	private String adminSearchFirstName;
	private String adminSearchMiddleName;
	private String adminSearchLastName;
	private String adminEmail;
	private String adminPhoneNumber;
	private String adminName;
	private String batchId;
	private String orderStartDate;
	private String orderEndDate;
	private String orderId;
	private String orderedDate;
	private String accountAdminEmailAddress;
	private String emailId;
	private String memberId;
	private String firstName;
	private String lastName;
	private String shippeddate;
	
	
	
	
	
	
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminPhoneNumber() {
		return adminPhoneNumber;
	}
	public void setAdminPhoneNumber(String adminPhoneNumber) {
		this.adminPhoneNumber = adminPhoneNumber;
	}
	public String getBreezeCardSerialNumber() {
		return breezeCardSerialNumber;
	}
	public void setBreezeCardSerialNumber(String breezeCardSerialNumber) {
		this.breezeCardSerialNumber = breezeCardSerialNumber;
	}
	public String getBreezecardid() {
		return breezecardid;
	}
	public void setBreezecardid(String breezecardid) {
		this.breezecardid = breezecardid;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getBreezeCardName() {
		return breezeCardName;
	}
	public void setBreezeCardName(String breezeCardName) {
		this.breezeCardName = breezeCardName;
	}
	public String getCardSerialNumber() {
		return cardSerialNumber;
	}
	public void setCardSerialNumber(String cardSerialNumber) {
		this.cardSerialNumber = cardSerialNumber;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPatronLastName() {
		return patronLastName;
	}
	public void setPatronLastName(String patronLastName) {
		this.patronLastName = patronLastName;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getMemberFirstName() {
		return memberFirstName;
	}
	public void setMemberFirstName(String memberFirstName) {
		this.memberFirstName = memberFirstName;
	}
	public String getMemberLastName() {
		return memberLastName;
	}
	public void setMemberLastName(String memberLastName) {
		this.memberLastName = memberLastName;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getPartnerFirstName() {
		return partnerFirstName;
	}
	public void setPartnerFirstName(String partnerFirstName) {
		this.partnerFirstName = partnerFirstName;
	}
	public String getPartnerLastName() {
		return partnerLastName;
	}
	public void setPartnerLastName(String partnerLastName) {
		this.partnerLastName = partnerLastName;
	}
	public String getPatronFirstName() {
		return patronFirstName;
	}
	public void setPatronFirstName(String patronFirstName) {
		this.patronFirstName = patronFirstName;
	}
	/**
	 * @return the adminSearchEmail
	 */
	public String getAdminSearchEmail() {
		return adminSearchEmail;
	}
	/**
	 * @param adminSearchEmail the adminSearchEmail to set
	 */
	public void setAdminSearchEmail(String adminSearchEmail) {
		this.adminSearchEmail = adminSearchEmail;
	}
	/**
	 * @return the adminSearchFirstName
	 */
	public String getAdminSearchFirstName() {
		return adminSearchFirstName;
	}
	/**
	 * @param adminSearchFirstName the adminSearchFirstName to set
	 */
	public void setAdminSearchFirstName(String adminSearchFirstName) {
		this.adminSearchFirstName = adminSearchFirstName;
	}
	/**
	 * @return the adminSearchLastName
	 */
	public String getAdminSearchLastName() {
		return adminSearchLastName;
	}
	/**
	 * @param adminSearchLastName the adminSearchLastName to set
	 */
	public void setAdminSearchLastName(String adminSearchLastName) {
		this.adminSearchLastName = adminSearchLastName;
	}
	/**
	 * @return the adminSearchMiddleName
	 */
	public String getAdminSearchMiddleName() {
		return adminSearchMiddleName;
	}
	/**
	 * @param adminSearchMiddleName the adminSearchMiddleName to set
	 */
	public void setAdminSearchMiddleName(String adminSearchMiddleName) {
		this.adminSearchMiddleName = adminSearchMiddleName;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getOrderEndDate() {
		return orderEndDate;
	}
	public void setOrderEndDate(String orderEndDate) {
		this.orderEndDate = orderEndDate;
	}
	public String getOrderStartDate() {
		return orderStartDate;
	}
	public void setOrderStartDate(String orderStartDate) {
		this.orderStartDate = orderStartDate;
	}
	public String getPatronMiddleName() {
		return patronMiddleName;
	}
	public void setPatronMiddleName(String patronMiddleName) {
		this.patronMiddleName = patronMiddleName;
	}
	public Long getPatronid() {
		return patronid;
	}
	public void setPatronid(Long patronid) {
		this.patronid = patronid;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getAccountOrderedStatus() {
		return accountOrderedStatus;
	}
	public void setAccountOrderedStatus(String accountOrderedStatus) {
		this.accountOrderedStatus = accountOrderedStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getAccountAdminEmailAddress() {
		return accountAdminEmailAddress;
	}
	public void setAccountAdminEmailAddress(String accountAdminEmailAddress) {
		this.accountAdminEmailAddress = accountAdminEmailAddress;
	}
	public String getOrderedDate() {
		return orderedDate;
	}
	public void setOrderedDate(String orderedDate) {
		this.orderedDate = orderedDate;
	}
	public String getShippeddate() {
		return shippeddate;
	}
	public void setShippeddate(String shippeddate) {
		this.shippeddate = shippeddate;
	}
	public String getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(String orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	
	
}

	