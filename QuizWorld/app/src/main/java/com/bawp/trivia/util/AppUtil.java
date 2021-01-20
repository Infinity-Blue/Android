package com.bawp.trivia.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.reveinfini.quizworld.quizAct;

public class AppUtil extends Application {
    private quizAct quizact;
    private Context context;

    public AppUtil(Context ctx) {
        this.context=ctx;
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            return true;
        }
        else{
            return false;
        }
    }
}