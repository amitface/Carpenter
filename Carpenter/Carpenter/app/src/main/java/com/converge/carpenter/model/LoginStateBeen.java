package com.converge.carpenter.model;

/**
 * Created by JugalJaiswal on 5/22/2016.
 */
public class LoginStateBeen
{
    String sr,state,district,tehsil,uname, name, mobile;

    public LoginStateBeen()
    {}

    public LoginStateBeen(String sr, String state, String district, String tehsil, String uname, String name, String mobile) {
        this.sr = sr;
        this.state = state;
        this.district = district;
        this.tehsil = tehsil;
        this.uname = uname;
        this.name = name;
        this.mobile = mobile;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getUname() {
        return uname;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
