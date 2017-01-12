/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.service.Impl;

import com.douwe.banque.dao.Impl.OperationsDaoImpl;
import com.douwe.banque.dao.IoperationsDao;
import com.douwe.banque.data.Operations;
import com.douwe.banque.service.IoperationsServ;
import java.util.List;


public class IoperationsServImpl implements IoperationsServ {

    private IoperationsDao ioperationsDao = new OperationsDaoImpl();
    
    public int create(Operations operations) {
        return ioperationsDao.create(operations);
    }

    public void update(Operations operations) {
        ioperationsDao.update(operations);
    }

    public void delete(int id) {
        ioperationsDao.delete(id);
    }

    public Operations findById(int id) {
        return ioperationsDao.findById(id);
    }

    public List<Operations> FindAll() {
        return ioperationsDao.FindAll();
        }
    
}
