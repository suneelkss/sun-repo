package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class AddNewProductForm extends ValidatorActionForm {
	
	    private String productId;
		private String fareInstrumentId;
		private String regionId;
		private String productName;
		private String productDescription;
		private String productDetailDescription;
		private String riderClassification;
		private String price;
		private String sortOrder;
		
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
		public String getProductDetailDescription() {
			return productDetailDescription;
		}
		public void setProductDetailDescription(String productDetailDescription) {
			this.productDetailDescription = productDetailDescription;
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
		
	
}