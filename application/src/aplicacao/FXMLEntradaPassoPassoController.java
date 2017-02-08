/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import desenho.Vertice;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 */
public class FXMLEntradaPassoPassoController implements Initializable {

    @FXML
    private Button proximo;

    @FXML
    private Button fim;

    @FXML
    private Button limpar;

    @FXML
    private TextField entrada;

    @FXML
    private Button calcular;

    @FXML
    private Label mensagem;

    private static int atual;
    private static ArrayList<Integer> list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        proximo.setDisable(true);
        fim.setDisable(true);

        proximo.setOnAction(event -> {

            if (atual == 0) {
                for (Vertice v : FXMLPrincipalController.lista) {
                    v.setFill(Color.DODGERBLUE);
                }
            }

            if (atual < GerenciadorAutomatos.passoApasso.size()) {

                for (Vertice v : FXMLPrincipalController.lista) {

                    if (v.getID() == list.get((atual % list.size()))) {
                        v.setFill(Color.RED);
                    }

                }

                atual++;
            }
        });

        fim.setOnAction(event -> {

            for (Vertice v : FXMLPrincipalController.lista) {
                v.setFill(Color.DODGERBLUE);
            }

            for (Vertice v : FXMLPrincipalController.lista) {

                for (Integer a : list) {
                    if (v.getID() == a) {
                        v.setFill(Color.RED);
                    }
                }

            }

            atual = 0;
        });

        limpar.setOnAction(event -> {

            for (Vertice v : FXMLPrincipalController.lista) {
                v.setFill(Color.DODGERBLUE);
            }

            mensagem.setText("");

            atual = 0;
        });

        calcular.setOnAction(event -> {

            String entradaTexto = entrada.getText();
            atual = 0;
            GerenciadorAutomatos.armazenarAutomato();

            switch (FXMLPrincipalController.maquinaAtual) {

                case FXMLPrincipalController.MACHINE_AUTOMATO_FINITE:
                    GerenciadorAutomatos.processamentoAutomato(entradaTexto);

                    if (GerenciadorAutomatos.verificacao) {
                        proximo.setDisable(false);
                        fim.setDisable(false);
                        list = GerenciadorAutomatos.passoApasso;
                    } else {

                        mensagem.setText("Entrada rejeitada!");
                        mensagem.setTextFill(Color.RED);
                        proximo.setDisable(true);
                        fim.setDisable(true);
                    }
                    break;

                
            }

        });

        entrada.setOnKeyReleased(event -> {
            mensagem.setText("");
            proximo.setDisable(true);
            fim.setDisable(true);
        });
    }

}
