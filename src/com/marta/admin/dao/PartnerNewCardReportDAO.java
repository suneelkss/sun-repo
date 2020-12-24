package com.marta.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.BcwtNewCardBenefitDTO;
import com.marta.admin.dto.BcwtPartneIdDTO;
import com.marta.admin.dto.PartnerMonthlyUsageReportDTO;
import com.marta.admin.dto.PartnerNewCardReportAdminDetailsDTO;
import com.marta.admin.dto.PartnerNewCardReportDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.PartnerNewCardReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.MartaQueries;
import com.marta.admin.utils.Util;
//import com.marta.admin.dao.NextFareConnection;
import com.marta.admin.dto.PartnerNewCardReportDTO;
/**
 * @author Sowjanya
 * 
 */
public class PartnerNewCardReportDAO extends MartaBaseDAO{
	final String ME = "PartnerNewCardReportDAO: ";
	/**
	 * Method to get Partner New Card Report 
	 * @param form 
	 * @return List
	 * @throws Exception
	 */
	public List getPartnerNewCardReportList(PartnerNewCardReportForm partnerNewCardReportForm) throws Exception{
		final String MY_NAME = ME + "getPartnerNewCardReportList: ";		
		BcwtsLogger.debug(MY_NAME);
		List nextfareCompanyIdList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		PartnerNewCardReportDTO partnerNewCardReportDTO = null;
		PartnerNewCardReportAdminDetailsDTO partnerNewCardReportAdminDetailsDTO=null;
		String nextFareCompanyId = null;
		boolean flag = false;
		List benefitList = new ArrayList();
		List collectionList = new ArrayList();
		String companyId=null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String queryForGetCompanyId = null;
			if(null != partnerNewCardReportForm){
				if(!Util.isBlankOrNull(partnerNewCardReportForm.getReportFormat())){
					flag = true;
					queryForGetCompanyId =" select " +
												"partnerComp.nextfareCompanyId," +
												"tmaComp.id.companyId," +
												"partnerComp.companyName, "+
												" tmaInfo.tmaName "+
											" from " +
												"TmaInformation tmaInfo," +
												"TmaCompanyLink tmaComp , " +
												"PartnerCompanyInfo partnerComp "+
											" where " +
												"tmaInfo.tmaId = tmaComp.id.tmaId " +
												"and partnerComp.companyId = tmaComp.id.companyId ";
				}
				if(!Util.isBlankOrNull(partnerNewCardReportForm.getTmaName()) && flag == true){
					queryForGetCompanyId = queryForGetCompanyId +" and lower(tmaInfo.tmaName) like '"+
					partnerNewCardReportForm.getTmaName().trim().toLowerCase()+"%'";
				}
				if(!Util.isBlankOrNull(partnerNewCardReportForm.getPartnerFirstName()) && flag == true){
					queryForGetCompanyId = "";
					queryForGetCompanyId = "select " +
												"partnerCompInfo.nextfareCompanyId, partnerAdminDetails.partnerCompanyInfo.companyId " +
											"from " +
												"PartnerAdminDetails partnerAdminDetails, " +
												"PartnerCompanyInfo partnerCompInfo " +
											"where " +
												"lower(partnerAdminDetails.firstName) like '%" +
												partnerNewCardReportForm.getPartnerFirstName().trim().toLowerCase()+"%' " +
												"and partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompInfo.companyId";
						
				}
				if(!Util.isBlankOrNull(partnerNewCardReportForm.getPartnerLastName()) && flag == true){
					queryForGetCompanyId = "";
					queryForGetCompanyId = "select " +
												"partnerCompInfo.nextfareCompanyId, partnerAdminDetails.partnerCompanyInfo.companyId " +
											"from " +
												"PartnerAdminDetails partnerAdminDetails, " +
												"PartnerCompanyInfo partnerCompInfo " +
											"where " +
												"lower(partnerAdminDetails.lastName) like '%" +
												partnerNewCardReportForm.getPartnerLastName().trim().toLowerCase()+"%' " +
												"and partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompInfo.companyId";
						
				}
				if(!Util.isBlankOrNull(partnerNewCardReportForm.getPartnerLastName()) &&
						!Util.isBlankOrNull(partnerNewCardReportForm.getPartnerFirstName()) && flag == true){
					queryForGetCompanyId = "";
					queryForGetCompanyId = "select " +
												"partnerCompInfo.nextfareCompanyId, partnerAdminDetails.partnerCompanyInfo.companyId " +
											"from " +
												"PartnerAdminDetails partnerAdminDetails, " +
												"PartnerCompanyInfo partnerCompInfo " +
											"where " +
												"lower(partnerAdminDetails.lastName) like '%" +
												partnerNewCardReportForm.getPartnerLastName().trim().toLowerCase()+"%' " +
												"and lower(partnerAdminDetails.firstName) like '%"+
												partnerNewCardReportForm.getPartnerFirstName().trim().toLowerCase()+"%' " +
												"and partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompInfo.companyId";
						
				}
				if(!Util.isBlankOrNull(partnerNewCardReportForm.getTmaName()) && flag == true
						&& !Util.isBlankOrNull(partnerNewCardReportForm.getPartnerFirstName())){
					queryForGetCompanyId = null;
					queryForGetCompanyId ="select " +
												"distinct partnerComp.nextfareCompanyId, partnerAdminDetails.partnerCompanyInfo.companyId " +
											"from " +
												"TmaInformation tmaInfo, " +
												"TmaCompanyLink tmaComp, " +
												"PartnerCompanyInfo partnerComp," +
												"PartnerAdminDetails partnerAdminDetails " +
											"where " +
												"tmaInfo.tmaId = tmaComp.id.tmaId " +
												"and partnerComp.companyId = tmaComp.id.companyId "+
												"and partnerAdminDetails.partnerCompanyInfo.companyId = partnerComp.companyId "+
												"and lower(tmaInfo.tmaName) like '%"+
												partnerNewCardReportForm.getTmaName().trim().toLowerCase()+"%'"+
												"and lower(partnerAdminDetails.firstName) like '%"+
												partnerNewCardReportForm.getPartnerFirstName().trim().toLowerCase()+"%'" ;					
				}
				if(!Util.isBlankOrNull(partnerNewCardReportForm.getTmaName()) && flag == true
						&& !Util.isBlankOrNull(partnerNewCardReportForm.getPartnerLastName())){
					queryForGetCompanyId = null;
					queryForGetCompanyId ="select " +
												"distinct partnerComp.nextfareCompanyId, partnerAdminDetails.partnerCompanyInfo.companyId " +
											"from " +
												"TmaInformation tmaInfo, " +
												"TmaCompanyLink tmaComp, " +
												"PartnerCompanyInfo partnerComp," +
												"PartnerAdminDetails partnerAdminDetails " +
											"where " +
												"tmaInfo.tmaId = tmaComp.id.tmaId " +
												"and partnerComp.companyId = tmaComp.id.companyId "+
												"and partnerAdminDetails.partnerCompanyInfo.companyId = partnerComp.companyId "+
												"and lower(tmaInfo.tmaName) like '%"+
												partnerNewCardReportForm.getTmaName().trim().toLowerCase()+"%'"+
												"and lower(partnerAdminDetails.lastName) like '%"+
												partnerNewCardReportForm.getPartnerLastName().trim().toLowerCase()+"%'";
				}
				if(!Util.isBlankOrNull(partnerNewCardReportForm.getTmaName()) && flag == true
						&& !Util.isBlankOrNull(partnerNewCardReportForm.getPartnerLastName())
						&& !Util.isBlankOrNull(partnerNewCardReportForm.getPartnerFirstName())){
					queryForGetCompanyId = null;
					queryForGetCompanyId ="select " +
												"distinct partnerComp.nextfareCompanyId, partnerAdminDetails.partnerCompanyInfo.companyId " +
											"from " +
												"TmaInformation tmaInfo, " +
												"TmaCompanyLink tmaComp, " +
												"PartnerCompanyInfo partnerComp," +
												"PartnerAdminDetails partnerAdminDetails " +
											"where " +
												"tmaInfo.tmaId = tmaComp.id.tmaId " +
												"and partnerComp.companyId = tmaComp.id.companyId "+
												"and partnerAdminDetails.partnerCompanyInfo.companyId = partnerComp.companyId "+
												"and lower(tmaInfo.tmaName) like '%"+
												partnerNewCardReportForm.getTmaName().trim().toLowerCase()+"%'"+
												"and lower(partnerAdminDetails.lastName) like '%"+
												partnerNewCardReportForm.getPartnerLastName().trim().toLowerCase()+"%'"+
												"and lower(partnerAdminDetails.firstName) like '%"+
												partnerNewCardReportForm.getPartnerFirstName().trim().toLowerCase()+"%'" ;					
				}
				nextfareCompanyIdList = session.createQuery(queryForGetCompanyId).list();
 				if(nextfareCompanyIdList !=null && !nextfareCompanyIdList.isEmpty()){
					for(Iterator iter = nextfareCompanyIdList.iterator();iter.hasNext();){
						partnerNewCardReportAdminDetailsDTO = new PartnerNewCardReportAdminDetailsDTO();
						Object[] element = (Object[]) iter.next();
						if(null != element[0] && !Util.isBlankOrNull(element[0].toString())){
							nextFareCompanyId = element[0].toString(); 
						}
						partnerNewCardReportAdminDetailsDTO.setCompanyId(nextFareCompanyId);
				if(null != element[1] && !Util.isBlankOrNull(element[1].toString())){
					String query1 =" select partnerAdmin.firstName,partnerAdmin.lastName,partnerAdmin.partnerCompanyInfo.companyId,"+
					 " partnerAdmin.partnerId "+
					 " from PartnerAdminDetails partnerAdmin,PartnerCompanyInfo partnerCompanyInfo where "+
					 " partnerAdmin.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId" +
				                " and partnerAdmin.partnerCompanyInfo.companyId = "+element[1];
						if(partnerNewCardReportForm.getPartnerFirstName()!= null &&
								!Util.isBlankOrNull(partnerNewCardReportForm.getPartnerFirstName())){
							query1 = query1 + " and lower(partnerAdmin.firstName) like '"+
							partnerNewCardReportForm.getPartnerFirstName().trim().toLowerCase()+"%'" ;
						}
						if(partnerNewCardReportForm.getPartnerLastName()!=null &&
								!Util.isBlankOrNull(partnerNewCardReportForm.getPartnerLastName())){
							query1=query1 + " and lower(partnerAdmin.lastName) like '"+
							partnerNewCardReportForm.getPartnerLastName().trim().toLowerCase()+"%'";
						}
					List partnerAdminList = session.createQuery(query1).list();
					if(partnerAdminList != null && !partnerAdminList.isEmpty()){
						for(Iterator it = partnerAdminList.iterator();it.hasNext();){
							Object[] partnerAdminObj = (Object[])it.next();
							if(partnerAdminObj[1] !=null && !Util.isBlankOrNull(partnerAdminObj[1].toString())){
								partnerNewCardReportAdminDetailsDTO.setPartnerId(partnerAdminObj[1].toString());
									
						}
							if(partnerAdminObj[2] !=null && !Util.isBlankOrNull(partnerAdminObj[2].toString())){
								 companyId=partnerAdminObj[2].toString();
								partnerNewCardReportAdminDetailsDTO.setCompanyId(partnerAdminObj[2].toString());
						}
					}
				}
			}
			}		
				}
				if(nextfareCompanyIdList !=null && !nextfareCompanyIdList.isEmpty()){
					for(Iterator iter = nextfareCompanyIdList.iterator();iter.hasNext();){
						partnerNewCardReportDTO = new PartnerNewCardReportDTO();
						Object nextFareCompanyId1[] = (Object[]) iter.next();
										if(nextFareCompanyId1[0] != null && !Util.isBlankOrNull(nextFareCompanyId1[0].toString())){
										 benefitList = getPartnerNewCardReportFromNextfare(nextFareCompanyId1[0].toString(),companyId,partnerNewCardReportForm);
										 benefitList.addAll(getPartnerNewCardReportRegionFromNextfare(nextFareCompanyId1[0].toString(),companyId,partnerNewCardReportForm));
										 if(benefitList != null && !benefitList.isEmpty()){
												for(Iterator benefitlist = benefitList.iterator();benefitlist.hasNext();){
													partnerNewCardReportDTO = (PartnerNewCardReportDTO) benefitlist.next(); 
													
												if(partnerNewCardReportDTO.getCardSerialNumber() !=null 
														&& !Util.isBlankOrNull(partnerNewCardReportDTO.getCardSerialNumber())){
													partnerNewCardReportDTO.setCardSerialNumber(partnerNewCardReportDTO.getCardSerialNumber());
												}else{
													partnerNewCardReportDTO.setCardSerialNumber(" ");
												}
												if(partnerNewCardReportDTO.getBenefitName() !=null 
														&& !Util.isBlankOrNull(partnerNewCardReportDTO.getBenefitName())){
													partnerNewCardReportDTO.setBenefitName(partnerNewCardReportDTO.getBenefitName());
												}else{
													partnerNewCardReportDTO.setBenefitName(" ");
												}
												if(partnerNewCardReportDTO.getEffectiveDate() != null 
														&& !Util.isBlankOrNull(partnerNewCardReportDTO.getEffectiveDate())){
													partnerNewCardReportDTO.setEffectiveDate(partnerNewCardReportDTO.getEffectiveDate());
												}else{
													partnerNewCardReportDTO.setEffectiveDate(" ");
												}
												}
										 collectionList.addAll(benefitList);
										 break;
										 }
									}
							  	}
							}
				}
				transaction.commit();
				session.flush();
				BcwtsLogger.info(MY_NAME + "got  list from DB ");
			}catch (Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;
			} finally {
				closeSession(session, transaction);
				BcwtsLogger.debug(MY_NAME + " Resources closed");
			}
			return collectionList;
		}
	/**
	 * Method to get Partner New Card Report From Next Fare Details 
	 * @param form 
	 * @return List
	 * @throws Exception
	 */
	public List getPartnerNewCardReportFromNextfare(String nextFareCompanyId,String companyId,PartnerNewCardReportForm partnerNewCardReportForm) throws Exception{
		
		final String MY_NAME = ME + "getPartnerNewCardList:";
		BcwtsLogger.debug(MY_NAME+" nextfare id"+nextFareCompanyId);
		List newCardDetailsList = new ArrayList();
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		String strFromDate = null;
		String strToDate = null;
		try {
				SimpleDateFormat sdfInput = new SimpleDateFormat("dd/mm/yyyy") ;			
			    SimpleDateFormat sdfOutput = new SimpleDateFormat("mm/dd/yyyy") ;
			    Date fromDate = sdfInput.parse("01/" + partnerNewCardReportForm.getMonthYear());
			    int month = Integer.parseInt(partnerNewCardReportForm.getCardToMonth());
			    int year = Integer.parseInt(partnerNewCardReportForm.getCardToYear());
			    Date toDate = sdfInput.parse(Util.lastDayOfMonth(month, year) + "/" + partnerNewCardReportForm.getMonthYearTo());
				strFromDate = sdfOutput.format(fromDate);
				strToDate = sdfOutput.format(toDate);
			/*	query = "select" +
							" mbh.SERIAL_NBR, cbd.BENEFIT_NAME, mbh.EFFECTIVE_DTM" +
						" from" +
							" NEXTFARE_MAIN.MEMBER_BENEFIT_HISTORY mbh," +
							" NEXTFARE_MAIN.CUSTOMER_BENEFIT_DEFINITION cbd" +
						" where" +
						" mbh.BENEFIT_ID = cbd.BENEFIT_ID" +
						" and mbh.CUSTOMER_ID = cbd.CUSTOMER_ID" + 
						" and mbh.CUSTOMER_ID = " + nextFareCompanyId + 
						" and cbd.CUSTOMER_ID = " + nextFareCompanyId +
						" and mbh.EFFECTIVE_DTM >= TO_DATE('" + strFromDate + "', " + "'MM/DD/YYYY')"+
						" and mbh.EFFECTIVE_DTM <= TO_DATE('" + strToDate + "', " + "'MM/DD/YYYY')";*/
			
				query = " select m.serial_nbr, m.SERIAL_NBR_ASSIGNED_DTM, cbd.benefit_name, m.SERIAL_NBR_ASSIGNED_DTM, cc.ORG_NAME  "
						+ "from nextfare_main.member m,nextfare_main.member_benefit mb, nextfare_main.customer_benefit_definition cbd, "    
					  +" nextfare_main.fare_instrument fi, nextfare_main.customer  cc  "
					  +" where mb.customer_id = cbd.customer_id "    
					  +" and m.member_id = mb.member_id    "
					  +" and m.customer_id = mb.customer_id "
					  + " and m.customer_id = cc.customer_id "
					  + " and mb.customer_id = cc.customer_id "   
					  +" and cbd.fare_instrument_id = fi.fare_instrument_id "    
					  +" and mb.benefit_id = cbd.benefit_id     "
					  +" and mb.customer_id = '"+nextFareCompanyId+"' "
                         +"  and mb.BENEFIT_ACTION_ID = '2'  and m.SERIAL_NBR_ASSIGNED_DTM >= TO_DATE(' "
					+" 	"+ strFromDate+" ',  'MM/DD/YYYY') "
					+" 	 and m.SERIAL_NBR_ASSIGNED_DTM <= TO_DATE('"+ strToDate+"',  'MM/DD/YYYY')";
				
				BcwtsLogger.info("Query "+query);
				nextFareConnection = NextFareConnection.getConnection();
				if(nextFareConnection != null) {
					pstmt = nextFareConnection.prepareStatement(query);
					if(pstmt != null) {
						rs = pstmt.executeQuery();
						if(rs != null) {
							while(rs.next()){
								PartnerNewCardReportDTO partnerNewCardReportDTO = new PartnerNewCardReportDTO();
								partnerNewCardReportDTO.setCardSerialNumber(rs.getString("SERIAL_NBR"));
								partnerNewCardReportDTO.setBenefitName(rs.getString("BENEFIT_NAME"));
								partnerNewCardReportDTO.setEffectiveDate(Util.getFormattedDate(rs.getObject("SERIAL_NBR_ASSIGNED_DTM")));
								partnerNewCardReportDTO.setCompanyId(nextFareCompanyId);
								partnerNewCardReportDTO.setCompanyName(rs.getString("ORG_NAME"));
								newCardDetailsList.add(partnerNewCardReportDTO);
							}
						}
					}
				}
			BcwtsLogger.info(MY_NAME + "got new card details list");
			}catch(Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} 
		return newCardDetailsList;
	}
	
