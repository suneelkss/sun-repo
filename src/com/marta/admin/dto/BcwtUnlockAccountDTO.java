package com.marta.admin.dto;

public class BcwtUnlockAccountDTO  implements java.io.Serializable {
	
	private String adminFname;
    private String adminLname;
    private String adminPhoneNumber;
    private String adminEmail;
    private String patronId;
    private String securityQuestion;
    private String securityAnswer;
    private String dateOfBirth;
    private String securityId;
    private String notes;
    private String unlockPassword;
    private String lastLogin;
    
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getAdminEmail() {
		return adminEmail;
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
	public String getPatronId() {
		return patronId;
	}
	public void setPatronId(String patronId) {
		this.patronId = patronId;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getUnlockPassword() {
		return unlockPassword;
	}
	public void setUnlockPassword(String unlockPassword) {
		this.unlockPassword = unlockPassword;
	}
	
}
