package com.converge.carpenter.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.converge.carpenter.database.DataBaseHelper;
import com.converge.carpenter.model.PainterDataBeen;
import com.converge.carpenter.others.GPSTracker;
import com.converge.carpenter.others.MarshMallowPermission;
import com.converge.carpenter.others.Singelton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JugalJaiswal on 5/8/2016.
 */
public class UploadPictureActivity extends AppCompatActivity
{
    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
    ProgressDialog prgDialog;
    String encodedString;
    RequestParams params = new RequestParams();
    String imgPath, fileName;
    Bitmap bitmap, lesimg=null;
    private static int RESULT_LOAD_IMG = 1;
    private static int REQUEST_IMAGE_CAPTURE = 1;
    private Toolbar toolbar;
    private LinearLayout enchroch_ll, clean_ll, street_light_ll, leakage_ll;
    private Button com_sub_btn;
    private Button usercamer_iv;
    private Button idcamer_iv;
    private GPSTracker gps;
    private String painter_name,mobile_no,age,address,state,thesil,district,pincode,native_loc,exp,distributor,routeid,rid,date_time,p_identity,p_id_number;
    private double latitude;
    private double longitude;
    private Spinner identity_sp;
    private EditText cardno_et;
    private Button submit_btn;
    private static String TIME_STAMP="null";
    public static String p_primg="";
    public static String p_idimg="";
    private TextView usrdone,iddone;
    private DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadpicture_activity);


        Intent in = getIntent();
        p_primg = in.getStringExtra("p_primg");
        p_idimg = in.getStringExtra("p_idimg");
        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        //Toast.makeText(getApplicationContext(),p_idimg,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),p_primg,Toast.LENGTH_SHORT).show();
        /*Intent in = getIntent();
        painter_name = in.getStringExtra("painter_name");
        painter_name = painter_name.replace(" ","%20");
        mobile_no = in.getStringExtra("mobile_no");
        age = in.getStringExtra("age");
        address = in.getStringExtra("address");
        state = in.getStringExtra("state");
        state = state.replace(" ", "%20");
        thesil = in.getStringExtra("thesil");
        district = in.getStringExtra("district");
        pincode = in.getStringExtra("pincode");
        native_loc = in.getStringExtra("native_loc");
        exp = in.getStringExtra("exp");
        distributor = in.getStringExtra("distributor");
        routeid = in.getStringExtra("route_id");
        rid = in.getStringExtra("rid");*/

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        date_time = df.format(c.getTime());




