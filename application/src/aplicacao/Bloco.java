/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date   February 15,2017
 */
class Bloco {
    
    private String texto;
    private int origem;
    private int destino;

    public Bloco(String texto, int origem, int destino) {
        this.texto = texto;
        this.origem = origem;
        this.destino = destino;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getOrigem() {
        return origem;
    }

    public void setOrigem(int origem) {
        this.origem = origem;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }
    
}
