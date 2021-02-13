/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca;

import java.time.LocalDate;
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
public class Revista extends MaterialEscrito {

    //Constantes de clase
    final private static ArrayList<Revista> REVISTAS = new ArrayList<>();
    final public static String PREFIX = "REV";

    final public static String[] FREQUENCIES = {
        "Diario",
        "Semanal",
        "Quincenal",
        "Mensual",
        "Bimestral",
        "trimestral",
        "Cuatrimestral",
        "Semestral",
        "Anual",
        "Bienal"
    };

    //Variables de clase
    private static int correlative = 1;

    //Propiedades
    private String publicationDate;
    private String periodicity;

    /**
     *
     * @param publicationDate
     * @param periodicity
     * @param publisher
     * @param tittle
     * @param ableableUnits
     */
    //Constructores
    public Revista(LocalDate publicationDate, String periodicity, String publisher,
            String tittle, int ableableUnits) {
        super(publisher, tittle, ableableUnits);
        this.publicationDate = publicationDate.toString();
        this.periodicity = periodicity;
        this.code = PREFIX + String.format("%05d", correlative);
        correlative++;
        REVISTAS.add(this);
    }

    protected Revista() {
    }

    //Métodos de clase
    public static ArrayList<Revista> getAll() {
        return (ArrayList<Revista>) REVISTAS.clone();
    }

    protected static void setRevistas(ArrayList<Revista> arr) {
        REVISTAS.clear();
        REVISTAS.addAll(arr);
    }

    protected static void setCorrelative(String lastCode) {
        lastCode = lastCode != null && lastCode.contains(PREFIX) ? lastCode : "0";
        correlative = Integer.parseInt(lastCode.replace(PREFIX, "")) + 1;
    }

    //Métodos de objetos
    public String toString() {
        return "Revista:"
                + "\nCódigo: " + code
                + "\nTítutlo: " + tittle
                + "\nUnidades Disponibles:" + ableableUnits
                + "\nEditorial: " + publisher
                + "\nFecha de publicación:" + publicationDate
                + "\nPeriodicidad:" + periodicity;
    }

    public LocalDate getPublicationDate() {
        return LocalDate.parse(this.publicationDate);
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate.toString();
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String freq) {
        int i = 0;
        int j = 0;
        for (String frq : FREQUENCIES) {
            if (frq.equalsIgnoreCase(freq)) {
                j = i;
            }
            i++;
        }
        this.periodicity = FREQUENCIES[j];
    }

    public boolean delete() {
        return REVISTAS.remove(this);
    }
}
