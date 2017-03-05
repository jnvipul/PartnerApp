package com.restaurant.partnerapp.utility;

import com.google.gson.Gson;

/**
 * Created by vJ on 3/6/17.
 */

public class GsonUtil {

    private static Gson gson;

    public static Gson getGson(){
        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }


}
