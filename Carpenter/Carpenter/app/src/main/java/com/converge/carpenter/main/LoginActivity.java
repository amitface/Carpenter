package com.converge.carpenter.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.converge.carpenter.database.DataBaseHelper;
import com.converge.carpenter.model.LoginBeen;
import com.converge.carpenter.model.LoginStateBeen;
import com.converge.carpenter.others.SessionManager;
import com.converge.carpenter.others.Singelton;
import com.converge.carpenter.others.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JugalJaiswal on 5/8/2016.
 */
public class LoginActivity extends Activity
{
    private Button login_btn;
    private EditText routeid_et;
    private String routeid;
    //public static ArrayList<LoginBeen> loginBeenArrayList;
    DataBaseHelper dataBaseHelper;
    private SessionManager sessionManager;
    private String inner_success;
    private Spinner state_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        login_btn = (Button)findViewById(R.id.login_btn);
        routeid_et = (EditText)findViewById(R.id.routeid_et);
        //state_sp = (Spinner) findViewById(R.id.state_sp);

        /*String[] state_ar = getApplicationContext().getResources().getStringArray(R.array.state_array);
        setSpinner(state_ar,identity_sp);*/

        //loginBeenArrayList = new ArrayList<LoginBeen>();
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());


        if (sessionManager.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeid = routeid_et.getText().toString();

                if (Singelton.checkInternetConenction(getApplicationContext())) {
                    new LoginState().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private class Login extends AsyncTask<String, Void, String> {
        ProgressDialog progressDoalog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDoalog = new ProgressDialog(LoginActivity.this);
            progressDoalog.setMessage("loading....");
            // progressDoalog.setTitle("Waiting for connection");
            //  progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDoalog.show();
        }



        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub  12050i04022
            String POST_URL="http://cnvg.in/newinsight/route_login.php?"+"routeid="+routeid;



            /*String POST_PARAMS=js.toString();//"roll_no=12050i04022&password=123123&tag=login";
            Log.e("=========", POST_PARAMS);
*/

            return Utility.sendGET(POST_URL);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            System.out.println("result here "+ result);


            progressDoalog.dismiss();

            if(result!=null)
            {


                try {
                    JSONObject js=new JSONObject(result);
                    int success = js.getInt("success");
                    if(success == 1)
                    {

                        //loginBeenArrayList.clear();
                        JSONArray ja = js.getJSONArray("routedata");
                        for (int i = 0; i < ja.length(); i++)
                        {
                            JSONObject jsonobject = ja.getJSONObject(i);
                            String num = jsonobject.getString("num");
                            String rid = jsonobject.getString("rid");
                            String act_typ = jsonobject.getString("act_typ");
                            String route_date = jsonobject.getString("route_date");
                            String vilname = jsonobject.getString("vilname");
                            String tehname = jsonobject.getString("tehname");
                            String disname = jsonobject.getString("disname");
                            String rand_id = jsonobject.getString("rand_id");
                            String route_name = jsonobject.getString("route_name");

                            LoginBeen loginBeen = new LoginBeen(num,rid,act_typ,route_date,vilname,tehname,disname,rand_id,route_name);
                            if(dataBaseHelper.isALreadHasStockDetails(rid))
                            {
                                dataBaseHelper.updateLoginData(loginBeen);
                            }
                            else
                            {
                                dataBaseHelper.createLoginData(loginBeen);
                            }


                            //loginBeenArrayList.add(loginBeen);

                        }

                        sessionManager.setLogin(true);
                        Intent in  = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(in);
                        finish();
                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(),"No Data Found.",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.e("error from json", "" + e);
                    e.printStackTrace();
                }

            }

        }
    }


    private void setSpinner(String[] sp_array,Spinner sp_name)
    {
        ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(LoginActivity.this,android.R.layout.simple_spinner_item, sp_array);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_name.setAdapter(gameKindArray);
    }

    private class LoginState extends AsyncTask<String, Void, String> {
        ProgressDialog progressDoalog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDoalog = new ProgressDialog(LoginActivity.this);
            progressDoalog.setMessage("loading....");
            // progressDoalog.setTitle("Waiting for connection");
            //  progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDoalog.show();
        }



        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub  12050i04022
            String POST_URL="http://cnvg.in/newinsight/new_route_login.php?"+"routeid="+routeid;

            String result = Utility.sendGET(POST_URL);

            if(result!=null)
            {


                try {
                    JSONObject js=new JSONObject(result);
                    int success = js.getInt("success");
                    if(success == 1)
                    {

                        inner_success = "true";

                        //loginBeenArrayList.clear();
                        JSONArray ja = js.getJSONArray("routedata");
                        for (int i = 0; i < ja.length(); i++)
                        {
                            JSONObject jsonobject = ja.getJSONObject(i);
                            String sr = jsonobject.getString("sr");
                            String state = jsonobject.getString("state");
                            String district = jsonobject.getString("district");
                            String tehsil = jsonobject.getString("tehsil");
                            String uname = jsonobject.getString("uname");
                            String name = jsonobject.getString("name");
                            String mobile = jsonobject.getString("mobile");


                            LoginStateBeen loginStateBeen = new LoginStateBeen(sr,state,district,tehsil,uname,name,mobile);

                            dataBaseHelper.createLoginStateData(loginStateBeen);

                            //loginBeenArrayList.add(loginBeen);

                        }


                    }
                    else if (success == 0)
                    {

                        inner_success = "false";

                    }

                    else
                    {
                        inner_success = "error";

                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.e("error from json", "" + e);
                    e.printStackTrace();
                }

            }







            /*String POST_PARAMS=js.toString();//"roll_no=12050i04022&password=123123&tag=login";
            Log.e("=========", POST_PARAMS);
*/

            return inner_success;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            System.out.println("result here "+ result);


            progressDoalog.dismiss();

            if(result!=null)
            {

                if(result.equals("true"))
                {
                    sessionManager.setLogin(true);
                    Intent in  = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(in);
                    finish();

                }else if (result.equals("false"))
                {
                    Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(getApplicationContext(),"No Data Found.",Toast.LENGTH_SHORT).show();
                }


                /*try {
                    JSONObject js=new JSONObject(result);
                    int success = js.getInt("success");
                    if(success == 1)
                    {

                        //loginBeenArrayList.clear();
                        JSONArray ja = js.getJSONArray("routedata");
                        for (int i = 0; i < ja.length(); i++)
                        {
                            JSONObject jsonobject = ja.getJSONObject(i);
                            String sr = jsonobject.getString("sr");
                            String state = jsonobject.getString("state");
                            String district = jsonobject.getString("district");
                            String tehsil = jsonobject.getString("tehsil");
                            String uname = jsonobject.getString("uname");
                            String name = jsonobject.getString("name");
                            String mobile = jsonobject.getString("mobile");


                            LoginStateBeen loginStateBeen = new LoginStateBeen(sr,state,district,tehsil,uname,name,mobile);

                                dataBaseHelper.createLoginStateData(loginStateBeen);

                            //loginBeenArrayList.add(loginBeen);

                        }

                        sessionManager.setLogin(true);
                        Intent in  = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(in);
                        finish();
                    }
                    else if (success == 0)
                    {
                        Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(),"No Data Found.",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.e("error from json", "" + e);
                    e.printStackTrace();
                }

            }*/
            }
        }
    }
}
