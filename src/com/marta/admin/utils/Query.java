package com.marta.admin.utils;

public class Query {
	
	public static final String OFOrderSummaryReportForIS = 
		"select" +			
			" sum(bcwtOrder.ordervalue)," +
			" sum(bcwtOrder.shippingamount)" +
		" from" +
			" BcwtOrder bcwtOrder" +
		" where" +
			" bcwtOrder.orderType = '" + Constants.ORDER_TYPE_IS + "'";
	
	public static final String OFOrderSummaryReportForGBS = 
		"select" +	
			" sum(bcwtOrder.ordervalue)," +
			" sum(bcwtOrder.shippingamount)" +
		" from" +
			" BcwtOrder bcwtOrder" +
		" where" +
			" bcwtOrder.orderType = '" + Constants.ORDER_TYPE_GBS + "'";
	
	public static final String OFOrderSummaryReportForPS = 
		"select" +	
			" sum(partnerCardOrder.orderQty)" +			
		" from" +
			" PartnerCardOrder partnerCardOrder";
	
	public static final String CancelledOrderReportForIS = 
		"select" +			
			" bcwtPatronBreezeCard.breezecardserialnumber," +
			" bcwtCancelledOrder.bcwtorder.orderid," +			
			" bcwtCancelledOrder.bcwtorder.bcwtpatron.firstname," +
			" bcwtCancelledOrder.bcwtorder.bcwtpatron.lastname," +
			" bcwtCancelledOrder.cancelleddate," +
			" bcwtCancelledOrder.bcwtorder.processedBy.firstname," +
			" bcwtCancelledOrder.bcwtreasontype.reasontype," +
			" bcwtCancelledOrder.bcwtorder.orderType" +						
		" from" +
			" BcwtCancelledOrder bcwtCancelledOrder," +
			" BcwtPatronBreezeCard bcwtPatronBreezeCard," +
			" BcwtOrder bcwtOrder, BcwtOrderDetails bcwtOrderDetails" +
		" where" +
			" bcwtCancelledOrder.bcwtorder.orderid = bcwtOrder.orderid" +	
			" and bcwtCancelledOrder.bcwtorder.orderid = bcwtOrderDetails.bcwtorder.orderid" +
			" and bcwtOrderDetails.bcwtpatronbreezecard.breezecardid = bcwtPatronBreezeCard.breezecardid" +
			" and bcwtCancelledOrder.activestatus = " + Constants.ACTIVE_STATUS + 
			" and bcwtOrder.orderType = '" + Constants.ORDER_TYPE_IS + "'";
	
	public static final String CancelledOrderReportForGBS = 
		"select" +
			" bcwtOrder.orderid," + // Not used this field
			" bcwtCancelledOrder.bcwtorder.orderid," +			
			" bcwtCancelledOrder.bcwtorder.bcwtpatron.firstname," +
			" bcwtCancelledOrder.bcwtorder.bcwtpatron.lastname," +
			" bcwtCancelledOrder.cancelleddate," +
			" bcwtCancelledOrder.bcwtorder.processedBy.firstname," +
			" bcwtCancelledOrder.bcwtreasontype.reasontype," +
			" bcwtCancelledOrder.bcwtorder.orderType" +
		" from" +
			" BcwtOrder bcwtOrder, BcwtCancelledOrder bcwtCancelledOrder" +			
		" where" +
			" bcwtCancelledOrder.bcwtorder.orderid = bcwtOrder.orderid" +			
			" and bcwtCancelledOrder.activestatus = " + Constants.ACTIVE_STATUS +
			" and bcwtOrder.orderType = '" + Constants.ORDER_TYPE_GBS + "'";

	public static final String CancelledOrderReportForPS = 
		"select" +			
			" bcwtPartnerCancelledOrder.partnercardorder.orderId," +			
			" bcwtPartnerCancelledOrder.partnercardorder.partnerAdmindetails.firstName," +
			" bcwtPartnerCancelledOrder.partnercardorder.partnerAdmindetails.lastName," +
			" bcwtPartnerCancelledOrder.cancelleddate," +
			" bcwtPartnerCancelledOrder.partnercardorder.processedBy.firstName," +
			" bcwtPartnerCancelledOrder.bcwtreasontype.reasontype" +			
		" from" +
			" PartnerCardOrder partnerCardOrder, BcwtPartnerCancelledOrder bcwtPartnerCancelledOrder" +
		" where" +
			" bcwtPartnerCancelledOrder.partnercardorder.orderId = partnerCardOrder.orderId" +			
			" and bcwtPartnerCancelledOrder.activestatus = " + Constants.ACTIVE_STATUS;
	
