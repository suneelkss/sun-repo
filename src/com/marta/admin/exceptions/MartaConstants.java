package com.marta.admin.exceptions;

/**
 * This class is used to describle the constants.
 * @author Administrator
 *
 */
public class MartaConstants {

	    /*** Common Error Codes ***/
	
	    public static final String SYS_CRI_0001 = "Network failure.Please contact system admin";
	    public static final String SYS_CRI_0002 = "Service has been temporarily unavailable.Please try again later";
	    public static final String SYS_CRI_0003 = "Session expired.Please Re-login";
	    public static final String SYS_CRI_0004 = "System Critical Error";

	    public static final String WS_CRI_0001 = "Network failure";
	    public static final String WS_CRI_0002 = "Internal Error";
	    public static final String WS_CRI_0003 = "Invalid service parameter";
	    public static final String WS_CRI_0004 = "Connection refused";

	    public static final String DB_CRI_0001 = "Error connecting database. Please re-login and try again";
	    public static final String DB_CRI_0002 = "Database driver not found";

	    public static final String DB_CRI_0004 = "Table not found";
	    public static final String DB_CRI_0005 = "Column not found";
	    public static final String DB_CRI_0006 = "Database schema not found";

	    public static final String DB_MAJ_0005 =  "already exists";
	    public static final String DB_MAJ_0006 =  "Syntax errors in the query";
	    
	    public static final String DB_MAJ_0007 = "Error while generating card nickname";

	    public static final String RES_CRI_0001 = "File specified is not found";
	    public static final String RES_CRI_0002 = "File type is not supported";
	    public static final String RES_CRI_0003 = "File is invalid";
	    public static final String RES_CRI_0004 = "Data format invalid";
	    public static final String RES_CRI_0005 = "Cannot read/parse a file";

	    public static final String RES_MAJ_0001 =  "File size is too large";
	    
	    /*** Error codes added for  other feature ***/
	    public static final String NULL_DATE	= "MAJ_1001";
        
	    /*** Card Order Status  ***/
	    public static final String BCWTS_CARD_ORDER_STATUS_FIND ="BCWTS_CARD_ORDER_STATUS_FIND ";
	    public static final String BCWTS_ORDER_STATUS_FIND   ="BCWTS_ORDER_STATUS_FIND ";
	    public static final String BCWTS_CARD_ORDER_STATUS_UPDATE ="BCWTS_CARD_ORDER_STATUS_UPDATE ";
	    
	    /*** Patron management ***/
        public static final String BCWTS_PATRON_FIND_TYPE="BCWTS_PATRON_FIND_TYPE";
	    public static final String BCWTS_PATRON_FIND_QUESTIONS = "BCWTS_PATRON_FIND_QUESTIONS";
	    public static final String BCWTS_PATRON_FIND_STATE  = "BCWTS_PATRON_FIND_STATE";
	    public static final String BCWTS_PATRON_FIND  = "BCWTS_PATRON_FIND";
	    public static final String BCWTS_PATRON_UPDATE = "BCWTS_PATRON_UPDATE";//"Unable to fetch";
	    public static final String BCWTS_PATRON_ADD = "BCWTS_PATRON_ADD";//"Unable to fetch";
	     
	    /*** Address Management message ***/
	    
	    public static final String BCWTS_PATRON_ADDRESS_FIND = "BCWTS_PATRON_ADDRESS_FIND";
	    public static final String BCWTS_PATRON_ADDRESS_UPDATE  = "BCWTS_PATRON_ADDRESS_UPDATE";
	    public static final String BCWTS_PATRON_ADDRESS_ADD  = "BCWTS_PATRON_ADDRESS_ADD";
	    public static final String BCWTS_PATRON_ADDRESS_STATUS  = "BCWTS_PATRON_ADDRESS_STATUS";
	    public static final String BCWTS_PATRON_ADDRESS_DELETE = "BCWTS_PATRON_ADDRESS_DELETE";	    
	    
	    public static final String BCWTS_CREDIT_CARD_ADD = "BCWTS_CREDIT_CARD_ADD";
	    public static final String BCWTS_CREDIT_CARD_UPDATE = "BCWTS_CREDIT_CARD_UPDATE";
	    public static final String BCWTS_CREDIT_CARD_DELETE = "BCWTS_CREDIT_CARD_DELETE";
	    public static final String BCWTS_CREDIT_CARD_FIND = "BCWTS_CREDIT_CARD_FIND";
	    public static final String BCWTS_EVENT_CREATE_FAILED = "BCWTS_EVENT_CREATE_FAILED";
	    public static final String BCWTS_GETTING_EVENT_FAILED = "BCWTS_GETTING_EVENT_FAILED";
	    public static final String BCWTS_DELETE_EVENT = "BCWTS_DELETE_EVENT";
	    public static final String BCWTS_UPDATE_EVENT = "BCWTS_UPDATE_EVENT";
	    public static final String BCWTS_ASSOCIATION_FAILED = "BCWTS_ASSOCIATION_FAILED";
	    
