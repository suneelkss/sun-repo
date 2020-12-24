package com.marta.admin.business;

import com.marta.admin.business.BatchJobDayEndProcessorImpl;
import com.marta.admin.business.BatchJobMonthEndProcessorImpl;
import com.marta.admin.business.BatchJobScheduler;

public abstract class BatchJobScheduler {

	public abstract void processJobs() throws Exception;
	
	public abstract boolean updateProductPrice() throws Exception;

	/**
	 * This method returns the specific job processor
	 * for the given job type
	 * 
	 * @param jobType The job type (day / month)
	 * @return Returns BatchJobScheduler instance
	 */
	public static BatchJobScheduler getInstance(String jobType) {
		if (jobType == null) {
			return null;
		}
		
		if (jobType.equalsIgnoreCase("day") || jobType.equalsIgnoreCase("daily")) {
			return new BatchJobDayEndProcessorImpl();
		}

		if (jobType.equalsIgnoreCase("month") || jobType.equalsIgnoreCase("monthly")) {
			return new BatchJobMonthEndProcessorImpl();
		}
		
		return null;
	}
	
}
