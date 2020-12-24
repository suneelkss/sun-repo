package com.marta.admin.dto;

import java.io.Serializable;

public class MemberBenefitDetailsDTO implements Serializable {
		
	private String customerId;
	private String memberId;
	private String benefitId;
	private String serialNbr;
	private String statusId;
	private String benefitname;
	private String companyname;
	
	
	
	
	
	
	
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getBenefitname() {
		return benefitname;
	}
	public void setBenefitname(String benefitname) {
		this.benefitname = benefitname;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getSerialNbr() {
		return serialNbr;
	}
	public void setSerialNbr(String serialNbr) {
		this.serialNbr = serialNbr;
	}
	public String getBenefitId() {
		return benefitId;
	}
	public void setBenefitId(String benefitId) {
		this.benefitId = benefitId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
