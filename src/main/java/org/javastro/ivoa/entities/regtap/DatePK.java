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
public class DatePK implements Serializable {
    @Basic(optional = false)
    @Column(name="ivoid",nullable = false)
    @XmlAttribute
    private String ivoid;
    @Basic(optional = false)
    @Column(name = "value_role", nullable = false)
    @XmlAttribute(name = "value_role")
    private String valueRole;

    public DatePK() {
    }

    public DatePK(String ivoid, String valueRole) {
        this.ivoid = ivoid;
        this.valueRole = valueRole;
    }

    public String getIvoid() {
        return ivoid;
    }

    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
    }

    public String getValueRole() {
        return valueRole;
    }

    public void setValueRole(String valueRole) {
        this.valueRole = valueRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        hash += (valueRole != null ? valueRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatePK)) {
            return false;
        }
        DatePK other = (DatePK) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        if ((this.valueRole == null && other.valueRole != null) || (this.valueRole != null && !this.valueRole.equals(other.valueRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.DatePK[ ivoid=" + ivoid + ", valueRole=" + valueRole + " ]";
    }

}
