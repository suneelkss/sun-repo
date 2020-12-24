package com.marta.admin.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hpsf.HPSFException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.marta.admin.dto.BcwtDetailedOrderReportDTO;
import com.marta.admin.dto.BcwtHotListReport;
import com.marta.admin.dto.BcwtNewCardReportDTO;
import com.marta.admin.dto.BcwtReportDTO;
import com.marta.admin.dto.BcwtUsageBenifitDTO;
import com.marta.admin.dto.BcwtsPartnerIssueDTO;
import com.marta.admin.dto.DisplayProductSummaryDTO;
import com.marta.admin.dto.PartnerMonthlyUsageReportDTO;
import com.marta.admin.dto.PartnerNewCardReportDTO;
import com.marta.admin.dto.PartnerReportSearchDTO;
import com.marta.admin.dto.PartnerUpcomingMonthlyReportDTO;
import com.marta.admin.dto.ProductDetailsReportDTO;
import com.marta.admin.dto.UpCommingMonthBenefitDTO;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.utils.Util;





public class ExcelGenerator {
	public static String ME = "ExcelGenerator: " ;
	public static  HSSFWorkbook hw = new HSSFWorkbook();

	/*
	 * Monthly Usage Report Ends
     * xls File Generate for Batch Process Starts
	 */
	public static ByteArrayOutputStream  generateFile(String fileLocation,String fileType)throws Exception {
			ByteArrayOutputStream bs = null;
			ArrayList dataList = null;
			String record  = null;
			try{
			    if(fileType.equals("xls")){
			    	POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream
								(fileLocation));
					HSSFWorkbook wb=new HSSFWorkbook(fs);
					HSSFSheet sheet = wb.getSheetAt(0);
					HSSFRow row;
					HSSFCell cell;
    				int rows;
					rows = sheet.getPhysicalNumberOfRows();
					int cols = 0;
					int tmp = 0;
				/*	for(int i = 0; i < 10 || i < rows; i++) {
						row = sheet.getRow(i);
						if(row != null) {*/
							tmp = sheet.getRow(0).getPhysicalNumberOfCells();
						if(tmp > cols) cols = tmp;
	/*					   }
						}*/
					    dataList = new ArrayList(rows*cols);
					    for(int r = 0; r < rows; r++) {
					    	row = sheet.getRow(r);
						    if(row != null) {
						    	for(int c = 0; c < cols; c++) {
						    		cell = row.getCell((short)c);
						            if(cell != null) {
						            	record = cell.toString();
						            }else{
						               	record = " ";
						            }
						            dataList.add(record);
						            }
						        }
						}
					bs = new ByteArrayOutputStream();
					bs = generateDownloadBPExcel(rows,tmp,dataList);
				  }
		        } catch ( Exception ex ) {
		        	throw new HPSFException(ex.getMessage());
			    }
			return bs;
	}

	/*
     * xls File Generate for Batch Process Ends
     * New xls File Generate for Batch Process Starts
     */
