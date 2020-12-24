package com.marta.admin.forms;

import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

import com.marta.admin.utils.Constants;

public class SignupForm extends ValidatorActionForm{
		private String firstName;
		private String lastName;
		private String middleName;
		private String address1;
		private String address2;
		private String city;
		private String state = Constants.GEORGIA_STATE_ID;
		private String zip;
		private String email;
		private String emailReEntered;
		private String phoneNumber;
		private String secretQuestion;
		private String secretAnswer;		
		private String patronType;
		private String patronTypeName;
		private String newPassword;
		private String currentPassword;
		private String confirmPassword;
		private String addressNickName;
		private String captchaEntered;
		private String maskedAnswer;
		private String captchaSoundEntered;
		private String recaptcha_response_field;
		private boolean isSignup;
		private String parentpatronid;
		private String phoneNumberOne;
		private String phoneNumberTwo;
		private String phoneNumberThree;
		
		
		
		
		public String getParentpatronid() {
			return parentpatronid;
		}
		public void setParentpatronid(String parentpatronid) {
			this.parentpatronid = parentpatronid;
		}
		/**
		 * @return the recaptcha_response_field
		 */
		public String getRecaptcha_response_field() {
			return recaptcha_response_field;
		}
		/**
		 * @param recaptcha_response_field the recaptcha_response_field to set
		 */
		public void setRecaptcha_response_field(String recaptcha_response_field) {
			this.recaptcha_response_field = recaptcha_response_field;
		}
		public String getCaptchaSoundEntered() {
			return captchaSoundEntered;
		}
		public void setCaptchaSoundEntered(String captchaSoundEntered) {
			this.captchaSoundEntered = captchaSoundEntered;
		}
		public String getMaskedAnswer() {
			return maskedAnswer;
		}
		public void setMaskedAnswer(String maskedAnswer) {
			this.maskedAnswer = maskedAnswer;
		}
		public String getCaptchaEntered() {
			return captchaEntered;
		}
		public void setCaptchaEntered(String captchaEntered) {
			this.captchaEntered = captchaEntered;
		}
		public String getAddressNickName() {
			return addressNickName;
		}
		public void setAddressNickName(String addressNickName) {
			this.addressNickName = addressNickName;
		}
		public String getPatronType() {
			return patronType;
		}
		public void setPatronType(String patronType) {
			this.patronType = patronType;
		}
		public String getAddress1() {
			return address1;
		}
		public void setAddress1(String address1) {
			this.address1 = address1;
		}
		public String getAddress2() {
			return address2;
		}
		public void setAddress2(String address2) {
			this.address2 = address2;
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
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getSecretAnswer() {
			return secretAnswer;
		}
		public void setSecretAnswer(String secretAnswer) {
			this.secretAnswer = secretAnswer;
		}
		public String getSecretQuestion() {
			return secretQuestion;
		}
		public void setSecretQuestion(String secretQuestion) {
			this.secretQuestion = secretQuestion;
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
		public String getEmailReEntered() {
			return emailReEntered;
		}
		public void setEmailReEntered(String emailReEntered) {
			this.emailReEntered = emailReEntered;
		}		
		public String getNewPassword() {
			return newPassword;
		}
		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}
		public String getConfirmPassword() {
			return confirmPassword;
		}
		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
		public String getCurrentPassword() {
			return currentPassword;
		}
		public void setCurrentPassword(String currentPassword) {
			this.currentPassword = currentPassword;
		}
		public boolean isSignup() {
			return isSignup;
		}
		public void setSignup(boolean isSignup) {
			this.isSignup = isSignup;
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
		public String getPatronTypeName() {
			return patronTypeName;
		}
		public void setPatronTypeName(String patronTypeName) {
			this.patronTypeName = patronTypeName;
		}
	}