	    /*** Order Management message ***/
	    
	    public static final String BCWTS_ORDER_SEARCH = "BCWTS_ORDER_SEARCH";
	    public static final String BCWTS_ORDER_FIND  = "BCWTS_ORDER_FIND";
	   
	    /*** Constants for  View / Edit card ***/
	    
	    public static final String BCWTS_ADD  = "BCWTS_ADD";
	    public static final String BCWTS_NICKNAME_UPDATE  = "BCWTS_NICKNAME_UPDATE";
	    public static final String BCWTS_THRESHOLD_UPDATE  = "BCWTS_THRESHOLD_UPDATE";
	    public static final String BCWTS_RECURRING_UPDATE  = "BCWTS_RECURRING_UPDATE";
	    
	    public static final String BCWTS_FIND  = "BCWTS_FIND";
	    public static final String BCWTS_FIND_IN_NEXT_FARE  = "BCWTS_FIND_IN_NEXT_FARE";
	    public static final String BCWTS_THRESHOLD_FIND  = "BCWTS_THRESHOLD_FIND";
	    public static final String BCWTS_RECURRING_FIND  = "BCWTS_RECURRING_FIND";
	    
	    public static final String BCWTS_REMOVE  = "BCWTS_REMOVE";
	    public static final String BCWTS_DEACTIVATE  = "BCWTS_DEACTIVATE";
	    public static final String BCWTS_ACTIVATE  = "BCWTS_ACTIVATE";
	    
	    public static final String BCWTS_REGION = "BCWTS_REGION";
	    
	    /*** Recurring threshold ***/
	    
	    public static final String BCWTS_BREEZE_CARD_RECURRING_FIND = "BCWTS_BREEZE_CARD_RECURRING_FIND";
	    public static final String BCWTS_BREEZE_CARD_RECURRING_ADD = "BCWTS_BREEZE_CARD_RECURRING_ADD";
	    public static final String BCWTS_BREEZE_CARD_RECURRING_UPDATE = "BCWTS_BREEZE_CARD_RECURRING_UPDATE";
	    public static final String BCWTS_BREEZE_CARD_RECURRING_DELETE = "BCWTS_BREEZE_CARD_RECURRING_DELETE";
	    
	    public static final String BCWTS_BREEZE_CARD_THRESHOLD_FIND = "BCWTS_BREEZE_CARD_THRESHOLD_FIND";
	    public static final String BCWTS_BREEZE_CARD_THRESHOLD_ADD = "BCWTS_BREEZE_CARD_THRESHOLD_ADD";
	    public static final String BCWTS_BREEZE_CARD_THRESHOLD_UPDATE = "BCWTS_BREEZE_CARD_THRESHOLD_UPDATE";
	    public static final String BCWTS_BREEZE_CARD_THRESHOLD_DELETE = "BCWTS_BREEZE_CARD_THRESHOLD_DELETE";
	    
	    /*** menus ***/
	    
	    public static final String BCWTS_MENUS_FIND = "BCWTS_MENUS_FIND";
	    
	    public static final String BCWTS_EVENTS_FIND = "BCWTS_EVENTS_FIND";
	    
	    public static final String BCWTS_PATRON_USER_FIND = "BCWTS_PATRON_USER_FIND";
	    public static final String BCWTS_PATRON_USER_UPDATE  = "BCWTS_PATRON_USER_UPDATE";
	    public static final String BCWTS_PATRON_USER_ADD  = "BCWTS_PATRON_USER_ADD";
	    public static final String BCWTS_PATRON_USER_DELETE = "BCWTS_PATRON_USER_DELETE";	
	    public static final String BCWTS_FIND_EVENT_DETAILS  = "BCWTS_FIND_EVENT_DETAILS";
	    public static final String BCWTS_FIND_LOG_DETAILS  = "BCWTS_FIND_LOG_DETAILS";
	    public static final String BCWTS_FIND_PARTNER_DETAILS  = "BCWTS_FIND_PARTNER_DETAILS";
	    
