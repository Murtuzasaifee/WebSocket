package com.bitoasis.websocket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SharedPrefUtils {

    private static final String IS_LOGIN = "islogin";
    private static final String PREF = "webSocketPref";

    public static void setLogin(AppCompatActivity context, boolean loginState) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_LOGIN, loginState);
        editor.apply();
    }

    public static boolean isLoggedIn(AppCompatActivity context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF,Context.MODE_PRIVATE);
        return sharedPref.getBoolean(IS_LOGIN, false);
    }

}
