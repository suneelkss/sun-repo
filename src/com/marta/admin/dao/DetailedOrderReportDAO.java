package com.marta.admin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.text.DateFormat;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.BcwtDetailedOrderBenefitDTO;
import com.marta.admin.dto.BcwtDetailedOrderReportDTO;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.MartaQueries;
import com.marta.admin.utils.Util;

public class DetailedOrderReportDAO extends MartaBaseDAO {
	final String ME = "DetailedOrderReportDAO: ";
	
	/**
	 * Method to get Detailed Order Report 
	 * @param form 
	 * @return List
	 * @throws Exception
	 */
//method for Detailed Order Report
	
	public List getDetailedOrderReport(ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getDetailedOrderReport: ";
		BcwtsLogger.debug(MY_NAME);
		List newCardList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List newCardDataList = null;
		BcwtDetailedOrderReportDTO orderReportDTO = null;
		String nextFareCompanyId = null;
		String partnerId = null;
		String benefitName = null;
		BcwtDetailedOrderBenefitDTO bcwtDetailedOrderBenefitDTO = null;
		String employeeId = null;
		String bcSerialNo = null;
		List benefitList = null;
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = MartaQueries.GET_DETAILED_ORDER_QUERY;
			if(reportForm.getCompanyName()!= null &&
					!Util.isBlankOrNull(reportForm.getCompanyName())){
				query = query + " and lower(tmaInfo.tmaName) like '"+
				 				reportForm.getCompanyName().trim().toLowerCase()+"%'";
				
			}
			if(reportForm.getSubCompanyName() != null &&
					!Util.isBlankOrNull(reportForm.getSubCompanyName())){
				query = query + " and lower(partnerComp.companyName) like '"+
 								reportForm.getSubCompanyName().trim().toLowerCase()+"%'";
			}
			newCardDataList = session.createQuery(query).list();
			if(newCardDataList !=null && !newCardDataList.isEmpty()){
				for(Iterator iter = newCardDataList.iterator();iter.hasNext();){
					orderReportDTO = new BcwtDetailedOrderReportDTO();
					Object element[] = (Object[]) iter.next();
					if(null != element[3] && !Util.isBlankOrNull(element[3].toString())){
						orderReportDTO.setCompanyName(element[3].toString());
					}
					if(null != element[1] && !Util.isBlankOrNull(element[1].toString())){
						nextFareCompanyId = element[1].toString(); 
					}
					if(null != element[0] && !Util.isBlankOrNull(element[0].toString())){
						String query1 = MartaQueries.GET_NEW_CARD_PARTNERADMIN_QUERY +
					                " and partnerAdmin.partnerCompanyInfo.companyId = "+element[0];
						if(reportForm.getPartnerFirstName()!= null &&
								!Util.isBlankOrNull(reportForm.getPartnerFirstName())){
							query1 = query1 + " and lower(partnerAdmin.firstName) like '"+
									reportForm.getPartnerFirstName().trim().toLowerCase()+"%'" ;
						}
						if(reportForm.getPartnerLastName()!=null &&
								!Util.isBlankOrNull(reportForm.getPartnerLastName())){
							query1=query1 + " and lower(partnerAdmin.lastName) like '"+
									reportForm.getPartnerLastName().trim().toLowerCase()+"%'";
						}
						List partnerAdminList = session.createQuery(query1).list();
						if(partnerAdminList != null && !partnerAdminList.isEmpty()){
							for(Iterator it = partnerAdminList.iterator();it.hasNext();){
								Object[] partnerAdminObj = (Object[])it.next();
								if(partnerAdminObj[0] !=null && !Util.isBlankOrNull(partnerAdminObj[0].toString())){
									if(partnerAdminObj[1] !=null && !Util.isBlankOrNull(partnerAdminObj[1].toString())){
										orderReportDTO.setPartnerAdminName(
												partnerAdminObj[0].toString()+" "+partnerAdminObj[1].toString());
									}else{
										orderReportDTO.setPartnerAdminName(partnerAdminObj[0].toString());
									}
								}else{
									if(partnerAdminObj[1] !=null && !Util.isBlankOrNull(partnerAdminObj[1].toString())){
										orderReportDTO.setPartnerAdminName(
												partnerAdminObj[1].toString());
									}
								}
								if(nextFareCompanyId != null && partnerAdminObj[3] !=null 
										&& !Util.isBlankOrNull(partnerAdminObj[3].toString())){
									partnerId = partnerAdminObj[3].toString();
									benefitList = getDetailedOrderBenefitDetails(nextFareCompanyId,partnerId,reportForm);
									
									 if(benefitList != null && !benefitList.isEmpty()){
										for(Iterator benefitlist = benefitList.iterator();benefitlist.hasNext();){
											bcwtDetailedOrderBenefitDTO = (BcwtDetailedOrderBenefitDTO) benefitlist.next(); 
											
										if(bcwtDetailedOrderBenefitDTO.getEmployeeId() !=null 
												&& !Util.isBlankOrNull(bcwtDetailedOrderBenefitDTO.getEmployeeId())){
											employeeId = bcwtDetailedOrderBenefitDTO.getEmployeeId();
											orderReportDTO.setEmployeeId(bcwtDetailedOrderBenefitDTO.getEmployeeId());
										}
										if(bcwtDetailedOrderBenefitDTO.getBreezecardSerialNo() !=null 
												&& !Util.isBlankOrNull(bcwtDetailedOrderBenefitDTO.getBreezecardSerialNo())){
											bcSerialNo = bcwtDetailedOrderBenefitDTO.getBreezecardSerialNo();
											orderReportDTO.setBreezecardSerialNo(bcwtDetailedOrderBenefitDTO.getBreezecardSerialNo());
										}
										if(bcwtDetailedOrderBenefitDTO.getEmployeeName() != null 
												&& !Util.isBlankOrNull(bcwtDetailedOrderBenefitDTO.getEmployeeName())){
											orderReportDTO.setEmployeeName(bcwtDetailedOrderBenefitDTO.getEmployeeName());
										}
										if(bcwtDetailedOrderBenefitDTO.getEffectiveDate() != null 
												&& !Util.isBlankOrNull(bcwtDetailedOrderBenefitDTO.getEffectiveDate())){
											DateFormat formatter ;
											formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
											Date fDate = (Date)formatter.parse(bcwtDetailedOrderBenefitDTO.getEffectiveDate());
											orderReportDTO.setEffectiveDate(Util.getFormattedDate(fDate));
										}
										if(bcwtDetailedOrderBenefitDTO.getBenefitName() != null 
												&& !Util.isBlankOrNull(bcwtDetailedOrderBenefitDTO.getBenefitName())){
											orderReportDTO.setBenefitName(bcwtDetailedOrderBenefitDTO.getBenefitName());
										}
									 }
									 }
								}
							}
						  }
					}
					if(benefitList != null && !benefitList.isEmpty()){
						newCardList.add(orderReportDTO);
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
		return newCardList;
	}
	/**
	 * Method to get Detailed Order Benefit Details Report 
	 * @param form 
	 * @return List
	 * @throws Exception
	 */
	//method to get connected with nextfare table
	private List getDetailedOrderBenefitDetails(String nextFareCompanyId,String partnerId,ReportForm reportForm ) throws Exception {
		final String MY_NAME = ME + "getBenefitDetails: ";
		BcwtsLogger.debug(MY_NAME);
		String newCardBenefit = null;
		Connection con = null;
		ResultSet rs = null;
		List benefitList = null;
		BcwtDetailedOrderBenefitDTO benefitDTO = null;
		try{
			benefitList = new ArrayList();
			con = NextFareConnection.getConnection();
			String query = "select mb.CUSTOMER_MEMBER_ID,mb.SERIAL_NBR,mb.FIRST_NAME,mb.LAST_NAME "+
						   "from nextfare_main.Member mb "+
						   "where mb.CUSTOMER_ID = "+nextFareCompanyId+" and mb.MEMBER_ID = "+partnerId ;
			if(reportForm.getEmployeeName()!= null &&
					!Util.isBlankOrNull(reportForm.getEmployeeName())){
				query = query + " and lower(mb.FIRST_NAME) like '"+
						reportForm.getEmployeeName().trim().toLowerCase()+"%'or " +
						"lower(mb.LAST_NAME) like '"+reportForm.getEmployeeName().trim().toLowerCase()+"%'or " +
						"lower(concat(mb.FIRST_NAME,mb.LAST_NAME)) like '"+reportForm.getEmployeeName().trim().toLowerCase()+"%'" ;
			}	
			if(reportForm.getEmployeeId() != null &&
					!Util.isBlankOrNull(reportForm.getEmployeeId())){
				query = query + " and lower(mb.CUSTOMER_MEMBER_ID) like '"+
						reportForm.getEmployeeId().trim().toLowerCase()+"%'";
			}
			if(reportForm.getBreezeCardSerialNumber() !=null &&
					!Util.isBlankOrNull(reportForm.getBreezeCardSerialNumber())){
				query = query + " and lower(mb.SERIAL_NBR) like '"+
						reportForm.getBreezeCardSerialNumber().trim().toLowerCase()+"%'";
			}
			if(con != null) {
				Statement stmt = con.createStatement();
				if(stmt != null) {
					rs = stmt.executeQuery(query);
				}
			}
			if(rs != null){
				while(rs.next()){
					benefitDTO = new BcwtDetailedOrderBenefitDTO();
					if(rs.getString(1) !=null && !Util.isBlankOrNull(rs.getString(1))){
						benefitDTO.setEmployeeId(rs.getString(1));
					}else{
						benefitDTO.setEmployeeId(" ");
					}if(rs.getString(2) !=null && !Util.isBlankOrNull(rs.getString(2))){
						benefitDTO.setBreezecardSerialNo(rs.getString(2));
					}else{
						benefitDTO.setBreezecardSerialNo(" ");
					}if((rs.getString(3) !=null && !Util.isBlankOrNull(rs.getString(3))) ||
							(rs.getString(4) !=null && !Util.isBlankOrNull(rs.getString(4)))){
						benefitDTO.setEmployeeName(rs.getString(3) + rs.getString(4));
					}else{
						benefitDTO.setEmployeeName(" ");
					}
					String Qery =" select distinct(mbh.BENEFIT_ID),mbh.EFFECTIVE_DTM"+
								 " from nextfare_main.Member_Benefit_History mbh where mbh.CUSTOMER_ID ="+nextFareCompanyId+"and"+
								 " mbh.MEMBER_ID ="+partnerId+" and mbh.SERIAL_NBR = '"+rs.getString(2)+"'";
					if(reportForm.getBillingStartDate() !=null &&
							reportForm.getBillingEndDate() != null &&
							!Util.isBlankOrNull(reportForm.getBillingStartDate()) &&
							!Util.isBlankOrNull(reportForm.getBillingEndDate())){
						SimpleDateFormat sdfInput = new SimpleDateFormat("MM/dd/yyyy") ;			
					    SimpleDateFormat sdfOutput = new SimpleDateFormat("MM/dd/yyyy") ;
					    String strFromDate = null;
					    String strToDate = null;
					    
					    Date fromDate = sdfInput.parse(reportForm.getBillingStartDate());
					    
					    Date toDate = sdfInput.parse(reportForm.getBillingEndDate());
						strFromDate = sdfOutput.format(fromDate);
						strToDate = sdfOutput.format(toDate);
						Qery = Qery + "and mbh.EFFECTIVE_DTM >= TO_DATE('" + strFromDate + "', " + "'MM/DD/YYYY')"+
								" and mbh.EFFECTIVE_DTM <= TO_DATE('" + strToDate + "', " + "'MM/DD/YYYY')";
						
					}
					Statement sts = con.createStatement();
					ResultSet rt = sts.executeQuery(Qery);
					if(rt !=null)
					{
						while(rt.next()){
							if(rt.getString(2) !=null && !Util.isBlankOrNull(rt.getString(2))){
								benefitDTO.setEffectiveDate(rt.getString(2));
							}else{
								benefitDTO.setEffectiveDate(" ");
							}
							String Qery1 =" select distinct(cbd.BENEFIT_NAME) from nextfare_main.Customer_Benefit_Definition cbd"+
										  " where cbd.CUSTOMER_ID = "+nextFareCompanyId+" and cbd.BENEFIT_ID = "+rt.getString(1);
							Statement sts1 = con.createStatement();
							ResultSet rt1 = sts1.executeQuery(Qery1);
							if(rt1 !=null)
							{
								while(rt1.next()){
									if(rt1.getString(1) !=null && !Util.isBlankOrNull(rt1.getString(1))){
										benefitDTO.setBenefitName(rt1.getString(1));
									}
								}
							}
							
						}
					}
					benefitList.add(benefitDTO);
				  }
				}
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME + e.getMessage());
			throw e;
		}
		return benefitList;
	}
	

}
