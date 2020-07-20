/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.guidolin.davide.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author david
 */
public class DBConnection {
    
    String dbUrl = "jdbc:derby://localhost:1527/FBLiteDB";
    String dbUser = "fbLite";
    String dbPass = "fbLite";
    Connection conn = null;
    
    public DBConnection(){
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        
    }
    
    public Connection getConnection(){
        try{
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return conn;
    }
    
}
