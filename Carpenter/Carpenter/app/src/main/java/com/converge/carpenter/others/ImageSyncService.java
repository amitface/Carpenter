package com.converge.carpenter.others;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.converge.carpenter.database.DataBaseHelper;
import com.converge.carpenter.fragment.HomeFragment;
import com.converge.carpenter.main.MyAlert;
import com.converge.carpenter.model.EventImageBeen;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JugalJaiswal on 5/15/2016.
 */

public class ImageSyncService extends Service
{

    public static final long NOTIFY_INTERVAL = 10 * 1000; // 10 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;
    DataBaseHelper dataBaseHelper;
    ArrayList<EventImageBeen> eventImageBeenArrayList;
    public static volatile int pos;

    String encodedString;
    RequestParams params = new RequestParams();
    String fileName;
    Bitmap bitmap, lesimg;
    @Override
    public IBinder onBind(Intent intent)
    {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        eventImageBeenArrayList = dataBaseHelper.getAllEventImageData();




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();


        if(Singelton.checkInternetConenction(this) && eventImageBeenArrayList.size()>0) {


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
                public void run() {
                    // display toast
                    //new SubmiDataTask().execute();
                    if(pos == eventImageBeenArrayList.size())
                    {
                        pos=0;
                        dataBaseHelper.deletevnimg();
                        mTimer.cancel();
                        stopSelf();
                        Intent intent = new Intent(getApplicationContext(), MyAlert.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);

                        HomeFragment.textViewPendingEvent.setText("Influence Meet : 0");
                        //Toast.makeText(getApplicationContext(),"Data Sync Completed.",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        encodeImagetoString();
                    }

                }

            });
        }
    }

    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute()
            {

                eventImageBeenArrayList = dataBaseHelper.getAllEventImageData();
                String filename = eventImageBeenArrayList.get(pos).getDate();
                params.put("filename",filename+".jpg");

            };

            @Override
            protected String doInBackground(Void... params) {


                encodedString = eventImageBeenArrayList.get(pos).getEvimg_data();

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
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    public void makeHTTPCall() {
        //prgDialog.setMessage("Invoking JSP");
        //prgDialog.show();



        AsyncHttpClient client = new AsyncHttpClient();

        //routeid, rid, date, lat, lang, e_image

        String ro = eventImageBeenArrayList.get(pos).getRouteid();
        ro = ro.replace(" ","%20");

        client.post(Constant.EVENT_IMAGE_UPLOADING+"routeid="+ro+"&rid="+eventImageBeenArrayList.get(pos).getRid()+"&date="+eventImageBeenArrayList.get(pos).getDate()+"&lat="+eventImageBeenArrayList.get(pos).getLat()+"&lang="+eventImageBeenArrayList.get(pos).getLongi(),
                params, new AsyncHttpResponseHandler()
                {

                    @Override
                    public void onSuccess(String response) {

                        //prgDialog.hide();
                        //Toast.makeText(getApplicationContext(), response,Toast.LENGTH_LONG).show();
                        pos = pos+1;
                    }


                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {

                        //prgDialog.hide();

                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }

                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }

                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });


    }

}
