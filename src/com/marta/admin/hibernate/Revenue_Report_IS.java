package com.marta.admin.hibernate;

import java.io.Serializable;
import java.util.Date;

public class Revenue_Report_IS implements Serializable {
	
	private String orderid;
	private String firstname;
	private String lastname;
	private String addressid;
	private String price;
	private String priceofbreezecard;
	private String ordertype;
	private String approvalcode;
	private String creditcardnumber;
	private String cardtype;
	private String cashvalue;
	private Date transactiondate;
	private String shippingamount; 
	private String products;
	private String ordervalue;
	
	
	
	public String getOrdervalue() {
		return ordervalue;
	}
	public void setOrdervalue(String ordervalue) {
		this.ordervalue = ordervalue;
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
	public String getAddressid() {
		return addressid;
	}
	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPriceofbreezecard() {
		return priceofbreezecard;
	}
	public void setPriceofbreezecard(String priceofbreezecard) {
		this.priceofbreezecard = priceofbreezecard;
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
	public String getCreditcardnumber() {
		return creditcardnumber;
	}
	public void setCreditcardnumber(String creditcardnumber) {
		this.creditcardnumber = creditcardnumber;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
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
	public String getShippingamount() {
		return shippingamount;
	}
	public void setShippingamount(String shippingamount) {
		this.shippingamount = shippingamount;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	} 
	
	

}
