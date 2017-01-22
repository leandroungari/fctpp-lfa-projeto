/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

/**
 *
 * @author BRUNO
 */
public class VerificacaoEE {
    
    public int numEstado;
    public int posPalavra;

    public VerificacaoEE(int numEstado, int posPalavra) {
        this.numEstado = numEstado;
        this.posPalavra = posPalavra;
    }
    
    public int getNumEstado() {
        return numEstado;
    }

    public void setNumEstado(int numEstado) {
        this.numEstado = numEstado;
    }

    public int getPosPalavra() {
        return posPalavra;
    }

    public void setPosPalavra(int posPalavra) {
        this.posPalavra = posPalavra;
    }
    
    
}
