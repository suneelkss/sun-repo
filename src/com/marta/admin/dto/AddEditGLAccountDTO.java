package com.marta.admin.dto;

public class AddEditGLAccountDTO implements java.io.Serializable{
	
	private String productAccountNumberId;
	private String fareInstrumentId;
	private String productName;
	private String chartOfAccountsId;
	private String codeCombinationId;
	private String salesAccount;
	
	private String editProductAccountNumberId;
	private String editFareInstrumentId;
	private String editProductName;
	private String editChartOfAccountsId;
	private String editCodeCombinationId;
	private String editSalesAccount;
	
	
	public String getSalesAccount() {
		return salesAccount;
	}

	public void setSalesAccount(String salesAccount) {
		this.salesAccount = salesAccount;
	}

	public String getEditSalesAccount() {
		return editSalesAccount;
	}

	public void setEditSalesAccount(String editSalesAccount) {
		this.editSalesAccount = editSalesAccount;
	}

	public String getEditChartOfAccountsId() {
		return editChartOfAccountsId;
	}
	
	public void setEditChartOfAccountsId(String editChartOfAccountsId) {
		this.editChartOfAccountsId = editChartOfAccountsId;
	}
	public String getEditCodeCombinationId() {
		return editCodeCombinationId;
	}
	public void setEditCodeCombinationId(String editCodeCombinationId) {
		this.editCodeCombinationId = editCodeCombinationId;
	}
	public String getEditFareInstrumentId() {
		return editFareInstrumentId;
	}
	public void setEditFareInstrumentId(String editFareInstrumentId) {
		this.editFareInstrumentId = editFareInstrumentId;
	}
	public String getEditProductAccountNumberId() {
		return editProductAccountNumberId;
	}
	public void setEditProductAccountNumberId(String editProductAccountNumberId) {
		this.editProductAccountNumberId = editProductAccountNumberId;
	}
	public String getEditProductName() {
		return editProductName;
	}
	public void setEditProductName(String editProductName) {
		this.editProductName = editProductName;
	}
	public String getChartOfAccountsId() {
		return chartOfAccountsId;
	}
	public void setChartOfAccountsId(String chartOfAccountsId) {
		this.chartOfAccountsId = chartOfAccountsId;
	}
	public String getCodeCombinationId() {
		return codeCombinationId;
	}
	public void setCodeCombinationId(String codeCombinationId) {
		this.codeCombinationId = codeCombinationId;
	}
	public String getFareInstrumentId() {
		return fareInstrumentId;
	}
	public void setFareInstrumentId(String fareInstrumentId) {
		this.fareInstrumentId = fareInstrumentId;
	}
	public String getProductAccountNumberId() {
		return productAccountNumberId;
	}
	public void setProductAccountNumberId(String productAccountNumberId) {
		this.productAccountNumberId = productAccountNumberId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

}
