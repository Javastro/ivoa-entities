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

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

//import org.eclipse.persistence.oxm.annotations.XmlPath;

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
    @NamedQuery(name = "TableColumn.findByTableIndex", query = "SELECT t FROM TableColumn t WHERE t.tableColumnPK.tableIndex = :tableIndex"),
    @NamedQuery(name = "TableColumn.findByName", query = "SELECT t FROM TableColumn t WHERE t.name = :name"),
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
    @XmlElement
    protected TableColumnPK tableColumnPK;
    @Basic(optional = false)
    @Column(name="name",nullable = false)
    private String name;
   @Basic(optional = true)
    @Column(name="std",nullable = true)
    @XmlElement(nillable = true)
    private short std;
   @Column(name="flag")
    private String flag;
    @Column(name="description",length=1024)
    private String description;
    @Column(name="unit")
    private String unit;
    @Column(name="ucd")
    private String ucd;
    @Column(name="utype")
    private String utype;
    @Basic(optional = false)
    @Column(name="datatype",nullable = false)
    private String datatype;
    @Column(name = "extended_schema")
    @XmlElement(name = "extended_schema")
    private String extendedSchema;
    @Column(name = "extended_type")
    @XmlElement(name = "extended_type")
    private String extendedType;
    @Column(name="arraysize")
    private String arraysize;
    @Column(name="delim")
    private String delim;
    @Column(name = "type_system")
    @XmlElement(name = "type_system")
    private String typeSystem;
   
    @XmlTransient
    @ManyToOne(optional = false)
    @JoinColumns({@JoinColumn(name = "ivoid", nullable = false, insertable = false, updatable = false, referencedColumnName = "ivoid"),
    @JoinColumn(name = "table_index", nullable = false, insertable = false, updatable = false, referencedColumnName = "table_index")
    })
    private ResTable table;

    TableColumn() {
        this.tableColumnPK = new TableColumnPK();
    }

    public TableColumn(String name, String type) {
        this.tableColumnPK = new TableColumnPK();
        this.name=(name);
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
        this.tableColumnPK.setTableIndex(tablePK.getTableIndex());
    }


    @Override
    public String toString() {
        return "net.ivoa.regtap.TableColumn[ tableColumnPK=" + tableColumnPK + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     * overrides @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((arraysize == null) ? 0 : arraysize.hashCode());
        result = prime * result
                + ((datatype == null) ? 0 : datatype.hashCode());
        result = prime * result + ((delim == null) ? 0 : delim.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result
                + ((extendedSchema == null) ? 0 : extendedSchema.hashCode());
        result = prime * result
                + ((extendedType == null) ? 0 : extendedType.hashCode());
        result = prime * result + ((flag == null) ? 0 : flag.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + std;
        result = prime * result
                + ((tableColumnPK == null) ? 0 : tableColumnPK.hashCode());
        result = prime * result
                + ((typeSystem == null) ? 0 : typeSystem.hashCode());
        result = prime * result + ((ucd == null) ? 0 : ucd.hashCode());
        result = prime * result + ((unit == null) ? 0 : unit.hashCode());
        result = prime * result + ((utype == null) ? 0 : utype.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * overrides @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TableColumn))
            return false;
        TableColumn other = (TableColumn) obj;
        if (arraysize == null) {
            if (other.arraysize != null)
                return false;
        } else if (!arraysize.equals(other.arraysize))
            return false;
        if (datatype == null) {
            if (other.datatype != null)
                return false;
        } else if (!datatype.equals(other.datatype))
            return false;
        if (delim == null) {
            if (other.delim != null)
                return false;
        } else if (!delim.equals(other.delim))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (extendedSchema == null) {
            if (other.extendedSchema != null)
                return false;
        } else if (!extendedSchema.equals(other.extendedSchema))
            return false;
        if (extendedType == null) {
            if (other.extendedType != null)
                return false;
        } else if (!extendedType.equals(other.extendedType))
            return false;
        if (flag == null) {
            if (other.flag != null)
                return false;
        } else if (!flag.equals(other.flag))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (std != other.std)
            return false;
        if (tableColumnPK == null) {
            if (other.tableColumnPK != null)
                return false;
        } else if (!tableColumnPK.equals(other.tableColumnPK))
            return false;
        if (typeSystem == null) {
            if (other.typeSystem != null)
                return false;
        } else if (!typeSystem.equals(other.typeSystem))
            return false;
        if (ucd == null) {
            if (other.ucd != null)
                return false;
        } else if (!ucd.equals(other.ucd))
            return false;
        if (unit == null) {
            if (other.unit != null)
                return false;
        } else if (!unit.equals(other.unit))
            return false;
        if (utype == null) {
            if (other.utype != null)
                return false;
        } else if (!utype.equals(other.utype))
            return false;
        return true;
    }

}
