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

import com.marta.admin.business.CardOrderStatusBusiness;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.exceptions.MartaException;

import com.marta.admin.forms.CardOrderedStatusForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class CardOrderedStatusAction  extends DispatchAction {
	
	final String ME = "CardOrderedStatusAction: ";

	public ActionForward displayCardOrderedStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayCardOrderedStatus: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for Display Card Order Status ");
		HttpSession session = request.getSession(true);
		String returnValue = "cardOrderedStatusMain";
		
			try {
				List cardOrderedList = new ArrayList();
				session.setAttribute(Constants.CARD_ORDERED_STATUS, cardOrderedList);
				returnValue = "cardOrderedStatusMain";
				session.removeAttribute("CARD_ORDERED_STATUS_FOR_IS");
				
			    } catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while getting information for" 
						+ " displaying Card order status page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			    }
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS);
		
		return mapping.findForward(returnValue);
	}
	
	public ActionForward cardOrderedStatusForIS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
			
		final String MY_NAME = ME + "cardOrderedStatusForIS: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for IS");
		HttpSession session = request.getSession(true);
		String returnValue = "cardOrderedStatusForIS";
	
		CardOrderedStatusForm cardOrderedStatusForm =(CardOrderedStatusForm) form;
		CardOrderStatusBusiness cardOrderStatusBusiness=new CardOrderStatusBusiness();
		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
		
			try {
				String patronid = "";
				if (null != session.getAttribute(Constants.USER_INFO)) {
					loginPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					if (null != loginPatronDTO) {
						patronid = loginPatronDTO.getPatronid().toString();}}
                
				//for getting order status of IS
				HashMap statusMap = null;
				if (null != session.getAttribute(Constants.STATUS_INFO_MAP)) {
					statusMap = (HashMap) session
					.getAttribute(Constants.STATUS_INFO_MAP);
				}
				List stateList = cardOrderStatusBusiness.getStatusInformationForIS(statusMap);
				session.setAttribute(Constants.STATUS_LIST, stateList);
				
				request.setAttribute("cardOrderedStatusForm", cardOrderedStatusForm);
				List iscardOrderedList = null;
				iscardOrderedList=cardOrderStatusBusiness
				          .isOrderSearch(cardOrderedStatusForm, patronid);
				session.setAttribute(Constants.CARD_ORDERED_STATUS_FOR_IS, iscardOrderedList);
				session.removeAttribute("SHOW_UPDATED_DIV");
			
						
				} catch (Exception e) {
			
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while getting information for" 
						+ " displaying Card Order Status for IS page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
				}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS_IS);
		
		return mapping.findForward(returnValue);
	}
	
	public ActionForward cardOrderedStatusForGBS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "cardOrderedStatusForGBS: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for GBS");
		HttpSession session = request.getSession(true);
		String returnValue = "cardOrderedStatusForGBS";
	
		CardOrderedStatusForm cardOrderedStatusForm =(CardOrderedStatusForm) form;
		CardOrderStatusBusiness cardOrderStatusBusiness=new CardOrderStatusBusiness();
		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
		
			try {
				String patronid = "";
				if (null != session.getAttribute(Constants.USER_INFO)) {
					loginPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					if (null != loginPatronDTO) {
						patronid = loginPatronDTO.getPatronid().toString();
					}
				}
			    //for getting order status of GBS
				HashMap statatusMap = null;
				if (null != session.getAttribute(Constants.STATUS_INFO_MAP)) {
					statatusMap = (HashMap) session
					.getAttribute(Constants.STATUS_INFO_MAP);
				}
				List stateList = cardOrderStatusBusiness.getStatusInformationForGBS(statatusMap);
				session.setAttribute(Constants.STATUS_LIST, stateList);
				
				request.setAttribute("cardOrderedStatusForm", cardOrderedStatusForm);
				List cardOrderedList = new ArrayList();
				cardOrderedList=cardOrderStatusBusiness.gbsOrderSearch(cardOrderedStatusForm, patronid);
				session.setAttribute(Constants.CARD_ORDERED_STATUS_FOR_GBS, cardOrderedList);
				session.removeAttribute("SHOW_UPDATE_GBS_DIV");
				
			    } catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while getting information for" 
						+ " displaying Card Order Status For GBS page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			    }
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS_GBS);
		
		return mapping.findForward(returnValue);
	}
	
	public ActionForward cardOrderedStatusForPS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "cardOrderedStatusForPS: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for PS");
		HttpSession session = request.getSession(true);
		String returnValue = "cardOrderedStatusForPS";
		
		CardOrderedStatusForm cardOrderedStatusForm =(CardOrderedStatusForm) form;
		CardOrderStatusBusiness cardOrderStatusBusiness=new CardOrderStatusBusiness();
		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
		
			try {
				String patronid = "";
				if (null != session.getAttribute(Constants.USER_INFO)) {
					loginPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					if (null != loginPatronDTO) {
						patronid = loginPatronDTO.getPatronid().toString();
					}
				}
	            //for getting order status of GBS
				HashMap statatusMap = null;
				if (null != session.getAttribute(Constants.STATUS_INFO_MAP)) {
					 statatusMap = (HashMap) session
					.getAttribute(Constants.STATUS_INFO_MAP);
				}
				List stateList = cardOrderStatusBusiness.getStatusInformationForPS(statatusMap);
				session.setAttribute(Constants.STATUS_LIST, stateList);
				
				request.setAttribute("cardOrderedStatusForm", cardOrderedStatusForm);			
				List pscardOrderedList = new ArrayList();
				pscardOrderedList=cardOrderStatusBusiness.psOrderSearch(cardOrderedStatusForm, patronid);
				session.setAttribute(Constants.CARD_ORDERED_STATUS_FOR_PS, pscardOrderedList);
				session.removeAttribute("SHOW_PS_UPDATE_DIV");
				
			    } catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while getting information for" 
						+ " displaying Card Order Status For PS page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			    }
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS_PS);
		
		return mapping.findForward(returnValue);
	}
		
	
	public ActionForward populategbsViewDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "populategbsViewDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for gbs Order");
		HttpSession session = request.getSession(true);
		String returnValue = "cardOrderedStatusForGBS";
		String displayOrderId="";
		
		CardOrderedStatusForm cardOrderedStatusForm =(CardOrderedStatusForm) form;
		CardOrderStatusBusiness cardOrderStatusBusiness=new CardOrderStatusBusiness();
		BcwtSearchDTO bcwtSearchDTO = null;
		
			try {
				if(request.getParameter("gbsOrderId")!=null &&! 
						Util.isBlankOrNull(request.getParameter("gbsOrderId"))){
					displayOrderId=request.getParameter("gbsOrderId");
				}
				
				bcwtSearchDTO=cardOrderStatusBusiness.populategbsViewDetails(displayOrderId);			
				
			    if(!Util.isBlankOrNull(bcwtSearchDTO.getOrderId())){
					cardOrderedStatusForm.setEditOrderId(bcwtSearchDTO.getOrderId());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getOrderedDate())){
					cardOrderedStatusForm.setEditOrderDate(bcwtSearchDTO.getOrderedDate());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getShippeddate())){
					cardOrderedStatusForm.setEditShippedDate(bcwtSearchDTO.getShippeddate());
				}
				
				if(!Util.isBlankOrNull(bcwtSearchDTO.getOrderStatus())){
					cardOrderedStatusForm.setEditOrderStatus(bcwtSearchDTO.getOrderStatus());
				}
				
				session.setAttribute("SHOW_DATA", bcwtSearchDTO);
				request.setAttribute("cardOrderedStatusForm", cardOrderedStatusForm);
				session.setAttribute("SHOW_UPDATE_GBS_DIV", "SHOW_UPDATE_GBS_DIV");
						
			    } catch (MartaException e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while getting information for" 
						+ " displaying pre populated data for gbs Order page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			    }
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS_GBS);
		
		return mapping.findForward(returnValue);

	}
	public ActionForward updateGbsOrderDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "updateGbsOrderDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "to update GBS card order status details");
		HttpSession session = request.getSession(true);
		String returnValue = "cardOrderedStatusForGBS";
		
		CardOrderStatusBusiness cardOrderStatusBusiness= null;
		String patronId = "";
		BcwtSearchDTO bcwtSearchDTO = null;
		BcwtPatronDTO loginPatronDTO =null;
		boolean isUpdate = false;
		CardOrderedStatusForm cardOrderedStatusForm =(CardOrderedStatusForm) form;
				
			try {
				cardOrderStatusBusiness = new CardOrderStatusBusiness();
				if(session.getAttribute(Constants.USER_INFO)!=null){
					loginPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					patronId = loginPatronDTO.getPatronid().toString();
				}
				bcwtSearchDTO = new BcwtSearchDTO();
	
				if(cardOrderedStatusForm.getEditOrderId() != null){
					bcwtSearchDTO.setOrderId(cardOrderedStatusForm.getEditOrderId());
				}
				if(cardOrderedStatusForm.getEditOrderDate() != null){
					bcwtSearchDTO.setOrderedDate(cardOrderedStatusForm.getEditOrderDate());
				}
				if(cardOrderedStatusForm.getEditOrderStatus() != null){
					bcwtSearchDTO.setOrderStatus(cardOrderedStatusForm.getEditOrderStatus());
				}
				if(cardOrderedStatusForm.getEditShippedDate() != null){
					bcwtSearchDTO.setShippeddate(cardOrderedStatusForm.getEditShippedDate());
				}
				 isUpdate = cardOrderStatusBusiness.updateGbsOrderDetails(bcwtSearchDTO);
				if(isUpdate){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.UPDATE_GBS_ORDER_DETAILS_SUCCESS));
					request.setAttribute(Constants.SUCCESS, "true");
					List cardOrderedList=cardOrderStatusBusiness.gbsOrderSearch(cardOrderedStatusForm, patronId);
					session.setAttribute(Constants.CARD_ORDERED_STATUS_FOR_GBS, cardOrderedList);
				}else{
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.UPDATE_GBS_ORDER_DETAILS_FAILURE));
					request.setAttribute(Constants.SUCCESS, "false");
				}
				session.removeAttribute("SHOW_EDIT_DIV");
				session.removeAttribute("SHOW_UPDATE_GBS_DIV");
				} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while updating card order status for GBS page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
				}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS_GBS);
	
		return mapping.findForward(returnValue);
	}
	
	public ActionForward populateisViewDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "populateisViewDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for gbs Order");
		HttpSession session = request.getSession(true);
		String returnValue = "cardOrderedStatusForIS";
		String displayOrderId="";
		
		CardOrderedStatusForm cardOrderedStatusForm =(CardOrderedStatusForm) form;
		CardOrderStatusBusiness cardOrderStatusBusiness=new CardOrderStatusBusiness();
		BcwtSearchDTO bcwtSearchDTO = null;
		
			try {
				if(!Util.isBlankOrNull(request.getParameter("isPatronId"))){
					displayOrderId=request.getParameter("isPatronId");
				}
				
				bcwtSearchDTO=cardOrderStatusBusiness.populateisViewDetails(displayOrderId);			
				
			    if(!Util.isBlankOrNull(bcwtSearchDTO.getOrderId())){
					cardOrderedStatusForm.setEditOrderId(bcwtSearchDTO.getOrderId());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getOrderedDate())){
					cardOrderedStatusForm.setEditOrderDate(bcwtSearchDTO.getOrderedDate());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getPartnerFirstName())){
					cardOrderedStatusForm.setEditpatronFirstName(bcwtSearchDTO.getPartnerFirstName());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getPartnerLastName())){
					cardOrderedStatusForm.setEditpatronLastName(bcwtSearchDTO.getPartnerLastName());
				}
				
				if(!Util.isBlankOrNull(bcwtSearchDTO.getOrderStatus())){
					cardOrderedStatusForm.setEditOrderStatus(bcwtSearchDTO.getOrderStatus());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getShippeddate())){
					cardOrderedStatusForm.setEditShippedDate(bcwtSearchDTO.getShippeddate());
				}
				
				session.setAttribute("SHOW_DATA", bcwtSearchDTO);
				request.setAttribute("cardOrderedStatusForm", cardOrderedStatusForm);
				session.setAttribute("SHOW_UPDATED_DIV", "SHOW_UPDATED_DIV");
									
				} catch (MartaException e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while getting information for" 
						+ " displaying pre populated data for is Order page:"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
				}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS_IS);
	
		return mapping.findForward(returnValue);

	}
	
	public ActionForward updateIsOrderDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "updateGbsOrderDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "to update GBS card order status details");
		HttpSession session = request.getSession(true);
		String returnValue = "cardOrderedStatusForIS";
		
		CardOrderStatusBusiness cardOrderStatusBusiness= null;
		String patronId = "";
		BcwtSearchDTO bcwtSearchDTO = null;
		boolean isUpdate = false;
		BcwtPatronDTO loginPatronDTO =null;
		CardOrderedStatusForm cardOrderedStatusForm =(CardOrderedStatusForm) form;
		
			try {
				cardOrderStatusBusiness = new CardOrderStatusBusiness();
				
				if(session.getAttribute(Constants.USER_INFO)!=null){
					loginPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					patronId = loginPatronDTO.getPatronid().toString();
				}
				bcwtSearchDTO = new BcwtSearchDTO();
	
				if(cardOrderedStatusForm.getEditOrderId() != null){
					bcwtSearchDTO.setOrderId(cardOrderedStatusForm.getEditOrderId());
				}
				if(cardOrderedStatusForm.getEditOrderDate() != null){
					bcwtSearchDTO.setOrderedDate(cardOrderedStatusForm.getEditOrderDate());
				}
				if(cardOrderedStatusForm.getEditOrderStatus() != null){
					bcwtSearchDTO.setOrderStatus(cardOrderedStatusForm.getEditOrderStatus());
				}
				if(cardOrderedStatusForm.getEditShippedDate() != null){
					bcwtSearchDTO.setShippeddate(cardOrderedStatusForm.getEditShippedDate());
				}
				isUpdate = cardOrderStatusBusiness.updateIsOrderDetails(bcwtSearchDTO);
				
				if(isUpdate){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.UPDATE_IS_ORDER_DETAILS_SUCCESS));
					request.setAttribute(Constants.SUCCESS, "true");
					List iscardOrderedList=cardOrderStatusBusiness
					             .isOrderSearch(cardOrderedStatusForm, patronId);
					session.setAttribute(Constants.CARD_ORDERED_STATUS_FOR_IS, iscardOrderedList);
				}else{
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.UPDATE_IS_ORDER_DETAILS_FAILURE));
					request.setAttribute(Constants.SUCCESS, "false");
				}
				
				session.removeAttribute("SHOW_EDIT_DIV");
				session.removeAttribute("SHOW_UPDATED_DIV");
				} catch (Exception e) {
					BcwtsLogger
					.error(MY_NAME
							+ " Exception while updating" 
							+ " card order status for IS page"
							+ e.getMessage());
					returnValue = ErrorHandler.handleError(e, "", request, mapping);
				}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS_IS);
	
		return mapping.findForward(returnValue);
	}
	
	public ActionForward populatepsViewDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "populatepsViewDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for ps Order");
		HttpSession session = request.getSession(true);
		String returnValue = "cardOrderedStatusForPS";
		
		CardOrderedStatusForm cardOrderedStatusForm =(CardOrderedStatusForm) form;
		CardOrderStatusBusiness cardOrderStatusBusiness=new CardOrderStatusBusiness();
		String displayOrderId="";
		BcwtSearchDTO bcwtSearchDTO = null;
		
			try {
				if(request.getParameter("psOrderId")!=null &&! 
						Util.isBlankOrNull(request.getParameter("psOrderId"))){
					displayOrderId=request.getParameter("psOrderId");
				}
				
				bcwtSearchDTO=cardOrderStatusBusiness
				           .populatepsViewDetails(displayOrderId);			
				
			    if(!Util.isBlankOrNull(bcwtSearchDTO.getOrderId())){
					cardOrderedStatusForm.setEditOrderId(bcwtSearchDTO.getOrderId());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getOrderedDate())){
					cardOrderedStatusForm.setEditOrderDate(bcwtSearchDTO.getOrderedDate());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getShippeddate())){
					cardOrderedStatusForm.setEditShippedDate(bcwtSearchDTO.getShippeddate());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getOrderStatus())){
					cardOrderedStatusForm.setEditOrderStatus(bcwtSearchDTO.getOrderStatus());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getBatchId())){
					cardOrderedStatusForm.setEditBatchId(bcwtSearchDTO.getBatchId());
				}
				if(!Util.isBlankOrNull(bcwtSearchDTO.getCompanyName())){
					cardOrderedStatusForm.setEditCompanyName(bcwtSearchDTO.getCompanyName());
				}
				session.setAttribute("SHOW_DATA", bcwtSearchDTO);
				request.setAttribute("cardOrderedStatusForm", cardOrderedStatusForm);
				session.setAttribute("SHOW_PS_UPDATE_DIV", "SHOW_PS_UPDATE_DIV");
						
			    } catch (MartaException e) {
					BcwtsLogger
					.error(MY_NAME
							+ " Exception while getting information for" 
							+ " displaying pre populated data for ps Order page:"
							+ e.getMessage());
					returnValue = ErrorHandler.handleError(e, "", request, mapping);
			    }
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS_PS);
	
		return mapping.findForward(returnValue);

	}
	public ActionForward updatePsOrderDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "updatePsOrderDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "to update PS card order status details");
		HttpSession session = request.getSession(true);
		String returnValue = "cardOrderedStatusForPS";
		
		CardOrderStatusBusiness cardOrderStatusBusiness= null;
		String patronId = "";
		BcwtSearchDTO bcwtSearchDTO = null;
		BcwtPatronDTO loginPatronDTO =null;
		boolean isUpdate = false;
		CardOrderedStatusForm cardOrderedStatusForm =(CardOrderedStatusForm) form;
		
		try {
			cardOrderStatusBusiness = new CardOrderStatusBusiness();
			
			if(session.getAttribute(Constants.USER_INFO)!=null){
				loginPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId = loginPatronDTO.getPatronid().toString();
			}
			
			bcwtSearchDTO = new BcwtSearchDTO();

			if(cardOrderedStatusForm.getEditOrderId() != null){
				bcwtSearchDTO.setOrderId(cardOrderedStatusForm.getEditOrderId());
			}
			if(cardOrderedStatusForm.getEditOrderDate() != null){
				bcwtSearchDTO.setOrderedDate(cardOrderedStatusForm.getEditOrderDate());
			}
			if(cardOrderedStatusForm.getEditOrderStatus() != null){
				bcwtSearchDTO.setOrderStatus(cardOrderedStatusForm.getEditOrderStatus());
			}
			if(cardOrderedStatusForm.getEditShippedDate() != null){
				bcwtSearchDTO.setShippeddate(cardOrderedStatusForm.getEditShippedDate());
			}
			 isUpdate = cardOrderStatusBusiness.updatePsOrderDetails(bcwtSearchDTO);
			if(isUpdate){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.UPDATE_PS_ORDER_DETAILS_SUCCESS));
				request.setAttribute(Constants.SUCCESS, "true");
				List cardOrderedList=cardOrderStatusBusiness.psOrderSearch(cardOrderedStatusForm, patronId);
				session.setAttribute(Constants.CARD_ORDERED_STATUS_FOR_PS, cardOrderedList);
			}else{
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.UPDATE_PS_ORDER_DETAILS_FAILURE));
				request.setAttribute(Constants.SUCCESS, "false");
			}
					
			session.removeAttribute("SHOW_EDIT_DIV");
			session.removeAttribute("SHOW_PS_UPDATE_DIV");
			} catch (Exception e) {
				BcwtsLogger
				.error(MY_NAME
						+ " Exception while updating" 
						+ " card order status for PS page"
						+ e.getMessage());
				returnValue = ErrorHandler.handleError(e, "", request, mapping);
			}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS_PS);

		return mapping.findForward(returnValue);
	}
	
	
	
}
