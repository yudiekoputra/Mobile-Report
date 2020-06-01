package com.pt.aiti.mobilereport.Teknisi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pt.aiti.mobilereport.LoginActivity;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.Constanta;
import com.pt.aiti.mobilereport.Utility.SessionManager;

import java.util.Timer;
import java.util.TimerTask;

public class HomeTeknisiActivity extends AppCompatActivity {
    private Context context = this;
    private ImageButton imageLogOut;
    private Button newProject, listProject;
    private int counter_back = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teknisi);

        TextView username = findViewById(R.id.username);
        username.setText(SessionManager.getUsername(context));

        imageLogOut = findViewById(R.id.imageLogOut);
        imageLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutConfirmation();
            }
        });

        newProject = findViewById(R.id.newProject);
        newProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewProjectActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listProject = findViewById(R.id.listProject);
        listProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListProjectActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void logoutConfirmation(){
        AlertDialog.Builder option = new AlertDialog.Builder(context);
        option.setMessage("Anda yakin mau Logout ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //jika user pilih ya
                        logoutApp();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //jika user pilih tidak
                        dialog.cancel();
                    }
                })
                .setCancelable(false);
        AlertDialog showOption = option.create();
        showOption.show();
    }

    private void logoutApp(){
        //open login activity
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        //tutup home menu activity
        finish();
    }

    //fungsi handle exit app 2 kali tap back
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(counter_back<2){
            Toast.makeText(context, "Tekan Back sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
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
