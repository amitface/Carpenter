package com.converge.carpenter.fragment;

/**
 * Created by Ravi on 29/07/15.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.converge.carpenter.database.DataBaseHelper;
import com.converge.carpenter.main.ImageUpload;
import com.converge.carpenter.main.LoginActivity;
import com.converge.carpenter.main.PainterDetailsActivity;
import com.converge.carpenter.main.R;
import com.converge.carpenter.model.EventImageBeen;
import com.converge.carpenter.model.ImageBeen;
import com.converge.carpenter.model.LoginStateBeen;
import com.converge.carpenter.others.GPSTracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment {


    private Button next_btn;
    private LinearLayout home_ll;
    private ListView hom_list;
    //private ArrayList<LoginBeen> loginBeenArrayList;
    private ArrayList<LoginStateBeen> loginStateBeenArrayList;
    LoginStateBeen loginStateBeen;
    private Context context;
    LoginActivity loginActivity;
    DataBaseHelper dataBaseHelper;



    private Toolbar toolbar;
    private TextView roue_name;
    private TextView num;
    private Button enter_new_btn;
    private Button take_ev_btn;


    private GPSTracker gps;
    private double latitude;
    private double longitude;
    private String date_time;
    private TextView roue_date;
    public static TextView textViewPendingEvent;
    public static TextView textViewPendingEntry;

   String s;

    public static HomeFragment newInstance(int index) {
        HomeFragment f = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("intex", index);
        f.setArguments(args);
        return f;
    }


    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public HomeFragment(Context context,ArrayList<LoginStateBeen> loginStateBeenArrayList) {
        // Required empty public constructor
        this.context = context;
        this.loginStateBeenArrayList = loginStateBeenArrayList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.event_image_layout, container, false);
        enter_new_btn = (Button)rootView.findViewById(R.id.enter_new_btn);
        take_ev_btn = (Button)rootView.findViewById(R.id.take_ev_btn);
        roue_date = (TextView)rootView.findViewById(R.id.roue_date);



        textViewPendingEntry = (TextView) rootView.findViewById(R.id.textViewPendingData);
        textViewPendingEvent = (TextView) rootView.findViewById(R.id.textViewPendingEventData);

        /*home_ll = (LinearLayout)rootView.findViewById(R.id.home_ll);
        hom_list = (ListView)rootView.findViewById(R.id.home_list);*/
        loginActivity = new LoginActivity();
        dataBaseHelper  = new DataBaseHelper(getActivity());
        loginStateBeenArrayList = new ArrayList<LoginStateBeen>();

        loginStateBeenArrayList = dataBaseHelper.getAllLoginStateData();
        s="";
        ArrayList<ImageBeen> PendingData = dataBaseHelper.getAllUserImageData();
        s=textViewPendingEntry.getText().toString()+" ";
        s=s+String.valueOf(PendingData.size());

        textViewPendingEntry.setText(s);
        s="";
        ArrayList<EventImageBeen> PendingDataEvent = dataBaseHelper.getAllEventImageData();
        s=textViewPendingEvent.getText().toString()+" ";
        s=s+String.valueOf(PendingDataEvent.size());
        textViewPendingEvent.setText(s);

        gps = new GPSTracker(getActivity());

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
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date_time = df.format(c.getTime());

        /*roue_name.setText(route_name);
        roue_date.setText(route_date);
        num.setText(numt);*/


        enter_new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), PainterDetailsActivity.class);
                /*in.putExtra("route_id", rand_id);
                in.putExtra("rid", rid);*/
                startActivity(in);
            }
        });
        take_ev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in = new Intent(getActivity(), ImageUpload.class);
                /*in.putExtra("route_id", rand_id);
                in.putExtra("rid", rid);*/
                startActivity(in);
                getActivity().finish();;

            }
        });



        /*LoginAdapter loginAdapter = new LoginAdapter(getActivity(),loginStateBeenArrayList);
        hom_list.setAdapter(loginAdapter);*/

        /*hom_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent in = new Intent(getActivity(),EventImageActivity.class);
                in.putExtra("act_type",loginBeenArrayList.get(position).getAct_typ());
                in.putExtra("disname",loginBeenArrayList.get(position).getDisname());
                in.putExtra("num",loginBeenArrayList.get(position).getNum());
                in.putExtra("rand_id",loginBeenArrayList.get(position).getRand_id());
                *//*getloginarrylist = dataBaseHelper.getAllLoginDataRid(loginBeenArrayList.get(position).getRid());
                String count = String.valueOf(getloginarrylist.size());*//*

                        in.putExtra("rid",loginBeenArrayList.get(position).getRid());

                in.putExtra("route_date",loginBeenArrayList.get(position).getRoute_date());
                in.putExtra("route_name",loginBeenArrayList.get(position).getRoute_name());
                in.putExtra("tehname",loginBeenArrayList.get(position).getTehname());
                in.putExtra("vilname",loginBeenArrayList.get(position).getVilname());
                startActivity(in);
                getActivity().finish();
            }
        });*/



        /*home_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), PainterDetailsActivity.class);
                startActivity(in);
            }
        });

        hom_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getActivity(), PainterDetailsActivity.class);
                startActivity(in);
            }
        });*/







        return rootView;
    }


    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body,HomeFragment.newInstance(1)).commit();
    }

}
