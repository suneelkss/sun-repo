package com.marta.admin.utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.ListCardsBatchProcessServletHelper;

public class ListCardsBatchProcessServlet extends HttpServlet{
	
	ListCardsBatchProcessServletHelper listCardsBatchProcessServletHelper = 
							new ListCardsBatchProcessServletHelper();
	/**
	 * Constructor of the object.
	 */
	public ListCardsBatchProcessServlet() {
		super();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occured
	 */
	public void init(ServletConfig config) throws ServletException {       
				
		try {
			listCardsBatchProcessServletHelper.startPSListCardsBatchProcessTimer();
		} catch (Exception e) {			
			BcwtsLogger.error("Exception in PSListCardsBatchProcessServlet.init()" + e.getMessage());
		}
		super.init(config);
	}
		
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); 
		try{
			listCardsBatchProcessServletHelper.stopPSListCardsBatchProcessTimer();
		} catch (Exception e) {			
			BcwtsLogger.error("Exception in PSListCardsBatchProcessServlet.destroy()" + e.getMessage());
		}
	}
}
