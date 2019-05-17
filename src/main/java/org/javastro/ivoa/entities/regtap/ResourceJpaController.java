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
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.xml.bind.annotation.XmlTransient;

import org.javastro.ivoa.entities.regtap.exceptions.IllegalOrphanException;
import org.javastro.ivoa.entities.regtap.exceptions.NonexistentEntityException;
import org.javastro.ivoa.entities.regtap.exceptions.PreexistingEntityException;

/**
 * A data access object for {@link Resource} that handles relationships between child objects.
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@XmlTransient
public class ResourceJpaController implements Serializable {


    public ResourceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Resource resource) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = createEntityManager();
            em.getTransaction().begin();
            em.persist(resource); // just rely on the cascade semantics to do the related elements
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findResource(resource.getIvoid()) != null) {
                throw new PreexistingEntityException("Resource " + resource + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Resource resource) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = createEntityManager();
            em.getTransaction().begin();
            Resource persistentResource = em.find(Resource.class, resource.getIvoid());
            persistentResource.getIvoid();

            for (Validation validationListNewValidationToAttach : resource.getValidationList()) {
                if (em.find(validationListNewValidationToAttach.getClass(), validationListNewValidationToAttach.getValidationPK())
                        == null)
                {
                    em.persist(validationListNewValidationToAttach);
                }
            }
            for (Subject subjectListNewSubjectToAttach : resource.getSubjectList()) {
                if (em.find(subjectListNewSubjectToAttach.getClass(), subjectListNewSubjectToAttach.getSubjectPK())
                        == null)
                {
                    em.persist(subjectListNewSubjectToAttach);
                }
            }
            for (ResDetail resDetailListNewResDetailToAttach : resource.getResDetailList()) {
                if (em.find(resDetailListNewResDetailToAttach.getClass(), resDetailListNewResDetailToAttach.getResDetailPK())
                        == null)
                {
                    em.persist(resDetailListNewResDetailToAttach);
                }
            }
            for (ResSchema resSchemaListNewResSchemaToAttach : resource.getResSchemaList()) {
                for (ResTable table : resSchemaListNewResSchemaToAttach.getResTableList()) {
                    for (TableColumn col : table.getTableColumnList()) {
                        if(em.find(col.getClass(), col.getTableColumnPK()) == null)
                        {
                            em.persist(col);
                        }
                    }
                    if (em.find(table.getClass(), table.getResTablePK()) == null) {
                        em.persist(table);
                    }
                    
                }
                if (em.find(resSchemaListNewResSchemaToAttach.getClass(), resSchemaListNewResSchemaToAttach.getResSchemaPK())
                        == null)
                {
                    em.persist(resSchemaListNewResSchemaToAttach);
                }
            }
            for (Date dateListNewDateToAttach : resource.getDateList()) {
                if (em.find(dateListNewDateToAttach.getClass(), dateListNewDateToAttach.getDatePK())
                        == null)
                {
                    em.persist(dateListNewDateToAttach);
                }
            }
            for (Relationship relationshipListNewRelationshipToAttach : resource.getRelationshipList()) {
                if (em.find(relationshipListNewRelationshipToAttach.getClass(), relationshipListNewRelationshipToAttach.getRelationshipPK())
                        == null)
                {
                    em.persist(relationshipListNewRelationshipToAttach);
                }
            }
            for (ResRole resRoleListNewResRoleToAttach : resource.getResRoleList()) {
                if (em.find(resRoleListNewResRoleToAttach.getClass(), resRoleListNewResRoleToAttach.getResRolePK())
                        == null)
                {
                    em.persist(resRoleListNewResRoleToAttach);
                }
            }
     
            //add new capabilities
            for (Capability capabilityListNewCapabilityToAttach : resource.getCapabilityList()) {
                for (Interface  iface : capabilityListNewCapabilityToAttach.getInterfaceList()) {
                    for (IntfParam param : iface.getIntfParamList()) {
                        if (em.find(param.getClass(), param.getIntfParamPK()) == null)
                        {
                            em.persist(param);
                        }
                    }
                    if (em.find(iface.getClass(), iface.getInterfacePK()) == null) {
                        em.persist(iface);
                    }
                }
                if( em.find(capabilityListNewCapabilityToAttach.getClass(), capabilityListNewCapabilityToAttach.getCapabilityPK())
                        == null)
                {
                    em.persist(capabilityListNewCapabilityToAttach);
                }
            }

            resource = em.merge(resource);


            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = resource.getIvoid();
                if (findResource(id) == null) {
                    throw new NonexistentEntityException("The resource with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = createEntityManager();
            em.getTransaction().begin();
            Resource resource;
            try {
                resource = em.getReference(Resource.class, id);
                resource.getIvoid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resource with id " + id + " no longer exists.", enfe);
            }
            em.remove(resource);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Resource> findResourceEntities() {
        return findResourceEntities(true, -1, -1);
    }

    public List<Resource> findResourceEntities(int maxResults, int firstResult) {
        return findResourceEntities(false, maxResults, firstResult);
    }

    private List<Resource> findResourceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resource.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Resource findResource(String id) {
        EntityManager em = createEntityManager();
        try {
            return em.find(Resource.class, id);
        } finally {
            em.close();
        }
    }

    public int getResourceCount() {
        EntityManager em = createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Resource> rt = cq.from(Resource.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
