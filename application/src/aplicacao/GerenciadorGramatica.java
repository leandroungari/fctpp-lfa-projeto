/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date December 29,2016
 */
public class GerenciadorGramatica {

    private HashMap<String, ArrayList<String>> tabela = new HashMap<>();

    public void montarGramatica(TableView tabela, Button ex, Button clear, TextField entrada) {

        //executar
        ex.setOnAction(event -> {

            LinhaTabela linha;
            ArrayList<String> lista;

            Object[] o = tabela.getItems().toArray();
            for (Object a : o) {
                linha = (LinhaTabela) a;

                if (!linha.getNaoTerminal().isEmpty()) {
                    
                    if (this.tabela.containsKey(linha.getNaoTerminal())) {
                        lista = this.tabela.get(linha.getNaoTerminal());
                        lista.add(linha.getTerminal());
                    } else {
                        lista = new ArrayList<>();
                        lista.add(linha.getTerminal());
                        this.tabela.put(linha.getNaoTerminal(), lista);
                    }
                }
            }
            
            String entradaPadrao = entrada.getText();
            int count = 0;
            String initialSymbol = ((LinhaTabela) tabela.getItems().get(0)).getNaoTerminal();
            // Chave + ArrayList
            int i = 0;
            boolean verificacao = true;
            while(i < entradaPadrao.length()){
                //how can i identify if it is a left-linear grammar or right-linear grammar?
                //what is the best solution to jump from a non-terminal symbol to its rule?
                
                
            }
            
            
        });

        //clear
        clear.setOnAction(event -> {

            tabela.getItems().clear();
            tabela.getItems().add(new LinhaTabela("", "->", ""));
        });

    }
}
