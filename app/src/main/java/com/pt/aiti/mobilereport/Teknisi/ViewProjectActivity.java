package com.pt.aiti.mobilereport.Teknisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pt.aiti.mobilereport.Admin.HomeAdminActivity;
import com.pt.aiti.mobilereport.Direktur.HomeDirekturActivity;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.BitmapHelper;
import com.pt.aiti.mobilereport.Utility.CustomExpandCollapseBar;
import com.pt.aiti.mobilereport.Utility.LoadingClass;
import com.pt.aiti.mobilereport.Utility.SessionManager;
import com.pt.aiti.mobilereport.Utility.inputProject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ViewProjectActivity extends AppCompatActivity {
    private Context context = this;
    private DatabaseReference reference;
    private TextView teknisi1, teknisi2, teknisi3, namaProject, lokasi, catatan, tanggalPengerjaan;
    private ImageView imageProject1, imageProject2, imageProject3;
    private Button buttonBack, buttonNext;
    private String primaryKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project);
        reference = FirebaseDatabase.getInstance().getReference("inputProject");
        primaryKey = getIntent().getExtras().getString("getPrimaryKey");

        teknisi1 = findViewById(R.id.teknisi1);
        teknisi2 = findViewById(R.id.teknisi2);
        teknisi3 = findViewById(R.id.teknisi3);
        namaProject = findViewById(R.id.namaProject);
        lokasi = findViewById(R.id.lokasi);
        catatan = findViewById(R.id.catatan);
        tanggalPengerjaan = findViewById(R.id.tanggalPengerjaan);
        imageProject1 = findViewById(R.id.imageProject1);
//        imageProject1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                lihatGambar1();
//            }
//        });
        imageProject2 = findViewById(R.id.imageProject2);
        imageProject3 = findViewById(R.id.imageProject3);
        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("getPrimaryKey", primaryKey);
                Intent intent = new Intent(context, ViewFinanceActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = SessionManager.getUsername(context);
                if (text.equalsIgnoreCase("admin")){
                    Intent intent = new Intent(context, HomeAdminActivity.class);
                    startActivity(intent);
                    finish();
                }else if (text.equalsIgnoreCase("direktur")){
                    Intent intent = new Intent(context, HomeDirekturActivity.class);
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

                    String image1 = getSnapshot.child("image1").getValue().toString();
                    String image2 = getSnapshot.child("image2").getValue().toString();
                    String image3 = getSnapshot.child("image3").getValue().toString();

                    byte[] imageBytes1, imageBytes2, imageBytes3;

                    imageBytes1 = Base64.decode(image1, Base64.DEFAULT);
                    Bitmap decodedImage1 = BitmapFactory.decodeByteArray(imageBytes1, 0, imageBytes1.length);
                    imageProject1.setImageBitmap(decodedImage1);

                    imageBytes2 = Base64.decode(image2, Base64.DEFAULT);
                    Bitmap decodedImage2 = BitmapFactory.decodeByteArray(imageBytes2, 0, imageBytes2.length);
                    imageProject2.setImageBitmap(decodedImage2);

                    imageBytes3 = Base64.decode(image3, Base64.DEFAULT);
                    Bitmap decodedImage3 = BitmapFactory.decodeByteArray(imageBytes3, 0, imageBytes3.length);
                    imageProject3.setImageBitmap(decodedImage3);
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
        }else if (text.equalsIgnoreCase("direktur")){
            Intent intent = new Intent(context, HomeDirekturActivity.class);
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
