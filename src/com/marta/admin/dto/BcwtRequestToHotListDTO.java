package com.marta.admin.dto;

import java.io.Serializable;
import java.util.Date;

public class BcwtRequestToHotListDTO implements Serializable{

	private String lastName;
	private String firstName;
	private String hotlistActionType;
	private String cardQuickSearch;
	private String notes;
	private String cardOrderType;
	private String companyId;
	private String companyName;
	private Long memberId;
	private String customerMemberId;
	private String serialNumber;
	private String partnerId;
	private String hotListedDate;
	private String pointOfSale;
	private String adminName;
	private boolean validatePointOfSale;
	private String cardType;
	
	
	
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getCardOrderType() {
		return cardOrderType;
	}
	public void setCardOrderType(String cardOrderType) {
		this.cardOrderType = cardOrderType;
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
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
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
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCardQuickSearch() {
		return cardQuickSearch;
	}
	public void setCardQuickSearch(String cardQuickSearch) {
		this.cardQuickSearch = cardQuickSearch;
	}
	public String getHotlistActionType() {
		return hotlistActionType;
	}
	public void setHotlistActionType(String hotlistActionType) {
		this.hotlistActionType = hotlistActionType;
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
	/**
	 * @return the hotListedDate
	 */
	public String getHotListedDate() {
		return hotListedDate;
	}
	/**
	 * @param hotListedDate the hotListedDate to set
	 */
	public void setHotListedDate(String hotListedDate) {
		this.hotListedDate = hotListedDate;
	}
	public boolean isValidatePointOfSale() {
		return validatePointOfSale;
	}
	public void setValidatePointOfSale(boolean validatePointOfSale) {
		this.validatePointOfSale = validatePointOfSale;
	}
	
	
}
