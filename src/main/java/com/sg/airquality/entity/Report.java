/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.airquality.entity;

import java.util.Date;
import java.util.List;

/**
 *
 * Object form of json query response                             
 */
public class Report {
    
    public String status;
    public Data data;
    
    
    
}





class Attribution{
    public String url;
    public String name;
    public String logo;
}

class City{
    public List<Double> geo;
    public String name;
    public String url;
}




















class Time{
    public String s;
    public String tz;
    public int v;
    public Date iso;
}

class Uvi{
    public int avg;
    public String day;
    public int max;
    public int min;
}

class Daily{
    public List<O3> o3;
    public List<Pm10> pm10;
    public List<Pm25> pm25;
    public List<Uvi> uvi;
}

class Forecast{
    public Daily daily;
}

class Debug{
    public Date sync;
}






















