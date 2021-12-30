/*
 * $Id$
 * 
 * Created on 21 Jan 2013 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2013 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.regtap.translate;

import static org.javastro.ivoa.entities.jaxb.Utils.marshall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.javastro.ivoa.entities.resource.registry.iface.VOResources;
import org.javastro.ivoa.jaxb.IvoaJAXBUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * Translate a VOResource to  RegTAP XML representation.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 21 Jan 2013
 * @version $Revision$ $date$
 */
public class RegTapTranslator {

    /** logger for this class */
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(RegTapTranslator.class);
    
    
    private InputStream xslFileStream;
    private TransformerFactory xformFactory = TransformerFactory.newInstance();


    public org.javastro.ivoa.entities.regtap.RegTAP translate (VOResources res) throws JAXBException, IOException, SAXException, ParserConfigurationException, TransformerException{
        
        xslFileStream = RegTapTranslator.class
                .getResourceAsStream("/convertToRegTap.xsl");
        assert xslFileStream != null : "could not find the RegistryFixup.xsl";
        Source xsl = new StreamSource(new BufferedReader(new InputStreamReader(
                xslFileStream)));
        Transformer rextapxform = xformFactory.newTransformer(xsl);

        
        
        Document doc = marshall(res);
        Source request = new DOMSource(doc);
        DOMResult response = new DOMResult();
        rextapxform.transform(request, response);
       
//        IvoaJAXBUtils.printXML(response.getNode(), new PrintWriter(System.out));
        return IvoaJAXBUtils.unmarshall((Document) response.getNode(),org.javastro.ivoa.entities.regtap.RegTAP.class,false);

       
    }
}


/*
 * $Log$
 */
