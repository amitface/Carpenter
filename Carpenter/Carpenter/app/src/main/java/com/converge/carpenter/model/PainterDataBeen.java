package com.converge.carpenter.model;

/**
 * Created by JugalJaiswal on 5/14/2016.
 */
public class PainterDataBeen
{
    String painter_name,mobile_no,age,address,state,thesil,district,pincode,native_loc,exp,distributor,rid,routeid,p_id_number;
    String p_identity,date_time,latitude,longitude,painter_type;

    public PainterDataBeen()
    {

    }

    public PainterDataBeen(String painter_type, String painter_name,String mobile_no,String age,String address,
                           String state,String thesil,String district,String pincode,String
            native_loc,String exp,String distributor,String p_identity,String p_id_number,String latitude, String longitude, String date_time, String routeid, String rid)
    {

        this.painter_type = painter_type;
        this.painter_name = painter_name;
        this.mobile_no = mobile_no;
        this.age = age;
        this.address = address;
        this.state = state;
        this.thesil = thesil;
        this.district = district;
        this.pincode = pincode;
        this.native_loc = native_loc;
        this.exp = exp;
        this.distributor = distributor;
        this.rid = rid;
        this.routeid = routeid;
        this.p_id_number = p_id_number;
        this.p_identity = p_identity;
        this.date_time = date_time;
        this.latitude = latitude;
        this.longitude = longitude;


    }

    public String getPainter_type() {
        return painter_type;
    }

    public void setPainter_type(String painter_type) {
        this.painter_type = painter_type;
    }

    public String getPainter_name() {
        return painter_name;
    }

    public void setPainter_name(String painter_name) {
        this.painter_name = painter_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getThesil() {
        return thesil;
    }

    public void setThesil(String thesil) {
        this.thesil = thesil;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getNative_loc() {
        return native_loc;
    }

    public void setNative_loc(String native_loc) {
        this.native_loc = native_loc;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRouteid() {
        return routeid;
    }

    public void setRouteid(String routeid) {
        this.routeid = routeid;
    }

    public String getP_id_number() {
        return p_id_number;
    }

    public void setP_id_number(String p_id_number) {
        this.p_id_number = p_id_number;
    }

    public String getP_identity() {
        return p_identity;
    }

    public void setP_identity(String p_identity) {
        this.p_identity = p_identity;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
