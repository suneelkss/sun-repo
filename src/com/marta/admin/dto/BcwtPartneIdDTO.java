package com.marta.admin.dto;

import java.io.Serializable;

public class BcwtPartneIdDTO implements Serializable{
	
	private String companyId = null;
	private String partnerId = null;
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	

}
