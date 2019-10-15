/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;

import arboles.*;
import java.util.Objects;

/**
 *
 * @author CD
 */
class NodoBT<T extends Comparable<T>> {
    T element;
    NodoBT<T> izq;
    NodoBT<T> der, papa;
    
    NodoBT(T element){
        this.element = element;
        izq = null;
        der = null;
        papa = null;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public NodoBT<T> getIzq() {
        return izq;
    }

    public void setIzq(NodoBT<T> izq) {
        this.izq = izq;
    }

    public NodoBT<T> getDer() {
        return der;
    }

    public void setDer(NodoBT<T> der) {
        this.der = der;
    }

    public NodoBT<T> getPapa() {
        return papa;
    }

    public void setPapa(NodoBT<T> papa) {
        this.papa = papa;
    }
    
    public void cuelga(NodoBT<T> n){
        if (n == null) 
            throw new NullPointerException();
        if(n.getElement().compareTo(element) < 0)
            izq = n;
        else
            der = n;
        n.setPapa(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.element);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NodoBT<?> other = (NodoBT<?>) obj;
        if (!Objects.equals(this.element, other.element)) {
            return false;
        }
        return true;
    }
    
    public int numHijos(){
       int resp = 0;
       if(izq != null)
           resp+= 1+izq.numHijos();
       if(der != null)
           resp+= 1+der.numHijos();
       return resp;
    }
}
