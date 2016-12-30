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

    public Loop(Vertice origem, String texto, boolean directed) {
        super(origem, origem, texto, directed);

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        double meioX = origem.getCenterX();
        double meioY = origem.getCenterY();

        this.forma = new Circle(meioX , meioY - 23, 18);
        this.forma.setFill(Color.TRANSPARENT);
        this.forma.setStroke(Color.BLACK);

        labelTexto = new Text(String.valueOf(texto));
        labelTexto.setStroke(Paint.valueOf("#000"));
        labelTexto.setFill(Paint.valueOf("#222"));
        posicionarTexto();
    }

    @Override
    public void posicionarTexto() {

        int cos = 0, sen = 1;
        size = 15;
        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        labelTexto.setLayoutX(forma.getCenterX());
        labelTexto.setLayoutY(forma.getCenterY() - 30);
    }

    @Override
    public void setInicio() {

        this.forma.setCenterX(origem.getCenterX());
        this.forma.setCenterY(origem.getCenterY() - 23);

        posicionarTexto();

    }

    public void setDestino() {

        posicionarTexto();

    }

    @Override
    public Shape getForma() {
        return this.forma;
    }

    @Override
    public void posicionarFlecha() {
        
    }

}
