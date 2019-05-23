/*
 * $Id$
 * 
 * Created on 22 Jan 2013 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2013 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.regtap;

import static org.junit.Assert.*;

import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;

import org.javastro.ivoa.entities.jaxb.IvoaJAXBContextFactory;
import org.javastro.ivoa.entities.jaxb.IvoaJAXBUtils;
import org.javastro.ivoa.entities.regtap.exceptions.PreexistingEntityException;
import org.javastro.ivoa.schema.Namespaces;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Set of tests for basic JPA functionality of the RegTAP classes.  
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 22 Jan 2013
 * @version $Revision$ $date$
 */
public class RegTapJPATest extends BaseTestPersistence {

    /** IVOID.
     */
    private static final String IVOID = "ivo://test.org/testresource";
    private static JAXBContext jc;
    private static Unmarshaller um;
    private org.javastro.ivoa.entities.regtap.Resource vr;
    private static ResourceJpaController rjc;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
        jc = IvoaJAXBContextFactory.newInstance();
        System.out.println(jc.toString());
        um = jc.createUnmarshaller();
        System.out.println(jc.toString());

        Schema schema = IvoaJAXBUtils.findSchema(Namespaces.RI.getNamespace());
        um.setSchema(schema);
        um.setEventHandler(new DefaultValidationEventHandler());

