package com.marta.admin.business;

import java.util.List;

import org.apache.struts.actions.DispatchAction;

import com.marta.admin.dao.PrintShippingLabelDAO;
import com.marta.admin.dto.AddressDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;

public class PrintShippingLabelBusiness extends DispatchAction{
	final String ME = "PrintShippingLabel";
	
	public List getPrintShippingLabelDetails(String orderType, String nextFareOrderId) 
	throws MartaException {
		final String MY_NAME = ME + " getPrintShippingLabelDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting PrintShippingLabelDetails ");
		
		PrintShippingLabelDAO printShippingLabelDAO
							= new PrintShippingLabelDAO();
		List printShippingLabelList = null;
		
		try {
			printShippingLabelList = printShippingLabelDAO.
									getPrintShippingLabelDetails(orderType, nextFareOrderId);
			
			BcwtsLogger.info(MY_NAME + " got Print Shipping Label Details:");					
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Print Shipping Label details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PRINT_SHIPPING_LABEL_DETAILS));
		}
		return printShippingLabelList;
	}

	public AddressDTO getPrintPrintShippingLabelDetails(String printOrderId,String orderType) 
	throws MartaException {	
		final String MY_NAME = ME + " getSearchPartnerRegistrationDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting Search Partner Registration Details ");
	
		PrintShippingLabelDAO printShippingLabelDAO = new PrintShippingLabelDAO();	
		AddressDTO addressDTO = null;
		try {		
			addressDTO = printShippingLabelDAO
				.getPrintPrintShippingLabelDetails(printOrderId,orderType);
			BcwtsLogger.info(MY_NAME + " got Search Partner Registration Details:");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception: " + e.getMessage());
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PRINT_SHIPPING_LABEL_PRINT));
		}
		return addressDTO;
	}
	
	public String getBarcode(String orderId, String orderType) throws Exception {
		final String MY_NAME = ME + " getBarcode: ";
		BcwtsLogger.debug(MY_NAME);
	
		PrintShippingLabelDAO printShippingLabelDAO = new PrintShippingLabelDAO();	
		String barcode = null;
		try {		
			barcode = printShippingLabelDAO.getBarcode(orderId, orderType);
			BcwtsLogger.info(MY_NAME + " got barcode: " + barcode);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception: " + e.getMessage());
			throw new MartaException(MY_NAME + "Exception while getting barcode");
		}
		return barcode;
	}
}
