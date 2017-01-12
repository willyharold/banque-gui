/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.dao;

import com.douwe.banque.data.Customer;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author harold
 */
public interface IcustomerDao {
   
   public int create(Customer customer);
   
   public int update(Customer customer);
   
   public int updateStatut(Customer customer);
   
   public void delete(int id);
   
   public Customer findById(int id);
  
   public List<Customer> FindAll();
   
   public List<Customer> findByName(String name);
   
   public List<Customer> findByStatuts(int id);
}
