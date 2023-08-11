/*
 * $Id$
 * 
 * Created on 26 Feb 2013 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2013 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */

package org.javastro.ivoa.entities.regtap;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;


/**
 *  Base class for Database Testing. Note that the conventional names for the annotated
 *  test methods are not used so that they are not hidden in the child classes, and 
 *  note that this problem should also be taken into consideration if there are any 
 *  grandchildren.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 26 Feb 2013
 * @version $Revision$ $date$
 */
public class BaseTestPersistence {

    protected static EntityManagerFactory emf;
    protected static TestPersistenceManager testPersistenceManager;
 
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClassBase() throws Exception {
        
        testPersistenceManager = new TestPersistenceManager();
        emf = testPersistenceManager.createEmf();
       
       
    }


    /**
     * 
     */
    public BaseTestPersistence() {
        super();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUpBase() throws Exception {
       
    }
    
    
   @After
    public void tearDownBase() throws Exception {
    }
    
      /**
     * Write the contents of the database to a file.
     * @param em the entity manager for the database.
     * @param filename The name of the file to write the DDL to.
     */
    protected void dumpDbData(jakarta.persistence.EntityManager em, String filename) {
        //IMPL hibernate specific way of getting connection... generally dirty, see  https://stackoverflow.com/questions/3493495/getting-database-connection-in-pure-jpa-setup
            Session sess = em.unwrap(Session.class);
            sess.doWork(conn -> {
                PreparedStatement ps = conn.prepareStatement("SCRIPT TO ?"); // this is H2db specific
                ps.setString(1, filename);
                ps.execute();
            });
    }
    
    protected static void dropDatabase()
    {
        //attempt to clear out the database so that multiple tests can be run in the same jvm - not really working however!
        try {
            DriverManager.getConnection("jdbc:derby:memory:ivoatestdb;drop=true");
        } catch (SQLException e) {//Derby treats this as and error
            ;
            if(!e.getSQLState().contains("08006")) 
                e.printStackTrace();// only report if not the expected error
        }
    }

}

/*
 * $Log$
 */
