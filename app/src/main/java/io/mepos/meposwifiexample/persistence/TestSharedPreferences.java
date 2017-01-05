package io.mepos.meposwifiexample.persistence;

import android.content.Context;
import android.content.SharedPreferences;


public class TestSharedPreferences {

    public static final String MY_PREFS_NAME = "meposinfo";
    private Context context;
    public TestSharedPreferences(Context context){
        this.context =context;
    }

    public String getTestInfo(String savedfield) {
        SharedPreferences information = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        String back = information.getString(savedfield, null);
        return back;
    }
    public void saveTestInfo(String mField, String mValue) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString(mField, mValue);
        editor.apply();
    }
}
