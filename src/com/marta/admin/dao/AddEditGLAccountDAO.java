package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.AddEditGLAccountDTO;
import com.marta.admin.hibernate.BcwtProductAccntNums;
import com.marta.admin.hibernate.BcwtProductAccntNumsId;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Util;

/**
 * 
 * @author Administrator
 *
 */
public class AddEditGLAccountDAO extends MartaBaseDAO{

	final String ME = "AddEditGLAccountDAO: ";

	/**
	 * To get GL Account list
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getAddEditGLAccountList()
			throws Exception {
			
				final String MY_NAME = ME + "getAddEditGLAccountList: ";
				BcwtsLogger.debug(MY_NAME);
				
				Session session = null;
				Transaction transaction = null;
				
				List addEditGLAccountParameters = new ArrayList();
				List addEditGLAccountList = new ArrayList();
				AddEditGLAccountDTO addEditGLAccountDTO = null;
			
			try {
				BcwtsLogger.debug(MY_NAME + "Get Session");
				session = getSession();
				BcwtsLogger.debug(MY_NAME + " Got Session");
				transaction = session.beginTransaction();
				BcwtsLogger.debug(MY_NAME + " Execute Query");

				Query query = session.createSQLQuery("select * from BCWT_PRODUCT_ACCNT_NUMS")
				.addScalar("BCWTS_PRODUCT_ACCNT_NUMS_ID", Hibernate.LONG)
				.addScalar("FARE_INSTRUMENT_ID", Hibernate.LONG)
				.addScalar("PRODUCTNAME", Hibernate.STRING)
				.addScalar("CHART_OF_ACCOUNTS_ID", Hibernate.LONG)
				.addScalar("CODE_COMBINATION_ID", Hibernate.LONG)
				.addScalar("ACCOUNT_NUM", Hibernate.LONG);
				
				addEditGLAccountParameters = query.list();
				
			for (Iterator it = addEditGLAccountParameters.iterator(); it.hasNext();){
				Object[] element = (Object[])it.next();
				addEditGLAccountDTO = new AddEditGLAccountDTO();
				if(element[0] != null ){
					addEditGLAccountDTO.setProductAccountNumberId(element[0].toString());
				}
				if(element[1] != null ){
					addEditGLAccountDTO.setFareInstrumentId(element[1].toString());
				}
				if(element[2] != null ){
					addEditGLAccountDTO.setProductName(element[2].toString());
				}
				if(element[3] != null ){
					addEditGLAccountDTO.setChartOfAccountsId(element[3].toString());
				}
				if(element[4] != null ){
					addEditGLAccountDTO.setCodeCombinationId(element[4].toString());
				}
				if(element[5] != null ){					
					addEditGLAccountDTO.setSalesAccount(element[5].toString());
				}
				addEditGLAccountList.add(addEditGLAccountDTO);
			}
			transaction.commit();
			session.flush();			
			BcwtsLogger.info(MY_NAME
					+ "Got add edit GL Account list and whose size is "
						+ addEditGLAccountParameters.size());
			} catch (Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;
			} finally {
				closeSession(session, transaction);
			}
			return addEditGLAccountList;
	}
	/**
	 * To update GL Account Details for product account number id
	 * 
	 * @param addEditGLAccountDTO
	 * @return
	 * @throws Exception
	 */
	public boolean updateAddEditGLAccountDetails
				(AddEditGLAccountDTO addEditGLAccountDTO) throws Exception {
	
		final String MY_NAME = ME + " updateAddEditGLAccountDetails: ";
		BcwtsLogger.debug(MY_NAME + " updating add edit GL Account details ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
	try {
		session = getSession();
		transaction = session.beginTransaction();

		BcwtProductAccntNums bcwtProductAccntNums = new BcwtProductAccntNums();
		
		String que = "from BcwtProductAccntNums bcwtproductaccntnums where " +
				"bcwtproductaccntnums.id.bcwtsProductAccntNumsId = "
				+Long.valueOf(addEditGLAccountDTO.getEditProductAccountNumberId());

		bcwtProductAccntNums = (BcwtProductAccntNums) session.createQuery(que).uniqueResult();
		
		if(!Util.isBlankOrNull(addEditGLAccountDTO.getEditChartOfAccountsId())){
			bcwtProductAccntNums.setChartOfAccountsId(new Long(addEditGLAccountDTO.getEditChartOfAccountsId()));
		}
		if(!Util.isBlankOrNull(addEditGLAccountDTO.getEditCodeCombinationId())){
			bcwtProductAccntNums.setCodeCombinationId(new Long(addEditGLAccountDTO.getEditCodeCombinationId()));
		}
		session.update(bcwtProductAccntNums);

		isUpdated = true;
		transaction.commit();
		session.flush();
		BcwtsLogger.info(MY_NAME + "add edit GL Account details updated to database ");
	} catch (Exception e) {
		BcwtsLogger.error(MY_NAME
				+ " Exception in updating add edit GL Account details :"
				+ e.getMessage());
		throw e;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	return isUpdated;
}
	/**
	 * To get GL Account details for productAccountNumberId
	 * 
	 * @param productAccountNumberId
	 * @return
	 * @throws Exception
	 */
	public AddEditGLAccountDTO getAddEditGLAccountDetails(Long productAccountNumberId)throws Exception{
		
		final String MY_NAME = ME + " getAddEditGLAccountDetails: ";
		BcwtsLogger.debug(MY_NAME + " to get add edit GL Account details list ");

		Transaction transaction = null;
		Session session = null;
		AddEditGLAccountDTO addEditGLAccountDTO = null ;

		List aList = new ArrayList();
		try {
			addEditGLAccountDTO = new AddEditGLAccountDTO();
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			
			Query query = session.createSQLQuery("select * from BCWT_PRODUCT_ACCNT_NUMS where BCWTS_PRODUCT_ACCNT_NUMS_ID ="+productAccountNumberId)
			.addScalar("BCWTS_PRODUCT_ACCNT_NUMS_ID", Hibernate.LONG)
			.addScalar("FARE_INSTRUMENT_ID", Hibernate.LONG)
			.addScalar("PRODUCTNAME", Hibernate.STRING)
			.addScalar("CHART_OF_ACCOUNTS_ID", Hibernate.LONG)
			.addScalar("CODE_COMBINATION_ID", Hibernate.LONG)
			.addScalar("ACCOUNT_NUM", Hibernate.LONG);
			
			aList = query.list();
			
			for (Iterator it = aList.iterator(); it.hasNext();){
				Object[] element = (Object[])it.next();
				addEditGLAccountDTO = new AddEditGLAccountDTO();
				if(element[0] != null ){
					addEditGLAccountDTO.setEditProductAccountNumberId(element[0].toString());
				}
				if(element[1] != null ){
					addEditGLAccountDTO.setEditFareInstrumentId(element[1].toString());
				}
				if(element[2] != null ){
					addEditGLAccountDTO.setEditProductName(element[2].toString());
				}
				if(element[3] != null ){
					addEditGLAccountDTO.setEditChartOfAccountsId(element[3].toString());
				}
				if(element[4] != null ){
					addEditGLAccountDTO.setEditCodeCombinationId(element[4].toString());
				}
				if(element[5] != null ){
					addEditGLAccountDTO.setEditSalesAccount(element[5].toString());
				}
			}
			transaction.commit();
			session.flush();
			session.close();
		}
		catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		return addEditGLAccountDTO;
	}
	
	/**
	 * To add GL Account details
	 * 
	 * @param addEditGLAccountDTO
	 * @return
	 * @throws Exception
	 */
	public boolean addAccountDetail(AddEditGLAccountDTO addEditGLAccountDTO)throws Exception{
		
			final String MY_NAME = ME + " addAccountDetail: ";
			BcwtsLogger.debug(MY_NAME + " to add GL Account details ");
	
			Transaction transaction = null;
			Session session = null;
			boolean isAdded = false; 

		try {
			
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			
			BcwtProductAccntNums bcwtProductAccntNums = new BcwtProductAccntNums();
			BcwtProductAccntNumsId bcwtProductAccntNumsId = new BcwtProductAccntNumsId();
			
			if(addEditGLAccountDTO.getFareInstrumentId() != null ){
				bcwtProductAccntNumsId.setFareInstrumentId(new Long(addEditGLAccountDTO.getFareInstrumentId()));
			}
			if(addEditGLAccountDTO.getProductName() != null ){
				bcwtProductAccntNumsId.setProductname(addEditGLAccountDTO.getProductName());
			}
			if(addEditGLAccountDTO.getChartOfAccountsId() != null ){
				bcwtProductAccntNums.setChartOfAccountsId(new Long
						(addEditGLAccountDTO.getChartOfAccountsId()));
			}
			if(addEditGLAccountDTO.getCodeCombinationId() != null ){
				bcwtProductAccntNums.setCodeCombinationId(new Long(addEditGLAccountDTO.getCodeCombinationId()));
			}
			if(addEditGLAccountDTO.getSalesAccount() != null ){
				bcwtProductAccntNums.setSalesAccnt(new Long(addEditGLAccountDTO.getSalesAccount()));
			}
			 bcwtProductAccntNums.setId(bcwtProductAccntNumsId);
			
			session.save(bcwtProductAccntNums);
			isAdded = true;
			transaction.commit();
			session.flush();
			session.close();
		}
		catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		return isAdded;
	}
}
