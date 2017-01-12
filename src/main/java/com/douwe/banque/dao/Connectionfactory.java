/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public final class Connectionfactory {
   private static final Connection conn=create();  
   public static Connection create(){
       try {
           Class.forName("org.sqlite.JDBC");
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Connectionfactory.class.getName()).log(Level.SEVERE, null, ex);
       }
  
       try {
           return DriverManager.getConnection("jdbc:sqlite:banque.db");
       } catch (SQLException ex) {
          return null;
       }
   } 
   public static Connection getConnection(){
       return conn;
   }
   
}