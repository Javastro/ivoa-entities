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

import java.util.List;

import org.javastro.ivoa.entities.regtap.exceptions.IllegalOrphanException;
import org.javastro.ivoa.entities.regtap.exceptions.NonexistentEntityException;
import org.javastro.ivoa.entities.regtap.exceptions.PreexistingEntityException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 * A data access object for {@link Resource} that handles relationships between child objects.
 * @author Paul Harrison <paul.harrison@manchester.ac.uk> 04-Feb-2013
 */
@XmlTransient
public class ResourceJpaController  {

   private final EntityManagerFactory emf ;
    final EntityManager em ;
    public ResourceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
        this.em=emf.createEntityManager();
    }
   
    public void close()
    {
       
        em.close();
    }

    public void create(Resource resource) throws PreexistingEntityException, Exception {
        ;
        try {
            em.getTransaction().begin();
            em.persist(resource); // just rely on the cascade semantics to do the related elements
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            if (findResource(resource.getIvoid()) != null) {
                throw new PreexistingEntityException("Resource " + resource + " already exists.", ex);
            }
            throw ex;
        } 
    }

    public void edit(Resource resource) throws IllegalOrphanException, NonexistentEntityException, Exception {
        try {
           
            em.getTransaction().begin();
            resource = em.merge(resource);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = resource.getIvoid();
                if (findResource(id) == null) {
                    throw new NonexistentEntityException("The resource with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } 
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        
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
        
    }

    public List<Resource> findResourceEntities() {
        return findResourceEntities(true, -1, -1);
    }

    public List<Resource> findResourceEntities(int maxResults, int firstResult) {
        return findResourceEntities(false, maxResults, firstResult);
    }

    private List<Resource> findResourceEntities(boolean all, int maxResults, int firstResult) {
       
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resource.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
       
    }

    public Resource findResource(String id) {
       
            return em.find(Resource.class, id);
       
    }

    public int getResourceCount() {
       
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Resource> rt = cq.from(Resource.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        
    }

}