        rjc = new ResourceJpaController(emf);
    
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
   }

    @After
    public void tearDown() throws Exception {
        rjc.destroy(IVOID);
    }
    
    
    @Test
    public void createtest() throws PreexistingEntityException, Exception {
        vr = new org.javastro.ivoa.entities.regtap.Resource();
        fillResource();


        Capability cap = new Capability();
        cap.addToResource(vr);
        cap.setName("capname");
        cap.setCapDescription("capdescription");
        cap.setStandardId("ivo://ivoa.std/std");
        
        Capability cap2 = new Capability();
        cap2.addToResource(vr);
        cap2.setName("capname2");
        cap2.setCapDescription("capdescription");
        cap2.setStandardId("ivo://ivoa.std/std");
        rjc.create(vr);
      
       
//        Interface intf = new Interface(vr, cap, (short)0, "ivo://test.org/intuse", "http://test.org/accessurl");
//        ijc.create(intf);
     }
  
    @Test
    public void addCapabilitytest() throws PreexistingEntityException, Exception{
        createtest();
        Capability cap2 = new Capability();
        cap2.addToResource(vr);
        cap2.setName("capname3");
        cap2.setCapDescription("capdescription");
        cap2.setStandardId("ivo://ivoa.std/std");
        rjc.edit(vr);
        
    }
    @Test
    public void addDetailToResourcetest() throws PreexistingEntityException, Exception{
        createtest();
        ResDetail det = new ResDetail();
        det.setDetailUtype("dutype");
        det.setDetailValue("detail");
        det.addToResource(vr);
        
        rjc.edit(vr);
        Resource res = rjc.findResource(IVOID);
        assertEquals("number of details",1, res.getResDetailList().size());
        assertNull("detail capindex for resource",  res.getResDetailList().get(0).getCapIndex());
        
    }


    @Test
    public void addResRoletest() throws PreexistingEntityException, Exception{
        createtest();
        ResRole det = new ResRole();
        det.setRoleName("ivo://test.org/editor");
        det.setRoleIvoid("ivo://test.org/somone");
        det.setRoleBaseRole("vor:resource.curation.contact");
        det.addToResource(vr);
        
        rjc.edit(vr);
        Resource res = rjc.findResource(IVOID);
        assertEquals("number of roles",1, res.getResRoleList().size());
        
    }
    @Test
    public void addSubjecttest() throws PreexistingEntityException, Exception{
        createtest();
        Subject det = new Subject("subject");
        det.addToResource(vr);
        
        rjc.edit(vr);
        Resource res = rjc.findResource(IVOID);
        assertEquals("number of subjects",1, res.getSubjectList().size());
        
    }
    @Test
    public void addAltIdtest() throws PreexistingEntityException, Exception{
        createtest();
        AltIdentifier det = new AltIdentifier("altId");
        det.addToResource(vr);
        
        rjc.edit(vr);
        Resource res = rjc.findResource(IVOID);
        assertEquals("number of alt ids",1, res.getAltIdentifier().size());
        
    }
   @Test
    public void addResRelationshiptest() throws PreexistingEntityException, Exception{
        createtest();
        Relationship det = new Relationship();
        det.setRelatedId("ivo://test.org/related");
        det.setRelationshipType("mirror-of");
        det.setRelatedName("fred");
        det.addToResource(vr);
        
        rjc.edit(vr);
        Resource res = rjc.findResource(IVOID);
        assertEquals("number of relationships",1, res.getRelationshipList().size());
        
    }

    
    @Test
    public void addValidationToResourcetest() throws PreexistingEntityException, Exception{
        createtest();
        Validation det = new Validation();
        det.setValidatedBy("ivo://test.org/validator");
        det.setLevel((short) 0);
        det.addToResource(vr);
        
        rjc.edit(vr);
        Resource res = rjc.findResource(IVOID);
        assertEquals("number of schema",1, res.getValidationList().size());
        assertNull("detail capindex for resource", res.getValidationList().get(0).getCapIndex());
        
    }

    @Test
    public void addValidationToCapabiltytest() throws PreexistingEntityException, Exception{
        createtest();
        Validation det = new Validation();
        det.setValidatedBy("ivo://test.org/validator");
        det.setLevel((short) 0);
        det.addToCapability(vr.getCapabilityList().get(0));
        
        rjc.edit(vr);
        Resource res = rjc.findResource(IVOID);
        assertEquals("number of validations",1, res.getValidationList().size());
        assertEquals("capindex",1, (int)res.getValidationList().get(0).getCapIndex());
       
    }

    @Test
    public void addSchemaTest() throws PreexistingEntityException, Exception{
        createtest();
        ResSchema schema = new ResSchema();
        schema.setName("schemaname");
        schema.setTitle("schematitle");
        schema.addToResource(vr);
        rjc.edit(vr);

        Resource res = rjc.findResource(IVOID);
        assertEquals("number of schema",1, res.getResSchemaList().size());
        vr=res; //for followon tests

    }

    
    @Test
    public void addTableTest() throws PreexistingEntityException, Exception{
        addSchemaTest();
        ResSchema schema = vr.getResSchemaList().get(0);
        ResTable table = new ResTable("name", "type");
        table.addToSchema(schema);
        rjc.edit(vr);

        Resource res = rjc.findResource(IVOID);
        assertEquals("number of tables",1, res.getResSchemaList().get(0).getResTableList().size());
        vr=res; //for followon tests

    }
    
    @Test
    public void addColTest() throws PreexistingEntityException, Exception{
        addTableTest();
        ResTable table = vr.getResSchemaList().get(0).getResTableList().get(0);
        TableColumn col = new TableColumn("colname", "coltype");
        col.addToTable(table);
        rjc.edit(vr);

        Resource res = rjc.findResource(IVOID);
        assertEquals("number of tables",1, res.getResSchemaList().get(0).getResTableList().get(0).getTableColumnList().size());

    }
     
    @Test
    public void addInterfacetest() throws PreexistingEntityException, Exception{
        createtest();
        Capability cap2 = vr.getCapabilityList().get(0);
        Interface iface = new Interface();
        iface.setAccessUrl("http://test.org/accesurl");
        iface.setUrlUse("ivo://test.org/urluse");
        iface.addToCapability(cap2);
        iface.setIntfType("inftype");
        
        rjc.edit(vr);
        
        Resource res = rjc.findResource(IVOID);
        assertEquals("number of interfaces",1, res.getCapabilityList().get(0).getInterfaceList().size());
        
    }
    
    @Test
    public void addParamtest() throws PreexistingEntityException, Exception{
        addInterfacetest();
        Capability cap2 = vr.getCapabilityList().get(0);
        Interface iface =  cap2.getInterfaceList().get(0);
        IntfParam param = new IntfParam("paramname", "datatype");
        param.addToInterface(iface);
        
        rjc.edit(vr);
        
        Resource res = rjc.findResource(IVOID);
        assertEquals("number of parameters",1, res.getCapabilityList().get(0).getInterfaceList().get(0).getIntfParamList().size());
    }   
    
    
    @Test
    public void removecapabilitytest() throws PreexistingEntityException, Exception{
        createtest();
        vr.getCapabilityList().remove(0);
        rjc.edit(vr);
    }


    
    @Test
    public void removeaddCapabilitytest() throws PreexistingEntityException, Exception{
        removecapabilitytest();
        Capability cap2 = new Capability();
        cap2.addToResource(vr);
        cap2.setName("capname3");
        cap2.setCapDescription("capdescription");
        cap2.setStandardId("ivo://ivoa.std/std");
        rjc.edit(vr);
        assertEquals("number of capabilities in memory", 2, vr.getCapabilityList().size());
        Resource res = rjc.findResource(IVOID);
        assertEquals("number of capabilities in database", 2, res.getCapabilityList().size());
        
        assertTrue(" live capability indices different", res.getCapabilityList().get(0).getCapabilityPK().getCapIndex() != res.getCapabilityList().get(1).getCapabilityPK().getCapIndex());
        assertTrue(" live capability indices different", vr.getCapabilityList().get(0).getCapabilityPK().getCapIndex() != vr.getCapabilityList().get(1).getCapabilityPK().getCapIndex());
    }
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void jaxtest() throws PreexistingEntityException, Exception{
       addColTest();
       Relationship det = new Relationship();
       det.setRelatedId("ivo://test.org/related2");
       det.setRelationshipType("mirror-again");
       det.setRelatedName("fred2");
       det.addToResource(vr);
       det = new Relationship();
       det.setRelatedId("ivo://test.org/related1");
       det.setRelationshipType("mirror");
       det.setRelatedName("fred");
       det.addToResource(vr);

       Validation val = new Validation();
       val.setValidatedBy("ivo://test.org/validator");
       val.setLevel((short) 0);
       val.addToResource(vr);
       val = new Validation();
       val.setValidatedBy("ivo://test.org/validator2");
       val.setLevel((short) 1);
       val.addToCapability(vr.getCapabilityList().get(0));


       Marshaller m = jc.createMarshaller();
        
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        m.marshal(new JAXBElement(new QName(
                "http://www.ivoa.net/xml/RegTAP/v1.0", "Resource"),
                org.javastro.ivoa.entities.regtap.Resource.class, vr), System.out);

        
    }
    
    @Test
    public void readtest() throws PreexistingEntityException, Exception {
        createtest();
        Resource vnew = rjc.findResource(IVOID);
        assertNotNull(vnew);
        
    }

    private void fillResource() {
        vr.setIvoid(IVOID);
        vr.setReferenceUrl("http://dummy.org/test");
        vr.setCreated(new Date());
        vr.setUpdated(new Date());
        vr.setStatus("new");
        vr.setShortName("shortname");
        vr.setResTitle("title");
        vr.setResDescription("description");
        vr.setResType("service");
    }

}


/*
 * $Log$
 */
