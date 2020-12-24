package com.marta.admin.utils;

/**
 * This class is used to keep the queries.
 * 
 */
public class MartaQueries {
	
	//Queries For Manage Partner Registration & Manage Company Status
	
		
	

	// Query to fetch shipment methods.
	public static final String SHIPPMENT_METHODS_QUERY = "from BcwtShipmentmethods shipmentMethods " +
			" where shipmentMethods.activestatus = '" + Constants.ACTIVE_STATUS +"'"+
			" order by shipmentMethods.shipmentmethod";

	// To get event,order details for welcome page
	public static final String EVENT_ORDER_QUERY = "select eventss.eventname,patron.firstname,patron.lastname,orderr.orderid," +
			" orderinfo.noofcards,product.productname,orderstatus.orderstatusname,orderr.shippeddate,patron.middlename from BcwtOrderStatus orderstatus,BcwtPatron patron join patron.bcwteventses " +
			" eventss join eventss.bcwtorders orderr  " +
			" join orderr.bcwtorderinfo orderinfo " +
			" left join orderinfo.bcwtgbsproductdetailses productDetails " +
			" left join productDetails.bcwtproduct product " +
			" where orderr.bcwtorderstatus.orderstatusid = orderstatus.orderstatusid" +
			" and orderr.bcwtorderstatus.orderstatusid != " + Constants.ORDER_STATUS_DELETED +
			" and eventss.bcwtpatron.patronid in" ;
	
	//Query to fetch events that are associated with shipped orders.
	public static final String ORDERED_EVENT_QUERY = "select distinct(bcwtEvents.eventid), bcwtEvents.eventname from BcwtEvents bcwtEvents, BcwtOrder bcwtOrder " +
			" where bcwtEvents.eventid = bcwtOrder.bcwtevents.eventid " +
			//" and bcwtEvents.bcwtpatron.patronid = bcwtOrder.bcwtpatron.patronid " +
			" and bcwtOrder.bcwtorderstatus.orderstatusid = '"+ Constants.ORDER_STATUS_PENDING +"' " +
			" and bcwtEvents.activestatus = '"+ Constants.ACTIVE_STATUS +"'"; 
	
	// Query to fetch order ids against against event id.
	public static final String GET_ORDER_IDS_QUERY = "select bcwtOrder.orderid from BcwtOrder bcwtOrder " +
			" where bcwtOrder.bcwtorderstatus.orderstatusid = '"+ Constants.ORDER_STATUS_PENDING +"'";
	
	// Query to fetch product list against order id. 
	public static final String GET_PRODUCT_LIST_QUERY = "select gbsProduct.bcwtproduct.productid, bcwtProduct.productname," +
			" bcwtProduct.fare_instrument_id,bcwtProduct.price "+ 
			" from BcwtGbsProductDetails gbsProduct,BcwtOrderInfo bcwtOrderInfo, BcwtProduct bcwtProduct "+ 
			" where bcwtOrderInfo.orderinfoid = gbsProduct.bcwtorderinfo.orderinfoid "+ 
			" and bcwtProduct.productid = gbsProduct.bcwtproduct.productid ";
	
	// Query to fetch cash and number of cards from BcwtOrderInfo against order id.
	public static final String GET_CASH_NOOFCARDS_QUERY = "select bcwtOrderInfo.noofcards,bcwtOrderInfo.cashvalue " +
			" from BcwtOrderInfo bcwtOrderInfo ";
	
	//Query to fetch alert data from ManualAlert table
	public static final String GET_ALERT_LIST_QUERY = " select bcwtManualAlert.manualalertid,bcwtManualAlert.bcwtpatron.patronid,bcwtManualAlert.releaseDate," +
	                                                  " bcwtManualAlert.discontinueDate,bcwtManualAlert.alertTitle,bcwtManualAlert.alertMessage,bcwtpatron.bcwtpatrontype.patrontypeid"+
	                                                  " from BcwtManualAlert bcwtManualAlert,BcwtPatron bcwtpatron,BcwtPatronType bcwtpatrontype" +
	                                                  " where bcwtpatron.patronid = bcwtManualAlert.bcwtpatron.patronid and bcwtpatron.bcwtpatrontype.patrontypeid = bcwtpatrontype.patrontypeid  ";
	
	/*//Query for Detailed Order Report 
	public static final String GET_NEW_CARD_QUERY = " select bcwtPartnerNewCard.companyname,bcwtPartnerNewCard.firstname,bcwtPartnerNewCard.lastname, "+
													" bcwtPartnerNewCard.customermemberid,bcwtPartnerNewCard.serialnumber,partnerAdminDetails.firstName, "+
													" partnerAdminDetails.lastName from BcwtPartnerNewCard bcwtPartnerNewCard,PartnerAdminDetails partnerAdminDetails ";*/
	
	
//	Query for Detailed Usage Report 
	/*public static final String GET_USAGE_QUERY = " select tmaInformation.tmaName,partnerCompanyInfo.companyname,partnerAdminDetails.lastName, "+
	                                             " from TmaInformation tmaInformation,PartnerCompanyInfo partnerCompanyInfo,PartnerAdminDetails partnerAdminDetails";*/
	
