/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.dao.Impl;

import com.douwe.banque.dao.IaccountDao;
import com.douwe.banque.data.Account;
import com.douwe.banque.dao.Connectionfactory;
import com.douwe.banque.dao.IcustomerDao;
import com.douwe.banque.data.AccountType;
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
public class AccountDaoImpl implements IaccountDao {

    private Connection conn;
    
    private IaccountDao accountDao;

//    public AccountDaoImpl() {
//        this.conn = Connectionfactory.getConnection();
//    }
    private IcustomerDao icustomerDao = new CustomerDaoImpl();

    public IaccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(IaccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public AccountDaoImpl() {
        this.conn = Connectionfactory.getConnection();
    }
    
    public void create(Account account) {
        try {
            PreparedStatement p=conn.prepareStatement("insert into account (statut, type, accountNumber, balance, phoneNumber,customer_id, dateCretation) values (?,?,?,?,?,?,?)");
            p.setInt(1, account.getStatus());
            p.setInt(2, account.getType().ordinal());
            p.setString(3, account.getAccountNumber());
            p.setString(4, account.getPhoneNumber());
            p.setDouble(5, account.getBalance());
            p.setInt(6, account.getCustomer().getId());
            p.setDate(7, (Date) account.getDateCreation());
           p.executeUpdate();
        } 
        catch (SQLException ex) {
            
        } 
    }

    public void update(Account account) {
        try {
            PreparedStatement p=conn.prepareStatement("update account (statut =?, type=?, accountNumber= ?, balance=?, phoneNumber=?,customer_id=?,dateCreation) where id =?");
            p.setInt(1, account.getStatus());
            p.setInt(2, account.getType().ordinal());
            p.setString(3, account.getAccountNumber());
            p.setString(5, account.getPhoneNumber());
            p.setDouble(4, account.getBalance());
            p.setInt(6, account.getCustomer().getId());
            p.setDate(7, (Date) account.getDateCreation());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement p=conn.prepareStatement("delete from account where id = ?");
            p.setInt(1,id);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Account findById(int id) {
        try {
            PreparedStatement p=conn.prepareStatement("select * from account where id = ?");
            p.setInt(1,id);
            ResultSet r=p.executeQuery();
            Account account = new Account();
            while(r.next()){
                 account.setId(r.getInt("id"));
                 account.setAccountNumber(r.getString("accountNumber"));
                 account.setBalance(r.getDouble("balance"));
                 account.setDateCreation(r.getDate("dateCreation"));
                 account.setCustomer(icustomerDao.findById(r.getInt("customer_id")));
                 account.setPhoneNumber(r.getString("phoneNumber"));
                 account.setStatus(r.getInt("statut"));
                 account.setType(AccountType.values()[r.getInt("type")]);
                   }
            return account;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Account> FindAll() {
        try {
            List<Account> accounts = new LinkedList<Account>();
            PreparedStatement p=conn.prepareStatement("select * from account ");
            ResultSet r=p.executeQuery();
            while(r.next()){
                 Account account = new Account();             
                 account.setId(r.getInt("id"));
                 account.setAccountNumber(r.getString("accountNumber"));
                 account.setBalance(r.getDouble("balance"));
                 account.setDateCreation(r.getDate("dateCreation"));
                 account.setCustomer(icustomerDao.findById(r.getInt("customer_id")));
                 account.setPhoneNumber(r.getString("phoneNumber"));
                 account.setStatus(r.getInt("statut"));
                 account.setType(AccountType.values()[r.getInt("type")]);
                 accounts.add(account);
            }
            return accounts;
        } catch (SQLException ex) {
            return null;
        }
    }

    public Account findByAccountNumber(String accountNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Account> findByCustomer(int statut, String client, AccountType ty, String accountNumber) {
       
        List<Account> accounts = new LinkedList<Account>();
        StringBuilder query = new StringBuilder("select account.*, customer.name from account, customer where customer.id = account.customer_id and account.status = ?");
        if ((client != null) && !("".equals(client))) {
            query.append("and name like '%");
            query.append(client);
            query.append("%'");
        }
        if ((accountNumber != null) && !("".equals(accountNumber))) {
            query.append("and accountNumber like '%");
            query.append(client);
            query.append("%'");
        }
        if (ty != null) {
            query.append("and type = ");
            query.append(ty.ordinal());
        }
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query.toString());
            st.setInt(1, statut);
            ResultSet r = st.executeQuery();
            Account account = new Account();
            while(r.next()){
                 account.setId(r.getInt("id"));
                 account.setAccountNumber(r.getString("accountNumber"));
                 account.setBalance(r.getDouble("balance"));
                 account.setDateCreation(r.getDate("dateCreation"));
                 account.setCustomer(icustomerDao.findById(r.getInt("customer_id")));
                 account.setPhoneNumber(r.getString("phoneNumber"));
                 account.setStatus(r.getInt("statut"));
                 account.setType(AccountType.values()[r.getInt("type")]);
                 accounts.add(account);
            }
            return accounts;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
               
    }
    
    public Account findByAccount(int id) {
        PreparedStatement p;
        try {
            p = conn.prepareStatement("select account.*, customer.name  from account, customer where account.id = ? and account.customer_id = customer.id");
            p.setInt(1,id);
            ResultSet r=p.executeQuery();
            Account account = new Account();
            while(r.next()){
                 account.setId(r.getInt("id"));
                 account.setAccountNumber(r.getString("accountNumber"));
                 account.setBalance(r.getDouble("balance"));
                 account.setDateCreation(r.getDate("dateCreation"));
                 account.setCustomer(icustomerDao.findById(r.getInt("customer_id")));
                 account.setPhoneNumber(r.getString("phoneNumber"));
                 account.setStatus(r.getInt("statut"));
                 account.setType(AccountType.values()[r.getInt("type")]);
                   }
            return account;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
}
