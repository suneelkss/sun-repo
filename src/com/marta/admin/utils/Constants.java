/**
 *
 */
package com.marta.admin.utils;
/**
 * This class is used to describle the constants.
 * @author Administrator
 *
 */
public class Constants {

	public static final String PATRON_TYPE_ADMIN = "5";
	public static final String MARTA_SUPREME_ADMIN = "50";
	public static final String MARTA_SUPER_ADMIN = "51";
	public static final String MARTA_ADMIN="52";
	public static final String MARTA_READONLY="53";
	public static final String MARTA_IT_ADMIN="54";
	public static final String SUPER_ADMIN="10";
	public static final String ADMIN="11";
	public static final String READ_ONLY="12";
	public static final String BATCHPROCESS="13";
	public static final String TMA="14";
	public static final String IS="1";
	public static final String GBS_SUPER_ADMIN="21";
	public static final String GBS_ADMIN="22";
	public static final String GBS_READONLY="23";
	
	public static final String BREAD_CRUMB_NAME = "BREADCRUMBNAME";
	
	//Update product price
	public static final String PRODUCT_PRICE_UPDATE_SUCCESS_MESAAGE = "PRODUCT_PRICE_UPDATE_SUCCESS_MESAAGE";
	public static final String PRODUCT_PRICE_UPDATE_FAILURE_MESAAGE = "PRODUCT_PRICE_UPDATE_FAILURE_MESAAGE";
	public static final String PRODUCT_PRICE_UPDATE_BREADCRUMB =" >> My Account >> Update Product Price";
	
	//ADD NEW PRODUCT
	public static final String PRODUCT_ADD_SUCESS_MESAAGE = "PRODUCT_ADD_SUCESS_MESAAGE";
	public static final String PRODUCT_ADD_FAILURE_MESAAGE = "PRODUCT_ADD_FAILURE_MESAAGE";
	public static final String NEXT_FARE_INSTRUMENT_EXISTS_STAUS = "NEXT_FARE_INSTRUMENT_EXISTS_STAUS";
	//Hotlist card
	public static final String HOTLIST_CARD_MANF_NUMBER = "160";
	public static final String HOTLIST_CARD_ID = "1";
	
	public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
	public static final String ACCOUNT_TYPE_PARTNER = "PARTNER";
	public static final String ACCOUNT_TYPE_TMA = "TMA";

	//TMA Password Status
	public static final String TMA_PASSWORD_STATUS_ACTIVE = "Active";
	public static final String TMA_PASSWORD_STATUS_BARRED = "Barred";
	
	//Status Flag - Partner_AdminDetails
	public static final String STATUS_FLAG_BARRED = "Barred";
	
	//Company Status	
	public static final String COMPANY_STATUS_REGISTERED = "201";
	public static final String COMPANY_STATUS_HOTLISTED = "202";
	public static final String COMPANY_STATUS_ACTIVE = "203";
	public static final String COMPANY_STATUS_ON_HOLD = "204";
	public static final String COMPANY_STATUS_PENDING_REGISTRATION = "205";
	public static final String STATUS_PENDING_REGISTRATION ="Pending Registration";
	public static final String STATUS_REGISTRATION ="Registered";
	public static final String STATUS_FLAG_ACTIVE = "Active";
	public static final String STATUS_FLAG_ONHOLD = "On Hold";
	public static final String STATUS_FLAG_HOTLISTED = "Hotlisted";
	
	//for utils
	public static final String XML_ROW_NODE = "data-row";
	public static final String XML_DATA_NODE = "data-value";
	public static final String XML_FILE_EXTN = ".XML";
	public static final String COMMA_DELIMITER = ",";	
	
	public static final String MESSAGE = "alertMessage";
    public static final String INFO = "info";
	public static final String WARNING = "warning";
	public static final String SUCCESS = "success";
	
	public static final String  DATE_FORMAT = "MM/dd/yyyy";
	public static final String  DATE_FORMAT2 = "dd-MM-yyyy";
	public static final String DATE_WITH_TIME_FORMAT = "MM/dd/yyyy hh:mm:ss";
	
	public static final String EXPORT_TO_CSV = "1";
	public static final String EXPORT_TO_TSV = "2";
	public static final String EXPORT_TO_TXT = "3";
	public static final String EXPORT_TO_PIPE = "4";
	
	public static final String EMPTY_STRING="";
	public static final String TRIPLE_HYPHEN="---";
	public static final String SINGLE_HYPHEN = "-";
	//add new product
	public static final String PRICE="PRICE";
	//	general
	public static final String YES = "yes";
	public static final String ON = "on";
	public static final String OFF = "off";
	public static final String NO = "no";
	public static final String NO_RECORDS_FOUND = "NO_RECORDS_FOUND";
	
	//active,inactivte State	
	public static final String ACTIVE_STATUS = "1";
	public static final String IN_ACTIVE_STATUS = "0";
	
	//for user management
	public static final String STATE_LIST = "STATE_LIST";
	public static final String PATRON_TYPE_NAME_LIST="PATRON_TYPE_NAME_LIST";
	public static final String SECRET_QUESTIONS_LIST = "SECRET_QUESTIONS_LIST";
	public static final String SECRET_QUESTIONS_ANSWER = "SECRET_QUESTIONS_ANSWER";
	public static final String IS_SHOW = "IS_SHOW";
	public static final String PATRON_ID = "PATRON_ID";
	public static final String USER_INFO = "USER_INFO ";
	public static final String BREEZECARD_LVBLIST= "BREEZECARD_LVBLIST";
	public static final String CARDDETAILS_MAP= "CARDDETAILS_MAP";
	public static final String CARD_PRODUCTS_MAP= "CARD_PRODUCTS_MAP";
	public static final String PATRON_CARDS= "PATRON_CARDS";
	public static final String IS_ADD_SHOW = "IS_ADD_SHOW";
	public static final String IS_EDIT_SHOW = "IS_EDIT_SHOW";
	public static final String SECRET_QUESTION = "SECRET_QUESTION";
	public static final String BREADCRUMB_MANAGE_ADDRESS = ">> My Account >> Manage Addresses";
	public static final String BREADCRUMB_VIEW_ORDER = ">> My Orders >> View Orders";
	public static final String BREADCRUMB_CANCEL_ORDER = ">> My Orders >> Cancel Orders";
	public static final String BREADCRUMB_VIEW_EDIT_PROFILE = ">> My Account >> View / Edit Profile";
	public static final String BREADCRUMB_NEW_CARD_REPORT =">> Reports >> Partner Report >> New Card Report";
	public static final String BREADCRUMB_CREATE_MANUAL_ALERT =">>  My Account >> Creating Manual Alert ";
	public static final String LOGGING_CALLS="LOGGING_CALLS";
	public static final String CALL_DETAILS_LIST="CALL_DETAILS_LIST";
	public static final String USER_NAME_LIST="USER_NAME_LIST";
	public static final String BREADCRUMB_BATCH_PROCESSING = ">>  My Account >>  Batch Processing";
	
	//for usertype Management	
	public static final String IS_MODULEID = "1";
	
	//mail content
	public static final String SIGN_UP_MAIL_CONTENT = "SIGN_UP_MAIL_CONTENT";
	public static final String SIGN_UP_MAIL_SUBJECT = "SIGN_UP_MAIL_SUBJECT";
	public static final String FORGOT_PWD_MAIL_CONTENT = "FORGOT_PWD_MAIL_CONTENT";
	public static final String FORGOT_PWD_MAIL_SUBJECT = "FORGOT_PWD_MAIL_SUBJECT";

	//login error message	
	public static final String BCWTS_PATRON_LOGIN_FAILUR_MESSAGE="BCWTS_PATRON_LOGIN_FAILUR_MESSAGE";
	public static final String BCWTS_PATRON_ACCOUNT_ACTIVATED_MESSAGE="BCWTS_PATRON_ACCOUNT_ACTIVATED_MESSAGE";
	public static final String BCWTS_PATRON_EMAIL_INCORRECT_MESSAGE="BCWTS_PATRON_EMAIL_INCORRECT_MESSAGE";
	public static final String PATRON_LOCKMESSAGES ="BCWTS_PATRON_ACCOUNT_LOCKED_MESSAGE";
	public static final String PATRON_LOCKEDAFTERLOGIN="BCWTS_PATRON_ACCOUNT_LOCKED_AFTER_LOGIN_MESSAGE";
	public static final String BCWTS_PATRON_LOGIN_SUPREM_ADMIN_FAILUR_MESSAGE="BCWTS_PATRON_LOGIN_SUPREM_ADMIN_FAILUR_MESSAGE";
	
	//	For breezecard management
	public static final String BREEZECARD_PRODUCTDETAILS = "BREEZECARD_PRODUCTDETAILS";
	public static final String BREEZECARD_PRODUCTSLIST = "BREEZECARD_PRODUCTSLIST";
	public static final String EXISTING_BREEZECARD_LIST = "EXISTING_BREEZECARD_LIST";
	public static final String NEW_CARD_COUNT = "NEW_CARD_COUNT";
	public static final String SHOPPINGCART = "SHOPPINGCART";
	public static final String SHOPPINGCART_RELOADCARD = "SHOPPINGCART_RELOADCARD";
	public static final String NEWCART = "NEWCART";
	public static final String ADDRESS_NICKNAMELIST = "ADDRESS_NICKNAMELIST";	
	public static final String AVAILABLE_CASH_VALUE = "AVAILABLE_CASH_VALUE";	
	public static final String CARDS_IN_CART = "CARDS_IN_CART";
	public static final String AVAILABLE_CASH_VALUE_MAP = "AVAILABLE_CASH_VALUE_MAP";	
	
