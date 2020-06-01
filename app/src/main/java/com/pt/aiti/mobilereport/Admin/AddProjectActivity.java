package com.pt.aiti.mobilereport.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Teknisi.HomeTeknisiActivity;
import com.pt.aiti.mobilereport.Utility.AddUser;
import com.pt.aiti.mobilereport.Utility.LoadingClass;
import com.pt.aiti.mobilereport.Utility.namaProject;

public class AddProjectActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private Context context = this;
    EditText namaProject;
    Button add, back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        mAuth = FirebaseAuth.getInstance();
        namaProject = findViewById(R.id.namaProject);
        add = findViewById(R.id.buttonAdd);
        add.setOnClickListener(this);
        back = findViewById(R.id.buttonBack);
    }

    private void registerProject() {
        String namaProjectValue = namaProject.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference;

        final ProgressDialog loading = LoadingClass.loadingAnimationCustom(context);
        loading.show();
        getReference = database.getReference();
        if (namaProjectValue.isEmpty()) {
            namaProject.setError("Field ini tidak boleh kosong");
            namaProject.requestFocus();
        }else {
            com.pt.aiti.mobilereport.Utility.namaProject addProject = new namaProject(namaProjectValue);
            getReference.child("ListProject").push()
                    .setValue(addProject).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loading.dismiss();
                    if (task.isSuccessful()){
                        Toast.makeText(context, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, HomeAdminActivity.class);
                        startActivity(intent);
                        finish();
                    }else{}
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                registerProject();
                break;
        }
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
        Intent intent = new Intent(context, HomeAdminActivity.class);
        startActivity(intent);
        finish();
    }

}
