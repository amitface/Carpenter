package com.converge.carpenter.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.converge.carpenter.database.DataBaseHelper;
import com.converge.carpenter.model.LoginStateBeen;
import com.converge.carpenter.model.PainterDataBeen;
import com.converge.carpenter.others.GPSTracker;
import com.converge.carpenter.others.Singelton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by JugalJaiswal on 5/8/2016.
 */
public class PainterDetailsActivity extends AppCompatActivity
{
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;


    private Toolbar toolbar;
    private LinearLayout enchroch_ll, clean_ll, street_light_ll, leakage_ll;
    private Button com_sub_btn;
    private Button next_btn;
    private EditText sing_email;
    private EditText mobile_et;
    private EditText age_et;
    private EditText answer_et_2;
    private Spinner state_sp;
    private Spinner tehsil_sp;
    private Spinner district_sp;
    private EditText pincode_et;
    private EditText nativloc_et,exp_et,assois_et;
    private String painter_name,mobile_no,age,address,state,thesil,district,pincode,native_loc,exp,distributor,route_id,rid, lat,longi;
    private String routeid;
    private Spinner identity_sp;
    private EditText cardno_et;
    private String p_id_number;
    private String p_identity;
    private String p_arch_type;
    private GPSTracker gps;
    private String date_time;
    private double latitude;
    private double longitude;
    DataBaseHelper dataBaseHelper;
    private Spinner distributor_sp;
    String[] district_ar;
    String[] distributor_ar;
    String[] tehsil_ar;
    String[] arch_type_ar;
    public static boolean tehsil = false;

