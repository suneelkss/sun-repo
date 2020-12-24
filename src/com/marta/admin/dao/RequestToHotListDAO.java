package com.marta.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.marta.admin.dto.BcwtRequestToHotListDTO;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.hibernate.BcwtHotListCardDetails;
import com.marta.admin.utils.Util;

/**
 * 
 * @author Jagadeesan
 */
public class RequestToHotListDAO extends MartaBaseDAO{
	
		final String ME = "RequestToHotListDAO: ";

		/**
		 * To update hotlist card details into DB
		 * 
		 * @param bcwtRequestToHotListDTO
		 * @return
		 * @throws Exception
		 */
	public boolean updateHotListDetails(BcwtRequestToHotListDTO bcwtRequestToHotListDTO) throws Exception{
		
		final String MY_NAME = ME + " updateHotListDetails: ";
		BcwtsLogger.debug(MY_NAME + " updating hot list details ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		BcwtHotListCardDetails bcwtHotListCardDetails = null;
		
		try {			
			session = getSession();
			transaction = session.beginTransaction();	
			String query = "from " +
								"BcwtHotListCardDetails bcwtHotListCardDetails " +
							"where " +
								"bcwtHotListCardDetails.serialnumber= '"+ bcwtRequestToHotListDTO.getSerialNumber() +"' ";
			
			bcwtHotListCardDetails = (BcwtHotListCardDetails) session.createQuery(query).uniqueResult();
			
			if(bcwtHotListCardDetails!=null){
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getFirstName())){
					bcwtHotListCardDetails.setFirstname(bcwtRequestToHotListDTO.getFirstName());
				}
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getLastName())){
					bcwtHotListCardDetails.setLastname(bcwtRequestToHotListDTO.getLastName());
				}
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getSerialNumber())){
					bcwtHotListCardDetails.setSerialnumber(bcwtRequestToHotListDTO.getSerialNumber());
				}
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getPointOfSale())){
					bcwtHotListCardDetails.setPointofsale(bcwtRequestToHotListDTO.getPointOfSale());
				}
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getAdminName())){
					bcwtHotListCardDetails.setAdminname(bcwtRequestToHotListDTO.getAdminName());
				}
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getHotListedDate())){
					bcwtHotListCardDetails.setHotlisteddate(Util.convertStringToDate(bcwtRequestToHotListDTO
							.getHotListedDate()));
				}
			}else{
				bcwtHotListCardDetails = new BcwtHotListCardDetails();
				
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getFirstName())){
					bcwtHotListCardDetails.setFirstname(bcwtRequestToHotListDTO.getFirstName());
				}
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getLastName())){
					bcwtHotListCardDetails.setLastname(bcwtRequestToHotListDTO.getLastName());
				}
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getSerialNumber())){
					bcwtHotListCardDetails.setSerialnumber(bcwtRequestToHotListDTO.getSerialNumber());
				}
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getPointOfSale())){
					bcwtHotListCardDetails.setPointofsale(bcwtRequestToHotListDTO.getPointOfSale());
				}
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getAdminName())){
					bcwtHotListCardDetails.setAdminname(bcwtRequestToHotListDTO.getAdminName());
				}
				if(!Util.isBlankOrNull(bcwtRequestToHotListDTO.getHotListedDate())){
					bcwtHotListCardDetails.setHotlisteddate(Util.convertStringToDate(bcwtRequestToHotListDTO
							.getHotListedDate()));
				}
			}
			
			session.saveOrUpdate(bcwtHotListCardDetails);
			isUpdated = true;
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "hot list card details updated to database ");			
		}
		 catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in hot list card details :"
					+ e.getMessage());
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}

	/**
	 * To get hotlist details for others using serialNumber
	 * 
	 * @param serialNumber
	 * @return
	 * @throws Exception
	 */
	public BcwtRequestToHotListDTO getHotListDetailsForOthers(String serialNumber)
						throws Exception{
		
		final String MY_NAME = ME + " getHotListDetailsForOthers: ";
		BcwtsLogger.debug(MY_NAME + " updating hot list details ");
		Session session = null;
		Transaction transaction = null;
		BcwtRequestToHotListDTO bcwtRequestToHotListDTO = null;
		try {			
			session = getSession();
			transaction = session.beginTransaction();
			bcwtRequestToHotListDTO = new BcwtRequestToHotListDTO();
			String query = "select " +
								"patron.firstname, " +
								"patron.lastname, " +
								"bcwtPatronBreezeCard.bcwtpatron.patronid, " +
								"patron.bcwtpatrontype.patrontypeid " +
						   "from " +
						   		"BcwtPatronBreezeCard bcwtPatronBreezeCard," +
						   		"BcwtPatron patron " +
						   "where " +
						   		"bcwtPatronBreezeCard.breezecardserialnumber='"+ serialNumber +"'" +
						   "and " +
						   		"bcwtPatronBreezeCard.bcwtpatron.patronid=patron.patronid";
			
			List patronHotlist = (List) session.createQuery(query).list();
			if(patronHotlist != null){
				for(Iterator it = patronHotlist.iterator(); it.hasNext();){				
					Object[] element = (Object[]) it.next();
					if(element[3]!= null && element[3].toString().equalsIgnoreCase(Constants.IS)){
						bcwtRequestToHotListDTO.setFirstName(element[0].toString());
						bcwtRequestToHotListDTO.setLastName(element[1].toString());
						bcwtRequestToHotListDTO.setCardType(Constants.ORDER_TYPE_IS);
					}if(element[3] != null && element[3].toString().equalsIgnoreCase(Constants.GBS_SUPER_ADMIN)
							|| element[3].toString().equalsIgnoreCase(Constants.GBS_ADMIN)
							|| element[3].toString().equalsIgnoreCase(Constants.GBS_READONLY)){
						bcwtRequestToHotListDTO.setFirstName(element[0].toString());
						bcwtRequestToHotListDTO.setLastName(element[1].toString());
						bcwtRequestToHotListDTO.setCardType(Constants.ORDER_TYPE_GBS);
					}
				}
			}
				transaction.commit();
				session.flush();
				BcwtsLogger.info(MY_NAME + "hot list card details updated to database ");			
			}
		 	catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron details :"
					+ e.getMessage());
			throw e;
		 	} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		 	}
		return bcwtRequestToHotListDTO;
	}
	/**
	 * To get recently hotlist card list
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getRecentlyHotListCardList() throws Exception {
		
		final String MY_NAME = ME + "getRequestToHotList: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List requestToHotListParameters = new ArrayList();
		List recentlyHotListCardList = new ArrayList(); 
		BcwtRequestToHotListDTO bcwtRequestToHotListDTO = null;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			String query = "from " +
								"BcwtHotListCardDetails bcwtHotListCardDetails " +
						   "order by " +
						   		"bcwtHotListCardDetails.hotlisteddate desc";
			requestToHotListParameters = session.createQuery(query).list();

		if(requestToHotListParameters != null && !requestToHotListParameters.isEmpty()){
			for (Iterator iter = requestToHotListParameters.iterator(); iter
					.hasNext();) {
				BcwtHotListCardDetails bcwtHotListCardDetails = (BcwtHotListCardDetails) iter.next();				
				bcwtRequestToHotListDTO = new BcwtRequestToHotListDTO();
	
					bcwtRequestToHotListDTO.setAdminName(bcwtHotListCardDetails.getAdminname());
					bcwtRequestToHotListDTO.setSerialNumber(bcwtHotListCardDetails.getSerialnumber());
					bcwtRequestToHotListDTO.setPointOfSale(bcwtHotListCardDetails.getPointofsale());
					bcwtRequestToHotListDTO.setLastName(bcwtHotListCardDetails.getLastname());
					bcwtRequestToHotListDTO.setFirstName(bcwtHotListCardDetails.getFirstname());
					bcwtRequestToHotListDTO.setHotListedDate(Util.getFormattedDate(bcwtHotListCardDetails
							.getHotlisteddate()).toString());
					recentlyHotListCardList.add(bcwtRequestToHotListDTO);
					
					if(recentlyHotListCardList.size()==5){
						requestToHotListParameters = new ArrayList();
						break;
					}
			}
		}
		transaction.commit();
		session.flush();			
		BcwtsLogger.info(MY_NAME
				+ "Got server ConfigParam list and whose size is "
					+ requestToHotListParameters.size());
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		
		} finally {
			closeSession(session, transaction);
		}
		return recentlyHotListCardList;
	}
		
	/**
	 * To get hotlist details
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getRequestToHotList() throws Exception {
	
	final String MY_NAME = ME + "getRequestToHotList: ";
	BcwtsLogger.debug(MY_NAME);
	Session session = null;
	Transaction transaction = null;
	List requestToHotListParameters = new ArrayList();
	List requestToHotList = new ArrayList(); 
	BcwtRequestToHotListDTO bcwtRequestToHotListDTO = null;

	try {
		BcwtsLogger.debug(MY_NAME + "Get Session");
		session = getSession();
		BcwtsLogger.debug(MY_NAME + " Got Session");
		transaction = session.beginTransaction();
		BcwtsLogger.debug(MY_NAME + " Execute Query");
		String query = "from BcwtHotListCardDetails";
		requestToHotListParameters = session.createQuery(query).list();
		for (Iterator iter = requestToHotListParameters.iterator(); iter
				.hasNext();) {
			BcwtHotListCardDetails bcwtHotListCardDetails = (BcwtHotListCardDetails) iter.next();				
			bcwtRequestToHotListDTO = new BcwtRequestToHotListDTO();
			
			bcwtRequestToHotListDTO.getCompanyId();
			bcwtRequestToHotListDTO.setCompanyName(bcwtHotListCardDetails.getCompanyname());
			bcwtRequestToHotListDTO.setAdminName(bcwtHotListCardDetails.getAdminname());
			bcwtRequestToHotListDTO.setSerialNumber(bcwtHotListCardDetails.getSerialnumber());
			bcwtRequestToHotListDTO.setCustomerMemberId(bcwtHotListCardDetails.getCustomermemberid());
			bcwtRequestToHotListDTO.setPointOfSale(bcwtHotListCardDetails.getPointofsale());
			bcwtRequestToHotListDTO.setLastName(bcwtHotListCardDetails.getLastname());
			bcwtRequestToHotListDTO.setFirstName(bcwtHotListCardDetails.getFirstname());
			bcwtRequestToHotListDTO.setHotListedDate(Util.getFormattedDate(bcwtHotListCardDetails
					.getHotlisteddate()).toString());
			
			requestToHotList.add(bcwtRequestToHotListDTO);
	}
	transaction.commit();
	session.flush();			
	BcwtsLogger.info(MY_NAME
			+ "Got server ConfigParam list and whose size is "
				+ requestToHotListParameters.size());
	} catch (Exception ex) {
		BcwtsLogger.error(MY_NAME + ex.getMessage());
		throw ex;
	
	} finally {
		closeSession(session, transaction);
	}
	return requestToHotList;
}
	
	
	/**
	 * To get hotlist card details for PS using serialNumber
	 * 
	 * @param serialNumber
	 * @return
	 * @throws Exception
	 */
	public BcwtRequestToHotListDTO getHotListDetailsForPS(String serialNumber) throws Exception{
		
		final String MY_NAME = ME + "getHotListDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BcwtRequestToHotListDTO bcwtRequestToHotListDTO = null;
		try {			
			String query = "select " +
								"FIRST_NAME," +
								"LAST_NAME," +
								"SERIAL_NBR " +
							"from " +
								"nextfare_main.MEMBER " +
							"where " +
								"SERIAL_NBR = '"+ serialNumber +"' ";			
			nextFareConnection = NextFareConnection.getConnection();			
			if(nextFareConnection != null) {
				pstmt = nextFareConnection.prepareStatement(query);
				if(pstmt != null) {
					rs = pstmt.executeQuery();
				}
			}			
			while(rs.next()){
				bcwtRequestToHotListDTO = new BcwtRequestToHotListDTO();				
				bcwtRequestToHotListDTO.setFirstName(rs.getString("FIRST_NAME"));
				bcwtRequestToHotListDTO.setLastName(rs.getString("LAST_NAME"));
				bcwtRequestToHotListDTO.setSerialNumber(rs.getString("SERIAL_NBR"));
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
		return bcwtRequestToHotListDTO;
	}
}
