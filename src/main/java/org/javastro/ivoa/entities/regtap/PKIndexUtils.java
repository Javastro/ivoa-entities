/*
 * Created on 9 Aug 2023 
 * Copyright 2023 Paul Harrison (paul.harrison@manchester.ac.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in file LICENSE
 */ 

package org.javastro.ivoa.entities.regtap;

import java.util.List;
import java.util.function.Supplier;

/**
 *  .
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 
 * @since 9 Aug 2023
 */
public class PKIndexUtils {

    
    public static <H extends Identifier,L extends PKIndex> void addWithIndex( L l, H h, List<L> li)
    {
       
        if(li.indexOf(l) == -1)
        {
            li.add(l);
            int i = li.indexOf(l);
            l.setPKIndex((short) i);
            l.setIvoid(h.getIvoid());
        }
    }
}


