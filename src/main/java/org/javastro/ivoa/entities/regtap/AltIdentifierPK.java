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
 * Primary key for the Alt Identifier.
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class AltIdentifierPK implements Serializable {
    @Basic(optional = false)
    @Column(name="ivoid",nullable = false)
    private String ivoid;
    @Basic(optional = false)
    @XmlElement(name="id")
    @Column(name="alt_identifier", nullable = false)
    private String altIdentifier;

    /**
     * @return the altIdentifier
     */
    public String getAltIdentifier() {
        return altIdentifier;
    }

    /**
     * @param altIdentifier the altIdentifier to set
     */
    public void setAltIdentifier(String altIdentifier) {
        this.altIdentifier = altIdentifier;
    }

    public AltIdentifierPK() {
    }

    public AltIdentifierPK(String ivoid, String subject) {
        this.ivoid = ivoid;
        this.altIdentifier = subject;
    }

    public String getIvoid() {
        return ivoid;
    }

    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
    }

  
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        hash += (altIdentifier != null ? altIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AltIdentifierPK)) {
            return false;
        }
        AltIdentifierPK other = (AltIdentifierPK) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        if ((this.altIdentifier == null && other.altIdentifier != null) || (this.altIdentifier != null && !this.altIdentifier.equals(other.altIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.AltIdentifierPK[ ivoid=" + ivoid + ", subject=" + altIdentifier + " ]";
    }

}
