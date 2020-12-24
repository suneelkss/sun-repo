package com.marta.admin.dto;

import java.io.Serializable;

public class BcwtManageAddCompanytoTMADTO implements Serializable{
	private String companyType;
	private String companyName;
	private String nextfareCompanyId;
	
	private String email;
	private String companyStatus;
	private String tmaName;
	private String phone1;
	private String companyId;
	private String partnerUserName;
	
	
	
	
	public String getPartnerUserName() {
		return partnerUserName;
	}
	public void setPartnerUserName(String partnerUserName) {
		this.partnerUserName = partnerUserName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTmaName() {
		return tmaName;
	}
	public void setTmaName(String tmaName) {
		this.tmaName = tmaName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getNextfareCompanyId() {
		return nextfareCompanyId;
	}
	public void setNextfareCompanyId(String nextfareCompanyId) {
		this.nextfareCompanyId = nextfareCompanyId;
	}
	
}
