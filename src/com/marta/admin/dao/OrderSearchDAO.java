package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.text.NumberFormat;


import org.apache.struts.util.LabelValueBean;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.marta.admin.forms.CardOrderedStatusForm;
import com.marta.admin.hibernate.PartnerCardOrder;
import com.marta.admin.hibernate.BcwtPartnerOrderInfo;
import com.marta.admin.hibernate.PartnerCardOrderStatus;
import com.marta.admin.hibernate.BcwtOrder;
import com.marta.admin.hibernate.BcwtOrderStatus;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.utils.BcwtsLogger;

import com.marta.admin.utils.Util;



public class OrderSearchDAO extends MartaBaseDAO {

	final String ME = "OrderSearchDAO: ";

	
	
	
	public List getGbsOrderDetails(CardOrderedStatusForm cardOrderedStatusForm,String patronid) throws Exception {
		final String MY_NAME = ME + "getGbsOrderDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		String query = null;
		
		List gbsOrderInfoList = new ArrayList();
		String orderType = null;
		
		try {	
			orderType = "GBS";
			session = getSession();
			transaction = session.beginTransaction();
			
			/*
			 * Tracking url is not available now
			 * in future after getting the url  
			 * it possible to integrate.
			 */
				if(orderType.equalsIgnoreCase("GBS"))
				{
				query = "select " +
							" bcwtorder.orderid, patron.firstname, patron.lastname," +
							" patron.emailid, bcwtorder.bcwtorderstatus.orderstatusname" +
							
						" from" +
							" BcwtOrder bcwtorder, BcwtPatron patron," +
							" BcwtOrderStatus status, BcwtOrderInfo oi" +						
						" where" +
							" bcwtorder.orderType = '" + orderType + "'" +
							" and bcwtorder.bcwtorderstatus.orderstatusid = status.orderstatusid " +
							" and bcwtorder.orderid = oi.bcwtorder.orderid" +														
							" and patron.patronid = bcwtorder.bcwtpatron.patronid";
				
				}
				if(null != cardOrderedStatusForm.getOrderStartDate() && !(cardOrderedStatusForm.getOrderStartDate()).equals("") && null != cardOrderedStatusForm.getOrderEndDate() && !(cardOrderedStatusForm.getOrderEndDate()).equals("")){
					query = query + " and to_date(bcwtorder.orderdate) >= to_date('"+cardOrderedStatusForm.getOrderStartDate()+"', 'mm/dd/yyyy')" +
						" and to_date(bcwtorder.orderdate) < to_date('"+Util.getNextDay(cardOrderedStatusForm.getOrderEndDate())+"', 'mm/dd/yyyy')";
				
				}else if(null != cardOrderedStatusForm.getOrderStartDate() && !(cardOrderedStatusForm.getOrderStartDate()).equals("")){
						query = query + " and to_date(bcwtorder.orderdate) >= to_date('"+cardOrderedStatusForm.getOrderStartDate()+"', 'mm/dd/yyyy')" ;
			
				}else if(null != cardOrderedStatusForm.getOrderEndDate() && !(cardOrderedStatusForm.getOrderEndDate()).equals("")){
						query = query + " and to_date(bcwtorder.orderdate) < to_date('"+Util.getNextDay(cardOrderedStatusForm.getOrderEndDate())+"', 'mm/dd/yyyy')" ;
			
				}
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getBatchId())) {
					query = query +		
					" and oi.batchid = " + cardOrderedStatusForm.getBatchId();
				}
			
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getOrderId())) {
					query = query +		
					" and bcwtorder.orderid = " + cardOrderedStatusForm.getOrderId();
				}			
					
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getOrderStatus())) {
					query = query +		
					" and status.orderstatusid = " + cardOrderedStatusForm.getOrderStatus();
				}
		    List queryList = session.createQuery(query).list();
			
			    if(queryList != null && !queryList.isEmpty()){
				for(Iterator ite = queryList.iterator(); ite.hasNext();){
					BcwtSearchDTO SearchDTO = new BcwtSearchDTO();
					Object[] obj = (Object[]) ite.next();
					
					if(obj[0] != null) {
						SearchDTO.setOrderId(obj[0].toString());						
					}
					if(obj[1] != null) {
						SearchDTO.setPatronFirstName(obj[1].toString());						
					}
					if(obj[2] != null) {
						SearchDTO.setPatronLastName(obj[2].toString());						
					}
					if(obj[3] != null) {
						SearchDTO.setEmailId(obj[3].toString());						
						}
					if(obj[4] != null) {
						SearchDTO.setOrderStatus(obj[4].toString());						
					}
								
					gbsOrderInfoList.add(SearchDTO);					
				}
			}			
	  transaction.commit();
	  session.flush();
	  session.close();
	  } catch (Exception e) {
	  BcwtsLogger.error(MY_NAME + e);
	  throw e;
	  } finally {
      closeSession(session, transaction);
	  BcwtsLogger.debug(MY_NAME + " Resources closed");
      }
	
			return gbsOrderInfoList;
	}
	
	public List getIsOrderDetails(CardOrderedStatusForm cardOrderedStatusForm,String patronid) throws Exception {
		final String MY_NAME = ME + "getIsOrderDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		String query = null;
		List isOrderInfoList = new ArrayList();
		String orderType = null;
		
		try {	
			orderType = "IS";
			session = getSession();
			transaction = session.beginTransaction();
			
				if(orderType.equalsIgnoreCase("IS"))
				{
					
					
					query = " select" +
					" bcwtorder.orderid, patron.firstname, patron.lastname," +
					" patron.emailid, bcwtorder.bcwtorderstatus.orderstatusname, bcwtorder.orderdate " +
					
				" from" +
					" BcwtOrder bcwtorder, BcwtPatron patron, BcwtPatronBreezeCard card," +
					" BcwtOrderStatus status, BcwtOrderDetails od"+ 						
				" where" +
					" bcwtorder.orderType = '" + orderType + "'" +
					" and bcwtorder.bcwtorderstatus.orderstatusid = status.orderstatusid " +
					" and bcwtorder.orderid = od.bcwtorder.orderid " +
					" and od.bcwtpatronbreezecard.breezecardid = card.breezecardid" + 
					" and patron.patronid = bcwtorder.bcwtpatron.patronid";
				
				}
				
			
				if(null != cardOrderedStatusForm.getOrderStartDate() && !(cardOrderedStatusForm.getOrderStartDate()).equals("") && null != cardOrderedStatusForm.getOrderEndDate() && !(cardOrderedStatusForm.getOrderEndDate()).equals("")){
					query = query + " and to_date(bcwtorder.orderdate) >= to_date('"+cardOrderedStatusForm.getOrderStartDate()+"', 'mm/dd/yyyy')" +
						" and to_date(bcwtorder.orderdate) < to_date('"+Util.getNextDay(cardOrderedStatusForm.getOrderEndDate())+"', 'mm/dd/yyyy')";
				
				}else if(null != cardOrderedStatusForm.getOrderStartDate() && !(cardOrderedStatusForm.getOrderStartDate()).equals("")){
						query = query + " and to_date(bcwtorder.orderdate) >= to_date('"+cardOrderedStatusForm.getOrderStartDate()+"', 'mm/dd/yyyy')" ;
			
				}else if(null != cardOrderedStatusForm.getOrderEndDate() && !(cardOrderedStatusForm.getOrderEndDate()).equals("")){
						query = query + " and to_date(bcwtorder.orderdate) < to_date('"+Util.getNextDay(cardOrderedStatusForm.getOrderEndDate())+"', 'mm/dd/yyyy')" ;
			
				}				
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getOrderId())) {
					
					query = query +		
					" and bcwtorder.orderid = " + cardOrderedStatusForm.getOrderId();
				}			
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getPatronFirstName())) {
					query = query +		
					" and UPPER(patron.firstname) like '%" + cardOrderedStatusForm.getPatronFirstName().toUpperCase() + "%'";
				}
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getPatronLastName())) {
					query = query +		
					" and UPPER(patron.lastname) like '%" + cardOrderedStatusForm.getPatronLastName().toUpperCase() + "%'";
				}			
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getOrderStatus())) {
				
					query = query +		
					" and status.orderstatusid = " + cardOrderedStatusForm.getOrderStatus();
				}		
			    List queryList = session.createQuery(query).list();
			
			    if(queryList != null && !queryList.isEmpty()){
				for(Iterator ite = queryList.iterator(); ite.hasNext();){
					BcwtSearchDTO SearchDTO = new BcwtSearchDTO();
					Object[] obj = (Object[]) ite.next();
					
					if(obj[0] != null) {
						SearchDTO.setOrderId(obj[0].toString());						
					}
					if(obj[1] != null) {
						SearchDTO.setPatronFirstName(obj[1].toString());						
					}
					if(obj[2] != null) {
						SearchDTO.setPatronLastName(obj[2].toString());						
					}
					if(obj[3] != null) {
						SearchDTO.setAccountAdminEmailAddress(obj[3].toString());						
						}
					if(obj[4] != null) {
						SearchDTO.setOrderStatus(obj[4].toString());						
					}
					if(obj[5] != null) {
						SearchDTO.setOrderedDate(Util.getFormattedDate(obj[5]));
					}
					isOrderInfoList.add(SearchDTO);					
				}
			}			
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + e);
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		return isOrderInfoList;
	}
	
	public List getPsOrderDetails(CardOrderedStatusForm cardOrderedStatusForm,String patronid) throws Exception {
		final String MY_NAME = ME + "getPsOrderDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		String query = null;
		List psOrderInfoList = new ArrayList();
		String orderType = null;
		
		try {	
			orderType = "PS";
			session = getSession();
			transaction = session.beginTransaction();
		
			
			/*
			 * Tracking url is not available now
			 * in future after getting the url  
			 * it possible to integrate.
			 */
				if(orderType.equalsIgnoreCase("PS"))
				{
					query = "select" +
										" distinct partnercardorder.orderId," +
										" partnercardorder.partnerCompanyinfo.companyName," +
										" partnercardorder.partnerCardorderstatus.orderStatus," +
										" bcwtpartnerorderinfo.batchid" +
									" from" +
										" PartnerCardOrder partnercardorder," +
										" PartnerCardOrderStatus partnercardorderstatus," +
										" BcwtPartnerOrderInfo bcwtpartnerorderinfo," +
										" PartnerCompanyInfo partnercompanyinfo" +
									" where" +
										" partnercardorder.orderId = bcwtpartnerorderinfo.partnerCardorder.orderId" +
										" and partnercardorder.partnerCompanyinfo.companyId = partnercompanyinfo.companyId" +
										" and partnercardorder.partnerCardorderstatus.orderStatusid = partnercardorderstatus.orderStatusid";
					
				}
				
				if(null != cardOrderedStatusForm.getOrderStartDate() && !(cardOrderedStatusForm.getOrderStartDate()).equals("") && null != cardOrderedStatusForm.getOrderEndDate() && !(cardOrderedStatusForm.getOrderEndDate()).equals("")){
					query = query + " and to_date(partnercardorder.orderDate) >= to_date('"+cardOrderedStatusForm.getOrderStartDate()+"', 'mm/dd/yyyy')" +
						" and to_date(partnercardorder.orderDate) < to_date('"+Util.getNextDay(cardOrderedStatusForm.getOrderEndDate())+"', 'mm/dd/yyyy')";
				
				}else if(null != cardOrderedStatusForm.getOrderStartDate() && !(cardOrderedStatusForm.getOrderStartDate()).equals("")){
						query = query + " and to_date(partnercardorder.orderDate) >= to_date('"+cardOrderedStatusForm.getOrderStartDate()+"', 'mm/dd/yyyy')" ;
			
				}else if(null != cardOrderedStatusForm.getOrderEndDate() && !(cardOrderedStatusForm.getOrderEndDate()).equals("")){
						query = query + " and to_date(partnercardorder.orderDate) < to_date('"+Util.getNextDay(cardOrderedStatusForm.getOrderEndDate())+"', 'mm/dd/yyyy')" ;
			
				}
				
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getOrderId())) {
					query = query +		
						" and partnercardorder.orderId = " + cardOrderedStatusForm.getOrderId();
				}			
								
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getOrderStatus())) {
					query = query +		
						" and partnercardorderstatus.orderStatusid = " + cardOrderedStatusForm.getOrderStatus();
				}
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getBatchId())) {
					query = query +		
						" and bcwtpartnerorderinfo.batchid = " + cardOrderedStatusForm.getBatchId();
				}
				if(!Util.isBlankOrNull(cardOrderedStatusForm.getCompanyName())) {
					query = query +		
						" and partnercompanyinfo.companyName = '" + cardOrderedStatusForm.getCompanyName() + "'";
				}
						
			    List queryList = session.createQuery(query).list();
			
			    if(queryList != null && !queryList.isEmpty()){
				for(Iterator ite = queryList.iterator(); ite.hasNext();){
					BcwtSearchDTO SearchDTO = new BcwtSearchDTO();
					Object[] obj = (Object[]) ite.next();
					
					if(obj[0] != null) {
						SearchDTO.setOrderId(obj[0].toString());						
					}				
					if(obj[1] != null) {
						SearchDTO.setCompanyName(obj[1].toString());						
					}
					if(obj[2] != null) {
						SearchDTO.setOrderStatus(obj[2].toString());						
					}
					if(obj[3] != null) {
						SearchDTO.setBatchId(obj[3].toString());						
					}			
					psOrderInfoList.add(SearchDTO);					
				}
			}			
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + e);
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		return psOrderInfoList;
	}
	

	public BcwtSearchDTO populategbsViewDetails(String orderId) throws Exception {

		final String MY_NAME = ME + "getAdminList: ";
		BcwtsLogger.debug(MY_NAME + "getting admin details started");
		Session session = null;
		Transaction transaction = null;
		//BcwtPatron bcwtPatron = null;
		//BcwtOrder bcwtorder=null;
		List viewList = null;
		
		BcwtSearchDTO SearchDTO = new BcwtSearchDTO();
		
	
		//BcwtOrder bcwtOrder2  = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			viewList = new ArrayList();
			
			/*
			 * Tracking url is not available now
			 * in future after getting the url  
			 * it possible to integrate.
			 */
			Long order = Long.valueOf(orderId);
			String query = " select bcwtorder.orderid,bcwtorder.bcwtorderstatus.orderstatusid," +
					"bcwtorder.orderdate,bcwtorder.shippeddate from BcwtOrder bcwtorder where bcwtorder.orderid= '"+ order +"' ";
					
					
			List ToIterate = session.createQuery(query).list();		
			for (Iterator iter = ToIterate.iterator(); iter.hasNext();) {
						Object[] bcwtOrderview = (Object[]) iter.next();
						SearchDTO = new BcwtSearchDTO();
										
						if (bcwtOrderview != null) {
												
							if (null != bcwtOrderview[0]) {
								SearchDTO.setOrderId(bcwtOrderview[0]
										.toString());
							}
							if (null != bcwtOrderview[1]) {
								SearchDTO.setOrderStatus(bcwtOrderview[1].toString());
							}
							if (null != bcwtOrderview[2]) {
								SearchDTO.setOrderedDate(Util.getFormattedDate(bcwtOrderview[2]));
							}
							
							if (null !=bcwtOrderview[3]) {
								SearchDTO.setShippeddate(Util.getFormattedDate(bcwtOrderview[3]));
							}
							
							}
						viewList.add(SearchDTO);
					
				}
			transaction.commit();
			session.flush();
			
			BcwtsLogger.info(MY_NAME + "retriving into the DB");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return SearchDTO;
	}
	
