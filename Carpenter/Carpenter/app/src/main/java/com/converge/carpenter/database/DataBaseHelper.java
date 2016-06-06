package com.converge.carpenter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.converge.carpenter.model.EventImageBeen;
import com.converge.carpenter.model.IdImageBeen;
import com.converge.carpenter.model.ImageBeen;
import com.converge.carpenter.model.LoginBeen;
import com.converge.carpenter.model.LoginStateBeen;
import com.converge.carpenter.model.PainterDataBeen;

import java.util.ArrayList;


/**
 * Created by Pooja Dubey on 2/2/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper
{

    private SQLiteDatabase myDataBase;
    private Context myContext;

    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "carpenter";
    private static final String PAINTER_LOGIN_DATA = "painter_login_data";
    private static final String PAINTER_DATA_TABLE = "painter_data_table";
    private static final String PAINTER_IDIMAGE_TABLE = "painter_idimage_table";
    private static final String PAINTER_USERIMAGE_TABLE = "painter_userimage_table";
    private static final String PAINTER_EVENTIMAGE_TABLE = "painter_image_table";
    private static final String PAINTER_LOGIN_TABLE = "painter_login_table";

    //for login data String num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;

    private static final String NUM = "num";
    private static final String RID = "rid";
    private static final String ACT_TYP = "act_typ";
    private static final String ROUTE_DATE = "route_date";
    private static final String VILNAME = "vilname";
    private static final String TEHNAME = "tehname";
    private static final String DISNAME = "disname";
    private static final String RAND_ID = "rand_id";
    private static final String ROUTE_NAME = "route_name";
    private static final String KEY_ID = "id";


    //for painter data String painter_name,mobile_no,age,address,
    // state,thesil,district,pincode,native_loc,exp,distributor,rid,routeid,p_id_number;
    //String p_identity,date_time,latitude,longitude;

    private static final String PAINTER_TYPE = "painter_type";
    private static final String PAINTER_NAME = "painter_name";
    private static final String MOBILE_NO = "mobile_no";
    private static final String NAME = "name";
    private static final String MOBILE = "mobile";
    private static final String AGE = "age";
    private static final String ADDRESS = "address";
    private static final String STATE = "state";
    private static final String TEHSIL = "thesil";
    private static final String DISTRICT = "district";
    private static final String PINCODE = "pincode";
    private static final String NATIVE_LOC = "native_loc";
    private static final String EXP = "exp";
    private static final String DISTRIBUTOR = "distributor";
    private static final String ROUTE_ID = "routeid";
    private static final String PID_NUMBER = "p_id_number";
    private static final String P_IDENTITY = "p_identity";
    private static final String DATE_TIME = "date_time";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private static final String SR = "sr";
    private static final String UNAME = "uname";




    //FOR ID IMAGE TABLE

    private static final String PAINTER_IDIMG_NAME = "painter_idimg_name";
    private static final String PAINTER_IDIMG_DATA = "painter_idimg_data";

    //FOR ID IMAGE TABLE

    private static final String PAINTER_USERIMG_NAME = "painter_userimg_name";
    private static final String PAINTER_USERIMG_DATA = "painter_userimg_data";

    //FOR ID IMAGE TABLE

    private static final String PAINTER_EVENTIMG_NAME = "painter_eventimg_name";
    private static final String PAINTER_EVENTIMG_DATA = "painter_eventimg_data";



    //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name
    private static final String CREATE_TABLE_LOGINDATA = "CREATE TABLE "
            + PAINTER_LOGIN_DATA + "(" + NUM + " TEXT,"
            + RID + " TEXT,"
            + ACT_TYP + " TEXT,"
            + ROUTE_DATE + " TEXT,"
            + VILNAME + " TEXT,"
            + TEHNAME + " TEXT,"
            + DISNAME + " TEXT,"
            + RAND_ID + " TEXT,"
            + ROUTE_NAME + " TEXT " + ")";



    //for painter data String painter_name,mobile_no,age,address,
    // state,thesil,district,pincode,native_loc,exp,distributor,rid,routeid,p_id_number;
    //String p_identity,date_time,latitude,longitude;

    private static final String CREATE_TABLE_PAINTERDATA = "CREATE TABLE "
            + PAINTER_DATA_TABLE + "(" + PAINTER_TYPE + " TEXT,"
            + PAINTER_NAME + " TEXT,"
            + MOBILE_NO + " TEXT,"
            + AGE + " TEXT,"
            + ADDRESS + " TEXT,"
            + STATE + " TEXT,"
            + TEHSIL + " TEXT,"
            + DISTRICT + " TEXT,"
            + PINCODE + " TEXT,"
            + NATIVE_LOC + " TEXT,"
            + EXP + " TEXT,"
            + DISTRIBUTOR + " TEXT,"
            + P_IDENTITY + " TEXT,"
            + PID_NUMBER + " TEXT,"
            + LATITUDE + " TEXT,"
            + LONGITUDE + " TEXT,"
            + DATE_TIME + " TEXT,"
            + ROUTE_ID + " TEXT,"
            + RID + " TEXT " + ")";



    private static final String CREATE_TABLE_PAINTERIDIMAGE = "CREATE TABLE "
            + PAINTER_IDIMAGE_TABLE + "(" + PAINTER_IDIMG_NAME + " TEXT,"
            + PAINTER_IDIMG_DATA + " TEXT " + ")";

    private static final String CREATE_TABLE_PAINTERUSERIMAGE = "CREATE TABLE "
            + PAINTER_USERIMAGE_TABLE + "(" + PAINTER_USERIMG_NAME + " TEXT,"
            + PAINTER_USERIMG_DATA + " TEXT " + ")";


    private static final String CREATE_TABLE_PAINTEREVENTIMAGE = "CREATE TABLE "
            + PAINTER_EVENTIMAGE_TABLE + "(" + ROUTE_ID + " TEXT,"
            + RID + " TEXT,"
            + PAINTER_EVENTIMG_NAME + " TEXT,"
            + PAINTER_EVENTIMG_DATA + " TEXT,"
            + DATE_TIME + " TEXT,"
            + LATITUDE + " TEXT,"
            + LONGITUDE + " TEXT " + ")";

    private static final String CREATE_TABLE_LOGINSTATE = "CREATE TABLE "
            + PAINTER_LOGIN_TABLE + "(" + SR + " TEXT,"
            + STATE + " TEXT,"
            + DISTRICT + " TEXT,"
            + TEHSIL + " TEXT,"
            + UNAME + " TEXT,"
            + NAME + " TEXT,"
            + MOBILE + " TEXT " + ")";







    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(CREATE_TABLE_LOGINDATA);
        db.execSQL(CREATE_TABLE_PAINTERDATA);
        db.execSQL(CREATE_TABLE_PAINTEREVENTIMAGE);
        db.execSQL(CREATE_TABLE_PAINTERUSERIMAGE);
        db.execSQL(CREATE_TABLE_PAINTERIDIMAGE);
        db.execSQL(CREATE_TABLE_LOGINSTATE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_LOGINDATA);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PAINTERDATA);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PAINTEREVENTIMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PAINTERIDIMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PAINTERUSERIMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_LOGINSTATE);
        onCreate(db);
    }


    public void createLoginData(LoginBeen loginBeen)
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(NUM, loginBeen.getNum());
        values.put(RID, loginBeen.getRid());
        values.put(ACT_TYP, loginBeen.getAct_typ());
        values.put(ROUTE_DATE, loginBeen.getRoute_date());
        values.put(VILNAME, loginBeen.getVilname());
        values.put(TEHNAME, loginBeen.getTehname());
        values.put(DISNAME, loginBeen.getDisname());
        values.put(RAND_ID, loginBeen.getRand_id());
        values.put(ROUTE_NAME, loginBeen.getRoute_name());

        // insert row
        long demo = db.insert(PAINTER_LOGIN_DATA, null, values);
        System.out.println("insert====" + demo);

        db.close();
    }


    public void createLoginStateData(LoginStateBeen loginStateBeen)
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(SR, loginStateBeen.getSr());
        values.put(STATE, loginStateBeen.getState());
        values.put(DISTRICT, loginStateBeen.getDistrict());
        values.put(TEHSIL, loginStateBeen.getTehsil());
        values.put(UNAME, loginStateBeen.getUname());
        values.put(NAME,loginStateBeen.getName());
        values.put(MOBILE, loginStateBeen.getMobile());


        // insert row
        long demo = db.insert(PAINTER_LOGIN_TABLE, null, values);
        System.out.println("insert====" + demo);

        db.close();
    }


    public void createPaintersData(PainterDataBeen painterDataBeen)
    {

        //for painter data String painter_name,mobile_no,age,address,
        // state,thesil,district,pincode,native_loc,exp,distributor,rid,routeid,p_id_number;
        //String p_identity,date_time,latitude,longitude;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PAINTER_TYPE, painterDataBeen.getPainter_type());
        values.put(PAINTER_NAME, painterDataBeen.getPainter_name());
        values.put(MOBILE_NO, painterDataBeen.getMobile_no());
        values.put(AGE, painterDataBeen.getAge());
        values.put(ADDRESS, painterDataBeen.getAddress());
        values.put(STATE, painterDataBeen.getState());
        values.put(TEHSIL, painterDataBeen.getThesil());
        values.put(DISTRICT, painterDataBeen.getDistrict());
        values.put(PINCODE, painterDataBeen.getPincode());
        values.put(NATIVE_LOC, painterDataBeen.getNative_loc());
        values.put(EXP, painterDataBeen.getExp());
        values.put(DISTRIBUTOR, painterDataBeen.getDistributor());
        values.put(P_IDENTITY, painterDataBeen.getP_identity());
        values.put(PID_NUMBER, painterDataBeen.getP_id_number());
        values.put(LATITUDE, painterDataBeen.getLatitude());
        values.put(LONGITUDE, painterDataBeen.getLongitude());
        values.put(DATE_TIME, painterDataBeen.getDate_time());
        values.put(ROUTE_ID, painterDataBeen.getRouteid());
        values.put(RID, painterDataBeen.getRid());

        // insert row
        long demo = db.insert(PAINTER_DATA_TABLE, null, values);
        System.out.println("insert====" + demo);

        db.close();
    }

    public void createEventImageData(EventImageBeen eventImageBeen)
    {

        //routeid,rid,date,lat,longi,evimg_name,evimg_data;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ROUTE_ID, eventImageBeen.getRouteid());
        values.put(RID, eventImageBeen.getRid());
        values.put(DATE_TIME, eventImageBeen.getDate());
        values.put(LATITUDE, eventImageBeen.getLat());
        values.put(LONGITUDE, eventImageBeen.getLongi());
        values.put(PAINTER_EVENTIMG_NAME, eventImageBeen.getEvimg_name());
        values.put(PAINTER_EVENTIMG_DATA, eventImageBeen.getEvimg_data());


        // insert row
        long demo = db.insert(PAINTER_EVENTIMAGE_TABLE, null, values);
        System.out.println("insert====" + demo);

        db.close();
    }

    public void createIdImageData(IdImageBeen idImageBeen)
    {

        //routeid,rid,date,lat,longi,evimg_name,evimg_data;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PAINTER_IDIMG_NAME, idImageBeen.getIdimg_name());
        values.put(PAINTER_IDIMG_DATA, idImageBeen.getIdimg_data());


        // insert row
        long demo = db.insert(PAINTER_IDIMAGE_TABLE, null, values);
        System.out.println("insert====" + demo);

        db.close();
    }

    public void createUserImageData(ImageBeen imageBeen)
    {

        //routeid,rid,date,lat,longi,evimg_name,evimg_data;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAINTER_USERIMG_NAME, imageBeen.getUsrimg_name());
        values.put(PAINTER_USERIMG_DATA, imageBeen.getUsrimg_data());


        // insert row
        long demo = db.insert(PAINTER_USERIMAGE_TABLE, null, values);
        System.out.println("insert====" + demo);

        db.close();
    }



    public void updateLoginData(LoginBeen loginBeen)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NUM, loginBeen.getNum());
        values.put(RID, loginBeen.getRid());
        values.put(ACT_TYP, loginBeen.getAct_typ());
        values.put(ROUTE_DATE, loginBeen.getRoute_date());
        values.put(VILNAME, loginBeen.getVilname());
        values.put(TEHNAME, loginBeen.getTehname());
        values.put(DISNAME, loginBeen.getDisname());
        values.put(RAND_ID, loginBeen.getRand_id());
        values.put(ROUTE_NAME, loginBeen.getRoute_name());


        // insert row
        long demo = db.update(PAINTER_LOGIN_DATA, values, RID + " = ?", new String[]{loginBeen.getRid()});
        System.out.println("update ====" + demo);
        db.close();
    }


    public void updateLoginStateData(LoginStateBeen loginStateBeen)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SR, loginStateBeen.getSr());
        values.put(STATE, loginStateBeen.getState());
        values.put(DISTRICT, loginStateBeen.getDistrict());
        values.put(TEHSIL, loginStateBeen.getTehsil());
        values.put(UNAME, loginStateBeen.getUname());
        values.put(NAME, loginStateBeen.getName());
        values.put(MOBILE, loginStateBeen.getMobile());


        // insert row
        long demo = db.update(PAINTER_LOGIN_TABLE, values, SR + " = ?", new String[]{loginStateBeen.getSr()});
        System.out.println("update ====" + demo);
        db.close();
    }






    public ArrayList<LoginBeen> getAllLoginData()
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        ArrayList<LoginBeen> demolist = new ArrayList<LoginBeen>();
        String selectQuery = "SELECT  * FROM " + PAINTER_LOGIN_DATA;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LoginBeen sb = new LoginBeen();
                sb.setNum(c.getString(c.getColumnIndex(NUM)));
                sb.setRid(c.getString(c.getColumnIndex(RID)));
                sb.setAct_typ(c.getString(c.getColumnIndex(ACT_TYP)));
                sb.setRoute_date(c.getString(c.getColumnIndex(ROUTE_DATE)));
                sb.setVilname(c.getString(c.getColumnIndex(VILNAME)));
                sb.setTehname(c.getString(c.getColumnIndex(TEHNAME)));
                sb.setDisname(c.getString(c.getColumnIndex(DISNAME)));
                sb.setRand_id(c.getString(c.getColumnIndex(RAND_ID)));
                sb.setRoute_name(c.getString(c.getColumnIndex(ROUTE_NAME)));

                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }


    public ArrayList<LoginStateBeen> getAllLoginStateData()
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        ArrayList<LoginStateBeen> demolist = new ArrayList<LoginStateBeen>();
        String selectQuery = "SELECT  * FROM " + PAINTER_LOGIN_TABLE;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LoginStateBeen sb = new LoginStateBeen();
                sb.setSr(c.getString(c.getColumnIndex(SR)));
                sb.setState(c.getString(c.getColumnIndex(STATE)));
                sb.setDistrict(c.getString(c.getColumnIndex(DISTRICT)));
                sb.setTehsil(c.getString(c.getColumnIndex(TEHSIL)));
                sb.setUname(c.getString(c.getColumnIndex(UNAME)));
                sb.setName(c.getString(c.getColumnIndex(NAME)));
                sb.setMobile(c.getString(c.getColumnIndex(MOBILE)));

                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }



    public ArrayList<LoginStateBeen> getAllLoginDistrict(String state)
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        ArrayList<LoginStateBeen> demolist = new ArrayList<LoginStateBeen>();
        String selectQuery = "SELECT * FROM "+PAINTER_LOGIN_TABLE +" WHERE "+STATE+" = ? ";
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, new String[]{state});

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LoginStateBeen sb = new LoginStateBeen();
                sb.setDistrict(c.getString(c.getColumnIndex(DISTRICT)));

                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }


    public ArrayList<LoginStateBeen> getAllLoginTehsil(String district)
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        ArrayList<LoginStateBeen> demolist = new ArrayList<LoginStateBeen>();
        String selectQuery = "SELECT * FROM "+PAINTER_LOGIN_TABLE +" WHERE "+DISTRICT+" = ? ";
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, new String[]{district});

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LoginStateBeen sb = new LoginStateBeen();

                sb.setTehsil(c.getString(c.getColumnIndex(TEHSIL)));

                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }

    public ArrayList<LoginStateBeen> getAllLoginDistributor(String tehsil) {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        ArrayList<LoginStateBeen> demolist = new ArrayList<LoginStateBeen>();
        String selectQuery = "SELECT * FROM " + PAINTER_LOGIN_TABLE + " WHERE " + TEHSIL + " = ? ";
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LoginStateBeen sb = new LoginStateBeen();
                sb.setSr(c.getString(c.getColumnIndex(SR)));
                sb.setState(c.getString(c.getColumnIndex(STATE)));
                sb.setDistrict(c.getString(c.getColumnIndex(DISTRICT)));
                sb.setTehsil(c.getString(c.getColumnIndex(TEHSIL)));
                sb.setUname(c.getString(c.getColumnIndex(UNAME)));
                sb.setName(c.getString(c.getColumnIndex(NAME)));
                sb.setMobile(c.getString(c.getColumnIndex(MOBILE)));

                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }


    public ArrayList<LoginBeen> getAllLoginDataRid(String rid)
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        ArrayList<LoginBeen> demolist = new ArrayList<LoginBeen>();
        String selectQuery = "SELECT * FROM "+PAINTER_LOGIN_DATA +" WHERE "+RID+" = ? ";
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, new String[]{rid});

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LoginBeen sb = new LoginBeen();
                sb.setNum(c.getString(c.getColumnIndex(NUM)));
                sb.setRid(c.getString(c.getColumnIndex(RID)));
                sb.setAct_typ(c.getString(c.getColumnIndex(ACT_TYP)));
                sb.setRoute_date(c.getString(c.getColumnIndex(ROUTE_DATE)));
                sb.setVilname(c.getString(c.getColumnIndex(VILNAME)));
                sb.setTehname(c.getString(c.getColumnIndex(TEHNAME)));
                sb.setDisname(c.getString(c.getColumnIndex(DISNAME)));
                sb.setRand_id(c.getString(c.getColumnIndex(RAND_ID)));
                sb.setRoute_name(c.getString(c.getColumnIndex(ROUTE_NAME)));

                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }


    public ArrayList<PainterDataBeen> getAllPainerData()
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        ArrayList<PainterDataBeen> demolist = new ArrayList<PainterDataBeen>();
        String selectQuery = "SELECT  * FROM " + PAINTER_DATA_TABLE;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                PainterDataBeen sb = new PainterDataBeen();
                sb.setPainter_type(c.getString(c.getColumnIndex(PAINTER_TYPE)));
                sb.setPainter_name(c.getString(c.getColumnIndex(PAINTER_NAME)));
                sb.setMobile_no(c.getString(c.getColumnIndex(MOBILE_NO)));
                sb.setAge(c.getString(c.getColumnIndex(AGE)));
                sb.setAddress(c.getString(c.getColumnIndex(ADDRESS)));
                sb.setState(c.getString(c.getColumnIndex(STATE)));
                sb.setThesil(c.getString(c.getColumnIndex(TEHSIL)));
                sb.setDistrict(c.getString(c.getColumnIndex(DISTRICT)));
                sb.setPincode(c.getString(c.getColumnIndex(PINCODE)));
                sb.setNative_loc(c.getString(c.getColumnIndex(NATIVE_LOC)));
                sb.setExp(c.getString(c.getColumnIndex(EXP)));
                sb.setDistributor(c.getString(c.getColumnIndex(DISTRIBUTOR)));
                sb.setP_identity(c.getString(c.getColumnIndex(P_IDENTITY)));
                sb.setP_id_number(c.getString(c.getColumnIndex(PID_NUMBER)));
                sb.setLatitude(c.getString(c.getColumnIndex(LATITUDE)));
                sb.setLongitude(c.getString(c.getColumnIndex(LONGITUDE)));
                sb.setDate_time(c.getString(c.getColumnIndex(DATE_TIME)));
                sb.setRouteid(c.getString(c.getColumnIndex(ROUTE_ID)));
                sb.setRid(c.getString(c.getColumnIndex(RID)));

                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }


    public ArrayList<ImageBeen> getAllUserImageData()
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        ArrayList<ImageBeen> demolist = new ArrayList<ImageBeen>();
        String selectQuery = "SELECT  * FROM " + PAINTER_USERIMAGE_TABLE;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ImageBeen sb = new ImageBeen();
                sb.setUsrimg_name(c.getString(c.getColumnIndex(PAINTER_USERIMG_NAME)));
                sb.setUsrimg_data(c.getString(c.getColumnIndex(PAINTER_USERIMG_DATA)));

                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }

    public ArrayList<IdImageBeen> getAllIdImageData()
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        ArrayList<IdImageBeen> demolist = new ArrayList<IdImageBeen>();
        String selectQuery = "SELECT  * FROM " + PAINTER_IDIMAGE_TABLE;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                IdImageBeen sb = new IdImageBeen();
                sb.setIdimg_name(c.getString(c.getColumnIndex(PAINTER_IDIMG_NAME)));
                sb.setIdimg_data(c.getString(c.getColumnIndex(PAINTER_IDIMG_DATA)));

                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }


    public ArrayList<EventImageBeen> getAllEventImageData()
    {

        //num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name;
        ArrayList<EventImageBeen> demolist = new ArrayList<EventImageBeen>();
        String selectQuery = "SELECT  * FROM " + PAINTER_EVENTIMAGE_TABLE;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                EventImageBeen sb = new EventImageBeen();


                sb.setRouteid(c.getString(c.getColumnIndex(ROUTE_ID)));
                sb.setRid(c.getString(c.getColumnIndex(RID)));
                sb.setDate(c.getString(c.getColumnIndex(DATE_TIME)));
                sb.setLat(c.getString(c.getColumnIndex(LATITUDE)));
                sb.setLongi(c.getString(c.getColumnIndex(LONGITUDE)));
                sb.setEvimg_name(c.getString(c.getColumnIndex(PAINTER_EVENTIMG_NAME)));
                sb.setEvimg_data(c.getString(c.getColumnIndex(PAINTER_EVENTIMG_DATA)));

                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }


    public boolean isALreadHasStockDetails(String demo_id)
    {
        boolean isAdded=false;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT * FROM "+PAINTER_LOGIN_DATA +" WHERE "+RID+" = ? ";
        Cursor cursor=sqLiteDatabase.rawQuery(query, new String[]{demo_id});
        if(cursor.getCount()>0)
        {
            isAdded=true;
            Log.e("==========", "ALready Added");
        }
        else
        {
            isAdded=false;
            Log.e("=========", "ALready Not Added");
        }
        sqLiteDatabase.close();
        return isAdded;
    }

    public void deleteUsers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(PAINTER_LOGIN_DATA, null, null);
        db.delete(PAINTER_LOGIN_TABLE,null,null);
        db.close();
        Log.d("", "Deleted all user info from sqlite");
    }

    public void deletedata()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(PAINTER_DATA_TABLE, null,null);
        db.delete(PAINTER_USERIMAGE_TABLE, null, null);
        db.delete(PAINTER_IDIMAGE_TABLE, null, null);


        db.close();
        Log.d("", "Deleted all user info from sqlite");
    }

    public void deletevnimg()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows

        db.delete(PAINTER_EVENTIMAGE_TABLE, null, null);
        ;

        db.close();
        Log.d("", "Deleted all user info from sqlite");
    }



}
