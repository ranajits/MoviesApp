package com.rnjt.eros.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.rnjt.eros.R;


public class NetworkUtil {

    public static boolean isNetworkAvailable(Context context, boolean showToast) {

        NetworkInfo info = Connectivity.getNetworkInfo(context);
        if((info != null && info.isConnected())){
            return true;
        }

        if (showToast) {
            Toast.makeText(context, R.string.error_try_again, Toast.LENGTH_SHORT).show();
        }
        return false;//(info != null && info.isConnected());

        //return false;
    }


    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = null;
        try {
            cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnected(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

}
