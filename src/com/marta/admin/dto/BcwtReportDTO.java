package com.marta.admin.dto;

public class BcwtReportDTO implements java.io.Serializable{

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
	private String billingMonth;
	private String billingYear;
	private String monthlyUsage;
	private Long companyid;
	private Long partnerid;
	private Long parentPartnerid;
	private Long roleid;
	private String lastname;
	private String firstname;
	private String username;
	private String password;
	private String companyname;
	private String cardSerialNo;
	private String benefitType;
	private String tmaName;
    private String partnerCompanyName;
    
    private String breezecardSerialNumber;
    private String benefitName;
    
    // General Ledger Report
    private String accountNumber;
    private String price;
    private String addressId;
    private double gbsSubTotal = 00.00;
    private double gbsDiscount = 00.00;
           
    /***************** OF - Starts here **********************/
    private String noOfPending;
    private String noOfOutstanding;
	private String noOfFulfilled;
	private String noOfCancelled;
	private String noOfHotlisted;
	private String noOfReturned;
	private String noOfPassed;
	private String noOfFailed;
    private String noOfDeliveryAttempts;
    private String noOfCardsSold;
	
	private String orderRequestId;
    //private String cardSerialNo;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String zip;
    private String orderDate;
    private String orderStatus;
    private String orderType;  //customerType

    private String cancelledDate;
    private String mediasalesAdmin;
    //private String reportType;
    private String reasonType;
    private String processeddate;
    private String failedtype;
    
    private String returnedDate;
    private String processedDate;
    
    private String batchId;
   
    private String orderFromDate;
    private String orderTodate;
    
    private String qaFirstName;
    private String qaLastName;
    
    private String noOfOrders;
    private String orderValue;
    private String shippingAmount;
    private String total;
    
    private String orderQty;
      
