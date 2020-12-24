package com.marta.admin.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

import com.lowagie.text.Chunk;
import com.marta.admin.business.ReportManagement;
import com.marta.admin.dao.ReportDAO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtReportDTO;
import com.marta.admin.dto.BcwtReportSearchDTO;
import com.marta.admin.dto.BcwtsPartnerIssueDTO;
import com.marta.admin.dto.CompanyInfoDTO;
import com.marta.admin.dto.DisplayProductSummaryDTO;
import com.marta.admin.dto.ProductDetailsReportDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.ExcelGenerator;
import com.marta.admin.utils.PdfGenerator;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;
import com.marta.admin.dto.PartnerReportSearchDTO;

/**
 * Action class for handling request for Report Management
 * 
 * @author Sowjanya
 */
public class ReportAction  extends DispatchAction {
	
	final String ME = "ReportAction: ";
	
	public ActionForward generateTMAHotlistReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "generateTMAHotlistReport: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partners");
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		String returnValue = "tmahotlistReport";
		ReportForm reportForm =(ReportForm) form;
		List fieldList = new ArrayList();
	    ReportManagement reportManagement = new ReportManagement();
	    List reportList = new ArrayList();
		try {
			
			if (null != request.getParameter("reportFormat")) {
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			
			fieldList = new ArrayList();
			fieldList.add(PropertyReader.getValue(Constants.FIRST_NAME));
			fieldList.add(PropertyReader.getValue(Constants.LAST_NAME));
			fieldList.add("Breeze Card");
			fieldList.add("Company Name");
			fieldList.add("Hotlist Date");
			fieldList.add("Admin");
			
			if(!Util.isBlankOrNull(reportForm.getTmaId())){
				request.setAttribute("NAME", reportManagement.getTMAName((reportForm.getTmaId())));
			    session.setAttribute("NAME", reportManagement.getTMAName((reportForm.getTmaId())));
			}
			if(!Util.isBlankOrNull(reportForm.getToDate()) && !Util.isBlankOrNull(reportForm.getFromDate())){
				String dateRange = reportForm.getFromDate() +" to "+ reportForm.getToDate();
				session.setAttribute("HOTLIST_D_RANGE", dateRange);
				request.setAttribute("HOTLIST_D_RANGE", dateRange);
			}
			
				reportList = reportManagement.getTMAHotListReport(reportForm);
			//	reportForm.setReportFormat(Constants.HTML_TYPE);
				
			
				session.setAttribute(Constants.BREAD_CRUMB_NAME,
						Constants.BREADCRUMB_ADMIN_HOTLIST_REPORT);
			returnValue = "tmahotlistReport";
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
				session.setAttribute("TMA_HOT_LIST_REPORT", reportList);
			}
			if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
				baos = PdfGenerator.generateTMAHotlistReportPDF("TMA HOTLIST REPORT", fieldList, reportList,request);
				responseGenerate("application/pdf",
						"TMA_HOT_LIST_REPORT" + ".pdf", response, baos);
			}
			if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
				baos = ExcelGenerator.generateTMAHotlistReportExcel("TMA HOTLIST REPORT", fieldList, reportList,request);
				responseGenerate("application/xls",
						"TMA_HOT_LIST_REPORT" + ".xls", response, baos);
			}
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward generateProductSummaryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "generateProductSummaryReport: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partners");
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		String returnValue = "productSummaryReport";
		ReportForm reportForm =(ReportForm) form;
		List fieldList = null;
	    ReportManagement reportManagement = new ReportManagement();
	    List<DisplayProductSummaryDTO> reportList = new ArrayList<DisplayProductSummaryDTO>();
	    
	    Map<String,Integer> productName = new HashMap<String,Integer>();

		 productName.put(Constants.TMAMARTA10R,0);
		 		productName.put(Constants.TMAMARTA20R,0);
		 		productName.put(Constants.TMAMARTACALM,0);
		 		productName.put(Constants.TMACBLOCCALM,0);
		 		productName.put(Constants.TMAGWZ1EXP10R,0);
		 		productName.put(Constants.TMACBEXPCALM,0);
		 		productName.put(Constants.TMACBLOC10R,0);
		 		productName.put(Constants.TMAGWLOC10R,0);
		 		productName.put(Constants.TMAGW1EXPCALM,0);
		 		productName.put(Constants.TMAGWZ2EXP10R,0);
		 		productName.put(Constants.TMAGWZ2EXPCALM,0);
		 		productName.put(Constants.TMACBEXP20R,0);
		 		productName.put(Constants.TMAGWLOCCALM,0);
		 		productName.put(Constants.PARGRBlueZ10R,0);
				productName.put(Constants.PARGRGreenZ10R,0);
				productName.put(Constants.PARGRGreenZCalM,0);
				productName.put(Constants.PARGRBlueZCalM,0);
	    
		try {
			//String nextfareids ="";
			fieldList = new ArrayList();
			fieldList.add("Company Name");
			fieldList.add("Benefit Name");
			fieldList.add("Benefit Operator");
			fieldList.add("Autoload Count");
			
			if (null != request.getParameter("reportFormat")) {
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}		
			
			    reportList = reportManagement.getProductSummarytReport(reportForm);
			    Collections.sort(reportList);
			//	reportForm.setReportFormat(Constants.HTML_TYPE);
			if(null != reportForm.getFromDate() && null != reportForm.getToDate()){
			    String dateRange = reportForm.getFromDate() +" to "+ reportForm.getToDate();
			
				request.setAttribute("DATE_RANGE", dateRange);
				session.setAttribute("DATE_RANGE", dateRange);
			}
				session.setAttribute(Constants.BREAD_CRUMB_NAME,
						Constants.BREADCRUMB_PRODUCT_SUMMARY_REPORT);
			returnValue = "productSummaryReport";
			
			for (Map.Entry<String, Integer> entry : productName.entrySet()){
				for(DisplayProductSummaryDTO artnerReportSearchDTO:reportList){
			        if(null != artnerReportSearchDTO.getBenefitName()){     
					if(entry.getKey().equals(artnerReportSearchDTO.getBenefitName().trim())){
			            	productName.put(entry.getKey(),entry.getValue()+artnerReportSearchDTO.getCount()); 
			             }
			        }
		 }
		 }
			
			
			if(!Util.isBlankOrNull(reportForm.getTmaId())){
				session.setAttribute("NAME", reportManagement.getTMAName((reportForm.getTmaId())));
			request.setAttribute("NAME", reportManagement.getTMAName((reportForm.getTmaId())));
			}

              ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
				session.setAttribute("PRODUCT_SUMMARY_REPORT", reportList);
				session.setAttribute("PRODUCT_SUMMARY_COUNT", productName);
			}
			if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
				baos = PdfGenerator.generatepRODUCTReportPDF("PRODUCT SUMMARY REPORT", fieldList, reportList,request,productName);
				responseGenerate("application/pdf",
						"PRODUCT SUMMARY REPORT" + ".pdf", response, baos);
			}
			if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
				baos = ExcelGenerator.generateTMAProductExcel("PRODUCT SUMMARY REPORT", fieldList, reportList,request,productName);
				responseGenerate("application/xls",
						"PRODUCT SUMMARY REPORT" + ".xls", response, baos);
			}
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying product summary report page:"
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward generateProductDetailReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "generateProductDetailReport: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partners");
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		String returnValue = "productDetailReport";
		ReportForm reportForm =(ReportForm) form;
		List fieldList = null;
	    ReportManagement reportManagement = new ReportManagement();
	    List<ProductDetailsReportDTO> reportList = new ArrayList<ProductDetailsReportDTO>();
		try {
			//String nextfareids ="";
			fieldList = new ArrayList();
			fieldList.add("Company Name");
			fieldList.add("First Name");
			fieldList.add("Last Name");
			fieldList.add("Member Id");
			fieldList.add("Breeze Card");
			fieldList.add("Benefit Name");
			fieldList.add("Benefit Operator");
			fieldList.add("Benefit Quantity");
			fieldList.add("Transaction Type");
			
			if (null != request.getParameter("reportFormat")) {
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}		
			
			    reportList = reportManagement.getProductDetailsReport(reportForm);
			    Collections.sort(reportList);
			//	reportForm.setReportFormat(Constants.HTML_TYPE);
			if(null != reportForm.getFromDate() && null != reportForm.getToDate()){
			    String dateRange = reportForm.getFromDate() +" to "+ reportForm.getToDate();
			
				request.setAttribute("DATE_RANGE", dateRange);
				session.setAttribute("DATE_RANGE", dateRange);
			}
				session.setAttribute(Constants.BREAD_CRUMB_NAME,
						Constants.BREADCRUMB_PRODUCT_SUMMARY_REPORT);
			returnValue = "productDetailReport";
			
			if(!Util.isBlankOrNull(reportForm.getTmaId())){
				session.setAttribute("NAME", reportManagement.getTMAName((reportForm.getTmaId())));
			request.setAttribute("NAME", reportManagement.getTMAName((reportForm.getTmaId())));
			}

              ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
				session.setAttribute("PRODUCT_DETAIL_REPORT", reportList);
			}
			if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
				baos = PdfGenerator.generatepRODUCTReportPDFDetail("PRODUCT DETAIL REPORT", fieldList, reportList,request);
				responseGenerate("application/pdf",
						"PRODUCT DETAIL REPORT" + ".pdf", response, baos);
			}
			if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
				baos = ExcelGenerator.generateTMAProductExcelDetail("PRODUCT DETAIL REPORT", fieldList, reportList,request);
				responseGenerate("application/xls",
						"PRODUCT DETAIL REPORT" + ".xls", response, baos);
			}
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying product DETAIL report page:"
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward displayPartnerProductSummaryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayPartnerProductSummaryReport: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partnerss");
		HttpSession session = request.getSession(true);
		List tmaList = new ArrayList();
		String returnValue = "productSummaryReport";
		
		 ReportManagement reportManagement  = new ReportManagement();
		
		try {
		
			tmaList = reportManagement.getTMAList(); 	
			session.setAttribute("TMA_LIST", tmaList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_PRODUCT_SUMMARY_REPORT);
			
			returnValue = "productSummaryReport";
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for product Summary Report:"
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward displayPartnerProductDetailReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayPartnerProductDetailReport: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partnerss");
		HttpSession session = request.getSession(true);
		List tmaList = new ArrayList();
		String returnValue = "productDetailReport";
		
		 ReportManagement reportManagement  = new ReportManagement();
		
		try {
		
			tmaList = reportManagement.getTMAList(); 	
			session.setAttribute("TMA_LIST", tmaList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_PRODUCT_DETAIL_REPORT);
			
			returnValue = "productDetailReport";
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for Product Detail Report:"
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward displayTMAHotlistReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayTMAHotlistReport: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partnerss");
		HttpSession session = request.getSession(true);
		List tmaList = new ArrayList();
		String returnValue = "orderReport";
		
		 ReportManagement reportManagement  = new ReportManagement();
		
		try {
		
			tmaList = reportManagement.getTMAList(); 	
			session.setAttribute("TMA_LIST", tmaList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_DETAILED_TMA_HOTLIST_REPORT);
			
			returnValue = "tmaHotlist";
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward generateTMAIssueReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "generateTMAIssueReport: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partners");
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		String returnValue = "tmaIssueListReport";
		ReportForm reportForm =(ReportForm) form;
		List fieldList = new ArrayList();
		
	    ReportManagement reportManagement = new ReportManagement();
	    List<BcwtsPartnerIssueDTO> issueList = new ArrayList();
		try {
			String fromDate = request.getParameter("from");
			String toDate = request.getParameter("to");
			
			if (null != request.getParameter("reportFormat")) {
				reportFormat = request.getParameter("reportFormat");
			//	reportForm.setReportFormat(reportFormat);
			}
			
			fieldList = new ArrayList();
			fieldList.add("Breeze Card");
			fieldList.add("Member Id");
			fieldList.add("Company Name");
			fieldList.add("Region");
			fieldList.add("Created By");
			fieldList.add("Creation Date");
			fieldList.add("Status");
			fieldList.add("Description");
			fieldList.add("Resolution");
			fieldList.add("MARTA Admin");
			
			
				issueList = reportManagement.getTMAIssueReport(fromDate,toDate);
			
				if(!Util.isBlankOrNull(fromDate) && !Util.isBlankOrNull(toDate) ){
					 String dateRange = fromDate + " to " + toDate;
				     session.setAttribute("ISSUE_D_RANGE", dateRange);
				     request.setAttribute("ISSUE_D_RANGE", dateRange);
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME,
						Constants.BREADCRUMB_DETAILED_TMA_ISSUE_REPORT);
			returnValue = "tmaIssueListReport";
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
				session.setAttribute("TMA_ISSUE_LIST_REPORT", issueList);
			}
			if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
				baos = PdfGenerator.generateTMAIssueReportPDF("Troubleshooting Report", fieldList, issueList,request);
				responseGenerate("application/pdf",
						"TMA_ISSUE_LIST_REPORT" + ".pdf", response, baos);
			}
			if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
				baos = ExcelGenerator.generateTMAIssueReportExcel("Troubleshooting Report", fieldList, issueList,request);
				responseGenerate("application/xls",
						"TMA_ISSUE_LIST_REPORT" + ".xls", response, baos);
			}
			
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	
	public ActionForward displayTMAIssueReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayTMAIssueReport: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partnerss");
		HttpSession session = request.getSession(true);
	
		String returnValue = "tmaIssueReport";
		
		
		
		try {
		
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_DETAILED_TMA_ISSUE_REPORT);
			
			returnValue = "tmaIssueReport";
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	
	
	/**
	 * Method to displayReportTypes.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	
	public ActionForward displayReportTypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayReportTypes: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partnerss");
		HttpSession session = request.getSession(true);
		
		String returnValue = "reportMain";
		
		try {
			
			returnValue = "reportMain";
			List monthList=Util.getMonths();
			List yearList=Util.getPastYears();
			session.setAttribute(Constants.MONTH_LIST,monthList);
			session.setAttribute(Constants.YEAR_LIST,yearList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_PARTNER_REPORT);
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	/**
	 * Method to displayUsageReport Details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
//	added for  usage report
	public ActionForward displayUsageReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "displayReportTypes: ";
		BcwtsLogger.debug(MY_NAME + "Populated data for partnerss");
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		String returnValue = "usageReport";
		ReportForm reportForm = (ReportForm)form;
		String userName = null;
		String date = null;
		String month = null;
		String year = null;
		
		List fieldList = null;
		BcwtPatronDTO bcwtPatronDTO = null;
	    ReportManagement reportManagement  = null;
	    List reportList = null;
	   
		List reportListActive = new ArrayList();
		
		try {
			if(reportForm.getReportFormat() != null){
				reportFormat = reportForm.getReportFormat();
				
			}
		
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				 
				userName = bcwtPatronDTO.getFirstname() + bcwtPatronDTO.getLastname();
			}
		if(null != reportForm.getMonth()){
				month = (String)reportForm.getMonth();
			}
			if(null != reportForm.getYear()){
				year = (String)reportForm.getYear();
			}
			date = month+"/"+01+"/"+year;
			
			List monthList = Util.getMonths();
			List yearList = Util.getPastYears();
			session.setAttribute(Constants.MONTH_LIST, monthList);
			session.setAttribute(Constants.YEAR_LIST, yearList);
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				fieldList.add(PropertyReader.getValue(Constants.BREEZECARD_SERIAL_NUMBER));
				fieldList.add(PropertyReader.getValue(Constants.BENEFIT_NAME));
				fieldList.add(PropertyReader.getValue(Constants.BILLING_MONTH));
				fieldList.add(PropertyReader.getValue(Constants.MONTHLY_USAGE));
				reportManagement = new ReportManagement();
				
				reportList = reportManagement.getUsageReport(date,reportForm);
				
				
				
				
				if(reportList == null){
					reportList = new ArrayList();
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					session.setAttribute(Constants.USAGE_REPORT, reportList);
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generateUsagePDF(
							Constants.USAGE_REPORT, fieldList, reportList,request);
					responseGenerate("application/pdf",
							Constants.USAGE_REPORT + ".pdf", response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)){
					baos = ExcelGenerator.generateUsageExcel(Constants.USAGE_REPORT, fieldList, reportList, request);
					responseGenerate("application/xls", Constants.USAGE_REPORT+ ".xls", response, baos);
				}
				}
			session.setAttribute(Constants.USAGE_REPORT, reportList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_ADMIN_USAGE_REPORT);
			returnValue = "usageReport";

		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	/**
	 * Method to displayOrderReport Details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayOrderReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayOrderReport: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partnerss");
		HttpSession session = request.getSession(true);
		ReportManagement management = null;
		String returnValue = "orderReport";
		List orderReport = null;
		
		try {
			management = new ReportManagement();
			
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_DETAILED_ORDER_REPORT);
			session.setAttribute(Constants.ORDER_REPORT, orderReport);
			returnValue = "orderReport";
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	/**
	 * Method to displayHotlistReport Details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	//New Method For HotList Report
	public ActionForward displayHotlistReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "displayReportTypes: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partners");
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		String returnValue = "hotlistReport";
		ReportForm reportForm =(ReportForm) form;
		BcwtPatronDTO bcwtPatronDTO = null;
		Long partnerId = new Long(0);
		String userName = null;
		List fieldList = new ArrayList();
	    ReportManagement reportManagement = null;
	    List reportList = new ArrayList();
		try {
			if (null != request.getParameter("reportFormat")) {
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			if(reportForm.getReportFormat() != null){
				reportFormat = reportForm.getReportFormat();
			}
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				partnerId = bcwtPatronDTO.getPatronid();
				userName = bcwtPatronDTO.getFirstname() + bcwtPatronDTO.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				fieldList.add(PropertyReader.getValue(Constants.FIRST_NAME));
				fieldList.add(PropertyReader.getValue(Constants.LAST_NAME));
				fieldList.add(PropertyReader.getValue(Constants.POINT_OF_SALE));
				fieldList.add(PropertyReader.getValue(Constants.HOT_LIST_DATE));
				fieldList.add(PropertyReader.getValue(Constants.BREEZECARD_SERIAL_NUMBER));
				fieldList.add(PropertyReader.getValue(Constants.ADMIN_NAME));
				reportManagement = new ReportManagement();
				reportList = reportManagement.getHotListReport(reportForm);
				if(reportList == null){
					//reportList = new ArrayList();
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					session.setAttribute(Constants.HOT_LIST_REPORT, reportList);
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generateHotListReportPDF(
							Constants.HOT_LIST_REPORT, fieldList, reportList,request);
					responseGenerate("application/pdf",
							Constants.HOT_LIST_REPORT + ".pdf", response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					baos = ExcelGenerator.generateHotListReportExcel(
							Constants.HOT_LIST_REPORT, fieldList, reportList,request);
					responseGenerate("application/xls",
							Constants.HOT_LIST_REPORT + ".xls", response, baos);
				}
				}
				session.setAttribute(Constants.HOT_LIST_REPORT, reportList);
				session.setAttribute(Constants.BREAD_CRUMB_NAME,
						Constants.BREADCRUMB_ADMIN_HOTLIST_REPORT);
			returnValue = "hotlistReport";
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Method to displayNewCardReport Details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayNewCardReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayReportTypes: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partnerss");
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		String returnValue = "newCardReport";
		ReportForm reportForm =(ReportForm) form;
		BcwtPatronDTO bcwtPatronDTO = null;
		Long partnerId = new Long(0);
		String userName = null;
		String companyName = null;
		List fieldList = null;
	    ReportManagement reportManagement = null;
	    List reportList = null;
		try {
			
			List newCardReport = new ArrayList();
			if (null != request.getParameter("reportFormat")) {
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			if(reportForm.getReportFormat() != null){
				reportFormat = reportForm.getReportFormat();
			}
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				partnerId = bcwtPatronDTO.getPatronid();
				userName = bcwtPatronDTO.getFirstname() + bcwtPatronDTO.getLastname();
			}
			if(reportForm.getStartBillingMonth() !=null && !Util.isBlankOrNull(reportForm.getStartBillingMonth()) &&
					reportForm.getEndBillingMonth() !=null && !Util.isBlankOrNull(reportForm.getEndBillingMonth()) &&
					reportForm.getStartBillingYear() !=null && !Util.isBlankOrNull(reportForm.getStartBillingYear()) &&
					reportForm.getEndBillingYear() !=null && !Util.isBlankOrNull(reportForm.getEndBillingYear())	
							){
				StringBuffer sf = new StringBuffer();
				sf.append(reportForm.getStartBillingMonth()).append("/").append(
						reportForm.getStartBillingYear());
				reportForm.setMonthYear(sf.toString());
				
				StringBuffer my = new StringBuffer();
				my.append(reportForm.getEndBillingMonth()).append("/").append(
						reportForm.getEndBillingYear());
				reportForm.setMonthYearTo(my.toString());
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				fieldList.add(PropertyReader.getValue(Constants.COMPANY_NAME));
				fieldList.add(PropertyReader.getValue(Constants.PARTNER_ADMIN_NAME));
				fieldList.add(PropertyReader.getValue(Constants.EMPLOYEE_NAME));
				fieldList.add(PropertyReader.getValue(Constants.BREEZECARD_SERIAL_NUMBER));
				fieldList.add(PropertyReader.getValue(Constants.BENEFIT_NAME));
				fieldList.add(PropertyReader.getValue(Constants.EFFECTIVE_DATE));
				reportManagement = new ReportManagement();
				reportList = reportManagement.getNewCardReport(reportForm);
				
				if (reportForm.getMonthYear() != null
						&& reportForm.getMonthYearTo() != null
						&& !Util.isBlankOrNull(reportForm.getMonthYear())
						&& !Util.isBlankOrNull(reportForm.getMonthYearTo())) {
					SimpleDateFormat sdfInput = new SimpleDateFormat(
							"dd/mm/yyyy");
					SimpleDateFormat sdfOutput = new SimpleDateFormat(
							"mm/dd/yyyy");

					Date fromDate = sdfInput.parse("01/"
							+ reportForm.getMonthYear());

					int month = Integer.parseInt(reportForm
							.getEndBillingMonth());
					int year = Integer.parseInt(reportForm
							.getEndBillingYear());
					Date toDate = sdfInput.parse(Util.lastDayOfMonth(month,
							year) + "/" + reportForm.getMonthYearTo());

					String strFromDate = sdfOutput.format(fromDate);
					String strToDate = sdfOutput.format(toDate);
					
					String dateRange = strFromDate +" to "+strToDate;
					
					request.setAttribute("NC_DATE_RANGE", dateRange);
					session.setAttribute("NC_DATE_RANGE", dateRange);
					
				}
				
				if(reportList == null){
					reportList = new ArrayList();
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					session.setAttribute(Constants.NEW_CARD_REPORT, reportList);
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generateNewCardReportPDF(
							Constants.NEW_CARD_REPORT, fieldList, reportList,request);
					responseGenerate("application/pdf",
							Constants.NEW_CARD_REPORT + ".pdf", response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					baos = ExcelGenerator.generateNewCardReportExcel(
							Constants.NEW_CARD_REPORT, fieldList, reportList,request);
					responseGenerate("application/xls",
							Constants.NEW_CARD_REPORT + ".xls", response, baos);
				}
				}
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_NEW_CARD_REPORT);
			session.setAttribute(Constants.NEW_CARD_REPORT, reportList);
			returnValue = "newCardReport";
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
		
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
				 BcwtsLogger.error(Util.getFormattedStackTrace(e));
		    }	
	}
	/**
	 * Method to displayActiveBenifitReport Details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayActiveBenifitReport(ActionMapping mapping,ActionForm form,
            HttpServletRequest request,HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "activeBenifitReport: ";
		BcwtsLogger.info(MY_NAME + "entering into Partner activeBenifitReport method");
		String returnValue = "activeBenifitReport";
		ReportManagement reportManagement = new ReportManagement();
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		List reportList =null;
		List fieldList = null;
		ReportForm reportForm = (ReportForm)form;
		String region="";
		String userName = null; 
		int size = 0;
		if (null != request.getParameter("reportFormat")) {
			reportFormat = request.getParameter("reportFormat");
			reportForm.setReportFormat(reportFormat);
		}
		region = request.getParameter("region");
		reportForm.setRegion(region);
		Map<String, Integer> productName = new HashMap<String, Integer>();
		try {
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			StringBuffer sf = new StringBuffer();
			sf.append(reportForm.getCardMonth()).append("/").append(
					reportForm.getCardYear());
			reportForm.setMonthYear(sf.toString());
			
			StringBuffer my = new StringBuffer();
			my.append(reportForm.getCardToMonth()).append("/").append(
					reportForm.getCardToYear());
			reportForm.setMonthYearTo(my.toString());
			
			
			if(!Util.isBlankOrNull(reportForm.getTmaName())){
				request.setAttribute("NAME", reportForm.getTmaName());
			}
		//	if(Util.isBlankOrNull(reportForm.get)){
			//	request.setAttribute("NAME", reportForm.getTmaName());
		//	}
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				 fieldList.add("Company Name");
	            fieldList.add(PropertyReader.getValue(Constants.FIRST_NAME));
	            fieldList.add(PropertyReader.getValue(Constants.LAST_NAME));
	            fieldList.add(PropertyReader.getValue(Constants.CUSTOMER_MEMBER_ID));
	            fieldList.add(PropertyReader.getValue(Constants.TRANSIT_CARD));
	            fieldList.add(PropertyReader.getValue(Constants.BENEFIT_NAME));
	           
	            reportList = reportManagement.getActiveBenefits(reportForm);
	            productName = reportManagement.getProductName();
	            
	            BcwtsLogger.debug("size product "+productName.size());
	            
	            for (Map.Entry<String, Integer> entry : productName.entrySet()){
					// if(entry.getValue() != 0){
						 BcwtsLogger.debug(entry.getKey()+" Count:"+entry.getValue());
						
					    	
					 //}
		    	 }
	            
	            if(!Util.isBlankOrNull(reportForm.getPartnerFirstName())){
	            	if(!reportList.isEmpty()){
	            	PartnerReportSearchDTO name = (PartnerReportSearchDTO) reportList.get(1);
	            	request.setAttribute("NAME", name.getCompanyName());
	            	}
				}
	            
	            size = reportList.size();
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					reportForm.setIsBlock("1");
					session.setAttribute(Constants.ACTIVE_BENIFIT_REPORT, reportList);
					session.setAttribute("PRODUCTCOUNT", productName);
					session.setAttribute("SIZE", new Integer(size));
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generateActiveBenefitPDF(
							Constants.ACTIVE_BENIFIT_REPORT, fieldList, reportList,request,productName);
					responseGenerate("application/pdf",
							Constants.ACTIVE_BENIFIT_REPORT + ".pdf", response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					baos = ExcelGenerator.generateActiveBenefitExcel(
							Constants.ACTIVE_BENIFIT_REPORT, fieldList, reportList,request, productName);
					responseGenerate("application/xls",
							Constants.ACTIVE_BENIFIT_REPORT + ".xls", response, baos);
				}
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_ADMIN_ACTIVE_BENEFIT_REPORT);
			BcwtsLogger.info(MY_NAME + "ended into action");
		} catch (Exception ex) {
			BcwtsLogger.error("Error while displaying report : "
					+ ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Method to General Ledger report
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward generalLedger(
					ActionMapping mapping, 
					ActionForm form,
					HttpServletRequest request, 
					HttpServletResponse response){		
		final String MY_NAME = ME + "generalLedger: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		
		String reportFormat  = "";
		List reportList = new ArrayList();
		List fieldList = new ArrayList();		
		ReportForm reportForm = (ReportForm) form;		
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";	
		String orderType = null;
		String returnValue = "generalLedger";
		
		try{
			HttpSession session = request.getSession(true);
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
						
			if(null != request.getParameter("reportFormat")){
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			
			ReportManagement reportManagement = new ReportManagement();
			BcwtReportSearchDTO bcwtReportSearchDTO = new BcwtReportSearchDTO();

			orderType = reportForm.getOrderType();
			if(!Util.isBlankOrNull(orderType)){
				request.setAttribute("ORDER_TYPE", orderType);				
			} else {
				orderType = Constants.ORDER_TYPE_IS;
			}

			if(reportForm != null && bcwtReportSearchDTO != null){
				BeanUtils.copyProperties(bcwtReportSearchDTO, reportForm);
			}
			
			if(!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderFromDate()) && !Util.isBlankOrNull(bcwtReportSearchDTO.getOrderTodate()) ){
				
				String dateRange = bcwtReportSearchDTO.getOrderFromDate()+" to "+bcwtReportSearchDTO.getOrderTodate();
				request.setAttribute("GL_DATE_RANGE", dateRange);
				session.setAttribute("GL_DATE_RANGE", dateRange);
				
			}
			
			if(!Util.isBlankOrNull(reportFormat)){
				reportList = reportManagement.getGeneralLedgerReport(bcwtReportSearchDTO);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				if(reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)){
					request.setAttribute(Constants.GENERAL_LEDGER_LIST, reportList);
					session.setAttribute(Constants.GENERAL_LEDGER_LIST, reportList);
				}	
				
				if(reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)){					 
					fieldList.add(PropertyReader.getValue(Constants.GLR_ACCOUNT_NO));
					fieldList.add(PropertyReader.getValue(Constants.GLR_ORDER_NO));
					fieldList.add(PropertyReader.getValue(Constants.GLR_CUSTOMER_TYPE));
					fieldList.add(PropertyReader.getValue(Constants.GLR_CUSTOMER_NAME));
					fieldList.add(PropertyReader.getValue(Constants.GLR_CREDIT_CARD_AUTHORIZATION_NO));
					fieldList.add(PropertyReader.getValue(Constants.GLR_TRANSACTION_DATE));
					fieldList.add(PropertyReader.getValue(Constants.GLR_PRODUCT));
					fieldList.add(PropertyReader.getValue(Constants.GLR_PRICE));
					fieldList.add(PropertyReader.getValue(Constants.GLR_QUANTITY));
					fieldList.add(PropertyReader.getValue(Constants.GLR_AMOUNT));				
					
					baos = PdfGenerator.generateGeneralLedgerPDF(
							Constants.GENERAL_LEDGER_REPORT_HEADING, fieldList, reportList,request);
					
					ResponseGenerate("application/pdf", 
							Constants.GENERAL_LEDGER_REPORT_FILENAME + ".pdf", response, baos);
					
					fieldList.clear();
				}				
				if(reportFormat.equalsIgnoreCase(Constants.Excel_TYPE)){					   
					fieldList.add(PropertyReader.getValue(Constants.GLR_ACCOUNT_NO));
					fieldList.add(PropertyReader.getValue(Constants.GLR_ORDER_NO));
					fieldList.add(PropertyReader.getValue(Constants.GLR_CUSTOMER_TYPE));
					fieldList.add(PropertyReader.getValue(Constants.GLR_CUSTOMER_NAME));
					fieldList.add(PropertyReader.getValue(Constants.GLR_CREDIT_CARD_AUTHORIZATION_NO));
					fieldList.add(PropertyReader.getValue(Constants.GLR_TRANSACTION_DATE));
					fieldList.add(PropertyReader.getValue(Constants.GLR_PRODUCT));
					fieldList.add(PropertyReader.getValue(Constants.GLR_PRICE));
					fieldList.add(PropertyReader.getValue(Constants.GLR_QUANTITY));
					fieldList.add(PropertyReader.getValue(Constants.GLR_AMOUNT));	
					
					baos= ExcelGenerator.generateGeneralLedgerExcel(
							Constants.GENERAL_LEDGER_REPORT_HEADING, fieldList, reportList,request);
				    
					ResponseGenerate("application/xls", 
							Constants.GENERAL_LEDGER_REPORT_FILENAME + ".xls", response, baos);
					
					fieldList.clear();
				}
			}
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> Partner Reports >> General Ledger Report");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception ex){
			BcwtsLogger.error("Error while displaying report : " + ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	
	/***************** OF - Starts here **********************/
	
	/**
     * Method to view reportlist
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
	public ActionForward reportselection(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		final String MY_NAME = ME + "reportselection: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		String returnValue = "reportmainPage";
		ReportManagement reportManagement = new ReportManagement();
		List reportList = new ArrayList();
		HttpSession session = request.getSession(true);
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";
		try{
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			reportList = reportManagement.getReportList();
			session.setAttribute(Constants.REPORT_LIST, reportList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> OF Reports");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception e){
			BcwtsLogger.error("Error while getting report list : " + e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}

	/**
	 * Method to generate order summary report
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward orderSummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		final String MY_NAME = ME + "orderSummary: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		String returnValue ="orderSummaryDisplay";
		ReportManagement reportManagement = new ReportManagement();
		HttpSession session = request.getSession(true);
		String reportType = "";
		String reportFormat  = "";
		List ReportList = new ArrayList();
		ReportForm reportForm = (ReportForm) form;
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";
		String orderType = null;
		
		int noOfOutstandingOrders = 0;
		int noOfFulfilledOrders = 0;
		int noOfCancelledOrders = 0;
		int noOfReturnedOrders = 0;
		BcwtReportDTO bcwtReportDTO = null;
		
		try{
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			
			BcwtReportSearchDTO bcwtReportSearchDTO = new BcwtReportSearchDTO();
			if(null != request.getParameter("reportFormat")){
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
						
			if(null != reportForm){
				BeanUtils.copyProperties(bcwtReportSearchDTO, reportForm);
			}
			
			orderType = reportForm.getOrderType();
			if(!Util.isBlankOrNull(orderType)){
				session.setAttribute("ORDER_TYPE", orderType);
				
				String fromDate = bcwtReportSearchDTO.getOrderFromDate();
				String toDate = bcwtReportSearchDTO.getOrderTodate();
				
				// Outstanding Orders
				noOfOutstandingOrders = reportManagement.getNumberOfOutstandingOrders(orderType, fromDate, toDate);
				bcwtReportDTO =
					reportManagement.getOrderSummaryReport(bcwtReportSearchDTO, Constants.OUTSTANDING_ORDERS);
				
				bcwtReportDTO.setOrderType(Constants.OUTSTANDING_ORDERS);
				bcwtReportDTO.setNoOfOrders(String.valueOf(noOfOutstandingOrders));
				ReportList.add(bcwtReportDTO);
				
				// Fulfilled Orders
				noOfFulfilledOrders = reportManagement.getNumberOfFulfilledOrders(orderType, fromDate, toDate);
				bcwtReportDTO = 
					reportManagement.getOrderSummaryReport(bcwtReportSearchDTO, Constants.FULLFILED_ORDERS);
				
				bcwtReportDTO.setOrderType(Constants.FULLFILED_ORDERS);
				bcwtReportDTO.setNoOfOrders(String.valueOf(noOfFulfilledOrders));				
				ReportList.add(bcwtReportDTO);
				
				// Cancelled Orders	
				noOfCancelledOrders = reportManagement.getNumberOfCancelledOrders(orderType, fromDate, toDate);
				bcwtReportDTO = 
					reportManagement.getOrderSummaryReport(bcwtReportSearchDTO, Constants.CANCELLED_ORDERS);

				bcwtReportDTO.setOrderType(Constants.CANCELLED_ORDERS);
				bcwtReportDTO.setNoOfOrders(String.valueOf(noOfCancelledOrders));
				ReportList.add(bcwtReportDTO);
				
				// Returned Orders
				noOfReturnedOrders = reportManagement.getNumberOfReturnedOrders(orderType, fromDate, toDate);
				bcwtReportDTO = 
					reportManagement.getOrderSummaryReport(bcwtReportSearchDTO, Constants.RETURNED_ORDERS);
				
				bcwtReportDTO.setOrderType(Constants.RETURNED_ORDERS);
				bcwtReportDTO.setNoOfOrders(String.valueOf(noOfReturnedOrders));
				ReportList.add(bcwtReportDTO);
				
			} else {
				orderType = Constants.ORDER_TYPE_IS;
			}
			
			//ordersummaryreport
			if(!Util.isBlankOrNull(reportFormat)){
				
				//ReportList = reportManagement.getOrderSummaryReport(bcwtReportSearchDTO);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if(reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)){
					request.setAttribute(Constants.ORDERSUMMARY_REPORT, ReportList);
					session.setAttribute(Constants.ORDERSUMMARY_REPORT, ReportList);
				}	
				
				if(reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)){
					List fieldList = new ArrayList(); 
					fieldList.add(Constants.ORDER_TYPE);
					fieldList.add(Constants.noOfOrders);
					fieldList.add(Constants.orderQuantity);
					fieldList.add(Constants.revenue);
					fieldList.add(Constants.shipping);
					fieldList.add(Constants.total);
					
					baos = PdfGenerator.generateOrderSummaryPDF(Constants.ORDERSUMMARYREPORT_HEADING,fieldList,ReportList);
					ResponseGenerate("application/pdf",Constants.ORDERSUMMARY_REPORTFILENAME+".pdf",response,baos);
					
				}
				
				//code starts for excel generation
				if(reportFormat.equalsIgnoreCase(Constants.Excel_TYPE)){
					   ArrayList headers = new ArrayList();
					   headers.add(Constants.ORDER_TYPE);
					   headers.add(Constants.noOfOrders);
					   headers.add(Constants.orderQuantity);
					   headers.add(Constants.revenue);
					   headers.add(Constants.shipping);
					   headers.add(Constants.total);
					   
					   baos= ExcelGenerator.generateOrderSummaryExcel(Constants.ORDERSUMMARYREPORT_HEADING, headers, ReportList);
					   ResponseGenerate("application/xls",Constants.ORDERSUMMARY_REPORTFILENAME+".xls",response,baos);
					}
			}
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> OF Reports >> Order Summary Report");			
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception ex){
			BcwtsLogger.error("Error while displaying report : " + ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Method to generate outstanding orders report
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward outstandingOrders(
					ActionMapping mapping, 
					ActionForm form,
					HttpServletRequest request, 
					HttpServletResponse response){		
		final String MY_NAME = ME + "outstandingOrders: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		
		String reportFormat  = "";
		List reportList = new ArrayList();
		List fieldList = new ArrayList();		
		ReportForm reportForm = (ReportForm) form;		
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";		
		String orderType = null;
		String isButton = null;
		String returnValue ="outstandingOrders";
		
		try{			
			HttpSession session = request.getSession(true);
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
						
			// To Populate Order Status Combo
			ReportManagement reportManagement = new ReportManagement();
			orderType = reportForm.getOrderType();
			if(!Util.isBlankOrNull(orderType)){
				session.setAttribute("ORDER_TYPE", orderType);				
			} else {
				orderType = Constants.ORDER_TYPE_IS;
			}
			
			List statusList = null;
			if(!Util.isBlankOrNull(orderType) && orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				statusList = reportManagement.getStatusInformation(orderType);
			} else if(!Util.isBlankOrNull(orderType)){
				statusList = reportManagement.getStatusInformation(orderType);
			}
			
			List orderStatusList = new ArrayList();
			if(statusList != null && !statusList.isEmpty()){
				for (Iterator iter = statusList.iterator(); iter.hasNext();) {
					LabelValueBean element = (LabelValueBean ) iter.next();				
					if(element != null && 
							!element.getLabel().equalsIgnoreCase(Constants.ORDER_SHIPPED)) {
						orderStatusList.add(element);
					}				
				}
			}
			
			if(orderStatusList != null && !orderStatusList.isEmpty()) {
				session.setAttribute(Constants.ORDER_STATUS_LIST, orderStatusList);
			} else if(!Util.isBlankOrNull(orderType) && orderStatusList == null) {
				BcwtsLogger.error(MY_NAME + "orderStatusList is null");
				throw new MartaException(MY_NAME + "orderStatusList is null");
			} else if(!Util.isBlankOrNull(orderType) && orderStatusList.isEmpty()) {
				BcwtsLogger.error(MY_NAME + "orderStatusList is empty");
				throw new MartaException(MY_NAME + "orderStatusList is empty");
			}
						
			if(null != request.getParameter("reportFormat")){
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			
			if(null != request.getParameter("isButton")){
				isButton = request.getParameter("isButton");				
			}
			
			BcwtReportSearchDTO bcwtReportSearchDTO = new BcwtReportSearchDTO();
			
			if(reportForm != null && bcwtReportSearchDTO != null){
				bcwtReportSearchDTO.setOrderType(reportForm.getOrderType());
				bcwtReportSearchDTO.setOrderRequestId(reportForm.getOrderRequestId());
				bcwtReportSearchDTO.setCardSerialNo(reportForm.getCardSerialNumber());
				bcwtReportSearchDTO.setFirstName(reportForm.getFirstName());
				bcwtReportSearchDTO.setLastName(reportForm.getLastName());
				bcwtReportSearchDTO.setEmail(reportForm.getEmail());
				bcwtReportSearchDTO.setPhoneNumber(reportForm.getPhoneNumber());
				bcwtReportSearchDTO.setOrderFromDate(reportForm.getOrderFromDate());
				bcwtReportSearchDTO.setOrderTodate(reportForm.getOrderTodate());
				bcwtReportSearchDTO.setOrderStatus(reportForm.getOrderStatus());
				bcwtReportSearchDTO.setBatchId(reportForm.getBatchId());
			}
			
			if(!Util.isBlankOrNull(reportFormat) && !Util.isBlankOrNull(isButton)){
				reportList = reportManagement.getOutstandingOrdersReport(bcwtReportSearchDTO);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				if(reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)){
					request.setAttribute(Constants.OUTSTANDING_ORDERS_REPORT, reportList);
				}	
				if(reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)){					 
					fieldList.add(PropertyReader.getValue(Constants.orderNumber));
					fieldList.add(PropertyReader.getValue(Constants.breezeCardNumber));
					fieldList.add(PropertyReader.getValue(Constants.firstName));
					fieldList.add(PropertyReader.getValue(Constants.lastName));
					fieldList.add(PropertyReader.getValue(Constants.email));
					fieldList.add(PropertyReader.getValue(Constants.phone));
					//fieldList.add(PropertyReader.getValue(Constants.zipCode));
					fieldList.add(PropertyReader.getValue(Constants.dateReceived));
					fieldList.add(PropertyReader.getValue(Constants.orderStatus));
					fieldList.add(PropertyReader.getValue(Constants.orderType));
					
					baos = PdfGenerator.generateOutstandingOrdersPDF(
							Constants.OUTSTANDING_ORDERS_REPORT_HEADING, fieldList, reportList);
					
					ResponseGenerate("application/pdf", 
							Constants.OUTSTANDING_ORDERS_REPORT_FILENAME+".pdf", response, baos);
					
					fieldList.clear();
				}				
				if(reportFormat.equalsIgnoreCase(Constants.Excel_TYPE)){					   
					fieldList.add(PropertyReader.getValue(Constants.orderNumber));
					fieldList.add(PropertyReader.getValue(Constants.breezeCardNumber));
					fieldList.add(PropertyReader.getValue(Constants.firstName));
					fieldList.add(PropertyReader.getValue(Constants.lastName));
					fieldList.add(PropertyReader.getValue(Constants.email));
					fieldList.add(PropertyReader.getValue(Constants.phone));
					//fieldList.add(PropertyReader.getValue(Constants.zipCode));
					fieldList.add(PropertyReader.getValue(Constants.dateReceived));
					fieldList.add(PropertyReader.getValue(Constants.orderStatus));
					fieldList.add(PropertyReader.getValue(Constants.orderType));
					
					baos= ExcelGenerator.generateOutstandingOrdersExcel(
							Constants.OUTSTANDING_ORDERS_REPORT_HEADING, fieldList, reportList);
				    
					ResponseGenerate("application/xls", 
							Constants.OUTSTANDING_ORDERS_REPORT_FILENAME+".xls", response, baos);
					
					fieldList.clear();
				}
			}
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> OF Reports >> Outstanding Orders Report");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception ex){
			BcwtsLogger.error("Error while displaying report : " + ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Method to generate Quality Assurance Summary report
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward qualityAssuranceSummary(
					ActionMapping mapping, 
					ActionForm form,
					HttpServletRequest request, 
					HttpServletResponse response){		
		final String MY_NAME = ME + "qualityAssuranceSummary: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		
		String reportFormat  = "";
		List reportList = new ArrayList();
		List fieldList = new ArrayList();		
		ReportForm reportForm = (ReportForm) form;		
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";		
		String returnValue ="qualityAssuranceSummary";
		
		try{
			HttpSession session = request.getSession(true);
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
						
			if(null != request.getParameter("reportFormat")){
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			
			ReportManagement reportManagement = new ReportManagement();
			BcwtReportSearchDTO bcwtReportSearchDTO = new BcwtReportSearchDTO();
			
			if(reportForm != null && bcwtReportSearchDTO != null){
				BeanUtils.copyProperties(bcwtReportSearchDTO, reportForm);
			}
			
			if(!Util.isBlankOrNull(reportFormat)){
				reportList = reportManagement.getQualityAssuranceSummary(bcwtReportSearchDTO);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				if(reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)){
					request.setAttribute(Constants.QUALITY_ASSURANCE_SUMMARY_REPORT, reportList);
					session.setAttribute(Constants.QUALITY_ASSURANCE_SUMMARY_REPORT, reportList);
				}	
				
				if(reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)){					 
					fieldList.add(PropertyReader.getValue(Constants.passed));
					fieldList.add(PropertyReader.getValue(Constants.failed));
					
					baos = PdfGenerator.generateQualityAssuranceSummaryPDF(
							Constants.QUALITY_ASSURANCE_SUMMARY_REPORT_HEADING, fieldList, reportList);
					
					ResponseGenerate("application/pdf", 
							Constants.QUALITY_ASSURANCE_SUMMARY_REPORT_FILENAME+".pdf", response, baos);
					
					fieldList.clear();
				}				
				if(reportFormat.equalsIgnoreCase(Constants.Excel_TYPE)){					   
					fieldList.add(PropertyReader.getValue(Constants.passed));
					fieldList.add(PropertyReader.getValue(Constants.failed));
					
					baos= ExcelGenerator.generateQualityAssuranceSummaryExcel(
							Constants.QUALITY_ASSURANCE_SUMMARY_REPORT_HEADING, fieldList, reportList);
				    
					ResponseGenerate("application/xls", 
							Constants.QUALITY_ASSURANCE_SUMMARY_REPORT_FILENAME+".xls", response, baos);
					
					fieldList.clear();
				}
			}
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> OF Reports >> Quality Assurance Summary Report");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception ex){
			BcwtsLogger.error("Error while displaying report : " + ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	

	/**
	 * Method to Returned Orders report
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward returnedOrders(
					ActionMapping mapping, 
					ActionForm form,
					HttpServletRequest request, 
					HttpServletResponse response){		
		final String MY_NAME = ME + "returnedOrders: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		
		String reportFormat  = "";
		List reportList = new ArrayList();
		List fieldList = new ArrayList();		
		ReportForm reportForm = (ReportForm) form;		
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";	
		String orderType = null;
		String returnValue ="returnedOrders";
		
		try{
			HttpSession session = request.getSession(true);
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
						
			if(null != request.getParameter("reportFormat")){
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			
			ReportManagement reportManagement = new ReportManagement();
			BcwtReportSearchDTO bcwtReportSearchDTO = new BcwtReportSearchDTO();

			orderType = reportForm.getOrderType();
			if(!Util.isBlankOrNull(orderType)){
				request.setAttribute("ORDER_TYPE", orderType);				
			} else {
				orderType = Constants.ORDER_TYPE_IS;
			}

			if(reportForm != null && bcwtReportSearchDTO != null){
				BeanUtils.copyProperties(bcwtReportSearchDTO, reportForm);
			}
			
			if(!Util.isBlankOrNull(reportFormat)){
				reportList = reportManagement.getReturnedOrdersReport(bcwtReportSearchDTO);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				if(reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)){
					request.setAttribute(Constants.RETURNED_ORDERS_REPORT, reportList);
					session.setAttribute(Constants.RETURNED_ORDERS_REPORT, reportList);
				}	
				
				if(reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)){					 
					fieldList.add(PropertyReader.getValue(Constants.orderNumber));
					fieldList.add(PropertyReader.getValue(Constants.breezeCardNumber));
					fieldList.add(PropertyReader.getValue(Constants.orderDate));
					fieldList.add(PropertyReader.getValue(Constants.returnedDate));
					//fieldList.add(PropertyReader.getValue(Constants.deliveryAttempts));
					fieldList.add(PropertyReader.getValue(Constants.orderType));
					
					baos = PdfGenerator.generateReturnedOrdersPDF(
							Constants.RETURNED_ORDERS_REPORT_HEADING, fieldList, reportList);
					
					ResponseGenerate("application/pdf", 
							Constants.RETURNED_ORDERS_REPORT_FILENAME + ".pdf", response, baos);
					
					fieldList.clear();
				}				
				if(reportFormat.equalsIgnoreCase(Constants.Excel_TYPE)){					   
					fieldList.add(PropertyReader.getValue(Constants.orderNumber));
					fieldList.add(PropertyReader.getValue(Constants.breezeCardNumber));
					fieldList.add(PropertyReader.getValue(Constants.orderDate));
					fieldList.add(PropertyReader.getValue(Constants.returnedDate));
					//fieldList.add(PropertyReader.getValue(Constants.deliveryAttempts));
					fieldList.add(PropertyReader.getValue(Constants.orderType));
					
					baos= ExcelGenerator.generateReturnedOrdersExcel(
							Constants.RETURNED_ORDERS_REPORT_HEADING, fieldList, reportList);
				    
					ResponseGenerate("application/xls", 
							Constants.RETURNED_ORDERS_REPORT_FILENAME + ".xls", response, baos);
					
					fieldList.clear();
				}
			}
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> OF Reports >> Returned Orders Report");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception ex){
			BcwtsLogger.error("Error while displaying report : " + ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
		
	/**
	 * Method to generate cancell order
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelOrder(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		final String MY_NAME = ME + "cancellOrder: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		ReportManagement reportManagement = new ReportManagement();
		HttpSession session = request.getSession(true);
		String reportType = "";
		String reportFormat  = "";
		List ReportList = new ArrayList();
		ReportForm reportForm = (ReportForm) form;
		String returnValue ="cancelOrder";
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";
		String orderType = null;
		String isButton = null;
		
		try {
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}			
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			
			if(null != request.getParameter("reportFormat")){
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			
			if(null != request.getParameter("isButton")){
				isButton = request.getParameter("isButton");				
			}
			
			orderType = reportForm.getOrderType();
			if(!Util.isBlankOrNull(orderType)){
				session.setAttribute("ORDER_TYPE", orderType);				
			} else {
				orderType = Constants.ORDER_TYPE_IS;
			}
			
			BcwtReportSearchDTO bcwtReportSearchDTO = new BcwtReportSearchDTO();
			if(null != reportForm){
				BeanUtils.copyProperties(bcwtReportSearchDTO, reportForm);
			}
			//ordercancelledreport
			if(!Util.isBlankOrNull(reportFormat) && !Util.isBlankOrNull(isButton)){
				List fieldList = new ArrayList(); 
				fieldList.add(Constants.ORDERID);
				fieldList.add(Constants.CARDSERIALNUMBER);
				fieldList.add(Constants.FIRSTNAME);
				fieldList.add(Constants.LASTNAME);
				fieldList.add(Constants.DATECANCELLED);
				fieldList.add(Constants.MEDIASALE);
				fieldList.add(Constants.REASONTYPE);
				fieldList.add(Constants.ORDERTYPE);
				ReportList = reportManagement.getOrderCancelledReport(bcwtReportSearchDTO);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if(reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)){
					request.setAttribute(Constants.ORDERCANCELLED_REPORT, ReportList);
					session.setAttribute(Constants.ORDERCANCELLED_REPORT, ReportList);
				}	
				if(reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)){
					
				    baos = PdfGenerator.generateCancelledOrderPDF(Constants.CANCELORDERREPORT_HEADING,fieldList,ReportList);
				    ResponseGenerate("application/pdf",Constants.CANCELLEDORDER_REPORTFILENAME+".pdf",response,baos);
				}
				if(reportFormat.equalsIgnoreCase(Constants.Excel_TYPE)){
					  
					  baos= ExcelGenerator.generateCancelledOrderExcel(Constants.CANCELORDERREPORT_HEADING, fieldList, ReportList);
					  ResponseGenerate("application/xls",Constants.CANCELLEDORDER_REPORTFILENAME+".xls",response,baos);
				}				
			}
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> OF Reports >> Cancelled Order Report");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception ex){
			BcwtsLogger.error("Error while displaying report : " + ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	
	public ActionForward qualitydetailed(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		final String MY_NAME = ME + "qualitydetail: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		ReportManagement reportManagement = new ReportManagement();
		HttpSession session = request.getSession(true);
		String reportType = "";
		String reportFormat  = "";
		List ReportList = new ArrayList();
		ReportForm reportForm = (ReportForm) form;
		String returnValue ="qualitydetailed";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(null != request.getParameter("reportFormat")){
			reportFormat = request.getParameter("reportFormat");
			reportForm.setReportFormat(reportFormat);
		}
		BcwtReportSearchDTO bcwtReportSearchDTO = new BcwtReportSearchDTO();
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";
		String orderType = null;
		
		try{
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			if(null != reportForm){
				BeanUtils.copyProperties(bcwtReportSearchDTO, reportForm);
			}
			
			orderType = reportForm.getOrderType();
			if(!Util.isBlankOrNull(orderType)){
				request.setAttribute("ORDER_TYPE", orderType);
				
				String fromDate = bcwtReportSearchDTO.getOrderFromDate();
				String toDate = bcwtReportSearchDTO.getOrderTodate();
				
				//int noOfFails = reportManagement.getNumberOfFails(orderType, fromDate, toDate);
				//request.setAttribute("NO_OF_FAILS", String.valueOf(noOfFails));
			} else {
				orderType = Constants.ORDER_TYPE_IS;
			}
			
			if(!Util.isBlankOrNull(reportFormat)){
				List fieldList = new ArrayList();
				fieldList.add(PropertyReader.getValue("breezecard.orderrequestedid"));
				fieldList.add(PropertyReader.getValue("breezecard.report.processedby.firstname"));
				fieldList.add(PropertyReader.getValue("breezecard.report.processedby.lastname"));
				fieldList.add(PropertyReader.getValue("breezecard.report.processeddate"));
				fieldList.add(PropertyReader.getValue(Constants.orderType));
				
				ReportList = reportManagement.getQualityDetailedReport(bcwtReportSearchDTO);
				
				if(reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)){
					request.setAttribute(Constants.QUALITYDETAILED_REPORT, ReportList);
					session.setAttribute(Constants.QUALITYDETAILED_REPORT, ReportList);
				}	
				if(reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)){
					 baos = PdfGenerator.generateQualityDetailsPDF(Constants.QUALITYDETAILEDREPORT_HEADING,fieldList,ReportList);
					    ResponseGenerate("application/pdf",Constants.QUALITYDETAILED_REPORTFILENAME+".pdf",response,baos);
				}
				if(reportFormat.equalsIgnoreCase(Constants.Excel_TYPE)){					  
					  baos= ExcelGenerator.generateQualityDetailsExcel(Constants.QUALITYDETAILEDREPORT_HEADING, fieldList, ReportList);
					  ResponseGenerate("application/xls",Constants.QUALITYDETAILED_REPORTFILENAME+".xls",response,baos);
				}
			}
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> OF Reports >> Quality Detailed Report");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception ex){
			BcwtsLogger.error("Error while displaying report : " + ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	
	
	/**
	 * method to generate response
	 * @param contentype
	 * @param filetype
	 * @param response
	 * @param baos
	 */
	 public static void ResponseGenerate(String contentype,String filename,HttpServletResponse response,ByteArrayOutputStream baos )
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
	    	e.printStackTrace();
	    }
  }
	 
	 
	 /**
		 * Method to Sales Metrics report
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
	 public ActionForward salesMetricsReport(
				ActionMapping mapping, 
				ActionForm form,
				HttpServletRequest request, 
				HttpServletResponse response){		
		final String MY_NAME = ME + "salesMetricsReport: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		
		String reportFormat  = "";
		List reportList = null;
		List fieldList = new ArrayList();		
		ReportForm reportForm = (ReportForm) form;		
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";	
		String orderType = null;
		String returnValue ="salesMetricsReport";
		
		try{
			HttpSession session = request.getSession(true);
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
						
			// To Populate Sales Metrics Data
			ReportManagement reportManagement = new ReportManagement();
								
			if(null != request.getParameter("reportFormat")){
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			
			BcwtReportSearchDTO bcwtReportSearchDTO = new BcwtReportSearchDTO();
			
			orderType = reportForm.getOrderType();
			if(!Util.isBlankOrNull(orderType)){
				request.setAttribute("ORDER_TYPE", orderType);			
			} else {
				orderType = Constants.ORDER_TYPE_IS;
			}
			
			if(reportForm != null && bcwtReportSearchDTO != null){
				bcwtReportSearchDTO.setOrderType(orderType);
				bcwtReportSearchDTO.setZip(reportForm.getZip());
				bcwtReportSearchDTO.setOrderFromDate(reportForm.getOrderFromDate());
				bcwtReportSearchDTO.setOrderTodate(reportForm.getOrderTodate());
			}
			
			if(!Util.isBlankOrNull(reportFormat)){
				reportList = reportManagement.getSalesMetricsReport(bcwtReportSearchDTO);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();			
				
				if(reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)){
					request.setAttribute(Constants.SALES_METRICS_REPORT, reportList);
					session.setAttribute(Constants.SALES_METRICS_REPORT, reportList);
				}	
				if(reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)){
					fieldList.add(PropertyReader.getValue(Constants.zipCode));
					fieldList.add(PropertyReader.getValue(Constants.numberOfCardsSold));
					
					baos = PdfGenerator.generateSalesMetricsPDF(
							Constants.SALES_METRICS_REPORT_HEADING, fieldList, reportList);
					
					ResponseGenerate("application/pdf", 
							Constants.SALES_METRICS_REPORT_FILENAME+".pdf", response, baos);
					
					fieldList.clear();
				}				
				if(reportFormat.equalsIgnoreCase(Constants.Excel_TYPE)){					   
	
					fieldList.add(PropertyReader.getValue(Constants.numberOfCardsSold));
					fieldList.add(PropertyReader.getValue(Constants.zipCode));
					
					baos= ExcelGenerator.generateSalesMetricsExcel(
							Constants.SALES_METRICS_REPORT_HEADING, fieldList, reportList);
				    
					ResponseGenerate("application/xls", 
							Constants.SALES_METRICS_REPORT_FILENAME+".xls", response, baos);
					
					fieldList.clear();
				}
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> OF Reports >> Sales Metrics Report");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception ex){
			BcwtsLogger.error("Error while displaying report : " + ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
			BcwtsLogger.debug(ME + " returnValue" + returnValue);
		}
	
		return mapping.findForward(returnValue);
	}
	 
	 
	public ActionForward mediaSalesPerformanceMetricsReport(
			ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) {
		final String MY_NAME = ME + "mediaSalesPerformanceMetricsReport: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		ReportManagement reportManagement = new ReportManagement();
		HttpSession session = request.getSession(true);
		String reportFormat  = "";
		List ReportList = new ArrayList();
		ReportForm reportForm = (ReportForm) form;
		String returnValue ="mediaSalesPerformanceMetrics";
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";
		String orderType = null;
		int noOfFulfilledOrders = 0;
		
		try{
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			
			if(null != request.getParameter("reportFormat")){
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			
			BcwtReportSearchDTO bcwtReportSearchDTO = new BcwtReportSearchDTO();
			
			orderType = reportForm.getOrderType();
			if(!Util.isBlankOrNull(orderType)){
				request.setAttribute("ORDER_TYPE", orderType);	
				
				String fromDate = bcwtReportSearchDTO.getOrderFromDate();
				String toDate = bcwtReportSearchDTO.getOrderTodate();
				
				noOfFulfilledOrders = reportManagement.getNumberOfFulfilledOrders(orderType, fromDate, toDate);
				request.setAttribute("NO_OF_FULLFILLED_ORDERS", String.valueOf(noOfFulfilledOrders));
			} else {
				orderType = Constants.ORDER_TYPE_IS;
			}
			
			if(null != reportForm){
				BeanUtils.copyProperties(bcwtReportSearchDTO, reportForm);
			}
			
			if(!Util.isBlankOrNull(reportFormat)){
				List fieldList = new ArrayList(); 
				fieldList.add(Constants.ORDER_ID);
				fieldList.add(Constants.ORDER_DATE);
				fieldList.add(Constants.ORDER_FILLEDBY_FIRSTNAME);
				fieldList.add(Constants.ORDER_FILLEDBY_LASTNAME);
				fieldList.add(Constants.ORDER_PROCESSEDBY_FIRSTNAME);
				fieldList.add(Constants.ORDER_PROCESSEDBY_LASTNAME);
				fieldList.add(Constants.ORDER_TYPE);
				
				ReportList = reportManagement.getMediaSalesPerformanceMetricsReport(bcwtReportSearchDTO);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				if(reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)){
					request.setAttribute(Constants.ORDERMEDIASALESMETRICS_REPORT, ReportList);
					session.setAttribute(Constants.ORDERMEDIASALESMETRICS_REPORT, ReportList);
				}	
				
				if(reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)){
				    baos = PdfGenerator.generateMediaSalesMetricsPDF(Constants.ORDERMEDIASALESMETRICS_REPORT_HEADING,fieldList,ReportList);
				    ResponseGenerate("application/pdf",Constants.ORDERMEDIASALESMETRICS_REPORT_FILENAME+".pdf",response,baos);
				}
				
				if(reportFormat.equalsIgnoreCase(Constants.Excel_TYPE)){
					  baos= ExcelGenerator.generateMediaSalesMetricsExcel(Constants.ORDERMEDIASALESMETRICS_REPORT_HEADING, fieldList, ReportList);
					  ResponseGenerate("application/xls",Constants.ORDERMEDIASALESMETRICS_REPORT_FILENAME+".xls",response,baos);
				}
			}
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> OF Reports >> Media Sales Performance Metrics Report");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception ex){
			BcwtsLogger.error("Error while displaying report : " + ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	
	public ActionForward revenueReport(
			ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) {
		final String MY_NAME = ME + "revenueReport: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		
		String reportFormat  = "";
		List ReportList = null;
		
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		String userName = "";
		
		String orderType = null;

		String returnValue = "revenueReport";
		
		try {			
			ReportManagement reportManagement = new ReportManagement();
			ReportForm reportForm = (ReportForm) form;
			
			HttpSession session = request.getSession(true);
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				patronId = patronDTOInSession.getPatronid();
				userName = patronDTOInSession.getFirstname() + " "
						+ patronDTOInSession.getLastname();
			}
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
			
			if(null != request.getParameter("reportFormat")){
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			
			BcwtReportSearchDTO bcwtReportSearchDTO = new BcwtReportSearchDTO();
			
			orderType = reportForm.getOrderType();
			if(!Util.isBlankOrNull(orderType)){
				request.setAttribute("ORDER_TYPE", orderType);				
			} else {
				orderType = Constants.ORDER_TYPE_IS;
			}
			
			if(null != reportForm){
				BeanUtils.copyProperties(bcwtReportSearchDTO, reportForm);
			}
			
			if(!Util.isBlankOrNull(reportFormat)){
				List fieldList = new ArrayList(); 
				fieldList.add(PropertyReader.getValue("breezecard.revenue.report.ordernumber"));
				fieldList.add(PropertyReader.getValue("breezecard.revenue.report.transactiondate"));
				fieldList.add(PropertyReader.getValue("breezecard.revenue.report.creditcardtype"));
				fieldList.add(PropertyReader.getValue("breezecard.revenue.report.amount"));
				fieldList.add(PropertyReader.getValue("breezecard.revenue.report.creditcardlast4"));
				fieldList.add(PropertyReader.getValue("breezecard.revenue.report.approvalcode"));				
				fieldList.add(PropertyReader.getValue("breezecard.revenue.report.ordermodule"));
				fieldList.add(PropertyReader.getValue("breezecard.revenue.report.products"));
				fieldList.add(PropertyReader.getValue("breezecard.revenue.report.customername"));
				fieldList.add(PropertyReader.getValue("breezecard.revenue.report.ordertype"));
				
				ReportList = reportManagement.getRevenueReport(bcwtReportSearchDTO);
				
				// Revenue Summary Report
				
				List revenueSummaryFieldList = new ArrayList();
				revenueSummaryFieldList.add(PropertyReader.getValue("breezecard.revenue.report.cardtype"));				
				revenueSummaryFieldList.add(PropertyReader.getValue("breezecard.revenue.report.amount"));
				revenueSummaryFieldList.add(PropertyReader.getValue("breezecard.revenue.report.number"));

				List revenueSummaryReportList = getRevenueSummaryList(bcwtReportSearchDTO);
				
				if(reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)){
					request.setAttribute(Constants.REVENUE_REPORT, ReportList);
					request.setAttribute(Constants.REVENUE_SUMMARY_REPORT, revenueSummaryReportList);
					
					session.setAttribute(Constants.REVENUE_REPORT, ReportList);
					session.setAttribute(Constants.REVENUE_SUMMARY_REPORT, revenueSummaryReportList);
				}	
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if(reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)){
				    baos = PdfGenerator.generateRevenueReportPDF(Constants.REVENUE_REPORT_HEADING,fieldList,ReportList,
				    		revenueSummaryFieldList,revenueSummaryReportList);
				    ResponseGenerate("application/pdf",Constants.REVENUE_REPORT_FILENAME+".pdf",response,baos);
				}
				
				if(reportFormat.equalsIgnoreCase(Constants.Excel_TYPE)){
					  baos= ExcelGenerator.generateRevenueReportExcel(Constants.REVENUE_REPORT_HEADING, fieldList,
							  ReportList,revenueSummaryFieldList,revenueSummaryReportList);
					  ResponseGenerate("application/xls",Constants.REVENUE_REPORT_FILENAME+".xls",response,baos);
				}
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Reports >> OF Reports >> Revenue Report");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception ex){
			BcwtsLogger.error("Error while displaying report : " + ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	private List getRevenueSummaryList(BcwtReportSearchDTO bcwtReportSearchDTO) throws Exception {
		BcwtReportDTO bcwtReportDTO = null;
		double totalAmount = 0.00;
		int totalNumber = 0;
		
		DecimalFormat numberFormat = new DecimalFormat("0.00");
        numberFormat.setMaximumFractionDigits(2);
	    numberFormat.setMinimumIntegerDigits(1);
	    List revenueSummaryList = new ArrayList(); 
	    
		try {
			ReportManagement reportManagement = new ReportManagement();
			
			bcwtReportDTO = reportManagement.getRevenueSummaryReport(bcwtReportSearchDTO, Constants.VISA);
			if(bcwtReportDTO != null) {
				totalAmount = totalAmount + Double.parseDouble(bcwtReportDTO.getAmount());
				totalNumber = totalNumber + Integer.parseInt(bcwtReportDTO.getNoOfOrders());
				bcwtReportDTO.setCreditCardType(Constants.VISA);
				revenueSummaryList.add(bcwtReportDTO);
			}
			
			bcwtReportDTO = reportManagement.getRevenueSummaryReport(bcwtReportSearchDTO, Constants.MASTERCARD);
			if(bcwtReportDTO != null) {
				totalAmount = totalAmount + Double.parseDouble(bcwtReportDTO.getAmount());
				totalNumber = totalNumber + Integer.parseInt(bcwtReportDTO.getNoOfOrders());
				bcwtReportDTO.setCreditCardType(Constants.MASTERCARD);
				revenueSummaryList.add(bcwtReportDTO);
			}
			
			bcwtReportDTO = reportManagement.getRevenueSummaryReport(bcwtReportSearchDTO, Constants.DISCOVER);
			if(bcwtReportDTO != null) {
				totalAmount = totalAmount + Double.parseDouble(bcwtReportDTO.getAmount());
				totalNumber = totalNumber + Integer.parseInt(bcwtReportDTO.getNoOfOrders());
				bcwtReportDTO.setCreditCardType(Constants.DISCOVER);
				revenueSummaryList.add(bcwtReportDTO);
			}
			
			bcwtReportDTO = reportManagement.getRevenueSummaryReport(bcwtReportSearchDTO, Constants.AMEX);
			if(bcwtReportDTO != null) {
				totalAmount = totalAmount + Double.parseDouble(bcwtReportDTO.getAmount());
				totalNumber = totalNumber + Integer.parseInt(bcwtReportDTO.getNoOfOrders());
				bcwtReportDTO.setCreditCardType(Constants.AMEX);
				revenueSummaryList.add(bcwtReportDTO);
			}
			
			bcwtReportDTO = new BcwtReportDTO();
			bcwtReportDTO.setCreditCardType(Constants.total);
			bcwtReportDTO.setAmount(numberFormat.format(totalAmount));
			bcwtReportDTO.setNoOfOrders(String.valueOf(totalNumber));
			revenueSummaryList.add(bcwtReportDTO);
			
		} catch (Exception e) {
			BcwtsLogger.error("Exception in getRevenueSummaryList(): " + e.getMessage());
			throw e;
		}
		
		return revenueSummaryList;
	}
	
	
	//--------------Sunil Code--------------------------------------------------------------
	/**
	 * Method to displayActiveBenifitReport Details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayActiveBenifitReport_New(ActionMapping mapping,ActionForm form,
            HttpServletRequest request,HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "activeBenifitReport_New: ";
		BcwtsLogger.info(MY_NAME + "entering into Partner activeBenifitReport_New method");
		String returnValue = "activeBenifitReport_New";
		ReportManagement reportManagement = new ReportManagement();
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		List reportList =null;
		List fieldList = null;
		List fieldList1 = null;
		ReportForm reportForm = (ReportForm)form;
		String userName = null; 
		ReportDAO reportDAO =null; 
		int size = 0;
		int total=0;
		if (null != request.getParameter("reportFormat")) {
			reportFormat = request.getParameter("reportFormat");
			reportForm.setReportFormat(reportFormat);
		}
		try {
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			StringBuffer sf = new StringBuffer();
			sf.append(reportForm.getCardMonth()).append("/").append(
					reportForm.getCardYear());
			reportForm.setMonthYear(sf.toString());
			
			StringBuffer my = new StringBuffer();
			my.append(reportForm.getCardToMonth()).append("/").append(
					reportForm.getCardToYear());
			reportForm.setMonthYearTo(my.toString());
			
			reportDAO = new ReportDAO();
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
	            fieldList.add(PropertyReader.getValue(Constants.COMPANY_NAME));
	            fieldList.add(PropertyReader.getValue(Constants.CARD_COUNT));          
	            
	            
	            reportList= new ArrayList();
	            
	            String tmaid = reportDAO.getTmaID(reportForm.getTmaName());	
	            
	            System.out.println("TMA ID ::  "+tmaid);
	            List vo = reportDAO.getTmacompanyList(tmaid);
	    		Iterator itr = vo.iterator();		
	    		while(itr.hasNext()){
	    			PartnerReportSearchDTO avo= new PartnerReportSearchDTO();	    			
	    			CompanyInfoDTO tcvo = (CompanyInfoDTO)itr.next();	    			
	    			avo.setCompanyName(tcvo.getCompanyName());  			
	    			
	    			try{
	    			int count = reportDAO.getActivebenefitsofCompany(tcvo.getCompanyId());	    			
	    			avo.setCardcount(count);
	    			total=total+count;
	    			}
	    			catch(Exception e){
	    				e.printStackTrace();
	    				
	    			}
	    			reportList.add(avo);
	    			
	    		}	
	    	     
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					reportForm.setIsBlock("1");
					session.setAttribute(Constants.ACTIVE_BENIFIT_REPORT, reportList);
					session.setAttribute("TOTAL", new Integer(total));
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generateActiveBenefitPDF_New(
							Constants.ACTIVE_BENIFIT_REPORT, fieldList,reportList,request,total+"",reportForm.getTmaName());
					responseGenerate("application/pdf",
							Constants.ACTIVE_BENIFIT_REPORT + ".pdf", response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					baos = ExcelGenerator.generateActiveBenefitExcel_New(
							Constants.ACTIVE_BENIFIT_REPORT, fieldList, reportList,request,total+"",reportForm.getTmaName());
					responseGenerate("application/xls",
							Constants.ACTIVE_BENIFIT_REPORT + ".xls", response, baos);
				}
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_ADMIN_ACTIVE_BENEFIT_REPORT);
			BcwtsLogger.info(MY_NAME + "ended into action");
		} catch (Exception ex) {
			ex.printStackTrace();
			BcwtsLogger.error("Error while displaying report : "
					+ ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	
	//---------------------------------------------------------------------------------------
	
}

	/***************** OF - Ends here **********************/
