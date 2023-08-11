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
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CollectionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

//import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Entity
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="capability")
@NamedQueries({
    @NamedQuery(name = "Capability.findAll", query = "SELECT c FROM Capability c"),
    @NamedQuery(name = "Capability.findByIvoid", query = "SELECT c FROM Capability c WHERE c.capabilityPK.ivoid = :ivoid"),
    @NamedQuery(name = "Capability.findByCapIndex", query = "SELECT c FROM Capability c WHERE c.capabilityPK.capIndex = :capIndex"),
    @NamedQuery(name = "Capability.findByCapName", query = "SELECT c FROM Capability c WHERE c.name = :capName"),
    @NamedQuery(name = "Capability.findByCapType", query = "SELECT c FROM Capability c WHERE c.type = :capType"),
    @NamedQuery(name = "Capability.findByCapDescription", query = "SELECT c FROM Capability c WHERE c.capDescription = :capDescription"),
    @NamedQuery(name = "Capability.findByStandardId", query = "SELECT c FROM Capability c WHERE c.standardId = :standardId")})
public class Capability implements Serializable, PKIndex, Identifier {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlElement
    protected CapabilityPK capabilityPK;
    @Column(name = "cap_name")
    @XmlElement(name = "cap_name")
    private String name;
    @Column(name = "cap_type")
    @XmlElement(name = "cap_type")
    private String type;
    @Column(name = "cap_description",length=1024)
    @XmlElement(name = "cap_description")
    private String capDescription;
    @Column(name = "standard_id")
    @XmlElement(name = "standard_id")
    private String standardId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capability", targetEntity=Interface.class, fetch= FetchType.EAGER, orphanRemoval=true)
    @XmlElement(name = "interface")
    private List<Interface> interfaceList;

    @XmlTransient
    @JoinColumn(name = "ivoid", referencedColumnName = "ivoid", nullable = false, insertable = false, updatable = false )
    @ManyToOne(optional = false)
    private Resource resource;

    public Capability() {
        capabilityPK = new CapabilityPK();
    }

    public Capability(CapabilityPK capabilityPK) {
        this.capabilityPK = capabilityPK;
    }

    public Capability(String ivoid, short capIndex) {
        this.capabilityPK = new CapabilityPK(ivoid, capIndex);
    }

    public CapabilityPK getCapabilityPK() {
        return capabilityPK;
    }
    
    public void setCapabilityPK(CapabilityPK capabilityPK) {
        this.capabilityPK = capabilityPK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapType() {
        return type;
    }

    public void setCapType(String capType) {
        this.type = capType;
    }

    public String getCapDescription() {
        return capDescription;
    }

    public void setCapDescription(String capDescription) {
        this.capDescription = capDescription;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public List<Interface> getInterfaceList() {
        if(interfaceList == null) interfaceList = new ArrayList<Interface>();
        return interfaceList;
    }

    public void setInterfaceList(List<Interface> interfaceList) {
        this.interfaceList = interfaceList;
    }

    public Resource getResource() {
        return resource;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        PKIndexUtils.addWithIndex(this, resource, resource.getCapabilityList());
   }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (capabilityPK != null ? capabilityPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Capability)) {
            return false;
        }
        Capability other = (Capability) object;
        if ((this.capabilityPK == null && other.capabilityPK != null) || (this.capabilityPK != null && !this.capabilityPK.equals(other.capabilityPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.Capability[ capabilityPK=" + capabilityPK + " ]";
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#getIndex()
     */
    @Override
    public short getIndex() {
        return capabilityPK.getCapIndex();
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#setPKIndex(short)
     */
    @Override
    public void setPKIndex(short idx) {
        this.capabilityPK.setCapIndex(idx);
    }

    /**
     * {@inheritDoc}
     * overrides @see org.javastro.ivoa.entities.regtap.PKIndex#setIvoid(java.lang.String)
     */
    @Override
    public void setIvoid(String i) {
        this.capabilityPK.setIvoid(i);
        
    }

    /**
     * {@inheritDoc}
     * overrides @see org.javastro.ivoa.entities.regtap.Identifier#getIvoid()
     */
    @Override
    public String getIvoid() {
       return this.getCapabilityPK().getIvoid();
    }

    
}
