package com.pt.aiti.mobilereport.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pt.aiti.mobilereport.LoginActivity;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Teknisi.ListProjectActivity;
import com.pt.aiti.mobilereport.Teknisi.NewProjectActivity;
import com.pt.aiti.mobilereport.Utility.Constanta;
import com.pt.aiti.mobilereport.Utility.SessionManager;

import java.util.Timer;
import java.util.TimerTask;

public class HomeAdminActivity extends AppCompatActivity {
    private Context context = this;
    private ImageButton imageLogOut;
    private Button addUser, addProject, listProject;
    private int counter_back = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        TextView username = findViewById(R.id.username);
        username.setText(SessionManager.getUsername(context));

        imageLogOut = findViewById(R.id.imageLogOut);
        imageLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutConfirmation();
            }
        });

        addUser = findViewById(R.id.addUser);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddUserActivity.class);
                startActivity(intent);
            }
        });

        addProject = findViewById(R.id.addProject);
        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddProjectActivity.class);
                startActivity(intent);
            }
        });

        listProject = findViewById(R.id.listProject);
        listProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListProjectActivity.class);
                startActivity(intent);
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
        MediaPlayer ring= MediaPlayer.create(context,R.raw.ended);
        ring.start();
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
