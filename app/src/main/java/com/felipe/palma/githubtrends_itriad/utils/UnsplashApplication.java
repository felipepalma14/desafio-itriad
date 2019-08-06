package com.felipe.palma.githubtrends_itriad.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Felipe Palma on 05/08/2019.
 */

public class UnsplashApplication extends Application {

    private static UnsplashApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static UnsplashApplication getApplicationInstance() {
        if (instance == null) {
            instance = new UnsplashApplication();
        }

        return instance;
    }

    public static boolean hasNetwork() {
        return instance.isNetworkAvailable();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null) && activeNetworkInfo.isConnectedOrConnecting();
    }
}
