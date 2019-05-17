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
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import javax.xml.XMLConstants;
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
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.javastro.ivoa.schema.Namespaces;
import org.javastro.ivoa.schema.SchemaMap;

import org.javastro.ivoa.entities.resource.Resource;
import org.javastro.ivoa.entities.resource.registry.iface.VOResources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
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
 * @version $Name: not supported by cvs2svn $
 * @since VOTech Stage 7
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
        

        LSResourceResolver resourceResolver = new LSResourceResolver() {

            public LSInput resolveResource(String type, String namespaceURI,
                    String publicId, String systemId, String baseURI) {
                System.out.println("schema resolver ns=" + namespaceURI + " sustemid="+systemId+ " base=" + baseURI+" type="+type);
                return new LSInput() {
                    
                    @Override
                    public void setSystemId(String systemId) {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException(
                                "Type1558106439675.setSystemId() not implemented");
                        
                    }
                    
                    @Override
                    public void setStringData(String stringData) {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException(
                                "Type1558106439675.setStringData() not implemented");
                        
                    }
                    
                    @Override
                    public void setPublicId(String publicId) {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException(
                                "Type1558106439675.setPublicId() not implemented");
                        
                    }
                    
                    @Override
                    public void setEncoding(String encoding) {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException(
                                "Type1558106439675.setEncoding() not implemented");
                        
                    }
                    
                    @Override
                    public void setCharacterStream(Reader characterStream) {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException(
                                "Type1558106439675.setCharacterStream() not implemented");
                        
                    }
                    
                    @Override
                    public void setCertifiedText(boolean certifiedText) {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException(
                                "Type1558106439675.setCertifiedText() not implemented");
                        
                    }
                    
                    @Override
                    public void setByteStream(InputStream byteStream) {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException(
                                "Type1558106439675.setByteStream() not implemented");
                        
                    }
                    
                    @Override
                    public void setBaseURI(String baseURI) {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException(
                                "Type1558106439675.setBaseURI() not implemented");
                        
                    }
                    
                    @Override
                    public String getSystemId() {
                        return systemId;
                        
                    }
                    
                    @Override
                    public String getStringData() {
                        return null;
                        
                    }
                    
                    @Override
                    public String getPublicId() {
                        return publicId;
                        
                    }
                    
                    @Override
                    public String getEncoding() {
                        return "UTF-8";//IMPL can we be sure?
                        
                    }
                    
                    @Override
                    public Reader getCharacterStream() {
                        return null;
                        
                    }
                    
                    @Override
                    public boolean getCertifiedText() {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException(
                                "Type1558106439675.getCertifiedText() not implemented");
                        
                    }
                    
                    @Override
                    public InputStream getByteStream() {
                        try {
                            return SchemaMap.getSchemaURL(namespaceURI).openStream();
                        } catch (IOException e) {
                            logger.error("problem with schema for namespace="+namespaceURI, e);
                            return null;
                        }
                        
                    }
                    
                    @Override
                    public String getBaseURI() {
                     return baseURI;
                        
                    }
                };
            }

        };
        sf.setResourceResolver(resourceResolver);
  
    }

    private IvoaJAXBUtils() {
        // stop instantiation
    }

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
            schema = findSchema(Namespaces.RI.getNamespace());
        } catch (IOException | SAXException e) {
            logger.warn("error finding schema to validate", e);
        }
        return IvoaJAXBUtils.marshall(new JAXBElement(new QName(
                Namespaces.RI.getNamespace(), "VOResources"), VOResources.class,
                desc), IvoaJAXBUtils.identityTransformer, schema);
    }


    /**
     * @param element
     * @param stylesheet
     * @param schema
     *            if non-null then this schema is used to validate
     * @return
     * @throws JAXBException
     * @throws TransformerException
     * @TODO - create a family of functions that marshall to other outputs...
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

        return unmarshall(clazz, validate, s);

    }

    private static <T> T unmarshall(Class<T> clazz, boolean validate,
            Source s) throws JAXBException, ValidationException {
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
        XmlSchema ann = clazz.getPackage().getAnnotation(
                javax.xml.bind.annotation.XmlSchema.class);      
  
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
        Schema schema = sf.newSchema(SchemaMap.getRegistrySchema());
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
