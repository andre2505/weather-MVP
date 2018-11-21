package com.smartnsoft.weathr.session;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    private static final String PREFERENCE_NAME = "DATA";
    private static final String KEY_CITY= "KEY_CITY";
    private static final String KEY_DAY = "KEY_FORECAST";

    public static void setPreferences(Context context, String city, int forecast){
       SharedPreferences sharedPreferences =  context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putString(KEY_CITY, city);
       editor.putInt(KEY_DAY, forecast);
       editor.apply();
    }

    public static String getCity(Context context){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CITY, null);
    }

    public static int getForecast(Context context){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_DAY, 0);
    }
}
