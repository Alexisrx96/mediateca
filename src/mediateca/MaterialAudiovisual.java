/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author irvin
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class MaterialAudiovisual extends Material {

    protected String gender;
    protected String length;

    /**
     *
     * @param gender
     * @param length
     * @param tittle
     * @param ableableUnits
     */
    public MaterialAudiovisual(String gender, LocalTime length, String tittle,
            int ableableUnits) {
        super(tittle, ableableUnits);
        this.gender = gender;
        this.length = length.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    protected MaterialAudiovisual() {
        super();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalTime getLength() {
        return LocalTime.parse(length);
    }

    public void setLength(LocalTime length) {
        this.length = length.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
