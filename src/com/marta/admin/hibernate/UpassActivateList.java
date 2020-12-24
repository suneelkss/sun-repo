package com.marta.admin.hibernate;

import java.util.Date;

public class UpassActivateList {
	
	private Long upassactivatelistid;
	private UpassSchools upassschool;
	private UpassAdmins upassadmin;
	private String firstname;
	private String lastname;
	private String phonenumber;
	private String email;
	private String memberid;
	private Date  requestDate;
	private String activatestatus;
	private String serialnumber;
	private String nfsid;
	private String benefitid;
	private String customermemberid;
	
	
	
	
	
	public String getCustomermemberid() {
		return customermemberid;
	}
	public void setCustomermemberid(String customermemberid) {
		this.customermemberid = customermemberid;
	}
	public Long getUpassactivatelistid() {
		return upassactivatelistid;
	}
	public void setUpassactivatelistid(Long upassactivatelistid) {
		this.upassactivatelistid = upassactivatelistid;
	}
	public UpassSchools getUpassschool() {
		return upassschool;
	}
	public void setUpassschool(UpassSchools upassschool) {
		this.upassschool = upassschool;
	}
	public UpassAdmins getUpassadmin() {
		return upassadmin;
	}
	public void setUpassadmin(UpassAdmins upassadmin) {
		this.upassadmin = upassadmin;
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
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getActivatestatus() {
		return activatestatus;
	}
	public void setActivatestatus(String activatestatus) {
		this.activatestatus = activatestatus;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getNfsid() {
		return nfsid;
	}
	public void setNfsid(String nfsid) {
		this.nfsid = nfsid;
	}
	public String getBenefitid() {
		return benefitid;
	}
	public void setBenefitid(String benefitid) {
		this.benefitid = benefitid;
	}
	
	

}