public List getPartnerNewCardReportRegionFromNextfare(String nextFareCompanyId,String companyId,PartnerNewCardReportForm partnerNewCardReportForm) throws Exception{
		
		final String MY_NAME = ME + "getPartnerNewCardList:";
		BcwtsLogger.debug(MY_NAME+" nextfare id"+nextFareCompanyId);
		List newCardDetailsList = new ArrayList();
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		String strFromDate = null;
		String strToDate = null;
		try {
				SimpleDateFormat sdfInput = new SimpleDateFormat("dd/mm/yyyy") ;			
			    SimpleDateFormat sdfOutput = new SimpleDateFormat("mm/dd/yyyy") ;
			    Date fromDate = sdfInput.parse("01/" + partnerNewCardReportForm.getMonthYear());
			    int month = Integer.parseInt(partnerNewCardReportForm.getCardToMonth());
			    int year = Integer.parseInt(partnerNewCardReportForm.getCardToYear());
			    Date toDate = sdfInput.parse(Util.lastDayOfMonth(month, year) + "/" + partnerNewCardReportForm.getMonthYearTo());
				strFromDate = sdfOutput.format(fromDate);
				strToDate = sdfOutput.format(toDate);
			/*	query = "select" +
							" mbh.SERIAL_NBR, cbd.BENEFIT_NAME, mbh.EFFECTIVE_DTM" +
						" from" +
							" NEXTFARE_MAIN.MEMBER_BENEFIT_HISTORY mbh," +
							" NEXTFARE_MAIN.CUSTOMER_BENEFIT_DEFINITION cbd" +
						" where" +
						" mbh.BENEFIT_ID = cbd.BENEFIT_ID" +
						" and mbh.CUSTOMER_ID = cbd.CUSTOMER_ID" + 
						" and mbh.CUSTOMER_ID = " + nextFareCompanyId + 
						" and cbd.CUSTOMER_ID = " + nextFareCompanyId +
						" and mbh.EFFECTIVE_DTM >= TO_DATE('" + strFromDate + "', " + "'MM/DD/YYYY')"+
						" and mbh.EFFECTIVE_DTM <= TO_DATE('" + strToDate + "', " + "'MM/DD/YYYY')";*/
			
				/*query = " select m.serial_nbr, m.SERIAL_NBR_ASSIGNED_DTM, cbd.benefit_name, m.SERIAL_NBR_ASSIGNED_DTM, cc.ORG_NAME  "
						+ "from nextfare_main.member m,nextfare_main.member_benefit mb, nextfare_main.customer_benefit_definition cbd, "    
					  +" nextfare_main.fare_instrument fi, nextfare_main.customer  cc  "
					  +" where mb.customer_id = cbd.customer_id "    
					  +" and m.member_id = mb.member_id    "
					  +" and m.customer_id = mb.customer_id "
					  + " and m.customer_id = cc.customer_id "
					  + " and mb.customer_id = cc.customer_id "   
					  +" and cbd.fare_instrument_id = fi.fare_instrument_id "    
					  +" and mb.benefit_id = cbd.benefit_id     "
					  +" and mb.customer_id = '"+nextFareCompanyId+"' "
                         +"  and mb.BENEFIT_ACTION_ID = '2'  and m.SERIAL_NBR_ASSIGNED_DTM >= TO_DATE(' "
					+" 	"+ strFromDate+" ',  'MM/DD/YYYY') "
					+" 	 and m.SERIAL_NBR_ASSIGNED_DTM <= TO_DATE('"+ strToDate+"',  'MM/DD/YYYY')";*/
				
				query = "	select m.serial_nbr, cbd.benefit_name, m.SERIAL_NBR_ASSIGNED_DTM, cc.ORG_NAME  from NEXTFARE_MAIN.MEMBER m , NEXTFARE_MAIN.AUTOLOAD au, nextfare_main.customer_benefit_definition cbd, nextfare_main.customer  cc where "
	             +"  m.SERIAL_NBR_ASSIGNED_DTM >= TO_DATE('"+ strFromDate+"','MM/DD/YYYY')  "
	             +"   and m.SERIAL_NBR_ASSIGNED_DTM <= TO_DATE('"+ strToDate+"',  'MM/DD/YYYY') and m.serial_nbr = au.serial_nbr "
	             +"  and m.customer_id = '"+nextFareCompanyId+"' "
	             +"   and AU.AUTOLOAD_ACTION_ID in (1,3) "
	             + " and m.customer_id = cc.customer_id "
	             +"   and AU.INSERTED_DTM >= TO_DATE('"+ strFromDate+"','MM/DD/YYYY') "  
	             +"   and AU.INSERTED_DTM <= TO_DATE('"+ strToDate+"',  'MM/DD/YYYY') "
	             +"   and AU.AUTOLOAD_TYPE_ID = '1' "
	             +"   and cbd.fare_instrument_id = AU.FARE_INSTRUMENT_ID "
	             +"   and cbd.customer_id = M.CUSTOMER_ID ";
				
				
				BcwtsLogger.info("Query "+query);
				nextFareConnection = NextFareConnection.getConnection();
				if(nextFareConnection != null) {
					pstmt = nextFareConnection.prepareStatement(query);
					if(pstmt != null) {
						rs = pstmt.executeQuery();
						if(rs != null) {
							while(rs.next()){
								PartnerNewCardReportDTO partnerNewCardReportDTO = new PartnerNewCardReportDTO();
								partnerNewCardReportDTO.setCardSerialNumber(rs.getString("SERIAL_NBR"));
								partnerNewCardReportDTO.setBenefitName(rs.getString("BENEFIT_NAME"));
								partnerNewCardReportDTO.setEffectiveDate(Util.getFormattedDate(rs.getObject("SERIAL_NBR_ASSIGNED_DTM")));
								partnerNewCardReportDTO.setCompanyId(nextFareCompanyId);
								partnerNewCardReportDTO.setCompanyName(rs.getString("ORG_NAME"));
								newCardDetailsList.add(partnerNewCardReportDTO);
							}
						}
					}
				}
			BcwtsLogger.info(MY_NAME + "got new card details list");
			}catch(Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} 
		return newCardDetailsList;
	}
	
		}
	



