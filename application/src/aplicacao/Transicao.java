/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date   January 14,2017
 */
public class Transicao {
    
    
    private String chave;
    private Estados alvo;

    public Transicao(String chave, Estados alvo) {
        this.chave = chave;
        this.alvo = alvo;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public Estados getAlvo() {
        return alvo;
    }

    public void setAlvo(Estados alvo) {
        this.alvo = alvo;
    }
    
    
}
