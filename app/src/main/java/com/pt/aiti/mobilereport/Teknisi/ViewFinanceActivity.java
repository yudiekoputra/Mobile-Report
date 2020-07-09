package com.pt.aiti.mobilereport.Teknisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pt.aiti.mobilereport.Admin.HomeAdminActivity;
import com.pt.aiti.mobilereport.Direktur.HomeDirekturActivity;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.LoadingClass;
import com.pt.aiti.mobilereport.Utility.SessionManager;

import java.io.ByteArrayOutputStream;

public class ViewFinanceActivity extends AppCompatActivity {
    private Context context = this;
    private DatabaseReference reference;
    private TextView keperluan1, keperluan2, keperluan3, biaya1, biaya2, biaya3, deskripsi1, deskripsi2, deskripsi3, totalbiaya;
    private ImageView imageBiaya1, imageBiaya2, imageBiaya3;
    private Button buttonBack, buttonNext;
    private String primaryKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_finance);
        reference = FirebaseDatabase.getInstance().getReference("inputProject");
        primaryKey = getIntent().getExtras().getString("getPrimaryKey");
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
        buttonNext = findViewById(R.id.buttonBalik);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("getPrimaryKey", primaryKey);
                Intent intent = new Intent(context, ViewProjectActivity.class);
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

                    String image4 = getSnapshot.child("image4").getValue().toString();
                    String image5 = getSnapshot.child("image5").getValue().toString();
                    String image6 = getSnapshot.child("image6").getValue().toString();

                    byte[] imageBytes1, imageBytes2, imageBytes3, imageBytes4, imageBytes5, imageBytes6;

                    imageBytes4 = Base64.decode(image4, Base64.DEFAULT);
                    Bitmap decodedImage4 = BitmapFactory.decodeByteArray(imageBytes4, 0, imageBytes4.length);
                    imageBiaya1.setImageBitmap(decodedImage4);

                    imageBytes5 = Base64.decode(image5, Base64.DEFAULT);
                    Bitmap decodedImage5 = BitmapFactory.decodeByteArray(imageBytes5, 0, imageBytes5.length);
                    imageBiaya2.setImageBitmap(decodedImage5);

                    imageBytes6 = Base64.decode(image6, Base64.DEFAULT);
                    Bitmap decodedImage6 = BitmapFactory.decodeByteArray(imageBytes6, 0, imageBytes6.length);
                    imageBiaya3.setImageBitmap(decodedImage6);

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
