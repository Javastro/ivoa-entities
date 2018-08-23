/*
 * $Id: IvoaJAXBUtils.java,v 1.2 2011-09-13 13:43:32 pah Exp $
 * 
 * Created on 13 May 2008 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2008 Astrogrid. All rights reserved.
 *
 * This software is published under the terms of the Astrogrid 
 * Software License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */

package org.javastro.ivoa.entities.jaxb;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationException;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.javastro.ivoa.schema.Namespaces;
import org.javastro.ivoa.schema.SchemaMap;

import org.javastro.ivoa.entities.resource.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


/**
 * Some utilities to make working with JAXB marshalling easier. Sets up all of
 * the validation and namespaces etc.
 * 
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 10 Jun 2008
 * @version $Name: not supported by cvs2svn $
 * @since VOTech Stage 7
 */
public class IvoaJAXBUtils {

//    public static Transformer regStylesheet;
    public static Transformer identityTransformer;
   /** logger for this class */
private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
        .getLogger(IvoaJAXBUtils.class);

    private static JAXBContext contextFactory;

    static {
        InputStream xslFileStream;
        TransformerFactory xformFactory = TransformerFactory.newInstance();
//        xslFileStream = WidarJAXBUtils.class
//                .getResourceAsStream("/RegistryFixup.xsl");
//        assert xslFileStream != null : "could not find the RegistryFixup.xsl";
//        Source xsl = new StreamSource(new BufferedReader(new InputStreamReader(
//                xslFileStream)));
        try {
//            regStylesheet = xformFactory.newTransformer(xsl);
            identityTransformer = xformFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            logger.error("problem setting up default registry stylesheet", e);
        }
        try {
            contextFactory = IvoaJAXBContextFactory.newInstance();
            if (logger.isDebugEnabled())
                logger.info(contextFactory.toString());
        } catch (JAXBException e) {
            logger.error("problem setting up the JAXB context", e);
        }

    }

    private IvoaJAXBUtils() {
        // stop instantiation
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Document marshall(Resource desc)
            throws ParserConfigurationException, JAXBException,
            TransformerException {
        Schema schema = null;// do not attempt to validate at the moment, as
                             // there is often not a single schema that suffices
                             // for Resources...(e.g. multiple capabilities)
                             // findSchema(desc.getClass());
        return IvoaJAXBUtils.marshall(new JAXBElement(new QName(
                Namespaces.VR.getNamespace(), "component"), Resource.class,
                desc), IvoaJAXBUtils.identityTransformer, schema);
    }

    /**
     * @param element
     * @param stylesheet
     * @param schema
     *            if non-null then this schema is used to validate
     * @return
     * @throws ParserConfigurationException
     * @throws JAXBException
     * @throws TransformerException
     * @TODO - create a family of functions that marshall to other outputs...
     */
    static public Document marshall(JAXBElement<?> element,
            Transformer stylesheet, Schema schema)
            throws ParserConfigurationException, JAXBException,
            TransformerException {

        // IMPL this is not particularly efficient, but does not matter as
        // not
        // used very often...
        javax.xml.parsers.DocumentBuilderFactory dbf = DocumentBuilderFactory
                .newInstance();
        dbf.setNamespaceAware(true);

        // IMPL is DOM best to use here?
        // IMPL needs to be overridden to remove some things
        Document doc = dbf.newDocumentBuilder().newDocument();
        Marshaller m = IvoaJAXBContextFactory.newInstance().createMarshaller();
        // m.setProperty("com.sun.xml.bind.namespacePrefixMapper",
        // new NamespacePrefixMapperImpl());
        if (schema != null) {
            m.setSchema(schema);
        }
        ValidationEventCollector handler = new ValidationEventCollector();
        m.setEventHandler(handler);

        m.marshal(element, doc);

        if (handler.hasEvents()) {
            logger.error("invalid - " + element);
            for (int i = 0; i < handler.getEvents().length; i++) {
                ValidationEvent array_element = handler.getEvents()[i];
                logger.error("validation error - " + array_element.toString()
                        );
            }
        }
        // xsl transfrom

        Source request = new DOMSource(doc);
        DOMResult response = new DOMResult();
        stylesheet.transform(request, response);

        return (Document) response.getNode();

    }

    public static <T> T unmarshall(InputStream is, Class<T> clazz)
            throws JAXBException, IOException, SAXException {
        StreamSource s = new StreamSource(is);
        T umObj = unmarshall(s, clazz, true);
        return umObj;
    }

    public static <T> T unmarshall(InputStream is, Class<T> clazz,
            boolean validate) throws JAXBException, IOException, SAXException {
        StreamSource s = new StreamSource(is);
        T umObj = unmarshall(s, clazz, validate);
        return umObj;
    }

    public static <T> T unmarshall(Document doc, Class<T> clazz)
            throws JAXBException, IOException, SAXException {
        return unmarshall(new DOMSource(doc), clazz, true);
    }

    public static <T> T unmarshall(Reader rd, Class<T> clazz)
            throws JAXBException, IOException, SAXException {
        return unmarshall(new StreamSource(rd), clazz, true);
    }

    public static <T> T unmarshall(Reader rd, Class<T> clazz, boolean validate)
            throws JAXBException, IOException, SAXException {
        return unmarshall(new StreamSource(rd), clazz, validate);
    }

    private static <T> T unmarshall(Source s, Class<T> clazz, boolean validate)
            throws JAXBException, IOException, SAXException {
        T retval;
        logger.debug("unmarshalling to " + clazz.getSimpleName());
        Unmarshaller um = contextFactory.createUnmarshaller();
        if (validate) {
            logger.debug("finding schema to validate");
            Schema schema = findSchema(clazz);
            um.setSchema(schema);
        } else {
            um.setSchema(null);
        }
        javax.xml.bind.util.ValidationEventCollector validationEventCollector = new javax.xml.bind.util.ValidationEventCollector();
        um.setEventHandler(validationEventCollector);

        JAXBElement<T> el = um.unmarshal(s, clazz);
        retval = el.getValue();
        if (validationEventCollector.hasEvents()) {
            StringBuffer errmsg = new StringBuffer();
            for (ValidationEvent err : validationEventCollector.getEvents()) {
                errmsg.append(err.toString());
                errmsg.append("\n");
            }
            logger.error(errmsg.toString());
            throw new ValidationException("xml invalid for "
                    + errmsg.toString());
        }

        return retval;

    }

    public static <T> Schema findSchema(Class<T> clazz) {
        javax.xml.validation.SchemaFactory sf = SchemaFactory
                .newInstance(Namespaces.XSD.getNamespace());
        XmlSchema ann = clazz.getPackage().getAnnotation(
                javax.xml.bind.annotation.XmlSchema.class);
        try {
            String namespace = ann.namespace();
            URL url = SchemaMap.getSchemaURL(namespace);
            Source schemas = new StreamSource(url.openStream(),
                    url.toExternalForm());
            Schema schema = sf.newSchema(schemas);
            return schema;
        } catch (Throwable e) {
            logger.warn("unable to find schema - validation will not occur", e);
            return null;
        }
    }

    public static void printXML(Node doc, Writer sw) {
        try {
            // Set up the output transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            // Print the DOM node

            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
