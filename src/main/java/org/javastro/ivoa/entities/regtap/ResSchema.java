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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlPath;

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
    @XmlPath(".")
    protected ResSchemaPK resSchemaPK;
    @Column(name = "schema_name", length = 256)
    @XmlElement(name = "schema_name")
    private String name;
    @Column(name = "schema_title", length = 256)
    @XmlElement(name = "schema_title")
    private String title;
    @Column(name = "schema_description", length = 256)
    @XmlElement(name = "schema_description")
    private String description;
    @Column(name = "schema_utype", length = 256)
    @XmlElement(name = "schema_utype")
    private String utype;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schema", targetEntity=ResTable.class, fetch= FetchType.EAGER, orphanRemoval=true)
    private PKIndexList<ResTable> resTableList;

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

    public PKIndexList<ResTable> getResTableList() {
        if (resTableList == null) resTableList = new PKIndexList<ResTable>();
        return resTableList;
    }

    public void setResTableList(PKIndexList<ResTable> resTableList) {
        this.resTableList = resTableList;
    }

    public Resource getResource() {
        return resource;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        if (resource.getResSchemaList().indexOf(this) == -1) {
            resource.getResSchemaList().addAndSetIndex(this);
        }
        this.resSchemaPK.setIvoid(resource.getIvoid());
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

}