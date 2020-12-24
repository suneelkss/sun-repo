package com.marta.admin.dto;

import java.io.Serializable;

public class ListCardsCompanyIdDTO implements Serializable {
	
	private String CompanyId;
	private String nextfareCompanyId;
	private String companyName;
	private String tmaName;
	
	private String memberId;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCompanyId() {
		return CompanyId;
	}
	public void setCompanyId(String companyId) {
		CompanyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getNextfareCompanyId() {
		return nextfareCompanyId;
	}
	public void setNextfareCompanyId(String nextfareCompanyId) {
		this.nextfareCompanyId = nextfareCompanyId;
	}
	public String getTmaName() {
		return tmaName;
	}
	public void setTmaName(String tmaName) {
		this.tmaName = tmaName;
	}
	

}
