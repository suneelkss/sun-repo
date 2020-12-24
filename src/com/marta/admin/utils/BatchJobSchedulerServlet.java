package com.marta.admin.utils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;

import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

import com.marta.admin.utils.BatchProcessDailyJob;
import com.marta.admin.utils.BatchProcessMonthlyJob;
import com.marta.admin.utils.UpdateConfigurationCacheJob;
import com.rsa.certj.pkcs7.Data;

public class BatchJobSchedulerServlet extends HttpServlet {

	private static final long serialVersionUID = 1223434324L;
	private SchedulerFactory schedFact = null;
	private Scheduler sched = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {

			Calendar cal = Calendar.getInstance();

			// Initialise and start the scheduler
			schedFact = new StdSchedulerFactory();
			sched = schedFact.getScheduler();
			sched.start();
		
		BcwtsLogger.info("-----------------------------Setting up cron jobs----------------------------------------");	
			//CronExpression cronExprDailyGL = new CronExpression("0 2 11 * * ?");
		//	CronExpression cronExprDaily = new CronExpression("0 40 13 * * ?");
		//	CronTrigger dailyJobTrigger = new CronTrigger();
		//	dailyJobTrigger.setCronExpression(cronExprDailyGL);
		
			
			
		//	dailyJobTrigger.setName("dailyGLJobTrigger");
			// Create the job details
			
		    JobDetail dailyJobDetail = new JobDetail("dailyGLJob",
		    		Scheduler.DEFAULT_GROUP, BatchProcessDailyJob.class);
		   
			
			JobDetail monthlyJobDetail = new JobDetail("monthlyOMJob",
					Scheduler.DEFAULT_MANUAL_TRIGGERS, BatchProcessMonthlyJob.class);
			
			
			
			
			Trigger dailyJobTriggerGL = TriggerUtils.makeDailyTrigger(00,10);
			dailyJobTriggerGL.setName("dailyGLTrigger");
			sched.scheduleJob(dailyJobDetail, dailyJobTriggerGL);
			
		
			
			
			
		
		//	sched.scheduleJob(dailyJobDetail, dailyJobTrigger);
			
		//	sched.
			// Creating Cron Trigger for the month end job
			CronExpression cronExpr = new CronExpression("0 45 23 L * ?");
			// In the above expn 45 - mins, 23 - hrs
			// and L stands for the last day of the month
			CronTrigger monthJobTrigger = new CronTrigger();
			monthJobTrigger.setCronExpression(cronExpr);
			monthJobTrigger.setName("monthOMJobTrigger");
			sched.scheduleJob(monthlyJobDetail, monthJobTrigger);
			
			

			// Create the job details
			JobDetail dailyJobDetail1 = new JobDetail(
					"configCacheDailyJob", Scheduler.DEFAULT_GROUP, 
					UpdateConfigurationCacheJob.class);
			
			// Creating the daily trigger to fire at 11:30 pm 
			// and add the triggers to scheduler
			Trigger dailyJobTrigger1 = TriggerUtils.makeDailyTrigger(23, 30);
			dailyJobTrigger1.setName("configCacheDailyJobTrigger");
		//	sched.scheduleJob(dailyJobDetail1, dailyJobTrigger1); for pilot
           BcwtsLogger.debug("--------------------------------Set cron jobs-----------------------------------------------------------");
		} catch (Exception ex) {
			BcwtsLogger.error("Class:BatchJobSchedulerServlet Method:init"+ Util.getFormattedStackTrace(ex));
			System.out.println("Error while starting the scheduler");
			
		}

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws IOException, ServletException {

		super.doGet(req, response);
	}

}