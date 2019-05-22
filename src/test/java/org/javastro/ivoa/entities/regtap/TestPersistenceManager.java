/*
 * Created on 16 May 2019 
 * Copyright 2019 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.regtap;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.config.TargetServer;

/**
 *  .
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 16 May 2019
 */
public class TestPersistenceManager extends AbstractPersistenceManager {

    /**
     * {@inheritDoc}
     * overrides @see org.javastro.ivoa.entities.regtap.AbstractPersistenceManager#setupConnectionProperties()
     */
    @Override
    void setupConnectionProperties() {
        //

        props.put(PersistenceUnitProperties.JDBC_URL, "jdbc:derby:memory:ivoatestdb;create=true");//IMPL only one db for every PU perhaps dangerous
//        props.put(PersistenceUnitProperties.JDBC_URL, "jdbc:derby:ivoatestdb;create=true;traceFile=derbytrace.out;traceLevel=-1;traceDirectory=/tmp");
        props.put(PersistenceUnitProperties.JDBC_DRIVER, "org.apache.derby.jdbc.EmbeddedDriver");
        props.put(PersistenceUnitProperties.TARGET_DATABASE, "org.eclipse.persistence.platform.database.DerbyPlatform");
        props.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_BOTH_GENERATION);
        props.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.DROP_AND_CREATE);
//        props.put(PersistenceUnitProperties.CACHE_SHARED_, "false");
        
     // Configure logging. FINE ensures all SQL is shown
        props.put(PersistenceUnitProperties.LOGGING_LEVEL, "FINE");
         
        // Ensure that no server-platform is configured
        props.put(PersistenceUnitProperties.TARGET_SERVER, TargetServer.None);
        
    }

}


