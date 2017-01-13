/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.dao.Impl;

import com.douwe.banque.dao.Connectionfactory;
import com.douwe.banque.dao.Impl.UsersDaoImpl;
import com.douwe.banque.dao.IoperationsDao;
import com.douwe.banque.dao.IusersDao;
import com.douwe.banque.data.Account;
import com.douwe.banque.data.Operations;
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
public class OperationsDaoImpl implements IoperationsDao{

    private Connection conn;
//    private IoperationsDao ioperationsDao = new OperationsDaoImpl();
    private IusersDao iusersDao = new UsersDaoImpl();
    public OperationsDaoImpl(){
        this.conn = Connectionfactory.getConnection();
    }
    public int create(Operations operations) {
        try {
            PreparedStatement p=conn.prepareStatement("insert into operations (account_id, user_id, dateOperation, description, operationType) values (?,?,?,?,?)");
            p.setInt(1, operations.getAccount().getId());
            p.setInt(2, operations.getUsers().getId());
            p.setDate(3, (Date) operations.getDateOperation());
            p.setString(4, operations.getDescription());
            p.setInt(5, operations.getOperationType().ordinal());
            p.executeUpdate();
            return 1;
        } 
        catch (SQLException ex) {
            Logger.getLogger(OperationsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public void update(Operations operations) {
        try {
            PreparedStatement p=conn.prepareStatement("update operations (account_id = ?, user_id =?, dateOperation=?, description=?, operationType=?) where id =?");
            p.setInt(1, operations.getAccount().getId());
            p.setInt(2, operations.getUsers().getId());
            p.setDate(3, (Date) operations.getDateOperation());
            p.setString(4, operations.getDescription());
            p.setInt(5, operations.getOperationType().ordinal());
            p.setInt(6, operations.getId());

            p.executeUpdate();
        } 
        catch (SQLException ex) {
            Logger.getLogger(OperationsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement p=conn.prepareStatement("delete from operations where id = ?");
            p.setInt(1,id);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OperationsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Operations findById(int id) {
        try {
            PreparedStatement p=conn.prepareStatement("select * from operations where id = ?");
            p.setInt(1,id);
            ResultSet r=p.executeQuery();
            Operations operation = new Operations();             
            operation.setId(r.getInt("id"));
            operation.setDescription(r.getString("description"));
            operation.setDateOperation(r.getDate("dateOperation"));
            operation.setId(r.getInt("id"));
            operation.setUsers(iusersDao.findById(r.getInt("user_id")));
            operation.setDescription(r.getString("description"));      
            return operation;
        } catch (SQLException ex) {
            return null;
        }

    }

    public List<Operations> FindAll() {
         try {
            List<Operations> operations = new LinkedList<Operations>();
            PreparedStatement p=conn.prepareStatement("select * from operations ");
            ResultSet r=p.executeQuery();
            while(r.next()){
                 Operations operation = new Operations();             
                 operation.setId(r.getInt("id"));
                 operation.setDescription(r.getString("description"));
                 operation.setDateOperation(r.getDate("dateOperation"));
                 operation.setId(r.getInt("id"));
                 operation.setUsers(iusersDao.findById(r.getInt("user_id")));
                 operation.setDescription(r.getString("description"));

                 operations.add(operation);
            }
            return operations;
        } catch (SQLException ex) {
            return null;
        }
    
    }
    
}
