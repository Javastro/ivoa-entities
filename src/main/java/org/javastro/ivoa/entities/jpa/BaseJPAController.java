/*
 * $Id: BaseJPAController.java,v 1.2 2011-09-13 13:43:33 pah Exp $
 * 
 * Created on 21 Mar 2011 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2011 Astrogrid. All rights reserved.
 *
 * This software is published under the terms of the Astrogrid 
 * Software License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */

package org.javastro.ivoa.entities.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Base JPA Controller implementation that contains a default {@link EntityManagerFactory}.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 13 Sep 2011
 * @version $Revision: 1.2 $ $date$
 */
public abstract class BaseJPAController {

    protected static EntityManagerFactory  emf = Persistence.createEntityManagerFactory("net.ivoa_ivoa-objects");;
 
    public BaseJPAController() {
        
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    /**
     * Set an {@link EntityManagerFactory} if the default one does not suffice.
     * @param emf the emf to set
     */
    public static void setEmf(EntityManagerFactory emf) {
        BaseJPAController.emf = emf;
    }

}

/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1.2.2  2011/09/13 13:32:35  pah
 * mainly documentation updates
 *
 * Revision 1.1.2.1  2011/06/09 22:18:53  pah
 * basic VOResource schema nearly done - but not got save/recall working
 *
 */
