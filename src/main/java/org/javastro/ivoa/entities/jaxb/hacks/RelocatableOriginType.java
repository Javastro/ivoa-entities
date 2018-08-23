

package org.javastro.ivoa.entities.jaxb.hacks;

import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for relocatableOriginType. 
 * This is a hacked "complex" type to make up for the huge substitution group mess in STC that confuses the code generators.
 * Will anyone use it? or care? I don't think that it will "work"
 * 
 * @author Paul Harrison (paul.harrison@manchester.ac.uk)
 * @deprecated Should be removed when hand-edited files are the product of this library 
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="relocatableOriginType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="RELOCATABLE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "relocatableOriginType")
public class RelocatableOriginType 
{

    private String val = "RELOCATABLE";

    /**
     * @param val
     */
    public RelocatableOriginType(String val) {
        this.val = val;
    }

    public String value() {
        return val;
    }



}
