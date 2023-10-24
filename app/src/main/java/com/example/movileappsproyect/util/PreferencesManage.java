package com.example.movileappsproyect.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.movileappsproyect.model.UserModel;

public class PreferencesManage {

    private final static String PREFERENCES_FILE_NAME = "prefs", PREFERENCE_ATTR_1_NAME = "user_name";

    public static boolean userExists(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE);
        String username = pref.getString(PREFERENCE_ATTR_1_NAME, "");

        //comprobar si hay algo
        return (!username.equals(""));
    }

    public static void storeUser(Context ctx, String name) {
        SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString(PREFERENCE_ATTR_1_NAME, name);
        ed.apply();
    }

    public static void removeUser(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.remove(PREFERENCE_ATTR_1_NAME);
        ed.apply();
    }
}