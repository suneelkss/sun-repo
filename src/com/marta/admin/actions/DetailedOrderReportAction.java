package com.marta.admin.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.DetailedOrderReportManagement;
import com.marta.admin.business.ReportManagement;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;
import com.marta.admin.utils.ExcelGenerator;
import com.marta.admin.utils.PdfGenerator;

public class DetailedOrderReportAction extends DispatchAction {
	
	final String ME = "DetailedOrderReportAction: ";
	
	public ActionForward displayOrderReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayOrderReport: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partnerss");
		HttpSession session = request.getSession(true);
		DetailedOrderReportManagement reportManagement = null;
		String returnValue = "orderReport";
		List orderReport = null;
		String reportFormat = null;
	    List fieldList = null;
	    List reportList = null;
		
		try {
			ReportForm reportForm =(ReportForm) form;
			BcwtPatronDTO bcwtPatronDTO = null;
			reportManagement = new DetailedOrderReportManagement();
			List newCardReport = new ArrayList();
			if (null != request.getParameter("reportFormat")) {
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			if(reportForm.getReportFormat() != null){
				reportFormat = reportForm.getReportFormat();
			}
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				fieldList.add(PropertyReader.getValue(Constants.COMPANY_NAME));
				fieldList.add(PropertyReader.getValue(Constants.PARTNER_ADMIN_NAME));
				fieldList.add(PropertyReader.getValue(Constants.EMPLOYEE_NAME));
				fieldList.add(PropertyReader.getValue(Constants.BREEZECARD_SERIAL_NUMBER));
				fieldList.add(PropertyReader.getValue(Constants.BENEFIT_NAME));
				fieldList.add(PropertyReader.getValue(Constants.EFFECTIVE_DATE));
				reportList = reportManagement.getDetailedOrderReport(reportForm);
				
				if(reportForm.getBillingStartDate() !=null &&
						reportForm.getBillingEndDate() != null &&
						!Util.isBlankOrNull(reportForm.getBillingStartDate()) &&
						!Util.isBlankOrNull(reportForm.getBillingEndDate())){
					SimpleDateFormat sdfInput = new SimpleDateFormat("MM/dd/yyyy") ;			
				    SimpleDateFormat sdfOutput = new SimpleDateFormat("MM/dd/yyyy") ;
				    String strFromDate = null;
				    String strToDate = null;
				    
				    Date fromDate = sdfInput.parse(reportForm.getBillingStartDate());
				    
				    Date toDate = sdfInput.parse(reportForm.getBillingEndDate());
					strFromDate = sdfOutput.format(fromDate);
					strToDate = sdfOutput.format(toDate);
					
					String dateRange = strFromDate + " to "+ strToDate;
					session.setAttribute("DORDER_D_RANGE", dateRange);
					request.setAttribute("DORDER_D_RANGE", dateRange);
					
				}
				
				if(reportList == null){
					reportList = new ArrayList();
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					//reportForm.setIsBlock("1");
					session.setAttribute(Constants.ORDER_REPORT, reportList);
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generateDetailedOrderPDF(
							Constants.DETAILED_ORDER_REPORT, fieldList, reportList,request);
					responseGenerate("application/pdf",
							Constants.DETAILED_ORDER_REPORT + ".pdf", response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					baos = ExcelGenerator.generateDetailedOrderExcel(
							Constants.DETAILED_ORDER_REPORT, fieldList, reportList,request);
					responseGenerate("application/xls",
							Constants.DETAILED_ORDER_REPORT + ".xls", response, baos);
				}
			}
			
			session.setAttribute(Constants.ORDER_REPORT, reportList);
			returnValue = "orderReport";
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "Unable to get Values", request, mapping);
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
		    	e.printStackTrace();
		    }	
	}

}