	public static final String CREDIT_PAYMENT_MODE = "CREDIT_PAYMENT_MODE";
	public static final String CREDIT_CARD_TYPE_LIST = "CREDIT_CARD_TYPE_LIST";
	public static final String DEBIT_CARD_TYPE_LIST = "DEBIT_CARD_TYPE_LIST";
	public static final String PAYMENT_CARD_LIST = "PAYMENT_CARD_LIST";
	public static final String CREDIT_CARD_TYPE_ID = "1";
	public static final String DEBIT_CARD_TYPE_ID = "2";
	public static final String MONTH_LIST = "MONTH_LIST";
	public static final String YEAR_LIST = "YEAR_LIST";
	public static final String BCWTS_ORDER_MESSAGE = "BCWTS_ORDER_MESSAGE";
	public static final String BCWTS_ORDER_SUBJECT = "BCWTS_ORDER_SUBJECT";
	public static final String DISPLAY_EDIT_PAGE = "DISPLAY_EDIT_PAGE";

	//PS - Order Status
	public static final String ORDER_STATUS_PS_SUBMITTED = "151"; //Pending
	public static final String ORDER_STATUS_PS_SHIPPED = "153";
	public static final String ORDER_STATUS_PS_REJECTED = "154"; //Cancelled
	public static final String ORDER_STATUS_PS_RETURNED = "155";
		
	//Order Status
	public static final String ORDER_STATUS = "ORDER_STATUS";
	public static final String ORDER_PENDING = "Pending";
	public static final String ORDER_REJECTED = "rejected";
	public static final String ORDER_SHIPPED = "Shipped";
	public static final String IN_PROCESS = "In Process";
	
	public static final String ORDER_STATUS_PENDING = "1";
	public static final String ORDER_STATUS_REJECTED = "3";
	public static final String ORDER_STATUS_SHIPPED = "2";
	public static final String ORDER_STATUS_ORDERED = "4";
	public static final String ORDER_STATUS_DELETED = "11";
	public static final String ORDER_STATUS_CANCELLED = "6";
	public static final String ORDER_STATUS_RETURNED = "7";
	public static final String ORDER_STATUS_COMPLETED = "13";
		
	//change password error message	
	public static final String PWD_NOTMATCH_DB="BCWTS_PATRON_PASSWORD_NOTMATCH";
	public static final String PWD_NOTMATCH_CONFIRMPWD="BCWTS_PATRON_PASSWORD_CONFIRM";
	public static final String PWD_CHANGED="BCWTS_PATRON_PASSWORD_SUCCESS";
	public static final String PWD_NOTCHANGED="BCWTS_PATRON_PASSWORD_FAILURE";
		
	//	for address management
	public static final String ADDRESS_LIST = "ADDRESS_LIST";
	public static final String ADDRESS_ADD_SUCESS_MESAAGE = "ADDRESS_ADD_SUCESS_MESAAGE";
	public static final String ADDRESS_ADD_FAILURE_MESAAGE = "ADDRESS_ADD_FAILURE_MESAAGE";
	public static final String ADDRESS_UPDATE_SUCESS_MESAAGE = "ADDRESS_UPDATE_SUCESS_MESAAGE";
	public static final String ADDRESS_UPDATE_FAILURE_MESAAGE = "ADDRESS_UPDATE_FAILURE_MESAAGE";
	public static final String ADDRESS_DELETE_SUCESS_MESAAGE = "ADDRESS_DELETE_SUCESS_MESAAGE";
	public static final String ADDRESS_DELETE_FAILURE_MESAAGE = "ADDRESS_DELETE_FAILURE_MESAAGE";
	public static final String ADDRESS_ORDER_MESAAGE = "ADDRESS_ORDER_MESAAGE";
	public static final String ADDRESS_CARD_MESAAGE = "ADDRESS_CARD_MESAAGE";
	public static final String ADDRESS_VERIFICATION_FAILURE_MESAAGE = "ADDRESS_VERIFICATION_FAILURE_MESAAGE";
	
	//	for patron management
	public static final String PATRON_ADD_SUCESS_MESAAGE = "PATRON_ADD_SUCESS_MESAAGE";
	public static final String PATRON_ADD_FAILURE_MESAAGE = "PATRON_ADD_SUCESS_MESAAGE";
	public static final String PATRON_UPDATE_SUCESS_MESAAGE = "PATRON_UPDATE_SUCESS_MESAAGE";
	public static final String PATRON_UPDATE_FAILURE_MESAAGE = "PATRON_UPDATE_FAILURE_MESAAGE";
	public static final String PATRON_FORGET_PASSWORD_SUCESS_MESAAGE = "PATRON_FORGET_PASSWORD_SUCESS_MESAAGE";
	public static final String PATRON_FORGET_PASSWORD_FAILURE_MESAAGE = "PATRON_FORGET_PASSWORD_FAILURE_MESAAGE";
	public static final String PATRON_WRONG_ANSWER_MESAAGE = "PATRON_WRONG_ANSWER_MESAAGE";
	public static final String PATRON_CAPTCHA_MESAAGE = "PATRON_CAPTCHA_MESAAGE";
	public static final String IS_CHANGE_PASSWORD_SHOW = "IS_CHANGE_PASSWORD_SHOW";
	
	// View / Edit Breeze Card
	public static final String UPDATE_NICKNAME_ACTION = "update";
	public static final String THRESHOLD_ACTION = "threshold";
	public static final String RECURRING_ACTION = "recurring";
	public static final String REMOVECARD_ACTION = "remove";
	public static final String ACTIVATE_DEACTIVATE_ACTION = "activeDeactive";
	public static final String LAST_LOGIN="LAST_LOGIN";
	public static final String RECURRING_FREQUENCY_WEEKLY = "weekly";
	public static final String RECURRING_FREQUENCY_MONTHLY = "monthly";
	public static final String ADD_EXISTING_CARD = "addExitingCard";
	public static final String ACTION_TAKEN = "action";

	public static final String BCWTS_CREDIT_CARD_ADD_SUCESS_MESAAGE = "BCWTS_CREDIT_CARD_ADD_SUCESS_MESAAGE";
	public static final String BCWTS_CREDIT_CARD_ADD_FAILURE_MESAAGE = "BCWTS_CREDIT_CARD_ADD_FAILURE_MESAAGE";
	public static final String BCWTS_CREDIT_CARD_UPDATE_SUCESS_MESAAGE = "BCWTS_CREDIT_CARD_UPDATE_SUCESS_MESAAGE";
	public static final String BCWTS_CREDIT_CARD_UPDATE_FAILURE_MESAAGE = "BCWTS_CREDIT_CARD_UPDATE_FAILURE_MESAAGE";
	public static final String BCWTS_CREDIT_CARD_DELETE_SUCESS_MESAAGE = "BCWTS_CREDIT_CARD_DELETE_SUCESS_MESAAGE";
	public static final String BCWTS_CREDIT_CARD_DELETE_FAILURE_MESAAGE = "BCWTS_CREDIT_CARD_DELETE_FAILURE_MESAAGE";
	
	//order management
	public static final String ORDER_DETAILS_LIST = "ORDER_DETAILS_LIST";
	public static final String ORDER_DETAILS_LIST_PENDING = "ORDER_DETAILS_LIST_PENDING";
	public static final String ORDER_STATUS_LIST = "ORDER_STATUS_LIST";
	
	// View Order History	
	public static final String ORDER_UPDATE_SUCCESS = "ORDER_UPDATE_SUCCESS";
	public static final String ORDER_UPDATE_FAILURE = "ORDER_UPDATE_FAILURE";
	
	//For linking existing cards	
	public static final String LINK_CARD_SUCCESS = "Your Card has been linked to system";
	public static final String LINK_CARD_FAILURE = "Problem occured while linking the card to system";
	public static final String BREEZE_CARD_EXIST = "Breeze Card Already Exist";
	public static final String LINK_EXISTING_DEACTIVATED_CARD = "Your Card has been linked to system";
	public static final String LINK_HOTLISTED_CARD = "This Breeze card has been Hotlisted, Please contact MARTA admin to activate.";
	public static final String BREEZECARD_ALREADY_ASSOICATED = "This Breeze Card is already associated to another patron.";
	public static final String INVALID_BREEZECARD = "Invalid breeze card serial number";
		
	//georgia state id
	public static final String GEORGIA_STATE_ID = "13";
	
	//Context Variables;
	public static final String MARTACONTEXTDTO="MARTAContextDTO";
	public static final String SESSION_CLOSED_ERROR = "/jsp/Login.jsp";
	public static final String SESSION_CLEAR_CONFORMAITION = "/jsp/ClearSession.jsp";
	public static final String MARTACONTEXTMAP = "MARTACONTEXTMAP";
	public static final String MARTAUSERSESSIONMAP = "MARTAUSERSESSIONMAP";	
	public static final String SESSION_EXPIRED="global.sessionexpired";
	
	public static final String CASH="Cash";
	public static final String PRODUCT="Product";
	public static final String RECURRING_DETAILS_LIST="RECURRING_DETAILS_LIST";
	public static final String REMOVE_THRESHOLD_ACTION = "removeThreshold";
	
	public static final String MAXIMUM_CASH_VALUE = "100";
	public static final String MAXIMUM_CASH_VALUE_FAILURE_MESSAGE_PURCHASE = "MAXIMUM_CASH_VALUE_FAILURE_MESSAGE_PURCHASE";
	public static final String MAXIMUM_CASH_VALUE_FAILURE_MESSAGE_RELOAD = "MAXIMUM_CASH_VALUE_FAILURE_MESSAGE_RELOAD";
	public static final String INVALID_BREEZECARD_SERIAL_NUMBER = "0000000000000000";
	public static final String EMPTY_SPACE = " ";
	
