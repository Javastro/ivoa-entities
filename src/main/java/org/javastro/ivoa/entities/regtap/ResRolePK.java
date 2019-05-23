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
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class ResRolePK implements Serializable {
    @Basic(optional = false)
    @Column(name="ivoid",nullable = false)
    private String ivoid;
    @Basic(optional = false)
    @Column(name = "role_ivoid", nullable = false)
    @XmlElement(name = "role_ivoid")
    private String roleIvoid;
    @Basic(optional = false)
    @Column(name = "base_utype", nullable = false)
    @XmlElement(name = "base_role")
    private String baseRole;

    public ResRolePK() {
    }

    public ResRolePK(String ivoid, String roleIvoid, String baseUtype) {
        this.ivoid = ivoid;
        this.roleIvoid = roleIvoid;
        this.baseRole = baseUtype;
    }

    public String getIvoid() {
        return ivoid;
    }

    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
    }

    public String getRoleIvoid() {
        return roleIvoid;
    }

    public void setRoleIvoid(String roleIvoid) {
        this.roleIvoid = roleIvoid;
    }


    /**
     * @return the baseRole
     */
    public String getBaseRole() {
        return baseRole;
    }

    /**
     * @param baseRole the baseRole to set
     */
    public void setBaseRole(String baseRole) {
        this.baseRole = baseRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        hash += (roleIvoid != null ? roleIvoid.hashCode() : 0);
        hash += (baseRole != null ? baseRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResRolePK)) {
            return false;
        }
        ResRolePK other = (ResRolePK) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        if ((this.roleIvoid == null && other.roleIvoid != null) || (this.roleIvoid != null && !this.roleIvoid.equals(other.roleIvoid))) {
            return false;
        }
        if ((this.baseRole == null && other.baseRole != null) || (this.baseRole != null && !this.baseRole.equals(other.baseRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.ResRolePK[ ivoid=" + ivoid + ", roleIvoid=" + roleIvoid + ", baseUtype=" + baseRole + " ]";
    }

}
