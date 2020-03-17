package com.pt.aiti.mobilereport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pt.aiti.mobilereport.Admin.HomeAdminActivity;
import com.pt.aiti.mobilereport.Teknisi.HomeTeknisiActivity;
import com.pt.aiti.mobilereport.Utility.Constanta;
import com.pt.aiti.mobilereport.Utility.LoadingClass;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    EditText email, password;
    Button buttonLogin;
    private int counter_back = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email= findViewById(R.id.email);
        password = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if(i == R.id.buttonLogin){
            signIn();
        }
    }

    private void signIn(){
        ProgressDialog loading = LoadingClass.loadingAnimationCustom(context);
        loading.show();
        String emailValue = email.getText().toString();
        String pwdValue = password.getText().toString();

        //sementara
        if (emailValue.equalsIgnoreCase("teknisi")){
            loading.dismiss();
            Intent intent = new Intent(context, HomeTeknisiActivity.class);
            startActivity(intent);
//            finish();
        }else if (emailValue.equalsIgnoreCase("admin")){
            loading.dismiss();
            Intent intent = new Intent(context, HomeAdminActivity.class);
            startActivity(intent);
//            finish();
        }else{
            loading.dismiss();
            Toast.makeText(context, "Email or Password is incorrect", Toast.LENGTH_SHORT).show();
        }

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
