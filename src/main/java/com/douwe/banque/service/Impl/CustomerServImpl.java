/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.service.Impl;

import com.douwe.banque.dao.IcustomerDao;
import com.douwe.banque.dao.Impl.CustomerDaoImpl;
import com.douwe.banque.data.Customer;
import com.douwe.banque.service.IcustomerServ;
import java.sql.ResultSet;
import java.util.List;


public class CustomerServImpl implements IcustomerServ {

    private IcustomerDao icustomerDao = new CustomerDaoImpl();
    
    public int create(Customer customer) {
        return icustomerDao.create(customer);
    }

    public int update(Customer customer) {
        return icustomerDao.update(customer);
    }

    public int updateStatut(int id) {
        return icustomerDao.updateStatut(icustomerDao.findById(id));
    }

    public void delete(int id) {
        icustomerDao.delete(id);
    }

    public Customer findById(int id) {
        return icustomerDao.findById(id);
    }

    public List<Customer> findAll() {
        return icustomerDao.FindAll();
    }

    public List<Customer> findByName(String name) {
        return icustomerDao.findByName(name);
    }

    public List<Customer> findByStatus(int id) {
        return icustomerDao.findByStatuts(id);
    }
    
}
