package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.sql.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.AddNewProductForm;
import com.marta.admin.forms.LoggingCallsForm;
import com.marta.admin.hibernate.BcwtConfigParams;
import com.marta.admin.hibernate.BcwtLogCall;
import com.marta.admin.hibernate.BcwtRegion;
import com.marta.admin.hibernate.PartnerCardOrder;
import com.marta.admin.hibernate.PartnerCardOrderStatus;
import com.marta.admin.hibernate.BcwtProduct;
import com.marta.admin.hibernate.BcwtOrderStatus;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.business.LogManagement;
import com.marta.admin.dto.AddNewProductDTO;
import com.marta.admin.dto.ApplicationSettingsDTO;
import com.marta.admin.dto.BcwtLogDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.Util;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;



public class AddNewProductDAO extends MartaBaseDAO {

	final String ME = "AddNewProductDAO: ";
	
	
	 public AddNewProductDTO getAddNewProductDetails(AddNewProductDTO addNewProductDTO) throws Exception {
	
	final String MY_NAME = ME + " updateIsOrderDetails: ";
	BcwtsLogger.debug(MY_NAME + " updating IS Order ");
	boolean isUpdated = false;
	Session session = null;
	Transaction transaction = null;
	BcwtProduct bcwtProduct=null;
	AddNewProductDTO aaddNewProductDTO =new AddNewProductDTO();
	List stateListToIterate= new ArrayList();
	Long productId=null;
	String hiberDb=null;
	Long proId = null;
	
	
	List fareInstrumentList = new ArrayList();
	List listToIterate= new ArrayList();
	
	boolean id = false;
	List nextFareList = new ArrayList();
	String nextFareId = "";
	String wer=addNewProductDTO.getFareInstrumentId();
	
	String checkQuery = "";
	try {
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(!Util.isBlankOrNull(addNewProductDTO.getFareInstrumentId())){
			checkQuery = "SELECT FI.SHORT_DESC, FI.DESCRIPTION,"
				  +" FI.RIDER_CLASS, PCFI.PRICE FROM NEXTFARE_MAIN.FARE_INSTRUMENT FI, NEXTFARE_MAIN.PROD_CATALOG_FARE_INST PCFI"
				  +" where FI.FARE_INSTRUMENT_ID='"+addNewProductDTO.getFareInstrumentId()+"' AND PCFI.FARE_INSTRUMENT_ID=FI.FARE_INSTRUMENT_ID";
		}
		nextFareConnection = NextFareConnection.getConnection();
		if(nextFareConnection != null) {
			pstmt = nextFareConnection.prepareStatement(checkQuery);
			if(pstmt != null) {
				rs = pstmt.executeQuery();
			}
		}
		
		/*while(rs.next()==false)
		{
			nextFareId = Constants.SUCCESS;			
			
			if(!Util.isBlankOrNull(rs.getString("SHORT_DESC"))){
				addNewProductDTO.setProductName(rs.getString("SHORT_DESC"));
			}
			
			if(!Util.isBlankOrNull(rs.getString("DESCRIPTION"))){
				addNewProductDTO.setProductDescription(rs.getString("DESCRIPTION"));
			}
			
			if(!Util.isBlankOrNull(rs.getString("RIDER_CLASS"))){
				addNewProductDTO.setRiderClassification(rs.getString("RIDER_CLASS"));
			}
			
		   if(!Util.isBlankOrNull(rs.getString("PRICE"))){
				addNewProductDTO.setPrice(rs.getString("PRICE"));
			}
			
			addNewProductDTO.setCheck(nextFareId);
			rs.close();
			pstmt.close();
			nextFareConnection.close();*/
			
		if(rs.next()== id){
			nextFareId = Constants.WARNING;
			addNewProductDTO.setCheck(nextFareId);
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			return addNewProductDTO;
		     }else{			
			nextFareId = Constants.SUCCESS;			
				
			if(!Util.isBlankOrNull(rs.getString("SHORT_DESC"))){
				addNewProductDTO.setProductName(rs.getString("SHORT_DESC"));
			}
			
			if(!Util.isBlankOrNull(rs.getString("DESCRIPTION"))){
				addNewProductDTO.setProductDescription(rs.getString("DESCRIPTION"));
			}
			
			if(!Util.isBlankOrNull(rs.getString("RIDER_CLASS"))){
				addNewProductDTO.setRiderClassification(rs.getString("RIDER_CLASS"));
			}
			
		   if(!Util.isBlankOrNull(rs.getString("PRICE"))){
				addNewProductDTO.setPrice(rs.getString("PRICE"));
			}
			
			addNewProductDTO.setCheck(nextFareId);
			rs.close();
			pstmt.close();
			nextFareConnection.close();
			
		}
		
		/*nextFareId = Constants.WARNING;
		addNewProductDTO.setCheck(nextFareId);
		rs.close();
		pstmt.close();
		nextFareConnection.close();
		return addNewProductDTO;*/
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
				
	try {
		BcwtProduct bcwtProduct2 = new BcwtProduct();
		if (Util.equalsIgnoreCase(nextFareId, Constants.SUCCESS)) {
			session = getSession();
			bcwtProduct = new BcwtProduct();
			BcwtsLogger.info("Got the session");
			transaction = session.beginTransaction();
			BcwtsLogger.info("Got the transaction");
			Long ids = Long.valueOf(addNewProductDTO.getFareInstrumentId());
			String query = "from BcwtProduct where fare_instrument_id = "+ids;
					

			//nextFareList = (List) session.createQuery(query).list();
			bcwtProduct2 =  (BcwtProduct) session.createQuery(query).uniqueResult();
			
			transaction.commit();
			session.flush();
			session.close();
			}
		
		   if(bcwtProduct2==null){
			System.out.println("KIKK");
			nextFareId = Constants.Pass;
			addNewProductDTO.setCheck(nextFareId);
		     }
		   else{
			nextFareId = Constants.Fail;
			addNewProductDTO.setCheck(nextFareId);
			return addNewProductDTO;
			
		}
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}	
	
	try{
	if(Util.equalsIgnoreCase(nextFareId, Constants.Pass)){
		session = getSession();
		BcwtsLogger.info("Got the session");
		transaction = session.beginTransaction();
		BcwtsLogger.info("Got the transaction");
		bcwtProduct = new BcwtProduct();
		
		if(addNewProductDTO.getFareInstrumentId()!=null ){
			bcwtProduct.setFare_instrument_id(Long.valueOf(addNewProductDTO.getFareInstrumentId()));				
		}
		
		if(addNewProductDTO.getRegionId()!=null ){
			//bcwtProduct.setRiderclassification(Integer.valueOf(addNewProductDTO.getRegionId()));	
			BcwtRegion bcwtRegion = new BcwtRegion();
			bcwtRegion.setRegionid(Long.valueOf(addNewProductDTO.getRegionId()));
			bcwtProduct.setBcwtregion(bcwtRegion);
		}
		if(addNewProductDTO.getProductName()!=null ){
			bcwtProduct.setProductname(addNewProductDTO.getProductName());				
		}
		
		if(addNewProductDTO.getProductDescription()!=null ){
			bcwtProduct.setProductdescription(addNewProductDTO.getProductDescription());				
		}
		
		if(addNewProductDTO.getProductDetailDescription()!=null ){
			bcwtProduct.setProductdetaildesc((addNewProductDTO.getProductDetailDescription()));				
		}
		
		if(addNewProductDTO.getRiderClassification()!=null ){
			bcwtProduct.setRiderclassification(Integer.valueOf((addNewProductDTO.getRiderClassification())));				
		}
		
		if(addNewProductDTO.getPrice()!=null ){
			bcwtProduct.setPrice(Double.valueOf(addNewProductDTO.getPrice()));				
		}
		else
 		{
 			bcwtProduct.setPrice(new Double(0));
 		}
		
		bcwtProduct.setActivestatus(Constants.YES);
		
		
		session.save(bcwtProduct);
		
		transaction.commit();
		session.flush();
		session.close();
		
	}
	}
	catch (Exception e) {
		e.printStackTrace();
		
	}
	return addNewProductDTO;
}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public List getRegion() throws Exception {
			final String MY_NAME = ME + "getState: ";
			BcwtsLogger.debug(MY_NAME);
			List regionList = new ArrayList();
			Session session = null;
			Transaction transaction = null;
			try {
				session = getSession();
				transaction = session.beginTransaction();
				String query = "select regionid, regionname from BcwtRegion order by regionname";
				List statusListToIterate = session.createQuery(query).list();
				for (Iterator iter = statusListToIterate.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					regionList.add(new LabelValueBean(String.valueOf(element[1]),
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
			return regionList;
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 /*public List getRider() throws Exception {
			final String MY_NAME = ME + "getStatus: ";
			BcwtsLogger.debug(MY_NAME);
			List riderClassificationList = new ArrayList();
			List stateListToIterate= new ArrayList();
			
			Connection nextFareConnection = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			

			try {
				
				
				String query ="select RC.RIDER_CLASS from NEXTFARE_MAIN.RIDER_CLASSIFICATION RC ORDER BY RIDER_CLASS" ;
				
				//String query = "select registrationStatusid,registrationStatus "
						//+ " from PartnerRegistrationStatus order by registrationStatusid ";
				
				//List stateListToIterate = session.createQuery(query).list();
				
				nextFareConnection = NextFareConnection.getConnection();
				
				if(nextFareConnection != null) {
					pstmt = nextFareConnection.prepareStatement(query);
					if(pstmt != null) {
						rs = pstmt.executeQuery();
					}
				}
				while(rs.next()){
				stateListToIterate.add(rs.getString("RIDER_CLASS"));
				}
				
				
				
				for (Iterator iter = stateListToIterate.iterator(); iter.hasNext();) {
					//Object[] element = (Object[]) iter.next();
					//Long id = (Long) iter.next();
					String idStr = (String) iter.next();
					riderClassificationList.add(new LabelValueBean(String.valueOf(idStr),
							String.valueOf(idStr)));
				}
				
				BcwtsLogger.info(MY_NAME + "got Status list from DB ");
			} catch (Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;
			} finally {
				rs.close();
				pstmt.close();
				nextFareConnection.close();
				
				BcwtsLogger.debug(MY_NAME + " Resources closed");
			}
			return riderClassificationList;
		}
	 public List getFareInstrument() throws Exception {
			final String MY_NAME = ME + "getStatus: ";
			BcwtsLogger.debug(MY_NAME);
			List fareInstrumentList = new ArrayList();
			List stateListToIterate= new ArrayList();
			
			Connection nextFareConnection = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			

			try {
				
				
				String query ="select FI.FARE_INSTRUMENT_ID from NEXTFARE_MAIN.FARE_INSTRUMENT FI ORDER BY FARE_INSTRUMENT_ID" ;
				
				//String query = "select registrationStatusid,registrationStatus "
						//+ " from PartnerRegistrationStatus order by registrationStatusid ";
				
				//List stateListToIterate = session.createQuery(query).list();
				
				nextFareConnection = NextFareConnection.getConnection();
				
				if(nextFareConnection != null) {
					pstmt = nextFareConnection.prepareStatement(query);
					if(pstmt != null) {
						rs = pstmt.executeQuery();
					}
				}
				while(rs.next()){
				stateListToIterate.add(rs.getString("FARE_INSTRUMENT_ID"));
				}
				
				
				
				for (Iterator iter = stateListToIterate.iterator(); iter.hasNext();) {
					//Object[] element = (Object[]) iter.next();
					//Long id = (Long) iter.next();
					String idStr = (String) iter.next();
					fareInstrumentList.add(new LabelValueBean(String.valueOf(idStr),
							String.valueOf(idStr)));
				}
				
				BcwtsLogger.info(MY_NAME + "got Status list from DB ");
			} catch (Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;
			} finally {
				rs.close();
				pstmt.close();
				nextFareConnection.close();
				
				BcwtsLogger.debug(MY_NAME + " Resources closed");
			}
			return fareInstrumentList;
		}*/
}