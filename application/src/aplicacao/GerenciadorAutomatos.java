/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import static aplicacao.FXMLPrincipalController.legendaAtual;
import static aplicacao.FXMLPrincipalController.verticeAtual;
import static aplicacao.FXMLPrincipalController.textoAtual;
import desenho.Aresta;
import desenho.Desenho;
import desenho.Legenda;
import desenho.Vertice;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class GerenciadorAutomatos {
    
    public int quantidade = 0;
    private Vertice inicio;
    private Vertice fim;
    public static boolean subtitleNode = false;
    public static Automato automato;
    public static boolean verificacao = false;
    
    public static String caminhoResultado;
    
    public static ArrayList<VerificacaoEE> vetor;
    
    public static void armazenarAutomato(){
        
        automato = new Automato();
        Estados est;
        for(Vertice v: FXMLPrincipalController.lista){
            est = new Estados(v.getID(), v.isSelected());
            automato.getLista().add(est);
            if(v.isIsInitial()) automato.setInicial(est);
        }
        
        for(Aresta a: FXMLPrincipalController.arestas){
            
            Estados inicio = null, fim = null;
            
            for(Estados e: automato.getLista()){
                
                if(e.getValor() == a.getOrigem()) inicio = e;
                if(e.getValor() == a.getDestino()) fim = e;
            }

            
            String[] transicoes = a.getTexto().split("|");
            for(String as: transicoes){
                if(!as.equals("|")){
                    
                    inicio.getLista().add(new Transicao(as, fim));
                }
            }
        }
    }

    public void montarPainel(Pane painelDesenho, ArrayList<Vertice> lista, ArrayList<Legenda> legendas) {

        FXMLPrincipalController.estado = Estado.NORMAL_CURSOR;

        painelDesenho.setOnMousePressed((MouseEvent event) -> {

            /*switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:
                    
                    break;

                case MOVER_CURSOR:

                    break;

                case INSERIR_CURSOR:

                    break;

                case TEXTO_CURSOR:
                    
                    break;
            }*/
            switch (FXMLPrincipalController.estado) {

                case NORMAL_CURSOR:

                    if (event.isPrimaryButtonDown()) {
                        double distancia, dx, dy;

                        //System.out.println("entrou " + (painelDesenho.getLayoutX() + event.getX()-40) + " -> " + (painelDesenho.getLayoutY() + event.getY()));
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

                    //System.out.println("saiu " + (painelDesenho.getLayoutX() + event.getX()-40) + " -> " + (painelDesenho.getLayoutY() + event.getY()));
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

    public static void processamentoAutomato(String palavraDeEntrada){
        
        //Chamo a função pela primeira vez com a palavra de entrada, posição da string e o estado inicial.
        verificacao = false;
        
        vetor = new ArrayList<>();
        verificaRegra(palavraDeEntrada, 0, automato.getLista().indexOf(automato.getInicial()));
    }
    
    public static void verificaRegra(String palavraDeEntrada, int posPalavra, int estadoAtual){
        
        for(VerificacaoEE v : vetor){ //Verificação ciclo vazio!
            
            if(v.getNumEstado() == estadoAtual && v.posPalavra == posPalavra) return;
        }
        
        if( ((posPalavra) == palavraDeEntrada.length()) && (automato.getLista().get(estadoAtual).isIsFinal()) ) {
            verificacao = true;
            return;
        }
        
        boolean aux = false;
        for(int i = 0; i < automato.getLista().get(estadoAtual).getLista().size(); i++){
            
            if(automato.getLista().get(estadoAtual).getLista().get(i).getChave().charAt(0) == 'λ' && automato.getLista().indexOf(automato.getLista().get(estadoAtual).getLista().get(i).getAlvo()) != estadoAtual){
            
                aux = true;
                break;
            }
            
        }
        
        if( (!aux) && ((posPalavra) == palavraDeEntrada.length()) && (!automato.getLista().get(estadoAtual).isIsFinal()) ){
            System.out.println("dgf");
            return;
        }
        
        for(int i = 0; i < automato.getLista().get(estadoAtual).getLista().size(); i++){
                        
            if('λ' == automato.getLista().get(estadoAtual).getLista().get(i).getChave().charAt(0)){
                
                if(automato.getLista().indexOf(automato.getLista().get(estadoAtual).getLista().get(i).getAlvo()) == estadoAtual) continue;
                else {
                    vetor.add(new VerificacaoEE(estadoAtual, posPalavra));
                    verificaRegra(palavraDeEntrada, posPalavra, automato.getLista().indexOf(automato.getLista().get(estadoAtual).getLista().get(i).getAlvo()));
                }
            }
            else if(palavraDeEntrada.isEmpty() && automato.getLista().get(estadoAtual).getLista().get(i).getChave().charAt(0) != 'λ'){
                continue;
            }
            else if(palavraDeEntrada.charAt(posPalavra) == automato.getLista().get(estadoAtual).getLista().get(i).getChave().charAt(0) ){
                
                verificaRegra(palavraDeEntrada, posPalavra+1, automato.getLista().indexOf(automato.getLista().get(estadoAtual).getLista().get(i).getAlvo()));
            }
        }
    }
    
    
}
