package com.pt.aiti.mobilereport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pt.aiti.mobilereport.Admin.HomeAdminActivity;
import com.pt.aiti.mobilereport.Teknisi.HomeTeknisiActivity;
import com.pt.aiti.mobilereport.Utility.Constanta;
import com.pt.aiti.mobilereport.Utility.LoadingClass;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Context context = this;
    EditText email, password;
    Button buttonLogin;
    private int counter_back = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
//        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        email= findViewById(R.id.email);
        password = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);

//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if(i == R.id.buttonLogin){
            signIn();
        }
    }

    private void signIn(){
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        final ProgressDialog loading = LoadingClass.loadingAnimationCustom(context);
        loading.show();
        String emailValue = email.getText().toString();
        String pwdValue = password.getText().toString();

        mAuth.signInWithEmailAndPassword(emailValue, pwdValue)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        loading.dismiss();
                        if (task.isSuccessful()){
                            onAuthSuccess(task.getResult().getUser());
                        }else{
                            Toast.makeText(context, "Email or Password is incorrect",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //sementara
//        if (emailValue.equalsIgnoreCase("teknisi")){
//            loading.dismiss();
//            Intent intent = new Intent(context, HomeTeknisiActivity.class);
//            startActivity(intent);
//            finish();
//        }else if (emailValue.equalsIgnoreCase("admin")){
//            loading.dismiss();
//            Intent intent = new Intent(context, HomeAdminActivity.class);
//            startActivity(intent);
//            finish();
//        }else{
//            loading.dismiss();
//            Toast.makeText(context, "Email or Password is incorrect", Toast.LENGTH_SHORT).show();
//        }

    }

    //fungsi dipanggil ketika proses Authentikasi berhasil
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // membuat User admin baru
//        writeNewAdmin(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        if (username.equalsIgnoreCase("teknisi")){
            startActivity(new Intent(context, HomeTeknisiActivity.class));
            finish();

        }else{
            startActivity(new Intent(context, HomeAdminActivity.class));
            finish();
        }
    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

//    // menulis ke Database
//    private void writeNewAdmin(String userId, String name, String email) {
//        Admin admin = new Admin(name, email);
//
//        mDatabase.child("admins").child(userId).setValue(admin);
//    }


    //fungsi untuk memvalidasi EditText email dan password agar tak kosong dan sesuai format
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Required");
            result = false;
        } else {
            email.setError(null);
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Required");
            result = false;
        } else {
            password.setError(null);
        }

        return result;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(counter_back<2){
            Toast.makeText(context, "Tekan Kembail sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
            counter_back++;
            countdownReset();
        }else if(counter_back==2){
            finish();
        }
    }
    private void countdownReset(){
        TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {
                counter_back=1;
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, Constanta.COUNTDOWN_RESET);
    }
}
