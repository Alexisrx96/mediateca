/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author irvin
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class MaterialEscrito extends Material {

    protected String publisher;

    /**
     *
     * @param publisher
     * @param tittle
     * @param ableableUnits
     */
    public MaterialEscrito(String publisher, String tittle, int ableableUnits) {
        super(tittle, ableableUnits);
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "MaterialEscrito{" + "publisher=" + publisher + '}';
    }
    
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    protected MaterialEscrito() {
        super();
    }
}
