/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.airquality.dao;

import com.sg.airquality.entity.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 16265
 */
@Repository
public class DaoDatabase implements Dao {


    @Autowired
    private JdbcTemplate jdbc;

    public JdbcTemplate getJdbc() { return jdbc; }
    
    
    
    
    
//////   Account methods   /////////////////////////////////////////////////////
    
    // Create an account. Account must have unique name (Check in service layer).      14 values in total             
    public void createAccount(Account a){
        jdbc.update( "INSERT INTO Account (username, password, defaultCity, showCo, showH, showO3, showNo2, showP, showPm10, showPm25, showSo2, showT, showW, daysToShow) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
                   a.getUsername(), a.getPassword(), 
                   a.getDefaultCity(),
                   a.isShowCo(), a.isShowH(), a.isShowNo2(), a.isShowO3(), a.isShowP(), a.isShowPm10(), a.isShowPm25(), a.isShowSo2(), a.isShowT(), a.isShowW(),
                   a.getDaysToShow()  );
    }

    // Store settings of an account
    public void updateSettings(Account a){
        jdbc.update( " UPDATE Account SET "
                + " defaultCity = \"" + a.getDefaultCity()  + "\" "
                + ", showCo = " + a.isShowCo()         
                + ", showH = " + a.isShowH()   
                + ", showO3 = " + a.isShowO3()   
                + ", showNo2 = " + a.isShowNo2()   
                + ", showP = " + a.isShowP()   
                + ", showPm10 = " + a.isShowPm10()   
                + ", showPm25 = " + a.isShowPm25()   
                + ", showSo2 = " + a.isShowSo2()   
                + ", showT = " + a.isShowT()   
                + ", showW = " + a.isShowW()   
                + ", daysToShow = " + a.getDaysToShow()
                + "  WHERE username = \"" + a.getUsername() + "\""   );
    }

    
    // Get an account (from username)
    public Account getAccount(String username){
        List<Account> a = jdbc.query( "SELECT * FROM Account WHERE username = \"" + username + "\"" , new AccountMapper() );
        return a.get(0);
    }
    
    
    
    
    // Set defaultCity given the username
    public void setDefaultCity(String username, String defaultCity){
        jdbc.update( "UPDATE Account Set defaultCity = \"" + defaultCity + "\" WHERE username = \"" + username + "\"" );
    }
    
    














    
}






class AccountMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int index ) throws SQLException {
        
        Account a = new Account(rs.getString("username"), rs.getString("password"), rs.getString("defaultCity"), 
                rs.getBoolean("showCo"), rs.getBoolean("showH"), rs.getBoolean("showNo2"), rs.getBoolean("showO3"), rs.getBoolean("showP"),
                rs.getBoolean("showPm10"), rs.getBoolean("showPm25"), rs.getBoolean("showSo2"), rs.getBoolean("showT"), rs.getBoolean("showW"),
                rs.getInt("daysToShow")  );
        return a;
        
        
    }
}








