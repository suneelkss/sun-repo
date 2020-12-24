package com.marta.admin.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
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
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Util;



	public class PdfGenerator {
	public static String ME = "PdfGenerator: " ;
 public static ByteArrayOutputStream generateUsagePDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request)throws Exception{
	 		String MY_NAME = ME + "generateUsagePDF:";
	 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
	 		Document document = new Document(PageSize.A4.rotate());
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	PdfPCell cell=null;
			PdfPTable datatable = null;
			boolean isFileGenerated = false;
	    	try {
	    		PdfWriter.getInstance(document,baos);
				document.open();
				Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
				document.setPageCount(2);
				Paragraph docHeaderPara = new Paragraph();
				docHeaderPara.setAlignment(Element.ALIGN_CENTER);
				docHeaderPara.add(reportHeading);
			    document.add(docHeaderPara);
		    	document.add(Chunk.NEWLINE);
		    	datatable= new PdfPTable(4);
		    	datatable.getDefaultCell().setBorderWidth(1);
		    	if(null != fieldList && !fieldList.isEmpty()) {
					for (Iterator iter = fieldList.iterator(); iter
							.hasNext();) {
						String field = (String) iter.next();
						cell = new PdfPCell(new Phrase(field));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setNoWrap(true);
						datatable.addCell(cell);
					}
		    	}
			   if(null != reportList && !reportList.isEmpty()) {
					for (Iterator iter = reportList.iterator(); iter.hasNext();) {
						BcwtUsageBenifitDTO bcwtUsageBenifitDTO = (BcwtUsageBenifitDTO) iter.next();
						cell = new PdfPCell(new Phrase(bcwtUsageBenifitDTO.getBreezecardSerialNo()));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);
						cell = new PdfPCell(new Phrase(bcwtUsageBenifitDTO.getBenefitName()));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);
						cell = new PdfPCell(new Phrase(bcwtUsageBenifitDTO.getBillingMonthYear()));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);
						cell = new PdfPCell(new Phrase(bcwtUsageBenifitDTO.getMonthlyUsage()));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);
					}
				}else{
					datatable.addCell("No records Found");
				}
				document.add(datatable);
				document.add(Chunk.NEWLINE);
			}catch(Exception e){
	    		e.printStackTrace();
	    		throw new Exception("Error in generating the order summary PDF Document:"+e);
	    	}finally{
	    		document.close();
	    	}
			return  baos;
	 }

	 public static ByteArrayOutputStream generateDetailedOrderPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request)throws Exception{
		 		String MY_NAME = ME + "generateDetailedOrderPDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    	PdfPCell cell=null;
				PdfPTable datatable = null;
		    	try {
		    		PdfWriter.getInstance(document,baos);
					document.open();
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
					document.setPageCount(2);
					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER);
					docHeaderPara.add(reportHeading);
				    document.add(docHeaderPara);
			    	document.add(Chunk.NEWLINE);
			    	
			    	
			    	if(null != request.getAttribute("DORDER_D_RANGE")){
			    	Paragraph docHeaderdate = new Paragraph();
			    	docHeaderdate.setAlignment(Element.ALIGN_CENTER);
			    	docHeaderdate.add("Report Date Range"+request.getAttribute("DORDER_D_RANGE"));
				    document.add(docHeaderdate);
			    	document.add(Chunk.NEWLINE);
			    	}
			    	
			    	datatable= new PdfPTable(6);
			    	datatable.getDefaultCell().setBorderWidth(1);
			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter
								.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
						}
			    	}
				   if(null != reportList && !reportList.isEmpty()) {
					   for (Iterator iter = reportList.iterator(); iter.hasNext();) {
						    BcwtDetailedOrderReportDTO orderReportDTO = (BcwtDetailedOrderReportDTO) iter.next();
							cell = new PdfPCell(new Phrase(orderReportDTO.getCompanyName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setHorizontalAlignment(Element.PHRASE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(orderReportDTO.getPartnerAdminName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(orderReportDTO.getEmployeeName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(orderReportDTO.getBreezecardSerialNo()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(orderReportDTO.getBenefitName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(orderReportDTO.getEffectiveDate()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
					   }
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);
					document.add(Chunk.NEWLINE);
				}catch(Exception e){
		    		e.printStackTrace();
		    		throw new Exception("Error in generating the order summary PDF Document:"+e);
		    	}finally{
		    		document.close();
		    	}
				return  baos;
		  }

	 public static ByteArrayOutputStream generateHotListPDF(String reportHeading,List fieldList,List reportList)throws Exception{
	 		String MY_NAME = ME + "generateHotListPDF:";
	 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
	 		Document document = new Document(PageSize.A4.rotate());
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	PdfPCell cell=null;
			PdfPTable datatable = null;
			boolean isFileGenerated = false;
	    	try {
	    		PdfWriter.getInstance(document,baos);
				document.open();
				Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
				document.setPageCount(2);
				Paragraph docHeaderPara = new Paragraph();
				docHeaderPara.setAlignment(Element.ALIGN_CENTER);
				docHeaderPara.add(reportHeading);
			    document.add(docHeaderPara);
		    	document.add(Chunk.NEWLINE);
		    	datatable= new PdfPTable(4);
		    	datatable.getDefaultCell().setBorderWidth(1);
		    	if(null != fieldList && !fieldList.isEmpty()) {
					for (Iterator iter = fieldList.iterator(); iter
							.hasNext();) {
						String field = (String) iter.next();
						cell = new PdfPCell(new Phrase(field));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setNoWrap(true);
						datatable.addCell(cell);
					}
		    	}
			   if(null != reportList && !reportList.isEmpty()) {
				}else{
					datatable.addCell("No records Found");
				}
				document.add(datatable);
				document.add(Chunk.NEWLINE);
			}catch(Exception e){
	    		e.printStackTrace();
	    		throw new Exception("Error in generating the order summary PDF Document:"+e);
	    	}finally{
	    		document.close();
	    	}
			return  baos;
	  }


	 /*
	  * main class
	  */
	 	public static void main(String args[])throws Exception
	 	{
	 		String filePath = "c:\\cancelorder.pdf";
			File pdfReportFile = new File(filePath);
			FileOutputStream fop=new FileOutputStream(pdfReportFile);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			List fieldList = new ArrayList();
 			List opList = new ArrayList();
    		if(null != baos && null != fop){
				 if(pdfReportFile.exists()){
				          fop.write(baos.toByteArray());
				          fop.flush();
				          fop.close();
				 }
			 }
	 	}
	 	
	 	 public static ByteArrayOutputStream generateUpassNewCardReportPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request)throws Exception{
		 		String MY_NAME = ME + "generateActiveBenefitPDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    	PdfPCell cell=null;
				PdfPTable datatable = null;
				boolean isFileGenerated = false;
		    	try {
		    		PdfWriter.getInstance(document,baos);
					document.open();			
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD);
					Font[] fonts = new Font[4];
		    		fonts[0] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
		    		fonts[1] = FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLD);
		    		fonts[2] = FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD);
		    		fonts[3] = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
					document.setPageCount(2);
					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER); 
					docHeaderPara.add(reportHeading);
					docHeaderPara.setFont(font);
				    document.add(docHeaderPara);
				    
			    	document.add(Chunk.NEWLINE);
			    	datatable= new PdfPTable(3);
			    	datatable.getDefaultCell().setBorderWidth(1);
			    	String companyName = "";
			    	String currentDate = "";
			    	
			    	if (null != request.getAttribute("COMPANY_NAME_REPORT")) {
		    		      companyName = request.getAttribute("COMPANY_NAME_REPORT").toString();
		    		}
		    		
		    		currentDate = Util.getCurrentDate();
			    	Paragraph docCompanyNamePara = new Paragraph();
			    	docCompanyNamePara.setAlignment(Element.ALIGN_LEFT);
			    	docCompanyNamePara.add(" Company Name :"+companyName);
			    	document.add(docCompanyNamePara);
			    	document.add(Chunk.NEWLINE);
			    	Paragraph docCurrentDatePara = new Paragraph();
			    	docCurrentDatePara.setAlignment(Element.ALIGN_LEFT);
			    	docCurrentDatePara.add(" Report Generated Date :"+currentDate);
			    	document.add(docCurrentDatePara);
			    	document.add(Chunk.NEWLINE);
			    	
			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter
								.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field,fonts[2]));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
						}
			    	}
				   if(null != reportList && !reportList.isEmpty()) {
						for (Iterator iter = reportList.iterator(); iter
								.hasNext();) {
							BcwtNewCardReportDTO partnerReportSearchDTO = (BcwtNewCardReportDTO) iter.next();
							cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getBreezecardSerialNo(),fonts[0]));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getBenefitName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(false);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getEffectiveDate()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(false);
							datatable.addCell(cell);
						}
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);		
					document.add(Chunk.NEWLINE);
				}catch(Exception e){	    		
		    		e.printStackTrace();
		    		throw new Exception("Error in generating the order summary PDF Document:"+e);
		    	}finally{
		    		document.close();
		    	}  
				return  baos;	
		  }
	 	
	 	
	 	

