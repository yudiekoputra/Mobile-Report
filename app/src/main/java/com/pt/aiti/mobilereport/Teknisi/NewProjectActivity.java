package com.pt.aiti.mobilereport.Teknisi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pt.aiti.mobilereport.MainActivity;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.BitmapHelper;
import com.pt.aiti.mobilereport.Utility.Constanta;
import com.pt.aiti.mobilereport.Utility.SessionManager;
import com.pt.aiti.mobilereport.Utility.namaProject;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.wingsofts.dragphotoview.DragPhotoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewProjectActivity extends AppCompatActivity {
    private Context context = this;
    private Spinner project;
    EditText teknisi1, teknisi2, teknisi3, lokasi, catatan;
    ImageView camera1, camera2, camera3;
    Button next, back;
    private int REQUEST_CODE_CAMERA1 = 1;
    private int REQUEST_CODE_CAMERA2 = 2;
    private int REQUEST_CODE_CAMERA3 = 3;
    private int REQUEST_CODE_GALERY1 = 1;
    private int REQUEST_CODE_GALERY2 = 2;
    private int REQUEST_CODE_GALERY3 = 3;

    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        Fresco.initialize(context);

        teknisi1 = findViewById(R.id.teknisi1);
        teknisi2 = findViewById(R.id.teknisi2);
        teknisi3 = findViewById(R.id.teknisi3);
        lokasi = findViewById(R.id.lokasi);
        catatan = findViewById(R.id.catatan);

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
        camera3 =  findViewById(R.id.image3);
        camera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogPilihan3();
            }
        });

        back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        next = findViewById(R.id.buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewProjet();
            }
        });

        project = findViewById(R.id.project);
        setSpinnerData();
    }

    private void saveNewProjet (){
        String teknisi1Value = teknisi1.getText().toString();
        String teknisi2Value = teknisi2.getText().toString();
        String teknisi3Value = teknisi3.getText().toString();
        String projectValue = project.getSelectedItem().toString();
        String lokasiValue = lokasi.getText().toString();
        String catatanValue = catatan.getText().toString();

        SessionManager.saveNewProject(context, teknisi1Value, teknisi2Value, teknisi3Value,
                projectValue, lokasiValue, catatanValue);
        convertImageString1();
        convertImageString2();
        convertImageString3();
        Intent intent = new Intent(context, FinanceActivity.class);
        startActivity(intent);
    }

    public void convertImageString1(){
        Bitmap bitmap = BitmapHelper.getInstance().getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imagebytes = baos.toByteArray();
        String image1 = Base64.encodeToString(imagebytes, Base64.DEFAULT);
        SessionManager.saveImage1(context, image1);
    }
    public void convertImageString2(){
        Bitmap bitmap2 = BitmapHelper.getInstance().getBitmap2();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap2.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes2 = baos.toByteArray();
        String image2 = Base64.encodeToString(imageBytes2, Base64.DEFAULT);
        SessionManager.saveImage2(context, image2);
    }
    public void convertImageString3(){
        Bitmap bitmap3 = BitmapHelper.getInstance().getBitmap3();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap3.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes3 = baos.toByteArray();
        String image3 = Base64.encodeToString(imageBytes3, Base64.DEFAULT);
        SessionManager.saveImage3(context, image3);
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
//                if(checkPermissionCamera()){
                    ambilImagedariCamera2();
//                }
            }
        });
//        myAlertDialog.setNegativeButton("Galery", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
////                ambilImagedariGalery2();
//            }
//        });
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
//                if(checkPermissionCamera()){
                    ambilImagedariCamera3();
//                }
            }
        });