    private ArrayList<LoginStateBeen> loginStateBeenArrayList;
    private ArrayList<LoginStateBeen> loginStateBeenArrayListdistrict;
    private ArrayList<LoginStateBeen> loginStateBeenArrayListtehsil;
    private ArrayList<LoginStateBeen> loginStateBeenArrayListdistributor;
    private Spinner arch_type_sp;
    private LinearLayout tehsilet_ll;
    private EditText tehsil_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdetails_activity);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        loginStateBeenArrayList = new ArrayList<LoginStateBeen>();
        loginStateBeenArrayListdistrict = new ArrayList<LoginStateBeen>();
        loginStateBeenArrayListtehsil = new ArrayList<LoginStateBeen>();
        loginStateBeenArrayListdistributor = new ArrayList<LoginStateBeen>();

        //Intent in = getIntent();

        routeid = "null";
        rid = "null";

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ACD Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next_btn = (Button) findViewById(R.id.next_btn);
        sing_email = (EditText)findViewById(R.id.sing_email);
        mobile_et = (EditText)findViewById(R.id.mobile_et);
        age_et = (EditText)findViewById(R.id.age_et);
        answer_et_2 = (EditText)findViewById(R.id.answer_et_2);
        state_sp = (Spinner)findViewById(R.id.state_sp);
        tehsil_sp = (Spinner)findViewById(R.id.tehsil_sp);
        district_sp = (Spinner)findViewById(R.id.district_sp);
        arch_type_sp = (Spinner)findViewById(R.id.arch_type_sp);


        distributor_sp = (Spinner) findViewById(R.id.distributor_sp);
        /*tehsil_sp = (EditText)findViewById(R.id.tehsil_sp);
        district_sp = (EditText)findViewById(R.id.district_sp);*/
        pincode_et = (EditText)findViewById(R.id.pincode_et);
        nativloc_et = (EditText)findViewById(R.id.nativloc_et);
        exp_et = (EditText)findViewById(R.id.exp_et);
        assois_et = (EditText)findViewById(R.id.assois_et);
        identity_sp = (Spinner)findViewById(R.id.identity_sp);
        cardno_et = (EditText)findViewById(R.id.cardno_et);
        tehsilet_ll = (LinearLayout)findViewById(R.id.tehsilet_ll);
        tehsil_et = (EditText)findViewById(R.id.tehsil_et);

        //String[] state_ar = getApplicationContext().getResources().getStringArray(R.array.state_array);
        loginStateBeenArrayList = dataBaseHelper.getAllLoginStateData();
        loginStateBeenArrayListdistrict = dataBaseHelper.getAllLoginDistrict(loginStateBeenArrayList.get(0).getState());

        ArrayList<String> ad = new ArrayList<String>();

        ad.add("--Select District--");
        for(int i=0;i<loginStateBeenArrayListdistrict.size();i++)
        {
            /*if(i == 0)
            {
                String dis = "--Select District--";
                ad.add(dis);

            }else
            {*/
                String dis = loginStateBeenArrayListdistrict.get(i).getDistrict();
                if(!ad.contains(dis))
                {
                    ad.add(dis);
                }
            //}


            district_ar = ad.toArray(new String[ad.size()]);
        }

        /*DistrictSpinnerAdapter districtSpinnerAdapter = new DistrictSpinnerAdapter(PainterDetailsActivity.this,loginStateBeenArrayListdistrict);
        district_sp.setAdapter(districtSpinnerAdapter);*/


        /*String[] district_ar = new String[loginStateBeenArrayListdistrict.size()];
        district_ar = loginStateBeenArrayListdistrict.toArray(district_ar);*/
        setSpinner(district_ar,district_sp);

        String[] id_ar = getApplicationContext().getResources().getStringArray(R.array.id_array);
        setSpinner(id_ar, identity_sp);
        String[] type_ar = getApplicationContext().getResources().getStringArray(R.array.type_array);
        setSpinner(type_ar, arch_type_sp);

        //district_sp.setPrompt("--Select District--");
        //tehsil_sp.setPrompt("--Select Tehsil--");

        district_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String st_name = district_sp.getSelectedItem().toString();
                if (!st_name.equals("--Select District--")) {

                    loginStateBeenArrayListtehsil = dataBaseHelper.getAllLoginTehsil(st_name);
                    ArrayList<String> ad = new ArrayList<String>();

                    ad.add("--Select Tehsil--");
                    ad.add("Other");
                    for(int i=0;i<loginStateBeenArrayListtehsil.size();i++)
                    {
                        /*if(i == 0)
                        {
                            String dis = "--Select Tehsil--";
                            ad.add(dis);

                        }else {*/
                            String dis = loginStateBeenArrayListtehsil.get(i).getTehsil();
                            if (!ad.contains(dis)) {
                                ad.add(dis);
                            }
                        //}
                        tehsil_ar = ad.toArray(new String[ad.size()]);
                    }

                    //String[] tehsil_ar = loginStateBeenArrayListtehsil.toArray(new String[loginStateBeenArrayListtehsil.size()]);
                    setSpinner(tehsil_ar, tehsil_sp);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tehsil_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String st_name = tehsil_sp.getSelectedItem().toString();
                if(st_name.equals("Other"))
                {
                    tehsil = true;
                    tehsilet_ll.setVisibility(View.VISIBLE);

                }else
                {
                    tehsil=false;
                    tehsilet_ll.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        /*setSpinner(district_ar,district_sp);
        setSpinner(tehsil_ar,tehsil_sp);*/
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        date_time = df.format(c.getTime());

        gps = new GPSTracker(PainterDetailsActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            lat = String.valueOf(latitude);
            longi = String.valueOf(longitude);

            // \n is for new line

        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


        
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                painter_name = sing_email.getText().toString();
                painter_name = painter_name.replace(" ", "%20");
                mobile_no = mobile_et.getText().toString();
                age = age_et.getText().toString();
                address = answer_et_2.getText().toString();
                address = address.replace(" ", "%20");
                p_arch_type = arch_type_sp.getSelectedItem().toString();
                p_arch_type = p_arch_type.replace(" ", "%20");
                System.out.println("type"+p_arch_type);
                /*state = state_sp.getSelectedItem().toString();
                state = state.replace(" ", "%20");*/
                p_id_number = cardno_et.getText().toString();
                p_identity = identity_sp.getSelectedItem().toString();


                //district = district_sp.getSelectedItem().toString();

                /*thesil = tehsil_sp.getText().toString();
                thesil = thesil.replace(" ", "%20");
                district = district_sp.getText().toString();
                district = district.replace(" ", "%20");*/
                pincode = pincode_et.getText().toString();
                native_loc = nativloc_et.getText().toString();
                native_loc = native_loc.replace(" ", "%20");
                exp = exp_et.getText().toString();
                distributor = assois_et.getText().toString();
                distributor = distributor.replace(" ","%20");


                if(painter_name.length()>0&&mobile_no.length()>0&&age.length()>0&&address.length()>0&&pincode.length()>0&&native_loc.length()>0&&exp.length()>0&&distributor.length()>0)
                {

                    if(mobile_no.length()>9)
                    {
                        if(pincode.length()>5)
                        {
                            if(exp.length()>0 && exp.length()<=2)
                            {
                                if(age.length()>1)
                                {
                                    if(Integer.parseInt(age_et.getText().toString())>Integer.parseInt(exp_et.getText().toString()))
                                    {
                                        p_identity = identity_sp.getSelectedItem().toString();
                                        p_identity = p_identity.replace(" ", "%20");
                                        state = loginStateBeenArrayList.get(0).getState();
                                        state = state.replace(" ", "%20");

                                        district = district_sp.getSelectedItem().toString();
                                        district = district.replace(" ", "%20");
                                        if (tehsil == false) {
                                            thesil = tehsil_sp.getSelectedItem().toString();
                                            thesil = thesil.replace(" ", "%20");
                                        }


                                        if (tehsil == true) {
                                            thesil = tehsil_et.getText().toString();
                                            thesil = thesil.replace(" ", "%20");
                                        }


                                        Singelton.painter_type = p_arch_type;
                                        Singelton.painter_name = painter_name;
                                        Singelton.mobile_no = mobile_no;
                                        Singelton.age = age;
                                        Singelton.address = address;
                                        Singelton.state = state;
                                        Singelton.thesil = thesil;
                                        Singelton.district = district;
                                        Singelton.pincode = pincode;
                                        Singelton.native_loc = native_loc;
                                        Singelton.exp = exp;
                                        Singelton.distributor = distributor;
                                        Singelton.p_identity = p_identity;
                                        Singelton.p_id_number = p_id_number;
                                        Singelton.lat = lat;
                                        Singelton.longi = longi;
                                        Singelton.date_time = date_time;
                                        Singelton.routeid = routeid;
                                        Singelton.rid = rid;


                                        Intent in = new Intent(PainterDetailsActivity.this, UserImageUpload.class);
                                        //in.putExtra("painter_name",painter_name);
                                        in.putExtra("p_primg", "timestamp");
                                        in.putExtra("p_idimg", "timestamp");
                                        startActivity(in);
                                        tehsil = false;
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Expericence cannot be greater than Age.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Please Enter Correct Age.",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Please Enter Valid Exp.",Toast.LENGTH_SHORT).show();
                            }


                            //new SubmiDataTask().execute();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Please Enter Valid Pincode.",Toast.LENGTH_SHORT).show();

                        }
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter Valid Mobile No.",Toast.LENGTH_SHORT).show();
                    }


                }else
                {
                    Toast.makeText(getApplicationContext(),"Please fil all the field.",Toast.LENGTH_SHORT).show();
                }


            }
        });




    }




