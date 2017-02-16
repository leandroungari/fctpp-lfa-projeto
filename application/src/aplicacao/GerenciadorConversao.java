/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import static aplicacao.GerenciadorAutomatos.automato;
import static aplicacao.GerenciadorAutomatos.quantidade;
import desenho.Desenho;
import desenho.Legenda;
import desenho.Vertice;
import java.beans.Expression;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    //Problemas, na ideia
    public static void converterGramaticaAutomato(TableView tabela) {

        FXMLPrincipalController.painelD.getChildren().clear();

        FXMLPrincipalController.lista.clear();
        FXMLPrincipalController.arestas.clear();
        FXMLPrincipalController.legendas.clear();

        GerenciadorAutomatos.automato = new Automato();

        Gramatica grammar = new Gramatica();

        Object[] t = tabela.getItems().toArray();
        LinhaTabela linha;
        int i = 0;
        ArrayList<LinhaTabela> lista = new ArrayList<>();
        for (Object o : t) {
            linha = (LinhaTabela) o;
            if (linha.getNaoTerminal().isEmpty()) {
                break;
            }
            lista.add(linha);
            automato.addEstado(i, 0, 0, false);

            boolean teste = grammar.create(i, linha.getNaoTerminal());
            
            if (i == 0) {
                grammar.setInitial(i);

                try {
                    automato.setInicial(automato.get(i));
                } catch (Exception ex) {
                    Logger.getLogger(GerenciadorConversao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (teste) {
                FXMLPrincipalController.lista.add(new Vertice(i, 0, 0, 20));
                i++;
            }
        }
        LinhaTabela a = new LinhaTabela("Z", "->", "λ");
        lista.add(a);
        automato.addEstado(i, 0, 0, true);
        FXMLPrincipalController.lista.add(new Vertice(i, 0, 0, 20));

        grammar.create(i, a.getNaoTerminal());

        desenho.Desenho.computeCircledPosition(grammar, 140);
        boolean isEsquerda = false;
        for (LinhaTabela l : lista) {

            if (l.getTerminal().length() == 2) {
                if (Character.isLowerCase(l.getTerminal().charAt(0))) {
                    isEsquerda = false;
                    break;
                } else {
                    isEsquerda = true;
                    break;
                }
            }

        }

        for (LinhaTabela l : lista) {
            grammar.add(l.getNaoTerminal(), l.getTerminal());
        }

        for (Estados est : automato.getLista()) {
            
            if (est.getValor() > grammar.getNaoTerminais().size() - 1) break;
            String as = (String) grammar.get(est.getValor());
            
            for (String s : grammar.getRegra().get(as)) {

                int alvo = -1, pos = -1;
                if (s.length() == 2) {
                    pos = (isEsquerda ? 0 : 1);
                    for (Integer j : grammar.getNaoTerminais().keySet()) {

                        if ((grammar.getNaoTerminais().get(j)).equals("" + s.charAt(pos))) {
                            alvo = j;
                            break;
                        }
                    }
                } else if (Character.isUpperCase(s.charAt(0))) {
                    pos = 0;
                    for (int j = 0; j < grammar.getNaoTerminais().size(); j++) {
                        if (((String) grammar.getNaoTerminais().get(j)).equals(s.charAt(pos))) {
                            alvo = j;
                            break;
                        }
                    }
                }

                if (s.length() == 2) {

                    if (isEsquerda) {
                        est.addTransicao(alvo, s.charAt(1) + "");

                    } else {
                        est.addTransicao(alvo, s.charAt(0) + "");
                    }

                } else if (Character.isUpperCase(s.charAt(0)) && (s.charAt(0) != 'λ')) {

                    est.addTransicao(alvo, "λ");
                } else if (Character.isLowerCase(s.charAt(0)) && (s.charAt(0) != 'λ')) {

                    est.addTransicao(grammar.getNaoTerminais().size() - 1, s.charAt(0) + "");

                } else {

                    est.setIsFinal(true);
                }
            }
        }

        quantidade = 0;
        String texto = "";
        Vertice inicio = null, fim = null;
        int k = 0;
        for (Estados est : automato.getLista()) {

            Vertice v = FXMLPrincipalController.lista.get(k);
            Desenho.desenharVertice(FXMLPrincipalController.painelD, v);

            if (est.isIsFinal()) {
                FXMLPrincipalController.lista.get(k).selecionar();
            }

            if (est == automato.getInicial()) {
                v.inicio.setLayoutX(v.getCenterX() - 35);
                v.inicio.setLayoutY(v.getCenterY() - 15);

                FXMLPrincipalController.painelD.getChildren().add(v.inicio);
            }

            for (int j = 0; j < FXMLPrincipalController.lista.size(); j++) {

                if (FXMLPrincipalController.lista.get(j).getID() == est.getValor()) {
                    inicio = FXMLPrincipalController.lista.get(j);
                }
            }

            for (Transicao trans : est.getLista()) {

                for (int j = 0; j < FXMLPrincipalController.lista.size(); j++) {

                    if (FXMLPrincipalController.lista.get(j).getID() == trans.getAlvo().getValor()) {
                        fim = FXMLPrincipalController.lista.get(j);
                    }
                }

                texto = trans.getChave();

                Desenho.desenharAresta(FXMLPrincipalController.painelD, inicio, fim, texto);
            }
            k++;
        }

        FXMLPrincipalController.conjunto.getSelectionModel().select(FXMLPrincipalController.tabautoD);

    }

    public static void converterAutomatoExpressao() {
        GerenciadorAutomatos.armazenarAutomato();
        int a = GerenciadorAutomatos.automato.getLista().size();
        GerenciadorAutomatos.automato.addEstado(a, 0, 0, true);
        Estados novo = null;

        try {
            novo = GerenciadorAutomatos.automato.get(a);
        } catch (Exception ex) {
            Logger.getLogger(GerenciadorConversao.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Estados est : GerenciadorAutomatos.automato.getLista()) {

            if (est.isIsFinal()) {
                est.addTransicao(novo.getValor(), "λ");
                est.setIsFinal(false);
            }

            for (Estados estado : GerenciadorAutomatos.automato.getLista()) {

                if (!est.hasTransicao(estado.getValor())) {
                    est.addTransicao(estado.getValor(), "Ø");
                }
            }
        }
        
        //resolver
    }

}
