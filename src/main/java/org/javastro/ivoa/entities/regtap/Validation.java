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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Entity
@Table(name="validation")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Validation.findAll", query = "SELECT v FROM Validation v"),
    @NamedQuery(name = "Validation.findByIvoid", query = "SELECT v FROM Validation v WHERE v.validationPK.ivoid = :ivoid"),
    @NamedQuery(name = "Validation.findByValidatedBy", query = "SELECT v FROM Validation v WHERE v.validatedBy = :validatedBy"),
    @NamedQuery(name = "Validation.findByLevel", query = "SELECT v FROM Validation v WHERE v.level = :level"),
    @NamedQuery(name = "Validation.findByCapIndex", query = "SELECT v FROM Validation v WHERE v.validationPK.capIndex = :capIndex")})
public class Validation implements Serializable, PKIndex {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlPath(".")
    protected ValidationPK validationPK;
    @Basic(optional = false)
    @Column(name = "validated_by", nullable = false, length = 256)
    @XmlElement(name = "validated_by")
    private String validatedBy;
    @Basic(optional = false)
    @Column(nullable = false)
    private short level;
    @XmlTransient
    @JoinColumn(name = "ivoid", referencedColumnName = "ivoid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Resource resource;

    public Validation() {
        this.validationPK = new ValidationPK();
    }

    public Validation(ValidationPK validationPK) {
        this.validationPK = validationPK;
    }

    public Validation(ValidationPK validationPK, String validatedBy, short level) {
        this.validationPK = validationPK;
        this.validatedBy = validatedBy;
        this.level = level;
    }

    public Validation(String ivoid, short capIndex) {
        this.validationPK = new ValidationPK(ivoid, capIndex);
    }

    public ValidationPK getValidationPK() {
        return validationPK;
    }

    public void setValidationPK(ValidationPK validationPK) {
        this.validationPK = validationPK;
    }

    public String getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public Resource getResource() {
        return resource;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        this.validationPK.setIvoid(resource.getIvoid());
        this.validationPK.setCapIndex((short) -1);
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
        this.validationPK.setIvoid(resource.getIvoid());
        if ( resource.getValidationList().indexOf(this)== -1)
        {
            resource.getValidationList().addAndSetIndex(this);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (validationPK != null ? validationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Validation)) {
            return false;
        }
        Validation other = (Validation) object;
        if ((this.validationPK == null && other.validationPK != null) || (this.validationPK != null && !this.validationPK.equals(other.validationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.Validation[ validationPK=" + validationPK + " ]";
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#getIndex()
     */
    @Override
    public short getIndex() {
       return getValidationPK().getCapIndex();
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#setPKIndex(short)
     */
    @Override
    public void setPKIndex(short idx) {
        getValidationPK().setCapIndex(idx);
    }

 
}
