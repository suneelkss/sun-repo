package com.marta.admin.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UpassAdmins {
	
	private Long upassadminid;
	private String firstname;
	private String lastname;
	private String middlename;
	private String emailid;
	private String phone;
	private String fax;
	private String loginid;
	private String password;
	private Date lastlogin;
	private String lockstatus;
	private UpassSchools upassschools;
	private PartnerAdminRole partnerAdminRole;
	private Long logincount;
	private Set bcwtpatronaddresses = new HashSet(0);
	 private Set bcwtpwdverifiies = new HashSet(0);
	
	
	 
	 
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public Set getBcwtpatronaddresses() {
		return bcwtpatronaddresses;
	}
	public void setBcwtpatronaddresses(Set bcwtpatronaddresses) {
		this.bcwtpatronaddresses = bcwtpatronaddresses;
	}
	public Set getBcwtpwdverifiies() {
		return bcwtpwdverifiies;
	}
	public void setBcwtpwdverifiies(Set bcwtpwdverifiies) {
		this.bcwtpwdverifiies = bcwtpwdverifiies;
	}
	public Long getUpassadminid() {
		return upassadminid;
	}
	public void setUpassadminid(Long upassadminid) {
		this.upassadminid = upassadminid;
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
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}
	public String getLockstatus() {
		return lockstatus;
	}
	public void setLockstatus(String lockstatus) {
		this.lockstatus = lockstatus;
	}
	public UpassSchools getUpassschools() {
		return upassschools;
	}
	public void setUpassschools(UpassSchools upassschools) {
		this.upassschools = upassschools;
	}
	public PartnerAdminRole getPartnerAdminRole() {
		return partnerAdminRole;
	}
	public void setPartnerAdminRole(PartnerAdminRole partnerAdminRole) {
		this.partnerAdminRole = partnerAdminRole;
	}
	public Long getLogincount() {
		return logincount;
	}
	public void setLogincount(Long logincount) {
		this.logincount = logincount;
	}
	
	
	

}
