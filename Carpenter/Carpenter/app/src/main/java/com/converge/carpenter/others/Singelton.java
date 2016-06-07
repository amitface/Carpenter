package com.converge.carpenter.others;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by JugalJaiswal on 5/10/2016.
 */
public class Singelton
{

    public static boolean id_imgdone = false;
    public static boolean usr_imgdone = false;

    public static volatile String painter_type="";
    public static volatile String painter_name="";
    public static volatile String mobile_no="";
    public static volatile String age="";
    public static volatile String address="";
    public static volatile String state="";
    public static volatile String thesil="";
    public static volatile String district="";
    public static volatile String pincode="";
    public static volatile String native_loc="";
    public static volatile String exp="";
    public static volatile String distributor="";
    public static volatile String p_identity="";
    public static volatile String p_id_number="";
    public static volatile String lat="";
    public static volatile String longi="";
    public static volatile String date_time="";
    public static volatile String routeid="";
    public static volatile String rid="";
    public static volatile String annual_income="";


    public static boolean checkInternetConenction(Context context)
    {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            return true;
        }
        else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
        {

            return false;
        }
        return false;
    }


}