	//USPS Verification
	public static final String USPS_SERVER_URL = "USPS_SERVER_URL";
	public static final String USPS_API = "USPS_API";
	public static final String USPS_USERID = "USPS_USERID";
	public static final String STATE_INFO_MAP= "STATE_INFO_MAP";
	public static final String STATUS_INFO_MAP= "STATUS_INFO_MAP";
	public static final String GBS_STATUS_LIST= "GBS_STATUS_LIST";
	public static final String STATUS_LIST= "STATUS_LIST";
	public static final String FARE_INSTRUMENT_LIST= "FARE_INSTRUMENT_LIST";
	public static final String RIDER_CLASSIFICATION_LIST= "RIDER_CLASSIFICATION_LIST";
	public static final String COMPANY_TYPE_LIST= "COMPANY_TYPE_LIST";
	public static final String TMA_NAME_LIST= "TMA_NAME_LIST";
	
	public static final String RIDER_INFO_MAP= "RIDER_INFO_MAP";
	public static final String RIDER_LIST= "RIDER_LIST";
	public static final String CREDITCARD_VERIFICATION_FAILURE_MESAAGE = "CREDITCARD_VERIFICATION_FAILURE_MESAAGE";
	
	
	public static final String REGION_LIST = "REGION_LIST";
	public static final String REGIONDTOLIST = "REGIONDTOLIST";
	public static final String PRODUCTLIST_REGION = "PRODUCTLIST_REGION";
	public static final String MARTAREGIONCODE = "MARTA";
	public static final String PRODUCTCOLLECTION = "PRODUCTCOLLECTION"; 
	
	//recurring
	public static final String BCWTS_RECURRING_ADD_SUCESS_MESAAGE = "BCWTS_RECURRING_ADD_SUCESS_MESAAGE";
	public static final String BCWTS_RECURRING_ADD_FAILURE_MESAAGE = "BCWTS_RECURRING_ADD_FAILURE_MESAAGE";
	public static final String BCWTS_RECURRING_UPDATE_SUCESS_MESAAGE = "BCWTS_RECURRING_UPDATE_SUCESS_MESAAGE";
	public static final String BCWTS_RECURRING_UPDATE_FAILURE_MESAAGE = "BCWTS_RECURRING_UPDATE_FAILURE_MESAAGE";
	public static final String BCWTS_RECURRING_DELETE_SUCESS_MESAAGE = "BCWTS_RECURRING_DELETE_SUCESS_MESAAGE";
	public static final String BCWTS_RECURRING_DELETE_FAILURE_MESAAGE = "BCWTS_RECURRING_DELETE_SUCESS_MESAAGE";
	
	//threshold
	public static final String BCWTS_THRESHOLD_ADD_SUCESS_MESAAGE = "BCWTS_THRESHOLD_ADD_SUCESS_MESAAGE";
	public static final String BCWTS_THRESHOLD_ADD_FAILURE_MESAAGE = "BCWTS_THRESHOLD_ADD_FAILURE_MESAAGE";
	public static final String BCWTS_THRESHOLD_UPDATE_SUCESS_MESAAGE = "BCWTS_THRESHOLD_UPDATE_SUCESS_MESAAGE";
	public static final String BCWTS_THRESHOLD_UPDATE_FAILURE_MESAAGE = "BCWTS_THRESHOLD_UPDATE_FAILURE_MESAAGE";
	public static final String BCWTS_THRESHOLD_DELETE_SUCESS_MESAAGE = "BCWTS_THRESHOLD_DELETE_SUCESS_MESAAGE";
	public static final String BCWTS_THRESHOLD_DELETE_FAILURE_MESAAGE = "BCWTS_THRESHOLD_DELETE_FAILURE_MESAAGE";

	public static final String IS_MARTA_ENV = "IS_MARTA_ENV";
	public static final String IS_ADDRESS_VAL = "IS_ADDRESS_VAL";
	public static final String IS_MAX_CARD_COUNT = "IS_MAX_CARD_COUNT";
	
	//menus
	public static final String MENU_MAP = "MENU_MAP";
	public static final String MENU_LIST = "MENU_LIST";
	
	public static final String MARTAREGIONID = "0";
	
	public static final String SOUND_CAPTCHA_FILE_PATH = "SOUND_CAPTCHA_FILE_PATH";
	public static final String MARTA_LOGO = "images/marta_logo.jpg";
		
	public static final String CASH_PRODUCTDESC = "Cash Value"; 
	public static final String CASH_FAREINSTRUMENTID = "32769";
	public static final String CASH_PRODUCTID = "999";
	
	public static final String BREEZE_CARD_SERIAL_NUMBER = "breezeCardSerialNumber";
	public static final String PREVIOUS_BREEZE_CARD_SERIAL_NUMBER = "PREVIOUS_BREEZE_CARD_SERIAL_NUMBER";
	public static final String BREEZE_CARD_DOES_NOT_MATCH = "breezecard.cardserialnumber.match";
	
	public static final int BREEZE_CARD_SERIAL_NUMBER_LENGTH = 16;
	
	public static final String CARD_NICKNAME = "NewCard";
	
	public static final String PASSWORD_CONDITION  = "breezecard.passwordcondition";
	
	public static final String IS_ORDER_PENDING = "IS_ORDER_PENDING"; 
	public static final String PENDING = "Pending";
	public static final String SUBMITTED= "Submitted";
	
	
	public static final String PENDING_PRODUCT_LIST = "PENDING_PRODUCT_LIST";
	
	public static final String CARD_NUMBER_PENDING = "Card Number Pending";
	
	public static final String INDIVIDUAL_SALES = "INDIVIDUAL_SALES";
	public static final String GBS = "GBS";
	public static final String EVENT_LIST = "EVENT_LIST";
	public static final String EVENT_DELETE_SUCESS_MESAAGE = "EVENT_DELETE_SUCESS_MESAAGE";
	public static final String EVENT_DELETE_FAILURE_MESAAGE = "EVENT_DELETE_FAILURE_MESAAGE";
	public static final String EVENT_UPDATED_SUCESS_MESAAGE = "EVENT_UPDATED_SUCESS_MESAAGE";
	public static final String EVENT_UPDATE_FAILURE_MESAAGE = "EVENT_UPDATE_FAILURE_MESAAGE";
	public static final String EVENT_CREATED_SUCESS_MESAAGE = "EVENT_CREATED_SUCESS_MESAAGE";
	public static final String EVENT_CREATED_FAILURE_MESAAGE = "EVENT_CREATED_FAILURE_MESAAGE";
	
	public static final String GBS_SUPER_ADMIN_IDS = "GBS_SUPER_ADMIN_IDS";
	public static final String GBS_ADMIN_IDS = "GBS_ADMIN_IDS";
	public static final String EVENT_ORDER_DETAILS = "EVENT_ORDER_DETAILS";
	public static final String BREADCRUMB_MANAGE_EVENTS = ">> My Events >> Manage Events";
	public static final String BREADCRUMB_CREATE_EVENTS = ">> My Events >> Create Events";
	
	public static final String DEFAULT_RIDER_CLASSIFICATION = "1";
	public static final String EVENT_NAME_ALREADY_EXIST = "Event name already exists, Give any other name";
	
	public static final String ADD_TO_CART = "addToCart";
	
	public static final String NEW_EVENT = "NewEvent1";
	
	public static final String REMOVE_CASH = "removeCash";
	public static final String UPDATE_CASH = "updateCash";
	public static final String REMOVE_PRODUCT = "removeProduct";
	
	public static final String RELOAD_CARD = "reloadCard";
	public static final String UNDERSCORE = "_";
	
	public static final String READ_ONLY_USER_TYPE = "23";
	public static final String USER_LIST = "USER_LIST";
	public static final String IS_ADD_SHOW_USER = "IS_ADD_SHOW_USER";
	public static final String IS_EDIT_SHOW_USER = "IS_EDIT_SHOW_USER";
	
	//	for user management
	public static final String USER_ADD_SUCESS_MESAAGE = "USER_ADD_SUCESS_MESAAGE";	
	public static final String USER_DELETE_SUCESS_MESAAGE = "USER_DELETE_SUCESS_MESAAGE";
	public static final String USER_DELETE_FAILURE_MESAAGE = "USER_DELETE_FAILURE_MESAAGE";
	
	//Adminstrator breadcrum
	public static final String BREADCRUMB_MANAGE_USER = ">> My Account >> Manage Administrator";
	public static final String SEARCH_LIST = "SEARCH_LIST";
	public static final String BREADCRUMB_SEARCH = ">> My Account >> Search";
	public static final String BREADCRUMB_ORDER_HISTORY = ">> My Orders >> View Order History";
	public static final String BREADCRUMB_ALERTMSG = ">> My Account >> Creating Manual Alert";
	
	//For GBS	
	public static final String EACH_BREEZE_CARD_VALUE = "2";
	public static final String SHIPMENT_METHODS_LIST = "SHIPMENT_METHODS_LIST";
	public static final String ADMIN_SEARCH_LIST = "ADMIN_SEARCH_LIST";
	public static final String ORDER_SEARCH_LIST = "ORDER_SEARCH_LIST";
	public static final String TRACK_LIST = "TRACK_LIST";
	public static final String BREADCRUMB_TRACK_SHIPMENT = ">> My Orders >> Track Shipments";
	
	public static final String ORDERED_ASSOCIATED_EVENT_LIST = "ORDERED_ASSOCIATED_EVENT_LIST";
	public static final String EVENT_ASSOCIATED_ORDER_LIST = "EVENT_ASSOCIATED_ORDER_LIST";
	public static final String ZERO = "0";
	public static final String ONE="1";
	public static final String DEFAULT_DOUBLE_VALUE = "0.00";
	
	public static final String ORDER_TYPE_GBS = "GBS";
	public static final String ORDER_TYPE_IS = "IS";
	public static final String ORDER_TYPE_PS = "PS";
	
	//public static final String NOT_SHIPPED = "Track URL from UPS";
	
	public static final String EMAIL_ID = "EMAIL_ID"; 
	public static final String DOUBLE_DOLLAR = "##";
	public static final String ROLE_PERMISSION = "ROLE_PERMISSION";
	
