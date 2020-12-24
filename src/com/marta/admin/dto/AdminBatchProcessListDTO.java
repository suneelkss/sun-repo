package com.marta.admin.dto;

import java.io.Serializable;
import java.util.Date;

import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.hibernate.PartnerBatchJob;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;
import com.marta.admin.dto.BcwtConfigParamsDTO;

public class AdminBatchProcessListDTO implements Serializable {

	private Long batchId;
	private String uploadedFileName;
	private Date uploadedDate;
	private String uploadedDateString;
	private Date processEndDate;
	private String processEndDateString;
	private String status;
	private String logFileName;
	private String logFileURL;
	private String xlsFilename;
	//added to show download link for log file
	private String isShow;
	
	
	
	
	public String getXlsFilename() {
		return xlsFilename;
	}

	public void setXlsFilename(String xlsFilename) {
		this.xlsFilename = xlsFilename;
	}

	//added to show delete link
	private String isShowDelete;
	
	// Uploaded file path
	private String logFileLocation;
	
	//added to download log files
	private String fileType;
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getLogFileURL() {
		return logFileURL;
	}

	public void setLogFileURL(String logFileURL) {
		this.logFileURL = logFileURL;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public AdminBatchProcessListDTO() {
		
	}
	
	public AdminBatchProcessListDTO(PartnerBatchJob partnerBatchJob) {
		this.batchId = partnerBatchJob.getBatchJobid();
		this.uploadedFileName = partnerBatchJob.getFileName();
		setUploadedDate(partnerBatchJob.getUploadTime());
		setProcessEndDate(partnerBatchJob.getProcessEndtime());
		this.logFileName = partnerBatchJob.getXlsFilename();
		this.fileType = partnerBatchJob.getFileType();
		this.logFileLocation = partnerBatchJob.getLogfileLocation();
		this.xlsFilename = partnerBatchJob.getXlsFilename();
		BcwtConfigParamsDTO configParamDTO = (BcwtConfigParamsDTO) 
		ConfigurationCache.configurationValues.get(Constants.BATCH_PROCESS_ARCHIEVE_DIR);

		this.logFileURL = configParamDTO.getParamvalue() + this.logFileName + this.fileType;
		this.status = partnerBatchJob.getPartnerBatchstatuses().getStatusDesc();
		if(this.status.equalsIgnoreCase(Constants.BATCH_PROCESS_INITIAL_STATUS_DESC) || 
				 this.status.equalsIgnoreCase(Constants.BATCH_PROCESS_PROCESSSTARTED_STATUS_DESC)){
			this.isShow = Constants.NO;
		}else{
			this.isShow = Constants.YES;
		}
		if(this.status.equalsIgnoreCase(Constants.BATCH_PROCESS_INITIAL_STATUS_DESC)){
			this.isShowDelete = Constants.YES;
		}else{
			this.isShowDelete = Constants.NO;
		}
	}
	
	
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public Date getProcessEndDate() {
		return processEndDate;
	}
	
	
	// changed from date-time format to only date format
	
	public void setProcessEndDate(Date processEndDate) {
		this.processEndDate = processEndDate;
		processEndDateString = Util.getDateAsString(processEndDate, Constants.DATE_FORMAT);
	}
	public String getProcessEndDateString() {
		return processEndDateString;
	}
	public void setProcessEndDateString(String processEndDateString) {
		this.processEndDateString = processEndDateString;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getUploadedDate() {
		return uploadedDate;
	}
//	 changed from date-time format to only date format
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
		uploadedDateString = Util.getDateAsString(uploadedDate, Constants.DATE_FORMAT);
	}
	public String getUploadedDateString() {
		return uploadedDateString;
	}
	public void setUploadedDateString(String uploadedDateString) {
		this.uploadedDateString = uploadedDateString;
	}
	public String getUploadedFileName() {
		return uploadedFileName;
	}
	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public String getLogFileLocation() {
		return logFileLocation;
	}

	public void setLogFileLocation(String logFileLocation) {
		this.logFileLocation = logFileLocation;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getIsShowDelete() {
		return isShowDelete;
	}

	public void setIsShowDelete(String isShowDelete) {
		this.isShowDelete = isShowDelete;
	}
	
	
}