	public static final String OFQualityDetailReportForIS = 
				"select" +
					" bcwtOrder.processedBy.firstname, bcwtOrder.processedBy.lastname," +
					" bcwtQualityResult.processeddate, bcwtQualityResult.bcwtorder.orderid," +					
					" bcwtOrder.orderType" +
				" from" +
					" BcwtPatron bcwtPatron," +
					" BcwtQualityResult bcwtQualityResult," +
					" BcwtOrder bcwtOrder" +
				" where" +					 
					" bcwtOrder.orderid = bcwtQualityResult.bcwtorder.orderid" + 
					" and bcwtQualityResult.bcwtpatron.patronid = bcwtPatron.patronid" +					
					" and bcwtQualityResult.activestatus = '" + Constants.ACTIVE_STATUS + "'" +
					" and bcwtOrder.orderType = '" + Constants.ORDER_TYPE_IS + "'";
	
	public static final String OFQualityDetailReportForGBS = 
		"select" +
			" bcwtOrder.processedBy.firstname, bcwtOrder.processedBy.lastname," +
			" bcwtQualityResult.processeddate, bcwtQualityResult.bcwtorder.orderid," +					
			" bcwtOrder.orderType" +
		" from" +
			" BcwtPatron bcwtPatron," +
			" BcwtQualityResult bcwtQualityResult," +
			" BcwtOrder bcwtOrder" +
		" where" +
			" bcwtOrder.orderid = bcwtQualityResult.bcwtorder.orderid" + 
			" and bcwtQualityResult.bcwtpatron.patronid = bcwtPatron.patronid" +					
			" and bcwtQualityResult.activestatus = '" + Constants.ACTIVE_STATUS + "'" +
			" and bcwtOrder.orderType = '" + Constants.ORDER_TYPE_GBS + "'";
	
	public static final String OFQualityDetailReportForPS = 
		"select" +
			" partnerCardOrder.processedBy.firstName, partnerCardOrder.processedBy.lastName," +
			" bcwtPartnerQualityResult.processed, bcwtPartnerQualityResult.partnercardorder.orderId" +				
		" from" +			
			" BcwtPartnerQualityResult bcwtPartnerQualityResult," +
			" PartnerCardOrder partnerCardOrder" +
		" where" +
			" partnerCardOrder.orderId = bcwtPartnerQualityResult.partnercardorder.orderId" +
			" and bcwtPartnerQualityResult.activestatus = '" + Constants.ACTIVE_STATUS + "'";
			
	
	public static final String OrderDetailsForOFGBS = "select bcwtpatron.firstname,bcwtpatron.middlename,bcwtpatron.lastname,bcwtorderinfo.noofcards,bcwtorder.orderdate,bcwtorderstatus.orderstatusname,bcwtevents.eventname,bcwtevents.eventstartdate,bcwtevents.eventenddate,bcwtevents.location,bcwtorder.ordervalue,bcwtorder.shippingamount "+
	                                                   "from BcwtOrder bcwtorder,BcwtPatron bcwtpatron,BcwtOrderInfo bcwtorderinfo,BcwtOrderStatus bcwtorderstatus,BcwtEvents bcwtevents where "; 
	public static final String PaymentCardDetails =" select bcwtpatronpaymentcards.expirymonth,bcwtpatronpaymentcards.expiryyear,bcwtpatronpaymentcards.creditcardno,bcwtpatronaddress.zip,bcwtpatronpaymentcards.nameoncard,bcwtcreditcardtype.creditcardname,bcwtpatronpaymentcards.paymentmode from BcwtPatronPaymentCards bcwtpatronpaymentcards,BcwtOrder bcwtorder,"+
	                                                " BcwtPatronAddress bcwtpatronaddress,BcwtCreditCardType bcwtcreditcardtype where bcwtorder.orderid ";
	public static final String EncodedOrderDetailsForOFGBS = "select bcwtpatron.firstname,bcwtpatron.middlename,bcwtpatron.lastname,bcwtorderinfo.noofcards,bcwtorder.orderdate,bcwtorderstatus.orderstatusname,bcwtevents.eventname,bcwtevents.eventstartdate,bcwtevents.eventenddate,bcwtevents.location,bcwtorder.ordervalue,bcwtorder.shippingamount, "+
                                                              "bcwtorderinfo.nextfareorderid,bcwtorderinfo.batchid from BcwtProduct bcwtproduct, "+
                                                              "BcwtOrder bcwtorder,BcwtPatron bcwtpatron,BcwtOrderInfo bcwtorderinfo,BcwtOrderStatus bcwtorderstatus,BcwtEvents bcwtevents where "; 
	
