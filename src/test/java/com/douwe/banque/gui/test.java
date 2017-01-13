/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.gui;

import com.douwe.banque.dao.Impl.OperationsDaoImpl;
import com.douwe.banque.dao.IoperationsDao;
import com.douwe.banque.data.Operation;
import com.douwe.banque.data.Operations;
import com.douwe.banque.data.RoleType;
import com.douwe.banque.data.Users;
import com.douwe.banque.service.IaccountServ;
import com.douwe.banque.service.Impl.AccountServImpl;
import com.douwe.banque.service.Impl.IoperationsServImpl;
import com.douwe.banque.service.Impl.IusersServImpl;
import com.douwe.banque.service.IoperationsServ;
import com.douwe.banque.service.IusersServ;
import java.sql.Connection;
import java.sql.Date;
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
            
        Users users = new Users();
        IusersServ iusersServ = new IusersServImpl();
        users.setUsername("willy");
        users.setPasswd("admin");
        users.setRole(RoleType.admin);
        IoperationsServ ioperationsServ = new IoperationsServImpl();
        IaccountServ iaccountServ = new AccountServImpl();
        Operations operations = new Operations();
        operations.setOperationType(Operation.ajout);
        operations.setDateOperation(new Date(new java.util.Date().getTime()));
        operations.setDescription("Ajout du client willy");
        operations.setAccount(iaccountServ.findById(1));
        operations.setUsers(iusersServ.findById(1));
        ioperationsServ.create(operations);
        
        System.out.println("reussi");
        
    }

}
