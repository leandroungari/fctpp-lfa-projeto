/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.util.ArrayList;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date   January 14,2017
 */
public class Estados {
    
    private int valor;
    private boolean isFinal;
    private ArrayList<Transicao> lista = new ArrayList<>();

    public Estados(int valor, boolean isFinal) {
        this.valor = valor;
        this.isFinal = isFinal;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isIsFinal() {
        return isFinal;
    }

    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public ArrayList<Transicao> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Transicao> lista) {
        this.lista = lista;
    }
    
    
    
}
