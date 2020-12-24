package com.marta.admin.hibernate;

import java.util.Date;

public class BcwtPartnerAudit  implements java.io.Serializable{

	private Long auditid;
	private String action;
	private Date   dateperformed;
	private String performed_by;
	private String serialnumber;
	
	
	
	
	
	
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getPerformed_by() {
		return performed_by;
	}
	public void setPerformed_by(String performed_by) {
		this.performed_by = performed_by;
	}
	public Long getAuditid() {
		return auditid;
	}
	public void setAuditid(Long auditid) {
		this.auditid = auditid;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getDateperformed() {
		return dateperformed;
	}
	public void setDateperformed(Date dateperformed) {
		this.dateperformed = dateperformed;
	}
	
	
	
	
}
