/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;

import aplicacao.FXMLPrincipalController;
import java.util.ArrayList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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

    private final ContextMenu contextMenu = new ContextMenu();

    public Loop(Vertice origem, String texto, boolean directed) {
        super(origem, origem, texto, directed);

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        double meioX = origem.getCenterX();
        double meioY = origem.getCenterY();

        this.forma = new Circle(meioX, meioY - 23, 18);
        this.forma.setFill(Color.TRANSPARENT);
        this.forma.setStroke(Color.BLACK);

        labelTexto = new Text(String.valueOf(texto));
        labelTexto.setStroke(Paint.valueOf("#000"));
        labelTexto.setFill(Paint.valueOf("#222"));
        posicionarTexto();

        this.labelTexto.setOnMousePressed(event -> {

            if (event.isSecondaryButtonDown()) {
                contextMenu.getItems().clear();

                if (FXMLPrincipalController.maquinaAtual != FXMLPrincipalController.MACHINE_MEALY) {

                    String[] opcoes = this.texto.split("|");
                    ArrayList<String> posterior = new ArrayList<>();
                    for (int i = 0; i < opcoes.length; i++) {

                        opcoes[i] = opcoes[i].trim();
                        if (!opcoes[i].equals("|")) {
                            posterior.add(opcoes[i]);
                            MenuItem a = new MenuItem(opcoes[i]);
                            a.setOnAction(e -> {

                                String novaTransicao = "";
                                int j = 0;
                                for (String s : posterior) {

                                    if (!s.equals(a.getText())) {
                                        novaTransicao += s;
                                        if (j != posterior.size() - 1) {
                                            novaTransicao += "|";
                                        }
                                    }
                                    j++;
                                }

                                if ((!novaTransicao.isEmpty()) && novaTransicao.charAt(novaTransicao.length() - 1) == '|') {
                                    novaTransicao = novaTransicao.substring(0, novaTransicao.length() - 1);
                                }

                                this.labelTexto.setText(novaTransicao);
                                this.texto = novaTransicao;

                                if (this.texto.isEmpty()) {

                                    FXMLPrincipalController.painelD.getChildren().remove(this.labelTexto);
                                    FXMLPrincipalController.painelD.getChildren().remove(this.getForma());
                                    FXMLPrincipalController.arestas.remove(this);
                                }

                            });

                            contextMenu.getItems().add(a);
                        }
                    }
                }
                else{
                    final int num = this.getEntrada().size();
                    for (int i = 0; i < num; i++) {
                        final int pos = i;
                        MenuItem a = new MenuItem(this.entrada.get(i) + " : " + this.getSaida().get(i));
                        a.setOnAction(e -> {

                            this.getEntrada().remove(pos);
                            this.getSaida().remove(pos);

                            String novaTransicao = "";
                            for (int b = 0; b < num - 1; b++) {
                                novaTransicao += this.entrada.get(b) + " : " + this.getSaida().get(b);
                                if (b != num - 2) {
                                    novaTransicao += "|";
                                }
                            }

                            this.labelTexto.setText(novaTransicao);
                            this.texto = novaTransicao;

                            if (this.texto.isEmpty()) {

                                FXMLPrincipalController.painelD.getChildren().remove(this.labelTexto);
                                FXMLPrincipalController.painelD.getChildren().remove(this.a);
                                FXMLPrincipalController.painelD.getChildren().remove(this.b);
                                FXMLPrincipalController.painelD.getChildren().remove(this.getForma());
                                FXMLPrincipalController.arestas.remove(this);
                            }
                        });
                        contextMenu.getItems().add(a);
                    }
                }

                contextMenu.show(this.getLabelTexto(), event.getScreenX(), event.getScreenY());
                event.consume();
            }
        });
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
