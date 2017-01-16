/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;


public class GerenciadorGramatica {

    public static ArrayList<String> listaTerminais = new ArrayList<>(); //vetor com os terminais
    public static ArrayList<String> listaNaoTerminais = new ArrayList<>(); //vetor com não-terminais

    public static ArrayList<String>[] estrutura;
    
    public static boolean verificacao = false;

    public void montarGramatica(TableView tabela, Button clear) {

        clear.setOnAction(event -> {

            tabela.getItems().clear();
            tabela.getItems().add(new LinhaTabela("", "->", ""));
        });
    }

    public static void armazenarGramatica(TableView tabela) {
        
        //cria o vetor de arraylists em que cada linha corresponde a um não terminal
        estrutura = new ArrayList[listaNaoTerminais.size()];
        for(int i = 0; i < listaNaoTerminais.size(); i++){
            estrutura[i] = new ArrayList<>();
        }
        
        LinhaTabela linha;
        
        Object[] o = tabela.getItems().toArray();
        for (Object a : o) {
            linha = (LinhaTabela) a;

            if (!linha.getNaoTerminal().isEmpty()) {
                
                estrutura[listaNaoTerminais.indexOf(linha.getNaoTerminal())].add(linha.getTerminal());
            }
            
        }
        
        /**
         * Gramática
         * S -> vazio | aA
         * A -> a | aB
         * B -> bB
         * B -> Vazio
         * 
         * 
         * ArrayList listaNaoTerminais
         * -- O estado inicial do autômato é a primeira posição, no caso o "S"
         * ["S","A","B"]
         *   0   1   2
         * 
         * Vetor Estrutura
         * [0] -> [vazio,"aA"] // corresponde ao S
         * [1] -> ["a","aB"]    // corresponde ao A
         * [2] -> [vazio,"bB"]      // corresponde ao B
         * 
         * 
         */
        
        for(int i = 0; i < listaNaoTerminais.size(); i++){
            System.out.println();
            for(String a : estrutura[i]){
                System.out.println(a);
            }
        }
        
        
        
    }
    
    /*
    public static void processamentoGramatica(String palavraDeEntrada){
        
        
    }
    
    public void verificacaoGramatica(){
        
        
    }*/
    
    
    public static void processamentoGramatica(String palavraDeEntrada){
        
        //char caracter;
        //for(int i = 0; i < palavraDeEntrada.length(); i++){
            
            //caracter = palavraDeEntrada.charAt(0);
            
            
            verificaRegra(palavraDeEntrada, 0, estrutura, 0, palavraDeEntrada.length());
        //}
        
    }
    
    public static void verificaRegra(String palavraDeEntrada, int posPalavra, ArrayList<String> estrutura[], int posI, int posF){
        
        int proxPosI = posI;
        for(int i = 0; i < estrutura[posI].size(); i++){
            
            if(estrutura[posI].get(i).charAt(0) == palavraDeEntrada.charAt(posPalavra)){ //Para GLUD
                
                int aux = 0;
                if(estrutura[posI].get(i).length() == 2){ //Se tem tamanho dois tem um não terminal
                    aux = 1;
                    proxPosI = identificaNaoTerminal(estrutura[posI].get(i).charAt(1));
                }
                
                if( (posPalavra+1) != posF ) {
                    System.out.println(posPalavra++);
                    verificaRegra(palavraDeEntrada, posPalavra++, estrutura, proxPosI, posF);
                }
                else {
                    //Bateu a string
                    if(aux == 1){
                        
                    }
                    else{
                        verificacao = true;
                        break;
                    }
                }
            }
            //else break;
        }
        
        //Não bateu a string, não conseguiu percorrer as regras, pois não bateu o padrão de entrada
        verificacao = false;
        
    }
    
    //Identifca o não terminal que entrara na proxima chamada da recursão, retornado sua posição no array estrutura.
    public static int identificaNaoTerminal(char naoTerminal){
        
        for(int i = 0; i < listaNaoTerminais.size(); i++){
            
            if(listaNaoTerminais.get(i).charAt(0) == naoTerminal) return(i);
        }
        return(-1); //Nunca vai retornar -1 !!!
    }
    
    
}