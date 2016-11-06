/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package desenho;

import java.util.ArrayList;
import javafx.scene.Cursor;
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
        
        this.numero = new Text("q" + id);
        numero.setFont(new Font(20));
        numero.setFill(Paint.valueOf("#fff"));
        
        
        this.setOnMouseEntered(event ->{
            this.getScene().setCursor(Cursor.OPEN_HAND);
        });
        
        this.setOnMouseExited(event ->{
            this.getScene().setCursor(Cursor.DEFAULT);
        });
        
    }

    public void setContorno(){
        
        this.numero.toFront();
        if(selected){
            this.desselecionarVertice();
        }
        else{
            this.selecionarVertice();
        }
    }
    
    public int getID(){
        return this.ID;
    }

    public void setID(int id){
        this.ID = id;
    }
    
    public void corrigirArestas(Grafo grafo){
        
        for(int a: origem) {
            
            grafo.edges.get(a).setInicio();
        }
        for(int b: destino) grafo.edges.get(b).setDestino();
        this.numero.setLayoutX(this.getLayoutX()-4);
        this.numero.setLayoutY(this.getLayoutY()+4);
        this.numero.toFront();
    }

    public boolean isSelected() {
        return selected;
    }
    
    
    public void selecionarVertice(){
        
        this.setStroke(Paint.valueOf("#000"));
        this.setStrokeWidth(8);
        selected = true;
    }
    
    public void desselecionarVertice(){
        
        this.setStroke(Paint.valueOf("transparent"));
        this.setStrokeWidth(1);
        selected = false;
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
