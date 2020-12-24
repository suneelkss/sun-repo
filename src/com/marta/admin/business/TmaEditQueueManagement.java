package com.marta.admin.business;

import java.util.List;

import com.marta.admin.dao.TmaEditQueueDAO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;

public class TmaEditQueueManagement {

	final String ME = "TmaEditQueueManagement: ";
	TmaEditQueueDAO tmaEditQueueDAO = new TmaEditQueueDAO();
	
	/**
	 * method to get new card details
	 * @return
	 * @throws MartaException
	 */
	public List getNewCardDetails() throws MartaException {
		final String MY_NAME = ME + "getNewCardDetails():";
		BcwtsLogger.debug(MY_NAME);
		List newCardDetailsList = null;
				
		try {			
			newCardDetailsList = tmaEditQueueDAO.getNewCardDetails();
			BcwtsLogger.info(MY_NAME + " List Card size is : " + newCardDetailsList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while fetching bcwtpartnernewcard Table :" + e.getMessage());
			throw new MartaException("Unable to fetching bcwtpartnernewcard Table");
		}	
		return newCardDetailsList;
	}	
	
	
	/**
	 * method to get hotlist card details
	 * @return
	 * @throws MartaException
	 */
	public List getHotlistCardDetails() throws MartaException {
		final String MY_NAME = ME + "getNewCardDetails():";
		BcwtsLogger.debug(MY_NAME);
		List hotlistCardDetailsList = null;
				
		try {			
			hotlistCardDetailsList = tmaEditQueueDAO.getHotlistCardDetails();
			BcwtsLogger.info(MY_NAME + " List Card size is : " + hotlistCardDetailsList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while fetching bcwthotlistwcarddetails Table :" + e.getMessage());
			throw new MartaException("Unable to fetch partnerhotlisttable Table");
		}	
		return hotlistCardDetailsList;
	}	
	
	

	/**
	 * method to get Batch card details
	 * @return
	 * @throws MartaException
	 */
	public List getBatchDetails() throws MartaException {
		final String MY_NAME = ME + "getBatchDetails():";
		BcwtsLogger.debug(MY_NAME);
		List partnerBatchDetailsList = null;
				
		try {			
			partnerBatchDetailsList = tmaEditQueueDAO.getBatchDetails();
			BcwtsLogger.info(MY_NAME + " List Card size is : " + partnerBatchDetailsList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while fetching bcwtbatchdetails Table :" + e.getMessage());
			throw new MartaException("Unable to fetch bcwtbatchdetails Table");
		}	
		return partnerBatchDetailsList;
	}	
	
	
	
	/**
	 * method to remove hotlist card
	 * @param id
	 * @return
	 * @throws MartaException
	 */
	public boolean removeHotlistCard(Long id)throws MartaException{
		final String MY_NAME = ME + "removeHotlistCard():";
		BcwtsLogger.debug(MY_NAME);
		boolean isDeleted = false;
		
		try {
			isDeleted = tmaEditQueueDAO.removeHotlistCard(id);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while deleting bcwthotlistwcarddetails Table :"
						+ e.getMessage());
		}
		return isDeleted;
	}
	
	/**
	 * method to remove new card
	 * @param id
	 * @return
	 * @throws MartaException
	 */
	public boolean removeNewCard(Long id)throws MartaException{
		final String MY_NAME = ME + "removeNewCard():";
		BcwtsLogger.debug(MY_NAME);
		boolean isDeleted = false;
		try {
			isDeleted = tmaEditQueueDAO.removeNewCard(id);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while fetching bcwtpartnernewcard Table :"
					+ e.getMessage());
		}
		return isDeleted; 
	}
	/**
	 * method to remove batch card
	 * @param id
	 * @return
	 * @throws MartaException
	 */
	public boolean removeBatchCard(Long id, String action)throws MartaException{
		final String MY_NAME = ME + "removeBatchCard():";
		BcwtsLogger.debug(MY_NAME);
		boolean isDeleted = false;
		try {
			isDeleted = tmaEditQueueDAO.removeBatchCard(id,action);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception while fetching partnerbatchdetails Table :"
					+ e.getMessage());
		}
		return isDeleted; 
	}
}