/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.airquality.service;

import com.sg.airquality.dao.Dao;
import com.sg.airquality.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 16265
 */
@Service
public class ServiceLayer {
    
    @Autowired
    Dao dao;
    
    
    
    // Ferry data form dao
    public Account getAccount(String username){
        return dao.getAccount(username);
    }
    
    
    
    public void setDefaultCity(String username, String defaultCity){
        
        dao.setDefaultCity(username, defaultCity);
        
    }
    
    
    
    // True if username exists and pswd correct. False otherwise. Set the flag if error
    public boolean verifyLogin(Account a){
        
        Account actual;
        
        // Verify that the username exists in the database
        try{
            actual = dao.getAccount(a.getUsername());
        } catch(Exception e){
            a.setFlagNoAccountFound(true);
            return false;
        }
        
        
        // If password not correct, set flag and return false
        if( !a.getPassword().equals(actual.getPassword()) ){
            a.setFlagWrongPassword(true);
            return false;
        }
        
        
        
        // Everything checkso ut, so return true
        return true;
        
  
    }
    
    
    
    // Set error flags if there is something wrong with suggested usernamea and password
    public void setCreateAccountFlags(Account a){
        
        Account actual;
        
        // If this account already exists in the database, set the error flag forthat    
        try{
            actual = dao.getAccount(a.getUsername());
        } catch(Exception e){

            // No account found, which is good. Return without setting anything
            return;
        }
        
        

                
                
                
        // An account has been found. Set the flag and return
        a.setFlagNonuniqueUsername(true);
        
        
    }
    
    
    
    
    // Create an account
    public void createAccount(Account a){
        dao.createAccount(a);
    }
    
    
    
    
    
    
    
    
    
    
    
} // end of servicelayer
