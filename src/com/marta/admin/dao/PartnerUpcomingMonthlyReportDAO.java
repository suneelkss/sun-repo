package com.marta.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dao.NextFareConnection;
import com.marta.admin.dto.BcwtDetailedOrderBenefitDTO;

import com.marta.admin.dto.BcwtPartneIdDTO;

import com.marta.admin.forms.ReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.MartaQueries;
import com.marta.admin.utils.Util;
import com.marta.admin.dto.UpCommingMonthBenefitDTO;
import com.marta.admin.hibernate.PartnerHotlistHistory;

import com.marta.admin.hibernate.BcwtPartnerNewCard;
import com.marta.admin.hibernate.PartnerBatchDetails;





public class PartnerUpcomingMonthlyReportDAO extends MartaBaseDAO {
	
	final String ME = "partnerUpcomingMonthlyReportDAO: ";
	/**
	 * Method to get PartnerId List
	 * @param form 
	 * @return List
	 * @throws Exception
	 */
	
	public List getPartnerIdList(ReportForm reportform) throws Exception{
		final String MY_NAME = ME + "getPartnerIdList: ";
		BcwtsLogger.debug(MY_NAME);
		List partnerIdList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List newCardDataList = null;
		BcwtPartneIdDTO orderReportDTO = null;
		String nextFareCompanyId = null;
		String partnerId = null;
		String benefitName = null;
		String partnerName = null;
		BcwtDetailedOrderBenefitDTO bcwtDetailedOrderBenefitDTO = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = MartaQueries.GET_PARTNER_UPCOMING_MONTHLY_REPORT_QUERY;
			if(reportform.getCompanyName() !=null &&
					!Util.isBlankOrNull(reportform.getCompanyName().toString())){
				query = query +"and lower(tmaInfo.tmaName) like '"+reportform.getCompanyName().toString().toLowerCase().trim() +"%'" ;
			}
			if(reportform.getSubCompanyName() !=null &&
					!Util.isBlankOrNull(reportform.getSubCompanyName().toString())){
				query = query +"and lower(partnerComp.companyName) like '"+reportform.getSubCompanyName().toString().toLowerCase().trim() +"%'" ;
			}
			newCardDataList = session.createQuery(query).list();
			if(newCardDataList !=null && !newCardDataList.isEmpty()){
				for(Iterator iter = newCardDataList.iterator();iter.hasNext();){
					//orderReportDTO = new BcwtPartneIdDTO();
					Object element[] = (Object[]) iter.next();
					if(null != element[1] && !Util.isBlankOrNull(element[1].toString())){
						BcwtsLogger.debug(" --------><----------------"+element[0]);
						nextFareCompanyId = element[1].toString(); 
						
					}
					
					if(null != element[0] && !Util.isBlankOrNull(element[0].toString()) &&
							null != element[1] && !Util.isBlankOrNull(element[1].toString())){
						String query1 = MartaQueries.GET_PARTNER_UPCOMING_MONTHLY_REPORT_PARTNERADMIN_QUERY +
					                " and partnerAdmin.partnerCompanyInfo.companyId = "+element[0];
						
						List partnerAdminList = session.createQuery(query1).list();
						if(partnerAdminList != null && !partnerAdminList.isEmpty()){
							for(Iterator it = partnerAdminList.iterator();it.hasNext();){
								orderReportDTO = new BcwtPartneIdDTO();
								Object[] partnerAdminObj = (Object[])it.next();
								if(partnerAdminObj[3] !=null && !Util.isBlankOrNull(partnerAdminObj[3].toString())){
									orderReportDTO.setCompanyId(nextFareCompanyId);
									partnerName = partnerAdminObj[2].toString();
							}
							
								orderReportDTO.setPartnerId(partnerName);
								partnerIdList.add(orderReportDTO);
						}
					}
				}
			}
				
			}
			
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got  list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return partnerIdList;
	}
	/*
	   * Method to get Upcoming Monthly Benefit Activivated Report
	   * 
	   */
	/**
	 * Method to get Upcoming Monthly Benefit Activivated Report
	 * @param nextfareCompanyId
	 * @return List
	 * @throws Exception
	 */

	
	

	
public List getUpcommingMonthlyReportListNew(Long partnerId,String region) throws Exception{
		
		final String MY_NAME = ME + "getUpcommingMonthlyReportList1:";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Connection con = null;
		ResultSet rs=null;
		Statement stmt = null;
		con = NextFareConnection.getConnection();
	//	PSListCardsDAO psDAO = new PSListCardsDAO();
	//	PartnerCompanyInfo partnerCompanyInfo = new PartnerCompanyInfo();
		try {
		//	partnerCompanyInfo = psDAO.getNextFareCustomerId(partnerId.toString());
			if(null != partnerId){
				String query ="select m.serial_nbr,m.first_name,m.last_name, m.customer_member_id, mb.benefit_action_id, mb.benefit_id, cbd.benefit_name, op.short_desc, op.fare_instrument_category_id, mb.last_benefit_delivery_dtm, INVENTORY.HOTLIST_ACTION, cc.org_name    "
   +" from nextfare_main.member m,nextfare_main.member_benefit mb, nextfare_main.customer_benefit_definition cbd,    "
    +" nextfare_main.fare_instrument fi, nextfare_main.fare_instrument_category op, NEXTFARE_MAIN.FARE_MEDIA_INVENTORY INVENTORY, nextfare_main.customer cc    "
   +"  where cc.customer_id = mb.customer_id and"
   + " cc.customer_id = cbd.customer_id "
   + " and cc.customer_id = m.customer_id "
   + " and mb.customer_id = cbd.customer_id     "
   +"  and m.member_id = mb.member_id    "
   +"  and m.customer_id = mb.customer_id    "
   +"  and cbd.fare_instrument_id = fi.fare_instrument_id    "
   +"  and fi.fare_instrument_category_id = op.fare_instrument_category_id     "
   +"  and mb.benefit_id = cbd.benefit_id   and m.benefit_status_id !='3' and "
   + " INVENTORY.SERIAL_NBR = m.SERIAL_NBR  "
   + "  and mb.benefit_action_id = '2' "
   +"  and mb.customer_id = '"+partnerId+"'";
				
				if (!region.equals("0")){
					if(region.equals("17"))
					query = query + " and op.fare_instrument_category_id in ('17','18') ";
					else
						query = query + " and op.fare_instrument_category_id = '"+ region+"' ";
				}
			
				stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if(rs != null){
				
				while(rs.next()){
				
										String memberName=rs.getString(2)+" "+rs.getString(3);
										UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
										if(!Util.isBlankOrNull(rs.getString(11))){
										if(!rs.getString(11).equals("0"))
										    upcommingmonthbenefitdto.setCardSerialNumber(rs.getString(1)+" "+"Hotlisted Card");
										else
											upcommingmonthbenefitdto.setCardSerialNumber(rs.getString(1));
										} else
											upcommingmonthbenefitdto.setCardSerialNumber(rs.getString(1));
										upcommingmonthbenefitdto.setMemberName(memberName);
										upcommingmonthbenefitdto.setMemberId(rs.getString(4));
										upcommingmonthbenefitdto.setBenefitId(rs.getString(6));
										upcommingmonthbenefitdto.setBenefitName(rs.getString(7));
										if(rs.getString(8).startsWith("CSC"))
											upcommingmonthbenefitdto.setRegion("MARTA");
										else
										   upcommingmonthbenefitdto.setRegion(rs.getString(8));
										upcommingmonthbenefitdto.setOperatorid(rs.getString(9));
										upcommingmonthbenefitdto.setFirstName(rs.getString(2));
										upcommingmonthbenefitdto.setLastName(rs.getString(3));
										upcommingmonthbenefitdto.setDateModified(rs.getDate(10));
										upcommingmonthbenefitdto.setCompanyName(rs.getString(12));
										
										
										
										if (null != rs.getString(5)) {
											// if(checkHotlist(rs.getString(1))){//NextFareMethods.IsHotlisted(rs.getString(1), "160")){
											
											if ("2".equals(rs.getString(5))) {

												upcommingmonthbenefitdto.setIsBenefitActivated("Active");
											} else {

												upcommingmonthbenefitdto.setIsBenefitActivated("Inactive");

										    }
											 
										}
											monthlySet.add(upcommingmonthbenefitdto);
					
					}
				
				
			}
		}
		}catch(Exception ex) {
			
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			rs.close();
			stmt.close();
			con.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return new ArrayList(monthlySet);
	}  
    
	
public List getUpcommingMonthlyReportList(Long partnerId) throws Exception{
		
		final String MY_NAME = ME + "getUpcommingMonthlyReportList1:";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Connection con = null;
		ResultSet rs=null;
		Statement stmt = null;
		con = NextFareConnection.getConnection();
	//	PSListCardsDAO psDAO = new PSListCardsDAO();
	//	PartnerCompanyInfo partnerCompanyInfo = new PartnerCompanyInfo();
		try {
		//	partnerCompanyInfo = psDAO.getNextFareCustomerId(partnerId.toString());
			if(null != partnerId){
				String query ="SELECT  MB.SERIAL_NBR,MB.FIRST_NAME,MB.LAST_NAME,MB.CUSTOMER_MEMBER_ID," +
				"MB.BENEFIT_STATUS_ID,INVENTORY.HOTLIST_ACTION FROM NEXTFARE_MAIN.MEMBER MB, " +
				"NEXTFARE_MAIN.FARE_MEDIA_INVENTORY INVENTORY WHERE MB.CUSTOMER_ID='"+partnerId+"'" +
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
											// if(checkHotlist(rs.getString(1))){//NextFareMethods.IsHotlisted(rs.getString(1), "160")){
											if(Util.isBlankOrNull(rs.getString(6))){
												  upcommingmonthbenefitdto.setIsBenefitActivated("Yes");
												}
												else if(!rs.getString(6).equals("0")){

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
			
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
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
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
	
		try{
			con = NextFareConnection.getConnection();
			
			String query="SELECT HOTLIST_ACTION from NEXTFARE_MAIN.FARE_MEDIA_INVENTORY where SERIAL_NBR=?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, serialNbr);
				
			rs= stmt.executeQuery();
			while(rs.next()){
				if(Util.isBlankOrNull(rs.getString(1))){
					  flag = false;
					}
					if(rs.getString(1).equals("0")) {
					 flag = false;

				   }
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


	/*public List getUpcommingMonthlyReportList(Long nextfareCompanyId) throws Exception{
			
			final String MY_NAME = ME + "getUpcommingMonthlyReportList1:";
			BcwtsLogger.debug(MY_NAME);
			List monthlySet = new ArrayList();
			Connection con = null;
			ResultSet rs=null;
			ResultSet r =null;
			Statement st =null;
			Statement stmt = null;
			con = NextFareConnection.getConnection();
			
			try {
				if(null != nextfareCompanyId){
				String query ="SELECT distinct(MBH.SERIAL_NBR),MBH.MEMBER_ID FROM NEXTFARE_MAIN.MEMBER_BENEFIT_HISTORY MBH WHERE CUSTOMER_ID="+nextfareCompanyId; // AND TO_DATE(MBH.INSERTED_DTM) <= TO_DATE('"+Util.getLastDateOfNextMonth()+"','mm/dd/yyyy') AND TO_DATE(MBH.EXPIRATION_DTM) <= TO_DATE('"+Util.getLastDateOfNextMonth()+"','mm/dd/yyyy')";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				if(rs != null){
					
					while(rs.next()){
						
						String qry = "SELECT  MB.SERIAL_NBR,MB.FIRST_NAME,MB.LAST_NAME,MB.CUSTOMER_MEMBER_ID,MB.BENEFIT_STATUS_ID FROM NEXTFARE_MAIN.MEMBER MB WHERE MB.CUSTOMER_ID="+nextfareCompanyId+" AND MB.MEMBER_ID="+rs.getString(2);
						 st = con.createStatement();
						 r = st.executeQuery(qry);
						
							
									if(r !=null)
									{
											while(r.next()){
											String memberName=r.getString(2)+" "+r.getString(3);
											PartnerUpcomingMonthlyReportDTO upcommingmonthbenefitdto = new PartnerUpcomingMonthlyReportDTO();
											upcommingmonthbenefitdto.setCardSerialNumber(r.getString(1));
											upcommingmonthbenefitdto.setMemberName(memberName);
											upcommingmonthbenefitdto.setMemberId(r.getString(4));
											if (null != r.getString(5)) {
												 if(NextFareMethods.IsHotlisted(r.getString(1), "160")){
													 upcommingmonthbenefitdto.setIsBenefitActivated("No");	 
												 }
												 else{
												if ("1".equals(r.getString(5))) {
													upcommingmonthbenefitdto.setIsBenefitActivated("Yes");
												} else {
													upcommingmonthbenefitdto.setIsBenefitActivated("No");
												
											    }
												 }
											}
												monthlySet.add(upcommingmonthbenefitdto);
										}
									}
									r.close();
									st.close();
						}
					
					
				}
			}
			}catch(Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;
			} finally {
				rs.close();
				stmt.close();
				con.close();
				BcwtsLogger.debug(MY_NAME + " Resources closed");
			}
			return monthlySet;
		}*/
	
	public List getUpcommingActivateReportList(Long nextfareCompanyId) throws Exception{
		
		final String MY_NAME = ME + "getActiveBenefitsList:";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Connection con = null;
		ResultSet rs=null;
		con = NextFareConnection.getConnection();
		Statement stmt = null;
		try {
			if(null != nextfareCompanyId){
			String query ="SELECT MBH.SERIAL_NBR,MBH.MEMBER_ID FROM NEXTFARE_MAIN.MEMBER_BENEFIT_HISTORY MBH WHERE CUSTOMER_ID="+nextfareCompanyId+" AND TO_DATE(MBH.INSERTED_DTM) >= TO_DATE('"+Util.getFirstDateOfNextMonth()+"','mm/dd/yyyy') AND TO_DATE(MBH.INSERTED_DTM) <= TO_DATE('"+Util.getLastDateOfNextMonth()+"','mm/dd/yyyy')";
			stmt = con.createStatement();
				rs = stmt.executeQuery(query);
			if(rs != null){
				
				while(rs.next()){
					UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
				upcommingmonthbenefitdto.setCardSerialNumber(rs.getString(1));
				upcommingmonthbenefitdto.setMemberId(rs.getString(2));
				monthlySet.add(upcommingmonthbenefitdto);
					}
			}
			}
		}catch(Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			rs.close();
			stmt.close();
			con.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return new ArrayList(monthlySet);
	}
		
	public List getUpcommingMonthlyDeactivateList(Long nextfareCompanyId) throws Exception{
		final String MY_NAME = ME + "getActiveBenefitsList:";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Connection con = null;
		ResultSet rs=null;
		con = NextFareConnection.getConnection();
		Statement stmt = null;
		try {
			if(null != nextfareCompanyId){
			String query ="SELECT MBH.SERIAL_NBR,MBH.MEMBER_ID FROM NEXTFARE_MAIN.MEMBER_BENEFIT_HISTORY MBH WHERE CUSTOMER_ID="+nextfareCompanyId+" AND TO_DATE(MBH.EXPIRATION_DTM) >= TO_DATE('"+Util.getFirstDateOfNextMonth()+"','mm/dd/yyyy') AND TO_DATE(MBH.EXPIRATION_DTM) <= TO_DATE('"+Util.getLastDateOfNextMonth()+"','mm/dd/yyyy')";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if(rs != null){
			while(rs.next()){
				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
				upcommingmonthbenefitdto.setCardSerialNumber(rs.getString(1));
				upcommingmonthbenefitdto.setMemberId(rs.getString(2));
				monthlySet.add(upcommingmonthbenefitdto);
			}
			}
			}
		}catch(Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			rs.close();
			stmt.close();
			con.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return new ArrayList(monthlySet);
	}
	
	public List getHotlistCardDetails(Long companyIdStr) throws Exception {
		final String MY_NAME = ME + "getHotlistCardDetails: "+companyIdStr;
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Session session = null;
		Transaction transaction = null;
			
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from PartnerHotlistHistory partnerHotlistHistory where partnerHotlistHistory.companyName in (select partnercompanyinfo.companyName from PartnerCompanyInfo partnercompanyinfo where partnercompanyinfo.companyId in ("+companyIdStr+"))";			
			List queryList = session.createQuery(query).list();			
			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				PartnerHotlistHistory partnerHotlistHistory = (PartnerHotlistHistory) iter.next();				
				
				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
				upcommingmonthbenefitdto.setCardSerialNumber(partnerHotlistHistory.getCardNumber()+" queue");
				upcommingmonthbenefitdto.setMemberName(partnerHotlistHistory.getFirstName()+" "+partnerHotlistHistory.getLastName());
				upcommingmonthbenefitdto.setMemberId(partnerHotlistHistory.getMemberId());
				upcommingmonthbenefitdto.setBenefitId(partnerHotlistHistory.getBenefitId());
				upcommingmonthbenefitdto.setFirstName(partnerHotlistHistory.getFirstName());
				upcommingmonthbenefitdto.setLastName(partnerHotlistHistory.getLastName());
				upcommingmonthbenefitdto.setCompanyName(partnerHotlistHistory.getCompanyName());
				
				
	
				if("yes".equals(partnerHotlistHistory.getUnhotlistFlag())){
						upcommingmonthbenefitdto.setIsBenefitActivated("Active");
						upcommingmonthbenefitdto.setDateModified(partnerHotlistHistory.getUnhotlistReqDate());
				}else{
					upcommingmonthbenefitdto.setDateModified(partnerHotlistHistory.getHotlistReqDate());
					upcommingmonthbenefitdto.setIsBenefitActivated("Inactive");
				}
				monthlySet.add(upcommingmonthbenefitdto);
			}
			
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got hotlist card details list from DB ");
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
	public List getNewCardDetails(Long partnerId) throws Exception {
		final String MY_NAME = ME + "getNewCardDetails: "+partnerId;
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Session session = null;
		Transaction transaction = null;
		
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtPartnerNewCard bcwtPartnerNewCard where bcwtPartnerNewCard.companyname in (select partnercompanyinfo.companyName from PartnerCompanyInfo partnercompanyinfo where partnercompanyinfo.companyId in ("+partnerId+"))";	
			
			List queryList = session.createQuery(query).list();
			
			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				BcwtPartnerNewCard bcwtPartnerNewCard = (BcwtPartnerNewCard) iter.next();
				if(null != bcwtPartnerNewCard.getBenefitid()){
				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
				upcommingmonthbenefitdto.setCardSerialNumber(bcwtPartnerNewCard.getSerialnumber()+" New Card");
				upcommingmonthbenefitdto.setMemberName(bcwtPartnerNewCard.getFirstname()+" "+bcwtPartnerNewCard.getLastname());
				upcommingmonthbenefitdto.setMemberId(bcwtPartnerNewCard.getCustomermemberid().toString());
				upcommingmonthbenefitdto.setBenefitId(bcwtPartnerNewCard.getBenefitid().toString());
				upcommingmonthbenefitdto.setDateModified(bcwtPartnerNewCard.getDate_added());
				upcommingmonthbenefitdto.setCompanyName(bcwtPartnerNewCard.getCompanyname());
				
				monthlySet.add(upcommingmonthbenefitdto);
				}
				if(null != bcwtPartnerNewCard.getBenefitid2()){
					UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
					upcommingmonthbenefitdto.setCardSerialNumber(bcwtPartnerNewCard.getSerialnumber()+" New Card");
					upcommingmonthbenefitdto.setMemberName(bcwtPartnerNewCard.getFirstname()+" "+bcwtPartnerNewCard.getLastname());
					upcommingmonthbenefitdto.setMemberId(bcwtPartnerNewCard.getCustomermemberid().toString());
					upcommingmonthbenefitdto.setBenefitId(bcwtPartnerNewCard.getBenefitid2().toString());
					upcommingmonthbenefitdto.setFirstName(bcwtPartnerNewCard.getFirstname());
					upcommingmonthbenefitdto.setLastName(bcwtPartnerNewCard.getLastname());
					upcommingmonthbenefitdto.setDateModified(bcwtPartnerNewCard.getDate_added());
					upcommingmonthbenefitdto.setCompanyName(bcwtPartnerNewCard.getCompanyname());
					monthlySet.add(upcommingmonthbenefitdto);
					}
				if(null != bcwtPartnerNewCard.getBenefitid3()){
					UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
					upcommingmonthbenefitdto.setCardSerialNumber(bcwtPartnerNewCard.getSerialnumber()+" New Card");
					upcommingmonthbenefitdto.setMemberName(bcwtPartnerNewCard.getFirstname()+" "+bcwtPartnerNewCard.getLastname());
					upcommingmonthbenefitdto.setMemberId(bcwtPartnerNewCard.getCustomermemberid().toString());
					upcommingmonthbenefitdto.setBenefitId(bcwtPartnerNewCard.getBenefitid3().toString());
					upcommingmonthbenefitdto.setFirstName(bcwtPartnerNewCard.getFirstname());
					upcommingmonthbenefitdto.setLastName(bcwtPartnerNewCard.getLastname());
					upcommingmonthbenefitdto.setDateModified(bcwtPartnerNewCard.getDate_added());
					upcommingmonthbenefitdto.setCompanyName(bcwtPartnerNewCard.getCompanyname());
					monthlySet.add(upcommingmonthbenefitdto);
					}
				if(null != bcwtPartnerNewCard.getBenefitid4()){
					UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
					upcommingmonthbenefitdto.setCardSerialNumber(bcwtPartnerNewCard.getSerialnumber()+" New Card");
					upcommingmonthbenefitdto.setMemberName(bcwtPartnerNewCard.getFirstname()+" "+bcwtPartnerNewCard.getLastname());
					upcommingmonthbenefitdto.setMemberId(bcwtPartnerNewCard.getCustomermemberid().toString());
					upcommingmonthbenefitdto.setBenefitId(bcwtPartnerNewCard.getBenefitid4().toString());
					upcommingmonthbenefitdto.setFirstName(bcwtPartnerNewCard.getFirstname());
					upcommingmonthbenefitdto.setLastName(bcwtPartnerNewCard.getLastname());
					upcommingmonthbenefitdto.setDateModified(bcwtPartnerNewCard.getDate_added());
					upcommingmonthbenefitdto.setCompanyName(bcwtPartnerNewCard.getCompanyname());
					monthlySet.add(upcommingmonthbenefitdto);
					}
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
	
	public List getBatchDetailsList(Long companyIdStr) throws Exception {
		final String MY_NAME = ME + "PartnerBatchDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Session session = null;
		Transaction transaction = null;
			
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from PartnerBatchDetails partnerBatchDetails where partnerBatchDetails.partnerId = "+companyIdStr;			
			List queryList = session.createQuery(query).list();			
			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				PartnerBatchDetails partnerBatchDetails = (PartnerBatchDetails) iter.next();				
				
				if(null !=partnerBatchDetails.getBenefitsId()){
				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
				upcommingmonthbenefitdto.setCardSerialNumber(partnerBatchDetails.getCardSerialNo()+" queue");
				upcommingmonthbenefitdto.setMemberName(partnerBatchDetails.getFirstName()+" "+partnerBatchDetails.getLastName());
				upcommingmonthbenefitdto.setMemberId(partnerBatchDetails.getCustomerMemberId());
				upcommingmonthbenefitdto.setBenefitId(partnerBatchDetails.getBenefitsId());
				upcommingmonthbenefitdto.setFirstName(partnerBatchDetails.getFirstName());
				upcommingmonthbenefitdto.setLastName(partnerBatchDetails.getLastName());
				upcommingmonthbenefitdto.setCardAction(partnerBatchDetails.getAction());
				upcommingmonthbenefitdto.setDateModified(partnerBatchDetails.getDate_added());
				
				if("Activate".equals(partnerBatchDetails.getBenefitactionid()))
						upcommingmonthbenefitdto.setIsBenefitActivated("Active");
				else
					upcommingmonthbenefitdto.setIsBenefitActivated("Inactive");
				
				monthlySet.add(upcommingmonthbenefitdto);
				}
			
				
				if(null !=partnerBatchDetails.getBenefitsId2()){
					if( !partnerBatchDetails.getBenefitsId2().equals("0")){
				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
				upcommingmonthbenefitdto.setCardSerialNumber(partnerBatchDetails.getCardSerialNo()+" queue");
				upcommingmonthbenefitdto.setMemberName(partnerBatchDetails.getFirstName()+" "+partnerBatchDetails.getLastName());
				upcommingmonthbenefitdto.setMemberId(partnerBatchDetails.getCustomerMemberId());
				upcommingmonthbenefitdto.setBenefitId(partnerBatchDetails.getBenefitsId2());
	
				if("Activate".equals(partnerBatchDetails.getBenefitactionid()))
						upcommingmonthbenefitdto.setIsBenefitActivated("Active");
				else
					upcommingmonthbenefitdto.setIsBenefitActivated("Inactive");
				
				monthlySet.add(upcommingmonthbenefitdto);
					}
				}
				
				if(null !=partnerBatchDetails.getBenefitsId3()){
					if( !partnerBatchDetails.getBenefitsId3().equals("0")){
					UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
					upcommingmonthbenefitdto.setCardSerialNumber(partnerBatchDetails.getCardSerialNo()+" queue");
					upcommingmonthbenefitdto.setMemberName(partnerBatchDetails.getFirstName()+" "+partnerBatchDetails.getLastName());
					upcommingmonthbenefitdto.setMemberId(partnerBatchDetails.getCustomerMemberId());
					upcommingmonthbenefitdto.setFirstName(partnerBatchDetails.getFirstName());
					upcommingmonthbenefitdto.setLastName(partnerBatchDetails.getLastName());
					upcommingmonthbenefitdto.setBenefitId(partnerBatchDetails.getBenefitsId3());
		
					if("Activate".equals(partnerBatchDetails.getBenefitactionid()))
							upcommingmonthbenefitdto.setIsBenefitActivated("Active");
					else
						upcommingmonthbenefitdto.setIsBenefitActivated("Inactive");
					
					monthlySet.add(upcommingmonthbenefitdto);
					}
				}
				if(null !=partnerBatchDetails.getBenefitsId4()){
					if( !partnerBatchDetails.getBenefitsId4().equals("0")){
					UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
					upcommingmonthbenefitdto.setCardSerialNumber(partnerBatchDetails.getCardSerialNo()+" queue");
					upcommingmonthbenefitdto.setMemberName(partnerBatchDetails.getFirstName()+" "+partnerBatchDetails.getLastName());
					upcommingmonthbenefitdto.setFirstName(partnerBatchDetails.getFirstName());
					upcommingmonthbenefitdto.setLastName(partnerBatchDetails.getLastName());
					upcommingmonthbenefitdto.setMemberId(partnerBatchDetails.getCustomerMemberId());
					upcommingmonthbenefitdto.setBenefitId(partnerBatchDetails.getBenefitsId4());
		
					if("Activate".equals(partnerBatchDetails.getBenefitactionid()))
							upcommingmonthbenefitdto.setIsBenefitActivated("Active");
					else
						upcommingmonthbenefitdto.setIsBenefitActivated("Inactive");
					
					monthlySet.add(upcommingmonthbenefitdto);
					}
				}
			}
			
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got  partnerBatchDetails list from DB :" +companyIdStr);
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
		
	
	
}
