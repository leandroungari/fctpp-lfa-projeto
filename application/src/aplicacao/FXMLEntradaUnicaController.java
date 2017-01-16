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


/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 */
public class FXMLEntradaUnicaController implements Initializable {
    
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
            
           GerenciadorGramatica.armazenarGramatica(FXMLPrincipalController.tabelaD);
           
           String entradaTexto = entrada.getText();
           //usar a variavel estrutura da classe GerenciarAutomatos
           
           
        });
        
    }  
    
}
