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
import java.util.List;

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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

//import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Entity
@Table(name = "res_schema")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "ResSchema.findAll", query = "SELECT r FROM ResSchema r"),
    @NamedQuery(name = "ResSchema.findByIvoid", query = "SELECT r FROM ResSchema r WHERE r.resSchemaPK.ivoid = :ivoid"),
    @NamedQuery(name = "ResSchema.findBySchemaIndex", query = "SELECT r FROM ResSchema r WHERE r.resSchemaPK.schemaIndex = :schemaIndex"),
    @NamedQuery(name = "ResSchema.findBySchemaName", query = "SELECT r FROM ResSchema r WHERE r.name = :schemaName"),
    @NamedQuery(name = "ResSchema.findBySchemaTitle", query = "SELECT r FROM ResSchema r WHERE r.title = :schemaTitle"),
    @NamedQuery(name = "ResSchema.findBySchemaDescription", query = "SELECT r FROM ResSchema r WHERE r.description = :schemaDescription"),
    @NamedQuery(name = "ResSchema.findBySchemaUtype", query = "SELECT r FROM ResSchema r WHERE r.utype = :schemaUtype")})
public class ResSchema implements Serializable, PKIndex {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlElement
    protected ResSchemaPK resSchemaPK;
    @Column(name = "schema_name")
    @XmlElement(name = "name")
    private String name;
    @Column(name = "schema_title")
    @XmlElement(name = "title")
    private String title;
    @Column(name = "schema_description",length=1024)
    @XmlElement(name = "description")
    private String description;
    @Column(name = "schema_utype")
    @XmlElement(name = "utype")
    private String utype;
    
 
    @XmlTransient
    @JoinColumn(name = "ivoid", referencedColumnName = "ivoid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Resource resource;

    public ResSchema() {
        this.resSchemaPK = new ResSchemaPK();
    }

    public ResSchema(ResSchemaPK resSchemaPK) {
        this.resSchemaPK = resSchemaPK;
    }

    public ResSchema(String ivoid, short schemaIndex) {
        this.resSchemaPK = new ResSchemaPK(ivoid, schemaIndex);
    }

    public ResSchemaPK getResSchemaPK() {
        return resSchemaPK;
    }

    public void setResSchemaPK(ResSchemaPK resSchemaPK) {
        this.resSchemaPK = resSchemaPK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public List<ResTable> getResTableList() {
       return resource.getResTableList();
    }

    public Resource getResource() {
        return resource;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        PKIndexUtils.addWithIndex(this, resource, resource.getResSchemaList());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resSchemaPK != null ? resSchemaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResSchema)) {
            return false;
        }
        ResSchema other = (ResSchema) object;
        if ((this.resSchemaPK == null && other.resSchemaPK != null) || (this.resSchemaPK != null && !this.resSchemaPK.equals(other.resSchemaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.ResSchema[ resSchemaPK=" + resSchemaPK + " ]";
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#getIndex()
     */
    @Override
    public short getIndex() {
      return this.resSchemaPK.getSchemaIndex();
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#setPKIndex(short)
     */
    @Override
    public void setPKIndex(short idx) {
        this.resSchemaPK.setSchemaIndex(idx);
    }

    /**
     * {@inheritDoc}
     * overrides @see org.javastro.ivoa.entities.regtap.PKIndex#setIvoid(java.lang.String)
     */
    @Override
    public void setIvoid(String i) {
        this.resSchemaPK.setIvoid(i);
        
    }

}
