/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.dao.Impl;

import com.douwe.banque.dao.Connectionfactory;
import com.douwe.banque.dao.IcustomerDao;
import com.douwe.banque.dao.IusersDao;
import com.douwe.banque.data.Customer;
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


public class CustomerDaoImpl implements IcustomerDao {

    private Connection conn;

    public CustomerDaoImpl() {
        this.conn = Connectionfactory.getConnection();
    }
    
    IusersDao iusersDao= new UsersDaoImpl();
 
    public int create(Customer customer) {
        if(customer.getStatus()==null)
            customer.setStatus(0);
        try {
            PreparedStatement p=conn.prepareStatement("insert into customer (status, name, emailAddress, phoneNumber,user_id) values (?,?,?,?,?)");
            p.setInt(1, customer.getStatus());
            p.setInt(5, customer.getUsers().getId());
            p.setString(3, customer.getEmailAddress());
            p.setString(2, customer.getName());
            p.setString(4, customer.getPhoneNumber());

           p.executeUpdate();
           return 1;
        } 
        catch (SQLException ex) {
            Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } 
    }

    public int updateStatut(Customer customer) {
        try {
            conn.setAutoCommit(false);
            PreparedStatement psmt = conn.prepareStatement("update customer set status = ? where id = ?");
            psmt.setInt(1, 1);
            psmt.setInt(2, customer.getId());
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int update(Customer customer) {
    try {
            PreparedStatement p=conn.prepareStatement("update customer (status =?, name=?, emailAddress=?, phoneNumber=?,user_id=?) where id=?");
            p.setInt(1, customer.getStatus());
            p.setInt(5, customer.getUsers().getId());
            p.setString(3, customer.getEmailAddress());
            p.setString(2, customer.getName());
            p.setString(4, customer.getPhoneNumber());
            p.setInt(6, customer.getId());

           p.executeUpdate();
           return 1;
        } 
        catch (SQLException ex) {
           Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
           return 0;
        }    
    }
    public void delete(int id) {
    try {
            PreparedStatement p=conn.prepareStatement("delete from customer where id = ?");
            p.setInt(1,id);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Customer findById(int id) {
        try {
            PreparedStatement p=conn.prepareStatement("select * from customer where id = ?");
            p.setInt(1,id);
            ResultSet r=p.executeQuery();
            Customer customer = new Customer();
            while(r.next()){
                 customer.setId(r.getInt("id"));
                 customer.setPhoneNumber(r.getString("phoneNumber"));
                 customer.setStatus(r.getInt("status"));
                 customer.setEmailAddress(r.getString("emailAddress"));
                 customer.setName(r.getString("name"));
                 customer.setUsers(iusersDao.findById(r.getInt("user_id")));
                   }
            return customer;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Customer> FindAll() {
        try {
            List<Customer> customers = new LinkedList<Customer>();
            PreparedStatement p=conn.prepareStatement("select * from account ");
            ResultSet r=p.executeQuery();
            while(r.next()){
                 Customer customer = new Customer();             
                 customer.setId(r.getInt("id"));
                 customer.setPhoneNumber(r.getString("phoneNumber"));
                 customer.setStatus(r.getInt("status"));
                 customer.setEmailAddress(r.getString("emailAddress"));
                 customer.setName(r.getString("name"));
                 customer.setUsers(iusersDao.findById(r.getInt("user_id")));

                 customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            return null;
        }
    }

    public List<Customer> findByName(String name) {
        try {
            List<Customer> customers = new LinkedList<Customer>();
            PreparedStatement pst = conn.prepareStatement("select * from customer where status = ? and name like ?");
            pst.setInt(1, 0);
            pst.setString(2, "%" + name + "%");
            ResultSet r=pst.executeQuery();
            while(r.next()){
                 Customer customer = new Customer();             
                 customer.setId(r.getInt("id"));
                 customer.setPhoneNumber(r.getString("phoneNumber"));
                 customer.setStatus(r.getInt("status"));
                 customer.setEmailAddress(r.getString("emailAddress"));
                 customer.setName(r.getString("name"));
                 customer.setUsers(iusersDao.findById(r.getInt("user_id")));
                 customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Customer> findByStatuts(int id) {
        try {
            List<Customer> customers = new LinkedList<Customer>();
            PreparedStatement pst = conn.prepareStatement("select * from customer where status = ? ");
            pst.setInt(1, id);
            ResultSet r=pst.executeQuery();
            while(r.next()){
                 Customer customer = new Customer();             
                 customer.setId(r.getInt("id"));
                 customer.setPhoneNumber(r.getString("phoneNumber"));
                 customer.setStatus(r.getInt("status"));
                 customer.setEmailAddress(r.getString("emailAddress"));
                 customer.setName(r.getString("name"));
                 customer.setUsers(iusersDao.findById(r.getInt("user_id")));
                 customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
