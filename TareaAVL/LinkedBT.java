/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;

import arboles.*;
import Interfaces.BinaryTreeADT;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author CD
 * @param <T>
 */
public class LinkedBT <T extends Comparable <T>> implements BinaryTreeADT<T>{
    protected NodoBT<T> raiz;
    protected int cant;
    
    public int size(){
        return cant;
    }
    
    @Override
    public Iterator<T> postOrder() { //hijos luego tu
       ArrayList<T> lista = new ArrayList<>();
       postOrder(raiz, lista);
       return lista.iterator();
    }

    private void postOrder(NodoBT<T> actual, ArrayList<T> lista) {
        //despues de tus hijos
       if(actual != null){
            postOrder(actual.izq, lista);
            postOrder(actual.der, lista);
            lista.add(actual.element);
       }
    }

    @Override
    public Iterator<T> inOrder() {
        //hijo izq ,tu ,hijo der
       ArrayList<T> lista = new ArrayList<>();
       inOrder(raiz, lista);
       return lista.iterator();
    }

    private void inOrder(NodoBT<T> actual, ArrayList<T> lista) {
       if(actual != null){
            inOrder(actual.izq, lista);
            lista.add(actual.element);
            inOrder(actual.der, lista);
       }
    }
    
    @Override 
    public Iterator<T> preOrder() {
        ArrayList<T> lista = new ArrayList<>();
        Stack<NodoBT<T>> pila = new Stack<>();
        pila.push(raiz);
        NodoBT<T> aux;
        while(!pila.isEmpty()){
            aux = pila.pop();
            lista.add(aux.element);
            if(aux.izq != null)
                pila.add(aux.izq);
            if(aux.der != null)
                pila.add(aux.der);
        }
       return lista.iterator();
    }
    /* 
    @Override
    public Iterator<T> preOrder() {
       ArrayList<T> lista = new ArrayList<>();
       preOrder(raiz, lista);
       return lista.iterator();
    }

    private void preOrder(NodoBT<T> actual, ArrayList<T> lista) {
       if(actual != null){
            lista.add(actual.element);
            preOrder(actual.izq, lista);
            preOrder(actual.der, lista);
       }
    }
    */
    
    @Override
    public Iterator<T> levelOrder() {
        ArrayList<T> lista = new ArrayList<>();
        Queue<NodoBT<T>> cola = new LinkedList<>();
        cola.add(raiz);
        NodoBT<T> aux;
        while(!cola.isEmpty()){
            aux = cola.remove();
            lista.add(aux.element);
            if(aux.izq != null)
                cola.add(aux.izq);
            if(aux.der != null)
                cola.add(aux.der);
        }
       return lista.iterator();
    }
    
    public int altura(){
        return altura(raiz);
    }
    
    private int altura(NodoBT<T> actual){
        if(actual == null)
            return 0;
        else
            return 1+Math.max(altura(actual.izq), altura(actual.der));
    }
    
    public int alturaIt(){
        int max = 0;
        Stack<NodoBT<T>> nodos = new Stack<>();
        Stack<Integer> niveles = new Stack<>();
        if(raiz != null){
            nodos.add(raiz);  
            niveles.add(1);
        }
        NodoBT<T> aux;
        Integer nivel;
        while(!nodos.isEmpty()){
            aux = nodos.pop();
            nivel = niveles.pop();
            max = Math.max(max, nivel);
            if(aux.izq != null){
                nodos.push(aux.izq);
                niveles.push(nivel+1);
            }
            if(aux.der != null){
                nodos.push(aux.der);
                niveles.push(nivel+1);
            }
        }
        return max;
    }
}
