package com.pt.aiti.mobilereport.Utility;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Constanta {
    public static int SPLASH_DELAY_TIME = 5000;
    public static int COUNTDOWN_RESET=1000;
    public static String SHARED_PREFERENCES_NAME = "shared_pref_v001";
    public static String SHARED_PREFERENCES_NAME_INT = "integer_001";

    public static String KEY_NIP = "NIP";
    public static String KEY_PASSWORD = "password";
    public static String KEY_REMEMBER = "remember";
    public static String KEY_FLAG_LOGIN = "login_flag";
    public static String KEY_USERNAME = "username";

    private ArrayList<namaProject>listProject;
    private DatabaseReference reference;

    public void getListProject(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("List Project").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listProject = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    namaProject project = snapshot.getValue(namaProject.class);
                    project.setKey(snapshot.getKey());
                    listProject.add(project);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static String [] PROJECT = {
            "-Pilih Jawaban-",
            "CCTV",
            "Fiber Optic",
            "Wifi Braodband",
            "Installasi Jaringan Internal"
    };

    public static String [] JABATAN= {
            "-Pilih Jawaban-",
            "Admin",
            "Teknisi"
    };

    //save shared preference
    public static String TEKNISI1 = "teknisi1";
    public static String TEKNISI2 = "teknisi2";
    public static String TEKNISI3 = "teknisi3";
    public static String NAMA_PROJECT = "project";
    public static String LOKASI = "lokasi";
    public static String CATATAB = "catatan";
}
