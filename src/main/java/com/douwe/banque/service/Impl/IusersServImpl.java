/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.banque.service.Impl;

import com.douwe.banque.dao.Impl.UsersDaoImpl;
import com.douwe.banque.dao.IusersDao;
import com.douwe.banque.data.Users;
import com.douwe.banque.service.IusersServ;
import java.util.List;


public class IusersServImpl implements IusersServ {
    private IusersDao iusersDao = new UsersDaoImpl();

    public int create(Users user) {
        return iusersDao.create(user);
    }

    public void update(Users user) {
        iusersDao.update(user);
    }

    public void delete(int id) {
        iusersDao.delete(id);
    }

    public Users findById(int id) {
        return iusersDao.findById(id);
    }

    public List<Users> FindAll() {
        return iusersDao.FindAll();
    }

    public Users findByUsername(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
