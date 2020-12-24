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

import com.marta.admin.business.NewNextfareMethods;
import com.marta.admin.business.RequestToHotList;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtRequestToHotListDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.HotListForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class RequestToHotlListAction extends DispatchAction {
	
	final String ME = "RequestToHotlListAction: ";
		
	/**
	 * Method to show hotlist card details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
		public ActionForward displayRequestHotlist(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "displayRequestHotlist: ";
			BcwtsLogger.debug(MY_NAME
					+ "getting hot list card details");
			HttpSession session = request.getSession(true);
			String returnValue = "requestToHotlList";
			HotListForm hotListForm = (HotListForm)form;
			RequestToHotList requestToHotList = new RequestToHotList();
			BcwtRequestToHotListDTO bcwtRequestToHotListDTO = new BcwtRequestToHotListDTO();
			BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
			String userName = "";
			
			try {
				if (null != session.getAttribute(Constants.USER_INFO)) {
					loginPatronDTO = (BcwtPatronDTO) session
							.getAttribute(Constants.USER_INFO);
					if (null != loginPatronDTO) {
						userName = loginPatronDTO.getFirstname() + " "
								+ loginPatronDTO.getLastname();
					}
				}
				List hotList = requestToHotList.getRequestToHotList(bcwtRequestToHotListDTO);
				session.setAttribute(Constants.HOTLIST_CARD_DETAILS_LIST, hotList);
				request.setAttribute("hotListForm", hotListForm);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);
				returnValue = "requestToHotlList";
				
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while getting information for hotlist card details page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_REQUEST_HOTLIST_CARD);
			return mapping.findForward(returnValue);
		}
		
		/**
		 * Method to hotlist a card for PS
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward hotListCardDetailsForPS(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "hotlistCards: ";
			BcwtsLogger.debug(MY_NAME
					+ "getting hot list card details through hotlist method");
			HttpSession session = request.getSession(true);
			String returnValue = "requestToHotlList";
			HotListForm hotListForm = (HotListForm)form;
			RequestToHotList requestToHotList = null;
			BcwtRequestToHotListDTO bcwtRequestToHotListDTO = null;
			BcwtRequestToHotListDTO getRequestHotListDTO = null;
			boolean isUpdated = false;
			String adminName = "";
			
			try {
				requestToHotList = new RequestToHotList();
				if(session.getAttribute(Constants.USER_INFO)!=null){
					BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
					loginPatronDTO = (BcwtPatronDTO) session
					.getAttribute(Constants.USER_INFO);
					if (null != loginPatronDTO) {
						adminName = loginPatronDTO.getFirstname() + " "
								+ loginPatronDTO.getLastname();						
					}
				}
				List hotList = new ArrayList();
				bcwtRequestToHotListDTO = new BcwtRequestToHotListDTO();
				getRequestHotListDTO = new BcwtRequestToHotListDTO();
				
				getRequestHotListDTO = requestToHotList.getHotListDetailsForPS
						(hotListForm.getBreezeCardSerialNumber());
				
				if(getRequestHotListDTO!=null){
					if(adminName!=null){
						getRequestHotListDTO.setAdminName(adminName);
					}
					getRequestHotListDTO.setPointOfSale(Constants.PS_HOTLIST);
					if(hotListForm.getCardOrderType() != null){
						bcwtRequestToHotListDTO.setCardOrderType(hotListForm.getCardOrderType());	
					}
					if(hotListForm.getBreezeCardSerialNumber() != null){
						bcwtRequestToHotListDTO.setSerialNumber(hotListForm.getBreezeCardSerialNumber());
					}
					getRequestHotListDTO.setHotListedDate(String.valueOf(Util.getCurrentDateAndTime()));
					
					isUpdated = requestToHotList.updateHotListCardDetails(getRequestHotListDTO);
						if(isUpdated){	
							NewNextfareMethods.hotlistCard(hotListForm.getBreezeCardSerialNumber());
							request.setAttribute(Constants.MESSAGE, PropertyReader
									.getValue(Constants.BREEZE_CARD_HOTLISTED_SUCCESS));
							request.setAttribute(Constants.SUCCESS, "true");
							hotList = requestToHotList.getRequestToHotList(bcwtRequestToHotListDTO);
							session.setAttribute(Constants.HOTLIST_CARD_DETAILS_LIST, hotList);						
						}else{
							request.setAttribute(Constants.MESSAGE, PropertyReader
									.getValue(Constants.BREEZE_CARD_HOTLISTED_FAILURE));
							request.setAttribute(Constants.SUCCESS, "false");
						}
				}else{
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BREEZE_CARD_DOES_NOT_EXIST));
					request.setAttribute(Constants.SUCCESS, "false");	
				}
				returnValue = "requestToHotlList";
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while updating hotlist card details:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_REQUEST_HOTLIST_CARD);
			return mapping.findForward(returnValue);
		}
		
		/**
		 * Method to hotlist a card for GBS
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward hotListCardDetailsForGBS(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "hotListCardDetailsForGBS: ";
			BcwtsLogger.debug(MY_NAME
					+ "getting hot list card details through hotlist method");
			HttpSession session = request.getSession(true);
			String returnValue = "requestToHotlList";
			HotListForm hotListForm = (HotListForm)form;
			RequestToHotList requestToHotList = null;
			BcwtRequestToHotListDTO bcwtRequestToHotListDTO = null;
			BcwtRequestToHotListDTO getRequestHotListDTO = null;
			boolean isUpdated = false;
			String adminName = "";
			
			try {
				requestToHotList = new RequestToHotList();
				if(session.getAttribute(Constants.USER_INFO)!=null){
					BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
					loginPatronDTO = (BcwtPatronDTO) session
					.getAttribute(Constants.USER_INFO);
					if (null != loginPatronDTO) {
						adminName = loginPatronDTO.getFirstname() + " "
								+ loginPatronDTO.getLastname();						
					}
				}
				bcwtRequestToHotListDTO = new BcwtRequestToHotListDTO();
				getRequestHotListDTO = new BcwtRequestToHotListDTO();
				
				getRequestHotListDTO = requestToHotList.getHotListDetailsForGBS
						(hotListForm.getBreezeCardSerialNumber());
				
				List hotList = new ArrayList();
				if(getRequestHotListDTO.getFirstName()!=null){
					if(Util.equalsIgnoreCase(getRequestHotListDTO.getCardType(), Constants.ORDER_TYPE_GBS)){
				
						if(adminName!=null){
							getRequestHotListDTO.setAdminName(adminName);
						}
						getRequestHotListDTO.setPointOfSale(Constants.GBS_HOTLIST);
						if(hotListForm.getCardOrderType() != null){
							getRequestHotListDTO.setCardOrderType(hotListForm.getCardOrderType());	
						}
						if(hotListForm.getBreezeCardSerialNumber() != null){
							getRequestHotListDTO.setSerialNumber(hotListForm.getBreezeCardSerialNumber());
						}
						getRequestHotListDTO.setHotListedDate(String.valueOf(Util.getCurrentDateAndTime()));
						
						isUpdated = requestToHotList.updateHotListCardDetails(getRequestHotListDTO);
						if(isUpdated){	
							NewNextfareMethods.hotlistCard(hotListForm.getBreezeCardSerialNumber());
							request.setAttribute(Constants.MESSAGE, PropertyReader
									.getValue(Constants.BREEZE_CARD_HOTLISTED_SUCCESS));
							request.setAttribute(Constants.SUCCESS, "true");
							hotList = requestToHotList.getRequestToHotList(bcwtRequestToHotListDTO);
							session.setAttribute(Constants.HOTLIST_CARD_DETAILS_LIST, hotList);						
						}else{
							request.setAttribute(Constants.MESSAGE, PropertyReader
									.getValue(Constants.BREEZE_CARD_HOTLISTED_FAILURE));
							request.setAttribute(Constants.SUCCESS, "false");
						}
					}else{
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.INVALID_POINT_OF_SALE));
						request.setAttribute(Constants.SUCCESS, "false");
					}
				}else{
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BREEZE_CARD_DOES_NOT_EXIST));
					request.setAttribute(Constants.SUCCESS, "false");	
				}
				returnValue = "requestToHotlList";
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while updating hotlist card details:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_REQUEST_HOTLIST_CARD);
			return mapping.findForward(returnValue);
		}
		
		/**
		 * Method to hotlist a card for IS 
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward hotListCardDetailsForIS(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "hotListCardDetailsForIS: ";
			BcwtsLogger.debug(MY_NAME
					+ "getting hot list card details through hotlist method");
			HttpSession session = request.getSession(true);
			String returnValue = "requestToHotlList";
			HotListForm hotListForm = (HotListForm)form;
			RequestToHotList requestToHotList = null;
			BcwtRequestToHotListDTO bcwtRequestToHotListDTO = null;
			BcwtRequestToHotListDTO getRequestHotListDTO = null;
			boolean isUpdated = false;
			String adminName = "";
			
			try {
				requestToHotList = new RequestToHotList();
				if(session.getAttribute(Constants.USER_INFO)!=null){
					BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
					loginPatronDTO = (BcwtPatronDTO) session
					.getAttribute(Constants.USER_INFO);
					if (null != loginPatronDTO) {
						adminName = loginPatronDTO.getFirstname() + " "
								+ loginPatronDTO.getLastname();						
					}
				}
				bcwtRequestToHotListDTO = new BcwtRequestToHotListDTO();
				getRequestHotListDTO = new BcwtRequestToHotListDTO();
				getRequestHotListDTO = requestToHotList.getHotListDetailsForIS
						(hotListForm.getBreezeCardSerialNumber());
				List hotList = new ArrayList();
				if(getRequestHotListDTO.getFirstName()!=null){
					if(Util.equalsIgnoreCase(getRequestHotListDTO.getCardType(), Constants.ORDER_TYPE_IS)){
						if(adminName!=null){
							getRequestHotListDTO.setAdminName(adminName);
						}
						getRequestHotListDTO.setPointOfSale(Constants.IS_HOTLIST);
						
						getRequestHotListDTO.setHotListedDate(String.valueOf(Util.getCurrentDateAndTime()));
						
						if(!Util.isBlankOrNull(hotListForm.getCardOrderType())){
							getRequestHotListDTO.setCardOrderType(hotListForm.getCardOrderType());	
						}
						if(!Util.isBlankOrNull(hotListForm.getBreezeCardSerialNumber())){
							getRequestHotListDTO.setSerialNumber(hotListForm.getBreezeCardSerialNumber());
						}
						isUpdated = requestToHotList.updateHotListCardDetails(getRequestHotListDTO);
							if(isUpdated){	
								NewNextfareMethods.hotlistCard(hotListForm.getBreezeCardSerialNumber());
								request.setAttribute(Constants.MESSAGE, PropertyReader
										.getValue(Constants.BREEZE_CARD_HOTLISTED_SUCCESS));
								request.setAttribute(Constants.SUCCESS, "true");
								hotList = requestToHotList.getRequestToHotList(bcwtRequestToHotListDTO);
								session.setAttribute(Constants.HOTLIST_CARD_DETAILS_LIST, hotList);						
							}else{
								request.setAttribute(Constants.MESSAGE, PropertyReader
										.getValue(Constants.BREEZE_CARD_HOTLISTED_FAILURE));
								request.setAttribute(Constants.SUCCESS, "false");
							}
					}else{
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.INVALID_POINT_OF_SALE));
						request.setAttribute(Constants.SUCCESS, "false");	
					}	
				}
				else{
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BREEZE_CARD_DOES_NOT_EXIST));
					request.setAttribute(Constants.SUCCESS, "false");	
				}
				returnValue = "requestToHotlList";
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while updating hotlist card details:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_REQUEST_HOTLIST_CARD);
			return mapping.findForward(returnValue);
		}
		/**
		 * Method to show recently hotlisted card details in welcome page
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward showHotListCardListInWelcomePage(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException {
			
			final String MY_NAME = ME + "showHotListCardListInWelcomePage: ";
			BcwtsLogger.debug(MY_NAME
					+ "show recently hot listed card list in welcome page");
			HttpSession session = request.getSession(true);
			String returnValue = "welcome";
			RequestToHotList requestToHotList = null;
			List recentlyHotlistCardlist=null;
			
			try {
				requestToHotList = new RequestToHotList();
				recentlyHotlistCardlist = requestToHotList.getRecentlyHotlistCardList();
				session.setAttribute(Constants.RECENT_HOTLIST_CARDLIST_WELCOMEPAGE, recentlyHotlistCardlist);
				returnValue = "welcome";
				
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while displaying recently hotlist card details in Welcome Page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnValue);
		}
}