public static ByteArrayOutputStream generateDownloadBPExcel(int row,int col,
			ArrayList dataList)throws Exception {

   HSSFWorkbook hwk = new HSSFWorkbook();
	String MY_NAME = ME + "generateDownloadBPExcel:";
	BcwtsLogger.info(MY_NAME + "entering into excel generator");
   HSSFSheet hs = hwk.createSheet("BatchProcess");
   hs.setDefaultColumnWidth((short)25);
	ByteArrayOutputStream bs = new ByteArrayOutputStream();
	int rowIdx = 0;
   short cellIdx = 0;
   List reportData = new ArrayList();
	try	{
	    HSSFRow hr = hs.createRow(rowIdx);
		HSSFCellStyle cellStyle = hwk.createCellStyle();
		HSSFFont hssfFont = hwk.createFont();
	    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	    hr.setHeightInPoints(20f);
	    if(dataList != null && !Util.isBlankOrNull(dataList.toString()) ){
	        for (Iterator cells = dataList.iterator(); cells.hasNext();) {
	        	for(int i = 0; i<row; i++){
	        		cellIdx = 0;
		        	for(int cols = 0 ; cols<col ; cols++){
		        		HSSFCell hssfCell = hr.createCell(cellIdx++);
		        		hssfCell.setCellStyle(cellStyle);
		        		if(cells.hasNext()){
		        			String headcell = (String) cells.next();
		        			hssfCell.setCellValue(headcell);
		              }
		        	}
		      	    rowIdx++;
		      	    hr = hs.createRow(rowIdx);
			      }
		    	}
	    	}
       hwk.setSheetName(0, "BatchProcess", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
       hwk.write(bs);
     }
	  catch (Exception e) {
   	throw new HPSFException(e.getMessage());
     }
     return bs;
}

	public static ByteArrayOutputStream generateDetailedOrderExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {

 		String MY_NAME = ME + "generateDetailedOrderExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hwd = new HSSFWorkbook();
 	    HSSFSheet hs = hwd.createSheet("DETAILEDORDER");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    BcwtDetailedOrderReportDTO orderReportDTO = null;
 		try	{
		 //   HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwd.createCellStyle();
			HSSFFont hssfFont = hwd.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		  //  hr.setHeightInPoints(20f);
		    

		    HSSFRow hrmHeading = hs.createRow(rowIdx);
			HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
			hssfCellHeading.setCellStyle(cellStyle);
			
			hssfCellHeading.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
			
			rowIdx++;
		    
			if(null != request.getAttribute("DORDER_D_RANGE")){
				
				 HSSFRow hrmDate = hs.createRow(rowIdx);
					HSSFCell hssfCellDate = hrmDate.createCell((short)2);
					hssfCellDate.setCellStyle(cellStyle);
					
					hssfCellDate.setCellValue("Report Date Range "+request.getAttribute("DORDER_D_RANGE"));
					hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
					
					rowIdx++;
				
			}
			
			HSSFRow hr = hs.createRow(rowIdx);
			hr.setHeightInPoints(20f);
			
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
            }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	 orderReportDTO = (BcwtDetailedOrderReportDTO) L.next();
	        	 reportData.add(orderReportDTO.getCompanyName());
	        	 reportData.add(orderReportDTO.getPartnerAdminName());
	        	 reportData.add(orderReportDTO.getEmployeeName());
	        	 reportData.add(orderReportDTO.getBreezecardSerialNo());
	        	 reportData.add(orderReportDTO.getBenefitName());
	        	 reportData.add(orderReportDTO.getEffectiveDate());

			}
	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
          		if(cellIdx > headers.size()-1){
          			cellIdx = 0;
          			hssfRow = hs.createRow(++rowIdx);
          		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                  hssfCell.setCellValue((String) cells.next());
                  cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hwd.setSheetName(0, "DETAILEDORDER", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hwd.write(bs);
	      } catch (Exception e) {
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }

	public static ByteArrayOutputStream generateUsageExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {

		HSSFWorkbook hwk = new HSSFWorkbook();
		String MY_NAME = ME + "generateMonthlyUsageExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		HSSFWorkbook hw1 = new HSSFWorkbook();
		HSSFSheet hs = hw1.createSheet("USAGE");
		hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;
		ArrayList reportData = new ArrayList();
		BcwtUsageBenifitDTO bcwtUsageBenifitDTO = null ;
		try {
			
		    HSSFCellStyle cellStyle = hw1.createCellStyle();
			HSSFFont hssfFont = hw1.createFont();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		   
		    
		   
		    
		    HSSFRow hrmHeading = hs.createRow(rowIdx);
			HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
			hssfCellHeading.setCellStyle(cellStyle);
			
			hssfCellHeading.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
			
			rowIdx++;
		    
			HSSFRow hr = hs.createRow(rowIdx);
			hr.setHeightInPoints(20f);

	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
	        }
	        for(Iterator L = reportList.iterator(); L.hasNext();) {
	        	bcwtUsageBenifitDTO = (BcwtUsageBenifitDTO)L.next();

	        	 reportData.add(bcwtUsageBenifitDTO.getBreezecardSerialNo());
	        	 reportData.add(bcwtUsageBenifitDTO.getBenefitName());
	        	 reportData.add(bcwtUsageBenifitDTO.getBillingMonthYear());
	        	 reportData.add(bcwtUsageBenifitDTO.getMonthlyUsage());

			}

	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
           		if(cellIdx > headers.size()-1){
           			cellIdx = 0;
           			hssfRow = hs.createRow(++rowIdx);
           		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                   hssfCell.setCellValue((String) cells.next());
                   cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hw1.setSheetName(0, "USAGE", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hw1.write(baos);
	      } catch (Exception e) {
	    	  throw new HPSFException(e.getMessage());
	      }
	      return baos;
	}


	public static ByteArrayOutputStream generateDisplayPatronExcel(
				String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {

	 		String MY_NAME = ME + "generateDisplayPatronExcel:";
	 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
	 		HSSFWorkbook hw1 = new HSSFWorkbook();
	 	    HSSFSheet hs = hw1.createSheet("Patron List");
	 	    hs.setDefaultColumnWidth((short)25);
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			int rowIdx = 0;
		    short cellIdx = 0;
		    ArrayList reportData = new ArrayList();
		    BcwtPatron bcwtPatron = null;
	 		try	{
			  //  HSSFRow hr = hs.createRow(rowIdx);
				HSSFCellStyle cellStyle = hw1.createCellStyle();
				HSSFFont hssfFont = hw1.createFont();
			    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			    cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    cellStyle.setFont(hssfFont);
			//    hr.setHeightInPoints(20f);
			    

			    HSSFRow hrmHeading = hs.createRow(rowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyle);
				
				hssfCellHeading.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;

				HSSFRow hr = hs.createRow(rowIdx);
				hr.setHeightInPoints(20f);
				
		        for (Iterator cells = headers.iterator(); cells.hasNext();) {
		              HSSFCell hssfCell = hr.createCell(cellIdx++);
		              hssfCell.setCellStyle(cellStyle);
		              Object headcell = (Object) cells.next();
		              hssfCell.setCellValue(headcell.toString());
	            }

		        for(Iterator L = reportList.iterator();L.hasNext() ;) {
		        	bcwtPatron = (BcwtPatron) L.next();

		        	 reportData.add((bcwtPatron.getPatronid()).toString());
		         	 reportData.add(bcwtPatron.getFirstname());
		        	 reportData.add(bcwtPatron.getLastname());
		        	 reportData.add(bcwtPatron.getLastlogin().toString());
		        	 if(null != bcwtPatron.getCreateddate())
		        	  reportData.add(bcwtPatron.getCreateddate().toString());
		        	 else
		        	   reportData.add("");

				}

		        rowIdx = rowIdx+1;
			 	int count =0;
			 	if(reportData.size() > 0) {
			            HSSFRow hssfRow = hs.createRow(rowIdx);
			            cellIdx = 0;
		            for (Iterator cells = reportData.iterator(); cells.hasNext();)
		              {
	           		if(cellIdx > headers.size()-1){
	           			cellIdx = 0;
	           			hssfRow = hs.createRow(++rowIdx);
	           		}
		                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
	                   hssfCell.setCellValue((String) cells.next());
	                   cellIdx = (short)(cellIdx + 1);
		               }
			        }

		        hw1.setSheetName(0, "PatronList", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
		        hw1.write(bs);
		      } catch (Exception e) {
		    	  throw new HPSFException(e.getMessage());
		      }
		      return bs;
    }

	
	public static ByteArrayOutputStream generateUpassNewCardReportExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {
		    
		    HSSFWorkbook hw = new HSSFWorkbook();
			String MY_NAME = ME + "generateNewCardReportExcel:";
			BcwtsLogger.info(MY_NAME + "entering into excel generator");
		    HSSFSheet hs = hw.createSheet("NEWCARD");
		    hs.setDefaultColumnWidth((short)25);
		    ByteArrayOutputStream bs = new ByteArrayOutputStream();
		    int rowIdx = 0;
		    short cellIdx = 0;
		    ArrayList reportData = new ArrayList();
		    String companyName = "Upass";
		    String currentDate = "";
			try	{
				
				if (null != request.getAttribute("COMPANY_NAME_REPORT")) {
	  		      companyName = request.getAttribute("COMPANY_NAME_REPORT").toString();
	 			}
	 			currentDate = Util.getCurrentDate();
	 			
	 			HSSFCellStyle cellStyle1 = hw.createCellStyle();
				HSSFFont hssfFont1 = hw.createFont();
			    cellStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			    
			    HSSFCellStyle cellStyle2 = hw.createCellStyle();
	 			HSSFFont hssfFont2 = hw.createFont();
	 			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			   // hssfFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	 			hssfFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	 			cellStyle2.setFont(hssfFont2);
	 			
				HSSFRow reportHead = hs.createRow(rowIdx);
				HSSFCell cellReportHead = reportHead.createCell(cellIdx);
				cellReportHead.setCellStyle(cellStyle2);
				cellReportHead.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx = rowIdx+1;
			    
	 		    HSSFRow hrmCompany = hs.createRow(rowIdx);
	 			HSSFCell hssfCellCompany = hrmCompany.createCell(cellIdx);
	 			hssfCellCompany.setCellStyle(cellStyle1);
	 			String company = " Company Name :  " +  companyName;
	 			hssfCellCompany.setCellValue(company);
	 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
	 			
	 			rowIdx = rowIdx+1;
	 			HSSFRow hrmDate = hs.createRow(rowIdx);
	 			HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
	 			hssfCellDate.setCellStyle(cellStyle1);
	 			String dateGenerated = " Report Generated Date :  " +  currentDate;
	 			hssfCellDate.setCellValue(dateGenerated);
	 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
	 			rowIdx = rowIdx+1;
	 		
		 		HSSFRow hr = hs.createRow(rowIdx);
				HSSFCellStyle cellStyle = hw.createCellStyle();
				HSSFFont hssfFont = hw.createFont();
			    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			    cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    cellStyle.setFont(hssfFont);
			    rowIdx = rowIdx+1;
			    hr.setHeightInPoints(20f);
		        for (Iterator cells = headers.iterator(); cells.hasNext();) {
		           
		        	HSSFCell hssfCell = hr.createCell(cellIdx++);
		              hssfCell.setCellStyle(cellStyle);
		              Object headcell = (Object) cells.next();
		              if(null!=headcell){ 
		            	 
		              hssfCell.setCellValue(headcell.toString());
		            }
		        }
		        for(Iterator L = reportList. iterator();L.hasNext() ;) {
		        	BcwtNewCardReportDTO newCardReportSearchDTO = (BcwtNewCardReportDTO) L.next();
					 reportData.add(newCardReportSearchDTO.getBreezecardSerialNo());
					 reportData.add(newCardReportSearchDTO.getBenefitName());
					 reportData.add(newCardReportSearchDTO.getEffectiveDate());
				}
			 	int count =0;
			 	if(reportData.size() > 0) {
			            HSSFRow hssfRow = hs.createRow(rowIdx);
			            cellIdx = 0;
		            for (Iterator cells = reportData.iterator(); cells.hasNext();) 
		              {
		        		if(cellIdx > headers.size()-1){
		        			cellIdx = 0;
		        			hssfRow = hs.createRow(++rowIdx);
		        		}
		                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
		                hssfCell.setCellValue((String) cells.next());
		                cellIdx = (short)(cellIdx + 1);
		               }
			        }
		        hw.setSheetName(0, "NEWCARD");//, HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
		        hw.write(bs);
		      } catch (Exception e) {	
		    	  BcwtsLogger.error(Util.getFormattedStackTrace(e));
		    	  throw new HPSFException(e.getMessage());            
		      }
		      return bs;			
		}
	

//	new method for new card report
	public static ByteArrayOutputStream generateNewCardReportExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {

 		String MY_NAME = ME + "generateNewCardReportExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hw1 = new HSSFWorkbook();
 	    HSSFSheet hs = hw1.createSheet("NEWCARD");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    BcwtNewCardReportDTO bcwtNewCardReportDTO = null;
 		try	{
		  //  HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hw1.createCellStyle();
			HSSFFont hssfFont = hw1.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		//    hr.setHeightInPoints(20f);
		    

		    HSSFRow hrmHeading = hs.createRow(rowIdx);
			HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
			hssfCellHeading.setCellStyle(cellStyle);
			
			hssfCellHeading.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
			
			rowIdx++;
			
			if(null != request.getAttribute("NC_DATE_RANGE")){
				HSSFRow hrmDateRange = hs.createRow(rowIdx);
				HSSFCell hssfCellDateRange = hrmDateRange.createCell((short)2);
				hssfCellDateRange.setCellStyle(cellStyle);
				
				hssfCellDateRange.setCellValue("Report Date Range: "+request.getAttribute("NC_DATE_RANGE"));
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;
			}
			
		    
			HSSFRow hr = hs.createRow(rowIdx);
			hr.setHeightInPoints(20f);
			
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
            }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	 bcwtNewCardReportDTO = (BcwtNewCardReportDTO) L.next();
	        	 reportData.add(bcwtNewCardReportDTO.getCompanyName());
	        	 reportData.add(bcwtNewCardReportDTO.getPartnerAdminName());
	        	 reportData.add(bcwtNewCardReportDTO.getEmployeeName());
	        	 reportData.add(bcwtNewCardReportDTO.getBreezecardSerialNo());
	        	 reportData.add(bcwtNewCardReportDTO.getBenefitName());
	        	 reportData.add(bcwtNewCardReportDTO.getEffectiveDate());

			}
	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
           		if(cellIdx > headers.size()-1){
           			cellIdx = 0;
           			hssfRow = hs.createRow(++rowIdx);
           		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                   hssfCell.setCellValue((String) cells.next());
                   cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hw1.setSheetName(0, "NEWCARD", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hw1.write(bs);
	      } catch (Exception e) {
	    	  throw new HPSFException(Util.getFormattedStackTrace(e));
	      }
	      return bs;
    }

	public static ByteArrayOutputStream generatePartnerMonthlyUsageSummaryReport(
			String reportHeading, List headers, List reportList, HttpServletRequest request) throws Exception{
		String MY_NAME = ME + "generatePartnerMonthlyUsageSummaryReport: ";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		HSSFWorkbook hw1 = new HSSFWorkbook();
		HSSFSheet hs = hw1.createSheet("USAGE SUMMARY REPORT");
		hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;
		ArrayList reportData = new ArrayList();
		PartnerMonthlyUsageReportDTO partnerMonthlyUsageReportDTO = null ;
		try {
			
			 HSSFCellStyle cellStyle = hw1.createCellStyle();
				HSSFFont hssfFont = hw1.createFont();
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			  //  cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    cellStyle.setFont(hssfFont);
			    rowIdx++;
		    HSSFRow hrmHeading = hs.createRow(rowIdx);
			HSSFCell hssfCellHeading = hrmHeading.createCell(cellIdx);
			hssfCellHeading.setCellStyle(cellStyle);
			
			hssfCellHeading.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
			
			rowIdx++;
		    
			    HSSFRow hrmDate = hs.createRow(rowIdx);
				HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
			//	hssfCellDate.setCellStyle(cellStyle);
				
				hssfCellDate.setCellValue("Report Generated on:"+Util.getDate());
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx++;
				
				 HSSFRow hrmDateRange = hs.createRow(rowIdx);
					HSSFCell hssfCellDateRange = hrmDateRange.createCell(cellIdx);
				//	hssfCellDate.setCellStyle(cellStyle);
					
					hssfCellDateRange.setCellValue("Report Date Range:"+request.getAttribute("MONTH_D_RANGE"));
					hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
					
					rowIdx++;
		    
		    if(null != request.getAttribute("NAME")){
				String name = (String)request.getAttribute("NAME");
			HSSFRow hrmCompany = hs.createRow(rowIdx);
			HSSFCell hssfCellCompany = hrmCompany.createCell(cellIdx);
		//	hssfCellCompany.setCellStyle(cellStyle);
			String company = " TMA/Company Name :  " +  name;
			hssfCellCompany.setCellValue(company);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx+1;
			}
		    
		    HSSFRow hr = hs.createRow(rowIdx);
		   
		    hr.setHeightInPoints(20f);
		    
		    
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
	        }
	        for(Iterator L = reportList.iterator(); L.hasNext();) {
	        	partnerMonthlyUsageReportDTO = (PartnerMonthlyUsageReportDTO)L.next();
	        	 reportData.add(partnerMonthlyUsageReportDTO.getCompanyName());
	        	 reportData.add(partnerMonthlyUsageReportDTO.getBreezeCardSerialNumber());
	        	 reportData.add(partnerMonthlyUsageReportDTO.getBillingMonth());
	        	 reportData.add(partnerMonthlyUsageReportDTO.getMonthlyUsage());

			}

	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
           		if(cellIdx > headers.size()-1){
           			cellIdx = 0;
           			hssfRow = hs.createRow(++rowIdx);
           		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                   hssfCell.setCellValue((String) cells.next());
                   cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hw1.setSheetName(0, "PARTNER", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hw1.write(baos);
	      } catch (Exception e) {
	    	  e.printStackTrace();
	    	  throw new HPSFException(e.getMessage());
	      }
	      return baos;
	}

//	new method for Hot List report
	public static ByteArrayOutputStream generateHotListReportExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {

 		String MY_NAME = ME + "generateHotListReportExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hw1 = new HSSFWorkbook();
 	    HSSFSheet hs = hw1.createSheet("HOTLIST");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    BcwtHotListReport bcwtHotListReport = null;
 		try	{
		//    HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hw1.createCellStyle();
			HSSFFont hssfFont = hw1.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
	//	    hr.setHeightInPoints(20f);
		   
		//    rowIdx++;
		    
		    HSSFRow hrmHeading = hs.createRow(rowIdx);
			HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
			hssfCellHeading.setCellStyle(cellStyle);
			
			hssfCellHeading.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
			
			rowIdx++;
			
			HSSFRow hr = hs.createRow(rowIdx);
			hr.setHeightInPoints(20f);
		    
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
	        }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	bcwtHotListReport = (BcwtHotListReport) L.next();
	        	 reportData.add(bcwtHotListReport.getFirstName());
	        	 reportData.add(bcwtHotListReport.getLastName());
	        	 reportData.add(bcwtHotListReport.getPointOfSale());
	        	 reportData.add(bcwtHotListReport.getHotListDate());
	        	 reportData.add(bcwtHotListReport.getCardSerialNumber());
	        	 reportData.add(bcwtHotListReport.getAdminName());

			}

	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
           		if(cellIdx > headers.size()-1){
           			cellIdx = 0;
           			hssfRow = hs.createRow(++rowIdx);
           		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                   hssfCell.setCellValue((String) cells.next());
                   cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hw1.setSheetName(0, "HOTLIST", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hw1.write(bs);
	      } catch (Exception e) {
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }

//	new method for partner new card report
	public static ByteArrayOutputStream generatePartnerNewCardReportExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {

 		String MY_NAME = ME + "generatePartnerNewCardReportExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hw1 = new HSSFWorkbook();
 	    HSSFSheet hs = hw1.createSheet("PARTNERNEWCARD");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    PartnerNewCardReportDTO partnerNewCardReportDTO = null;
 		try	{
		    
 			HSSFCellStyle cellStyle = hw1.createCellStyle();
			HSSFFont hssfFont = hw1.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		    HSSFRow hrmHeading = hs.createRow(rowIdx);
			HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
			hssfCellHeading.setCellStyle(cellStyle);
			
			hssfCellHeading.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
			
			rowIdx++;
		    
			    HSSFRow hrmDate = hs.createRow(rowIdx);
				HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
			//	hssfCellDate.setCellStyle(cellStyle);
				
				hssfCellDate.setCellValue("Report Generated On: "+Util.getDate());
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx++;
		    
				
				//NEW_D_RANGE
				
				 HSSFRow hrmDateRange = hs.createRow(rowIdx);
				HSSFCell hssfCellDateRange = hrmDateRange.createCell(cellIdx);
			//	hssfCellDate.setCellStyle(cellStyle);
				
				hssfCellDateRange.setCellValue("Report Date Range: "+request.getAttribute("NEW_D_RANGE"));
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx++;
				
				HSSFRow hrmCount = hs.createRow(rowIdx);
				HSSFCell hrmCountDate = hrmCount.createCell(cellIdx);
				hrmCountDate.setCellStyle(cellStyle);
				String newCount = (String)request.getAttribute("NEWCARDCOUNT");
				hrmCountDate.setCellValue("New Cards Added: "+newCount);
				rowIdx = rowIdx + 1;
				
		    if(null != request.getAttribute("NAME")){
				String name = (String)request.getAttribute("NAME");
			HSSFRow hrmCompany = hs.createRow(rowIdx);
			HSSFCell hssfCellCompany = hrmCompany.createCell(cellIdx);
		//	hssfCellCompany.setCellStyle(cellStyle);
			String company = " TMA/Company Name :  " +  name;
			hssfCellCompany.setCellValue(company);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx+1;
			}
		    rowIdx = rowIdx+1;
		    
		    HSSFRow hr = hs.createRow(rowIdx);
			
		    hr.setHeightInPoints(20f);
		    
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
            }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	partnerNewCardReportDTO = (PartnerNewCardReportDTO) L.next();
	        	 reportData.add(partnerNewCardReportDTO.getCompanyName());
	        	 reportData.add(partnerNewCardReportDTO.getCardSerialNumber());
	        	 reportData.add(partnerNewCardReportDTO.getBenefitName());
	        	 reportData.add(partnerNewCardReportDTO.getEffectiveDate());

			}
	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
           		if(cellIdx > headers.size()-1){
           			cellIdx = 0;
           			hssfRow = hs.createRow(++rowIdx);
           		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                   hssfCell.setCellValue((String) cells.next());
                   cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hw1.setSheetName(0, "PARTNERNEWCARD", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hw1.write(bs);
	      } catch (Exception e) {
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }
	
	
	public static ByteArrayOutputStream generateTMAHotlistReportExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {

 		String MY_NAME = ME + "generatePartnerNewCardReportExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hw1 = new HSSFWorkbook();
 	    HSSFSheet hs = hw1.createSheet("PARTNERNEWCARD");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    BcwtHotListReport bcwtHotListReport = null;
 		try	{
		    
 			HSSFCellStyle cellStyle = hw1.createCellStyle();
			HSSFFont hssfFont = hw1.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		    HSSFRow hrmHeading = hs.createRow(rowIdx);
			HSSFCell hssfCellHeading = hrmHeading.createCell(cellIdx);
			hssfCellHeading.setCellStyle(cellStyle);
			
			hssfCellHeading.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
			
			rowIdx++;
		    
			    HSSFRow hrmDate = hs.createRow(rowIdx);
				HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
			//	hssfCellDate.setCellStyle(cellStyle);
				
				hssfCellDate.setCellValue("Report Generated On: "+Util.getDate());
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx++;
				if(null != request.getAttribute("HOTLIST_D_RANGE")){
					HSSFRow hrmDateRange = hs.createRow(rowIdx);
					HSSFCell hssfCellDateRange = hrmDate.createCell(cellIdx);
				//	hssfCellDate.setCellStyle(cellStyle);
					
					hssfCellDateRange.setCellValue("Report Date Range: "+request.getAttribute("HOTLIST_D_RANGE"));
					hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
					
					rowIdx++;
				}
				
		    if(null != request.getAttribute("NAME")){
				String name = (String)request.getAttribute("NAME");
			HSSFRow hrmCompany = hs.createRow(rowIdx);
			HSSFCell hssfCellCompany = hrmCompany.createCell(cellIdx);
		//	hssfCellCompany.setCellStyle(cellStyle);
			String company = " TMA Name :  " +  name;
			hssfCellCompany.setCellValue(company);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx+1;
			}
		    rowIdx = rowIdx+1;
		    
		    HSSFRow hr = hs.createRow(rowIdx);
			
		    hr.setHeightInPoints(20f);
		    
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
            }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	 bcwtHotListReport = (BcwtHotListReport) L.next();
	        	 reportData.add(bcwtHotListReport.getFirstName());
	        	 reportData.add(bcwtHotListReport.getLastName());
	        	 reportData.add(bcwtHotListReport.getCardSerialNumber());
	        	 reportData.add(bcwtHotListReport.getCompanyName());
	        	 reportData.add(bcwtHotListReport.getHotListDate());
	        	 reportData.add(bcwtHotListReport.getAdminName());
	        	 

			}
	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
           		if(cellIdx > headers.size()-1){
           			cellIdx = 0;
           			hssfRow = hs.createRow(++rowIdx);
           		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                   hssfCell.setCellValue((String) cells.next());
                   cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hw1.setSheetName(0, "PARTNERNEWCARD", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hw1.write(bs);
	      } catch (Exception e) {
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }
	
	public static ByteArrayOutputStream generateTMAProductExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request,Map<String,Integer> productName)throws Exception {

 		String MY_NAME = ME + "generatePartnerNewCardReportExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hw1 = new HSSFWorkbook();
 	    HSSFSheet hs = hw1.createSheet("PARTNERNEWCARD");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    DisplayProductSummaryDTO bcwtHotListReport = null;
 		try	{
		    
 			HSSFCellStyle cellStyle = hw1.createCellStyle();
			HSSFFont hssfFont = hw1.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		    HSSFRow hrmHeading = hs.createRow(rowIdx);
			HSSFCell hssfCellHeading = hrmHeading.createCell(cellIdx);
			hssfCellHeading.setCellStyle(cellStyle);
			
			hssfCellHeading.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
			
			rowIdx++;
		    
			    HSSFRow hrmDate = hs.createRow(rowIdx);
				HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
			//	hssfCellDate.setCellStyle(cellStyle);
				
				hssfCellDate.setCellValue("Report Generated On: "+Util.getDate());
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx++;
				
				String dateRange = (String) request.getAttribute("DATE_RANGE");
		    	if(!Util.isBlankOrNull(dateRange) ){
				    HSSFRow hrmDateRange = hs.createRow(rowIdx);
					HSSFCell hssfCellDateRange = hrmDateRange.createCell(cellIdx);
				//	hssfCellDate.setCellStyle(cellStyle);
					
					hssfCellDateRange.setCellValue("Report Date Range: "+dateRange);
					hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
					
					rowIdx++;
		    	}
		    
		    if(null != request.getAttribute("NAME")){
				String name = (String)request.getAttribute("NAME");
			HSSFRow hrmCompany = hs.createRow(rowIdx);
			HSSFCell hssfCellCompany = hrmCompany.createCell(cellIdx);
		//	hssfCellCompany.setCellStyle(cellStyle);
			String company = " TMA Name :  " +  name;
			hssfCellCompany.setCellValue(company);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx+1;
			}
		    rowIdx = rowIdx+1;
		    
		    for (Map.Entry<String, Integer> entry : productName.entrySet()){
				 if(entry.getValue() != 0){
					 HSSFRow hry = hs.createRow(rowIdx); 
					 HSSFCell hssfCelly = hry.createCell(cellIdx);
					//	hssfCelly.setCellStyle(cellStyle1);
						hssfCelly.setCellValue(entry.getKey()+" Count:"+entry.getValue());
						rowIdx++;
					//	hs.addMergedRegion(new CellRangeAddress(rowIdx, cellIdx, rowIdx, 5));
				 }
			 }
		    
		    HSSFRow hr = hs.createRow(rowIdx);
			
		    hr.setHeightInPoints(20f);
		    
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
            }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	 bcwtHotListReport = (DisplayProductSummaryDTO) L.next();
	        	 reportData.add(bcwtHotListReport.getCompanyName());
	        	 reportData.add(bcwtHotListReport.getBenefitName());
	        	 reportData.add(bcwtHotListReport.getRegion());
	        	 reportData.add(String.valueOf(bcwtHotListReport.getCount()));
	        }
	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
           		if(cellIdx > headers.size()-1){
           			cellIdx = 0;
           			hssfRow = hs.createRow(++rowIdx);
           		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                   hssfCell.setCellValue((String) cells.next());
                   cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hw1.setSheetName(0, "PRODUCT SUMMARY REPORT", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hw1.write(bs);
	      } catch (Exception e) {
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }
	
	public static ByteArrayOutputStream generateTMAProductExcelDetail(
			String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {

 		String MY_NAME = ME + "generatePartnerNewCardReportExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hw1 = new HSSFWorkbook();
 	    HSSFSheet hs = hw1.createSheet("PARTNERNEWCARD");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    ProductDetailsReportDTO bcwtHotListReport = null;
 		try	{
		    
 			HSSFCellStyle cellStyle = hw1.createCellStyle();
			HSSFFont hssfFont = hw1.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		    HSSFRow hrmHeading = hs.createRow(rowIdx);
			HSSFCell hssfCellHeading = hrmHeading.createCell(cellIdx);
			hssfCellHeading.setCellStyle(cellStyle);
			
			hssfCellHeading.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
			
			rowIdx++;
		    
			    HSSFRow hrmDate = hs.createRow(rowIdx);
				HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
			//	hssfCellDate.setCellStyle(cellStyle);
				
				hssfCellDate.setCellValue("Report Generated On: "+Util.getDate());
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx++;
				
				String dateRange = (String) request.getAttribute("DATE_RANGE");
		    	if(!Util.isBlankOrNull(dateRange) ){
				    HSSFRow hrmDateRange = hs.createRow(rowIdx);
					HSSFCell hssfCellDateRange = hrmDateRange.createCell(cellIdx);
				//	hssfCellDate.setCellStyle(cellStyle);
					
					hssfCellDateRange.setCellValue("Report Date Range: "+dateRange);
					hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
					
					rowIdx++;
		    	}
		    
		    if(null != request.getAttribute("NAME")){
				String name = (String)request.getAttribute("NAME");
			HSSFRow hrmCompany = hs.createRow(rowIdx);
			HSSFCell hssfCellCompany = hrmCompany.createCell(cellIdx);
		//	hssfCellCompany.setCellStyle(cellStyle);
			String company = " TMA Name :  " +  name;
			hssfCellCompany.setCellValue(company);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx+1;
			}
		    rowIdx = rowIdx+1;
		    
		    HSSFRow hr = hs.createRow(rowIdx);
			
		    hr.setHeightInPoints(20f);
		    
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
            }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	 bcwtHotListReport = (ProductDetailsReportDTO) L.next();
	        	 reportData.add(bcwtHotListReport.getCompanyname());
	        	 reportData.add(bcwtHotListReport.getFirstname());
	        	 reportData.add(bcwtHotListReport.getLastname());
	        	 reportData.add(bcwtHotListReport.getMemberid());
	        	 reportData.add(bcwtHotListReport.getBreezecard());
	        	 reportData.add(bcwtHotListReport.getBenefitname());
	        	 reportData.add(bcwtHotListReport.getRegion());
	        	 reportData.add(String.valueOf(bcwtHotListReport.getCount()));
	        	 reportData.add(bcwtHotListReport.getTransactiontype());
	        	 

			}
	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
           		if(cellIdx > headers.size()-1){
           			cellIdx = 0;
           			hssfRow = hs.createRow(++rowIdx);
           		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                   hssfCell.setCellValue((String) cells.next());
                   cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hw1.setSheetName(0, "PRODUCT DETAIL REPORT", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hw1.write(bs);
	      } catch (Exception e) {
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }
	
	public static ByteArrayOutputStream generateTMAIssueReportExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {

 		String MY_NAME = ME + "generatePartnerNewCardReportExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hw1 = new HSSFWorkbook();
 	    HSSFSheet hs = hw1.createSheet("PARTNERNEWCARD");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	 
 		try	{
		    
 			HSSFCellStyle cellStyle = hw1.createCellStyle();
			HSSFFont hssfFont = hw1.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		    HSSFRow hrmHeading = hs.createRow(rowIdx);
			HSSFCell hssfCellHeading = hrmHeading.createCell(cellIdx);
			hssfCellHeading.setCellStyle(cellStyle);
			
			hssfCellHeading.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
			
			rowIdx++;
		    
			    HSSFRow hrmDate = hs.createRow(rowIdx);
				HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
			//	hssfCellDate.setCellStyle(cellStyle);
				
				hssfCellDate.setCellValue("Report Generated On: "+Util.getDate());
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx++;
		    
				String dateRange = (String) request.getAttribute("ISSUE_D_RANGE");
		    	
		    	if(!Util.isBlankOrNull(dateRange)){
			    HSSFRow hrmDateRange = hs.createRow(rowIdx);
				HSSFCell hssfCellDateRange = hrmDateRange.createCell(cellIdx);
			//	hssfCellDate.setCellStyle(cellStyle);
				
				hssfCellDateRange.setCellValue("Report Date Range: "+dateRange);
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx++;	
		    	}
		   
		    rowIdx = rowIdx+1;
		    
		    HSSFRow hr = hs.createRow(rowIdx);
			
		    hr.setHeightInPoints(20f);
		    
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
            }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	BcwtsPartnerIssueDTO bcwtsPartnerIssueDTO =(BcwtsPartnerIssueDTO)  L.next();
	        	 reportData.add(bcwtsPartnerIssueDTO.getSerialnumber());
	        	 reportData.add(bcwtsPartnerIssueDTO.getMemberid());
	        	 reportData.add(bcwtsPartnerIssueDTO.getCompanyname());
	        	 reportData.add(bcwtsPartnerIssueDTO.getRegion());
	        	 reportData.add(bcwtsPartnerIssueDTO.getCreatedby());
	        	 reportData.add(bcwtsPartnerIssueDTO.getCreationdate().toString());
	        	 reportData.add(bcwtsPartnerIssueDTO.getIssuestatus());
	        	 reportData.add(Util.removeHtmlFormatting(bcwtsPartnerIssueDTO.getIssuedescription()));
	        	 
	        	 reportData.add(bcwtsPartnerIssueDTO.getResolution());
	        	 reportData.add(bcwtsPartnerIssueDTO.getAdminName());
	        	 

			}
	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
           		if(cellIdx > headers.size()-1){
           			cellIdx = 0;
           			hssfRow = hs.createRow(++rowIdx);
           		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                   hssfCell.setCellValue((String) cells.next());
                   cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hw1.setSheetName(0, "PARTNERNEWCARD", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hw1.write(bs);
	      } catch (Exception e) {
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }
	
	public static ByteArrayOutputStream generateActiveBenefitExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request,Map<String,Integer> productName)throws Exception {

 		String MY_NAME = ME + "generateActiveBenefitExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hwb = new HSSFWorkbook();
 	    HSSFSheet hs = hwb.createSheet("ACTIVEBENEFIT");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    int countMonthValue = 0;
	    int countYearValue = 0;
	    String companyName = "";
	    String currentDate = "";

 		try	{

 			if (null != request.getAttribute("COMPANY_NAME_REPORT")) {
  		      companyName = request.getAttribute("COMPANY_NAME_REPORT").toString();
 			}

 			currentDate = Util.getCurrentDate();

 			 for(Iterator L = reportList. iterator();L.hasNext() ;) {
 	        	PartnerReportSearchDTO partnerReportSearchDTO = (PartnerReportSearchDTO) L.next();
 	        	countMonthValue = partnerReportSearchDTO.getCountMonth();
 	        	countYearValue = partnerReportSearchDTO.getCountYear();
 			}
 			HSSFCellStyle cellStyle1 = hwb.createCellStyle();
 			HSSFFont hssfFont1 = hwb.createFont();
		    cellStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		   // hssfFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle1.setFont(hssfFont1);

		    HSSFCellStyle cellStyle2 = hwb.createCellStyle();
 			HSSFFont hssfFont2 = hwb.createFont();
 			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		   // hssfFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
 			hssfFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
 			cellStyle2.setFont(hssfFont2);

			HSSFRow reportHead = hs.createRow(rowIdx);
			HSSFCell cellReportHead = reportHead.createCell(cellIdx);
			cellReportHead.setCellStyle(cellStyle2);
			cellReportHead.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx+1;

			if(null != request.getAttribute("NAME")){
				String name = (String)request.getAttribute("NAME");
			HSSFRow hrmCompany = hs.createRow(rowIdx);
			HSSFCell hssfCellCompany = hrmCompany.createCell(cellIdx);
			hssfCellCompany.setCellStyle(cellStyle1);
			String company = " TMA/Company Name :  " +  name;
			hssfCellCompany.setCellValue(company);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx+1;
			}
			
			HSSFRow hrmDate = hs.createRow(rowIdx);
			HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
			hssfCellDate.setCellStyle(cellStyle1);
			String dateGenerated = "  Report Generated Date :  " +  currentDate;
			hssfCellDate.setCellValue(dateGenerated);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx + 1;
			if(null != productName){
			 for (Map.Entry<String, Integer> entry : productName.entrySet()){
				 if(entry.getValue() != 0){
					 HSSFRow hry = hs.createRow(rowIdx); 
					 HSSFCell hssfCelly = hry.createCell(cellIdx);
						hssfCelly.setCellStyle(cellStyle1);
						hssfCelly.setCellValue(entry.getKey()+" Count:"+entry.getValue());
						rowIdx++;
					//	hs.addMergedRegion(new CellRangeAddress(rowIdx, cellIdx, rowIdx, 5));
				 }
			 }

			}


			rowIdx = rowIdx+1;
		   /* HSSFRow hry = hs.createRow(rowIdx);
 			HSSFCell hssfCelly = hry.createCell(cellIdx);
 			hssfCelly.setCellStyle(cellStyle1);
 			String countYear = "Active Annual Pass Count : " +  countYearValue;
 			hssfCelly.setCellValue(countYear);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
 			rowIdx = rowIdx+1;

			HSSFRow hrm = hs.createRow(rowIdx);
			HSSFCell hssfCellm = hrm.createCell(cellIdx);
			hssfCellm.setCellStyle(cellStyle1);
			String countMonth = " Active Monthly Pass Count :  " +  countMonthValue;
			hssfCellm.setCellValue(countMonth);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
			*/
			
			
		/*
		    HSSFRow hry = hs.createRow(rowIdx);
 			HSSFCell hssfCelly = hry.createCell(cellIdx);
 			hssfCelly.setCellStyle(cellStyle1);
 			String countYear = "Active Monthly Pass Count : " +  countMonthValue;
 			hssfCelly.setCellValue(countYear);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
 			rowIdx = rowIdx+1;
 		    HSSFRow hrm = hs.createRow(rowIdx);
			HSSFCell hssfCellm = hrm.createCell(cellIdx);
			hssfCellm.setCellStyle(cellStyle1);
			String countMonth = " Active Annual Pass Count :  " +  countYearValue;
			hssfCellm.setCellValue(countMonth);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5)); */
			
			
			HSSFRow hr = hs.createRow(++rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			HSSFFont hssfFont = hwb.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		    hr.setHeightInPoints(20f);
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
            }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	PartnerReportSearchDTO partnerReportSearchDTO = (PartnerReportSearchDTO) L.next();
	        	 reportData.add(partnerReportSearchDTO.getCompanyName());
	        	 reportData.add(partnerReportSearchDTO.getFirstName());
	        	 reportData.add(partnerReportSearchDTO.getLastName());
				 reportData.add(partnerReportSearchDTO.getCustomerMemberId());
				 reportData.add(partnerReportSearchDTO.getCardSerialNo());
				 reportData.add(partnerReportSearchDTO.getBenefitType());
			}
	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
            		if(cellIdx > headers.size()-1){
            			cellIdx = 0;
            			hssfRow = hs.createRow(++rowIdx);
            		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                    hssfCell.setCellValue((String) cells.next());
                    cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hwb.setSheetName(0, "ACTIVEBENEFIT", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hwb.write(bs);
	      } catch (Exception e) {
	    	  e.printStackTrace();
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }

	//------------------------------Sunil Code--------------------------------------------------

	public static ByteArrayOutputStream generateActiveBenefitExcel_New(
			String reportHeading,List headers,List reportList,HttpServletRequest request, String total, String tmaname)throws Exception {


 		String MY_NAME = ME + "generateActiveBenefitExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hwb = new HSSFWorkbook();
 	    HSSFSheet hs = hwb.createSheet("ACTIVEBENEFIT");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    int countMonthValue = 0;
	    int countYearValue = 0;
	    String companyName = "";
	    String currentDate = "";

 		try	{

 			if (null != request.getAttribute("COMPANY_NAME_REPORT")) {
  		      companyName = request.getAttribute("COMPANY_NAME_REPORT").toString();
 			}

 			currentDate = Util.getCurrentDate();

 			 for(Iterator L = reportList. iterator();L.hasNext() ;) {
 	        	PartnerReportSearchDTO partnerReportSearchDTO = (PartnerReportSearchDTO) L.next();
 	        	countMonthValue = partnerReportSearchDTO.getCountMonth();
 	        	countYearValue = partnerReportSearchDTO.getCountYear();
 			}
 			HSSFCellStyle cellStyle1 = hwb.createCellStyle();
 			HSSFFont hssfFont1 = hwb.createFont();
		    cellStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		   // hssfFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle1.setFont(hssfFont1);

		    HSSFCellStyle cellStyle2 = hwb.createCellStyle();
 			HSSFFont hssfFont2 = hwb.createFont();
 			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		   // hssfFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
 			hssfFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
 			cellStyle2.setFont(hssfFont2);

			HSSFRow reportHead = hs.createRow(rowIdx);
			HSSFCell cellReportHead = reportHead.createCell(cellIdx);
			cellReportHead.setCellStyle(cellStyle2);
			cellReportHead.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx+1;
			System.out.println("After rowIdx");
			/*HSSFRow hrmCompany = hs.createRow(rowIdx);
			HSSFCell hssfCellCompany = hrmCompany.createCell(cellIdx);
			hssfCellCompany.setCellStyle(cellStyle1);
			String company = " Company Name :  " +  companyName;
			hssfCellCompany.setCellValue(company);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx+1;
			*/
			HSSFRow hrmDate = hs.createRow(rowIdx);
			HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
			hssfCellDate.setCellStyle(cellStyle1);
			String dateGenerated = "Tma Name :"+tmaname+"     Annual Pass Count:"+total+"  Report Generated Date :  " +  currentDate;
			hssfCellDate.setCellValue(dateGenerated);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));



			rowIdx = rowIdx+1;
			/*
		    HSSFRow hry = hs.createRow(rowIdx);
 			HSSFCell hssfCelly = hry.createCell(cellIdx);
 			hssfCelly.setCellStyle(cellStyle1);
 			String countYear = "Active Annual Pass Count : " +  total;
 			hssfCelly.setCellValue(countYear);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
 			rowIdx = rowIdx+1;

			HSSFRow hrm = hs.createRow(rowIdx);
			HSSFCell hssfCellm = hrm.createCell(cellIdx);
			hssfCellm.setCellStyle(cellStyle1);
			String countMonth = " Active Monthly Pass Count :  " +  countMonthValue;
			hssfCellm.setCellValue(countMonth);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
			*/

			HSSFRow hr = hs.createRow(++rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			HSSFFont hssfFont = hwb.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		    hr.setHeightInPoints(20f);




		    //cellIdx = (short) (cellIdx+1);
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              if(null != headcell)
	              hssfCell.setCellValue(headcell.toString());
            }



	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	PartnerReportSearchDTO partnerReportSearchDTO = (PartnerReportSearchDTO) L.next();
	        	 reportData.add(partnerReportSearchDTO.getCompanyName());
				 reportData.add(partnerReportSearchDTO.getCardcount()+"");
				 //reportData.add(partnerReportSearchDTO.getCardSerialNo());
				 //reportData.add(partnerReportSearchDTO.getBenefitType());
			}




	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
            		if(cellIdx > headers.size()-1){
            			cellIdx = 0;
            			hssfRow = hs.createRow(++rowIdx);
            		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                    hssfCell.setCellValue((String) cells.next());
                    cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hwb.setSheetName(0, "ACTIVEBENEFIT", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hwb.write(bs);
	      } catch (Exception e) {
	    	  e.printStackTrace();
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }

	//-------------------------------------------------------------------------------------------


	//excelgenerator for Partner Upcoming Monthly Report

	public static ByteArrayOutputStream generatePartnerUpcomingMonthlyReportExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request,ReportForm reportForm,Map<String,Integer> productName)throws Exception {

 		String MY_NAME = ME + "generatePartnerUpcomingMonthlyReportExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hwu = new HSSFWorkbook();
 	    HSSFSheet hs = hwu.createSheet("PartnerUpcomingMonthlyReport");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    UpCommingMonthBenefitDTO orderReportDTO = null;
	    String currentDate = null;
 		try	{
		  

		    HSSFCellStyle cellStyle1 = hwu.createCellStyle();
			HSSFFont hssfFont1 = hwu.createFont();
		    cellStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		    HSSFCellStyle cellStyle2 = hwu.createCellStyle();
 			HSSFFont hssfFont2 = hwu.createFont();
 			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		   // hssfFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
 			hssfFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
 			cellStyle2.setFont(hssfFont2);
			
		    HSSFRow reportHead = hs.createRow(rowIdx);
			HSSFCell cellReportHead = reportHead.createCell(cellIdx);
			cellReportHead.setCellStyle(cellStyle2);
			cellReportHead.setCellValue(reportHeading);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

			rowIdx = rowIdx+1;
		    
		    
		    if(null != request.getAttribute("NAME")){
	    		String name = (String)request.getAttribute("NAME");
	    		rowIdx = rowIdx+1;
	 			HSSFRow tmaName = hs.createRow(rowIdx);
	 			HSSFCell tmaNameCell = tmaName.createCell(cellIdx);
	 			tmaNameCell.setCellStyle(cellStyle1);
	 			String dateGenerated = " TMA/Company Name :  " +  name;
	 			tmaNameCell.setCellValue(dateGenerated);
	 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
			}
		    currentDate = Util.getCurrentDate();
			rowIdx = rowIdx+1;
 			HSSFRow hrmDate = hs.createRow(rowIdx);
 			HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
 			hssfCellDate.setCellStyle(cellStyle1);
 			String dateGenerated = " Report Generated Date :  " +  currentDate;
 			hssfCellDate.setCellValue(dateGenerated);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

 			rowIdx = rowIdx+1;
 			 String actSize = (String)request.getAttribute("AccountCount");
 			HSSFRow totalAccountCards = hs.createRow(rowIdx);
 			HSSFCell totalAccountCardsCell = totalAccountCards.createCell(cellIdx);
 			totalAccountCardsCell.setCellStyle(cellStyle1);
 			//String dateGenerated = " Report Generated Date :  " +  currentDate;
 			totalAccountCardsCell.setCellValue("Total Card for the Account :"+actSize);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

 			rowIdx = rowIdx+1;
 			
 			 String yesSize = (String)request.getAttribute("YesINQueue");
 			HSSFRow totalActiveAccountCards = hs.createRow(rowIdx);
 			HSSFCell totalActiveAccountCardsCell = totalActiveAccountCards.createCell(cellIdx);
 			totalAccountCardsCell.setCellStyle(cellStyle1);
 			//String dateGenerated = " Report Generated Date :  " +  currentDate;
 			totalActiveAccountCardsCell.setCellValue("Total Number of Benefits in the Queue for Activation:"+yesSize);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

 			rowIdx = rowIdx+1;
 			
 			 String size = (String)request.getAttribute("NoINQueue");
             HSSFRow totalDeActiveAccountCards = hs.createRow(rowIdx);
  			HSSFCell totalDeActiveAccountCardsCell = totalDeActiveAccountCards.createCell(cellIdx);
  			totalDeActiveAccountCardsCell.setCellStyle(cellStyle1);
  			//String dateGenerated = " Report Generated Date :  " +  currentDate;
  			totalDeActiveAccountCardsCell.setCellValue("Total Number of Benefits in the Queue for Dectivation:"+size);
  			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
  			rowIdx = rowIdx+1;
 			
  			 String newSize = (String)request.getAttribute("NewCardCount");
 			HSSFRow totalNewAccountCards = hs.createRow(rowIdx);
 			HSSFCell totalNewAccountCardsCell = totalNewAccountCards.createCell(cellIdx);
 			totalNewAccountCardsCell.setCellStyle(cellStyle1);
 			//String dateGenerated = " Report Generated Date :  " +  currentDate;
 			totalNewAccountCardsCell.setCellValue("Total Number of New Cards in the Queue:"+newSize);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));

 			rowIdx = rowIdx+1;
 			
 			int totalCount = 0;
			 for (Map.Entry<String, Integer> entry : productName.entrySet()){
				 if(entry.getValue() != 0){
					 HSSFRow hry = hs.createRow(rowIdx); 
					 HSSFCell hssfCelly = hry.createCell(cellIdx);
						hssfCelly.setCellStyle(cellStyle1);
						hssfCelly.setCellValue(entry.getKey()+" Count:"+entry.getValue());
						rowIdx++;
						totalCount = totalCount + entry.getValue();
					//	hs.addMergedRegion(new CellRangeAddress(rowIdx, cellIdx, rowIdx, 5));
				 }
			 }
			
			HSSFRow totalBenefitCount = hs.createRow(rowIdx);
			HSSFCell totalBenefitCountCell = totalBenefitCount.createCell(cellIdx);
			totalBenefitCountCell.setCellStyle(cellStyle1);
			//String dateGenerated = " Report Generated Date :  " +  currentDate;
			totalBenefitCountCell.setCellValue("Total number of benefits for next month:"+totalCount);
			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
			
			rowIdx = rowIdx+1;
 			
 			  HSSFRow hr = hs.createRow(rowIdx);
 				HSSFCellStyle cellStyle = hwu.createCellStyle();
 				HSSFFont hssfFont = hwu.createFont();
 			    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
 			    cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
 			    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
 			    cellStyle.setFont(hssfFont);
 			    hr.setHeightInPoints(20f);
 			
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
            }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	orderReportDTO = (UpCommingMonthBenefitDTO) L.next();
	        	 reportData.add(orderReportDTO.getCompanyName());
	        	 reportData.add(orderReportDTO.getCardSerialNumber());
	        	 reportData.add(orderReportDTO.getFirstName());
	        	 reportData.add(orderReportDTO.getLastName());
	        	 reportData.add(orderReportDTO.getMemberId());
	        	 reportData.add(orderReportDTO.getBenefitName());
	        	 reportData.add(orderReportDTO.getIsBenefitActivated());
	        	if(null != orderReportDTO.getDateModified())
	        	 reportData.add(orderReportDTO.getDateModified().toString());
	        	else
	        	 reportData.add("NA");
	        	 reportData.add(orderReportDTO.getRegion());

			}
	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
	            	if(cellIdx > headers.size()-1){
	          			cellIdx = 0;
	          			hssfRow = hs.createRow(++rowIdx);
	            	}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                    hssfCell.setCellValue((String) cells.next());
                    cellIdx = (short)(cellIdx + 1);
	              }
		        }
	        hwu.setSheetName(0, "PARTNERUPCOMINGMONTHLYREPORT", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hwu.write(bs);
	      } catch (Exception e) {
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }

	/***************** Partner Reports - General Ledger - Starts here **********************/
	public static ByteArrayOutputStream generateGeneralLedgerExcel(
			String reportHeading, List headers, List reportList, HttpServletRequest request)
			throws Exception {
		String MY_NAME = ME + "generateGeneralLedgerExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet hs = hwb.createSheet(Constants.GENERAL_LEDGER_REPORT_FILENAME);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;

		try {
			ArrayList ReportData = new ArrayList();
		//	HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyle.setWrapText(true);
		//	hr.setHeightInPoints(15f);
			hs.setDefaultColumnWidth((short)18);

			 HSSFRow hrmHeading = hs.createRow(rowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyle);
				
				hssfCellHeading.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;

				if(null != request.getAttribute("GL_DATE_RANGE")){
					    HSSFRow hrmDateRange = hs.createRow(rowIdx);
						HSSFCell hssfDateRange = hrmDateRange.createCell((short)2);
						hssfDateRange.setCellStyle(cellStyle);
						
						hssfDateRange.setCellValue("Report Date Range: "+request.getAttribute("GL_DATE_RANGE"));
					//	hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
						
						rowIdx++;	
					
				}
				
				HSSFRow hr = hs.createRow(rowIdx);
				hr.setHeightInPoints(20f);
			
			
			for (Iterator cells = headers.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hr.createCell(cellIdx++);
				hssfCell.setCellStyle(cellStyle);
				hssfCell.setCellValue((String) cells.next());
			}
			for (Iterator L = reportList.iterator(); L.hasNext();) {
				BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) L.next();
				ReportData.add(bcwtReportDTO.getAccountNumber());
				ReportData.add(bcwtReportDTO.getOrderRequestId());
				ReportData.add(bcwtReportDTO.getOrderType());
				ReportData.add(bcwtReportDTO.getCustomerName());
				ReportData.add(bcwtReportDTO.getApprovalCode());
				ReportData.add(bcwtReportDTO.getTransactionDate());
				ReportData.add(bcwtReportDTO.getProducts());
				ReportData.add(bcwtReportDTO.getPrice());
				ReportData.add(bcwtReportDTO.getOrderQty());
				ReportData.add(bcwtReportDTO.getAmount());
			}
			rowIdx ++;
			if(ReportData.size() > 0) {
				HSSFRow hssfRow = hs.createRow(rowIdx++);
				cellIdx = 0;
				for (Iterator cells = ReportData.iterator(); cells.hasNext();) {
					if(cellIdx >= headers.size()){
            			hssfRow = hs.createRow(rowIdx++);
            			cellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
					hssfCell.setCellValue((String) cells.next());
				}
	         }
			hwb.setSheetName(0, Constants.BCWTS_GENERAL_LEDGER_REPORT,
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
			hwb.write(bs);
		} catch (Exception e) {
			throw new HPSFException(e.getMessage());
		}
		return bs;
	}
	/***************** Partner Reports - General Ledger - Ends here **********************/

	/***************** OF - Starts here **********************/

	public static ByteArrayOutputStream generateOrderSummaryExcel(
			String reportHeading, List headers, List reportList)
			throws Exception {
		String MY_NAME = ME + "generateOrderSummaryExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet hs = hwb.createSheet("ORDERSUMMARYREPORT");
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;

		try {
			ArrayList ReportData = new ArrayList();
	//		HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyle.setWrapText(true);
		//	hr.setHeightInPoints(15f);
			hs.setDefaultColumnWidth((short)18);

			 HSSFRow hrmHeading = hs.createRow(rowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyle);
				
				hssfCellHeading.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;


			    HSSFRow hrmDate = hs.createRow(rowIdx);
				HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
			//	hssfCellDate.setCellStyle(cellStyle);
				
				hssfCellDate.setCellValue("Report Generated On: "+Util.getDate());
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx++;
				
				
				HSSFRow hr = hs.createRow(rowIdx);
				hr.setHeightInPoints(20f);
				
				
			
			for (Iterator cells = headers.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hr.createCell(cellIdx++);
				hssfCell.setCellStyle(cellStyle);
				hssfCell.setCellValue((String) cells.next());
			}
			for (Iterator L = reportList.iterator(); L.hasNext();) {
				BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) L.next();
				ReportData.add(bcwtReportDTO.getOrderType());
				ReportData.add(bcwtReportDTO.getNoOfOrders());
				ReportData.add(bcwtReportDTO.getOrderQty());
				ReportData.add(bcwtReportDTO.getOrderValue());
				ReportData.add(bcwtReportDTO.getShippingAmount());
				ReportData.add(bcwtReportDTO.getTotal());
			}
			rowIdx = 1;
			if(ReportData.size() > 0) {
				HSSFRow hssfRow = hs.createRow(rowIdx++);
				cellIdx = 0;
				for (Iterator cells = ReportData.iterator(); cells.hasNext();) {
					if(cellIdx >= headers.size()){
            			hssfRow = hs.createRow(rowIdx++);
            			cellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
					hssfCell.setCellValue((String) cells.next());
				}
	         }

			hwb.setSheetName(0, "ORDERSUMMARYREPORT",
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
			hwb.write(bs);
		} catch (Exception e) {
			throw new HPSFException(e.getMessage());
		}
		return bs;
	}


	public static ByteArrayOutputStream generateOutstandingOrdersExcel(
			String reportHeading, List headers, List reportList)
			throws Exception {
		String MY_NAME = ME + "generateOutstandingOrdersExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet hs = hwb.createSheet(Constants.OUTSTANDING_ORDERS_REPORT);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;

		try {
			ArrayList ReportData = new ArrayList();
		//	HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyle.setWrapText(true);
		//	hr.setHeightInPoints(15f);
			hs.setDefaultColumnWidth((short)18);

			 HSSFRow hrmHeading = hs.createRow(rowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyle);
				
				hssfCellHeading.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;

				HSSFRow hr = hs.createRow(rowIdx);
				hr.setHeightInPoints(20f);
			
			for (Iterator cells = headers.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hr.createCell(cellIdx++);
				hssfCell.setCellStyle(cellStyle);
				hssfCell.setCellValue((String) cells.next());
			}
			for (Iterator L = reportList.iterator(); L.hasNext();) {
				BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) L.next();
				ReportData.add(bcwtReportDTO.getOrderRequestId());
				ReportData.add(bcwtReportDTO.getCardSerialNo());
				ReportData.add(bcwtReportDTO.getFirstName());
				ReportData.add(bcwtReportDTO.getLastName());
				ReportData.add(bcwtReportDTO.getEmail());
				ReportData.add(bcwtReportDTO.getPhoneNumber());
				//ReportData.add(bcwtReportDTO.getZip());
				ReportData.add(bcwtReportDTO.getOrderDate());
				ReportData.add(bcwtReportDTO.getOrderStatus());
				ReportData.add(bcwtReportDTO.getOrderType());
			}
			rowIdx = 1;
			if(ReportData.size() > 0) {
				HSSFRow hssfRow = hs.createRow(rowIdx++);
				cellIdx = 0;
				for (Iterator cells = ReportData.iterator(); cells.hasNext();) {
					if(cellIdx >= headers.size()){
            			hssfRow = hs.createRow(rowIdx++);
            			cellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
					hssfCell.setCellValue((String) cells.next());
				}
	         }
			hwb.setSheetName(0, Constants.OUTSTANDING_ORDERS_REPORT,
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
			hwb.write(bs);
		} catch (Exception e) {
			throw new HPSFException(e.getMessage());
		}
		return bs;
	}

	public static ByteArrayOutputStream generateQualityDetailsExcel(
			String reportHeading, List headers, List reportList)
			throws Exception {
		String MY_NAME = ME + "generateQualityDetailsExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet hs = hwb.createSheet(Constants.QUALITYDETAILED_REPORT);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;

		try {
			ArrayList ReportData = new ArrayList();
		//	HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyle.setWrapText(true);
		//	hr.setHeightInPoints(15f);
			hs.setDefaultColumnWidth((short)18);

			 HSSFRow hrmHeading = hs.createRow(rowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyle);
				
				hssfCellHeading.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;

				HSSFRow hr = hs.createRow(rowIdx);
				hr.setHeightInPoints(20f);	
			
			for (Iterator cells = headers.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hr.createCell(cellIdx++);
				hssfCell.setCellStyle(cellStyle);
				hssfCell.setCellValue((String) cells.next());
			}
			for (Iterator L = reportList.iterator(); L.hasNext();) {
				BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) L.next();
				ReportData.add(bcwtReportDTO.getOrderRequestId());
				ReportData.add(bcwtReportDTO.getFirstName());
				ReportData.add(bcwtReportDTO.getLastName());
				ReportData.add(bcwtReportDTO.getProcessedDate());
				ReportData.add(bcwtReportDTO.getOrderType());
			}
			rowIdx = 1;
			if(ReportData.size() > 0) {
				HSSFRow hssfRow = hs.createRow(rowIdx++);
				cellIdx = 0;
				for (Iterator cells = ReportData.iterator(); cells.hasNext();) {
					if(cellIdx >= headers.size()){
            			hssfRow = hs.createRow(rowIdx++);
            			cellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
					hssfCell.setCellValue((String) cells.next());
				}
	         }
			hwb.setSheetName(0, Constants.QUALITYDETAILED_REPORT,
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
			hwb.write(bs);
		} catch (Exception e) {
			throw new HPSFException(e.getMessage());
		}
		return bs;
	}

	public static ByteArrayOutputStream generateSalesMetricsExcel(
			String reportHeading, List headers, List reportList)
			throws Exception {
		String MY_NAME = ME + "generateSalesMetricsExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet hs = hwb.createSheet(Constants.SALES_METRICS_REPORT);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;

		try {
			ArrayList ReportData = new ArrayList();
		//	HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyle.setWrapText(true);
		//	hr.setHeightInPoints(15f);
			hs.setDefaultColumnWidth((short)18);

			 HSSFRow hrmHeading = hs.createRow(rowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyle);
				
				hssfCellHeading.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;

				HSSFRow hr = hs.createRow(rowIdx);
				hr.setHeightInPoints(20f);	
			
			for (Iterator cells = headers.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hr.createCell(cellIdx++);
				hssfCell.setCellStyle(cellStyle);
				hssfCell.setCellValue((String) cells.next());
			}
			for (Iterator L = reportList.iterator(); L.hasNext();) {
				BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) L.next();
				ReportData.add(bcwtReportDTO.getZip());
				ReportData.add(bcwtReportDTO.getNoOfCardsSold());
			}
			rowIdx ++;
			if(ReportData.size() > 0) {
				HSSFRow hssfRow = hs.createRow(rowIdx++);
				cellIdx = 0;
				for (Iterator cells = ReportData.iterator(); cells.hasNext();) {
					if(cellIdx >= headers.size()){
            			hssfRow = hs.createRow(rowIdx++);
            			cellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
					hssfCell.setCellValue((String) cells.next());
				}
	         }
			hwb.setSheetName(0, Constants.SALES_METRICS_REPORT,
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
			hwb.write(bs);
		} catch (Exception e) {
			throw new HPSFException(e.getMessage());
		}
		return bs;
	}

	public static ByteArrayOutputStream generateQualityAssuranceSummaryExcel(
			String reportHeading, List headers, List reportList)
			throws Exception {
		String MY_NAME = ME + "generateQualityAssuranceSummaryExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet hs = hwb.createSheet(Constants.QUALITY_ASSURANCE_SUMMARY_REPORT);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;

		try {
			ArrayList ReportData = new ArrayList();
//			HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyle.setWrapText(true);
//			hr.setHeightInPoints(15f);
			hs.setDefaultColumnWidth((short)18);

			 HSSFRow hrmHeading = hs.createRow(rowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyle);
				
				hssfCellHeading.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;

				HSSFRow hr = hs.createRow(rowIdx);
				hr.setHeightInPoints(20f);	
			
			
			for (Iterator cells = headers.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hr.createCell(cellIdx++);
				hssfCell.setCellStyle(cellStyle);
				hssfCell.setCellValue((String) cells.next());
			}
			for (Iterator L = reportList.iterator(); L.hasNext();) {
				BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) L.next();
				ReportData.add(bcwtReportDTO.getNoOfPassed());
				ReportData.add(bcwtReportDTO.getNoOfFailed());
			}
			rowIdx ++;
			if(ReportData.size() > 0) {
				HSSFRow hssfRow = hs.createRow(rowIdx++);
				cellIdx = 0;
				for (Iterator cells = ReportData.iterator(); cells.hasNext();) {
					if(cellIdx >= headers.size()){
            			hssfRow = hs.createRow(rowIdx++);
            			cellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
					hssfCell.setCellValue((String) cells.next());
				}
	         }
			hwb.setSheetName(0, Constants.QUALITY_ASSURANCE_SUMMARY_REPORT,
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
			hwb.write(bs);
		} catch (Exception e) {
			throw new HPSFException(e.getMessage());
		}
		return bs;
	}

	public static ByteArrayOutputStream generateReturnedOrdersExcel(
			String reportHeading, List headers, List reportList)
			throws Exception {
		String MY_NAME = ME + "generateReturnedOrdersExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet hs = hwb.createSheet(Constants.RETURNED_ORDERS_REPORT);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;

		try {
			ArrayList ReportData = new ArrayList();
		//	HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyle.setWrapText(true);
	//		hr.setHeightInPoints(15f);
			hs.setDefaultColumnWidth((short)18);

			 HSSFRow hrmHeading = hs.createRow(rowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyle);
				
				hssfCellHeading.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;

				HSSFRow hr = hs.createRow(rowIdx);
				hr.setHeightInPoints(20f);
			
			for (Iterator cells = headers.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hr.createCell(cellIdx++);
				hssfCell.setCellStyle(cellStyle);
				hssfCell.setCellValue((String) cells.next());
			}
			for (Iterator L = reportList.iterator(); L.hasNext();) {
				BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) L.next();
				ReportData.add(bcwtReportDTO.getOrderRequestId());
				ReportData.add(bcwtReportDTO.getCardSerialNo());
				ReportData.add(bcwtReportDTO.getOrderDate());
				ReportData.add(bcwtReportDTO.getReturnedDate());
				//ReportData.add(bcwtReportDTO.getNoOfDeliveryAttempts());
				ReportData.add(bcwtReportDTO.getOrderType());
			}
			rowIdx = 1;
			if(ReportData.size() > 0) {
				HSSFRow hssfRow = hs.createRow(rowIdx++);
				cellIdx = 0;
				for (Iterator cells = ReportData.iterator(); cells.hasNext();) {
					if(cellIdx >= headers.size()){
            			hssfRow = hs.createRow(rowIdx++);
            			cellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
					hssfCell.setCellValue((String) cells.next());
				}
	         }
			hwb.setSheetName(0, Constants.RETURNED_ORDERS_REPORT,
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
			hwb.write(bs);
		} catch (Exception e) {
			throw new HPSFException(e.getMessage());
		}
		return bs;
	}

	public static ByteArrayOutputStream generateCancelledOrderExcel(
			String reportHeading, List headers, List reportList)
			throws Exception {
		String MY_NAME = ME + "generateCancelledOrderExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet hs = hwb.createSheet(Constants.OUTSTANDING_ORDERS_REPORT);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;

		try {
			ArrayList ReportData = new ArrayList();
		//	HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyle.setWrapText(true);
		//	hr.setHeightInPoints(15f);
			hs.setDefaultColumnWidth((short)18);

			 HSSFRow hrmHeading = hs.createRow(rowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyle);
				
				hssfCellHeading.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;

				HSSFRow hr = hs.createRow(rowIdx);
				hr.setHeightInPoints(20f);
			
			for (Iterator cells = headers.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hr.createCell(cellIdx++);
				hssfCell.setCellStyle(cellStyle);
				hssfCell.setCellValue((String) cells.next());
			}
			for (Iterator L = reportList.iterator(); L.hasNext();) {
				BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) L.next();
				ReportData.add(bcwtReportDTO.getOrderRequestId());
				ReportData.add(bcwtReportDTO.getCardSerialNo());
				ReportData.add(bcwtReportDTO.getFirstName());
				ReportData.add(bcwtReportDTO.getLastName());
				ReportData.add(bcwtReportDTO.getCancelledDate());
				ReportData.add(bcwtReportDTO.getMediasalesAdmin());
				ReportData.add(bcwtReportDTO.getReasonType());
				ReportData.add(bcwtReportDTO.getOrderType());
			}
			rowIdx = 1;
			if(ReportData.size() > 0) {
				HSSFRow hssfRow = hs.createRow(rowIdx++);
				cellIdx = 0;
				for (Iterator cells = ReportData.iterator(); cells.hasNext();) {
					if(cellIdx >= headers.size()){
            			hssfRow = hs.createRow(rowIdx++);
            			cellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
					hssfCell.setCellValue((String) cells.next());
				}
	         }
			hwb.setSheetName(0, Constants.CANCELLED_ORDERS_REPORT,
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
			hwb.write(bs);
		} catch (Exception e) {
			throw new HPSFException(e.getMessage());
		}
		return bs;
	}


	public static ByteArrayOutputStream generateMediaSalesMetricsExcel(
			String reportHeading, List headers, List reportList)
			throws Exception {
		String MY_NAME = ME + "generateMediaSalesMetricsExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet hs = hwb.createSheet("MEDIASALESPERFORMANCEMETRICS");
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
		short cellIdx = 0;

		try {
			ArrayList ReportData = new ArrayList();
		//	HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyle.setWrapText(true);
		//	hr.setHeightInPoints(15f);
			hs.setDefaultColumnWidth((short)18);

			 HSSFRow hrmHeading = hs.createRow(rowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyle);
				
				hssfCellHeading.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,(short)2,rowIdx,(short)4));
				
				rowIdx++;

				HSSFRow hr = hs.createRow(rowIdx);
				hr.setHeightInPoints(20f);
			
			for (Iterator cells = headers.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hr.createCell(cellIdx++);
				hssfCell.setCellStyle(cellStyle);
				hssfCell.setCellValue((String) cells.next());
			}
			for (Iterator L = reportList.iterator(); L.hasNext();) {
				BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) L.next();
				ReportData.add(bcwtReportDTO.getOrderRequestId());
				ReportData.add(bcwtReportDTO.getOrderDate());
				ReportData.add(bcwtReportDTO.getFirstName());
				ReportData.add(bcwtReportDTO.getLastName());
				ReportData.add(bcwtReportDTO.getQaFirstName());
				ReportData.add(bcwtReportDTO.getQaLastName());
				ReportData.add(bcwtReportDTO.getOrderType());
			}
			rowIdx = 1;
			if(ReportData.size() > 0) {
				HSSFRow hssfRow = hs.createRow(rowIdx++);
				cellIdx = 0;
				for (Iterator cells = ReportData.iterator(); cells.hasNext();) {
					if(cellIdx >= headers.size()){
            			hssfRow = hs.createRow(rowIdx++);
            			cellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
					hssfCell.setCellValue((String) cells.next());
				}
	         }
			hwb.setSheetName(0, "MEDIASALESPERFORMANCEMETRICS",
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
			hwb.write(bs);
		} catch (Exception e) {
			throw new HPSFException(e.getMessage());
		}
		return bs;
	}

	public static ByteArrayOutputStream generateRevenueReportExcel(
			String reportHeading, List headers, List reportList,
			List revenueSummaryFieldList, List revenueSummaryReportList)
			throws Exception {
		String MY_NAME = ME + "generateRevenueReportExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");

		ByteArrayOutputStream bs = new ByteArrayOutputStream();

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();

			/**************** Revenue Summary Report - Starts Here *********/
			int summaryRowIdx = 0;
			short summaryCellIdx = 0;

			HSSFSheet hsSummary = hwb.createSheet(Constants.REVENUE_SUMMARY_REPORT);
			HSSFCellStyle cellStyleSummary = hwb.createCellStyle();
			ArrayList summaryReportData = new ArrayList();
	//		HSSFRow hrSummary = hsSummary.createRow(summaryRowIdx);

			cellStyleSummary.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyleSummary.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyleSummary.setWrapText(true);
	//		hrSummary.setHeightInPoints(15f);
	//		hsSummary.setDefaultColumnWidth((short)18);

			 HSSFRow hrmHeading = hsSummary.createRow(summaryRowIdx);
				HSSFCell hssfCellHeading = hrmHeading.createCell((short)2);
				hssfCellHeading.setCellStyle(cellStyleSummary);
				
				hssfCellHeading.setCellValue(reportHeading);
				hsSummary.addMergedRegion(new Region(summaryRowIdx,(short)2,summaryRowIdx,(short)4));
				
				summaryRowIdx++;

				HSSFRow hrSummary = hsSummary.createRow(summaryRowIdx);
				hrSummary.setHeightInPoints(20f);
			
			if(revenueSummaryFieldList != null && !revenueSummaryFieldList.isEmpty()){
				for (Iterator cellsSummary = revenueSummaryFieldList.iterator(); cellsSummary.hasNext();) {
					HSSFCell hssfCell = hrSummary.createCell(summaryCellIdx++);
					hssfCell.setCellStyle(cellStyleSummary);
					hssfCell.setCellValue((String) cellsSummary.next());
				}
			}
			if(revenueSummaryReportList != null && !revenueSummaryReportList.isEmpty()){
				for (Iterator iteSummary = revenueSummaryReportList.iterator(); iteSummary.hasNext();) {
					BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iteSummary.next();
					summaryReportData.add(bcwtReportDTO.getCreditCardType());
					summaryReportData.add(bcwtReportDTO.getAmount());
					summaryReportData.add(bcwtReportDTO.getNoOfOrders());
				}
			}

			summaryRowIdx = 1;
			if(summaryReportData.size() > 0) {
				HSSFRow hssfRow = hsSummary.createRow(summaryRowIdx++);
				summaryCellIdx = 0;
				for (Iterator cellsSummary = summaryReportData.iterator(); cellsSummary.hasNext();) {
					if(summaryCellIdx >= revenueSummaryFieldList.size()){
            			hssfRow = hsSummary.createRow(summaryRowIdx++);
            			summaryCellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(summaryCellIdx++);
					hssfCell.setCellValue((String) cellsSummary.next());
				}
	         }
			hwb.setSheetName(0, "REVENUE_SUMMARY",
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);

			/**************** Revenue Summary Report - Ends Here *********/

			int rowIdx = 0;
			short cellIdx = 0;

			HSSFSheet hs = hwb.createSheet(Constants.REVENUE_REPORT);
			ArrayList ReportData = new ArrayList();
			HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			cellStyle.setWrapText(true);
			hr.setHeightInPoints(15f);
			hs.setDefaultColumnWidth((short)18);

			for (Iterator cells = headers.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hr.createCell(cellIdx++);
				hssfCell.setCellStyle(cellStyle);
				hssfCell.setCellValue((String) cells.next());
			}
			for (Iterator L = reportList.iterator(); L.hasNext();) {
				BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) L.next();
				ReportData.add(bcwtReportDTO.getOrderRequestId());
				ReportData.add(bcwtReportDTO.getTransactionDate());
				ReportData.add(bcwtReportDTO.getCreditCardType());
				ReportData.add(bcwtReportDTO.getAmount());
				ReportData.add(bcwtReportDTO.getCreditCardLast4());
				ReportData.add(bcwtReportDTO.getApprovalCode());
				ReportData.add(bcwtReportDTO.getOrderModule());
				ReportData.add(bcwtReportDTO.getProducts());
				ReportData.add(bcwtReportDTO.getCustomerName());
				ReportData.add(bcwtReportDTO.getOrderType());
			}

			rowIdx = 1;
			if(ReportData.size() > 0) {
				HSSFRow hssfRow = hs.createRow(rowIdx++);
				cellIdx = 0;
				for (Iterator cells = ReportData.iterator(); cells.hasNext();) {
					if(cellIdx >= headers.size()){
            			hssfRow = hs.createRow(rowIdx++);
            			cellIdx =0;
					}
					HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
					hssfCell.setCellValue((String) cells.next());
				}
	         }
			hwb.setSheetName(1, "REVENUE_DETAILS",
					HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);

			hwb.write(bs);
		} catch (Exception e) {
			throw new HPSFException(e.getMessage());
		}
		return bs;
	}
	
	public static ByteArrayOutputStream generateMonthlyUsageExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request)throws Exception {
		   
		HSSFWorkbook hwk = new HSSFWorkbook();
		String MY_NAME = ME + "generateMonthlyUsageExcel:";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
	    HSSFSheet hs = hwk.createSheet("MONTHLYUSAGE");
	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    String companyName = "";
	    String currentDate = "";
	    ArrayList reportData = new ArrayList();
			try	{
				
			if (null != request.getAttribute("COMPANY_NAME_REPORT")) {
	  		      companyName = request.getAttribute("COMPANY_NAME_REPORT").toString();
	 			}
	  		
	 			currentDate = Util.getCurrentDate();
				
	 			HSSFCellStyle cellStyle1 = hwk.createCellStyle();
				HSSFFont hssfFont1 = hwk.createFont();
			    cellStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			    
			    HSSFCellStyle cellStyle2 = hwk.createCellStyle();
	 			HSSFFont hssfFont2 = hwk.createFont();
	 			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			   // hssfFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	 			hssfFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	 			cellStyle2.setFont(hssfFont2);
	 			
				HSSFRow reportHead = hs.createRow(rowIdx);
				HSSFCell cellReportHead = reportHead.createCell(cellIdx);
				cellReportHead.setCellStyle(cellStyle2);
				cellReportHead.setCellValue(reportHeading);
				hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
				
				rowIdx = rowIdx+1;
	 			
	 	/*	    HSSFRow hrmCompany = hs.createRow(rowIdx);
	 			HSSFCell hssfCellCompany = hrmCompany.createCell(cellIdx);
	 			hssfCellCompany.setCellStyle(cellStyle1);
	 			String company = " Company Name :  " +  companyName;
	 			hssfCellCompany.setCellValue(company);
	 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
	 			
	 			rowIdx = rowIdx+1;
	 			HSSFRow hrmDate = hs.createRow(rowIdx);
	 			HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
	 			hssfCellDate.setCellStyle(cellStyle1);
	 		//	String dateGenerated = " Report Generated Date :  " +  currentDate;
	 		//	hssfCellDate.setCellValue(dateGenerated);
	 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
	 			rowIdx = rowIdx+1;*/
	 			
		    HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwk.createCellStyle();
			HSSFFont hssfFont = hwk.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		    
		   
		    hr.setHeightInPoints(20f);
	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	              hssfCell.setCellValue(headcell.toString());
	        }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	PartnerReportSearchDTO partnerReportSearchDTO = (PartnerReportSearchDTO) L.next();
	        	 reportData.add(partnerReportSearchDTO.getCardSerialNo());
				 reportData.add(partnerReportSearchDTO.getBenefitType());
				 reportData.add(partnerReportSearchDTO.getBillingMonth());
				 reportData.add(partnerReportSearchDTO.getMonthlyUsage());
			}
		 	rowIdx = rowIdx + 1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();) 
	              {
	        		if(cellIdx > headers.size()-1){
	        			cellIdx = 0;
	        			hssfRow = hs.createRow(++rowIdx);
	        		}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
	                hssfCell.setCellValue((String) cells.next());
	                cellIdx = (short)(cellIdx + 1);
	               }
		        }
	        hwk.setSheetName(0, "MONTHLYUSAGE");//, HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hwk.write(bs);
	      } catch (Exception e) {			
	    	  throw new HPSFException(e.getMessage());            
	      }
	      return bs;			
	}
	
	public static ByteArrayOutputStream generateUpassPartnerUpcomingMonthlyReportExcel(
			String reportHeading,List headers,List reportList,HttpServletRequest request, ReportForm reportForm)throws Exception {

 		String MY_NAME = ME + "generatePartnerUpcomingMonthlyReportExcel:";
 		BcwtsLogger.info(MY_NAME + "entering into excel generator");
 		HSSFWorkbook hwu = new HSSFWorkbook();
 	    HSSFSheet hs = hwu.createSheet("PartnerUpcomingMonthlyReport");
 	    hs.setDefaultColumnWidth((short)25);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		int rowIdx = 0;
	    short cellIdx = 0;
	    ArrayList reportData = new ArrayList();
	    UpCommingMonthBenefitDTO orderReportDTO = null;
	    String currentDate = null;
 		try	{
		    HSSFRow hr = hs.createRow(rowIdx);
			HSSFCellStyle cellStyle = hwu.createCellStyle();
			HSSFFont hssfFont = hwu.createFont();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    cellStyle.setFont(hssfFont);
		    hr.setHeightInPoints(20f);

		    HSSFCellStyle cellStyle1 = hwu.createCellStyle();
			HSSFFont hssfFont1 = hwu.createFont();
		    cellStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		    currentDate = Util.getCurrentDate();
			rowIdx = rowIdx+1;
 			HSSFRow hrmDate = hs.createRow(rowIdx);
 			HSSFCell hssfCellDate = hrmDate.createCell(cellIdx);
 			hssfCellDate.setCellStyle(cellStyle1);
 			String dateGenerated = " Report Generated Date :  " +  currentDate;
 			hssfCellDate.setCellValue(dateGenerated);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
 			
 			HSSFRow hrmTotalCards = hs.createRow(rowIdx);
 			HSSFCell hssfTotalCards = hrmTotalCards.createCell(cellIdx);
 			hssfTotalCards.setCellStyle(cellStyle1);
 			String total = " Total Number of Cards for Account:  " +  reportForm.getUpCommingReportList().size();
 			hssfTotalCards.setCellValue(total);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
 			rowIdx = rowIdx+1;
 			
 			HSSFRow hrmTotalCardsQueue = hs.createRow(rowIdx);
 			HSSFCell hssfTotalCardsQueue = hrmTotalCardsQueue.createCell(cellIdx);
 			hssfTotalCardsQueue.setCellStyle(cellStyle1);
 			String totalQueue = " Total Number of Cards in the Queue for Activation/Deactivation:  " +  (reportForm.getUpCommingReportActList().size() + reportForm.getUpCommingReportDeactList().size());
 			hssfTotalCardsQueue.setCellValue(totalQueue);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
 			rowIdx = rowIdx+1;
 			
 			HSSFRow hrmTotalNewCardsQueue = hs.createRow(rowIdx);
 			HSSFCell hssfTotalNewCardsQueue = hrmTotalNewCardsQueue.createCell(cellIdx);
 			hssfTotalNewCardsQueue.setCellStyle(cellStyle1);
 			String totalNewQueue = " Total Number of New Cards in the Queue:  " +  reportForm.getUpCommingNewCardList().size();
 			hssfTotalNewCardsQueue.setCellValue(totalNewQueue);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
 			rowIdx = rowIdx+1;
 			
 			HSSFRow hrmTotalActiveCardsQueue = hs.createRow(rowIdx);
 			HSSFCell hssfTotalActiveCardsQueue = hrmTotalActiveCardsQueue.createCell(cellIdx);
 			hssfTotalActiveCardsQueue.setCellStyle(cellStyle1);
 			String totalActiveQueue = " Total Number of Cards to be Activated:  " +  reportForm.getYesCount();
 			hssfTotalActiveCardsQueue.setCellValue(totalActiveQueue);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
 			rowIdx = rowIdx+1;
 			
 			HSSFRow hrmTotalDeActiveCardsQueue = hs.createRow(rowIdx);
 			HSSFCell hssfTotalDeActiveCardsQueue = hrmTotalDeActiveCardsQueue.createCell(cellIdx);
 			hssfTotalDeActiveCardsQueue.setCellStyle(cellStyle1);
 			String totalDeActiveQueue = " Total Number of Cards to be Deactivated:  " +  reportForm.getNoCount();
 			hssfTotalDeActiveCardsQueue.setCellValue(totalDeActiveQueue);
 			hs.addMergedRegion(new Region(rowIdx,cellIdx,rowIdx,(short)5));
 			rowIdx = rowIdx+1;


	        for (Iterator cells = headers.iterator(); cells.hasNext();) {
	              HSSFCell hssfCell = hr.createCell(cellIdx++);
	              hssfCell.setCellStyle(cellStyle);
	              Object headcell = (Object) cells.next();
	            if(null!=headcell)
	              hssfCell.setCellValue(headcell.toString());
            }
	        for(Iterator L = reportList. iterator();L.hasNext() ;) {
	        	orderReportDTO = (UpCommingMonthBenefitDTO) L.next();
	        	 reportData.add(orderReportDTO.getCardSerialNumber());
	        	 reportData.add(orderReportDTO.getMemberName());
	        	 reportData.add(orderReportDTO.getMemberId());
	        	 reportData.add(orderReportDTO.getIsBenefitActivated());

			}
	        rowIdx = rowIdx+1;
		 	int count =0;
		 	if(reportData.size() > 0) {
		            HSSFRow hssfRow = hs.createRow(rowIdx);
		            cellIdx = 0;
	            for (Iterator cells = reportData.iterator(); cells.hasNext();)
	              {
	            	if(cellIdx > headers.size()-1){
	          			cellIdx = 0;
	          			hssfRow = hs.createRow(++rowIdx);
	            	}
	                HSSFCell hssfCell = hssfRow.createCell(cellIdx);
                    hssfCell.setCellValue((String) cells.next());
                    cellIdx = (short)(cellIdx + 1);
	              }
		        }
	        hwu.setSheetName(0, "PARTNERUPCOMINGMONTHLYREPORT", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE);
	        hwu.write(bs);
	      } catch (Exception e) {
	    	  BcwtsLogger.error("Exception "+Util.getFormattedStackTrace(e));
	    	  throw new HPSFException(e.getMessage());
	      }
	      return bs;
    }
	
	/***************** OF - Ends here **********************/
}