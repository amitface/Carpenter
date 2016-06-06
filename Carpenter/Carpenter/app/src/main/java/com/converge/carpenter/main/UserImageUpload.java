package com.converge.carpenter.main;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.converge.carpenter.database.DataBaseHelper;
import com.converge.carpenter.model.ImageBeen;
import com.converge.carpenter.others.GPSTracker;
import com.converge.carpenter.others.Singelton;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JugalJaiswal on 5/13/2016.
 */
public class UserImageUpload extends AppCompatActivity
{
    ProgressDialog prgDialog;
    String encodedString;
    RequestParams params = new RequestParams();
    String imgPath, fileName;
    Bitmap bitmap, lesimg;
    private static int RESULT_LOAD_IMG = 1;
    private static int REQUEST_IMAGE_CAPTURE = 1;
    private static String TIME_STAMP="null";
    String date;
    String id="10001";
    private double latitude;
    private double longitude;
    private String routeid;
    private String rid;
    private Toolbar toolbar;
    private GPSTracker gps;
    private String date_time;
    private String p_primg;
    private Button user_img;
    DataBaseHelper dataBaseHelper;
    private Button done_btn;
    private ContentValues values;
    private Uri imageUri;
    private String paintername;
    private Button buttonLoadPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_upload);
        imageUri=null;
        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        Intent in = getIntent();
        p_primg = in.getStringExtra("p_primg");
        //paintername = in.getStringExtra("painter_name");
        //Toast.makeText(getApplicationContext(),paintername,Toast.LENGTH_SHORT).show();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Image Upload");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        prgDialog = new ProgressDialog(this);
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/



        user_img = (Button)findViewById(R.id.user_img);
        buttonLoadPicture = (Button)findViewById(R.id.buttonLoadPicture);

        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.print("userimage");
                encodeImagetoString();

            }
        });

        gps = new GPSTracker(UserImageUpload.this);
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


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        date_time = df.format(c.getTime());

    }


    public void dispatchTakePictureIntent(View view)
    {

        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            buttonLoadPicture.setText("Retry");
            user_img.setVisibility(View.VISIBLE);
            Bitmap imageBitmap;
            Bitmap resized;
            /*Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap dateimageBitmap = (Bitmap) extras.get("data");*/

            try {

                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                int in = imageBitmap.getHeight();
                int wi = imageBitmap.getWidth();
                System.out.println("width"+wi+"Height"+in);

                ExifInterface ei = new ExifInterface(getPath(imageUri));
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        imageBitmap = rotateImage(imageBitmap, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        imageBitmap = rotateImage(imageBitmap, 180);
                        break;
                    // etc.
                }

                if(in < 500)
                {
                    resized = imageBitmap;
                }
                else if(in < 1500)
                {
                    resized = Bitmap.createScaledBitmap(imageBitmap, (int) (imageBitmap.getWidth() / 2), (int) (imageBitmap.getHeight() / 2), true);
                }else
                {
                    resized = Bitmap.createScaledBitmap(imageBitmap, (int) (imageBitmap.getWidth() / 4), (int) (imageBitmap.getHeight() / 4), true);
                }



                int iin = resized.getHeight();
                int iwi = resized.getWidth();
                System.out.println("width"+iwi+"Height"+iin);

                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                imgView.setImageBitmap(resized);
                lesimg=resized;

                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                String hjkl=currentDateTimeString.replaceAll(" ", "_");
                String hiop=hjkl.replaceAll(":", "-");
                TIME_STAMP=hiop;
                fileName=TIME_STAMP+".jpg";
                params.put("filename", fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }


    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;

                bitmap=lesimg;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byte_arr = stream.toByteArray();

                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                prgDialog.show();

                params.put("e_image", encodedString);
                if(imageUri!=null)
                    deletePicture(imageUri);
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    public void makeHTTPCall() {
        prgDialog.setMessage("Invoking JSP");
        prgDialog.show();
        //AsyncHttpClient client = new AsyncHttpClient();

        //routeid, rid, date, lat, lang, e_image


        ImageBeen usrImageBeen = new ImageBeen(fileName,encodedString);
        dataBaseHelper.createUserImageData(usrImageBeen);
        Toast.makeText(getApplicationContext(),"Image Saved",Toast.LENGTH_SHORT).show();
        Singelton.usr_imgdone = true;
        prgDialog.hide();
        Intent in = new Intent(UserImageUpload.this,IdImageUpload.class);
        startActivity(in);
        finish();


        /*client.post("http://cnvg.in/newinsight/upload_painter_img.php",
                params, new AsyncHttpResponseHandler()
                {

                    @Override
                    public void onSuccess(String response) {

                        prgDialog.hide();
                        Toast.makeText(getApplicationContext(), response,
                                Toast.LENGTH_LONG).show();
                        finish();
                    }


                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {

                        prgDialog.hide();

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
                });*/
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
