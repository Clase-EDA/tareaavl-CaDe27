/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;

import arboles.*;
import Interfaces.BSTADT;
import java.util.Iterator;

/**
 *
 * @author CD
 * @param <T>
 */
public class LinkedBSTAVL<T extends Comparable <T>> extends LinkedBST<T> implements BSTADT<T>  {
    

    private NodoAVL<T> rotacion(NodoAVL<T> N){
        NodoAVL<T> papa, alfa, beta, gamma, a, b, c, d;
        
        alfa = N;
        papa = alfa.getPapa();
        //izq
        if(N.fe == -2){
            beta = alfa.getIzq();
            d = alfa.getDer();
            //izq izq
            if(beta.fe <=0){
                c = beta.getDer();
                
                beta.cuelga(alfa);
                if(c != null)
                    alfa.cuelga(c);
                else
                    alfa.izq= null;

                if(papa != null)
                   papa.cuelga(beta);
                else{
                   beta.setPapa(null);
                   raiz = beta;
                }
                // para actualizar los factores de equilibrio:
                //si beta tenia 0
                if(beta.fe == 0){
                    alfa.setFe(-1);
                    beta.setFe(1);
                }
                else{ //si tenia -1
                    alfa.setFe(0);
                    beta.setFe(0);
                }
                return beta;
            }
            //izq der
            else{
                gamma = beta.getDer();
                b = gamma.getIzq();
                c = gamma.getDer();
                
                if(b!= null)
                    beta.cuelga(b);
                else
                    beta.der = null;
                if(c != null)
                    alfa.cuelga(c);
                else
                    alfa.izq = null;
                gamma.cuelga(alfa);
                gamma.cuelga(beta);

                if(papa != null)
                   papa.cuelga(gamma);
                else{
                   gamma.setPapa(null);
                   raiz = gamma;
                }
                //para actualizar factores de equilibrio
                switch (gamma.fe) {
                    case 0:
                        beta.setFe(0);
                        alfa.setFe(0);
                        break;
                    case -1:
                        gamma.setFe(0);
                        beta.setFe(0);
                        alfa.setFe(1);
                        break;
                    default://(case 1:)
                        gamma.setFe(0);
                        beta.setFe(-1);
                        alfa.setFe(0);
                        break;
                }
                return gamma;
            }
        }
        //der
        else{
           beta = alfa.getDer();
           a = alfa.getIzq();
           //der der 
           if(beta.fe >= 0){
                b = beta.getIzq();
                
                if(b != null)
                    alfa.cuelga(b);
                else
                    alfa.der = null;
                
                beta.cuelga(alfa);

                if(papa != null)
                   papa.cuelga(beta);
                else{
                   beta.setPapa(null);
                   raiz = beta;
                }
                // para actualizar los factores de equilibrio:
                //si beta tenia 0
                if(beta.fe == 0){
                    alfa.setFe(1);
                    beta.setFe(-1);
                }
                else{ //si tenia 1
                    alfa.setFe(0);
                    beta.setFe(0);
                }
                return beta;
            }
            //der izq
            else{
                gamma = beta.getIzq();
                b = gamma.getIzq();
                c = gamma.getDer();
                
                if (b!= null)
                    alfa.cuelga(b);
                else
                    alfa.der = null;
                
                if(c != null)
                    beta.cuelga(c);
                else
                    beta.izq = null;
                
                gamma.cuelga(alfa);
                gamma.cuelga(beta);

                if(papa != null)
                   papa.cuelga(gamma);
                else{
                   gamma.setPapa(null);
                   raiz = gamma;
                }
                //para actualizar factores de equilibrio
                switch (gamma.fe) {
                    case 0:
                        beta.setFe(0);
                        alfa.setFe(0);
                        break;
                    case -1:
                        gamma.setFe(0);
                        beta.setFe(1);
                        alfa.setFe(0);
                        break;
                    default://(case 1:)
                        gamma.setFe(0);
                        beta.setFe(0);
                        alfa.setFe(-1);
                        break;
                }
                return gamma;
            }  
        }   
    }
    
    //inserta
    public void inserta(T elemento){
        if(elemento == null)
            throw new NullPointerException();
        if(this.cant == 0){
            raiz = new NodoAVL(elemento);
        }
        else{
            NodoAVL<T> nodo = deberiaSerMiPapa((NodoAVL<T>)raiz, elemento);
            NodoAVL<T> nuevo = new NodoAVL<>(elemento);
            nodo.cuelga(nuevo);
            nodo.actualizaFeHijo(nuevo, 1);
            actualizaFactorEquilibrio(nodo, 1);
        }
        ++cant;
    }
    
    
    private NodoAVL<T> deberiaSerMiPapa(NodoAVL<T> nodo, T elemento){
        int compara = elemento.compareTo(nodo.element);
        if(compara >= 0 && nodo.getDer() != null)
            return deberiaSerMiPapa(nodo.getDer(), elemento);
        else if(compara < 0 && nodo.getIzq() != null)
            return deberiaSerMiPapa(nodo.getIzq(), elemento);
        else
            return nodo;
    }
    
