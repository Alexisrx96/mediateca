/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author irvin
 */
public interface IMaterialHandler<T extends IMaterial> extends IXmlHandler<T>{

    public ArrayList<T> getList();
    public T[] getAbleables();

    public void setList(ArrayList<T> list);
    public void setList();
    
    public T addElement();
    public T editElement(T element);
    public boolean deleteElement(String code);
    public T searchElement(String code);
    
    public boolean  isMyElement(String code);
    
    
}
