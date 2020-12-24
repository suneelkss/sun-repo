package com.marta.admin.business;

import java.util.ArrayList;
import java.util.List;

import com.marta.admin.dao.PartnerNewCardReportDAO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.PartnerNewCardReportForm;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;

/**
 * @author Sowjanya Business class for get report Details
 */
public class PartnerNewCardReportManagement {
	final String ME = "PartnerNewCardReportManagement: ";
	/**
	 * Method to get Partner New Card Report Details.
	 * 
	 * @return newCardReportList
	 * @throws MartaException
	 */
	public List getPartnerNewCardReportList(PartnerNewCardReportForm partnerNewCardReportForm) throws Exception {
		  final String MY_NAME = ME + " getNewCardReportList: ";
			BcwtsLogger.debug(MY_NAME + " Fetching report for NewCardReportList.");
			List newCardReportList = null;
			PartnerNewCardReportDAO partnerNewCardreportDAO = null;
			try {
				newCardReportList = new ArrayList();
				partnerNewCardreportDAO = new PartnerNewCardReportDAO();
				newCardReportList = partnerNewCardreportDAO.getPartnerNewCardReportList(partnerNewCardReportForm);
				BcwtsLogger.info(MY_NAME + " got report List " + newCardReportList.size());
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Exception in getting reportlist: "+e.getMessage());
				throw new MartaException(PropertyReader
						.getValue(MartaConstants.BCWTS_REPORT_LIST));
			}
			return newCardReportList;
	  }
}
