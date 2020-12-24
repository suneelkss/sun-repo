package com.marta.admin.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;






import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.PartnerMonthlyUsageReportDTO;
import com.marta.admin.business.PartnerMonthlyUsageReport;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.PartnerMonthlyUsageReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.Util;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.PdfGenerator;
import com.marta.admin.utils.ExcelGenerator;


/**
 * 
 * @author Jagadeesan
 *
 */
public class PartnerMonthlyUsageReportAction extends DispatchAction{

	final String ME = "PartnerMonthlyUsageReportAction";
	
	/**
	 * Method to show partner monthly usage report
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayPartnerMonthlyUsageReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayPartnerMonthlyUsageReport: ";
		BcwtsLogger.debug(MY_NAME + "Displaying partner monthly usage report page");
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		String returnValue = "partnerMonthlyUsageSummaryReport";
		PartnerMonthlyUsageReportForm partnerMonthlyUsageReportForm = (PartnerMonthlyUsageReportForm)form;
		String userName = null;
		String date = null;
		String month = null;
		String year = null;
		String todate = null;
		String tomonth = null;
		String toyear = null;
		List fieldList = null;
		List reportListActive = new ArrayList();
		BcwtPatronDTO bcwtPatronDTO = null;
	    PartnerMonthlyUsageReport partnerMonthlyUsageReport  = null;
	    List reportList = null;
	    String generateFor = "";
		try {
			if(partnerMonthlyUsageReportForm.getReportFormat() != null){
				reportFormat = partnerMonthlyUsageReportForm.getReportFormat();
			}
			if(null != request.getParameter("generateFor")){
				generateFor = request.getParameter("generateFor");
				
			}
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				userName = bcwtPatronDTO.getFirstname() + bcwtPatronDTO.getLastname();
			}
			if(null != partnerMonthlyUsageReportForm.getMonth()){
				month = (String)partnerMonthlyUsageReportForm.getMonth();
			}
			if(null != partnerMonthlyUsageReportForm.getYear()){
				year = (String)partnerMonthlyUsageReportForm.getYear();
			}
			date = month+"/"+01+"/"+year;
			
			if(null != partnerMonthlyUsageReportForm.getTomonth()){
				tomonth = (String)partnerMonthlyUsageReportForm.getTomonth();
			}
			if(null != partnerMonthlyUsageReportForm.getToyear()){
				toyear = (String)partnerMonthlyUsageReportForm.getToyear();
			}
			todate = tomonth+"/"+31+"/"+toyear;
			
			if(!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getTmaName()))
				request.setAttribute("NAME", partnerMonthlyUsageReportForm.getTmaName());
			
			List monthList = Util.getMonths();
			List yearList = Util.getPastYears();
			session.setAttribute(Constants.MONTH_LIST, monthList);
			session.setAttribute(Constants.YEAR_LIST, yearList);
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				fieldList.add("Company Name");
				fieldList.add(PropertyReader.getValue(Constants.breezeCardNumber));
			//	fieldList.add(PropertyReader.getValue(Constants.BENEFIT_NAME));
				fieldList.add(PropertyReader.getValue(Constants.BILLING_MONTH));
				fieldList.add(PropertyReader.getValue(Constants.MONTHLY_USAGE));
				partnerMonthlyUsageReport = new PartnerMonthlyUsageReport();
				
				 String dateRange = date + " to " + todate;
			     session.setAttribute("MONTH_D_RANGE", dateRange);
			     request.setAttribute("MONTH_D_RANGE", dateRange);
				
				reportList = partnerMonthlyUsageReport
										.getPartnerMonthlyUsageSummaryReport(date,todate, partnerMonthlyUsageReportForm);
				
				if(generateFor.equalsIgnoreCase("ACTIVE")){
					for(Iterator iter=reportList.iterator();iter.hasNext();){
						
						PartnerMonthlyUsageReportDTO temp = (PartnerMonthlyUsageReportDTO) iter.next();
						BcwtsLogger.info("What --"+temp.getBreezeCardSerialNumber()+" -"+temp.getCompanyName()+" -"+temp.getMonthlyUsage());
						if(temp.getMonthlyUsage().equalsIgnoreCase("Yes")){
							reportListActive.add(temp);
						}
						
					}
					reportList = reportListActive;
				}
				
				if(reportList == null){
					reportList = new ArrayList();
				}
				
				if(!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getPartnerFirstName())){
					if(!reportList.isEmpty()){
					PartnerMonthlyUsageReportDTO partnerMonthlyUsageReportDTO = (PartnerMonthlyUsageReportDTO) reportList.get(1);
					request.setAttribute("NAME", partnerMonthlyUsageReportDTO.getCompanyName());
					}
				}
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					session.setAttribute(Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT, reportList);
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generatePartnerMonthlyUsageReportPDF(
							Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT, fieldList, reportList,request);
					responseGenerate("application/pdf",
							Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT + ".pdf", response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)){
					baos = ExcelGenerator
							.generatePartnerMonthlyUsageSummaryReport(Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT, 
									fieldList, reportList, request);
					responseGenerate("application/xls", Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT+ ".xls", 
							response, baos);
				}
			}
			session.setAttribute(Constants.PARTNER_MONTHLY_USAGE_SUMMARY_REPORT, reportList);
			returnValue = "partnerMonthlyUsageSummaryReport";
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while displaying partner monthly usage summary report page:"
					+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME, 
								">> Reports >> Partner Reports >> Partner Monthly Usage Summary Report");
		return mapping.findForward(returnValue);
	}
	/**
	 * 
	 * 
	 * @param contentype
	 * @param filename
	 * @param response
	 * @param baos
	 */
	private void responseGenerate(String contentype,String filename,
			HttpServletResponse response,ByteArrayOutputStream baos ){
	
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
}
