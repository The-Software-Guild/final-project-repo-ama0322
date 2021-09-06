/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.airquality.dao;

import com.sg.airquality.entity.Account;
import java.util.List;

/**
 *
 * @author 16265
 */
public interface Dao {
    
    
    
    public void setDefaultCity(String username, String defaultCity);
    
    // Create an account. Account must have unique name (Check in service layer).      14 values in total             
    public void createAccount(Account a);

    // Store settings of an account
    public void updateSettings(Account a);

    
    // Get an account (from username)
    public Account getAccount(String username);
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
