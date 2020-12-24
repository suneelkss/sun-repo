package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.UpassSchoolDTO;
import com.marta.admin.hibernate.UpassSchools;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Util;

public class UpassDAO extends MartaBaseDAO{
	
	public List getUpassSchools(){
		
		Session session = null;
		Transaction transaction = null;
		UpassSchools  upassSchools = new UpassSchools();
		UpassSchoolDTO upassSchoolsdto = new UpassSchoolDTO(); 
		
		List schoolList = new ArrayList();
		try {
			BcwtsLogger.debug("Get Session");
			session = getSession();
			BcwtsLogger.debug(" Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(" Execute Query");

			String query = "from UpassSchools";
			
			
			schoolList = (ArrayList)session.createQuery(query).list();
			
			
			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info("Got Upass Schools ");
		} catch (Exception ex) {
			BcwtsLogger.error(Util.getFormattedStackTrace(ex));
		

		} finally {
			closeSession(session, transaction);

		}
		
		
		return schoolList;
	}

}
