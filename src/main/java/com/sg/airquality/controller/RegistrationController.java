/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.airquality.controller;

import static com.sg.airquality.controller.BaseController.violations;
import com.sg.airquality.entity.Account;
import com.sg.airquality.service.ServiceLayer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author 16265
 */
@Controller
public class RegistrationController {
    
    @Autowired 
    ServiceLayer s;    
    
    
    static String username; 
    static String password;  
    
    static Set<ConstraintViolation< Account >> violations = new HashSet<>();
    
    
    
    // Load the page for registration
    @GetMapping("registration")
    public String registration(HttpServletRequest request, Model model){
        

        // Load information for the form (if there is any)
        model.addAttribute( "username", username );
        model.addAttribute( "password", password );
        
        // Load violations (if there are any)
        model.addAttribute("errors", violations);
        

        return "registration";              // Refresh the page
    }
    
    
    
    
    
    
    
    // Attempt to create a user account
    @PostMapping("registration")
    public String registration2(HttpServletRequest request, Model model){
        
        // Pull in the candidate username and password. Put them in account object
        username = request.getParameter("username");
        password = request.getParameter("password");
        Account a = new Account(username, password);
        
        // Pass to service layer to set account error flags (just one to make sure that this is a unique acocunt username)        
        s.setCreateAccountFlags(a);          
        
        // Verify that the account is legitimate
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(a);
        
        
        // If no errors, create the account and send the user to their logged in page
        if(violations.isEmpty()){
            
            // Create the account
            s.createAccount(a);
            
            // Put the account information for the html page to use (in url, append the username and password)
            model.addAttribute( "account", a );
            LoggedInController.username = username;
            
            
            // Clear the model that loggedInDefault uses
            LoggedInController.searchResultsNames = new ArrayList<String>();
            LoggedInController.co = "";
            LoggedInController.h = "";
            LoggedInController.no2 = "";
            LoggedInController.o3 = "";
            LoggedInController.p = "";
            LoggedInController.pm10 = "";
            LoggedInController.pm25 = "";
            LoggedInController.so2 = "";
            LoggedInController.t = "";
            LoggedInController.w = "";
            
            
            return "redirect:/loggedInDefault?uname=" + username;      
        }
        
        
        
        
        
        
        // Otherwise, there are errors. Refresh the page and load the errors. 
        return "redirect:/registration";              // Refresh the page     like return "redirect:/editSuperhero?id=" + name;
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
