package com.example.hp.gotohealth;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefManager {

    public static final String SP_JUST_HELATH = "spJustHealth";
    public static final String SP_USERNAME = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_JK = "spJk";
    public static final String SP_TGL_LAHIR = "spTglLahir";
    public static final String SP_HAS_LOGIN = "spHasLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharePrefManager(Context context) {
        this.sp = context.getSharedPreferences(SP_JUST_HELATH, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpNama() {
        return sp.getString(SP_USERNAME, "");
    }

    public String getSpEmail() {
        return sp.getString(SP_EMAIL, "");
    }

    public Boolean getSpHasLogin() {
        return sp.getBoolean(SP_HAS_LOGIN, false);
    }
}
