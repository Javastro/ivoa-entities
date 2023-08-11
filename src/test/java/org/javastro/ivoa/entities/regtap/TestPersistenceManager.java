/*
 * Created on 16 May 2019 
 * Copyright 2019 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.regtap;


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
    props.put("jakarta.persistence.jdbc.url", "jdbc:h2:mem:regtap;DB_CLOSE_DELAY=-1");
        props.put("jakarta.persistence.jdbc.driver", "org.h2.Driver");
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.put("hibernate.hbm2ddl.schema-generation.script.append", "false");
//        props.put("jakarta.persistence.jdbc.url", "jdbc:derby:memory:ivoatestdb;create=true");//IMPL only one db for every PU perhaps dangerous
//        props.put("jakarta.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        
        
    }

}


