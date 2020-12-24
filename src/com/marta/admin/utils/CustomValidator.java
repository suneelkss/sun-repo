package com.marta.admin.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

/**
 *
 * This class is used for custom validation
 *
 */
public class CustomValidator {

	 public static boolean validateTrueOrFalse
		(Object bean, ValidatorAction va, Field field, ActionMessages errors,
		 Validator validator, HttpServletRequest request) {
		 boolean returnVal = false;
		 String fieldName = field.getVarValue("field");
		 String fieldValue = field.getVarValue("fieldVal");
		 String value2 = ValidatorUtils.getValueAsString(bean,fieldName);
		 if (fieldValue.equalsIgnoreCase("null")) {

			 if(GenericValidator.isBlankOrNull(value2)) {
				 returnVal = true;
			 }

		 }
		 if (fieldValue.equalsIgnoreCase("notnull")) {
			 if(!GenericValidator.isBlankOrNull(value2)) {
				 returnVal = true;
			 }

		 }
		return returnVal;
	 }

	 public static boolean validateRequired
		(Object bean, ValidatorAction va, Field field, ActionMessages errors,
		 Validator validator, HttpServletRequest request) {

		 boolean returnVal = false;
		 String fieldName = field.getVarValue("field");
		 String fieldValue = field.getVarValue("fieldVal");
		 String requiredField = field.getVarValue("requiredField");
		 String requiredFieldCompany = field.getVarValue("requiredField1");
		 String requiredFieldBusiness = field.getVarValue("requiredField2");
		 String requiredFieldListCompany = field.getVarValue("requiredField3");
		 String value = ValidatorUtils.getValueAsString(bean,fieldName);
		 String RequiredValue = ValidatorUtils.getValueAsString(bean,requiredField);
		 
		

		 int fieldVal = Integer.parseInt(value);

		 if(fieldVal == 2) {
			 if(GenericValidator.isBlankOrNull(RequiredValue)
					 		&& fieldValue.equalsIgnoreCase("2")) {
				 errors.add(requiredField,new ActionMessage("company.required"));
				 returnVal = false;
			 } 
		 }

		 if(fieldVal == 3) {
			 String RequiredValueCompany = ValidatorUtils.getValueAsString(bean,requiredFieldCompany);			 
			 if((GenericValidator.isBlankOrNull(RequiredValue) &&  
					 GenericValidator.isBlankOrNull(RequiredValueCompany))
					 		&& fieldValue.equalsIgnoreCase("3")) {					 
				 errors.add(requiredFieldCompany,new ActionMessage("company.required"));
				 errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
				 returnVal = false;
			 } else if ((!GenericValidator.isBlankOrNull(RequiredValue) &&  
					 GenericValidator.isBlankOrNull(RequiredValueCompany))
				 		&& fieldValue.equalsIgnoreCase("3")) {	
				 errors.add(requiredFieldCompany,new ActionMessage("company.required"));
				returnVal = false;	
				 
			 } else if ((GenericValidator.isBlankOrNull(RequiredValue) &&  
					 !GenericValidator.isBlankOrNull(RequiredValueCompany))
				 		&& fieldValue.equalsIgnoreCase("3")) {	
				 errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
				 returnVal = false;	
			 } else {
				 returnVal = true;
			 }
		 }

		 if(fieldVal == 4) {
			 
			 String RequiredValueCompany = ValidatorUtils.getValueAsString(bean,requiredFieldCompany);
			 String RequiredValueBusiness = ValidatorUtils.getValueAsString(bean,requiredFieldBusiness);
			 

			 if((GenericValidator.isBlankOrNull(RequiredValueCompany)
					 && GenericValidator.isBlankOrNull(RequiredValueBusiness)
					 && GenericValidator.isBlankOrNull(RequiredValue))
					 && fieldValue.equalsIgnoreCase("4")) {					 
				 errors.add(requiredFieldCompany,new ActionMessage("company.required"));
				 errors.add(requiredFieldBusiness,new ActionMessage("businessunit.required"));
				 errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
				 returnVal = false;
			 }else if((!GenericValidator.isBlankOrNull(RequiredValueCompany)
					 && GenericValidator.isBlankOrNull(RequiredValueBusiness)
					 && GenericValidator.isBlankOrNull(RequiredValue))
					 && fieldValue.equalsIgnoreCase("4")) {					 
				 errors.add(requiredFieldBusiness,new ActionMessage("businessunit.required"));
				 errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
				 returnVal = false;
			 }else if((GenericValidator.isBlankOrNull(RequiredValueCompany)
					 && !GenericValidator.isBlankOrNull(RequiredValueBusiness)
					 && GenericValidator.isBlankOrNull(RequiredValue))
					 && fieldValue.equalsIgnoreCase("4")) {
				 errors.add(requiredFieldCompany,new ActionMessage("company.required"));
				 errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
				 returnVal = false;
			 }else if((GenericValidator.isBlankOrNull(RequiredValueCompany)
					 && GenericValidator.isBlankOrNull(RequiredValueBusiness)
					 && !GenericValidator.isBlankOrNull(RequiredValue))
					 && fieldValue.equalsIgnoreCase("4")) {				
				 errors.add(requiredFieldCompany,new ActionMessage("company.required"));
				 errors.add(requiredFieldBusiness,new ActionMessage("businessunit.required"));
				 returnVal = false;
			 }else if((!GenericValidator.isBlankOrNull(RequiredValueCompany)
					 && !GenericValidator.isBlankOrNull(RequiredValueBusiness)
					 && GenericValidator.isBlankOrNull(RequiredValue))
					 && fieldValue.equalsIgnoreCase("4")) {					 
				 errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
				 returnVal = false;
			 }else if((GenericValidator.isBlankOrNull(RequiredValueCompany)
					 && !GenericValidator.isBlankOrNull(RequiredValueBusiness)
					 && !GenericValidator.isBlankOrNull(RequiredValue))
					 && fieldValue.equalsIgnoreCase("4")) {					 
				 errors.add(requiredFieldCompany,new ActionMessage("company.required"));
				 returnVal = false;
			 }else if((!GenericValidator.isBlankOrNull(RequiredValueCompany)
					 && GenericValidator.isBlankOrNull(RequiredValueBusiness)
					 && !GenericValidator.isBlankOrNull(RequiredValue))
					 && fieldValue.equalsIgnoreCase("4")) {					 
				 errors.add(requiredFieldBusiness,new ActionMessage("businessunit.required"));
				 returnVal = false;
			 }else{
				 returnVal = true;
			 }
			 
		 }
		 //To check list users.
		 if(fieldVal == 5) {
			if(fieldValue.equalsIgnoreCase("5")) {
				try {
					 String[] requiredValue = (String[]) PropertyUtils.getProperty(bean, requiredField);
					 if (requiredValue != null) {
						 for (int i = 0; i < requiredValue.length; i++) {
							 if(Util.isBlankOrNull(requiredValue[i])) {
								 returnVal = false;
							 } else {
								 returnVal = true;
								 break;
							 }
						 }						 
						 if(!returnVal) {
							 String RequiredValueListCompany = ValidatorUtils.getValueAsString(bean,requiredFieldListCompany);			 
							 if(GenericValidator.isBlankOrNull(RequiredValueListCompany)) {					 
								 errors.add(requiredFieldListCompany,new ActionMessage("company.required"));
							 }
							 errors.add(requiredField,new ActionMessage("list.required" ));							 
						 }
					 } else {
						 errors.add(requiredField,new ActionMessage("list.required" ));
						 String RequiredValueListCompany = ValidatorUtils.getValueAsString(bean,requiredFieldListCompany);			 
						 if(GenericValidator.isBlankOrNull(RequiredValueListCompany)) {					 
							 errors.add(requiredFieldListCompany,new ActionMessage("company.required"));
						 }
						 returnVal = false;
						 
					 }
				} catch (Exception ex) {
					ex.printStackTrace();
				}	
			}
		 }

		 return returnVal;

	 }

