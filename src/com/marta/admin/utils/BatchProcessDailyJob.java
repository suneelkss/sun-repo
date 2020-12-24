package com.marta.admin.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.marta.admin.business.BatchJobScheduler;

public class BatchProcessDailyJob implements Job {
	
	public BatchProcessDailyJob() {
		
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			BcwtsLogger.info("\n\nProcessing the Daily GL Job\n\n");
			BatchJobScheduler dailyBatchJob = BatchJobScheduler.getInstance("daily");
			dailyBatchJob.processJobs();
			
			BcwtsLogger.info("Processing the Daily GL Job - Completed");
		} catch (Exception ex) {
			BcwtsLogger.error("Error while processing the daily GL jobs"+Util.getFormattedStackTrace(ex));
		}
	}

}
