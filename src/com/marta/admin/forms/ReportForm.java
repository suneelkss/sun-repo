package com.marta.admin.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

public class ReportForm extends ValidatorActionForm {
	
	private int yesCount;
	private int noCount;
	private String reportType;
	private String html;
	private String pdf;
	private String excel;
	private String companyName;
	private String subCompanyName;
	private String partnerAdminName;
	private String employeeName;
	private String employeeId;
	private String billingMY;
	private String billingRangeDate;
	private String benifitName;
	private String effectiveDate;
	private String hotListDate;
	private String hotlistActionType;
	private String breezeCardSerialNumber;
	private String firstName;
	private String lastName;
	private String pointOfSale;
	private String cardMonth;
    private String cardYear;
    private String cardToMonth;
    private String cardToYear;
    private String monthYear; 
    private String monthYearTo;
    private String tmaName;
    private String partnerCompanyName;
    private String isBlock;
    private String companyId;
    private String fromDate;
    private String toDate;
    private String tmaId;
    
    private String adminName;
    private String partnerName;
    private String cardSerialNo;
	private String benefitType;
	
	private String customerMemberId;
    private String cardHolderName;
    private String month;
	private String year;
    private int countMonth;
    private int countYear;
    private String partnerFirstName;
    private String partnerLastName;
  
    //for partnerupcoming monthly report
    private List upCommingReportList = new ArrayList();
    private List upCommingReportActList = new ArrayList();
    private List upCommingReportDeactList = new ArrayList();
    private List upCommingNewCardList = new ArrayList();
	
	//to add report format
	private String reportFormat;
	
	//to get billing month and year seperately
	private String billingMonth;
	private String billingYear;
	private String startBillingMonth;
	private String startBillingYear;
	
	private String endBillingMonth;
	private String endBillingYear;
	private String monthlyUsage;
	
	private String billingStartDate;
	private String billingEndDate;
	
	/***************** OF - Starts here **********************/
	
	//private String reportType;
    private String orderFromDate;
    private String orderTodate;
    //private String reportFormat;
    private String orderRequestId;
    private String cardSerialNumber;
    private String qaFirstName;
    private String qaLastName;
    private String email;
    private String phoneNumber;
    private String orderDate;
    private String orderStatus;
    private String batchId;
    
    private String anyOneFieldMandatory;
    
    private String orderType; // IS, GBS & PS    
    private String zip;
    
    private String region;
    private String monthlyusageAll;

	/***************** OF - Ends here **********************/

	/**
	 * @return the orderType
	 */
	
    
    
    
    public String getOrderType() {
		return orderType;
	}

	public String getMonthlyusageAll() {
		return monthlyusageAll;
	}

