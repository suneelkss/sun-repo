package com.marta.admin.dto;

import java.io.Serializable;

import com.marta.admin.hibernate.BcwtPatron;

public class BatchProcessPartnerListDTO implements Serializable{
	
	private String partnerId;
	private String firstName;
	private String lastName;
	private String companyName;
	private String companyId;
	private BcwtPatron adminId;
	
	public BcwtPatron getAdminId() {
		return adminId;
	}
	public void setAdminId(BcwtPatron adminId) {
		this.adminId = adminId;
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
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

}
