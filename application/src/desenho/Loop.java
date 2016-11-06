/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;

import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/*
 *   
 * @author Leandro Ungari <leandroungari@gmail.com>
 * @date   June 10,2016
 */
public class Loop extends Aresta {

    private Circle forma;

    private int deslocamento = 10;
    private int size = 10;

    private double centerX = 250;
    private double centerY = 250;

    public Loop(Vertice origem, int peso, boolean directed) {
        super(origem, origem, peso, directed);
        double r = Math.sqrt(Math.pow(origem.getLayoutX() - centerX, 2) + Math.pow(origem.getLayoutY() - centerY, 2));
        double cos = (origem.getLayoutX() - centerX) / r;
        double sen = (origem.getLayoutY() - centerY) / r;

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        double meioX = origem.getLayoutX();
        double meioY = origem.getLayoutY();

        this.forma = new Circle(meioX - (xAB * -cos - yA * -sen), meioY - (xAB * -sen + yA * -cos), 18);
        this.forma.setFill(Color.TRANSPARENT);
        this.forma.setStroke(Color.BLACK);
        if (peso != 0) {
            labelPeso = new Text(String.valueOf(peso));
            labelPeso.setStroke(Paint.valueOf("#000"));
            labelPeso.setFill(Paint.valueOf("#222"));
            posicionarTexto();
        }

    }

    public Loop(Vertice origem, int peso, boolean directed, boolean topologica) {
        super(origem, origem, peso, directed);
        double r = Math.sqrt(Math.pow(origem.getLayoutX() - centerX, 2) + Math.pow(origem.getLayoutY() - centerY, 2));
        double cos = (origem.getLayoutX() - centerX) / r;
        double sen = (origem.getLayoutY() - centerY) / r;

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        double meioX = origem.getLayoutX();
        double meioY = origem.getLayoutY();

        this.forma = new Circle(meioX, meioY - 20, 18);
        this.forma.setFill(Color.TRANSPARENT);
        this.forma.setStroke(Color.BLACK);
        if (peso != 0) {
            labelPeso = new Text(String.valueOf(peso));
            labelPeso.setStroke(Paint.valueOf("#000"));
            labelPeso.setFill(Paint.valueOf("#222"));
            posicionarTexto();
        }

    }

    @Override
    public void posicionarTexto() {

        double r = Math.sqrt(Math.pow(origem.getLayoutX() - centerX, 2) + Math.pow(origem.getLayoutY() - centerY, 2));
        double cos = (origem.getLayoutX() - centerX) / r;
        double sen = (origem.getLayoutY() - centerY) / r;

        size = 15;
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        labelPeso.setLayoutX(forma.getCenterX() - (xAB * -cos - yA * -sen));
        labelPeso.setLayoutY(forma.getCenterY() - (xAB * -sen + yA * -cos));
    }

    @Override
    public void setInicio() {



        this.forma.setCenterX(origem.getLayoutX());
        this.forma.setCenterY(origem.getLayoutY() - 20 );

        if (peso != 0) {
            posicionarTexto();
        }
    }

    public void setDestino() {

        if (peso != 0) {
            posicionarTexto();
        }
    }

    public void posicionarFlecha() {

        double r = Math.sqrt(Math.pow(origem.getLayoutX() - destino.getLayoutX(), 2) + Math.pow(origem.getLayoutY() - destino.getLayoutY(), 2));
        double cos = (destino.getLayoutX() - origem.getLayoutX()) / r;
        double sen = (destino.getLayoutY() - origem.getLayoutY()) / r;

        double meioX = (origem.getLayoutX() + destino.getLayoutX()) / 2;
        double meioY = (origem.getLayoutY() + destino.getLayoutY()) / 2;
//        System.out.printf("\n%f %f", sen, cos);
//        pc = new Point(meioX + 8*cos, meioY + 8*sen, 0, Color.BLACK);
//        pa = new Point(meioX - 8*cos/Math.abs(cos), meioY + 8*sen/Math.abs(sen), 0, Color.BLACK);
//        pb = new Point(meioX + 8*cos/Math.abs(cos), meioY - 8*sen/Math.abs(sen), 0, Color.BLACK);
        int deslocamento = 14;
        int size = 6;
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        pa = new Light.Point(xAB * -cos - yA * -sen + meioX, xAB * -sen + yA * -cos + meioY, 0, Color.BLACK);
        pb = new Light.Point(xAB * -cos - yB * -sen + meioX, xAB * -sen + yB * -cos + meioY, 0, Color.BLACK);
        pc = new Light.Point(deslocamento * -cos + meioX, deslocamento * -sen + meioY, 0, Color.BLACK);

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
        return this.forma;
    }

}
