/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author irvin
 */
public class XmlHandler {
    //Cuando se recibe una interfaz por
    //parámetro se recibe una implementación de esta
    public void objectToXML(IXmlHandler t) throws JAXBException {
        try {
            //Obtiene el nombre de la clase
            //EJEMPLO: nombreClase.xml
            //getClass() devuelve la clase
            //getSimpleName() devuelve el nombre de la clase 
            String fileName = t.getClass()//mediateca.NombreClase.class
                    .getSimpleName()//NombreClase
                    + ".xml";//NombleClase.xml

            // Crea el contexto JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(t.getClass());

            //Crea el Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Formatea la salida
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //imprime el Marshal en consola
            jaxbMarshaller.marshal(t, System.out);
            //imprime el Marshal en un archivo
            jaxbMarshaller.marshal(t, new File(fileName));
        } catch (JAXBException e) {
        }
    }

    public <T extends IXmlHandler > T xmlToObject(T t) throws JAXBException {
        //Obtiene el nombre de la clase
        String fileName = t.getClass().getSimpleName() + ".xml";
        JAXBContext jaxbContext = JAXBContext.newInstance(t.getClass());
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        //We had written this file in marshalling example
        T newT = (T) t.getClass().cast(
                jaxbUnmarshaller.unmarshal(new File(fileName))
        );
        newT.setCorrelative();
        return newT;
    }
}