	public static final String GET_USAGE_QUERY = " select tmaComp.id.companyId,partnerComp.nextfareCompanyId,partnerComp.companyName, "+
												 " tmaInfo.tmaName "+
												 " from TmaInformation tmaInfo,TmaCompanyLink tmaComp , PartnerCompanyInfo partnerComp "+
												 " where tmaInfo.tmaId = tmaComp.id.tmaId and partnerComp.companyId = tmaComp.id.companyId ";
	
	/*public static final String GET_USAGE_PARTNERADMIN_QUERY = " select partnerAdminDetails.firstName," +
                                                              "  from " +
                                                              " PartnerAdminDetails partnerAdminDetails where ";*/

	public static final String GET_USAGE_PARTNERADMIN_QUERY = "select partnerAdmin.firstName,partnerAdmin.lastName,partnerAdmin.partnerCompanyInfo.companyId,"+
															  " partnerAdmin.partnerId "+
															  " from PartnerAdminDetails partnerAdmin,PartnerCompanyInfo partnerCompanyInfo where "+
															  " partnerAdmin.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId";
	public static final String GET_HOT_LIST_QUERY ="";
	
	
//	subQuery for New Card Report(1)
	public static final String GET_NEW_CARD_PARTNERADMIN_QUERY = " select partnerAdmin.firstName,partnerAdmin.lastName,partnerAdmin.partnerCompanyInfo.companyId,"+
																 " partnerAdmin.partnerId "+
																 " from PartnerAdminDetails partnerAdmin,PartnerCompanyInfo partnerCompanyInfo where "+
																 " partnerAdmin.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId";
	
//	Query for New Card Report 
	public static final String GET_NEW_CARD_QUERY1 = " select bcwtPartnerNewCard.companyname,bcwtPartnerNewCard.firstname,bcwtPartnerNewCard.lastname, "+
													" bcwtPartnerNewCard.customermemberid,bcwtPartnerNewCard.serialnumber,partnerAdminDetails.firstName, "+
													" partnerAdminDetails.lastName from BcwtPartnerNewCard bcwtPartnerNewCard,PartnerAdminDetails partnerAdminDetails ";
   //Query for New Card Report 
	public static final String GET_NEW_CARD_QUERY = " select tmaComp.id.companyId,partnerComp.nextfareCompanyId,partnerComp.companyName, "+
													" tmaInfo.tmaName "+
													" from TmaInformation tmaInfo,TmaCompanyLink tmaComp , PartnerCompanyInfo partnerComp "+
													" where tmaInfo.tmaId = tmaComp.id.tmaId and partnerComp.companyId = tmaComp.id.companyId ";
	
	  //Query for Detailed Order Report 
	public static final String GET_DETAILED_ORDER_QUERY = " select tmaComp.id.companyId,partnerComp.nextfareCompanyId,partnerComp.companyName, "+
													" tmaInfo.tmaName "+
													" from TmaInformation tmaInfo,TmaCompanyLink tmaComp , PartnerCompanyInfo partnerComp "+
													" where tmaInfo.tmaId = tmaComp.id.tmaId and partnerComp.companyId = tmaComp.id.companyId ";
	
	public static final String GET_DETAILED_ORDER_PARTNERADMIN_QUERY = " select partnerAdmin.firstName,partnerAdmin.lastName,partnerAdmin.partnerCompanyInfo.companyId,"+
																 " partnerAdmin.partnerId "+
																 " from PartnerAdminDetails partnerAdmin,PartnerCompanyInfo partnerCompanyInfo where "+
																 " partnerAdmin.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId";
	
	public static final String ACTIVE_BENIFIT_QUERY =" select tmaComp.id.companyId,partnerComp.nextfareCompanyId,partnerComp.companyName, "+
	                                                 " tmaInfo.tmaName "+
	                                                 " from TmaInformation tmaInfo,TmaCompanyLink tmaComp , PartnerCompanyInfo partnerComp "+
	                                                 " where tmaInfo.tmaId = tmaComp.id.tmaId and partnerComp.companyId = tmaComp.id.companyId ";
	
	public static final String GET_ACTIVE_BENIFIT_QUERY = " select partnerAdmin.firstName,partnerAdmin.lastName,partnerAdmin.partnerCompanyInfo.companyId,"+
	                                                             " partnerAdmin.partnerId "+
	                                                             " from PartnerAdminDetails partnerAdmin,PartnerCompanyInfo partnerCompanyInfo where "+
	                                                             " partnerAdmin.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId";
	
