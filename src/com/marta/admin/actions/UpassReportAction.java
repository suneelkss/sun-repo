package com.marta.admin.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.ListCardsManagement;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.Util;
import com.marta.admin.business.ReportManagement;
import com.marta.admin.dto.PartnerAdminDetailsDTO;

import com.marta.admin.dto.UpCommingMonthBenefitDTO;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.utils.ExcelGenerator;
import com.marta.admin.utils.PdfGenerator;
import com.marta.admin.utils.PropertyReader;



public class UpassReportAction extends DispatchAction {

	final String ME = "UpassReportAction: ";
	ListCardsManagement listCardsManagement = new ListCardsManagement();

	public ActionForward upassSchoolList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "upassSchoolList: ";
		BcwtsLogger.debug(MY_NAME + "Showing school list");
		String returnPath = "upassSchoolList";
		String userName = null;

		ListCardsManagement listCardsManagement = null;
		List schoolList = null;
		try {

			listCardsManagement = new ListCardsManagement();
			HttpSession session = request.getSession(true);
			schoolList = listCardsManagement.getSchoolDetails();
			session.setAttribute("schoolList", schoolList);

			BcwtsLogger.info(MY_NAME + "User Name:" + userName);

			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_SCHOOLLIST_REPORTS);
		} catch (Exception e) {
			BcwtsLogger.error("Error while showing school list  page : "
					+ Util.getFormattedStackTrace(e));
			returnPath = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnPath);
	}

	public ActionForward reportselection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		final String MY_NAME = ME + "reportselection: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		String returnValue = "upassReportMain";

		HttpSession session = request.getSession(true);

		try {

			String nextfarId = (String) request.getParameter("nextfareCompId");
			session.setAttribute("NextFareId", nextfarId);
			BcwtsLogger.debug(MY_NAME + "User Name:" + nextfarId);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_REPORTS);
			BcwtsLogger.info(MY_NAME + "ended into action");
		} catch (Exception e) {
			BcwtsLogger.error("Error while getting report list : "
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}

	// report for new cards

	public ActionForward newCardReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		final String MY_NAME = ME + "newCardReport: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		String returnValue = "newCardReport";

		HttpSession session = request.getSession(true);

		try {
			List monthList = Util.getMonths();
			List yearList = Util.getPastYears();
			session.setAttribute(Constants.MONTH_LIST, monthList);
			session.setAttribute(Constants.YEAR_LIST, yearList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_UPASS_NEW_CARD_REPORT);
			BcwtsLogger.info(MY_NAME + "ended into action");
		} catch (Exception e) {
			BcwtsLogger.error("Error while getting report list : "
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}

	public ActionForward displayNewCardReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		final String MY_NAME = ME + "displayNewCardReport: ";
		BcwtsLogger.info(MY_NAME + "entering into displayNewCardReport method");
		String returnValue = "newCardReport";
		ReportManagement reportManagement = new ReportManagement();
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		List reportList = null;
		List fieldList = null;
		ReportForm reportForm = (ReportForm) form;

		String companyid = "";

		if (null != request.getParameter("reportFormat")) {
			reportFormat = request.getParameter("reportFormat");
			reportForm.setReportFormat(reportFormat);
		}
		try {

			companyid = (String) session.getAttribute("NextFareId");
			BcwtsLogger.info("Generating new card report for company id: "
					+ companyid);
			reportForm.setCompanyId(companyid);

			StringBuffer sf = new StringBuffer();
			sf.append(reportForm.getCardMonth()).append("/")
					.append(reportForm.getCardYear());
			reportForm.setMonthYear(sf.toString());

			StringBuffer my = new StringBuffer();
			my.append(reportForm.getCardToMonth()).append("/")
					.append(reportForm.getCardToYear());
			reportForm.setMonthYearTo(my.toString());

			BcwtsLogger.info("Report Format" + reportFormat);

			if (!Util.isBlankOrNull(reportFormat)) {
				reportForm.setReportFormat(reportFormat);
				fieldList = new ArrayList();
				fieldList
						.add(PropertyReader.getValue(Constants.CARD_SERIAL_NO));
				fieldList.add(PropertyReader.getValue(Constants.BENEFIT_NAME));
				fieldList
						.add(PropertyReader.getValue(Constants.EFFECTIVE_DATE));
				reportList = reportManagement.getNewCardReportList(reportForm);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					reportForm.setIsBlock("1");
					session.setAttribute(Constants.NEW_CARD_REPORT, reportList);
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generateUpassNewCardReportPDF(
							Constants.NEW_CARD_REPORT, fieldList, reportList,
							request);
					responseGenerate("application/pdf",
							Constants.NEW_CARD_REPORT + ".pdf", response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					baos = ExcelGenerator.generateUpassNewCardReportExcel(
							Constants.NEW_CARD_REPORT, fieldList, reportList,
							request);
					responseGenerate("application/xls",
							Constants.NEW_CARD_REPORT + ".xls", response, baos);
				}
			}
			BcwtsLogger.info(MY_NAME + "ended into action");
		} catch (Exception ex) {
			BcwtsLogger.error("Error while displaying report : "
					+ ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}

	// report for Active Benefits Reports
	public ActionForward activeBenefitsReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		final String MY_NAME = ME + "activeBenefitsReport: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		String returnValue = "activeBenifitReport";

		HttpSession session = request.getSession(true);

		try {
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_ACTIVE_BENEFIT_REPORT);
			BcwtsLogger.info(MY_NAME + "ended into action");
		} catch (Exception e) {
			BcwtsLogger.error("Error while getting report list : "
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}

	public ActionForward displayActiveBenifitReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "activeBenifitReport: ";
		BcwtsLogger.info(MY_NAME
				+ "entering into Upass activeBenifitReport method");
		String returnValue = "activeBenifitReport";
		ReportManagement reportManagement = new ReportManagement();
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		List reportList = null;
		List fieldList = null;
		ReportForm reportForm = (ReportForm) form;
		String userName = null;
		int size = 0;
		if (null != request.getParameter("reportFormat")) {

			reportFormat = request.getParameter("reportFormat");

			reportForm.setReportFormat(reportFormat);
		}
		try {

			String nextfareId = (String) session.getAttribute("NextFareId");

			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				fieldList.add(PropertyReader.getValue(Constants.CARD_HOLDER));
				fieldList.add(PropertyReader
						.getValue(Constants.CUSTOMER_MEMBER_ID));
				fieldList.add(PropertyReader.getValue(Constants.TRANSIT_CARD));
				fieldList.add(PropertyReader.getValue(Constants.BENEFIT_NAME));

				reportList = reportManagement.getActiveBenefits(nextfareId);
				size = reportList.size();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					reportForm.setIsBlock("1");
					session.setAttribute(Constants.ACTIVE_BENIFIT_REPORT,
							reportList);
					session.setAttribute("SIZE", new Integer(size));
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generateActiveBenefitPDF(
							Constants.ACTIVE_BENIFIT_REPORT, fieldList,
							reportList, request,null);
					responseGenerate("application/pdf",
							Constants.ACTIVE_BENIFIT_REPORT + ".pdf", response,
							baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					baos = ExcelGenerator.generateActiveBenefitExcel(
							Constants.ACTIVE_BENIFIT_REPORT, fieldList,
							reportList, request,null);
					responseGenerate("application/xls",
							Constants.ACTIVE_BENIFIT_REPORT + ".xls", response,
							baos);
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

	private void responseGenerate(String contentype, String filename,
			HttpServletResponse response, ByteArrayOutputStream baos) {
		final String MY_NAME = "ResponseGenerate: ";
		BcwtsLogger.info(MY_NAME + "entering into ResponseGenerate method");
		try {
			response.setBufferSize(baos.size());
			byte requestBytes[] = baos.toByteArray();
			ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);
			response.reset();
			response.setContentType(contentype);
			response.setHeader("Content-disposition", "attachment; filename="
					+ filename);
			byte[] buf = new byte[baos.size()];
			int len;
			while ((len = bis.read(buf)) > 0) {
				response.getOutputStream().write(buf, 0, buf.length);
			}
			bis.close();
			response.getOutputStream().flush();
			BcwtsLogger.info(MY_NAME + "response generated");
		} catch (Exception e) {
			BcwtsLogger.error(Util.getFormattedStackTrace(e));
		}
	}

	// Monthly Usage Report

	public ActionForward monthlyUsageReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		final String MY_NAME = ME + "monthlyUsageReport: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		String returnValue = "monthlyUsage";

		HttpSession session = request.getSession(true);
		PartnerAdminDetailsDTO partnerAdminDetailsDTO = null;

		try {

			List monthList = Util.getMonths();
			List yearList = Util.getPastYears();
			session.setAttribute(Constants.MONTH_LIST, monthList);
			session.setAttribute(Constants.YEAR_LIST, yearList);

			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.UPASS_MONTHLY_USAGE_SUMMARY_REPORT);
			BcwtsLogger.info(MY_NAME + "ended into action");
		} catch (Exception e) {
			BcwtsLogger.error("Error while getting report list : "
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}

	public ActionForward monthlyUsageDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		final String MY_NAME = "monthlyUsageDetails: ";
		BcwtsLogger.info(MY_NAME + "entering into MonthlyUsage method");
		String returnValue = "monthlyUsage";
		ReportManagement reportManagement = new ReportManagement();
		HttpSession session = request.getSession(true);
		String reportType = "";
		String reportFormat = "";
		List reportList = new ArrayList();
		List fieldList = null;
		ReportForm reportForm = (ReportForm) form;


		String userName = "";
		String companyid = "";
		PdfGenerator pdfGenerator = null;
		ExcelGenerator excelGenerator = null;
		String date = null;
		String month = null;
		String year = null;
	

		

		try {
			if(reportForm.getReportFormat() != null){
				reportFormat = reportForm.getReportFormat();
			}
			
			companyid = (String) session.getAttribute("NextFareId");
			if (null != reportForm.getMonth()) {
				month = (String) reportForm.getMonth();
			}
			if (null != reportForm.getYear()) {
				year = (String) reportForm.getYear();
			}
			date = month + "/" + 01 + "/" + year;
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				pdfGenerator = new PdfGenerator();
				fieldList.add(PropertyReader
						.getValue(Constants.BC_SERIAL_NUMBER));
				fieldList.add(PropertyReader.getValue(Constants.BENEFIT_NAME));
				fieldList.add(PropertyReader.getValue(Constants.BILLING_MONTH));
				fieldList.add(PropertyReader.getValue(Constants.MONTHLY_USAGE));
				reportList = reportManagement.getMonthlyUsageDetails(date,
						companyid);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					request.setAttribute(Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT,
							reportList);
					session.setAttribute(Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT,
							reportList);
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = pdfGenerator.generateMonthlyUsagePDF(
							Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT, fieldList,
							reportList, request);
					responseGenerate("application/pdf",
							Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT + ".pdf", response,
							baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					excelGenerator = new ExcelGenerator();
					baos = excelGenerator.generateMonthlyUsageExcel(
							Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT, fieldList,
							reportList, request);
					responseGenerate("application/xls",
							Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT + ".xls", response,
							baos);
				}

			}
			BcwtsLogger.info(MY_NAME + "ended into action");
		} catch (Exception ex) {
			BcwtsLogger.error("Error while displaying report : "
					+ Util.getFormattedStackTrace(ex));
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}

	// Upcomming Monthly Benefits Report
	
	
	public ActionForward upCommingMonthBenefitReport(ActionMapping mapping,ActionForm form,
			 HttpServletRequest request,HttpServletResponse response){
		final String MY_NAME = ME + "upCommingMonthBenefitReport: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		String returnValue = "UpcomingBenefit";
		HttpSession session = request.getSession(true);
			try{
			
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					"Upcomming Monthly Benefits Report");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception e){
			BcwtsLogger.error("Error while getting report list : " + e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	public ActionForward upCommingMonthBenefitResult(ActionMapping mapping,ActionForm form,
			 HttpServletRequest request,
			HttpServletResponse response) {
		final String MY_NAME = ME + "upCommingMonthBenefitResult: ";
		BcwtsLogger.info(MY_NAME
				+ "entering into upCommingMonthBenefitResult method");
		String returnValue = "UpcomingBenefit";
		ReportManagement reportManagement = new ReportManagement();
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		List ReportList = null;
		List PartnerNewCardList = null;
		List PartnerHotListTableList= null;
		List PartnerBatchDetailsList = null;
		List fieldList = null;
		int yesCount = 0;
		int noCount = 0;
		ReportForm reportForm = (ReportForm) form;
	
		
		Long partnerId = null;
		String companyid = "";
		String userName = null;
		String schoolName = "";
		UpCommingMonthBenefitDTO upcommingmonthbenefitdtoNF = new UpCommingMonthBenefitDTO();
	
		try {
			if(reportForm.getReportFormat() != null){
				reportFormat = reportForm.getReportFormat();
			}
	
			
			
			if (null != session.getAttribute("NextFareId")) {
				companyid = (String) session
						.getAttribute("NextFareId");
				reportForm.setCompanyId(companyid);
			
				request.setAttribute(Constants.COMPANY_NAME_REPORT, "UPASS");
			}
			
			
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				fieldList
						.add(PropertyReader.getValue(Constants.CARD_SERIAL_NO));
				fieldList.add(PropertyReader.getValue(Constants.EMPLOYEE_NAME));
				fieldList.add(PropertyReader.getValue(Constants.EMPLOYEE_ID));
				fieldList.add(PropertyReader
						.getValue(Constants.IS_BENEFIT_ACTIVATED));
				ReportList = reportManagement.getUpcommingMonthlyReportList(
						new Long(companyid));
			     
				
				 
				
				PartnerHotListTableList = reportManagement.getUpcommingMonthlyActReportList(
						new Long(companyid));
				PartnerNewCardList = reportManagement.getUpcommingMonthlyDeactReportList(
						new Long(companyid));
				PartnerBatchDetailsList = reportManagement.getUpcommingMonthlyBatchDetails(new Long(companyid));
		
				
				for(Iterator it=PartnerHotListTableList.iterator();it.hasNext();){
				Object O = null;	
					UpCommingMonthBenefitDTO upcommingmonthbenefitdto = (UpCommingMonthBenefitDTO) it.next();
					
					/* check to remove entries in Partner Hotlist Table from Report List */
					for(Iterator iter=ReportList.iterator();iter.hasNext();){
						upcommingmonthbenefitdtoNF = (UpCommingMonthBenefitDTO) iter.next();
						if(upcommingmonthbenefitdtoNF.getCardSerialNumber().equals(upcommingmonthbenefitdto.getCardSerialNumber().substring(0, 16))){
							O = upcommingmonthbenefitdtoNF; 	
						}
						
					}
					ReportList.remove(O);
					
					ReportList.add(upcommingmonthbenefitdto);
					
				}
				for(Iterator it=PartnerBatchDetailsList.iterator();it.hasNext();){
					Object O = null;	
						UpCommingMonthBenefitDTO upcommingmonthbenefitdto = (UpCommingMonthBenefitDTO) it.next();
						
						/* check to remove entries in Partner Hotlist Table from Report List */
						for(Iterator iter=ReportList.iterator();iter.hasNext();){
							upcommingmonthbenefitdtoNF = (UpCommingMonthBenefitDTO) iter.next();
							if(upcommingmonthbenefitdtoNF.getCardSerialNumber().equals(upcommingmonthbenefitdto.getCardSerialNumber().substring(0, 16))){
								O = upcommingmonthbenefitdtoNF; 	
							}
							
						}
						ReportList.remove(O);
						
						ReportList.add(upcommingmonthbenefitdto);
						
					}
            for(Iterator it=PartnerNewCardList.iterator();it.hasNext();){
					
					UpCommingMonthBenefitDTO upcommingmonthbenefitdto = (UpCommingMonthBenefitDTO) it.next();
					ReportList.add(upcommingmonthbenefitdto);
					
				}
            
            for(Iterator iter = ReportList.iterator();iter.hasNext();){
            	UpCommingMonthBenefitDTO upcommingmonthbenefitdto2 = (UpCommingMonthBenefitDTO) iter.next();
            	 if(upcommingmonthbenefitdto2.getIsBenefitActivated().equalsIgnoreCase("Yes"))
            		 yesCount++;
            	 else
            		 noCount++;
            		 
            }
            
        //    ReportList.size();
				reportForm.setUpCommingReportList(ReportList);
				reportForm.setUpCommingReportActList(PartnerHotListTableList);
				reportForm.setUpCommingReportDeactList(PartnerBatchDetailsList);
				reportForm.setUpCommingNewCardList(PartnerNewCardList);
				reportForm.setYesCount(yesCount);
				reportForm.setNoCount(noCount);
				
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					reportForm.setIsBlock("1");
					session.setAttribute(
							Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT, ReportList);
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generateUpassUpcomingMonthlyReportPDF(
							Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT, fieldList,
							ReportList,request,reportForm);
					responseGenerate("application/pdf",
							Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT + ".pdf",
							response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					baos = ExcelGenerator.generateUpassPartnerUpcomingMonthlyReportExcel(
							Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT, fieldList,
							ReportList,request, reportForm);
					responseGenerate("application/xls",
							Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT + ".xls",
							response, baos);
				}
			}
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
	
	

}
