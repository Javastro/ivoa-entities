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
public class TableColumnPK implements Serializable {
    /** serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name="ivoid",nullable = false)
    private String ivoid;
    @Basic(optional = false)
    @Column(name = "table_index", nullable = false)
    @XmlElement(name = "table_index")
    private short tableIndex;

    public TableColumnPK() {
    }

    public TableColumnPK(String ivoid, short schemaIndex, short tableIndex, String name) {
        this.ivoid = ivoid;
        this.tableIndex = tableIndex;
    }

    public String getIvoid() {
        return ivoid;
    }

    public void setIvoid(String ivoid) {
        this.ivoid = ivoid;
    }

    public short getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(short tableIndex) {
        this.tableIndex = tableIndex;
    }

 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ivoid != null ? ivoid.hashCode() : 0);
        hash += (int) tableIndex;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TableColumnPK)) {
            return false;
        }
        TableColumnPK other = (TableColumnPK) object;
        if ((this.ivoid == null && other.ivoid != null) || (this.ivoid != null && !this.ivoid.equals(other.ivoid))) {
            return false;
        }
        if (this.tableIndex != other.tableIndex) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.TableColumnPK[ ivoid=" + ivoid +  ", tableIndex=" + tableIndex + " ]";
    }

}
