/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.hibernatesearchtest;

/**
 *
 * @author simone
 */
import java.util.Properties;
 
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
 
/**
 * Contains utility methods 
 * 
 * @author srccodes.com
 * @version 1.0
 *
 */
public class HibernateUtil {
     
    private static SessionFactory sessionFactory = null;  
    private static ServiceRegistry serviceRegistry = null;  
       
    private static SessionFactory configureSessionFactory() throws HibernateException {  
        Configuration configuration = new Configuration();  
        configuration.configure();  
         
        Properties properties = configuration.getProperties();
         
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();          
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);  
         
        return sessionFactory;  
    }
     
    // We need to configure session factory once. 
    // Rest of the time we will get session using the same.
    static {
        configureSessionFactory();
    }
     
    private HibernateUtil() {}
     
    public static Session getSession() {
        return sessionFactory.openSession();
    }
}