package com.restaurant.partnerapp;

import android.app.Application;

import com.restaurant.partnerapp.constants.Endpoints;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vJ on 3/6/17.
 */

public class ApplicationState extends Application {

    private static ApplicationState mInstance;

    public static ApplicationState getInstance(){
        return mInstance;
    }

    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        setup();
    }

    private void setup() {
        mInstance = this;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Endpoints.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
