package com.marta.admin.dao;

/**
 *	This class is Base class for all the Data access object (DAO) for
 *	domain model class. 
 */

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.marta.admin.hibernate.sessionfactory.MartaSessionFactory;

/**
 * 
 * Data access object (DAO) to create database connection
 * 
 */

public class MartaBaseDAO {

	/**
	 * This method to get hibernate session
	 * 
	 * @return Session
	 */
	protected Session getSession() {
		return MartaSessionFactory.getSession();
	}

	/**
	 * This method commit the transaction and close the session
	 * 
	 * @param session -
	 *            session to be closed
	 * @param transaction -
	 *            transaction to be commited
	 */
	protected void closeSession(Session session, Transaction transaction) {
		try {
			if (transaction != null && !transaction.wasCommitted()) {
				transaction.commit();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		try {
			MartaSessionFactory.closeSession();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method roll back the current transaction
	 * 
	 * @param tx -
	 *            transaction to be rollbacked
	 * @throws HibernateException
	 */
	protected void rollBackTransaction(Transaction tx) {
		try {
			if (tx != null && !tx.wasRolledBack()) {
				tx.rollback();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}
}
