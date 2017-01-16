/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date December 29,2016
 */
public class GerenciadorGramatica {

    public static ArrayList<String> listaTerminais = new ArrayList<>(); //vetor com os terminais
    public static ArrayList<String> listaNaoTerminais = new ArrayList<>(); //vetor com não-terminais

    public static ArrayList<String>[] estrutura;
    
    

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
         * A -> a | b
         * B -> vazio
         * 
         * 
         * ArrayList listaNaoTerminais
         * -- O estado inicial do autômato é a primeira posição, no caso o "S"
         * ["S","A","B"]
         *   0   1   2
         * 
         * Vetor Estrutura
         * [0] -> [vazio,"aA"] // corresponde ao S
         * [1] -> ["a","b"]    // corresponde ao A
         * [2] -> [vazio]      // corresponde ao B
         * 
         * 
         */
    }
}
