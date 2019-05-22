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
public class InterfacePK implements Serializable {
    @Basic(optional = false)
    @Column(name="ivoid",nullable = false, length = 256)
    private String ivoid;
    @Basic(optional = false)
    @Column(name = "intf_index", nullable = false)
    @XmlElement(name = "intf_index")
    private short intfIndex;

    public InterfacePK() {
    }

    public InterfacePK(String ivoid, short intfIndex) {
        this.ivoid = ivoid;
        this.intfIndex = intfIndex;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        hash += (int) intfIndex;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterfacePK)) {
            return false;
        }
        InterfacePK other = (InterfacePK) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        if (this.intfIndex != other.intfIndex) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.InterfacePK[ ivoid=" + ivoid + ",  intfIndex=" + intfIndex + " ]";
    }

}
