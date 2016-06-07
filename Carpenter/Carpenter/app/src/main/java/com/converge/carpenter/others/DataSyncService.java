package com.converge.carpenter.others;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import com.converge.carpenter.database.DataBaseHelper;
import com.converge.carpenter.fragment.HomeFragment;
import com.converge.carpenter.main.MyAlert;
import com.converge.carpenter.model.IdImageBeen;
import com.converge.carpenter.model.ImageBeen;
import com.converge.carpenter.model.LoginBeen;
import com.converge.carpenter.model.PainterDataBeen;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JugalJaiswal on 5/15/2016.
 */
public class DataSyncService extends Service
{

    public static final long NOTIFY_INTERVAL = 15 * 1000; // 10 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;
    DataBaseHelper dataBaseHelper;
    ArrayList<PainterDataBeen> painterDataBeenArrayList;
    ArrayList<ImageBeen> imageBeenArrayList;
    ArrayList<IdImageBeen> idImageBeenArrayList;
    String p_primg;
    String p_idimg;
    String encodedString;
    RequestParams params = new RequestParams();
    String fileName;
    Bitmap bitmap, lesimg;
    public static volatile int position = 0;
    @Override
    public IBinder onBind(Intent intent)
    {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        painterDataBeenArrayList = new ArrayList<PainterDataBeen>();
        imageBeenArrayList = new ArrayList<ImageBeen>();
        idImageBeenArrayList = new ArrayList<IdImageBeen>();
        painterDataBeenArrayList = dataBaseHelper.getAllPainerData();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show();


        if(Singelton.checkInternetConenction(this) && painterDataBeenArrayList.size()>=0 && imageBeenArrayList.size()>=0 && idImageBeenArrayList.size()>=0) {


            if(mTimer != null)
            {
                mTimer.cancel();
            } else
            {
                // recreate new
                mTimer = new Timer();
            }
            // schedule task
            mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);

            //new SubmiDataTask().execute();




        }else
        {
            Toast.makeText(this,"No Data And Internet Connection Found.",Toast.LENGTH_SHORT).show();
            stopSelf();
        }


        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();


    }


    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run()
                {
                    // display toast
                    if(position == painterDataBeenArrayList.size())
                    {
                        position = 0;
                        dataBaseHelper.deletedata();
                        mTimer.cancel();
                        stopSelf();
                        Intent intent = new Intent(getApplicationContext(), MyAlert.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        HomeFragment.textViewPendingEntry.setText("Influence Data : 0");
                        //dialogueBox("Data Sync Completed.");
                        //Toast.makeText(getApplicationContext(),"Data Sync Completed.",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        new SubmiDataTask().execute();
                    }


                }

            });
        }
    }

    private void dialogueBox(String message)
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                arg0.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private class SubmiDataTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog progressDoalog;



        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //Toast.makeText(getApplicationContext(),"Asynctask Started.",Toast.LENGTH_SHORT).show();
        }



        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub  12050i04022


            String POST_URL=Constant.PAINTER_DATA_UPLOADING;

            String POST_PARAMS = "p_type="+painterDataBeenArrayList.get(position).getPainter_type()+"&bus_potential="+painterDataBeenArrayList.get(position).getAnnual_Income()+"&p_name="+painterDataBeenArrayList.get(position).getPainter_name()+"&p_mobile="+painterDataBeenArrayList.get(position).getMobile_no()+"&p_age="+painterDataBeenArrayList.get(position).getAge()+"&p_village="+painterDataBeenArrayList.get(position).getAddress()+"&p_state="+painterDataBeenArrayList.get(position).getState()+"&p_tehsil="+painterDataBeenArrayList.get(position).getThesil()
                    +"&p_dist="+painterDataBeenArrayList.get(position).getDistrict()+"&p_pincode="+painterDataBeenArrayList.get(position).getPincode()+"&p_location="+painterDataBeenArrayList.get(position).getNative_loc()+"&p_exp="+painterDataBeenArrayList.get(position).getExp()+"&p_distributor="+painterDataBeenArrayList.get(position).getDistributor()
                    +"&p_identity="+painterDataBeenArrayList.get(position).getP_identity()+"&p_id_number="+painterDataBeenArrayList.get(position).getP_id_number()+"&latitude="+painterDataBeenArrayList.get(position).getLatitude()+"&longitude="+painterDataBeenArrayList.get(position).getLongitude()
                    +"&date_time="+painterDataBeenArrayList.get(position).getDate_time()+"&routeid="+painterDataBeenArrayList.get(position).getRouteid()+"&rid="+painterDataBeenArrayList.get(position).getRid();

            System.out.println("=========" + POST_URL + POST_PARAMS);


            return Utility.sendPOST(POST_PARAMS, POST_URL);
            //return "success";
        }

        @Override
        protected void onPostExecute(String result)
        {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.e("result here ", "" + result);
            System.out.println(result);

            //progressDoalog.dismiss();

            if(result!=null)
            {

                try {
                    JSONObject js = new JSONObject(result);
                    int success = js.getInt("success");
                    if (success == 1)
                    {

                        //Toast.makeText(getApplicationContext(),"Data Inserted.",Toast.LENGTH_SHORT).show();
                        p_primg = js.getString("p_primg");
                        p_idimg = js.getString("p_idimg");


                        encodeImagetoString(p_primg);


                        //stopSelf();

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
                }
            }

        }


    }


    public String encodeImagetoString(final String usrimg)
    {

        System.out.println("in encode image to string");
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute()
            {

                imageBeenArrayList = dataBaseHelper.getAllUserImageData();
                params.put("filename",usrimg+".jpg");

            };

            @Override
            protected String doInBackground(Void... params) {




                if(imageBeenArrayList.size()>=0)
                {
                    encodedString = imageBeenArrayList.get(position).getUsrimg_data();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Data Not Found.",Toast.LENGTH_SHORT).show();
                }



                //encodedString = encodedString.replace(" ","%20");
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                //prgDialog.setMessage("Calling Upload");
                //prgDialog.show();

                params.put("e_image", encodedString);

                triggerImageUpload();
            }
        }.execute(null, null, null);

        return encodedString;
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    public void makeHTTPCall() {
        //prgDialog.setMessage("Invoking JSP");
        //prgDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();

        //routeid, rid, date, lat, lang, e_image

//"&p_id_image="+encodedString
        client.post(Constant.USER_IMAGE_UPLOADING,
                params, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(String response) {

                        //prgDialog.hide();
                        //Toast.makeText(getApplicationContext(), response,Toast.LENGTH_LONG).show();

                        encodeImagetoStringid(p_idimg);

                    }


                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {

                        //prgDialog.hide();

                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        } else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }


    public String encodeImagetoStringid(final String userid) {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute()
            {

                params.put("filename",userid+".jpg");

            };

            @Override
            protected String doInBackground(Void... params) {



                idImageBeenArrayList = dataBaseHelper.getAllIdImageData();
                encodedString = idImageBeenArrayList.get(position).getIdimg_data();

                //encodedString = encodedString.replace(" ","%20");
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                //prgDialog.setMessage("Calling Upload");
                //prgDialog.show();

                params.put("e_image", encodedString);

                triggerImageUploadid();
            }
        }.execute(null, null, null);

        return encodedString;
    }

    public void triggerImageUploadid() {
        makeHTTPCallid();
    }

    public void makeHTTPCallid() {
        //prgDialog.setMessage("Invoking JSP");
        //prgDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();

        //routeid, rid, date, lat, lang, e_image

//"&p_id_image="+encodedString
        client.post(Constant.IDENTITY_IMAGE_UPLOADING,
                params, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(String response)
                    {

                        //prgDialog.hide();

                        position = position+1;
                        System.out.println(position);
                        //Toast.makeText(getApplicationContext(), response,Toast.LENGTH_LONG).show();
                        //new Login().execute();



                    }


                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {

                        //prgDialog.hide();

                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        } else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
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
            //progressDoalog = new ProgressDialog(MainActivity.this);
            //progressDoalog.setMessage("loading....");
            // progressDoalog.setTitle("Waiting for connection");
            //  progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //progressDoalog.show();
        }



        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub  12050i04022
            String POST_URL="http://cnvg.in/newinsight/route_login.php?"+"routeid="+painterDataBeenArrayList.get(0).getRouteid();



            /*String POST_PARAMS=js.toString();//"roll_no=12050i04022&password=123123&tag=login";
            Log.e("=========", POST_PARAMS);
*/

            return Utility.sendGET(POST_URL);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            System.out.println("result here " + result);


            //progressDoalog.dismiss();

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

}
