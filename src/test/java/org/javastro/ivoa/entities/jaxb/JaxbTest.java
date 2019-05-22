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

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.javastro.ivoa.schema.Namespaces;
import org.javastro.ivoa.schema.SchemaMap;
import org.javastro.ivoa.entities.jaxb.IvoaJAXBContextFactory;
import org.javastro.ivoa.entities.jpa.ResourceJpaController;
import org.javastro.ivoa.entities.jpa.exceptions.PreexistingEntityException;
import org.javastro.ivoa.entities.resource.Resource;
import org.javastro.ivoa.entities.resource.registry.iface.VOResources;
import org.javastro.ivoa.entities.vosi.tables.Tableset;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

public class JaxbTest {


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testReadXML() throws JAXBException, IOException, SAXException, TransformerException{
        // Unmarshall the file into a content object
        VOResources vr =   IvoaJAXBUtils.unmarshall(new InputStreamReader(getClass()
                .getResourceAsStream("/VOResource.xml")),VOResources.class, true);
        System.out.println(vr.getResources().get(0).getIdentifier());

        Document doc = IvoaJAXBUtils.marshall(vr);
        IvoaJAXBUtils.printXML(doc, new PrintWriter(System.out));
 
       
        
    }
    
    
        @Test
    public void testReadRegistry() throws JAXBException, IOException, SAXException{
        VOResources rs = readResources("/registry.xml");
        assertEquals("identifier", "ivo://test/registry",rs.getResources().get(0).getIdentifier());
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
           assertNotNull("resource not found "+r, res);
        return IvoaJAXBUtils.unmarshall(new InputStreamReader(res),VOResources.class, true);
            
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
    /**
     * @param string
     * @return
     * @throws SAXException 
     * @throws IOException 
     * @throws JAXBException 
     */
    private Resource readResource(String r) throws JAXBException, IOException, SAXException {
            final InputStream res = getClass()
                    .getResourceAsStream(r);
           assertNotNull("resource not found "+r, res);
        return IvoaJAXBUtils.unmarshall(new InputStreamReader(res),Resource.class, true);
        
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
      
    @Test
    public void testReadVOSITablesm() throws JAXBException, IOException, SAXException{
        Tableset rs = readVosiTableSet("/vosi-tables-min.xml");
        assertEquals("number of tables in second schema" , 4, rs.getSchemas().get(1).getTables().size());
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
           assertNotNull("resource not found "+r, res);
        return IvoaJAXBUtils.unmarshall(new InputStreamReader(res),Tableset.class, true);
       
    }

    @Test
    public void testReadVOSITablesf() throws JAXBException, IOException, SAXException{
        Tableset rs = readVosiTableSet("/vosi-tables-full.xml");
        assertEquals("number of tables in first schema" , 2, rs.getSchemas().get(0).getTables().size());
    }
    @Test
    public void testReadVOSITablesRegTap() throws JAXBException, IOException, SAXException{
        Tableset rs = readVosiTableSet("/vosi-tables-regtap.xml");
        assertEquals("number of tables in regtap schema" , 20, rs.getSchemas().get(0).getTables().size());
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

