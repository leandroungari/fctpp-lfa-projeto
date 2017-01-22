/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.util.ArrayList;

public class Automato {
    
    private ArrayList<Estados> lista = new ArrayList<>();
    private Estados inicial;

    public Automato() {
    }

    public ArrayList<Estados> getLista() {
        return lista;
    }


    public Estados getInicial() {
        return inicial;
    }

    public void setInicial(Estados inicial) {
        this.inicial = inicial;
    }
    
    
}
