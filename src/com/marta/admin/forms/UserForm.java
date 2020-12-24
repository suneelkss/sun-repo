package com.marta.admin.forms;

import com.marta.admin.utils.Constants;

public class UserForm extends SignupForm{
	private String editFirstName;
	private String editLastName;
	private String editMiddleName;
	private String address1;	
	private String editEmail;
	private String editEmailReEntered;
	private String editPhoneNumber;
	private String editPatronType;
	private String userid;
	private String phoneNumberOneEdit;
	private String phoneNumberTwoEdit;
	private String phoneNumberThreeEdit;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getEditEmail() {
		return editEmail;
	}
	public void setEditEmail(String editEmail) {
		this.editEmail = editEmail;
	}
	public String getEditEmailReEntered() {
		return editEmailReEntered;
	}
	public void setEditEmailReEntered(String editEmailReEntered) {
		this.editEmailReEntered = editEmailReEntered;
	}
	public String getEditFirstName() {
		return editFirstName;
	}
	public void setEditFirstName(String editFirstName) {
		this.editFirstName = editFirstName;
	}
	public String getEditLastName() {
		return editLastName;
	}
	public void setEditLastName(String editLastName) {
		this.editLastName = editLastName;
	}
	public String getEditMiddleName() {
		return editMiddleName;
	}
	public void setEditMiddleName(String editMiddleName) {
		this.editMiddleName = editMiddleName;
	}
	public String getEditPhoneNumber() {
		return editPhoneNumber;
	}
	public void setEditPhoneNumber(String editPhoneNumber) {
		this.editPhoneNumber = editPhoneNumber;
	}	
	public String getEditPatronType() {
		return editPatronType;
	}
	public void setEditPatronType(String editPatronType) {
		this.editPatronType = editPatronType;
	}
	public String getPhoneNumberOneEdit() {
		return phoneNumberOneEdit;
	}
	public void setPhoneNumberOneEdit(String phoneNumberOneEdit) {
		this.phoneNumberOneEdit = phoneNumberOneEdit;
	}
	public String getPhoneNumberThreeEdit() {
		return phoneNumberThreeEdit;
	}
	public void setPhoneNumberThreeEdit(String phoneNumberThreeEdit) {
		this.phoneNumberThreeEdit = phoneNumberThreeEdit;
	}
	public String getPhoneNumberTwoEdit() {
		return phoneNumberTwoEdit;
	}
	public void setPhoneNumberTwoEdit(String phoneNumberTwoEdit) {
		this.phoneNumberTwoEdit = phoneNumberTwoEdit;
	}
}
