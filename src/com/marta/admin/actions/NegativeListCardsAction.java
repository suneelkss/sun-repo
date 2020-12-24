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
import com.marta.admin.business.CardOrderStatusBusiness;
import com.marta.admin.business.NegativeListCardsBusiness;
import com.marta.admin.business.LogManagement;
import com.marta.admin.business.PatronManagement;
import com.marta.admin.dao.SearchDAO;
import com.marta.admin.dto.ApplicationSettingsDTO;
import com.marta.admin.dto.NegativeListCardsDTO;

import com.marta.admin.dto.BcwtLogDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ApplicationSettingsForm;
import com.marta.admin.forms.CardOrderedStatusForm;
import com.marta.admin.forms.NegativeListCardForm;
import com.marta.admin.forms.LoggingCallsForm;
import com.marta.admin.forms.SignupForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class NegativeListCardsAction  extends DispatchAction {
	
	final String ME = "NegativeListCardsAction: ";

	/*public ActionForward displayNegativeListCardsStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayNegativeListCardsStatus: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for NegativeListCards");
		HttpSession session = request.getSession(true);
		
		String returnValue = "negativeListCardsStatusMain";
		
		try {
			
			List negativeListCardsList = new ArrayList();
			session.setAttribute(Constants.NEGATIVE_LIST_CARDS, negativeListCardsList);
			returnValue = "negativeListCardsStatusMain";
			session.removeAttribute("CARD_ORDERED_STATUS_FOR_IS");
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_CARD_ORDER_STATUS);
		
		return mapping.findForward(returnValue);
	}*/
	
	public ActionForward negativeListCards(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
			
		final String MY_NAME = "negativeListCards: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for negativeListCardsStatus");
		HttpSession session = request.getSession(true);
		
		String returnValue = "negativeListCardsStatusMain";
	
		NegativeListCardForm negativeListCardForm =(NegativeListCardForm) form;
		NegativeListCardsBusiness negativeListCardsBusiness=new NegativeListCardsBusiness();
		
		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
		
			try {
				String patronid = "";
				if (null != session.getAttribute(Constants.USER_INFO)) {
					loginPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					if (null != loginPatronDTO) {
						patronid = loginPatronDTO.getPatronid().toString();
					}
				}
   
			request.setAttribute("negativeListCardForm", negativeListCardForm);
			List iscardOrderedList = new ArrayList();
			iscardOrderedList=negativeListCardsBusiness.negativeListCardSearch(negativeListCardForm, patronid);
			session.setAttribute(Constants.NEGATIVE_LIST_CARDS, iscardOrderedList);
			session.removeAttribute("NEGATIVE_LIST_SHOW_UPDATED_DIV");
			session.setAttribute(Constants.BREAD_CRUMB_NAME,">> My Account >> Negative List Payment Cards " );
			//session.setAttribute("SHOW_UPDATED_DIV", "SHOW_UPDATED_DIV");
			returnValue = "negativeListCardsStatusMain";
			
		} catch (Exception e) {
			
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		
		return mapping.findForward(returnValue);
	}
	
	public ActionForward populateNegativeListViewDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "populateNegativeListViewDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for Negative List");
		HttpSession session = request.getSession(true);
		String returnValue = "negativeListCardsStatusMain";
		
		NegativeListCardForm negativeListCardForm =(NegativeListCardForm) form;
		NegativeListCardsBusiness negativeListCardsBusiness=new NegativeListCardsBusiness();
		
		String patronId = "";
		
		String displayOrderId="";
		
		//BcwtSearchDTO bcwtSearchDTO = null;
		NegativeListCardsDTO negativeListCardsDTO=new NegativeListCardsDTO();
		BcwtPatronDTO bcwtPatronDTO=null;
		List orderList=new ArrayList();
		try {
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId = bcwtPatronDTO.getPatronid().toString();
			}
			
			if(!Util.isBlankOrNull(request.getParameter("isPatronId"))){
				displayOrderId=request.getParameter("isPatronId");
				negativeListCardForm.setPaymentCardId(displayOrderId);
			}
			
			negativeListCardsDTO=negativeListCardsBusiness.populateNegativeListViewDetails(displayOrderId);			
			
		    /*if(!Util.isBlankOrNull(negativeListCardsDTO.getPatronFirstName())){
		    	negativeListCardForm.setPatronFirstName(negativeListCardsDTO.getPatronFirstName());
			}
			if(!Util.isBlankOrNull(negativeListCardsDTO.getPatronLastName())){
				negativeListCardForm.setPatronLastName(negativeListCardsDTO.getPatronLastName());
			}
			if(!Util.isBlankOrNull(negativeListCardsDTO.getCreditCardNo())){
				negativeListCardForm.setCreditCardNo(negativeListCardsDTO.getCreditCardNo());
			}
			if(!Util.isBlankOrNull(negativeListCardsDTO.getExpiryMonth())){
				negativeListCardForm.setExpiryMonth(negativeListCardsDTO.getExpiryMonth());
			}
			
			if(!Util.isBlankOrNull(negativeListCardsDTO.getExpiryYear())){
				negativeListCardForm.setExpiryYear(negativeListCardsDTO.getExpiryYear());
			}
			
			if(!Util.isBlankOrNull(negativeListCardsDTO.getZipCode())){
				negativeListCardForm.setZipCode(negativeListCardsDTO.getZipCode());
			}*/
			String no = "no";
			String yes = "yes";
			
			if(!Util.isBlankOrNull(negativeListCardsDTO.getPatronName())){
				negativeListCardForm.setShowPatronName(negativeListCardsDTO.getPatronName());
			}
			if(!Util.isBlankOrNull(negativeListCardsDTO.getCreditCardNo())){
				negativeListCardForm.setShowCreditCardNo(negativeListCardsDTO.getCreditCardNo());
			}
			if(!Util.isBlankOrNull(negativeListCardsDTO.getExpiryDate())){
				negativeListCardForm.setShowExpiryDate(negativeListCardsDTO.getExpiryDate());
			}
			if(!Util.isBlankOrNull(negativeListCardsDTO.getNegativeListStatus())){
				if(negativeListCardsDTO.getNegativeListStatus().equalsIgnoreCase(Constants.NO)){
					negativeListCardForm.setNegativeListStatus("0");					
				}
				if(negativeListCardsDTO.getNegativeListStatus().equalsIgnoreCase(Constants.YES)){
					negativeListCardForm.setNegativeListStatus("1");					
				}				
			}
			session.setAttribute("NEGATIVE_LIST_SHOW_DATA", negativeListCardsDTO);
			request.setAttribute("negativeListCardForm", negativeListCardForm);
			session.setAttribute("NEGATIVE_LIST_SHOW_UPDATED_DIV", "NEGATIVE_LIST_SHOW_UPDATED_DIV");
			
		
		} catch (MartaException e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_NEGATIVE_LIST_STATUS);
	
		return mapping.findForward(returnValue);

	}
	
	public ActionForward updateNegativeListCardsDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "updateNegativeListCardsDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "to update application settings details");
		HttpSession session = request.getSession(true);
		String returnValue = "negativeListCardsStatusMain";
		NegativeListCardsBusiness negativeListCardsBusiness=null;
	
		String patronId = "";
		BcwtSearchDTO bcwtSearchDTO = null;
		NegativeListCardsDTO negativeListCardsDTO=null;
		
		boolean isUpdate = false;
		BcwtPatronDTO loginPatronDTO =null;
		String paymentCardId = ""; 
		NegativeListCardForm negativeListCardForm =(NegativeListCardForm) form;
		List updategbsorderList = new ArrayList();
		try {
			
			negativeListCardsBusiness=new NegativeListCardsBusiness();
			
			if(session.getAttribute(Constants.USER_INFO)!=null){
				loginPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				patronId = loginPatronDTO.getPatronid().toString();
			}
			negativeListCardsDTO = new NegativeListCardsDTO(); 
			
			/*if(negativeListCardForm.getNegativeListStatus() != null){
				negativeListCardsDTO.setNegativeListStatus(negativeListCardForm.getNegativeListStatus());
			}*/
			
			
			if(!Util.isBlankOrNull(negativeListCardForm.getPaymentCardId())){
				negativeListCardsDTO.setPaymentCardId(negativeListCardForm.getPaymentCardId());
			}
			if(!Util.isBlankOrNull(negativeListCardForm.getNegativeListStatus())){
				if(negativeListCardForm.getNegativeListStatus().equalsIgnoreCase(Constants.ACTIVE_STATUS)){
					negativeListCardsDTO.setNegativeListStatus("YES");
				}
				if(negativeListCardForm.getNegativeListStatus().equalsIgnoreCase(Constants.IN_ACTIVE_STATUS)){
					negativeListCardsDTO.setNegativeListStatus("NO");
				}
			}
			
			
			isUpdate = negativeListCardsBusiness.updateNegativeListCardsDetails(negativeListCardsDTO);
			
			if(isUpdate){
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.UPDATE_NEGATIVE_LIST_DETAILS_SUCCESS));
				request.setAttribute(Constants.SUCCESS, "true");
				List iscardOrderedList=negativeListCardsBusiness.negativeListCardSearch(negativeListCardForm, patronId);
				session.setAttribute(Constants.NEGATIVE_LIST_CARDS, iscardOrderedList);
			}else{
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.UPDATE_NEGATIVE_LIST_DETAILS_FAILURE));
				request.setAttribute(Constants.SUCCESS, "false");
			}
			
			session.removeAttribute("SHOW_EDIT_DIV");
			session.removeAttribute("NEGATIVE_LIST_SHOW_UPDATED_DIV");
			
			
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while displaying marta admin page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_NEGATIVE_LIST_STATUS);
	
		return mapping.findForward(returnValue);
	}
	
}