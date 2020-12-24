package com.marta.admin.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.AddEditGLAccount;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;


public class GLBatchProcessAction extends DispatchAction{
	
	final String ME = "GLBatchProcessAction: ";
	
	
	/**
	 * Display GL Batch process Process page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displayGLBatchProcessPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "displayGLBatchProcessPage: ";
		BcwtsLogger.debug(MY_NAME + "display GL batch process page");
		String returnValue = "glBatchProcess";		
		HttpSession session = request.getSession(true);		
		try {
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while displaying GL batch process page :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_GL_BATCH_PROCESS);
		
		return mapping.findForward(returnValue);
	}
	
	
	/**
	 * Execute GL Batch process
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
			
	public ActionForward runGlBatchProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "runGlBatchProcess: ";
		BcwtsLogger.debug(MY_NAME + "execute gl batch process");
		String returnValue = "glBatchProcess";		
		HttpSession session = request.getSession(true);		
		try {
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while executing gl batch process :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_GL_BATCH_PROCESS);
		
		return mapping.findForward(returnValue);
	}
}