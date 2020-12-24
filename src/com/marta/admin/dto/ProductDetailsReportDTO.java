package com.marta.admin.dto;



public class ProductDetailsReportDTO implements  Comparable<ProductDetailsReportDTO>{

	private String breezecard;
	private String firstname;
	private String lastname;
	private String benefitname;
	private String companyname;
	private String memberid;
	private String region;
	private int count;
	private String transactiontype;
	
	
	public String getTransactiontype() {
		return transactiontype;
	}
	
	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
	public String getBreezecard() {
		return breezecard;
	}
	public void setBreezecard(String breezecard) {
		this.breezecard = breezecard;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getBenefitname() {
		return benefitname;
	}
	public void setBenefitname(String benefitname) {
		this.benefitname = benefitname;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	} 
	
	@Override
	public int compareTo(ProductDetailsReportDTO o) {
		// TODO Auto-generated method stub
		return companyname.compareTo(o.companyname);
	}
	
	
}
