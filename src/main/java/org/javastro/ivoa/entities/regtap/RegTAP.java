/*
 * Created on 20 May 2019 
 * Copyright 2019 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.regtap;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Overall Container class for RegTAP XML.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 20 May 2019
 */
@XmlRootElement(name = "regtap")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegTAP {
    @XmlElement(name="resource")
    public List<Resource> resources = new ArrayList<>();

}


