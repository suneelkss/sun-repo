package com.marta.admin.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.PartnerUpcomingMonthlyReportManagement;
import com.marta.admin.dao.ListCardsDAO;
import com.marta.admin.dto.BcwtPartneIdDTO;
import com.marta.admin.dto.PartnerReportSearchDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.ExcelGenerator;
import com.marta.admin.utils.PdfGenerator;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;
import com.marta.admin.dto.UpCommingMonthBenefitDTO;
/**
 * Action class for handling request for Partner Upcoming Monthly Report Management
 * 
 */
public class PartnerUpcomingMonthlyReportAction extends DispatchAction {
	
	final String ME = "DetailedOrderReportAction: ";
	
	//method to call jsp
	/**
	 * Method to Upcoming Monthly Benefit Report
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	
	public ActionForward upCommingMonthReport(ActionMapping mapping,ActionForm form,
			 HttpServletRequest request,
			HttpServletResponse response) {
		final String MY_NAME = ME + "upCommingMonthReport: ";
		BcwtsLogger.info(MY_NAME
				+ "entering into upCommingMonthReport method");
		String returnValue = "UpcomingBenefit";
		PartnerUpcomingMonthlyReportManagement reportManagement = new PartnerUpcomingMonthlyReportManagement();
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		List reportList = null;
		List deActList = null;
		List actList= null;
		
		ReportForm reportForm = (ReportForm) form;
		try {
			reportList = new ArrayList();
			reportForm.setUpCommingReportList(reportList);
			reportForm.setUpCommingReportActList(actList);
			reportForm.setUpCommingReportDeactList(deActList);
			reportForm.setIsBlock("0");
			
			returnValue = "UpcomingBenefit";
			BcwtsLogger.info(MY_NAME + "ended into action");
		} catch (Exception ex) {
			BcwtsLogger.error("Error while displaying report : "
					+ ex.getMessage());
			returnValue = ErrorHandler.handleError(ex, "", request, mapping);
		}
		session.removeAttribute(Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT);
		request.removeAttribute(Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT);
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_UPCOMING_MONTHLY_BENEFIT_REPORT);
		BcwtsLogger.debug(ME + " returnValue" + returnValue);
		return mapping.findForward(returnValue);
	}
	
	/**
	 * Method to Partner UpComming MonthBenefit Result.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	
//	method for upcoming monthly benefit report
	public ActionForward partnerUpCommingMonthBenefitResult(ActionMapping mapping,ActionForm form,
			 HttpServletRequest request,
			HttpServletResponse response) {
		final String MY_NAME = ME + "partnerUpCommingMonthBenefitResult: ";
		BcwtsLogger.info(MY_NAME
				+ "entering into partnerUpCommingMonthBenefitResult method");
		String returnValue = "UpcomingBenefit";
		PartnerUpcomingMonthlyReportManagement reportManagement = new PartnerUpcomingMonthlyReportManagement();
		HttpSession session = request.getSession(true);
		String reportFormat = null;
		List<UpCommingMonthBenefitDTO> reportList = null;
		List deActList = null;
		List actList= null;
		List fieldList = null;
		List batchDetailsList = null;
		ReportForm reportForm = (ReportForm) form;
		Long partnerId = null;
		String userName = null;
		String companyName = "";
		String region = "";
		List partnerIdList = null;
		BcwtPartneIdDTO partneIdDTO = null;
		Long nextfareCompanyId = new Long(0);
		UpCommingMonthBenefitDTO upcommingmonthbenefitdtoNF = new UpCommingMonthBenefitDTO();
		ListCardsDAO listCardsDAO = new ListCardsDAO();
		int accountCount = 0;
		Map<String,Integer> productName  = new HashMap<String,Integer>();
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
		try {
			if (null != request.getParameter("reportFormat")) {
				reportFormat = request.getParameter("reportFormat");
				reportForm.setReportFormat(reportFormat);
			}
			region = request.getParameter("region");
			reportForm.setRegion(region);
			BcwtsLogger.debug(MY_NAME + "User Name:" + userName);
			
			if(!Util.isBlankOrNull(reportForm.getCompanyName())){
				request.setAttribute("NAME", reportForm.getCompanyName());
			}
			
			if (!Util.isBlankOrNull(reportFormat)) {
				fieldList = new ArrayList();
				fieldList.add("Company Name");
				fieldList.add(PropertyReader.getValue(Constants.BREEZECARD_SERIAL_NUMBER));
				fieldList.add("First Name");
				fieldList.add("Last Name");
				fieldList.add(PropertyReader.getValue(Constants.EMPLOYEE_ID));
				fieldList.add("Benefit Name");
				fieldList.add("Benefit Status");
				fieldList.add("Last Modified");
				
				fieldList.add("Benefit Region");
				partnerIdList = reportManagement.getPartnerIdList(reportForm);
				if(partnerIdList !=null && !partnerIdList.isEmpty()){
					for(Iterator iter = partnerIdList.iterator();iter.hasNext();){
						partneIdDTO = (BcwtPartneIdDTO) iter.next();
						if(partneIdDTO !=null){
							partnerId = Long.valueOf(partneIdDTO.getPartnerId());
							
							/*System.out.println("Partner ID: "+partnerId);
							System.out.println("Partner ID: "+partneIdDTO.getCompanyId());*/
							nextfareCompanyId = Long.valueOf(partneIdDTO.getCompanyId());
							if(nextfareCompanyId !=null && 
									!Util.isBlankOrNull(nextfareCompanyId.toString())){
								reportList = reportManagement.getUpcommingMonthlyReportList(
										nextfareCompanyId,region);
								accountCount = reportList.size();
								actList = reportManagement.getUpcommingMonthlyActReportList(
										partnerId);
								deActList = reportManagement.getUpcommingMonthlyDeactReportList(
										partnerId);
								batchDetailsList = reportManagement.getUpcommingMonthlyBatchDetails(partnerId);
								
								
								for(Iterator it=actList.iterator();it.hasNext();){
									Object O = null;	
										UpCommingMonthBenefitDTO upcommingmonthbenefitdto = (UpCommingMonthBenefitDTO) it.next();
										upcommingmonthbenefitdto.setOperatorid("17");
										/* check to remove entries in Partner Hotlist Table from Report List */
										for(Iterator itr=reportList.iterator();itr.hasNext();){
											upcommingmonthbenefitdtoNF = (UpCommingMonthBenefitDTO) itr.next();
											if(upcommingmonthbenefitdtoNF.getCardSerialNumber().equals(upcommingmonthbenefitdto.getCardSerialNumber().substring(0, 16)) && 
													upcommingmonthbenefitdtoNF.getBenefitId().equals(upcommingmonthbenefitdto.getBenefitId()) ){
												O = upcommingmonthbenefitdtoNF; 
											/*	upcommingmonthbenefitdto.setRegion(upcommingmonthbenefitdtoNF.getRegion());
												upcommingmonthbenefitdto.setBenefitName(upcommingmonthbenefitdtoNF.getBenefitName());
												if(upcommingmonthbenefitdtoNF.getOperatorid().equals("18"))
												upcommingmonthbenefitdto.setOperatorid("17");
												else
													upcommingmonthbenefitdto.setOperatorid(upcommingmonthbenefitdtoNF.getOperatorid());*/
											}
											
										}
										reportList.remove(O);
										if(Util.isBlankOrNull(upcommingmonthbenefitdto.getBenefitName())){
											if(!upcommingmonthbenefitdto.getBenefitId().equals("0")){
											String benefitdetails = listCardsDAO.getCustomerBenefitNameforBenefitId(partnerId.toString(),upcommingmonthbenefitdto.getBenefitId());
											String details[] = benefitdetails.split("/");
											upcommingmonthbenefitdto.setBenefitName(details[0]);
											upcommingmonthbenefitdto.setRegion(details[1]);
											if (details[0].equals("18"))
												upcommingmonthbenefitdto.setOperatorid("17");
											else
												upcommingmonthbenefitdto
														.setOperatorid(details[2]);
											
											}	
											
										}
										if(!region.equals("0")){
										if(upcommingmonthbenefitdto.getOperatorid().equals(region)){
											 reportList.add(upcommingmonthbenefitdto);
										 }}
										else
											 reportList.add(upcommingmonthbenefitdto);
										
										
										
									}
								for(Iterator it=batchDetailsList.iterator();it.hasNext();){
									Object O = null;	
										UpCommingMonthBenefitDTO upcommingmonthbenefitdto = (UpCommingMonthBenefitDTO) it.next();
										upcommingmonthbenefitdto.setOperatorid("17");
										/* check to remove entries in Partner Hotlist Table from Report List */
										 for(Iterator itr=reportList.iterator();itr.hasNext();){
											upcommingmonthbenefitdtoNF = (UpCommingMonthBenefitDTO) itr.next();
											if(upcommingmonthbenefitdtoNF.getCardSerialNumber().equals(upcommingmonthbenefitdto.getCardSerialNumber().substring(0, 16))
													&& upcommingmonthbenefitdtoNF.getBenefitId().equals(upcommingmonthbenefitdto.getBenefitId())){
												O = upcommingmonthbenefitdtoNF; 
											/*	upcommingmonthbenefitdto.setRegion(upcommingmonthbenefitdtoNF.getRegion());
												upcommingmonthbenefitdto.setBenefitName(upcommingmonthbenefitdtoNF.getBenefitName());
												upcommingmonthbenefitdto.setCompanyName(upcommingmonthbenefitdtoNF.getCompanyName());
												if(upcommingmonthbenefitdtoNF.getOperatorid().equals("18"))
													upcommingmonthbenefitdto.setOperatorid("17");
													else
														upcommingmonthbenefitdto.setOperatorid(upcommingmonthbenefitdtoNF.getOperatorid());*/
											}
											
										}
										reportList.remove(O);
										if(Util.isBlankOrNull(upcommingmonthbenefitdto.getBenefitName())){
											if(!upcommingmonthbenefitdto.getBenefitId().equals("0")){
											String benefitdetails = listCardsDAO.getCustomerBenefitNameforBenefitId(partnerId.toString(),upcommingmonthbenefitdto.getBenefitId());
											String details[] = benefitdetails.split("/");
											upcommingmonthbenefitdto.setBenefitName(details[0]);
											upcommingmonthbenefitdto.setRegion(details[1]);
											if (details[0].equals("18"))
												upcommingmonthbenefitdto.setOperatorid("17");
											else
												upcommingmonthbenefitdto
														.setOperatorid(details[2]);
											
											}	
											
										}
										if(!region.equals("0")){
											if(upcommingmonthbenefitdto.getOperatorid().equals(region)){
												 reportList.add(upcommingmonthbenefitdto);
											 }}
											else
												 reportList.add(upcommingmonthbenefitdto);
										
									}
								  for(Iterator it=deActList.iterator();it.hasNext();){
										
										UpCommingMonthBenefitDTO upcommingmonthbenefitdto = (UpCommingMonthBenefitDTO) it.next();
										reportList.add(upcommingmonthbenefitdto);
										
									}
								
								
							}else{
								request.setAttribute(Constants.MESSAGE, "NextFare CompanyId cannot be null.");
							}
							reportForm.setUpCommingReportList(reportList);
							reportForm.setUpCommingReportActList(actList);
							reportForm.setUpCommingReportDeactList(deActList);
						}
				 }
			    }
				int deActiveCount = 0;
				int activeCount = 0;
				int newCardCount = 0;
				for(Iterator itx = reportList.iterator(); itx.hasNext();){
					UpCommingMonthBenefitDTO upcommingmonthbenefitdto = (UpCommingMonthBenefitDTO) itx
							.next();
					 if(upcommingmonthbenefitdto.getCardSerialNumber().contains(" queue") && upcommingmonthbenefitdto.getIsBenefitActivated().equals("Inactive") && upcommingmonthbenefitdto.getIsBenefitActivated().equals("Inactive") && Util.isBlankOrNull(upcommingmonthbenefitdto.getCardAction()))
						    deActiveCount++;
					 else if(upcommingmonthbenefitdto.getCardSerialNumber().contains(" queue") && upcommingmonthbenefitdto.getIsBenefitActivated().equals("Active")&& upcommingmonthbenefitdto.getIsBenefitActivated().equals("Inactive") && Util.isBlankOrNull(upcommingmonthbenefitdto.getCardAction()))
						 activeCount++;   
					 else  if(!Util.isBlankOrNull(upcommingmonthbenefitdto.getCardAction())){
						 if( upcommingmonthbenefitdto.getCardAction().equalsIgnoreCase("New")){
					    newCardCount ++;
						 }
			}
					
				}
				
				 for (Map.Entry<String, Integer> entry : productName.entrySet()){
						for(UpCommingMonthBenefitDTO upcommingmonthbenefitdto:reportList){
					        if(null != upcommingmonthbenefitdto.getBenefitName()){     
							if(entry.getKey().equals(upcommingmonthbenefitdto.getBenefitName().trim())){
					            	productName.put(entry.getKey(),entry.getValue()+1); 
					             }
					        }
				 }
				 }
				
				request.setAttribute("NoINQueue", String.valueOf(deActiveCount));
				int actualActiveCount = activeCount;
				request.setAttribute("YesINQueue", String.valueOf(actualActiveCount));
				request.setAttribute("AccountCount", String.valueOf(accountCount));
				request.setAttribute("NewCardCount", String.valueOf(newCardCount+deActList.size()));
				session.setAttribute("PRODUCTMAP", productName);
				if(actList == null){
					actList = new ArrayList();
				}
				if(deActList == null){
					deActList = new ArrayList();
				}
				if(reportList ==null){
					reportList = new ArrayList();
				}
				
				if(!Util.isBlankOrNull(reportForm.getSubCompanyName())){
					if(!reportList.isEmpty()){
					UpCommingMonthBenefitDTO orderReportDTO = (UpCommingMonthBenefitDTO) reportList.get(1);
	            	request.setAttribute("NAME", orderReportDTO.getCompanyName());
					}
				}
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (reportFormat.equalsIgnoreCase(Constants.HTML_TYPE)) {
					reportForm.setIsBlock("1");
					session.setAttribute(
							Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT, reportList);
				}
				if (reportFormat.equalsIgnoreCase(Constants.PDF_TYPE)) {
					baos = PdfGenerator.generatePartnerUpcomingMonthlyReportPDF("UPCOMING MONTHLY BENEFIT_REPORT", fieldList,
							reportList,request,reportForm,productName);
					responseGenerate("application/pdf",
							Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT + ".pdf",
							response, baos);
				}
				if (reportFormat.equalsIgnoreCase(Constants.EXCEL_TYPE)) {
					baos = ExcelGenerator.generatePartnerUpcomingMonthlyReportExcel("UPCOMING MONTHLY BENEFIT_REPORT", fieldList,
							reportList,request,reportForm,productName);
					responseGenerate("application/xls",
							Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT + ".xls",
							response, baos);
				}
			}
			session.setAttribute(
					Constants.PARTNER_UPCOMMING_MON_BENEFIT_REPORT, reportList);
			BcwtsLogger.info(MY_NAME + "ended into action");
		} catch (Exception ex) {
			BcwtsLogger.error("Error while displaying report : "
					+ Util.getFormattedStackTrace(ex));
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
