/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.airquality.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.airquality.entity.*;
import com.sg.airquality.service.ServiceLayer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 *
 * @author 16265
 */
@Controller
public class BaseController {
    
    @Autowired 
    ServiceLayer s;
    
    static Set<ConstraintViolation< Account >> violations = new HashSet<>();
    
    // Data for the website to load
    static Map<String, String> searchResults = new HashMap<>();       // Get url from station name
    static List<String> searchResultsNames = new ArrayList<>();       // Display for search results
    static Report report;                                             // Air quality report
    static String co;
    static String h;
    static String no2;
    static String o3;
    static String p;
    static String pm10;
    static String pm25;
    static String so2;
    static String t;
    static String w;
    
    static String dataLoadError = "";                                        
    
    
    // the default landing page. Load in search information if there is any
    @GetMapping("base")
    public String displayBase(Model model){
        
        // Load information from search results and report if there are any
        model.addAttribute("searchResultsNames", searchResultsNames   );
        model.addAttribute("dataLoadError", dataLoadError);
        model.addAttribute("report", report);
        model.addAttribute("co", co);
        model.addAttribute("h", h);
        model.addAttribute("no2", no2);
        model.addAttribute("o3", o3);
        model.addAttribute("p", p);
        model.addAttribute("pm10", pm10);
        model.addAttribute("pm25", pm25);
        model.addAttribute("so2", so2);
        model.addAttribute("t", t);
        model.addAttribute("w", w);
        
        // Load violations
        model.addAttribute( "violations", violations );
        
        return "base";
    }
    
    
    
    // Fill in the Search results when user types something in
    @GetMapping("searchStations")
    public String searchStations( HttpServletRequest request ){
        
        String searchTerm = request.getParameter("searchTerm");
        
        // Get the results of the search
        searchResults = getStations(searchTerm);
        
        // Populate searchResultsNames
        searchResultsNames = new ArrayList<String>();
        searchResultsNames.addAll(searchResults.keySet());
        
        
        // if searchResultsNames is empty, then add a message
        if(searchResultsNames.size() == 0){
            searchResultsNames.add( "No results found for " + searchTerm );
        }
        
        // Successful operation, so clear all errors/violations
        dataLoadError = "";
        violations = new HashSet<>();
        
        // Reload the page to show results
        return "redirect:/base";
    }
            
    
 
