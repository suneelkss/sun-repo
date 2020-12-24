package com.marta.admin.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.ResourceBundle;

import com.marta.admin.dao.MartaBaseDAO;

public class EncryptDecryptPassword extends MartaBaseDAO {

	final static String ME = "EncryptDecryptPassword: ";

	public static String driverName = "";
	public static String url = "";
	public static String user = "";
	public static String password = ""; 
	
	private static void setDatabaseDetails(){
		 // to read nextware db path from properties file
			 ResourceBundle rs = ResourceBundle.getBundle("com.marta.admin.resources.nextfare");
			 driverName = rs.getString("driverName");
			 url = rs.getString("BCWTSDBPath");
			 user = rs.getString("BCWTSuser");
			 password = rs.getString("BCWTSpassword");
			// System.out.println("url: "+url);
	     
		} 
	
	
	
	/**
	 * To Encrypt String/Password
	 *
	 * @param encPwd
	 * @return encryptedPwd
	 * @throws Exception
	 */
	public static String encryptPassword(String encPwd) throws Exception {
		final String MY_NAME = ME + "encryptPassword: ";
		BcwtsLogger.debug(MY_NAME);
		String encryptedPwd = null;
		CallableStatement cs = null;
		Connection conn = null;

		try {
			conn = getConnection();

			if(cs == null) {
				cs = conn.prepareCall ("{?= call ENCRYPT_STRING(?)}");
			}

			cs.clearParameters();
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.setString(2, encPwd);
            cs.execute();
            encryptedPwd = cs.getString(1);
 		} catch (Exception ex) {
 			ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally{
			if(cs != null){
				cs.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		return encryptedPwd;
	}

	/**
	 * To Decrypt String/Password
	 *
	 * @param decPwd
	 * @return decryptedPwd
	 * @throws Exception
	 */
	public static String decryptPassword(String decPwd) throws Exception {
		final String MY_NAME = ME + "decryptPassword: ";
		BcwtsLogger.debug(MY_NAME);
		String decryptedPwd = null;

		CallableStatement cs = null;
		Connection conn = null;

		try {
			conn = getConnection();

			if(cs == null) {
				cs = conn.prepareCall ("{?= call DECRYPT_STRING(?)}");
			}

			cs.clearParameters();
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.setString(2, decPwd);
            cs.execute();
            decryptedPwd = cs.getString(1);
 		} catch (Exception ex) {
 			ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally{
			if(cs != null){
				cs.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		cs.close();
		conn.close();
		return decryptedPwd;
	}

	/*public static String driverName = "oracle.jdbc.driver.OracleDriver";
	public static String url = "jdbc:oracle:thin:@10.7.30.128:1521:nft";
	public static String user = "bcwts";
	public static String password = "bcwts";*/

	public static Connection getConnection() throws Exception {
		setDatabaseDetails();
		Connection con = null;
		try{
			Class.forName(driverName);
		    con = DriverManager.getConnection(url,user,password);
	   }catch(Exception  ex){
		   ex.printStackTrace();
	   }
	   return con;
	}	
}
