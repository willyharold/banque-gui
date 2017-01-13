/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.dao.Impl;

import com.douwe.banque.dao.Connectionfactory;
import com.douwe.banque.dao.IusersDao;
import com.douwe.banque.data.RoleType;
import com.douwe.banque.data.Users;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UsersDaoImpl implements IusersDao {

    private Connection conn;

    public UsersDaoImpl() {
        this.conn = Connectionfactory.getConnection();
    }

    
    public int create(Users user) {
        if(user.getRole()==null)
            user.setRole(RoleType.values()[0]);
        if(user.getStatut()==null)
            user.setStatut(0);
        try {
            PreparedStatement p=conn.prepareStatement("insert into users (username, passwd, status, role) values (?,?,?,?)");
            p.setString(1, user.getUsername());
            p.setString(2, user.getPasswd());
            p.setInt(3, user.getStatut());
            p.setInt(4, user.getRole().ordinal());
            p.executeUpdate();
            return 1;
        } 
        catch (SQLException ex) {
            Logger.getLogger(UsersDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public void update(Users user) {
        try {
            PreparedStatement p=conn.prepareStatement("update users (username=?, passwd=?, status=?, role=?) where id=?");
            p.setString(1, user.getUsername());
            p.setString(2, user.getPasswd());
            p.setInt(3, user.getStatut());
            p.setInt(4, user.getRole().ordinal());
            p.setInt(5, user.getId());
            p.executeUpdate();
        } 
        catch (SQLException ex) {
            Logger.getLogger(UsersDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void delete(int id) {
        try {
            PreparedStatement p=conn.prepareStatement("delete from users where id = ?");
            p.setInt(1,id);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Users findById(int id) {
        
        try {
            PreparedStatement p = conn.prepareStatement("select * from users where id = ?");
            p.setInt(1, id);
            ResultSet r=p.executeQuery();
            Users user = new Users();
            user.setId(r.getInt("id"));
            user.setPasswd(r.getString("passwd"));
            user.setRole(RoleType.values()[r.getInt("role")]);
            user.setStatut(r.getInt("status"));
            user.setUsername(r.getString("username"));
            return user;
        } catch (SQLException ex) {
            return null;
        }

    }
    
     public Users findByUsername(String username) {
        
        try {
            PreparedStatement p = conn.prepareStatement("select * from users where username = ?");
            p.setString(1, username);
            ResultSet r=p.executeQuery();
            Users user = new Users();
            user.setId(r.getInt("id"));
            user.setPasswd(r.getString("passwd"));
            user.setRole(RoleType.values()[r.getInt("role")]);
            user.setStatut(r.getInt("status"));
            user.setUsername(r.getString("username"));
            return user;
        } catch (SQLException ex) {
            return null;
        }

    }

    public List<Users> FindAll() {
        try {
            List<Users> users = new LinkedList<Users>();
            PreparedStatement p = conn.prepareStatement("select * from users ");
            ResultSet r=p.executeQuery();
            while(r.next()){
                Users user = new Users();
                user.setId(r.getInt("id"));
                user.setPasswd(r.getString("passwd"));
                user.setRole(RoleType.values()[r.getInt("role")]);
                user.setStatut(r.getInt("status"));
                user.setUsername(r.getString("username"));
                users.add(user);
            }
            return users;
        } catch (SQLException ex) {
            return null;
        }
    }
    
}
