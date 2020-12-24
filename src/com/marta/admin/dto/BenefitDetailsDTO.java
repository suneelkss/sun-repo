package com.marta.admin.dto;

import java.io.Serializable;

public class BenefitDetailsDTO implements Serializable {

	private String customerId;
	private String companyName;
	private String memberId;
	private String benefitId;
	private String benefitName;
	private String benefitStatus;
	private String fareIntrId;
	
	
	
	public String getFareIntrId() {
		return fareIntrId;
	}

	public void setFareIntrId(String fareIntrId) {
		this.fareIntrId = fareIntrId;
	}

	//To show benefit status on mouse over in datagrid
	private String benefitDetails;

	public String getBenefitDetails() {
		return benefitDetails;
	}

	public void setBenefitDetails(String benefitDetails) {
		this.benefitDetails = benefitDetails;
	}

	public String getBenefitId() {
		return benefitId;
	}

	public void setBenefitId(String benefitId) {
		this.benefitId = benefitId;
	}

	public String getBenefitName() {
		return benefitName;
	}

	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}

	public String getBenefitStatus() {
		return benefitStatus;
	}

	public void setBenefitStatus(String benefitStatus) {
		this.benefitStatus = benefitStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
