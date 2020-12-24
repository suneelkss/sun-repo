package com.marta.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.BatchJobScheduler;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;

public class UpdateProductPriceAction extends DispatchAction {
	
	final String ME = "UpdateProductPriceAction: ";

	public ActionForward displayPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "displayPage: ";
		BcwtsLogger.debug(MY_NAME);
		String returnValue = null;;
		
		try {			
			returnValue = "updateProductPrice";
			HttpSession session = request.getSession(true);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.PRODUCT_PRICE_UPDATE_BREADCRUMB);
		}catch (Exception e) {
			BcwtsLogger.error(MY_NAME+ "Exception while displaying update product price page" + e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		return mapping.findForward(returnValue);
	}
	
	public ActionForward updateProductPrice(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "updateProductPrice: ";
		BcwtsLogger.debug(MY_NAME);
		String returnValue = "updateProductPrice";
		
		try {
			BatchJobScheduler dailyBatchJob = BatchJobScheduler.getInstance("monthly");
			
			boolean isUpdated = dailyBatchJob.updateProductPrice();
			
			if(isUpdated) {
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.PRODUCT_PRICE_UPDATE_SUCCESS_MESAAGE));
				request.setAttribute(Constants.SUCCESS, "true");
			} else {
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.PRODUCT_PRICE_UPDATE_FAILURE_MESAAGE));
			}
			HttpSession session = request.getSession(true);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.PRODUCT_PRICE_UPDATE_BREADCRUMB);
		}catch (Exception e) {
			BcwtsLogger.error(MY_NAME+ "Exception while updating product price" + e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		return mapping.findForward(returnValue);
	}
}