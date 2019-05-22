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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class RelationshipPK implements Serializable {
    @Basic(optional = false)
    @Column(name="ivoid", nullable = false, length = 256)
    private String ivoid;
    @Basic(optional = false)
    @Column(name = "relationship_type", nullable = false, length = 256)
    @XmlElement(name = "relationship_type")
    private String relationshipType;
    @Basic(optional = false)
    @Column(name = "related_id", nullable = false, length = 256)
    @XmlElement(name = "related_id")
    private String relatedId;

    public RelationshipPK() {
    }

    public RelationshipPK(String ivoid, String relationshipType, String relatedId) {
        this.ivoid = ivoid;
        this.relationshipType = relationshipType;
        this.relatedId = relatedId;
    }

    public String getIvoid() {
        return ivoid;
    }

    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        hash += (relationshipType != null ? relationshipType.hashCode() : 0);
        hash += (relatedId != null ? relatedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelationshipPK)) {
            return false;
        }
        RelationshipPK other = (RelationshipPK) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        if ((this.relationshipType == null && other.relationshipType != null) || (this.relationshipType != null && !this.relationshipType.equals(other.relationshipType))) {
            return false;
        }
        if ((this.relatedId == null && other.relatedId != null) || (this.relatedId != null && !this.relatedId.equals(other.relatedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.RelationshipPK[ ivoid=" + ivoid + ", relationshipType=" + relationshipType + ", relatedId=" + relatedId + " ]";
    }

}
