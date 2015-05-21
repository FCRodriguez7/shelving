package edu.yale.sml.persistence.config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class HibernateOracleUtils {

    private static final Logger logger = getLogger(HibernateOracleUtils.class);

    private static final String RESOURCE = "shelfscan.oracle.hibernate.cfg.xml";

    private static SessionFactory sessionFactory = null;

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure(RESOURCE)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("Error", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        } else {
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory == null) {
            return;
        }

        try {
            getSessionFactory().close();
            sessionFactory = null;
        } catch (HibernateException he) {
            throw new HibernateException(he);
        }
    }
}