//        myAlertDialog.setNegativeButton("Galery", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
////                ambilImagedariGalery3();
//            }
//        });
        myAlertDialog.setNeutralButton("Lihat Gambar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                lihatGambar3();
            }
        });
        myAlertDialog.show();
    }

    public boolean checkPermissionGalery(){
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALERY1);
                return false;
            }else{
                return true;
            }

        }else{
            return true;
        }
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
        }else if(requestCode==REQUEST_CODE_GALERY1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //diijinkan
                ambilImagedariGalery();
            }else{
                //tidak diijinkan
                Toast.makeText(context, "Anda harus memberikan izin akses", Toast.LENGTH_SHORT).show();
            }
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

    private void ambilImagedariGalery(){
//        ImagePicker.create((Activity) context)
//                .multi()
//                .folderMode(true)
//                .start(REQUEST_CODE_GALERY1);
        Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galery.setType("image/*");
        galery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galery, "Pilih foto"), REQUEST_CODE_GALERY1);
    }
    private void ambilImagedariGalery2(){
        ImagePicker.create((Activity) context)
                .multi()
                .folderMode(true)
                .start(REQUEST_CODE_GALERY2);
    }
    private void ambilImagedariGalery3(){
        ImagePicker.create((Activity) context)
                .multi()
                .folderMode(true)
                .start(REQUEST_CODE_GALERY3);
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
                    Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), options);

                    Glide.with(context).load(bitmap).into(camera1);
                    BitmapHelper.getInstance().setBitmap(bitmap);

                }
            }else if (requestCode == REQUEST_CODE_GALERY1) {
                if(data != null){
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());

                        Glide.with(context).load(bitmap).into(camera1);
                        BitmapHelper.getInstance().setBitmap(bitmap);
                    }catch (IOException x){
                        System.out.println("Gagal ambil dari gallery : "+x.getMessage());
                    }
                }
            }
            else if (requestCode == REQUEST_CODE_CAMERA2) {
                //tangkapimage dari kamera dan tampilkan di activity camera
                if (image != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap2 = BitmapFactory.decodeFile(image.getPath(), options);

                    Glide.with(context).load(bitmap2).into(camera2);
                    BitmapHelper.getInstance().setBitmap2(bitmap2);
                }
            }else if (requestCode == REQUEST_CODE_GALERY2) {
                if (image != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap2 = BitmapFactory.decodeFile(image.getPath(), options);

                    Glide.with(context).load(bitmap2).into(camera2);
                    BitmapHelper.getInstance().setBitmap2(bitmap2);

                }
            }
            if (requestCode == REQUEST_CODE_CAMERA3) {
                //tangkapimage dari kamera dan tampilkan di activity camera
                if (image != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap3 = BitmapFactory.decodeFile(image.getPath(), options);

                    Glide.with(context).load(bitmap3).into(camera3);
                    BitmapHelper.getInstance().setBitmap3(bitmap3);
                }
            }else if (requestCode == REQUEST_CODE_GALERY3) {
                if (image != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap3 = BitmapFactory.decodeFile(image.getPath(), options);

                    Glide.with(context).load(bitmap3).into(camera3);
                    BitmapHelper.getInstance().setBitmap3(bitmap3);
                }
            }
        }
    }

    private void lihatGambar1(){
        if(camera1 != null){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            View mView = getLayoutInflater().inflate(R.layout.custom_foto_pinch, null);
            PhotoView photoView = mView.findViewById(R.id.imageView);
            photoView.setImageBitmap(BitmapHelper.getInstance().getBitmap());
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
            photoView.setImageBitmap(BitmapHelper.getInstance().getBitmap2());
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
            photoView.setImageBitmap(BitmapHelper.getInstance().getBitmap3());
            mBuilder.setView(mView);
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }else {

        }
    }

    private void setSpinnerData() {

        ArrayAdapter<String> adapterProject = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Constanta.PROJECT);
        adapterProject.setDropDownViewResource(android.R.layout.simple_spinner_item);
        project.setAdapter(adapterProject);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder option = new AlertDialog.Builder(context);
        option.setMessage("Apakah Anda yakin ingin membatalkan input project ini dan kembali ke Menu ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        backKeHome();
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setCancelable(true);
        AlertDialog showOption = option.create();
        showOption.show();
    }

    private void backKeHome(){
        Intent intent = new Intent(context, HomeTeknisiActivity.class);
        startActivity(intent);
        finish();
    }


}