	public static final int PHONE_NUMBER_LENGTH = 10;
	public static final int PHONE_NUMBER_START_INDEX = 0;
	public static final int FIRST_PHONE_NUMBER_END_INDEX = 3;
	public static final int SECOND_PHONE_NUMBER_START_INDEX = 3;
	public static final int SECOND_PHONE_NUMBER_END_INDEX = 6;
	public static final int THIRD_PHONE_NUMBER_START_INDEX = 6;
	
	//for cancelled order message
	public static final String ORDER_CANCELLED_SUCESS_MESAAGE = "ORDER_CANCELLED_SUCESS_MESAAGE";
	public static final String ORDER_CANCELLED_FAILURE_MESAAGE = "ORDER_CANCELLED_FAILURE_MESAAGE";
	
	public static final String DOUBLE_TILLED = "~~";
	public static final int EXPECTED_DELIVERY_DAYS = 5; 
	
	public static final int GBS_SUPER_ADMIN_TYPE = 21;
	public static final int GBS_ADMIN_TYPE = 22;
	public static final int GBS_READ_ONLY_TYPE = 23;
	
	public static final String SHIPPING_CHARGE_FREE = "free";
	
	public static final String VIEWORDERHISTORY = "VIEWORDERHISTORY";
	//public static final String ORDER_NUMBER = "breezecard.ordernumber";
	//public static final String  EVENT_NAME = "breezecard.eventname";
	//public static final String  ORDER_DATE= "breezecard.orderdate";
	//public static final String VIEW_ORDER_STATUS = "breezecard.orderstatus";
	//public static final String ORDER_AMOUNT = "breezecard.orderamount";
	
	public static final String BC_SERIAL_NUMBER = "breezecard.bcserialnumber";
	
	public static final String SHIPPING_AND_HANDLING_LIST = "SHIPPING_AND_HANDLING_LIST";
	public static final String GBS_DISCOUNT_LIST = "GBS_DISCOUNT_LIST";
	public static final String GBS_DISCOUNT_PRODUCTID_LIST = "GBS_DISCOUNT_PRODUCTID_LIST";
	
	public static final String CARD_RELEASED = "Released";
	public static final String CARD_NOT_RELEASED = "Not Released";
	public static final String BC_RELEASE_STATUS = "breezecard.bcreleasestatus";
	
	public static final String SEARCH_LIST_ADMIN = "SEARCH_LIST_ADMIN";
	
	public static final String LOG_LIST = "LOG_LIST";
	public static final String LOCKED_USER_DETAILS = "LOCKED_USER_DETAILS";
	public static final String  IS_CREATE_SHOW = "IS_CREATE_SHOW";
	public static final String VIEW_USER_DETAILS="VIEW_USER_DETAILS";
	
	public static final String ADMIN_USER_LIST = "ADMIN_USER_LIST";
	
	public static final String NEW_USER_MAIL_CONTENT = "NEW_USER_MAIL_CONTENT";
	public static final String NEW_ADMIN_MAIL_SUBJECT = "NEW_ADMIN_MAIL_SUBJECT";
	
	public static final String UNLOCK_MAIL_CONTENT = "UNLOCK_MAIL_CONTENT";
	
	public static final String PARTNER_SEARCH_LIST = "PARTNER_SEARCH_LIST";
	
	public static final String OTHERS = "OTHERS";
	public static final String PARTNER = "PARTNER";
	public static final String GBS_PATRON_SEARCH_LIST = "GBS_PATRON_SEARCH_LIST";
	public static final String BREEZE_CARD_SEARCH_LIST = "BREEZE_CARD_SEARCH_LIST";
	public static final String ACCOUNT_ADMIN_SEARCH_LIST = "ACCOUNT_ADMIN_SEARCH_LIST";
	public static final String PARTNER_MEMBER_SEARCH_LIST = "PARTNER_MEMBER_SEARCH_LIST";
	public static final String USAGE_REPORT = "USAGE_REPORT";
	public static final String ACTIVE_BENIFIT_REPORT = "ACTIVE_BENIFIT_REPORT";
	public static final String ORDER_REPORT = "ORDER_REPORT";
	public static final String HOTLIST_REPORT = "HOTLIST_REPORT";
	public static final String NEWCARD_REPORT = "NEWCARD_REPORT";
	public static final String CARD_ORDERED_STATUS = "CARD_ORDERED_STATUS";
	public static final String CARD_ORDERED_STATUS_FOR_GBS = "CARD_ORDERED_STATUS_FOR_GBS";
	public static final String CARD_ORDERED_STATUS_FOR_IS = "CARD_ORDERED_STATUS_FOR_IS";
	public static final String CARD_ORDERED_STATUS_FOR_PS = "CARD_ORDERED_STATUS_FOR_PS";
	
	public static final String NEGATIVE_LIST_CARDS = "NEGATIVE_LIST_CARDS";
	// for add new product
	
	public static final String ADD_NEW_PRODUCT_STATUS = "ADD_NEW_PRODUCT_STATUS";
	
	//for application settings parameter
	public static final String APPLICATION_SETTINGS_LIST = "APPLICATION_SETTINGS_LIST";
	public static final String APPLICATION_SETTINGS_SUCCESS = "APPLICATION_SETTINGS_SUCCESS"; 
	public static final String APPLICATION_SETTINGS_FAILURE = "APPLICATION_SETTINGS_FAILURE";
	public static final String RESET_PARAM_VALUE_SUCCESS = "RESET_PARAM_VALUE_SUCCESS";
	public static final String RESET_PARAM_VALUE_FAILURE = "RESET_PARAM_VALUE_FAILURE";
	public static final String SHOW_SECURITY_KEY_DIV = "SHOW_SECURITY_KEY_DIV";
	public static final String SHOW_OTHERS_DIV = "SHOW_OTHERS_DIV";
	public static final String SHOW_EDIT_DIV = "SHOW_EDIT_DIV";
	
	
	//for alert List
	public static final String ALERT_MESSAGE_LIST = "ALERT_MESSAGE_LIST";
	public static final String ALERT_MESSAGE_ADDED_SUCESS = "ALERT_MESSAGE_ADDED_SUCESS";
	public static final String ALERT_MESSAGE_ADDED_FAILURE = "ALERT_MESSAGE_ADDED_FAILURE";
	public static final String ALERT_MESSAGE_UPDATE_SUCESS = "ALERT_MESSAGE_UPDATE_SUCESS";
	public static final String ALERT_MESSAGE_UPDATE_FAILURE = "ALERT_MESSAGE_UPDATE_FAILURE";
	public static final String ALERT_MESSAGE_REMOVED_SUCESS = "ALERT_MESSAGE_REMOVED_SUCESS";
	public static final String ALERT_MESSAGE_REMOVED_FAILURE = "ALERT_MESSAGE_REMOVED_FAILURE";
	
	public static final String ALERT_MESSAGE_LIST_WELCOMEPAGE = "ALERT_MESSAGE_LIST_WELCOMEPAGE";
	public static final String RECENT_LOGCALL_LIST_WELCOMEPAGE = "RECENT_LOGCALL_LIST_WELCOMEPAGE";
	public static final String LOCKED_LIST_WELCOMEPAGE = "LOCKED_LIST_WELCOMEPAGE";
	public static final String UNLOCKED_LIST_WELCOMEPAGE = "UNLOCKED_LIST_WELCOMEPAGE";
	public static final String RECENT_HOTLIST_CARDLIST_WELCOMEPAGE = "RECENT_HOTLIST_CARDLIST_WELCOMEPAGE";
	
	public static final String VIEW_PARTNER_USER_DETAILS = "VIEW_PARTNER_USER_DETAILS";
	public static final String VIEW_SHOW_USER_DETAILS = "VIEW_SHOW_USER_DETAILS";
	
	public static final String ORDERS_COUNT_LIST = "ORDERS_COUNT_LIST";
	
	public static final int SUPREME_ADMIN = 50;
	public static final int IT_ADMIN = 54;
	
	//for company status starts here
	public static final String SUBMITTED_STATUS_ID = "80";
	public static final String PENDING_STATUS_ID = "81";
	public static final String APPROVED_STATUS_ID = "82";
	public static final String REJECTED_STATUS_ID = "83";
	
	public static final String COMPANY_STATUS_LIST = "COMPANY_STATUS_LIST";
	public static final String PARTNER_COMPANY_INFO_RECORD_ADDED = "PARTNER_COMPANY_INFO_RECORD_ADDED";
	public static final String PARTNER_COMPANY_INFO_RECORD_ADD_FAILED = "PARTNER_COMPANY_INFO_RECORD_ADD_FAILED";
	public static final String PARTNER_ADMIN_DETAIL_AND_INFO_RECORD_ADDED = "PARTNER_ADMIN_DETAIL_AND_INFO_RECORD_ADDED";
	public static final String PARTNER_ADMIN_DETAIL_AND_INFO_RECORD_ADD_FAILED = "PARTNER_ADMIN_DETAIL_AND_INFO_RECORD_ADD_FAILED";
	public static final String NEW_COMPANY_STATUS_MAIL_CONTENT ="NEW_COMPANY_STATUS_MAIL_CONTENT";
	public static final String NEW_COMPANY_STATUS_MAIL_SUBJECT = "NEW_COMPANY_STATUS_MAIL_SUBJECT";
	
	//	for company status ends here
    //	for  add  company to tma starts here
	public static final String MANAGE_ADD_COMPANY_TO_TMA_LIST = "MANAGE_ADD_COMPANY_TO_TMA_LIST";
    //	for  add  company to tma ends here
	
	
	//for manage partner registration request starts here
	
