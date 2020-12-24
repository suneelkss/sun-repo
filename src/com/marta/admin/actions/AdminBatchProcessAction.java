package com.marta.admin.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.marta.admin.business.AdminBatchProcessBusiness;
import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BatchProcessPartnerListDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.PartnerAdminDetailsDTO;
import com.marta.admin.forms.AdminBatchProcessForm;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.CsvGenerator;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.ExcelGenerator;
import com.marta.admin.utils.FileUtil;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;


public class AdminBatchProcessAction extends DispatchAction {
	final String ME = "BatchProcessAction: ";
	
	public ActionForward loadPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		final String MY_NAME = ME + "loadPage: ";
		HttpSession session = request.getSession(true);
		String returnValue = null;
		String partnerId = null;
		String companyId = null;
		String companyName = null;
		try {
			if (null != session.getAttribute(Constants.USER_INFO)) {
				if(request.getParameter("partnerId") !=null && 
						!Util.isBlankOrNull(request.getParameter("partnerId"))){
					partnerId = request.getParameter("partnerId").toString();
					session.setAttribute("PARTNERID", partnerId);
				}
				if(request.getParameter("companyId") !=null && 
						!Util.isBlankOrNull(request.getParameter("companyId"))){
					companyId = request.getParameter("companyId").toString();
					session.setAttribute("COMPANYID", companyId);
				}if(request.getParameter("companyName") !=null && 
						!Util.isBlankOrNull(request.getParameter("companyName"))){
					companyName = request.getParameter("companyName").toString();
					session.setAttribute("COMPANYNAME", companyName);
				}
			returnValue = "batchProcess";
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_BATCH_PROCESSING);
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while loading the batch processing page :"
					+ e.getMessage());
			//returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		BcwtsLogger.debug(MY_NAME + " to load batch process page ");		
		return mapping.findForward(returnValue);
	}
	
	public ActionForward generateXmlFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		final String MY_NAME = ME + "generateXmlFile: ";
		BcwtsLogger.debug(MY_NAME + " to generate Xml file ");
		BcwtPatronDTO bcwtPatronDTO = null;
		HttpSession session = request.getSession(true);
		String returnValue = "viewBatchHistory";
		List batchList = null;
		Long partnerid = new Long(0);
		 
		String fileType = null;
		String fileLocation = null;
		String logFileName = null;
		String uploadFileName = null;
		ByteArrayOutputStream bs = null;
		String xmlFileName = null;
		String msg = null;
		try {
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				partnerid = bcwtPatronDTO.getPatronid();
			}
			AdminBatchProcessForm batchProcessForm = (AdminBatchProcessForm) form;
			AdminBatchProcessBusiness batchProcess = new AdminBatchProcessBusiness();
			batchList = batchProcess.getBatchJobs(partnerid);
			batchProcessForm.setBatchJobsList(batchList);
			if (null != request.getParameter("fileType")) {
				fileType = request.getParameter("fileType");
			}
			if (null != request.getParameter("fileLoc")) {
				fileLocation = request.getParameter("fileLoc");
			}
			if (null != request.getParameter("filename")) {
				logFileName = request.getParameter("filename");
			}
			if (null != request.getParameter("uploadFileName")) {
				uploadFileName = request.getParameter("uploadFileName");
			}
			String fileNameWithPath = fileLocation + /* logFileName + */"."
					+ fileType;
			StringTokenizer st = new StringTokenizer(uploadFileName, ".");
			if (st.hasMoreTokens()) {
				String fileName = st.nextToken();
				String file_type = st.nextToken();
				xmlFileName = fileName + ".xml";
			}
			msg = responseGenerateXml("application/xml", xmlFileName, response,
					fileNameWithPath, request);
			if (msg == null) {
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.FILE_NOT_FOUND_MESAAGE));
				session.setAttribute(Constants.VIEW_BATCH_HISTORY, batchList);
				returnValue = "viewBatchHistory";
			} else {
				request.removeAttribute(Constants.MESSAGE);
				session.setAttribute(Constants.VIEW_BATCH_HISTORY, batchList);
				returnValue = "viewBatchHistory";
			}
		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME
					+ " Exception while getting the batch order history :"
					+ Util.getFormattedStackTrace(e));
			// returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}

		session.setAttribute(Constants.VIEW_BATCH_HISTORY, batchList);
		request.setAttribute(Constants.MESSAGE, PropertyReader
				.getValue(Constants.FILE_DOWNLOAD_FAILURE_MESAAGE));
		return mapping.findForward(returnValue);
	}
	
	private String responseGenerateXml(String contentype, String filename,
			HttpServletResponse response, String fileLocation,
			HttpServletRequest request) {
		final String MY_NAME = "responseGenerateXml: ";
		BcwtsLogger.info(MY_NAME + "entering into ResponseGenerate method");
		ServletOutputStream servletOut = null;
		FileInputStream fIn = null;
		String dataPresent = null;
		try {
			fIn = new FileInputStream(fileLocation);
			servletOut = response.getOutputStream();
			response.setContentType(contentype);
			response.setHeader("Content-Disposition", "attachment; filename="
					+ filename);
			response.setContentLength(fIn.available());
			int ch;
			while ((ch = fIn.read()) != -1) {
				servletOut.write((char) ch);
			}
			servletOut.flush();
			dataPresent = "yes";
			servletOut.close();
			fIn.close();
			BcwtsLogger.info(MY_NAME + "response generated");
		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME
					+ " Exception while downloading the batch order history :"
					+ e.getMessage());
		}
		return dataPresent;
	}
	
	
	public ActionForward processBatch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		final String MY_NAME = ME + "processBatch: ";
		BcwtsLogger.debug(MY_NAME + " processing batch ");
		HttpSession session = request.getSession(true);
		BcwtPatronDTO patronDTO = null;
		String companyName = "";
		boolean isValid = false;
		Long partnerId = new Long(0);
		Long adminId = new Long(0);
		String companyId = null;
		String date = null;
		int count ;
		BatchProcessPartnerListDTO  listDTO = null;
		String returnValue = "batchProcess";
		
		try{
	 		if (null != session.getAttribute(Constants.USER_INFO)) {
	 			patronDTO = (BcwtPatronDTO) session
					.getAttribute(Constants.USER_INFO);
				//companyName = patronDTO.getCompanyname();
	 			adminId = patronDTO.getPatronid();
			}
	 		
	 		BcwtPatron bcwtPatron = new BcwtPatron();
	 		BeanUtils.copyProperties(bcwtPatron, patronDTO);
	 			 		
	 		listDTO = new BatchProcessPartnerListDTO();
	 		if(null != session.getAttribute("PARTNERID")){
	 			String partid = (String) session.getAttribute("PARTNERID");
	 			partnerId = Long.valueOf(partid);
	 		}
	 		if(null != session.getAttribute("COMPANYID")){
				companyId = (String) session.getAttribute("COMPANYID");
				
			}
	 		if(null != session.getAttribute("COMPANYNAME")){
	 			companyName = (String) session.getAttribute("COMPANYNAME");
				
			}
	 		
	 		listDTO.setPartnerId(partnerId.toString());
	 		listDTO.setCompanyId(companyId);
	 		listDTO.setAdminId(bcwtPatron);
			date = Util.getCurrentDate();
			AdminBatchProcessBusiness batchProcess = new AdminBatchProcessBusiness();
			count = batchProcess.getFileCount(partnerId, date);
			if(count >= 5){
				isValid = true;
			}
			if(!isValid){
			AdminBatchProcessForm batchProcessForm = (AdminBatchProcessForm) form;
			FormFile batchFile = batchProcessForm.getBatchFile();
			String fileName = batchFile.getFileName();
			InputStream inputStream = batchFile.getInputStream();
			String archivalFileName =  getArchivalFileName(fileName);
			Date uploadTime = new Date();
			BcwtConfigParamsDTO configParamDTO = (BcwtConfigParamsDTO) 
			ConfigurationCache.configurationValues.get(Constants.BATCH_PROCESS_ARCHIEVE_DIR);
			String archivalFolder = configParamDTO.getParamvalue();
			BcwtsLogger.debug(MY_NAME + " saving the excel file to archival folder ");
			try {
				File directoryFile = new File(archivalFolder +
						companyId); 
				if (!directoryFile.exists()) {
					directoryFile.mkdirs();
				}
				FileUtil.writeToFile(inputStream, archivalFolder + "/" +
						companyId + "/" + archivalFileName+".xls");
	    		} catch (Exception e) {
		    		e.printStackTrace();
			      }
			BcwtsLogger.debug(MY_NAME + " batch file saved. File:  "+archivalFolder
					+archivalFileName+".xls");
			File archivalFile = new File(archivalFolder+ "/" +
					companyId + "/" + archivalFileName+".xls"); 
			
			//To check if file size > 10MB
			double fileSize = getFileSize(archivalFile.getAbsolutePath());
			BcwtsLogger.debug(MY_NAME + "fileSize= " + fileSize);
			
			if((fileSize/(1024*1024)) > 10) {
				request.setAttribute(Constants.MESSAGE, "File size should be less than 10MB");
			} else {
				inputStream = new FileInputStream(archivalFile);
				//BatchProcess batchProcess = new BatchProcess();
				BcwtsLogger.debug(MY_NAME + " processing the batch file, calling the business method ");
				
				batchProcess.processBatchFile(inputStream, fileName, listDTO, 
						uploadTime, archivalFileName, archivalFolder +"/"+ companyId+"/");
				request.setAttribute("MESSAGE_STATUS", "true");
				request.setAttribute(Constants.MESSAGE, "File uploaded successfully");
				BcwtsLogger.info(MY_NAME + " batch process completed, forwarding to next page ");
			}
			}
			else{
				request.removeAttribute("MESSAGE_STATUS");
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.BP_FILE_UPLOAD_ERRORMSG));
			}
		}
		catch(Exception e){
			BcwtsLogger.error(MY_NAME + " Exception while uploading file: "	+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}

	private double getFileSize(String filename) {
	    File file = new File(filename);
	    if (!file.exists() || !file.isFile()) {
	      System.out.println("getFileSize()::File doesn\'t exist");
	      return -1;
	    }
	    //Here we get the actual size
	    return file.length();
	}
	
	private String getArchivalFileName(String fileName) {
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.BATCH_PROCESS_ARCHIVAL_FILE_DATE_FORMAT);
		return fileName+"_"+sdf.format(new Date());
	}
	
	/* Added for testing
	public static void main(String[] args) {
		BatchProcessAction batchProcessAction = new BatchProcessAction(); 
		System.out.println("FileName->"+batchProcessAction.getArchivalFileName("abc.xls"));
	}*/
	
	/**
	 * To view batch history page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewBatchHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		final String MY_NAME = ME + "viewBatchHistory: ";		
		BcwtsLogger.debug(MY_NAME + " to view Batch History ");
		BcwtPatronDTO partnerAdminDetailsDTO = null;
		HttpSession session = request.getSession(true);
		String returnValue = null;
		List batchList = null;
		Long partnerid = new Long(0);
		Long companyid = new Long(0);
		try {
			if (null != session.getAttribute(Constants.USER_INFO)) {
				partnerAdminDetailsDTO = (BcwtPatronDTO) session
				.getAttribute(Constants.USER_INFO);
				
			}
			if(null != session.getAttribute("PARTNERID")){
	 			String partid = (String) session.getAttribute("PARTNERID");
	 			String comId = (String) session.getAttribute("COMPANYID");
	 			
	 			partnerid = Long.valueOf(partid);
	 			companyid = Long.valueOf(comId);
	 			
	 		}
			AdminBatchProcessForm batchProcessForm = (AdminBatchProcessForm) form;
			AdminBatchProcessBusiness batchProcess = new AdminBatchProcessBusiness();
			batchList = batchProcess.getBatchJobs(companyid);
			batchProcessForm.setBatchJobsList(batchList);
			request.removeAttribute(Constants.MESSAGE);
			session.setAttribute(Constants.VIEW_BATCH_HISTORY,batchList);
			returnValue = "viewBatchHistory";
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while getting the batch order history :"
					+ e.getMessage());
			//returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}		
		return mapping.findForward(returnValue);
	}
	/**
	 * Method for Search Batch Processing 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchBatch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		final String MY_NAME = ME + "searchBatch: ";		
		BcwtsLogger.debug(MY_NAME + " to searchBatch ");
		HttpSession session = request.getSession(true);
		AdminBatchProcessForm batchprocessForm=(AdminBatchProcessForm)form;
		String returnValue = "searchBatch";
		List batchSearchList = null;
		Long partnerid = new Long(0);
		BcwtPatronDTO bcwtPatronDTO = null;
		try {
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
				.getAttribute(Constants.USER_INFO);
				//partnerid = partnerAdminDetailsDTO.getPartnerid();
				
			}	
			if(null != session.getAttribute("PARTNERID")){
	 			String partid = (String) session.getAttribute("PARTNERID");
	 			partnerid = Long.valueOf(partid);
	 		}
			AdminBatchProcessBusiness batchProcess = new AdminBatchProcessBusiness();
			batchSearchList=batchProcess.batchList(batchprocessForm,partnerid);
			batchprocessForm.setBatchJobsSearchList(batchSearchList);
			session.setAttribute(Constants.SEARCH_BATCH_HISTORY,batchSearchList);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while getting the batch order history :"
					+ e.getMessage());
			//returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}		
		return mapping.findForward(returnValue);
	}
	/**
	 * To generate csv and log file
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward generateFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		final String MY_NAME = ME + "generateFile: ";		
		BcwtsLogger.debug(MY_NAME + " to generate  xls file ");
		BcwtPatronDTO bcwtPatronDTO = null;
		HttpSession session = request.getSession(true);
		String returnValue = "viewBatchHistory";
		List batchList = null;
		Long partnerid = new Long(0);
		String fileType = null;
		String fileLocation = null;
		String logFileName = null;
		String uploadFileName = null;
		ByteArrayOutputStream bs = null;
		String xlsfilename = null;
		try {
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
				.getAttribute(Constants.USER_INFO);
				//partnerid = partnerAdminDetailsDTO.getPartnerid();
			}	
			if(null != session.getAttribute("PARTNERID")){
	 			String partid = (String) session.getAttribute("PARTNERID");
	 			partnerid = Long.valueOf(partid);
	 		}
			AdminBatchProcessForm batchProcessForm = (AdminBatchProcessForm) form;
			AdminBatchProcessBusiness batchProcess = new AdminBatchProcessBusiness();
			batchList = batchProcess.getBatchJobs(partnerid);
			batchProcessForm.setBatchJobsList(batchList);
			if(null != request.getParameter("fileType")){
				fileType =  request.getParameter("fileType");
			}
			if(null != request.getParameter("fileLoc")){
				fileLocation = request.getParameter("fileLoc");
			}
			if(null != request.getParameter("filename")){
				logFileName = request.getParameter("filename");
			}
			if(null != request.getParameter("uploadFileName")){
				uploadFileName = request.getParameter("uploadFileName");
			}
			if(null != request.getParameter("xlsFilename")){
				xlsfilename = request.getParameter("xlsFilename");
			}
			String fileNameWithPath = "";
		
			if(fileLocation.endsWith("xml")){
			
			 fileNameWithPath = xlsfilename; 	
			}
			else{
			fileNameWithPath = fileLocation + logFileName + "." + fileType;
			}
		//	String fileNameWithPath = fileLocation + logFileName + "." + fileType;
			ExcelGenerator excelGenerator = new ExcelGenerator();
			bs = excelGenerator.generateFile(fileNameWithPath, fileType);
			if(bs !=null){
				responseGenerate("application/xls",uploadFileName,response,bs);
				request.removeAttribute(Constants.MESSAGE);
				session.setAttribute(Constants.VIEW_BATCH_HISTORY,batchList);
				returnValue = "viewBatchHistory";
			}
			else{
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.FILE_NOT_FOUND_MESAAGE));
				session.setAttribute(Constants.VIEW_BATCH_HISTORY,batchList);
				returnValue = "viewBatchHistory";
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while getting the batch order history :"
					+ Util.getFormattedStackTrace(e));
			//returnValue = ErrorHandler.handleError(e, "", request, mapping);
			session.setAttribute(Constants.VIEW_BATCH_HISTORY,batchList);
			request.setAttribute(Constants.MESSAGE, PropertyReader
					.getValue(Constants.FILE_DOWNLOAD_FAILURE_MESAAGE));
		}	
		
		return mapping.findForward(returnValue);
	}
	
	/**
	 * To generate csv and log file
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward generateCsvFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		final String MY_NAME = ME + "generateCsvFile: ";		
		BcwtsLogger.debug(MY_NAME + " to generate csv file ");
		BcwtPatronDTO bcwtPatronDTO = null;
		HttpSession session = request.getSession(true);
		String returnValue = "viewBatchHistory";
		List batchList = null;
		Long partnerid = new Long(0);
		String fileType = null;
		String fileLocation = null;
		String logFileName = null;
		String uploadFileName = null;
		ByteArrayOutputStream bs = null;
		String csvFileName = null;
		String msg = null;
		try {
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
				.getAttribute(Constants.USER_INFO);
				//partnerid = bcwtPatronDTO.getPartnerid();
			}	
			if(null != session.getAttribute("PARTNERID")){
	 			String partid = (String) session.getAttribute("PARTNERID");
	 			partnerid = Long.valueOf(partid);
	 		}
			AdminBatchProcessForm batchProcessForm = (AdminBatchProcessForm) form;
			AdminBatchProcessBusiness batchProcess = new AdminBatchProcessBusiness();
			batchList = batchProcess.getBatchJobs(partnerid);
			batchProcessForm.setBatchJobsList(batchList);
			if(null != request.getParameter("fileType")){
				fileType =  request.getParameter("fileType");
			}
			if(null != request.getParameter("fileLoc")){
				fileLocation = request.getParameter("fileLoc");
			}
			if(null != request.getParameter("filename")){
				logFileName = request.getParameter("filename");
			}
			if(null != request.getParameter("uploadFileName")){
				uploadFileName = request.getParameter("uploadFileName");
			}
			String fileNameWithPath = fileLocation + logFileName + "." + fileType;
			StringTokenizer st = new StringTokenizer(uploadFileName,".");
			if(st.hasMoreTokens()){
		    		String fileName = st.nextToken();
		    		String file_type= st.nextToken();
		    		csvFileName = fileName +".csv";
			}
			msg = responseGenerateCsv("application/csv",csvFileName,response,fileNameWithPath,request);
			if(msg == null){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.FILE_NOT_FOUND_MESAAGE));
				session.setAttribute(Constants.VIEW_BATCH_HISTORY,batchList);
				returnValue = "viewBatchHistory";
			}
			else{
				request.removeAttribute(Constants.MESSAGE);
				session.setAttribute(Constants.VIEW_BATCH_HISTORY,batchList);
				returnValue = "viewBatchHistory";
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while getting the batch order history :"
					+ e.getMessage());
			//returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}	
		
		session.setAttribute(Constants.VIEW_BATCH_HISTORY,batchList);
		request.setAttribute(Constants.MESSAGE, PropertyReader
				.getValue(Constants.FILE_DOWNLOAD_FAILURE_MESAAGE));
		return mapping.findForward(returnValue);
	}
	/**
	 * To generate response object
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	 private void responseGenerate(String contentype,String filename,HttpServletResponse response,ByteArrayOutputStream baos )
     {
			final String MY_NAME =  "ResponseGenerate: ";
			BcwtsLogger.info(MY_NAME + "entering into ResponseGenerate method");
	    try{
		    response.setBufferSize(baos.size());		
		    byte requestBytes[] = baos.toByteArray();
		    ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);
		    response.reset();
		    response.setContentType(contentype);
		    response.setHeader("Content-disposition","attachment; filename="+filename);
		    byte[] buf = new byte[baos.size()];
		    int len;
		    while ((len = bis.read(buf)) > 0){
		     response.getOutputStream().write(buf, 0,buf.length);
		    }
		    bis.close();
		    response.getOutputStream().flush(); 
		    BcwtsLogger.info(MY_NAME + "response generated");
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME
					+ " Exception while getting the batch order history :"
					+ e.getMessage());	    }
     }
	 private String responseGenerateCsv(String contentype,String filename,HttpServletResponse response,String fileLocation,HttpServletRequest request )
     {
			final String MY_NAME =  "responseGenerateCsv: ";
			BcwtsLogger.info(MY_NAME + "entering into ResponseGenerate method");
			ServletOutputStream servletOut = null;
			FileInputStream fIn = null;
			String dataPresent = null;
	     try{
		    fIn = new FileInputStream(fileLocation);
			servletOut = response.getOutputStream() ;
	       	response.setContentType(contentype);
		    response.setHeader("Content-Disposition","attachment; filename="+filename);
		    response.setContentLength(fIn.available());
		    int ch;
		    while ((ch = fIn.read()) != -1) {
		       servletOut.write((char) ch);
		    }
		    servletOut.flush();
		    dataPresent = "yes";
		    servletOut.close();
		    fIn.close();
			BcwtsLogger.info(MY_NAME + "response generated");
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME
					+ " Exception while downloading the batch order history :"
					+ e.getMessage());
	    }
		return dataPresent;
     }
	 
	 /**
		 * To delete Log File
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	public ActionForward deleteLogFile(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			final String MY_NAME = ME + "deleteLogFile: ";		
			BcwtsLogger.debug(MY_NAME + " to Delete Log File ");
			BcwtPatronDTO bcwtPatronDTO = null;
			HttpSession session = request.getSession(true);
			String returnValue = "viewBatchHistory";
			List batchList = null;
			Long partnerid = new Long(0);
			Long batchId = new Long(0);
			String fileLocation = null;
			String fileName = null;
			boolean isDelete = false;
			boolean isDeleteSuccess = false;
			try {
				if (null != session.getAttribute(Constants.USER_INFO)) {
					bcwtPatronDTO = (BcwtPatronDTO) session
					.getAttribute(Constants.USER_INFO);
					//partnerid = partnerAdminDetailsDTO.getPartnerid();
				}
				if(null != session.getAttribute("PARTNERID")){
		 			String partid = (String) session.getAttribute("PARTNERID");
		 			partnerid = Long.valueOf(partid);
		 		}
				if(null != request.getParameter("batchId")){
					batchId = Long.valueOf(request.getParameter("batchId"));
				}
				if(null != request.getParameter("fileLoc")){
					fileLocation = request.getParameter("fileLoc");
				}
				if(null != request.getParameter("batchId")){
					fileName = request.getParameter("filename");
					fileName = fileName+".xls";
				}
				AdminBatchProcessBusiness batchProcess = new AdminBatchProcessBusiness();
				isDelete = batchProcess.deleteLogFile(batchId);
				//delete from location
				File file = new File(fileLocation + fileName);
				if(file.exists() && file.isFile()) {
					isDeleteSuccess = file.delete();
				}
				AdminBatchProcessForm batchProcessForm = (AdminBatchProcessForm) form;
				batchList = batchProcess.getBatchJobs(partnerid);
				batchProcessForm.setBatchJobsList(batchList);
				if(isDelete && isDeleteSuccess){
					request.setAttribute("MESSAGE_STATUS", "true");
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.FILE_DELETED_SUCCESS_MESAAGE));
					session.setAttribute(Constants.VIEW_BATCH_HISTORY,batchList);
					returnValue = "viewBatchHistory";
				}
				else{
					request.removeAttribute("MESSAGE_STATUS");
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.FILE_DELETED_FAILURE_MESAAGE));
					session.setAttribute(Constants.VIEW_BATCH_HISTORY,batchList);
					returnValue = "viewBatchHistory";
				}
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME
						+ " Exception while getting the batch order history :"
						+ e.getMessage());
				//returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}		
			return mapping.findForward(returnValue);
	}
	/*
	 * method to retrive all partners
	 */
	public ActionForward partnerList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		final String MY_NAME = ME + "partnerList: ";
		HttpSession session = request.getSession(true);
		String returnValue = null;
		Long partnerId = new Long(0);
		AdminBatchProcessBusiness batchProcessBusiness = null;
		AdminBatchProcessForm batchProcessForm = null;
		List partnerList = null;
		try {
			batchProcessBusiness = new AdminBatchProcessBusiness();
			batchProcessForm = (AdminBatchProcessForm) form;
			if (null != session.getAttribute(Constants.USER_INFO)) {
			
			}
			partnerList = batchProcessBusiness.getPartnerList(partnerId,batchProcessForm);
			session.setAttribute(Constants.BATCH_PROCCESS_PARTNER_LIST, partnerList);
			returnValue = "batchProcessPartnerList";
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_BATCH_PROCESSING);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while loading the batch processing Partner List page :"
					+ e.getMessage());
			//returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		BcwtsLogger.debug(MY_NAME + " to load batch process Partner List page ");		
		return mapping.findForward(returnValue);
	}
	 
}

