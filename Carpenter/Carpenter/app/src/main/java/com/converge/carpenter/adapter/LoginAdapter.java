package com.converge.carpenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.converge.carpenter.database.DataBaseHelper;
import com.converge.carpenter.main.R;
import com.converge.carpenter.model.LoginBeen;

import java.util.ArrayList;

/**
 * Created by JugalJaiswal on 5/11/2016.
 */
public class LoginAdapter extends BaseAdapter
{

    private ArrayList<LoginBeen> loginBeenArrayList;
    Context context;
    private TextView ro_date;
    private TextView ro_name;
    private TextView num;
    DataBaseHelper dataBaseHelper;
    ArrayList<LoginBeen> getLoginBeenArrayList;

    public LoginAdapter(Context context, ArrayList<LoginBeen> loginBeenArrayList)
    {
        this.context = context;
        this.loginBeenArrayList = loginBeenArrayList;
    }


    @Override
    public int getCount() {
        return loginBeenArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.login_item, parent, false);
        dataBaseHelper = new DataBaseHelper(context);
        getLoginBeenArrayList = new ArrayList<LoginBeen>();

        LoginBeen been = new LoginBeen();
        ro_date = (TextView)row.findViewById(R.id.ro_date);
        ro_name = (TextView)row.findViewById(R.id.ro_name);
        num = (TextView)row.findViewById(R.id.num);

        /*ro_date.setText(loginBeenArrayList.get(position).getRoute_date());
        ro_name.setText(loginBeenArrayList.get(position).getRoute_name());
        num.setText(loginBeenArrayList.get(position).getNum());*/

        ro_date.setText(loginBeenArrayList.get(position).getRoute_date());
        ro_name.setText(loginBeenArrayList.get(position).getRoute_name());
        /*getLoginBeenArrayList = dataBaseHelper.getAllLoginDataRid(loginBeenArrayList.get(position).getRid());
        String count = String.valueOf(getLoginBeenArrayList.size());*/
        num.setText(loginBeenArrayList.get(position).getNum());
        //num.setText(count);

        return row;
    }
}
