package com.marta.admin.dto;

public class PartnerUpcomingMonthlyReportDTO implements java.io.Serializable{
	
	private String reportType;
    private String reportFormat;
    private String cardSerialNumber;
    private String memberName;
    private String memberId;
    private String isBenefitActivated;
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
}