	 public static boolean checkRequiredFields
		(Object bean, ValidatorAction va, Field field, ActionMessages errors,
		 Validator validator, HttpServletRequest request) {

		 boolean returnVal = false;
		 String fieldName = field.getVarValue("field");
		 String fieldValue = field.getVarValue("fieldVal");
		 String requiredField = field.getVarValue("requiredField");
		 String fieldVal = ValidatorUtils.getValueAsString(bean,fieldName);

		 try {
			 String[] requiredPropertyValue = (String[]) PropertyUtils.getProperty(bean, requiredField);
			 if(null != requiredPropertyValue){
				 if(fieldVal.equalsIgnoreCase("company")) {
					 if(Util.isBlankOrNull(requiredPropertyValue[0]) &&
							 fieldValue.equalsIgnoreCase("company") ) {
						 returnVal = true;
					 }
				 } else if(fieldVal.equalsIgnoreCase("bu")) {
					 if(Util.isBlankOrNull(requiredPropertyValue[0])&&
							 fieldValue.equalsIgnoreCase("bu")) {
						 returnVal = true;
					 }
				 } else if(fieldVal.equalsIgnoreCase("brand")) {
					 if(Util.isBlankOrNull(requiredPropertyValue[0])&&
							 fieldValue.equalsIgnoreCase("brand")) {
						 returnVal = true;
					 }
				 } else if(fieldVal.equalsIgnoreCase("list")) {
					 if(Util.isBlankOrNull(requiredPropertyValue[0])&&
							 fieldValue.equalsIgnoreCase("list")) {
						 returnVal = true;
					 }
				 }
			 }
		 } catch (Exception ex) {
			ex.printStackTrace();
		 }



		 return returnVal;

	 }

