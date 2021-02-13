/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author irvin
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Material implements IMaterial{

    protected String code;
    protected String tittle;
    protected int ableableUnits;

    public Material(String tittle, int ableableUnits) {
        this.tittle = tittle;
        this.ableableUnits = ableableUnits;
    }

    protected Material() {
    }

    public String getCode() {
        return code;
    }

    public String getTittle() {
        return tittle;
    }

    public int getAbleableUnits() {
        return ableableUnits;
    }

    public void setAbleableUnits(int ableableUnits) {
        this.ableableUnits = ableableUnits;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

}
