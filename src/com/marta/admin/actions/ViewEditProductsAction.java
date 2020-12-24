package com.marta.admin.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.ViewEditProducts;
import com.marta.admin.dto.ViewEditProductsDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ViewEditProductsForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

/**
 * Action class for view edit products details
 * @author Jagadeesan
 *
 */
public class ViewEditProductsAction extends DispatchAction{
	
	   final String ME = "ViewEditProductsAction: ";
	   
		/**
		 * Method to show all products details
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward displayViewEditProductsPage(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {

			final String MY_NAME = ME + "viewEditProducts: ";
			BcwtsLogger.debug(MY_NAME + "gathering pre populated data for view edit products page");
			String returnValue = "viewEditProducts";
			ViewEditProducts viewEditProducts = new ViewEditProducts();
			HttpSession session = request.getSession(true);
			String userName = "";
			
			try {
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);
				List viewEditProductsList = viewEditProducts.getViewEditProductsList();
				session.setAttribute(Constants.VIEW_EDIT_PRODUCTS_LIST, viewEditProductsList);
				session.removeAttribute("SHOW_VIEW_EDIT_PRODUCTS_DIV");
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);
				
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Error while displaying view edit products page :"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
				e.printStackTrace();
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_VIEW_EDIT_PRODUCTS);
			return mapping.findForward(returnValue);
		}
		
		/**
		 * Method to update product details
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward updateViewEditProductsDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "updateViewEditProductsDetails: ";
			BcwtsLogger.debug(MY_NAME
					+ "to update view edit product details");
			HttpSession session = request.getSession(true);
			String returnValue = "viewEditProducts";
			ViewEditProducts viewEditProducts = null ;
			ViewEditProductsDTO viewEditProductsDTO = null ;
			ViewEditProductsForm viewEditProductsForm = (ViewEditProductsForm)form;
			boolean isUpdate = false;
			List viewEditProductsList = new ArrayList();
			try {
				viewEditProducts = new ViewEditProducts();
				viewEditProductsDTO = new ViewEditProductsDTO();
				
				if(viewEditProductsForm.getProductId() != null ){
					viewEditProductsDTO.setProductId(viewEditProductsForm.getProductId());
				}
				if(viewEditProductsForm.getEditFareInstrumentId() != null ){
					viewEditProductsDTO.setFareInstrumentId(viewEditProductsForm.getEditFareInstrumentId());
				}
				if(viewEditProductsForm.getEditRegionId() != null ){
					viewEditProductsDTO.setRegionId(viewEditProductsForm.getEditRegionId());
				}
				if(viewEditProductsForm.getEditRegionName() != null ){
					viewEditProductsDTO.setRegionName(viewEditProductsForm.getEditRegionName());
				}
				if(viewEditProductsForm.getEditProductName() != null){
					viewEditProductsDTO.setProductName(viewEditProductsForm.getEditProductName());
				}
				if(viewEditProductsForm.getEditProductDescription() != null ){
					viewEditProductsDTO.setProductDescription(viewEditProductsForm.getEditProductDescription());
				}
				if(viewEditProductsForm.getEditProductDetailedDesc() != null ){
					viewEditProductsDTO.setProductDetailedDesc(viewEditProductsForm.getEditProductDetailedDesc());
				}
				if(viewEditProductsForm.getEditPrice() != null ){
					viewEditProductsDTO.setPrice(viewEditProductsForm.getEditPrice());
				}
				if(viewEditProductsForm.getEditActiveStatus() != null){
					viewEditProductsDTO.setActiveStatus(viewEditProductsForm.getEditActiveStatus());
				}
				if(viewEditProductsForm.getEditSortOrder() != null ){
					viewEditProductsDTO.setSortOrder(viewEditProductsForm.getEditSortOrder());
				}
				
				isUpdate = viewEditProducts.updateViewEditProductsDetails(viewEditProductsDTO);
				
				if(isUpdate){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.VIEW_EDIT_PRODUCTS_SUCCESS));
					request.setAttribute(Constants.SUCCESS, "true");
					viewEditProductsList = viewEditProducts.getViewEditProductsList();
					session.setAttribute(Constants.VIEW_EDIT_PRODUCTS_LIST, viewEditProductsList);
				}else{
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.VIEW_EDIT_PRODUCTS_FAILURE));
					request.setAttribute(Constants.SUCCESS, "false");
				}
				session.removeAttribute("SHOW_VIEW_EDIT_PRODUCTS_DIV");
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while updating view edit products details:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_VIEW_EDIT_PRODUCTS);
			return mapping.findForward(returnValue);
		}
		/**
		 * Method to populate product detail for editing
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward populateViewEditProductsDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "getViewEditProductsDetails: ";
			BcwtsLogger.debug(MY_NAME + "get view edit product details");
			HttpSession session = request.getSession(true);
			ViewEditProductsForm viewEditProductsForm = (ViewEditProductsForm)form;
			ViewEditProductsDTO viewEditProductsDTO = null ;
			ViewEditProducts viewEditProducts = null ;
			String returnValue = "viewEditProducts";
			String productIdStr = "";
			
			if(!Util.isBlankOrNull(request.getParameter("editProductId"))){
				productIdStr = request.getParameter("editProductId").toString();
			}
			try {
				Long productId = new Long(productIdStr);
				
				viewEditProducts = new ViewEditProducts();
				viewEditProductsForm.setEditProductId(productId.toString());
				viewEditProductsDTO = viewEditProducts.getViewEditProductDetails(productId);
				
				if(null != viewEditProductsDTO.getProductId()){
					viewEditProductsForm.setProductId(viewEditProductsDTO.getProductId());
				}
				if(null != viewEditProductsDTO.getFareInstrumentId()){
					viewEditProductsForm.setEditFareInstrumentId(viewEditProductsDTO.getFareInstrumentId());
				}
				if(null != viewEditProductsDTO.getRegionId()){
					viewEditProductsForm.setEditRegionId(viewEditProductsDTO.getRegionId());
				}
				if(null != viewEditProductsDTO.getRegionName()){
					viewEditProductsForm.setEditRegionName(viewEditProductsDTO.getRegionName());
				}
				if(null != viewEditProductsDTO.getProductName()){
					viewEditProductsForm.setEditProductName(viewEditProductsDTO.getProductName());
				}
				if(null != viewEditProductsDTO.getProductDescription()){
					viewEditProductsForm.setEditProductDescription(viewEditProductsDTO.getProductDescription());
				}
				if(null != viewEditProductsDTO.getProductDetailedDesc()){
					viewEditProductsForm.setEditProductDetailedDesc(viewEditProductsDTO.getProductDetailedDesc());
				}
				if(null != viewEditProductsDTO.getRiderClassification()){
					viewEditProductsForm.setEditRiderClassification(viewEditProductsDTO.getRiderClassification());
				}
				if(null != viewEditProductsDTO.getPrice()){
					viewEditProductsForm.setEditPrice(viewEditProductsDTO.getPrice());
				}
				if(null != viewEditProductsDTO.getSortOrder()){
					viewEditProductsForm.setEditSortOrder(viewEditProductsDTO.getSortOrder());
				}
				if(null != viewEditProductsDTO.getActiveStatus()){
					viewEditProductsForm.setEditActiveStatus(viewEditProductsDTO.getActiveStatus());
				}
				session.setAttribute("SHOW_VIEW_EDIT_PRODUCT_LIST", viewEditProductsDTO);
				session.setAttribute("SHOW_VIEW_EDIT_PRODUCTS_DIV", "SHOW_VIEW_EDIT_PRODUCTS_DIV");
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME
						+ " Exception while populating view edit products details :"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					Constants.BREADCRUMB_VIEW_EDIT_PRODUCTS);
			return mapping.findForward(returnValue);		
		}
}
