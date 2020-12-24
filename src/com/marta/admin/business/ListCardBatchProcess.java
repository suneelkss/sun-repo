package com.marta.admin.business;

import java.util.Iterator;
import java.util.List;

import com.marta.admin.dao.ListCardsDAO;
import com.marta.admin.dto.ListCardDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;

public class ListCardBatchProcess {
		
		final String ME = "ListCardBatchProcess: ";
		ListCardsManagement listCardsManagement = new ListCardsManagement();
		ListCardsDAO listCardsDAO = new ListCardsDAO();
		
		/**
		 * To add new card
		 * 
		 * @return isSuccess
		 * @throws MartaException
		 */
		public boolean addNewCard() throws MartaException {
			final String MY_NAME = ME + "addNewCard():";
			boolean isSuccess = false;
			
			try {
				List newCardDetailsList = listCardsDAO.getNewCardDetails();
				
				for (Iterator iter = newCardDetailsList.iterator(); iter.hasNext();) {
					ListCardDTO listCardsDTO = (ListCardDTO) iter.next();
					
					isSuccess = NewNextfareMethods.addNewCard(listCardsDTO);
					
					if(isSuccess) {
						boolean isDeleted =  listCardsDAO.deleteNewCard(listCardsDTO.getSerialNumber());
						if(!isDeleted){
							BcwtsLogger.error(MY_NAME + "Card cannot be deleted, card no: " + listCardsDTO.getSerialNumber());
						}
					} else {
						BcwtsLogger.error(MY_NAME + "Card cannot be added, card no: " + listCardsDTO.getSerialNumber());
					}
				}
				isSuccess = true;
			} catch (Exception e) {
				isSuccess = false;	
				BcwtsLogger.error(MY_NAME + " Exception in adding new card :" + e.getMessage());
				throw new MartaException("Unable to add new card");
			}	
			return isSuccess;
		}

		/**
		 * To activate/deactivate benefit
		 * 
		 * @return isSuccess
		 * @throws MartaException
		 */
		public boolean activateDeactivateBenefit() throws MartaException {
			final String MY_NAME = ME + "activateDeactivateBenefit():";
			boolean isSuccess = false;
			
			try {
				List hotlistHistoryDetails = listCardsDAO.getHotlistHistoryDetails();
				
				for (Iterator iter = hotlistHistoryDetails.iterator(); iter.hasNext();) {
					ListCardDTO listCardsDTO  = (ListCardDTO) iter.next();
					if(listCardsDTO.getHotlistFlag().equalsIgnoreCase(Constants.YES) 
							&& listCardsDTO.getUnhotlistFlag().equalsIgnoreCase(Constants.NO)) {
						isSuccess = NewNextfareMethods.hotlistCards(listCardsDTO);
						
						if(isSuccess) {
							boolean isDeleted =  listCardsDAO.deleteHotlistHistory(listCardsDTO.getSerialNumber());
							if(!isDeleted){
								BcwtsLogger.error(MY_NAME + "Card cannot be deleted, card no: " + listCardsDTO.getSerialNumber());
							}
						} else {
							BcwtsLogger.error(MY_NAME + "Benefit cannot be deactivated for card no: " + listCardsDTO.getSerialNumber());
						}
					} else if(listCardsDTO.getHotlistFlag().equalsIgnoreCase(Constants.NO)
							&& listCardsDTO.getUnhotlistFlag().equalsIgnoreCase(Constants.YES)){
						String resp = NewNextfareMethods.removeHotlist(listCardsDTO.getSerialNumber());
						if(resp.equals("Unhotlisted"))
							   isSuccess = true;
						 else
							 isSuccess = false;
						if(isSuccess) {
							boolean isDeleted =  listCardsDAO.deleteHotlistHistory(listCardsDTO.getSerialNumber());
							if(!isDeleted){
								BcwtsLogger.error(MY_NAME + "Card cannot be deleted, card no: " + listCardsDTO.getSerialNumber());
							}
						} else {
							BcwtsLogger.error(MY_NAME + "Benefit cannot be activated for card no: " + listCardsDTO.getSerialNumber());
						}
					}
				}
				isSuccess = true;
			} catch (Exception e) {
				isSuccess = false;	
				BcwtsLogger.error(MY_NAME + " Exception while activating/deactivating benefit :" + e.getMessage());
				throw new MartaException("Unable to activate/deactivate benefit");
			}	
			return isSuccess;
		}
	}