	public static final String MANAGE_PARTNER_REGISTRATION_LIST = "MANAGE_PARTNER_REGISTRATION_LIST";
	public static final String PARTNER_REGISTRATION_RECORD_ADDED = "PARTNER_REGISTRATION_RECORD_ADDED";
	public static final String PARTNER_REGISTRATION_RECORD_ADD_FAILED = "PARTNER_REGISTRATION_RECORD_ADD_FAILED";
	public static final String PARTNER_REGISTRATION_REQUEST_WELCOME ="PARTNER_REGISTRATION_REQUEST_WELCOME";
	public static final String GRID_LIST = "GRID_LIST";
	public static final String PASSWORD_STATUS_TEMP = "Temp";
	public static final String APPROVED ="Approved";	
	public static final String REJECTED ="Rejected";
	public static final String COMPANY_REGISTRATION_RECORD_ADDED = "COMPANY_REGISTRATION_RECORD_ADDED";
	public static final String COMPANY_REGISTRATION_RECORD_ADD_FAILED = "COMPANY_REGISTRATION_RECORD_ADD_FAILED";
	
	//	for manage partner registration request ends here
	//for view reports
	public static final String EXCEL_TYPE = "excel";
	
	public static final String NEW_CARD_REPORT = "NEW_CARD_REPORT";
	public static final String UPASS_MONTHLY_USAGE_SUMMARY_REPORT = "MONTHLY USAGE REPORT";
	
	//for detailed order report
	public static final String DETAILED_ORDER_REPORT = "DETAILED_ORDER_REPORT";
	public static final String HOT_LIST_REPORT = "HOT_LIST_REPORT";
	public static final String PARTNER_MONTHLY_USAGE_SUMMARY_REPORT = "PARTNER_MONTHLY_USAGE_SUMMARY_REPORT";
	public static final String PARTNER_NEW_CARD_REPORT = "PARTNER_NEW_CARD_REPORT";
	
	public static final String CARD_HOLDER = "breezecard.cardholder";
	public static final String CUSTOMER_MEMBER_ID = "breezecard.customermemberid";
	public static final String TRANSIT_CARD = "breezecard.transitcard";
	public static final String BENEFIT_NAME = "breezecard.benefitname";
	public static final String BILLING_MONTH = "breezecard.billingmonthandyear";
	public static final String MONTHLY_USAGE = "breezecard.monthlyusage";
	public static final String EFFECTIVE_DATE = "breezecard.effectivedate";
	public static final String PARTNER_ADMIN_NAME = "breezecard.partneradminname";
	public static final String EMPLOYEE_NAME = "breezecard.employeename";
	public static final String COMPANY_NAME = "breezecard.admin.companyname";
	public static final String BILLING_MONTH_YEAR = "breezecard.billingmonthandyear";
	public static final String BREEZECARD_SERIAL_NUMBER = "breezecard.upcomingbenefitreport.breezecardnumber";
	public static final String EMPLOYEE_ID = "breezecard.employeeid";
	public static final String HOTLIST_ACTION_TYPE="breezecard.hotlistactiontype";
	public static final String HOTLIST_DATE="breezecard.hotlistdate";
	public static final String FIRST_NAME="breezecard.firstname";
	public static final String LAST_NAME="breezecard.lastname";
	public static final String POINT_OF_SALE="breezecard.pointofsale";
	public static final String ADMIN_NAME="breezecard.adminname";
	public static final String HOT_LIST_DATE="breezecard.hotlisteddate";
	public static final String TMA_NAME = "breezecard.tmaname";
	public static final String PARTNER_NAME = "breezecard.partnername";
	public static final String COMPANY_NAME_REPORT = "COMPANY_NAME_REPORT";
	
	
	//for hotlist card details list
	public static final String HOTLIST_CARD_DETAILS_LIST = "HOTLIST_CARD_DETAILS_LIST";
	public static final String PARTNER_SEARCH_LIST_WELCOMEPAGE = "PARTNER_SEARCH_LIST_WELCOMEPAGE";
	
	public static final String PARTNER_REGISTRATION_REQUEST_LIST = "PARTNER_REGISTRATION_REQUEST_LIST";
	public static final String ADD_COMPANY_REGISTRATION_REQUEST_LIST = "ADD_COMPANY_REGISTRATION_REQUEST_LIST";
	
	public static final String PS_HOTLIST = "PS";
	public static final String IS_HOTLIST = "IS";
	public static final String GBS_HOTLIST = "GBS";
	public static final String CARD_SERIAL_NO="CARD_SERIAL_NO";
	
	//for upcomingmonthly report	
	public static final String IS_BENEFIT_ACTIVATED = "breezecard.report.isbenefitactivated";
	public static final String PARTNER_UPCOMMING_MON_BENEFIT_REPORT = "PARTNER_UPCOMMING_MON_BENEFIT_REPORT";
	
	// for general ledger report
	public static final String GENERAL_LEDGER_LIST ="GENERAL_LEDGER_LIST";
	public static final String GENERAL_LEDGER_REPORT_HEADING ="General Ledger Report";
	public static final String GENERAL_LEDGER_REPORT_FILENAME ="GeneralLedgerReport";
	public static final String GLR_ACCOUNT_NO ="breezecard.report.generalledger.accountnumber";
	public static final String GLR_ORDER_NO ="breezecard.report.generalledger.ordernumber";
	public static final String GLR_CUSTOMER_TYPE ="breezecard.report.generalledger.customertype";
	public static final String GLR_CUSTOMER_NAME ="breezecard.report.generalledger.customername";
	public static final String GLR_CREDIT_CARD_AUTHORIZATION_NO ="breezecard.report.generalledger.creditcardauthorizationno";
	public static final String GLR_TRANSACTION_DATE ="breezecard.report.generalledger.transactiondate";
	public static final String GLR_PRODUCT ="breezecard.report.generalledger.product";
	public static final String GLR_PRICE="breezecard.report.generalledger.price";
	public static final String GLR_QUANTITY ="breezecard.report.generalledger.quantity";
	public static final String GLR_AMOUNT ="breezecard.report.generalledger.amount";
	public static final String BCWTS_GENERAL_LEDGER_REPORT="BCWTS_GENERAL_LEDGER_REPORT";
	
	public static final String PRODUCT_NAME_SHIPPING = "Shipping";
	public static final String PRODUCT_NAME_GBS_DISCOUNT = "GBS Discount";
	public static final String PRODUCT_NAME_BREEZECARD = "BreezeCard";
	public static final String PRODUCT_NAME_STORED_VALUE= "STORED VALUE";
		
	//for invoice period 
	public static final String PS_INVOICE_PERIOD = "PS_INVOICE_PERIOD";
	
//	Benefit Id - Annual Pass
	public static final String ANNUAL_PASS_BENEFIT_NAME = "Annual Pass";
	public static final String ANNUAL_PASS_BENEFIT_ID = "30721";
	public static final String BENEFIT_STATUS_ID_ONE = "1";
	
	//for listcards
	public static final String CARD_DETAILS_LIST = "CARD_DETAILS_LIST";
	public static final String BENEFIT_DETAILS_LIST = "BENEFIT_DETAILS_LIST";
	public static final String MEMBER_UPDATE_SUCCESS_MESAAGE = "MEMBER_UPDATE_SUCCESS_MESAAGE";
	public static final String MEMBER_UPDATE_FAILURE_MESAAGE = "MEMBER_UPDATE_FAILURE_MESAAGE";
	public static final String BENEFIT_ACTIVATED_SUCCESS_MESSAGE="BENEFIT_ACTIVATED_SUCCESS_MESSAGE";
	public static final String BENEFIT_DEACTIVATED_SUCCESS_MESSAGE="BENEFIT_DEACTIVATED_SUCCESS_MESSAGE";
	public static final String BENEFIT_ACTIVATED_FAILURE_MESSAGE="BENEFIT_ACTIVATED_FAILURE_MESSAGE";
	public static final String BENEFIT_DEACTIVATED_FAILURE_MESSAGE="BENEFIT_DEACTIVATED_FAILURE_MESSAGE";
	public static final String SERIAL_NO_REPLACED_SUCCESS_MESSAGE="SERIAL_NO_REPLACED_SUCCESS_MESSAGE";
	public static final String SERIAL_NO_REPLACED_FAILURE_MESSAGE="SERIAL_NO_REPLACED_FAILURE_MESSAGE"; 
	public static final String HOTLIST_CARDS_SUCCESS_MESSAGE="HOTLIST_CARDS_SUCCESS_MESSAGE";
	public static final String HOTLIST_CARDS_FAILURE_MESSAGE="HOTLIST_CARDS_FAILURE_MESSAGE";
	public static final String HOTLIST_CARDS_SAVE_FAILURE_MESSAGE="HOTLIST_CARDS_SAVE_FAILURE_MESSAGE";
	public static final String ADD_THRESHOLD_SUCCESS_MESSAGE="ADD_THRESHOLD_SUCCESS_MESSAGE";
	public static final String ADD_THRESHOLD_FAILURE_MESSAGE="ADD_THRESHOLD_FAILURE_MESSAGE";
	public static final String ADD_RECURRING_SUCCESS_MESSAGE="ADD_RECURRING_SUCCESS_MESSAGE";
	public static final String ADD_RECURRING_FAILURE_MESSAGE="ADD_RECURRING_FAILURE_MESSAGE";
	public static final String ADD_NEW_CARD_SUCCESS_MESSAGE="ADD_NEW_CARD_SUCCESS_MESSAGE";
	public static final String ADD_NEW_CARD_FAILURE_MESSAGE="ADD_NEW_CARD_FAILURE_MESSAGE";
	public static final String CARD_ALREADY_ADDED_UNDER_PROCESS="CARD_ALREADY_ADDED_UNDER_PROCESS";
	public static final String MEMBER_ID_EXIST="MEMBER_ID_EXIST";
	public static final String WRONG_RIDER_CLASS="WRONG_RIDER_CLASS";
	public static final String SERIAL_NO_NOT_EXIST="SERIAL_NO_NOT_EXIST";
	public static final String MEMBER_ID_SERIAL_NO_EXIST="MEMBER_ID_SERIAL_NO_EXIST";
	public static final String CARD_HOTLISTED_MESSAGE="CARD_HOTLISTED_MESSAGE";
	public static final String MEMBER_CARD_ASSOCIATED_MESSAGE="MEMBER_CARD_ASSOCIATED_MESSAGE";
	public static final String CUSTOMER_CARD_ASSOCIATED_MESSAGE="CUSTOMER_CARD_ASSOCIATED_MESSAGE";
	public static final String CARD_ASSOCIATED_MESSAGE="CARD_ASSOCIATED_MESSAGE";
	public static final String NEW_CARD_QUEUE_LIST = "NEW_CARD_QUEUE_LIST";
	
	
	
	
	
//	List Cards
	public static final String BREADCRUMB_LIST_CARDS = ">> My Cards >> List Cards";
	public static final String BREADCRUMB_EDIT_CARDS = ">> My Cards >> List Cards >> Edit Member Information";
	public static final String BREADCRUMB_ISSUE_LIST = ">> My Cards >> List Cards >> TMA Issue List";
	public static final String BREADCRUMB_REPLACE_CARDS = ">> My Cards >> List Cards >> Replace Cards";
	public static final String BREADCRUMB_HOTLIST_CARDS = ">> My Cards >> List Cards >> Hotlist Cards";
	public static final String BREADCRUMB_THRESHOLD = ">> My Cards >> List Cards >> Threshold";
	public static final String BREADCRUMB_RECURRING = ">> My Cards >> List Cards >> Recurring";
	public static final String BREADCRUMB_NEW_CARD = ">> My Cards >> List Cards >> New Card";
	public static final String BREADCRUMB_NEW_CARD_QUEUE_LIST = ">> My Cards >> Edit Queue";
	public static final String BREADCRUMB_SCHOOLLIST_CARDS = ">> My Cards >> UPASS List Cards";
	public static final String BREADCRUMB_SCHOOLLIST_REPORTS = ">> My Cards >> UPASS Reports";
	
	
	public static final String BREADCRUMB_MANAGE_MARTA_ADMIN = ">> My Account >> Manage MARTA Administrator";
	
