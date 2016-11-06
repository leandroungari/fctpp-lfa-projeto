/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codificacoes.representacaoComputacional;

/**
 *
 * @author BRUNO
 */
public class Adjacencia {
    
    private int numeroVertices;
    private RepresentacaoComputacional representacao;
    private boolean tipoGrafo;
    
    public Adjacencia(int numeroVertices, RepresentacaoComputacional representacao, boolean tipoGrafo){
        
        this.setNumeroVertices(numeroVertices);
        this.representacao = representacao;
        this.tipoGrafo = tipoGrafo;
    }

    public int getNumeroVertices() {
        return numeroVertices;
    }

    public void setNumeroVertices(int numeroVertices) {
        this.numeroVertices = numeroVertices;
    }
    
    public void adicionaAresta(int i, int j, int peso){
        
        this.representacao.adicionaAresta(i, j, peso);
    }
    
    public boolean verificaAdjacencia(int i, int j){
        
        return(this.representacao.verificarAdjacencia(i, j));
    }
    
    public int getPeso(int i, int j){
        
        return(this.representacao.getPeso(i, j));
    }
    
    public void exibir(){
        
        System.out.println("Número de vertices: " + this.numeroVertices);
        this.representacao.exibir();
    }
    
    public boolean hasNext(int pos){
        
        return(this.representacao.hasNext(pos));
    }
    
    public int next(int pos){
        
        return(this.representacao.next(pos));
    }
    
    public void inicializaIterator(int pos){
        
        this.representacao.inicializaIterator(pos);
    }

    public boolean isTipoGrafo() {
        return tipoGrafo;
    }

    public void setTipoGrafo(boolean tipoGrafo) {
        this.tipoGrafo = tipoGrafo;
    }

    public RepresentacaoComputacional getRepresentacao() {
        return representacao;
    }
    
}
