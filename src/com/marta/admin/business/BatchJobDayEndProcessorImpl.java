package com.marta.admin.business;

import java.util.ArrayList;
import java.util.Date;

import com.marta.admin.dao.BatchGLJobDAO;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.SendMail;
import com.marta.admin.utils.Util;

public class BatchJobDayEndProcessorImpl extends BatchJobScheduler {

	final String ME = "BatchJobDayEndProcessorImpl: ";

	public void processJobs() throws Exception {
		final String MY_NAME = ME + "processJobs: ";
		boolean isStatus;
		boolean gbsStatus;
		BatchGLJobDAO batchGLJobDAO = new BatchGLJobDAO();
		
		if(batchGLJobDAO.isJobRuning()){
			BcwtsLogger.info("--------Instance of the JOb already Running-----------");	
		}else{
	
		BcwtsLogger.info(MY_NAME + "Starting IS Process");
		isStatus = processIS();
		BcwtsLogger.info(MY_NAME + "END IS Process");

		BcwtsLogger.info(MY_NAME + "Starting GBS Process");
		gbsStatus = processGBS();
		BcwtsLogger.info(MY_NAME + "END GBS Process");

		/* Run the GL Iface Stored Procedure */
		BcwtsLogger.info(MY_NAME + "START GL IFACE STORED PROCUDURE");
		batchGLJobDAO.insertIntoGLIface();
		BcwtsLogger.info(MY_NAME + "END GL IFACE STORED PROCUDURE");
		
		/*send email to admin*/
		BcwtsLogger.info(MY_NAME + "START Send Status Email");
		sendMailToPSadmin(isStatus,gbsStatus);
		BcwtsLogger.info(MY_NAME + "End Send Status Email");
		}

	}
	public boolean updateProductPrice() throws Exception{
		boolean b = true;
		return b;
	}
	
	public boolean processGBS() {
		final String MY_NAME = ME + "processGBS: ";
		BcwtsLogger.info(MY_NAME + "Start GBS Process--");
		ArrayList newCardList;
		ArrayList storedValueList;
		ArrayList orderList;
		ArrayList discountList;
		BatchGLJobDAO batchGLJobDAO = new BatchGLJobDAO();
		boolean glStatus = true;
		try {

			/* Get products sold yesterday */
			orderList = batchGLJobDAO
					.getOrdersGBS(Util.getFormattedYesterday());
			/* Add to custom Table */
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --Start--");
			if (null != orderList)
				batchGLJobDAO.updateCustomTable(orderList);
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --END--");

			/* Get total shipping and card count */
			newCardList = batchGLJobDAO.getNewCardOrdersGBS(Util
					.getFormattedYesterday());
			/* Add to custom Table */
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --Start--");
			if (null != newCardList)
				batchGLJobDAO.updateCustomTable(newCardList);
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --END--");

			/* Get total stored value */
			storedValueList = batchGLJobDAO.getCashOrdersGBS(Util
					.getFormattedYesterday());
			/* Add to custom Table */
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --Start--");
			if (null != storedValueList)
				batchGLJobDAO.updateCustomTable(storedValueList);
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --END--");

			/* Get Total Discounts */
		/*	discountList = batchGLJobDAO.getDiscountsGBS(Util
					.getFormattedYesterday());
			/* Add to custom Table */
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --Start--");
		/*	if (null != discountList)
				batchGLJobDAO.updateCustomTable(discountList);*/
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --END--");
			BcwtsLogger.info(MY_NAME + "GBS PROCESS --END--");
		} catch (Exception e) {
			glStatus=false;
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(e));
		
		}
 return glStatus;
	}

	public boolean processIS() {
		final String MY_NAME = ME + "processIS: ";
		ArrayList orderList = new ArrayList();
		ArrayList newCardList = new ArrayList();
		ArrayList storedValueList = new ArrayList();
		boolean glStatus = true;
		BatchGLJobDAO batchGLJobDAO = new BatchGLJobDAO();

		try {

			// yesterday.;
			/* Get product Orders for yesterday */
			BcwtsLogger.info(MY_NAME + "Get Orders --Start--");
			orderList = batchGLJobDAO.getOrdersIS(Util.getFormattedYesterday());
			BcwtsLogger.info(MY_NAME + "Get Orders --END--");
			/* Add to custom Table */
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --Start--");
			if (null != orderList)
				batchGLJobDAO.updateCustomTable(orderList);
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --END--");
			/* Get cash Orders for yesterday */
			BcwtsLogger.info(MY_NAME + "Get Stored Value Orders --Start--");
			storedValueList = batchGLJobDAO.getCashOrdersIS(Util
					.getFormattedYesterday());
			BcwtsLogger.info(MY_NAME + "Get Stored Value  --END--");
			/* Add to custom Table */
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --Start--");
			if (null != storedValueList)
				batchGLJobDAO.updateCustomTable(storedValueList);
			BcwtsLogger.info(MY_NAME + "ADD to CUSTOM TABLE --END--");

			/* Get new card Orders for yesterday */
			BcwtsLogger.info(MY_NAME + "Get NEW CARDS Orders --Start--");
			newCardList = batchGLJobDAO.getNewCardOrdersIS(Util
					.getFormattedYesterday());
			BcwtsLogger.info(MY_NAME + "Get NEW CARDS Orders --END--");
			/* Add to custom Table */
			BcwtsLogger.info(MY_NAME
					+ "ADD NEW CARD ORDERS TO CUSTOM TABLE --END--");
			if (null != newCardList)
				batchGLJobDAO.updateCustomTable(newCardList);

		} catch (Exception e) {
			glStatus=false; 
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(e));
			

		}
		 return glStatus;
	}

