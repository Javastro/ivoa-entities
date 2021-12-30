/*
 * Created on 18 Aug 2021 
 * Copyright 2021 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.jaxb;

import static org.junit.jupiter.api.Assertions.*;

import org.javastro.ivoa.entities.resource.Resource;
import org.javastro.ivoa.jaxb.JaxbAnnotationMeta;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *  .
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 18 Aug 2021
 */
class JaxbAnnotationMetaTest {

    private static JaxbAnnotationMeta<Resource> meta;

    /**
     * @throws java.lang.Exception
     */
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        
        meta = JaxbAnnotationMeta.of(Resource.class);
    }

    /**
     * Test method for {@link org.javastro.ivoa.jaxb.JaxbAnnotationMeta#getJavaType()}.
     */
    @Test
    void testGetJavaType() {
        assertEquals(Resource.class, meta.getJavaType());
    }

    /**
     * Test method for {@link org.javastro.ivoa.jaxb.JaxbAnnotationMeta#getNamespace()}.
     */
    @Test
    void testGetNamespace() {
        assertEquals("http://www.ivoa.net/xml/VOResource/v1.0", meta.getNamespace());
    }

    /**
     * Test method for {@link org.javastro.ivoa.jaxb.JaxbAnnotationMeta#getName()}.
     */
    @Test
    void testGetName() {
        assertEquals("Resource",meta.getName());
    }

}


