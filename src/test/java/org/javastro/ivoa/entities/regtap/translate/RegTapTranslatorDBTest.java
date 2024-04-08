/*
 * Created on 17 May 2019 
 * Copyright 2019 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.regtap.translate;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.javastro.ivoa.entities.IvoaJAXBUtils;
import org.javastro.ivoa.entities.regtap.BaseTestPersistence;
import org.javastro.ivoa.entities.regtap.ResourceJpaController;
import org.javastro.ivoa.entities.regtap.exceptions.PreexistingEntityException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the regtap translator.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 17 May 2019
 */
public class RegTapTranslatorDBTest extends BaseTestPersistence {

    private static ResourceJpaController rjc;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        rjc = new ResourceJpaController(emf);
        assertNotNull(rjc);
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    private org.javastro.ivoa.entities.resource.registry.iface.VOResources res;
    private RegTapTranslator trans;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        
      final InputStream resourceStream = RegTapTranslator.class.getResourceAsStream("/VOResource.xml");
      res = IvoaJAXBUtils.unmarshall(resourceStream, org.javastro.ivoa.entities.resource.registry.iface.VOResources.class);
      assertNotNull(res);
      trans = new RegTapTranslator();
      assertNotNull(trans);
    }

    @Test
    public void test() throws PreexistingEntityException, Exception {
           org.javastro.ivoa.entities.regtap.RegTAP rt = trans.translate(res);
           assertNotNull(rt);
           for (org.javastro.ivoa.entities.regtap.Resource r : rt.resources  ) {
               rjc.create(r);
            
        }
           
        
    }

}


