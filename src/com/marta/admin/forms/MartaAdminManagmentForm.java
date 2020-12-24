package com.marta.admin.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class MartaAdminManagmentForm extends ValidatorActionForm {
	
	private String adminFname;
	private String adminLname;
	private String adminPhoneNumber;
	private String adminEmail;
	private String firstName;
	private String middleName;
	private String lastName;	
	private String email;
	private String phoneNumber;
	private String phoneNumberOne;
	private String phoneNumberTwo;
	private String phoneNumberThree;
	private String dateOfBirth;
	private String roleName;
	private String searchEmail;
	private String searchFirstName;
	private String searchLastName;
	private String searchMiddleName;
	
	
	private String editFirstName;
	private String editMiddleName;
	private String editLastName;	
	private String editEmail;
	private String editPhoneNumber;
	private String editPhoneNumberOne;
	private String editPhoneNumberTwo;
	private String editPhoneNumberThree;
	private String editDateOfBirth;
	private String editRoleName;
	private String  userId;
	
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		
		this.firstName = "";
		this.lastName = "";
		this.middleName = "";
		this.email = "";
		this.phoneNumber = "";		
	}
	/**
	 * @return the adminEmail
	 */
	public String getAdminEmail() {
		return adminEmail;
	}
	/**
	 * @param adminEmail the adminEmail to set
	 */
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	/**
	 * @return the adminFname
	 */
	public String getAdminFname() {
		return adminFname;
	}
	/**
	 * @param adminFname the adminFname to set
	 */
	public void setAdminFname(String adminFname) {
		this.adminFname = adminFname;
	}
	/**
	 * @return the adminLname
	 */
	public String getAdminLname() {
		return adminLname;
	}
	/**
	 * @param adminLname the adminLname to set
	 */
	public void setAdminLname(String adminLname) {
		this.adminLname = adminLname;
	}
	/**
	 * @return the adminPhoneNumber
	 */
	public String getAdminPhoneNumber() {
		return adminPhoneNumber;
	}
	/**
	 * @param adminPhoneNumber the adminPhoneNumber to set
	 */
	public void setAdminPhoneNumber(String adminPhoneNumber) {
		this.adminPhoneNumber = adminPhoneNumber;
	}
	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the editDateOfBirth
	 */
	public String getEditDateOfBirth() {
		return editDateOfBirth;
	}
	/**
	 * @param editDateOfBirth the editDateOfBirth to set
	 */
	public void setEditDateOfBirth(String editDateOfBirth) {
		this.editDateOfBirth = editDateOfBirth;
	}
	/**
	 * @return the editEmail
	 */
	public String getEditEmail() {
		return editEmail;
	}
	/**
	 * @param editEmail the editEmail to set
	 */
	public void setEditEmail(String editEmail) {
		this.editEmail = editEmail;
	}
	/**
	 * @return the editFirstName
	 */
	public String getEditFirstName() {
		return editFirstName;
	}
	/**
	 * @param editFirstName the editFirstName to set
	 */
	public void setEditFirstName(String editFirstName) {
		this.editFirstName = editFirstName;
	}
	/**
	 * @return the editLastName
	 */
	public String getEditLastName() {
		return editLastName;
	}
	/**
	 * @param editLastName the editLastName to set
	 */
	public void setEditLastName(String editLastName) {
		this.editLastName = editLastName;
	}
	/**
	 * @return the editMiddleName
	 */
	public String getEditMiddleName() {
		return editMiddleName;
	}
	/**
	 * @param editMiddleName the editMiddleName to set
	 */
	public void setEditMiddleName(String editMiddleName) {
		this.editMiddleName = editMiddleName;
	}
	/**
	 * @return the editPhoneNumber
	 */
	public String getEditPhoneNumber() {
		return editPhoneNumber;
	}
	/**
	 * @param editPhoneNumber the editPhoneNumber to set
	 */
	public void setEditPhoneNumber(String editPhoneNumber) {
		this.editPhoneNumber = editPhoneNumber;
	}
	/**
	 * @return the editRoleName
	 */
	public String getEditRoleName() {
		return editRoleName;
	}
	/**
	 * @param editRoleName the editRoleName to set
	 */
	public void setEditRoleName(String editRoleName) {
		this.editRoleName = editRoleName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the searchEmail
	 */
	public String getSearchEmail() {
		return searchEmail;
	}
	/**
	 * @param searchEmail the searchEmail to set
	 */
	public void setSearchEmail(String searchEmail) {
		this.searchEmail = searchEmail;
	}
	/**
	 * @return the searchFirstName
	 */
	public String getSearchFirstName() {
		return searchFirstName;
	}
	/**
	 * @param searchFirstName the searchFirstName to set
	 */
	public void setSearchFirstName(String searchFirstName) {
		this.searchFirstName = searchFirstName;
	}
	/**
	 * @return the searchLastName
	 */
	public String getSearchLastName() {
		return searchLastName;
	}
	/**
	 * @param searchLastName the searchLastName to set
	 */
	public void setSearchLastName(String searchLastName) {
		this.searchLastName = searchLastName;
	}
	/**
	 * @return the searchMiddleName
	 */
	public String getSearchMiddleName() {
		return searchMiddleName;
	}
	/**
	 * @param searchMiddleName the searchMiddleName to set
	 */
	public void setSearchMiddleName(String searchMiddleName) {
		this.searchMiddleName = searchMiddleName;
	}
	public String getPhoneNumberOne() {
		return phoneNumberOne;
	}
	public void setPhoneNumberOne(String phoneNumberOne) {
		this.phoneNumberOne = phoneNumberOne;
	}
	public String getPhoneNumberThree() {
		return phoneNumberThree;
	}
	public void setPhoneNumberThree(String phoneNumberThree) {
		this.phoneNumberThree = phoneNumberThree;
	}
	public String getPhoneNumberTwo() {
		return phoneNumberTwo;
	}
	public void setPhoneNumberTwo(String phoneNumberTwo) {
		this.phoneNumberTwo = phoneNumberTwo;
	}
	public String getEditPhoneNumberOne() {
		return editPhoneNumberOne;
	}
	public void setEditPhoneNumberOne(String editPhoneNumberOne) {
		this.editPhoneNumberOne = editPhoneNumberOne;
	}
	public String getEditPhoneNumberThree() {
		return editPhoneNumberThree;
	}
	public void setEditPhoneNumberThree(String editPhoneNumberThree) {
		this.editPhoneNumberThree = editPhoneNumberThree;
	}
	public String getEditPhoneNumberTwo() {
		return editPhoneNumberTwo;
	}
	public void setEditPhoneNumberTwo(String editPhoneNumberTwo) {
		this.editPhoneNumberTwo = editPhoneNumberTwo;
	}
}
