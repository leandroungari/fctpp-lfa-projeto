/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package desenho;

import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * 
 * @author Leandro Ungari <leandroungari@gmail.com>
 * @date   May 15,2016
 */
public class Vertice extends Circle{
    
    private int ID;
    private boolean selected = false;
    public Text numero;
    
    protected ArrayList<Integer> origem = new ArrayList<>();
    protected ArrayList<Integer> destino = new ArrayList<>();
    
    public Vertice(int id, double x, double y, double raio) {
        
        super(x, y, raio);
        this.ID = id;
        this.setFill(Color.DODGERBLUE);
        
        this.numero = new Text("q" + id);
        this.numero.setFont(new Font(17));
        this.numero.setFill(Paint.valueOf("#fff"));
        this.numero.setLayoutX(this.getCenterX() - 10);
        this.numero.setLayoutY(this.getCenterY() + 5);
        
        this.setOnMouseEntered(event ->{
            this.getScene().setCursor(Cursor.OPEN_HAND);
        });
        
        this.setOnMouseExited(event ->{
            this.getScene().setCursor(Cursor.DEFAULT);
        });
    }
    
    public int getID(){
        return this.ID;
    }

    public void setID(int id){
        this.ID = id;
    }
    
    public void corrigir(){
        this.numero.setX(this.getLayoutX());
        this.numero.setY(this.getLayoutY());
        this.numero.toFront();
    }

    public boolean isSelected() {
        return selected;
    }
    
    public void opacidade(double value){
        this.setOpacity(value);
    }

    public ArrayList<Integer> getOrigem() {
        return origem;
    }

    public void setOrigem(ArrayList<Integer> origem) {
        this.origem = origem;
    }

    public ArrayList<Integer> getDestino() {
        return destino;
    }

    public void setDestino(ArrayList<Integer> destino) {
        this.destino = destino;
    }
}
