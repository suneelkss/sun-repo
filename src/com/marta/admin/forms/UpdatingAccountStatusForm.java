package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class UpdatingAccountStatusForm extends ValidatorActionForm{
	
	
	private String typeOfSearch;
	private String patronOrCompanyName;
	private String patronOrCompanyId;
	private String accountAdminEmailAddress;
	private String accountStatus;
	private String note;
	
	
	public String getAccountAdminEmailAddress() {
		return accountAdminEmailAddress;
	}
	public void setAccountAdminEmailAddress(String accountAdminEmailAddress) {
		this.accountAdminEmailAddress = accountAdminEmailAddress;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPatronOrCompanyId() {
		return patronOrCompanyId;
	}
	public void setPatronOrCompanyId(String patronOrCompanyId) {
		this.patronOrCompanyId = patronOrCompanyId;
	}
	public String getPatronOrCompanyName() {
		return patronOrCompanyName;
	}
	public void setPatronOrCompanyName(String patronOrCompanyName) {
		this.patronOrCompanyName = patronOrCompanyName;
	}
	public String getTypeOfSearch() {
		return typeOfSearch;
	}
	public void setTypeOfSearch(String typeOfSearch) {
		this.typeOfSearch = typeOfSearch;
	}

}
