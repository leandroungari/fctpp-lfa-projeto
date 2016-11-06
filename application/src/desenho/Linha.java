/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;

import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/*
 *   
 * @author Leandro Ungari <leandroungari@gmail.com>
 * @date   June 10,2016
 */
public class Linha extends Aresta {

    private Line forma;

    public Linha(Vertice origem, Vertice destino, int peso, boolean directed) {
        super(origem, destino, peso, directed);

        this.forma = new Line(origem.getLayoutX(), origem.getLayoutY(), destino.getLayoutX(), destino.getLayoutY());
        
        if (peso != 0) {
            labelPeso = new Text(String.valueOf(peso));
            labelPeso.setStroke(Paint.valueOf("#000"));
            labelPeso.setFill(Paint.valueOf("#222"));
            posicionarTexto();
        }

        if (directed) {

            double r = Math.sqrt(Math.pow(origem.getLayoutX() - destino.getLayoutX(), 2) + Math.pow(origem.getLayoutY() - destino.getLayoutY(), 2));
            double cos = (destino.getLayoutX() - origem.getLayoutX()) / r;
            double sen = (destino.getLayoutY() - origem.getLayoutY()) / r;

            int deslocamento = 14;
            int size = 6;
            int xAB = size + deslocamento;
            int yA = size;
            int yB = -size;

            double meioX = (origem.getLayoutX() + destino.getLayoutX()) / 2;
            double meioY = (origem.getLayoutY() + destino.getLayoutY()) / 2;

            pa = new Light.Point(xAB * -cos - yA * -sen + meioX, xAB * -sen + yA * -cos + meioY, 0, Color.BLACK);
            pb = new Light.Point(xAB * -cos - yB * -sen + meioX, xAB * -sen + yB * -cos + meioY, 0, Color.BLACK);
            pc = new Light.Point(deslocamento * -cos + meioX, deslocamento * -sen + meioY, 0, Color.BLACK);
            a = new Line(pc.getX(), pc.getY(), pa.getX(), pa.getY());
            b = new Line(pc.getX(), pc.getY(), pb.getX(), pb.getY());
            a.setStroke(Color.BLACK);
            b.setStroke(Color.BLACK);
        }
    }

    public void setPosition() {

        forma.setStartX(origem.getLayoutX());
        forma.setStartY(origem.getLayoutY());
        forma.setEndX(destino.getLayoutX());
        forma.setEndY(destino.getLayoutY());
    }

    public void setInicio() {
        forma.setStartX(origem.getLayoutX());
        forma.setStartY(origem.getLayoutY());
        if (peso != 0) {
            posicionarTexto();
        }
        if (directed) {
            posicionarFlecha();
        }
    }

    public void setDestino() {
        forma.setEndX(destino.getLayoutX());
        forma.setEndY(destino.getLayoutY());
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
