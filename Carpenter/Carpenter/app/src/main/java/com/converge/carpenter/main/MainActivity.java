package com.converge.carpenter.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.converge.carpenter.database.DataBaseHelper;
import com.converge.carpenter.fragment.FragmentDrawer;
import com.converge.carpenter.fragment.HomeFragment;
import com.converge.carpenter.model.LoginBeen;
import com.converge.carpenter.others.DataSyncService;
import com.converge.carpenter.others.ImageSyncService;
import com.converge.carpenter.others.SessionManager;
import com.converge.carpenter.others.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {



    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private SessionManager sessionManager;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        sessionManager = new SessionManager(getApplicationContext());
        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        // display the first navigation drawer view on app launch
        displayView(0);
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


        if(id == R.id.action_search)
        {
            startService(new Intent(getBaseContext(), DataSyncService.class));
            //Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_settings)
        {
            startService(new Intent(getBaseContext(), ImageSyncService.class));
            //Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = "ACD";
                break;
            case 1:

                sessionManager.setLogin(false);
                dataBaseHelper.deleteUsers();
                Intent in = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(in);
                finish();
                /*fragment = new FriendsFragment();
                title = "Chattisgarh UAD";*/
                break;
            /*case 2:
                fragment = new MessagesFragment();
                title = "Chattisgarh UAD";
                break;*/
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
//            getSupportActionBar().setTitle(title);
        }
    }

    private class Login extends AsyncTask<String, Void, String> {
        ProgressDialog progressDoalog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDoalog = new ProgressDialog(MainActivity.this);
            progressDoalog.setMessage("loading....");
            // progressDoalog.setTitle("Waiting for connection");
            //  progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDoalog.show();
        }



        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub  12050i04022
            String POST_URL="http://cnvg.in/newinsight/route_login.php?";



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