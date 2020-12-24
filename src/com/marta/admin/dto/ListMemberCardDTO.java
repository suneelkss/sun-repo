package com.marta.admin.dto;

import java.io.Serializable;

public class ListMemberCardDTO implements Serializable {
	
	private String companyId;
	private String memberId;
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	

}
