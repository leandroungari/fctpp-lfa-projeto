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
    public static boolean composta = false;

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
    
    public void addEstado(int id, float x, float y, boolean isFinal){
        
        for(Estados e: lista){
            if(e.getValor() == id) return;
        }
        
        Estados est = new Estados(id, isFinal);
        est.setAutomato(this);
        est.setX(x);
        est.setY(y);
        lista.add(est);
    }
    
    public Estados get(int id) throws Exception{
        
        for(Estados e: lista){
            if (e.getValor() == id) return e;
        }
        
        throw new Exception("Doesn't exist this element ID!");
    }
    
    public void setInicial(int id) throws Exception{
        
        this.inicial = this.get(id);
    }
    
}
