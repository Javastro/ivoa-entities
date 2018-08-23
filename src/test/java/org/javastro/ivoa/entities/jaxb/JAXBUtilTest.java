/*
 * $Id: JAXBUtilTest.java,v 1.2 2011-09-13 13:43:32 pah Exp $
 * 
 * Created on 13 Sep 2011 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2011 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.jaxb;

import static org.junit.Assert.*;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.javastro.ivoa.entities.jaxb.IvoaJAXBUtils;
import org.javastro.ivoa.entities.resource.registry.iface.VOResources;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *  .
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 13 Sep 2011
 * @version $Revision: 1.2 $ $date$
 */
public class JAXBUtilTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void unmarshaltest() throws JAXBException, IOException, SAXException {
        VOResources resources = IvoaJAXBUtils.unmarshall(this.getClass().getResourceAsStream("/VOResource.xml"), VOResources.class);
        assertNotNull("resource bundle", resources);
    }
}


/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1.2.1  2011/09/13 13:32:58  pah
 * new test
 *
 */
