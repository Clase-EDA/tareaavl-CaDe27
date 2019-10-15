/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;

import arboles.*;
import Interfaces.BSTADT;

/**
 *
 * @author CD
 * @param <T>
 */
public class LinkedBST<T extends Comparable<T>> extends LinkedBT<T> implements BSTADT<T>{
    @Override
    public void add(T elem) {
        if(raiz == null)
            raiz = new NodoBT(elem);
        else{
            NodoBT<T> papa = null;
            NodoBT<T> aux = raiz;
            boolean band = true;
            while(aux != null){
                papa = aux;
                if(elem.compareTo(aux.element) >= 0)
                    aux = aux.der;
                else
                    aux = aux.izq;
            }
            aux = new NodoBT<>(elem);
            aux.papa = papa;
            papa.cuelga(aux);
        }
    }

    public T busca(T elemento){
        NodoBT<T> resp = null;
        if(elemento == null)
            throw new NullPointerException();
        else
            resp = busca(raiz, elemento);
        return resp==null? null:resp.element;
    }
    
    private NodoBT<T> busca(NodoBT<T> nodo, T elemento){
        if(nodo == null)
            return null;
        int compara = nodo.getElement().compareTo(elemento);
        if(compara == 0)
            return nodo;
        else if(compara>0)
            return busca(nodo.getDer(), elemento);
        else
            return busca(nodo.getIzq(), elemento);
    }
    
    @Override
    public T remove(T elem) {
        //si es la raiz
        T resp = null;
        NodoBT<T> nodo = busca(raiz, elem);
        if(nodo != null){
            this.cant--;
            resp = nodo.getElement();
            //si no tiene hijos
            if(nodo.getDer() == null && nodo.getIzq() == null)
            {
                if(nodo == raiz)
                    raiz = null;
                else
                {
                    if(elem.compareTo(nodo.getPapa().getElement()) < 0)
                        nodo.getPapa().setIzq(null);
                    else
                        nodo.getPapa().setDer(null);
                    nodo.setPapa(null);
                }
                
            }
            //si solo tiene hijo izq
            else if(raiz.getIzq() == null)
            {
                if(nodo == raiz)
                {
                    raiz = nodo.getDer();
                    raiz.setPapa(null);
                }
                else
                {
                    nodo.getPapa().cuelga(nodo.getDer());
                }
            }
            //si solo tiene hijo der
            else if(raiz.getDer() == null)
            {
               if(nodo == raiz)
                {
                    raiz = nodo.getIzq();
                    raiz.setPapa(null);
                }
                else
                {
                    nodo.getPapa().cuelga(nodo.getIzq());
                } 
            }
            //si tiene dos hijos
            else
            {
                NodoBT<T> sI = sucesorInorder(nodo);
                nodo.setElement(sI.getElement());
                if(sI == nodo.getDer()){
                    nodo.setDer(sI.getDer());
                    if(nodo.getDer() != null)
                        nodo.getDer().setPapa(nodo);
                }
                else{
                    sI.getPapa().setIzq(sI.getDer());
                    if(sI.getDer()!= null)
                        sI.getDer().setPapa(sI.getPapa());
                }
                
            }
        }
        return resp;
    }

    protected NodoBT<T> sucesorInorder(NodoBT<T> actual) throws NullPointerException{
        if(actual == null)
            throw new NullPointerException();
        if(actual.getDer() == null)
            return actual.getPapa();
        else
        {
            actual = actual.getDer();
            while(actual.getIzq()!=null)
                actual = actual.getIzq();
        }
        return actual;
    }
    
    @Override
    public T removeMin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T findMin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T removeMax() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T findMax() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
