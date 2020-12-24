package com.marta.admin.business;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

import com.marta.admin.dao.ApplicationSettingsDAO;
import com.marta.admin.dao.LogDAO;
import com.marta.admin.dao.OrderSearchDAO;
import com.marta.admin.dao.NegativeListCardsDAO;
import com.marta.admin.dao.PatronDAO;
import com.marta.admin.dao.SearchDAO;
import com.marta.admin.dto.ApplicationSettingsDTO;
import com.marta.admin.dto.NegativeListCardsDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.dto.BcwtStateDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.forms.NegativeListCardForm;
public class NegativeListCardsBusiness extends DispatchAction 
{







  public List negativeListCardSearch(NegativeListCardForm negativeListCardForm,String patronId) throws MartaException{
    		
    		final String MY_NAME =  " negativeListCardSearch: ";
    		BcwtsLogger.debug(MY_NAME + " getting is Order information ");
    		
    		List isOrderList = null;
    		
    		try {
    			
    			NegativeListCardsDAO negativeListCardsDAO=new NegativeListCardsDAO();
    			isOrderList = negativeListCardsDAO.getNegativeListCardDetails(negativeListCardForm,patronId);
    			
    			BcwtsLogger.info(MY_NAME + " got user List");
    		    } catch (Exception e) {
    			BcwtsLogger.error(MY_NAME + " Exception in getting user list :"+e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_PATRON_USER_FIND));
    		}
    		return isOrderList;	
    	}
  
  public NegativeListCardsDTO populateNegativeListViewDetails(String orderId) throws MartaException {
		final String MY_NAME =  " populateNegativeListViewDetails: ";
		BcwtsLogger.debug(MY_NAME + " to populate view user details ");
		//List lockedUserAccounntList = new ArrayList();
		NegativeListCardsDTO viewList = null;
		try {
			NegativeListCardsDAO negativeListCardsDAO=new NegativeListCardsDAO();
			//OrderSearchDAO  orderSearchDAO=new OrderSearchDAO();
			viewList = negativeListCardsDAO.populateNegativeListViewDetails(orderId);
			BcwtsLogger.info(MY_NAME + " got OrderList");
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in order display admin list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ORDER_FIND));
		}
		return viewList;
	}
  
  public boolean updateNegativeListCardsDetails(NegativeListCardsDTO negativeListCardsDTO)throws MartaException{
		
		final String MY_NAME =  " updateNegativeListCardsDetails: ";
		BcwtsLogger.debug(MY_NAME + " to update application settings detail ");
		//OrderSearchDAO  orderSearchDAO= null;
		NegativeListCardsDAO negativeListCardsDAO =null;
		
		boolean isUpdate  = false;
		try {
		     negativeListCardsDAO=new NegativeListCardsDAO();
			
			 isUpdate = negativeListCardsDAO.updateNegativeListCardsDetails(negativeListCardsDTO);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in adding a patron user details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.APPLICATION_SETTINGS_UPDATE));
		}
		return isUpdate;
	}
  

  
  
}
        