	    //Manage Marta Administrator
	    public static final String BCWTS_ADMIN_USER_LIST_FIND  = "BCWTS_ADMIN_USER_LIST_FIND";//Unable to get user detail list
	    public static final String BCWTS_ADMIN_USER_FIND = "BCWTS_ADMIN_USER_FIND";
	    public static final String BCWTS_ADMIN_USER_ADD = "BCWTS_ADMIN_USER_ADD";
	    public static final String BCWTS_ADMIN_USER_DELETE = "BCWTS_ADMIN_USER_DELETE";
	    public static final String BCWTS_ADMIN_DETAILS = "BCWTS_ADMIN_DETAILS";
	    public static final String BCWTS_UNLOCK_ADMIN_DETAILS = "BCWTS_UNLOCK_ADMIN_DETAILS";
	    
	    public static final String BCWTS_PARTNER_REGISTRATION_LIST_FIND ="BCWTS_PARTNER_REGISTRATION_LIST_FIND"; 
	    
	//view Reports
	    public static final String BCWTS_REPORT_FIND_HOTLIST = "BCWTS_REPORT_FIND_HOTLIST";  
	    public static final String BCWTS_REPORT_LIST = "BCWTS_ORDER_REPORTS";
	    //For Applications Settings
	    public static final String APPLICATION_SETTINGS_UPDATE = "APPLICATION_SETTINGS_UPDATE";
	    public static final String APPLICATION_SETTINGS_LIST_FIND = "APPLICATION_SETTINGS_LIST_FIND";
	    
	    /*** order search ***/
	    
	    public static final String BCWTS_ORDER_SEARCH_FIND = "BCWTS_ORDER_SEARCH_FIND";
	    
	    //for Request to Hot list a Card
		public static final String HOT_LIST_DETAILS_FOR_PS = "HOT_LIST_DETAILS_FOR_PS";
		public static final String HOT_LIST_DETAILS_FOR_IS = "HOT_LIST_DETAILS_FOR_IS";
		public static final String HOT_LIST_DETAILS_FOR_GBS = "HOT_LIST_DETAILS_FOR_GBS";
		public static final String UPDATE_HOT_LIST_CARD_DETAILS = "UPDATE_HOT_LIST_CARD_DETAILS";
		public static final String HOT_LIST_CARD_DETAILS_LIST = "HOT_LIST_CARD_DETAILS_LIST";
		
	    public static final String BCWTS_ADMIN_CHECK_USERNAME = "BCWTS_ADMIN_CHECK_USERNAME";
	    
	    //for partnerupcomingMonthly Report
	    public static final String BCWTS_REPORT_ACT_LIST = "BCWTS_ORDER_REPORTS";
	    public static final String BCWTS_REPORT_DE_ACT_LIST = "BCWTS_REPORT_DE_ACT_LIST";
	    
	    //for GeneralLedger Report
	    public static final String BCWTS_GENERAL_LEDGER_REPORT = "BCWTS_GENERAL_LEDGER_REPORT";
	    /**
	     * OF reports
	     */
	    //public static final String BCWTS_REPORT_LIST = "BCWTS_ORDER_REPORTS";
	    public static final String BCWTS_ORDER_SUMMARY = "BCWTS_ORDER_SUMMARY";
	    public static final String BCWTS_ORDER_CANCELLED = "BCWTS_ORDER_CANCELLED";
	    public static final String BCWTS_OUTSTANDING_ORDERS_REPORT = "BCWTS_OUTSTANDING_ORDERS_REPORT";
	    public static final String BCWTS_QUALITY_ASSURANCE_SUMMARY_REPORT = "BCWTS_QUALITY_ASSURANCE_SUMMARY_REPORT";
	    public static final String BCWTS_RETURNED_ORDERS_REPORT = "BCWTS_RETURNED_ORDERS_REPORT";
	    public static final String BCWTS_SALES_METRICS_REPORT = "BCWTS_SALES_METRICS_REPORT";
	    public static final String BCWTS_MEDIASALES_PERFORMANCE_METRICS_REPORT = "BCWTS_MEDIASALES_PERFORMANCE_METRICS_REPORT";
	    public static final String BCWTS_REVENUE_REPORT = "BCWTS_REVENUE_REPORT";
	    public static final String BCWTS_REVENUE_SUMMARY_REPORT = "BCWTS_REVENUE_SUMMARY_REPORT";

	    //For View Edit Products
	    public static final String VIEW_EDIT_PRODUCTS_LIST_FIND = "VIEW_EDIT_PRODUCTS_LIST_FIND";
	    public static final String VIEW_EDIT_PRODUCTS_UPDATE = "VIEW_EDIT_PRODUCTS_UPDATE";
	    
	    //print shipping label
	    public static final String BCWTS_PRINT_SHIPPING_LABEL_DETAILS = "BCWTS_PRINT_SHIPPING_LABEL_DETAILS";
	    public static final String BCWTS_PRINT_SHIPPING_LABEL_PRINT = "BCWTS_PRINT_SHIPPING_LABEL_PRINT";
}
