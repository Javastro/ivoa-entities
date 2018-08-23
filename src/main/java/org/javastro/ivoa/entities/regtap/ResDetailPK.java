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
public class ResDetailPK implements Serializable {
    @Basic(optional = false)
    @Column(nullable = false, length = 256)
    private String ivoid;
    @Basic(optional = false)
    @Column(name = "cap_index", nullable = false)
    @XmlElement(name = "cap_index")
    private Short capIndex;
    @Basic(optional = false)
    @Column(name = "detail_utype", nullable = false, length = 256)
    @XmlElement(name = "detail_utype")
    private String detailUtype;

    public ResDetailPK() {
    }

    public ResDetailPK(String ivoid, Short capIndex, String detailUtype) {
        this.ivoid = ivoid;
        this.capIndex = capIndex;
        this.detailUtype = detailUtype;
    }

    public String getIvoid() {
        return ivoid;
    }

    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
    }

    public Short getCapIndex() {
        return capIndex;
    }

    public void setCapIndex(short i) {
        this.capIndex = i;
    }

    public String getDetailUtype() {
        return detailUtype;
    }

    public void setDetailUtype(String detailUtype) {
        this.detailUtype = detailUtype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        if(capIndex != null)hash += (int) capIndex;
        hash += (detailUtype != null ? detailUtype.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResDetailPK)) {
            return false;
        }
        ResDetailPK other = (ResDetailPK) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        if (this.capIndex != other.capIndex) {
            return false;
        }
        if ((this.detailUtype == null && other.detailUtype != null) || (this.detailUtype != null && !this.detailUtype.equals(other.detailUtype))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.ResDetailPK[ ivoid=" + ivoid + ", capIndex=" + capIndex + ", detailUtype=" + detailUtype + " ]";
    }

}
