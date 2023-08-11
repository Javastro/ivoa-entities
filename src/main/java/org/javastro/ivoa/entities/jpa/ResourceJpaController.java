/*
 * $$Id: ResourceJpaController.java,v 1.2 2011-09-13 13:43:33 pah Exp $$
 *
 *
 *  Created on 07-Jun-2011 by Paul Harrison <paul.harrison@manchester.ac.uk>
 * 
 * This software is published under the terms of the Academic
 * Free License, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 * 
 *  Copyright (c) The University of Manchester. All rights reserved.
 *
 */
package org.javastro.ivoa.entities.jpa;

import jakarta.persistence.EntityManagerFactory;

import org.javastro.ivoa.entities.resource.Resource;
import org.javastro.ivoa.jpa.EntityMetadata;
import org.javastro.ivoa.jpa.SimpleJPARepository;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 07-Jun-2011
 */
public class ResourceJpaController extends SimpleJPARepository<Resource, String>  {

    /**
     * @param name
     * @param emf
     */
    public ResourceJpaController(String name, EntityManagerFactory emf) {
        super(name, emf, new EntityMetadata<Resource, String>() {

            @Override
            public Class<Resource> getJavaType() {
               return Resource.class;                
            }

            @Override
            public String getID(Resource ent) {
                return ent.getIdentifier();
                
            }
        }
        );
    }
     
}
