
package com.marta.admin.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.marta.admin.dao.ReportDAO;
import com.marta.admin.dto.BcwtReportDTO;
import com.marta.admin.dto.BcwtReportSearchDTO;
import com.marta.admin.dto.BcwtsPartnerIssueDTO;
import com.marta.admin.dto.DisplayProductSummaryDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.hibernate.TmaInformation;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;
import com.marta.admin.dto.ProductDetailsReportDTO;

/**
 * @author Sowjanya Business class for get all report Details
 */
public class ReportManagement {
	final String ME = "ReportManagement: ";
	
	public  Map<String,Integer> productName;
	
	
	
	  
	public Map<String, Integer> getProductName() {
		return productName;
	}

	public void setProductName(Map<String, Integer> productName) {
		this.productName = productName;
	}
	
	
	
	public List getTMAList() throws MartaException {
		final String MY_NAME = ME + " getTMAList: ";
		BcwtsLogger.debug(MY_NAME + " getting tma information ");
		//List tmaList = null;
		ReportDAO reportDAO = null;
		List<LabelValueBean> tmaPairList = new ArrayList<LabelValueBean>();
		try {
			reportDAO = new ReportDAO();
			List tmaList = reportDAO.getTMAList();
				for (Iterator iter = tmaList.iterator(); iter
						.hasNext();) {
					TmaInformation element =  (TmaInformation) iter.next();
					tmaPairList.add(new LabelValueBean(element.getTmaName(), element.getTmaId().toString()));
				}
			BcwtsLogger.info(MY_NAME + " got hotlist" + tmaList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting hotlist :"
					+ e.getMessage());
		}
		return tmaPairList;
	}
	
	
	/**
	 * Method to get HotList Report.
	 * 
	 * @return hotListList
	 * @throws MartaException
	 */
	public List getHotListReport(ReportForm reportForm) throws MartaException {
		final String MY_NAME = ME + " getHotlistReport: ";
		BcwtsLogger.debug(MY_NAME + " getting hotlist information ");
		List hotListList = null;
		ReportDAO reportDAO = null;
		try {
			reportDAO = new ReportDAO();
			hotListList = reportDAO.getHotListReport(reportForm);
			BcwtsLogger.info(MY_NAME + " got hotlist" + hotListList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting hotlist :"
					+ e.getMessage());
		}
		return hotListList;
	}
	
