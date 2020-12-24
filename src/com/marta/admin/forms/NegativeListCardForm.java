package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class NegativeListCardForm extends ValidatorActionForm {
	
	    private String patronFirstName;
		private String patronLastName;
		private String patronId;
		private String zipCode;
		private String creditCardNo;
		private String expiryMonth;
		private String expiryYear;
		private String expiryDate;
		private String negativeListStatus;
		private String patronName;
		private String paymentCardId;
		private String showPatronName;
		private String showCreditCardNo;
		private String showExpiryDate;
		
		
		
		
		public String getShowPatronName() {
			return showPatronName;
		}
		public void setShowPatronName(String showPatronName) {
			this.showPatronName = showPatronName;
		}
		public String getPaymentCardId() {
			return paymentCardId;
		}
		public void setPaymentCardId(String paymentCardId) {
			this.paymentCardId = paymentCardId;
		}
		public String getCreditCardNo() {
			return creditCardNo;
		}
		public void setCreditCardNo(String creditCardNo) {
			this.creditCardNo = creditCardNo;
		}
		public String getExpiryMonth() {
			return expiryMonth;
		}
		public void setExpiryMonth(String expiryMonth) {
			this.expiryMonth = expiryMonth;
		}
		public String getExpiryYear() {
			return expiryYear;
		}
		public void setExpiryYear(String expiryYear) {
			this.expiryYear = expiryYear;
		}
		public String getNegativeListStatus() {
			return negativeListStatus;
		}
		public void setNegativeListStatus(String negativeListStatus) {
			this.negativeListStatus = negativeListStatus;
		}
		public String getPatronFirstName() {
			return patronFirstName;
		}
		public void setPatronFirstName(String patronFirstName) {
			this.patronFirstName = patronFirstName;
		}
		public String getPatronId() {
			return patronId;
		}
		public void setPatronId(String patronId) {
			this.patronId = patronId;
		}
		public String getPatronLastName() {
			return patronLastName;
		}
		public void setPatronLastName(String patronLastName) {
			this.patronLastName = patronLastName;
		}
		public String getPatronName() {
			return patronName;
		}
		public void setPatronName(String patronName) {
			this.patronName = patronName;
		}
		public String getZipCode() {
			return zipCode;
		}
		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}
		public String getExpiryDate() {
			return expiryDate;
		}
		public void setExpiryDate(String expiryDate) {
			this.expiryDate = expiryDate;
		}
		public String getShowCreditCardNo() {
			return showCreditCardNo;
		}
		public void setShowCreditCardNo(String showCreditCardNo) {
			this.showCreditCardNo = showCreditCardNo;
		}
		public String getShowExpiryDate() {
			return showExpiryDate;
		}
		public void setShowExpiryDate(String showExpiryDate) {
			this.showExpiryDate = showExpiryDate;
		}
		

	
}