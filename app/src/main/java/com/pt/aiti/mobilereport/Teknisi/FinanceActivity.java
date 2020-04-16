package com.pt.aiti.mobilereport.Teknisi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.github.chrisbanes.photoview.PhotoView;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.BitmapHelper;

import java.io.IOException;

import static android.text.TextUtils.isEmpty;

public class FinanceActivity extends AppCompatActivity {
    private Context context = this;
    Button back, submit, hitungBiaya;
    ImageView camera1, camera2, camera3;
    EditText keperluan1, biaya1, deskripsi1, keperluan2, biaya2, deskripsi2, keperluan3, biaya3, deskripsi3;
    TextView totalBiaya;
    private int REQUEST_CODE_CAMERA1 = 1;
    private int REQUEST_CODE_CAMERA2 = 2;
    private int REQUEST_CODE_CAMERA3 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        keperluan1 = findViewById(R.id.keperluan1);
        biaya1 = findViewById(R.id.biaya1);
        deskripsi1 = findViewById(R.id.deskripsi1);
        keperluan2 = findViewById(R.id.keperluan2);
        biaya2 = findViewById(R.id.biaya2);
        deskripsi2 = findViewById(R.id.deskripsi2);
        keperluan3 = findViewById(R.id.keperluan3);
        biaya3 = findViewById(R.id.biaya3);
        deskripsi3 = findViewById(R.id.deskripsi3);
        totalBiaya = findViewById(R.id.totalbiaya);
        back = findViewById(R.id.buttonBack);
        submit = findViewById(R.id.buttonSubmit);
        hitungBiaya = findViewById(R.id.hitungBiaya);
        hitungBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                hitungTotalBiaya();
            }
        });
        camera1 = findViewById(R.id.image1);
        camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogPilihan();
            }
        });
        camera2 = findViewById(R.id.image2);
        camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogPilihan2();
            }
        });
        camera3 = findViewById(R.id.image3);
        camera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogPilihan3();
            }
        });

    }

    public void hitungTotalBiaya(){
        String biaya1Value = biaya1.getText().toString().trim();
        String biaya2Value = biaya2.getText().toString().trim();
        String biaya3Value = biaya3.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(biaya1Value)){
            isEmptyFields = true;
            biaya1.setError("Field ini tidak boleh kosong / isi 0 jika kosong");
        }
        if (TextUtils.isEmpty(biaya2Value)){
            isEmptyFields = true;
            biaya2.setError("Field ini tidak boleh kosong / isi 0 jika kosong");
        }
        if (TextUtils.isEmpty(biaya3Value)){
            isEmptyFields = true;
            biaya3.setError("Field ini tidak boleh kosong / isi 0 jika kosong");
        }
        if (!isEmptyFields) {

            int totalVlue = Integer.valueOf(biaya1Value)+Integer.valueOf(biaya2Value)+Integer.valueOf(biaya3Value);
            totalBiaya.setText(String.valueOf(totalVlue));
        }
    }

    private void setDialogPilihan(){
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(checkPermissionCamera()){
                    ambilImagedariCamera();
                }
            }
        });
        myAlertDialog.setNeutralButton("Lihat Gambar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                lihatGambar1();
            }
        });
        myAlertDialog.show();
    }

    private void setDialogPilihan2(){
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ambilImagedariCamera2();
            }
        });
        myAlertDialog.setNeutralButton("Lihat Gambar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                lihatGambar2();
            }
        });
        myAlertDialog.show();
    }

    private void setDialogPilihan3(){
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ambilImagedariCamera3();
            }
        });
        myAlertDialog.setNeutralButton("Lihat Gambar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                lihatGambar3();
            }
        });
        myAlertDialog.show();
    }

    //runtime permission for camera access
    public boolean checkPermissionCamera(){
        int currentAPIVersion = Build.VERSION.SDK_INT;

        if(currentAPIVersion >= Build.VERSION_CODES.M){
            //lakukan cek permission
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                //tampilkan dialog untuk minta izin permission
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA1);

                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_CODE_CAMERA1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //diijinkan
                ambilImagedariCamera();
            }else{
                //tidak diijinkan
                Toast.makeText(context, "Anda harus memberikan izin akses camera", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode==REQUEST_CODE_CAMERA2){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //diijinkan
                ambilImagedariCamera2();
            }else{
                //tidak diijinkan
                Toast.makeText(context, "Anda harus memberikan izin akses camera", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode==REQUEST_CODE_CAMERA3) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //diijinkan
                ambilImagedariCamera3();
            } else {
                //tidak diijinkan
                Toast.makeText(context, "Anda harus memberikan izin akses camera", Toast.LENGTH_SHORT).show();
            }
//        }else if(requestCode==REQUEST_CODE_GALERY1){
//            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                //diijinkan
//                ambilImagedariGalery();
//            }else{
//                //tidak diijinkan
//                Toast.makeText(context, "Anda harus memberikan izin akses", Toast.LENGTH_SHORT).show();
//            }
        }else{
            //
        }
    }
    private void ambilImagedariCamera(){
        ImagePicker.cameraOnly().start((Activity) context, REQUEST_CODE_CAMERA1);
    }
    private void ambilImagedariCamera2(){
        ImagePicker.cameraOnly().start((Activity) context, REQUEST_CODE_CAMERA2);
    }
    private void ambilImagedariCamera3(){
        ImagePicker.cameraOnly().start((Activity) context, REQUEST_CODE_CAMERA3);
    }

    //untuk menangkap balikan dari kamera & galery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            if (requestCode == REQUEST_CODE_CAMERA1) {
                //tangkapimage dari kamera dan tampilkan di activity camera
                if (image != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap4 = BitmapFactory.decodeFile(image.getPath(), options);

                    Glide.with(context).load(bitmap4).into(camera1);
                    BitmapHelper.getInstance().setBitmap4(bitmap4);
                }
            }
            else if (requestCode == REQUEST_CODE_CAMERA2) {
                //tangkapimage dari kamera dan tampilkan di activity camera
                if (image != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap5 = BitmapFactory.decodeFile(image.getPath(), options);

                    Glide.with(context).load(bitmap5).into(camera2);
                    BitmapHelper.getInstance().setBitmap5(bitmap5);
                }
            }
            if (requestCode == REQUEST_CODE_CAMERA3) {
                //tangkapimage dari kamera dan tampilkan di activity camera
                if (image != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap6 = BitmapFactory.decodeFile(image.getPath(), options);

                    Glide.with(context).load(bitmap6).into(camera3);
                    BitmapHelper.getInstance().setBitmap6(bitmap6);
                }
            }
        }
    }

    private void lihatGambar1(){
        if(camera1 != null){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            View mView = getLayoutInflater().inflate(R.layout.custom_foto_pinch, null);
            PhotoView photoView = mView.findViewById(R.id.imageView);
            photoView.setImageBitmap(BitmapHelper.getInstance().getBitmap4());
            mBuilder.setView(mView);
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }else {

        }
    }
    private void lihatGambar2(){
        if(camera1 != null){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            View mView = getLayoutInflater().inflate(R.layout.custom_foto_pinch, null);
            PhotoView photoView = mView.findViewById(R.id.imageView);
            photoView.setImageBitmap(BitmapHelper.getInstance().getBitmap5());
            mBuilder.setView(mView);
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }else {

        }
    }
    private void lihatGambar3(){
        if(camera1 != null){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            View mView = getLayoutInflater().inflate(R.layout.custom_foto_pinch, null);
            PhotoView photoView = mView.findViewById(R.id.imageView);
            photoView.setImageBitmap(BitmapHelper.getInstance().getBitmap6());
            mBuilder.setView(mView);
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }else {

        }
    }
}
