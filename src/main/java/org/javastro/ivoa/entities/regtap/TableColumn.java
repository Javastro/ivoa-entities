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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 *
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@Entity
@Table(name = "table_column")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "TableColumn.findAll", query = "SELECT t FROM TableColumn t"),
    @NamedQuery(name = "TableColumn.findByIvoid", query = "SELECT t FROM TableColumn t WHERE t.tableColumnPK.ivoid = :ivoid"),
    @NamedQuery(name = "TableColumn.findBySchemaIndex", query = "SELECT t FROM TableColumn t WHERE t.tableColumnPK.schemaIndex = :schemaIndex"),
    @NamedQuery(name = "TableColumn.findByTableIndex", query = "SELECT t FROM TableColumn t WHERE t.tableColumnPK.tableIndex = :tableIndex"),
    @NamedQuery(name = "TableColumn.findByName", query = "SELECT t FROM TableColumn t WHERE t.tableColumnPK.name = :name"),
    @NamedQuery(name = "TableColumn.findByDatatype", query = "SELECT t FROM TableColumn t WHERE t.datatype = :datatype"),
    @NamedQuery(name = "TableColumn.findByDescription", query = "SELECT t FROM TableColumn t WHERE t.description = :description"),
    @NamedQuery(name = "TableColumn.findByUcd", query = "SELECT t FROM TableColumn t WHERE t.ucd = :ucd"),
    @NamedQuery(name = "TableColumn.findByUnit", query = "SELECT t FROM TableColumn t WHERE t.unit = :unit"),
    @NamedQuery(name = "TableColumn.findByUtype", query = "SELECT t FROM TableColumn t WHERE t.utype = :utype"),
    @NamedQuery(name = "TableColumn.findByFlag", query = "SELECT t FROM TableColumn t WHERE t.flag = :flag"),
    @NamedQuery(name = "TableColumn.findByStd", query = "SELECT t FROM TableColumn t WHERE t.std = :std"),
    @NamedQuery(name = "TableColumn.findByExtendedSchema", query = "SELECT t FROM TableColumn t WHERE t.extendedSchema = :extendedSchema"),
    @NamedQuery(name = "TableColumn.findByExtendedType", query = "SELECT t FROM TableColumn t WHERE t.extendedType = :extendedType"),
    @NamedQuery(name = "TableColumn.findByArraysize", query = "SELECT t FROM TableColumn t WHERE t.arraysize = :arraysize"),
    @NamedQuery(name = "TableColumn.findByDelim", query = "SELECT t FROM TableColumn t WHERE t.delim = :delim"),
    @NamedQuery(name = "TableColumn.findByTypeSystem", query = "SELECT t FROM TableColumn t WHERE t.typeSystem = :typeSystem")})
public class TableColumn implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlPath(".")
    protected TableColumnPK tableColumnPK;
    @Basic(optional = false)
    @Column(nullable = false, length = 256)
    private String datatype;
    @Column(length = 256)
    private String description;
    @Column(length = 256)
    private String ucd;
    @Column(length = 256)
    private String unit;
    @Column(length = 256)
    private String utype;
    @Column(length = 256)
    private String flag;
    @Basic(optional = false)
    @Column(nullable = false)
    private short std;
    @Column(name = "extended_schema", length = 256)
    @XmlElement(name = "extended_schema")
    private String extendedSchema;
    @Column(name = "extended_type", length = 256)
    @XmlElement(name = "extended_type")
    private String extendedType;
    @Column(length = 256)
    private String arraysize;
    @Column(length = 256)
    private String delim;
    @Column(name = "type_system", length = 256)
    @XmlElement(name = "type_system")
    private String typeSystem;
    @XmlTransient
    @ManyToOne(optional = false)
    @JoinColumns({@JoinColumn(name = "ivoid", nullable = false, insertable = false, updatable = false, referencedColumnName = "ivoid"),
    @JoinColumn(name = "schema_index", nullable = false, insertable = false, updatable = false, referencedColumnName = "schema_index"),
    @JoinColumn(name = "table_index", nullable = false, insertable = false, updatable = false, referencedColumnName = "table_index")
    })
    private ResTable table;

    TableColumn() {
        this.tableColumnPK = new TableColumnPK();
    }

    public TableColumn(String name, String type) {
        this.tableColumnPK = new TableColumnPK();
        this.tableColumnPK.setName(name);
        this.datatype = type;
       
    }
    public TableColumn(TableColumnPK tableColumnPK) {
        this.tableColumnPK = tableColumnPK;
    }

    public TableColumn(TableColumnPK tableColumnPK, String name, String datatype, short std) {
        this.tableColumnPK = tableColumnPK;
        this.datatype = datatype;
        this.std = std;
    }

    public TableColumn(String ivoid, short schemaIndex, short tableIndex, String name) {
        this.tableColumnPK = new TableColumnPK(ivoid, schemaIndex, tableIndex, name);
    }

    public TableColumnPK getTableColumnPK() {
        return tableColumnPK;
    }

    public void setTableColumnPK(TableColumnPK tableColumnPK) {
        this.tableColumnPK = tableColumnPK;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getArraysize() {
        return arraysize;
    }

    public void setArraysize(String arraysize) {
        this.arraysize = arraysize;
    }

    public String getDelim() {
        return delim;
    }

    public void setDelim(String delim) {
        this.delim = delim;
    }

    public String getTypeSystem() {
        return typeSystem;
    }

    public void setTypeSystem(String typeSystem) {
        this.typeSystem = typeSystem;
    }


    public ResTable getTable() {
        return table;
    }

    public void addToTable(ResTable table) {
        this.table = table;
        if (table.getTableColumnList().indexOf(this) == -1) {
            table.getTableColumnList().add(this);
        }
        ResTablePK tablePK = table.getResTablePK();
        this.tableColumnPK.setIvoid(tablePK.getIvoid());
        this.tableColumnPK.setSchemaIndex(tablePK.getSchemaIndex());
        this.tableColumnPK.setTableIndex(tablePK.getTableIndex());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tableColumnPK != null ? tableColumnPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TableColumn)) {
            return false;
        }
        TableColumn other = (TableColumn) object;
        if ((this.tableColumnPK == null && other.tableColumnPK != null) || (this.tableColumnPK != null && !this.tableColumnPK.equals(other.tableColumnPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.TableColumn[ tableColumnPK=" + tableColumnPK + " ]";
    }

}
