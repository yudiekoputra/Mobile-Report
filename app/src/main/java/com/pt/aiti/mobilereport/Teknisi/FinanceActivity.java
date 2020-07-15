package com.pt.aiti.mobilereport.Teknisi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.BitmapHelper;
import com.pt.aiti.mobilereport.Utility.LoadingClass;
import com.pt.aiti.mobilereport.Utility.NumberTextWatcherForThousand;
import com.pt.aiti.mobilereport.Utility.NumberTextWatcherForThousandTv;
import com.pt.aiti.mobilereport.Utility.SessionManager;
import com.pt.aiti.mobilereport.Utility.inputProject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;

public class FinanceActivity extends AppCompatActivity {
    private Context context = this;
    Button back, submit, hitungBiaya;
    ImageView camera1, camera2, camera3;
    EditText keperluan1, biaya1, deskripsi1, keperluan2, biaya2, deskripsi2, keperluan3, biaya3, deskripsi3;
    TextView totalBiaya, tanggalProject;
    private int REQUEST_CODE_CAMERA1 = 1;
    private int REQUEST_CODE_CAMERA2 = 2;
    private int REQUEST_CODE_CAMERA3 = 3;
    private StorageReference storageReference;
    private String storageUrl;
    Double aDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        storageReference = FirebaseStorage.getInstance().getReference();

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

        biaya1.addTextChangedListener(new NumberTextWatcherForThousand(biaya1));
        biaya2.addTextChangedListener(new NumberTextWatcherForThousand(biaya2));
        biaya3.addTextChangedListener(new NumberTextWatcherForThousand(biaya3));

        String tanggalSekarang = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        tanggalProject = findViewById(R.id.tanggalProject);
        tanggalProject.setText(tanggalSekarang);

        back = findViewById(R.id.buttonBack);
        submit = findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                submitProject();
                uploadFoto();
            }
        });
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

//    public void uploadFoto(){
//        Bitmap uri1 =BitmapHelper.getInstance().getBitmap();
//        String image1Value = ("gambarProject1");
//        String image2Value = ("gambarProject2");
//        String image3Value = ("gambarProject3");
//        String image4Value = ("gambarBiaya1");
//        String image5Value = ("gambarBiaya2");
//        String image6Value = ("gambarBiaya3");
//        final StorageReference reference1 = storageReference.child("project/"+image1Value);
//        final StorageReference reference2 = storageReference.child("project/"+image2Value);
//        final StorageReference reference3 = storageReference.child("project/"+image3Value);
//        final StorageReference reference4 = storageReference.child("finance/"+image4Value);
//        final StorageReference reference5 = storageReference.child("finance/"+image5Value);
//        final StorageReference reference6 = storageReference.child("finance/"+image6Value);
//
////        UploadTask uploadTask = reference1.putStream(uri1());
//    }

    public void uploadFoto(){
        convertImageString4();
        convertImageString5();
        convertImageString6();
        submitProject();
    }

    public void submitProject(){
        final ProgressDialog loading = LoadingClass.loadingAnimationCustom(context);
        loading.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference;

        String keperluan1Value = keperluan1.getText().toString();
        String keperluan2Value = keperluan2.getText().toString();
        String keperluan3Value = keperluan3.getText().toString();
        String biaya1Value = biaya1.getText().toString();
        String biaya2Value = biaya2.getText().toString();
        String biaya3Value = biaya3.getText().toString();
        String deskripsi1Value = deskripsi1.getText().toString();
        String deskripsi2Value = deskripsi2.getText().toString();
        String deskripsi3Value = deskripsi3.getText().toString();
        String totalBiayaValue = totalBiaya.getText().toString();
        String teknisi1Value = SessionManager.getTeknisi1(context);
        String teknisi2Value = SessionManager.getTeknisi2(context);
        String teknisi3Value = SessionManager.getTeknisi3(context);
        String namaProjectValue = SessionManager.getNamaProject(context);
        String lokasiValue = SessionManager.getLokasi(context);
        String catatanValue = SessionManager.getCatatan(context);
        String tanggalProjectValue = tanggalProject.getText().toString();
        String image1Value = SessionManager.getImage1(context);
        String image2Value = SessionManager.getImage2(context);
        String image3Value = SessionManager.getImage3(context);
        String image4Value = SessionManager.getImage4(context);
        String image5Value = SessionManager.getImage5(context);
        String image6Value = SessionManager.getImage6(context);
//        storageUrl

        getReference = database.getReference();

        getReference.child("inputProject").push()
                .setValue(new inputProject(keperluan1Value, keperluan2Value, keperluan3Value, biaya1Value, biaya2Value, biaya3Value,
                        deskripsi1Value, deskripsi2Value, deskripsi3Value, totalBiayaValue, teknisi1Value, teknisi2Value, teknisi3Value,
                        namaProjectValue, lokasiValue, catatanValue, tanggalProjectValue, image1Value, image2Value, image3Value,
                        image4Value, image5Value, image6Value)).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                MediaPlayer ring= MediaPlayer.create(context,R.raw.saveinput);
                ring.start();
                Toast.makeText(context, "Data Tersimpan", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, HomeTeknisiActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void convertImageString4(){
        Bitmap bitmap4 = BitmapHelper.getInstance().getBitmap4();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap4.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imagebytes4 = baos.toByteArray();
        String image4 = Base64.encodeToString(imagebytes4, Base64.DEFAULT);
        SessionManager.saveImage4(context, image4);
    }

    public void convertImageString5(){
        Bitmap bitmap5 = BitmapHelper.getInstance().getBitmap5();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap5.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes5 = baos.toByteArray();
        String image5 = Base64.encodeToString(imageBytes5, Base64.DEFAULT);
        SessionManager.saveImage5(context, image5);
    }

    public void convertImageString6(){
        Bitmap bitmap6 = BitmapHelper.getInstance().getBitmap6();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap6.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes6 = baos.toByteArray();
        String image6 = Base64.encodeToString(imageBytes6, Base64.DEFAULT);
        SessionManager.saveImage6(context, image6);
    }

    public void hitungTotalBiaya(){
//        String biaya1Value = biaya1.getText().toString().trim();
//        String biaya2Value = biaya2.getText().toString().trim();
//        String biaya3Value = biaya3.getText().toString().trim();
        totalBiaya.addTextChangedListener(new NumberTextWatcherForThousandTv(totalBiaya));
        String biaya1Value = NumberTextWatcherForThousand.trimCommaOfString(biaya1.getText().toString());
        String biaya2Value = NumberTextWatcherForThousand.trimCommaOfString(biaya2.getText().toString());
        String biaya3Value = NumberTextWatcherForThousand.trimCommaOfString(biaya3.getText().toString());

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, HomeTeknisiActivity.class);
        startActivity(intent);
        finish();
    }
}
