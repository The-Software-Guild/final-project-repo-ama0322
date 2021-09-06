/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.airquality;

import com.sg.airquality.controller.BaseController;
import com.sg.airquality.entity.Report;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 16265
 */
public class TestClass {
    
    
    	public static void main(String[] args) {
		
            
            BaseController c = new BaseController();
            
            
            
            
            Map<String, String> m = c.getStations("new york");

            List<String> stationUrls = new ArrayList<String> (m.values());

            String name = stationUrls.get(0);
            
            String requestUrl = "https://api.waqi.info/feed/" +  name + "/?token=1179ba38ad29415340ff49883e333452f0695027";
            
            Report r = c.getReport(requestUrl);
            

            System.out.println(r);
            
	}
    
    
    
    
}
