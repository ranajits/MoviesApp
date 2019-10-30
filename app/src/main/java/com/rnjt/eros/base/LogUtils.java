package com.rnjt.eros.base;

import android.util.Log;


public class LogUtils {

    private static final boolean DEBUG = true;
    private static final String TAG = "EROS";

    public static boolean isDebug() {
        return DEBUG;
    }

    public static void logE(String msg) {

            Log.e(TAG, msg);

    }


}
