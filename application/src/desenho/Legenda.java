/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;

import aplicacao.FXMLPrincipalController;
import javafx.scene.Cursor;
import javafx.scene.control.Label;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date November 26,2016
 */
public class Legenda {
    
    private Label legenda;
    private double xRelative;
    private double yRelative;
    
    public Legenda(double x, double y) {
        
        legenda = new Label();
        legenda.setLayoutX(x);
        legenda.setLayoutY(y);
        legenda.setPrefHeight(13);
        legenda.setStyle("-fx-background-color: #1E90FF; -fx-padding: 5px 10px; -fx-text-fill: #fff; -fx-font-size: 12");
        
        legenda.setOnMouseEntered(event -> {
            legenda.getScene().setCursor(Cursor.OPEN_HAND);
        });

        legenda.setOnMouseExited(event -> {
            legenda.getScene().setCursor(Cursor.DEFAULT);
        });
        
        legenda.setOnMousePressed(event -> {
            FXMLPrincipalController.legendaAtual = this;
        });
        
        legenda.setOnMouseReleased(event -> {
            FXMLPrincipalController.legendaAtual = null;
        });
    }
    
    public void setText(String text){
        this.legenda.setText(text);
    }
    
    public Label get(){
        return this.legenda;
    }
    
    public void setLayout(double x, double y){
        this.legenda.setLayoutX(x);
        this.legenda.setLayoutY(y);
    }
    
    
}
