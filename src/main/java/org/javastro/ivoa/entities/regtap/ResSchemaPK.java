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
public class ResSchemaPK implements Serializable {
    /** serialVersionUID.
     */
    private static final long serialVersionUID = -1472454756028696002L;
    @Basic(optional = false)
    @Column(name="ivoid",nullable = false)
    private String ivoid;
    @Basic(optional = false)
    @Column(name = "schema_index", nullable = false)
    @XmlElement(name = "schema_index")
    private short schemaIndex;

    public ResSchemaPK() {
    }

    public ResSchemaPK(String ivoid, short schemaIndex) {
        this.ivoid = ivoid;
        this.schemaIndex = schemaIndex;
    }

    public String getIvoid() {
        return ivoid;
    }

    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
    }

    public short getSchemaIndex() {
        return schemaIndex;
    }

    public void setSchemaIndex(short schemaIndex) {
        this.schemaIndex = schemaIndex;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        hash += (int) schemaIndex;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResSchemaPK)) {
            return false;
        }
        ResSchemaPK other = (ResSchemaPK) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        if (this.schemaIndex != other.schemaIndex) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.ResSchemaPK[ ivoid=" + ivoid + ", schemaIndex=" + schemaIndex + " ]";
    }

}
