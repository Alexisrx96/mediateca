/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca;

/**
 *
 * @author irvin
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "libros")
@XmlAccessorType(XmlAccessType.FIELD)
public class Libros implements IMaterialHandler<Libro> {
    @XmlElement(name = "libro")
    private ArrayList<Libro> list = Libro.getAll();

    @Override
    public ArrayList<Libro> getList() {
        return list;
    }

    @Override
    public Libro[] getAbleables() {
        List<Libro> newList = list
                .stream()
                .filter((t) -> t.getAbleableUnits() > 0)
                .collect(Collectors.toList());
        Libro[] arr = new Libro[newList.size()];
        arr = newList.toArray(arr);
        return arr;
    }

    @Override
    public void setList(ArrayList<Libro> list) {
        this.list = list;
    }
    @Override
    public void setList() {
        this.list = Libro.getAll();
    }

    @Override
    public boolean isMyElement(String code) {
        return code.startsWith(Libro.PREFIX) && code.length() == 8; 
    }
    
    

    @Override
    public void setCorrelative() {
        Libro.setLibros(list);
        if (list.size() > 0) {
            Libro libro = list.get(list.size() - 1);
            Libro.setCorrelative(libro.getCode());
        } else {
            Libro.setCorrelative("");
        }
    }
    
    @Override
    public Libro addElement() {

        String tittle = setTittle();
        String author = setAuthor();
        String isbn = setIsbn();
        int pageCount = setPageCount();
        String publisher = setPublisher();
        int publicationYear = setPublicationYear();
        int ableableUnits = setAbleableUnits();


        Libro libro = new Libro(author, isbn, publicationYear,
                pageCount, publisher, tittle,
                ableableUnits
        );

        return libro;
    }

    @Override
    public Libro editElement(Libro element) {
        
        final String author = "author";
        final String isbn = "isbn";
        final String publisher = "publisher";
        final String publicationYear = "publicationYear";
        final String pageCount = "pageCount";
        final String tittle = "tittle";
        final String ableableUnits = "ableableUnits";
        final String[] options = {
            author,
            isbn,
            pageCount,
            publisher,
            publicationYear,
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
                case author:
                    element.setAuthor(setAuthor());
                    break;
                case isbn:
                    element.setIsbn(setIsbn());
                    break;
                case pageCount:
                    element.setPageCount(setPageCount());
                    break;
                case publisher:
                    element.setPublisher(setPublisher());
                    break;
                case publicationYear:
                    element.setPublicationYear(setPublicationYear());
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
        
        for (Libro libro : list) {
            if (libro.getCode().equalsIgnoreCase(code)) {
                return libro.delete();
            }
        }
        return false;
    }

    @Override
    public Libro searchElement(String code) {
        
        for (Libro libro : list) {
            if (libro.getCode().equalsIgnoreCase(code)) {
                return libro;
            }
        }
        return null;
    }
    
    

    public String setAuthor() {
        String author;
        do {
            author = JOptionPane.showInputDialog(
                    null,
                    "Ingrese el nombre del autor",
                    "Autor",
                    JOptionPane.INFORMATION_MESSAGE
            );
            author = author == null ? "" : author;
            if (author.isEmpty()) {
                showError("Debes ingresar un nombre de autor");
            }
        } while (author.isEmpty());
        return author;
    }

    public String setIsbn() {
        final String validIsbn = "Ejemplos de ISBN\nISBN 978-0-596-52068-7\nISBN-13: 978-0-596-52068-7\n978 0 596 52068 7\n9780596520687\n0-596-52068-9\n0 512 52068 9\nISBN-10 0-596-52068-9\nISBN-10: 0-596-52068-9\n";
        final String regex = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
        Pattern pattern = Pattern.compile(regex);
        String isbn;
        boolean isIsbn;
        do {
            isbn = JOptionPane.showInputDialog(
                    null,
                    "Ingrese el número ISBN.\n" + validIsbn,
                    "ISBN",
                    JOptionPane.INFORMATION_MESSAGE
            );
            isbn = isbn == null ? "" : isbn;
            Matcher matcher = pattern.matcher(isbn);
            isIsbn = matcher.matches();
            if (!isIsbn) {
                showError("Debes ingresar un número ISBN válido");
            }
        } while (!isIsbn);
        return isbn;
    }

    public int setPageCount() {
        int pageCount;
        boolean isInteger;
        do {
            try {
                pageCount = Integer.parseInt(JOptionPane.showInputDialog(
                        null,
                        "Ingrese la cantidad de páginas",
                        "Cantidad de páginas",
                        JOptionPane.INFORMATION_MESSAGE
                ));
                isInteger = pageCount > 0;
            } catch (Exception e) {
                pageCount = 0;
                isInteger = false;
            }
            if (!isInteger) {
                showError("Debes ingresar un número entero mayor que cero");
            }
        } while (!isInteger);
        return pageCount;
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
        return publisher;
    }

    public int setPublicationYear() {
        int publicationYear;
        boolean isInteger;
        do {
            try {
                publicationYear = Integer.parseInt(JOptionPane.showInputDialog(
                        null,
                        "Ingrese el año de publicación",
                        "Año de publicación",
                        JOptionPane.INFORMATION_MESSAGE
                ));
                isInteger = publicationYear <= LocalDate.now().getYear();
            } catch (Exception e) {
                publicationYear = 0;
                showError("Debes ingresar un año, "
                        + LocalDate.now().getYear() + " o anterior");
                isInteger = false;
            }
        } while (!isInteger);
        return publicationYear;
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
        return tittle;
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
