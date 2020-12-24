package com.marta.admin.dto;

import java.io.Serializable;

public class BcwtPartnerAdminInfoDTO implements Serializable {
	
	private String userName;
	private String password;
	private String partnerId;
	private String passwordStatus;
	private String wrongAttempts;
	private String tempPassword;
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordStatus() {
		return passwordStatus;
	}

	public void setPasswordStatus(String passwordStatus) {
		this.passwordStatus = passwordStatus;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public String getWrongAttempts() {
		return wrongAttempts;
	}

	public void setWrongAttempts(String wrongAttempts) {
		this.wrongAttempts = wrongAttempts;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
