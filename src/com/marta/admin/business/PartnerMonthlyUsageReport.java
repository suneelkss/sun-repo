package com.marta.admin.business;

import java.util.List;

import com.marta.admin.dao.PartnerMonthlyUsageReportDAO;

import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.PartnerMonthlyUsageReportForm;
import com.marta.admin.utils.BcwtsLogger;

/**
 * 
 * @author Jagadeesan
 *
 */
public class PartnerMonthlyUsageReport {

	final String ME = "ReportManagement: ";

	/**
	 * 
	 * 
	 * @param date
	 * @param partnerMonthlyUsageReportForm
	 * @return
	 * @throws MartaException
	 */
	public List getPartnerMonthlyUsageSummaryReport(String date, String todate,
			PartnerMonthlyUsageReportForm partnerMonthlyUsageReportForm) throws MartaException {
		
		final String MY_NAME = ME + " getPartnerMonthlyUsageSummaryReport: ";
		BcwtsLogger.debug(MY_NAME + " getting partner monthly usage summary report information ");
		List parterMonthlyUsageSummaryReportList = null;
		PartnerMonthlyUsageReportDAO partnerMonthlyUsageReportDAO = null;
		try {
			partnerMonthlyUsageReportDAO = new PartnerMonthlyUsageReportDAO();
			parterMonthlyUsageSummaryReportList = partnerMonthlyUsageReportDAO
										.getPartnerMonthlyUsageSummaryReport(date,todate, partnerMonthlyUsageReportForm);
			BcwtsLogger.info(MY_NAME + " got partner monthly usage summary report" 
					+ parterMonthlyUsageSummaryReportList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting partner monthly usage summary report :"
					+ e.getMessage());
		}
		return parterMonthlyUsageSummaryReportList;
	}
}