package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class PrintShippingLabelForm extends ValidatorActionForm {
		
	private String nextFareOrderId;
	private String orderType;
	
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
