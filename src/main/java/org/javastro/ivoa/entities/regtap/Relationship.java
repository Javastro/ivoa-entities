/*
 * $$Id:$$
 *
 *
 *  Created on 04-Feb-2013 by Paul Harrison <paul.harrison@manchester.ac.uk>
 * 
 * This software is published under the terms of the Academic
 * Free License, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 * 
 *  Copyright (c) The University of Manchester. All rights reserved.
 *
 */ 
package org.javastro.ivoa.entities.regtap;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

/**
 * RegTAP relationship.
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Embeddable
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Relationship implements Serializable {
    private static final long serialVersionUID = 1L;
    @Transient //IMPL perhaps get rid of this - however useful in the intermediate XML representation
    @XmlAttribute
    private String ivoid;
    @Basic(optional = false)
    @Column(name = "relationship_type", nullable = false)
    @XmlElement(name = "relationship_type")
    private String relationshipType;
    @Basic(optional = true)
    @Column(name = "related_id", nullable = true)
    @XmlElement(name = "related_id")
    private String relatedId;
    @Basic(optional = true)
    @Column(name = "related_name", nullable = true)
    @XmlElement(name = "related_name")
    private String relatedName;

    //IMPL this might not be necessary with @elementCollection
    @XmlTransient
    @Transient
    private Resource resource;

    
    
  
    /**
     * 
     */
    public Relationship() {
    }
    
    

    /**
     * @param relationshipType
     * @param relatedId
     * @param relatedName
     */
    public Relationship(String relationshipType, String relatedId,
            String relatedName) {
        this.relationshipType = relationshipType;
        this.relatedId = relatedId;
        this.relatedName = relatedName;
    }



    public Resource getResource() {
        return resource;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        this.ivoid = (resource.getIvoid());
        if(resource.getRelationshipList().indexOf(this) == -1){
            resource.getRelationshipList().add(this);
        }
    }

    /**
     * @return the relationshipType
     */
    public String getRelationshipType() {
        return relationshipType;
    }

    /**
     * @param relationshipType the relationshipType to set
     */
    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    /**
     * @return the relatedId
     */
    public String getRelatedId() {
        return relatedId;
    }

    /**
     * @param relatedId the relatedId to set
     */
    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    /**
     * @return the relatedName
     */
    public String getRelatedName() {
        return relatedName;
    }

    /**
     * @param relatedName the relatedName to set
     */
    public void setRelatedName(String relatedName) {
        this.relatedName = relatedName;
    }

    /**
     * @return the ivoid
     */
    public String getIvoid() {
        return ivoid;
    }

 
}
