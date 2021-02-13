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
 * DVDS
 *
 * @author irvin
 */
@XmlRootElement(name = "dvds")
@XmlAccessorType(XmlAccessType.FIELD)
public class DVDS implements IMaterialHandler<DVD> {

    @XmlElement(name = "dvd")
    private ArrayList<DVD> list = new ArrayList<>();

    @Override
    public ArrayList<DVD> getList() {
        return list;
    }

    @Override
    public DVD[] getAbleables() {
        List<DVD> newList = list
                .stream()
                .filter((t) -> t.getAbleableUnits() > 0)
                .collect(Collectors.toList());
        DVD[] arr = new DVD[newList.size()];
        arr = newList.toArray(arr);
        return arr;
    }

    @Override
    public void setList(ArrayList<DVD> list) {
        this.list = list;
    }

    @Override
    public void setList() {
        this.list = DVD.getAll();
    }

    @Override
    public boolean isMyElement(String code) {
        return code.startsWith(DVD.PREFIX) && code.length() == 8; 
    }
    @Override
    public void setCorrelative() {
        DVD.setDvds(list);
        if (list.size() > 0) {
            DVD dvd = list.get(list.size() - 1);
            DVD.setCorrelative(dvd.getCode());
        } else {
            DVD.setCorrelative("");
        }
    }

    public DVD addElement() {
        String director = setDirector();
        String gender = setGender();
        LocalTime length = setLength();
        String tittle = setTittle();
        int ableableUnits = setAbleableUnits();
        DVD dvd = new DVD(director, gender, length, tittle, ableableUnits);
        return dvd;
    }

    @Override
    public DVD editElement(DVD element) {

        final String director = "director";
        final String gender = "gender";
        final String length = "length";
        final String tittle = "tittle";
        final String ableableUnits = "ableableUnits";
        final String[] options = {
            director,
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
                case director:
                    element.setDirector(setDirector());
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

        for (DVD dvd : list) {
            if (dvd.getCode().equalsIgnoreCase(code)) {
                return dvd.delete();
            }
        }
        return false;
    }

    @Override
    public DVD searchElement(String code) {
        for (DVD dvd : list) {
            if (dvd.getCode().equalsIgnoreCase(code)) {
                return dvd;
            }
        }
        return null;
    }

    private String setDirector() {
        String director;
        do {
            director = JOptionPane.showInputDialog(
                    null,
                    "Ingrese el nombre del director",
                    "Director",
                    JOptionPane.INFORMATION_MESSAGE
            );
            director = director == null ? "" : director;
            if (director.isEmpty()) {
                showError("Debes ingresar el nombre del director");

            }
        } while (director.isEmpty());
        return director;
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

    private void showError(String errorMessage) {

        JOptionPane.showMessageDialog(
                null,
                errorMessage,
                "ERROR",
                JOptionPane.ERROR_MESSAGE
        );
    }

}