private void setSpinner(String[] sp_array,Spinner sp_name)
{
    ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(PainterDetailsActivity.this,android.R.layout.simple_spinner_item, sp_array);
    gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    sp_name.setAdapter(gameKindArray);
}


    private class SubmiDataTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDoalog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDoalog = new ProgressDialog(PainterDetailsActivity.this);
            progressDoalog.setMessage("loading....");
            // progressDoalog.setTitle("Waiting for connection");
            //  progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDoalog.show();
        }



        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub  12050i04022

            /*
            * "p_name="+painter_name+"&p_mobile="+mobile_no+"&p_age="+age+"&p_village="+address+"&p_state="+state+"&p_tehsil="+thesil
                    +"&p_dist="+district+"&p_pincode="+pincode+"&p_location="+native_loc+"&p_exp="+exp+"&p_distributor="+distributor
                    +"&p_identity="+p_identity+"&p_id_number="+p_id_number+"&latitude="+latitude+"&longitude="+longitude
                    +"&date_time="+date_time+"&routeid="+routeid+"&rid="+rid;
            * */


            PainterDataBeen painterDataBeen = new PainterDataBeen(p_arch_type,painter_name,mobile_no,age,address,state,thesil,district,pincode,native_loc,exp,distributor,p_identity,p_id_number,lat,longi,date_time,routeid,rid);
            dataBaseHelper.createPaintersData(painterDataBeen);
            /*String POST_URL="http://cnvg.in/newinsight/painter_reg.php?";

            //p_name, p_mobile, p_age, p_village, p_tehsil, p_dist, p_pincode, p_state, p_location, p_exp, p_distributor, p_image,
            // p_identity, p_id_image, p_id_number, latitude, longitude, date_time, routeid, rid

            //String POST_PARAMS="";
            //String POST_PARAMS= null;



            String POST_PARAMS = "p_name="+painter_name+"&p_mobile="+mobile_no+"&p_age="+age+"&p_village="+address+"&p_state="+state+"&p_tehsil="+thesil
                    +"&p_dist="+district+"&p_pincode="+pincode+"&p_location="+native_loc+"&p_exp="+exp+"&p_distributor="+distributor
                    +"&p_identity="+p_identity+"&p_id_number="+p_id_number+"&latitude="+latitude+"&longitude="+longitude
                    +"&date_time="+date_time+"&routeid="+routeid+"&rid="+rid;

            System.out.println("=========" + POST_URL + POST_PARAMS);*/


            //return Utility.sendPOST(POST_PARAMS, POST_URL);
            return "success";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.e("result here ", "" + result);
            System.out.println(result);

            progressDoalog.dismiss();

            if(result!=null)
            {

                if(result.equals("success"))
                {
                    Toast.makeText(getApplicationContext(), "Data Submitted Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(PainterDetailsActivity.this,UploadPictureActivity.class);
                    in.putExtra("p_primg","timestamp");
                    in.putExtra("p_idimg","timestamp");
                    startActivity(in);
                    finish();

                }else
                {
                    Toast.makeText(getApplicationContext(), "Data Not Submitted Sucessfully", Toast.LENGTH_SHORT).show();
                }
                /*try {
                    JSONObject js = new JSONObject(result);
                    int success = js.getInt("success");
                    if (success == 1)
                    {

                        Toast.makeText(getApplicationContext(),"Data Inserted.",Toast.LENGTH_SHORT).show();
                        String p_primg = js.getString("p_primg");
                        String p_idimg = js.getString("p_idimg");
                        Toast.makeText(getApplicationContext(),p_idimg,Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),p_primg,Toast.LENGTH_SHORT).show();

                        Intent in = new Intent(PainterDetailsActivity.this,UploadPictureActivity.class);
                        in.putExtra("p_primg",p_primg);
                        in.putExtra("p_idimg",p_idimg);
                        startActivity(in);




                    }
                    else if(success == 0)
                    {

                        Toast.makeText(getApplicationContext(),"Data Not Inserted.",Toast.LENGTH_SHORT).show();

                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(),"Unable to process.",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.e("error from json", "" + e);
                    e.printStackTrace();
                }*/


                /*try {
                    JSONObject js=new JSONObject(result);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.e("error from json", "" + e);
                    e.printStackTrace();
                }*/

            }

        }
    }

}
