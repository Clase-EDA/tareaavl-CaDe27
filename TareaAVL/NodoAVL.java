/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;

import arboles.*;

/**
 *
 * @author CD
 */
public class NodoAVL<T extends Comparable<T>> extends NodoBT<T> {
    
    int fe; //factor de equilibrio (hijos en derecha - hijos en izq)

    public NodoAVL(T element) {
        super(element);
        this.fe = 0;
    }

    public int getFe() {
        return fe;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }
    
    public void actualizaFeHijo(NodoAVL<T> hijo, int band) {
        if(element.compareTo(hijo.element) < 0)
            this.fe += 1*band;
        else
            this.fe += -1*band;
    }


    public NodoAVL<T> getIzq() {
        return (NodoAVL<T>)izq;
    }

    public void setIzq(NodoAVL<T> izq) {
        this.izq = izq;
    }

    public NodoAVL<T> getDer() {
        return (NodoAVL<T>)der;
    }

    public void setDer(NodoAVL<T> der) {
        this.der = der;
    }

    public NodoAVL<T> getPapa() {
        return (NodoAVL<T>)papa;
    }

    public void setPapa(NodoAVL<T> papa) {
        this.papa = papa;
    }

    
    @Override
    public String toString() {
        return "NodoAVL{\n" + super.toString() + "\tfe=" + fe + '}';
    }
     
    
    public void calculaFactorEquilibrio(){
        int hder, hizq;
        hder = der == null? 0:1+izq.numHijos();
        hizq = izq == null? 0:1+der.numHijos();
        fe = hder - hizq;
    }
    
    
}