	public static final String BREADCRUMB_UNLOCK_ACCOUNT = ">> My Account >> Unlocking Account";
	
	public static final String UNLOCK_SUCESS_MESSAGE = "UNLOCK_SUCESS_MESSAGE";		
	public static final String UNLOCK_FAILURE_MESSAGE = "UNLOCK_FAILURE_MESSAGE";	
	
	public static final String BREEZE_CARD_DOES_NOT_EXIST = "BREEZE_CARD_DOES_NOT_EXIST";
	public static final String BREEZE_CARD_HOTLISTED_SUCCESS = "BREEZE_CARD_HOTLISTED_SUCCESS";
	public static final String BREEZE_CARD_HOTLISTED_FAILURE = "BREEZE_CARD_HOTLISTED_FAILURE";
	public static final String INVALID_POINT_OF_SALE = "INVALID_POINT_OF_SALE";
	public static final String HOT_LIST_DETAILS_FOR_PS = "HOT_LIST_DETAILS_FOR_PS";
	public static final String HOT_LIST_DETAILS_FOR_IS = "HOT_LIST_DETAILS_FOR_IS";
	public static final String HOT_LIST_DETAILS_FOR_GBS = "HOT_LIST_DETAILS_FOR_GBS";
	public static final String UPDATE_HOT_LIST_CARD_DETAILS = "UPDATE_HOT_LIST_CARD_DETAILS";
	
// for Card Order Status
	public static final String UPDATE_IS_ORDER_DETAILS_SUCCESS = "UPDATE_IS_ORDER_DETAILS_SUCCESS";
	public static final String UPDATE_IS_ORDER_DETAILS_FAILURE = "UPDATE_IS_ORDER_DETAILS_FAILURE";
	
	public static final String UPDATE_GBS_ORDER_DETAILS_SUCCESS = "UPDATE_GBS_ORDER_DETAILS_SUCCESS";
	public static final String UPDATE_GBS_ORDER_DETAILS_FAILURE = "UPDATE_GBS_ORDER_DETAILS_FAILURE";
	
	public static final String UPDATE_PS_ORDER_DETAILS_SUCCESS = "UPDATE_PS_ORDER_DETAILS_SUCCESS";
	public static final String UPDATE_PS_ORDER_DETAILS_FAILURE = "UPDATE_PS_ORDER_DETAILS_FAILURE";
	
	
	// for Negative List Payment Card
	
	public static final String UPDATE_NEGATIVE_LIST_DETAILS_SUCCESS = "UPDATE_NEGATIVE_LIST_DETAILS_SUCCESS";
	public static final String UPDATE_NEGATIVE_LIST_DETAILS_FAILURE = "UPDATE_NEGATIVE_LIST_DETAILS_FAILURE";
	
	//for alert message  to be in green
	public static final String ALERT_STATUS ="ALERT_STATUS";
	
	//for AdminBatchProcess
	public static final String BATCH_PROCESS_ACTION_VALUE_HOTLIST = "HOTLIST"; 
	public static final String BATCH_PROCESS_ACTION_VALUE_NEW = "NEW";
	public static final String BATCH_PROCESS_ACTION_VALUE_UPDATE = "UPDATE";
	public static final String BATCH_PROCESS_ACTION_VALUE_DELETEMEMBER = "DELETEMEMBER";
	public static final String BATCH_PROCESS_ACTION_VALUE_REPLACE = "REPLACE";
	public static final String BATCH_PROCESS_ACTION_VALUE_NOCHANGE = "NOCHANGE";
	public static final String BATCH_PROCESS_BENEFIT_ACTION_ACTIVATE = "Activate";
	
	public static final String BATCH_PROCESS_ARCHIEVE_DIR = "BATCH_PROCESS_ARCHIEVE_DIR";
	public static final String LOG_CALLS_ARCHIVE_DIR = "LOG_CALLS_ARCHIVE_DIR";
	public static final String BATCH_PROCESS_ID_VALUE = "1";
	public static final String BATCH_PROCESS_MANF_NUMBER = "160";
	
	public static final Long BATCH_PROCESS_INITIAL_STATUS_ID = new Long(322);
	public static final String BATCH_PROCESS_INITIAL_STATUS_DESC = "PROCESSING";

	public static final Long BATCH_PROCESS_SUCCESS_STATUS_ID = new Long(323);
	public static final String BATCH_PROCESS_SUCCESS_STATUS_DESC = "PASS";

	public static final Long BATCH_PROCESS_FAILURE_STATUS_ID = new Long(325);
	public static final String BATCH_PROCESS_FAILURE_STATUS_DESC = "FAILURE";

	public static final Long BATCH_PROCESS_PARTIAL_STATUS_ID = new Long(324);
	public static final String BATCH_PROCESS_PARTIAL_STATUS_DESC = "PARTIAL";
	
	public static final String SEARCH_BATCH_HISTORY = "SEARCH_BATCH_HISTORY";
	public static final String VIEW_BATCH_HISTORY = "VIEW_BATCH_HISTORY";
	
	//for Batch Process	
	public static final String FILE_NOT_FOUND_MESAAGE = "FILE_NOT_FOUND_MESSAGE";
	public static final String FILE_DELETED_SUCCESS_MESAAGE = "FILE_DELETED_SUCCESS_MESSAGE";
	public static final String FILE_DELETED_FAILURE_MESAAGE = "FILE_DELETED_FAILURE_MESSAGE";
	
	public static final String FILE_DOWNLOAD_FAILURE_MESAAGE = "FILE_DOWNLOAD_FAILURE_MESAAGE";
	
	public static final String PARTNER_SIGNUP_SUCCESS_MESAAGE = "PARTNER_SIGNUP_SUCCESS_MESAAGE";
	public static final String BREADCRUMB_SUPPORT = ">>  Support >> Support";
	public static final int TMA_SUPER_ADMIN_TYPE = 14;
	

	//added for changes in batchjob
	public static final Long BATCH_PROCESS_PROCESSSTARTED_STATUS_ID = new Long(327);
	public static final String BATCH_PROCESS_PROCESSSTARTED_STATUS_DESC = "PROCESSSTARTED";	
	public static final String BATCH_PROCESS_ARCHIVAL_FILE_DATE_FORMAT = "MM-dd-yyyy_hh_mm_ss";
	//	to print error msg for batch process
	public static final String BP_FILE_UPLOAD_ERRORMSG = "BP_FILE_UPLOAD_ERRORMSG";
	
	
	//Deactivate Company/TMA account
	public static final String COMPANY_DEACTIVATE_SUCCESS_MESSAGE = "COMPANY_DEACTIVATE_SUCCESS_MESSAGE";
	public static final String COMPANY_DEACTIVATE_FAILURE_MESSAGE = "COMPANY_DEACTIVATE_FAILURE_MESSAGE";	
	public static final String TMA_DEACTIVATE_SUCCESS_MESSAGE = "TMA_DEACTIVATE_SUCCESS_MESSAGE";
	public static final String TMA_DEACTIVATE_FAILURE_MESSAGE = "TMA_DEACTIVATE_FAILURE_MESSAGE";

	//For Reports
	public static final String DETAILED_HTML_REPORT = "breezecard.htmlreport";
	
	
	/*************** OF Reports - Starts here **************/
	
	public static final String OUTSTANDING_ORDERS = "Outstanding Orders"; 
	public static final String FULLFILED_ORDERS = "Orders Fullfiled";
	public static final String CANCELLED_ORDERS = "Orders Cancelled";
	public static final String RETURNED_ORDERS = "Orders Returned";

	public static final String VISA = "Visa"; 
	public static final String MASTERCARD = "Master Card";	
	public static final String DISCOVER = "Discover";
	public static final String AMEX = "Amex"; //American Express
		
	//Credit card type - Payment Mode = 1
	public static final String CREDITCARD_TYPE_VISA = "1"; 
	public static final String CREDITCARD_TYPE_MASTERCARD = "2";	
	public static final String CREDITCARD_TYPE_DISCOVER = "3";
	public static final String CREDITCARD_TYPE_AMEX = "4"; //American Express
	
