/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.service.Impl;

import com.douwe.banque.dao.IaccountDao;
import com.douwe.banque.dao.Impl.AccountDaoImpl;
import com.douwe.banque.data.Account;
import com.douwe.banque.data.AccountType;
import java.util.List;
import com.douwe.banque.service.IaccountServ;


public class AccountServImpl implements IaccountServ {
    
    private IaccountDao iaccountDao = new AccountDaoImpl();

    public void create(Account account) {
        iaccountDao.create(account);
    }

    public int update(Account account) {
        return iaccountDao.update(account);
    }

    public void delete(int id) {
        iaccountDao.delete(id);
    }

    public Account findById(int id) {
       return  iaccountDao.findById(id);
    }

    public List<Account> FindAll() {
        return iaccountDao.FindAll();
    }

    public int updateStatut(String id) {
        Account account = new Account();
        account = iaccountDao.findByAccountNumber(id);
        if (account == null)
            return 0;
        else
        {
            account.setStatus(1);
            iaccountDao.update(account);
            return 1;
        }
    }

    public Account findByAccountNumber(String accountnumber) {
        return iaccountDao.findByAccountNumber(accountnumber);
    }

    public List<Account> findByCustomer(int statut, String client, AccountType ty, String accountNumber) {
        return iaccountDao.findByCustomer(statut, client, ty, accountNumber);
    }

    public Account findByAccount(int id) {
        return iaccountDao.findByAccount(id);
    }
    
}
