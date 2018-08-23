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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "res_detail")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "ResDetail.findAll", query = "SELECT r FROM ResDetail r"),
    @NamedQuery(name = "ResDetail.findByIvoid", query = "SELECT r FROM ResDetail r WHERE r.resDetailPK.ivoid = :ivoid"),
    @NamedQuery(name = "ResDetail.findByCapIndex", query = "SELECT r FROM ResDetail r WHERE r.resDetailPK.capIndex = :capIndex"),
    @NamedQuery(name = "ResDetail.findByDetailUtype", query = "SELECT r FROM ResDetail r WHERE r.resDetailPK.detailUtype = :detailUtype"),
    @NamedQuery(name = "ResDetail.findByDetailValue", query = "SELECT r FROM ResDetail r WHERE r.detailValue = :detailValue")})
public class ResDetail implements Serializable, PKIndex {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlPath(".")
    protected ResDetailPK resDetailPK;
    @Basic(optional = false)
    @Column(name = "detail_value", nullable = false, length = 256)
    @XmlElement(name = "detail_value")
    private String detailValue;
    @XmlTransient
    @JoinColumn(name = "ivoid", referencedColumnName = "ivoid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Resource resource;

    public ResDetail() {
        this.resDetailPK = new ResDetailPK();
    }

    public ResDetail(ResDetailPK resDetailPK) {
        this.resDetailPK = resDetailPK;
    }

    public ResDetail(ResDetailPK resDetailPK, String detailValue) {
        this.resDetailPK = resDetailPK;
        this.detailValue = detailValue;
    }

    public ResDetail(String ivoid, short capIndex, String detailUtype) {
        this.resDetailPK = new ResDetailPK(ivoid, capIndex, detailUtype);
    }

    public ResDetailPK getResDetailPK() {
        return resDetailPK;
    }

    public void setResDetailPK(ResDetailPK resDetailPK) {
        this.resDetailPK = resDetailPK;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }

    public Resource getResource() {
        return resource;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        this.resDetailPK.setIvoid(resource.getIvoid());
        this.resDetailPK.setCapIndex((short) -1);
        if(resource.getResDetailList().indexOf(this) == -1){
            resource.getResDetailList().add(this);
        }
    }
    
    public void addToCapability(Capability capability){
        this.resource = capability.getResource();
        if(resource.getResDetailList().indexOf(this) == -1){
            resource.getResDetailList().addAndSetIndex(this);
            
        }
        this.resDetailPK.setIvoid(resource.getIvoid());
   }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resDetailPK != null ? resDetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResDetail)) {
            return false;
        }
        ResDetail other = (ResDetail) object;
        if ((this.resDetailPK == null && other.resDetailPK != null) || (this.resDetailPK != null && !this.resDetailPK.equals(other.resDetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.ResDetail[ resDetailPK=" + resDetailPK + " ]";
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#getIndex()
     */
    @Override
    public short getIndex() {
        return this.resDetailPK.getCapIndex();
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#setPKIndex(short)
     */
    @Override
    public void setPKIndex(short idx) {
        this.resDetailPK.setCapIndex(idx);
    }

    /**
     * @param string
     */
    public void setDetailUtype(String string) {
       getResDetailPK().setDetailUtype(string);
    }

}
