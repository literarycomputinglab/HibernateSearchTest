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
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.engine.spi.FacetManager;
import org.hibernate.search.query.facet.Facet;
import org.hibernate.search.query.facet.FacetingRequest;

/**
 * Hello world!
 *
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        //omegaEmbeddedExample();
        //omegaPathExample(false);
        omegaFacetExample();
    }

    private static void omegaFacetExample() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("facetExample");
        EntityManager omegaEntityManager = entityManagerFactory.createEntityManager();

        if (false) {
            omegaEntityManager.getTransaction().begin();

            Source s = new Source();

            s.setUri(URI.create("/uri/source/02").toASCIIString());

            Content c1 = new Content();
            c1.setData("Contenuto della source");

            Content c2 = new Content();
            c2.setData("Contenuto della annotazione");

            s.setContent(c1);
            c1.setSource(s);

            Locus l1 = new Locus();
            l1.setSource(s);
            l1.setStart(0);
            l1.setEnd(5);
            l1.setFragment(l1.getSource().getContent().getData().substring(l1.getStart(), l1.getEnd()));

            Locus l2 = new Locus();
            l2.setSource(s);
            l2.setStart(10);
            l2.setEnd(14);
            l2.setFragment(l2.getSource().getContent().getData().substring(l2.getStart(), l2.getEnd()));

            Annotation a = new Annotation();
            a.setCont(c2);
            List<Locus> loci = new ArrayList<Locus>();
            loci.add(l1);
            loci.add(l2);
            a.setLoci(loci);
            a.setType("Abbreviation");

            l1.setAnnotation(a);
            l2.setAnnotation(a);

            Content c3 = new Content();
            c3.setData("Altro contenuto di una seconda annotazione");
            Annotation a2 = new Annotation();
            a2.setCont(c3);
            List<Locus> loci2 = new ArrayList<Locus>();
            Locus l3 = new Locus();
            l3.setSource(s);
            l3.setStart(0);
            l3.setEnd(5);
            l3.setFragment(l3.getSource().getContent().getData().substring(l3.getStart(), l3.getEnd()));
            loci2.add(l3);
            a2.setLoci(loci2);
            a2.setType("Line");

            l3.setAnnotation(a2);

            omegaEntityManager.persist(a);
            omegaEntityManager.persist(a2);

            omegaEntityManager.getTransaction().commit();
        }

        omegaEntityManager.getTransaction().begin();

        FullTextEntityManager fullTextEntityManager
                = org.hibernate.search.jpa.Search.getFullTextEntityManager(omegaEntityManager);

        QueryBuilder builder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Annotation.class).get();

        FacetingRequest labelFacetingRequest = builder.facet()
                .name("typeFacetRequest")
                .onField("type")
                .discrete().createFacetingRequest();

        Query luceneQuery = builder.all().createQuery(); // match all query
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Annotation.class);
        FacetManager facetManager = fullTextQuery.getFacetManager();
        facetManager.enableFaceting(labelFacetingRequest);
        
        List<Annotation> results = fullTextQuery.getResultList();
        
        for (Annotation result : results) {
            
            logger.info("id: " + result.getId() + " " +result.getCont().getData());
            
        }
        
        List<Facet> facets = facetManager.getFacets("typeFacetRequest");
        
        logger.info("facets:  " +facets.size());
        
        for (Facet facet : facets) {
            logger.info("faceting name: " + facet.getFacetingName());
            logger.info("count: " + facet.getCount());
            logger.info("field name: " + facet.getFieldName());
            logger.info("value: " + facet.getValue());
            logger.info("facet query: " + facet.getFacetQuery());
            
            
        }
        
        

        omegaEntityManager.getTransaction().commit();

    }

    public static void omegaPathExample(boolean condition) {

        Boolean create = new Boolean(condition);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("omegaEmbeddedPathExample");
        EntityManager omegaEntityManager = entityManagerFactory.createEntityManager();

        if (create) {
            omegaEntityManager.getTransaction().begin();

            Source s = new Source();

            s.setUri(URI.create("/uri/source/02").toASCIIString());

            Content c1 = new Content();
            c1.setData("Contenuto della source");

            Content c2 = new Content();
            c2.setData("Contenuto della annotazione");

            s.setContent(c1);
            c1.setSource(s);

            Locus l1 = new Locus();
            l1.setSource(s);
            l1.setStart(0);
            l1.setEnd(5);
            l1.setFragment(l1.getSource().getContent().getData().substring(l1.getStart(), l1.getEnd()));

            Locus l2 = new Locus();
            l2.setSource(s);
            l2.setStart(10);
            l2.setEnd(14);
            l2.setFragment(l2.getSource().getContent().getData().substring(l2.getStart(), l2.getEnd()));

            Annotation a = new Annotation();
            a.setCont(c2);
            List<Locus> loci = new ArrayList<Locus>();
            loci.add(l1);
            loci.add(l2);
            a.setLoci(loci);

            l1.setAnnotation(a);
            l2.setAnnotation(a);

            Content c3 = new Content();
            c3.setData("Altro contenuto di una seconda annotazione");
            Annotation a2 = new Annotation();
            a2.setCont(c3);
            List<Locus> loci2 = new ArrayList<Locus>();
            Locus l3 = new Locus();
            l3.setSource(s);
            l3.setStart(0);
            l3.setEnd(5);
            l3.setFragment(l3.getSource().getContent().getData().substring(l3.getStart(), l3.getEnd()));
            loci2.add(l3);
            a2.setLoci(loci2);
            l3.setAnnotation(a2);

            omegaEntityManager.persist(a);
            omegaEntityManager.persist(a2);

            omegaEntityManager.getTransaction().commit();
        }

        FullTextEntityManager fullTextEntityManager
                = org.hibernate.search.jpa.Search.getFullTextEntityManager(omegaEntityManager);

        omegaEntityManager.getTransaction().begin();
//        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Annotation.class).get();
//
//        logger.info("Before query");
//
//        org.apache.lucene.search.Query query = qb
//                .phrase()
//                .onField("loci.fragment")
//                .sentence("Conte")
//                .createQuery();
//
//        javax.persistence.Query persistenceQuery
//                = fullTextEntityManager.createFullTextQuery(query, Annotation.class);
//        List result = persistenceQuery.getResultList();
//
//        Iterator<Annotation> it = result.iterator();
//        while (it.hasNext()) {
//            Annotation ann = (Annotation) it.next();
//            logger.info("RES: " + ann.getCont().getData() + " => " + ann.getId() + " : " + ann.getLoci());
//
//            //source.getContent().setData("terzo testo del contenuto");
//        }

        SearchFactory searchFactory = fullTextEntityManager.getSearchFactory();

        QueryParser parser = new QueryParser("cont.data", searchFactory.getAnalyzer(Annotation.class));

        try {
            org.apache.lucene.search.Query luceneQuery = parser.parse("cont.data:Altro");
            javax.persistence.Query fullTextQuery
                    = fullTextEntityManager.createFullTextQuery(luceneQuery);

            List<Annotation> result = fullTextQuery.getResultList();

            for (Annotation r : result) {
                logger.info(r.getId() + " " + r.getCont().getData());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        omegaEntityManager.getTransaction().commit();
        logger.info("After query and commit");

    }

    public static void omegaEmbeddedExample() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("omegaEmbeddedExample");
        EntityManager omegaEntityManager = entityManagerFactory.createEntityManager();

//        omegaEntityManager.getTransaction().begin();
//
//        Content c = new Content();
//        Source s = new Source();
//
//        s.setContent(c);
//        s.setUri(URI.create("/uriexample/1").toASCIIString());
//        c.setData("Testo del contenuto");
//
//        Content c2 = new Content();
//        Source s2 = new Source();
//
//        s2.setContent(c2);
//        s2.setUri(URI.create("/uriexample/2").toASCIIString());
//        c2.setData("Secondo testo del contenuto");
//
//        omegaEntityManager.persist(s);
//        omegaEntityManager.persist(s2);
//        omegaEntityManager.getTransaction().commit();
        FullTextEntityManager fullTextEntityManager
                = org.hibernate.search.jpa.Search.getFullTextEntityManager(omegaEntityManager);

        omegaEntityManager.getTransaction().begin();
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Source.class).get();
//        org.apache.lucene.search.Query query = qb
//                .keyword()
//                .wildcard()
//                .onField("cont.data")
//                .matching("seco*")
//                .createQuery();
//        
        org.apache.lucene.search.Query query = qb
                .phrase()
                .onField("content.data")
                .sentence("secondo testo del")
                .createQuery();

        javax.persistence.Query persistenceQuery
                = fullTextEntityManager.createFullTextQuery(query, Source.class);
        List result = persistenceQuery.getResultList();

        Iterator<Source> it = result.iterator();
        while (it.hasNext()) {
            Source source = (Source) it.next();
            System.err.println("RES: " + source.getUri() + " => " + source.getContent().getData());
            //source.getContent().setData("terzo testo del contenuto");
            omegaEntityManager.getTransaction().commit();
            break;
        }

        omegaEntityManager.getTransaction().begin();

        query = qb.phrase()
                .onField("content.data")
                .sentence("terzo testo del")
                .createQuery();

        persistenceQuery
                = fullTextEntityManager.createFullTextQuery(query, Source.class);
        result = persistenceQuery.getResultList();

        it = result.iterator();
        while (it.hasNext()) {
            Source source = (Source) it.next();
            System.err.println("RES2: " + source.getUri() + " => " + source.getContent().getData());
        }

        /* JPA */
        omegaEntityManager.getTransaction().commit();
    }

    public static void contactExample() {

        // org.hibernate.Session session = HibernateSessionFactory.getSession();
        //     Logger.getLogger( LogCategory.INFOSTREAM_LOGGER_CATEGORY.toString() ).setLevel(  );
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("contactExample");
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
