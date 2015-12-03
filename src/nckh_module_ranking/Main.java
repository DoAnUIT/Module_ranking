/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nckh_module_ranking;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Minh Nhat
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Module_ranking ranking = new Module_ranking();
        ranking.XMLParser();
        while(true){
            try {
                System.out.println("Ranking start");
                ranking.clean_ranking();
                ranking.start_ranking();
                System.out.println("Ranking End");
                Thread.sleep((long)ranking.sleep_time);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
