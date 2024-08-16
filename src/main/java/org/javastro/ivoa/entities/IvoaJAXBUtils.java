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

package org.javastro.ivoa.entities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import javax.xml.XMLConstants;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationException;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.util.ValidationEventCollector;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.javastro.ivoa.schema.Namespaces;
import org.javastro.ivoa.schema.SchemaMap;
import org.javastro.ivoa.schema.XMLValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;


/**
 * Some utilities to make working with JAXB marshalling easier. Sets up all of
 * the validation and namespaces etc.
 * 
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 10 Jun 2008
 * @since VOTech Stage 7
 * TODO need to rationalize with @see org.javastro.ivoa.entities.jaxb.Utils
 */
public class IvoaJAXBUtils {

    //    public static Transformer regStylesheet
    public static Transformer identityTransformer;
    /** logger for this class */
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(IvoaJAXBUtils.class);

    private static JAXBContext contextFactory;
    private static final SAXParserFactory saxParserFactory;
    private static javax.xml.validation.SchemaFactory sf = SchemaFactory
            .newInstance(Namespaces.XSD.getNamespace());

    private static javax.xml.parsers.DocumentBuilderFactory dbf;

    static {
        saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);
        try {
            //stop external entities being loaded
            // see https://github.com/OWASP/CheatSheetSeries/blob/master/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.md

            //            saxParserFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            //            saxParserFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            //            saxParserFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            sf.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "file,jar:file");
            sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file,jar:file");
        } catch (SAXNotRecognizedException | SAXNotSupportedException //| ParserConfigurationException 
                e1) {
            logger.error("problem setting up the schema factory to use only local schema",e1);
            e1.printStackTrace();
        }


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


        dbf = DocumentBuilderFactory
                .newInstance();
        dbf.setNamespaceAware(true);

//IMPL not needed now using xmlresolver?
//        LSResourceResolver resourceResolver = new XMLValidator.SchemaResolver();
//        sf.setResourceResolver(resourceResolver);

    }

    private IvoaJAXBUtils() {
        // stop instantiation
    }

    /**
     * @param element the element to be marshalled 
     * @param stylesheet a stylesheet to transform the element - may be identity
     * @param schema if non-null then this schema is used to validate
     * @return
     * @throws JAXBException
     * @throws TransformerException
     */
    static public Document marshall(JAXBElement<?> element,
            Transformer stylesheet, Schema schema)
                    throws  JAXBException,
                    TransformerException {

        // IMPL this is not particularly efficient, but does not matter as
        // not
        // used very often...

        // IMPL is DOM best to use here?
        // IMPL needs to be overridden to remove some things
        Document doc;
        try {
            doc = dbf.newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            throw new JAXBException("problem setting up parser", e);
        }
        Marshaller m = IvoaJAXBContextFactory.newInstance().createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

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



    public static <T> T unmarshall(InputStream inputStream, Class<T> class1)
            throws JAXBException, IOException, SAXException {
        return unmarshall(  new InputStreamReader(inputStream), class1);
    }

    public static <T> T unmarshall(Reader rd, Class<T> clazz)
            throws JAXBException, IOException, SAXException {
        return unmarshall(rd, clazz, true);
    }

    public static <T> T unmarshall(Node doc, Class<T> clazz, boolean validate)
            throws JAXBException, IOException, SAXException {
        return unmarshall( new DOMSource(doc), clazz, validate);
    }

    public static <T> T unmarshall(Reader r, Class<T> clazz, boolean validate)
            throws JAXBException, IOException, SAXException {
        XMLReader saxreader;
        try {
            final SAXParser saxParser = saxParserFactory.newSAXParser();
            saxreader = saxParser.getXMLReader();
        } catch (ParserConfigurationException e) {
            // should not happen hopefully
            throw new IOException("problem with configuring XML parser", e);
        }
        saxreader.setEntityResolver(new IVOAEntityResolver());
        SAXSource s = new SAXSource(saxreader, new InputSource(r));

        return unmarshall(s, clazz, validate);

    }


    private static <T> T unmarshall(Source s, Class<T> clazz,
            boolean validate) throws JAXBException, ValidationException {
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
        jakarta.xml.bind.util.ValidationEventCollector validationEventCollector = new jakarta.xml.bind.util.ValidationEventCollector();
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
        XmlSchema ann = clazz.getPackage().getAnnotation(
                jakarta.xml.bind.annotation.XmlSchema.class);      

        try {
            String namespace = ann.namespace();
            logger.debug("schema for class {} {}",clazz.getName(), namespace);
            return findSchema(namespace);
        } catch (Throwable e) {
            logger.warn("unable to find schema - validation will not occur", e);
            return null;
        }
    }

    public static Schema findSchema(String namespace)
            throws IOException, SAXException {
        //always load multiple schema
        Schema schema = sf.newSchema(SchemaMap.getRegistrySchemaAsSources());
        return schema;
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

    static class IVOAEntityResolver implements EntityResolver{

        /* (non-Javadoc)
         * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
         */
        @Override
        public InputSource resolveEntity(String publicId, String systemId)
                throws SAXException, IOException {
            logger.info("entity resolver "+publicId+" "+systemId);
            URL schemaURL;
            if( (schemaURL = SchemaMap.getSchemaURL(systemId)) == null) {
                logger.warn("cannot find schema for {}",systemId);
                schemaURL = new URL(systemId); // hope that systemID is a valid URL if not a namespace
            }
            return new InputSource(schemaURL.openStream());
        }

    }




}
