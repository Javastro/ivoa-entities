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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Entity
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="relationship")
@NamedQueries({
    @NamedQuery(name = "Relationship.findAll", query = "SELECT r FROM Relationship r"),
    @NamedQuery(name = "Relationship.findByIvoid", query = "SELECT r FROM Relationship r WHERE r.relationshipPK.ivoid = :ivoid"),
    @NamedQuery(name = "Relationship.findByRelationshipType", query = "SELECT r FROM Relationship r WHERE r.relationshipPK.relationshipType = :relationshipType"),
    @NamedQuery(name = "Relationship.findByRelatedId", query = "SELECT r FROM Relationship r WHERE r.relationshipPK.relatedId = :relatedId"),
    @NamedQuery(name = "Relationship.findByRelatedName", query = "SELECT r FROM Relationship r WHERE r.relatedName = :relatedName")})
public class Relationship implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlPath(".")
    protected RelationshipPK relationshipPK;
    @Basic(optional = false)
    @Column(name = "related_name", nullable = false, length = 256)
    @XmlElement(name = "related_name")
    private String relatedName;
    @XmlTransient
    @JoinColumn(name = "ivoid", referencedColumnName = "ivoid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Resource resource;

    public Relationship() {
        this.relationshipPK = new RelationshipPK();
    }

    public Relationship(RelationshipPK relationshipPK) {
        this.relationshipPK = relationshipPK;
    }

    public Relationship(RelationshipPK relationshipPK, String relatedName) {
        this.relationshipPK = relationshipPK;
        this.relatedName = relatedName;
    }

    public Relationship(String ivoid, String relationshipType, String relatedId) {
        this.relationshipPK = new RelationshipPK(ivoid, relationshipType, relatedId);
    }

    public RelationshipPK getRelationshipPK() {
        return relationshipPK;
    }

    public void setRelationshipPK(RelationshipPK relationshipPK) {
        this.relationshipPK = relationshipPK;
    }

    public String getRelatedName() {
        return relatedName;
    }

    public void setRelatedName(String relatedName) {
        this.relatedName = relatedName;
    }

    public Resource getResource() {
        return resource;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        this.relationshipPK.setIvoid(resource.getIvoid());
        if(resource.getRelationshipList().indexOf(this) == -1){
            resource.getRelationshipList().add(this);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relationshipPK != null ? relationshipPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relationship)) {
            return false;
        }
        Relationship other = (Relationship) object;
        if ((this.relationshipPK == null && other.relationshipPK != null) || (this.relationshipPK != null && !this.relationshipPK.equals(other.relationshipPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.Relationship[ relationshipPK=" + relationshipPK + " ]";
    }

    /**
     * @param string
     */
    public void setRelationshipType(String string) {
        this.relationshipPK.setRelationshipType(string);
    }

    /**
     * @param string
     */
    public void setRelatedId(String string) {
        this.relationshipPK.setRelatedId(string);
    }

}