//	 	New Method added for New Card Report
		 public static ByteArrayOutputStream generateNewCardReportPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request)throws Exception{
		 		String MY_NAME = ME + "generateNewCardReportPDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    	PdfPCell cell=null;
				PdfPTable datatable = null;
				boolean isFileGenerated = false;
		    	try {
		    		PdfWriter.getInstance(document,baos);
					document.open();
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
					document.setPageCount(2);
					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER);
					docHeaderPara.add(reportHeading);
				    document.add(docHeaderPara);
			    	document.add(Chunk.NEWLINE);
			    	
			    	
			    	if(null != request.getAttribute("NC_DATE_RANGE")){
			    	Paragraph docHeaderDate = new Paragraph();
			    	docHeaderDate.setAlignment(Element.ALIGN_CENTER);
			    	docHeaderDate.add("Report Date Range"+ request.getAttribute("NC_DATE_RANGE"));
				    document.add(docHeaderDate);
			    	document.add(Chunk.NEWLINE);
			    	}
			    	datatable= new PdfPTable(6);
			    	datatable.getDefaultCell().setBorderWidth(1);
			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter
								.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
						}
			    	}
				   if(null != reportList && !reportList.isEmpty()) {
						for (Iterator iter = reportList.iterator(); iter.hasNext();) {
							BcwtNewCardReportDTO bcwtNewCardReportDTO = (BcwtNewCardReportDTO) iter.next();
							cell = new PdfPCell(new Phrase(bcwtNewCardReportDTO.getCompanyName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setHorizontalAlignment(Element.PHRASE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtNewCardReportDTO.getPartnerAdminName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtNewCardReportDTO.getEmployeeName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtNewCardReportDTO.getBreezecardSerialNo()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtNewCardReportDTO.getBenefitName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtNewCardReportDTO.getEffectiveDate()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
						}
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);
					document.add(Chunk.NEWLINE);
				}catch(Exception e){
		    		e.printStackTrace();
		    		throw new Exception("Error in generating the order summary PDF Document:"+e);
		    	}finally{
		    		document.close();
		    	}
				return  baos;
		  }




// 	New Method added for Hot List Report
			 public static ByteArrayOutputStream generateHotListReportPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request)throws Exception{
			 		String MY_NAME = ME + "generateHotListReportPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	PdfPCell cell=null;
					PdfPTable datatable = null;
					boolean isFileGenerated = false;
			    	try {
			    		PdfWriter.getInstance(document,baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);
						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
						docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	datatable= new PdfPTable(6);
				    	datatable.getDefaultCell().setBorderWidth(1);
				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter
									.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setNoWrap(true);
								datatable.addCell(cell);
							}
				    	}
					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter.hasNext();) {
								BcwtHotListReport bcwtHotListReport= (BcwtHotListReport) iter.next();
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getFirstName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getLastName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getPointOfSale()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getHotListDate()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getCardSerialNumber()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getAdminName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		e.printStackTrace();
			    		throw new Exception("Error in generating the order summary PDF Document:"+e);
			    	}finally{
			    		document.close();
			    	}
					return  baos;
			  }
			 public static ByteArrayOutputStream generatePartnerMonthlyUsageReportPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request)throws Exception{
			 		String MY_NAME = ME + "generatePartnerMonthlyUsageReportPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	PartnerMonthlyUsageReportDTO partnerMonthlyUsageReportDTO = null;
			    	PdfPCell cell=null;
					PdfPTable datatable = null;
					boolean isFileGenerated = false;
			    	try {
			    		PdfWriter.getInstance(document,baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);
						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
						docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	
				    
				    	Paragraph docHeaderPara1 = new Paragraph();
						docHeaderPara1.setAlignment(Element.ALIGN_LEFT);
						docHeaderPara1.add("Report Generated on:" +Util.getDate());
					    document.add(docHeaderPara1);
				    	document.add(Chunk.NEWLINE);
				    	
				    	//MONTH_D_RANGE
				    	Paragraph docHeaderPara12 = new Paragraph();
				    	docHeaderPara12.setAlignment(Element.ALIGN_LEFT);
				    	docHeaderPara12.add("Report Date Range:" +request.getAttribute("MONTH_D_RANGE"));
					    document.add(docHeaderPara12);
				    	document.add(Chunk.NEWLINE);
						
					  //  document.add(docHeaderPara);
				    //	document.add(Chunk.NEWLINE);
				    	
				    	if(null != request.getAttribute("NAME")){
				    		String name =(String) request.getAttribute("NAME");
				    		Paragraph docHeaderPara2 = new Paragraph();
				    		docHeaderPara2.setAlignment(Element.ALIGN_LEFT);
						docHeaderPara2.add("TMA/Company Name:"+name);
					    document.add(docHeaderPara2);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	
				    	datatable= new PdfPTable(4);
				    	datatable.getDefaultCell().setBorderWidth(1);
				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter
									.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setNoWrap(true);
								datatable.addCell(cell);
							}
				    	}
					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter.hasNext();) {
								partnerMonthlyUsageReportDTO= (PartnerMonthlyUsageReportDTO) iter.next();
								
								cell = new PdfPCell(new Phrase(partnerMonthlyUsageReportDTO.getCompanyName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								
								cell = new PdfPCell(new Phrase(partnerMonthlyUsageReportDTO.getBreezeCardSerialNumber()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								
								cell = new PdfPCell(new Phrase(partnerMonthlyUsageReportDTO.getBillingMonth()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(partnerMonthlyUsageReportDTO.getMonthlyUsage()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		e.printStackTrace();
			    		throw new Exception("Error in generating the order summary PDF Document:"+e);
			    	}finally{
			    		document.close();
			    	}
					return  baos;
			 }

//New Method For Partner New Card Report
			 public static ByteArrayOutputStream generatePartnerNewCardReportPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request)throws Exception{
			 		String MY_NAME = ME + "generatePartnerNewCardReportPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	PdfPCell cell=null;
					PdfPTable datatable = null;
					boolean isFileGenerated = false;
			    	try {
			    		PdfWriter.getInstance(document,baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);
						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
						docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	
				    	Paragraph docDate = new Paragraph();
				    	docDate.setAlignment(Element.ALIGN_LEFT);
				    	docDate.add("Report Generated On: "+Util.getDate());
					    document.add(docDate);
				    	document.add(Chunk.NEWLINE);
				    	
				    	Paragraph docDateRange = new Paragraph();
				    	docDateRange.setAlignment(Element.ALIGN_LEFT);
				    	docDateRange.add("Report Date Range: "+request.getAttribute("NEW_D_RANGE"));
					    document.add(docDateRange);
				    	document.add(Chunk.NEWLINE);
				    	
				    	String count = (String)request.getAttribute("NEWCARDCOUNT");
				    	
				    	Paragraph docCardCount = new Paragraph();
				    	docCardCount.setAlignment(Element.ALIGN_LEFT);
				    	docCardCount.add(" New Cards Added:"+count);
				    	document.add(docCardCount);
				    	document.add(Chunk.NEWLINE);
				    	
				    	if(null != request.getAttribute("NAME")){
				        String name =(String) request.getAttribute("NAME");
				    	Paragraph tmaName = new Paragraph();
				    	tmaName.setAlignment(Element.ALIGN_LEFT);
				    //	docDate.add(Util.getDate());
				    	tmaName.add("TMA/Company Name: "+name);
				    	document.add(tmaName);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	datatable= new PdfPTable(4);
				    	datatable.getDefaultCell().setBorderWidth(1);
				    	
				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter
									.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setNoWrap(true);
								datatable.addCell(cell);
							}
				    	}
					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter.hasNext();) {
								PartnerNewCardReportDTO partnerNewCardReportDTO = (PartnerNewCardReportDTO) iter.next();
								cell = new PdfPCell(new Phrase(partnerNewCardReportDTO.getCompanyName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(partnerNewCardReportDTO.getCardSerialNumber()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(partnerNewCardReportDTO.getBenefitName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(partnerNewCardReportDTO.getEffectiveDate()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		e.printStackTrace();
			    		throw new Exception("Error in generating the order summary PDF Document:"+e);
			    	}finally{
			    		document.close();
			    	}
					return  baos;
			  }
			 
			 
			 public static ByteArrayOutputStream generateTMAHotlistReportPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request)throws Exception{
			 		String MY_NAME = ME + "generatePartnerNewCardReportPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	PdfPCell cell=null;
					PdfPTable datatable = null;
					boolean isFileGenerated = false;
			    	try {
			    		PdfWriter.getInstance(document,baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);
						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
						docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	
				    	Paragraph docDate = new Paragraph();
				    	docDate.setAlignment(Element.ALIGN_LEFT);
				    	docDate.add("Report Generated On: "+Util.getDate());
					    document.add(docDate);
				    	document.add(Chunk.NEWLINE);
				    	
				    	if(null != request.getAttribute("HOTLIST_D_RANGE")){
				    	Paragraph docDateRange = new Paragraph();
				    	docDateRange.setAlignment(Element.ALIGN_LEFT);
				    	docDateRange.add("Report Date Range: "+request.getAttribute("HOTLIST_D_RANGE"));
					    document.add(docDateRange);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	if(null != request.getAttribute("NAME")){
				        String name =(String) request.getAttribute("NAME");
				    	Paragraph tmaName = new Paragraph();
				    	tmaName.setAlignment(Element.ALIGN_LEFT);
				    //	docDate.add(Util.getDate());
				    	tmaName.add("TMA: "+name);
				    	document.add(tmaName);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	datatable= new PdfPTable(6);
				    	datatable.getDefaultCell().setBorderWidth(1);
				    	
				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter
									.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setNoWrap(true);
								datatable.addCell(cell);
							}
				    	}
					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter.hasNext();) {
								BcwtHotListReport bcwtHotListReport = (BcwtHotListReport) iter.next();
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getFirstName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getLastName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getCardSerialNumber()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getCompanyName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getHotListDate()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								
								cell = new PdfPCell(new Phrase(bcwtHotListReport.getAdminName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		e.printStackTrace();
			    		throw new Exception("Error in generating the order summary PDF Document:"+e);
			    	}finally{
			    		document.close();
			    	}
					return  baos;
			  }
			 
			 
			 public static ByteArrayOutputStream generatepRODUCTReportPDFDetail(String reportHeading,List fieldList,List reportList,HttpServletRequest request)throws Exception{
			 		String MY_NAME = ME + "generatePartnerNewCardReportPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	PdfPCell cell=null;
					PdfPTable datatable = null;
					boolean isFileGenerated = false;
					ProductDetailsReportDTO bcwtHotListReport = null;
			    	try {
			    		PdfWriter.getInstance(document,baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);
						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
						docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	
				    	Paragraph docDate = new Paragraph();
				    	docDate.setAlignment(Element.ALIGN_LEFT);
				    	docDate.add("Report Generated On: "+Util.getDate());
					    document.add(docDate);
				    	document.add(Chunk.NEWLINE);
				    	
				    	String dateRange = (String) request.getAttribute("DATE_RANGE");
				    	if(!Util.isBlankOrNull(dateRange)){
				    	Paragraph docDateRange = new Paragraph();
				    	docDateRange.setAlignment(Element.ALIGN_LEFT);
				    	docDateRange.add("Report Date Range: "+dateRange);
					    document.add(docDateRange);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	if(null != request.getAttribute("NAME")){
				        String name =(String) request.getAttribute("NAME");
				    	Paragraph tmaName = new Paragraph();
				    	tmaName.setAlignment(Element.ALIGN_LEFT);
				    //	docDate.add(Util.getDate());
				    	tmaName.add("TMA: "+name);
				    	document.add(tmaName);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	datatable= new PdfPTable(9);
				    	datatable.getDefaultCell().setBorderWidth(1);
				    	
				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter
									.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setNoWrap(true);
								datatable.addCell(cell);
							}
				    	}
					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter.hasNext();) {
								 bcwtHotListReport = (ProductDetailsReportDTO) iter.next();
								 cell = new PdfPCell(new Phrase(bcwtHotListReport.getCompanyname()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									//cell.setHorizontalAlignment(Element.PHRASE);
									datatable.addCell(cell);
									cell = new PdfPCell(new Phrase(bcwtHotListReport.getFirstname()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									//cell.setHorizontalAlignment(Element.PHRASE);
									datatable.addCell(cell);
									
									cell = new PdfPCell(new Phrase(bcwtHotListReport.getLastname()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									//cell.setHorizontalAlignment(Element.PHRASE);
									datatable.addCell(cell);
									
									cell = new PdfPCell(new Phrase(bcwtHotListReport.getMemberid()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									//cell.setHorizontalAlignment(Element.PHRASE);
									datatable.addCell(cell);
									
									cell = new PdfPCell(new Phrase(bcwtHotListReport.getBreezecard()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									//cell.setHorizontalAlignment(Element.PHRASE);
									datatable.addCell(cell);
															
									cell = new PdfPCell(new Phrase(bcwtHotListReport.getBenefitname()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									//cell.setHorizontalAlignment(Element.PHRASE);
									datatable.addCell(cell);
									
									
									cell = new PdfPCell(new Phrase(bcwtHotListReport.getRegion()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									datatable.addCell(cell);
									cell = new PdfPCell(new Phrase(String.valueOf(bcwtHotListReport.getCount())));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									datatable.addCell(cell);
									
									cell = new PdfPCell(new Phrase(bcwtHotListReport.getTransactiontype()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									//cell.setHorizontalAlignment(Element.PHRASE);
									datatable.addCell(cell);
								
								
							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		e.printStackTrace();
			    		throw new Exception("Error in generating the order summary PDF Document:"+e);
			    	}finally{
			    		document.close();
			    	}
					return  baos;
			  }
			 
			 public static ByteArrayOutputStream generatepRODUCTReportPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request,Map<String,Integer> productName)throws Exception{
			 		String MY_NAME = ME + "generatePartnerNewCardReportPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	PdfPCell cell=null;
					PdfPTable datatable = null;
					boolean isFileGenerated = false;
					DisplayProductSummaryDTO bcwtHotListReport = null;
			    	try {
			    		PdfWriter.getInstance(document,baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);
						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
						docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	
				    	Paragraph docDate = new Paragraph();
				    	docDate.setAlignment(Element.ALIGN_LEFT);
				    	docDate.add("Report Generated On: "+Util.getDate());
					    document.add(docDate);
				    	document.add(Chunk.NEWLINE);
				    	
				    	String dateRange = (String) request.getAttribute("DATE_RANGE");
				    	if(!Util.isBlankOrNull(dateRange)){
				    	Paragraph docDateRange = new Paragraph();
				    	docDateRange.setAlignment(Element.ALIGN_LEFT);
				    	docDateRange.add("Report Date Range: "+dateRange);
					    document.add(docDateRange);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	if(null != request.getAttribute("NAME")){
				        String name =(String) request.getAttribute("NAME");
				    	Paragraph tmaName = new Paragraph();
				    	tmaName.setAlignment(Element.ALIGN_LEFT);
				    //	docDate.add(Util.getDate());
				    	tmaName.add("TMA: "+name);
				    	document.add(tmaName);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	
				    	Paragraph docCountMPara = new Paragraph();
				    	docCountMPara.setAlignment(Element.ALIGN_LEFT);
				    	 for (Map.Entry<String, Integer> entry : productName.entrySet()){
							 if(entry.getValue() != 0){
								 docCountMPara.add(entry.getKey()+" Count:"+entry.getValue());
								 docCountMPara.add(Chunk.NEWLINE);
							    	
							 }
				    	 }
				    	 document.add(docCountMPara);
					     document.add(Chunk.NEWLINE);
				    	
				    	datatable= new PdfPTable(4);
				    	datatable.getDefaultCell().setBorderWidth(1);
				    	
				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter
									.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setNoWrap(true);
								datatable.addCell(cell);
							}
				    	}
					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter.hasNext();) {
								 bcwtHotListReport = (DisplayProductSummaryDTO) iter.next();
								 cell = new PdfPCell(new Phrase(bcwtHotListReport.getCompanyName()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									//cell.setHorizontalAlignment(Element.PHRASE);
									datatable.addCell(cell);
									
															
									cell = new PdfPCell(new Phrase(bcwtHotListReport.getBenefitName()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									//cell.setHorizontalAlignment(Element.PHRASE);
									datatable.addCell(cell);
									
									
									cell = new PdfPCell(new Phrase(bcwtHotListReport.getRegion()));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									datatable.addCell(cell);
									cell = new PdfPCell(new Phrase(String.valueOf(bcwtHotListReport.getCount())));
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									datatable.addCell(cell);
									
								
								
								
							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		e.printStackTrace();
			    		throw new Exception("Error in generating the order summary PDF Document:"+e);
			    	}finally{
			    		document.close();
			    	}
					return  baos;
			  }
			 
			 public static ByteArrayOutputStream generateTMAIssueReportPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request)throws Exception{
			 		String MY_NAME = ME + "generatePartnerNewCardReportPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	PdfPCell cell=null;
					PdfPTable datatable = null;
					boolean isFileGenerated = false;
			    	try {
			    		PdfWriter.getInstance(document,baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);
						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
						docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	
				    	
				    	
				    	Paragraph docDate = new Paragraph();
				    	docDate.setAlignment(Element.ALIGN_LEFT);
				    	docDate.add("Report Generated On: "+Util.getDate());
					    document.add(docDate);
				    	document.add(Chunk.NEWLINE);
				    	
				    	String dateRange = (String) request.getAttribute("ISSUE_D_RANGE");
				    	
				    	if(!Util.isBlankOrNull(dateRange)){
				    	Paragraph docDateRange = new Paragraph();
				    	docDateRange.setAlignment(Element.ALIGN_LEFT);
				    	docDateRange.add("Report Date Range: "+dateRange);
					    document.add(docDateRange);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	
				    	
				    	datatable= new PdfPTable(10);
				    	datatable.getDefaultCell().setBorderWidth(1);
				    	
				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter
									.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//	cell.setNoWrap(true);
								datatable.addCell(cell);
							}
				    	}
					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter.hasNext();) {
								BcwtsPartnerIssueDTO bcwtsPartnerIssueDTO =(BcwtsPartnerIssueDTO) iter.next();
								cell = new PdfPCell(new Phrase(bcwtsPartnerIssueDTO.getSerialnumber()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtsPartnerIssueDTO.getMemberid()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtsPartnerIssueDTO.getCompanyname()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtsPartnerIssueDTO.getRegion()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								
								cell = new PdfPCell(new Phrase(bcwtsPartnerIssueDTO.getCreatedby()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								
								cell = new PdfPCell(new Phrase(bcwtsPartnerIssueDTO.getCreationdate().toString()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								
								cell = new PdfPCell(new Phrase(bcwtsPartnerIssueDTO.getIssuestatus()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								
								cell = new PdfPCell(new Phrase(Util.removeHtmlFormatting(bcwtsPartnerIssueDTO.getIssuedescription())));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								
								cell = new PdfPCell(new Phrase(bcwtsPartnerIssueDTO.getResolution()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								
								cell = new PdfPCell(new Phrase(bcwtsPartnerIssueDTO.getAdminName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		e.printStackTrace();
			    		throw new Exception("Error in generating the order summary PDF Document:"+e);
			    	}finally{
			    		document.close();
			    	}
					return  baos;
			  }
			 
			 
			 public static ByteArrayOutputStream generateActiveBenefitPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request,Map<String,Integer> productName)throws Exception{
			 		String MY_NAME = ME + "generateActiveBenefitPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	PdfPCell cell=null;
					PdfPTable datatable = null;
					boolean isFileGenerated = false;
					int countMonthValue = 0;
				    int countYearValue = 0;
				    String companyName = "";
				    String currentDate = "";
			    	try {
			    		Font[] fonts = new Font[4];
			    		fonts[0] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
			    		fonts[1] = FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLD);
			    		fonts[2] = FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD);
			    		fonts[3] = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
			    		if (null != request.getAttribute("COMPANY_NAME_REPORT")) {
			    		      companyName = request.getAttribute("COMPANY_NAME_REPORT").toString();
			    		}

			    		currentDate = Util.getCurrentDate();

			    		PdfWriter.getInstance(document,baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD);
						document.setPageCount(2);
						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
						docHeaderPara.setFont(font);
						docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	
				    	for(Iterator L = reportList. iterator();L.hasNext() ;) {
			 	        	PartnerReportSearchDTO partnerReportSearchDTO = (PartnerReportSearchDTO) L.next();
			 	        	countMonthValue = partnerReportSearchDTO.getCountMonth();
			 	        	countYearValue = partnerReportSearchDTO.getCountYear();
			 			}
				    	
				    	if(null != request.getAttribute("NAME")){
				    		String name = (String)request.getAttribute("NAME");
				    	Paragraph docCompanyNamePara = new Paragraph();
				    	docCompanyNamePara.setAlignment(Element.ALIGN_LEFT);
				    	docCompanyNamePara.add(" TMA/Company Name :"+name);
				    	document.add(docCompanyNamePara);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	
				    	Paragraph docCurrentDatePara = new Paragraph();
				    	docCurrentDatePara.setAlignment(Element.ALIGN_LEFT);
				    	docCurrentDatePara.add(" Report Generated Date :"+currentDate);
				    	document.add(docCurrentDatePara);
				    	document.add(Chunk.NEWLINE);
				    	
				    	if(null != productName){
				    	Paragraph docCountMPara = new Paragraph();
				    	docCountMPara.setAlignment(Element.ALIGN_LEFT);
				    	 for (Map.Entry<String, Integer> entry : productName.entrySet()){
							 if(entry.getValue() != 0){
								 docCountMPara.add(entry.getKey()+" Count:"+entry.getValue());
								 docCountMPara.add(Chunk.NEWLINE);
							    	
							 }
				    	 }
				    	 document.add(docCountMPara);
					     document.add(Chunk.NEWLINE);
				    	}
				    	/*docCountPara.add(" Active Annual Pass Count :"+countYearValue);
				    	document.add(docCountPara);
				    	document.add(Chunk.NEWLINE);
				    	Paragraph docCountMPara = new Paragraph();
				    	docCountMPara.setAlignment(Element.ALIGN_LEFT);
				    	docCountMPara.add(" Active Monthly Pass Count :"+countMonthValue);
				    	document.add(docCountMPara);
				    	document.add(Chunk.NEWLINE);
				    	*/
				    	datatable= new PdfPTable(6);
				    	datatable.getDefaultCell().setBorderWidth(1);
				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter
									.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field,fonts[2]));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Font.BOLD);

								//cell.setNoWrap(true);
								datatable.addCell(cell);
								datatable.setHorizontalAlignment(Font.ITALIC);
							}
				    	}
					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter
									.hasNext();) {
								PartnerReportSearchDTO partnerReportSearchDTO = (PartnerReportSearchDTO) iter.next();
								cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getCompanyName(),fonts[0]));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								
								
								cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getFirstName(),fonts[0]));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getLastName(),fonts[0]));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getCustomerMemberId()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getCardSerialNo()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getBenefitType()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		e.printStackTrace();
			    		throw new Exception("Error in generating the order summary PDF Document:"+e);
			    	}finally{
			    		document.close();
			    	}
					return  baos;
			  }

			 //------------------------------Sunil Code---------------------------------------------------
			 public static ByteArrayOutputStream generateActiveBenefitPDF_New(String reportHeading,List fieldList,List reportList,HttpServletRequest request, String total, String tmaname)throws Exception{
			 		String MY_NAME = ME + "generateActiveBenefitPDF_New:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator New");
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	PdfPCell cell=null;
					PdfPTable datatable = null;
					boolean isFileGenerated = false;
					int countMonthValue = 0;
				    int countYearValue = 0;
				    String companyName = "";
				    String currentDate = "";
			    	try {
			    		Font[] fonts = new Font[4];
			    		fonts[0] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
			    		fonts[1] = FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLD);
			    		fonts[2] = FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD);
			    		fonts[3] = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
			    		if (null != request.getAttribute("COMPANY_NAME_REPORT")) {
			    		      companyName = request.getAttribute("COMPANY_NAME_REPORT").toString();
			    		}

			    		currentDate = Util.getCurrentDate();

			    		PdfWriter.getInstance(document,baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD);
						document.setPageCount(2);
						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
						docHeaderPara.setFont(font);
						docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	Paragraph docCountPara = new Paragraph();
				    	Paragraph docCountPara1 = new Paragraph();
				    	docCountPara.setAlignment(Element.ALIGN_LEFT);
				    	for(Iterator L = reportList. iterator();L.hasNext() ;) {
			 	        	PartnerReportSearchDTO partnerReportSearchDTO = (PartnerReportSearchDTO) L.next();
			 	        	countMonthValue = partnerReportSearchDTO.getCountMonth();
			 	        	countYearValue = partnerReportSearchDTO.getCountYear();
			 			}
				    	/*Paragraph docCompanyNamePara = new Paragraph();
				    	docCompanyNamePara.setAlignment(Element.ALIGN_LEFT);
				    	docCompanyNamePara.add(" Company Name :"+companyName);
				    	document.add(docCompanyNamePara);
				    	document.add(Chunk.NEWLINE);
				    	*/
				    	Paragraph docCurrentDatePara = new Paragraph();
				    	docCurrentDatePara.setAlignment(Element.ALIGN_LEFT);
				    	docCurrentDatePara.add(" Report Generated Date :"+currentDate);
				    	document.add(docCurrentDatePara);
				    	document.add(Chunk.NEWLINE);
				    	docCountPara.add(" Active Annual Pass Count :"+total);
				    	document.add(docCountPara);
				    	document.add(Chunk.NEWLINE);

				    	docCountPara1.add(" Tma Name :"+tmaname);
				    	document.add(docCountPara1);
				    	document.add(Chunk.NEWLINE);
				    	/*
				    	Paragraph docCountMPara = new Paragraph();
				    	docCountMPara.setAlignment(Element.ALIGN_LEFT);
				    	docCountMPara.add(" Active Monthly Pass Count :"+countMonthValue);
				    	document.add(docCountMPara);
				    	document.add(Chunk.NEWLINE);
				    	*/
				    	datatable= new PdfPTable(2);
				    	datatable.getDefaultCell().setBorderWidth(1);
				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter
									.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field,fonts[2]));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Font.BOLD);

								//cell.setNoWrap(true);
								datatable.addCell(cell);
								datatable.setHorizontalAlignment(Font.ITALIC);
							}
				    	}
					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter
									.hasNext();) {


								PartnerReportSearchDTO partnerReportSearchDTO = (PartnerReportSearchDTO) iter.next();


								cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getCompanyName(),fonts[0]));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);


								cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getCardcount()+"",fonts[0]));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);


								/*cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getCardSerialNo()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getBenefitType()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								*/
							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		e.printStackTrace();
			    		throw new Exception("Error in generating the order summary PDF Document:"+e);
			    	}finally{
			    		document.close();
			    	}
					return  baos;
			  }



			 //-------------------------------------------------------------------------------------------


			 //methos for partner Upcomng Monthly Report
			 public static ByteArrayOutputStream generatePartnerUpcomingMonthlyReportPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request,ReportForm reportForm, Map<String,Integer> productName)throws Exception{
			 		String MY_NAME = ME + "generatePartnerUpcomingMonthlyReportPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	PdfPCell cell=null;
					PdfPTable datatable = null;
					boolean isFileGenerated = false;
					String currentDate = "";
			    	try {
			    		PdfWriter.getInstance(document,baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);
						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
						docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);

				    	if(null != request.getAttribute("NAME")){
				    		String name = (String)request.getAttribute("NAME");
				    		Paragraph docCurrentDatePara = new Paragraph();
					    	docCurrentDatePara.setAlignment(Element.ALIGN_LEFT);
					    	docCurrentDatePara.add(" TMA/Company Name :"+name);
					    	document.add(docCurrentDatePara);
					    	document.add(Chunk.NEWLINE);
				    	}
				    	
				    	currentDate = Util.getCurrentDate();
				    	Paragraph docCurrentDatePara = new Paragraph();
				    	docCurrentDatePara.setAlignment(Element.ALIGN_LEFT);
				    	docCurrentDatePara.add(" Report Generated Date :"+currentDate);
				    	document.add(docCurrentDatePara);
				    	document.add(Chunk.NEWLINE);
				    	 String actSize = (String)request.getAttribute("AccountCount");
				    	Paragraph totalCard = new Paragraph();
				    	totalCard.setAlignment(Element.ALIGN_LEFT);
				    	totalCard.add("Total Card for the Account :"+actSize);
				    	document.add(totalCard);
				    	document.add(Chunk.NEWLINE);
				    	 String yesSize = (String)request.getAttribute("YesINQueue");
				    	Paragraph totalNumberofActivatedCards = new Paragraph();
				    	totalNumberofActivatedCards.setAlignment(Element.ALIGN_LEFT);
				    	totalNumberofActivatedCards.add("Total Number of Benefits in the Queue for Activation:"+yesSize);
				    	document.add(totalNumberofActivatedCards);
				    	document.add(Chunk.NEWLINE);
				    	
				    	 String size = (String)request.getAttribute("NoINQueue");
					    	Paragraph totalNumberofDeActivatedCards = new Paragraph();
					    	totalNumberofDeActivatedCards.setAlignment(Element.ALIGN_LEFT);
					    	totalNumberofDeActivatedCards.add("Total Number of Benefits in the Queue for Dectivation:"+size);
					    	document.add(totalNumberofDeActivatedCards);
					    	document.add(Chunk.NEWLINE);
				    	
					    	 String newSize = (String)request.getAttribute("NewCardCount");
				    	Paragraph totalNumberofNewCardsforAccount = new Paragraph();
				    	totalNumberofNewCardsforAccount.setAlignment(Element.ALIGN_LEFT);
				    	totalNumberofNewCardsforAccount.add("Total Number of New Cards in the Queue:"+newSize);
				    	document.add(totalNumberofNewCardsforAccount);
				    	document.add(Chunk.NEWLINE);
				    	
				    	int totalCount = 0;
				    	Paragraph docCountMPara = new Paragraph();
				    	docCountMPara.setAlignment(Element.ALIGN_LEFT);
				    	 for (Map.Entry<String, Integer> entry : productName.entrySet()){
							 if(entry.getValue() != 0){
								 docCountMPara.add(entry.getKey()+" Count:"+entry.getValue());
								 docCountMPara.add(Chunk.NEWLINE);
								 totalCount = totalCount +  entry.getValue();	
							 }
				    	 }
				    	 docCountMPara.add("Total number of benefits for next month:"+totalCount);
						 docCountMPara.add(Chunk.NEWLINE);
				    	 document.add(docCountMPara);
					     document.add(Chunk.NEWLINE);
				    	

				    	datatable= new PdfPTable(9);
				    	datatable.getDefaultCell().setBorderWidth(1);
				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter
									.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
							}
				    	}
					   if(null != reportList && !reportList.isEmpty()) {
						   for (Iterator iter = reportList.iterator(); iter.hasNext();) {
							   UpCommingMonthBenefitDTO orderReportDTO = (UpCommingMonthBenefitDTO) iter.next();
								
							   cell = new PdfPCell(new Phrase(orderReportDTO.getCompanyName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
							   
							   cell = new PdfPCell(new Phrase(orderReportDTO.getCardSerialNumber()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setHorizontalAlignment(Element.PHRASE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(orderReportDTO.getFirstName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(orderReportDTO.getLastName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(orderReportDTO.getMemberId()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(orderReportDTO.getBenefitName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(orderReportDTO.getIsBenefitActivated()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								if(null != orderReportDTO.getDateModified())
								cell = new PdfPCell(new Phrase(orderReportDTO.getDateModified().toString()));
								else
									cell = new PdfPCell(new Phrase("NA"));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(orderReportDTO.getRegion()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
						   }
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		e.printStackTrace();
			    		throw new Exception("Error in generating the order summary PDF Document:"+e);
			    	}finally{
			    		document.close();
			    	}
					return  baos;
			  }

			 /********* General Ledger Report Starts Here ***********/
			 public static ByteArrayOutputStream generateGeneralLedgerPDF(
					 String reportHeading,List fieldList,List reportList,HttpServletRequest request) throws Exception {

			 		String MY_NAME = ME + "generateGeneralLedgerPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");

			    	PdfPCell cell=null;
					PdfPTable datatable = null;
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

					try {
						PdfWriter.getInstance(document, baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);

						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
				        docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	
				    	if(null != request.getAttribute("GL_DATE_RANGE")){
				    	Paragraph docDateRange = new Paragraph();
				    	docDateRange.setAlignment(Element.ALIGN_LEFT);
				    	docDateRange.add("Report Date Range :"+request.getAttribute("GL_DATE_RANGE"));
					    document.add(docDateRange);
				    	document.add(Chunk.NEWLINE);
				    	}
				    	datatable= new PdfPTable(fieldList.size());
				    	datatable.getDefaultCell().setBorderWidth(1);

				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
							}
				    	}

					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter.hasNext();) {
								BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();
								cell = new PdfPCell(new Phrase(bcwtReportDTO.getAccountNumber()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderRequestId()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderType()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtReportDTO.getCustomerName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtReportDTO.getApprovalCode()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtReportDTO.getTransactionDate()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtReportDTO.getProducts()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtReportDTO.getPrice()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderQty()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
								cell = new PdfPCell(new Phrase(bcwtReportDTO.getAmount()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								//cell.setNoWrap(true);
								datatable.addCell(cell);
							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		throw new Exception("Error in generating General Ledger PDF document: " + Util.getFormattedStackTrace(e));
			    	}finally{
			    		document.close();
			    	}
					return baos;
			  }

			 /********* General Ledger Report Ends Here ***********/

			 /***************** OF - Starts here **********************/

			 public static ByteArrayOutputStream generateOrderSummaryPDF(String reportHeading,List fieldList,List reportList)throws Exception{
			 		String MY_NAME = ME + "generateOrderSummaryPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");

			 		PdfPCell cell=null;
					PdfPTable datatable = null;
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

					try {
						PdfWriter.getInstance(document, baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);

						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
				        docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);
				    	
				    	Paragraph docDate = new Paragraph();
				    	docDate.setAlignment(Element.ALIGN_LEFT);
				    	docDate.add("Report Generated On: "+Util.getDate());
					    document.add(docDate);
				    	document.add(Chunk.NEWLINE);

				    	datatable= new PdfPTable(fieldList.size());
				    	datatable.getDefaultCell().setBorderWidth(1);

				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
							}
				    	}

					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter.hasNext();) {
								BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderType()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getNoOfOrders()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderQty()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderValue()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getShippingAmount()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getTotal()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
							}
						} else {
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		throw new Exception("Error in generating order summary PDF document: " + e);
			    	}finally{
			    		document.close();
			    	}
					return baos;
				}

		 public static ByteArrayOutputStream generateOutstandingOrdersPDF(
				 String reportHeading,List fieldList,List reportList) throws Exception {

		 		String MY_NAME = ME + "generateOutstandingOrdersPDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");

		    	PdfPCell cell=null;
				PdfPTable datatable = null;
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

				try {
					PdfWriter.getInstance(document, baos);
					document.open();
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
					document.setPageCount(2);

					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER);
			        docHeaderPara.add(reportHeading);
				    document.add(docHeaderPara);
			    	document.add(Chunk.NEWLINE);

			    	datatable= new PdfPTable(fieldList.size());
			    	datatable.getDefaultCell().setBorderWidth(1);

			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
						}
			    	}

				   if(null != reportList && !reportList.isEmpty()) {
						for (Iterator iter = reportList.iterator(); iter.hasNext();) {
							BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderRequestId()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getCardSerialNo()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getFirstName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getLastName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getEmail()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getPhoneNumber()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
							/*
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getZip()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
							*/
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderDate()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderStatus()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderType()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
						}
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);
					document.add(Chunk.NEWLINE);
				}catch(Exception e){
		    		throw new Exception("Error in generating outstanding orders PDF document: " + e);
		    	}finally{
		    		document.close();
		    	}
				return baos;
		  }

		 public static ByteArrayOutputStream generateQualityAssuranceSummaryPDF(
				 String reportHeading,List fieldList,List reportList) throws Exception {

		 		String MY_NAME = ME + "generateQualityAssuranceSummaryPDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");

		    	PdfPCell cell=null;
				PdfPTable datatable = null;
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

				try {
					PdfWriter.getInstance(document, baos);
					document.open();
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
					document.setPageCount(2);

					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER);
			        docHeaderPara.add(reportHeading);
				    document.add(docHeaderPara);
			    	document.add(Chunk.NEWLINE);

			    	datatable= new PdfPTable(fieldList.size());
			    	datatable.getDefaultCell().setBorderWidth(1);

			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
						}
			    	}

				   if(null != reportList && !reportList.isEmpty()) {
						for (Iterator iter = reportList.iterator(); iter.hasNext();) {
							BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getNoOfPassed()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getNoOfFailed()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
						}
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);
					document.add(Chunk.NEWLINE);
				}catch(Exception e){
		    		throw new Exception("Error in generating outstanding orders PDF document: " + e);
		    	}finally{
		    		document.close();
		    	}
				return baos;
		  }

		 public static ByteArrayOutputStream generateReturnedOrdersPDF(
				 String reportHeading,List fieldList,List reportList) throws Exception {

		 		String MY_NAME = ME + "generateReturnedOrdersPDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");

		    	PdfPCell cell=null;
				PdfPTable datatable = null;
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

				try {
					PdfWriter.getInstance(document, baos);
					document.open();
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
					document.setPageCount(2);

					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER);
			        docHeaderPara.add(reportHeading);
				    document.add(docHeaderPara);
			    	document.add(Chunk.NEWLINE);

			    	datatable= new PdfPTable(fieldList.size());
			    	datatable.getDefaultCell().setBorderWidth(1);

			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
						}
			    	}

				   if(null != reportList && !reportList.isEmpty()) {
						for (Iterator iter = reportList.iterator(); iter.hasNext();) {
							BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderRequestId()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getCardSerialNo()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderDate()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getReturnedDate()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
							/*
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getNoOfDeliveryAttempts()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
							*/
							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderType()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
						}
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);
					document.add(Chunk.NEWLINE);
				}catch(Exception e){
		    		throw new Exception("Error in generating outstanding orders PDF document: " + e);
		    	}finally{
		    		document.close();
		    	}
				return baos;
		  }

		 public static ByteArrayOutputStream generateCancelledOrderPDF(
			String reportHeading,List fieldList,List reportList) throws Exception {

	 		String MY_NAME = ME + "generateCancelledOrderPDF:";
	 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");

	    	PdfPCell cell=null;
			PdfPTable datatable = null;
	 		Document document = new Document(PageSize.A4.rotate());
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

			try {
				PdfWriter.getInstance(document, baos);
				document.open();
				Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
				document.setPageCount(2);

				Paragraph docHeaderPara = new Paragraph();
				docHeaderPara.setAlignment(Element.ALIGN_CENTER);
		        docHeaderPara.add(reportHeading);
			    document.add(docHeaderPara);
		    	document.add(Chunk.NEWLINE);

		    	datatable= new PdfPTable(fieldList.size());
		    	datatable.getDefaultCell().setBorderWidth(1);

		    	if(null != fieldList && !fieldList.isEmpty()) {
					for (Iterator iter = fieldList.iterator(); iter.hasNext();) {
						String field = (String) iter.next();
						cell = new PdfPCell(new Phrase(field));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);
					}
		    	}

			   if(null != reportList && !reportList.isEmpty()) {
					for (Iterator iter = reportList.iterator(); iter.hasNext();) {
						BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();

						cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderRequestId()));
						cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);

						cell = new PdfPCell(new Phrase(bcwtReportDTO.getCardSerialNo()));
						cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);

						cell = new PdfPCell(new Phrase(bcwtReportDTO.getFirstName()));
						cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);

						cell = new PdfPCell(new Phrase(bcwtReportDTO.getLastName()));
						cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);

						cell = new PdfPCell(new Phrase(bcwtReportDTO.getCancelledDate()));
						cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);

						cell = new PdfPCell(new Phrase(bcwtReportDTO.getMediasalesAdmin()));
						cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);

						cell = new PdfPCell(new Phrase(bcwtReportDTO.getReasonType()));
						cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);

						cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderType()));
						cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						datatable.addCell(cell);
					}
				}else{
					datatable.addCell("No records Found");
				}
				document.add(datatable);
				document.add(Chunk.NEWLINE);
			}catch(Exception e){
	    		throw new Exception("Error in generating cancelled orders PDF document: " + e);
	    	}finally{
	    		document.close();
	    	}
			return baos;
		}

		 public static ByteArrayOutputStream generateQualityDetailsPDF(
				 String reportHeading,List fieldList,List reportList) throws Exception {

		 		String MY_NAME = ME + "generateQualityDetailedPDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");

		    	PdfPCell cell=null;
				PdfPTable datatable = null;
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

				try {
					PdfWriter.getInstance(document, baos);
					document.open();
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
					document.setPageCount(2);

					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER);
			        docHeaderPara.add(reportHeading);
				    document.add(docHeaderPara);
			    	document.add(Chunk.NEWLINE);

			    	datatable= new PdfPTable(fieldList.size());
			    	datatable.getDefaultCell().setBorderWidth(1);

			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
						}
			    	}

				   if(null != reportList && !reportList.isEmpty()) {
						for (Iterator iter = reportList.iterator(); iter.hasNext();) {
							BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderRequestId()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getFirstName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getLastName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getProcessedDate()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderType()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
						}
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);
					document.add(Chunk.NEWLINE);
				}catch(Exception e){
		    		throw new Exception("Error in generating QualityDetails PDF document: " + e);
		    	}finally{
		    		document.close();
		    	}
				return baos;
		  }

		 public static ByteArrayOutputStream generateSalesMetricsPDF(
				 String reportHeading,List fieldList,List reportList) throws Exception {

		 		String MY_NAME = ME + "generateSalesMetricsPDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");

		    	PdfPCell cell=null;
				PdfPTable datatable = null;
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

				try {
					PdfWriter.getInstance(document, baos);
					document.open();
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
					document.setPageCount(2);

					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER);
			        docHeaderPara.add(reportHeading);
				    document.add(docHeaderPara);
			    	document.add(Chunk.NEWLINE);

			    	datatable= new PdfPTable(fieldList.size());
			    	datatable.getDefaultCell().setBorderWidth(1);

			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
						}
			    	}

				   if(null != reportList && !reportList.isEmpty()) {
						for (Iterator iter = reportList.iterator(); iter.hasNext();) {
							BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getZip()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getNoOfCardsSold()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
						}
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);
					document.add(Chunk.NEWLINE);
				}catch(Exception e){
		    		throw new Exception("Error in generating SalesMetrics PDF document: " + e);
		    	}finally{
		    		document.close();
		    	}
				return baos;
		  }

		   public static ByteArrayOutputStream generateMediaSalesMetricsPDF(
					 String reportHeading,List fieldList,List reportList) throws Exception {

			 		String MY_NAME = ME + "generateMediaSalesMetricsPDF:";
			 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");

			    	PdfPCell cell=null;
					PdfPTable datatable = null;
			 		Document document = new Document(PageSize.A4.rotate());
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

					try {
						PdfWriter.getInstance(document, baos);
						document.open();
						Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
						document.setPageCount(2);

						Paragraph docHeaderPara = new Paragraph();
						docHeaderPara.setAlignment(Element.ALIGN_CENTER);
				        docHeaderPara.add(reportHeading);
					    document.add(docHeaderPara);
				    	document.add(Chunk.NEWLINE);

				    	datatable= new PdfPTable(fieldList.size());
				    	datatable.getDefaultCell().setBorderWidth(1);

				    	if(null != fieldList && !fieldList.isEmpty()) {
							for (Iterator iter = fieldList.iterator(); iter.hasNext();) {
								String field = (String) iter.next();
								cell = new PdfPCell(new Phrase(field));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);
							}
				    	}

					   if(null != reportList && !reportList.isEmpty()) {
							for (Iterator iter = reportList.iterator(); iter.hasNext();) {
								BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderRequestId()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderDate()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getFirstName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getLastName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getQaFirstName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getQaLastName()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

								cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderType()));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								datatable.addCell(cell);

							}
						}else{
							datatable.addCell("No records Found");
						}
						document.add(datatable);
						document.add(Chunk.NEWLINE);
					}catch(Exception e){
			    		throw new Exception("Error in generating outstanding orders PDF document: " + e);
			    	}finally{
			    		document.close();
			    	}
					return baos;
			  }


		   public static ByteArrayOutputStream generateRevenueReportPDF(
				 String reportHeading,List fieldList,List reportList,
				 List revenueSummaryFieldList, List revenueSummaryReportList) throws Exception {

		 		String MY_NAME = ME + "generateRevenueReportPDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");

		    	PdfPCell cell=null;
				PdfPTable datatable = null;
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

		    	PdfPTable datatableForSummary = null;

				try {
					PdfWriter.getInstance(document, baos);
					document.open();
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
					document.setPageCount(2);

			    	/********* Revenue Summary Report Starts Here ***********/

			    	Paragraph docHeaderParaForSummary = new Paragraph();
			    	docHeaderParaForSummary.setAlignment(Element.ALIGN_CENTER);
			    	docHeaderParaForSummary.add("Revenue Summary Report");
				    document.add(docHeaderParaForSummary);
			    	document.add(Chunk.NEWLINE);

			    	datatableForSummary = new PdfPTable(revenueSummaryFieldList.size());
			    	datatableForSummary.getDefaultCell().setBorderWidth(1);

			    	if(null != revenueSummaryFieldList && !revenueSummaryFieldList.isEmpty()) {
						for (Iterator iter = revenueSummaryFieldList.iterator(); iter.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatableForSummary.addCell(cell);
						}
			    	}

			    	if(null != revenueSummaryReportList && !revenueSummaryReportList.isEmpty()) {
						for (Iterator iter = revenueSummaryReportList.iterator(); iter.hasNext();) {
							BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getCreditCardType()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatableForSummary.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getAmount()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatableForSummary.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getNoOfOrders()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatableForSummary.addCell(cell);
						}
			    	} else {
			    		datatableForSummary.addCell("No records Found");
					}
			    	document.add(datatableForSummary);
					document.add(Chunk.NEWLINE);

			    	/********* Revenue Summary Report Ends Here ***********/

					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER);
			        docHeaderPara.add(reportHeading);
				    document.add(docHeaderPara);
			    	document.add(Chunk.NEWLINE);

			    	datatable= new PdfPTable(fieldList.size());
			    	datatable.getDefaultCell().setBorderWidth(1);

			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
						}
			    	}

				   if(null != reportList && !reportList.isEmpty()) {
						for (Iterator iter = reportList.iterator(); iter.hasNext();) {
							BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderRequestId()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getTransactionDate()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getCreditCardType()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getAmount()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getCreditCardLast4()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getApprovalCode()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderModule()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getProducts()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getCustomerName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);

							cell = new PdfPCell(new Phrase(bcwtReportDTO.getOrderType()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
						}
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);
					document.add(Chunk.NEWLINE);
				}catch(Exception e){
		    		throw new Exception("Error in generating revenue report PDF document: " + e.getMessage());
		    	}finally{
		    		document.close();
		    	}
				return baos;
		  }
		   
		   
		   public static ByteArrayOutputStream generateMonthlyUsagePDF(String reportHeading,List fieldList,List reportList, HttpServletRequest request)throws Exception{
		 		String MY_NAME = ME + "generateMonthlyUsagePDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    	PdfPCell cell=null;
				PdfPTable datatable = null;
				boolean isFileGenerated = false;
				String companyName = "";
				String currentDate = "";
		    	try {
		    		
		    		if (null != request.getAttribute("COMPANY_NAME_REPORT")) {
		    		      companyName = request.getAttribute("COMPANY_NAME_REPORT").toString();
		    		}
		    		currentDate = Util.getCurrentDate();
		    		
		    		PdfWriter.getInstance(document,baos);
					document.open();			
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD);		
					Font[] fonts = new Font[4];
		    		fonts[0] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
		    		fonts[1] = FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLD);
		    		fonts[2] = FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD);
		    		fonts[3] = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
					document.setPageCount(2);
					
					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER); 
					docHeaderPara.add(reportHeading);
					docHeaderPara.setFont(font);
				    document.add(docHeaderPara);
			    	document.add(Chunk.NEWLINE);
					Paragraph docCompanyNamePara = new Paragraph();
			    	docCompanyNamePara.setAlignment(Element.ALIGN_LEFT);
			    	docCompanyNamePara.add(" Company Name :"+companyName);
			    	document.add(docCompanyNamePara);
			    	document.add(Chunk.NEWLINE);
			    	Paragraph docCurrentDatePara = new Paragraph();
			    	docCurrentDatePara.setAlignment(Element.ALIGN_LEFT);
			    	docCurrentDatePara.add(" Report Generated Date :"+currentDate);
			    	document.add(docCurrentDatePara);
			    	document.add(Chunk.NEWLINE);
			    	
					
			    	datatable= new PdfPTable(4);
			    	datatable.getDefaultCell().setBorderWidth(1);
			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter
								.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field,fonts[2]));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
						}
			    	}
				   if(null != reportList && !reportList.isEmpty()) {
						for (Iterator iter = reportList.iterator(); iter
								.hasNext();) {
							PartnerReportSearchDTO partnerReportSearchDTO = (PartnerReportSearchDTO) iter.next();
							cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getCardSerialNo(),fonts[0]));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getBenefitType()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(false);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getBillingMonth()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(partnerReportSearchDTO.getMonthlyUsage()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setNoWrap(true);
							datatable.addCell(cell);
						}
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);		
					document.add(Chunk.NEWLINE);
				}catch(Exception e){	    		
		    		e.printStackTrace();
		    		throw new Exception("Error in generating the order summary PDF Document:"+e);
		    	}finally{
		    		document.close();
		    	}  
				return  baos;	
		  }
			 /***************** OF - Ends here **********************/

		   public static ByteArrayOutputStream generateUpassUpcomingMonthlyReportPDF(String reportHeading,List fieldList,List reportList,HttpServletRequest request, ReportForm reportForm)throws Exception{
		 		String MY_NAME = ME + "generatePartnerUpcomingMonthlyReportPDF:";
		 		BcwtsLogger.info(MY_NAME + "entering into pdf generator");
		 		Document document = new Document(PageSize.A4.rotate());
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    	PdfPCell cell=null;
				PdfPTable datatable = null;
				boolean isFileGenerated = false;
				String currentDate = "";
		    	try {
		    		PdfWriter.getInstance(document,baos);
					document.open();
					Font font= new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.NORMAL);
					document.setPageCount(2);
					Paragraph docHeaderPara = new Paragraph();
					docHeaderPara.setAlignment(Element.ALIGN_CENTER);
					docHeaderPara.add(reportHeading);
				    document.add(docHeaderPara);
			    	document.add(Chunk.NEWLINE);

			    	currentDate = Util.getCurrentDate();
			    	Paragraph docCurrentDatePara = new Paragraph();
			    	docCurrentDatePara.setAlignment(Element.ALIGN_LEFT);
			    	docCurrentDatePara.add(" Report Generated Date :"+currentDate);
			    	document.add(docCurrentDatePara);
			    	document.add(Chunk.NEWLINE);
			    	
			    	Paragraph docTotalCards = new Paragraph();
			    	docTotalCards.setAlignment(Element.ALIGN_LEFT);
			    	docTotalCards.add("Total Number of Cards for Account:"+reportForm.getUpCommingReportList().size());
			    	document.add(docTotalCards);
			    	
			    	Paragraph docTotalCardsInTheQueue = new Paragraph();
			    	docTotalCardsInTheQueue.setAlignment(Element.ALIGN_LEFT);
			    	docTotalCardsInTheQueue.add("Total Number of Cards in the Queue for Activation/Deactivation:"+ (reportForm.getUpCommingReportActList().size() + reportForm.getUpCommingReportDeactList().size()));
			    	document.add(docTotalCardsInTheQueue);
			    	
			    	Paragraph docTotalNewCardsInTheQueue = new Paragraph();
			    	docTotalNewCardsInTheQueue.setAlignment(Element.ALIGN_LEFT);
			    	docTotalNewCardsInTheQueue.add("Total Number of New Cards in the Queue:"+reportForm.getUpCommingNewCardList().size());
			    	document.add(docTotalNewCardsInTheQueue);
			    	document.add(Chunk.NEWLINE);


			    	datatable= new PdfPTable(4);
			    	datatable.getDefaultCell().setBorderWidth(1);
			    	if(null != fieldList && !fieldList.isEmpty()) {
						for (Iterator iter = fieldList.iterator(); iter
								.hasNext();) {
							String field = (String) iter.next();
							cell = new PdfPCell(new Phrase(field));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setNoWrap(true);
							datatable.addCell(cell);
						}
			    	}
				   if(null != reportList && !reportList.isEmpty()) {
					   for (Iterator iter = reportList.iterator(); iter.hasNext();) {
						   UpCommingMonthBenefitDTO orderReportDTO = (UpCommingMonthBenefitDTO) iter.next();
							cell = new PdfPCell(new Phrase(orderReportDTO.getCardSerialNumber()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							//cell.setHorizontalAlignment(Element.PHRASE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(orderReportDTO.getMemberName()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(orderReportDTO.getMemberId()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
							cell = new PdfPCell(new Phrase(orderReportDTO.getIsBenefitActivated()));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							datatable.addCell(cell);
					   }
					}else{
						datatable.addCell("No records Found");
					}
					document.add(datatable);
					document.add(Chunk.NEWLINE);
				}catch(Exception e){
		    		e.printStackTrace();
		    		throw new Exception("Error in generating the order summary PDF Document:"+e);
		    	}finally{
		    		document.close();
		    	}
				return  baos;
		  }

}