package com.marta.admin.dto;

import java.io.Serializable;

public class BcwtCompanyStatusDTO implements Serializable{

	private String companyName;
	private String partnerCompanyStatus;
	private String purchaseOrderNo;
	private String companyCurrentStatus;
	private String companyId;
	private String companyStatus;
	private String nextFareId;
	private String confirmNextFareId;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String fax;
	private String email;
	private String nextFareIdFlag;
	
	
	public String getNextFareIdFlag() {
		return nextFareIdFlag;
	}
	public void setNextFareIdFlag(String nextFareIdFlag) {
		this.nextFareIdFlag = nextFareIdFlag;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCompanyCurrentStatus() {
		return companyCurrentStatus;
	}
	public void setCompanyCurrentStatus(String companyCurrentStatus) {
		this.companyCurrentStatus = companyCurrentStatus;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}
	public String getNextFareId() {
		return nextFareId;
	}
	public void setNextFareId(String nextFareId) {
		this.nextFareId = nextFareId;
	}
	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}
	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPartnerCompanyStatus() {
		return partnerCompanyStatus;
	}
	public void setPartnerCompanyStatus(String partnerCompanyStatus) {
		this.partnerCompanyStatus = partnerCompanyStatus;
	}
	public String getConfirmNextFareId() {
		return confirmNextFareId;
	}
	public void setConfirmNextFareId(String confirmNextFareId) {
		this.confirmNextFareId = confirmNextFareId;
	}
}
