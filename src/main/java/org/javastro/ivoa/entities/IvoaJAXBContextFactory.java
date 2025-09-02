/*
 * $Id: IvoaJAXBContextFactory.java,v 1.2 2011-09-13 13:43:33 pah Exp $
 *
 * Created on 12 Mar 2008 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2008 Astrogrid. All rights reserved.
 *
 * This software is published under the terms of the Astrogrid
 * Software License, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 *
 */

package org.javastro.ivoa.entities;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

/**
 * Utility Factory to create JAXB contexts which know about all the classes necessary for Registry Data Model.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 12 Mar 2008
 * @since VOTech Stage 6
 */
public class IvoaJAXBContextFactory {


	private static volatile JAXBContext jc;

	public static JAXBContext newInstance() throws JAXBException
	{
		if(jc == null)
			synchronized (IvoaJAXBContextFactory.class) {
				if (jc == null)
					jc = JAXBContext.newInstance(

							"org.javastro.ivoa.entities.resource"
									+ ":org.javastro.ivoa.entities.resource.dataservice"
									+ ":org.javastro.ivoa.entities.resource.registry"
									+ ":org.javastro.ivoa.entities.resource.registry.iface"
									+ ":org.javastro.ivoa.entities.resource.applications"
									+ ":org.javastro.ivoa.entities.resource.cone"
									+ ":org.javastro.ivoa.entities.resource.sia"
									+ ":org.javastro.ivoa.entities.resource.standard"
									+ ":org.javastro.ivoa.entities.resource.registry.oaipmh"
									+ ":org.javastro.ivoa.entities.resource.registry.dublincore"
									+ ":org.javastro.ivoa.entities.resource.registry.dublincore.simple"
					            + ":org.javastro.ivoa.entities.vosi.availability"
                           + ":org.javastro.ivoa.entities.vosi.capabilities"
                           + ":org.javastro.ivoa.entities.vosi.tables"
               );
			}
		return jc;

	}
}


