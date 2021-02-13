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
@XmlRootElement(name = "libro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Libro extends MaterialEscrito {

    //Constantes de clase
    final private static ArrayList<Libro> LIBROS = new ArrayList<>();
    final public static String PREFIX = "LIB";

    //Variables de clase
    private static int correlative = 1;
    
    //Propiedades
    private String author;
    private String isbn;
    private int publicationYear;
    private int pageCount;

    /**
     *
     * @param author
     * @param isbn
     * @param publicationYear
     * @param pageCount
     * @param publisher
     * @param tittle
     * @param ableableUnits
     */
    
    //Constructores
    public Libro(String author, String isbn, int publicationYear,
            int pageCount, String publisher, String tittle,
            int ableableUnits) {
        super(publisher, tittle, ableableUnits);
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.pageCount = pageCount;
        this.code = PREFIX + String.format("%05d", correlative);
        correlative++;
        LIBROS.add(this);
    }

    protected Libro() {
        super();
    }

    //Métodos de clase
    public static ArrayList<Libro> getAll() {
        return (ArrayList<Libro>) LIBROS.clone();
    }

    protected static void setLibros(ArrayList<Libro> arr) {
        LIBROS.clear();
        LIBROS.addAll(arr);
    }

    protected static void setCorrelative(String lastCode) {
        lastCode = lastCode != null && lastCode.contains(PREFIX) ? lastCode : "0";
        correlative = Integer.parseInt(lastCode.replace(PREFIX, "")) + 1;
    }

    //Métodos de objetos

    @Override
    public String toString() {
        return "Libro:"  
                + "\nCódigo: " + code 
                + "\nTítutlo: " + tittle 
                + "\nUnidades Disponibles:" + ableableUnits
                + "\nEditorial: " + publisher
                + "\nAutor:" + author 
                + "\nISBN:" + isbn 
                + "\nAño de publicación:" + publicationYear 
                + "\nCantida de páginas:" + pageCount;
    }
    
    
          
    public String getAuthor() {
        for (int i = 0; i < 10; i++) {
            
        }
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean delete() {
        return LIBROS.remove(this);
    }
}
