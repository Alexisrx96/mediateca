/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca;

import java.time.LocalDate;
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
@XmlRootElement(name = "revistas")
@XmlAccessorType(XmlAccessType.FIELD)
public class Revistas implements IMaterialHandler<Revista> {

    
    @XmlElement(name = "revista")
    private ArrayList<Revista> list = new ArrayList<>();

    @Override
    public ArrayList<Revista> getList() {
        return list;
    }

    @Override
    public Revista[] getAbleables() {
        List<Revista> newList = list
                .stream()
                .filter((t) -> t.getAbleableUnits() > 0)
                .collect(Collectors.toList());
        Revista[] arr = new Revista[newList.size()];
        arr = newList.toArray(arr);
        return arr;
    }

    @Override
    public void setList(ArrayList<Revista> list) {
        this.list = list;
    }
    @Override
    public void setList() {
        this.list = Revista.getAll();
    }

    @Override
    public boolean isMyElement(String code) {
        return code.startsWith(Revista.PREFIX) && code.length() == 8; 
    }
    @Override
    public void setCorrelative() {
        Revista.setRevistas(list);
        if (list.size() > 0) {
            Revista revista = list.get(list.size() - 1);
            Revista.setCorrelative(revista.getCode());
        } else {
            Revista.setCorrelative("");
        }
    }
    
    public Revista addElement() {
        String periodicity = setPeriodicity();
        String publisher = setPublisher();
        String tittle = setTittle();
        LocalDate publicationDate = setPublicationDate();
        int ableableUnits = setAbleableUnits();

        Revista revista = new Revista(publicationDate, periodicity, publisher, tittle, ableableUnits);
        
        return revista;
    }

    @Override
    public Revista editElement(Revista element) {
        
        final String periodicity = "periodicity";
        final String publisher = "publisher";
        final String publicationDate = "publicationDate";
        final String tittle = "tittle";
        final String ableableUnits = "ableableUnits";
        final String[] options = {
            periodicity,
            publisher,
            publicationDate,
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
                case periodicity:
                    element.setPeriodicity(setPeriodicity());
                    break;
                case publisher:
                    element.setPublisher(setPublisher());
                    break;
                case publicationDate:
                    element.setPublicationDate(setPublicationDate());
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
        for (Revista revista : list) {
            if (revista.getCode().equalsIgnoreCase(code)) {
                return revista.delete();
            }
        }
        return false;
    }

    @Override
    public Revista searchElement(String code) {
        for (Revista revista : list) {
            if (revista.getCode().equalsIgnoreCase(code)) {
                return revista;
            }
        }
        return null;
    }
    
    
    

    public String setPeriodicity() {
        String periodicity;
        do {
            periodicity = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione la periodicidad de la revista",
                    "Periodicidad",
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    Revista.FREQUENCIES,
                    Revista.FREQUENCIES[0]
            );
            periodicity = periodicity == null ? "":periodicity;
            if (periodicity.isEmpty()) {
                showError("Debes ingresar una periodicidad");
            }
        } while (periodicity.isEmpty());
        return  periodicity;
    }

    public String setPublisher() {
        String publisher;
        do {
            publisher = JOptionPane.showInputDialog(
                    null,
                    "Ingrese el nombre de la editorial",
                    "Editorial",
                    JOptionPane.INFORMATION_MESSAGE
            );
            publisher = publisher == null ? "" : publisher;
            if (publisher.isEmpty()) {
                showError("Debes ingresar una editorial");
            }
        } while (publisher.isEmpty());
        return  publisher;
    }

    public String setTittle() {
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
        
        return  tittle;
    }

    public LocalDate setPublicationDate() {
        LocalDate publicationDate;
        boolean isDate;
        
        do {
            try {
                publicationDate = LocalDate.parse(JOptionPane.showInputDialog(
                        null,
                        "Ingrese la fecha de publicación de la película\n"
                                + "Formato: yyyy-MM-dd  (2000-12-31)",
                        "Fecha de publicación",
                        JOptionPane.INFORMATION_MESSAGE
                ));
                isDate = true;
            } catch (Exception e) {
                publicationDate = null;
                showError("Debes ingresar una fecha");
                isDate = false;
            }
        } while (!isDate);
        return  publicationDate;
    }

    public int setAbleableUnits() {
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
