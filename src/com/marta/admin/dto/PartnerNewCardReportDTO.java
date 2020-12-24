package com.marta.admin.dto;

public class PartnerNewCardReportDTO implements java.io.Serializable {
	private String reportType;
    private String newCardFromDate;
    private String newCardTodate;
    private String reportFormat;
    private String cardSerialNumber;
    private String employeeName;
    private String employeeId;
    private String benefitName;
    private String partnerAdminName;
    private String companyName;
    private String cardMonth;
    private String effectiveDate;
    private String tmaName;
    private String partnerName;
    private String companyId;

    private Long partnerId;
    
    private String cardYear;
    
    private String monthYear; 
    private String anyOneFieldMandatory;
    private String cardToMonth;
    private String cardToYear;
    
	public String getAnyOneFieldMandatory() {
		return anyOneFieldMandatory;
	}
	public void setAnyOneFieldMandatory(String anyOneFieldMandatory) {
		this.anyOneFieldMandatory = anyOneFieldMandatory;
	}
	public String getBenefitName() {
		return benefitName;
	}
	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}
	public String getCardMonth() {
		return cardMonth;
	}
	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
	}
	public String getCardSerialNumber() {
		return cardSerialNumber;
	}
	public void setCardSerialNumber(String cardSerialNumber) {
		this.cardSerialNumber = cardSerialNumber;
	}
	public String getCardYear() {
		return cardYear;
	}
	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getNewCardFromDate() {
		return newCardFromDate;
	}
	public void setNewCardFromDate(String newCardFromDate) {
		this.newCardFromDate = newCardFromDate;
	}
	public String getNewCardTodate() {
		return newCardTodate;
	}
	public void setNewCardTodate(String newCardTodate) {
		this.newCardTodate = newCardTodate;
	}
	public String getPartnerAdminName() {
		return partnerAdminName;
	}
	public void setPartnerAdminName(String partnerAdminName) {
		this.partnerAdminName = partnerAdminName;
	}
	public Long getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	public String getReportFormat() {
		return reportFormat;
	}
	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getTmaName() {
		return tmaName;
	}
	public void setTmaName(String tmaName) {
		this.tmaName = tmaName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCardToMonth() {
		return cardToMonth;
	}
	public void setCardToMonth(String cardToMonth) {
		this.cardToMonth = cardToMonth;
	}
	public String getCardToYear() {
		return cardToYear;
	}
	public void setCardToYear(String cardToYear) {
		this.cardToYear = cardToYear;
	}

}
