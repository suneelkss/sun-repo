package com.marta.admin.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.marta.admin.business.BatchJobScheduler;

public class BatchProcessMonthlyJob implements Job {
	
	public BatchProcessMonthlyJob() {
		
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			System.out.println("\n\nProcessing the Monthly Job\n\n");
			BatchJobScheduler dailyBatchJob = BatchJobScheduler.getInstance("monthly");
			//dailyBatchJob.processJobs();

			// To update price in BCWTPRODUCT table
			dailyBatchJob.updateProductPrice();

			System.out.println("Processing the Monthly Job - Completed");
		} catch (Exception ex) {
			System.out.println("Error while processing the monthly jobs");
			ex.printStackTrace();
		}
	}

}
