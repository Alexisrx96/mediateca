/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca;

import java.time.LocalTime;
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
public class DVD extends MaterialAudiovisual {

    //Constantes de clase
    final private static ArrayList<DVD> DVDS = new ArrayList<>();
    final public static String PREFIX = "DVD";

    //Variables de clase
    private static int correlative = 1;

    //Propiedades
    private String director;

    /**
     *
     * @param director
     * @param gender
     * @param length
     * @param tittle
     * @param ableableUnits
     */
    //Constructores
    public DVD(String director, String gender,
            LocalTime length, String tittle,
            int ableableUnits) {
        super(gender, length, tittle, ableableUnits);
        this.director = director;
        this.code = PREFIX + String.format("%05d", correlative);
        correlative++;
        DVDS.add(this);
    }

    protected DVD() {
    }

    //Métodos de clase
    public void setDirector(String director) {
        this.director = director;
    }

    public static ArrayList<DVD> getAll() {
        return (ArrayList<DVD>) DVDS.clone();
    }

    protected static void setDvds(ArrayList<DVD> arr) {
        DVDS.clear();
        DVDS.addAll(arr);
    }

    protected static void setCorrelative(String lastCode) {
        lastCode = lastCode != null && lastCode.contains(PREFIX) ? lastCode : "0";
        correlative = Integer.parseInt(lastCode.replace(PREFIX, "")) + 1;
    }

    //Métodos de objetos
    @Override
    public String toString() {
        return "DVD:"
                + "\nCódigo: " + code
                + "\nTítutlo: " + tittle
                + "\nUnidades Disponibles:" + ableableUnits
                + "\nGénero: " + gender
                + "\nDuración:" + length
                + "\nDirector:" + director;
    }

    public String getDirector() {
        return director;
    }

    public boolean delete() {
        return DVDS.remove(this);
    }
}
