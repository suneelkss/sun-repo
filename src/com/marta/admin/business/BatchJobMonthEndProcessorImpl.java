package com.marta.admin.business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.marta.admin.business.BatchJobScheduler;
import com.marta.admin.business.NewNextfareMethods;
import com.marta.admin.dao.AdminPartnerBatchProcessDAO;
import com.marta.admin.dao.BatchOMJobDAO;
import com.marta.admin.dto.BatchProcessDataDTO;
import com.marta.admin.hibernate.BcwtHotListCardDetails;
import com.marta.admin.hibernate.BcwtPartnerNewCard;
import com.marta.admin.hibernate.PartnerAdminDetails;
import com.marta.admin.hibernate.PartnerBatchDetails;
import com.marta.admin.hibernate.PartnerBatchDetailsHistory;
import com.marta.admin.hibernate.PartnerBatchDetailsHistoryId;
import com.marta.admin.hibernate.PartnerCompanyInfo;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.dto.PartnerAdminDetailsDTO;
import com.marta.admin.dto.UpCommingMonthBenefitDTO;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class BatchJobMonthEndProcessorImpl extends BatchJobScheduler {

	final String ME = "BatchJobMonthEndProcessorImpl: ";

	public boolean updateProductPrice() throws Exception {
		final String MY_NAME = ME + "updateProductPrice: ";
		AdminPartnerBatchProcessDAO partnerBatchProcessDAO = new AdminPartnerBatchProcessDAO();

		boolean isUpdated = partnerBatchProcessDAO.updateProductPrice();
		BcwtsLogger.debug(MY_NAME + "isUpdated: " + isUpdated);

		return isUpdated;
	}

	public void processJobs() throws Exception {

		ArrayList companyids;
		int count;
		ArrayList benefits;
		double totalNewCardQuantity;

		BatchOMJobDAO batchOMJobDAO = new BatchOMJobDAO();

		BcwtsLogger.debug(" Processing Upcomming Monthly Job Started");
		// Upcoming Monthly 
		processUpcommingMonth();
		BcwtsLogger.debug(" Processing Upcomming Monthly Job End");
		BcwtsLogger.debug(" Processing New Card Order Started");
		// New Cards
		processNewCardOrders();
		BcwtsLogger.debug(" Processing New Card Order End");
		// run the stored procedure
		BcwtsLogger.debug(" Stored Procedure Started");
		batchOMJobDAO.insertIntoOMIface();
		BcwtsLogger.debug(" Stored Procedure End");

	}

	public static void processUpcommingMonth() {
		ArrayList companyids;
		int count;
		ArrayList benefits;
		double totalNewCardQuantity;

		BatchOMJobDAO batchOMJobDAO = new BatchOMJobDAO();

		// Get all companyids
		try {
			companyids = (ArrayList) batchOMJobDAO.getCustomers();

			for (Iterator iter = companyids.iterator(); iter.hasNext();) {

				PartnerCompanyInfo partnerCompanyInfo = (PartnerCompanyInfo) iter
						.next();
				// Get Oracle/Agencyid from Nextfare
				String agencyId = batchOMJobDAO.getAgencyID(partnerCompanyInfo
						.getNextfareCompanyId());
				// Get company upcoming benefits count
				count = getUpcomingBenifits(partnerCompanyInfo
						.getNextfareCompanyId());
				// Get company active benefit list

				benefits = batchOMJobDAO.getFareInstrumentId(partnerCompanyInfo
						.getNextfareCompanyId());
				String OrderNumber = partnerCompanyInfo.getCompanyName() + "-"
						+ Util.getDate().toString();
				// Get company company new card orders
				// totalNewCardQuantity =
				// batchOMJobDAO.getCardOrders(partnerCompanyInfo.getCompanyId().toString());
				// insert into temp iface table for each company
				batchOMJobDAO.updateCustomTableOM(agencyId, count, OrderNumber,"UM");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void processNewCardOrders() {
		ArrayList companyids;
		int count;
		ArrayList benefits;
		int totalNewCardQuantity;

		BatchOMJobDAO batchOMJobDAO = new BatchOMJobDAO();

		// Get all companyids
		try {
			companyids = (ArrayList) batchOMJobDAO.getCustomers();

			for (Iterator iter = companyids.iterator(); iter.hasNext();) {

				PartnerCompanyInfo partnerCompanyInfo = (PartnerCompanyInfo) iter
						.next();
				// Get Oracle/Agencyid from Nextfare
				String agencyId = batchOMJobDAO.getAgencyID(partnerCompanyInfo
						.getNextfareCompanyId());
		
				String OrderNumber = "New Card Order" + "-"
						+ Util.getDate().toString();
				// Get company company new card orders
				 totalNewCardQuantity =
				   batchOMJobDAO.getCardOrders(partnerCompanyInfo.getCompanyId().toString());
				// insert into temp iface table for each company
				batchOMJobDAO.updateCustomTableOM(agencyId, totalNewCardQuantity, OrderNumber,"NC");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public static int getUpcomingBenifits(String nextFareCustomerId) {

		List ReportList = null;
		List PartnerNewCardList = null;
		List PartnerHotListTableList = null;
		List PartnerBatchDetailsList = null;
		List fieldList = null;
		int count = 0;
		// PartnerAdminDetailsDTO partnerAdminDetailsDTO = null;
		Long partnerId = null;
		String userName = null;
		String companyName = "";
		UpCommingMonthBenefitDTO upcommingmonthbenefitdtoNF = new UpCommingMonthBenefitDTO();
		BatchOMJobDAO batchOMJobDAO = new BatchOMJobDAO();
		try {
			ReportList = batchOMJobDAO
					.getUpcommingMonthlyReportList(nextFareCustomerId);

			PartnerHotListTableList = batchOMJobDAO
					.getHotlistCardDetails(nextFareCustomerId);
			PartnerNewCardList = batchOMJobDAO
					.getNewCardDetails(nextFareCustomerId);
			PartnerBatchDetailsList = batchOMJobDAO.getBatchDetailsList(nextFareCustomerId);

			for (Iterator it = PartnerHotListTableList.iterator(); it.hasNext();) {
				Object O = null;
				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = (UpCommingMonthBenefitDTO) it
						.next();

				/*
				 * check to remove entries in Partner Hotlist Table from Report
				 * List
				 */
				for (Iterator iter = ReportList.iterator(); iter.hasNext();) {
					upcommingmonthbenefitdtoNF = (UpCommingMonthBenefitDTO) iter
							.next();
					if (upcommingmonthbenefitdtoNF.getCardSerialNumber()
							.equals(
									upcommingmonthbenefitdto
											.getCardSerialNumber().substring(0,
													16))) {
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
			for (Iterator it = PartnerNewCardList.iterator(); it.hasNext();) {

				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = (UpCommingMonthBenefitDTO) it
						.next();
				ReportList.add(upcommingmonthbenefitdto);

			}

			count = 0;
			for(Iterator finalIter = ReportList.iterator();finalIter.hasNext();){
				UpCommingMonthBenefitDTO upcommingmonthbenefitdtoFinal = (UpCommingMonthBenefitDTO) finalIter.next(); 
				if(upcommingmonthbenefitdtoFinal.getIsBenefitActivated().equalsIgnoreCase("Yes"))
					count++;
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public static void main(String[] args) {
		BatchJobMonthEndProcessorImpl temp = new BatchJobMonthEndProcessorImpl();
		try {
			temp.processJobs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
