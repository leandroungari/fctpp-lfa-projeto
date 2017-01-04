/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;

import aplicacao.FXMLPrincipalController;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 * @date May 13,2016
 */
public class Desenho {

    private static double initialX;
    private static double initialY;

    public static void desenharVertice(Pane pane, Vertice shape) {

        pane.getChildren().add(shape);
        pane.getChildren().add(shape.numero);
    }

    public static void desenharCampo(Pane pane, TextField text) {

        pane.getChildren().add(text);
    }

    public static void desenharAresta(Pane pane, Vertice inicio, Vertice fim, String texto) {

        for (Aresta a : FXMLPrincipalController.arestas) {
            if ((a.getOrigemVertice() == inicio && a.getDestinoVertice() == fim)
                    || (a.getOrigemVertice() == fim && a.getDestinoVertice() == inicio)) {

                a.getLabelTexto().setText(a.getLabelTexto().getText() + " | " + texto);
                return;
            }
        }

        if (inicio == fim) {

            Loop aresta = new Loop(inicio, texto, false);
            aresta.getForma().setStroke(Paint.valueOf("#000"));
            aresta.getForma().setStrokeWidth(1);
            aresta.getForma().setFill(Color.TRANSPARENT);

            FXMLPrincipalController.arestas.add(aresta);

            pane.getChildren().add(aresta.getForma());
            pane.getChildren().add(aresta.labelTexto);
            aresta.getForma().toBack();

        } else {

            Arco aresta = new Arco(inicio, fim, texto, true);
            aresta.getForma().setStroke(Paint.valueOf("#000"));
            aresta.getForma().setStrokeWidth(1);
            aresta.getForma().setFill(Color.TRANSPARENT);

            FXMLPrincipalController.arestas.add(aresta);

            pane.getChildren().add(aresta.getForma());
            pane.getChildren().add(aresta.labelTexto);
            pane.getChildren().add(aresta.a);
            pane.getChildren().add(aresta.b);
            aresta.getForma().toBack();
        }

    }
}
