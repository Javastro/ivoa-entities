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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name="res_date")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Date.findAll", query = "SELECT d FROM Date d"),
    @NamedQuery(name = "Date.findByIvoid", query = "SELECT d FROM Date d WHERE d.datePK.ivoid = :ivoid"),
    @NamedQuery(name = "Date.findByDateValue", query = "SELECT d FROM Date d WHERE d.dateValue = :dateValue"),
    @NamedQuery(name = "Date.findByValueRole", query = "SELECT d FROM Date d WHERE d.datePK.valueRole = :valueRole")})
public class Date implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlPath(".")
    protected DatePK datePK;
    @Basic(optional = false)
    @Column(name = "date_value", nullable = false)
    @XmlElement(name = "date_value")
    @Temporal(TemporalType.DATE)
    private java.util.Date dateValue;
    @XmlTransient
    @JoinColumn(name = "ivoid", referencedColumnName = "ivoid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Resource resource;

    public Date() {
    }

    public Date(DatePK datePK) {
        this.datePK = datePK;
    }

    public Date(DatePK datePK, java.util.Date dateValue) {
        this.datePK = datePK;
        this.dateValue = dateValue;
    }

    public Date(String ivoid, String valueRole) {
        this.datePK = new DatePK(ivoid, valueRole);
    }

    public DatePK getDatePK() {
        return datePK;
    }

    public void setDatePK(DatePK datePK) {
        this.datePK = datePK;
    }

    public java.util.Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(java.util.Date dateValue) {
        this.dateValue = dateValue;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datePK != null ? datePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Date)) {
            return false;
        }
        Date other = (Date) object;
        if ((this.datePK == null && other.datePK != null) || (this.datePK != null && !this.datePK.equals(other.datePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.Date[ datePK=" + datePK + " ]";
    }

}