	public static final String OFOutstandingOrdersReportForIS = 		
			"select" +
				" bcwtpatronbreezecard.breezecardserialnumber, bcwtorder.orderid," +
				" bcwtpatron.firstname, bcwtpatron.lastname," +
				" bcwtpatron.emailid, bcwtpatron.phonenumber," +
				" bcwtpatronaddress.zip, bcwtorder.orderdate," +
				" bcwtorderstatus.orderstatusname, bcwtorder.orderType" +
			" from" +
				" BcwtOrder bcwtorder, BcwtPatron bcwtpatron," +
				" BcwtPatronAddress bcwtpatronaddress, BcwtOrderStatus bcwtorderstatus," +
				" BcwtPatronBreezeCard bcwtpatronbreezecard, BcwtOrderDetails bcwtorderdetails" +
			" where" +
				" bcwtorder.orderid = bcwtorderdetails.bcwtorder.orderid" +
				" and bcwtpatronbreezecard.breezecardid = bcwtorderdetails.bcwtpatronbreezecard.breezecardid" +
				" and bcwtorder.bcwtpatron.patronid = bcwtpatron.patronid" +				
				" and bcwtorder.bcwtpatronaddress.patronaddressid = bcwtpatronaddress.patronaddressid" +
				" and bcwtorder.bcwtorderstatus.orderstatusid = bcwtorderstatus.orderstatusid" +
				" and bcwtorder.bcwtorderstatus.orderstatusid != " + Constants.ORDER_STATUS_SHIPPED + 
				" and bcwtorder.orderType = '" + Constants.ORDER_TYPE_IS + "'";

	public static final String OFOutstandingOrdersReportForGBS = 		
		"select" +
			" bcwtorderinfo.batchid, bcwtorder.orderid," +
			" bcwtpatron.firstname, bcwtpatron.lastname," +
			" bcwtpatron.emailid, bcwtpatron.phonenumber," +
			" bcwtpatronaddress.zip, bcwtorder.orderdate," +
			" bcwtorderstatus.orderstatusname, bcwtorder.orderType" +		
		" from" +
			" BcwtOrder bcwtorder, BcwtPatron bcwtpatron," +
			" BcwtPatronAddress bcwtpatronaddress, BcwtOrderStatus bcwtorderstatus," +			
			" BcwtOrderInfo bcwtorderinfo" +
		" where" +
			" bcwtorder.bcwtpatron.patronid = bcwtpatron.patronid" +				
			" and bcwtorder.bcwtpatronaddress.patronaddressid = bcwtpatronaddress.patronaddressid" +
			" and bcwtorder.bcwtorderstatus.orderstatusid = bcwtorderstatus.orderstatusid" +
			" and bcwtorder.orderid = bcwtorderinfo.bcwtorder.orderid" +
			" and bcwtorder.bcwtorderstatus.orderstatusid != " + Constants.ORDER_STATUS_SHIPPED +
			" and bcwtorder.orderType = '" + Constants.ORDER_TYPE_GBS + "'";
	     
	public static final String OFQualityAssuranceSummary = 
		"select " +
			"bcwtqualityresult.qualityresult" +
		" from " +
			"BcwtQualityResult bcwtqualityresult, BcwtOrder bcwtorder" +
		" where" +	
			" bcwtqualityresult.bcwtorder.orderid = bcwtorder.orderid" +
			" and bcwtqualityresult.activestatus = '" + Constants.ACTIVE_STATUS + "'";
	
	public static final String OFCardSerialNo = 
		"select bcwtpatronbreezecard.breezecardserialnumber from" +
		" BcwtOrder bcwtorder, BcwtPatronBreezeCard bcwtpatronbreezecard, BcwtOrderDetails bcwtorderdetails" +
		" where" +	
		" bcwtorder.orderid = bcwtorderdetails.bcwtorder.orderid" +
		" and bcwtpatronbreezecard.breezecardid = bcwtorderdetails.bcwtpatronbreezecard.breezecardid" +
		" and bcwtpatronbreezecard.activestatus = '" + Constants.ACTIVE_STATUS + "'";
	