    private void actualizaFactorEquilibrio(NodoAVL<T> nodo,int band){
        if(band == 1){//inserta
            while(nodo != null && nodo.fe != 0){
                if(nodo.fe == 2 || nodo.fe == -2)
                    nodo = rotacion(nodo);
                else if(nodo.getPapa() == null)
                    break;
                else{
                    nodo.getPapa().actualizaFeHijo(nodo, 1);
                    nodo = nodo.getPapa();
                }
            }
        }
        else{//elimina
            while(nodo != null && nodo.fe == 0){
                if(nodo.fe == 2 || nodo.fe == -2)
                    nodo = rotacion(nodo);
                else if(nodo.getPapa() == null)
                    break;
                else{
                    nodo.getPapa().actualizaFeHijo(nodo, -1);
                    nodo = nodo.getPapa();
                }
            }
        }
    }
    
    //borra
    public T borra(T elemento){
        if(elemento == null)
            throw new NullPointerException();
        NodoAVL<T> porBorrar = busca((NodoAVL<T>)raiz, elemento);
        if(porBorrar == null)
            return null;
        T resp = porBorrar.element;
        //indica a partir de que nodo tengo que actualizar hacia arriba
        NodoAVL<T> actualiza = borra(porBorrar);
        if(actualiza.fe == 2 || actualiza.fe == -2)
                    actualiza = rotacion(actualiza);
        actualizaFactorEquilibrio(actualiza, -1);
        return resp;
    }
    
    private NodoAVL<T> borra(NodoAVL<T> nodo){
        //si es la raiz
        NodoAVL<T> resp;
        --cant;
        if(nodo.getDer() == null && nodo.getIzq() == null)
        {
            if(nodo == raiz){
                raiz = null;
                resp = null;
            }
            else{
                resp = nodo.getPapa();
                resp.actualizaFeHijo(nodo, -1);
                if(nodo.element.compareTo(nodo.getPapa().getElement()) < 0)
                    resp.setIzq(null);
                else
                    resp.setDer(null);
                nodo.setPapa(null);
            }
        }
        //si solo tiene hijo izq
        else if(nodo.getDer() == null)
        {
            if(nodo == raiz)
            {
                raiz = nodo.getIzq();
                raiz.setPapa(null);
                resp = null;
            }
            else
            {
                resp = nodo.getPapa();
                resp.actualizaFeHijo(nodo, -1);
                resp.cuelga(nodo.getIzq());
                }
        }
        //si solo tiene hijo der
        else if(nodo.getIzq() == null)
        {
           if(nodo == raiz)
            {
                raiz = nodo.getDer();
                raiz.setPapa(null);
                resp = null;
            }
            else
            {
                resp = nodo.getPapa();
                resp.actualizaFeHijo(nodo, -1);
                resp.cuelga(nodo.getDer());
            } 
        }
        //si tiene dos hijos
        else
        {
            NodoAVL<T> sI = (NodoAVL<T>)sucesorInorder(nodo);
            nodo.setElement(sI.getElement());
            resp = sI.getPapa();
            resp.actualizaFeHijo(sI, -1);
            if(sI == nodo.getDer()){
                if(sI.getDer()!=null)
                    nodo.cuelga(sI.getDer());
                else
                    sI.setElement(null);
            }
            else{
                if(sI.getDer() != null)
                    resp.cuelga(sI.getDer());
                else
                    resp.setIzq(null);
            }
        }
        return resp;
    }
    
    //busca
    @Override
    public T busca(T elemento){
        NodoBT<T> resp = null;
        if(elemento == null)
            throw new NullPointerException();
        else
            resp = busca((NodoAVL<T>)raiz, elemento);
        return resp==null? null:resp.element;
    }
    
    private NodoAVL<T> busca(NodoAVL<T> nodo, T elemento){
        if(nodo == null)
            return null;
        int compara = nodo.getElement().compareTo(elemento);
        if(compara == 0)
            return nodo;
        //si el del nodo es mayor, te mando a la izq
        else if(compara>0)
            return busca(nodo.getIzq(), elemento);
        //si el del nodo menor, te manso a la der
        else
            return busca(nodo.getDer(), elemento);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = this.levelOrder();
        int size = (int)(Math.log(cant)/Math.log(2));
        int pot = 1;
        for(int i =  0; i < size; ++i){
            sb.append("nivel ").append(i).append(" :");
            for(int j = pot; j < 2*pot && j < cant; ++j){
                sb.append(it.next()).append(" ");
            }
            sb.append("\n");
            pot*=2;
        }
        sb.append("nivel ").append(size).append(" :");
        for(int j = pot; j <= cant; ++j){
            sb.append(it.next()).append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }
}
