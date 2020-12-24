package com.marta.admin.dto;

import java.io.Serializable;

public class CustomerBenefitDetailsDTO implements Serializable {
	
	private String customerId;
	private String companyName;
	private String benefitId;
	private String benefitName;
	private String fare_instrument_id;
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
	public String getFare_instrument_id() {
		return fare_instrument_id;
	}
	public void setFare_instrument_id(String fare_instrument_id) {
		this.fare_instrument_id = fare_instrument_id;
	}
}
