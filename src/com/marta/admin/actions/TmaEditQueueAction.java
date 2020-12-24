package com.marta.admin.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

import com.marta.admin.business.TmaEditQueueManagement;
//import com.marta.admin.business.TmaManagement;
//import com.marta.admin.dto.PSViewOrderHistoryDTO;
//import com.marta.admin.dto.TmaInformationDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.Util;

public class TmaEditQueueAction extends DispatchAction {
	
	final String ME = "TmaEditQueueAction: ";
	
	/**
	 * Display new card details .
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returnPath
	 * @throws MartaException
	 */
	public ActionForward getNewCardDetails(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
		final String MY_NAME = ME + "getNewCardDetails: ";
		BcwtsLogger.debug(MY_NAME + "Showing list of new cards in the queue");
		String returnPath = "newCardQueueTMA";
		HttpSession session = request.getSession(true);
		TmaEditQueueManagement tmaEditQueueManagement = null;
		List newCardList = null;
		List hotListCardDetails = null;	
		List partnerBatchDetails = null;
		
		try {			
				
			tmaEditQueueManagement = new TmaEditQueueManagement();						
			newCardList = tmaEditQueueManagement.getNewCardDetails();
			hotListCardDetails = tmaEditQueueManagement.getHotlistCardDetails();
			partnerBatchDetails = tmaEditQueueManagement.getBatchDetails();
			newCardList.addAll(partnerBatchDetails);
			newCardList.addAll(hotListCardDetails);
		
			session.setAttribute(Constants.NEW_CARD_QUEUE_LIST, newCardList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_NEW_CARD_QUEUE_LIST);
			
			
		}catch (Exception e) {			
			BcwtsLogger.error("Error while showing list cards page : " + Util.getFormattedStackTrace(e));
			returnPath = ErrorHandler.handleError(e, "", request, mapping);
		}		
		return mapping.findForward(returnPath);		
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward deleteQueue(ActionMapping mapping, 
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException{
		
		final String MY_NAME = ME + "deleteQueue: ";
		BcwtsLogger.debug(MY_NAME + "delete a queue");		
		HttpSession session = request.getSession();
		TmaEditQueueManagement tmaEditQueueManagement = null;
		String returnPath = "newCardQueueTMA";
		List companyList = null;
		List newCardList = null;
		List hotListCardDetails = null;
		String companyIdStr = "";
		try {
			String deleteId = request.getParameter("deleteid");
			BcwtsLogger.info("delete d"+deleteId);
			
			Long id = null;
			tmaEditQueueManagement = new TmaEditQueueManagement();
			String action = "";
			if(null!=deleteId){
				StringTokenizer st = new StringTokenizer(deleteId,"::");
				if(st.hasMoreTokens()){
						id = new Long(st.nextToken());
						action = st.nextToken();	
						
				}
				if(action.equalsIgnoreCase(Constants.TMA_ACTIVATE) || action.equalsIgnoreCase(Constants.TMA_DEACTIVATE) ){
					System.out.println("Id hotlist: "+id);
					tmaEditQueueManagement.removeBatchCard(id,action);
					tmaEditQueueManagement.removeHotlistCard(id);	
					
				}else if(action.equalsIgnoreCase(Constants.TMA_NEWCARD)){
					System.out.println("Id new card: "+id);
					tmaEditQueueManagement.removeNewCard(id);	
				}else{
					
					System.out.println("Id batch card: "+action);
					tmaEditQueueManagement.removeHotlistCard(id);	
					
				}
			}
			
		//	companyIdStr = companyIdStr.substring(0,companyIdStr.length()-2);
			newCardList = tmaEditQueueManagement.getNewCardDetails();
			hotListCardDetails = tmaEditQueueManagement.getHotlistCardDetails();
			newCardList.addAll(hotListCardDetails);
			session.setAttribute(Constants.NEW_CARD_QUEUE_LIST, newCardList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_NEW_CARD_QUEUE_LIST);
			
			
			
		}
		catch (Exception e) {
			BcwtsLogger.error("Error while deleting a card : " + Util.getFormattedStackTrace(e));
			returnPath = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnPath);	
	}
	
	/*public ActionForward getCardOrders(ActionMapping mapping, 
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException{
	
	final String MY_NAME = ME + "getCardOrders: ";
	BcwtsLogger.debug(MY_NAME + "Showing list of card orders");
	String returnPath = "cardOrders";
	HttpSession session = request.getSession(true);
	TmaManagement tmaManagement = null;
	
	List<PSViewOrderHistoryDTO> cardOrderList = new ArrayList<PSViewOrderHistoryDTO>();

	try {			
		TmaInformationDTO tmaInformationDTO = (TmaInformationDTO) session.getAttribute(Constants.TMA_USER_INFO);
			tmaManagement = new TmaManagement();						
		cardOrderList = tmaManagement.getTmaCardOrders(String.valueOf(tmaInformationDTO.getId()));
		
	
		session.setAttribute("CARD_ORDER_LIST", cardOrderList);
		session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_TMA_CARD_ORDERS);
		
		
	}catch (Exception e) {			
		BcwtsLogger.error("Error while showing list cards page : " + Util.getFormattedStackTrace(e));
		returnPath = ErrorHandler.handleError(e, "", request, mapping);
	}		
	return mapping.findForward(returnPath);		
}
	*/
}