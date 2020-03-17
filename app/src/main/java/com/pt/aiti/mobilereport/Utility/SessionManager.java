package com.pt.aiti.mobilereport.Utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    protected static SharedPreferences retrieveSharedPreferences(Context context){
        return context.getSharedPreferences(Constanta.SHARED_PREFERENCES_NAME, context.MODE_PRIVATE);
    }

    //mode editor(CRUD)
    protected static SharedPreferences.Editor retrieveSharedPreferencesEditor(Context context){
        return retrieveSharedPreferences(context).edit();
    }

    /*getter & setter
    definisikan fungsi getter & setter sesuai keperluan aplikasi
     */
    //save data login
    public static void saveDataLogin(Context context, String NIP, String password, boolean remember){
        SharedPreferences.Editor editor = retrieveSharedPreferencesEditor(context);
        editor.putString(Constanta.KEY_NIP, NIP);
        editor.putString(Constanta.KEY_PASSWORD, password);
        editor.putBoolean(Constanta.KEY_REMEMBER, remember);

        editor.commit();
    }

    //save flag login
    public static void saveLoginFlag(Context context, boolean login){
        SharedPreferences.Editor editor = retrieveSharedPreferencesEditor(context);
        editor.putBoolean(Constanta.KEY_FLAG_LOGIN, login);

        editor.commit();
    }

    //get data (getter)

    //ambil flag login
    public static boolean cekLoginFlag(Context context){
        return retrieveSharedPreferences(context).getBoolean(Constanta.KEY_FLAG_LOGIN, false);
    }

    public static String getUsername(Context context){
        return retrieveSharedPreferences(context).getString(Constanta.KEY_USERNAME, "");
    }

}
