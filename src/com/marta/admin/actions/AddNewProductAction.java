package com.marta.admin.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.ApplicationSettings;
import com.marta.admin.business.AddNewProductBusiness;
import com.marta.admin.business.CardOrderStatusBusiness;
import com.marta.admin.business.LogManagement;
import com.marta.admin.business.PatronManagement;
import com.marta.admin.dao.SearchDAO;
import com.marta.admin.dto.AddNewProductDTO;
import com.marta.admin.dto.BcwtLogDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ApplicationSettingsForm;
import com.marta.admin.forms.AddNewProductForm;
import com.marta.admin.forms.CardOrderedStatusForm;
import com.marta.admin.forms.LoggingCallsForm;
import com.marta.admin.forms.SignupForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class AddNewProductAction extends DispatchAction {

	final String ME = "AddNewProductAction: ";

	public ActionForward displayAddNewProductStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "displayAddNewProductStatus: ";
		BcwtsLogger.debug(MY_NAME + "Populated data ");
		HttpSession session = request.getSession(true);
		String returnValue = "addNewProductStatusMain";
		AddNewProductDTO addNewProductDTO = null;
		AddNewProductForm addNewProductForm = (AddNewProductForm) form;
		AddNewProductBusiness addNewProductBusiness = new AddNewProductBusiness();
		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
		List fareInstrumentList = null;
		List riderClassificationList = null;

		try {
			String patronid = "";
			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronid = loginPatronDTO.getPatronid().toString();
				}
			}

			// fare instrument id (start)
			/*
			fareInstrumentList = new ArrayList();
			fareInstrumentList = addNewProductBusiness
					.getFareInstrumentInformation();
			session.setAttribute("FARE_INSTRUMENT_LIST", fareInstrumentList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					" >> My Account >> Add New Product");
			// fare instrument id (end)

			// Rider Classification (start)
			riderClassificationList = new ArrayList();
			riderClassificationList = addNewProductBusiness
					.getRiderInformation();
			session.setAttribute("RIDER_CLASSIFICATION_LIST",
					riderClassificationList);
			// Rider Classification (end)
			*/
			
			// Region id (Start)

			HashMap regionidMap = null;
			request.setAttribute("addNewProductForm", addNewProductForm);
			List addNewProductList = new ArrayList();
			if (null != session.getAttribute(Constants.STATUS_INFO_MAP)) {
				regionidMap = (HashMap) session
						.getAttribute(Constants.STATUS_INFO_MAP);
			}

			// session.setAttribute(Constants.ADD_NEW_PRODUCT_STATUS,
			// addNewProductList);
			List regionidList = addNewProductBusiness
					.getRegionInformation(regionidMap);

			session.setAttribute(Constants.STATUS_LIST, regionidList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, ">> My Account >> Add New Product");

			// Region id (end)
			/*
			 * HashMap riderMap = null; if (null !=
			 * session.getAttribute(Constants.RIDER_INFO_MAP)) { riderMap =
			 * (HashMap) session .getAttribute(Constants.RIDER_INFO_MAP); } List
			 * riderList = addNewProductBusiness.getRiderInformation(riderMap);
			 * 
			 * session.setAttribute(Constants.RIDER_LIST, riderList);
			 */
			returnValue = "addNewProductStatusMain";

		} catch (Exception e) {
			BcwtsLogger
					.error(MY_NAME
							+ " Exception while getting information for displaying unlock page:"
							+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}

		return mapping.findForward(returnValue);
	}

	public ActionForward addNewProductStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "displayAddNewProductStatus: ";
		BcwtsLogger.debug(MY_NAME + "Populated data ");
		HttpSession session = request.getSession(true);
		String returnValue = "addNewProductStatusMain";
		AddNewProductDTO addNewProductDTO = null;
		AddNewProductForm addNewProductForm = (AddNewProductForm) form;
		AddNewProductBusiness addNewProductBusiness = new AddNewProductBusiness();
		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
		String patronid = "";
		try {

			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronid = loginPatronDTO.getPatronid().toString();
				}
			}
			addNewProductDTO = new AddNewProductDTO();

			/*
			 * if(addNewProductForm.getProductId() != null){
			 * addNewProductDTO.setProductId(addNewProductForm.getProductId()); }
			 */
			if (addNewProductForm.getFareInstrumentId() != null) {
				addNewProductDTO.setFareInstrumentId(addNewProductForm
						.getFareInstrumentId());
			}
			if (addNewProductForm.getRegionId() != null) {
				addNewProductDTO.setRegionId(addNewProductForm.getRegionId());
			}
			
			if (addNewProductForm.getProductDetailDescription() != null) {
				addNewProductDTO.setProductDetailDescription(addNewProductForm
						.getProductDetailDescription());
			}
			/*if (addNewProductForm.getProductName() != null) {
				addNewProductDTO.setProductName(addNewProductForm
						.getProductName());
			}
			if (addNewProductForm.getProductDescription() != null) {
				addNewProductDTO.setProductDescription(addNewProductForm
						.getProductDescription());
			}
			
			if (addNewProductForm.getRiderClassification() != null) {
				addNewProductDTO.setRiderClassification(addNewProductForm
						.getRiderClassification());
			}
			if (addNewProductForm.getPrice() != null) {
				addNewProductDTO.setPrice(addNewProductForm.getPrice());
			}
			if (addNewProductForm.getSortOrder() != null) {
				addNewProductDTO.setSortOrder(addNewProductForm.getSortOrder());
			}*/
			
			AddNewProductDTO resultAddNewProductDTO = addNewProductBusiness
					.addNewProductList(addNewProductDTO);

			if(!Util.isBlankOrNull(resultAddNewProductDTO.getCheck())){
				
				if(Util.equalsIgnoreCase(resultAddNewProductDTO.getCheck(), Constants.WARNING)){
					request.setAttribute(Constants.MESSAGE, "Invalid Fare Instrument Id");
					request.setAttribute(Constants.SUCCESS, "false");
				}
				
				if(Util.equalsIgnoreCase(resultAddNewProductDTO.getCheck(), Constants.Fail)){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.NEXT_FARE_INSTRUMENT_EXISTS_STAUS));
					request.setAttribute(Constants.SUCCESS, "false");
				}
				
				if(Util.equalsIgnoreCase(resultAddNewProductDTO.getCheck(), Constants.Pass)){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.PRODUCT_ADD_SUCESS_MESAAGE));
					request.setAttribute(Constants.SUCCESS, "true");
				}
			}
			
			/*if (!Util.isBlankOrNull(nextFareId)) {

				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.NEXT_FARE_INSTRUMENT_EXISTS_STAUS));
				request.setAttribute(Constants.SUCCESS, "false");
			} else {
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.PRODUCT_ADD_SUCESS_MESAAGE));
				request.setAttribute(Constants.SUCCESS, "true");
			}*/
			returnValue = "addNewProductStatusMain";

		} catch (Exception e) {
			BcwtsLogger
					.error(MY_NAME
							+ " Exception while getting information for displaying unlock page:"
							+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}

		return mapping.findForward(returnValue);
	}
}
