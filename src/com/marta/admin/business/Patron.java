package com.marta.admin.business;

import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.SignupForm;

/**
 * Interface to add and update patron details.
 * 
 * @version 1.0
 * @created 27-Oct-2009 12:31:17 PM
 * 
 */
public interface Patron {	
	
	/**
	 * Methdo to add patron.
	 * 
	 * @param signupForm
	 * @param martaURL
	 * @return
	 * @throws MartaException
	 */
	public boolean addPatron(SignupForm signupForm,String martaURL)throws MartaException;	
	
	/**
	 * Method to update patron.
	 * 
	 * @param signupForm
	 * @param patronId
	 * @param updatePassword
	 * @param isAnswerUpdated
	 * @return
	 * @throws MartaException
	 */
	public boolean updatePatron(SignupForm signupForm,Long patronId,boolean updatePassword,boolean isAnswerUpdated)throws MartaException;

}