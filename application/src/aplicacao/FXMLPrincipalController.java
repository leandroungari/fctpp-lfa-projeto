/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import desenho.Aresta;
import java.util.regex.*;
import desenho.Legenda;
import desenho.Vertice;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLPrincipalController implements Initializable {

    /////////////////////////////
    // Itens para o desenho de automatos
    @FXML
    private Pane painelDesenho;
    @FXML
    private Button normalCursor;
    @FXML
    private Button moverCursor;
    @FXML
    private Button inserirCursor;
    @FXML
    private Button textoCursor;
    
    @FXML
    private MenuItem entradaUnicaAutomato;
    @FXML
    private MenuItem entradaMultiplaAutomato;
    @FXML
    private MenuItem entradaPassoPasso;

    /////////////////////////////
    // Itens para a expressão regular
    @FXML
    private TextField regraTextField;
    @FXML
    private TextField entradaTextField;
    @FXML
    private Button botaoAjudaER;
    @FXML
    private TextArea textAreaAjudaER;

    /////////////////////////////////
    //Itens para a gramática regular
    @FXML
    private TableView table;
    @FXML
    private TableColumn naoTerminal;
    @FXML
    private TableColumn seta;
    @FXML
    private TableColumn terminal;
    @FXML
    private Label labelErroER;

    @FXML
    private MenuItem entradaUnica;
    @FXML
    private MenuItem entradaMultipla;
    
    @FXML
    private Button limparGramatica;

    @FXML
    private Label stringNaoTerminal;
    @FXML
    private Label stringTerminal;
    
    @FXML
    private Tab tabexp;
    @FXML
    private Tab tabauto;
    @FXML
    private Tab tabgram;
    @FXML
    private TabPane conjuntoTab;
    
    
    
    ////////////////////////////////////
    
    @FXML
    private MenuItem abrirAutomato;
    @FXML
    private MenuItem salvarAutomato;
    
    @FXML
    private MenuItem sair;

    ////////////////////////////////////
    //Itens gerais
    public static Vertice verticeAtual;
    public static Legenda legendaAtual;
    public static Legenda adesivoAtual;
    public static Text textoAtual;
    public static Pane painelD;
    public static TableView tabelaD;
    public static Tab tabautoD, tabgramD, tabexpD;
    public static TabPane conjunto;

    public static Vertice verticeInicial;
    public static Vertice verticeFinal;
    public static Vertice verticeSobre;

    public static ArrayList<Vertice> lista = new ArrayList<>();
    public static ConcurrentLinkedQueue<Aresta> arestas = new ConcurrentLinkedQueue<>();
    public static ArrayList<Legenda> legendas = new ArrayList<>();
    public static Estado estado;
    
    ////////////////
    // Conversão
    
    @FXML 
    private MenuItem convexpauto;
    
    @FXML 
    private MenuItem convautoexp;
    
    @FXML 
    private MenuItem convautogram;
    
    @FXML 
    private MenuItem convgramauto;
    
    //////////////////////////
    // Menu Item
    @FXML 
    private CheckMenuItem auto;
    
    @FXML 
    private CheckMenuItem mealy;
    
    @FXML 
    private CheckMenuItem moore;
    
    @FXML 
    private MenuItem limpar;
    
    public static final int MACHINE_AUTOMATO_FINITE = 1;
    public static final int MACHINE_MOORE = 2;
    public static final int MACHINE_MEALY = 3;
    
    public static int maquinaAtual;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        FXMLPrincipalController.maquinaAtual = MACHINE_AUTOMATO_FINITE;
        auto.setSelected(true);
        
        sair.setOnAction(event -> System.exit(0));
        
        limpar.setOnAction(event -> {
            FXMLPrincipalController.painelD.getChildren().clear();
            FXMLPrincipalController.lista.clear();
            FXMLPrincipalController.arestas.clear();
            FXMLPrincipalController.legendas.clear();
        });
        
        convexpauto.setOnAction(event -> {
            
            try {
                GerenciadorConversao.converterExpressaoAutomato(regraTextField);
            } catch (Exception ex) {
                Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        convautogram.setOnAction(event -> {
            
            GerenciadorConversao.converterAutomatoGramatica(tabelaD);
        });
        
        convautoexp.setOnAction(event -> {
            try {
                GerenciadorConversao.converterAutomatoExpressao(regraTextField);
            } catch (Exception ex) {
                Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        convgramauto.setOnAction(event -> {
            GerenciadorConversao.converterGramaticaAutomato(tabelaD);
        });
        
        auto.setOnAction(event -> {
            FXMLPrincipalController.maquinaAtual = MACHINE_AUTOMATO_FINITE;
            FXMLPrincipalController.painelD.getChildren().clear();
            auto.setSelected(true);
            mealy.setSelected(false);
            moore.setSelected(false);
            
            FXMLPrincipalController.lista.clear();
            FXMLPrincipalController.arestas.clear();
            GerenciadorAutomatos.quantidade = 0;
            entradaPassoPasso.setDisable(false);
            convautoexp.setDisable(false);
            convautogram.setDisable(false);
            convexpauto.setDisable(false);
            convgramauto.setDisable(false);
        });
        
        mealy.setOnAction(event -> {
            FXMLPrincipalController.maquinaAtual = MACHINE_MEALY;
            FXMLPrincipalController.painelD.getChildren().clear();
            auto.setSelected(false);
            mealy.setSelected(true);
            moore.setSelected(false);
            
            FXMLPrincipalController.lista.clear();
            FXMLPrincipalController.arestas.clear();
            GerenciadorAutomatos.quantidade = 0;
            entradaPassoPasso.setDisable(true);
            convautoexp.setDisable(true);
            convautogram.setDisable(true);
            convexpauto.setDisable(true);
            convgramauto.setDisable(true);
        });
        
        moore.setOnAction(event -> {
            FXMLPrincipalController.maquinaAtual = MACHINE_MOORE;
            FXMLPrincipalController.painelD.getChildren().clear();
            auto.setSelected(false);
            mealy.setSelected(false);
            moore.setSelected(true);
            
            FXMLPrincipalController.lista.clear();
            FXMLPrincipalController.arestas.clear();
            GerenciadorAutomatos.quantidade = 0;
            entradaPassoPasso.setDisable(true);
            convautoexp.setDisable(true);
            convautogram.setDisable(true);
            convexpauto.setDisable(true);
            convgramauto.setDisable(true);
        });
        
        painelD = painelDesenho;
        tabelaD = table;
        tabautoD = tabauto;
        tabgramD = tabgram;
        tabexpD = tabexp;
        conjunto = conjuntoTab;

        //Executar automatos
        GerenciadorAutomatos g = new GerenciadorAutomatos();
        g.montarPainel(painelDesenho, lista, legendas);
        g.montarBotoes(normalCursor, moverCursor, inserirCursor, textoCursor);

        //Executar gramática
        GerenciadorGramatica gramatica = new GerenciadorGramatica();
        gramatica.montarGramatica(table, limparGramatica);

        //Expressão Regular - inicio
        this.entradaTextField.setStyle("-fx-border-color:#31ef02");
        this.entradaTextField.setStyle("-fx-background-color:#31ef02");

        regraTextField.setOnKeyReleased(event -> {

            String regra = this.regraTextField.getText();

            if (!this.verificarSintaxeRegraRegex(regra)) {
                this.labelErroER.setVisible(true);
                this.regraTextField.setStyle("-fx-border-color:#ff0f0f");
                this.entradaTextField.setStyle("-fx-background-color:#ff0f0f");
            } else {
                this.labelErroER.setVisible(false);
                this.regraTextField.setStyle("-fx-border:none");
                this.patternRegexERcomEntrada();
            }

        });

        entradaTextField.setOnKeyReleased(event -> {

            this.patternRegexERcomEntrada();
        });

        this.adicionarTextoTextArea(this.textAreaAjudaER);
        botaoAjudaER.setOnMouseClicked(event -> {

            if (this.textAreaAjudaER.isVisible()) {
                this.textAreaAjudaER.setVisible(false);
            } else {
                this.textAreaAjudaER.setVisible(true);
            }

        });

        this.inicializarTabela();

        entradaUnica.setOnAction(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("FXMLEntradaUnica.fxml"));

            } catch (IOException ex) {

                Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Stage stage = new Stage();
            stage.setTitle("Gramática - Entrada única");
            stage.setScene(new Scene(root, 450, 450));
            //stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initStyle(StageStyle.DECORATED);
            stage.showAndWait();

        });

        entradaMultipla.setOnAction(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("FXMLEntradaMultipla.fxml"));

            } catch (IOException ex) {

                Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Stage stage = new Stage();
            stage.setTitle("Gramática - Multipla");
            stage.setScene(new Scene(root, 450, 450));
            //stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initStyle(StageStyle.DECORATED);
            stage.showAndWait();

        });
        
        entradaUnicaAutomato.setOnAction(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("FXMLEntradaUnicaAutomato.fxml"));

            } catch (IOException ex) {

                Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Stage stage = new Stage();
            stage.setTitle("Autômatos - Entrada única");
            stage.setScene(new Scene(root, 450, 450));
            //stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initStyle(StageStyle.DECORATED);
            stage.showAndWait();

        });
        
        entradaPassoPasso.setOnAction(event -> {
            
            
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("FXMLEntradaPassoPasso.fxml"));

            } catch (IOException ex) {

                Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Stage stage = new Stage();
            stage.setTitle("Autômatos - Passo a Passo");
            stage.setScene(new Scene(root));
            //stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            
            stage.setOnCloseRequest(e -> {
                
                for(Vertice v: FXMLPrincipalController.lista){
                    v.setFill(Color.DODGERBLUE);
                }
            });
            
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initStyle(StageStyle.DECORATED);
            stage.showAndWait();

        });

        entradaMultiplaAutomato.setOnAction(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("FXMLEntradaMultiplaAutomato.fxml"));

            } catch (IOException ex) {

                Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Stage stage = new Stage();
            stage.setTitle("Autômatos - Multipla");
            stage.setScene(new Scene(root, 450, 450));
            //stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initStyle(StageStyle.DECORATED);
            stage.showAndWait();

        });
        
        
        abrirAutomato.setOnAction(event -> {
            GerenciadorAutomatos.abrirArquivo();
            GerenciadorAutomatos.desenhar();
        });
        
        salvarAutomato.setOnAction(event -> {
        
            GerenciadorAutomatos.salvarArquivo();
        });
    }

    public void adicionarTextoTextArea(TextArea ta) {

        String conteudo = "Instruções para a utilização da aplicação:\n"
                + "Expressões Regulares\n\n"
                + "No campo Expressão Regular deve ser inserido a ER, regra, que espera-se que um conjunto de strings de entrada sejam validas.\n\n"
                + "No campo Entrada deve ser inserido uma string e a aplicação verifica se a strng de entrada está valida de acordo com a regra definida\nanteriormente.\n\n"
                + "Caracteres Valídos para o campo de Expressão Regular: Letras, Números, '+', '*', '.', '(', ')', '|' \n"
                + "Caso qualquer caractere invalído seja inserido, não será realizado a verificação com relação a string de entrada.\n\n"
                + "Significado dos caracteres:\n\n"
                + "Letras e números : Caracteres da string de entrada\n"
                + "| : OU\n"
                + "* : Qualquer quantidade de um determinado caractere, ou string, inclusive vazio!\n"
                + "+ : Qualquer quantidade de um determinado caractere, não incluindo o vazio!\n"
                + ". : Concatenação\n"
                + "( ) : Parênteses, muito utilizado para expressões regulares do tipo (a|b)*\n\n"
                + "Exemplos de entradas:\n\n"
                + "1)Expressão Regular: (a|b)\n"
                + "1)Entrada: a\n"
                + "Para o exemplo 1) a entrada está correta, pois a expressão permite qualquer entrada o formada por um único a ou um único b.\n\n"
                + "2)Expressão Regular: a(a|b)*\n"
                + "2)Entrada: aaaab\n"
                + "Para o exemplo 2) a entrada está correta, pois a expressão permite qualquer entrada formada inicialmente pela letra a, seguido\n"
                + "de qualquer ocorrência de 'a' ou 'b' sendo estes em qualquer quantidade.\n\n"
                + "3)Expressão Regular: a*\n"
                + "3)Entrada: aaaaaaa\n"
                + "Para o exemplo 3) a entrada está correta, pois a expressão permite qualquer entrada o formada por qualquer quantidade de a, \n"
                + "inclusive nenhum.\n\n"
                + "4)Expressão Regular: a+\n"
                + "4)Entrada: aaa\n"
                + "Para o exemplo 4) a entrada está correta, pois a expressão permite qualquer entrada o formada por qualquer quantidade de a, \n"
                + "sendo que essa quantidade seja pelo menos um.\n\n";

        ta.setText(conteudo);
    }

    public void trocarCorTextField(boolean verif, TextField tf) {

        if (verif) {
            //Cor verde = #31ef02 - Correto
            tf.setStyle("-fx-border-color:#31ef02");
            tf.setStyle("-fx-background-color:#31ef02");
        } else {
            //Cor vermelho = #ff0f0f - Errado
            tf.setStyle("-fx-border-color:#ff0f0f");
            tf.setStyle("-fx-background-color:#ff0f0f");
        }
    }

    public boolean verificarSintaxeRegraRegex(String regra) {

        char c;
        for (int i = 0; i < regra.length(); i++) {
            c = regra.charAt(i);

            if (!Character.isLetterOrDigit(c)) {

                if (c != '+' && c != '*' && c != '.' && c != '|' && c != '.' && c != '(' && c != ')') {
                    return false;
                }
            }
        }

        return (true);
    }

    public void patternRegexERcomEntrada() {

        String regra = this.regraTextField.getText();
        String entrada = this.entradaTextField.getText();

        try {
            Pattern.compile(regra);
            boolean verif = Pattern.matches(regra, entrada);
            this.trocarCorTextField(verif, entradaTextField);
        } catch (PatternSyntaxException e) {
        }

    }
    //Expressão Regular - Fim

    public void inicializarTabela() {

        this.criarLinhaTabela();
    }

    public void criarLinhaTabela() {

        table.setEditable(true);
        naoTerminal.setCellValueFactory(new PropertyValueFactory<LinhaTabela, String>("naoTerminal"));
        seta.setCellValueFactory(new PropertyValueFactory<LinhaTabela, String>("seta"));
        terminal.setCellValueFactory(new PropertyValueFactory<LinhaTabela, String>("terminal"));

        naoTerminal.setCellFactory(TextFieldTableCell.forTableColumn());
        naoTerminal.setOnEditCommit(new EventHandler<CellEditEvent<LinhaTabela, String>>() {

            @Override
            public void handle(CellEditEvent<LinhaTabela, String> event) {

                String valor = event.getNewValue();

                if (valor.length() == 1 && Character.isUpperCase(valor.charAt(0))) {
                    ((LinhaTabela) event.getTableView().getItems().get(event.getTablePosition().getRow())).setNaoTerminal(event.getNewValue());

                    boolean NaoTtemSimbolo = true;
                    for (String s : GerenciadorGramatica.listaNaoTerminais) {

                        if (s.equals(valor)) {
                            NaoTtemSimbolo = false;
                        }
                    }

                    if (NaoTtemSimbolo) {

                        String texto = "Símbolos não-terminais:";
                        GerenciadorGramatica.listaNaoTerminais.add(valor);

                        for (String s : GerenciadorGramatica.listaNaoTerminais) {

                            texto += " " + s;
                        }

                        stringNaoTerminal.setText(texto);
                    }
                } else {                    

                    Object[] t = table.getItems().toArray();
                    table.getItems().clear();
                    int i;
                    for (i = 0; i < t.length; i++) {

                        table.getItems().add(t[i]);
                    }

                    table.getSelectionModel().select(event.getTablePosition().getRow());
                    return;
                }

                Object[] t = table.getItems().toArray();
                table.getItems().clear();
                int i;
                for (i = 0; i < t.length; i++) {

                    if (i == t.length - 1) {
                        ((LinhaTabela) t[i]).setTerminal("λ");
                    }

                    table.getItems().add(t[i]);
                }

                table.getSelectionModel().select(table.getItems().size() - 1);

                if (table.getSelectionModel().getSelectedIndex() == table.getItems().size() - 1) {
                    table.getItems().add(new LinhaTabela("", "->", ""));
                }
            }
        });

        terminal.setCellFactory(TextFieldTableCell.forTableColumn());
        terminal.setOnEditCommit(new EventHandler<CellEditEvent<LinhaTabela, String>>() {

            @Override
            public void handle(CellEditEvent<LinhaTabela, String> event) {

                String valor = event.getNewValue();
                String simboloInserido;

                if (valor.isEmpty()) {
                    ((LinhaTabela) event.getTableView().getItems().get(event.getTablePosition().getRow())).setTerminal("λ");
                    
                    simboloInserido = "λ";
                    
                } else if (valor.length() == 1) {
                    ((LinhaTabela) event.getTableView().getItems().get(event.getTablePosition().getRow())).setTerminal(event.getNewValue());
                 
                    simboloInserido = valor;
                } else if (valor.length() == 2
                        && ((Character.isUpperCase(valor.charAt(0)) && Character.isLowerCase(valor.charAt(1)))
                        || (Character.isUpperCase(valor.charAt(1)) && Character.isLowerCase(valor.charAt(0))))) {

                    ((LinhaTabela) event.getTableView().getItems().get(event.getTablePosition().getRow())).setTerminal(event.getNewValue());

                    if (Character.isLowerCase(valor.charAt(0))) {
                        
                        simboloInserido = String.valueOf(valor.charAt(0));
                    } else {
       
                        simboloInserido = String.valueOf(valor.charAt(1));
                    }
                } else {

                    Object[] t = table.getItems().toArray();
                    table.getItems().clear();
                    int i;
                    for (i = 0; i < t.length; i++) {

                        table.getItems().add(t[i]);
                    }

                    table.getSelectionModel().select(event.getTablePosition().getRow());
                    return;
                }
                
                

                boolean NaoTtemSimbolo = true;
                for (String s : GerenciadorGramatica.listaTerminais) {

                    if (s.equals(simboloInserido)) {
                        NaoTtemSimbolo = false;
                    }
                }

                if (NaoTtemSimbolo) {
                    GerenciadorGramatica.listaTerminais.add(simboloInserido);
                    String texto = "Símbolos terminais:";
                    
                    for (String s : GerenciadorGramatica.listaTerminais) {

                        texto += " " + s;
                    }

                    stringTerminal.setText(texto);
                }

            }

        });

        ObservableList<LinhaTabela> conteudo;

        conteudo = FXCollections.observableArrayList(
                new LinhaTabela("", "->", ""));

        table.setItems(conteudo);
    }

}
