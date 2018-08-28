/*
 * $Id: JaxBBaseTest.java,v 1.2 2011-09-13 13:43:32 pah Exp $
 * 
 * Created on 24 Aug 2011 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2011 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.jaxb;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.namespace.QName;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.javastro.ivoa.entities.resource.Resource;
import org.javastro.ivoa.entities.resource.dataservice.Tableset;
import org.javastro.ivoa.entities.resource.registry.iface.VOResources;
import org.javastro.ivoa.schema.Namespaces;
import org.javastro.ivoa.schema.SchemaMap;
import org.junit.Before;
import org.junit.BeforeClass;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import junit.framework.AssertionFailedError;

/**
 *  .
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 24 Aug 2011
 * @version $Revision: 1.2 $ $date$
 */
public class JaxBBaseTest {
    protected static JAXBContext jc;
    protected static Unmarshaller um;
    protected static Marshaller m;
    protected static XMLReader reader;
    protected static Validator validator;
    
    static final String JAXP_SCHEMA_LANGUAGE =
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA =
        "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE =
        "http://java.sun.com/xml/jaxp/properties/schemaSource";
    private static javax.xml.validation.SchemaFactory sf;
    private static Schema schema;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        jc = IvoaJAXBContextFactory.newInstance();
        System.out.println(jc.toString());
        sf = SchemaFactory
                .newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);

        
        
        List<Source> schemas = new ArrayList<Source>();
