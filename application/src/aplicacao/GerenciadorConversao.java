/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import javafx.scene.control.TextField;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date January 23,2017
 */
public class GerenciadorConversao {

    public static void converterExpressaoAutomato(TextField field) {
    
        String expressao = field.getText();
        GerenciadorAutomatos.armazenarAutomato();
        
        
    }

    public static void converterAutomatoExpressao(){
        
    }

    public static void converterGramaticaAutomato() {

    }
    
    public static void converterAutomatoGramatica(){
        
    }

}
