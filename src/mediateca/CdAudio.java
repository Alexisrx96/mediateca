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
public class CdAudio extends MaterialAudiovisual {

    //Constantes de clase
    final private static ArrayList<CdAudio> CDS_AUDIO = new ArrayList<>();
    final public static String PREFIX = "CDA";

    //Variables de clase
    private static int correlative = 1;

    //Propiedades
    private String artist;
    private int songsCount;

    /**
     *
     * @param artist
     * @param songsCount
     * @param gender
     * @param length
     * @param tittle
     * @param ableableUnits
     */
    //Constructores
    public CdAudio(String artist, int songsCount, String gender,
            LocalTime length, String tittle, int ableableUnits) {
        super(gender, length, tittle, ableableUnits);
        this.artist = artist;
        this.songsCount = songsCount;
        this.code = PREFIX + String.format("%05d", correlative);
        correlative++;
        CDS_AUDIO.add(this);
    }

    protected CdAudio() {
    }

    //Métodos de clase
    public static ArrayList<CdAudio> getAll() {
        return (ArrayList<CdAudio>) CDS_AUDIO.clone();
    }

    protected static void setCdsAudio(ArrayList<CdAudio> arr) {
        CDS_AUDIO.clear();
        CDS_AUDIO.addAll(arr);
    }

    protected static void setCorrelative(String lastCode) {
        lastCode = lastCode != null && lastCode.contains(PREFIX) ? lastCode : "0";
        correlative = Integer.parseInt(lastCode.replace(PREFIX, "")) + 1;
    }

    //Métodos de objetos
    @Override
    public String toString() {
        return "CD de Audio:"
                + "\nCódigo: " + code
                + "\nTítutlo: " + tittle
                + "\nUnidades Disponibles:" + ableableUnits
                + "\nGénero: " + gender
                + "\nDuración:" + length
                + "\nArtista:" + artist
                + "\nNúmero de canciones: " + songsCount;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getSongsCount() {
        return songsCount;
    }

    public void setSongsCount(int songsCount) {
        this.songsCount = songsCount;
    }

    public boolean delete() {
        return CDS_AUDIO.remove(this);
    }
}
