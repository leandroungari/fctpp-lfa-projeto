/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;

import aplicacao.FXMLPrincipalController;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 * @date May 15,2016
 */
public class Vertice extends Circle {

    private int ID;
    private boolean selected = false;
    private boolean isInitial = false;
    public boolean hasSubtitle = false;
    public Text numero;
    public Polygon inicio;
    public Legenda legenda;
    public Legenda adesivo;
    public String adesivoTexto;

    private final ContextMenu contextMenu = new ContextMenu();

    public Vertice(int id, double x, double y, double raio) {

        super(x, y, raio);
        adesivo = new Legenda(x + 15, y - 30);
        adesivoTexto = "λ";

        adesivo.get().setOnMousePressed(event -> {

            FXMLPrincipalController.adesivoAtual = adesivo;
        });
        this.ID = id;
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
        this.setFill(Color.DODGERBLUE);

        this.numero = new Text("q" + id);
        this.numero.setFont(new Font(17));
        this.numero.setFill(Paint.valueOf("#fff"));
        this.numero.setLayoutX(this.getCenterX() - 10);
        this.numero.setLayoutY(this.getCenterY() + 5);

        this.inicio = new Polygon(0, 0, 0, 30, 15, 15);
        this.inicio.setFill(Color.BLACK);

        MenuItem remover = new MenuItem("Remover");
        remover.setOnAction(event -> {
            
            if (FXMLPrincipalController.maquinaAtual == FXMLPrincipalController.MACHINE_MOORE) {
                FXMLPrincipalController.painelD.getChildren().remove(this.adesivo.get());
            }
            
            FXMLPrincipalController.painelD.getChildren().remove(this.numero);
            FXMLPrincipalController.painelD.getChildren().remove(this.inicio);
            FXMLPrincipalController.painelD.getChildren().remove(this);

            for (Aresta a : FXMLPrincipalController.arestas) {

                if (this == a.getOrigemVertice() || a.getDestinoVertice() == this) {

                    FXMLPrincipalController.painelD.getChildren().remove(a.getForma());
                    FXMLPrincipalController.painelD.getChildren().remove(a.a);
                    FXMLPrincipalController.painelD.getChildren().remove(a.b);
                    FXMLPrincipalController.painelD.getChildren().remove(a.labelTexto);
                    FXMLPrincipalController.arestas.remove(a);
                }
            }

            FXMLPrincipalController.lista.remove(this);

            event.consume();
        });

        MenuItem legenda = new MenuItem("Inserir legenda");
        legenda.setOnAction(event -> {

            legenda.setDisable(true);
            hasSubtitle = true;
            TextField entrada = new TextField();
            entrada.setPrefSize(50, 20);
            entrada.setLayoutX(this.getCenterX() - 25);
            entrada.setLayoutY(this.getCenterY() + 17);

            entrada.setOnKeyReleased(e -> {

                if (e.getCode() == KeyCode.ENTER) {

                    if (!entrada.getText().isEmpty()) {

                        String desc = entrada.getText();
                        this.legenda = new Legenda(0, 0);
                        this.legenda.setText(desc);
                        this.legenda.get().setTextAlignment(TextAlignment.CENTER);
                        this.legenda.get().setPrefWidth(70);
                        this.legenda.get().setMaxWidth(70);
                        this.legenda.get().setMaxHeight(18);
                        this.legenda.setLayout(this.getCenterX() - 35, this.getCenterY() + 17);
                        this.legenda.get().toFront();
                        this.legenda.get().setOnMousePressed(t -> {
                            t.consume();
                        });

                        FXMLPrincipalController.painelD.getChildren().add(this.legenda.get());
                    }

                    FXMLPrincipalController.painelD.getChildren().remove(entrada);
                }
            });

            FXMLPrincipalController.painelD.getChildren().add(entrada);
        });

        MenuItem inicio = new MenuItem("Inicial");
        inicio.setOnAction(event -> {

            if (!isInitial) {

                for (Vertice v : FXMLPrincipalController.lista) {
                    if (v.isIsInitial()) {

                        v.setIsInitial(false);
                        FXMLPrincipalController.painelD.getChildren().remove(v.inicio);
                    }
                }

                this.inicio.setLayoutX(this.getCenterX() - 35);
                this.inicio.setLayoutY(this.getCenterY() - 15);
                FXMLPrincipalController.painelD.getChildren().add(this.inicio);

                isInitial = true;

            } else {
                FXMLPrincipalController.painelD.getChildren().remove(this.inicio);
            }
            event.consume();
        });

        MenuItem fim = new MenuItem("Final");
        fim.setOnAction(event -> {

            if (this.isSelected()) {
                this.desselecionar();
            } else {
                this.selecionar();
            }

            event.consume();
        });

        if (FXMLPrincipalController.maquinaAtual == FXMLPrincipalController.MACHINE_AUTOMATO_FINITE) {
            contextMenu.getItems().addAll(remover, new SeparatorMenuItem(), legenda, new SeparatorMenuItem(), inicio, fim);
        } else {
            contextMenu.getItems().addAll(remover, new SeparatorMenuItem(), legenda, new SeparatorMenuItem(), inicio);
        }

        this.setOnMouseEntered(event -> {

            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:

                    break;

                case MOVER_CURSOR:
                    FXMLPrincipalController.verticeSobre = this;
                    this.getScene().setCursor(Cursor.MOVE);
                    break;

                case INSERIR_CURSOR:

                    break;

                case TEXTO_CURSOR:

                    break;
            }
        });

