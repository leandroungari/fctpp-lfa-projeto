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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 */
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
    
    
    /////////////////////////////
    // Itens para a expressão regular
    @FXML
    private TextField regraTextField;
    @FXML
    private TextField entradaTextField;
    
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
    private Button executarGramatica;
    @FXML
    private Button limparGramatica;
    @FXML
    private TextField entradaGramatica;
    
    ////////////////////////////////////
    //Itens gerais
    
    public static Vertice verticeAtual;
    public static Legenda legendaAtual;
    public static Text textoAtual;
    public static Pane painelD;
    
    public static Vertice verticeInicial;
    public static Vertice verticeFinal;
    public static Vertice verticeSobre;
    
    public static ArrayList<Vertice> lista = new ArrayList<>();
    public static ConcurrentLinkedQueue<Aresta> arestas = new ConcurrentLinkedQueue<>();
    public static ArrayList<Legenda> legendas = new ArrayList<>();
    public static Estado estado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        painelD = painelDesenho;
        
        //Executar automatos
        GerenciadorAutomatos g = new GerenciadorAutomatos();
        g.montarPainel(painelDesenho, lista, legendas);
        g.montarBotoes(normalCursor, moverCursor, inserirCursor, textoCursor);
        
        
        //Executar gramática
        GerenciadorGramatica gramatica = new GerenciadorGramatica();
        gramatica.montarGramatica(table, executarGramatica, limparGramatica, entradaGramatica);
        
        //Expressão Regular - inicio
        this.entradaTextField.setStyle("-fx-border-color:#31ef02");
        this.entradaTextField.setStyle("-fx-background-color:#31ef02");
        
        regraTextField.setOnKeyReleased(event ->{
            
            String regra = this.regraTextField.getText();
            
            if(!this.verificarSintaxeRegraRegex(regra)){
                this.labelErroER.setVisible(true);
                this.regraTextField.setStyle("-fx-border-color:#ff0f0f");
                this.entradaTextField.setStyle("-fx-background-color:#ff0f0f");
            }
            else{
                this.labelErroER.setVisible(false);
                this.regraTextField.setStyle("-fx-border:none");
                this.patternRegexERcomEntrada();
            }
            
        });
        
        entradaTextField.setOnKeyReleased(event ->{
            
            this.patternRegexERcomEntrada();
            
        });
        
        this.inicializarTabela();
    }    
 
    public void trocarCorTextField(boolean verif, TextField tf){
        
        if(verif){
            //Cor verde = #31ef02 - Correto
            tf.setStyle("-fx-border-color:#31ef02");
            tf.setStyle("-fx-background-color:#31ef02");
        }
        else {
            //Cor vermelho = #ff0f0f - Errado
            tf.setStyle("-fx-border-color:#ff0f0f");
            tf.setStyle("-fx-background-color:#ff0f0f");
        }
    }
    
    public boolean verificarSintaxeRegraRegex(String regra){
        
        char c;
        for(int i = 0; i < regra.length(); i++){
            c = regra.charAt(i);
            
            if(!Character.isLetterOrDigit(c)){
                
                if(c != '+' && c != '*' && c != '.' && c != '|' && c != '.' && c != '(' && c != ')'){
                    return false;
                }
            }
        }
        
        return(true);
    }
    
    public void patternRegexERcomEntrada(){
        
        String regra = this.regraTextField.getText();
        String entrada = this.entradaTextField.getText();
            
        try{
            Pattern.compile(regra);
            boolean verif = Pattern.matches(regra, entrada);
            this.trocarCorTextField(verif, entradaTextField);
        }catch(PatternSyntaxException e){}
        
    }
    //Expressão Regular - Fim
            
    public void inicializarTabela(){
        
        this.criarLinhaTabela();
    }
 
    public void criarLinhaTabela(){
        
        table.setEditable(true);
        naoTerminal.setCellValueFactory(new PropertyValueFactory<LinhaTabela, String>("naoTerminal"));
        seta.setCellValueFactory(new PropertyValueFactory<LinhaTabela, String>("seta"));
        terminal.setCellValueFactory(new PropertyValueFactory<LinhaTabela, String>("terminal"));
        
        naoTerminal.setCellFactory(TextFieldTableCell.forTableColumn());
        naoTerminal.setOnEditCommit(new EventHandler<CellEditEvent<LinhaTabela, String>>() {
            
            @Override
            public void handle(CellEditEvent<LinhaTabela, String> event) {
                
                ((LinhaTabela) event.getTableView().getItems().get(event.getTablePosition().getRow())).setNaoTerminal(event.getNewValue());
                Object[] t = table.getItems().toArray();
                table.getItems().clear();
                int i;
                for(i = 0; i < t.length; i++){
                    
                    if(i == t.length-1){
                        ((LinhaTabela) t[i]).setTerminal("λ");
                    }
                    
                    table.getItems().add(t[i]);
                }
                
                
                
                System.out.println();
            }        
        });
        
        
        terminal.setCellFactory(TextFieldTableCell.forTableColumn());
        terminal.setOnEditCommit(new EventHandler<CellEditEvent<LinhaTabela, String>>() {
            
            @Override
            public void handle(CellEditEvent<LinhaTabela, String> event) {
                
                ((LinhaTabela) event.getTableView().getItems().get(event.getTablePosition().getRow())).setTerminal(event.getNewValue());
                
                
                
                if(table.getSelectionModel().getSelectedIndex() == table.getItems().size() - 1){
                    table.getItems().add(new LinhaTabela("", "->", ""));
                }
            }
                
        });
        
        
        ObservableList<LinhaTabela> conteudo;
        
        conteudo = FXCollections.observableArrayList(
                new LinhaTabela("","->",""));
        
        table.setItems(conteudo);
    }
    
    
    
}
