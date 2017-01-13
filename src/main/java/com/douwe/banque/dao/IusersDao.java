/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.dao;

import com.douwe.banque.data.Users;
import java.util.List;

/**
 *
 * @author harold
 */
public interface IusersDao {
   public int create(Users user);
   
   public void update(Users user);
   
   public void delete(int id);
   
   public Users findById(int id);
  
   public List<Users> FindAll();
   
   public Users findByUsername(String username);
}
