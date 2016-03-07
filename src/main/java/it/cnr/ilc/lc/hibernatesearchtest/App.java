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
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.text.AbstractDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

/**
 * Hello world!
 *
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        logger.trace("puppacilafava");
        // org.hibernate.Session session = HibernateSessionFactory.getSession();
        //     Logger.getLogger( LogCategory.INFOSTREAM_LOGGER_CATEGORY.toString() ).setLevel(  );

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("OmegaPU");
        EntityManager omegaEntityManager = entityManagerFactory.createEntityManager();

        try {
            //Contact angelo = new Contact();
            //angelo.setId(1);
            //angelo.setEmail("angelo@provider.com");
            //angelo.setName("Angelo Mario Del Grosso");

            //Contact michele = new Contact();
            //angelo.setId(1);
            //michele.setEmail("michele@provider.com");
            //michele.setName("Michele Antonio Di Tocco");
            omegaEntityManager.getTransaction().begin();

            List<Contact> contacts1 = GenerateContacts.INSTANCE.generate("angelo", "angela", "michele", "michela", "antonio", "antonia", "antonietta", "antonina");


            /* hibernate */
            //session.saveOrUpdate(angelo);
            //session.flush();
            /* JPA */
            for (Contact contact : contacts1) {
                System.err.println(contact.toString());
                //Contact c = new Contact();
                //c.setName(contact.getName());
                //c.setEmail(contact.getEmail());
                omegaEntityManager.persist(contact);

            }

            omegaEntityManager.getTransaction().commit();
//            omegaEntityManager.getTransaction().begin();
//
//            omegaEntityManager.persist(michele);
//
//            omegaEntityManager.getTransaction().commit();
            // omegaEntityManager.close();

            /* hibernate re-indexing */
            //FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
            //fullTextSession.createIndexer().startAndWait();

            /* JPA re-indexing */
            FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(omegaEntityManager);
//            fullTextEntityManager.createIndexer().startAndWait();

            /* hibernate lucene query */
            //Transaction tx = fullTextSession.beginTransaction();
            //QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Contact.class).get();
            // org.apache.lucene.search.Query query = qb.keyword().wildcard().onFields("name").matching("angel*").createQuery();
//            org.apache.lucene.search.Query query = qb
//                    .keyword()
//                    .fuzzy()
//                    .withEditDistanceUpTo(2)
//                    .withPrefixLength(1)
//                    .onField("name")
//                    .matching("Angillo")
//                    .createQuery();
            // org.apache.lucene.search.Query query = qb.phrase().onField("email").sentence("angelo").createQuery();
            // org.apache.lucene.search.Query query = qb.keyword().onField("name").matching("angelo").createQuery();
            //org.apache.lucene.search.Query query = qb.phrase().onField("name").sentence("Angelo Mario").createQuery();
            // wrap Lucene query in a org.hibernate.Query  
            // org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Contact.class);
            // fullTextSession.close();
            // execute search  
            // List result = hibQuery.list();
            /* JPA lucene query */
            omegaEntityManager.getTransaction().begin();
            QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Contact.class).get();
            org.apache.lucene.search.Query query = qb
                    .keyword()
                    .wildcard()
                    .onFields("name")
                    .matching("anto*")
                    .createQuery();

            javax.persistence.Query persistenceQuery
                    = fullTextEntityManager.createFullTextQuery(query, Contact.class);
            List result = persistenceQuery.getResultList();

            Iterator<Contact> it = result.iterator();
            while (it.hasNext()) {
                Contact c = (Contact) it.next();
                System.err.println("RES: " + c);
            }
          
            /* JPA */
            omegaEntityManager.getTransaction().commit();
            
              /* hibernate */
            //tx.commit();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
               
                /* JPA */
                omegaEntityManager.close();
                
                 /* hibernate */
                //session.close();

            } catch (Exception e) {
                System.err.println("CATCH2: " + e.getMessage());
            }
        }

    }

    private static void doIndex() throws InterruptedException {
        Session session = HibernateUtil.getSession();

        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.createIndexer().startAndWait();

        fullTextSession.close();
    }
//     
//    private static List<Contact> search(String queryString) {
//        Session session = HibernateUtil.getSession();
//        FullTextSession fullTextSession = Search.getFullTextSession(session);
//         
//        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Contact.class).get();
//        org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields("name").matching(queryString).createQuery();
// 
//        // wrap Lucene query in a javax.persistence.Query
//        org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Contact.class);
//         
//        List<Contact> contactList = fullTextQuery.list();
//         
//        fullTextSession.close();
//         
//        return contactList;
//    }
//     
//    private static void displayContactTableData() {
//        Session session = null;
//         
//        try {
//            session = HibernateUtil.getSession();
//             
//            // Fetching saved data
//            List<Contact> contactList = session.createQuery("from Contact").list();
//             
//            for (Contact contact : contactList) {
//                System.out.println(contact);
//            }
//             
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally{
//            if(session != null) {
//                session.close();
//            }
//        }
//    }
//     
//    public static void main(String[] args) throws InterruptedException {
//        System.out.println("\n\n******Data stored in Contact table******\n");
//        displayContactTableData();
//         
//        // Create an initial Lucene index for the data already present in the database
//        doIndex();
//         
//        Scanner scanner = new Scanner(System.in);
//        String consoleInput = null;
//         
//        while (true) {
//            // Prompt the user to enter query string
//            System.out.print("\n\nEnter search key (To exit type 'X')");            
//            consoleInput = scanner.nextLine();
//             
//            if("X".equalsIgnoreCase(consoleInput)) {
//                System.out.println("End");
//                System.exit(0);
//            }   
//             
//            List<Contact> result = search(consoleInput);            
//            System.out.println("\n\n>>>>>>Record found for '" + consoleInput + "'");
//             
//            for (Contact contact : result) {
//                System.out.println(contact);
//            }               
//        }           
//    }
}
