package com.marta.admin.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.PartnerNewCardReportManagement;
import com.marta.admin.business.ReportManagement;
import com.marta.admin.dto.BcwtPartneIdDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.PartnerMonthlyUsageReportDTO;
import com.marta.admin.dto.PartnerNewCardReportAdminDetailsDTO;
import com.marta.admin.dto.PartnerNewCardReportDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.PartnerNewCardReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.ExcelGenerator;
import com.marta.admin.utils.PdfGenerator;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

/**
 * Action class for handling request for Partner New Card Report Management
 * 
 * @author Sowjanya
 */
public class PartnerNewCardReport extends DispatchAction{
	final String ME = "PartnerNewCardReport: ";
	/**
	 * Method to display Partner New card Report.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayPartnerNewCardReport(ActionMapping mapping,ActionForm form,
			 HttpServletRequest request,HttpServletResponse response){
		final String MY_NAME = ME + "displayPartnerNewCardReport: ";
		BcwtsLogger.info(MY_NAME + "entering into action");
		String returnValue = "partnerNewCardReport";
		HttpSession session = request.getSession(true);
		Long partnerId = new Long(0); 
		String userName = null;
		BcwtPatronDTO bcwtPatronDTO = null;
		try{
			if (null != session.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				partnerId = bcwtPatronDTO.getPatronid();
				userName = bcwtPatronDTO.getFirstname() + bcwtPatronDTO.getLastname();
			}
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			List monthList=Util.getMonths();
			List yearList=Util.getPastYears();
			session.setAttribute(Constants.MONTH_LIST,monthList);
			session.setAttribute(Constants.YEAR_LIST,yearList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, ">> Reports >> Partner Report >> Partner New Card Report ");
			BcwtsLogger.info(MY_NAME + "ended into action");
		}catch(Exception e){
			BcwtsLogger.error("Error while getting report list : " + e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	/**
	 * Method to display Partner New card Report Result.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayPartnerNewCardReportResult(ActionMapping mapping,ActionForm form,
			 HttpServletRequest request,
			HttpServletResponse response) {
		final String MY_NAME = ME + "partnernewCardReport: ";
		BcwtsLogger.info(MY_NAME + "entering into Partner newCardReport method");
		String returnValue = "partnerNewCardReport";
		PartnerNewCardReportManagement partnerNewCardreportManagement = new PartnerNewCardReportManagement();
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		List reportList =new ArrayList();
		List fieldList = new ArrayList();
		PartnerNewCardReportForm partnerNewCardReportForm = (PartnerNewCardReportForm) form;
		Long partnerId =null;
		String userName = null;
		Long nextfareCompanyId = new Long(0);
		Set<String> newCardCount = new HashSet<String>();
		if (null != request.getParameter("reportFormat")) {
			reportFormat = request.getParameter("reportFormat");
			partnerNewCardReportForm.setReportFormat(reportFormat);
		}
		try {
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			
		/*	SimpleDateFormat sdfInput = new SimpleDateFormat("dd/mm/yyyy") ;			
		    SimpleDateFormat sdfOutput = new SimpleDateFormat("mm/dd/yyyy") ;
		    Date fromDate = sdfInput.parse("01/" + partnerNewCardReportForm.getMonthYear());
		    int month = Integer.parseInt(partnerNewCardReportForm.getCardToMonth());
		    int year = Integer.parseInt(partnerNewCardReportForm.getCardToYear());
		    Date toDate = sdfInput.parse(Util.lastDayOfMonth(month, year) + "/" + partnerNewCardReportForm.getMonthYearTo());
			String strFromDate = sdfOutput.format(fromDate);
			String strToDate = sdfOutput.format(toDate);
			
			String dateRange = strFromDate +" to " + strToDate;
			
			request.setAttribute("NEW_D_RANGE", dateRange);
			session.setAttribute("NEW_D_RANGE", dateRange);*/
			
