package com.marta.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.DeactivatePartnerAccountDTO;
import com.marta.admin.forms.DeactivatePartnerAccountForm;
import com.marta.admin.hibernate.PartnerAdminDetails;
import com.marta.admin.hibernate.PartnerCompanyInfo;
import com.marta.admin.hibernate.PartnerCompanyStatus;
import com.marta.admin.hibernate.TmaInformation;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;

public class DeactivatePartnerAccountDAO extends MartaBaseDAO {

	final String ME = "DeactivatePartnerAccountDAO :: ";
	
	/**
	 * To get Partner Details
	 * 
	 * @param deactivatePartnerAccountForm
	 * @return partnerDetailsList
	 * @throws Exception
	 */
	public List getPartnerDetails(DeactivatePartnerAccountForm deactivatePartnerAccountForm)
	throws Exception {
		final String MY_NAME = ME + "getPartnerDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List partnerDetailsList = new ArrayList();		
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			 
			String query =
					"select" +
						" partnerCompanyInfo.companyId," +
						" partnerCompanyInfo.companyName," +
						" partnerCompanyInfo.partnerCompanyType.companyType," +
						" partnerCompanyInfo.partnerCompanyStatus.companyStatus" +
					" from" +
						" PartnerCompanyInfo partnerCompanyInfo" +
					" where" +
						" partnerCompanyInfo.partnerCompanyStatus.companyStatusId = " + Constants.COMPANY_STATUS_ACTIVE;
						
						
			if(!Util.isBlankOrNull(deactivatePartnerAccountForm.getCompanyId())){
				query = query + " and partnerCompanyInfo.companyId = " + deactivatePartnerAccountForm.getCompanyId();
			}
			if(!Util.isBlankOrNull(deactivatePartnerAccountForm.getCompanyName())){
				query = query + " and upper(partnerCompanyInfo.companyName) like '%" + deactivatePartnerAccountForm.getCompanyName().toUpperCase() + "%'";
			}			
			if(!Util.isBlankOrNull(deactivatePartnerAccountForm.getCompanyType())){
				query = query + " and partnerCompanyInfo.partnerCompanyType.companyTypeId = " + deactivatePartnerAccountForm.getCompanyType();
			}
			if(!Util.isBlankOrNull(deactivatePartnerAccountForm.getCompanyStatus())){
				query = query + " and partnerCompanyInfo.partnerCompanyStatus.companyStatusId = " + deactivatePartnerAccountForm.getCompanyStatus();
			}
			
			List queryList= session.createQuery(query).list();
			
			if(queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					DeactivatePartnerAccountDTO deactivatePartnerAccountDTO = new DeactivatePartnerAccountDTO();
					if(element[0] != null) {
						deactivatePartnerAccountDTO.setCompanyId(element[0].toString());
					}
					if(element[1] != null) {
						deactivatePartnerAccountDTO.setCompanyName(element[1].toString());
					}
					if(element[2] != null) {
						deactivatePartnerAccountDTO.setCompanyType(element[2].toString());
					}
					if(element[3] != null) {
						deactivatePartnerAccountDTO.setCompanyStatus(element[3].toString());
					}
					
					partnerDetailsList.add(deactivatePartnerAccountDTO);
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got partnerDetails List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return partnerDetailsList;
	}
	
	/**
	 * To get TMA Partner Details
	 * 
	 * @param deactivatePartnerAccountForm
	 * @return tmaPartnerDetailsList
	 * @throws Exception
	 */
	public List getTMAPartnerDetails(DeactivatePartnerAccountForm deactivatePartnerAccountForm)
	throws Exception {
		final String MY_NAME = ME + "getTMAPartnerDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List tmaPartnerDetailsList = new ArrayList();		
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			 
			String query =
					"select" +
						" tmaInformation.tmaId," +
						" tmaInformation.tmaName," +
						" tmaInformation.tmaUsername," +
						" tmaInformation.tmaEmail" +
					" from" +
						" TmaInformation tmaInformation" +
					" where" +
						" tmaInformation.tmaPasswordStatus = '" + Constants.TMA_PASSWORD_STATUS_ACTIVE + "'";
						
						
			if(!Util.isBlankOrNull(deactivatePartnerAccountForm.getTmaId())){
				query = query + " and tmaInformation.tmaId = " + deactivatePartnerAccountForm.getTmaId();
			}
			if(!Util.isBlankOrNull(deactivatePartnerAccountForm.getTmaName())){
				query = query + " and upper(tmaInformation.tmaName) like '%" + deactivatePartnerAccountForm.getTmaName().toUpperCase() + "%'";
			}			
			if(!Util.isBlankOrNull(deactivatePartnerAccountForm.getTmaUserName())){
				query = query + " and upper(tmaInformation.tmaUsername) like '%" + deactivatePartnerAccountForm.getTmaUserName().toUpperCase() + "%'";
			}
			if(!Util.isBlankOrNull(deactivatePartnerAccountForm.getTmaEmail())){
				query = query + " and upper(tmaInformation.tmaEmail) like '%" + deactivatePartnerAccountForm.getTmaEmail().toUpperCase() + "%'";
			}
			
			List queryList= session.createQuery(query).list();
			
			if(queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					DeactivatePartnerAccountDTO deactivatePartnerAccountDTO = new DeactivatePartnerAccountDTO();
					if(element[0] != null) {
						deactivatePartnerAccountDTO.setTmaId(element[0].toString());
					}
					if(element[1] != null) {
						deactivatePartnerAccountDTO.setTmaName(element[1].toString());
					}
					if(element[2] != null) {
						deactivatePartnerAccountDTO.setTmaUserName(element[2].toString());
					}
					if(element[3] != null) {
						deactivatePartnerAccountDTO.setTmaEmail(element[3].toString());
					}
					
					tmaPartnerDetailsList.add(deactivatePartnerAccountDTO);
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got TMA partnerDetails List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return tmaPartnerDetailsList;
	}
	
	/**
	 * To get card serial number list by company id
	 * 
	 * @param companyId
	 * @return cardSerialNumberList
	 * @throws Exception
	 */
	public List getCardSerialNumber(String companyId) throws Exception {
		final String MY_NAME = ME + "getCardSerialNumber: ";
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		List cardSerialNumberList = new ArrayList();
		
		try {			
			String query = "select" +
								" SERIAL_NBR" +
						   " from" +
						   		" nextfare_main.MEMBER" +
						   " where" +
						   		" CUSTOMER_ID = " + companyId;
			
			nextFareConnection = NextFareConnection.getConnection();
			
			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}
			
			while(rs.next()){				
				String serialNumber = rs.getString("SERIAL_NBR");				
				cardSerialNumberList.add(serialNumber);
			}
			BcwtsLogger.info(MY_NAME + "got cardSerialNumber List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return cardSerialNumberList;
	}
	
	/**
	 * To get PartnerAdminDetails.
	 * @param companyId
	 * @return partnerAdminDetailsList
	 * @throws Exception
	 */
	public List getPartnerAdminDetails(String companyId) throws Exception {
		final String MY_NAME = ME + "getTMAPartnerDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List partnerAdminDetailsList = new ArrayList();		
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			 
			String query = "from PartnerAdminDetails partnerAdminDetails " +
								"where partnerAdminDetails.partnerCompanyInfo.companyId = " + companyId;
			
			List queryList= session.createQuery(query).list();
			
			if(queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					PartnerAdminDetails partnerAdminDetails = (PartnerAdminDetails) iter.next();
					partnerAdminDetailsList.add(partnerAdminDetails);
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got partnerAdminDetails List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return partnerAdminDetailsList;
	}
		
	/**
	 * To Update StatusFlag in Partner_AdminDetails Table.
	 * 
	 * @param partnerId
	 * @return isUpdated
	 * @throws Exception
	 */
	public boolean updateStatusFlag(String partnerId, String status) throws Exception {
		final String MY_NAME = ME + "updateStatusFlag: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		
		try {		
			session = getSession();
			transaction = session.beginTransaction();
			
			PartnerAdminDetails partnerAdminDetails = 
				(PartnerAdminDetails) session.load(PartnerAdminDetails.class, Long.valueOf(partnerId));
			
			if(partnerAdminDetails != null && !Util.isBlankOrNull(status)) {
				partnerAdminDetails.setStatusFlag(status);
				session.update(partnerAdminDetails);
				transaction.commit();
				isUpdated = true;
			} else {
				transaction.rollback();
			}		
			
			session.flush();
			session.close();			
			BcwtsLogger.info(MY_NAME + "isUpdated= " + isUpdated);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}
	
	/**
	 * To Update Company Status
	 * 
	 * @param companyId
	 * @param status
	 * @return isUpdated
	 * @throws Exception
	 */
	public boolean updateCompanyStatus(String companyId, String status) throws Exception {
		final String MY_NAME = ME + "updateCompanyStatus: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		
		try {		
			session = getSession();
			transaction = session.beginTransaction();
			
			PartnerCompanyInfo partnerCompanyInfo = 
				(PartnerCompanyInfo) session.load(PartnerCompanyInfo.class, Long.valueOf(companyId));
			
			if(partnerCompanyInfo != null && !Util.isBlankOrNull(status)) {
				PartnerCompanyStatus partnerCompanyStatus = new PartnerCompanyStatus();
				partnerCompanyStatus.setCompanyStatusId(Long.valueOf(status));				
				partnerCompanyInfo.setPartnerCompanyStatus(partnerCompanyStatus);
				
				session.update(partnerCompanyInfo);
				transaction.commit();
				isUpdated = true;
			} else {
				transaction.rollback();
			}		
			
			session.flush();
			session.close();			
			BcwtsLogger.info(MY_NAME + "isUpdated= " + isUpdated);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}
	
	/**
	 * To get company type
	 * 
	 * @return companyTypeList
	 * @throws Exception
	 */
	public List getCompanyTypeAsLabelValueBean() throws Exception {
		final String MY_NAME = ME + "getCompanyTypeAsLabelValueBean: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List companyTypeList = new ArrayList();		
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			 
			String query =
					"select" +
						" partnerCompanyType.companyTypeId," +
						" partnerCompanyType.companyType" +						
					" from" +
						" PartnerCompanyType partnerCompanyType" +
					" order by partnerCompanyType.companyTypeId";
						
			List queryList= session.createQuery(query).list();
			
			if(queryList != null && !queryList.isEmpty()){
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					if(element[0] != null && element[1] != null) {
						companyTypeList.add(new LabelValueBean(element[1].toString(), element[0].toString()));
					}					
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got companyType List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return companyTypeList;
	}
	
	/**
	 * To get company Id list by using tmaId
	 * 
	 * @param tmaId
	 * @return companyIdList
	 * @throws Exception
	 */
	public List getCompanyId(String tmaId) throws Exception {
		final String MY_NAME = ME + "getCompanyId: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List companyIdList = new ArrayList();		
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			 
			String query = "select" +
								" tmaCompanyLink.id.companyId" +
							" from" +
								" TmaCompanyLink tmaCompanyLink" +
							" where tmaCompanyLink.id.tmaId = " + tmaId;
			
			List queryList= session.createQuery(query).list();
			
			if(queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object companyId = (Object) iter.next();
					if(companyId != null) {
						companyIdList.add(companyId.toString());
					}
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got companyId List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return companyIdList;
	}

	/**
	 * To Update TMA Password Status
	 * 
	 * @param tmaId
	 * @param status
	 * @return isUpdated
	 * @throws Exception
	 */
	public boolean updateTmaPasswordStatus(String tmaId, String status) throws Exception {
		final String MY_NAME = ME + "updateTmaPasswordStatus: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		
		try {		
			session = getSession();
			transaction = session.beginTransaction();
			
			TmaInformation tmaInformation = 
				(TmaInformation) session.load(TmaInformation.class, Long.valueOf(tmaId));
			
			if(tmaInformation != null && !Util.isBlankOrNull(status)) {								
				tmaInformation.setTmaPasswordStatus(status);
				
				session.update(tmaInformation);
				transaction.commit();
				isUpdated = true;
			} else {
				transaction.rollback();
			}		
			
			session.flush();
			session.close();			
			BcwtsLogger.info(MY_NAME + "isUpdated= " + isUpdated);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}
	
}