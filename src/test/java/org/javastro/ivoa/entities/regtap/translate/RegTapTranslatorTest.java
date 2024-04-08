/*
 * Created on 10 Aug 2023 
 * Copyright 2023 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.regtap.translate;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.javastro.ivoa.entities.IvoaJAXBUtils;
import org.javastro.ivoa.entities.resource.registry.iface.VOResources;
import org.javastro.ivoa.jaxb.SchemaNamer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

/**
 *  .
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 10 Aug 2023
 */
class RegTapTranslatorTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDown() throws Exception {
    }

    /**
     * Test method for {@link org.javastro.ivoa.entities.regtap.translate.RegTapTranslator#translate(org.javastro.ivoa.entities.resource.registry.iface.VOResources)}.
     * @throws SAXException 
     * @throws IOException 
     * @throws JAXBException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     */
    @Test
    void testTranslate() throws JAXBException, IOException, SAXException, ParserConfigurationException, TransformerException {
             final InputStream resourceStream = RegTapTranslator.class.getResourceAsStream("/VOResource.xml");
      
      JAXBContext.newInstance("org.javastro.ivoa.entities.regtap").generateSchema(new SchemaNamer(Map.of("http://www.ivoa.net/xml/RegTAP/v1.0","RegTAP.xsd")));  
      
      VOResources res = IvoaJAXBUtils.unmarshall(resourceStream, org.javastro.ivoa.entities.resource.registry.iface.VOResources.class);
      assertNotNull(res);
      RegTapTranslator trans = new RegTapTranslator();
      assertNotNull(trans);
      org.javastro.ivoa.entities.regtap.RegTAP rt = trans.translate(res);
    }

}


