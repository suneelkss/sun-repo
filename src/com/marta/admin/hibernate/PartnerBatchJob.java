package com.marta.admin.hibernate;

import java.util.Date;


/**
 * PartnerBatchjob generated by MyEclipse - Hibernate Tools
 */

public class PartnerBatchJob  implements java.io.Serializable {


    // Fields    

     private Long batchJobid;
     private PartnerBatchStatuses partnerBatchStatuses;
     private Long partnerId;
     private Long adminId;
     private String fileName;
     private Date uploadTime;
     private Date processStarttime;
     private Date processEndtime;
     private String logfileLocation;
     private String nfsPartnerId;
     private String logfileLocation2;
     private String xlsFilename;
     private String fileType;


    // Constructors

    /** default constructor */
    public PartnerBatchJob() {
    }

	/** minimal constructor */
    public PartnerBatchJob(Long batchJobid, Long partnerId, Long adminId) {
        this.batchJobid = batchJobid;
        this.partnerId = partnerId;
        this.adminId = adminId;
    }
    
    /** full constructor */
    public PartnerBatchJob(Long batchJobid, PartnerBatchStatuses partnerBatchStatuses, Long partnerId, Long adminId, String fileName, Date uploadTime, Date processStarttime, Date processEndtime, String logfileLocation, String nfsPartnerId, String logfileLocation2, String xlsFilename, String fileType) {
        this.batchJobid = batchJobid;
        this.partnerBatchStatuses = partnerBatchStatuses;
        this.partnerId = partnerId;
        this.adminId = adminId;
        this.fileName = fileName;
        this.uploadTime = uploadTime;
        this.processStarttime = processStarttime;
        this.processEndtime = processEndtime;
        this.logfileLocation = logfileLocation;
        this.nfsPartnerId = nfsPartnerId;
        this.logfileLocation2 = logfileLocation2;
        this.xlsFilename = xlsFilename;
        this.fileType = fileType;
    }

   
    // Property accessors

    public Long getBatchJobid() {
        return this.batchJobid;
    }
    
    public void setBatchJobid(Long batchJobid) {
        this.batchJobid = batchJobid;
    }

    public PartnerBatchStatuses getPartnerBatchstatuses() {
        return this.partnerBatchStatuses;
    }
    
    public void setPartnerBatchstatuses(PartnerBatchStatuses partnerBatchStatuses) {
        this.partnerBatchStatuses = partnerBatchStatuses;
    }

    public Long getPartnerId() {
        return this.partnerId;
    }
    
    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getAdminId() {
        return this.adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadTime() {
        return this.uploadTime;
    }
    
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getProcessStarttime() {
        return this.processStarttime;
    }
    
    public void setProcessStarttime(Date processStarttime) {
        this.processStarttime = processStarttime;
    }

    public Date getProcessEndtime() {
        return this.processEndtime;
    }
    
    public void setProcessEndtime(Date processEndtime) {
        this.processEndtime = processEndtime;
    }

    public String getLogfileLocation() {
        return this.logfileLocation;
    }
    
    public void setLogfileLocation(String logfileLocation) {
        this.logfileLocation = logfileLocation;
    }

    public String getNfsPartnerId() {
        return this.nfsPartnerId;
    }
    
    public void setNfsPartnerId(String nfsPartnerId) {
        this.nfsPartnerId = nfsPartnerId;
    }

    public String getLogfileLocation2() {
        return this.logfileLocation2;
    }
    
    public void setLogfileLocation2(String logfileLocation2) {
        this.logfileLocation2 = logfileLocation2;
    }

    public String getXlsFilename() {
        return this.xlsFilename;
    }
    
    public void setXlsFilename(String xlsFilename) {
        this.xlsFilename = xlsFilename;
    }

    public String getFileType() {
        return this.fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
   








}