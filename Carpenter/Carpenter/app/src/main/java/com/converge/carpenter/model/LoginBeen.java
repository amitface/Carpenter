package com.converge.carpenter.model;

/**
 * Created by JugalJaiswal on 5/11/2016.
 */
public class LoginBeen
{
    String num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;


    public LoginBeen(){}


    public LoginBeen(String num, String rid, String act_typ, String route_date, String vilname, String tehname, String disname, String rand_id, String route_name)
    {

        this.num = num;
        this.rid = rid;
        this.act_typ = act_typ;
        this.route_date = route_date;
        this.vilname = vilname;
        this.tehname = tehname;
        this.disname = disname;
        this.rand_id = rand_id;
        this.route_name = route_name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getAct_typ() {
        return act_typ;
    }

    public void setAct_typ(String act_typ) {
        this.act_typ = act_typ;
    }

    public String getRoute_date() {
        return route_date;
    }

    public void setRoute_date(String route_date) {
        this.route_date = route_date;
    }

    public String getVilname() {
        return vilname;
    }

    public void setVilname(String vilname) {
        this.vilname = vilname;
    }

    public String getTehname() {
        return tehname;
    }

    public void setTehname(String tehname) {
        this.tehname = tehname;
    }

    public String getDisname() {
        return disname;
    }

    public void setDisname(String disname) {
        this.disname = disname;
    }

    public String getRand_id() {
        return rand_id;
    }

    public void setRand_id(String rand_id) {
        this.rand_id = rand_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }
}
