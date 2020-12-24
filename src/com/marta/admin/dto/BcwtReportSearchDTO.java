package com.marta.admin.dto;

public class BcwtReportSearchDTO  implements java.io.Serializable{

	private String reportType;
	private String reportFormat;
	
	private String orderFromDate;
    private String orderTodate;
      
    private String orderRequestId;
    private String cardSerialNo;
    
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    
    private String orderDate;
    private String orderStatus;
    private String batchId;
    
    private String orderType;
    private String zip;
  
    private String qaFirstName;
    private String qaLastName;

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

	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderFromDate() {
		return orderFromDate;
	}
	public void setOrderFromDate(String orderFromDate) {
		this.orderFromDate = orderFromDate;
	}
	public String getOrderTodate() {
		return orderTodate;
	}
	public void setOrderTodate(String orderTodate) {
		this.orderTodate = orderTodate;
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
	public String getCardSerialNo() {
		return cardSerialNo;
	}
	public void setCardSerialNo(String cardSerialNo) {
		this.cardSerialNo = cardSerialNo;
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
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderRequestId() {
		return orderRequestId;
	}
	public void setOrderRequestId(String orderRequestId) {
		this.orderRequestId = orderRequestId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
