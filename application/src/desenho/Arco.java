/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;

import aplicacao.FXMLPrincipalController;
import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.effect.Light;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
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

    private int deslocamento = -5;
    private int size = 20;
    

    public Arco(Vertice origem, Vertice destino, String texto, boolean directed) {
        super(origem, destino, texto, directed);

        double meioX = Math.abs((destino.getCenterX() + origem.getCenterX()) / 2);
        double meioY = Math.abs((destino.getCenterY() + origem.getCenterY()) / 2);
            
        double dif1 = origem.getCenterX() - destino.getCenterX();
        double dif2 = origem.getCenterY() - destino.getCenterY();
        double r = Math.sqrt(Math.pow(dif1, 2) + Math.pow(dif2, 2));
        double cos = (destino.getCenterX() - origem.getCenterX()) / r;
        double sen = (destino.getCenterY() - origem.getCenterY()) / r;

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        //forma = new QuadCurve(origem.getCenterX(), origem.getCenterY(), meioX + 10*sen, meioY+10*cos, destino.getCenterX(), destino.getCenterY());
        forma = new QuadCurve(origem.getCenterX(), origem.getCenterY(), xAB * -cos - yA * -sen + meioX, xAB * -sen + yA * -cos + meioY, destino.getCenterX(), destino.getCenterY());

        if (texto != null) {
            labelTexto = new Text(texto);
            labelTexto.setStroke(Paint.valueOf("#000"));
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
            a.setStroke(Color.BLACK); a.setStrokeWidth(3);
            b.setStroke(Color.BLACK); b.setStrokeWidth(3);
        }
        
        //inserir movimentação de setas
        
    }
    
    @Override
    public void setInicio() {

        double meioX = Math.abs((destino.getCenterX() + origem.getCenterX()) / 2);
        double meioY = Math.abs((destino.getCenterY() + origem.getCenterY()) / 2);

        double r = Math.sqrt(Math.pow(origem.getCenterX() - destino.getCenterX(), 2) + Math.pow(origem.getCenterY() - destino.getCenterY(), 2));
        double cos = (destino.getCenterX() - origem.getCenterX()) / r;
        double sen = (destino.getCenterY() - origem.getCenterY()) / r;

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        forma.setStartX(origem.getCenterX());
        forma.setStartY(origem.getCenterY());
        forma.setControlX(xAB * -cos - yA * -sen + meioX);
        forma.setControlY(xAB * -sen + yA * -cos + meioY);

        if (texto != null) {
            posicionarTexto();
        }
        if (directed) {
            posicionarFlecha();
        }
    }

    public void setDestino() {
        double meioX = Math.abs((destino.getCenterX() + origem.getCenterX()) / 2);
        double meioY = Math.abs((destino.getCenterY() + origem.getCenterY()) / 2);

        double r = Math.sqrt(Math.pow(origem.getCenterX() - destino.getCenterX(), 2) + Math.pow(origem.getCenterY() - destino.getCenterY(), 2));
        double cos = (destino.getCenterX() - origem.getCenterX()) / r;
        double sen = (destino.getCenterY() - origem.getCenterY()) / r;

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        forma.setEndX(destino.getCenterX());
        forma.setEndY(destino.getCenterY());
        forma.setControlX(xAB * -cos - yA * -sen + meioX);
        forma.setControlY(xAB * -sen + yA * -cos + meioY);
        if (texto != null) {
            posicionarTexto();
        }
        if (directed) {
            posicionarFlecha();
        }
    }

    public void posicionarFlecha() {

        double r = Math.sqrt(Math.pow(origem.getCenterX() - destino.getCenterX(), 2) + Math.pow(origem.getCenterY() - destino.getCenterY(), 2));
        double cos = (destino.getCenterX() - origem.getCenterX()) / r;
        double sen = (destino.getCenterY() - origem.getCenterY()) / r;

        double meioX = Math.abs((destino.getCenterX() + origem.getCenterX()) / 2);
        double meioY = Math.abs((destino.getCenterY() + origem.getCenterY()) / 2);

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

        double r = Math.sqrt(Math.pow(origem.getCenterX() - destino.getCenterX(), 2) + Math.pow(origem.getCenterY() - destino.getCenterY(), 2));
        double cos = (destino.getCenterX() - origem.getCenterX()) / r;
        double sen = (destino.getCenterY() - origem.getCenterY()) / r;

        double meioX = (origem.getCenterX() + destino.getCenterX()) / 2;
        double meioY = (origem.getCenterY() + destino.getCenterY()) / 2;
        
        int size = 10;
        int deslocamento = 10;
        
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        labelTexto.setLayoutX(xAB * -cos - yA * -sen + (forma.getControlX() + meioX) / 2);
        labelTexto.setLayoutY(xAB * -sen - yA * cos + (forma.getControlY() + meioY) / 2);
    }

}
