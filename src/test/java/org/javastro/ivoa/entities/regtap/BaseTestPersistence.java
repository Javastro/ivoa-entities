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
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;

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
    
 
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClassBase() throws Exception {
        
        emf = new TestPersistenceManager().createEmf();
       
       
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
