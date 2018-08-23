/*
 * $Id: TestAll.java,v 1.2 2011-09-13 13:43:33 pah Exp $
 * 
 * Created on 28 May 2011 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2011 Astrogrid. All rights reserved.
 *
 * This software is published under the terms of the Astrogrid 
 * Software License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.resource;

import static org.junit.Assert.*;

import org.javastro.ivoa.entities.jpa.ResourceJpaController;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAll {
    private static ResourceJpaController rcont;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
        rcont = new ResourceJpaController();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testit(){
        fail("not implemented");
    }

    
}


/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1.2.1  2011/06/09 22:18:56  pah
 * basic VOResource schema nearly done - but not got save/recall working
 *
 */
