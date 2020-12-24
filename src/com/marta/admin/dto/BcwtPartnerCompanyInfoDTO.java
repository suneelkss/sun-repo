package com.marta.admin.dto;

import java.io.Serializable;

public class BcwtPartnerCompanyInfoDTO implements Serializable {
	
	private String companyId;
	private String companyName;
	private String companyTypeId;
	private String companyStatusId;
	private String purchaseOrderNo;
	private String nextFareCompanyId;
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyStatusId() {
		return companyStatusId;
	}
	public void setCompanyStatusId(String companyStatusId) {
		this.companyStatusId = companyStatusId;
	}
	public String getCompanyTypeId() {
		return companyTypeId;
	}
	public void setCompanyTypeId(String companyTypeId) {
		this.companyTypeId = companyTypeId;
	}
	public String getNextFareCompanyId() {
		return nextFareCompanyId;
	}
	public void setNextFareCompanyId(String nextFareCompanyId) {
		this.nextFareCompanyId = nextFareCompanyId;
	}
	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}
	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

}
