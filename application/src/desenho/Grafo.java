/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenho;

import codificacoes.representacaoComputacional.Adjacencia;
import java.util.ArrayList;
import javafx.scene.layout.Pane;

/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 * @date May 15,2016
 */
public class Grafo {

    private Pane parent;
    private boolean directed;
    private boolean topologica = false;

    public Grafo(Pane parent, int nVert, Adjacencia lista) {

        this.parent = parent;
        this.directed = lista.isTipoGrafo();

        RainbowScale cS = new RainbowScale();
        //GrayScale cS = new GrayScale();
        int colorStep = 255 / nVert;
        int r, g, b;
        for (int i = 0; i < nVert; i++) {

            Vertice v = new Vertice(i, 0, 0, 15);

            java.awt.Color awt = cS.getColor(i * colorStep);
            r = awt.getRed();
            g = awt.getGreen();
            b = awt.getBlue();

            javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(r, g, b);
            v.setFill(fxColor);

            this.vertex.add(v);
        }

        if (!topologica) {
            computeCircledPosition(150);
        }

        Aresta a;
        int indice;
        for (int i = 0; i < nVert; i++) {

            for (int j = 0; j < nVert; j++) {

                if (lista.verificaAdjacencia(i, j)) {

                    if (i == j) {

                        a = new Loop(this.getVertice(i), lista.getPeso(i, j), directed);
                    } else if (lista.verificaAdjacencia(j, i)) {

                        if (directed) {
                            a = new Arco(this.getVertice(i), this.getVertice(j), lista.getPeso(i, j), directed);
                        } else {
                            a = new Linha(this.getVertice(i), this.getVertice(j), lista.getPeso(i, j), directed);
                        }

                    } else {

                        a = new Linha(this.getVertice(i), this.getVertice(j), lista.getPeso(i, j), directed);
                    }

                    edges.add(a);
                    indice = edges.indexOf(a);
                    vertex.get(i).origem.add(indice);
                    vertex.get(j).destino.add(indice);
                }
            }
        }
    }

    public void desenhar() {
        //Draw each edges of the graph
        if (!topologica) {
            for (Aresta edge : edges) {

                if (directed == false) {

                    if (edge.getOrigem() <= edge.getDestino()) {
                        Desenho.desenharAresta(parent, edge);
                    }

                } else {
                    Desenho.desenharAresta(parent, edge);
                }

            }
        } else {
            for (Aresta edge : edges) {
                Desenho.desenharAresta(parent, edge);
            }
        }

        //Draw each vertice of the graph
        for (Vertice v : this.vertex) {
            //Desenho.desenharVertice(parent, v, this);
        }
    }

    public Vertice getVertice(int i) {
        return this.vertex.get(i);
    }

    public void computeCircledPosition(int ray) {

        int nVert = this.vertex.size();
        int step = 360 / nVert;
        int deslocX = 100 + ray;
        int deslocY = 100 + ray;
        for (int i = 0; i < nVert; i++) {
            double ang = i * step;
            ang = ang * Math.PI / 180;//necessario em radianos
            float X = (float) Math.cos(ang);
            float Y = (float) Math.sin(ang);
            X = X * ray + deslocX;
            Y = Y * ray + deslocY;
            this.vertex.get(i).setLayoutX(X);
            this.vertex.get(i).setLayoutY(Y);
        }
    }

    public Aresta getAresta(int origem, int destino) {

        for (Aresta a : edges) {

            if (a.getOrigem() == origem && a.getDestino() == destino) {

                return a;
            }
        }

        return null;
    }

    public ArrayList<Vertice> getVertex() {
        return vertex;
    }

    public void setVertex(ArrayList<Vertice> vertex) {
        this.vertex = vertex;
    }

    public ArrayList<Aresta> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Aresta> edges) {
        this.edges = edges;
    }

    public void setTopologica(boolean valor) {
        this.topologica = valor;

    }

    public boolean getTopologica() {
        return topologica;
    }

    protected ArrayList<Vertice> vertex = new ArrayList<>();
    protected ArrayList<Aresta> edges = new ArrayList<>();
}