	//Revenue Report    
    private String transactionDate;
    private String creditCardType;
    private String amount;
    private String creditCardLast4;
    private String approvalCode; //Payment Authorization
    private String products;
    private String orderModule;
    private String customerName;
    
	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * @return the addressId
	 */
	public String getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the approvalCode
	 */
	public String getApprovalCode() {
		return approvalCode;
	}
	/**
	 * @param approvalCode the approvalCode to set
	 */
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
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
	 * @return the benefitName
	 */
	public String getBenefitName() {
		return benefitName;
	}
	/**
	 * @param benefitName the benefitName to set
	 */
	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}
	/**
	 * @return the benefitType
	 */
	public String getBenefitType() {
		return benefitType;
	}
	/**
	 * @param benefitType the benefitType to set
	 */
	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}
	/**
	 * @return the benifitName
	 */
	public String getBenifitName() {
		return benifitName;
	}
	/**
	 * @param benifitName the benifitName to set
	 */
	public void setBenifitName(String benifitName) {
		this.benifitName = benifitName;
	}
	/**
	 * @return the billingMonth
	 */
	public String getBillingMonth() {
		return billingMonth;
	}
	/**
	 * @param billingMonth the billingMonth to set
	 */
	public void setBillingMonth(String billingMonth) {
		this.billingMonth = billingMonth;
	}
	/**
	 * @return the billingMY
	 */
	public String getBillingMY() {
		return billingMY;
	}
	/**
	 * @param billingMY the billingMY to set
	 */
	public void setBillingMY(String billingMY) {
		this.billingMY = billingMY;
	}
	/**
	 * @return the billingRangeDate
	 */
	public String getBillingRangeDate() {
		return billingRangeDate;
	}
	/**
	 * @param billingRangeDate the billingRangeDate to set
	 */
	public void setBillingRangeDate(String billingRangeDate) {
		this.billingRangeDate = billingRangeDate;
	}
	/**
	 * @return the billingYear
	 */
	public String getBillingYear() {
		return billingYear;
	}
	/**
	 * @param billingYear the billingYear to set
	 */
	public void setBillingYear(String billingYear) {
		this.billingYear = billingYear;
	}
	/**
	 * @return the breezecardSerialNumber
	 */
	public String getBreezecardSerialNumber() {
		return breezecardSerialNumber;
	}
	/**
	 * @param breezecardSerialNumber the breezecardSerialNumber to set
	 */
	public void setBreezecardSerialNumber(String breezecardSerialNumber) {
		this.breezecardSerialNumber = breezecardSerialNumber;
	}
	/**
	 * @return the breezeCardSerialNumber
	 */
	public String getBreezeCardSerialNumber() {
		return breezeCardSerialNumber;
	}
	/**
	 * @param breezeCardSerialNumber the breezeCardSerialNumber to set
	 */
	public void setBreezeCardSerialNumber(String breezeCardSerialNumber) {
		this.breezeCardSerialNumber = breezeCardSerialNumber;
	}
	/**
	 * @return the cancelledDate
	 */
	public String getCancelledDate() {
		return cancelledDate;
	}
	/**
	 * @param cancelledDate the cancelledDate to set
	 */
	public void setCancelledDate(String cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
	/**
	 * @return the cardSerialNo
	 */
	public String getCardSerialNo() {
		return cardSerialNo;
	}
	/**
	 * @param cardSerialNo the cardSerialNo to set
	 */
	public void setCardSerialNo(String cardSerialNo) {
		this.cardSerialNo = cardSerialNo;
	}
	/**
	 * @return the companyid
	 */
	public Long getCompanyid() {
		return companyid;
	}
	/**
	 * @param companyid the companyid to set
	 */
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	/**
	 * @return the companyname
	 */
	public String getCompanyname() {
		return companyname;
	}
	/**
	 * @param companyname the companyname to set
	 */
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the creditCardLast4
	 */
	public String getCreditCardLast4() {
		return creditCardLast4;
	}
	/**
	 * @param creditCardLast4 the creditCardLast4 to set
	 */
	public void setCreditCardLast4(String creditCardLast4) {
		this.creditCardLast4 = creditCardLast4;
	}
	/**
	 * @return the creditCardType
	 */
	public String getCreditCardType() {
		return creditCardType;
	}
	/**
	 * @param creditCardType the creditCardType to set
	 */
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
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
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}
	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	/**
	 * @return the excel
	 */
	public String getExcel() {
		return excel;
	}
	/**
	 * @param excel the excel to set
	 */
	public void setExcel(String excel) {
		this.excel = excel;
	}
	/**
	 * @return the failedtype
	 */
	public String getFailedtype() {
		return failedtype;
	}
	/**
	 * @param failedtype the failedtype to set
	 */
	public void setFailedtype(String failedtype) {
		this.failedtype = failedtype;
	}
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the hotlistActionType
	 */
	public String getHotlistActionType() {
		return hotlistActionType;
	}
	/**
	 * @param hotlistActionType the hotlistActionType to set
	 */
	public void setHotlistActionType(String hotlistActionType) {
		this.hotlistActionType = hotlistActionType;
	}
	/**
	 * @return the hotListDate
	 */
	public String getHotListDate() {
		return hotListDate;
	}
	/**
	 * @param hotListDate the hotListDate to set
	 */
	public void setHotListDate(String hotListDate) {
		this.hotListDate = hotListDate;
	}
	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}
	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the mediasalesAdmin
	 */
	public String getMediasalesAdmin() {
		return mediasalesAdmin;
	}
	/**
	 * @param mediasalesAdmin the mediasalesAdmin to set
	 */
	public void setMediasalesAdmin(String mediasalesAdmin) {
		this.mediasalesAdmin = mediasalesAdmin;
	}
	/**
	 * @return the monthlyUsage
	 */
	public String getMonthlyUsage() {
		return monthlyUsage;
	}
	/**
	 * @param monthlyUsage the monthlyUsage to set
	 */
	public void setMonthlyUsage(String monthlyUsage) {
		this.monthlyUsage = monthlyUsage;
	}
	/**
	 * @return the noOfCancelled
	 */
	public String getNoOfCancelled() {
		return noOfCancelled;
	}
	/**
	 * @param noOfCancelled the noOfCancelled to set
	 */
	public void setNoOfCancelled(String noOfCancelled) {
		this.noOfCancelled = noOfCancelled;
	}
	/**
	 * @return the noOfCardsSold
	 */
	public String getNoOfCardsSold() {
		return noOfCardsSold;
	}
	/**
	 * @param noOfCardsSold the noOfCardsSold to set
	 */
	public void setNoOfCardsSold(String noOfCardsSold) {
		this.noOfCardsSold = noOfCardsSold;
	}
	/**
	 * @return the noOfDeliveryAttempts
	 */
	public String getNoOfDeliveryAttempts() {
		return noOfDeliveryAttempts;
	}
	/**
	 * @param noOfDeliveryAttempts the noOfDeliveryAttempts to set
	 */
	public void setNoOfDeliveryAttempts(String noOfDeliveryAttempts) {
		this.noOfDeliveryAttempts = noOfDeliveryAttempts;
	}
	/**
	 * @return the noOfFailed
	 */
	public String getNoOfFailed() {
		return noOfFailed;
	}
	/**
	 * @param noOfFailed the noOfFailed to set
	 */
	public void setNoOfFailed(String noOfFailed) {
		this.noOfFailed = noOfFailed;
	}
	/**
	 * @return the noOfFulfilled
	 */
	public String getNoOfFulfilled() {
		return noOfFulfilled;
	}
	/**
	 * @param noOfFulfilled the noOfFulfilled to set
	 */
	public void setNoOfFulfilled(String noOfFulfilled) {
		this.noOfFulfilled = noOfFulfilled;
	}
	/**
	 * @return the noOfHotlisted
	 */
	public String getNoOfHotlisted() {
		return noOfHotlisted;
	}
	/**
	 * @param noOfHotlisted the noOfHotlisted to set
	 */
	public void setNoOfHotlisted(String noOfHotlisted) {
		this.noOfHotlisted = noOfHotlisted;
	}
	/**
	 * @return the noOfOrders
	 */
	public String getNoOfOrders() {
		return noOfOrders;
	}
	/**
	 * @param noOfOrders the noOfOrders to set
	 */
	public void setNoOfOrders(String noOfOrders) {
		this.noOfOrders = noOfOrders;
	}
	/**
	 * @return the noOfOutstanding
	 */
	public String getNoOfOutstanding() {
		return noOfOutstanding;
	}
	/**
	 * @param noOfOutstanding the noOfOutstanding to set
	 */
	public void setNoOfOutstanding(String noOfOutstanding) {
		this.noOfOutstanding = noOfOutstanding;
	}
	/**
	 * @return the noOfPassed
	 */
	public String getNoOfPassed() {
		return noOfPassed;
	}
	/**
	 * @param noOfPassed the noOfPassed to set
	 */
	public void setNoOfPassed(String noOfPassed) {
		this.noOfPassed = noOfPassed;
	}
	/**
	 * @return the noOfPending
	 */
	public String getNoOfPending() {
		return noOfPending;
	}
	/**
	 * @param noOfPending the noOfPending to set
	 */
	public void setNoOfPending(String noOfPending) {
		this.noOfPending = noOfPending;
	}
	/**
	 * @return the noOfReturned
	 */
	public String getNoOfReturned() {
		return noOfReturned;
	}
	/**
	 * @param noOfReturned the noOfReturned to set
	 */
	public void setNoOfReturned(String noOfReturned) {
		this.noOfReturned = noOfReturned;
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
	 * @return the orderModule
	 */
	public String getOrderModule() {
		return orderModule;
	}
	/**
	 * @param orderModule the orderModule to set
	 */
	public void setOrderModule(String orderModule) {
		this.orderModule = orderModule;
	}
	/**
	 * @return the orderQty
	 */
	public String getOrderQty() {
		return orderQty;
	}
	/**
	 * @param orderQty the orderQty to set
	 */
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
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
	/**
	 * @return the orderValue
	 */
	public String getOrderValue() {
		return orderValue;
	}
	/**
	 * @param orderValue the orderValue to set
	 */
	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}
	/**
	 * @return the parentPartnerid
	 */
	public Long getParentPartnerid() {
		return parentPartnerid;
	}
	/**
	 * @param parentPartnerid the parentPartnerid to set
	 */
	public void setParentPartnerid(Long parentPartnerid) {
		this.parentPartnerid = parentPartnerid;
	}
	/**
	 * @return the partnerAdminName
	 */
	public String getPartnerAdminName() {
		return partnerAdminName;
	}
	/**
	 * @param partnerAdminName the partnerAdminName to set
	 */
	public void setPartnerAdminName(String partnerAdminName) {
		this.partnerAdminName = partnerAdminName;
	}
	/**
	 * @return the partnerCompanyName
	 */
	public String getPartnerCompanyName() {
		return partnerCompanyName;
	}
	/**
	 * @param partnerCompanyName the partnerCompanyName to set
	 */
	public void setPartnerCompanyName(String partnerCompanyName) {
		this.partnerCompanyName = partnerCompanyName;
	}
	/**
	 * @return the partnerid
	 */
	public Long getPartnerid() {
		return partnerid;
	}
	/**
	 * @param partnerid the partnerid to set
	 */
	public void setPartnerid(Long partnerid) {
		this.partnerid = partnerid;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the pdf
	 */
	public String getPdf() {
		return pdf;
	}
	/**
	 * @param pdf the pdf to set
	 */
	public void setPdf(String pdf) {
		this.pdf = pdf;
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
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the processeddate
	 */
	public String getProcesseddate() {
		return processeddate;
	}
	/**
	 * @param processeddate the processeddate to set
	 */
	public void setProcesseddate(String processeddate) {
		this.processeddate = processeddate;
	}
	/**
	 * @return the processedDate
	 */
	public String getProcessedDate() {
		return processedDate;
	}
	/**
	 * @param processedDate the processedDate to set
	 */
	public void setProcessedDate(String processedDate) {
		this.processedDate = processedDate;
	}
	/**
	 * @return the products
	 */
	public String getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(String products) {
		this.products = products;
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
	/**
	 * @return the reasonType
	 */
	public String getReasonType() {
		return reasonType;
	}
	/**
	 * @param reasonType the reasonType to set
	 */
	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}
	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}
	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	/**
	 * @return the returnedDate
	 */
	public String getReturnedDate() {
		return returnedDate;
	}
	/**
	 * @param returnedDate the returnedDate to set
	 */
	public void setReturnedDate(String returnedDate) {
		this.returnedDate = returnedDate;
	}
	/**
	 * @return the roleid
	 */
	public Long getRoleid() {
		return roleid;
	}
	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	/**
	 * @return the shippingAmount
	 */
	public String getShippingAmount() {
		return shippingAmount;
	}
	/**
	 * @param shippingAmount the shippingAmount to set
	 */
	public void setShippingAmount(String shippingAmount) {
		this.shippingAmount = shippingAmount;
	}
	/**
	 * @return the subCompanyName
	 */
	public String getSubCompanyName() {
		return subCompanyName;
	}
	/**
	 * @param subCompanyName the subCompanyName to set
	 */
	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}
	/**
	 * @return the tmaName
	 */
	public String getTmaName() {
		return tmaName;
	}
	/**
	 * @param tmaName the tmaName to set
	 */
	public void setTmaName(String tmaName) {
		this.tmaName = tmaName;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the gbsSubTotal
	 */
	public double getGbsSubTotal() {
		return gbsSubTotal;
	}
	/**
	 * @param gbsSubTotal the gbsSubTotal to set
	 */
	public void setGbsSubTotal(double gbsSubTotal) {
		this.gbsSubTotal = gbsSubTotal;
	}
	/**
	 * @return the gbsDiscount
	 */
	public double getGbsDiscount() {
		return gbsDiscount;
	}
	/**
	 * @param gbsDiscount the gbsDiscount to set
	 */
	public void setGbsDiscount(double gbsDiscount) {
		this.gbsDiscount = gbsDiscount;
	}
}
