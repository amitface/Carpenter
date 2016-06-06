package com.converge.carpenter.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.converge.carpenter.others.GPSTracker;
import com.converge.carpenter.others.ImageSyncService;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by JugalJaiswal on 5/11/2016.
 */
public class EventImageActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextView roue_name;
    private TextView num;
    private Button enter_new_btn;
    private Button take_ev_btn;

    private String numt;
    private String rid;
    private String act_typ;
    private String route_date;
    private String vilname;
    private String tehname;
    private String disname;
    private String rand_id;
    private String route_name;
    private GPSTracker gps;
    private double latitude;
    private double longitude;
    private String date_time;
    private TextView roue_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_image_layout);

        /*Intent in = getIntent();
        act_typ = in.getStringExtra("act_type");
        disname = in.getStringExtra("disname");
        numt = in.getStringExtra("num");
        rand_id = in.getStringExtra("rand_id");
        rid = in.getStringExtra("rid");
        route_date = in.getStringExtra("route_date");
        route_name = in.getStringExtra("route_name");
        tehname = in.getStringExtra("tehname");
        vilname = in.getStringExtra("vilname");*/


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Painters Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(EventImageActivity.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });
        roue_name = (TextView)findViewById(R.id.roue_name);
        num = (TextView)findViewById(R.id.num);

        enter_new_btn = (Button)findViewById(R.id.enter_new_btn);
        take_ev_btn = (Button)findViewById(R.id.take_ev_btn);
        roue_date = (TextView)findViewById(R.id.roue_date);

        gps = new GPSTracker(EventImageActivity.this);

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

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date_time = df.format(c.getTime());

        roue_name.setText(route_name);
        roue_date.setText(route_date);
        num.setText(numt);
        enter_new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(EventImageActivity.this, PainterDetailsActivity.class);
                /*in.putExtra("route_id", rand_id);
                in.putExtra("rid", rid);*/
                startActivity(in);
            }
        });
        take_ev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in = new Intent(EventImageActivity.this, ImageUpload.class);
                /*in.putExtra("route_id", rand_id);
                in.putExtra("rid", rid);*/
                startActivity(in);

            }
        });
        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search)
        {
            startService(new Intent(getBaseContext(), ImageSyncService.class));
            //Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
