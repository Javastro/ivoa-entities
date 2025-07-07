/*
 * Created on 3 Feb 2025 
 * Copyright 2025 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.resource.registry.oaipmh;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.javastro.ivoa.entities.IvoaJAXBContextFactory;
import org.javastro.ivoa.entities.IvoaJAXBUtils;
import org.javastro.ivoa.entities.resource.Resource;
import org.javastro.ivoa.entities.resource.dataservice.CatalogService;
import org.javastro.ivoa.entities.resource.registry.iface.ResourceInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

/**
 *  .
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 3 Feb 2025
 */
class OAIPMHTest {

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

    @Test
    void test() throws JAXBException, IOException, SAXException {
        OAIPMH resp = new OAIPMH();
        ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
        resp.setResponseDate(now);
        RequestType rt = RequestType.builder().withMetadataPrefix("ivo_resouce").withIdentifier("ivo://adil.ncsa/sia").withMetadataPrefix("ivo_resource").withVerb(VerbType.LIST_RECORDS).build();
        resp.setRequest(rt);
        InputStream s = this.getClass().getResourceAsStream("/sia.xml");
        Resource r = IvoaJAXBUtils.unmarshall(new InputStreamReader(s), CatalogService.class);
        ResourceInstance ri = new ResourceInstance(r);
        MetadataType meta = new MetadataType(ri);
        RecordType record = RecordType.builder().withMetadata(meta).build();
        ListRecordsType records =ListRecordsType.builder().addRecords(record).build();
        resp.setListRecords(records );
        
         Marshaller m = IvoaJAXBContextFactory.newInstance().createMarshaller();
         m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
         StringWriter sw = new StringWriter();
         m.marshal(resp, sw);
         System.out.println(sw.toString());
        
    }

}


