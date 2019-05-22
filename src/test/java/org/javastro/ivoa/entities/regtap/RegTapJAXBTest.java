/*
 * Created on 20 May 2019 
 * Copyright 2019 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.regtap;

import static org.junit.Assert.*;

import java.io.PrintWriter;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.javastro.ivoa.entities.jaxb.IvoaJAXBUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 *  .
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 20 May 2019
 */
public class RegTapJAXBTest {

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

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws JAXBException, TransformerException {
       RegTAP rt = new RegTAP();
       Resource r = new Resource("ivo://test", "restype", new java.util.Date(), new java.util.Date(), "status", "shortname", "restitle", "descriotion", "http://www.jb.man.ac.uk");
       rt.resources.add(r);
       Document doc = IvoaJAXBUtils.marshall(rt);
       IvoaJAXBUtils.printXML(doc, new PrintWriter(System.out));
    }

}


