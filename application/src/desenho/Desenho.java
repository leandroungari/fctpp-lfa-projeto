/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import jfxtras.labs.util.event.MouseControlUtil;

/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 * @date May 13,2016
 */
public class Desenho { 
    
    private static double initialX;
    private static double initialY; 
    public static Grafo novo;

    public static void desenharVertice(Pane pane, Vertice shape) {

        MouseControlUtil.makeDraggable(shape, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                shape.corrigir();
            }
        }, null);

        shape.setOnDragDetected((MouseEvent event) -> {
            initialX = shape.getLayoutX();
            initialY = shape.getLayoutY();
            shape.setLayoutX(initialX);
            shape.setLayoutY(initialY);
        });

        shape.setOnMouseReleased(event -> {
            
            double endX, endY;
            endX = event.getX(); endY = event.getY();
            
            if (!(endX < pane.getWidth() && endY < pane.getHeight())) {
                shape.setLayoutX(initialX);
                shape.setLayoutY(initialY);
            }

            shape.corrigir();
        });

        pane.getChildren().add(shape);
        pane.getChildren().add(shape.numero);
        shape.corrigir();
    }

    public static void desenharAresta(Pane pane, Aresta aresta) {

        aresta.getForma().setStroke(Paint.valueOf("#000"));

        pane.getChildren().add(aresta.getForma());
        aresta.getForma().setStrokeWidth(2);

        if (aresta.getPeso() != 0) {
            pane.getChildren().add(aresta.labelPeso);
        }

        if (aresta.isDirected() && !(aresta instanceof Loop)) {
            pane.getChildren().add(aresta.a);
            pane.getChildren().add(aresta.b);
            aresta.a.setStrokeWidth(2);
            aresta.b.setStrokeWidth(2);
        }
    }
}
