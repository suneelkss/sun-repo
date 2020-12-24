package com.marta.admin.business;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;


import com.marta.admin.dao.OrderSearchDAO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.forms.CardOrderedStatusForm;


public class CardOrderStatusBusiness extends DispatchAction 
{
	    final String ME = "CardOrderStatusBusiness: " ;
	    
        public List gbsOrderSearch(CardOrderedStatusForm cardOrderedStatusForm,String patronId) throws MartaException{
		
		final String MY_NAME = ME + " gbsOrderSearch: ";
		BcwtsLogger.debug(MY_NAME + " getting gbs Order information ");
		
		List gbsOrderList = null;
		
		try {
			OrderSearchDAO  orderSearchDAO=new OrderSearchDAO();
			gbsOrderList = orderSearchDAO.getGbsOrderDetails(cardOrderedStatusForm,patronId);
			BcwtsLogger.info(MY_NAME + " got gbs Order information List");
		    } catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting gbs Order information list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_CARD_ORDER_STATUS_FIND ));
		}
		return gbsOrderList;	
	}
        
        public List isOrderSearch(CardOrderedStatusForm cardOrderedStatusForm,String patronId) throws MartaException{
    		
    		final String MY_NAME = ME + " isOrderSearch: ";
    		BcwtsLogger.debug(MY_NAME + " getting is Order information ");
    		
    		List isOrderList = null;
    		
    		try {
    			OrderSearchDAO  orderSearchDAO=new OrderSearchDAO();
    			isOrderList = orderSearchDAO.getIsOrderDetails(cardOrderedStatusForm,patronId);
    			BcwtsLogger.info(MY_NAME + " got is Order information List");
    		    } catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in getting is Order information list :"+e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_CARD_ORDER_STATUS_FIND ));
    		}
    		return isOrderList;	
    	}
        
        public List psOrderSearch(CardOrderedStatusForm cardOrderedStatusForm,String patronId) throws MartaException{
    		
    		final String MY_NAME = ME + " psOrderSearch: ";
    		BcwtsLogger.debug(MY_NAME + " getting ps Order information ");
    		
    		List psOrderList = null;
    		
    		try {
    			OrderSearchDAO  orderSearchDAO=new OrderSearchDAO();
    			psOrderList = orderSearchDAO.getPsOrderDetails(cardOrderedStatusForm,patronId);
    			BcwtsLogger.info(MY_NAME + " got ps Order information List");
    		    } catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in getting ps Order information list :"+e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_CARD_ORDER_STATUS_FIND ));
    		}
    		return psOrderList;	
    	}
        public BcwtSearchDTO populategbsViewDetails(String orderId) throws MartaException {
    		final String MY_NAME = ME + " populategbsViewDetails: ";
    		BcwtsLogger.debug(MY_NAME + " to populate gbs view details ");
    		//List lockedUserAccounntList = new ArrayList();
    		BcwtSearchDTO viewList = null;
    		try {
    		
    			OrderSearchDAO  orderSearchDAO=new OrderSearchDAO();
    			viewList = orderSearchDAO.populategbsViewDetails(orderId);
    			BcwtsLogger.info(MY_NAME + " got List to populate gbs view  details ");
    			
    		} catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in populating gbs view details list :"
    					+ e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_ORDER_STATUS_FIND));
    		}
    		return viewList;
    	}
        
        public boolean updateGbsOrderDetails(BcwtSearchDTO bcwtSearchDTO)throws MartaException{
    		
    		final String MY_NAME = ME + " updateGbsOrderDetails: ";
    		BcwtsLogger.debug(MY_NAME + " to update Gbs Order Details ");
    		OrderSearchDAO  orderSearchDAO= null;
    		
    		boolean isUpdate  = false;
    		try {
    			orderSearchDAO = new OrderSearchDAO();
    			 isUpdate = orderSearchDAO.updateGbsOrderDetails(bcwtSearchDTO);
    		} catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in updating Gbs Order Details :"
    					+ e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_CARD_ORDER_STATUS_UPDATE));
    		}
    		return isUpdate;
    	}
        
        public BcwtSearchDTO populateisViewDetails(String orderId) throws MartaException {
    		final String MY_NAME = ME + " populateisViewDetails: ";
    		BcwtsLogger.debug(MY_NAME + " to populate view user details ");
    		//List lockedUserAccounntList = new ArrayList();
    		BcwtSearchDTO viewList = null;
    		try {
    		
    			OrderSearchDAO  orderSearchDAO=new OrderSearchDAO();
    			viewList = orderSearchDAO.populateisViewDetails(orderId);
    			BcwtsLogger.info(MY_NAME + " got OrderList");
    			
    		} catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in order display admin list :"
    					+ e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_ORDER_STATUS_FIND));
    		}
    		return viewList;
    	}
        
        public boolean updateIsOrderDetails(BcwtSearchDTO bcwtSearchDTO)throws MartaException{
    		
    		final String MY_NAME = ME + " updateIsOrderDetails: ";
    		BcwtsLogger.debug(MY_NAME + " to update application settings detail ");
    		OrderSearchDAO  orderSearchDAO= null;
    		
    		boolean isUpdate  = false;
    		try {
    			orderSearchDAO = new OrderSearchDAO();
    			 isUpdate = orderSearchDAO.updateIsOrderDetails(bcwtSearchDTO);
    		} catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in adding a patron user details :"
    					+ e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_CARD_ORDER_STATUS_UPDATE));
    		}
    		return isUpdate;
    	}
        
        public BcwtSearchDTO populatepsViewDetails(String orderId) throws MartaException {
    		final String MY_NAME = ME + " populatepsViewDetails: ";
    		BcwtsLogger.debug(MY_NAME + " to populate view user details ");
    		//List lockedUserAccounntList = new ArrayList();
    		BcwtSearchDTO viewList = null;
    		try {
    		
    			OrderSearchDAO  orderSearchDAO=new OrderSearchDAO();
    			viewList = orderSearchDAO.populatepsViewDetails(orderId);
    			BcwtsLogger.info(MY_NAME + " got OrderList");
    			
    		} catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in order display admin list :"
    					+ e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_ORDER_STATUS_FIND));
    		}
    		return viewList;
    	}
        
 public boolean updatePsOrderDetails(BcwtSearchDTO bcwtSearchDTO)throws MartaException{
    		
    		final String MY_NAME = ME + " updatePsOrderDetails: ";
    		BcwtsLogger.debug(MY_NAME + " to update PS OREDER detail ");
    		OrderSearchDAO  orderSearchDAO= null;
    		
    		boolean isUpdate  = false;
    		try {
    			orderSearchDAO = new OrderSearchDAO();
    			 isUpdate = orderSearchDAO.updatePsOrderDetails(bcwtSearchDTO);
    		} catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in adding a patron user details :"
    					+ e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_CARD_ORDER_STATUS_UPDATE));
    		}
    		return isUpdate;
    	}
        
        public List getStatusInformationForIS(HashMap statusMap) throws MartaException {
    		final String MY_NAME = ME + " getStateInformation: ";
    		BcwtsLogger.debug(MY_NAME + " getting state information ");
    		List statusList = null;
    		OrderSearchDAO orderSearchDAO = new OrderSearchDAO();
    		//PatronDAO patronDAO = new PatronDAO();
    		try {

    			if (null != statusMap) {
    				statusList = new ArrayList();
    				Map sortedMap = new TreeMap(statusMap);
    				for (Iterator iter = sortedMap.values().iterator(); iter
    				.hasNext();) {
    					BcwtSearchDTO element = (BcwtSearchDTO) iter.next();
    					statusList.add(new LabelValueBean(
    							String.valueOf(element.getOrderStatus()), String
    							.valueOf(element.getOrderStatusId())));
    				}
    			}else{
    				statusList = orderSearchDAO.getOrderStatusForIS();
    			}
    			BcwtsLogger.info(MY_NAME + " got state List" + statusList.size());
    		} catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in getting state list :"
    					+ e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_PATRON_FIND_STATE));
    		}
    		return statusList;
    	}
        public List getStatusInformationForGBS(HashMap statatusMap) throws MartaException {
    		final String MY_NAME = ME + " getStateInformation: ";
    		BcwtsLogger.debug(MY_NAME + " getting state information ");
    		List statusList = null;
    		OrderSearchDAO orderSearchDAO = new OrderSearchDAO();
    		//PatronDAO patronDAO = new PatronDAO();
    		try {

    			if (null != statatusMap) {
    				statusList = new ArrayList();
    				Map sortedMap = new TreeMap(statatusMap);
    				for (Iterator iter = sortedMap.values().iterator(); iter
    				.hasNext();) {
    					BcwtSearchDTO element = (BcwtSearchDTO) iter.next();
    					statusList.add(new LabelValueBean(
    							String.valueOf(element.getOrderStatus()), String
    							.valueOf(element.getOrderStatusId())));
    				}
    			}else{
    				statusList = orderSearchDAO.getOrderStatusForGBS();
    			}
    			BcwtsLogger.info(MY_NAME + "getting Order Status" + statusList.size());
    		} catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in getting Order Status :"
    					+ e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_PATRON_FIND_STATE));
    		}
    		return statusList;
    	}
        public List getStatusInformationForPS(HashMap statatusMap) throws MartaException {
    		final String MY_NAME = ME + " getStateInformation: ";
    		BcwtsLogger.debug(MY_NAME + " getting state information ");
    		List statusList = null;
    		OrderSearchDAO orderSearchDAO = new OrderSearchDAO();
    		//PatronDAO patronDAO = new PatronDAO();
    		try {

    			if (null != statatusMap) {
    				statusList = new ArrayList();
    				Map sortedMap = new TreeMap(statatusMap);
    				for (Iterator iter = sortedMap.values().iterator(); iter
    				.hasNext();) {
    					BcwtSearchDTO element = (BcwtSearchDTO) iter.next();
    					statusList.add(new LabelValueBean(
    							String.valueOf(element.getOrderStatus()), String
    							.valueOf(element.getOrderStatusId())));
    				}
    			}else{
    				statusList = orderSearchDAO.getOrderStatusForPS();
    			}
    			BcwtsLogger.info(MY_NAME + " got state List" + statusList.size());
    		} catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in getting state list :"
    					+ e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_PATRON_FIND_STATE));
    		}
    		return statusList;
    	}      
}