	 public static boolean checkRequiredForStringArray
		(Object bean, ValidatorAction va, Field field, ActionMessages errors,
		 Validator validator, HttpServletRequest request) {

		 boolean returnVal = false;
		 String fieldName = field.getProperty();
		 try {
			 String[] requiredValue = (String[]) PropertyUtils.getProperty(bean, fieldName);
				 for (int i = 0; i < requiredValue.length; i++) {
					 if(!Util.isBlankOrNull(requiredValue[i])) {
						 return true;
					 }
				 }

			if(!returnVal) {
				errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnVal;
	 }

	 public static boolean isAtPresentAsFirstCharacter
	 	(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			 Validator validator, HttpServletRequest request) {

		 boolean returnVal = false;
		 String fieldName = field.getProperty();
		 String fieldValue = ValidatorUtils.getValueAsString(bean,fieldName);
		 if(!GenericValidator.isBlankOrNull(fieldValue)) {
			 String atCharacter = "@";
			 if (fieldValue.startsWith(atCharacter)) {
				 returnVal = true;
			 } else {
				 returnVal = false;
			 }
		 }

		 if(!returnVal) {
				errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
		 }
		 return returnVal;
	 }
	 
	 public static Date getServerDateAndTime(Date userDate,String userTimeZone) throws Exception {
			
			try {
				DateFormat serverTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				DateFormat userTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String strDate = serverTimeFormat.format(userDate);
				//Server Time Zone
				serverTimeFormat.setTimeZone(TimeZone.getTimeZone(userTimeZone));
				Date date = serverTimeFormat.parse(strDate);   
				//User Specific Time Zone
				userTimeFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName()));
				return date;
				
			} catch (Exception ex) {
				throw ex;
			}		
			
		}
	 
	 public static boolean checkValueRequired
		(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			 Validator validator, HttpServletRequest request) {

		 boolean returnVal = false;
		 String fieldValue = ValidatorUtils.getValueAsString(bean,field.getProperty());
		 String requiredField = field.getVarValue("requiredField");
		 String requiredValue = ValidatorUtils.getValueAsString(bean,requiredField);
		 if(!GenericValidator.isBlankOrNull(fieldValue)) {
			 if (!GenericValidator.isBlankOrNull(requiredValue)) {
				 returnVal = true;
			 } else {
				 returnVal = false;
			 }
		 }else{
			 returnVal = true;
		 }
		 
		 if(!returnVal) {
				errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
		 }
		 return returnVal;
	 }
	 
