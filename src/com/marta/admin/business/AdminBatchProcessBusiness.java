package com.marta.admin.business;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.marta.admin.dao.AdminPartnerBatchProcessDAO;
import com.marta.admin.dto.BatchProcessPartnerListDTO;
import com.marta.admin.dto.PartnerAdminDetailsDTO;
import com.marta.admin.forms.AdminBatchProcessForm;
import com.marta.admin.hibernate.PartnerBatchJob;
import com.marta.admin.hibernate.PartnerBatchStatuses;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;

public class AdminBatchProcessBusiness {
	
	final String ME = "BatchProcess: ";
	
	/**
	 * This method returns the list of batch jobs for the 
	 * given company id
	 * 
	 * @param companyId
	 * @return Returns list of PartnerBatchJob 
	 * @throws Exception
	 */
	public List getBatchJobs(Long partnerid) throws Exception  {
		AdminPartnerBatchProcessDAO partnerBatchProcessDAO = new AdminPartnerBatchProcessDAO();
		List batchJobsList = partnerBatchProcessDAO.getBatchJobsForPartnerId(partnerid);
		return batchJobsList;
	}

	public void processBatchFile(InputStream inputStream, String fileName, 
			BatchProcessPartnerListDTO listDTO, Date uploadTime, 
			String archivalFileName, String archivalRootFolder) throws Exception {
		
		final String MY_NAME = ME + "processBatchFile: ";
		
		BcwtsLogger.debug(MY_NAME + " updating batch start ");
		PartnerBatchJob partnerBatchJob = updateBatchStart(fileName, listDTO, uploadTime, archivalFileName, archivalRootFolder);
		BcwtsLogger.info(MY_NAME + " batch start updated ");
		
/*		
		BcwtsLogger.debug(MY_NAME + " reading the batch excel ");
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		HSSFSheet firstSheet = workbook.getSheetAt(0);
		
		int firstRowNumber = firstSheet.getFirstRowNum();
		int lastRowNumber = firstSheet.getLastRowNum();
		
		// skip the header row
		int startIndex = firstRowNumber + 1;
		int lastIndex = lastRowNumber;
		
		int successCount = 0;
		int failureCount = 0;
		
		int completionStatus = 0;
		StringBuffer logFileContentsBuffer = new StringBuffer(); 
		
		for (int loopIndex = startIndex; loopIndex <= lastIndex; loopIndex++) {
			HSSFRow currentRow = firstSheet.getRow(loopIndex);
			BatchProcessDataDTO batchProcessDataDTO = processRowData(currentRow);
			logFileContentsBuffer.append(batchProcessDataDTO.getDataAsString()
					+System.getProperty("line.separator")+System.getProperty("line.separator"));
			
			if (processData(batchProcessDataDTO)) {
				successCount++;
			} else {
				failureCount++;
			}
			
			if (successCount > 0 && failureCount == 0) {
				completionStatus = 1;
			} else if (successCount == 0 && failureCount > 0) {
				completionStatus = 3;
			} else {
				completionStatus = 2;
			}
		}
		
		// Write contents to log file
		FileUtil.writeToFileWithoutEncoding(logFileContentsBuffer.toString(), archivalRootFolder+File.separator+archivalFileName+".csv");
		
		BcwtsLogger.info(MY_NAME + " completed processing the batch excel contents ");
		
		BcwtsLogger.debug(MY_NAME + " updating batch completion ");
		updateBatchCompletion(fileName, completionStatus, partnerBatchJob);
		BcwtsLogger.info(MY_NAME + " batch completion updated ");
*/		
	}
	
