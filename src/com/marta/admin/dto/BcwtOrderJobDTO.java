package com.marta.admin.dto;

public class BcwtOrderJobDTO {
	
	private String productName;
	private String fare_instrument_id;
	private int count;
	private double total;
	private String chatsOfAccountsId;
	private String codeCombinationId;
	private String totalShipping;
	private String blankCardCount;
	private String lineType;
	private double discountedAmount;
	
	
	
	
	
	
	
	
	
	public double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public String getTotalShipping() {
		return totalShipping;
	}
	public void setTotalShipping(String totalShipping) {
		this.totalShipping = totalShipping;
	}
	public String getBlankCardCount() {
		return blankCardCount;
	}
	public void setBlankCardCount(String blankCardCount) {
		this.blankCardCount = blankCardCount;
	}
	public String getChatsOfAccountsId() {
		return chatsOfAccountsId;
	}
	public void setChatsOfAccountsId(String chatsOfAccountsId) {
		this.chatsOfAccountsId = chatsOfAccountsId;
	}
	public String getCodeCombinationId() {
		return codeCombinationId;
	}
	public void setCodeCombinationId(String codeCombinationId) {
		this.codeCombinationId = codeCombinationId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getFare_instrument_id() {
		return fare_instrument_id;
	}
	public void setFare_instrument_id(String fareInstrumentId) {
		fare_instrument_id = fareInstrumentId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	} 
	

	
	
	
}
