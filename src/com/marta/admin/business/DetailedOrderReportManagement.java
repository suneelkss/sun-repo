package com.marta.admin.business;

import java.util.List;

import com.marta.admin.dao.DetailedOrderReportDAO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;
/**
 *  Business class for get Detailed Order report Details
 */
public class DetailedOrderReportManagement {
	final String ME = "DetailedOrderReportManagement: ";
	/**
	 * Method to get Detailed Order Report.
	 * 
	 * @return hotListList
	 * @throws MartaException
	 */
//	method for Detailed Order report
	public List getDetailedOrderReport(ReportForm reportForm) throws MartaException {
		final String MY_NAME = ME + " getDetailedOrderReport: ";
		
		BcwtsLogger.debug(MY_NAME + " getting detailed order information ");
		List detailedOrderList = null;
		DetailedOrderReportDAO reportDAO = null;
		try {
			reportDAO = new DetailedOrderReportDAO();
			detailedOrderList = reportDAO.getDetailedOrderReport(reportForm);
			
			BcwtsLogger.info(MY_NAME + " got newcard" + detailedOrderList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting newcard :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_REPORT_FIND_HOTLIST));
		}
		return detailedOrderList;
	}

}
