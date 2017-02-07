/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import static aplicacao.FXMLPrincipalController.adesivoAtual;
import static aplicacao.FXMLPrincipalController.legendaAtual;
import static aplicacao.FXMLPrincipalController.verticeAtual;
import static aplicacao.FXMLPrincipalController.textoAtual;
import desenho.Aresta;
import desenho.Desenho;
import desenho.Legenda;
import desenho.Vertice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GerenciadorAutomatos {

    public static int quantidade = 0;
    private Vertice inicio;
    private Vertice fim;
    public static boolean subtitleNode = false;
    public static Automato automato;
    public static boolean verificacao = false;

    public static short tipoAutomato = 0;

    public static String caminhoResultado;
    public static ArrayList<Integer> passoApasso;

    public static ArrayList<VerificacaoEE> vetor;

    public static void armazenarAutomato() {

        automato = new Automato();
        Estados est;
        for (Vertice v : FXMLPrincipalController.lista) {
            est = new Estados(v.getID(), v.isSelected());
            automato.getLista().add(est);
            if (v.isIsInitial()) {
                automato.setInicial(est);
            }
        }

        for (Aresta a : FXMLPrincipalController.arestas) {

            Estados inicio = null, fim = null;

            for (Estados e : automato.getLista()) {

                if (e.getValor() == a.getOrigem()) {
                    inicio = e;
                }
                if (e.getValor() == a.getDestino()) {
                    fim = e;
                }
            }

            String[] transicoes = a.getTexto().split("|");
            for (String as : transicoes) {
                if (!as.equals("|")) {

                    inicio.getLista().add(new Transicao(as, fim));
                }
            }
        }
    }

    public void montarPainel(Pane painelDesenho, ArrayList<Vertice> lista, ArrayList<Legenda> legendas) {

        FXMLPrincipalController.estado = Estado.NORMAL_CURSOR;

        painelDesenho.setOnMousePressed((MouseEvent event) -> {

            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:

                    if (event.isPrimaryButtonDown()) {
                        double distancia, dx, dy;

                        for (Vertice v : FXMLPrincipalController.lista) {
                            dx = Math.pow(v.getCenterX() - (painelDesenho.getLayoutX() + event.getX() - 40), 2);
                            dy = Math.pow(v.getCenterY() - (painelDesenho.getLayoutY() + event.getY()), 2);

                            distancia = Math.sqrt(dx + dy);

                            if (distancia <= 20) {
                                this.inicio = v;
                                break;
                            }
                        }
                    }

                    break;

                case MOVER_CURSOR:

                    break;

                case INSERIR_CURSOR:

                    if (event.isPrimaryButtonDown()) {
                        double x = event.getX();
                        double y = event.getY();

                        Vertice v = new Vertice(quantidade++, x, y, 20);

                        if (FXMLPrincipalController.maquinaAtual == FXMLPrincipalController.MACHINE_MOORE) {

                            TextInputDialog dialog = new TextInputDialog("");
                            dialog.setTitle("Saída do Estado");
                            dialog.setHeaderText("Insira a saída do texto");
                            dialog.setContentText("Entre com a saída do estado:");

                            String name;
                            Optional<String> result = dialog.showAndWait();
                            if (result.isPresent()) {
                                
                                name = result.get();
                                if (name.isEmpty()) {
                                    v.adesivo.setText("λ");
                                    v.adesivoTexto = "λ";
                                } else {
                                    v.adesivo.setText(name);
                                    v.adesivoTexto = name;
                                }

                            } else {
                                return;
                            }
                        }

                        lista.add(v);
                        Desenho.desenharVertice(painelDesenho, v);
                    }

                    break;

                case TEXTO_CURSOR:

                    if (event.isPrimaryButtonDown()) {
                        TextField entrada = new TextField();
                        entrada.setLayoutX(event.getX());
                        entrada.setLayoutY(event.getY());
                        entrada.setPrefSize(50, 13);

                        entrada.setOnKeyReleased(e -> {

                            if (e.getCode() == KeyCode.ENTER) {

                                if (!entrada.getText().isEmpty()) {
                                    Legenda legenda = new Legenda(event.getX(), event.getY());
                                    legenda.setText(entrada.getText());
                                    legendas.add(legenda);
                                    painelDesenho.getChildren().add(legenda.get());
                                }

                                painelDesenho.getChildren().remove(entrada);
                            }
                        });

                        Desenho.desenharCampo(painelDesenho, entrada);
                    }

                    break;
            }
        });

        painelDesenho.setOnMouseDragged(event -> {

            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:

                    break;

                case MOVER_CURSOR:

                    if (verticeAtual != null) {
                        verticeAtual.setCenterX(event.getX());
                        verticeAtual.setCenterY(event.getY());
                        verticeAtual.corrigir(event.getX(), event.getY());
                    }

                    if (legendaAtual != null) {

                        legendaAtual.get().setLayoutX(event.getX());
                        legendaAtual.get().setLayoutY(event.getY());
                    }

                    if (textoAtual != null) {

                        textoAtual.setLayoutX(event.getX());
                        textoAtual.setLayoutY(event.getY());
                    }
                    
                    if (adesivoAtual != null) {
                        
                        adesivoAtual.setLayout(event.getX(), event.getY());
                        verticeAtual.setCenterX(event.getX() - 15);
                        verticeAtual.setCenterY(event.getY() + 30);
                        verticeAtual.corrigir(event.getX() -15 , event.getY()+30);
                    }
                    break;

                case INSERIR_CURSOR:

                    break;

                case TEXTO_CURSOR:

                    break;
            }
        });

        painelDesenho.setOnMouseDragExited(event -> {

            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:

                    break;

                case MOVER_CURSOR:

                    if (verticeAtual != null) {
                        verticeAtual = null;
                    }

                    if (legendaAtual != null) {
                        legendaAtual = null;
                    }

                    if (textoAtual != null) {
                        textoAtual = null;
                    }

                    break;

                case INSERIR_CURSOR:

                    break;

                case TEXTO_CURSOR:

                    break;
            }
        });

        painelDesenho.setOnMouseReleased(event -> {

            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:
                    double distancia,
                     dx,
                     dy;

                    for (Vertice v : FXMLPrincipalController.lista) {
                        dx = Math.pow(v.getCenterX() - (painelDesenho.getLayoutX() + event.getX() - 40), 2);
                        dy = Math.pow(v.getCenterY() - (painelDesenho.getLayoutY() + event.getY()), 2);

                        distancia = Math.sqrt(dx + dy);

                        if (distancia <= 20) {
                            this.fim = v;
                            break;
                        }
                    }

                    if (this.inicio != null && this.fim != null) {
                        Vertice.criarAresta(inicio, fim);
                    }

                    inicio = fim = null;

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

    public void montarBotoes(Button normal, Button mover, Button inserir, Button texto) {

        normal.setOnAction(event -> {

            FXMLPrincipalController.estado = Estado.NORMAL_CURSOR;
        });
        normal.setTooltip(new Tooltip("Realizar seleções, desenhar de transições."));

        mover.setOnAction(event -> {

            FXMLPrincipalController.estado = Estado.MOVER_CURSOR;
            verticeAtual = null;
            legendaAtual = null;
        });
        mover.setTooltip(new Tooltip("Realizar o deslocamento de elementos do painel."));

        inserir.setOnAction(event -> {

            FXMLPrincipalController.estado = Estado.INSERIR_CURSOR;
        });
        inserir.setTooltip(new Tooltip("Inserir novos estados."));

        texto.setOnAction(event -> {

            FXMLPrincipalController.estado = Estado.TEXTO_CURSOR;
        });
        texto.setTooltip(new Tooltip("Inserir anotações de texto."));
    }

    public static void processamentoAutomato(String palavraDeEntrada) {

        //Chamo a função pela primeira vez com a palavra de entrada, posição da string e o estado inicial.
        verificacao = false;

        vetor = new ArrayList<>();
        passoApasso = new ArrayList<>();

        caminhoResultado += "q" + automato.getLista().get(automato.getLista().indexOf(automato.getInicial())).getValor() + " -> ";
        passoApasso.add(automato.getLista().get(automato.getLista().indexOf(automato.getInicial())).getValor());
        verificaRegra(palavraDeEntrada, 0, automato.getLista().indexOf(automato.getInicial()));

    }

    public static void verificaRegra(String palavraDeEntrada, int posPalavra, int estadoAtual) {

        for (VerificacaoEE v : vetor) { //Verificação ciclo vazio!

            if (v.getNumEstado() == estadoAtual && v.posPalavra == posPalavra) {
                return;
            }
        }

        if (((posPalavra) == palavraDeEntrada.length()) && (automato.getLista().get(estadoAtual).isIsFinal())) {
            verificacao = true;
            return;
        }

        boolean aux = false;
        for (int i = 0; i < automato.getLista().get(estadoAtual).getLista().size(); i++) {

            if (automato.getLista().get(estadoAtual).getLista().get(i).getChave().charAt(0) == 'λ' && automato.getLista().indexOf(automato.getLista().get(estadoAtual).getLista().get(i).getAlvo()) != estadoAtual) {

                aux = true;
                break;
            }

        }

        if ((!aux) && ((posPalavra) == palavraDeEntrada.length()) && (!automato.getLista().get(estadoAtual).isIsFinal())) {
            return;
        }

        for (int i = 0; i < automato.getLista().get(estadoAtual).getLista().size(); i++) {

            if ('λ' == automato.getLista().get(estadoAtual).getLista().get(i).getChave().charAt(0)) {

                if (automato.getLista().indexOf(automato.getLista().get(estadoAtual).getLista().get(i).getAlvo()) == estadoAtual) {
                    continue;
                } else {
                    vetor.add(new VerificacaoEE(estadoAtual, posPalavra));
                    caminhoResultado += "q" + automato.getLista().get(estadoAtual).getLista().get(i).getAlvo().getValor() + " -> ";
                    passoApasso.add(automato.getLista().get(estadoAtual).getLista().get(i).getAlvo().getValor());
                    verificaRegra(palavraDeEntrada, posPalavra, automato.getLista().indexOf(automato.getLista().get(estadoAtual).getLista().get(i).getAlvo()));
                }
            } else if (palavraDeEntrada.isEmpty() && automato.getLista().get(estadoAtual).getLista().get(i).getChave().charAt(0) != 'λ') {
                continue;
            } else if (palavraDeEntrada.charAt(posPalavra) == automato.getLista().get(estadoAtual).getLista().get(i).getChave().charAt(0)) {

                caminhoResultado += "q" + automato.getLista().get(estadoAtual).getLista().get(i).getAlvo().getValor() + " -> ";
                passoApasso.add(automato.getLista().get(estadoAtual).getLista().get(i).getAlvo().getValor());
                verificaRegra(palavraDeEntrada, posPalavra + 1, automato.getLista().indexOf(automato.getLista().get(estadoAtual).getLista().get(i).getAlvo()));
            }
        }
    }

    public static String salvarAutomato() {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;\n"
                + "\t<type>fa</type>&#13;\n\t<automaton>&#13;\n"
                + "\t\t<!--The list of states.-->&#13;\n";

        for (Vertice v : FXMLPrincipalController.lista) {

            xml += "\t\t<state id=\"" + v.getID() + "\" name=\"q" + v.getID() + "\">&#13;\n"
                    + "\t\t\t<x>" + v.getCenterX() + "</x>&#13;\n"
                    + "\t\t\t<y>" + v.getCenterY() + "</y>&#13;\n";

            if (v.isIsInitial()) {
                xml += "\t\t\t<initial/>&#13;\n";
            }
            if (v.isSelected()) {
                xml += "\t\t\t<final/>&#13;\n";
            }
            xml += "\t\t</state>&#13;\n";
        }

        xml += "\t\t<!--The list of transitions.-->&#13;\n";
        String transicao = "", valor = "";
        int origem, destino;
        for (Aresta a : FXMLPrincipalController.arestas) {

            if (a.getOrigem() == a.getDestino()) {
                destino = origem = a.getDestino();
                valor = a.getTexto();

            } else {
                origem = a.getOrigem();
                destino = a.getDestino();
                valor = a.getTexto();
            }

            if (!a.getTexto().equals("λ")) {
                transicao = "<read>" + valor + "</read>";
            } else {
                transicao = "<read/>";
            }

            xml += "\t\t<transition>&#13;\n"
                    + "\t\t\t<from>" + origem + "</from>&#13;\n"
                    + "\t\t\t<to>" + destino + "</to>&#13;\n"
                    + "\t\t\t" + transicao + "&#13;\n"
                    + "\t\t</transition>&#13;\n";
        }

        xml += "\t</automaton>&#13;\n"
                + "</structure>";

        return xml;
    }

    public static void salvarArquivo() {

        String xml = salvarAutomato();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Salvar Autômato Finito");
        chooser.getExtensionFilters().addAll(
                new ExtensionFilter("Arquivo do JFLAP 7", "*.jff")
        );

        File file = chooser.showSaveDialog(null);

        try {
            if (file.exists()) {

                file.delete();
                file.createNewFile();
            }

            if (file != null) {

                PrintStream out = new PrintStream(file);
                out.print(xml);
                out.close();
            }

        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }

    public static void abrirArquivo() {

        Automato auto = new Automato();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir Autômato Finito");
        chooser.getExtensionFilters().addAll(
                new ExtensionFilter("Arquivo do JFLAP 7", "*.jff")
        );

        File file = chooser.showOpenDialog(null);

        if (file == null) {
            return;
        }
        String xml = "";

        Scanner arquivo = null;
        try {
            arquivo = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GerenciadorAutomatos.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (arquivo.hasNext()) {
            xml += arquivo.nextLine();
        }

        xml = xml.replaceAll("&#13;", "");
        xml = xml.replaceAll("\\t", "");

        try {

            File temp = File.createTempFile("~temp", ".xml");

            PrintStream out = new PrintStream(temp);
            out.print(xml);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document doc = builder.parse(temp);
            doc.getDocumentElement().normalize();

            //State
            int id;
            float x, y;
            boolean isFinal, isInitial;
            NodeList listaEstados = doc.getElementsByTagName("state");

            for (int i = 0; i < listaEstados.getLength(); i++) {
                Node node = listaEstados.item(i);
                isFinal = isInitial = false;
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element e = (Element) node;
                    id = Integer.parseInt(e.getAttribute("id"));

                    x = Float.parseFloat(((Element) e.getElementsByTagName("x").item(0)).getTextContent());
                    y = Float.parseFloat(((Element) e.getElementsByTagName("y").item(0)).getTextContent());

                    if (e.getElementsByTagName("final").getLength() == 1) {
                        isFinal = true;
                    }

                    auto.addEstado(id, x, y, isFinal);

                    if (e.getElementsByTagName("initial").getLength() == 1) {
                        isInitial = true;
                        auto.setInicial(id);
                    }

                }
            }

            //Transition
            int inicio, fim;
            String letra;
            NodeList listaTransicoes = doc.getElementsByTagName("transition");
            for (int i = 0; i < listaTransicoes.getLength(); i++) {

                Node node = listaTransicoes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element e = (Element) node;
                    inicio = Integer.parseInt(((Element) e.getElementsByTagName("from").item(0)).getTextContent());
                    fim = Integer.parseInt(((Element) e.getElementsByTagName("to").item(0)).getTextContent());
                    letra = ((Element) e.getElementsByTagName("read").item(0)).getTextContent();
                    auto.get(inicio).addTransicao(fim, letra);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GerenciadorAutomatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GerenciadorAutomatos.class.getName()).log(Level.SEVERE, null, ex);
        }

        GerenciadorAutomatos.automato = auto;

    }

    public static void desenhar() {

        FXMLPrincipalController.painelD.getChildren().clear();

        Vertice v, origem = null, destino;

        for (Estados est : automato.getLista()) {

            v = new Vertice(est.getValor(), est.getX(), est.getY(), 20);
            Desenho.desenharVertice(FXMLPrincipalController.painelD, v);
            FXMLPrincipalController.lista.add(v);

            if (est == automato.getInicial()) {
                v.inicio.setLayoutX(v.getCenterX() - 35);
                v.inicio.setLayoutY(v.getCenterY() - 15);
                FXMLPrincipalController.painelD.getChildren().add(v.inicio);
            }
        }

        for (Estados est : automato.getLista()) {

            for (Vertice vertice : FXMLPrincipalController.lista) {
                if (est.getValor() == vertice.getID()) {
                    origem = vertice;
                    break;
                }
            }
            String chave = "";
            for (Transicao t : est.getLista()) {

                for (Vertice a : FXMLPrincipalController.lista) {

                    if (a.getID() == t.getAlvo().getValor()) {
                        destino = a;

                        if (t.getChave().isEmpty()) {
                            chave = "λ";
                        } else {
                            chave = t.getChave();
                        }

                        Desenho.desenharAresta(FXMLPrincipalController.painelD, origem, destino, chave);
                        break;
                    }
                }
            }

            FXMLPrincipalController.conjunto.getSelectionModel().select(FXMLPrincipalController.tabautoD);
        }
    }
}
