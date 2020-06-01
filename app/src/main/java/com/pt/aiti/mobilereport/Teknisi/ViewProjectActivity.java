package com.pt.aiti.mobilereport.Teknisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pt.aiti.mobilereport.Admin.HomeAdminActivity;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.CustomExpandCollapseBar;
import com.pt.aiti.mobilereport.Utility.LoadingClass;
import com.pt.aiti.mobilereport.Utility.SessionManager;
import com.pt.aiti.mobilereport.Utility.inputProject;

import java.util.ArrayList;

public class ViewProjectActivity extends AppCompatActivity {
    private Context context = this;
    private CustomExpandCollapseBar expandCollapseBar;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ArrayList<inputProject> listSurvey;
    private TextView teknisi1, teknisi2, teknisi3, namaProject, lokasi, catatan, tanggalPengerjaan,
    keperluan1, keperluan2, keperluan3, biaya1, biaya2, biaya3, deskripsi1, deskripsi2, deskripsi3, totalbiaya;
    private ImageView imageProject1, imageProject2, imageProject3, imageBiaya1, imageBiaya2, imageBiaya3;
    private Button buttonBack;
    private String primaryKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project);
        reference = FirebaseDatabase.getInstance().getReference("inputProject");
        primaryKey = getIntent().getExtras().getString("getPrimaryKey");

        TextView barExpandProject = findViewById(R.id.barExpandRekapProject);
        ExpandableLinearLayout layoutRekapProject = findViewById(R.id.layoutExpandRekapProject);
        expandCollapseBar = new CustomExpandCollapseBar(barExpandProject, layoutRekapProject, false);
        TextView barExpandFinance = findViewById(R.id.barExpandFinance);
        ExpandableLinearLayout layoutRekapFinance = findViewById(R.id.layoutExpandFinance);
        expandCollapseBar = new CustomExpandCollapseBar(barExpandFinance, layoutRekapFinance, false);

        teknisi1 = findViewById(R.id.teknisi1);
        teknisi2 = findViewById(R.id.teknisi2);
        teknisi3 = findViewById(R.id.teknisi3);
        namaProject = findViewById(R.id.namaProject);
        lokasi = findViewById(R.id.lokasi);
        catatan = findViewById(R.id.catatan);
        tanggalPengerjaan = findViewById(R.id.tanggalPengerjaan);
        keperluan1 = findViewById(R.id.keperluan1);
        keperluan2 = findViewById(R.id.keperluan2);
        keperluan3 = findViewById(R.id.keperluan3);
        biaya1 = findViewById(R.id.biaya1);
        biaya2 = findViewById(R.id.biaya2);
        biaya3 = findViewById(R.id.biaya3);
        deskripsi1 = findViewById(R.id.Deskripsi1);
        deskripsi2 = findViewById(R.id.Deskripsi2);
        deskripsi3 = findViewById(R.id.Deskripsi3);
        totalbiaya = findViewById(R.id.totalBiaya);
        imageBiaya1 = findViewById(R.id.imageBiaya1);
        imageBiaya2 = findViewById(R.id.imageBiaya2);
        imageBiaya3 = findViewById(R.id.imageBiaya3);
        imageProject1 = findViewById(R.id.imageProject1);
        imageProject2 = findViewById(R.id.imageProject2);
        imageProject3 = findViewById(R.id.imageProject3);
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = SessionManager.getUsername(context);
                if (text.equalsIgnoreCase("admin")){
                    Intent intent = new Intent(context, HomeAdminActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(context, HomeTeknisiActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        getData();
    }

    public void getData(){
        final ProgressDialog loading = LoadingClass.loadingAnimationCustom(context);
        Query findQuery = reference.orderByKey().equalTo(primaryKey);
        findQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot getSnapshot : dataSnapshot.getChildren()){
                    teknisi1.setText(getSnapshot.child("teknisi1").getValue().toString());
                    teknisi2.setText(getSnapshot.child("teknisi2").getValue().toString());
                    teknisi3.setText(getSnapshot.child("teknisi3").getValue().toString());
                    namaProject.setText(getSnapshot.child("namaProject").getValue().toString());
                    lokasi.setText(getSnapshot.child("lokasi").getValue().toString());
                    tanggalPengerjaan.setText(getSnapshot.child("tanggalProject").getValue().toString());
                    catatan.setText(getSnapshot.child("catatan").getValue().toString());
                    totalbiaya.setText(getSnapshot.child("totalBiaya").getValue().toString());
                    biaya1.setText(getSnapshot.child("biaya1").getValue().toString());
                    biaya2.setText(getSnapshot.child("biaya2").getValue().toString());
                    biaya3.setText(getSnapshot.child("biaya3").getValue().toString());
                    keperluan1.setText(getSnapshot.child("keperluan1").getValue().toString());
                    keperluan2.setText(getSnapshot.child("keperluan2").getValue().toString());
                    keperluan3.setText(getSnapshot.child("keperluan3").getValue().toString());
                    deskripsi1.setText(getSnapshot.child("deskripsi1").getValue().toString());
                    deskripsi2.setText(getSnapshot.child("deskripsi2").getValue().toString());
                    deskripsi3.setText(getSnapshot.child("deskripsi3").getValue().toString());

                    Toast.makeText(getApplicationContext(), "Data Berhasil Dimuat", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyListActivity", databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        String text = SessionManager.getUsername(context);
        if (text.equalsIgnoreCase("admin")){
            Intent intent = new Intent(context, HomeAdminActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(context, HomeTeknisiActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
