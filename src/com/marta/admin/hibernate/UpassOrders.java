package com.marta.admin.hibernate;

import java.util.Date;

public class UpassOrders {
	
	private Long upassorderid;
	private BcwtOrderStatus bcwtorderstatus;
	private UpassAddress upassaddress;
	private UpassAdmins upassadmin;
	private Date orderdate;
	private Double noofcards;
	private Double shippingamount;
	private Long processedBy;
	private Long nextfareorderid;
	private Long batchid;
	private Date shippeddate;
	private Long nextfarecompanyid;
	
	
	
	public Long getNextfarecompanyid() {
		return nextfarecompanyid;
	}
	public void setNextfarecompanyid(Long nextfarecompanyid) {
		this.nextfarecompanyid = nextfarecompanyid;
	}
	public Long getUpassorderid() {
		return upassorderid;
	}
	public void setUpassorderid(Long upassorderid) {
		this.upassorderid = upassorderid;
	}
	public BcwtOrderStatus getBcwtorderstatus() {
		return bcwtorderstatus;
	}
	public void setBcwtorderstatus(BcwtOrderStatus bcwtorderstatus) {
		this.bcwtorderstatus = bcwtorderstatus;
	}
	public UpassAddress getUpassaddress() {
		return upassaddress;
	}
	public void setUpassaddress(UpassAddress upassaddress) {
		this.upassaddress = upassaddress;
	}
	public UpassAdmins getUpassadmin() {
		return upassadmin;
	}
	public void setUpassadmin(UpassAdmins upassadmin) {
		this.upassadmin = upassadmin;
	}
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	public Double getNoofcards() {
		return noofcards;
	}
	public void setNoofcards(Double noofcards) {
		this.noofcards = noofcards;
	}
	public Double getShippingamount() {
		return shippingamount;
	}
	public void setShippingamount(Double shippingamount) {
		this.shippingamount = shippingamount;
	}
	public Long getProcessedBy() {
		return processedBy;
	}
	public void setProcessedBy(Long processedBy) {
		this.processedBy = processedBy;
	}
	public Long getNextfareorderid() {
		return nextfareorderid;
	}
	public void setNextfareorderid(Long nextfareorderid) {
		this.nextfareorderid = nextfareorderid;
	}
	public Long getBatchid() {
		return batchid;
	}
	public void setBatchid(Long batchid) {
		this.batchid = batchid;
	}
	public Date getShippeddate() {
		return shippeddate;
	}
	public void setShippeddate(Date shippeddate) {
		this.shippeddate = shippeddate;
	}
	
	

}
