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

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
@Table(name="subject")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Subject.findAll", query = "SELECT s FROM Subject s"),
    @NamedQuery(name = "Subject.findByIvoid", query = "SELECT s FROM Subject s WHERE s.subjectPK.ivoid = :ivoid"),
    @NamedQuery(name = "Subject.findBySubject", query = "SELECT s FROM Subject s WHERE s.subjectPK.subject = :subject")})
public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    @XmlElement
    protected SubjectPK subjectPK;
    @XmlTransient
    @JoinColumn(name = "ivoid", referencedColumnName = "ivoid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Resource resource;
    
    /**
     * 
     */
    Subject() {
        this.subjectPK = new SubjectPK();
    }

    public Subject(String Subject) {
        this.subjectPK = new SubjectPK();
        this.subjectPK.setSubject(Subject);
    }
   public SubjectPK getSubjectPK() {
        return subjectPK;
    }

    public void setSubjectPK(SubjectPK subjectPK) {
        this.subjectPK = subjectPK;
    }

    public Resource getResource() {
        return resource;
    }
    
    public void setSubject(String subject)
    {
        this.subjectPK.setSubject(subject);
    }

    public void addToResource(Resource resource) {
        this.resource = resource;
        if(resource.getSubjectList().indexOf(this) == -1)
        {
            resource.getSubjectList().add(this);
        }
        this.subjectPK.setIvoid(resource.getIvoid());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectPK != null ? subjectPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subject)) {
            return false;
        }
        Subject other = (Subject) object;
        if ((this.subjectPK == null && other.subjectPK != null) || (this.subjectPK != null && !this.subjectPK.equals(other.subjectPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.ivoa.regtap.Subject[ subjectPK=" + subjectPK + " ]";
    }

}
