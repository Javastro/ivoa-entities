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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Validation.findAll", query = "SELECT v FROM Validation v"),
    @NamedQuery(name = "Validation.findByIvoid", query = "SELECT v FROM Validation v WHERE v.validationPK.ivoid = :ivoid"),
    @NamedQuery(name = "Validation.findByValidatedBy", query = "SELECT v FROM Validation v WHERE v.validatedBy = :validatedBy"),
    @NamedQuery(name = "Validation.findByLevel", query = "SELECT v FROM Validation v WHERE v.level = :level"),
    @NamedQuery(name = "Validation.findByCapIndex", query = "SELECT v FROM Validation v WHERE v.validationPK.capIndex = :capIndex")})
public class Validation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Transient
    private String ivoid;
    @Basic(optional = true)
    @Column(name = "cap_index", nullable = true)
    @XmlElement(name = "cap_index", required = false, nillable = true)
    private Short capIndex;
    @Basic(optional = false)
    @Column(name = "validated_by", nullable = false)
    @XmlElement(name = "validated_by")
    private String validatedBy;
    @Basic(optional = false)
    @Column(name="level",nullable = false)
    private short level;
    @XmlTransient
    @Transient
    private Resource resource;

    public Validation() {
    }

     /**
     * @param capIndex
     * @param validatedBy
     * @param level
     */
    public Validation(short capIndex, String validatedBy, short level) {
        this.capIndex = capIndex;
        this.validatedBy = validatedBy;
        this.level = level;
    }

    public Resource getResource() {
        return resource;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        this.ivoid = (resource.getIvoid());
       if ( resource.getValidationList().indexOf(this)== -1)
        {
            resource.getValidationList().add(this);
        }
    }

    /**
     * @param capability
     */
    public void addToCapability(Capability capability) {
        this.resource = capability.getResource();
        this.ivoid = (resource.getIvoid());
        this.capIndex = capability.getIndex();
        if ( resource.getValidationList().indexOf(this)== -1)
        {
            resource.getValidationList().add(this);
        }
    }

    /**
     * @return the capIndex
     */
    public Short getCapIndex() {
        return capIndex;
    }

    /**
     * @param capIndex the capIndex to set
     */
    public void setCapIndex(Short capIndex) {
        this.capIndex = capIndex;
    }

    /**
     * @return the validatedBy
     */
    public String getValidatedBy() {
        return validatedBy;
    }

    /**
     * @param validatedBy the validatedBy to set
     */
    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }

    /**
     * @return the level
     */
    public short getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(short level) {
        this.level = level;
    }

    /**
     * {@inheritDoc}
     * overrides @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((capIndex==null)? 0 : capIndex.hashCode());
        result = prime * result + ((ivoid == null) ? 0 : ivoid.hashCode());
        result = prime * result + level;
        result = prime * result
                + ((validatedBy == null) ? 0 : validatedBy.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * overrides @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Validation))
            return false;
        Validation other = (Validation) obj;
        if (capIndex != other.capIndex)
            return false;
        if (ivoid == null) {
            if (other.ivoid != null)
                return false;
        } else if (!ivoid.equals(other.ivoid))
            return false;
        if (level != other.level)
            return false;
        if (validatedBy == null) {
            if (other.validatedBy != null)
                return false;
        } else if (!validatedBy.equals(other.validatedBy))
            return false;
        return true;
    }


 
}
