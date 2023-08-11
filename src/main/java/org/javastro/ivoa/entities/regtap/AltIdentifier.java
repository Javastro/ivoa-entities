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

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

//import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Entity
@Table(name="alt_identifier")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@NamedQueries({
    @NamedQuery(name = "AltIdentifier.findAll", query = "SELECT s FROM AltIdentifier s"),
    @NamedQuery(name = "AltIdentifier.findByIvoid", query = "SELECT s FROM AltIdentifier s WHERE s.altidPK.ivoid = :ivoid"),
    @NamedQuery(name = "AltIdentifier.findByAltId", query = "SELECT s FROM AltIdentifier s WHERE s.altidPK.altIdentifier = :altId")})
public class AltIdentifier implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlElement
    protected AltIdentifierPK altidPK;
    @XmlTransient
    @JoinColumn(name = "ivoid", referencedColumnName = "ivoid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Resource resource;
    
    /**
     * 
     */
    AltIdentifier() {
        this.altidPK = new AltIdentifierPK();
    }

    public AltIdentifier(String id) {
        this.altidPK = new AltIdentifierPK();
        this.altidPK.setAltIdentifier(id);
    }

    public Resource getResource() {
        return resource;
    }
    
    public void setAltId(String id)
    {
        this.altidPK.setAltIdentifier(id);
    }

    /**
     * @return the altidPK
     */
    public AltIdentifierPK getAltidPK() {
        return altidPK;
    }

    /**
     * @param altidPK the altidPK to set
     */
    public void setAltidPK(AltIdentifierPK altidPK) {
        this.altidPK = altidPK;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        if(resource.getAltIdentifier().indexOf(this) == -1)
        {
            resource.getAltIdentifier().add(this);
        }
        this.altidPK.setIvoid(resource.getIvoid());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (altidPK != null ? altidPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AltIdentifier)) {
            return false;
        }
        AltIdentifier other = (AltIdentifier) object;
        if ((this.altidPK == null && other.altidPK != null) || (this.altidPK != null && !this.altidPK.equals(other.altidPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.Subject[ subjectPK=" + altidPK + " ]";
    }

}
