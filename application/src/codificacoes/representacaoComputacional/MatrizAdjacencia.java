/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codificacoes.representacaoComputacional;

import java.util.Iterator;

/**
 *
 * @author BRUNO
 */
public class MatrizAdjacencia extends RepresentacaoComputacional {
    
    //private int matriz[][];
    private itemMatriz matriz[][];
    public int posicaoAtual[];
    
    public MatrizAdjacencia(int numeroVertices){
        
        super(numeroVertices);
        //this.matriz = new int[numeroVertices][numeroVertices];
        this.matriz = new itemMatriz[numeroVertices][numeroVertices];
        this.posicaoAtual = new int[this.numeroVertices];
        
        for(int i = 0; i < this.matriz.length; i++){
            
            for(int j = 0; j < this.matriz[i].length; j++){
                
                this.matriz[i][j] = new itemMatriz(true);
            }
            
        }
        
    }

    @Override
    public void adicionaAresta(int i, int j, int peso) {
        //this.matriz[i][j] = peso;
        this.matriz[i][j].setPeso(peso);
        this.matriz[i][j].setContem(true);
    }

    @Override
    public boolean verificarAdjacencia(int i, int j) {
        //if(this.matriz[i][j] != 0) return(true);
        if(this.matriz[i][j].getContem() == true) return(true);
        return(false);
    }
    
    @Override
    public int getPeso(int i, int j) {
        //return(this.matriz[i][j]);
        return(this.matriz[i][j].getPeso());
    }

    @Override
    public void exibir() {
        
        String matriz = "";
        
        for(int i = 0; i < this.matriz.length; i++){
            
            for(int j = 0; j < this.matriz[i].length; j++){
                
                System.out.print(this.matriz[i][j].getPeso() + " ");
            }
            
            System.out.println("");
        }
        
    }

    
    public boolean hasNext(int pos) {
        
        this.posicaoAtual[pos]++;
        while(this.posicaoAtual[pos] < this.matriz[pos].length){
            
            if(this.matriz[pos][this.posicaoAtual[pos]].getContem() == true) return(true);
            this.posicaoAtual[pos]++;
            
        }
        return(false);
    }

    public int next(int pos) {

        return(this.posicaoAtual[pos]);
    }
    
    public void inicializaIterator(int pos){
        
        this.posicaoAtual[pos] = -1;
    }
    
    private class itemMatriz{
        
        private boolean contem;
        private int peso;
        
        public itemMatriz(boolean cotem, int peso){
            
            this.setContem(contem);
            this.setPeso(peso);
        }
        
        public itemMatriz(boolean cotem){
            
            this.setContem(contem);
        }

        public boolean getContem() {
            return contem;
        }

        public void setContem(boolean contem) {
            this.contem = contem;
        }

        public int getPeso() {
            return peso;
        }

        public void setPeso(int peso) {
            this.peso = peso;
        }
        
        
    }

}