			if(!Util.isBlankOrNull(partnerNewCardReportForm.getTmaName()))
				request.setAttribute("NAME", partnerNewCardReportForm.getTmaName());
			
			StringBuffer sf = new StringBuffer();
			sf.append(partnerNewCardReportForm.getCardMonth()).append("/").append(
					partnerNewCardReportForm.getCardYear());
			partnerNewCardReportForm.setMonthYear(sf.toString());
			StringBuffer my = new StringBuffer();
			my.append(partnerNewCardReportForm.getCardToMonth()).append("/").append(
					partnerNewCardReportForm.getCardToYear());
			partnerNewCardReportForm.setMonthYearTo(my.toString());
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				fieldList.add("Company Name");
				fieldList.add(PropertyReader.getValue(Constants.BREEZECARD_SERIAL_NUMBER));
				fieldList.add(PropertyReader.getValue(Constants.BENEFIT_NAME));
				fieldList.add(PropertyReader.getValue(Constants.EFFECTIVE_DATE));
				PartnerNewCardReportDTO newCardReportDTO = null;
				
				reportList = partnerNewCardreportManagement.getPartnerNewCardReportList(partnerNewCardReportForm);
				
				SimpleDateFormat sdfInput = new SimpleDateFormat("dd/mm/yyyy") ;			
			    SimpleDateFormat sdfOutput = new SimpleDateFormat("mm/dd/yyyy") ;
			    Date fromDate = sdfInput.parse("01/" + partnerNewCardReportForm.getMonthYear());
			    int month = Integer.parseInt(partnerNewCardReportForm.getCardToMonth());
			    int year = Integer.parseInt(partnerNewCardReportForm.getCardToYear());
			    Date toDate = sdfInput.parse(Util.lastDayOfMonth(month, year) + "/" + partnerNewCardReportForm.getMonthYearTo());
				String strFromDate = sdfOutput.format(fromDate);
				String strToDate = sdfOutput.format(toDate);
				
				String dateRange = strFromDate +" to " + strToDate;
				
				request.setAttribute("NEW_D_RANGE", dateRange);
				session.setAttribute("NEW_D_RANGE", dateRange);
				
				if(reportList !=null && !reportList.isEmpty()){
					for(Iterator iter = reportList.iterator();iter.hasNext();){
						newCardReportDTO = (PartnerNewCardReportDTO) iter.next();
						if(newCardReportDTO !=null){
							partnerId = newCardReportDTO.getPartnerId();
							nextfareCompanyId = Long.valueOf(newCardReportDTO.getCompanyId());
						}
					}
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if(!Util.isBlankOrNull(partnerNewCardReportForm.getPartnerFirstName())){
					if(!reportList.isEmpty()){
						PartnerNewCardReportDTO partnerNewCardReportDTO = (PartnerNewCardReportDTO) reportList.get(1);
					request.setAttribute("NAME", partnerNewCardReportDTO.getCompanyName());
					}
				}
				
				for(Iterator iter = reportList.iterator();iter.hasNext();){
					PartnerNewCardReportDTO partnerNewCardReportDTO = (PartnerNewCardReportDTO)iter.next();
					newCardCount.add(partnerNewCardReportDTO.getCardSerialNumber());
				}
				  request.setAttribute("NEWCARDCOUNT", String.valueOf(newCardCount.size()));
				
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					session.setAttribute(Constants.PARTNER_NEW_CARD_REPORT, reportList);
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generatePartnerNewCardReportPDF(
							Constants.PARTNER_NEW_CARD_REPORT, fieldList, reportList,request);
					responseGenerate("application/pdf",
							Constants.PARTNER_NEW_CARD_REPORT + ".pdf", response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					baos = ExcelGenerator.generatePartnerNewCardReportExcel(
							Constants.PARTNER_NEW_CARD_REPORT, fieldList, reportList,request);
					responseGenerate("application/xls",
							Constants.PARTNER_NEW_CARD_REPORT + ".xls", response, baos);
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
