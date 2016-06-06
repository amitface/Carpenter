package com.converge.carpenter.model;

/**
 * Created by JugalJaiswal on 5/14/2016.
 */
public class ImageBeen
{

    String usrimg_name,usrimg_data;

    public ImageBeen()
    {

    }

    public ImageBeen(String usrimg_name, String usrimg_data)
    {
        this.usrimg_name = usrimg_name;
        this.usrimg_data = usrimg_data;
    }

    public String getUsrimg_name() {
        return usrimg_name;
    }

    public void setUsrimg_name(String usrimg_name) {
        this.usrimg_name = usrimg_name;
    }

    public String getUsrimg_data() {
        return usrimg_data;
    }

    public void setUsrimg_data(String usrimg_data) {
        this.usrimg_data = usrimg_data;
    }
}
