
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
  * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Leandro Ungari Cayres <leandroungari@gmail.com>
 * @date   January 30,2017
 */
public class Comparação {
    
    public static void main(String... args){
        
        Scanner original = null;
       
        try {
            File a = new File("D:\\Documentos\\normal.jflap");
            original = new Scanner(a);
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Comparação.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scanner primeiro = null;
        try {
            File b = new File("D:\\Documentos\\primeiro.jflap");
            
            primeiro = new Scanner(b);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Comparação.class.getName()).log(Level.SEVERE, null, ex);
        }
        String s1,s2;
        int linha = 1;
        
        while(original.hasNext() && primeiro.hasNext()){
            
            s1 = original.next(); s2 = primeiro.next();
            
            if (s1.length() != s2.length()) System.out.println("-> " + linha);
            
            for (int i = 0; i < s1.length() && i < s2.length(); i++){
                
                if (s1.charAt(i) != s2.charAt(i)) {
                    System.out.println(s1);
                    System.out.println(s2);
                    System.out.println(linha + " - " + i);
                }
            }
            
            linha++;
        }
    }
}
