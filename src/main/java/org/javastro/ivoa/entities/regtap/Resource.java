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
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Entity
@Table(name="resource")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="resource", propOrder={"ivoid", "altIdentifier", "resType","created","updated","status","shortName","resTitle","contentLevel",
        "resDescription", "referenceUrl", "creator", "contentType", "sourceFormat", "sourceValue", "version",
        "regionOfRegard", "waveband",  "rights", "rightsURI",
        "resRoleList", "subjectList", "capabilityList",
        "validationList", "relationshipList",  "dateList", "resSchemaList", "resTableList",
         "resDetailList"
        })
@NamedQueries({
    @NamedQuery(name = "Resource.findAll", query = "SELECT r FROM Resource r"),
    @NamedQuery(name = "Resource.findByIvoid", query = "SELECT r FROM Resource r WHERE r.ivoid = :ivoid"),
    @NamedQuery(name = "Resource.findByResType", query = "SELECT r FROM Resource r WHERE r.resType = :resType"),
    @NamedQuery(name = "Resource.findByCreated", query = "SELECT r FROM Resource r WHERE r.created = :created"),
    @NamedQuery(name = "Resource.findByUpdated", query = "SELECT r FROM Resource r WHERE r.updated = :updated"),
    @NamedQuery(name = "Resource.findByStatus", query = "SELECT r FROM Resource r WHERE r.status = :status"),
    @NamedQuery(name = "Resource.findByShortName", query = "SELECT r FROM Resource r WHERE r.shortName = :shortName"),
    @NamedQuery(name = "Resource.findByResTitle", query = "SELECT r FROM Resource r WHERE r.resTitle = :resTitle"),
    @NamedQuery(name = "Resource.findByContentLevel", query = "SELECT r FROM Resource r WHERE r.contentLevel = :contentLevel"),
    @NamedQuery(name = "Resource.findByResDescription", query = "SELECT r FROM Resource r WHERE r.resDescription = :resDescription"),
    @NamedQuery(name = "Resource.findByReferenceUrl", query = "SELECT r FROM Resource r WHERE r.referenceUrl = :referenceUrl"),
    @NamedQuery(name = "Resource.findByContentType", query = "SELECT r FROM Resource r WHERE r.contentType = :contentType"),
    @NamedQuery(name = "Resource.findBySourceFormat", query = "SELECT r FROM Resource r WHERE r.sourceFormat = :sourceFormat"),
    @NamedQuery(name = "Resource.findBySourceValue", query = "SELECT r FROM Resource r WHERE r.sourceValue = :sourceValue"),
    @NamedQuery(name = "Resource.findByVersion", query = "SELECT r FROM Resource r WHERE r.version = :version"),
    @NamedQuery(name = "Resource.findByRegionOfRegard", query = "SELECT r FROM Resource r WHERE r.regionOfRegard = :regionOfRegard"),
    @NamedQuery(name = "Resource.findByWaveband", query = "SELECT r FROM Resource r WHERE r.waveband = :waveband"),
    @NamedQuery(name = "Resource.findByRights", query = "SELECT r FROM Resource r WHERE r.rights = :rights")
    })