//        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
//        File newdir = new Filedir();
//        newdir.mkdirs();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upload Picture");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gps = new GPSTracker(UploadPictureActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line

        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        identity_sp = (Spinner)findViewById(R.id.identity_sp);
        cardno_et = (EditText)findViewById(R.id.cardno_et);
        submit_btn = (Button)findViewById(R.id.done_btn);

        usercamer_iv = (Button)findViewById(R.id.usercamer_iv);
        idcamer_iv = (Button)findViewById(R.id.idcamer_iv);
        usrdone = (TextView)findViewById(R.id.usrdone);
        iddone = (TextView)findViewById(R.id.iddone);

        if(Singelton.usr_imgdone == true)
        {

            usrdone.setVisibility(View.VISIBLE);

        }

        if(Singelton.id_imgdone == true)
        {

            iddone.setVisibility(View.VISIBLE);

        }

        String[] id_ar = getApplicationContext().getResources().getStringArray(R.array.id_array);
        setSpinner(id_ar,identity_sp);

        usercamer_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                /*Intent in = new Intent(UploadPictureActivity.this,UserImageUpload.class);
                in.putExtra("p_primg", p_primg);
                startActivity(in);
                finish();*/

            }
        });

        idcamer_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                /*Intent in = new Intent(UploadPictureActivity.this,IdImageUpload.class);
                in.putExtra("p_idimg", p_idimg);
                startActivity(in);
                finish();*/

            }
        });




        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(Singelton.usr_imgdone == true && Singelton.id_imgdone == false)
                {
                    dialogueBox("Upload Painter Identity Image.");

                }else if(Singelton.usr_imgdone == false && Singelton.id_imgdone == true)
                {
                    dialogueBox("Upload Painter Image.");
                }
                else if(Singelton.id_imgdone == false && Singelton.usr_imgdone== false)
                {
                    dialogueBox("Upload Painter and Painter Id Image.");
                }else if(Singelton.usr_imgdone == true && Singelton.id_imgdone== true)
                {

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UploadPictureActivity.this);
                    alertDialogBuilder.setMessage("Both Images Saved Successfully.");

                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1)
                        {

                            new SubmiDataTask().execute();
                            Singelton.id_imgdone = false;
                            Singelton.usr_imgdone = false;
                            p_primg = "";
                            p_idimg = "";
                            Intent in = new Intent(UploadPictureActivity.this,MainActivity.class);
                            startActivity(in);
                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else
                {
                    dialogueBox("Something wrong.");
                }


            }





        });


    }


    private class SubmiDataTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDoalog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDoalog = new ProgressDialog(UploadPictureActivity.this);
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


            PainterDataBeen painterDataBeen = new PainterDataBeen(Singelton.painter_type,Singelton.annual_income,Singelton.painter_name,Singelton.mobile_no,Singelton.age,Singelton.address,Singelton.state,Singelton.thesil,Singelton.district,Singelton.pincode,Singelton.native_loc,Singelton.exp,Singelton.distributor,Singelton.p_identity,Singelton.p_id_number,Singelton.lat,Singelton.longi,Singelton.date_time,Singelton.routeid,Singelton.rid);
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
                    /*Intent in = new Intent(PainterDetailsActivity.this,UserImageUpload.class);
                    //in.putExtra("painter_name",painter_name);
                    in.putExtra("p_primg","timestamp");
                    in.putExtra("p_idimg","timestamp");
                    startActivity(in);
                    finish();*/

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

    private void dialogueBox(String message)
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UploadPictureActivity.this);
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

    private void setSpinner(String[] sp_array,Spinner sp_name)
    {
        ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(UploadPictureActivity.this,android.R.layout.simple_spinner_item, sp_array);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_name.setAdapter(gameKindArray);
    }




    public void dispatchTakePictureIntent(View view) {
        if (!marshMallowPermission.checkPermissionForCamera()) {
            marshMallowPermission.requestPermissionForCamera();
        } else {
            if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                marshMallowPermission.requestPermissionForExternalStorage();
            } else {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap dateimageBitmap = (Bitmap) extras.get("data");


            ImageView imgView = (ImageView) findViewById(R.id.imgView);
            imgView.setImageBitmap(imageBitmap);
            lesimg=imageBitmap;
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            String hjkl=currentDateTimeString.replaceAll(" ", "_");
            String hiop=hjkl.replaceAll(":", "-");
            TIME_STAMP=hiop;
            fileName=p_idimg+TIME_STAMP+".jpeg";
            params.put("filename", fileName);
        }
    }





    public String encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                if(lesimg.equals(null))
                {
                    lesimg=BitmapFactory.decodeResource(getResources(),R.drawable.noimage);
                }

                bitmap=lesimg;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                byte[] byte_arr = stream.toByteArray();

                encodedString = Base64.encodeToString(byte_arr, Base64.NO_WRAP);


                //encodedString = encodedString.replace(" ","%20");
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                prgDialog.show();

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
        prgDialog.setMessage("Invoking JSP");
        prgDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();

        //routeid, rid, date, lat, lang, e_image

//"&p_id_image="+encodedString
        client.post("http://cnvg.in/newinsight/upload_painter_id.php",
                params, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(String response) {

                        prgDialog.hide();
                        Toast.makeText(getApplicationContext(), response,
                                Toast.LENGTH_LONG).show();
                    }


                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {

                        prgDialog.hide();

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

    private void deletePicture(Uri uri)
    {
        File fdelete = new File(getPath(uri));
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + uri.getPath());
            } else {
                System.out.println("file not Deleted :" + uri.getPath());
            }
        }
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =      cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (prgDialog != null) {
            prgDialog.dismiss();
        }
    }


}
