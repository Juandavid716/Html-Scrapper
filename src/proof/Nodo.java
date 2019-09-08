/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proof;

/**
 *
 * @author ESTU-5K
 */
public class Nodo {
    Nodo izquierdo;
    Nodo derecho;
    Object dato;
    int nohijos;
    Nodo hijos[];
    Nodo hijosT[];
public void copiarHijos(){
hijosT = new Nodo[nohijos+1];
    for (int i = 0; i < this.nohijos; i++) {
        hijosT[i] = hijos[i];
    }

}
 public void verNodo(){
        System.out.println(dato);
    }
  
public void aumentarHijo(Nodo nodo){
copiarHijos();
hijosT[this.nohijos] = nodo;
hijos = hijosT;
this.nohijos++;
}
    public Nodo( Object dato) {
        
        this.dato = dato;
    }


    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }
    
    
}
