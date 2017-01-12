/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.dao;

import com.douwe.banque.data.Account;
import com.douwe.banque.data.AccountType;
import java.util.List;

/**
 *
 * @author harold
 */
public interface IaccountDao {
   
   public void create(Account account);
   
   public void update(Account account);
   
   public void delete(int id);
   
   public Account findById(int id);
   
   public Account findByAccountNumber(String accountNumber);
  
   public List<Account> FindAll();
   
   public List<Account> findByCustomer(int statut, String client, AccountType ty, String accountNumber);
   
   public Account findByAccount(int id);

}
