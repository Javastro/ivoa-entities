/*
 * $Id: JaxBReadingTest.java,v 1.2 2011-09-13 13:43:32 pah Exp $
 * 
 * Created on 8 Jun 2011 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2011 Astrogrid. All rights reserved.
 *
 * This software is published under the terms of the Astrogrid 
 * Software License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.jaxb;

import static org.junit.Assert.*;

import java.io.IOException;


import javax.xml.bind.JAXBException;

import org.javastro.ivoa.entities.resource.Resource;
import org.javastro.ivoa.entities.resource.registry.iface.VOResources;
import org.junit.Test;
import org.xml.sax.SAXException;



public class JaxBReadingTest extends JaxBBaseTest {
    
    @Test
    public void testReadRegistry() throws JAXBException, IOException, SAXException{
        VOResources rs = readResources("/registry.xml");
        assertEquals("identifier", "ivo://test/registry",rs.getResources().get(0).getIdentifier());
    }
 
    @Test
    public void testReadApplication() throws JAXBException, IOException, SAXException{
        VOResources rs = readResources("/application.xml");
        assertEquals("identifier", "ivo://cds.fr/applications/aladin",rs.getResources().get(0).getIdentifier());
    }
     
    @Test
    public void testReadConeSearch() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/conesearch.xml");
        assertEquals("identifier" , "ivo://adil.ncsa/vocone", rs.getIdentifier());
    }
    @Test
    public void testReadOrganization() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/organization.xml");
        assertEquals("identifier" , "ivo://ivoa.net/IVOA", rs.getIdentifier());
    }
    @Test
    public void testReadSia() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/sia.xml");
        assertEquals("identifier" , "ivo://adil.ncsa/sia", rs.getIdentifier());
    }
    @Test
    public void testReadStc() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/siaStc.xml");
        assertEquals("identifier" , "ivo://nasa.heasarc/swiftmastr", rs.getIdentifier());
    }
    public void testReadStd() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/siastd.xml");
        assertEquals("identifier" , "ivo://ivoa.net/std/SIA", rs.getIdentifier());
    }
    @Test
    public void testReadStd2() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/vospacestd.xml");
        assertEquals("identifier" , "ivo://ivoa.net/vospace/core", rs.getIdentifier());
    }
      
   
}


/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1.2.1  2011/09/01 09:32:33  pah
 * test for reading xml
 *
 * Revision 1.1.2.1  2011/06/09 22:18:52  pah
 * basic VOResource schema nearly done - but not got save/recall working
 *
 */
