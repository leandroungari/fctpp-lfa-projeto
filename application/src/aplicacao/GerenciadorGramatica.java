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

    public static String resultadoGramatica;

    public static boolean linearDireita;

    public void montarGramatica(TableView tabela, Button clear) {

        clear.setOnAction(event -> {

            tabela.getItems().clear();
            tabela.getItems().add(new LinhaTabela("", "->", ""));
        });
    }

    public static void armazenarGramatica(TableView tabela) {

        //cria o vetor de arraylists em que cada linha corresponde a um não terminal
        estrutura = new ArrayList[listaNaoTerminais.size()];
        for (int i = 0; i < listaNaoTerminais.size(); i++) {
            estrutura[i] = new ArrayList<>();
        }

        LinhaTabela linha;
        boolean verificou = true;
        Object[] o = tabela.getItems().toArray();
        for (Object a : o) {
            linha = (LinhaTabela) a;

            if (!linha.getNaoTerminal().isEmpty()) {

                if (verificou && linha.getTerminal().length() == 2) {
                    verificou = false;

                    if (Character.isLowerCase(linha.getTerminal().charAt(0))) {
                        linearDireita = true;
                    } else {
                        linearDireita = false;
                    }
                }

                estrutura[listaNaoTerminais.indexOf(linha.getNaoTerminal())].add(linha.getTerminal());
            }

        }

        /**
         * Gramática S -> vazio | aA A -> a | aB B -> bB B -> Vazio
         *
         *
         * ArrayList listaNaoTerminais -- O estado inicial do autômato é a
         * primeira posição, no caso o "S" ["S","A","B"] 0 1 2
         *
         * Vetor Estrutura [0] -> [vazio,"aA"] // corresponde ao S [1] ->
         * ["a","aB"] // corresponde ao A [2] -> [vazio,"bB"] // corresponde ao
         * B
         *
         *
         */

    }

    public static void processamentoGramatica(String palavraDeEntrada) {

        verificacao = false;
        resultadoGramatica = "";
        int inicio;
        if(linearDireita){
            inicio = 0;
        }
        else{
            inicio = palavraDeEntrada.length()-1;
        }
        
        verificaRegra(palavraDeEntrada, inicio, estrutura, 0, palavraDeEntrada.length());

    }

    public static void verificaRegra(String palavraDeEntrada, int posPalavra, ArrayList<String> estrutura[], int posI, int posF) {

        int proxPosI = posI;
        for (int i = 0; i < estrutura[posI].size(); i++) {

            if (0 <= posPalavra && posPalavra < posF) {

                if (linearDireita) { // GLUD

                    if (estrutura[posI].get(i).charAt(0) == palavraDeEntrada.charAt(posPalavra)) {

                        if (estrutura[posI].get(i).length() == 1) {

                            if (posPalavra == posF - 1) {
                                verificacao = true;
                                resultadoGramatica = estrutura[posI].get(i) + resultadoGramatica;

                                if (posPalavra != 0) {
                                    resultadoGramatica = " -> " + resultadoGramatica;
                                }

                                return;
                            } else {
                                continue;
                            }
                        } else { //Se tem tamanho dois tem um não terminal
                            //aux = 1;
                            proxPosI = identificaNaoTerminal(estrutura[posI].get(i).charAt(1));

                            verificaRegra(palavraDeEntrada, posPalavra + 1, estrutura, proxPosI, posF);

                            if (verificacao) {
                                resultadoGramatica = estrutura[posI].get(i) + resultadoGramatica;

                                if (posPalavra != 0) {
                                    resultadoGramatica = " -> " + resultadoGramatica;
                                }
                                return;
                            }
                        }
                    } else {

                        if (Character.isUpperCase(estrutura[posI].get(i).charAt(0))) {

                            proxPosI = identificaNaoTerminal(estrutura[posI].get(i).charAt(0));

                            verificaRegra(palavraDeEntrada, posPalavra, estrutura, proxPosI, posF);

                            if (verificacao) {
                                resultadoGramatica = estrutura[posI].get(i) + " -> " + resultadoGramatica;

                                /*if (posPalavra != 0) {
                                    resultadoGramatica = " -> " + resultadoGramatica;
                                }*/

                                return;
                            }
                        }
                    }

                } else { //GLUE
                    
                    char t = estrutura[posI].get(i).charAt(estrutura[posI].get(i).length()-1);
                    if (t == palavraDeEntrada.charAt(posPalavra)) {

                        if (estrutura[posI].get(i).length() == 1) {

                            if (posPalavra == 0) {
                                verificacao = true;
                                resultadoGramatica = estrutura[posI].get(i) + resultadoGramatica;

                                if (posPalavra != posF-1) {
                                    resultadoGramatica = " -> " + resultadoGramatica;
                                }

                                return;
                            } else {
                                continue;
                            }
                        } else { //Se tem tamanho dois tem um não terminal
                            //aux = 1;
                            proxPosI = identificaNaoTerminal(estrutura[posI].get(i).charAt(0));

                            verificaRegra(palavraDeEntrada, posPalavra - 1, estrutura, proxPosI, posF);

                            if (verificacao) {
                                resultadoGramatica = estrutura[posI].get(i) + resultadoGramatica;

                                if (posPalavra != posF-1) {
                                    resultadoGramatica = " -> " + resultadoGramatica;
                                }
                                return;
                            }
                        }
                    } else {

                        if (estrutura[posI].get(i).length() == 1 && Character.isUpperCase(estrutura[posI].get(i).charAt(0))) {

                            proxPosI = identificaNaoTerminal(estrutura[posI].get(i).charAt(0));

                            verificaRegra(palavraDeEntrada, posPalavra, estrutura, proxPosI, posF);

                            if (verificacao) {
                                resultadoGramatica =  estrutura[posI].get(i) + " -> " + resultadoGramatica;

                                /*if (posPalavra != posF-1) {
                                    resultadoGramatica = " -> " + resultadoGramatica;
                                }*/

                                return;
                            }
                        }
                    }
   
                }
                //else break;
            } else {

                if (Character.isUpperCase(estrutura[posI].get(i).charAt(0)) && estrutura[posI].get(i).length() == 1) {
                    
                    proxPosI = identificaNaoTerminal(estrutura[posI].get(i).charAt(0));

                    verificaRegra(palavraDeEntrada, posPalavra, estrutura, proxPosI, posF);

                    if (verificacao) {
                        resultadoGramatica = estrutura[posI].get(i) + resultadoGramatica;

                        if (posPalavra != 0 || posPalavra != posF-1) {
                            resultadoGramatica = " -> " + resultadoGramatica;
                        }
                        return;
                    }
                    
                } else if (estrutura[posI].get(i).charAt(0) == 'λ') {
                    verificacao = true;
                    
                    if (verificacao) {
                        resultadoGramatica = estrutura[posI].get(i) + resultadoGramatica;

                        if (posPalavra != 0 || posPalavra != posF-1) {
                            resultadoGramatica = " -> " + resultadoGramatica;
                        }
                        return;
                    }
                    
                }

            }

        }

        //Não bateu a string, não conseguiu percorrer as regras, pois não bateu o padrão de entrada
        //if(verificacao != true) verificacao = false;
    }

    //Identifca o não terminal que entrara na proxima chamada da recursão, retornado sua posição no array estrutura.
    public static int identificaNaoTerminal(char naoTerminal) {

        for (int i = 0; i < listaNaoTerminais.size(); i++) {

            if (listaNaoTerminais.get(i).charAt(0) == naoTerminal) {
                return (i);
            }
        }

        return (-1); //Nunca vai retornar -1 !!!
    }

}
