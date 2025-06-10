/*
 * Created on 13 Dec 2021 
 * Copyright 2021 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.jaxb;

import java.io.IOException;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;

import org.javastro.ivoa.entities.IvoaJAXBUtils;
import org.javastro.ivoa.entities.resource.registry.iface.VOResources;
import org.javastro.ivoa.schema.Namespaces;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import org.javastro.ivoa.entities.resource.Resource;

/**
 *  .
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 13 Dec 2021
 */
public class Utils {
    /** logger for this class */
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(Utils.class);
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Document marshall(Resource desc)
            throws JAXBException,
            TransformerException {
        Schema schema = null;// do not attempt to validate at the moment, as
        // there is often not a single schema that suffices
        // for Resources...(e.g. multiple capabilities)
        // findSchema(desc.getClass());
        return IvoaJAXBUtils.marshall(new JAXBElement(new QName(
                Namespaces.VR.getNamespace(), "resource"), Resource.class,
                desc), IvoaJAXBUtils.identityTransformer, schema);
    }

  

    public static Document marshall(VOResources desc)
            throws JAXBException,
            TransformerException {
        Schema schema = null;
        try {
            schema = IvoaJAXBUtils.findSchema(Namespaces.RI.getNamespace());
        } catch (IOException | SAXException e) {
            logger.warn("error finding schema to validate", e);
        }
        return IvoaJAXBUtils.marshall(new JAXBElement(new QName(
                Namespaces.RI.getNamespace(), "VOResources"), VOResources.class,
                desc), IvoaJAXBUtils.identityTransformer, schema);
    }



}


