package com.rnjt.eros.helpers;


public class SessionManager {


    private static SessionManager sInstance = null;



    public static SessionManager getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("Initialize SessionManager");
        }
        return sInstance;
    }

}
