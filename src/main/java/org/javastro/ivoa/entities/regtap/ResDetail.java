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
import javax.persistence.Id;
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
    @NamedQuery(name = "ResDetail.findAll", query = "SELECT r FROM ResDetail r"),
    @NamedQuery(name = "ResDetail.findByIvoid", query = "SELECT r FROM ResDetail r WHERE r.ivoid = :ivoid"),
    @NamedQuery(name = "ResDetail.findByCapIndex", query = "SELECT r FROM ResDetail r WHERE r.capIndex = :capIndex"),
    @NamedQuery(name = "ResDetail.findByDetailUtype", query = "SELECT r FROM ResDetail r WHERE r.detailUtype = :detailUtype"),
    @NamedQuery(name = "ResDetail.findByDetailValue", query = "SELECT r FROM ResDetail r WHERE r.detailValue = :detailValue")})
public class ResDetail implements Serializable {
    @Transient //but keep for the XML
    private String ivoid;
    @Basic(optional = true)
    @Column(name = "cap_index", nullable = true)
    @XmlElement(name = "cap_index", nillable = true)
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
