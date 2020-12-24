package com.marta.admin.hibernate;

import java.util.Date;

public class UpassBatchJob {
	
	private Long batchJobid;

    private UpassAdmins upassadminid;
    private String fileName;
    private Date uploadTime;
    private Date processStarttime;
    private Date processEndtime;
    private String logfileLocation;
    private String nfsId;
    private PartnerBatchStatuses partnerBatchStatuses;
    private String xlsFilename;
	public Long getBatchJobid() {
		return batchJobid;
	}
	public void setBatchJobid(Long batchJobid) {
		this.batchJobid = batchJobid;
	}
	
	public UpassAdmins getUpassadminid() {
		return upassadminid;
	}
	public void setUpassadminid(UpassAdmins upassadminid) {
		this.upassadminid = upassadminid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Date getProcessStarttime() {
		return processStarttime;
	}
	public void setProcessStarttime(Date processStarttime) {
		this.processStarttime = processStarttime;
	}
	public Date getProcessEndtime() {
		return processEndtime;
	}
	public void setProcessEndtime(Date processEndtime) {
		this.processEndtime = processEndtime;
	}
	public String getLogfileLocation() {
		return logfileLocation;
	}
	public void setLogfileLocation(String logfileLocation) {
		this.logfileLocation = logfileLocation;
	}
	public String getNfsId() {
		return nfsId;
	}
	public void setNfsId(String nfsId) {
		this.nfsId = nfsId;
	}
	public PartnerBatchStatuses getPartnerBatchStatuses() {
		return partnerBatchStatuses;
	}
	public void setPartnerBatchStatuses(PartnerBatchStatuses partnerBatchStatuses) {
		this.partnerBatchStatuses = partnerBatchStatuses;
	}
	public String getXlsFilename() {
		return xlsFilename;
	}
	public void setXlsFilename(String xlsFilename) {
		this.xlsFilename = xlsFilename;
	}
    
    
}
