package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class DisplayPatronForm extends ValidatorActionForm {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String firstName;
private String lastName;
private String patronId;
private String activeStatus;
private String emailId;
private String phoneNum;






public String getPhoneNum() {
	return phoneNum;
}
public void setPhoneNum(String phoneNum) {
	this.phoneNum = phoneNum;
}
public static long getSerialversionuid() {
	return serialVersionUID;
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
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getPatronId() {
	return patronId;
}
public void setPatronId(String patronId) {
	this.patronId = patronId;
}
public String getActiveStatus() {
	return activeStatus;
}
public void setActiveStatus(String activeStatus) {
	this.activeStatus = activeStatus;
}




}