public void sendMailToPSadmin(boolean isStatus, boolean gbsStatus) throws Exception {
		
		final String MY_NAME = ME + "sendMailToPSadmin: ";
		BcwtsLogger.debug(MY_NAME + "starting sending mail....");
		String passCounts = "0";
		String emailId = "ubaid@gantecusa.com";
		String isResult="fail";
		String gbsResult="fail";
		
		if(isStatus)
			isResult="Pass";
		if(gbsStatus)
			gbsResult="Pass";
			
		
		//failCounts = intFailCount.toString();
		ConfigurationCache configurationCache = new ConfigurationCache();
		BcwtConfigParamsDTO smtpPathDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
			.get("SMTP_SERVER_PATH");
		BcwtsLogger.debug(MY_NAME + "smtp path : "
				+ smtpPathDTO.getParamvalue());
		BcwtConfigParamsDTO fromDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
			.get("WEBMASTER_MAIL_ID");
		BcwtsLogger.debug(MY_NAME + "From Id : "
				+ fromDTO.getParamvalue());
		BcwtConfigParamsDTO mailportDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
			.get(Constants.MAIL_PORT);
		BcwtConfigParamsDTO mailprotocolDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
			.get(Constants.MAIL_PROTOCOL);
		String content = "<html>GL Daily Job Status<br/>" +
				"Individual Sales Status:"+isResult+"<br/>" + 
				"GBS Status:"+gbsResult+"" +
				
						"</html>";
			
			
			
		BcwtsLogger.debug(MY_NAME + "content :" + content);
		String subject = PropertyReader
		.getValue(Constants.BATCHPROCESS_STATUS_MAIL_SUBJECT);

		BcwtsLogger.debug(MY_NAME + "subject :" + subject
				+ "userEmail :" +emailId );
		try {

			SendMail.sendMail(fromDTO.getParamvalue(), emailId,
					content, smtpPathDTO.getParamvalue(), subject,
					"", mailportDTO.getParamvalue(),
					mailprotocolDTO.getParamvalue());
			BcwtsLogger.info(MY_NAME + "Mail sent sucess");
		} catch (Exception e) {
			BcwtsLogger.error("Error while sending email"+ Util.getFormattedStackTrace(e));
		}
	}
	
	
	
	public static void main(String[] args) {
		BatchJobDayEndProcessorImpl temp = new BatchJobDayEndProcessorImpl();
		try {
			temp.processJobs();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}