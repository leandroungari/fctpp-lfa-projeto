/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLEntradaUnicaAutomatoController implements Initializable {

    @FXML
    private Button executar;

    @FXML
    private Button limpar;

    @FXML
    private TextField entrada;

    @FXML
    private TextArea saida;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        limpar.setOnAction(event -> {
            entrada.setText("");
            saida.setText("");
        });

        executar.setOnAction(event -> {

            GerenciadorAutomatos.armazenarAutomato();

            //usar a variavel estrutura da classe GerenciarAutomatos
            String entradaTexto = entrada.getText();

            switch (FXMLPrincipalController.maquinaAtual) {
                case FXMLPrincipalController.MACHINE_AUTOMATO_FINITE:

                    GerenciadorAutomatos.processamentoAutomato(entradaTexto);
                    GerenciadorAutomatos.caminhoResultado = "";
                    if (GerenciadorAutomatos.verificacao) {
                        saida.setText("Aprovado.\nCaminho: " + GerenciadorAutomatos.caminhoResultado);
                    } else {
                        saida.setText("Rejeitado.");
                    }

                    break;
                case FXMLPrincipalController.MACHINE_MOORE:
                    
                    GerenciadorAutomatos.processarMoore(entradaTexto);
                    saida.setText(GerenciadorAutomatos.saidaMoore);
                    break;
                    
                case FXMLPrincipalController.MACHINE_MEALY:
                    GerenciadorAutomatos.processarMealy(entradaTexto);
                    saida.setText(GerenciadorAutomatos.saidaMealy);
                    break;
            }

        });

    }

}
