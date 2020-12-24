package com.marta.admin.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import oracle.jdbc.OracleTypes;

import org.hibernate.Session;
import org.hibernate.Transaction;


import com.marta.admin.dao.NextFareConnection;
import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.business.NewNextfareMethods;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.UpCommingMonthBenefitDTO;
import com.marta.admin.hibernate.BcwtPartnerNewCard;
import com.marta.admin.hibernate.PartnerCardOrder;
import com.marta.admin.hibernate.PartnerHotlistHistory;
import com.marta.admin.hibernate.PartnerCompanyInfo;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;
import com.marta.admin.hibernate.PartnerBatchDetails;


public class BatchOMJobDAO extends MartaBaseDAO{
	
	
	
	
	final String ME = "BatchOMJobDAO: " ;
	

	
	public static String driverName = "";
	public static String url = "";
	public static String user = "";
	public static String password = "";

	private static void setDatabaseDetails(){
		ConfigurationCache configurationCache = null;
		BcwtConfigParamsDTO configParamDTO = null;
	 // to read nextware db path from properties file
	 ResourceBundle rs = ResourceBundle.getBundle("com.marta.admin.resources.nextfare");
	 configurationCache = new ConfigurationCache();
	 configParamDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
			.get(Constants.IS_MARTA_ENV);
	
		 driverName = rs.getString("driverName");
		 url = rs.getString("BCWTSDBPath");
		 user = rs.getString("BCWTSuser");
		 password = rs.getString("BCWTSpassword");
	
     
	} 
	
	

	public static Connection getConnection() throws Exception {
		Connection con = null;
		try {
			setDatabaseDetails();
			Class.forName(driverName);
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception ex) {
			BcwtsLogger.error("getConnection" + Util.getFormattedStackTrace(ex));
		}
		return con;
	}
	
	public List getBatchDetailsList(String nextFarecpnyIdStr) throws Exception {
		final String MY_NAME = ME + "PartnerBatchDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Session session = null;
		Transaction transaction = null;
			
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from PartnerBatchDetails partnerBatchDetails where partnerBatchDetails.partnerId in " +
					"(select partnercompanyinfo.companyId from PartnerCompanyInfo " +
					"partnercompanyinfo where partnercompanyinfo.companyId = '"+nextFarecpnyIdStr+"')";			
			List queryList = session.createQuery(query).list();			
			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				PartnerBatchDetails partnerBatchDetails = (PartnerBatchDetails) iter.next();				
				
				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
				upcommingmonthbenefitdto.setCardSerialNumber(partnerBatchDetails.getCardSerialNo()+" queue");
				upcommingmonthbenefitdto.setMemberName(partnerBatchDetails.getFirstName()+" "+partnerBatchDetails.getLastName());
				upcommingmonthbenefitdto.setMemberId(partnerBatchDetails.getCustomerMemberId());
	
				if("Activate".equals(partnerBatchDetails.getBenefitactionid()))
						upcommingmonthbenefitdto.setIsBenefitActivated("Yes");
				else
					upcommingmonthbenefitdto.setIsBenefitActivated("No");
				
				monthlySet.add(upcommingmonthbenefitdto);
			}
			
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got  partnerBatchDetails list from DB :" +nextFarecpnyIdStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		
		return new ArrayList(monthlySet);
	}	
	
	
	
	