public class Resource implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name="ivoid", nullable = false)
    private String ivoid;
    
    @XmlElement(type=AltIdentifier.class, name="alt_identifier")
    @XmlElementWrapper(name="altids")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource", targetEntity=AltIdentifier.class, fetch= FetchType.LAZY, orphanRemoval=true)
    private List<AltIdentifier> altIdentifier;
    
    @Basic(optional = false)
    @Column(name = "res_type", nullable = false)
    @XmlElement(name = "res_type")
    private String resType;
    @Basic(optional = false)
    @Column(name="created",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name="updated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    @Basic(optional = false)
    @Column(name="status",nullable = false)
    private String status;
    @Basic(optional = false)
    @Column(name = "short_name", nullable = false)
    @XmlElement(name = "short_name")
    private String shortName;
    @Basic(optional = false)
    @Column(name = "res_title", nullable = false)
    @XmlElement(name = "res_title")
    private String resTitle;
    @Column(name = "content_level")
    @XmlElement(name = "content_level")
    private String contentLevel;
    @Basic(optional = false)
    @Column(name = "res_description",length=1024, nullable = false)
    @XmlElement(name = "res_description")
    private String resDescription;
    @Basic(optional = false)
    @Column(name = "reference_url", nullable = false)
    @XmlElement(name = "reference_url")
    private String referenceUrl;
    
    @Basic(optional = false)
    @Column(name="creator_seq")
    @XmlElement(name="creator_seq")
    private String creator;
    @Column(name = "content_type")
    @XmlElement(name = "content_type")
    private String contentType;
    @Column(name = "source_format")
    @XmlElement(name = "source_format")
    private String sourceFormat;
    @Column(name = "source_value")
    @XmlElement(name = "source_value")
    private String sourceValue;
    @Column(name="res_vesion")
    @XmlElement(name="res_version")
    private String version;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "region_of_regard", precision = 22)
    @XmlElement(name = "region_of_regard")
    private Double regionOfRegard;
    @Column(name="waveband")
    private String waveband;
    @Column(name="rights")
    private String rights;
    @Column(name="rights_uri")
    @XmlElement(name="rights_uri")
    private String rightsURI;
    //IMPL would be nice (more efficient) to make the fetchtype LAZY - but does not appear easy/possible
    @XmlElement(type=Validation.class, name="validation")
    @XmlElementWrapper(name="validationList")
    @ElementCollection
    @CollectionTable(name="validation")
    private List<Validation> validationList;
    
    @XmlElement(type=Subject.class, name="subject")
    @XmlElementWrapper(name="subjects")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource", targetEntity=Subject.class, fetch= FetchType.LAZY, orphanRemoval=true)
    private List<Subject> subjectList;
    
    @XmlElement(type=ResDetail.class, name="detail")
    @XmlElementWrapper(name="details")
    @ElementCollection
    @CollectionTable(name = "res_detail",joinColumns = @JoinColumn(name="ivoid"))
    private List<ResDetail> resDetailList;
    
    @XmlElement(type=ResSchema.class, name="schema")
    @XmlElementWrapper(name="schemata")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource", targetEntity=ResSchema.class, fetch= FetchType.EAGER, orphanRemoval=true)
    private PKIndexList<ResSchema> resSchemaList;
    
    @XmlElement(type=org.javastro.ivoa.entities.regtap.Date.class, name="date")
    @XmlElementWrapper(name="dates")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource", targetEntity=org.javastro.ivoa.entities.regtap.Date.class, fetch= FetchType.EAGER, orphanRemoval=true)
    private List<org.javastro.ivoa.entities.regtap.Date> dateList;
    
    @XmlElement(type=Relationship.class, name="relationship")
    @XmlElementWrapper(name="relationships")
    @ElementCollection
    @CollectionTable(name="relationship", joinColumns = @JoinColumn(name="ivoid"))
    private List<Relationship> relationshipList;
    
    @XmlElement(type=ResRole.class, name="role")
    @XmlElementWrapper(name="roles")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource", targetEntity=ResRole.class, fetch= FetchType.LAZY, orphanRemoval=true)
    private List<ResRole> resRoleList;
    
    @XmlElement(type=Capability.class, name="capability")
    @XmlElementWrapper(name="capabilities")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource", targetEntity=Capability.class, fetch= FetchType.EAGER, orphanRemoval=true)
    private PKIndexList<Capability> capabilityList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource", targetEntity=ResTable.class, fetch= FetchType.EAGER, orphanRemoval=true)
    @XmlElementWrapper(name="tables")
    @XmlElement(name = "table")
    private PKIndexList<ResTable> resTableList;

    
    public Resource() {
    }

    public Resource(String ivoid) {
        this.ivoid = ivoid;
    }

    public Resource(String ivoid, String resType, Date created, Date updated, String status, String shortName, String resTitle, String resDescription, String referenceUrl) {
        this.ivoid = ivoid;
        this.resType = resType;
        this.created = created;
        this.updated = updated;
        this.status = status;
        this.shortName = shortName;
        this.resTitle = resTitle;
        this.resDescription = resDescription;
        this.referenceUrl = referenceUrl;
    }

    public String getIvoid() {
        return ivoid;
    }

    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
    }

    /**
     * @return the altIdentifier
     */
    public List<AltIdentifier> getAltIdentifier() {
        return altIdentifier;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getResTitle() {
        return resTitle;
    }

    public void setResTitle(String resTitle) {
        this.resTitle = resTitle;
    }

    public String getContentLevel() {
        return contentLevel;
    }

    public void setContentLevel(String contentLevel) {
        this.contentLevel = contentLevel;
    }

    public String getResDescription() {
        return resDescription;
    }

    public void setResDescription(String resDescription) {
        this.resDescription = resDescription;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSourceFormat() {
        return sourceFormat;
    }

    public void setSourceFormat(String sourceFormat) {
        this.sourceFormat = sourceFormat;
    }

    public String getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Double getRegionOfRegard() {
        return regionOfRegard;
    }

    public void setRegionOfRegard(Double regionOfRegard) {
        this.regionOfRegard = regionOfRegard;
    }

    public String getWaveband() {
        return waveband;
    }

    public void setWaveband(String waveband) {
        this.waveband = waveband;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    @XmlTransient
    public List<Validation> getValidationList() {
        if(validationList == null) validationList = new ArrayList<>();
        return validationList;
    }

 
    @XmlTransient
    public List<Subject> getSubjectList() {
        if (subjectList == null) subjectList = new ArrayList<Subject>();
        return subjectList;
    }

 
    @XmlTransient
    public List<ResDetail> getResDetailList() {
        if (resDetailList == null) resDetailList = new  ArrayList<>();
        return resDetailList;
    }

 
    @XmlTransient
    public PKIndexList<ResSchema> getResSchemaList() {
        if(resSchemaList == null ) resSchemaList = new PKIndexList<ResSchema>();
        return resSchemaList;
    }


    @XmlTransient
    public List<org.javastro.ivoa.entities.regtap.Date> getDateList() {
        if (dateList== null) dateList = new ArrayList<org.javastro.ivoa.entities.regtap.Date>();
        return dateList;
    }

 

    @XmlTransient
    public List<Relationship> getRelationshipList() {
        if(relationshipList == null) relationshipList = new ArrayList<Relationship>();
        return relationshipList;
    }

 
    @XmlTransient
    public List<ResRole> getResRoleList() {
        if (resRoleList == null) resRoleList = new ArrayList<ResRole>();
        return resRoleList;
    }

 
    @XmlTransient
    public PKIndexList<Capability> getCapabilityList() {
        if(capabilityList == null){
            capabilityList = new PKIndexList<Capability>();
        }
        return capabilityList;
    }
    @XmlTransient
    public PKIndexList<ResTable> getResTableList() {
        if (resTableList == null) resTableList = new PKIndexList<ResTable>();
        return resTableList;
    }

 
   @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resource)) {
            return false;
        }
        Resource other = (Resource) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.Resource[ ivoid=" + ivoid + " ]";
    }

    /**
     * @return the rightsURI
     */
    public String getRightsURI() {
        return rightsURI;
    }

    /**
     * @param rightsURI the rightsURI to set
     */
    public void setRightsURI(String rightsURI) {
        this.rightsURI = rightsURI;
    }

}
