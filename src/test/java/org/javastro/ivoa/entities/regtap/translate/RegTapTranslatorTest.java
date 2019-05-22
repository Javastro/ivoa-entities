/*
 * Created on 17 May 2019 
 * Copyright 2019 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.regtap.translate;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.javastro.ivoa.entities.jaxb.IvoaJAXBUtils;
import org.javastro.ivoa.entities.regtap.BaseTestPersistence;
import org.javastro.ivoa.entities.resource.Resource;
import org.javastro.ivoa.entities.resource.registry.iface.VOResources;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Tests the regtap translator.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 17 May 2019
 */
public class RegTapTranslatorTest extends BaseTestPersistence {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    private VOResources res;
    private RegTapTranslator trans;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        
      res = IvoaJAXBUtils.unmarshall(RegTapTranslator.class.getResourceAsStream("/VOResource.xml"), VOResources.class);
      trans = new RegTapTranslator();
    }

    @Test
    public void test() throws JAXBException, IOException, SAXException, ParserConfigurationException, TransformerException {
           org.javastro.ivoa.entities.regtap.RegTAP rt = trans.translate(res);
           assertNotNull(rt);
        
    }

}


