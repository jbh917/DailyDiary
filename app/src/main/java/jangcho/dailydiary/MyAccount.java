package jangcho.dailydiary;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by CHJ on 2016. 12. 20..
 */

public class MyAccount {

    private static String PREF_NAME = "prefs";

    public MyAccount() {
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static Object getValue(Context context, String key) {
        if(key.equals("CNT")) {
            return getPrefs(context).getInt(key, 0);
        } else if(key.equals("AVAILABLE")) {
            return getPrefs(context).getBoolean(key, false);
        } else if(key.equals("FONT")) {
            return getPrefs(context).getString(key, "MILKYWAY");
        } else {
            return getPrefs(context).getString(key, "");
        }

    }

    public static void setPencilCount(Context context, int value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("CNT", value);
        editor.commit();
    }

    public static void setFontAvailable(Context context, boolean value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean("AVAILABLE", value);
        editor.commit();
    }

    public static void setFontStartTime(Context context, String value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("STARTAT", value);
        editor.commit();
    }

    public static void setFont(Context context, String value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("FONT", value);
        editor.commit();
    }
}