        this.numero.setOnMouseEntered(event -> {

            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:

                    break;

                case MOVER_CURSOR:
                    FXMLPrincipalController.verticeSobre = this;
                    this.getScene().setCursor(Cursor.MOVE);
                    break;

                case INSERIR_CURSOR:

                    break;

                case TEXTO_CURSOR:

                    break;
            }

        });

        this.setOnMouseExited(event -> {
            this.getScene().setCursor(Cursor.DEFAULT);
            FXMLPrincipalController.verticeSobre = null;
        });

        this.numero.setOnMouseExited(event -> {
            this.getScene().setCursor(Cursor.DEFAULT);
            FXMLPrincipalController.verticeSobre = null;
        });

        this.setOnDragDetected((MouseEvent event) -> {
            event.consume();
        });

        this.setOnMousePressed(event -> {

            if (event.isPrimaryButtonDown()) {
                FXMLPrincipalController.verticeAtual = this;

            } else if (event.isSecondaryButtonDown()) {
                contextMenu.show(this, event.getScreenX(), event.getScreenY());
                event.consume();
            }

            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:

                    break;

                case MOVER_CURSOR:

                    break;

                case INSERIR_CURSOR:

                    break;

                case TEXTO_CURSOR:

                    break;
            }

        });

        this.numero.setOnMousePressed(event -> {

            if (event.isPrimaryButtonDown()) {
                FXMLPrincipalController.verticeAtual = this;
            } else if (event.isSecondaryButtonDown()) {
                contextMenu.show(this.numero, event.getScreenX(), event.getScreenY());
            }

            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:

                    break;

                case MOVER_CURSOR:

                    break;

                case INSERIR_CURSOR:

                    break;

                case TEXTO_CURSOR:

                    break;
            }
        });

        this.setOnMouseDragExited(event -> {

            FXMLPrincipalController.verticeAtual = null;

            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:

                    break;

                case MOVER_CURSOR:

                    break;

                case INSERIR_CURSOR:

                    break;

                case TEXTO_CURSOR:

                    break;
            }
        });

        this.numero.setOnMouseDragExited(event -> {

            FXMLPrincipalController.verticeAtual = null;

            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:

                    break;

                case MOVER_CURSOR:

                    break;

                case INSERIR_CURSOR:

                    break;

                case TEXTO_CURSOR:

                    break;
            }
        });

    }

    public boolean isIsInitial() {
        return isInitial;
    }

    public void setIsInitial(boolean isInitial) {
        this.isInitial = isInitial;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public void selecionar() {
        this.setStrokeWidth(5);
        this.selected = true;
    }

    public void desselecionar() {
        this.setStrokeWidth(1);
        this.selected = false;
    }

    public void corrigir(double x, double y) {
        this.numero.setLayoutX(x - 10);
        this.numero.setLayoutY(y + 5);
        this.numero.toFront();

        this.inicio.setLayoutX(this.getCenterX() - 35);
        this.inicio.setLayoutY(this.getCenterY() - 15);

        if (hasSubtitle) {
            this.legenda.setLayout(this.getCenterX() - 35, this.getCenterY() + 17);
        }

        if (FXMLPrincipalController.maquinaAtual == FXMLPrincipalController.MACHINE_MOORE) {

            this.adesivo.get().setLayoutX(x + 15);
            this.adesivo.get().setLayoutY(y - 30);
        }

        for (Aresta a : FXMLPrincipalController.arestas) {

            if (this == a.getOrigemVertice()) {

                a.setInicio();
                a.getForma().toBack();
            } else if (this == a.getDestinoVertice()) {

                a.setDestino();
                a.getForma().toBack();
            }
        }

    }

    public boolean isSelected() {
        return selected;
    }

    public void opacidade(double value) {
        this.setOpacity(value);
    }
    
    
    
    public static void criarAresta(Vertice inicio, Vertice fim) {

        TextField entrada = new TextField();
        double xP = (inicio.getCenterX() + fim.getCenterX()) / 2;
        double yP = (inicio.getCenterY() + fim.getCenterY()) / 2;
        entrada.setLayoutX(xP-40);
        entrada.setLayoutY(yP);
        entrada.setPrefSize(50, 13);
        entrada.setText("λ");

        if (FXMLPrincipalController.maquinaAtual == FXMLPrincipalController.MACHINE_AUTOMATO_FINITE) {
            entrada.setOnKeyReleased(e -> {

                if (e.getCode() == KeyCode.ENTER) {
                    if (!entrada.getText().isEmpty()) {
                        Desenho.desenharAresta(FXMLPrincipalController.painelD, inicio, fim, entrada.getText());
                    }
                    FXMLPrincipalController.painelD.getChildren().remove(entrada);
                }
            });
        }

        Desenho.desenharCampo(FXMLPrincipalController.painelD, entrada);

        if (FXMLPrincipalController.maquinaAtual == FXMLPrincipalController.MACHINE_MEALY) {

            TextField saida = new TextField();
            double x = (inicio.getCenterX() + fim.getCenterX()) / 2;
            double y = (inicio.getCenterY() + fim.getCenterY()) / 2;
            saida.setLayoutX(x + 11);
            saida.setLayoutY(y);
            saida.setPrefSize(50, 13);
            saida.setText("λ");
            
            saida.setOnKeyReleased(e -> {

                if (e.getCode() == KeyCode.ENTER) {
                    if (! (entrada.getText().isEmpty() || saida.getText().isEmpty()) ) {
                        Desenho.desenharAresta(FXMLPrincipalController.painelD, inicio, fim, entrada.getText() + " : " + saida.getText());
                    }
                    FXMLPrincipalController.painelD.getChildren().remove(entrada);
                    FXMLPrincipalController.painelD.getChildren().remove(saida);
                }
                
            });
            
            Desenho.desenharCampo(FXMLPrincipalController.painelD, saida);
        }

    }
}