public boolean updateGbsOrderDetails(BcwtSearchDTO bcwtSearchDTO) throws Exception {
		
		final String MY_NAME = ME + " updateGbsOrderDetails: ";
		BcwtsLogger.debug(MY_NAME + " updating Gbs Order ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		
		//BcwtSearchDTO SearchDTO = new BcwtSearchDTO();
		Long orderId=null;
		
		BcwtOrderStatus bcwtOrderStatus = null;
		BcwtOrder bcwtOrder = null;
		/*
		 * Tracking url is not available now
		 * in future after getting the url  
		 * it possible to integrate.
		 */
		try {
			session = getSession();
			transaction = session.beginTransaction();
			orderId = new Long(bcwtSearchDTO.getOrderId());
			String query = "from BcwtOrder bcwtorder where bcwtorder.orderid= '"+ orderId +"' ";
			bcwtOrder = (BcwtOrder)session.createQuery(query).uniqueResult();

			if(bcwtSearchDTO.getShippeddate()!=null ){
				bcwtOrder.setShippeddate(Util.convertStringToDate(bcwtSearchDTO.getShippeddate()));				
			}
			
			if(bcwtSearchDTO.getOrderStatus()!=null ){
			    bcwtOrderStatus = new BcwtOrderStatus();
				bcwtOrderStatus.setOrderstatusid(Long.valueOf(bcwtSearchDTO.getOrderStatus()));
				bcwtOrder.setBcwtorderstatus(bcwtOrderStatus);
			}
			
			session.update(bcwtOrder);
			isUpdated = true;
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Application settings details updated to database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron details :"
					+ e.getMessage());
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}

public BcwtSearchDTO populatepsViewDetails(String orderId) throws Exception {

	final String MY_NAME = ME + "populatepsViewDetails: ";
	BcwtsLogger.debug(MY_NAME + "getting admin details started");
	Session session = null;
	Transaction transaction = null;
	//BcwtPatron bcwtPatron = null;
	//BcwtOrder bcwtorder=null;
	
	BcwtSearchDTO SearchDTO = new BcwtSearchDTO();
	
	
	//BcwtOrder bcwtOrder2  = null;
	try {
		session = getSession();
		transaction = session.beginTransaction();
		
		/*
		 * Tracking url is not available now
		 * in future after getting the url  
		 * it possible to integrate.
		 */
		Long order = Long.valueOf(orderId);
		String query = "select " +
		" partnercardorder.orderId," +
		" bcwtpartnerorderinfo.batchid,partnercardorder.partnerCompanyinfo.companyName," +
		" partnercardorder.orderDate,bcwtpartnerorderinfo.shippeddate,partnercardorder.partnerCardorderstatus.orderStatusid" +

		" from" +
		" PartnerCardOrder partnercardorder, PartnerCardOrderStatus partnercardorderstatus," +
		" PartnerAdminDetails partneradmindetails, BcwtPartnerOrderInfo bcwtpartnerorderinfo," +
		" PartnerCompanyInfo partnercompanyinfo"+

		" where " +
		" partnercardorder.partnerCardorderstatus.orderStatusid = partnercardorderstatus.orderStatusid" +
		" and partnercardorder.partnerAdmindetails.partnerId = partneradmindetails.partnerId" +
		" and partnercardorder.orderId = bcwtpartnerorderinfo.partnerCardorder.orderId" +
		" and partnercompanyinfo.companyId = partnercardorder.partnerCompanyinfo.companyId and partnercardorder.orderId ='"+ order +"' ";
				
				
		List ToIterate = session.createQuery(query).list();		
		for (Iterator iter = ToIterate.iterator(); iter.hasNext();) {
					Object[] bcwtOrderview = (Object[]) iter.next();
					SearchDTO = new BcwtSearchDTO();
														
					if (bcwtOrderview != null) {
											
						if (null != bcwtOrderview[0]) {
							SearchDTO.setOrderId(bcwtOrderview[0]
									.toString());
						}
						if (null != bcwtOrderview[1]) {
							SearchDTO.setBatchId(bcwtOrderview[1].toString());
						}
						if (null != bcwtOrderview[2]) {
							SearchDTO.setCompanyName(bcwtOrderview[2].toString());
						}
						if (null != bcwtOrderview[3]) {
							SearchDTO.setOrderedDate(Util.getFormattedDate(bcwtOrderview[3]));
						}
						if (null !=bcwtOrderview[4]) {
							SearchDTO.setShippeddate(Util.getFormattedDate(bcwtOrderview[4]));
						}
						if (null != bcwtOrderview[5]) {
							SearchDTO.setOrderStatus(bcwtOrderview[5].toString());
						}
						}
					}
			transaction.commit();
		    session.flush();
			BcwtsLogger.info(MY_NAME + "retriving into the DB");
	} catch (Exception ex) {
		BcwtsLogger.error(MY_NAME + ex.getMessage());
		throw ex;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	return SearchDTO;
}

public boolean updatePsOrderDetails(BcwtSearchDTO bcwtSearchDTO) throws Exception {
	
	final String MY_NAME = ME + " updatePsOrderDetails: ";
	BcwtsLogger.debug(MY_NAME + " updating Ps Order ");
	boolean isUpdated = false;
	Session session = null;
	Transaction transaction = null;
	
	Long orderId=null;
	PartnerCardOrder partnercardorder=null;	
	PartnerCardOrderStatus partnerCardOrderStatus=null;
	//BcwtOrderStatus bcwtOrderStatus = null;
	//BcwtOrder bcwtOrder = null;
	boolean isPartnerOrderStatus = false;
	/*
	 * Tracking url is not available now
	 * in future after getting the url  
	 * it possible to integrate.
	 */
	try {
		session = getSession();
		transaction = session.beginTransaction();
		orderId = new Long(bcwtSearchDTO.getOrderId());
		String query = "from PartnerCardOrder partnercardorder where partnercardorder.orderId= '"+ orderId +"' ";
		partnercardorder = (PartnerCardOrder)session.createQuery(query).uniqueResult();
		if(partnercardorder!=null){
			if(bcwtSearchDTO.getOrderStatus()!=null ){
				partnerCardOrderStatus = new PartnerCardOrderStatus();
				partnerCardOrderStatus.setOrderStatusid(Long.valueOf(bcwtSearchDTO.getOrderStatus()));
				partnercardorder.setPartnerCardorderstatus(partnerCardOrderStatus);				
			}
		}		
		session.update(partnercardorder);
		transaction.commit();
		session.flush();
		session.close();
		isPartnerOrderStatus = true;		
		if(isPartnerOrderStatus){
			isUpdated = true;
			if(bcwtSearchDTO.getShippeddate()!=null){
				session = getSession();
				transaction = session.beginTransaction();
				String query2 = "from BcwtPartnerOrderInfo bcwtpartnerorderinfo " +
						"where bcwtpartnerorderinfo.partnerCardorder.orderId = '"+ orderId +"' ";
				BcwtPartnerOrderInfo bcwtPartnerOrderInfo = new BcwtPartnerOrderInfo();
				bcwtPartnerOrderInfo = (BcwtPartnerOrderInfo) session.createQuery(query2).uniqueResult();
				
				if(bcwtPartnerOrderInfo!=null){					
					bcwtPartnerOrderInfo.setShippeddate
						(Util.convertStringToDate(bcwtSearchDTO.getShippeddate()));
				}
				session.update(bcwtPartnerOrderInfo);
				transaction.commit();
				session.flush();
				session.close();
			}
		}
		BcwtsLogger.info(MY_NAME + "Application settings details updated to database ");
	} catch (Exception e) {
		BcwtsLogger.error(MY_NAME
				+ " Exception in updating patron details :"
				+ e.getMessage());
		throw e;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	return isUpdated;
}
public BcwtSearchDTO populateisViewDetails(String orderId) throws Exception {

	final String MY_NAME = ME + "getAdminList: ";
	BcwtsLogger.debug(MY_NAME + "getting admin details started");
	Session session = null;
	Transaction transaction = null;
	
	
	BcwtSearchDTO SearchDTO = new BcwtSearchDTO();
	
	
	
	try {
		session = getSession();
		transaction = session.beginTransaction();
		
		
					
		Long order = Long.valueOf(orderId);
		String query = " select bcwtorder.orderid,bcwtpatron.firstname,bcwtpatron.lastname," +
		"bcwtorder.orderdate,bcwtorder.shippeddate,bcwtorder.bcwtorderstatus.orderstatusid from BcwtOrder bcwtorder,BcwtPatron bcwtpatron where " +
		"bcwtorder.orderid= '"+ order +"' and bcwtorder.bcwtpatron.patronid = bcwtpatron.patronid";
				
				
		List ToIterate = session.createQuery(query).list();		
		for (Iterator iter = ToIterate.iterator(); iter.hasNext();) {
					Object[] bcwtOrderview = (Object[]) iter.next();
			
					SearchDTO = new BcwtSearchDTO();
					
					if (bcwtOrderview != null) {
											
						if (null != bcwtOrderview[0]) {
							SearchDTO.setOrderId(bcwtOrderview[0]
									.toString());
						}
						if (null != bcwtOrderview[1]) {
							SearchDTO.setPartnerFirstName(bcwtOrderview[1].toString());
						}
						if (null != bcwtOrderview[2]) {
							SearchDTO.setPartnerLastName(bcwtOrderview[2].toString());
						}
						
						if (null !=bcwtOrderview[3]) {
							SearchDTO.setOrderedDate(Util.getFormattedDate(bcwtOrderview[3]));
						}
						if (null != bcwtOrderview[4]) {
							SearchDTO.setShippeddate(Util.getFormattedDate(bcwtOrderview[4]));
						}
						if (null != bcwtOrderview[5]) {
							SearchDTO.setOrderStatus(bcwtOrderview[5].toString());
						}
						}
				
			}
		
		transaction.commit();
		session.flush();
		
		BcwtsLogger.info(MY_NAME + "retriving into the DB");
	} catch (Exception ex) {
		BcwtsLogger.error(MY_NAME + ex.getMessage());
		throw ex;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	return SearchDTO;
}

public boolean updateIsOrderDetails(BcwtSearchDTO bcwtSearchDTO) throws Exception {
	
	final String MY_NAME = ME + " updateIsOrderDetails: ";
	BcwtsLogger.debug(MY_NAME + " updating IS Order ");
	boolean isUpdated = false;
	Session session = null;
	Transaction transaction = null;
	//BcwtOrder bcwtorder=null;
	//BcwtSearchDTO SearchDTO = new BcwtSearchDTO();
	Long orderId=null;
	
	try {
		session = getSession();
		transaction = session.beginTransaction();
		orderId = new Long(bcwtSearchDTO.getOrderId());
		String query = "from BcwtOrder bcwtorder where bcwtorder.orderid= '"+ orderId +"' ";
		BcwtOrder bcwtOrder = (BcwtOrder)session.createQuery(query).uniqueResult();
		if(bcwtSearchDTO.getShippeddate()!=null ){
			bcwtOrder.setShippeddate(new Date(bcwtSearchDTO.getShippeddate()));				
		}
		if(bcwtSearchDTO.getOrderStatus()!=null ){
			BcwtOrderStatus bcwtOrderStatus = new BcwtOrderStatus();
			bcwtOrderStatus.setOrderstatusid(Long.valueOf(bcwtSearchDTO.getOrderStatus()));
			bcwtOrder.setBcwtorderstatus(bcwtOrderStatus);
		}
		
		session.update(bcwtOrder);
		isUpdated = true;
		transaction.commit();
		session.flush();
		BcwtsLogger.info(MY_NAME + "Application settings details updated to database ");
	} catch (Exception e) {
		BcwtsLogger.error(MY_NAME
				+ " Exception in updating patron details :"
				+ e.getMessage());
		throw e;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	return isUpdated;
}




public List getOrderStatusForGBS() throws Exception {
	final String MY_NAME = ME + "getState: ";
	BcwtsLogger.debug(MY_NAME);
	List statusList = new ArrayList();
	Session session = null;
	Transaction transaction = null;
	String displayinhistory ="1";
	try {
		session = getSession();
		transaction = session.beginTransaction();
		String query = "select orderstatusid, orderstatusname from BcwtOrderStatus where orderstatusid <> 12 and displayinhistory='" + displayinhistory + "' order by orderstatusname";
		List statusListToIterate = session.createQuery(query).list();
		for (Iterator iter = statusListToIterate.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			statusList.add(new LabelValueBean(String.valueOf(element[1]),
					String.valueOf(element[0])));
		}
		transaction.commit();
		session.flush();
		BcwtsLogger.info(MY_NAME + "got State list from DB ");
	} catch (Exception ex) {
		BcwtsLogger.error(MY_NAME + ex.getMessage());
		throw ex;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	return statusList;
}
public List getOrderStatusForIS() throws Exception {
	final String MY_NAME = ME + "getState: ";
	BcwtsLogger.debug(MY_NAME);
	List statusList = new ArrayList();
	Session session = null;
	Transaction transaction = null;
	String displayinhistory ="1";
	try {
		session = getSession();
		transaction = session.beginTransaction();
		String query = "select orderstatusid, orderstatusname from BcwtOrderStatus where displayinhistory='" + displayinhistory + "' order by orderstatusname";
		List statusListToIterate = session.createQuery(query).list();
		for (Iterator iter = statusListToIterate.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			statusList.add(new LabelValueBean(String.valueOf(element[1]),
					String.valueOf(element[0])));
		}
		transaction.commit();
		session.flush();
		BcwtsLogger.info(MY_NAME + "got State list from DB ");
	} catch (Exception ex) {
		BcwtsLogger.error(MY_NAME + ex.getMessage());
		throw ex;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	return statusList;
}
public List getOrderStatusForPS() throws Exception {
	final String MY_NAME = ME + "getState: ";
	BcwtsLogger.debug(MY_NAME);
	List statusList = new ArrayList();
	Session session = null;
	Transaction transaction = null;
	String displayinhistory ="1";
	try {
		session = getSession();
		transaction = session.beginTransaction();
		String query = "select orderStatusid, orderStatus from PartnerCardOrderStatus where displayInHistory='" + displayinhistory + "' order by orderStatus";
		List statusListToIterate = session.createQuery(query).list();
		for (Iterator iter = statusListToIterate.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			statusList.add(new LabelValueBean(String.valueOf(element[1]),
					String.valueOf(element[0])));
		}
		transaction.commit();
		session.flush();
		BcwtsLogger.info(MY_NAME + "got State list from DB ");
	} catch (Exception ex) {
		BcwtsLogger.error(MY_NAME + ex.getMessage());
		throw ex;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	return statusList;
}
}

