package com.marta.admin.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.marta.admin.business.UpdateConfigurationCache;

public class UpdateConfigurationCacheJob implements Job {
	
	final String ME = "UpdateConfigurationCacheJob :: ";
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		final String MY_NAME = ME + " execute: ";
		BcwtsLogger.debug(MY_NAME);
		
		UpdateConfigurationCache updateConfigurationCache 
									= new UpdateConfigurationCache();
		try {
			BcwtsLogger.info(MY_NAME + "*** Update Configuration Cache Job Started ***");
			
			updateConfigurationCache.updateConfigurationCache();
			
			BcwtsLogger.info(MY_NAME + "*** Update Configuration Cache Job Ended ***");
		} catch (Exception e) {
			BcwtsLogger.info(MY_NAME + "*** Update Configuration Cache Job Failed ***");
			BcwtsLogger.error(MY_NAME + " Exception: " + e.getMessage());			
		}
	}
}