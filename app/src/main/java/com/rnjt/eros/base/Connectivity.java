package com.rnjt.eros.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class Connectivity {

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = null;
        try {
            cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity to a Wifi network
     *
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Check if there is fast connectivity
     * @param context
     * @return
     */
    /*public static boolean isConnectedFast(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && Connectivity.isConnectionFast(info.getType(),info.getSubtype()));
    }*/

    /**
     * Check if the connection is fast
     *
     * @param context
     * @return
     */
    public static int networkSpeed(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        int type = info.getType();
        int subType = info.getSubtype();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return 0;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return TelephonyManager.NETWORK_TYPE_1xRTT; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return TelephonyManager.NETWORK_TYPE_CDMA; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return TelephonyManager.NETWORK_TYPE_EDGE; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return TelephonyManager.NETWORK_TYPE_EVDO_0; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return TelephonyManager.NETWORK_TYPE_EVDO_A; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return TelephonyManager.NETWORK_TYPE_GPRS; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return TelephonyManager.NETWORK_TYPE_HSDPA; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return TelephonyManager.NETWORK_TYPE_HSPA; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return TelephonyManager.NETWORK_TYPE_HSUPA; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return TelephonyManager.NETWORK_TYPE_UMTS; // ~ 400-7000 kbps
            /*
             * Above API level 7, make sure to set android:targetSdkVersion
			 * to appropriate level to use these
			 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return TelephonyManager.NETWORK_TYPE_EHRPD; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return TelephonyManager.NETWORK_TYPE_EVDO_B; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return TelephonyManager.NETWORK_TYPE_HSPAP; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return TelephonyManager.NETWORK_TYPE_IDEN; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return TelephonyManager.NETWORK_TYPE_LTE; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return TelephonyManager.NETWORK_TYPE_UNKNOWN;
            }
        } else {
            return 0;
        }
    }

    public static String getNetworkType(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        if (info == null) {
            return "No Network";
        }

        int type = info.getType();


        if (type == ConnectivityManager.TYPE_WIFI) {
            return "WIFI";
        }


        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                /**
                 From this link https://goo.gl/R2HOjR ..NETWORK_TYPE_EVDO_0 & NETWORK_TYPE_EVDO_A
                 EV-DO is an evolution of the CDMA2000 (IS-2000) standard that supports high data rates.

                 Where CDMA2000 https://goo.gl/1y10WI .CDMA2000 is a family of 3G[1] mobile technology standards for sending voice,
                 data, and signaling data between mobile phones and cell sites.
                 */
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                //Log.d("Type", "3g");
                //For 3g HSDPA , HSPAP(HSPA+) are main  networktype which are under 3g Network
                //But from other constants also it will 3g like HSPA,HSDPA etc which are in 3g case.
                //Some cases are added after  testing(real) in device with 3g enable data
                //and speed also matters to decide 3g network type
                //http://goo.gl/bhtVT
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                //No specification for the 4g but from wiki
                //I found(LTE (Long-Term Evolution, commonly marketed as 4G LTE))
                //https://goo.gl/9t7yrR
                return "4G";
            default:
                return "Notfound";
        }
    }
}
