package com.marta.admin.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

import oracle.jdbc.OracleTypes;

import com.marta.admin.dto.BcwtOrderJobDTO;
import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;


public class BatchGLJobDAO {

	public static String driverName = " ";//"oracle.jdbc.driver.OracleDriver";
	public static String url = " ";//"jdbc:oracle:thin:@10.200.0.93:1521:NFT";
	public static String user = " ";//"bcwts";
	public static String password = " ";//"bcwts";

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
			BcwtsLogger.error("Class:BatchGLJobDAO Method: getConnection" +  Util.getFormattedStackTrace(ex));
			
		}
		return con;
	}

	public boolean isJobRuning(){
		String today = Util.getFormattedtoday();
		BcwtsLogger.info("Check if a job has already run today"+today);
		boolean isRunning = false;
		String query = "select interface_date from bcwts_to_oragl_iface bcwtgl " +
				"where BCWTGL.INTERFACE_DATE = to_date('"+today.toString()+"','DD-MM-YYYY')";
		try{
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				isRunning = true;
			}
			
		}catch(Exception e){
			
		}
		
		return isRunning;
	}
	
	
	/*
	 * Get the orders
	 */

	public ArrayList getOrdersIS(String orderDate) {

		ArrayList temp = new ArrayList();
		String query = "select count (productname), prd.productname Product, prd.price, ( prd.price * count (productname)) Total, fare_instrument_id from bcwtorder o,"
				+ " bcwtorderdetails od,bcwtbreezecardproduct bcp,bcwtproduct prd where o.orderid=od.orderid and o.ordertype='IS' and trunc(o.transactiondate) = trunc(to_date('"
				+ orderDate
				+ "','DD-MM-YYYY'))and od.orderdetailsid = bcp.orderdetailsid and prd.productid = bcp.productid group by prd.productname, prd.price, fare_instrument_id ";

		try {

			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			// System.out.println("Count Product    Price   Total");
			while (rs.next()) {
				BcwtOrderJobDTO bcwtOrderJobDTO = new BcwtOrderJobDTO();
				// System.out.println(rs.getString(1)+"  "+
				// rs.getString(2)+"  "+ rs.getString(3)+"  "+rs.getString(4));
				bcwtOrderJobDTO.setProductName(rs.getString(2));
				bcwtOrderJobDTO.setCount(rs.getInt(1));
				bcwtOrderJobDTO.setTotal(rs.getDouble(4));
				bcwtOrderJobDTO.setFare_instrument_id(rs.getString(5));
			//	bcwtOrderJobDTO.setLineType("S");
				temp.add(bcwtOrderJobDTO);
			}
			st.close();
			rs.close();
			conn.close();
		} catch (Exception e) {
            
			BcwtsLogger.error("Class:BatchGLJobDAO Method: getOrdersIS" +  Util.getFormattedStackTrace(e));
		} finally {

		}
		return temp;
	}

	public ArrayList getNewCardOrdersIS(String orderDate) {

		ArrayList temp = new ArrayList();
		String query = "SELECT COUNT(*) NewCards,sum(shippingamount) Shipping FROM bcwtorder WHERE ordertype = 'IS' AND patronaddressid IS NOT NULL and trunc(transactiondate) = trunc(to_date('"
				+ orderDate + "','DD-MM-YYYY')) ";

		try {

			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			double costOfBlankCard;
			ConfigurationCache configurationCache = new ConfigurationCache();
			BcwtConfigParamsDTO breezeCardValue = (BcwtConfigParamsDTO) configurationCache.configurationValues
					.get("IS_CARD_COST");
		 if (breezeCardValue.getParamvalue().equalsIgnoreCase("free"))
			costOfBlankCard = 0;
			 else
			costOfBlankCard = Integer.parseInt(breezeCardValue
			  .getParamvalue());
			 
			while (rs.next()) {
				if (null != rs.getString(1)) {
					BcwtOrderJobDTO bcwtOrderJobDTO = new BcwtOrderJobDTO();
					bcwtOrderJobDTO.setProductName("BreezeCard");
					bcwtOrderJobDTO.setFare_instrument_id("0");
					bcwtOrderJobDTO
							.setTotal((rs.getDouble(1) * costOfBlankCard));
				//	bcwtOrderJobDTO.setLineType("S");
					temp.add(bcwtOrderJobDTO);
				}
				if (null != rs.getString(2)) {
					BcwtOrderJobDTO bcwtOrderJobDTO = new BcwtOrderJobDTO();
					bcwtOrderJobDTO.setProductName("Shipping");
					bcwtOrderJobDTO.setFare_instrument_id("0");
					bcwtOrderJobDTO.setTotal(rs.getDouble(2));
				//	bcwtOrderJobDTO.setLineType("S");
					temp.add(bcwtOrderJobDTO);
				}
			}
			st.close();
			rs.close();
			conn.close();
		} catch (Exception e) {

			BcwtsLogger.error("Class:BatchGLJobDAO Method: getNewCardOrdersIS" +  Util.getFormattedStackTrace(e));
		} finally {

		}
		return temp;
	}

	public void updateCustomTable(ArrayList temp) {
		Connection conn;
		PreparedStatement pStmt;
		Statement stmt;
		ResultSet rs;
		Date javadate = new Date();
		String Code_Combination_id_dr;
		
		ConfigurationCache configurationCache = new ConfigurationCache();
		BcwtConfigParamsDTO breezeCardValue = (BcwtConfigParamsDTO) configurationCache.configurationValues
				.get("CODE_COMBINATION_ID_DR");
		
		
		  Code_Combination_id_dr = breezeCardValue.getParamvalue();
		 
		//Code_Combination_id_dr = "65017";
		
		for (Iterator iter = temp.iterator(); iter.hasNext();) {
			BcwtOrderJobDTO bcwtOrderJobDTO = new BcwtOrderJobDTO();
			bcwtOrderJobDTO = (BcwtOrderJobDTO) iter.next();
			if(bcwtOrderJobDTO.getTotal()!=0){
			try {
			
				conn = getConnection();
				stmt = conn.createStatement();
				rs = stmt
						.executeQuery("select CHART_OF_ACCOUNTS_ID,CODE_COMBINATION_ID from BCWT_PRODUCT_ACCNT_NUMS where fare_instrument_id = "
								+ bcwtOrderJobDTO.getFare_instrument_id()
								+ " or productname = '"
								+ bcwtOrderJobDTO.getProductName() + "'");
				while (rs.next()) {
					if (null != rs.getString(1)) {
						bcwtOrderJobDTO.setChatsOfAccountsId(rs.getString(1));
						
					}
					if (null != rs.getString(2)) {
						bcwtOrderJobDTO.setCodeCombinationId(rs.getString(2));
					}

				}

				pStmt = conn
						.prepareStatement("insert into bcwts_to_oragl_iface (INTERFACE_DATE,STATUS,ERROR_MESSAGE,SET_OF_BOOKS_ID,ACCOUNTING_DATE,CURRENCY_CODE,DATE_CREATED,CREATED_BY,ACTUAL_FLAG,USER_JE_CATEGORY_NAME,USER_JE_SOURCE_NAME,"
								+ "USER_CURRENCY_CONVERSION_TYPE,AVERAGE_JOURNAL_FLAG,AMOUNT,TRANSACTION_DATE,PERIOD_NAME,CHART_OF_ACCOUNTS_ID,FUNCTIONAL_CURRENCY_CODE,CODE_COMBINATION_ID_DR,"
								+ "CODE_COMBINATION_ID_CR,DATE_CREATED_IN_GL,STATUS_DESCRIPTION,STAT_AMOUNT,INVOICE_DATE,INVOICE_IDENTIFIER,REFERENCE_DATE, DISC_AMOUNT,CODE_COMBINATION_ID_DR_DISC) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				// pStmt.setInt(1,2);
				pStmt.setDate(1, new java.sql.Date(javadate.getTime()));
				pStmt.setString(2, "New");
				pStmt.setString(3, "");
				pStmt.setInt(4, 1001);
				pStmt.setDate(5, new java.sql.Date((Util.getFormattedYesterdayDate().getTime())));
				pStmt.setString(6, "USD");
				pStmt.setDate(7, new java.sql.Date((Util.getFormattedYesterdayDate().getTime())));
				pStmt.setString(8, "6");
				pStmt.setString(9, "A");
				pStmt.setString(10, "Web Ticketing");
				pStmt.setString(11, "Web Prepaid Sales");
				pStmt.setString(12, "USD");
				pStmt.setString(13, "");
				pStmt.setDouble(14, bcwtOrderJobDTO.getTotal());
				pStmt.setDate(15, new java.sql.Date((Util.getFormattedYesterdayDate().getTime())));
				pStmt.setString(16, Util.getPeriodName());
				pStmt.setString(17, bcwtOrderJobDTO.getChatsOfAccountsId());
				pStmt.setString(18, "USD");
				pStmt.setString(19, Code_Combination_id_dr);
				pStmt.setString(20, bcwtOrderJobDTO.getCodeCombinationId());
				pStmt.setDate(21, new java.sql.Date(javadate.getTime()));
				pStmt.setString(22, "");
				pStmt.setString(23, "");
				pStmt.setString(24, "");
				pStmt.setString(25, "");
				pStmt.setString(26, "");
				pStmt.setDouble(27,bcwtOrderJobDTO.getDiscountedAmount());
				pStmt.setString(28, "7267");
				pStmt.executeUpdate();
				pStmt.close();
				conn.close();
			} catch (Exception e) {
				BcwtsLogger.error("Class:BatchGLJobDAO Method: updateCustomTable" +  Util.getFormattedStackTrace(e));
			}
			}
		}

	}

	public  void insertIntoGLIface() {

		Date javadate = new Date();

		try {
			Connection connection = getConnection();
			ResultSet resultSet;
			CallableStatement callStmt = (CallableStatement) connection
					.prepareCall("{call  BCWTS_ORACLE_GL_IFACE_PKG.PROCESS_RECORDS(?,?,?,?,?)}");

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
            
			callStmt.close();
			connection.close();
			System.out.println("hello:");
			
			/*  while(resultSet.next()){
			  
			  System.out.println("hello: "+resultSet.getString(5));
			  
			  }*/
			 
		} catch (Exception e) {
			BcwtsLogger.error("Class:BatchGLJobDAO Method: insertIntoGLIface" +  Util.getFormattedStackTrace(e));
		}

	}

	public ArrayList getCashOrdersIS(String orderDate) {

		ArrayList temp = new ArrayList();
		String query = "select sum(cashvalue) from bcwtorderdetails where orderid in (select orderid from bcwtorder where trunc(transactiondate) = trunc(to_date('"
				+ orderDate + "','DD-MM-YYYY')))";

		try {

			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				if (null != rs.getString(1)) {
					BcwtOrderJobDTO bcwtOrderJobDTO = new BcwtOrderJobDTO();

					bcwtOrderJobDTO.setProductName("STORED VALUE");

					bcwtOrderJobDTO.setTotal(rs.getDouble(1));
				//	bcwtOrderJobDTO.setLineType("S");
					temp.add(bcwtOrderJobDTO);
				}
			}
			st.close();
			rs.close();
			conn.close();
		} catch (Exception e) {

			BcwtsLogger.error("Class:BatchGLJobDAO Method: getCashOrdersIS" +  Util.getFormattedStackTrace(e));
		} finally {

		}
		return temp;
	}

	public ArrayList getCashOrdersGBS(String orderDate) {

		ArrayList temp = new ArrayList();
		String query = "SELECT (SUM(cashvalue)* SUM(noofcards)) Total "
				+ " FROM bcwtorderinfo " + " WHERE cashvalue IS NOT NULL "
				+ " AND orderid     IN " + " (SELECT orderid "
				+ " FROM bcwtorder "
				+ " WHERE TRUNC(transactiondate) = TRUNC(to_date('" + orderDate
				+ "','DD-MM-YYYY'))) ";

		try {

			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				if (null != rs.getString(1)) {
					BcwtOrderJobDTO bcwtOrderJobDTO = new BcwtOrderJobDTO();

					bcwtOrderJobDTO.setProductName("STORED VALUE");
					bcwtOrderJobDTO.setTotal(rs.getDouble(1));
					
					temp.add(bcwtOrderJobDTO);
				}
			}
			st.close();
			rs.close();
			conn.close();
		} catch (Exception e) {

			BcwtsLogger.error("Class:BatchGLJobDAO Method: getCashOrdersGBS" +  Util.getFormattedStackTrace(e));
		} finally {

		}
		return temp;
	}

	public ArrayList getDiscountsGBS(String orderDate) {

		ArrayList temp = new ArrayList();
		String query = "SELECT sum(discountedamount) "
				+ " FROM bcwtorderinfo "
				+ " WHERE orderid IN "
				+ " (SELECT orderid "
				+ " FROM bcwtorder "
				+ " WHERE TRUNC(transactiondate) = TRUNC(to_date('"+orderDate+"','DD-MM-YYYY')))";

		try {

			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				BcwtOrderJobDTO bcwtOrderJobDTO = new BcwtOrderJobDTO();

				bcwtOrderJobDTO.setProductName("GBS Discount");

				bcwtOrderJobDTO.setTotal(rs.getDouble(1));
				bcwtOrderJobDTO.setLineType("D");
				temp.add(bcwtOrderJobDTO);
			}
			st.close();
			rs.close();
			conn.close();
		} catch (Exception e) {

			BcwtsLogger.error("Class:BatchGLJobDAO Method: getDiscountsGBS" +  Util.getFormattedStackTrace(e));
		} finally {

		}
		return temp;
	}

	public ArrayList getNewCardOrdersGBS(String orderDate) {

		ArrayList temp = new ArrayList();
		String query = "SELECT SUM(noofcards), " + " SUM(shippingamount) "
				+ " FROM bcwtorder bo, " + " bcwtorderinfo boi "
				+ "WHERE bo.ordertype = 'GBS' "
				+ "AND bo.orderid     = boi.orderid "
				+ "and trunc(transactiondate) = trunc(to_date('" + orderDate // change
																		// to
																		// tran
																		// date
				+ "','DD-MM-YYYY'))";

		try {

			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			double costOfBlankCard;
			ConfigurationCache configurationCache = new ConfigurationCache();
			BcwtConfigParamsDTO breezeCardValue = (BcwtConfigParamsDTO) configurationCache.configurationValues
					.get("GBS_CARD_VALUE");
			 if (breezeCardValue.getParamvalue().equalsIgnoreCase("free"))
			costOfBlankCard = 0;
			 else
			 costOfBlankCard = Integer.parseInt(breezeCardValue
			 .getParamvalue());
		//	costOfBlankCard = 1.00;
				while (rs.next()) {
					if (null != rs.getString(1)) {
						BcwtOrderJobDTO bcwtOrderJobDTO = new BcwtOrderJobDTO();
						bcwtOrderJobDTO.setProductName("BreezeCard");
						bcwtOrderJobDTO.setFare_instrument_id("0");
						bcwtOrderJobDTO
								.setTotal((rs.getDouble(1) * costOfBlankCard));
						
						temp.add(bcwtOrderJobDTO);
					}
					if (null != rs.getString(2)) {
						BcwtOrderJobDTO bcwtOrderJobDTO = new BcwtOrderJobDTO();
						bcwtOrderJobDTO.setProductName("Shipping");
						bcwtOrderJobDTO.setFare_instrument_id("0");
						bcwtOrderJobDTO.setTotal(rs.getDouble(2));
						
						temp.add(bcwtOrderJobDTO);
					}
			

			}
			st.close();
			rs.close();
			conn.close();
		} catch (Exception e) {

			BcwtsLogger.error("Class:BatchGLJobDAO Method: getNewCardOrdersGBS" +  Util.getFormattedStackTrace(e));
		} finally {

		}
		return temp;
	}

	public ArrayList getOrdersGBS(String orderDate) {

		ArrayList temp = new ArrayList();
		String query = "select " + " od.noofcards  , "
				+ " prd.productname Product, "
				+ " ( prd.price * noofcards ) Total, " + " fare_instrument_id, discountedamount"
				+ " from bcwtorder o, " + " bcwtorderinfo od, "
				+ " bcwtgbsproductdetails bgpd, " + " bcwtproduct prd "
				+ " where o.orderid=od.orderid " + " and o.ordertype = 'GBS' "
				+ " and trunc(o.transactiondate) = trunc(to_date('"
				+ orderDate
				+ "','DD-MM-YYYY')) " // chnage to tran date
				+ " and od.orderinfoid =bgpd.orderinfoid "
				+ " and prd.productid=bgpd.productid ";

		try {

			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			// System.out.println("Count Product    Price   Total");
			while (rs.next()) {
				BcwtOrderJobDTO bcwtOrderJobDTO = new BcwtOrderJobDTO();
				// System.out.println(rs.getString(1)+"  "+
				// rs.getString(2)+"  "+ rs.getString(3)+"  "+rs.getString(4));
				bcwtOrderJobDTO.setProductName(rs.getString(2));
				bcwtOrderJobDTO.setCount(rs.getInt(1));
				bcwtOrderJobDTO.setTotal(rs.getDouble(3));
				bcwtOrderJobDTO.setFare_instrument_id(rs.getString(4));
				bcwtOrderJobDTO.setDiscountedAmount(rs.getDouble(5));
			//	bcwtOrderJobDTO.setLineType("S");
				temp.add(bcwtOrderJobDTO);
			}
			st.close();
			rs.close();
			conn.close();
		} catch (Exception e) {

			BcwtsLogger.error("Class:BatchGLJobDAO Method: getOrdersGBS" +  Util.getFormattedStackTrace(e));
		} finally {
			
			
		}
		return temp;
	}
	
	public static void main(String args[]){
		
	//	insertIntoGLIface();
		
		
		
		
	}
	
	

}
