package com.marta.admin.business;

import java.util.List;
import org.apache.struts.actions.DispatchAction;
import com.marta.admin.dao.AddEditGLAccountDAO;
import com.marta.admin.dto.AddEditGLAccountDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.PropertyReader;

/**
 * 
 * @author Administrator
 *
 */
public class AddEditGLAccount extends DispatchAction{

	final String ME = "AddEditGLAccountAction: ";
	
	/**
	 * Method to get GL Account list
	 * 
	 * @return
	 * @throws MartaException
	 */
	public List getAddEditGLAccountList() throws MartaException{
		
			final String MY_NAME = ME + " getAddEditGLAccountList: ";
			BcwtsLogger.debug(MY_NAME + " getting add edit GL Account list ");
			List addEditGLAccountList = null ;
		try {
			AddEditGLAccountDAO addEditGLAccountDAO = new AddEditGLAccountDAO();
			addEditGLAccountList = addEditGLAccountDAO.getAddEditGLAccountList();
			BcwtsLogger.info(MY_NAME + " got add edit GL Account List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting GL Account list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(Constants.UNABLE_TO_GET_GL_ACCOUNT_LIST));
		}
		return addEditGLAccountList;	
	}
	
	/**
	 * Method to update GL Account 
	 * 
	 * @param addEditGLAccountDTO
	 * @return
	 * @throws MartaException
	 */
	public boolean updateAddEditGLAccountDetails(AddEditGLAccountDTO addEditGLAccountDTO)throws MartaException{
		
			final String MY_NAME = ME + "updateAddEditGLAccountDetails: ";
			BcwtsLogger.debug(MY_NAME + " to update add edit GL Account detail");
			AddEditGLAccountDAO addEditGLAccountDAO = null;
			boolean isUpdate = false;
		try{
			addEditGLAccountDAO = new AddEditGLAccountDAO();
			isUpdate = addEditGLAccountDAO.updateAddEditGLAccountDetails(addEditGLAccountDTO);
		}catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in updating GL Account details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader.getValue(Constants.ADD_EDIT_GL_ACCOUNT_UPDATE_FAILURE_MESSAGE));
		}
		return isUpdate;
	}
	
	/**
	 * Method to get GL Account details for productAccountNumberId
	 * 
	 * @param productAccountNumberId
	 * @return
	 * @throws MartaException
	 */
	public AddEditGLAccountDTO getAddEditGLAccountDetails(Long productAccountNumberId)throws MartaException{
		
			final String MY_NAME = ME + " getAddEditGLAccountDetails: ";
			BcwtsLogger.debug(MY_NAME + " to get add edit GL Account detail list ");
			AddEditGLAccountDAO addEditGLAccountDAO = null;
			AddEditGLAccountDTO addEditGLAccountDTO = null;
		try {
			addEditGLAccountDAO = new AddEditGLAccountDAO();
			addEditGLAccountDTO = addEditGLAccountDAO.getAddEditGLAccountDetails(productAccountNumberId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting the add edit GL Account detail :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(Constants.UNABLE_TO_GET_GL_ACCOUNT_LIST));
		}
		return addEditGLAccountDTO;
	}
	
	/**
	 * Method to add new GL Account details
	 * 
	 * @param addEditGLAccountDTO
	 * @return
	 * @throws MartaException
	 */
	public boolean addAccountDetail(AddEditGLAccountDTO addEditGLAccountDTO)throws MartaException{
		
			final String MY_NAME = ME + " addAccountDetail: ";
			BcwtsLogger.debug(MY_NAME + " to get add account detail list ");
			AddEditGLAccountDAO addEditGLAccountDAO = null;
			boolean isAdded ; 
		try {
			addEditGLAccountDAO = new AddEditGLAccountDAO();
			isAdded = addEditGLAccountDAO.addAccountDetail(addEditGLAccountDTO); 
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in adding GL Account detail :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(Constants.ADD_EDIT_GL_ACCOUNT_ADD_FAILURE_MESSAGE));
		}
		return isAdded;
	}
}