	public String getTMAName(String tmaid) throws MartaException {
		final String MY_NAME = ME + " getTMAName: ";
		BcwtsLogger.debug(MY_NAME + tmaid);
		String tmaName = null;
		ReportDAO reportDAO = null;
		try {
			reportDAO = new ReportDAO();
			tmaName = reportDAO.getTmaName(tmaid);
		//	BcwtsLogger.info(MY_NAME + " got hotlist" + hotListList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting tma name :"
					+ e.getMessage());
		}
		return tmaName;
	}
	
	public List getTMAHotListReport(ReportForm reportForm) throws MartaException {
		final String MY_NAME = ME + " getTMAHotListReport: ";
		BcwtsLogger.debug(MY_NAME + " getting hotlist information ");
		List hotListList = null;
		ReportDAO reportDAO = null;
		try {
			reportDAO = new ReportDAO();
			hotListList = reportDAO.getTMAHotListReport(reportForm);
			BcwtsLogger.info(MY_NAME + " got hotlist" + hotListList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting hotlist :"
					+ e.getMessage());
		}
		return hotListList;
	}
	
	public List<DisplayProductSummaryDTO> getProductSummarytReport(ReportForm reportForm) throws MartaException {
		final String MY_NAME = ME + " getProductSummarytReport: ";
		BcwtsLogger.debug(MY_NAME + " getting product summary information ");
		List<DisplayProductSummaryDTO> productList = null;
		ReportDAO reportDAO = null;
		try {
			reportDAO = new ReportDAO();
			productList = reportDAO.getPartnerProductSummary(reportForm);
			productList.addAll(reportDAO.getPartnerProductSummaryCalM(reportForm));
			BcwtsLogger.info(MY_NAME + " got product summary" + productList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting product summary :"
					+ e.getMessage());
		}
		return productList;
	}
	
	public List<ProductDetailsReportDTO> getProductDetailsReport(ReportForm reportForm) throws MartaException {
		final String MY_NAME = ME + " getProductDetailsReport: ";
		BcwtsLogger.debug(MY_NAME + " getting product details information ");
		List<ProductDetailsReportDTO> productList = null;
		ReportDAO reportDAO = null;
		try {
			reportDAO = new ReportDAO();
			productList = reportDAO.getPartnerProductDetails(reportForm);
			productList.addAll(reportDAO.getPartnerProductDetailsCalM(reportForm));
			productList.addAll(reportDAO.getPartnerProductDetailsRemove(reportForm));
						BcwtsLogger.info(MY_NAME + " got product details" + productList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting product details :"
					+ e.getMessage());
		}
		return productList;
	}
	
	public List<BcwtsPartnerIssueDTO> getTMAIssueReport(String fromDate,String toDate) throws MartaException {
		final String MY_NAME = ME + " getTMAIssueReport: ";
		BcwtsLogger.debug(MY_NAME + " getting issue information ");
		List<BcwtsPartnerIssueDTO> issueList = null;
		ReportDAO reportDAO = null;
		try {
			reportDAO = new ReportDAO();
			issueList = reportDAO.getTMAIssueList(fromDate,toDate);
			BcwtsLogger.info(MY_NAME + " got issue list" + issueList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting hotlist :"
					+ e.getMessage());
		}
		return issueList;
	}
	
	/**
	 * Method to get NewCard Report.
	 * 
	 * @return newCardList
	 * @throws MartaException
	 */
	//method for new card report
	public List getNewCardReport(ReportForm reportForm) throws MartaException {
		final String MY_NAME = ME + " getNewCardReport: ";
		
		BcwtsLogger.debug(MY_NAME + " getting newcard information ");
		List newCardList = null;
		ReportDAO reportDAO = null;
		try {
			reportDAO = new ReportDAO();
			newCardList = reportDAO.getNewCardReport(reportForm);
			
			BcwtsLogger.info(MY_NAME + " got newcard" + newCardList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting newcard :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_REPORT_FIND_HOTLIST));
		}
		return newCardList;
	}
	
	 /*
	   * Method to get New Card Report in Partner Sales
	   * 
	   */
	public List getNewCardReportList(ReportForm form) throws Exception {
		  
		  final String MY_NAME = ME + " getNewCardReportList: ";
			BcwtsLogger.debug(MY_NAME + " Fetching report for NewCardReportList.");
			List newCardReportList = null;
			ReportDAO reportDAO = null;
			try {
				newCardReportList = new ArrayList();
				reportDAO = new ReportDAO();
				newCardReportList = reportDAO.getNewCardReportList(form);
				BcwtsLogger.info(MY_NAME + " got report List " + newCardReportList.size());
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Exception in getting reportlist: "+e.getMessage());
				throw new MartaException(PropertyReader
						.getValue(MartaConstants.BCWTS_REPORT_LIST));
			}
			return newCardReportList;
	  }
	
	/**
	 * Method to get Usage Report.
	 * 
	 * @return usageList
	 * @throws MartaException
	 */
	public List getUsageReport(String date,ReportForm reportForm) throws MartaException {
		final String MY_NAME = ME + " getUsageReport: ";
		BcwtsLogger.debug(MY_NAME + " getting useReport information ");
		List usageList = null;
		ReportDAO reportDAO = null;
		try {
			usageList = new ArrayList();
			reportDAO = new ReportDAO();
			usageList = reportDAO.getUsageReport(date,reportForm);
			BcwtsLogger.info(MY_NAME + " got useage Report" + usageList.size());
			} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting usageReport :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_REPORT_FIND_HOTLIST));
		}
		return usageList;
	}
	
	/**
	 * Method to get ActiveBenefits Report.
	 * 
	 * @return activeBenefitList
	 * @throws MartaException
	 */
	
	public List getActiveBenefits(ReportForm reportForm) throws Exception {
		  
		  final String MY_NAME = ME + " getActiveBenefits: ";
			BcwtsLogger.debug(MY_NAME + " Fetching report for ActiveBenefits.");
			List activeBenefitList = null;
			ReportDAO reportDAO = null;
			try {
				activeBenefitList = new ArrayList();
				reportDAO = new ReportDAO();
				reportDAO.setProductName();
				activeBenefitList = reportDAO.getActiveBenefitsList(reportForm);
				
				productName = reportDAO.getProductName();
				BcwtsLogger.info(MY_NAME + " got report List " + activeBenefitList.size());
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Exception in getting reportlist: "+e.getMessage());
				throw new MartaException(PropertyReader
						.getValue(MartaConstants.BCWTS_REPORT_LIST));
			}
			return activeBenefitList;
	  }
	
	 public List getActiveBenefits(String partnerId) throws Exception {
		  
		  final String MY_NAME = ME + " getActiveBenefits: ";
			BcwtsLogger.debug(MY_NAME + " Fetching report for ActiveBenefits.");
			List activeBenefitList = null;
			ReportDAO reportDAO = null;
			try {
				activeBenefitList = new ArrayList();
				reportDAO = new ReportDAO();
				activeBenefitList = reportDAO.getUpassActiveBenefitsList(partnerId);
				BcwtsLogger.info(MY_NAME + " got report List " + activeBenefitList.size());
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Exception in getting reportlist: "+e.getMessage());
				throw new MartaException(PropertyReader
						.getValue(MartaConstants.BCWTS_REPORT_LIST));
			}
			return activeBenefitList;
	  }
	
	
	/**
	 * Method to get General Ledger Report
	 * @param bcwtReportSearchDTO
	 * @return reportList
	 * @throws MartaException
	 */
	public List getGeneralLedgerReport(BcwtReportSearchDTO bcwtReportSearchDTO) throws MartaException {
		
		final String MY_NAME = ME + " getGeneralLedgerReport: ";
		BcwtsLogger.debug(MY_NAME + " getGeneralLedgerReport");
		
		List reportList = new ArrayList();
		ReportDAO reportDAO = new ReportDAO();
		try {
			reportList = reportDAO.getGeneralLedgerReport(bcwtReportSearchDTO);//getGeneralLedgerReport(bcwtReportSearchDTO);
			BcwtsLogger.info(MY_NAME + " got GeneralLedgerReport " + reportList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting GeneralLedgerReport: "+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_GENERAL_LEDGER_REPORT));
		}
		return reportList;
		
	}
	
	/***************** OF - Starts here **********************/
	
	/**
	 * Method to get Status information.
	 * 
	 * @return statusList
	 * @throws MartaException
	 */
	public List getStatusInformation(String orderType) throws MartaException {
		final String MY_NAME = ME + " getStatusInformation: ";
		BcwtsLogger.debug(MY_NAME + " getting status information ");
		List statusList = null;
		ReportDAO reportDAO = new ReportDAO();
		try {
			if(!Util.isBlankOrNull(orderType) && orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				statusList = reportDAO.getPSStatusInformation();
			} else {
				statusList = reportDAO.getStatusInformation();
			}
			if(statusList != null){
			    BcwtsLogger.info(MY_NAME + " got status List" + statusList.size());
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting status list: "+e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting status list");
		}
		return statusList;
	}
	
	/**
	 * Method to get Status information.
	 * 
	 * @return statusList
	 * @throws MartaException
	 */
	public List getStatusInformation() throws MartaException {
		final String MY_NAME = ME + " getStatusInformation: ";
		BcwtsLogger.debug(MY_NAME + " getting status information ");
		List statusList = null;
		ReportDAO reportDAO = new ReportDAO();
		try {
			statusList = reportDAO.getStatusInformation();
			if(statusList != null){
			    BcwtsLogger.info(MY_NAME + " got status List" + statusList.size());
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting status list: "+e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting status list");
		}
		return statusList;
	}

	/**
	 * Method to get report list.
	 
	 * @throws MartaException
	 */
	public List getReportList() throws MartaException {
		
		final String MY_NAME = ME + " getReportList: ";
		BcwtsLogger.debug(MY_NAME + " Fetching all reportList.");
		List reportList = new ArrayList();
		ReportDAO reportDAO = new ReportDAO();
		try {
			reportList = reportDAO.getReportList();
			BcwtsLogger.info(MY_NAME + " got report List " + reportList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting reportlist: "+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_REPORT_LIST));
		}
		return reportList;
		
	}
	/**
	 * Method for ordersummaryreport
	 * @param bcwtReportSearchDTO
	 * @return
	 * @throws MartaException
	 */
	public BcwtReportDTO getOrderSummaryReport(BcwtReportSearchDTO bcwtReportSearchDTO, String type) throws MartaException {
		
		final String MY_NAME = ME + " getOrderSummaryReport: ";
		BcwtsLogger.debug(MY_NAME + " Fetching order summaryreport.");
		BcwtReportDTO bcwtReportDTO = null;
		ReportDAO reportDAO = new ReportDAO();
		try {
			bcwtReportDTO = reportDAO.getOrderSummary(bcwtReportSearchDTO, type);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting orderSummaryReport: "+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ORDER_SUMMARY));
		}
		return bcwtReportDTO;
		
	}

	/**
	 * Method for outstanding orders report
	 * @param bcwtReportSearchDTO
	 * @return
	 * @throws MartaException
	 */
	public List getOutstandingOrdersReport(BcwtReportSearchDTO bcwtReportSearchDTO) throws MartaException {
		
		final String MY_NAME = ME + " getOutstandingOrdersReport: ";
		BcwtsLogger.debug(MY_NAME + " getOutstandingOrdersReport");
		
		List reportList = new ArrayList();
		ReportDAO reportDAO = new ReportDAO();
		try {
			reportList = reportDAO.getOutstandingOrdersReport(bcwtReportSearchDTO);
			BcwtsLogger.info(MY_NAME + " got OutstandingOrdersReport " + reportList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting OutstandingOrdersReport: "+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_OUTSTANDING_ORDERS_REPORT));
		}
		return reportList;		
	}
	
	/**
	 * Method to generate Quality Assurance Summary report
	 * @param bcwtReportSearchDTO
	 * @return
	 * @throws MartaException
	 */
	public List getQualityAssuranceSummary(BcwtReportSearchDTO bcwtReportSearchDTO) throws MartaException {
		
		final String MY_NAME = ME + " getQualityAssuranceSummary: ";
		BcwtsLogger.debug(MY_NAME + " getQualityAssuranceSummary");
		
		List reportList = new ArrayList();
		ReportDAO reportDAO = new ReportDAO();
		try {
			reportList = reportDAO.getQualityAssuranceSummary(bcwtReportSearchDTO);
			BcwtsLogger.info(MY_NAME + " got getQualityAssuranceSummary " + reportList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting getQualityAssuranceSummary: "+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_QUALITY_ASSURANCE_SUMMARY_REPORT));
		}
		return reportList;		
	}
	
	/**
	 * Method to generate Quality Assurance Summary report
	 * @param bcwtReportSearchDTO
	 * @return reportList
	 * @throws MartaException
	 */
	public List getReturnedOrdersReport(BcwtReportSearchDTO bcwtReportSearchDTO) throws MartaException {
		
		final String MY_NAME = ME + " getReturnedOrdersReport: ";
		BcwtsLogger.debug(MY_NAME + " getReturnedOrdersReport");
		
		List reportList = new ArrayList();
		ReportDAO reportDAO = new ReportDAO();
		try {
			reportList = reportDAO.getReturnedOrdersReport(bcwtReportSearchDTO);
			BcwtsLogger.info(MY_NAME + " got ReturnedOrdersReport " + reportList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting ReturnedOrdersReport: "+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_RETURNED_ORDERS_REPORT));
		}
		return reportList;
		
	}
	
	public List getOrderCancelledReport(BcwtReportSearchDTO bcwtReportSearchDTO) throws MartaException {
		
		final String MY_NAME = ME + " getOrderCancelledReport: ";
		BcwtsLogger.debug(MY_NAME + " Fetching order cancelreport.");
		List reportList = new ArrayList();
		ReportDAO reportDAO = new ReportDAO();
		try {
			reportList = reportDAO.getCancelledOrderReport(bcwtReportSearchDTO);
			BcwtsLogger.info(MY_NAME + " got order cancelled report " + reportList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting ordercancelledReport: "+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ORDER_CANCELLED));
		}
		return reportList;
		
	}
	
    public List getQualityDetailedReport(BcwtReportSearchDTO bcwtReportSearchDTO) throws MartaException {
		
		final String MY_NAME = ME + " getQualityDetailedReport: ";
		BcwtsLogger.debug(MY_NAME + " Fetching order qualitydetailreport.");
		List reportList = new ArrayList();
		ReportDAO reportDAO = new ReportDAO();
		try {
			reportList = reportDAO.getQualityDetailedReport(bcwtReportSearchDTO);
			BcwtsLogger.info(MY_NAME + " got order qualitydetail report " + reportList.size());
		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME + " Exception in getting qualitydetailedReport: "+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ORDER_CANCELLED));
		}
		return reportList;
		
	}	
    
    public List getMediaSalesPerformanceMetricsReport(BcwtReportSearchDTO bcwtReportSearchDTO) throws MartaException {
		final String MY_NAME = ME + " getMediaSalesPerformanceMetricsReport: ";
		BcwtsLogger.debug(MY_NAME);
		List reportList = new ArrayList();
		ReportDAO reportDAO = new ReportDAO();
		try {
			reportList = reportDAO.getMediaSalesPerformanceMetricsReport(bcwtReportSearchDTO);
			BcwtsLogger.info(MY_NAME + " got MediaSalesPerformanceMetricsReport List " + reportList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting MediaSalesPerformanceMetricsReport List: "+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_MEDIASALES_PERFORMANCE_METRICS_REPORT));
		}
		return reportList;
		
	}

	public List getSalesMetricsReport(BcwtReportSearchDTO bcwtReportSearchDTO) throws MartaException {
		
		final String MY_NAME = ME + " getSalesMetricsReport: ";
		BcwtsLogger.debug(MY_NAME + " Fetching SalesMerticsReport.");
		List reportList = null;	
		ReportDAO reportDAO = new ReportDAO();
		try {
			reportList = reportDAO.getSalesMetricsReport(bcwtReportSearchDTO);
			BcwtsLogger.info(MY_NAME + " got SalesMertics report " + reportList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting SalesMertics: "+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_SALES_METRICS_REPORT));
		}
		return reportList;
		
	}
	
	public int getNumberOfPendingOrders(String orderType, String fromDate, String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfPendingOrders: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfPendingOrders = 0;
		ReportDAO reportDAO = new ReportDAO();
		
		try {
			noOfPendingOrders = reportDAO.getNumberOfPendingOrders(orderType, fromDate, toDate);
			BcwtsLogger.info(MY_NAME + " got NumberOfPendingOrders = " + noOfPendingOrders);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + "Exception in getting noOfPendingOrders: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting Number of Pending Orders");
		}
		
		return noOfPendingOrders;		
	}
	
	public int getNumberOfOutstandingOrders(String orderType, String fromDate, String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfOutstandingOrders: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfOutstandingOrders = 0;
		ReportDAO reportDAO = new ReportDAO();
		
		try {
			noOfOutstandingOrders = reportDAO.getNumberOfOutstandingOrders(orderType, fromDate, toDate);
			BcwtsLogger.info(MY_NAME + " got noOfOutstandingOrders = " + noOfOutstandingOrders);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + "Exception in getting noOfOutstandingOrders: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting Number of Outstanding Orders");
		}
		
		return noOfOutstandingOrders;		
	}

	public int getNumberOfFulfilledOrders(String orderType, String fromDate, String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfFulfilledOrders: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfFulfilledOrders = 0;
		ReportDAO reportDAO = new ReportDAO();
		
		try {
			noOfFulfilledOrders = reportDAO.getNumberOfFulfilledOrders(orderType, fromDate, toDate);
			BcwtsLogger.info(MY_NAME + " got noOfFulfilledOrders = " + noOfFulfilledOrders);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + "Exception in getting NumberOfFulfilledOrders: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting Number of Fulfilled Orders");
		}
		
		return noOfFulfilledOrders;		
	}
	
	public int getNumberOfCancelledOrders(String orderType, String fromDate, String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfCancelledOrders: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfCancelledOrders = 0;
		ReportDAO reportDAO = new ReportDAO();
		
		try {
			noOfCancelledOrders = reportDAO.getNumberOfCancelledOrders(orderType, fromDate, toDate);
			BcwtsLogger.info(MY_NAME + " got noOfCancelledOrders = " + noOfCancelledOrders);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + "Exception in getting noOfCancelledOrders: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting Number of Cancelled Orders");
		}
		
		return noOfCancelledOrders;		
	}

	public int getNumberOfReturnedOrders(String orderType, String fromDate, String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfReturnedOrders: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfReturnedOrders = 0;
		ReportDAO reportDAO = new ReportDAO();
		
		try {
			noOfReturnedOrders = reportDAO.getNumberOfReturnedOrders(orderType, fromDate, toDate);
			BcwtsLogger.info(MY_NAME + " got noOfReturnedOrders = " + noOfReturnedOrders);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + "Exception in getting noOfReturnedOrders: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting Number of Returned Orders");
		}
		
		return noOfReturnedOrders;		
	}
	
	public int getNumberOfFails(String orderType, String fromDate, String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfFails: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfFails = 0;
		ReportDAO reportDAO = new ReportDAO();
		
		try {
			noOfFails = reportDAO.getNumberOfFails(orderType, fromDate, toDate);
			BcwtsLogger.info(MY_NAME + " got noOfFails = " + noOfFails);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + "Exception in getting noOfFails: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception in getting noOfFails");
		}
		
		return noOfFails;		
	}
	
	
	public List getRevenueReport(BcwtReportSearchDTO bcwtReportSearchDTO) throws MartaException {
		final String MY_NAME = ME + " getRevenueReport: ";
		BcwtsLogger.debug(MY_NAME + " Fetching Revenue Report");
		List reportList = null;	
		ReportDAO reportDAO = new ReportDAO();
		try {
		   if(bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(Constants.ORDER_TYPE_IS))
				reportList = reportDAO.getRevenueReportNew(bcwtReportSearchDTO);//getRevenueReportNew(bcwtReportSearchDTO);
			else
			reportList = reportDAO.getRevenueReport(bcwtReportSearchDTO);
			BcwtsLogger.info(MY_NAME + " got Revenue Report " + reportList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Revenue report: " + e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_REVENUE_REPORT));
		}
		return reportList;
		
	}
	
	public BcwtReportDTO getRevenueSummaryReport(BcwtReportSearchDTO bcwtReportSearchDTO, String creditCardType)
	throws MartaException {
		final String MY_NAME = ME + " getRevenueSummaryReport: ";
		BcwtsLogger.info(MY_NAME + " Fetching Revenue Summary Report");
		BcwtReportDTO bcwtReportDTO = null;
		ReportDAO reportDAO = new ReportDAO();
		try {
			bcwtReportDTO = reportDAO.getRevenueSummaryReport(bcwtReportSearchDTO, creditCardType);			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Revenue Summary Report: " + e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_REVENUE_SUMMARY_REPORT));
		}
		return bcwtReportDTO;
		
	}
	
	
	 public List getMonthlyUsageDetails(String date,String customerId) throws Exception {
		  
		  final String MY_NAME = ME + " getMonthlyUsageDetails: ";
			BcwtsLogger.debug(MY_NAME + " Fetching report for MonthyUsage.");
			List monthlyUsageList = null;
			ReportDAO reportDAO = null;
			try {
				monthlyUsageList = new ArrayList();
				reportDAO = new ReportDAO();
				monthlyUsageList = reportDAO.getMonthlyUsageList(date,customerId);
				BcwtsLogger.info(MY_NAME + " got report List " + monthlyUsageList.size());
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Exception in getting reportlist: "+Util.getFormattedStackTrace(e));
				throw new MartaException(PropertyReader
						.getValue(MartaConstants.BCWTS_REPORT_LIST));
			}
			return monthlyUsageList;
	} 
	 
	 public List getUpcommingMonthlyReportList(Long partnerId) throws Exception {
	   	  final String MY_NAME = ME + " getUpcommingMonthlyReportList: ";
	   		BcwtsLogger.debug(MY_NAME + " Fetching report for getUpcommingMonthlyReportList.");
	   		List upCommingMonthlyReportList = null;
	   		ReportDAO reportDAO = null;
	   		try {
	   			upCommingMonthlyReportList = new ArrayList();
	   			reportDAO = new ReportDAO();
	   			upCommingMonthlyReportList = reportDAO.getUpcommingMonthlyReportList(partnerId);
	   			BcwtsLogger.info(MY_NAME + " got report List " + upCommingMonthlyReportList.size());
	   		} catch (Exception e) {
	   			BcwtsLogger.error(MY_NAME + " Exception in getting getUpcommingMonthlyReportList reportlist: "+e.getMessage());
	   			throw new MartaException(PropertyReader
	   					.getValue(MartaConstants.BCWTS_REPORT_LIST));
	   		}
	   		return upCommingMonthlyReportList;
	   }

	   /*
      * Method to get upcomming monthly benefit Report in Partner Sales
      * 
      */
     public List getUpcommingMonthlyActReportList(Long partnerId) throws Exception {
     	  final String MY_NAME = ME + " getUpcommingMonthlyActReportList: ";
     		BcwtsLogger.debug(MY_NAME + " Fetching report for getUpcommingMonthlyActReportList.");
     		List upCommingMonthlyReportList = null;
     		ReportDAO reportDAO = null;
     		try {
     			upCommingMonthlyReportList = new ArrayList();
     			reportDAO = new ReportDAO();
     			upCommingMonthlyReportList = reportDAO.getHotlistCardDetails(partnerId); // getUpcommingActivateReportList(partnerId);
     			BcwtsLogger.info(MY_NAME + " got activate report List " + upCommingMonthlyReportList.size());
     		} catch (Exception e) {
     			BcwtsLogger.error(MY_NAME + " Exception in getting getUpcommingMonthlyActReportList : "+e.getMessage());
     			throw new MartaException(PropertyReader
     					.getValue(MartaConstants.BCWTS_REPORT_ACT_LIST));
     		}
     		return upCommingMonthlyReportList;
     }
     
     /*
      * Method to get upcomming monthly benefit Deactivated Report in Partner Sales
      * 
      */
    public List getUpcommingMonthlyDeactReportList(Long partnerId) throws Exception {
     	  final String MY_NAME = ME + " getUpcommingMonthlyDeactReportList: ";
     		BcwtsLogger.debug(MY_NAME + " Fetching report for getUpcommingMonthlyDeactReportList.");
     		List upCommingMonthlyReportList = null;
     		ReportDAO reportDAO = null;
     		try {
     			upCommingMonthlyReportList = new ArrayList();
     			reportDAO = new ReportDAO();
     			upCommingMonthlyReportList = reportDAO.getNewCardDetails(partnerId);   //getUpcommingMonthlyDeactivateList(partnerId);
     			BcwtsLogger.info(MY_NAME + " got De-activate report List " + upCommingMonthlyReportList.size());
     		} catch (Exception e) {
     			BcwtsLogger.error(MY_NAME + " Exception in getting getUpcommingMonthlyDeactReportList : "+e.getMessage());
     			throw new MartaException(PropertyReader
     					.getValue(MartaConstants.BCWTS_REPORT_DE_ACT_LIST));
     		}
     		return upCommingMonthlyReportList;
     }
    
    /*
     * Method to get upcomming monthly benefit Report in Partner Sales
     * 
     */
    public List getUpcommingMonthlyBatchDetails(Long partnerId) throws Exception {
    	  final String MY_NAME = ME + " getUpcommingMonthlyActReportList: ";
    		BcwtsLogger.debug(MY_NAME + " Fetching report for getUpcommingMonthlyActReportList.");
    		List upCommingMonthlyReportList = null;
    		ReportDAO reportDAO = null;
    		try {
    			upCommingMonthlyReportList = new ArrayList();
    			reportDAO = new ReportDAO();
    			upCommingMonthlyReportList = reportDAO.getBatchDetailsList(partnerId); // getUpcommingActivateReportList(partnerId);
    			BcwtsLogger.info(MY_NAME + " got activate report List " + upCommingMonthlyReportList.size());
    		} catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in getting getUpcommingMonthlyActReportList : "+e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_REPORT_ACT_LIST));
    		}
    		return upCommingMonthlyReportList;
    }
	 
	/***************** OF - Ends here **********************/
	
	
	
}
