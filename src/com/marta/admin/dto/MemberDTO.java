package com.marta.admin.dto;

import java.util.List;

public class MemberDTO {

	private static final long serialVersionUID = 1L;
	private String customerid;
	private String memberid;
	private String customermemberid;
	private String serialnumber;
	private String lastname;
	private String firstname;
	private String phoneid;
	private String email;
	private String benefitstatusid;
	private String serialnumberassigneddtm;
	private List benefitid;
	private String notes;
	/**
	 * @return the benefitstatusid
	 */
	public List getBenefitid() {
		return benefitid;
	}
	public void setBenefitid(List benefitid) {
		this.benefitid = benefitid;
	}
	public String getBenefitstatusid() {
		return benefitstatusid;
	}
	public void setBenefitstatusid(String benefitstatusid) {
		this.benefitstatusid = benefitstatusid;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getCustomermemberid() {
		return customermemberid;
	}
	public void setCustomermemberid(String customermemberid) {
		this.customermemberid = customermemberid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getPhoneid() {
		return phoneid;
	}
	public void setPhoneid(String phoneid) {
		this.phoneid = phoneid;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getSerialnumberassigneddtm() {
		return serialnumberassigneddtm;
	}
	public void setSerialnumberassigneddtm(String serialnumberassigneddtm) {
		this.serialnumberassigneddtm = serialnumberassigneddtm;
	}
}
