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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Entity
@Table(name = "res_table")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "ResTable.findAll", query = "SELECT r FROM ResTable r"),
    @NamedQuery(name = "ResTable.findByIvoid", query = "SELECT r FROM ResTable r WHERE r.resTablePK.ivoid = :ivoid"),
    @NamedQuery(name = "ResTable.findBySchemaIndex", query = "SELECT r FROM ResTable r WHERE r.resTablePK.schemaIndex = :schemaIndex"),
    @NamedQuery(name = "ResTable.findByTableIndex", query = "SELECT r FROM ResTable r WHERE r.resTablePK.tableIndex = :tableIndex"),
    @NamedQuery(name = "ResTable.findByTableName", query = "SELECT r FROM ResTable r WHERE r.name = :tableName"),
    @NamedQuery(name = "ResTable.findByTableTitle", query = "SELECT r FROM ResTable r WHERE r.title = :tableTitle"),
    @NamedQuery(name = "ResTable.findByTableType", query = "SELECT r FROM ResTable r WHERE r.type = :tableType"),
    @NamedQuery(name = "ResTable.findByTableUtype", query = "SELECT r FROM ResTable r WHERE r.utype = :tableUtype"),
    @NamedQuery(name = "ResTable.findByTableDescription", query = "SELECT r FROM ResTable r WHERE r.description = :tableDescription")})
public class ResTable implements Serializable, PKIndex {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlPath(".")
    protected ResTablePK resTablePK;
    @Basic(optional = false)
    @Column(name = "table_name", nullable = false, length = 256)
    @XmlElement(name = "table_name")
    private String name;
    @Column(name = "table_title", length = 256)
    @XmlElement(name = "table_title")
    private String title;
    @Basic(optional = false)
    @Column(name = "table_type", nullable = false, length = 256)
    @XmlElement(name = "table_type")
    private String type;
    @Column(name = "table_utype", length = 256)
    @XmlElement(name = "table_utype")
    private String utype;
    @Column(name = "table_description", length = 256)
    @XmlElement(name = "table_description")
    private String description;
    
    @OneToMany(cascade = CascadeType.ALL, targetEntity=TableColumn.class, fetch= FetchType.LAZY, orphanRemoval=true, mappedBy = "table")
    private List<TableColumn> tableColumnList;
 
    @XmlTransient
    @ManyToOne(optional = false)
    @JoinColumns({@JoinColumn(name = "ivoid", nullable = false, insertable = false, updatable = false, referencedColumnName = "ivoid"),@JoinColumn(name = "schema_index", referencedColumnName = "schema_index", insertable = false, updatable = false, unique = true, nullable = false)})
    private ResSchema schema;

    ResTable() {
        this.resTablePK = new ResTablePK();
    }
    
    public ResTable(String name, String type) {
        this.name = name;
        this.type = type;
        this.resTablePK = new ResTablePK();
    }
    

    public ResTable(ResTablePK resTablePK) {
        this.resTablePK = resTablePK;
    }

    public ResTable(ResTablePK resTablePK, String tableName, String tableType) {
        this.resTablePK = resTablePK;
        this.name = tableName;
        this.type = tableType;
    }

    public ResTable(String ivoid, short schemaIndex, short tableIndex) {
        this.resTablePK = new ResTablePK(ivoid, schemaIndex, tableIndex);
    }

    public ResTablePK getResTablePK() {
        return resTablePK;
    }

    public void setResTablePK(ResTablePK resTablePK) {
        this.resTablePK = resTablePK;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TableColumn> getTableColumnList() {
        if (tableColumnList == null) tableColumnList = new ArrayList<TableColumn>();
        return tableColumnList;
    }

    public void setTableColumnList(List<TableColumn> tableColumnList) {
        this.tableColumnList = tableColumnList;
    }


    public ResSchema getSchema() {
        return schema;
    }

    public void addToSchema(ResSchema schema) {
        this.schema = schema;
        if (schema.getResTableList().indexOf(this) == -1) {
            schema.getResTableList().addAndSetIndex(this);
        }
        this.resTablePK.setIvoid(schema.getResSchemaPK().getIvoid());
        this.resTablePK.setSchemaIndex(schema.getIndex());
        
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resTablePK != null ? resTablePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResTable)) {
            return false;
        }
        ResTable other = (ResTable) object;
        if ((this.resTablePK == null && other.resTablePK != null) || (this.resTablePK != null && !this.resTablePK.equals(other.resTablePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.ResTable[ resTablePK=" + resTablePK + " ]";
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#getIndex()
     */
    @Override
    public short getIndex() {
        return this.resTablePK.getTableIndex();
    }

    /* (non-Javadoc)
     * @see net.ivoa.regtap.PKIndex#setPKIndex(short)
     */
    @Override
    public void setPKIndex(short idx) {
        this.resTablePK.setTableIndex(idx);
    }

}
