/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;

import javafx.scene.effect.Light.Point;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 * @date May 15,2016
 */
public abstract class Aresta {

    protected Vertice origem;
    protected Vertice destino;
    protected boolean directed;
    protected boolean selected = false;
    protected String texto;

    public Text labelTexto;
    public Point pa, pb, pc;
    public Line a, b;
    

    public boolean dupla = false;

    public Aresta(Vertice origem, Vertice destino, String texto, boolean directed) {

        this.origem = origem;
        this.destino = destino;
        this.directed = directed;
        this.texto = texto;
    }
    
    public abstract void setInicio();
    
    public abstract void setDestino();
    
    public void selecionarAresta() {
        
        this.getForma().setStrokeWidth(5);
        if(directed && origem.getID() != destino.getID()) {
            this.a.setStrokeWidth(5);
            this.b.setStrokeWidth(5);
        }
        
        selected = true;
    }

    public void desselecionarAresta() {
        this.getForma().setStrokeWidth(2);
        if(directed && origem.getID() != destino.getID()){
            this.a.setStrokeWidth(2);
            this.b.setStrokeWidth(2);
        }
        
        selected = false;
    }

    public void opacidade(double value) {
        if(directed && origem.getID() != destino.getID()){
            this.a.setOpacity(value);
            this.b.setOpacity(value);
        }
        
        this.getForma().setOpacity(value);
    }


    public boolean isDirected() {
        return directed;
    }

    public void posicionarTexto() {

        double r = Math.sqrt(Math.pow(origem.getLayoutX() - destino.getLayoutX(), 2) + Math.pow(origem.getLayoutY() - destino.getLayoutY(), 2));
        double cos = (destino.getLayoutX() - origem.getLayoutX()) / r;
        double sen = (destino.getLayoutY() - origem.getLayoutY()) / r;
        
        double meioX = (origem.getLayoutX() + destino.getLayoutX()) / 2;
        double meioY = (origem.getLayoutY() + destino.getLayoutY()) / 2;
        
        int deslocamento = 10;
        int size = 15;
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        labelTexto.setLayoutX(xAB * -cos - yA * -sen + meioX);
        labelTexto.setLayoutY(xAB * -sen + yA * -cos + meioY);
    }

    public abstract Shape getForma();
    
    public abstract void posicionarFlecha();

    public int getOrigem(){
        return this.origem.getID();
    }
    
    public int getDestino(){
        return this.destino.getID();
    }
    
    public Vertice getOrigemVertice(){
        return this.origem;
    }
    
    public Vertice getDestinoVertice(){
        return this.destino;
    }
    
    public String toString(){
        return " ["+this.getOrigem()+","+this.getDestino()+ "] ";
    }

    public Text getLabelTexto() {
        return labelTexto;
    }

    public void setLabelTexto(Text labelTexto) {
        this.labelTexto = labelTexto;
    }
    
    
}