	public static final String OFReturnedOrdersReportForIS = 
		"select" +			
			" bcwtreturnedorder.bcwtorder.orderid," +
			" bcwtreturnedorder.bcwtorder.orderdate, bcwtreturnedorder.returneddate," + 
			" bcwtreturnedorder.bcwtorder.orderType," +
			" bcwtPatronBreezeCard.breezecardserialnumber" +
		" from" +
			" BcwtReturnedOrder bcwtreturnedorder, BcwtOrder bcwtorder," +
			" BcwtPatronBreezeCard bcwtPatronBreezeCard, BcwtOrderDetails bcwtOrderDetails"  +
		" where" +	
			" bcwtreturnedorder.bcwtorder.orderid = bcwtorder.orderid" +
			" and bcwtorder.orderid = bcwtOrderDetails.bcwtorder.orderid" +	
			" and bcwtreturnedorder.bcwtorder.orderid = bcwtOrderDetails.bcwtorder.orderid" +
			" and bcwtOrderDetails.bcwtpatronbreezecard.breezecardid = bcwtPatronBreezeCard.breezecardid" +
			" and bcwtorder.orderType = '" + Constants.ORDER_TYPE_IS + "'" +
			" and bcwtreturnedorder.activestatus = '" + Constants.ACTIVE_STATUS + "'";
		
	public static final String OFReturnedOrdersReportForGBS = 
		"select" +			
			" bcwtreturnedorder.bcwtorder.orderid," +
			" bcwtreturnedorder.bcwtorder.orderdate, bcwtreturnedorder.returneddate," + 
			" bcwtreturnedorder.bcwtorder.orderType" +
		" from" +
			" BcwtReturnedOrder bcwtreturnedorder, BcwtOrder bcwtorder" +
		" where" +	
			" bcwtreturnedorder.bcwtorder.orderid = bcwtorder.orderid" +
			" and bcwtreturnedorder.activestatus = '" + Constants.ACTIVE_STATUS + "'" +
			" and bcwtorder.orderType = '" + Constants.ORDER_TYPE_GBS + "'";
	
	public static final String OFReturnedOrdersReportForPS = 
		"select" +			
			" bcwtPartnerReturnedOrder.partnercardorder.orderId," +
			" bcwtPartnerReturnedOrder.partnercardorder.orderDate," +
			" bcwtPartnerReturnedOrder.returneddate" + 
		" from" +
			" BcwtPartnerReturnedOrder bcwtPartnerReturnedOrder, PartnerCardOrder partnerCardOrder" +
		" where" +	
			" bcwtPartnerReturnedOrder.partnercardorder.orderId = partnerCardOrder.orderId" +
			" and bcwtPartnerReturnedOrder.activestatus = '" + Constants.ACTIVE_STATUS + "'";

	public static final String OFMediaSalesPerformanceMetricsReportForIS = 
		"select" +
			" bcwtOrder.orderid," +
			" bcwtOrder.orderdate," +
			" bcwtOrder.bcwtpatron.firstname," +
			" bcwtOrder.bcwtpatron.lastname," +
			" bcwtOrder.processedBy.firstname," +
			" bcwtOrder.processedBy.lastname," +
			" bcwtOrder.orderType" +
		" from" +
			" BcwtOrder bcwtOrder" +
		" where" +
			" bcwtOrder.bcwtorderstatus.orderstatusid = " + Constants.ORDER_STATUS_SHIPPED + 
			" and bcwtOrder.orderType = '" + Constants.ORDER_TYPE_IS + "'";

	public static final String OFMediaSalesPerformanceMetricsReportForGBS = 
		"select" +
			" bcwtOrder.orderid," +
			" bcwtOrder.orderdate," +
			" bcwtOrder.bcwtpatron.firstname," +
			" bcwtOrder.bcwtpatron.lastname," +
			" bcwtOrder.processedBy.firstname," +
			" bcwtOrder.processedBy.lastname," +
			" bcwtOrder.orderType" +
		" from" +
			" BcwtOrder bcwtOrder" +
		" where" +
			" bcwtOrder.bcwtorderstatus.orderstatusid = " + Constants.ORDER_STATUS_SHIPPED + 
			" and bcwtOrder.orderType = '" + Constants.ORDER_TYPE_GBS + "'";

	public static final String OFMediaSalesPerformanceMetricsReportForPS =		
		"select" +
		" partnercardorder.orderId," +
		" partnercardorder.orderDate," +
		" partnercardorder.partnerAdmindetails.firstName, " +
		" partnercardorder.partnerAdmindetails.lastName," +
		" partnercardorder.processedBy.firstName," +
		" partnercardorder.processedBy.lastName" +
	" from" +
		" PartnerCardOrder partnercardorder" +
	" where " +
		" partnercardorder.partnerCardorderstatus.orderStatusid = " + Constants.ORDER_STATUS_PS_SHIPPED;
	
	public static final String OFSalesMetricReportForIS = 
		"select" +
			" count(bcwtorder.orderid)" +
		" from" +
			" BcwtOrder bcwtorder, BcwtPatronAddress bcwtPatronAddress" +
		" where" +
			" bcwtorder.bcwtpatronaddress.patronaddressid = bcwtPatronAddress.patronaddressid" +
			" and bcwtorder.orderType = '" + Constants.ORDER_TYPE_IS + "'";
	
