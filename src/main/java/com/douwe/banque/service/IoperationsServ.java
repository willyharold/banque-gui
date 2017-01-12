/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.service;

import com.douwe.banque.data.Operations;
import java.util.List;

/**
 *
 * @author harold
 */
public interface IoperationsServ {
  
    public int create(Operations operations);
   
   public void update(Operations operations);
   
   public void delete(int id);
   
   public Operations findById(int id);
  
   public List<Operations> FindAll();
}
