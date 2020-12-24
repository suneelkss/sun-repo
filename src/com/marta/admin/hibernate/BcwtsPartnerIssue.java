package com.marta.admin.hibernate;

import java.io.Serializable;
import java.util.Date;

public class BcwtsPartnerIssue implements Serializable {
	
	private Long bcwtspartnerissueid;
	private String issuedescription;
	private String category;
	private String issuestatus;
	private Date creationdate;
	private Date closedate;
	private String tmaname;
	private String companyname;
	private String serialnumber;
	private String memberid;
	private String createdby;
	private String resolution;
	private BcwtPatron adminid;
	private String email;
	private String region;
	
	
	
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getBcwtspartnerissueid() {
		return bcwtspartnerissueid;
	}
	public void setBcwtspartnerissueid(Long bcwtspartnerissueid) {
		this.bcwtspartnerissueid = bcwtspartnerissueid;
	}
	public String getIssuedescription() {
		return issuedescription;
	}
	public void setIssuedescription(String issuedescription) {
		this.issuedescription = issuedescription;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getIssuestatus() {
		return issuestatus;
	}
	public void setIssuestatus(String issuestatus) {
		this.issuestatus = issuestatus;
	}
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	public Date getClosedate() {
		return closedate;
	}
	public void setClosedate(Date closedate) {
		this.closedate = closedate;
	}
	public String getTmaname() {
		return tmaname;
	}
	public void setTmaname(String tmaname) {
		this.tmaname = tmaname;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public BcwtPatron getAdminid() {
		return adminid;
	}
	public void setAdminid(BcwtPatron adminid) {
		this.adminid = adminid;
	}
	
	
	
	
	

}
