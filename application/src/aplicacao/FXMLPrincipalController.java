/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import desenho.Desenho;
import desenho.Vertice;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import jfxtras.labs.scene.layout.ScalableContentPane;

/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 */
public class FXMLPrincipalController implements Initializable {
    
    @FXML
    private ScalableContentPane painelDesenho;
    
    private Pane root;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        root = painelDesenho.getContentPane();
        
        painelDesenho.setOnMousePressed(event -> {
        
            double x = event.getX();
            double y = event.getY();
            
            Vertice v = new Vertice(0, x, y, 20);
            v.setFill(Color.DODGERBLUE);
           
            Desenho.desenharVertice(root, v);
        });
        
        
    }    
    
}
