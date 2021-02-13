/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author irvin
 */
@XmlRootElement(name = "cdsAudio")
@XmlAccessorType(XmlAccessType.FIELD)
public class CdsAudio implements IMaterialHandler<CdAudio> {

    @XmlElement(name = "cdAudio")
    private ArrayList<CdAudio> list = new ArrayList<>();

    @Override
    public ArrayList<CdAudio> getList() {
        return list;
    }

    @Override
    public CdAudio[] getAbleables() {
        List<CdAudio> newList = list
                .stream()
                .filter((t) -> t.getAbleableUnits() > 0)
                .collect(Collectors.toList());
        CdAudio[] arr = new CdAudio[newList.size()];
        arr = newList.toArray(arr);
        return arr;
    }

    @Override
    public void setList(ArrayList<CdAudio> list) {
        this.list = list;
    }

    @Override
    public void setList() {
        this.list = CdAudio.getAll();
    }

    @Override
    public boolean isMyElement(String code) {
        return code.startsWith(CdAudio.PREFIX) && code.length() == 8; 
    } 
    
    @Override
    public void setCorrelative() {
        CdAudio.setCdsAudio(list);
        if (list.size() > 0) {

            CdAudio cdAudio = list.get(list.size() - 1);
            CdAudio.setCorrelative(cdAudio.getCode());
        } else {
            CdAudio.setCorrelative("");
        }
    }

    public CdAudio addElement() {
        String artist = setArtist();
        int songsCount = setSongsCount();
        String gender = setGender();
        LocalTime length = setLength();
        String tittle = setTittle();
        int ableableUnits = setAbleableUnits();

        CdAudio ca = new CdAudio(artist, songsCount, gender,
                length, tittle, ableableUnits);

        return ca;
    }

    @Override
    public CdAudio editElement(CdAudio element) {
        final String artist = "artist";
        final String songsCount = "songsCount";
        final String gender = "gender";
        final String length = "length";
        final String tittle = "tittle";
        final String ableableUnits = "ableableUnits";
        final String[] options = {
            artist,
            songsCount,
            gender,
            length,
            tittle,
            ableableUnits
        };
        String option;
        do {
            option = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione la opción a editar",
                    "Periodicidad",
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    options,
                    options[0]
            );
            option = option == null ? "" : option;
            switch (option) {
                case artist:
                    element.setArtist(setArtist());
                    break;
                case songsCount:
                    element.setSongsCount(setSongsCount());
                    break;
                case gender:
                    element.setGender(setGender());
                    break;
                case length:
                    element.setLength(setLength());
                    break;
                case tittle:
                    element.setTittle(setTittle());
                    break;
                case ableableUnits:
                    element.setAbleableUnits(setAbleableUnits());
                    break;
            }
        } while (option.isEmpty());
        return element;
    }

    @Override
    public boolean deleteElement(String code) {
        
        for (CdAudio cdAudio : list) {
            if (cdAudio.getCode().equalsIgnoreCase(code)) {
                return cdAudio.delete();
            }
        }
        return false;
    }

    @Override
    public CdAudio searchElement(String code) {
        

        for (CdAudio cdAudio : list) {
            if (cdAudio.getCode().equalsIgnoreCase(code)) {
                return cdAudio;
            }
        }
        return null;
    }

    private void showError(String errorMessage) {

        JOptionPane.showMessageDialog(
                null,
                errorMessage,
                "ERROR",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private int setSongsCount() {
        int songsCount;
        boolean isInteger;
        do {
            try {
                songsCount = Integer.parseInt(JOptionPane.showInputDialog(
                        null,
                        "Ingrese la cantidad de canciones",
                        "Cantidad de canciones",
                        JOptionPane.INFORMATION_MESSAGE
                ));
                isInteger = songsCount > 0;
            } catch (Exception e) {
                songsCount = 0;
                isInteger = false;
            }
            if (!isInteger) {
                showError("Debes ingresar un n[umero mayor que cero");
            }
        } while (!isInteger);
        return songsCount;
    }

    private String setArtist() {
        String artist;
        do {
            artist = JOptionPane.showInputDialog(
                    null,
                    "Ingrese el nombre del artista",
                    "Director",
                    JOptionPane.INFORMATION_MESSAGE
            );
            artist = artist == null ? "" : artist;
            if (artist.isEmpty()) {
                showError("Debes ingresar el nombre del artista");
            }
        } while (artist.isEmpty());
        return artist;
    }

    private String setGender() {
        String gender;
        do {
            gender = JOptionPane.showInputDialog(
                    null,
                    "Ingrese el género",
                    "Género",
                    JOptionPane.INFORMATION_MESSAGE
            );
            gender = gender == null ? "" : gender;
            if (gender.isEmpty()) {
                showError("Debes ingresar un género");
            }
        } while (gender.isEmpty());
        return gender;
    }

    private LocalTime setLength() {
        LocalTime length;
        boolean isTime;
        do {
            try {
                length = LocalTime.parse(JOptionPane.showInputDialog(
                        null,
                        "Ingrese la duración del CD de audio\n"
                        + "Formato: HH:mm:ss (01:45:05)",
                        "Duración",
                        JOptionPane.INFORMATION_MESSAGE
                ));
                isTime = true;
            } catch (Exception e) {
                length = null;
                showError("debes ingresar una duración válida");
                isTime = false;
            }
        } while (!isTime);
        return length;
    }

    private String setTittle() {
        String tittle;
        do {
            tittle = JOptionPane.showInputDialog(
                    null,
                    "Ingrese el título",
                    "Títutlo",
                    JOptionPane.INFORMATION_MESSAGE
            );
            tittle = tittle == null ? "" : tittle;
            if (tittle.isEmpty()) {
                showError("Debes ingresar un título");
            }
        } while (tittle.isEmpty());

        return tittle;
    }

    private int setAbleableUnits() {
        int ableableUnits;
        boolean isInteger;
        do {
            try {
                ableableUnits = Integer.parseInt(JOptionPane.showInputDialog(
                        null,
                        "Ingrese la cantidad de copias disponibles",
                        "Copias disponibles",
                        JOptionPane.INFORMATION_MESSAGE
                ));
                isInteger = ableableUnits >= 0;
            } catch (Exception e) {
                ableableUnits = 0;
                isInteger = false;
            }
            if (!isInteger) {
                showError("Debes ingresar un número entero mayor o igual a cero");
            }
        } while (!isInteger);
        return ableableUnits;
    }
}
