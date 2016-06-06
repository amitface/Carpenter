package com.converge.carpenter.model;

/**
 * Created by JugalJaiswal on 5/14/2016.
 */
public class IdImageBeen
{
    String idimg_name,idimg_data;

    public IdImageBeen(){}

    public IdImageBeen(String idimg_name, String idimg_data)
    {
        this.idimg_name = idimg_name;
        this.idimg_data = idimg_data;
    }

    public String getIdimg_name() {
        return idimg_name;
    }

    public void setIdimg_name(String idimg_name) {
        this.idimg_name = idimg_name;
    }

    public String getIdimg_data() {
        return idimg_data;
    }

    public void setIdimg_data(String idimg_data) {
        this.idimg_data = idimg_data;
    }
}
