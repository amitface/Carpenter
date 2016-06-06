package com.converge.carpenter.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.converge.carpenter.main.PainterDetailsActivity;
import com.converge.carpenter.main.R;
import com.converge.carpenter.model.LoginStateBeen;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Created by JugalJaiswal on 5/22/2016.
 */
public class DistrictSpinnerAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private ArrayList data;

    LoginStateBeen tempValues=null;
    LayoutInflater inflater;

    /*************  CustomAdapter Constructor *****************/
    public DistrictSpinnerAdapter
    (
            PainterDetailsActivity activitySpinner,
            ArrayList objects

    )
    {
        super(activitySpinner,0,objects);

        /********** Take passed values **********/
        activity = activitySpinner;
        data     = objects;


        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_row, parent, false);

        /***** Get each Model object from Arraylist ********/
        data = new ArrayList<LoginStateBeen>(new LinkedHashSet<LoginStateBeen>(data));
        tempValues = null;
        tempValues = (LoginStateBeen) data.get(position);


        TextView label = (TextView)row.findViewById(R.id.label);


        if(position==0){

            // Default selected Spinner item
            label.setText("--Select District--");

        }
        else
        {
            // Set values for spinner each row


                label.setText(tempValues.getDistrict());


        }

        return row;
    }
}
