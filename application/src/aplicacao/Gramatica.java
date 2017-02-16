/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date   February 08,2017
 */
public class Gramatica {
    
    public static final String[] lista = new String[26];
    
    
    private HashMap<Integer, String> naoTerminais = new HashMap<>();
    private HashMap<String, ArrayList<String>> regra = new HashMap<>();
    private String regraInicial = "";

    public Gramatica() {
        
        lista[0] = "S";
        for (int i = 1; i < 26; i++) {
            if (64 + i < (int) 'S') {
                lista[i] = ""+ (char) (64 + i);
            }
            else lista[i] = ""+ (char) (65 + i);
            
        }
    }
    
    public boolean create (int valor, String chave) {
        
        regra.put(chave, new ArrayList<>());
        for (int i = 0; i < naoTerminais.size(); i++) {
            if (((String) naoTerminais.get(i)).equals(chave)) {
                return false;
            }
        }
        
        naoTerminais.put(valor, chave); return true;
    }
    
    public String get(Integer num){
        return naoTerminais.get(num);
    }

    public void add (String regra, String valor) {
        
        for (String s: this.regra.get(regra)) {
            
            if (s.equals(valor)) {
                return;
            }
        }
        
        this.regra.get(regra).add(valor);
    }
    
    public void remove (String regra, String valor) throws Exception {
        if (!this.regra.get(regra).remove(valor)) throw new Exception("Element not found!");
    }
    
    public void setInitial (int num) {
        this.regraInicial = this.get(num);
    }

    public HashMap<String, ArrayList<String>> getRegra() {
        return regra;
    }

    public String getRegraInicial() {
        return regraInicial;
    }

    public HashMap<Integer, String> getNaoTerminais() {
        return naoTerminais;
    }
    
    
}
