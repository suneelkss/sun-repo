
package com.marta.admin.hibernate;

import java.util.Date;

public class GLGBSCASHView implements java.io.Serializable{
	
	private String productname;
	private String accountnumber;
	private String orderid;
	private String firstname;
	private String lastname;
	private String ordertype;
	private String approvalcode;
	private String price;
	private String cashvalue;
	private Date transactiondate;
	private String shippingamount;
	private String patronaddressid;
	private String priceofbreezecard;
	private String noofcards;
	private String discountedamount;
	
	
	
	
	
	public String getNoofcards() {
		return noofcards;
	}
	public void setNoofcards(String noofcards) {
		this.noofcards = noofcards;
	}
	public String getDiscountedamount() {
		return discountedamount;
	}
	public void setDiscountedamount(String discountedamount) {
		this.discountedamount = discountedamount;
	}
	public String getPatronaddressid() {
		return patronaddressid;
	}
	public void setPatronaddressid(String patronaddressid) {
		this.patronaddressid = patronaddressid;
	}
	public String getPriceofbreezecard() {
		return priceofbreezecard;
	}
	public void setPriceofbreezecard(String priceofbreezecard) {
		this.priceofbreezecard = priceofbreezecard;
	}
	public String getShippingamount() {
		return shippingamount;
	}
	public void setShippingamount(String shippingamount) {
		this.shippingamount = shippingamount;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
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
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getApprovalcode() {
		return approvalcode;
	}
	public void setApprovalcode(String approvalcode) {
		this.approvalcode = approvalcode;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCashvalue() {
		return cashvalue;
	}
	public void setCashvalue(String cashvalue) {
		this.cashvalue = cashvalue;
	}
	public Date getTransactiondate() {
		return transactiondate;
	}
	public void setTransactiondate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}
	
	

}
