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
import jakarta.persistence.Id;
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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResDetail implements Serializable {
    @Transient //but keep for the XML
    @XmlAttribute
    private String ivoid;
    @Basic(optional = true)
    @Column(name = "cap_index", nullable = true)
    @XmlAttribute(name = "cap_index", required = false)
    private Short capIndex;
    @Basic(optional = false)
    @Column(name = "detail_utype", nullable = false)
    @XmlElement(name = "detail_utype")
    private String detailUtype;
    @Basic(optional = false)
    @Column(name = "detail_value", nullable = false)
    @XmlElement(name = "detail_value")
    private String detailValue;
    @XmlTransient
    @Transient
    private Resource resource;

    public ResDetail() {
    }

  
   
 
 
    /**
     * @param ivoid
     * @param capIndex
     * @param detailUtype
     * @param detailValue
     */
    public ResDetail(String ivoid, Short capIndex, String detailUtype,
            String detailValue) {
        this.ivoid = ivoid;
        this.capIndex = capIndex;
        this.detailUtype = detailUtype;
        this.detailValue = detailValue;
    }





    /**
     * @return the ivoid
     */
    public String getIvoid() {
        return ivoid;
    }





    /**
     * @param ivoid the ivoid to set
     */
    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
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
     * @return the detailUtype
     */
    public String getDetailUtype() {
        return detailUtype;
    }





    /**
     * @param detailUtype the detailUtype to set
     */
    public void setDetailUtype(String detailUtype) {
        this.detailUtype = detailUtype;
    }





    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }

    public Resource getResource() {
        return resource;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        this.ivoid = (resource.getIvoid());
        resource.getResDetailList().add(this);
    }





    /**
     * {@inheritDoc}
     * overrides @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((capIndex == null) ? 0 : capIndex.hashCode());
        result = prime * result
                + ((detailUtype == null) ? 0 : detailUtype.hashCode());
        result = prime * result
                + ((detailValue == null) ? 0 : detailValue.hashCode());
        result = prime * result + ((ivoid == null) ? 0 : ivoid.hashCode());
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
        if (!(obj instanceof ResDetail))
            return false;
        ResDetail other = (ResDetail) obj;
        if (capIndex == null) {
            if (other.capIndex != null)
                return false;
        } else if (!capIndex.equals(other.capIndex))
            return false;
        if (detailUtype == null) {
            if (other.detailUtype != null)
                return false;
        } else if (!detailUtype.equals(other.detailUtype))
            return false;
        if (detailValue == null) {
            if (other.detailValue != null)
                return false;
        } else if (!detailValue.equals(other.detailValue))
            return false;
        if (ivoid == null) {
            if (other.ivoid != null)
                return false;
        } else if (!ivoid.equals(other.ivoid))
            return false;
        return true;
    }
    

  
}
