package com.marta.admin.utils;

import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.business.ListCardBatchProcess;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;

public class ListCardsBatchProcessServletHelper {
	
	private PSListCardsBatchProcessTimer listCardsBatchProcessTimer = null;
	private boolean isCont = true;

	public void startPSListCardsBatchProcessTimer(){
		listCardsBatchProcessTimer = new PSListCardsBatchProcessTimer();
		listCardsBatchProcessTimer.start();
	}

	public void stopPSListCardsBatchProcessTimer(){
		isCont = false;
		listCardsBatchProcessTimer.interrupt();
	}

	public class PSListCardsBatchProcessTimer extends Thread {
	
		public void run(){
			boolean isSuccess = false;
			while(isCont){
				try {
					if(Util.getCurrentDate().equalsIgnoreCase(Util.getLastDateOfMonth())){
						BcwtsLogger.info("******** PSListCardsBatchProcess - Started **********");
						isSuccess = executeBatch();
						BcwtsLogger.info("PSListCardsBatchProcess.run() - isSuccess: " + isSuccess);
					}
					sleep(86400000); //24 hours = 8 64 00000 milliseconds
				} catch(InterruptedException ex){
				    interrupt();
				    break;
				} catch (Exception e) {					
					BcwtsLogger.error("Exception in PSListCardsBatchProcessTimer.run(): " + e.getMessage());
				}
		    }
		}	
	} 

	private boolean executeBatch() throws MartaException {
		ListCardBatchProcess psListCardsBatchProcess = new ListCardBatchProcess();
		BcwtConfigParamsDTO configParamDTO = null;
		boolean isSuccess = false;
		
		try {
			configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
			if(configParamDTO.getParamvalue().equalsIgnoreCase(Constants.YES)){

				psListCardsBatchProcess.addNewCard();
				
				psListCardsBatchProcess.activateDeactivateBenefit();
			}	
			isSuccess = true;
		} catch (Exception e) {
			BcwtsLogger.error("Exception in executeBatch(): " + e.getMessage());
		}
		return isSuccess;
	}
	

}
