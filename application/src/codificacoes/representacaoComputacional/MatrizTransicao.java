/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codificacoes.representacaoComputacional;

import java.util.ArrayList;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date   January 06,2017
 */
public class MatrizTransicao {
    
    private int numeroEstados;
    private int numeroTransicoes;
    private String[] transicoes;
    private ArrayList<Integer>[][] tabela;
    
    public MatrizTransicao(int numeroEstados, String[] transicoes){
        
        this.transicoes = transicoes;
        this.tabela = new ArrayList[numeroEstados][transicoes.length];
        this.numeroEstados = numeroEstados;
        this.numeroTransicoes = transicoes.length;
        
        for(int i = 0; i < numeroEstados; i++)
            for(int j = 0; j < numeroTransicoes; j++)
                tabela[i][j] = new ArrayList<>();
        
    }
    
    public ArrayList<Integer> getEstadoAlvo(int origem, String transicao) throws Exception{
        
        int i;
        for(i = 0; i < numeroTransicoes; i++){
            if(transicoes[i].equals(transicao)){
                return tabela[origem][i];
            }
        }
        
        throw new Exception("Transição inexistente");
    }
    
    public void add(int origem, int alvo, String transicao){
        
        for(int i = 0; i < numeroTransicoes; i++){
            if(transicoes[i].equals(transicao)){
                
                tabela[origem][i].add(alvo);
            }
        }
    }
    
    
}
