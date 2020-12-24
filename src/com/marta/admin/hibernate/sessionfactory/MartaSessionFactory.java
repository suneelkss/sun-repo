package com.marta.admin.hibernate.sessionfactory;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

/**
 * Configures and provides access to Hibernate sessions, tied to the
 * current thread of execution.  Follows the Thread Local Session
 * pattern, see {@link http://hibernate.org/42.html }.
 */
public class MartaSessionFactory {

    /**
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses
     * #resourceAsStream style lookup for its configuration file.
     * The default classpath location of the hibernate config file is
     * in the default package. Use #setConfigFile() to update
     * the location of the configuration file for the current session.
     */
    private static String CONFIG_FILE_LOCATION = "/com/marta/admin/hibernate/xml/hibernate.cfg.xml";
	private static final ThreadLocal threadLocal = new ThreadLocal();
    private  static Configuration configuration = new Configuration();
    private static org.hibernate.SessionFactory sessionFactory;
  //  private static String configFile = CONFIG_FILE_LOCATION;
    private static final Configuration cfg = new Configuration();
    

    private MartaSessionFactory() {
    }
    
    /**
	 * * initialises the configuration if not yet done and returns the current *
	 * instance * *
	 *
	 * @return
	 */
	public static SessionFactory getInstance() {
		if (sessionFactory == null)
			initSessionFactory();
		return sessionFactory;
	}
	
	
	/**
	 * * Returns the ThreadLocal Session instance. Lazy initialize the *
	 * <code>SessionFactory</code> if needed. * *
	 *
	 * @return Session *
	 * @throws HibernateException
	 */
	public Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * * The behaviour of this method depends on the session context you have *
	 * configured. This factory is intended to be used with a hibernate.cfg.xml *
	 * including the following property <property *
	 * name="current_session_context_class">thread</property> This would return *
	 * the current open session or if this does not exist, will create a new *
	 * session * *
	 *
	 * @return
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * * initializes the sessionfactory in a safe way even if more than one
	 * thread * tries to build a sessionFactory
	 */
	private static synchronized void initSessionFactory() {
		/*
		 * Check again for null because sessionFactory may have been 
		 * initialized between the last check and now 
		 */
		
		Logger log = Logger.getLogger(MartaSessionFactory.class);
		
		if (sessionFactory == null) {
			try {
				cfg.configure(CONFIG_FILE_LOCATION);
				String sessionFactoryJndiName = cfg
						.getProperty(Environment.SESSION_FACTORY_NAME);
				if (sessionFactoryJndiName != null) {
					cfg.buildSessionFactory();
					log.debug("get a jndi session factory");
					sessionFactory = (SessionFactory) (new InitialContext())
							.lookup(sessionFactoryJndiName);
				} else {
					log.debug("classic factory");
					sessionFactory = cfg.buildSessionFactory();
				}
			} catch (Exception e) {				
				e.printStackTrace();
				throw new HibernateException(
						"Could not initialize the Hibernate configuration");
			}
		}		
	}

	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

        return session;
    }

	/**
     *  Rebuild hibernate session factory
     *
     */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(CONFIG_FILE_LOCATION);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}
	
	
	public static void close() {
		if (sessionFactory != null)
			sessionFactory.close();
		sessionFactory = null;
	}

	/**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null && session.isOpen()) {
            session.close();
        }
    }

	/**
     *  return session factory
     *
     */
	/*public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}*/

	/**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
	/*public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}*/

	/**
     *  return hibernate configuration
     *
     */
	/*public static Configuration getConfiguration() {
		return configuration;
	}*/	
	
}