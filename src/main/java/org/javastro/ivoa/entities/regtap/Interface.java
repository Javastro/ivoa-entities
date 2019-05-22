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

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name="interface")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Interface.findAll", query = "SELECT i FROM Interface i"),
    @NamedQuery(name = "Interface.findByIvoid", query = "SELECT i FROM Interface i WHERE i.interfacePK.ivoid = :ivoid"),
    @NamedQuery(name = "Interface.findByCapIndex", query = "SELECT i FROM Interface i WHERE i.capIndex = :capIndex"),
    @NamedQuery(name = "Interface.findByIntfIndex", query = "SELECT i FROM Interface i WHERE i.interfacePK.intfIndex = :intfIndex"),
    @NamedQuery(name = "Interface.findByIntfType", query = "SELECT i FROM Interface i WHERE i.intfType = :intfType"),
    @NamedQuery(name = "Interface.findByIntfRole", query = "SELECT i FROM Interface i WHERE i.intfRole = :intfRole"),
    @NamedQuery(name = "Interface.findByStdVersion", query = "SELECT i FROM Interface i WHERE i.stdVersion = :stdVersion"),
    @NamedQuery(name = "Interface.findByQueryType", query = "SELECT i FROM Interface i WHERE i.queryType = :queryType"),
    @NamedQuery(name = "Interface.findByResultType", query = "SELECT i FROM Interface i WHERE i.resultType = :resultType"),
    @NamedQuery(name = "Interface.findByWsdlUrl", query = "SELECT i FROM Interface i WHERE i.wsdlUrl = :wsdlUrl"),
    @NamedQuery(name = "Interface.findByUrlUse", query = "SELECT i FROM Interface i WHERE i.urlUse = :urlUse"),
    @NamedQuery(name = "Interface.findByAccessUrl", query = "SELECT i FROM Interface i WHERE i.accessUrl = :accessUrl")})
public class Interface implements Serializable,PKIndex {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlPath(".")
    protected InterfacePK interfacePK;
    @Basic(optional = false)
    @Column(name = "cap_index", nullable = false)
    @XmlElement(name = "cap_index")
    private short capIndex;
    @Column(name = "intf_type", length = 256)
    @XmlElement(name = "intf_type")
    private String intfType;
    @Column(name = "intf_role", length = 256)
    @XmlElement(name = "intf_role")
    private String intfRole;
    @Column(name = "std_version", length = 256)
    @XmlElement(name = "std_version")
    private String stdVersion;
    @Column(name = "query_type", length = 256)
    @XmlElement(name = "query_type")
    private String queryType;
    @Column(name = "result_type", length = 256)
    @XmlElement(name = "result_type")
    private String resultType;
    @Column(name = "wsdl_url", length = 256)
    @XmlElement(name = "wsdlURL")
    private String wsdlUrl;
    @Basic(optional = false)
    @Column(name = "url_use", nullable = false, length = 256)
    @XmlElement(name = "url_use")
    private String urlUse;
    @Basic(optional = false)
    @Column(name = "access_url", nullable = false, length = 256)
    @XmlElement(name = "access_url")
    private String accessUrl;
 
    @Basic(optional = true)
    @Column(name = "mirror_url", nullable = true, length = 256)
    @XmlElement(name = "mirror_url")
    private String mirrorUrl;
    
    @Basic(optional = false)
    @Column(name = "authenticated_only", nullable = false)
    @XmlElement(name = "authenticated_only")
    private int authenticatedOnly;

    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iface", targetEntity=IntfParam.class, fetch= FetchType.LAZY, orphanRemoval=true)
    @XmlElement(name = "param")
    private List<IntfParam> intfParamList;

    @XmlTransient
    @ManyToOne(optional = false)
    @JoinColumns({@JoinColumn(name = "ivoid", nullable = false, insertable = false, updatable = false, referencedColumnName = "ivoid"),@JoinColumn(name = "cap_index", referencedColumnName = "cap_index", insertable = false, updatable = false, nullable = false)})
    private Capability capability;

    public Interface() {
        this.interfacePK = new InterfacePK();
    }

    public Interface(InterfacePK interfacePK) {
        this.interfacePK = interfacePK;
    }

    public Interface(InterfacePK interfacePK, String urlUse, String accessUrl) {
        this.interfacePK = interfacePK;
        this.urlUse = urlUse;
        this.accessUrl = accessUrl;
    }

    public Interface(String ivoid, short capIndex, short intfIndex) {
        this.interfacePK = new InterfacePK(ivoid, intfIndex);
        this.capIndex = capIndex;
    }

    /**
     * @param vr
     * @param cap
     * @param s
     * @param string
     * @param string2
     */
    public Interface(Resource vr, Capability cap, short s, String urlUse, String accessUrl) {
        this(new InterfacePK(vr.getIvoid(),s),urlUse, accessUrl);
        this.capIndex =  cap.getCapabilityPK().getCapIndex();
    }

    public InterfacePK getInterfacePK() {
        return interfacePK;
    }

    public void setInterfacePK(InterfacePK interfacePK) {
        this.interfacePK = interfacePK;
    }

    public String getIntfType() {
        return intfType;
    }

    public void setIntfType(String intfType) {
        this.intfType = intfType;
    }

    public String getIntfRole() {
        return intfRole;
    }

    public void setIntfRole(String intfRole) {
        this.intfRole = intfRole;
    }

    public String getStdVersion() {
        return stdVersion;
    }

    public void setStdVersion(String stdVersion) {
        this.stdVersion = stdVersion;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getWsdlUrl() {
        return wsdlUrl;
    }

    public void setWsdlUrl(String wsdlUrl) {
        this.wsdlUrl = wsdlUrl;
    }

    public String getUrlUse() {
        return urlUse;
    }

    public void setUrlUse(String urlUse) {
        this.urlUse = urlUse;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public List<IntfParam> getIntfParamList() {
        if(intfParamList == null) intfParamList = new ArrayList<IntfParam>();
        return intfParamList;
    }

    public void setIntfParamList(List<IntfParam> intfParamList) {
        this.intfParamList = intfParamList;
    }

    public Capability getCapability() {
        return capability;
    }

    public void addToCapability(Capability capability) {
        this.capability = capability;
        if( capability.getInterfaceList().indexOf(this) == -1)
        {
            capability.getInterfaceList().addAndSetIndex(this);
        }
        this.setCapIndex(capability.getIndex());
        this.interfacePK.setIvoid(capability.getCapabilityPK().getIvoid());
    }

    /**
     * @return the capIndex
     */
    public short getCapIndex() {
        return capIndex;
    }

    /**
     * @param capIndex the capIndex to set
     */
    public void setCapIndex(short capIndex) {
        this.capIndex = capIndex;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (interfacePK != null ? interfacePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interface)) {
            return false;
        }
        Interface other = (Interface) object;
        if ((this.interfacePK == null && other.interfacePK != null) || (this.interfacePK != null && !this.interfacePK.equals(other.interfacePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.Interface[ interfacePK=" + interfacePK + " ]";
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#getIndex()
     */
    @Override
    public short getIndex() {
       return this.interfacePK.getIntfIndex();
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#setPKIndex(short)
     */
    @Override
    public void setPKIndex(short idx) {
        this.interfacePK.setIntfIndex(idx);
    }

}