    // Load the data from the selected location
    @GetMapping("loadStationReport")
    public String loadStationReport( HttpServletRequest request ){
        
        // Get the user's station selection from the dropdown menu
        String stationName = request.getParameter("stationName");
        
        
        // Get the url for that station name. Construct the http request
        String stationUrl = searchResults.get(stationName);
        String requestUrl = "https://api.waqi.info/feed/" + stationUrl + "/?token=1179ba38ad29415340ff49883e333452f0695027";
        
        
        // Get the report 
        report = getReport(requestUrl);
        
        // re order searchResultsNames so that stationName is now at the front
        searchResultsNames.remove(stationName);
        searchResultsNames.add(0, stationName);
        
        // If the report is null (data from statio ncould not be retrieved), set error
        if(report == null){
                                         
            dataLoadError = "Data could not be loaded from selected station";
            
            // Clear out report info if it's there
            co = "";
            h = "";
            no2 = "";
            o3 = "";
            p = "";
            pm10 = "";
            pm25 = "";
            so2 = "";
            t = "";
            w = "";
            
            return "redirect:/base";    
        }
        
        
        // Fill in todaysParticulates
        try{
        co = String.valueOf(report.data.iaqi.co.v) ;
        } catch(Exception e){ co = "n/a"; }
        try{
        h = String.valueOf(report.data.iaqi.h.v) ;
        } catch(Exception e){  h = "n/a"; }
        try{
        no2 = String.valueOf(report.data.iaqi.no2.v) ;
        } catch(Exception e){no2 = "n/a"; }
        try{
        o3 = String.valueOf(report.data.iaqi.o3.v) ;
        } catch(Exception e){o3 = "n/a"; }
        try{
        p = String.valueOf(report.data.iaqi.p.v) ;
        } catch(Exception e){p = "n/a"; }
        try{
        pm10 = String.valueOf(report.data.iaqi.pm10.v) ;
        } catch(Exception e){pm10 = "n/a"; }
        try{
        pm25 = String.valueOf(report.data.iaqi.pm25.v) ;
        } catch(Exception e){ pm25 = "n/a"; }
        try{
        so2 = String.valueOf(report.data.iaqi.so2.v) ;
        } catch(Exception e){ so2 = "n/a"; }
        try{
        t = String.valueOf(report.data.iaqi.t.v) ;
        } catch(Exception e){t = "n/a"; }
        try{
        w =  String.valueOf(report.data.iaqi.w.v) ;
        } catch(Exception e){ w = "n/a"; }
        
        
        
        // Successful operation, so clear all errors/violations
        dataLoadError = "";
        violations = new HashSet<>();
        
        
        // Refresh the page
        return "redirect:/base";
        
    }

    
    
    
    
    
    // Log the user in.                 TODO: Idea: Hash the username and password
    @GetMapping("logInUser")
    public String logInUser( HttpServletRequest request, Model model ){
    
    
        // Get the entered username and password. Create an account object from it                               
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");
        Account a = new Account(username, password);
        
        // Set the error flags
        s.verifyLogin(a); 
            
        // Validate the login
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(a);

        
        // If there are errors, refresh the page and display errors
        if(!violations.isEmpty()) {
            return "redirect:/base";
            
        }
            
        
        // Put the account information for the html page to use (in url, append the username and password)
        model.addAttribute( "account", a );
        
        // Else no errors, so log the user in. Set LoggInController's defaultCity variable and username variable
        LoggedInController.defaultCity = s.getAccount(a.getUsername()).getDefaultCity();
        LoggedInController.username = a.getUsername();
        
        // Clear the model that loggedInDefault uses
        model.addAttribute("searchResultsNames", ""   );
        model.addAttribute("report", "");
        model.addAttribute("co", "");
        model.addAttribute("h", "");
        model.addAttribute("no2", "");
        model.addAttribute("o3", "");
        model.addAttribute("p", "");
        model.addAttribute("pm10", "");
        model.addAttribute("pm25", "");
        model.addAttribute("so2", "");
        model.addAttribute("t", "");
        model.addAttribute("w", "");
        
        
        
        
        
        //return "redirect:/loggedInDefault?uname=" + username;
        return "redirect:/loadDefaultStationReport";
        
        
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    
    
    
    
    
    
    
     
    
    // Get the air quality report from a url. If null, print no data found. 
    public Report getReport(String url){
        
        // get the URL object
        URL wikiRequest = null;
        try {
            wikiRequest = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Open a stream with the url object
        try {
            URLConnection connection = wikiRequest.openConnection() ;
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Get the scanner from the stream
        Scanner scanner = null;
        try {
            scanner = new Scanner(wikiRequest.openStream() );
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Get the string from scanner. Turn it into Report object
        String response = scanner.useDelimiter("\\Z").next();
        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
        Report report = null;
        
        // Turn string into report object
        try {
            report = om.readValue( response, Report.class );
        } catch (JsonProcessingException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        scanner.close();
        
        
        
        // return the report object 
        return report;
        
        
    }
    
    
    
    
    // Get station name plus its url
    public Map<String, String> getStations(String query){
        
        // In query, replace all spaces with %20
        query = query.replace(" ", "%20");
        
        // Construct the url 
        String url = "https://api.waqi.info/search/?token=1179ba38ad29415340ff49883e333452f0695027&keyword=" + query;
        
        // get the URL object
        URL wikiRequest = null;
        try {
            wikiRequest = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Open a stream with the url object
        try {
            URLConnection connection = wikiRequest.openConnection() ;
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Get the scanner from the stream
        Scanner scanner = null;
        try {
            scanner = new Scanner(wikiRequest.openStream() );
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Get the string from scanner. Turn it into SearchResult object
        String response = scanner.useDelimiter("\\Z").next();
        ObjectMapper om = new ObjectMapper();
        SearchResult sr = null;
        try {
            // Turn string into search result object
            sr = om.readValue( response, SearchResult.class );
        } catch (JsonProcessingException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }


        
        
        scanner.close();
        
        
        
        // Add all the stations name to the return object
        Map <String, String> stations = new HashMap<>();
        
        
        // Go through the response
        for( Datum d: sr.data ){ 
            // Add the station name and then the url to the list 
            stations.put(d.station.name, d.station.url);  
        }
        
        
        
        
        
        // return the report object 
        return stations;
        
        
    }
    

    
    
    
    
    
}
