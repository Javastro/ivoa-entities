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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 * RegTAP relationship.
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Embeddable
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Relationship.findAll", query = "SELECT r FROM Relationship r"),
    @NamedQuery(name = "Relationship.findByRelationshipType", query = "SELECT r FROM Relationship r WHERE r.relationshipType = :relationshipType"),
    @NamedQuery(name = "Relationship.findByRelatedId", query = "SELECT r FROM Relationship r WHERE r.relatedId = :relatedId"),
    @NamedQuery(name = "Relationship.findByRelatedName", query = "SELECT r FROM Relationship r WHERE r.relatedName = :relatedName")})
public class Relationship implements Serializable {
    private static final long serialVersionUID = 1L;
    @Transient //IMPL perhaps get rid of this - however useful in the intermediate XML representation
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