	//Credit card type - Payment Mode = 2
	public static final String CREDITCARD_TYPE_VISA_2 = "5"; 
	public static final String CREDITCARD_TYPE_MASTERCARD_2 = "6";	
	
	//Reports	
	public static final String REPORT_LIST = "reportlist";
	public static final String SHIPPED_STATUS = "2";
	public static final String CANCELLED_STATUS = "6";
	public static final String RETURNED_STATUS = "7";
	
	public static final String ORDERSUMMARY_REPORT = "ORDERSUMMARY_REPORT";
	public static final String ORDERCANCELLED_REPORT = "ORDERCANCELLED_REPORT";
	public static final String QUALITYDETAILED_REPORT = "QUALITYDETAILED_REPORT";
	public static final String OUTSTANDING_ORDERS_REPORT = "OUTSTANDING_ORDERS_REPORT";
	public static final String QUALITY_ASSURANCE_SUMMARY_REPORT = "QUALITY_ASSURANCE_SUMMARY";
	public static final String RETURNED_ORDERS_REPORT = "RETURNED_ORDERS_REPORT";
	public static final String CANCELLED_ORDERS_REPORT = "CANCELLED_ORDERS_REPORT";
	
	public static final String PDF_TYPE = "pdf";
	public static final String Excel_TYPE = "excel";
	public static final String HTML_TYPE = "html";
	
	//	OF - order summary Report
	public static final String ORDERSUMMARYREPORT_HEADING = "Order Summary Report";	
	public static final String noOfOrders = "No of Orders";
	public static final String orderQuantity = "Order Quantity";
	public static final String revenue = "Revenue";
	public static final String shipping = "Shipping";
	public static final String total = "Total";
	public static final String ORDERSUMMARY_REPORTFILENAME="ordersummary";
	
	//	OF - Sales Metrics Report
	public static final String SALES_METRICS_REPORT = "SALES_METRICS_REPORT";
	public static final String SALES_METRICS_REPORT_HEADING = "Sales Metrics Report";
	public static final String SALES_METRICS_REPORT_FILENAME = "salesmetricsreport";
	public static final String numberOfCardsSold = "breezecard.report.salesmetricsreport.numberofcardssold";
	

	// OF - Outstanding Orders Report
	public static final String OUTSTANDING_ORDERS_REPORT_HEADING = "Outstanding Orders Report";
	public static final String OUTSTANDING_ORDERS_REPORT_FILENAME="outstandingorders";
	public static final String orderNumber = "breezecard.report.outstandingorders.ordernumber";
	public static final String breezeCardNumber = "breezecard.report.outstandingorders.breezecardnumber";
	public static final String firstName = "breezecard.report.outstandingorders.firstname";
	public static final String lastName = "breezecard.report.outstandingorders.lastname";
	public static final String email = "breezecard.report.outstandingorders.email";
	public static final String phone = "breezecard.report.outstandingorders.phone";
	public static final String zipCode = "breezecard.report.outstandingorders.zipcode";
	public static final String dateReceived = "breezecard.report.outstandingorders.datereceived";
	public static final String orderStatus = "breezecard.report.outstandingorders.orderstatus";
	public static final String orderType = "breezecard.report.outstandingorders.ordertype";
	
	//OF - Quality Assurance Summary Report
	public static final String QUALITY_ASSURANCE_SUMMARY_REPORT_HEADING = "Quality Assurance Summary Report";
	public static final String QUALITY_ASSURANCE_SUMMARY_REPORT_FILENAME = "QualityAssuranceSummary";
	public static final String passed = "breezecard.report.qualityassurancesummary.passed";
	public static final String failed = "breezecard.report.qualityassurancesummary.failed";
	public static final String Pass = "Pass";
	public static final String Fail = "Fail";
	
	//OF - Returned Orders Report	
	public static final String RETURNED_ORDERS_REPORT_HEADING = "Returned Orders Report";
	public static final String RETURNED_ORDERS_REPORT_FILENAME = "ReturnedOrdersReport";
	public static final String orderDate = "breezecard.report.returnedorders.orderdate";
	public static final String returnedDate = "breezecard.report.returnedorders.returneddate";
	public static final String deliveryAttempts = "breezecard.report.returnedorders.deliveryattempts";

	public static final String CANCELORDERREPORT_HEADING = "Cancelled Order Report";
	public static final String ORDERID = "Order Request ID";
	public static final String CARDSERIALNUMBER = "Card Serial Number";
	public static final String FIRSTNAME = "Patron First Name";
	public static final String LASTNAME = "Patron Last Name ";
	public static final String DATECANCELLED = "Date Cancelled";
	public static final String MEDIASALE = "Admin For Media Sale ";
	public static final String REASONTYPE = "Reason Type";
	public static final String ORDERTYPE = "Order Type";
	public static final String CANCELLEDORDER_REPORTFILENAME="cancelledorder";
	
	public static final String QUALITYDETAILEDREPORT_HEADING = "Quality Detailed Report";
	public static final String adminmediafirstname = "breezecard.martaadminmediafname";
	public static final String adminmedialastname = "breezecard.martaadminmedialname";
	public static final String dateoffailed ="breezecard.dateoffailed";
	public static final String ordernumber = "breezecard.orderrequestedid";
	public static final String qafailedtype = "breezecard.qafailedtype";
	public static final String QUALITYDETAILED_REPORTFILENAME="qualitydetailed";

	public static final String Quality_Result = "Fail";
	
	public static final String IS_ORDERTYPE = "Individual";
	//OF Media Sales Performance Metrics Report
	public static final String ORDERMEDIASALESMETRICS_REPORT="MEDIA_SALES_PERFORMANCE_METRICS_REPORT";
	public static final String MEDIASALESFIRSTNAME="breezecard.report.mediasalesperformancemetrics.mediasalesfirstname";
	public static final String MEDIASALESLASTNAME="breezecard.report.mediasalesperformancemetrics.mediasalesLastname";
	public static final String PROCESSEDDATE="Processed Date";
	public static final String ORDERMEDIASALESMETRICS_REPORT_HEADING = "Media Sales Performance Metrics Report";
	public static final String ORDERMEDIASALESMETRICS_REPORT_FILENAME="mediasalesperformancemetrics";
	public static final String ORDER_ID = "Order Request ID";
	public static final String ORDER_DATE = "Order Date";
	public static final String ORDER_TYPE = "Order Type";
	public static final String ORDER_FILLEDBY_FIRSTNAME = "Filled By First Name";
	public static final String ORDER_FILLEDBY_LASTNAME = "Filled By Last Name";
	public static final String ORDER_PROCESSEDBY_FIRSTNAME = "Processed By First Name";
	public static final String ORDER_PROCESSEDBY_LASTNAME = "Processed By Last Name";

	//Revenue Report
	public static final String REVENUE_REPORT = "REVENUE_REPORT";
	public static final String REVENUE_REPORT_HEADING = "Revenue Report";
	public static final String REVENUE_REPORT_FILENAME = "RevenueReport";
	public static final String REVENUE_SUMMARY_REPORT = "REVENUE_SUMMARY_REPORT";
	
	/*************** OF Reports - Ends here **************/
	
	
	//breadcrumb for reports
	public static final String BREADCRUMB_PARTNER_REPORT =">> Reports >> Partner Report ";
	public static final String BREADCRUMB_ADMIN_USAGE_REPORT =">> Reports >> Partner Report >> Usage Report ";
	public static final String BREADCRUMB_ADMIN_HOTLIST_REPORT =">> Reports >> Partner Report >> Hotlist Report ";
	public static final String BREADCRUMB_PRODUCT_SUMMARY_REPORT =">> Reports >> Partner Report >> Partner Product Summary Report ";
	public static final String BREADCRUMB_PRODUCT_DETAIL_REPORT =">> Reports >> Partner Report >> Partner Product Detail Report ";
	public static final String BREADCRUMB_ADMIN_ACTIVE_BENEFIT_REPORT =">> Reports >> Partner Report >> Active Benefit Report ";
	public static final String BREADCRUMB_DETAILED_ORDER_REPORT =">> Reports >> Partner Report >> Detailed Order Report";
	public static final String BREADCRUMB_DETAILED_TMA_HOTLIST_REPORT =">> Reports >> Partner Report >> TMA Hotlist Report";
	public static final String BREADCRUMB_DETAILED_TMA_ISSUE_REPORT =">> Reports >> Partner Report >> TMA Issue Report";
	public static final String BREADCRUMB_UPCOMING_MONTHLY_BENEFIT_REPORT =">> Reports >> Partner Report >> Partner Upcoming Monthly Benefit Report";
	
	//breadcrumb for card order status
	public static final String BREADCRUMB_CARD_ORDER_STATUS = ">> My Cards >> Card Ordered Status";
	public static final String BREADCRUMB_CARD_ORDER_STATUS_IS = ">> My Cards >> Card Ordered Status >> IS";
	public static final String BREADCRUMB_CARD_ORDER_STATUS_GBS = ">> My Cards >> Card Ordered Status >> GBS";
	public static final String BREADCRUMB_CARD_ORDER_STATUS_PS = ">> My Cards >> Card Ordered Status >> PS";
	
    //breadcrumb for Negative List
	
	public static final String BREADCRUMB_NEGATIVE_LIST_STATUS = ">>My Account >> Negative List Payment Cards";
	//breadcrumb for application settings
	public static final String BREADCRUMB_APPLICATION_SETTINGS = ">>My Account >>Application Settings";
	
	//breadcrumb for request to hotlist a card
	public static final String BREADCRUMB_REQUEST_HOTLIST_CARD = ">>My Account >>Request to Hotlist Card";
	
	//breadcrumb for partner monthly usage summary report
	public static final String BREADCRUMB_REPORTS_PARTNER_MONTHLY_SUMMARY_REPORT = ">>My Account >>Reports >>Partner Monthly Usage Summary Report";
	public static final String BREADCRUMB_REPORTS = ">>My Account >>Reports";
	
