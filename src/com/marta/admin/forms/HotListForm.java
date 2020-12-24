package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorActionForm;
import java.util.Date;

public class HotListForm extends ValidatorActionForm {
	
	private String lastName;
	private String firstName;
	private String notes;
	private String cardOrderType;
	private String breezeCardSerialNumber;
	private String companyId;
	private String companyName;
	private String memberId;
	private String customerMemberId;
	private String partnerId;
	private Date hotListedDate;
	private String pointOfSale;
	private String adminName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCardOrderType() {
		return cardOrderType;
	}
	public void setCardOrderType(String cardOrderType) {
		this.cardOrderType = cardOrderType;
	}
	public String getBreezeCardSerialNumber() {
		return breezeCardSerialNumber;
	}
	public void setBreezeCardSerialNumber(String breezeCardSerialNumber) {
		this.breezeCardSerialNumber = breezeCardSerialNumber;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
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
	public String getCustomerMemberId() {
		return customerMemberId;
	}
	public void setCustomerMemberId(String customerMemberId) {
		this.customerMemberId = customerMemberId;
	}
	public Date getHotListedDate() {
		return hotListedDate;
	}
	public void setHotListedDate(Date hotListedDate) {
		this.hotListedDate = hotListedDate;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPointOfSale() {
		return pointOfSale;
	}
	public void setPointOfSale(String pointOfSale) {
		this.pointOfSale = pointOfSale;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