	private PartnerBatchJob updateBatchStart(String fileName, BatchProcessPartnerListDTO listDTO, 
			Date uploadTime, String archivalFileName, String archivalRootFolder) throws Exception {
		
		final String MY_NAME = ME + "updateBatchStart: ";
		
		BcwtsLogger.debug(MY_NAME + " updating batch start information to database ");
		
		PartnerBatchStatuses partnerBatchStatuses = new PartnerBatchStatuses();
		partnerBatchStatuses.setStatusId(Constants.BATCH_PROCESS_INITIAL_STATUS_ID);
		partnerBatchStatuses.setStatusDesc(Constants.BATCH_PROCESS_INITIAL_STATUS_DESC);
		
		PartnerBatchJob partnerBatchJob = new PartnerBatchJob();
		partnerBatchJob.setPartnerBatchstatuses(partnerBatchStatuses);
		partnerBatchJob.setPartnerId(Long.valueOf(listDTO.getCompanyId()));
		partnerBatchJob.setAdminId(Long.valueOf(listDTO.getPartnerId()));
		partnerBatchJob.setUploadTime(uploadTime);
		partnerBatchJob.setProcessStarttime(new Date());
		partnerBatchJob.setLogfileLocation(archivalRootFolder);
		partnerBatchJob.setFileName(fileName);
		partnerBatchJob.setXlsFilename(archivalFileName);
		partnerBatchJob.setFileType(".xls");
		
		// Update the database with the batch process start operation
		AdminPartnerBatchProcessDAO partnerBatchProcessDAO = new AdminPartnerBatchProcessDAO();
		Long batchID = partnerBatchProcessDAO.addBatchProcess(partnerBatchJob);
		partnerBatchJob.setBatchJobid(batchID);
		
		BcwtsLogger.debug(MY_NAME + " batch start information updated to database. new batch job id: "+batchID);
		return partnerBatchJob;
	}
	
	// Status 1-Success, 2-Partial, 3-Failure
	private void updateBatchCompletion(String fileName, int status, 
			PartnerBatchJob partnerBatchJob) throws Exception {
		
		final String MY_NAME = ME + "updateBatchCompletion: ";
		
		BcwtsLogger.debug(MY_NAME + " updating batch completion information to database ");
		
		// Update the database with the batch process completion operation and status
		partnerBatchJob.setProcessEndtime(new Date());
		PartnerBatchStatuses partnerBatchStatuses = new PartnerBatchStatuses();
		if (status == 1) {
			partnerBatchStatuses.setStatusId(Constants.BATCH_PROCESS_SUCCESS_STATUS_ID);
			partnerBatchStatuses.setStatusDesc(Constants.BATCH_PROCESS_SUCCESS_STATUS_DESC);
		} else if (status == 2) {
			partnerBatchStatuses.setStatusId(Constants.BATCH_PROCESS_PARTIAL_STATUS_ID);
			partnerBatchStatuses.setStatusDesc(Constants.BATCH_PROCESS_PARTIAL_STATUS_DESC);
		} else {
			partnerBatchStatuses.setStatusId(Constants.BATCH_PROCESS_FAILURE_STATUS_ID);
			partnerBatchStatuses.setStatusDesc(Constants.BATCH_PROCESS_FAILURE_STATUS_DESC);
		}
		
		partnerBatchJob.setPartnerBatchstatuses(partnerBatchStatuses);
		AdminPartnerBatchProcessDAO partnerBatchProcessDAO = new AdminPartnerBatchProcessDAO();
		partnerBatchProcessDAO.updateBatchProcess(partnerBatchJob);
		BcwtsLogger.debug(MY_NAME + " batch completion information updated to database ");
	}
	
	public List batchList(AdminBatchProcessForm batchprocessForm,Long partnerid) throws Exception{
		AdminPartnerBatchProcessDAO partnerdao=new AdminPartnerBatchProcessDAO();
		
		return partnerdao.getSearchBatchJobs(batchprocessForm,partnerid);
	}
	
	public boolean deleteLogFile(Long batchId) throws Exception  {
		AdminPartnerBatchProcessDAO partnerBatchProcessDAO = new AdminPartnerBatchProcessDAO();
		boolean isDelete = partnerBatchProcessDAO.deleteLogFile(batchId);
		return isDelete;
	}
	
	//to get the file count
	public int getFileCount(Long partnerId,String date) throws Exception  {
		AdminPartnerBatchProcessDAO partnerBatchProcessDAO = new AdminPartnerBatchProcessDAO();
		int count = partnerBatchProcessDAO.getFileCount(partnerId, date);
		return count;
	}
	//to retrive partnerList
	public List getPartnerList(Long partnerid,AdminBatchProcessForm batchProcessForm) throws Exception  {
		AdminPartnerBatchProcessDAO partnerBatchProcessDAO = new AdminPartnerBatchProcessDAO();
		List batchPartnerList = partnerBatchProcessDAO.getPartnerList(partnerid,batchProcessForm);
		return batchPartnerList;
	}
}

