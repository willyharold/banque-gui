/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.gui;

import com.douwe.banque.dao.Impl.OperationsDaoImpl;
import com.douwe.banque.dao.IoperationsDao;
import com.douwe.banque.data.Operations;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harold
 */
public class test {
    private static Connection conn;
    public static void main(String[] args){
      
            //IoperationsDao io = new OperationsDaoImpl();
            
        List<Operations> op = new LinkedList<Operations>();
        IoperationsDao ioperationsDao = new OperationsDaoImpl();
        op = ioperationsDao.FindAll();
        for(Operations r:op){
                System.out.println(r.getDescription());
        }
        
    }

}