// list of namespaces that we want to include in schema checking
        String namespaces[] = {
                 Namespaces.RI.getNamespace()
                ,Namespaces.VR.getNamespace()
                ,Namespaces.REG.getNamespace()
                ,Namespaces.VA.getNamespace()
                ,Namespaces.CS.getNamespace()
                ,Namespaces.VS.getNamespace()
                ,Namespaces.SIA.getNamespace()
                ,Namespaces.VSTD.getNamespace()
                ,Namespaces.VOSI_TAB.getNamespace()
        };
        
        for (String namespace : namespaces) {
            URL url = SchemaMap.getSchemaURL(namespace);
            schemas.add(new StreamSource(url.openStream(), url
                    .toExternalForm()));
        }
        
        schema = sf.newSchema(schemas.toArray(new Source[0]));

    }

    @Before
    public void setUp() throws Exception {
        um = jc.createUnmarshaller();
        um.setSchema(null);
        um.setEventHandler(new DefaultValidationEventHandler());
        // as an alternative to the jaxb standard mechanism for validation, the following can be done  using the unmarshal(Source) API.
        // It does not seem to work properly with the relative links between the schema however - not sure why
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setValidating(true);
        spf.setSchema(schema);
        SAXParser saxParser = spf.newSAXParser();
        boolean valid = saxParser.isValidating();
//        saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
        AssertDefaultHandler handler = new AssertDefaultHandler();
        validator = schema.newValidator();
        validator.setErrorHandler(handler);
        
        reader = saxParser.getXMLReader();
       
        EntityResolver resolver = new IVOAEntityResolver();
        reader.setEntityResolver(resolver);
//        reader.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource",mkSchemaLocationString(SchemaMap.ALL));
        
        reader.setContentHandler(handler);
        reader.setErrorHandler(handler);

    
        m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
   }
    
    protected VOResources readResources(String resource) throws JAXBException, IOException,
            SAXException {
                   
             //   
                    validator.validate(new StreamSource(getClass().getResourceAsStream(resource)));
                    ((AssertDefaultHandler)validator.getErrorHandler()).checkError();
                    VOResources vr =  (VOResources)  um.unmarshal(getClass().getResourceAsStream(resource));
                    System.out.println(vr.getResources().get(0).getIdentifier());
            //
            // 
                    m.marshal(new JAXBElement(new QName(
                            "http://www.ivoa.net/xml/RegistryInterface/v1.0", "VOResources"),
                            VOResources.class, vr), System.out);
                    return vr;
                    
                }
    protected Resource readResource(String resource) throws JAXBException, IOException,
    SAXException {
         validator.validate(new StreamSource(getClass().getResourceAsStream(resource)));
         ((AssertDefaultHandler)validator.getErrorHandler()).checkError();
         Object ob = um.unmarshal(getClass().getResourceAsStream(resource));
         Resource vr =  ((JAXBElement<Resource>)ob).getValue();
         System.out.println(vr.getIdentifier());
    //
    // 
            m.marshal(new JAXBElement(new QName(
                    "http://www.ivoa.net/xml/RegistryInterface/v1.0", "Resource"),
                    Resource.class, vr), System.out);
            return vr;
            
        }
    
    protected Tableset readVosiTableSet(String resource) throws JAXBException, IOException,
    SAXException {
         validator.validate(new StreamSource(getClass().getResourceAsStream(resource)));
         ((AssertDefaultHandler)validator.getErrorHandler()).checkError();
         Object ob = um.unmarshal(getClass().getResourceAsStream(resource));
         Tableset vr =  ((Tableset)ob);
         System.out.println(vr.getSchemas().size());
    //
    // 
            m.marshal(new JAXBElement(new QName(
                    "http://www.ivoa.net/xml/RegistryInterface/v1.0", "Tableset"),
                    Tableset.class, vr), System.out);
            return vr;
            
        }


    /** collapse the map into a space-separated string, suitable for passing to the parser */
    @SuppressWarnings("rawtypes")
    protected static String mkSchemaLocationString(Map schemaLocations) {
        StringBuffer result = new StringBuffer();
        for (Iterator i = schemaLocations.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry e = (Map.Entry)i.next();
            result.append(e.getKey().toString());
            result.append(' ');
            result.append(e.getValue().toString());
            result.append(' ');
        }
        return result.toString();
    }
    /** handler passed to schema-validation parses - logs all errors, throws assertion failed if errors seen by end */
    static class AssertDefaultHandler extends DefaultHandler {
      private StringBuffer buff = new StringBuffer();
      
      public String getMessages() {
        return buff.toString();
      }
            
      boolean sawError = false;
      
      public void endDocument() throws SAXException {
        checkError();
      }



    public void checkError() throws AssertionFailedError {
        if (sawError) {
          throw new AssertionFailedError("The document is invalid with respect to its schema" 
                                         + this.getMessages());
        }
    }
      
     

      public void error(SAXParseException e) throws SAXException {
        sawError = true;
        System.err.println("Error:" + this.getMessage(e));
        this.buff.append("\n").append(this.getMessage(e));
      }
      
      public void fatalError(SAXParseException e) throws SAXException {
        sawError = true;
        System.err.println("Fatal: " + this.getMessage(e));
        this.buff.append("\n").append(this.getMessage(e));            
      }
      
      public void warning(SAXParseException e) throws SAXException {
        System.err.println("Warn: " + this.getMessage(e));
        this.buff.append("\n").append(this.getMessage(e));           
      }
      
      private String getMessage(SAXParseException e) {
        return e.getMessage() +
               " Found at line " +
               e.getLineNumber() +
               ", column " +
               e.getColumnNumber() +
               " in " +
               e.getSystemId();
      }
    }
    
    static class IVOAEntityResolver implements EntityResolver{

        /* (non-Javadoc)
         * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
         */
        @Override
        public InputSource resolveEntity(String publicId, String systemId)
                throws SAXException, IOException {
            System.err.println("entity resolver "+publicId+" "+systemId);
            URL schemaURL;
            if( (schemaURL = SchemaMap.getSchemaURL(systemId)) == null) {
                schemaURL = new URL(systemId); // hope that systemID is a valid URL if not a namespace
            }
            return new InputSource(schemaURL.openStream());
        }
        
    }


}


/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1.2.1  2011/09/01 09:32:33  pah
 * test for reading xml
 *
 */
