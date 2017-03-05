package com.restaurant.partnerapp.utility;

import android.util.Log;

/**
 * Created by vJ on 3/6/17.
 */

public class Logger {

    public static void debug(String message){
        Log.d("TAG", message);
    }

    public static void debug(String tag, String message){
        Log.d(tag, message);
    }

}
