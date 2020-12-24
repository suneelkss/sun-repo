package com.marta.admin.dto;

import com.marta.admin.dto.DisplayProductSummaryDTO;

public class DisplayProductSummaryDTO implements Comparable<DisplayProductSummaryDTO>{
	
	private String companyName;
	private String benefitName;
	private int count;
	private String region;
	

	
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBenefitName() {
		return benefitName;
	}
	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public int compareTo(DisplayProductSummaryDTO o) {
		// TODO Auto-generated method stub
		return companyName.compareTo(o.getCompanyName());
	}
	
	
	

}