	//View/Edit Products
	public static final String VIEW_EDIT_PRODUCTS_LIST = "VIEW_EDIT_PRODUCTS_LIST";
	public static final String SHOW_VIEW_EDIT_PRODUCTS_DIV = "SHOW_VIEW_EDIT_PRODUCTS_DIV";
	public static final String REGION_NAME_INFO_MAP = "REGION_NAME_INFO_MAP";
	public static final String REGION_NAME_LIST = "REGION_NAME_LIST";
	public static final String SHOW_VIEW_EDIT_PRODUCT_LIST = "SHOW_VIEW_EDIT_PRODUCT_LIST";
	public static final String FARE_INSTRUMENT_ID_LIST = "FARE_INSTRUMENT_ID_LIST";
	public static final String FARE_INSTRUMENT_ID_INFO_MAP = "FARE_INSTRUMENT_ID_INFO_MAP";
	public static final String RIDER_CLASS_INFO_MAP = "RIDER_CLASS_INFO_MAP";
	public static final String RIDER_CLASS_LIST = "RIDER_CLASS_LIST";
	public static final String VIEW_EDIT_PRODUCTS_SUCCESS = "VIEW_EDIT_PRODUCTS_SUCCESS";
	public static final String VIEW_EDIT_PRODUCTS_FAILURE = "VIEW_EDIT_PRODUCTS_FAILURE";
	public static final String FARE_INSTRUMENT_ID_EXISTS_STATUS = "FARE_INSTRUMENT_ID_EXISTS_STATUS";
	
	//Logging calls Raj
	public static final String ADD_NEW_LOG_ERROR_MESSAGE = "ADD_NEW_LOG_ERROR_MESSAGE";
	public static final String INVALID_USER_TYPE = "INVALID_USER_TYPE";
	public static final String NEW_LOG_CALL_ADDED = "NEW_LOG_CALL_ADDED";
	public static final String INVALID_USER_NAME = "INVALID_USER_NAME";
	public static final String UNABLE_TO_LOG_NEW_LOG_CALL = "UNABLE_TO_LOG_NEW_LOG_CALL";
	
	//breadcrumb for view/edit products
	public static final String BREADCRUMB_VIEW_EDIT_PRODUCTS = ">>My Account >>View/Edit Products";
	
	//batch process
	public static final String BATCH_PROCCESS_PARTNER_LIST="BATCH_PROCCESS_PARTNER_LIST";
	
	//Batch Process - downloading csv
	public static final String BATCH_PROCESS_STATUS_CSV_PASS = "PASSED";
	public static final String BATCH_PROCESS_STATUS_CSV_FAIL = "FAILED";
	
	//To Send Mail to Admin
	public static final String BATCHPROCESS_STATUS_MAIL_CONTENT = "BATCH_PROCESS_MAIL_CONTENT";
	public static final String BATCHPROCESS_STATUS_MAIL_SUBJECT = "BATCH_PROCESS_MAIL_SUBJECT";
	
	//Add company to TMA
	public static final String COMPANY_DONT_HAVE_PARTNER = "COMPANY_DONT_HAVE_PARTNER";
	public static final String COMPANY_ADDED_TO_TMA = "COMPANY_ADDED_TO_TMA";
	public static final String UNABLE_TO_ADD_COMPANY_TO_TMA = "UNABLE_TO_ADD_COMPANY_TO_TMA";
	
	// Manage marta admin
	public static final String ADMIN_USER_ALREADY_EXISTS = "ADMIN_USER_ALREADY_EXISTS";
	public static final String ADMIN_USER_ADD_SUCCESS_MESSAGE = "ADMIN_USER_ADD_SUCCESS_MESSAGE";	
	public static final String ADMIN_USER_ADD_FAILURE_MESSAGE = "ADMIN_USER_ADD_FAILURE_MESSAGE";
	public static final String ADMIN_USER_UPDATE_SUCCESS_MESSAGE = "ADMIN_USER_UPDATE_SUCCESS_MESSAGE";
	public static final String ADMIN_USER_UPADTE_FAILURE_MESSAGE = "ADMIN_USER_UPADTE_FAILURE_MESSAGE";
	public static final String ADMIN_USER_DELETED_SUCCESS_MESSAGE = "ADMIN_USER_DELETED_SUCCESS_MESSAGE";
	public static final String ADMIN_USER_DELETED_FAILURE_MESSAGE = "ADMIN_USER_DELETED_FAILURE_MESSAGE";
	
	//breadcrumb for Order Search
	public static final String BREADCRUMB_ORDER_SEARCH = ">> My Account >> Search >> Order Search";
	public static final String BREADCRUMB_PARTNER_SEARCH = ">> My Account >> Search >> partner Search";
	public static final String BREADCRUMB_PARTNER_MEMBER_SEARCH = ">> My Account >> Search >> partner Member Search";
	public static final String BREADCRUMB_ACCOUNT_ADMIN_SEARCH = ">> My Account >> Search >> Account Admin Search";
	public static final String BREADCRUMB_BREEZE_CARD_SEARCH = ">> My Account >> Search >> Breeze Card Search";
	public static final String BREADCRUMB_GBS_PATRON_SEARCH = ">> My Account >> Search >> GBS Patron Search";
	
	//breadcrumb for New log call
	public static final String BREADCRUMB_NEW_LOG_CALL = ">> My Account >> Logging Calls >> New Call";
	
	//breadcrumb for Logging call history
	public static final String BREADCRUMB_LOGGING_CALL_HISTORY = ">> My Account >> Logging Calls >> Logging Call History";
	
	public static final String FORGOT_PASSWORD_ROLE_ERROR_MESSAGE = "FORGOT_PASSWORD_ROLE_ERROR_MESSAGE";
	
	//Add/Edit GL Account
	public static final String BREADCRUMB_ADD_EDIT_GL_ACCOUNT = ">>My Account >>Add/Edit GL Account";
	public static final String ADD_EDIT_GL_ACCOUNT_ADD_SUCCESS_MESSAGE = "ADD_EDIT_GL_ACCOUNT_ADD_SUCCESS_MESSAGE";	
	public static final String ADD_EDIT_GL_ACCOUNT_ADD_FAILURE_MESSAGE = "ADD_EDIT_GL_ACCOUNT_ADD_FAILURE_MESSAGE";
	public static final String ADD_EDIT_GL_ACCOUNT_UPDATE_SUCCESS_MESSAGE = "ADD_EDIT_GL_ACCOUNT_UPDATE_SUCCESS_MESSAGE";
	public static final String ADD_EDIT_GL_ACCOUNT_UPDATE_FAILURE_MESSAGE = "ADD_EDIT_GL_ACCOUNT_UPDATE_FAILURE_MESSAGE";
	public static final String UNABLE_TO_GET_GL_ACCOUNT_LIST = "UNABLE_TO_GET_GL_ACCOUNT_LIST";
	
	//GL Batch Process
	public static final String BREADCRUMB_GL_BATCH_PROCESS = ">>My Account >>GL Batch Process";
	
	//CREDIT_CARD_SECURITY_KEY 
	public static final String CREDIT_CARD_SECURITY_KEY  = "CREDIT_CARD_SECURITY_KEY";
	
	//Mail information
	public static final String SMTP_SERVER_PATH = "SMTP_SERVER_PATH";
	public static final String WEBMASTER_MAIL_ID = "WEBMASTER_MAIL_ID";
	public static final String MAIL_PORT = "MAIL_PORT";
	public static final String MAIL_PROTOCOL = "MAIL_PROTOCOL";
	
	//	Print Shipping Label
	public static final String PRINT_SHIPPING_LABEL_LIST ="PRINT_SHIPPING_LABEL_LIST";
	public static final String PRINT_SHIPPING_DETAILS ="PRINT_SHIPPING_DETAILS";
	public static final String BAR_CODE_STR = "BAR_CODE_STR";
	
	// For New Active Benefit Report
	public static final String MAREPORT_COMANY_NAME = "MAREPORT_COMANY_NAME";
	public static final String CARD_COUNT = "ACTIVE_BENEFIT_QUANTITY";
	//Upass Reports
	public static final String BREADCRUMB_ACTIVE_BENEFIT_REPORT =">>Upass Reports >> Active Benefit Report";
	public static final String BREADCRUMB_UPASS_NEW_CARD_REPORT =">>Upass Reports >> New Card Report";
	
	public static final String TMA_NEWCARD = "New Card";
	public static final String TMA_HOTLISTCARD = "Hotlist Card";
	public static final String TMA_ACTIVATE  = "Activate";
	public static final String TMA_DEACTIVATE  = "Deactivate";
	
	public static final String TMAMARTA10R = "PAR Marta10R";
	public static final String TMAMARTA20R ="PAR Marta20R";
	public static final String TMAMARTACALM = "PAR MartaMthly";
	public static final String TMACBLOCCALM = "PAR CBLocMthly";
	public static final String TMAGWZ1EXP10R = "PAR GWZ1Exp10R";
	public static final String TMACBEXPCALM = "PAR CBExpMthly"; 
	public static final String TMACBLOC10R ="PAR CBLoc10R";
	public static final String TMAGWLOC10R ="PAR GWLoc10R";
	public static final String TMAGW1EXPCALM = "PAR GWZ1ExpMtly"; 
	public static final String TMAGWZ2EXP10R = "PAR GWZ2Exp10R";
	public static final String TMAGWZ2EXPCALM = "PAR GWZ2ExpMtly";
	public static final String TMACBEXP20R = "PAR CBExp20R";
	public static final String TMAGWLOCCALM ="PAR GWLocMthly";
	
	public static final String PARGRBlueZ10R = "PAR GRBlueZ10R";
	public static final String PARGRGreenZ10R = "PAR GRGreenZ10R";
	public static final String PARGRGreenZCalM = "PAR GRGreenMtly";
	public static final String PARGRBlueZCalM ="PAR GRBlueMthly";
	
	
}
