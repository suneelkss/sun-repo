package com.marta.admin.business;

import java.util.ArrayList;
import java.util.List;

import com.marta.admin.dao.PartnerUpcomingMonthlyReportDAO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;

/**
 * Business class for get Upcoming Monthly Benefit report Details
 */
public class PartnerUpcomingMonthlyReportManagement {
	final String ME = "partnerUpcomingMonthlyReportManagement: ";
	/*
	 * to get the partnerId for PS
	 * 
	 */
	/**
	 * Method to get PartnerId List.
	 * 
	 * @return upCommingMonthlyReportList
	 * @throws MartaException
	 */
	public List getPartnerIdList(ReportForm reportform) throws Exception {
		final String MY_NAME = ME + " getPartnerIdList: ";
  		BcwtsLogger.debug(MY_NAME + " Fetching report for getPartnerIdList.");
  		List upCommingMonthlyReportList = null;
  		PartnerUpcomingMonthlyReportDAO reportDAO = null;
  		try {
  			upCommingMonthlyReportList = new ArrayList();
  			reportDAO = new PartnerUpcomingMonthlyReportDAO();
  			upCommingMonthlyReportList = reportDAO.getPartnerIdList(reportform);
  			BcwtsLogger.info(MY_NAME + " got report List " + upCommingMonthlyReportList.size());
  		} catch (Exception e) {
  			BcwtsLogger.error(MY_NAME + " Exception in getting getUpcommingMonthlyReportList reportlist: "+e.getMessage());
  			throw new MartaException(PropertyReader
  					.getValue(MartaConstants.BCWTS_REPORT_LIST));
  		}
  		return upCommingMonthlyReportList;
	}
	/**
	 * Method to get get upcomming monthly benefit.
	 * 
	 * @return upCommingMonthlyReportList
	 * @throws MartaException
	 */
	 /*
     * Method to get upcomming monthly benefit Report in Partner Sales
     * 
     */
    public List getUpcommingMonthlyReportList(Long nextfareCompanyId,String region) throws Exception {
  	  final String MY_NAME = ME + " getUpcommingMonthlyReportList: ";
  		BcwtsLogger.debug(MY_NAME + " Fetching report for getUpcommingMonthlyReportList.");
  		List upCommingMonthlyReportList = null;
  		PartnerUpcomingMonthlyReportDAO reportDAO = null;
  		try {
  			upCommingMonthlyReportList = new ArrayList();
  			reportDAO = new PartnerUpcomingMonthlyReportDAO();
  			upCommingMonthlyReportList = reportDAO.getUpcommingMonthlyReportListNew(nextfareCompanyId, region);
  			BcwtsLogger.info(MY_NAME + " got report List " + upCommingMonthlyReportList.size());
  		} catch (Exception e) {
  			BcwtsLogger.error(MY_NAME + " Exception in getting getUpcommingMonthlyReportList reportlist: "+e.getMessage());
  			throw new MartaException(PropertyReader
  					.getValue(MartaConstants.BCWTS_REPORT_LIST));
  		}
  		return upCommingMonthlyReportList;
  }
    /*
     * Method to get upcomming monthly benefit activate Report in Partner Sales
     * 
     */
    public List getUpcommingMonthlyActReportList(Long nextfareCompanyId) throws Exception {
    	  final String MY_NAME = ME + " getUpcommingMonthlyActReportList: ";
    		BcwtsLogger.debug(MY_NAME + " Fetching report for getUpcommingMonthlyActReportList.");
    		List upCommingMonthlyReportList = null;
    		PartnerUpcomingMonthlyReportDAO reportDAO = null;
    		try {
    			upCommingMonthlyReportList = new ArrayList();
    			reportDAO = new PartnerUpcomingMonthlyReportDAO();
    			upCommingMonthlyReportList = reportDAO.getHotlistCardDetails(nextfareCompanyId);
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
   public List getUpcommingMonthlyDeactReportList(Long nextfareCompanyId) throws Exception {
    	  final String MY_NAME = ME + " getUpcommingMonthlyDeactReportList: ";
    		BcwtsLogger.debug(MY_NAME + " Fetching report for getUpcommingMonthlyDeactReportList.");
    		List upCommingMonthlyReportList = null;
    		PartnerUpcomingMonthlyReportDAO reportDAO = null;
    		try {
    			upCommingMonthlyReportList = new ArrayList();
    			reportDAO = new PartnerUpcomingMonthlyReportDAO();
    			upCommingMonthlyReportList = reportDAO.getNewCardDetails(nextfareCompanyId);
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
   		PartnerUpcomingMonthlyReportDAO reportDAO = null;
   		try {
   			upCommingMonthlyReportList = new ArrayList();
   			reportDAO = new PartnerUpcomingMonthlyReportDAO();
   			upCommingMonthlyReportList = reportDAO.getBatchDetailsList(partnerId); // getUpcommingActivateReportList(partnerId);
   			BcwtsLogger.info(MY_NAME + " got activate report List " + upCommingMonthlyReportList.size());
   		} catch (Exception e) {
   			BcwtsLogger.error(MY_NAME + " Exception in getting getUpcommingMonthlyActReportList : "+e.getMessage());
   			throw new MartaException(PropertyReader
   					.getValue(MartaConstants.BCWTS_REPORT_ACT_LIST));
   		}
   		return upCommingMonthlyReportList;
   }
   

}
