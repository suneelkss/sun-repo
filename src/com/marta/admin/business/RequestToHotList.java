package com.marta.admin.business;

import java.util.List;
import org.apache.struts.actions.DispatchAction;
import com.marta.admin.dao.RequestToHotListDAO;
import com.marta.admin.dto.BcwtRequestToHotListDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;

/**
 * 
 * 
 * @author Jagadeesan
 *
 */
public class RequestToHotList extends DispatchAction{
	
	final String ME = "RequestToHotlListAction: ";
	/**
	 * To get all hotlist card details
	 * 
	 * @param bcwtRequestToHotListDTO
	 * @return
	 * @throws MartaException
	 */
	public List getRequestToHotList(BcwtRequestToHotListDTO bcwtRequestToHotListDTO) throws MartaException{
		
		final String MY_NAME = ME + " getRequestToHotList: ";
		BcwtsLogger.debug(MY_NAME + " getting hot list information ");
		List requestToHotList = null;
		
		try {
			RequestToHotListDAO requestToHotListDAO = new RequestToHotListDAO();
			requestToHotList = requestToHotListDAO.getRequestToHotList();
			BcwtsLogger.info(MY_NAME + " got user List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting hot list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.HOT_LIST_CARD_DETAILS_LIST));
		}
		return requestToHotList;	
	}
	/**
	 * To get recently hotlist card list
	 * 
	 * @return
	 * @throws MartaException
	 */
	public List getRecentlyHotlistCardList() throws MartaException{
		
		final String MY_NAME = ME + " getRecentlyHotlistCardList: ";
		BcwtsLogger.debug(MY_NAME + " getting recently hotlist card list information ");
		List recentlyHotListCardList = null;
		
		try {
			RequestToHotListDAO requestToHotListDAO = new RequestToHotListDAO();
			recentlyHotListCardList = requestToHotListDAO.getRecentlyHotListCardList();
			BcwtsLogger.info(MY_NAME + " got hotlist card List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting hot list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.HOT_LIST_CARD_DETAILS_LIST));
		}
		return recentlyHotListCardList;	
	}
	
	/**
	 * To get partner details from Next_Fare DB
	 * @param serialNumber
	 * @return
	 * @throws MartaException
	 */
	public BcwtRequestToHotListDTO getHotListDetailsForPS(String serialNumber)throws MartaException{
		
		final String MY_NAME = ME + " getHotListDetailsForPS: ";
		BcwtsLogger.debug(MY_NAME + " to get hot list details for PS ");
		RequestToHotListDAO requestToHotListDAO = null;
		BcwtRequestToHotListDTO bcwtRequestToHotListDTO = null;

		try {
			requestToHotListDAO = new RequestToHotListDAO();
			bcwtRequestToHotListDTO = requestToHotListDAO.getHotListDetailsForPS(serialNumber);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting hot list details for PS:"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.HOT_LIST_DETAILS_FOR_PS));
		}
		return bcwtRequestToHotListDTO;
	}
	
	
	/**
	 * To get partner details from Next_Fare DB
	 * @param serialNumber
	 * @return
	 * @throws MartaException
	 */
	public BcwtRequestToHotListDTO getHotListDetailsForIS(String serialNumber)throws MartaException{
		
		final String MY_NAME = ME + " getHotListDetailsForIS: ";
		BcwtsLogger.debug(MY_NAME + " to get hot list details for IS ");
		RequestToHotListDAO requestToHotListDAO = null;
		BcwtRequestToHotListDTO bcwtRequestToHotListDTO = null;

		try {
			requestToHotListDAO = new RequestToHotListDAO();
			bcwtRequestToHotListDTO = requestToHotListDAO.getHotListDetailsForOthers(serialNumber);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting hot list details for IS :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.HOT_LIST_DETAILS_FOR_IS));
		}
		return bcwtRequestToHotListDTO;
	}
	
	
	/**
	 * To get partner details from Next_Fare DB
	 * @param serialNumber
	 * @return
	 * @throws MartaException
	 */
	public BcwtRequestToHotListDTO getHotListDetailsForGBS(String serialNumber)throws MartaException{
		
		final String MY_NAME = ME + " getHotListDetailsForGBS: ";
		BcwtsLogger.debug(MY_NAME + " to get hot list details for GBS ");
		RequestToHotListDAO requestToHotListDAO = null;
		BcwtRequestToHotListDTO bcwtRequestToHotListDTO = null;
		
		try {
			requestToHotListDAO = new RequestToHotListDAO();
			bcwtRequestToHotListDTO = requestToHotListDAO.getHotListDetailsForOthers(serialNumber);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in adding a patron user details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.HOT_LIST_DETAILS_FOR_GBS));
		}
		return bcwtRequestToHotListDTO;
	}
	/**
	 * To update hotlist card details into DB
	 * 
	 * @param bcwtRequestToHotListDTO
	 * @return
	 * @throws MartaException
	 */
	public boolean updateHotListCardDetails(BcwtRequestToHotListDTO bcwtRequestToHotListDTO)
			throws MartaException{
		
		final String MY_NAME = ME + " updateHotListCardDetails: ";
		BcwtsLogger.debug(MY_NAME + " to update hot list card detail ");
		RequestToHotListDAO requestToHotListDAO = null;
		boolean isUpdate  = false;

		try {
			requestToHotListDAO = new RequestToHotListDAO();
		
			 isUpdate = requestToHotListDAO.updateHotListDetails(bcwtRequestToHotListDTO);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in update hot list card details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.UPDATE_HOT_LIST_CARD_DETAILS));
		}
		return isUpdate;
	}
}
