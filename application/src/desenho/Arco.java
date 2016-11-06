/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;

import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/*
 *   
 * @author Leandro Ungari <leandroungari@gmail.com>
 * @date   June 10,2016
 */
public class Arco extends Aresta {

    private QuadCurve forma;

    private int deslocamento = 14;
    private int size = 16;

    public Arco(Vertice origem, Vertice destino, int peso, boolean directed) {
        super(origem, destino, peso, directed);

        double meioX = Math.abs((destino.getLayoutX() + origem.getLayoutX()) / 2);
        double meioY = Math.abs((destino.getLayoutY() + origem.getLayoutY()) / 2);

        double r = Math.sqrt(Math.pow(origem.getLayoutX() - destino.getLayoutX(), 2) + Math.pow(origem.getLayoutY() - destino.getLayoutY(), 2));
        double cos = (destino.getLayoutX() - origem.getLayoutX()) / r;
        double sen = (destino.getLayoutY() - origem.getLayoutY()) / r;

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        forma = new QuadCurve(origem.getLayoutX(), origem.getLayoutY(), xAB * -cos - yA * -sen + meioX, xAB * -sen + yA * -cos + meioY, destino.getLayoutX(), destino.getLayoutY());
        forma.setFill(Color.TRANSPARENT);

        if (peso != 0) {
            labelPeso = new Text(String.valueOf(peso));
            labelPeso.setStroke(Paint.valueOf("#000"));
            labelPeso.setFill(Paint.valueOf("#222"));
            posicionarTexto();
        }

        if (directed) {

            int deslocamento = 5;
            int size = 6;
            
            xAB = size + deslocamento;
            yA = size;
            yB = -size; 

            pa = new Light.Point(xAB * -cos - yA * -sen + (forma.getControlX() + meioX) / 2, xAB * -sen + yA * -cos + (forma.getControlY() + meioY) / 2, 0, Color.BLACK);
            pb = new Light.Point(xAB * -cos - yB * -sen + (forma.getControlX() + meioX) / 2, xAB * -sen + yB * -cos + (forma.getControlY() + meioY) / 2, 0, Color.BLACK);
            pc = new Light.Point(deslocamento * -cos + (forma.getControlX() + meioX) / 2, deslocamento * -sen + (forma.getControlY() + meioY) / 2, 0, Color.BLACK);
            a = new Line(pc.getX(), pc.getY(), pa.getX(), pa.getY());
            b = new Line(pc.getX(), pc.getY(), pb.getX(), pb.getY());
            a.setStroke(Color.BLACK);
            b.setStroke(Color.BLACK);
        }
    }
    
    public Arco(Vertice origem, Vertice destino, int peso, boolean directed, boolean topologica) {
        super(origem, destino, peso, directed);

        double meioX = Math.abs((destino.getLayoutX() + origem.getLayoutX()) / 2);
        double meioY = Math.abs((destino.getLayoutY() + origem.getLayoutY()) / 2);

        double r = Math.sqrt(Math.pow(origem.getLayoutX() - destino.getLayoutX(), 2) + Math.pow(origem.getLayoutY() - destino.getLayoutY(), 2));
        double cos = (destino.getLayoutX() - origem.getLayoutX()) / r;
        double sen = (destino.getLayoutY() - origem.getLayoutY()) / r;
        
        if(topologica){
            size = 20 + 25*Math.abs(destino.getID() - origem.getID());
            deslocamento = 10;
        }
        
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        forma = new QuadCurve(origem.getLayoutX(), origem.getLayoutY(), xAB * -cos - yA * -sen + meioX, xAB * -sen + yA * -cos + meioY, destino.getLayoutX(), destino.getLayoutY());
        forma.setFill(Color.TRANSPARENT);

        if (peso != 0) {
            labelPeso = new Text(String.valueOf(peso));
            labelPeso.setStroke(Paint.valueOf("#000"));
            labelPeso.setFill(Paint.valueOf("#222"));
            posicionarTexto();
        }

        if (directed) {

            int deslocamento = 5;
            int size = 6;
            
            xAB = size + deslocamento;
            yA = size;
            yB = -size; 

            pa = new Light.Point(xAB * -cos - yA * -sen + (forma.getControlX() + meioX) / 2, xAB * -sen + yA * -cos + (forma.getControlY() + meioY) / 2, 0, Color.BLACK);
            pb = new Light.Point(xAB * -cos - yB * -sen + (forma.getControlX() + meioX) / 2, xAB * -sen + yB * -cos + (forma.getControlY() + meioY) / 2, 0, Color.BLACK);
            pc = new Light.Point(deslocamento * -cos + (forma.getControlX() + meioX) / 2, deslocamento * -sen + (forma.getControlY() + meioY) / 2, 0, Color.BLACK);
            a = new Line(pc.getX(), pc.getY(), pa.getX(), pa.getY());
            b = new Line(pc.getX(), pc.getY(), pb.getX(), pb.getY());
            a.setStroke(Color.BLACK);
            b.setStroke(Color.BLACK);
        }
    }

