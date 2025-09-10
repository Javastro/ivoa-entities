/*
 * $Id: JaxbTest.java,v 1.2.2.1 2012-02-14 10:52:41 pah Exp $
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

import static org.javastro.ivoa.entities.jaxb.Utils.marshall;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import jakarta.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.javastro.ivoa.entities.IvoaJAXBUtils;
import org.javastro.ivoa.entities.resource.Resource;
import org.javastro.ivoa.entities.resource.registry.iface.VOResources;
import org.javastro.ivoa.entities.vosi.tables.Tableset;
import org.javastro.ivoa.schema.Namespaces;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class JaxbTest {



    @Test
    public void testReadXML() throws JAXBException, IOException, SAXException, TransformerException{
        // Unmarshall the file into a content object
        VOResources vr =   IvoaJAXBUtils.unmarshall(new InputStreamReader(getClass()
                .getResourceAsStream("/VOResource.xml")),VOResources.class, true);
        System.out.println(vr.getResources().get(0).getIdentifier());

        Document doc = marshall(vr);
       // IvoaJAXBUtils.printXML(doc, new PrintWriter(System.out));
        
    }
    
    
        @Test
    public void testReadRegistry() throws JAXBException, IOException, SAXException{
        VOResources rs = readResources("/registry.xml");
        assertEquals( "ivo://test/registry",rs.getResources().get(0).getIdentifier(),"identifier");
    }
 
    /**
         * @param string
         * @return
     * @throws SAXException 
     * @throws IOException 
     * @throws JAXBException 
         */
        private VOResources readResources(String r) throws JAXBException, IOException, SAXException {
           final InputStream res = getClass()
                    .getResourceAsStream(r);
           assertNotNull( res,"resource not found "+r);
            System.out.println(r+" "+ LocalDateTime.now());
        return IvoaJAXBUtils.unmarshall(new InputStreamReader(res),VOResources.class, true);
            
        }

    @Test
    public void testReadApplication() throws JAXBException, IOException, SAXException{
        VOResources rs = readResources("/application.xml");
        assertEquals( "ivo://cds.fr/applications/aladin",rs.getResources().get(0).getIdentifier());
    }
     
    @Test
    public void testReadConeSearch() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/conesearch.xml");
        assertEquals("ivo://adil.ncsa/vocone", rs.getIdentifier());
    }
    /**
     * @param r
     * @return
     * @throws SAXException 
     * @throws IOException 
     * @throws JAXBException 
     */
    private Resource readResource(String r) throws JAXBException, IOException, SAXException {
            final InputStream res = getClass()
                    .getResourceAsStream(r);
           assertNotNull( res,"resource not found "+r);
           System.out.println(r+" "+ LocalDateTime.now());
        return IvoaJAXBUtils.unmarshall(new InputStreamReader(res),Resource.class, true);
        
    }

    @Test
    public void testReadOrganization() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/organization.xml");
        assertEquals( "ivo://ivoa.net/IVOA", rs.getIdentifier());
    }
    @Test
    public void testReadSia() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/sia.xml");
        assertEquals(  "ivo://adil.ncsa/sia", rs.getIdentifier());
    }
    @Test
    public void testReadStc() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/siaStc.xml");
        assertEquals( "ivo://nasa.heasarc/swiftmastr", rs.getIdentifier());
    }
    public void testReadStd() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/siastd.xml");
        assertEquals( "ivo://ivoa.net/std/SIA", rs.getIdentifier());
    }
    @Test
    public void testReadStd2() throws JAXBException, IOException, SAXException{
        Resource rs = readResource("/vospacestd.xml");
        assertEquals( "ivo://ivoa.net/vospace/core", rs.getIdentifier());
    }
      
    @Test
    public void testReadVOSITablesm() throws JAXBException, IOException, SAXException{
        Tableset rs = readVosiTableSet("/vosi-tables-min.xml");
        assertEquals(4, rs.getSchemas().get(1).getTables().size(),"number of tables in second schema" );
    }
    /**
     * @param string
     * @return
     * @throws SAXException 
     * @throws IOException 
     * @throws JAXBException 
     */
    private Tableset readVosiTableSet(String r) throws JAXBException, IOException, SAXException {
            final InputStream res = getClass()
                    .getResourceAsStream(r);
           assertNotNull( res,"resource not found "+r);
           System.out.println(r+" "+ LocalDateTime.now());
        return IvoaJAXBUtils.unmarshall(new InputStreamReader(res),Tableset.class, true);
       
    }

    @Test
    public void testReadVOSITablesf() throws JAXBException, IOException, SAXException{
        Tableset rs = readVosiTableSet("/vosi-tables-full.xml");
        assertEquals( 2, rs.getSchemas().get(0).getTables().size(),"number of tables in first schema" );
    }
    @Test
    public void testReadVOSITablesRegTap() throws JAXBException, IOException, SAXException{
        Tableset rs = readVosiTableSet("/vosi-tables-regtap.xml");
        assertEquals( 20, rs.getSchemas().get(0).getTables().size(),"number of tables in regtap schema" );
    }

//    @Test
//    public void testWriteToDB() throws PreexistingEntityException, Exception{
//        // Unmarshall the file into a content object
//        VOResources vr =  (VOResources) um.unmarshal(getClass()
//                .getResourceAsStream("/VOResource.xml"));
//        System.out.println(vr.getResources().get(0).getIdentifier());
//        ResourceJpaController jpacont = new ResourceJpaController();
//        for (Resource res : vr.getResources()) {
//            jpacont.create(res);
//        }
//
//       
//    }
}

