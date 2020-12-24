package com.marta.admin.business;

import java.util.List;
import org.apache.struts.actions.DispatchAction;
import com.marta.admin.dao.ViewEditProductsDAO;
import com.marta.admin.dto.ViewEditProductsDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;

/**
 * This is the business class to view edit products
 * 
 * @author Jagadeesan
 *
 */
public class ViewEditProducts extends DispatchAction{

	final String ME = "ViewEditProductsAction: ";
	/**
	 * Method to get view edit products list
	 * 
	 * @return
	 * @throws MartaException
	 */
	public List getViewEditProductsList() throws MartaException{
		
		final String MY_NAME = ME + " getViewEditProductsList: ";
		BcwtsLogger.debug(MY_NAME + " getting user information ");
		List viewEditProductsList = null; 
		try {
			ViewEditProductsDAO viewEditProductDAO = new ViewEditProductsDAO();
			viewEditProductsList = viewEditProductDAO.getViewEditProductsList();
			BcwtsLogger.info(MY_NAME + " got user List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting user list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.VIEW_EDIT_PRODUCTS_LIST_FIND));
		}
		return viewEditProductsList;	
	}
	/**
	 * Method to update product details
	 * 
	 * @param viewEditProductsDTO
	 * @return
	 * @throws MartaException
	 */
	public boolean updateViewEditProductsDetails(ViewEditProductsDTO viewEditProductsDTO)throws MartaException{
		
		final String MY_NAME = ME + "updateViewEditProductsDetails: ";
		BcwtsLogger.debug(MY_NAME + " to update view edit products detail");
		ViewEditProductsDAO viewEditProductsDAO = null ;
		boolean isUpdate = false;
		try{
			viewEditProductsDAO = new ViewEditProductsDAO();
			isUpdate = viewEditProductsDAO.updateViewEditProductsDetails(viewEditProductsDTO);
		}catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in adding a patron user details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader.getValue(MartaConstants.VIEW_EDIT_PRODUCTS_UPDATE));
		}
		return isUpdate;
	}
	/**
	 * Method to get product details for productId
	 * 
	 * @param productId
	 * @return
	 * @throws MartaException
	 */
	public ViewEditProductsDTO getViewEditProductDetails(Long productId)throws MartaException{
		
		final String MY_NAME = ME + " getViewEditProductDetails: ";
		BcwtsLogger.debug(MY_NAME + " to get view edit product detail list ");
		ViewEditProductsDAO viewEditProductsDAO = null;
		ViewEditProductsDTO viewEditProductsDTO = null;
		try {
			viewEditProductsDAO = new ViewEditProductsDAO();
			viewEditProductsDTO = viewEditProductsDAO.getViewEditProductDetails(productId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting the patron user detail :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.VIEW_EDIT_PRODUCTS_LIST_FIND));
		}
		return viewEditProductsDTO;
	}
}
