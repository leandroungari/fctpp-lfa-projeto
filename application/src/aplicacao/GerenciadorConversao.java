/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import desenho.Legenda;
import desenho.Vertice;
import java.util.ArrayList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date January 23,2017
 */
public class GerenciadorConversao {

    public static void converterExpressaoAutomato(TextField field) {

        String expressao = field.getText();

    }

    public static void converterAutomatoGramatica(TableView tabela) {

        GerenciadorAutomatos.armazenarAutomato();
        Gramatica grammar = new Gramatica();

        int i = 0;
        grammar.create(GerenciadorAutomatos.automato.getInicial().getValor(), Gramatica.lista[i]);

        for (Estados est : GerenciadorAutomatos.automato.getLista()) {

            if (GerenciadorAutomatos.automato.getInicial() != est) {
                i++;
                grammar.create(est.getValor(), Gramatica.lista[i]);
            }
        }

        for (Estados est : GerenciadorAutomatos.automato.getLista()) {

            for (Transicao t : est.getLista()) {

                if (t.getChave().equals("λ")) {
                    
                    if (t.getAlvo().getValor() != est.getValor()) {
                        grammar.add(grammar.get(est.getValor()), grammar.get(t.getAlvo().getValor()));
                    }
                    
                    
                } else {
                    grammar.add(grammar.get(est.getValor()), t.getChave() + grammar.get(t.getAlvo().getValor()));
                }
            }

            if (est.isIsFinal()) {
                grammar.add(grammar.get(est.getValor()), "λ");
            }
        }
        
        
        tabela.getItems().clear();

        for (String valor : grammar.getRegra().get("S")) {

            tabela.getItems().add(new LinhaTabela("S", "->", valor));
        }

        for (String s : grammar.getRegra().keySet()) {

            if (!s.equals("S")) {

                for (String valor : grammar.getRegra().get(s)) {

                    tabela.getItems().add(new LinhaTabela(s, "->", valor));
                }
            }

        }

        for (Vertice v : FXMLPrincipalController.lista) {
            
            if (v.legenda != null && FXMLPrincipalController.painelD.getChildren().contains(v.legenda.get())) {
                FXMLPrincipalController.painelD.getChildren().remove(v.legenda.get());
            }
            
            v.contextMenu.getItems().get(2).setDisable(true);
            v.legenda = new Legenda(0, 0);
            v.legenda.setText(grammar.get(v.getID()));
            v.legenda.get().setTextAlignment(TextAlignment.CENTER);
            v.legenda.setLayout(v.getCenterX() - 12, v.getCenterY() + 17);
            v.legenda.get().toFront();
            
            FXMLPrincipalController.painelD.getChildren().add(v.legenda.get());
        }

        FXMLPrincipalController.conjunto.getSelectionModel().select(FXMLPrincipalController.tabgramD);

    }

    public static void converterGramaticaAutomato() {

    }

    public static void converterAutomatoExpressao() {
        GerenciadorAutomatos.armazenarAutomato();

    }

}