	 /**
	  * Method to get date and time.
	  * 
	  * @param userDate
	  * @param userTimeZone
	  * @return
	  * @throws Exception
	  */
	 public static String getDateAndTime(Date userDate,String userTimeZone) throws Exception {
			
			try {
				DateFormat serverTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm a"); 
				DateFormat userTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm a"); 
				/*System.out.println("userDate --->"+userDate);
				System.out.println("strDate --->"+strDate);*/
				//Server Time Zone
				serverTimeFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName()));
				//User Specific Time Zone
				//System.out.println("date --->"+date);
				userTimeFormat.setTimeZone(TimeZone.getTimeZone(userTimeZone));
				//System.out.println("userTimeFormat.format(date) --->"+userTimeFormat.format(userDate));
				//return userTimeFormat.format(date);
				return userTimeFormat.format(userDate);
				
			} catch (Exception ex) {
				throw ex;
			}		
			
		}
	 
	 /**
	  * Method to check Captcha that entered in signup page.
	  * 
	  * @param bean
	  * @param va
	  * @param field
	  * @param errors
	  * @param validator
	  * @param request
	  * @return
	  */
	 public static boolean checkCaptcha
		(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			 Validator validator, HttpServletRequest request) throws Exception{

		 boolean returnVal = false;
		 /***
		  * Commented for ReCaptcha (start)
		  */
		 /*String captchaGenerated = "";
		 HttpSession session = request.getSession(true);
		 if (null != session.getAttribute("CAPTCHA_KEY")) {
				captchaGenerated = (String) session
						.getAttribute("CAPTCHA_KEY");
			}
		 String capctchaEntered = ValidatorUtils.getValueAsString(bean,field.getProperty());
		 if(!GenericValidator.isBlankOrNull(capctchaEntered)
				 && capctchaEntered.equals(captchaGenerated)) {			 
			returnVal = true;			
		 }else{
			 returnVal = false;
		 }
		 
		 if(!returnVal) {
				errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
		 }*/
		 /***
		  * Commented for ReCaptcha (end)
		  */
		 try{
				String remoteAddr = request.getRemoteAddr();
				ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
				reCaptcha.setPrivateKey("6LcA_goAAAAAAKPPPz3y7HThCHveAadNppI5_KWa");
				
				String challenge = request.getParameter("recaptcha_challenge_field");
				String uresponse = request.getParameter("recaptcha_response_field");
				
				ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
				
				if (reCaptchaResponse.isValid()) {
					returnVal = true;
				} else {
					returnVal = false;
				}
			}catch(Exception e){
				throw e;
			}
					 
			if(!returnVal) {
				errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
			}
		 return returnVal;
	 }
	 
	 /**
	  * Method to validate the breeze card serial numbers that were entered thru the link existing card.
	  * 
	  * @param bean
	  * @param va
	  * @param field
	  * @param errors
	  * @param validator
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 public static boolean checkBreezeCardSerialNumber
		(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			 Validator validator, HttpServletRequest request) throws Exception {

		 boolean returnVal = false;

		 String previousSerialNumber="";
		 String breezeCardSerialNumber = "";
		 String fieldName = field.getKey();
		 		 
		 breezeCardSerialNumber = ValidatorUtils.getValueAsString(bean,field.getProperty());
		
		 if(!Util.isBlankOrNull(breezeCardSerialNumber)){
			 
			 // Removing the space in the breeze card serial number.
			 breezeCardSerialNumber = Util.removeSpaceInBreezeCardSerialNo(breezeCardSerialNumber);

			 // Setting the serial number in the request if the field is 'breezeCardSerialNumber'.
			 if(fieldName.equals(Constants.BREEZE_CARD_SERIAL_NUMBER)){
				 request.setAttribute(Constants.PREVIOUS_BREEZE_CARD_SERIAL_NUMBER, breezeCardSerialNumber);
			 }
			 
			 if(null != request.getAttribute(Constants.PREVIOUS_BREEZE_CARD_SERIAL_NUMBER)){
				 previousSerialNumber = request.getAttribute(Constants.PREVIOUS_BREEZE_CARD_SERIAL_NUMBER).toString();
			 }else{
				 previousSerialNumber = breezeCardSerialNumber;
			 }
			 
			 // Checking whether both the breezeCardSerialNumber and confirmBreezeCardSerialNumber are same.
			 if(!Util.isBlankOrNull(previousSerialNumber) && previousSerialNumber.equals(breezeCardSerialNumber)){
				
				 // Checking whether both the breezeCardSerialNumber 
				 // and confirmBreezeCardSerialNumber are in the valid length(16).
				 if(breezeCardSerialNumber.length() == Constants.BREEZE_CARD_SERIAL_NUMBER_LENGTH) {
					 returnVal = true;
				 }else{
				 	returnVal = false;
				 }
					 
				 if(!returnVal) {
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
				 }
					 
			 }else{
				 // Failure Case: Thrown error message if the serial numbers are not same. 
				 errors.add(field.getKey(), new ActionMessage("breezecard.cardserialnumber.match"));
			 }
			 
		 }
		
		 return returnVal;
	 }

}