	public void setMonthlyusageAll(String monthlyusageAll) {
		this.monthlyusageAll = monthlyusageAll;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTmaId() {
		return tmaId;
	}

	public void setTmaId(String tmaId) {
		this.tmaId = tmaId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public List getUpCommingNewCardList() {
		return upCommingNewCardList;
	}

	public void setUpCommingNewCardList(List upCommingNewCardList) {
		this.upCommingNewCardList = upCommingNewCardList;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getBillingEndDate() {
		return billingEndDate;
	}

	public void setBillingEndDate(String billingEndDate) {
		this.billingEndDate = billingEndDate;
	}

	public String getBillingStartDate() {
		return billingStartDate;
	}

	public void setBillingStartDate(String billingStartDate) {
		this.billingStartDate = billingStartDate;
	}

	public String getEndBillingMonth() {
		return endBillingMonth;
	}

	public void setEndBillingMonth(String endBillingMonth) {
		this.endBillingMonth = endBillingMonth;
	}

	public String getEndBillingYear() {
		return endBillingYear;
	}

	public void setEndBillingYear(String endBillingYear) {
		this.endBillingYear = endBillingYear;
	}

	public String getStartBillingMonth() {
		return startBillingMonth;
	}

	public void setStartBillingMonth(String startBillingMonth) {
		this.startBillingMonth = startBillingMonth;
	}

	public String getStartBillingYear() {
		return startBillingYear;
	}

	public void setStartBillingYear(String startBillingYear) {
		this.startBillingYear = startBillingYear;
	}

	public String getMonthlyUsage() {
		return monthlyUsage;
	}

	public void setMonthlyUsage(String monthlyUsage) {
		this.monthlyUsage = monthlyUsage;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public String getBillingRangeDate() {
		return billingRangeDate;
	}

	public void setBillingRangeDate(String billingRangeDate) {
		this.billingRangeDate = billingRangeDate;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getBillingMY() {
		return billingMY;
	}

	public void setBillingMY(String billingMY) {
		this.billingMY = billingMY;
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

	public String getPartnerAdminName() {
		return partnerAdminName;
	}

	public void setPartnerAdminName(String partnerAdminName) {
		this.partnerAdminName = partnerAdminName;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getSubCompanyName() {
		return subCompanyName;
	}

	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}

	public String getBenifitName() {
		return benifitName;
	}

	public void setBenifitName(String benifitName) {
		this.benifitName = benifitName;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getHotlistActionType() {
		return hotlistActionType;
	}

	public void setHotlistActionType(String hotlistActionType) {
		this.hotlistActionType = hotlistActionType;
	}

	public String getHotListDate() {
		return hotListDate;
	}

	public void setHotListDate(String hotListDate) {
		this.hotListDate = hotListDate;
	}

	public String getBreezeCardSerialNumber() {
		return breezeCardSerialNumber;
	}

	public void setBreezeCardSerialNumber(String breezeCardSerialNumber) {
		this.breezeCardSerialNumber = breezeCardSerialNumber;
	}

	public String getBillingMonth() {
		return billingMonth;
	}

	public void setBillingMonth(String billingMonth) {
		this.billingMonth = billingMonth;
	}

	public String getBillingYear() {
		return billingYear;
	}

	public void setBillingYear(String billingYear) {
		this.billingYear = billingYear;
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

	public String getPointOfSale() {
		return pointOfSale;
	}

	public void setPointOfSale(String pointOfSale) {
		this.pointOfSale = pointOfSale;
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

	public String getPartnerCompanyName() {
		return partnerCompanyName;
	}

	public void setPartnerCompanyName(String partnerCompanyName) {
		this.partnerCompanyName = partnerCompanyName;
	}

	public String getTmaName() {
		return tmaName;
	}

	public void setTmaName(String tmaName) {
		this.tmaName = tmaName;
	}

	/**
	 * @return the isBlock
	 */
	public String getIsBlock() {
		return isBlock;
	}

	/**
	 * @param isBlock the isBlock to set
	 */
	public void setIsBlock(String isBlock) {
		this.isBlock = isBlock;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardSerialNo() {
		return cardSerialNo;
	}

	public void setCardSerialNo(String cardSerialNo) {
		this.cardSerialNo = cardSerialNo;
	}

	public int getCountMonth() {
		return countMonth;
	}

	public void setCountMonth(int countMonth) {
		this.countMonth = countMonth;
	}

	public int getCountYear() {
		return countYear;
	}

	public void setCountYear(int countYear) {
		this.countYear = countYear;
	}

	public String getCustomerMemberId() {
		return customerMemberId;
	}

	public void setCustomerMemberId(String customerMemberId) {
		this.customerMemberId = customerMemberId;
	}

	public List getUpCommingReportActList() {
		return upCommingReportActList;
	}

	public void setUpCommingReportActList(List upCommingReportActList) {
		this.upCommingReportActList = upCommingReportActList;
	}

	public List getUpCommingReportDeactList() {
		return upCommingReportDeactList;
	}

	public void setUpCommingReportDeactList(List upCommingReportDeactList) {
		this.upCommingReportDeactList = upCommingReportDeactList;
	}

	public List getUpCommingReportList() {
		return upCommingReportList;
	}

	public void setUpCommingReportList(List upCommingReportList) {
		this.upCommingReportList = upCommingReportList;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the anyOneFieldMandatory
	 */
	public String getAnyOneFieldMandatory() {
		return anyOneFieldMandatory;
	}

	/**
	 * @param anyOneFieldMandatory the anyOneFieldMandatory to set
	 */
	public void setAnyOneFieldMandatory(String anyOneFieldMandatory) {
		this.anyOneFieldMandatory = anyOneFieldMandatory;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the cardSerialNumber
	 */
	public String getCardSerialNumber() {
		return cardSerialNumber;
	}

	/**
	 * @param cardSerialNumber the cardSerialNumber to set
	 */
	public void setCardSerialNumber(String cardSerialNumber) {
		this.cardSerialNumber = cardSerialNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the orderDate
	 */
	public String getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the orderFromDate
	 */
	public String getOrderFromDate() {
		return orderFromDate;
	}

	/**
	 * @param orderFromDate the orderFromDate to set
	 */
	public void setOrderFromDate(String orderFromDate) {
		this.orderFromDate = orderFromDate;
	}
	/**
	 * @return the orderRequestId
	 */
	public String getOrderRequestId() {
		return orderRequestId;
	}

	/**
	 * @param orderRequestId the orderRequestId to set
	 */
	public void setOrderRequestId(String orderRequestId) {
		this.orderRequestId = orderRequestId;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the orderTodate
	 */
	public String getOrderTodate() {
		return orderTodate;
	}

	/**
	 * @param orderTodate the orderTodate to set
	 */
	public void setOrderTodate(String orderTodate) {
		this.orderTodate = orderTodate;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the qaFirstName
	 */
	public String getQaFirstName() {
		return qaFirstName;
	}

	/**
	 * @param qaFirstName the qaFirstName to set
	 */
	public void setQaFirstName(String qaFirstName) {
		this.qaFirstName = qaFirstName;
	}

	/**
	 * @return the qaLastName
	 */
	public String getQaLastName() {
		return qaLastName;
	}

	/**
	 * @param qaLastName the qaLastName to set
	 */
	public void setQaLastName(String qaLastName) {
		this.qaLastName = qaLastName;
	}

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

	public int getYesCount() {
		return yesCount;
	}

	public void setYesCount(int yesCount) {
		this.yesCount = yesCount;
	}

	public int getNoCount() {
		return noCount;
	}

	public void setNoCount(int noCount) {
		this.noCount = noCount;
	}
	
	

}
