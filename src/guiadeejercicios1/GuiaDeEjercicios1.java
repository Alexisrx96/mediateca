/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiadeejercicios1;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import mediateca.*;

/**
 *
 * @author irvin
 */
public class GuiaDeEjercicios1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final String M_LIBRO = "Libros";
        final String M_REVISTA = "Revistas";
        final String M_CD = "CD's de Audio";
        final String M_DVD = "DVD's";
        final String ADD = "Agregar material";
        final String EDIT = "Modificar material";
        final String LIST = "Listar materiales disponibles";
        final String DELETE = "Borrar material";
        final String SEARCH = "Buscar material";
        final String EXIT = "Salir";

        final String[] MENU = {
            ADD, EDIT, LIST,
            DELETE, SEARCH, EXIT
        };
        final String[] MATERIALS = {
            M_LIBRO,
            M_REVISTA,
            M_CD,
            M_DVD
        };
        //Permite manejar arch
        XmlHandler xml = new XmlHandler();

        String materialSelected;
        boolean findedMaterial;
        HashMap<String, IMaterialHandler> MATERIAL_HANDLER = new HashMap<>();
        MATERIAL_HANDLER.put(MATERIALS[0], new Libros());
        MATERIAL_HANDLER.put(MATERIALS[1], new Revistas());
        MATERIAL_HANDLER.put(MATERIALS[2], new CdsAudio());
        MATERIAL_HANDLER.put(MATERIALS[3], new DVDS());

        //reemplaza todos los elementos del HashMap
        //Reemplaza con el retorno del tipo de v
        MATERIAL_HANDLER.replaceAll(
                //k = key, v = value
                (k, v) -> {
                    try {
                        //se obtienen los datos de los xml
                        v = xml.xmlToObject(v);
                    } catch (Exception e) {
                    }
                    return v;
                }
        );
        
        String option;

        do {
            //Al usar un arreglo devuelve un objeto dependiendo de la selleción
            option = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione una opción",
                    "Menú",
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    MENU,
                    MENU[0]
            );
            option = option == null ? MENU[5] : option;

            switch (option) {
                case ADD:
                    materialSelected = (String) JOptionPane
                            .showInputDialog(
                                    null,
                                    "Seleccione un material a crear",
                                    "Materiales",
                                    JOptionPane.DEFAULT_OPTION,
                                    null,
                                    MATERIALS,
                                    MATERIALS[0]
                            );
                    materialSelected = materialSelected == null
                            ? ""
                            : materialSelected;

                    if (MATERIAL_HANDLER.containsKey(materialSelected)) {

                        MATERIAL_HANDLER.get(materialSelected).addElement();
                        MATERIAL_HANDLER.get(materialSelected).setList();
                        try {
                            xml.objectToXML(MATERIAL_HANDLER
                                    .get(materialSelected));
                        } catch (Exception e) {
                        }

                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "No has elegido ninguna opción",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    break;
                case EDIT:
                    findedMaterial = false;
                    materialSelected = JOptionPane.showInputDialog(
                            null,
                            "Ingrese el código del material",
                            "Materiales",
                            JOptionPane.DEFAULT_OPTION
                    );
                    materialSelected = materialSelected == null
                            ? ""
                            : materialSelected;

                    for (IMaterialHandler mh : MATERIAL_HANDLER.values()) {
                        if (mh.isMyElement(materialSelected)) {
                            IMaterial material = mh.searchElement(materialSelected);
                            if (material != null) {
                                mh.editElement(material);
                                findedMaterial = true;
                                try {
                                    mh.setList();
                                    xml.objectToXML(mh);
                                } catch (Exception e) {

                                }
                            }
                        }
                    }
                    if (!findedMaterial) {
                        JOptionPane.showMessageDialog(
                                null,
                                "No existe el material",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    break;

                case DELETE:
                    findedMaterial = false;
                    materialSelected = JOptionPane.showInputDialog(
                            null,
                            "Ingrese el código del material",
                            "Materiales",
                            JOptionPane.DEFAULT_OPTION
                    );
                    materialSelected = materialSelected == null
                            ? ""
                            : materialSelected;

                    for (IMaterialHandler mh : MATERIAL_HANDLER.values()) {
                        if (mh.isMyElement(materialSelected)) {
                            if (mh.deleteElement(materialSelected)) {
                                findedMaterial = true;
                                try {
                                    mh.setList();
                                    xml.objectToXML(mh);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                    if (!findedMaterial) {
                        JOptionPane.showMessageDialog(
                                null,
                                "No existe el material",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    break;
                case SEARCH:
                    String message = "";
                    findedMaterial = false;
                    materialSelected = JOptionPane.showInputDialog(
                            null,
                            "Ingrese el código del material",
                            "Materiales",
                            JOptionPane.DEFAULT_OPTION
                    );
                    materialSelected = materialSelected == null
                            ? ""
                            : materialSelected;

                    for (IMaterialHandler mh : MATERIAL_HANDLER.values()) {
                        if (mh.isMyElement(materialSelected)) {
                            Object material = mh.searchElement(materialSelected);
                            if (material != null) {
                                message = material.toString();
                                findedMaterial = true;
                            }
                        }

                    }
                    if (!findedMaterial) {
                        JOptionPane.showMessageDialog(
                                null,
                                "No existe el material",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                message,
                                "Material",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    break;
                case LIST:
                    for (Map.Entry<String,IMaterialHandler> mh
                            : MATERIAL_HANDLER.entrySet()) {
                        System.out.println(mh.getKey() + " disponibles");
                        for (IMaterial a : mh.getValue().getAbleables()) {
                            System.out.println(
                                    "Codigo: " + a.getCode()
                                    + " Nombre: " + a.getTittle()
                                    + " Unidades disponibles: " 
                                            + a.getAbleableUnits()
                            );
                        }
                    }
 
                    break;
                case EXIT:
                    int exit = JOptionPane.showConfirmDialog(
                            null,
                            "¿Está seguro que desea salir?",
                            EXIT,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    option = exit == JOptionPane.YES_OPTION ? option : "";
            }

        } while (!option.equals(EXIT));
    }
}
