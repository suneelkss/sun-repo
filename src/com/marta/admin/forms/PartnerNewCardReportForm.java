package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorForm;

public class PartnerNewCardReportForm extends ValidatorForm{
	private String tmaName;
	private String partnerName;
	private String html;
	private String pdf;
	private String excel;
	private String month;
	private String year;
	private String reportFormat;
	private String monthlyUsage;
	private String benifitName;
	private String breezeCardSerialNumber;
	private String billingMonth;
	private String billingYear;
	private String billingMY;
	private String effectiveDate;
	private String cardMonth;
    private String cardYear;
    private String cardToMonth;
    private String cardToYear;
    private String monthYear; 
    private String monthYearTo;
    private String isBlock="0";
    private String companyId;
    private String partnerFirstName;
    private String partnerLastName; 
	public String getPartnerFirstName() {
		return partnerFirstName;
	}
	public void setPartnerFirstName(String partnerFirstName) {
		this.partnerFirstName = partnerFirstName;
	}
	public String getPartnerLastName() {
		return partnerLastName;
	}
	public void setPartnerLastName(String partnerLastName) {
		this.partnerLastName = partnerLastName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getBenifitName() {
		return benifitName;
	}
	public void setBenifitName(String benifitName) {
		this.benifitName = benifitName;
	}
	public String getBillingMonth() {
		return billingMonth;
	}
	public void setBillingMonth(String billingMonth) {
		this.billingMonth = billingMonth;
	}
	public String getBillingMY() {
		return billingMY;
	}
	public void setBillingMY(String billingMY) {
		this.billingMY = billingMY;
	}
	public String getBillingYear() {
		return billingYear;
	}
	public void setBillingYear(String billingYear) {
		this.billingYear = billingYear;
	}
	public String getBreezeCardSerialNumber() {
		return breezeCardSerialNumber;
	}
	public void setBreezeCardSerialNumber(String breezeCardSerialNumber) {
		this.breezeCardSerialNumber = breezeCardSerialNumber;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getExcel() {
		return excel;
	}
	public void setExcel(String excel) {
		this.excel = excel;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonthlyUsage() {
		return monthlyUsage;
	}
	public void setMonthlyUsage(String monthlyUsage) {
		this.monthlyUsage = monthlyUsage;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPdf() {
		return pdf;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	public String getReportFormat() {
		return reportFormat;
	}
	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}
	public String getTmaName() {
		return tmaName;
	}
	public void setTmaName(String tmaName) {
		this.tmaName = tmaName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCardMonth() {
		return cardMonth;
	}
	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
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
	public String getCardYear() {
		return cardYear;
	}
	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}
	public String getIsBlock() {
		return isBlock;
	}
	public void setIsBlock(String isBlock) {
		this.isBlock = isBlock;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getMonthYearTo() {
		return monthYearTo;
	}
	public void setMonthYearTo(String monthYearTo) {
		this.monthYearTo = monthYearTo;
	}
}
