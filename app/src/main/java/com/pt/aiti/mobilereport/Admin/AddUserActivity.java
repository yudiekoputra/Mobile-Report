package com.pt.aiti.mobilereport.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pt.aiti.mobilereport.MainActivity;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.AddUser;
import com.pt.aiti.mobilereport.Utility.Constanta;
import com.pt.aiti.mobilereport.Utility.LoadingClass;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener  {
    private Context context = this;
    private Spinner jabatanUser;
    EditText namaUser, emailUser, passwordUser;
    Button add, back;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        mAuth = FirebaseAuth.getInstance();

        namaUser = findViewById(R.id.namaUser);
        emailUser = findViewById(R.id.emailUser);
        passwordUser = findViewById(R.id.passwordUser);
        add = findViewById(R.id.buttonAdd);
        add.setOnClickListener(this);
        back = findViewById(R.id.buttonBack);
        jabatanUser = findViewById(R.id.jabatan);

        setSpinnerData();
    }

    private void registerUser() {
        final String name = namaUser.getText().toString().trim();
        final String email = emailUser.getText().toString().trim();
        String password = passwordUser.getText().toString().trim();
        final String jabatanValue = jabatanUser.getSelectedItem().toString();

        if (name.isEmpty()) {
            namaUser.setError("Field ini tidak boleh kosong");
            namaUser.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailUser.setError("Field ini tidak boleh kosong");
            emailUser.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailUser.setError("Format Email tidak sesuai");
            emailUser.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordUser.setError("Field ini tidak boleh kosong");
            passwordUser.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordUser.setError("Password minimal harus 6 karakter");
            passwordUser.requestFocus();
            return;
        }

        if (jabatanUser.getSelectedItemId()==0) {
            Toast.makeText(context, "Anda belum memilih jabatan", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog loading = LoadingClass.loadingAnimationCustom(context);
        loading.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            AddUser user = new AddUser(
                                    name,
                                    email,
                                    jabatanValue
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    loading.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "User berhasil ditambahkan", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, HomeAdminActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void setSpinnerData() {
        ArrayAdapter<String> adapterProject = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, Constanta.JABATAN);
        adapterProject.setDropDownViewResource(android.R.layout.simple_spinner_item);
        jabatanUser.setAdapter(adapterProject);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                registerUser();
                break;
        }
    }
}
