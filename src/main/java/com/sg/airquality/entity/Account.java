/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.airquality.entity;

import javax.validation.constraints.AssertFalse;

/**
 * Account associated with a username. Contains all of the user's settings such as default city 
 */
public class Account {
    
    
    
    String username;
    String password;
    
    String defaultCity = "";
    
    
    // Settings to show or not various pollutants. True by default
    boolean showCo = true;
    boolean showH = true;
    boolean showNo2 = true;
    boolean showO3 = true;
    boolean showP = true;
    boolean showPm10 = true;
    boolean showPm25 = true;
    boolean showSo2 = true;
    boolean showT = true;
    boolean showW = true;
    
    
    @AssertFalse(message = "No account with that username found")
    boolean flagNoAccountFound = false;        // error flag to set if username not found in database
    
    @AssertFalse(message = "Wrong password")
    boolean flagWrongPassword = false;         // error flag to set
            
    @AssertFalse(message = "An account already exists with that username. Select another one")
    boolean flagNonuniqueUsername = false;      // error flag to set if suggested username is not unique        
    
    
    // How many days to show in total (5 is current day plus four day forecast)      
    int daysToShow = 5;       // Default is 1. Max is five

    
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    public Account(String username, String password, String defaultCity, 
                   boolean showCo, boolean showH, boolean showNo2, boolean showO3, boolean showP, boolean showPm10, boolean showPm25, boolean showSo2, boolean showT, boolean showW, 
                   int daysToShow) {
        this.username = username;
        this.password = password;
        this.defaultCity = defaultCity;
        this.showCo = showCo;
        this.showH = showH;
        this.showNo2 = showNo2;
        this.showO3 = showO3;
        this.showP = showP;
        this.showPm10 = showPm10;
        this.showPm25 = showPm25;
        this.showSo2 = showSo2;
        this.showT = showT;
        this.showW = showW;
        this.daysToShow = daysToShow;
    }

    
    
    
    public boolean isFlagNonuniqueUsername() {
        return flagNonuniqueUsername;
    }

    public void setFlagNonuniqueUsername(boolean flagNonuniqueUsername) {
        this.flagNonuniqueUsername = flagNonuniqueUsername;
    }

    
    
    
    public boolean isFlagNoAccountFound() {
        return flagNoAccountFound;
    }

    public void setFlagNoAccountFound(boolean flagNoAccountFound) {
        this.flagNoAccountFound = flagNoAccountFound;
    }

    public boolean isFlagWrongPassword() {
        return flagWrongPassword;
    }

    public void setFlagWrongPassword(boolean flagWrongPassword) {
        this.flagWrongPassword = flagWrongPassword;
    }
    
    
    
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDefaultCity() {
        return defaultCity;
    }

    public void setDefaultCity(String defaultCity) {
        this.defaultCity = defaultCity;
    }

    public boolean isShowCo() {
        return showCo;
    }

    public void setShowCo(boolean showCo) {
        this.showCo = showCo;
    }

    public boolean isShowH() {
        return showH;
    }

    public void setShowH(boolean showH) {
        this.showH = showH;
    }

    public boolean isShowNo2() {
        return showNo2;
    }

    public void setShowNo2(boolean showNo2) {
        this.showNo2 = showNo2;
    }

    public boolean isShowO3() {
        return showO3;
    }

    public void setShowO3(boolean showO3) {
        this.showO3 = showO3;
    }

    public boolean isShowP() {
        return showP;
    }

    public void setShowP(boolean showP) {
        this.showP = showP;
    }

    public boolean isShowPm10() {
        return showPm10;
    }

    public void setShowPm10(boolean showPm10) {
        this.showPm10 = showPm10;
    }

    public boolean isShowPm25() {
        return showPm25;
    }

    public void setShowPm25(boolean showPm25) {
        this.showPm25 = showPm25;
    }

    public boolean isShowSo2() {
        return showSo2;
    }

    public void setShowSo2(boolean showSo2) {
        this.showSo2 = showSo2;
    }

    public boolean isShowT() {
        return showT;
    }

    public void setShowT(boolean showT) {
        this.showT = showT;
    }

    public boolean isShowW() {
        return showW;
    }

    public void setShowW(boolean showW) {
        this.showW = showW;
    }

    public int getDaysToShow() {
        return daysToShow;
    }

    public void setDaysToShow(int daysToShow) {
        this.daysToShow = daysToShow;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