    @Override
    public void setInicio() {

        double meioX = Math.abs((destino.getLayoutX() + origem.getLayoutX()) / 2);
        double meioY = Math.abs((destino.getLayoutY() + origem.getLayoutY()) / 2);

        double r = Math.sqrt(Math.pow(origem.getLayoutX() - destino.getLayoutX(), 2) + Math.pow(origem.getLayoutY() - destino.getLayoutY(), 2));
        double cos = (destino.getLayoutX() - origem.getLayoutX()) / r;
        double sen = (destino.getLayoutY() - origem.getLayoutY()) / r;

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        forma.setStartX(origem.getLayoutX());
        forma.setStartY(origem.getLayoutY());
        forma.setControlX(xAB * -cos - yA * -sen + meioX);
        forma.setControlY(xAB * -sen + yA * -cos + meioY);

        if (peso != 0) {
            posicionarTexto();
        }
        if (directed) {
            posicionarFlecha();
        }
    }

    public void setDestino() {
        double meioX = Math.abs((destino.getLayoutX() + origem.getLayoutX()) / 2);
        double meioY = Math.abs((destino.getLayoutY() + origem.getLayoutY()) / 2);

        double r = Math.sqrt(Math.pow(origem.getLayoutX() - destino.getLayoutX(), 2) + Math.pow(origem.getLayoutY() - destino.getLayoutY(), 2));
        double cos = (destino.getLayoutX() - origem.getLayoutX()) / r;
        double sen = (destino.getLayoutY() - origem.getLayoutY()) / r;

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        forma.setEndX(destino.getLayoutX());
        forma.setEndY(destino.getLayoutY());
        forma.setControlX(xAB * -cos - yA * -sen + meioX);
        forma.setControlY(xAB * -sen + yA * -cos + meioY);
        if (peso != 0) {
            posicionarTexto();
        }
        if (directed) {
            posicionarFlecha();
        }
    }

    public void posicionarFlecha() {

        double r = Math.sqrt(Math.pow(origem.getLayoutX() - destino.getLayoutX(), 2) + Math.pow(origem.getLayoutY() - destino.getLayoutY(), 2));
        double cos = (destino.getLayoutX() - origem.getLayoutX()) / r;
        double sen = (destino.getLayoutY() - origem.getLayoutY()) / r;

        double meioX = Math.abs((destino.getLayoutX() + origem.getLayoutX()) / 2);
        double meioY = Math.abs((destino.getLayoutY() + origem.getLayoutY()) / 2);

        int deslocamento = 5;
        int size = 6;
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        pa = new Light.Point(xAB * -cos - yA * -sen + (forma.getControlX() + meioX) / 2, xAB * -sen + yA * -cos + (forma.getControlY() + meioY) / 2, 0, Color.BLACK);
        pb = new Light.Point(xAB * -cos - yB * -sen + (forma.getControlX() + meioX) / 2, xAB * -sen + yB * -cos + (forma.getControlY() + meioY) / 2, 0, Color.BLACK);
        pc = new Light.Point(deslocamento * -cos + (forma.getControlX() + meioX) / 2, deslocamento * -sen + (forma.getControlY() + meioY) / 2, 0, Color.BLACK);

        a.setStartX(pc.getX());
        a.setStartY(pc.getY());
        a.setEndX(pa.getX());
        a.setEndY(pa.getY());
        b.setStartX(pc.getX());
        b.setStartY(pc.getY());
        b.setEndX(pb.getX());
        b.setEndY(pb.getY());
    }

    @Override
    public Shape getForma() {

        return forma;
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

        labelPeso.setLayoutX(xAB * -cos - yA * -sen + (forma.getControlX() + meioX) / 2);
        labelPeso.setLayoutY(xAB * -sen + yA * -cos + (forma.getControlY() + meioY) / 2);

    }

}
