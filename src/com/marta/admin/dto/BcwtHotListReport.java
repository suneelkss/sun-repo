package com.marta.admin.dto;

import javax.enterprise.deploy.model.J2eeApplicationObject;

public class BcwtHotListReport implements java.io.Serializable{
	private String companyName; 
	private String partnerAdminName;
	private String employeeName;
	private String hotlistActionType;
	private String hotListDate;
	private String cardSerialNumber;
	private String firstName;
	private String lastName;
	private String pointOfSale;
	private String adminName;
	private String partnerId;
	private String tmaName;
	
	
	
	public String getTmaName() {
		return tmaName;
	}
	public void setTmaName(String tmaName) {
		this.tmaName = tmaName;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
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
	public String getPointOfSale() {
		return pointOfSale;
	}
	public void setPointOfSale(String pointOfSale) {
		this.pointOfSale = pointOfSale;
	}
	public String getCardSerialNumber() {
		return cardSerialNumber;
	}
	public void setCardSerialNumber(String cardSerialNumber) {
		this.cardSerialNumber = cardSerialNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getHotlistActionType() {
		return hotlistActionType;
	}
	public void setHotlistActionType(String hotlistActionType) {
		this.hotlistActionType = hotlistActionType;
	}
	public String getHotListDate() {
		return hotListDate;
	}
	public void setHotListDate(String hotListDate) {
		this.hotListDate = hotListDate;
	}
	public String getPartnerAdminName() {
		return partnerAdminName;
	}
	public void setPartnerAdminName(String partnerAdminName) {
		this.partnerAdminName = partnerAdminName;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

}
