package com.marta.admin.forms;

import java.util.List;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

public class AdminBatchProcessForm extends ValidatorActionForm {

	private static final long serialVersionUID = 1345454355L;
	
	private FormFile batchFile;
	private List batchJobsList;
	private String fromDate;
	private String toDate;
	private String firstName;
	private String lastPartnerName;
	private List batchJobsSearchList;
	
	private String partnerFirstName;
	private String partnerLastName;
	private String companyName;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPartnerFirstName() {
		return partnerFirstName;
	}

	public void setPartnerFirstName(String partnerFirstName) {
		this.partnerFirstName = partnerFirstName;
	}

	public String getPartnerLastName() {
		return partnerLastName;
	}

	public void setPartnerLastName(String partnerLastName) {
		this.partnerLastName = partnerLastName;
	}

	public List getBatchJobsList() {
		return batchJobsList;
	}

	public void setBatchJobsList(List batchJobsList) {
		this.batchJobsList = batchJobsList;
	}

	public FormFile getBatchFile() {
		return batchFile;
	}

	public void setBatchFile(FormFile batchFile) {
		this.batchFile = batchFile;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	

	public List getBatchJobsSearchList() {
		return batchJobsSearchList;
	}

	public void setBatchJobsSearchList(List batchJobsSearchList) {
		this.batchJobsSearchList = batchJobsSearchList;
	}

	public String getLastPartnerName() {
		return lastPartnerName;
	}

	public void setLastPartnerName(String lastPartnerName) {
		this.lastPartnerName = lastPartnerName;
	}
}
