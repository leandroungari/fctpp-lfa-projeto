/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 */
public class FXMLEntradaMultiplaController implements Initializable {

    @FXML
    private Button executar;

    @FXML
    private Button limpar;

    @FXML
    private Button adicionar;

    @FXML
    private TextField entrada;

    @FXML
    private TableView tabela;
    @FXML
    private TableColumn naoTerminal;
    @FXML
    private TableColumn seta;
    @FXML
    private TableColumn terminal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        naoTerminal.setCellValueFactory(new PropertyValueFactory<LinhaTabela, String>("naoTerminal"));
        seta.setCellValueFactory(new PropertyValueFactory<LinhaTabela, String>("seta"));
        terminal.setCellValueFactory(new PropertyValueFactory<LinhaTabela, String>("terminal"));

        limpar.setOnAction(event -> {
            entrada.setText("");
            tabela.getItems().clear();
        });

        adicionar.setOnAction(event -> {

            LinhaTabela linha = new LinhaTabela(entrada.getText(), "->", "");
            tabela.getItems().add(linha);

        });

        executar.setOnAction(event -> {

            GerenciadorGramatica.armazenarGramatica(FXMLPrincipalController.tabelaD);

            //usar a variavel estrutura da classe GerenciarAutomatos
            Object[] listas = tabela.getItems().toArray();
            LinhaTabela linha;
            ArrayList<String> valoresEntrada = new ArrayList<>();
            for (int i = 0; i < listas.length; i++) {
                linha = (LinhaTabela) listas[i];
                valoresEntrada.add(linha.getNaoTerminal());
            }

            ArrayList<String> resultado = new ArrayList<>();

            //o  ArrayList valoresEntrada contém os as strings de entrada
            for (String s : valoresEntrada) {

                GerenciadorGramatica.processamentoGramatica(s);

                if (GerenciadorGramatica.verificacao == true) {
                    resultado.add("Aprovado");
                } else {
                    resultado.add("Rejeitado");
                }
            }

            Object[] vetor = tabela.getItems().toArray();

            LinhaTabela li;
            for (int i = 0; i < vetor.length; i++) {
                li = (LinhaTabela) vetor[i];
                li.setTerminal(resultado.get(i));
            }

            Object[] t = tabela.getItems().toArray();
            tabela.getItems().clear();
            int i;
            for (i = 0; i < t.length; i++) {

                tabela.getItems().add(t[i]);
            }
        });

    }

}
