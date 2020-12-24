package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * 
 * @author Jagadeesan
 *
 */
public class ViewEditProductsForm extends ValidatorActionForm{

	private String productId;
	private String fareInstrumentId;
	private String regionId;
	private String productName;
	private String productDescription;
	private String productDetailedDesc;
	private String riderClassification;
	private String price;
	private String sortOrder;
	private String regionName;
	private String activeStatus;
	
	private String editProductId;
	private String editFareInstrumentId;
	private String editRegionId;
	private String editProductName;
	private String editProductDescription;
	private String editProductDetailedDesc;
	private String editRiderClassification;
	private String editPrice;
	private String editSortOrder;
	private String editRegionName;
	private String editActiveStatus;
	
	public String getEditActiveStatus() {
		return editActiveStatus;
	}
	public void setEditActiveStatus(String editActiveStatus) {
		this.editActiveStatus = editActiveStatus;
	}
	public String getEditFareInstrumentId() {
		return editFareInstrumentId;
	}
	public void setEditFareInstrumentId(String editFareInstrumentId) {
		this.editFareInstrumentId = editFareInstrumentId;
	}
	public String getEditPrice() {
		return editPrice;
	}
	public void setEditPrice(String editPrice) {
		this.editPrice = editPrice;
	}
	public String getEditProductDescription() {
		return editProductDescription;
	}
	public void setEditProductDescription(String editProductDescription) {
		this.editProductDescription = editProductDescription;
	}
	public String getEditProductDetailedDesc() {
		return editProductDetailedDesc;
	}
	public void setEditProductDetailedDesc(String editProductDetailedDesc) {
		this.editProductDetailedDesc = editProductDetailedDesc;
	}
	public String getEditProductName() {
		return editProductName;
	}
	public void setEditProductName(String editProductName) {
		this.editProductName = editProductName;
	}
	public String getEditRegionId() {
		return editRegionId;
	}
	public void setEditRegionId(String editRegionId) {
		this.editRegionId = editRegionId;
	}
	public String getEditRegionName() {
		return editRegionName;
	}
	public void setEditRegionName(String editRegionName) {
		this.editRegionName = editRegionName;
	}
	public String getEditRiderClassification() {
		return editRiderClassification;
	}
	public void setEditRiderClassification(String editRiderClassification) {
		this.editRiderClassification = editRiderClassification;
	}
	public String getEditSortOrder() {
		return editSortOrder;
	}
	public void setEditSortOrder(String editSortOrder) {
		this.editSortOrder = editSortOrder;
	}
	public String getEditProductId() {
		return editProductId;
	}
	public void setEditProductId(String editProductId) {
		this.editProductId = editProductId;
	}
	public String getFareInstrumentId() {
		return fareInstrumentId;
	}
	public void setFareInstrumentId(String fareInstrumentId) {
		this.fareInstrumentId = fareInstrumentId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductDetailedDesc() {
		return productDetailedDesc;
	}
	public void setProductDetailedDesc(String productDetailedDesc) {
		this.productDetailedDesc = productDetailedDesc;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getRiderClassification() {
		return riderClassification;
	}
	public void setRiderClassification(String riderClassification) {
		this.riderClassification = riderClassification;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	
}
