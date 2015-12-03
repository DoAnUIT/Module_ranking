/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nckh_module_ranking;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Minh Nhat
 */
public class Module_ranking {
    private int period_time = 0;
    public float sleep_time = 0;
    Connection conn = null;
    public void XMLParser(){
    SAXBuilder builder = new SAXBuilder();
    File xmlFile = new File("config.xml");

        try {

            org.jdom.Document document = (org.jdom.Document) builder.build(xmlFile);
            Element node = document.getRootElement();

            period_time = Integer.parseInt(node.getChildText("period_time"));
            sleep_time = Float.parseFloat(node.getChildText("sleep_time")) * 1000 * 3600;
            
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }

public void start_ranking(){

        try {
            try {
                // TODO code application logic here
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Module_ranking.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nckh_hot_news?characterEncoding=UTF-8",
                    "root","rootmysql!@3");
            
            CallableStatement call = conn.prepareCall("{call start_ranking(?)}");
            call.setInt(1, period_time);
            call.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Module_ranking.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(conn != null)
                try {
                    conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Module_ranking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

public void clean_ranking(){

        try {
            try {
                // TODO code application logic here
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Module_ranking.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nckh_hot_news?characterEncoding=UTF-8",
                    "root","rootmysql!@3");
            
            CallableStatement call = conn.prepareCall("{call clean_ranking()}");
            call.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Module_ranking.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(conn != null)
                try {
                    conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Module_ranking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
    
