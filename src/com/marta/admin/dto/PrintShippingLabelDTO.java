package com.marta.admin.dto;

import java.io.Serializable;

public class PrintShippingLabelDTO implements Serializable{
	
	private String orderId;
	private String nextFareOrderId;
	private String orderType;
	private AddressDTO addressDTO;
	private String batchId;
	private String barcode;
	
	/**
	 * @return the addressDTO
	 */
	public AddressDTO getAddressDTO() {
		return addressDTO;
	}
	/**
	 * @param addressDTO the addressDTO to set
	 */
	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}
	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}
	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}
	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	/**
	 * @return the nextFareOrderId
	 */
	public String getNextFareOrderId() {
		return nextFareOrderId;
	}
	/**
	 * @param nextFareOrderId the nextFareOrderId to set
	 */
	public void setNextFareOrderId(String nextFareOrderId) {
		this.nextFareOrderId = nextFareOrderId;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
