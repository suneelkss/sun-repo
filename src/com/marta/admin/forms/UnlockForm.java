package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class UnlockForm extends ValidatorActionForm {
	
	private String adminFname;
	private String adminLname;
	private String adminPhoneNumber;
	private String adminEmail;
	private String resetAccount;
	private String patronIdToUnlock;
	
	private String firstNameUnlock;
	private String lastNameUnlock;
	private String mailIdUnlock;
	private String notes;
	private String unlockedPassword;
	private String lastLogin;
	
	
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getUnlockedPassword() {
		return unlockedPassword;
	}
	public void setUnlockedPassword(String unlockedPassword) {
		this.unlockedPassword = unlockedPassword;
	}
	public String getFirstNameUnlock() {
		return firstNameUnlock;
	}
	public void setFirstNameUnlock(String firstNameUnlock) {
		this.firstNameUnlock = firstNameUnlock;
	}
	public String getLastNameUnlock() {
		return lastNameUnlock;
	}
	public void setLastNameUnlock(String lastNameUnlock) {
		this.lastNameUnlock = lastNameUnlock;
	}
	public String getMailIdUnlock() {
		return mailIdUnlock;
	}
	public void setMailIdUnlock(String mailIdUnlock) {
		this.mailIdUnlock = mailIdUnlock;
	}
	public String getPatronIdToUnlock() {
		return patronIdToUnlock;
	}
	public void setPatronIdToUnlock(String patronIdToUnlock) {
		this.patronIdToUnlock = patronIdToUnlock;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public String getResetAccount() {
		return resetAccount;
	}
	public void setResetAccount(String resetAccount) {
		this.resetAccount = resetAccount;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminFname() {
		return adminFname;
	}
	public void setAdminFname(String adminFname) {
		this.adminFname = adminFname;
	}
	public String getAdminLname() {
		return adminLname;
	}
	public void setAdminLname(String adminLname) {
		this.adminLname = adminLname;
	}
	public String getAdminPhoneNumber() {
		return adminPhoneNumber;
	}
	public void setAdminPhoneNumber(String adminPhoneNumber) {
		this.adminPhoneNumber = adminPhoneNumber;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
