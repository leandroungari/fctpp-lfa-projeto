/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.util.regex.*;
import desenho.Desenho;
import desenho.Vertice;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;
import jfxtras.labs.scene.layout.ScalableContentPane;


/**
 *
 * @author Leandro Ungari <leandroungari@gmail.com>
 */
public class FXMLPrincipalController implements Initializable {
    
    @FXML
    private ScalableContentPane painelDesenho;
    @FXML
    private Button verificarBotao;
    @FXML
    private TextField regraTextField;
    @FXML
    private TextField entradaTextField;
    @FXML
    private TableView table;
    @FXML
    private TableColumn naoTerminal;
    @FXML
    private TableColumn seta;
    @FXML
    private TableColumn terminal;
    
    private Pane root;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        root = painelDesenho.getContentPane();
        
        painelDesenho.setOnMousePressed(event -> {
        
            double x = event.getX();
            double y = event.getY();
            
            Vertice v = new Vertice(0, x, y, 20);
            
           
            Desenho.desenharVertice(root, v);
        });
        
        verificarBotao.setOnAction(event -> {
            
            String regra = this.regraTextField.getText();
            String entrada = this.entradaTextField.getText();
            
            boolean verif = Pattern.matches(regra, entrada);
            
            if(verif)JOptionPane.showMessageDialog(null, "deu certo");
            else JOptionPane.showMessageDialog(null, "nao deu certo");
        });
        

        
        this.inicializarTabela();
    }    
    
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
