package com.marta.admin.hibernate;

import java.util.Date;


/**
 * Bcwtpartnercancelledorder generated by MyEclipse - Hibernate Tools
 */

public class BcwtPartnerCancelledOrder  implements java.io.Serializable {


    // Fields

     private Long cancelledorderid;
     private PartnerCardOrder partnercardorder;
     private BcwtReasonType bcwtreasontype;
     private String note;
     private Date cancelleddate;
     private String cancelledby;
     private String activestatus;

	public String getActivestatus() {
		return activestatus;
	}
	public void setActivestatus(String activestatus) {
		this.activestatus = activestatus;
	}
	public BcwtReasonType getBcwtreasontype() {
		return bcwtreasontype;
	}
	public void setBcwtreasontype(BcwtReasonType bcwtreasontype) {
		this.bcwtreasontype = bcwtreasontype;
	}
	public String getCancelledby() {
		return cancelledby;
	}
	public void setCancelledby(String cancelledby) {
		this.cancelledby = cancelledby;
	}
	public Date getCancelleddate() {
		return cancelleddate;
	}
	public void setCancelleddate(Date cancelleddate) {
		this.cancelleddate = cancelleddate;
	}
	public Long getCancelledorderid() {
		return cancelledorderid;
	}
	public void setCancelledorderid(Long cancelledorderid) {
		this.cancelledorderid = cancelledorderid;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public PartnerCardOrder getPartnercardorder() {
		return partnercardorder;
	}
	public void setPartnercardorder(PartnerCardOrder partnercardorder) {
		this.partnercardorder = partnercardorder;
	}



}