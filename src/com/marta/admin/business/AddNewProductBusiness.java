package com.marta.admin.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

import com.marta.admin.dao.AddNewProductDAO;
import com.marta.admin.dao.ManagePartnerRegistrationDAO;
import com.marta.admin.dao.OrderSearchDAO;
import com.marta.admin.dto.AddNewProductDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.AddNewProductForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;


public class AddNewProductBusiness extends DispatchAction 
{



 public AddNewProductDTO addNewProductList(AddNewProductDTO addNewProductDTO) throws MartaException{
    		
    		final String MY_NAME = " isAddNewProduct: ";
    		BcwtsLogger.debug(MY_NAME + " getting  information ");
    		AddNewProductDAO  addNewProductDAO=null;
    		List listAddNewProduct = null;
    		String nextFareId  = null;
    		AddNewProductDTO resultAddNewProductDTO = new AddNewProductDTO();
    		try {
    			 addNewProductDAO=new AddNewProductDAO();
    			 resultAddNewProductDTO = addNewProductDAO.getAddNewProductDetails(addNewProductDTO);
    			//listAddNewProduct = addNewProductDAO.getAddNewProductDetails(addNewProductDTO);
    			BcwtsLogger.info(MY_NAME + " got user List");
    		    } catch (Exception e) {
    		    	
    			BcwtsLogger.error(MY_NAME + " Exception in getting user list :"+e.getMessage());
    			throw new MartaException(PropertyReader
    					.getValue(MartaConstants.BCWTS_PATRON_USER_FIND));
    		}
    		return resultAddNewProductDTO;	
    	}
 
 public List getRegionInformation(HashMap regionidMap) throws MartaException {
		final String MY_NAME = " getRegionInformation: ";
		BcwtsLogger.debug(MY_NAME + " getting region information ");
		List regionList = null;
		AddNewProductDAO addNewProductDAO = new AddNewProductDAO();
		//PatronDAO patronDAO = new PatronDAO();
		try {

			if (null != regionidMap) {
				regionList = new ArrayList();
				Map sortedMap = new TreeMap(regionidMap);
				for (Iterator iter = sortedMap.values().iterator(); iter
				.hasNext();) {
					
					AddNewProductDTO element = (AddNewProductDTO) iter.next();
					regionList.add(new LabelValueBean(
							String.valueOf(element.getRegionId()), String
							.valueOf(element.getRegionId())));
				}
			}else{
				regionList = addNewProductDAO.getRegion();
			}
			BcwtsLogger.info(MY_NAME + " got state List" + regionList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting state list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_FIND_STATE));
		}
		return regionList;
	}
 
 /*public List getRiderInformation() throws MartaException {
	 final String MY_NAME =  " getaddNewProductStatus : ";
		BcwtsLogger.debug(MY_NAME + " getting addNew Product Status Details ");
		
		AddNewProductDAO addNewProductDAO = new AddNewProductDAO();
		List riderClassificationList = null;
		
		try {			
			riderClassificationList = addNewProductDAO.getRider();
			BcwtsLogger.info(MY_NAME + " got Manage Partner Registration Status Details:");					
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Manage Partner" +
				" Registration Status details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		
		return riderClassificationList;
	}
 public List getFareInstrumentInformation()throws MartaException {
		final String MY_NAME =  " getaddNewProductStatus : ";
		BcwtsLogger.debug(MY_NAME + " getting addNew Product Status Details ");
		
		AddNewProductDAO addNewProductDAO = new AddNewProductDAO();
		List fareInstrumentList = null;
		
		try {			
			fareInstrumentList = addNewProductDAO.getFareInstrument();
			BcwtsLogger.info(MY_NAME + " got Manage Partner Registration Status Details:");					
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting Manage Partner" +
				" Registration Status details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
				.getValue(MartaConstants.BCWTS_PARTNER_REGISTRATION_LIST_FIND));
		}
		
		return fareInstrumentList;
	}*/
	
}