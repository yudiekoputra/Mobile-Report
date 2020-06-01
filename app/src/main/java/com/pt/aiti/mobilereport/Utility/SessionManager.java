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

    public static void saveNewProject(Context context, String teknisi1, String teknisi2, String teknisi3,
                                      String project, String lokasi, String catatan){
        SharedPreferences.Editor editor = retrieveSharedPreferencesEditor(context);
        editor.putString(String.valueOf(Constanta.TEKNISI1), teknisi1);
        editor.putString(String.valueOf(Constanta.TEKNISI2), teknisi2);
        editor.putString(String.valueOf(Constanta.TEKNISI3), teknisi3);
        editor.putString(String.valueOf(Constanta.NAMA_PROJECT), project);
        editor.putString(String.valueOf(Constanta.LOKASI), lokasi);
        editor.putString(String.valueOf(Constanta.CATATAB), catatan);

        editor.commit();
    }

    public static void saveUsername(Context context, String username){
        SharedPreferences.Editor editor = retrieveSharedPreferencesEditor(context);
        editor.putString(Constanta.KEY_USERNAME, username);
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

    public static String getTeknisi1(Context context){
        return retrieveSharedPreferences(context).getString(Constanta.TEKNISI1, "");
    }

    public static String getTeknisi2(Context context){
        return retrieveSharedPreferences(context).getString(Constanta.TEKNISI2, "");
    }

    public static String getTeknisi3(Context context){
        return retrieveSharedPreferences(context).getString(Constanta.TEKNISI3, "");
    }

    public static String getNamaProject(Context context){
        return retrieveSharedPreferences(context).getString(Constanta.NAMA_PROJECT, "");
    }

    public static String getLokasi(Context context){
        return retrieveSharedPreferences(context).getString(Constanta.LOKASI, "");
    }

    public static String getCatatan(Context context){
        return retrieveSharedPreferences(context).getString(Constanta.CATATAB, "");
    }

}