	 //Query for Partner Upcoming Monthly Report 
	public static final String GET_PARTNER_UPCOMING_MONTHLY_REPORT_QUERY = " select tmaComp.id.companyId,partnerComp.nextfareCompanyId,partnerComp.companyName, "+
																			" tmaInfo.tmaName "+
																			" from TmaInformation tmaInfo,TmaCompanyLink tmaComp , PartnerCompanyInfo partnerComp "+
																			" where tmaInfo.tmaId = tmaComp.id.tmaId and partnerComp.companyId = tmaComp.id.companyId ";
	
	public static final String GET_PARTNER_UPCOMING_MONTHLY_REPORT_PARTNERADMIN_QUERY = " select partnerAdmin.firstName,partnerAdmin.lastName,partnerAdmin.partnerCompanyInfo.companyId,"+
																						 " partnerAdmin.partnerId "+
																						 " from PartnerAdminDetails partnerAdmin,PartnerCompanyInfo partnerCompanyInfo where "+
																						 " partnerAdmin.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId";
							
	//	Query for Partner NewCard Report 
	public static final String GET_PARTNER_NEW_CARD_REPORT_QUERY = " select tmaComp.id.companyId,partnerComp.nextfareCompanyId,partnerComp.companyName, "+
		" tmaInfo.tmaName "+
		" from TmaInformation tmaInfo,TmaCompanyLink tmaComp , PartnerCompanyInfo partnerComp "+
		" where tmaInfo.tmaId = tmaComp.id.tmaId and partnerComp.companyId = tmaComp.id.companyId ";
	
	public static final String GET_PARTNER_NEW_CARD_REPORT_PARTNERADMIN_QUERY = " select partnerAdmin.firstName,partnerAdmin.lastName,partnerAdmin.partnerCompanyInfo.companyId,"+
					 " partnerAdmin.partnerId "+
					 " from PartnerAdminDetails partnerAdmin,PartnerCompanyInfo partnerCompanyInfo where "+
					 " partnerAdmin.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId";
	
	public static final String PARTNER_MONTHLY_USAGE_SUMMARY_REPORT = " partnerComp.nextfareCompanyId,"+
																		" tmaInfo.tmaName "+
																		" from TmaInformation tmaInfo,TmaCompanyLink tmaComp , PartnerCompanyInfo partnerComp "+
																		" where tmaInfo.tmaId = tmaComp.id.tmaId and partnerComp.companyId = tmaComp.id.companyId ";


	//	Query for List cards
	public static final String GET_LIST_CARDS_COMPANYID_QUERY = " select tmaComp.id.companyId,partnerComp.nextfareCompanyId,partnerComp.companyName, "+
																" tmaInfo.tmaName,partnerComp.companyId "+
																" from TmaInformation tmaInfo,TmaCompanyLink tmaComp , PartnerCompanyInfo partnerComp "+
																" where tmaInfo.tmaId = tmaComp.id.tmaId and partnerComp.companyId = tmaComp.id.companyId ";
	
	
	//query to retrive partner or member id
	public static final String GET_MEMBERID_LIST_CARDS_QUERY = " select partnerAdmin.firstName,partnerAdmin.lastName,partnerAdmin.partnerCompanyInfo.companyId,"+
															 " partnerAdmin.partnerId ,partnerCompanyInfo.companyName,partnerCompanyInfo.nextfareCompanyId "+
															 " from PartnerAdminDetails partnerAdmin,PartnerCompanyInfo partnerCompanyInfo where "+
															 " partnerAdmin.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId";
	
	//to retrive Partner List for Batch Process
	public static final String GET_BATCH_PROCESS_PARTNERLIST = " select distinct(partnerAdmin.partnerId), partnerAdmin.firstName,partnerAdmin.lastName,partnerAdmin.partnerCompanyInfo.companyName," +
															   " partnerAdmin.partnerCompanyInfo.companyId "+
															   " from PartnerAdminDetails partnerAdmin,PartnerCompanyInfo partnerCompanyInfo where "+
															   " partnerAdmin.partnerCompanyInfo.companyId = partnerCompanyInfo.companyId and partnerAdmin.statusFlag is null";
	
	//to get tma companylist
	public static final String GET_TMA_COMPANYLIST_QUERY = " select distinct(tmaComp.id.companyId),partnerComp.nextfareCompanyId,partnerComp.companyName, "+
														   " tmaInfo.tmaName,partnerComp.companyId "+
														   " from TmaInformation tmaInfo,TmaCompanyLink tmaComp , PartnerCompanyInfo partnerComp "+
														   " where tmaInfo.tmaId = tmaComp.id.tmaId and partnerComp.companyId = tmaComp.id.companyId  and" +
														   " partnerComp.partnerCompanyStatus = '"+ Long.valueOf(Constants.COMPANY_STATUS_ACTIVE) +"' ";
															

}
