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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Entity
@Table(name = "intf_param")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "IntfParam.findAll", query = "SELECT i FROM IntfParam i"),
    @NamedQuery(name = "IntfParam.findByIvoid", query = "SELECT i FROM IntfParam i WHERE i.intfParamPK.ivoid = :ivoid"),
    @NamedQuery(name = "IntfParam.findByIntfIndex", query = "SELECT i FROM IntfParam i WHERE i.intfParamPK.intfIndex = :intfIndex"),
    @NamedQuery(name = "IntfParam.findByName", query = "SELECT i FROM IntfParam i WHERE i.intfParamPK.name = :name"),
    @NamedQuery(name = "IntfParam.findByDatatype", query = "SELECT i FROM IntfParam i WHERE i.datatype = :datatype"),
    @NamedQuery(name = "IntfParam.findByDescription", query = "SELECT i FROM IntfParam i WHERE i.description = :description"),
    @NamedQuery(name = "IntfParam.findByUcd", query = "SELECT i FROM IntfParam i WHERE i.ucd = :ucd"),
    @NamedQuery(name = "IntfParam.findByUnit", query = "SELECT i FROM IntfParam i WHERE i.unit = :unit"),
    @NamedQuery(name = "IntfParam.findByUtype", query = "SELECT i FROM IntfParam i WHERE i.utype = :utype"),
    @NamedQuery(name = "IntfParam.findByStd", query = "SELECT i FROM IntfParam i WHERE i.std = :std"),
    @NamedQuery(name = "IntfParam.findByExtendedSchema", query = "SELECT i FROM IntfParam i WHERE i.extendedSchema = :extendedSchema"),
    @NamedQuery(name = "IntfParam.findByExtendedType", query = "SELECT i FROM IntfParam i WHERE i.extendedType = :extendedType"),
    @NamedQuery(name = "IntfParam.findByUse", query = "SELECT i FROM IntfParam i WHERE i.param_use = :use_param")})
public class IntfParam implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlPath(".")
    protected IntfParamPK intfParamPK;
    @Column(name="description",length = 256)
    private String description;
    @Column(name="ucd",length = 256)
    private String ucd;
    @Column(name="unit",length = 256)
    private String unit;
    @Column(name="utype",length = 256)
    private String utype;
    @Basic(optional = false)
    @Column(name="datatype",nullable = false, length = 256)
    private String datatype;
    @Column(name = "extended_schema", length = 256)
    @javax.xml.bind.annotation.XmlElement(name = "extended_schema")
    private String extendedSchema;
    @Column(name = "extended_type", length = 256)
    @XmlElement(name = "extended_type")
    private String extendedType;
    @Column(name = "arraysize", length = 256)
    @XmlElement(name = "arraysize")
    private String arraysize;
    @Column(name = "delim", length = 256)
    @XmlElement(name = "delim")
    private String delim;
    @Column(name="param_use",length = 256)
    @XmlElement(name="use")
    private String param_use;
    @Basic(optional = false)
    @Column(name="std",nullable = false)
    private short std;
     
    @XmlTransient
    @ManyToOne(optional = false)
    @JoinColumns({@JoinColumn(name = "ivoid", nullable = false, insertable = false, updatable = false, referencedColumnName = "ivoid"),
    @JoinColumn(name = "intf_index", referencedColumnName = "intf_index", insertable = false, updatable = false, nullable = false)})
    private Interface iface;

    IntfParam() {
        intfParamPK = new IntfParamPK();
    }
    
    public IntfParam(String name, String datatype) {
        intfParamPK = new IntfParamPK();
        intfParamPK.setName(name);
        this.datatype = datatype;
    }
  

    public IntfParam(IntfParamPK intfParamPK) {
        this.intfParamPK = intfParamPK;
    }

    public IntfParam(IntfParamPK intfParamPK, String datatype, short std) {
        this.intfParamPK = intfParamPK;
        this.datatype = datatype;
        this.std = std;
    }

    public IntfParam(String ivoid,  short intfIndex, String name) {
        this.intfParamPK = new IntfParamPK(ivoid,  intfIndex, name);
    }

    public IntfParamPK getIntfParamPK() {
        return intfParamPK;
    }

    public void setIntfParamPK(IntfParamPK intfParamPK) {
        this.intfParamPK = intfParamPK;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUcd() {
        return ucd;
    }

    public void setUcd(String ucd) {
        this.ucd = ucd;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public short getStd() {
        return std;
    }

    public void setStd(short std) {
        this.std = std;
    }

    public String getExtendedSchema() {
        return extendedSchema;
    }

    public void setExtendedSchema(String extendedSchema) {
        this.extendedSchema = extendedSchema;
    }

    public String getExtendedType() {
        return extendedType;
    }

    public void setExtendedType(String extendedType) {
        this.extendedType = extendedType;
    }

 
 
    public Interface getIface() {
        return iface;
    }

    public void addToInterface(Interface iface) {
        this.iface = iface;
        
        if( iface.getIntfParamList().indexOf(this) == -1)
        {
            iface.getIntfParamList().add(this);
        }
        this.intfParamPK.setIvoid(iface.getInterfacePK().getIvoid());
        this.intfParamPK.setIntfIndex(iface.getIndex());

        
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intfParamPK != null ? intfParamPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntfParam)) {
            return false;
        }
        IntfParam other = (IntfParam) object;
        if ((this.intfParamPK == null && other.intfParamPK != null) || (this.intfParamPK != null && !this.intfParamPK.equals(other.intfParamPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.IntfParam[ intfParamPK=" + intfParamPK + " ]";
    }

}
