/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date January 14,2017
 */
public class Estados {

    private int valor;
    private boolean isFinal;
    private float x, y;
    private String adesivo;
    private ArrayList<Transicao> lista = new ArrayList<>();
    private Automato automato;

    public Estados(int valor, boolean isFinal) {
        this.valor = valor;
        this.isFinal = isFinal;
    }

    public int getValor() {
        return valor;
    }

    public String getAdesivo() {
        return adesivo;
    }

    public void setAdesivo(String adesivo) {
        this.adesivo = adesivo;
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void addTransicao(int alvo, String letra) {


        for (Transicao t : lista) {
            if (t.getAlvo().getValor() == alvo && t.getChave().equals(letra)) {

                return;
            } else if (Automato.composta && t.getAlvo().getValor() == alvo && (!t.getChave().equals(letra))) {
                
                t.setChave("("+t.getChave() + "|" + letra+")");
                return;
            }
        }

        try {

            this.lista.add(new Transicao(letra, GerenciadorAutomatos.automato.get(alvo)));

        } catch (Exception ex) {
            Logger.getLogger(Estados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean hasTransicao(int destino) {

        for (Transicao t : this.getLista()) {
            if (t.getAlvo().getValor() == destino) {
                return true;
            }
        }
        return false;
    }

    public Automato getAutomato() {
        return automato;
    }

    public void setAutomato(Automato automato) {
        this.automato = automato;
    }

}
