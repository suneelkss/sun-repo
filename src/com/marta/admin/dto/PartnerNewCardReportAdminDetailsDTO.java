package com.marta.admin.dto;
import java.io.Serializable;

public class PartnerNewCardReportAdminDetailsDTO implements java.io.Serializable {
		
		private String companyId = null;
		private String partnerId = null;
		private String partnerName;
		private String tmaName;
		
		public String getPartnerName() {
			return partnerName;
		}
		public void setPartnerName(String partnerName) {
			this.partnerName = partnerName;
		}
		public String getTmaName() {
			return tmaName;
		}
		public void setTmaName(String tmaName) {
			this.tmaName = tmaName;
		}
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
