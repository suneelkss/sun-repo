package com.marta.admin.dto;

import java.util.Date;

public class UpCommingMonthBenefitDTO implements java.io.Serializable{
	private String reportType;
    private String redportFormat;
    private String cardSerialNumber;
    private String memberName;
    private String memberId;
    private String isBenefitActivated;
    private String benefitName;
    private String benefitId;
    private String region;
    private String operatorid; 
    private String firstName;
    private String lastName;
    private Date dateModified;
    private String companyName;
    private String cardAction;
    
    
    
    
    
    
    
    
	public String getCardAction() {
		return cardAction;
	}
	public void setCardAction(String cardAction) {
		this.cardAction = cardAction;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getRedportFormat() {
		return redportFormat;
	}
	public void setRedportFormat(String redportFormat) {
		this.redportFormat = redportFormat;
	}
	public String getBenefitName() {
		return benefitName;
	}
	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}
	public String getBenefitId() {
		return benefitId;
	}
	public void setBenefitId(String benefitId) {
		this.benefitId = benefitId;
	}
	public String getCardSerialNumber() {
		return cardSerialNumber;
	}
	public void setCardSerialNumber(String cardSerialNumber) {
		this.cardSerialNumber = cardSerialNumber;
	}
	public String getIsBenefitActivated() {
		return isBenefitActivated;
	}
	public void setIsBenefitActivated(String isBenefitActivated) {
		this.isBenefitActivated = isBenefitActivated;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
