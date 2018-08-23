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
@Table(name = "res_role")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "ResRole.findAll", query = "SELECT r FROM ResRole r"),
    @NamedQuery(name = "ResRole.findByIvoid", query = "SELECT r FROM ResRole r WHERE r.resRolePK.ivoid = :ivoid"),
    @NamedQuery(name = "ResRole.findByRoleName", query = "SELECT r FROM ResRole r WHERE r.roleName = :roleName"),
    @NamedQuery(name = "ResRole.findByRoleIvoid", query = "SELECT r FROM ResRole r WHERE r.resRolePK.roleIvoid = :roleIvoid"),
    @NamedQuery(name = "ResRole.findByBaseRole", query = "SELECT r FROM ResRole r WHERE r.resRolePK.baseRole = :baseRole"),
    @NamedQuery(name = "ResRole.findByAddress", query = "SELECT r FROM ResRole r WHERE r.address = :address"),
    @NamedQuery(name = "ResRole.findByEmail", query = "SELECT r FROM ResRole r WHERE r.email = :email"),
    @NamedQuery(name = "ResRole.findByTelephone", query = "SELECT r FROM ResRole r WHERE r.telephone = :telephone"),
    @NamedQuery(name = "ResRole.findByLogo", query = "SELECT r FROM ResRole r WHERE r.logo = :logo")})
public class ResRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlPath(".")
    protected ResRolePK resRolePK;
    @Basic(optional = false)
    @Column(name = "role_name", nullable = false, length = 256)
    @XmlElement(name = "role_name")
    private String roleName;
    @Column(length = 256)
    private String address;
    @Column(length = 256)
    private String email;
    @Column(length = 256)
    private String telephone;
    @Column(length = 256)
    private String logo;
    @XmlTransient
    @JoinColumn(name = "ivoid", referencedColumnName = "ivoid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Resource resource;

    public ResRole() {
        this.resRolePK = new ResRolePK();
    }

    public ResRole(ResRolePK resRolePK) {
        this.resRolePK = resRolePK;
    }

    public ResRole(ResRolePK resRolePK, String roleName) {
        this.resRolePK = resRolePK;
        this.roleName = roleName;
    }

    public ResRole(String ivoid, String roleIvoid, String baseUtype) {
        this.resRolePK = new ResRolePK(ivoid, roleIvoid, baseUtype);
    }

    public ResRolePK getResRolePK() {
        return resRolePK;
    }

    public void setResRolePK(ResRolePK resRolePK) {
        this.resRolePK = resRolePK;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Resource getResource() {
        return resource;
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        this.resRolePK.setIvoid(resource.getIvoid());
        if(resource.getResRoleList().indexOf(this) == -1){
            resource.getResRoleList().add(this);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resRolePK != null ? resRolePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResRole)) {
            return false;
        }
        ResRole other = (ResRole) object;
        if ((this.resRolePK == null && other.resRolePK != null) || (this.resRolePK != null && !this.resRolePK.equals(other.resRolePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.ResRole[ resRolePK=" + resRolePK + " ]";
    }

    /**
     * @param string
     */
    public void setRoleIvoid(String string) {
        this.resRolePK.setRoleIvoid(string);
    }

    /**
     * @param string
     */
    public void setRoleBaseRole(String string) {
        this.resRolePK.setBaseRole(string);
    }

}
