/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import static aplicacao.FXMLPrincipalController.legendaAtual;
import static aplicacao.FXMLPrincipalController.verticeAtual;
import static aplicacao.FXMLPrincipalController.textoAtual;
import desenho.Arco;
import desenho.Aresta;
import desenho.Desenho;
import desenho.Legenda;
import desenho.Vertice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.QuadCurve;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class GerenciadorAutomatos {

    public int quantidade = 0;
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

        String tipo = "";
        armazenarAutomato();

        //verifica se é um afs, moore ou mealy
        switch (tipoAutomato) {

            case 0:
                tipo = "fsa";
                break;

            case 1:
                tipo = "moore_machine";
                break;

            case 2:
                tipo = "mealy_machine";
                break;
        }

        //////////////////////////////////////////////
        String conjuntoEstados = "\t\t\t<structure type=\"state_set\">&#13;\n";
        String conjuntoInicial = "\t\t\t<structure type=\"start_state\">&#13;\n"
                + "\t\t\t\t<state>&#13;\n"
                + "\t\t\t\t<name>q" + automato.getInicial().getValor() + "</name>&#13;\n"
                + "\t\t\t\t<id>" + automato.getInicial().getValor() + "</id>&#13;\n"
                + "\t\t\t\t</state>&#13;\n"
                + "\t\t\t</structure>&#13;\n";
        String conjuntoFinais = "\t\t\t<structure type=\"final_states\">&#13;\n";
        String conjuntoTransicoes = "\t\t\t<structure type=\"transition_set\">&#13;\n";
        String lista = "\t\t\t<structure type=\"input_alph\">&#13;\n";
        String aux = "";

        HashMap<String, Integer> listaTransicoes = new HashMap<>();

        for (Estados a : automato.getLista()) {
            aux = "\t\t\t\t<state>&#13;\n"
                    + "\t\t\t\t\t<name>q" + a.getValor() + "</name>&#13;\n"
                    + "\t\t\t\t\t<id>" + a.getValor() + "</id>&#13;\n"
                    + "\t\t\t\t</state>&#13;\n";

            conjuntoEstados += aux;
            if (a.isIsFinal()) {
                conjuntoFinais += aux;
            }
            
            String chave;
            
            for (Transicao t : a.getLista()) {

                listaTransicoes.put(t.getChave(), 1);
                
                if (t.getChave().equals("λ")){
                    chave = "\t\t\t\t\t<input/>&#13;\n";
                }
                else{
                    chave = "\t\t\t\t\t<input>" + t.getChave() + "</input>&#13;\n";
                }
                
                conjuntoTransicoes += "\t\t\t\t<fsa_trans>&#13;\n"
                        + chave
                        + "\t\t\t\t\t<from>&#13;\n"
                        + "\t\t\t\t\t\t<name>q" + a.getValor() + "</name>&#13;\n"
                        + "\t\t\t\t\t\t<id>" + a.getValor() + "</id>&#13;\n"
                        + "\t\t\t\t\t</from>&#13;\n"
                        + "\t\t\t\t\t<to>&#13;\n"
                        + "\t\t\t\t\t\t<name>q" + t.getAlvo().getValor() + "</name>&#13;\n"
                        + "\t\t\t\t\t\t<id>" + t.getAlvo().getValor() + "</id>&#13;\n"
                        + "\t\t\t\t\t</to>&#13;\n"
                        + "\t\t\t\t</fsa_trans>&#13;\n";
            }
        }

        for (String s : listaTransicoes.keySet()) {
            if (!s.equals("λ")) lista += "\t\t\t\t<symbol>" + s + "</symbol>&#13;\n";
        }

        conjuntoEstados += "\t\t\t</structure>&#13;\n";
        conjuntoFinais += "\t\t\t</structure>&#13;\n";
        conjuntoTransicoes += "\t\t\t</structure>&#13;\n";
        lista += "\t\t\t</structure>&#13;\n";
        String fim = "\t\t</structure>&#13;\n";

        String mapeamentoPontos = "\t\t<state_point_map>&#13;\n";

        String estadoLabel = "\t<state_label_map>&#13;\n";
        
    
        
        for (Vertice v : FXMLPrincipalController.lista) {
            
            mapeamentoPontos += "\t\t\t<state_point>&#13;\n"
                    + "\t\t\t\t<state>" + v.getID() + "</state>&#13;\n"
                    + "\t\t\t\t<point>&#13;\n"
                    + "\t\t\t\t\t<x>" + String.format(Locale.US, "%.1f", v.getCenterX()) + "</x>&#13;\n"
                    + "\t\t\t\t\t<y>" + String.format(Locale.US, "%.1f", v.getCenterY()) + "</y>&#13;\n"
                    + "\t\t\t\t</point>&#13;\n"
                    + "\t\t\t</state_point>&#13;\n";

            if (v.hasSubtitle) {

                estadoLabel += "\t\t<note>&#13;\n"
                        + "\t\t\t<value>" + v.legenda.get().getText() + "</value>&#13;\n"
                        + "\t\t\t<point>&#13;\n"
                        + "\t\t\t\t<x>" + v.legenda.get().getLayoutX() + "</x>&#13;\n"
                        + "\t\t\t\t<y>" + v.legenda.get().getLayoutY() + "</y>&#13;\n"
                        + "\t\t\t</point>&#13;\n"
                        + "\t\t</note>&#13;\n";
            }
        }

        mapeamentoPontos += "\t\t</state_point_map>&#13;\n";
        estadoLabel += "\t</state_label_map>&#13;\n";

        String noteMap = "\t<note_map>&#13;\n";

        for (Legenda l : FXMLPrincipalController.legendas) {
            noteMap += "\t\t<note>&#13;\n"
                    + "\t\t\t<value>" + l.get().getText() + "</value>&#13;\n"
                    + "\t\t\t<point>&#13;\n"
                    + "\t\t\t\t<x>" + l.get().getLayoutX() + "</x>&#13;\n"
                    + "\t\t\t\t<y>" + l.get().getLayoutY() + "</y>&#13;\n"
                    + "\t\t\t</point>&#13;\n"
                    + "\t\t</note>&#13;\n";
        }

        noteMap += "\t</note_map>&#13;\n";
        ///////////////////////////////////////////////

        String controlePontos = "\t\t<control_point_map>&#13;\n";

        for (Aresta a : FXMLPrincipalController.arestas) {

            if (a.getOrigem() != a.getDestino()) {
                
                Arco b = (Arco) a;
                QuadCurve f = (QuadCurve) b.getForma();
                controlePontos += "\t\t\t<control_point>&#13;\n"
                        + "\t\t\t\t<from>" + b.getOrigem() + "</from>&#13;\n"
                        + "\t\t\t\t<to>" + b.getDestino() + "</to>&#13;\n"
                        + "\t\t\t\t<point>&#13;\n"
                        + "\t\t\t\t\t<x>" + String.format(Locale.US, "%.1f", f.getControlX()) + "</x>&#13;\n"
                        + "\t\t\t\t\t<y>" + String.format(Locale.US, "%.1f", f.getControlY()) + "</y>&#13;\n"
                        + "\t\t\t\t</point>&#13;\n"
                        + "\t\t\t</control_point>&#13;\n";
                
            } else {
                controlePontos += "\t\t\t<control_point>&#13;\n"
                        + "\t\t\t\t<from>" + a.getOrigem() + "</from>&#13;\n"
                        + "\t\t\t\t<to>" + a.getDestino() + "</to>&#13;\n"
                        + "\t\t\t\t<point>&#13;\n"
                        + "\t\t\t\t\t<x>" + a.getOrigemVertice().getCenterX() + "</x>&#13;\n"
                        + "\t\t\t\t\t<y>" + (a.getOrigemVertice().getCenterY() - 23) + "</y>&#13;\n"
                        + "\t\t\t\t</point>&#13;\n"
                        + "\t\t\t</control_point>&#13;\n";
            }
        }

        controlePontos += "\t\t</control_point_map>&#13;\n";
        //////////////////////////////////////

        //////////////////////////////////////
        String xml = "";
        xml += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<structure type=\"editor_panel\">&#13;\n"
                + "\t<structure type=\"transition_graph\">&#13;\n"
                + "\t\t<structure mode=\"Default mode\" type=\"" + tipo + "\">&#13;\n";

        if (tipoAutomato == 0) {
            xml += conjuntoEstados + conjuntoFinais + conjuntoTransicoes + conjuntoInicial + lista
                    + "\t\t</structure>&#13;\n" + mapeamentoPontos + controlePontos + "\t</structure>&#13;\n"
                    + estadoLabel + noteMap + "</structure>";
        } else if (tipoAutomato == 1) {

        } else {

        }

        return xml;
    }

    public static void salvarArquivo() {

        String xml = salvarAutomato();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Salvar Autômato");
        chooser.getExtensionFilters().addAll(
                new ExtensionFilter("Arquivo do JFLAP 8", "*.jflap")
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
            }
            
        } catch (IOException e) {
            
            System.out.println(e.getMessage());
        }
    }
    
    public static void abrirArquivo(){
        
    }
}
