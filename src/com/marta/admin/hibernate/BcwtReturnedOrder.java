package com.marta.admin.hibernate;

import java.util.Date;

/**
 * Bcwtreturnedorder generated by MyEclipse - Hibernate Tools
 */

public class BcwtReturnedOrder  implements java.io.Serializable {

     private Long returnedorderid;
     private BcwtReasonType bcwtreasontype;
     private BcwtOrder bcwtorder;
     private String note;
     private Date returneddate;
     private String updatedby;
     private String activestatus;
     
	/**
	 * @return the activestatus
	 */
	public String getActivestatus() {
		return activestatus;
	}
	/**
	 * @param activestatus the activestatus to set
	 */
	public void setActivestatus(String activestatus) {
		this.activestatus = activestatus;
	}
	/**
	 * @return the bcwtorder
	 */
	public BcwtOrder getBcwtorder() {
		return bcwtorder;
	}
	/**
	 * @param bcwtorder the bcwtorder to set
	 */
	public void setBcwtorder(BcwtOrder bcwtorder) {
		this.bcwtorder = bcwtorder;
	}
	/**
	 * @return the bcwtreasontype
	 */
	public BcwtReasonType getBcwtreasontype() {
		return bcwtreasontype;
	}
	/**
	 * @param bcwtreasontype the bcwtreasontype to set
	 */
	public void setBcwtreasontype(BcwtReasonType bcwtreasontype) {
		this.bcwtreasontype = bcwtreasontype;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the returneddate
	 */
	public Date getReturneddate() {
		return returneddate;
	}
	/**
	 * @param returneddate the returneddate to set
	 */
	public void setReturneddate(Date returneddate) {
		this.returneddate = returneddate;
	}
	/**
	 * @return the returnedorderid
	 */
	public Long getReturnedorderid() {
		return returnedorderid;
	}
	/**
	 * @param returnedorderid the returnedorderid to set
	 */
	public void setReturnedorderid(Long returnedorderid) {
		this.returnedorderid = returnedorderid;
	}
	/**
	 * @return the updatedby
	 */
	public String getUpdatedby() {
		return updatedby;
	}
	/**
	 * @param updatedby the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

}