package com.marta.admin.dto;

public class BcwtDetailedOrderReportDTO implements java.io.Serializable{

	private String companyName ;
	private String partnerAdminName;
	private String employeeName;
	private String billingMonthYear;
	private String breezecardSerialNo;
	private String benefitName;
	private String effectiveDate;
	private String employeeId; 
	
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getBenefitName() {
		return benefitName;
	}
	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}
	public String getBillingMonthYear() {
		return billingMonthYear;
	}
	public void setBillingMonthYear(String billingMonthYear) {
		this.billingMonthYear = billingMonthYear;
	}
	public String getBreezecardSerialNo() {
		return breezecardSerialNo;
	}
	public void setBreezecardSerialNo(String breezecardSerialNo) {
		this.breezecardSerialNo = breezecardSerialNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getPartnerAdminName() {
		return partnerAdminName;
	}
	public void setPartnerAdminName(String partnerAdminName) {
		this.partnerAdminName = partnerAdminName;
	}
	
}
