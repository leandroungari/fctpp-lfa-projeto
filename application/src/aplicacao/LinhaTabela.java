/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author BRUNO
 */
public class LinhaTabela {
    
    private final SimpleStringProperty naoTerminal;
    private final SimpleStringProperty seta;
    private final SimpleStringProperty terminal;

    public LinhaTabela(String naoTerminal, String seta, String terminal) {
        this.naoTerminal = new SimpleStringProperty(naoTerminal);
        this.seta = new SimpleStringProperty(seta);
        this.terminal = new SimpleStringProperty(terminal);
    }

    public String getNaoTerminal() {
        return naoTerminal.get();
    }

    public String getSeta() {
        return seta.get();
    }

    public String getTerminal() {
        return terminal.get();
    }
    
    public void setNaoTerminal(String naoTerminal){
        
        this.naoTerminal.set(naoTerminal);
    }
    
    public void setSeta(String seta){
        
        this.seta.set(seta);
    }
    
    public void setTerminal(String terminal){
        
        this.terminal.set(terminal);
    }

}
