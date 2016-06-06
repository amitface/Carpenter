package com.converge.carpenter.model;

/**
 * Created by JugalJaiswal on 5/14/2016.
 */
public class EventImageBeen
{

    String routeid,rid,date,lat,longi,evimg_name,evimg_data;

    public EventImageBeen(){}

    public String getRouteid() {
        return routeid;
    }

    public void setRouteid(String routeid) {
        this.routeid = routeid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getEvimg_name() {
        return evimg_name;
    }

    public void setEvimg_name(String evimg_name) {
        this.evimg_name = evimg_name;
    }

    public String getEvimg_data() {
        return evimg_data;
    }

    public void setEvimg_data(String evimg_data) {
        this.evimg_data = evimg_data;
    }

    public EventImageBeen(String routeid,String rid,String date,String lat, String longi, String evimg_name, String evimg_data)
    {
        this.routeid = routeid;
        this.rid = rid;
        this.date = date;
        this.lat = lat;
        this.longi = longi;
        this.evimg_name = evimg_name;
        this.evimg_data = evimg_data;



    }


}
