package com.marta.admin.dto;

import java.io.Serializable;

public class BcwtNewCardBenefitDTO implements Serializable{
	
	private String employeeName;
	private String billingMonthYear;
	private String breezecardSerialNo;
	private String benefitName;
	private String effectiveDate;
	private String employeeId;
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
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

}
