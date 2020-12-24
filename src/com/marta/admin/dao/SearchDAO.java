/**
 * 
 */
package com.marta.admin.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lowagie.text.Element;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.dto.BcwtUnlockAccountDTO;
import com.marta.admin.forms.SearchForm;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;


/**
 * @author
 * 
 */
public class SearchDAO extends MartaBaseDAO {

	final String ME = "SearchDAO: ";
	/**
	 * Method to get Partner Search 
	 * @param form 
	 * @return List
	 * @throws Exception
	 */
	public List getPartnerDetails(BcwtSearchDTO searchDTO, String patronId)
			throws Exception {

		final String MY_NAME = ME + "getPartnerSearchList: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List partnerInformation = null;
		BcwtSearchDTO bcwtSearchDTO = null;
		List searchList = null;
	

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			partnerInformation = new ArrayList();

			String query = "select partnerAdminDetails.partnerCompanyInfo.companyId, "
					+ "partnerAdminDetails.lastName, "
					+ "partnerAdminDetails.firstName,partnerCompanyInfo.companyName "
					+ "from PartnerCompanyInfo partnerCompanyInfo, PartnerAdminDetails partnerAdminDetails "
					+ "where partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId";

			if (null != searchDTO) {
				if (null != searchDTO.getCompanyName()) {
					query = query
							+ " and lower(partnerCompanyInfo.companyName)like '"
							+ searchDTO.getCompanyName().trim().toLowerCase()
							+ "%'";
				}
			
				if (null != searchDTO.getCompanyID()) {
					query = query
					+ " and lower(partnerAdminDetails.partnerCompanyInfo.companyId)like '"
					+ searchDTO.getCompanyID().trim().toLowerCase()
					+ "%'";
				}
				if (null != searchDTO.getPartnerFirstName()) {
					query = query
							+ " and lower(partnerAdminDetails.firstName)like '"
							+ searchDTO.getPartnerFirstName().trim()
									.toLowerCase() + "%'";
				}
				if (null != searchDTO.getPartnerLastName()) {
					query = query
							+ " and lower(partnerAdminDetails.lastName)like '"
							+ searchDTO.getPartnerLastName().trim()
									.toLowerCase() + "%'";
				}
			}
			searchList = session.createQuery(query).list();
			for (Iterator iter = searchList.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				if (element != null) {
					bcwtSearchDTO = new BcwtSearchDTO();
					bcwtSearchDTO.setCompanyID(element[0].toString());
					bcwtSearchDTO.setPartnerLastName(element[1].toString());
					bcwtSearchDTO.setPartnerFirstName(element[2].toString());
					bcwtSearchDTO.setCompanyName(element[3].toString());
					partnerInformation.add(bcwtSearchDTO);
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got Patron by email ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;

		} finally {
			closeSession(session, transaction);

		}
		return partnerInformation;
	}
	
	
	public List getpartnerMemberSearchDetails(BcwtSearchDTO searchDTO, String patronId)
	throws Exception {

											final String MY_NAME = ME + "getpartnerMemberSearchDetails: ";
											BcwtsLogger.debug(MY_NAME);
											Session session = null;
											Transaction transaction = null;
											List partnerMemberInfoList = null;
											List partnerMembersearchList = null;
											
											try {
												BcwtsLogger.debug(MY_NAME + "Get Session");
												session = getSession();
												BcwtsLogger.debug(MY_NAME + " Got Session");
												transaction = session.beginTransaction();
												BcwtsLogger.debug(MY_NAME + " Execute Query");
												
											String query = "select partnerCompanyInfo.companyName,"
													+ " partnerAdminDetails.firstName,partnerAdminDetails.lastName"
													+ " from PartnerCompanyInfo partnerCompanyInfo, PartnerAdminDetails partnerAdminDetails "
													+ " where partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId";

											if (null != searchDTO) {
												if (null != searchDTO.getCompanyName()) {
													query = query
															+ " and lower(partnerCompanyInfo.companyName)like '"
															+ searchDTO.getCompanyName().trim().toLowerCase()
															+ "%'";
												}
												
												if (null != searchDTO.getMemberFirstName()) {
													query = query
															+ " and lower(partnerAdminDetails.firstName)like '"
															+ searchDTO.getMemberFirstName().trim()
																	.toLowerCase() + "%'";
												}
												if (null != searchDTO.getMemberLastName()) {
													query = query
															+ " and lower(partnerAdminDetails.lastName)like '"
															+ searchDTO.getMemberLastName().trim()
																	.toLowerCase() + "%'";
												}
											}
												
											
												partnerMembersearchList = session.createQuery(query).list();
											
												partnerMemberInfoList = new ArrayList();
												if (partnerMembersearchList != null && !partnerMembersearchList.isEmpty()) {
													for (Iterator iter = partnerMembersearchList.iterator(); iter.hasNext();) {
														Object[] element = (Object[]) iter.next();
														if (element != null) {
															searchDTO = new BcwtSearchDTO();
															searchDTO.setCompanyName(element[0].toString());
															searchDTO.setMemberFirstName(element[1].toString());
															searchDTO.setMemberLastName(element[2].toString());
															partnerMemberInfoList.add(searchDTO);
														}
													}
												}
											
												transaction.commit();
												session.flush();
												session.close();
												BcwtsLogger.info(MY_NAME + "Got Patron by email ");
											} catch (Exception ex) {
												BcwtsLogger.error(MY_NAME + ex.getMessage());
												throw ex;
											
											} finally {
												closeSession(session, transaction);
											
											}
											return partnerMemberInfoList;
											}
	public List getGbsPatronDetails(BcwtSearchDTO searchDTO, String patronId)
			throws Exception {

		final String MY_NAME = ME + "getGbsPatronSearchList: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List gbsPatronInfoList = null;
		List gbssearchList = null;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			String hQuery = "select bcwtPatron.firstname, bcwtPatron.lastname"
					+ " from BcwtPatron bcwtPatron where bcwtPatron.patronid is not null and (bcwtPatron.bcwtpatrontype.patrontypeid = " +
					"'"+Constants.GBS_SUPER_ADMIN+"' or bcwtPatron.bcwtpatrontype.patrontypeid = " +
					"'"+Constants.GBS_READ_ONLY_TYPE+"' or bcwtPatron.bcwtpatrontype.patrontypeid = " +
					"'"+Constants.GBS_ADMIN+"')";

			if (null != searchDTO) 
			{
				
			if (null != searchDTO.getPatronFirstName()) {
				hQuery = hQuery
						+ " and lower(bcwtPatron.firstname)like '"
						+ searchDTO.getPatronFirstName().trim()
								.toLowerCase() + "%'";
			}
			if (null != searchDTO.getPatronLastName()) {
				hQuery = hQuery
						+ " and lower(bcwtPatron.lastname)like '"
						+ searchDTO.getPatronLastName().trim()
								.toLowerCase() + "%'";
			}
			
			}
			gbssearchList = session.createQuery(hQuery).list();

			gbsPatronInfoList = new ArrayList();
			if (gbssearchList != null && !gbssearchList.isEmpty()) {
				for (Iterator iter = gbssearchList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					if (element != null) {
						searchDTO = new BcwtSearchDTO();
						searchDTO.setPatronFirstName(element[0].toString());
						searchDTO.setPatronLastName(element[1].toString());
						gbsPatronInfoList.add(searchDTO);
					}
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got Patron by email ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;

		} finally {
			closeSession(session, transaction);

		}
		return gbsPatronInfoList;
	}

	/**
	 * To get order search details
	 * 
	 * @param searchDTO
	 * @param patronId
	 * @return
	 * @throws Exception
	 */
	 public List getOrderDetails(BcwtSearchDTO searchDTO,String patronId) throws Exception {

			final String MY_NAME = ME + "getOrderSearchList: ";
			BcwtsLogger.debug(MY_NAME);
			Session session = null;
			Transaction transaction = null;
			List orderInformation = null;
			BcwtSearchDTO bcwtSearchDTO = null;
			List searchList = null;
			try {
				BcwtsLogger.debug(MY_NAME + "Get Session");
				session = getSession();
				BcwtsLogger.debug(MY_NAME + " Got Session");
				transaction = session.beginTransaction();
				BcwtsLogger.debug(MY_NAME + " Execute Query");
				orderInformation  = new ArrayList();
						
			  String query= "select" +
								" order.orderid," +
								" order.orderType," +
								" patron.firstname," +
								" patron.lastname" +
							" from" +
								" BcwtOrder order," +
								" BcwtPatron patron " +
								" where order.bcwtpatron.patronid = patron.patronid ";
								
				if(searchDTO != null){
					if(searchDTO.getOrderType() == null){
						query = query + " and order.orderType like '" +Constants.ORDER_TYPE_IS+ "%'";
						searchList = session.createQuery(query).list();
						for (Iterator iter = searchList.iterator(); iter.hasNext();) {
							Object[] element = (Object[]) iter.next();
							if (element != null) {
								bcwtSearchDTO = new BcwtSearchDTO();
								if(element[0] != null){
									bcwtSearchDTO.setOrderNumber(element[0].toString());	
								}
								if(element[1] != null){
									bcwtSearchDTO.setOrderType(element[1].toString());
								}
								if(element[2] != null){
									bcwtSearchDTO.setMemberFirstName(element[2].toString());
								}
								if(element[3] != null){
									bcwtSearchDTO.setMemberLastName(element[3].toString());
								}
								orderInformation.add(bcwtSearchDTO);
							}
						}
					}
					if(!Util.isBlankOrNull(searchDTO.getOrderType())&& (searchDTO.getOrderType())
							.equalsIgnoreCase(Constants.ORDER_TYPE_IS)){
						if(!Util.isBlankOrNull(searchDTO.getOrderType())){
						    query = query + " and order.orderType like '" +searchDTO.getOrderType()+ "%'";
						}
						if(!Util.isBlankOrNull(searchDTO.getOrderNumber())){
						    query = query + " and order.orderid = " + searchDTO.getOrderNumber();
						}
						if(!Util.isBlankOrNull(searchDTO.getMemberFirstName())){
						    query = query + " and lower(patron.firstname) like '%" +searchDTO.getMemberFirstName()
						    					.trim().toLowerCase()+ "%'";
						}
						if(!Util.isBlankOrNull(searchDTO.getMemberLastName())){
						    query = query + " and lower(patron.lastname) like '%" +searchDTO.getMemberLastName()
						    					.trim().toLowerCase()+ "%'";
						}
						searchList = session.createQuery(query).list();
						for (Iterator iter = searchList.iterator(); iter.hasNext();) {
							Object[] element = (Object[]) iter.next();
							if (element != null) {
								bcwtSearchDTO = new BcwtSearchDTO();
								if(element[0] != null){
									bcwtSearchDTO.setOrderNumber(element[0].toString());	
								}
								if(element[1] != null){
									bcwtSearchDTO.setOrderType(element[1].toString());	
								}
								if(element[2] != null){
									bcwtSearchDTO.setMemberFirstName(element[2].toString());	
								}
								if(element[3] != null){
									bcwtSearchDTO.setMemberLastName(element[3].toString());	
								}
								orderInformation.add(bcwtSearchDTO);
							}
						}
					}
					if(!Util.isBlankOrNull(searchDTO.getOrderType())&& (searchDTO.getOrderType())
							.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)){
						if(!Util.isBlankOrNull(searchDTO.getOrderType())){
						    query = query + " and order.orderType like '" +searchDTO.getOrderType()+ "%'";
						}
						if(!Util.isBlankOrNull(searchDTO.getOrderNumber())){
						    query = query + " and order.orderid = " +searchDTO.getOrderNumber();
						}
						if(!Util.isBlankOrNull(searchDTO.getMemberFirstName())){
						    query = query + " and lower(patron.firstname) like '%" +searchDTO.getMemberFirstName()
						    					.trim().toLowerCase()+ "%'";
						}
						if(!Util.isBlankOrNull(searchDTO.getMemberLastName())){
						    query = query + " and lower(patron.lastname) like '%" +searchDTO.getMemberLastName()
						    					.trim().toLowerCase()+ "%'";
						}
						searchList = session.createQuery(query).list();
						
						
						for (Iterator iter = searchList.iterator(); iter.hasNext();) {
							Object[] element = (Object[]) iter.next();
							if (element != null) {
								bcwtSearchDTO = new BcwtSearchDTO();
								bcwtSearchDTO.setOrderNumber(element[0].toString());
								bcwtSearchDTO.setOrderType(element[1].toString());
								bcwtSearchDTO.setMemberFirstName(element[2].toString());
								bcwtSearchDTO.setMemberLastName(element[3].toString());
								
								orderInformation.add(bcwtSearchDTO);
							}
						}
					}
					if(!Util.isBlankOrNull(searchDTO.getOrderType()) && (searchDTO.getOrderType())
							.equalsIgnoreCase(Constants.ORDER_TYPE_PS)){
						query = "";
						query = 
							"select" +
								" partnerCardOrder.orderId," +
								" partnerCardOrder.partnerAdmindetails.firstName, " +
								" partnerCardOrder.partnerAdmindetails.lastName" +
							" from" +
								" PartnerCardOrder partnerCardOrder" +
							" where" +
								" partnerCardOrder.orderId is not null";
												
						if(!Util.isBlankOrNull(searchDTO.getOrderNumber())){
						    query = query + " and partnerCardOrder.orderId = " + searchDTO.getOrderNumber();
						}
						if(!Util.isBlankOrNull(searchDTO.getMemberFirstName())){
						    query = query + " and lower(partnerCardOrder.partnerAdmindetails.firstName) like '%" + 
						    						searchDTO.getMemberFirstName().trim().toLowerCase() + "%'";
						}
						if(!Util.isBlankOrNull(searchDTO.getMemberLastName())){
						    query = query + " and lower(partnerCardOrder.partnerAdmindetails.lastName) like '%" + 
						    						searchDTO.getMemberLastName().trim().toLowerCase() + "%'";
						}
						searchList = session.createQuery(query).list();
						
						for(Iterator it = searchList.iterator(); it.hasNext();){
							Object[] element = (Object[])it.next();
							if(element != null){
							bcwtSearchDTO = new BcwtSearchDTO();
							bcwtSearchDTO.setOrderNumber(element[0].toString());
							bcwtSearchDTO.setMemberFirstName(element[1].toString());
							bcwtSearchDTO.setMemberLastName(element[2].toString());
							bcwtSearchDTO.setOrderType(Constants.ORDER_TYPE_PS);
							
							orderInformation.add(bcwtSearchDTO);
							}
						}
					}
				}

				transaction.commit();
				session.flush();
				session.close();
				BcwtsLogger.info(MY_NAME + "Got Patron by email ");
				
			} catch (Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;

			} finally {
				closeSession(session, transaction);

			}
			return orderInformation;
		}
	 /**
		 * Method to get BreezeCard Search Deatails
		 * @param form 
		 * @return List
		 * @throws Exception
		 */
      public List getBreezeCardDetails(BcwtSearchDTO searchDTO,String patronId)
       throws Exception {
	   final String MY_NAME = ME + "getBreezeCardList: ";
	    BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	    List cardDetailsList = new ArrayList();
		
		try {			
			
			String query = " select" +
								" MEMBER_ID, FIRST_NAME, LAST_NAME, SERIAL_NBR, PHONE_ID, EMAIL, CUSTOMER_MEMBER_ID" +
						   " from" +
						   		" nextfare_main.MEMBER where nextfare_main.MEMBER.BENEFIT_STATUS_ID = 1";
						   		
		
			
			
			if(!Util.isBlankOrNull(searchDTO.getFirstName())){
				query = query + " and lower(FIRST_NAME) like '" + 
				searchDTO.getFirstName().trim().toLowerCase() + "%'";
			}
			if(!Util.isBlankOrNull(searchDTO.getLastName())){
				query = query + " and lower(LAST_NAME) like '" + 
				searchDTO.getLastName().trim().toLowerCase() + "%'";
			}
			
			if(!Util.isBlankOrNull(searchDTO.getMemberId())){
				query = query + " and lower(MEMBER_ID) like '" + 
				searchDTO.getMemberId().trim().toLowerCase() + "%'";
			}
			
			if(!Util.isBlankOrNull(searchDTO.getBreezeCardSerialNumber())){
				query = query + " and lower(SERIAL_NBR) like '" + 
				searchDTO.getBreezeCardSerialNumber().trim().toLowerCase() + "%'";
			}			
			
			
			nextFareConnection = NextFareConnection.getConnection();
			
			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}
		
			
			while(rs.next()){
				BcwtSearchDTO bcwtSearchDTO = new BcwtSearchDTO();
				bcwtSearchDTO.setMemberId(rs.getString("MEMBER_ID"));
				bcwtSearchDTO.setFirstName(rs.getString("FIRST_NAME"));
				bcwtSearchDTO.setLastName(rs.getString("LAST_NAME"));
				bcwtSearchDTO.setBreezeCardSerialNumber(rs.getString("SERIAL_NBR"));
				cardDetailsList.add(bcwtSearchDTO);
			}
			BcwtsLogger.info(MY_NAME + "got card details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return cardDetailsList;
	}
      public List displayPartnerSearchList(String patronId,String patronTypeId,String emailId)
  	throws Exception {

  final String MY_NAME = ME + "displayPartnerSearchList: ";
  BcwtsLogger.debug(MY_NAME);
  Session session = null;
  Transaction transaction = null;
  List partnerInformation1 = null;
  BcwtSearchDTO bcwtSearchDTO = null;
  List searchList = null;
  String companyId = "";

  try {
  	BcwtsLogger.debug(MY_NAME + "Get Session");
  	session = getSession();
  	BcwtsLogger.debug(MY_NAME + " Got Session");
  	transaction = session.beginTransaction();
  	BcwtsLogger.debug(MY_NAME + " Execute Query");
  	partnerInformation1 = new ArrayList();

  	String query = "select partnerAdminDetails.partnerCompanyInfo.companyId, "
  			+ "partnerAdminDetails.lastName, "
  			+ "partnerAdminDetails.firstName,partnerCompanyInfo.companyName "
  			+ "from PartnerCompanyInfo partnerCompanyInfo, PartnerAdminDetails partnerAdminDetails "
  			+ "where partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId";

  	if (null != bcwtSearchDTO) {
  		if (null != bcwtSearchDTO.getCompanyName()) {
  			query = query
  					+ " and lower(partnerCompanyInfo.companyName)like '"
  					+ bcwtSearchDTO.getCompanyName().trim().toLowerCase()
  					+ "%'";
  		}
  		if (null != bcwtSearchDTO.getCompanyID()) {
  			query = query + " and partnerCompanyInfo.companyId = "
  					+ companyId;
  		}
  		if (null != bcwtSearchDTO.getPartnerFirstName()) {
  			query = query
  					+ " and lower(partnerAdminDetails.firstName)like '"
  					+ bcwtSearchDTO.getPartnerFirstName().trim()
  							.toLowerCase() + "%'";
  		}
  		if (null != bcwtSearchDTO.getPartnerLastName()) {
  			query = query
  					+ " and lower(partnerAdminDetails.lastName)like '"
  					+ bcwtSearchDTO.getPartnerLastName().trim()
  							.toLowerCase() + "%'";
  		}
  	}
  	searchList = session.createQuery(query).list();
  	for (Iterator iter = searchList.iterator(); iter.hasNext();) {
  		Object[] element = (Object[]) iter.next();
  		if (element != null) {
  			bcwtSearchDTO = new BcwtSearchDTO();
  			bcwtSearchDTO.setCompanyID(element[0].toString());
  			bcwtSearchDTO.setPartnerLastName(element[1].toString());
  			bcwtSearchDTO.setPartnerFirstName(element[2].toString());
  			bcwtSearchDTO.setCompanyName(element[3].toString());
  			partnerInformation1.add(bcwtSearchDTO);
  		}
  	}

  	transaction.commit();
  	session.flush();
  	session.close();
  	BcwtsLogger.info(MY_NAME + "Got Patron by email ");
  } catch (Exception ex) {
  	BcwtsLogger.error(MY_NAME + ex.getMessage());
  	throw ex;

  } finally {
  	closeSession(session, transaction);

  }
  return partnerInformation1;
 }

	 
/*	 
public List getBreezeCardDetails(BcwtSearchDTO searchDTO, String patronId)
	throws Exception {

    final String MY_NAME = ME + "getBreezeCardList: ";
    BcwtsLogger.debug(MY_NAME);
    Session session = null;
    Transaction transaction = null;
    List partnerInformation = null;
    BcwtSearchDTO bcwtSearchDTO = null;
    List searchList = null;
   
    String breezeCardSerialNumber = searchDTO.getBreezeCardSerialNumber();
    

    try {
	BcwtsLogger.debug(MY_NAME + "Get Session");
	session = getSession();
	BcwtsLogger.debug(MY_NAME + " Got Session");
	transaction = session.beginTransaction();
	BcwtsLogger.debug(MY_NAME + " Execute Query");
	partnerInformation = new ArrayList();

	
	   

	
	String query = "select bcwtPatronBreezeCard.breezecardid,bcwtPatronBreezeCard.breezecardname," 
					+ " bcwtPatronBreezeCard.activestatus,"
			    	+ " bcwtPatronBreezeCard.breezecardserialnumber"
			    	+ " from nextfare_main.MEMBER bcwtPatronBreezeCard";
			    	//+ " where bcwtPatronBreezeCard.activestatus='1'";
			    	//+ " where bcwtPatronBreezeCard.breezecardserialnumber like '%0%'";
		    
       
	if (null != searchDTO) {
		if (null != searchDTO.getLastName()) {
			query = query
					+ " and lower(bcwtPatronBreezeCard.breezecardname)like '"
					+ searchDTO.getLastName().trim().toLowerCase() + "%'";
		}
		if (null != searchDTO.getFirstName()) {
			query = query
					+ " and lower(bcwtPatronBreezeCard.breezecardname)like '"
					+ searchDTO.getFirstName().trim().toLowerCase() + "%'";
		}
	
		if (null != searchDTO.getMemberId()) {
			query = query
					+ " and lower(bcwtPatronBreezeCard.activestatus)like '"
					+ searchDTO.getMemberId().trim().toLowerCase() + "%'";
		}
		if (null != searchDTO.getBreezeCardSerialNumber()) {
			query = query
					+ " and lower(bcwtPatronBreezeCard.breezecardserialnumber)like '"
					+ searchDTO.getBreezeCardSerialNumber().trim().toLowerCase() + "%'";
		}
		
		
	}	
	
	searchList = session.createQuery(query).list();
	
	for (Iterator iter = searchList.iterator(); iter.hasNext();) {
		Object[] element = (Object[]) iter.next();
		if (element != null) {
			bcwtSearchDTO = new BcwtSearchDTO();
			bcwtSearchDTO.setBreezeCardSerialNumber(element[3].toString());
			bcwtSearchDTO.setLastName(element[1].toString());
			bcwtSearchDTO.setFirstName(element[2].toString());
			bcwtSearchDTO.setMemberId(element[2].toString());
			partnerInformation.add(bcwtSearchDTO);
	}
	}

	transaction.commit();
	session.flush();
	session.close();
	BcwtsLogger.info(MY_NAME + "Got Patron by email ");
} catch (Exception ex) {
	BcwtsLogger.error(MY_NAME + ex.getMessage());
	throw ex;

} finally {
	closeSession(session, transaction);

}
return partnerInformation;

	
}*/
      /**
		 * Method to get AccountAdmin Search Deatails
		 * @param form 
		 * @return List
		 * @throws Exception
		 */
	public List getAccountAdminDetails(BcwtSearchDTO searchDTO,String patronId)
	throws Exception {
	    final String MY_NAME = ME + "getPartnerSearchList: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List partnerInformation = null;
		List searchList = null;
	
		
		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			

			String query = "select bcwtPatron.emailid, "
					+ " bcwtPatron.phonenumber, bcwtPatron.firstname"
					+ " from BcwtPatron bcwtPatron where bcwtPatron.patronid is not null and (bcwtPatron.bcwtpatrontype.patrontypeid = " +
					"'"+Constants.MARTA_SUPREME_ADMIN+"' or bcwtPatron.bcwtpatrontype.patrontypeid = " +
					"'"+Constants.MARTA_IT_ADMIN+"' or bcwtPatron.bcwtpatrontype.patrontypeid = " +
					"'"+Constants.MARTA_READONLY+"' or bcwtPatron.bcwtpatrontype.patrontypeid = " +
					"'"+Constants.MARTA_SUPER_ADMIN+"' or bcwtPatron.bcwtpatrontype.patrontypeid = " +
					"'"+Constants.MARTA_ADMIN+"')";
			
			if (null != searchDTO) {
				
				if (null != searchDTO.getAdminEmail()) {
					query = query
							+ " and lower(bcwtPatron.emailid) like '"
							+ searchDTO.getAdminEmail().trim().toLowerCase()
							+ "%'";
				}
				
				if (null != searchDTO.getAdminPhoneNumber()) {
					query = query
							+ " and lower(bcwtPatron.phonenumber) like '"
					        + searchDTO.getAdminPhoneNumber().trim().toLowerCase()
					        + "%'";
				}
				
				if (null != searchDTO.getAdminName()) {
					query = query
							+ " and lower(bcwtPatron.firstname) like '"
							+ searchDTO.getAdminName().trim().toLowerCase()
							+ "%'";
				}
				
				
				
			}
			searchList = session.createQuery(query).list();
			partnerInformation = new ArrayList();
			if (searchList != null && !searchList.isEmpty()) {
				
				for (Iterator iter = searchList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
			if (element != null) {
				searchDTO = new BcwtSearchDTO();
					
					if(element[0]!=null){
						searchDTO.setAdminEmail(element[0].toString());
					}
					if(element[1]!=null){
						searchDTO.setAdminPhoneNumber(element[1].toString());
					}
					if(element[2]!=null){
						searchDTO.setAdminName(element[2].toString());
					}
					
					partnerInformation.add(searchDTO);
					
				}
			}
		}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got Patron by email ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;

		} finally {
			closeSession(session, transaction);

		}
		return partnerInformation;
	}
	

		/*public List getAccountAdminDetails(BcwtSearchDTO searchDTO, String patronId)
	throws Exception {

	final String MY_NAME = ME + "searchLockedAdmins: ";
	BcwtsLogger.debug(MY_NAME + "SEARCH admin details started");
	Session session = null;
	Transaction transaction = null;
	String firstName = "";
	String eMail = "";
	String phoneNumber = "";
	List lockedAdminList = new ArrayList();
	BcwtSearchDTO bcwtSearchDTO = null;
	
	

	try {
		session = getSession();
		transaction = session.beginTransaction();
		if (!Util.isBlankOrNull(bcwtSearchDTO.getAdminName())) {
			 firstName = bcwtSearchDTO.getAdminName();
		}
		if (!Util.isBlankOrNull(bcwtSearchDTO.getAdminEmail())) {
			eMail = bcwtSearchDTO.getAdminEmail();
		}
		if (!Util.isBlankOrNull(bcwtSearchDTO.getAdminPhoneNumber())) {
			phoneNumber = bcwtSearchDTO.getAdminPhoneNumber();
		}
		
	
	
		String query = "from BcwtPatron bcwtpatron where" +
		" bcwtpatron.lockstatus = '"+Constants.YES+"'";
		if (null != firstName && !firstName.equals("")) {
			query = query + " and lower(bcwtpatron.firstname) like '"
			+ firstName.trim().toLowerCase() + "%'";
		}
		
		if (null != eMail && !eMail.equals("")) {
			query = query + " and lower(bcwtpatron.emailid) like '"
			+ eMail.trim().toLowerCase() + "%'";
		}
		if (null != phoneNumber && !phoneNumber.equals("")) {
			query = query + " and lower(bcwtpatron.phonenumber) like '"
			+ phoneNumber.trim().toLowerCase() + "%'";
		}
	
		List queryList = session.createQuery(query).list();
		for (Iterator iter = queryList.iterator(); iter.hasNext();) {
			BcwtPatron bcwtPatron = (BcwtPatron) iter.next();
			if (null != bcwtPatron) {
				BcwtSearchDTO bcwtSearchDTO1 = new BcwtSearchDTO();
				if(!Util.isBlankOrNull(bcwtPatron.getFirstname())){
					bcwtSearchDTO1.setAdminName(bcwtPatron.getFirstname());
				}
				
				if(!Util.isBlankOrNull(bcwtPatron.getEmailid())){
					bcwtSearchDTO1.setAdminEmail(bcwtPatron.getEmailid());
				}
				if(!Util.isBlankOrNull(bcwtPatron.getPhonenumber())){
					bcwtSearchDTO1.setAdminPhoneNumber(bcwtPatron.getPhonenumber());
				}
				
				lockedAdminList.add(bcwtSearchDTO1);
			}
		}
		session.flush();
		BcwtsLogger.info(MY_NAME + "retriving into the DB");
	} catch (Exception ex) {
		BcwtsLogger.error(MY_NAME + ex.getMessage());
		throw ex;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	return lockedAdminList;

/*	if (null != bcwtPatron) {
		if(null != bcwtPatron.getFirstname()){
			bcwtSearchDTO.setAdminName(bcwtPatron.getFirstname());
		}
		if(null != bcwtPatron.getEmailid()){
			bcwtSearchDTO.setAdminEmail(bcwtPatron.getEmailid());
		}
		if(null != bcwtPatron.getPhonenumber()){
			bcwtSearchDTO.setAdminPhoneNumber(bcwtPatron.getPhonenumber());
		}
	}
	if(bcwtSearchDTO != null){
	
	for (Iterator iter = searchList.iterator(); iter.hasNext();) {
		
		Object[] element = (Object[]) iter.next();
		if (element != null) {
			bcwtSearchDTO = new BcwtSearchDTO();
			bcwtSearchDTO.setAdminEmail(element[0].toString());
			bcwtSearchDTO.setAdminPhoneNumber(element[0].toString());
			bcwtSearchDTO.setAdminName(element[0].toString());
			adminInformation.add(bcwtSearchDTO);
		}
	
	}
	}
	transaction.commit();
	session.flush();
	session.close();
	BcwtsLogger.info(MY_NAME + "Got Patron by email ");
} catch (Exception ex) {
	BcwtsLogger.error(MY_NAME + ex.getMessage());
	throw ex;

} finally {
	closeSession(session, transaction);

}
return adminInformation;

*/
	
}	
	
	



