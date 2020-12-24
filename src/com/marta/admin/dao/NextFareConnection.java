package com.marta.admin.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import java.sql.Connection;

import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;

public class NextFareConnection {
	
	final static String ME = "NextWareConnection: ";
	public static String driverName = "";
	public static String dbPath = "";
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
	 if ((Constants.YES).equalsIgnoreCase(configParamDTO
				.getParamvalue())) {
		 driverName = rs.getString("driverName");
		 dbPath = rs.getString("DBPath");
		 user = rs.getString("user");
		 password = rs.getString("password");
	 }
	 else{
		 driverName = rs.getString("nextfare_driverName");
		 dbPath = rs.getString("nextfare_DBPath");
		 user = rs.getString("nextfare_user");
		 password = rs.getString("nextfare_password");
	 }
     
	}  
    //to get database connection
	/**This will get the Connection Object
	 * return Connection
	 */
	public static Connection getConnection() throws SQLException,Exception
	{
		setDatabaseDetails();
		final String MY_NAME = ME + "getConnection: ";
		BcwtsLogger.debug(MY_NAME+ " getting connection for nextware db "); 
		Connection con = null; 
		try{
		Class.forName(driverName);
	    String connectionURL = dbPath;
	    con = DriverManager.getConnection(connectionURL,user,password);
	    BcwtsLogger.debug(MY_NAME+ "got connection for nextware db "); 
	   }catch(SQLException  e){
		   BcwtsLogger.error(MY_NAME + e.getMessage());
			throw e;
	   }catch(Exception  ex){
		   BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
	   }
	   return con;
	}
	
	public static void closeConnection(Connection con){
		final String MY_NAME = ME + "closeConnection: ";
		try{
			con.close();
		}catch(SQLException e){
			BcwtsLogger.error(MY_NAME + e.getMessage());
		}
	     
	} 

}
