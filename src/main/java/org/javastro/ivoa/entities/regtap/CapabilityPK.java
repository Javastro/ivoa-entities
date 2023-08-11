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
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class CapabilityPK implements Serializable {
    /** serialVersionUID.
     */
    private static final long serialVersionUID = -3746821140158873791L;
    @Basic(optional = false)
    @Column(name="ivoid",nullable = false)
    @XmlAttribute
    private String ivoid;
    @XmlAttribute (name = "cap_index")
    @Column(name = "cap_index", nullable = false)
    private short capIndex;

    public CapabilityPK() {
    }

    public CapabilityPK(String ivoid, short capIndex) {
        this.ivoid = ivoid;
        this.capIndex = capIndex;
    }

    public String getIvoid() {
        return ivoid;
    }

    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
    }

    public short getCapIndex() {
        return capIndex;
    }

    public void setCapIndex(short capIndex) {
        this.capIndex = capIndex;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        hash += (int) capIndex;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CapabilityPK)) {
            return false;
        }
        CapabilityPK other = (CapabilityPK) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        if (this.capIndex != other.capIndex) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.CapabilityPK[ ivoid=" + ivoid + ", capIndex=" + capIndex + " ]";
    }

}