public List getUpcommingMonthlyReportList(String nextfareId) throws Exception{
		
		final String MY_NAME = ME + "getUpcommingMonthlyReportList1:";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Connection con = null;
		ResultSet rs=null;
		Statement stmt = null;
		con = NextFareConnection.getConnection();
		BcwtsLogger.info(MY_NAME +"getting Upcomming Monthly for "+nextfareId);
	
		try {
		
			if(null != nextfareId){
			/*String query ="SELECT  MB.SERIAL_NBR,MB.FIRST_NAME,MB.LAST_NAME,MB.CUSTOMER_MEMBER_ID,MB.BENEFIT_STATUS_ID FROM NEXTFARE_MAIN.MEMBER MB WHERE MB.CUSTOMER_ID='"+nextfareId+"' " +
					"AND MB.MEMBER_ID in "+
					"(SELECT MBH.MEMBER_ID FROM NEXTFARE_MAIN.MEMBER_BENEFIT_HISTORY MBH WHERE CUSTOMER_ID='"+nextfareId+"')";//+" AND TO_DATE(MBH.INSERTED_DTM) <= TO_DATE('"+Util.getLastDateOfNextMonth()+"','mm/dd/yyyy') AND TO_DATE(MBH.EXPIRATION_DTM) <= TO_DATE('"+Util.getLastDateOfNextMonth()+"','mm/dd/yyyy')";
			*/
				String query ="SELECT  MB.SERIAL_NBR,MB.FIRST_NAME,MB.LAST_NAME,MB.CUSTOMER_MEMBER_ID," +
				"MB.BENEFIT_STATUS_ID,INVENTORY.HOTLIST_ACTION FROM NEXTFARE_MAIN.MEMBER MB, " +
				"NEXTFARE_MAIN.FARE_MEDIA_INVENTORY INVENTORY WHERE MB.CUSTOMER_ID='"+nextfareId+"'" +
		" AND  MB.BENEFIT_STATUS_ID = '1'  and  " +
		"INVENTORY.SERIAL_NBR = MB.SERIAL_NBR order by MB.LAST_NAME asc";
			
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if(rs != null){
				
				while(rs.next()){
				
										String memberName=rs.getString(2)+" "+rs.getString(3);
										UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
										upcommingmonthbenefitdto.setCardSerialNumber(rs.getString(1));
										upcommingmonthbenefitdto.setMemberName(memberName);
										upcommingmonthbenefitdto.setMemberId(rs.getString(4));
										if (null != rs.getString(5)) {
												if(!rs.getString(6).equals("0")){
												 upcommingmonthbenefitdto.setIsBenefitActivated("No");	 
											}
											 else{
											if ("1".equals(rs.getString(5))) {
												
												upcommingmonthbenefitdto.setIsBenefitActivated("Yes");
											} else {
												
												upcommingmonthbenefitdto.setIsBenefitActivated("No");
											
										    }
											 }
										}
											monthlySet.add(upcommingmonthbenefitdto);
					
					}
				
				
			}
		}
		}catch(Exception ex) {
			
			BcwtsLogger.error(MY_NAME +Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			rs.close();
			stmt.close();
			con.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return new ArrayList(monthlySet);
	}


public boolean checkHotlist(String serialNbr){
	   String MY_NAME = "checkHotlist"; 
	   boolean flag = true; 
	    Connection con = null;
		Statement stmt=null;
		ResultSet rs=null;
		
	
		try{
			con = NextFareConnection.getConnection();
			stmt = con.createStatement();
			String query="SELECT HOTLIST_ACTION from NEXTFARE_MAIN.FARE_MEDIA_INVENTORY where SERIAL_NBR="+serialNbr;
					
				
			rs= stmt.executeQuery(query.toString());
			while(rs.next()){
				if(rs.getString(1).equals("0"))
					flag = false;
				
				}
			
			
		}
		catch(Exception ex){
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
					
		}
		finally{
			try{
			
			rs.close();	
			stmt.close();
			
			NextFareConnection.closeConnection(con);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
			}
			catch(SQLException ex){
				BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			}
		}
		

	   
	   
	   
	   
	   return flag;
}



public List getHotlistCardDetails(String nextfareId) throws Exception {
	final String MY_NAME = ME + "getHotlistCardDetails: ";
	BcwtsLogger.debug(MY_NAME);
	Set monthlySet = new HashSet();
	Session session = null;
	Transaction transaction = null;
		
	try {
		session = getSession();
		transaction = session.beginTransaction();
		String query = "from PartnerHotlistHistory partnerHotlistHistory where partnerHotlistHistory.companyName in (select partnercompanyinfo.companyName from PartnerCompanyInfo partnercompanyinfo where partnercompanyinfo.companyId in ("+nextfareId+"))";			
		List queryList = session.createQuery(query).list();			
		for (Iterator iter = queryList.iterator(); iter.hasNext();) {
			PartnerHotlistHistory partnerHotlistHistory = (PartnerHotlistHistory) iter.next();				
			
			UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
			upcommingmonthbenefitdto.setCardSerialNumber(partnerHotlistHistory.getCardNumber()+" queue");
			upcommingmonthbenefitdto.setMemberName(partnerHotlistHistory.getFirstName()+" "+partnerHotlistHistory.getLastName());
			upcommingmonthbenefitdto.setMemberId(partnerHotlistHistory.getMemberId());

			if("yes".equals(partnerHotlistHistory.getUnhotlistFlag()))
					upcommingmonthbenefitdto.setIsBenefitActivated("Yes");
			else
				upcommingmonthbenefitdto.setIsBenefitActivated("No");
			
			monthlySet.add(upcommingmonthbenefitdto);
		}
		
		transaction.commit();
		session.flush();
		BcwtsLogger.info(MY_NAME + "got hotlist card details list from DB ");
	} catch (Exception ex) {
		
		BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
		throw ex;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	
	return new ArrayList(monthlySet);
}

public List getNewCardDetails(String nextfareId) throws Exception {
	final String MY_NAME = ME + "getNewCardDetails: ";
	BcwtsLogger.debug(MY_NAME);
	Set monthlySet = new HashSet();
	Session session = null;
	Transaction transaction = null;
	
	
	try {
		session = getSession();
		transaction = session.beginTransaction();
		String query = "from BcwtPartnerNewCard bcwtPartnerNewCard where bcwtPartnerNewCard.companyname in (select partnercompanyinfo.companyName from PartnerCompanyInfo partnercompanyinfo where partnercompanyinfo.companyId in ("+nextfareId+"))";	
		
		List queryList = session.createQuery(query).list();
		
		for (Iterator iter = queryList.iterator(); iter.hasNext();) {
			BcwtPartnerNewCard bcwtPartnerNewCard = (BcwtPartnerNewCard) iter.next();
			UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
			upcommingmonthbenefitdto.setCardSerialNumber(bcwtPartnerNewCard.getSerialnumber()+" New Card");
			upcommingmonthbenefitdto.setMemberName(bcwtPartnerNewCard.getFirstname()+" "+bcwtPartnerNewCard.getLastname());
			upcommingmonthbenefitdto.setMemberId(bcwtPartnerNewCard.getCustomermemberid().toString());
			monthlySet.add(upcommingmonthbenefitdto);
		}
		transaction.commit();
		session.flush();
		BcwtsLogger.info(MY_NAME + "got new card details list from DB ");
	} catch (Exception ex) {
		ex.printStackTrace();
		BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
		throw ex;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	return new ArrayList(monthlySet);
}

public String getAgencyID(String customerID){
	
	BcwtsLogger.info(" Class: BatchOMJobDAO  Method: getAgencyID for:" + customerID);
	String agencyID = "";
	Connection conn;
	Statement stmt;
	ResultSet rs;
	String query = "select agency_customer_id from nextfare_main.customer where customer_id ='"+customerID+"'";
	try{
	 conn = NextFareConnection.getConnection();
	stmt = conn.createStatement();
	rs = stmt.executeQuery(query);
	while(rs.next()){
		agencyID = rs.getString(1);
		
	}
	rs.close();
	stmt.close();
	conn.close();
	}
	catch(Exception e){
		
		BcwtsLogger.error(" Class: BatchOMJobDAO  Method: getAgencyID" + Util.getFormattedStackTrace(e));
	}
return agencyID;
}

	public List getCustomers() throws Exception {
	final String MY_NAME = ME + "getCustomers: ";
	BcwtsLogger.debug(MY_NAME);
	Set monthlySet = new HashSet();
	Session session = null;
	Transaction transaction = null;
	ArrayList nextfareIds = new ArrayList();
		
	try {
		session = getSession();
		transaction = session.beginTransaction();
		String query = "from PartnerCompanyInfo partnerCompanyInfo where partnerCompanyInfo.nextfareCompanyId > 0";			
		List queryList = session.createQuery(query).list();			
		for (Iterator iter = queryList.iterator(); iter.hasNext();) {
			PartnerCompanyInfo partnerCompanyInfo = (PartnerCompanyInfo) iter.next();				
			
			nextfareIds.add(partnerCompanyInfo);
			

		}
		
		transaction.commit();
		session.flush();
		BcwtsLogger.info(MY_NAME + "getCustomers ");
	} catch (Exception ex) {
		
		BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
		throw ex;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	
	return nextfareIds;
}


public ArrayList getFareInstrumentId(String customerID){
	String agencyID = "";
	Connection conn;
	Statement stmt;
	ResultSet rs;
	ArrayList benefits = new ArrayList(); 
	String query = "select CBD.FARE_INSTRUMENT_ID from NEXTFARE_MAIN.CUSTOMER_BENEFIT_DEFINITION  CBD WHERE CBD.BENEFIT_STATUS_ID = '1' AND  CUSTOMER_ID ='"+customerID+"'";
	try{
	 conn = NextFareConnection.getConnection();
	stmt = conn.createStatement();
	rs = stmt.executeQuery(query);
	while(rs.next()){
		agencyID = rs.getString(1);
		benefits.add(agencyID);
	}
	rs.close();
	stmt.close();
	conn.close();
	}
	catch(Exception e){
		
		BcwtsLogger.error("getFareInstrumentId" + Util.getFormattedStackTrace(e));
	}
return benefits;
}


public int getCardOrders(String companyId) throws Exception {
	final String MY_NAME = ME + "getCardOrders: ";
	BcwtsLogger.debug(MY_NAME);
	Set monthlySet = new HashSet();
	Session session = null;
	Transaction transaction = null;
	
	int totalQuantity = 0; 
	
		
	try {
		session = getSession();
		transaction = session.beginTransaction();
		String query = "from PartnerCardOrder partnerCardOrder where trunc(partnerCardOrder.orderDate) between trunc(to_date("+Util.getFirstDateOfMonth()+",'DD-MM-YYYY')) and trunc(to_date("+Util.getLastDateOfMonth2()+",'DD-MM-YYYY')) and partnerCardOrder.partnerCompanyinfo.companyId ='"+companyId+"'";			
		List queryList = session.createQuery(query).list();			
		for (Iterator iter = queryList.iterator(); iter.hasNext();) {
			PartnerCardOrder partnerCardOrder = (PartnerCardOrder) iter.next();				
			
			totalQuantity = totalQuantity + partnerCardOrder.getOrderQty().intValue();
			
			

		}
		
		transaction.commit();
		session.flush();
		BcwtsLogger.info(MY_NAME + "got new card order details list from DB ");
	} catch (Exception ex) {
		BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
		throw ex;
	} finally {
		closeSession(session, transaction);
		BcwtsLogger.debug(MY_NAME + " Resources closed");
	}
	System.out.println("total partnerCardOrder: "+totalQuantity);
	return totalQuantity;
}


public void updateCustomTableOM(String custNum, int count,String orderNumber, String psOrderType){
	
	PreparedStatement psmt;
	Connection conn;
	Date javadate = new Date();
	BatchOMJobDAO batchOMJobDAO = new BatchOMJobDAO();
	String custId = batchOMJobDAO.getCustomerID(custNum); 
	String shipToId = batchOMJobDAO.getShipToID(custId);
	String invoiceToId = batchOMJobDAO.getInvoiveToID(custId);
	String productId;
	String orderType;
	String lineType;
	if(psOrderType.equals("UM")){
	 productId = "270098";
	 orderType = "Web Sales Order";
     lineType  = "Web Sales Line"; 
	}
	else{
	 productId = "419127";	
	 orderType =  "Web Sales Order w Ship";
	 lineType  = "Web Sales Line w Ship";
	}
	try{
	
		conn = getConnection();
	    psmt = conn.prepareStatement("insert into  BCWTS_TO_ORAOM_IFACE " +
	    		"(STATUS,INTERFACE_DATE ,ORDER_SOURCE_ID,ORDERED_DATE," +
	    		"ORDER_TYPE,CUSTOMER_ID ,SOLD_TO_ORG_ID,SHIP_TO_ORG_ID," +
	    		"INVOICE_TO_ORG_ID,REQUEST_DATE,OPERATION_CODE,LINE_NUMBER,PRICING_DATE," +
	    		"PROMISE_DATE,SCHEDULE_SHIP_DATE,LINE_TYPE,INVENTORY_ITEM_ID,ORDERED_QUANTITY," +
	    		"PRICING_QUANTITY,CREATED_BY ,LAST_UPDATED_BY,SHIP_FROM_ORG_ID,ORIG_SYS_DOCUMENT_REF,ORIG_SYS_LINE_REF) " +
	    		" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
	    psmt.setString(1, "New");
	    psmt.setDate(2, new java.sql.Date(javadate.getTime()));
	    psmt.setString(3,"1001");
	    psmt.setDate(4, new java.sql.Date(Util.getLastDateOfNextMonthOM().getTime()));
	    psmt.setString(5, orderType);
	    psmt.setString(6,custId ); 
	    psmt.setString(7,custId); 
	    psmt.setString(8,shipToId); 
	    psmt.setString(9,invoiceToId); 
	    psmt.setDate(10,new java.sql.Date(javadate.getTime())); //???
	    psmt.setString(11,"INSERT");
	    psmt.setString(12, "1");
	    psmt.setDate(13,new java.sql.Date(Util.getLastDateOfNextMonthOM().getTime()));
	    psmt.setDate(14,new java.sql.Date(javadate.getTime()));
	    psmt.setDate(15,new java.sql.Date(javadate.getTime()));
	    psmt.setString(16, lineType);
	    psmt.setString(17, productId); 
	    psmt.setInt(18, count);
        psmt.setInt(19, count);
        psmt.setString(20,"2049");
        psmt.setString(21,"2049");
        psmt.setString(22,"103");
        psmt.setString(23,orderNumber);
        psmt.setString(24,orderNumber);
        psmt.executeUpdate();
		psmt.close();
		conn.close();
        
	    
	    
	    
	
	}
	catch(Exception e){
		
		BcwtsLogger.error("updateCustomTableOM" + Util.getFormattedStackTrace(e));
	}
	
	
}

public String getCustomerID(String custNum){
	
Statement stmt;
ResultSet rs;
Connection conn;
String custId = "";
String sql = "Select customer_id from ra_customers where customer_number ='"+custNum+"'";
try{
conn = getConnection();	
stmt = conn.createStatement();
rs = stmt.executeQuery(sql);
while(rs.next()){
	
	custId = rs.getString(1);
}
	
}
catch(Exception e){
	BcwtsLogger.error("getCustomerID: " +custNum+ Util.getFormattedStackTrace(e));
}
	
return custId;	
}

public String getInvoiveToID(String custId){
	
	Statement stmt;
	ResultSet rs;
	Connection conn;
	String invoiceToId = "";
	String sql = "select site_use.location invoice_to_org_id from hz_parties cust_party ," +
			"hz_cust_accounts cust , hz_party_sites party_sites , hz_locations loc , " +
			"hz_cust_site_uses_all site_use , hz_cust_acct_sites_all sites_all " +
" where 1=1 " +
" and cust.party_id = cust_party.party_id " +
" and party_sites.party_id = cust_party.party_id " +
" and party_sites.location_id = loc.location_id " +
" and site_use.cust_acct_site_id = sites_all.cust_acct_site_id " +
" and sites_all.party_site_id = party_sites.party_site_id" +
" and site_use.site_use_code = 'BILL_TO'" +
" and cust_party.party_id = (select party_id from ra_customers where customer_id = '"+custId+"')";
	try{
	conn = getConnection();	
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	while(rs.next()){
		
		invoiceToId = rs.getString(1);
	}
		
	}
	catch(Exception e){
		BcwtsLogger.error("getInvoiveToID" + Util.getFormattedStackTrace(e));
	}
		
	return invoiceToId;	
	}


public String getShipToID(String custId){
	
	Statement stmt;
	ResultSet rs;
	Connection conn;
	String shipToId = "";
	String sql = "select site_use.location ship_to_org_id " +
" from hz_parties cust_party ,hz_cust_accounts cust , hz_party_sites party_sites " +
" , hz_locations loc , hz_cust_site_uses_all site_use , hz_cust_acct_sites_all sites_all " +
" where 1=1 " +
" and cust.party_id = cust_party.party_id " +
" and party_sites.party_id = cust_party.party_id " + 
" and party_sites.location_id = loc.location_id " +
" and site_use.cust_acct_site_id = sites_all.cust_acct_site_id " +
" and sites_all.party_site_id = party_sites.party_site_id " +
" and site_use.site_use_code = 'SHIP_TO' " +
" and cust_party.party_id = (select party_id from ra_customers where customer_id = '"+custId+"')";
	try{
	conn = getConnection();	
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	while(rs.next()){
		
		shipToId = rs.getString(1);
	}
		
	}
	catch(Exception e){
		BcwtsLogger.error("getShipToID" + Util.getFormattedStackTrace(e));
	}
		
	return shipToId;	
	}


public static void main(String[] Args){
	
	BatchOMJobDAO batchOMJobDAO = new BatchOMJobDAO();
	try{
	//Util.getLastDateOfNextMonth();	
	System.out.println("Cust Id"+Util.getLastDateOfNextMonthOM());
//	batchOMJobDAO.updateCustomTableOM();
	}
	catch(Exception e){
		
		
	}
}

public void insertIntoOMIface() {

	Date javadate = new Date();

	try {
		Connection connection = getConnection();
		ResultSet resultSet;
		CallableStatement callStmt = (CallableStatement) connection
				.prepareCall("{call  BCWTS_ORACLE_OM_IFACE_PKG.PROCESS_RECORDS(?,?,?,?,?)}");

		/*
		 * P_IFACE_ID := 2; P_START_DATE := NULL; P_END_DATE := NULL;
		 * P_EXECUTION_TYPE := 'S';
		 */

		callStmt.setInt(1, 0);

		callStmt.setDate(2, null);// ;new
		// java.sql.Date(javadate.getTime()));

		callStmt.setDate(3, null); // java.sql.Date(javadate.getTime()));

		callStmt.setString(4, " ");

		callStmt.registerOutParameter(5, OracleTypes.VARCHAR);

		callStmt.executeQuery();
		resultSet = (ResultSet) callStmt.getObject(5);

		/*
		 * while(resultSet.next()){
		 * 
		 * // System.out.println("hello: "+resultSet.getString(5));
		 * 
		 * }
		 */
	} catch (Exception e) {
		BcwtsLogger.error("insertIntoOMIface" + Util.getFormattedStackTrace(e));
	}

}

}
