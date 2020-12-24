
package com.marta.admin.dto;

import java.io.Serializable;
import java.util.Date;



public class BcwtLogDTO implements java.io.Serializable{
	private String companyName;
	private String companyId;
	private String administratorEmail ;
	private String administratorPhoneNumber ;
	private String administratorUserName ;
	private String reasonForCall ;
	private String dateOfCall ;
	private String timeStampForCall;
	private String note;
	private String fromDate;
	private String firstname;
	private String emailid;
	private String firstName;
	private String email;
	private String patronId;
	private String patronTypeId;
	private String logCallId;
	private String adminPatronId;
	private String adminPatronTypeId;
	private String userName;
	private String userType;
	private String lastName;
	
	private String savedFileName;
	private String uploadedFileName;
	
	
	
	
	public String getPatronId() {
		return patronId;
	}
	public void setPatronId(String patronId) {
		this.patronId = patronId;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getAdministratorEmail() {
		return administratorEmail;
	}
	public void setAdministratorEmail(String administratorEmail) {
		this.administratorEmail = administratorEmail;
	}
	public String getAdministratorPhoneNumber() {
		return administratorPhoneNumber;
	}
	public void setAdministratorPhoneNumber(String administratorPhoneNumber) {
		this.administratorPhoneNumber = administratorPhoneNumber;
	}
	public String getAdministratorUserName() {
		return administratorUserName;
	}
	public void setAdministratorUserName(String administratorUserName) {
		this.administratorUserName = administratorUserName;
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
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getReasonForCall() {
		return reasonForCall;
	}
	public void setReasonForCall(String reasonForCall) {
		this.reasonForCall = reasonForCall;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPatronTypeId() {
		return patronTypeId;
	}
	public void setPatronTypeId(String patronTypeId) {
		this.patronTypeId = patronTypeId;
	}
	public String getLogCallId() {
		return logCallId;
	}
	public void setLogCallId(String logCallId) {
		this.logCallId = logCallId;
	}
	/**
	 * @return the dateOfCall
	 */
	public String getDateOfCall() {
		return dateOfCall;
	}
	/**
	 * @param dateOfCall the dateOfCall to set
	 */
	public void setDateOfCall(String dateOfCall) {
		this.dateOfCall = dateOfCall;
	}
	/**
	 * @return the timeStampForCall
	 */
	public String getTimeStampForCall() {
		return timeStampForCall;
	}
	/**
	 * @param timeStampForCall the timeStampForCall to set
	 */
	public void setTimeStampForCall(String timeStampForCall) {
		this.timeStampForCall = timeStampForCall;
	}
	public String getAdminPatronId() {
		return adminPatronId;
	}
	public void setAdminPatronId(String adminPatronId) {
		this.adminPatronId = adminPatronId;
	}
	public String getAdminPatronTypeId() {
		return adminPatronTypeId;
	}
	public void setAdminPatronTypeId(String adminPatronTypeId) {
		this.adminPatronTypeId = adminPatronTypeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return the savedFileName
	 */
	public String getSavedFileName() {
		return savedFileName;
	}
	/**
	 * @param savedFileName the savedFileName to set
	 */
	public void setSavedFileName(String savedFileName) {
		this.savedFileName = savedFileName;
	}
	/**
	 * @return the uploadedFileName
	 */
	public String getUploadedFileName() {
		return uploadedFileName;
	}
	/**
	 * @param uploadedFileName the uploadedFileName to set
	 */
	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}