	public static final String OFSalesMetricReportForGBS = 
		"select" +
			" sum(bcwtOrderInfo.noofcards)" +
		" from" +
			" BcwtOrder bcwtorder, BcwtPatronAddress bcwtPatronAddress, BcwtOrderInfo bcwtOrderInfo" +
		" where" +
		    " bcwtorder.orderid = bcwtOrderInfo.bcwtorder.orderid" +
			" and bcwtorder.bcwtpatronaddress.patronaddressid = bcwtPatronAddress.patronaddressid" +
			" and bcwtorder.orderType = '" + Constants.ORDER_TYPE_GBS + "'";
	
	public static final String OFSalesMetricReportForPS = 
		"select" +
			" sum(partnerCardOrder.orderQty)" +
		" from" +
			" PartnerCardOrder partnerCardOrder, PartnerAddress partnerAddress" +
		" where" +
			" partnerCardOrder.partnerAddress.addressId = partnerAddress.addressId";
	
	public static final String OFRevenueReportForISNew = "select orderid,firstname,lastname,addressid,price,priceofbreezecard,ordertype,"
			+ "approvalcode,creditcardnumber,cardtype,cashvalue,transactiondate,shippingamount,ordervalue, LISTAGG(productname, ';') "
			+ "WITHIN GROUP (ORDER BY orderid) as products from REVENUE_REPORT_IS";
	
	public static final String OFRevenueReportForIS = 		
		"select" +
			" bcwtOrder.orderid," +
			" bcwtPatron.firstname," +
			" bcwtPatron.middlename," +
			" bcwtPatron.lastname," +
			" bcwtOrder.orderType," +
			" bcwtOrder.bcwtpatronpaymentcards.bcwtcreditcardtype.creditcardname," +
			" bcwtOrder.ordervalue," +
			" bcwtOrder.shippingamount," +
			" bcwtOrder.bcwtpatronpaymentcards.creditcardno," +
			" bcwtOrder.bcwtpatronpaymentcards.transactiondate," +
			" bcwtOrder.paymentAuthorization," +
			" bcwtOrder.bcwtpatronaddress.patronaddressid," +
			" bcwtOrder.transactiondate" +
		" from" +
			" BcwtOrder bcwtOrder," +
			" BcwtPatron bcwtPatron" +			
		" where" +
			" bcwtOrder.bcwtpatron.patronid = bcwtPatron.patronid" +
			" and bcwtOrder.orderType = '" + Constants.ORDER_TYPE_IS + "'";
	
	public static final String OFRevenueReportForGBS = 		
		"select" +
			" bcwtOrder.orderid," +
			" bcwtPatron.firstname," +
			" bcwtPatron.middlename," +
			" bcwtPatron.lastname," +
			" bcwtOrder.orderType," +
			" bcwtOrder.bcwtpatronpaymentcards.bcwtcreditcardtype.creditcardname," +
			" bcwtOrder.ordervalue," +
			" bcwtOrder.shippingamount," +
			" bcwtOrder.bcwtpatronpaymentcards.creditcardno," +
			" bcwtOrder.bcwtpatronpaymentcards.transactiondate," +
			" bcwtOrder.paymentAuthorization," +
			" bcwtOrder.bcwtpatronaddress.patronaddressid," +
			" bcwtOrder.transactiondate" +
		" from" +
			" BcwtOrder bcwtOrder," +
			" BcwtPatron bcwtPatron" +			
		" where" +
			" bcwtOrder.bcwtpatron.patronid = bcwtPatron.patronid" +
			" and bcwtOrder.orderType = '" + Constants.ORDER_TYPE_GBS + "'";
	
	public static final String OFRevenueSummaryReportForIS = 		
		"select" +
			" count(*)," +
			" sum(bcwtOrder.ordervalue)," +
			" sum(bcwtOrder.shippingamount)" +			
		" from" +
			" BcwtOrder bcwtOrder" +
		" where" +
			" bcwtOrder.orderType = '" + Constants.ORDER_TYPE_IS + "'";
	
	public static final String OFRevenueSummaryReportForGBS = 		
		"select" +
			" count(*)," +
			" sum(bcwtOrder.ordervalue)," +
			" sum(bcwtOrder.shippingamount)" +			
		" from" +
			" BcwtOrder bcwtOrder" +
		" where" +
			" bcwtOrder.orderType = '" + Constants.ORDER_TYPE_GBS + "'";
		
}
