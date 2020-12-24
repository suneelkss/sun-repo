package com.marta.admin.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.PrintShippingLabelBusiness;
import com.marta.admin.dto.AddressDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.PrintShippingLabelForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.Util;

public class PrintShippingLabelAction extends DispatchAction{
	final String ME = "PrintShippingLabelAction: ";
	
	public ActionForward displayPrintShippingLabel(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "printShippingLabel: ";
		BcwtsLogger.debug(MY_NAME + "Populated data for Print Shipping Label");
		String returnValue = null;			
		List printShippingLabelList = null;		
		HttpSession session = request.getSession(true);
		String nextFareOrderId = null;
		String orderType = null;
		
		try{
			returnValue = "printShippingLabel";
			PrintShippingLabelForm printShippingLabelForm = (PrintShippingLabelForm) form;
			
			PrintShippingLabelBusiness printShippingLabelBusiness =
					new PrintShippingLabelBusiness();
			
			orderType = printShippingLabelForm.getOrderType();
			nextFareOrderId = request.getParameter("nextFareOrderId");
			
			if(!Util.isBlankOrNull(orderType)){
				session.setAttribute("ORDER_TYPE", orderType);				
			} else {
				orderType = Constants.ORDER_TYPE_GBS;
			}
			
			printShippingLabelList = printShippingLabelBusiness.getPrintShippingLabelDetails(orderType, nextFareOrderId);
			
			session.setAttribute(Constants.PRINT_SHIPPING_LABEL_LIST, printShippingLabelList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, ">> My Account >> Print Shipping Label");
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME+" Exception while populated data " +
				"for Print Shipping Label "+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		return mapping.findForward(returnValue);			
	}	
		
	public ActionForward printShippingLabel(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "printShippingLabel: ";
		BcwtsLogger.debug(MY_NAME+ "Populated Print data for Print Shipping Label");
		String returnValue = null;
		String orderId = null;
		HttpSession session = request.getSession(true);
		String orderType = null;
		
		try {		
			returnValue = "printShippingLabel";
			
			PrintShippingLabelBusiness printShippingLabelBusiness =
									new PrintShippingLabelBusiness();
			
			if(request.getParameter("orderId")!= null){
				orderId = request.getParameter("orderId").toString();
			} 			
			if(request.getParameter("orderType")!= null){
				orderType = request.getParameter("orderType").toString();
			}
			
			session.removeAttribute(Constants.BAR_CODE_STR);
			String barcode = printShippingLabelBusiness.getBarcode(orderId, orderType);	
			if(!Util.isBlankOrNull(barcode)) {
				session.setAttribute(Constants.BAR_CODE_STR, barcode);
			} else {
				throw new Exception(MY_NAME + "Barcode is null");
			}
			
			AddressDTO addressDTO = printShippingLabelBusiness 
										.getPrintPrintShippingLabelDetails(orderId, orderType);
			if(addressDTO == null) {
				throw new Exception(MY_NAME + "addressDTO is null");
			} else {
				session.setAttribute(Constants.PRINT_SHIPPING_DETAILS, addressDTO);
			}
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME+" Exception while populated  print data " +
					"for Print Shipping Label "+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}		
		return mapping.findForward(returnValue);				
	}
	
}
