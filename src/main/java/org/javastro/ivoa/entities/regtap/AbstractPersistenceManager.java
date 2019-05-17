/*
 * Created on 16 May 2019 
 * Copyright 2019 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.regtap;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Persistence Manager for RegTap db.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 16 May 2019
 */
public abstract class AbstractPersistenceManager {
  private static final String PU = "org.javastro.ivoa.entities.regtap_pu";
  protected Map<String, String> props = new HashMap<>();

  
       /**
     * set connection properties in the props map;
     */
    abstract void setupConnectionProperties();
  
       public EntityManagerFactory createEmf()
       {
          return Persistence.createEntityManagerFactory(PU, props);
       }
}


