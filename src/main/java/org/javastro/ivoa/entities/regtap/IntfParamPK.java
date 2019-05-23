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
public class IntfParamPK implements Serializable {
    @Basic(optional = false)
    @Column(name="ivoid", nullable = false)
    private String ivoid;
    @Basic(optional = false)
    @Column(name = "intf_index", nullable = false)
    @XmlElement(name = "intf_index")
    private short intfIndex;
    @Basic(optional = false)
    @Column(name="name",nullable = false)
    private String name;

    public IntfParamPK() {
    }

    public IntfParamPK(String ivoid,  short intfIndex, String name) {
        this.ivoid = ivoid;
        this.intfIndex = intfIndex;
        this.name = name;
    }

    public String getIvoid() {
        return ivoid;
    }

    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
    }


    public short getIntfIndex() {
        return intfIndex;
    }

    public void setIntfIndex(short intfIndex) {
        this.intfIndex = intfIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        hash += (int) intfIndex;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntfParamPK)) {
            return false;
        }
        IntfParamPK other = (IntfParamPK) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        if (this.intfIndex != other.intfIndex) {
            return false;
        }
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.IntfParamPK[ ivoid=" + ivoid +  ", intfIndex=" + intfIndex + ", name=" + name + " ]";
    }

}
