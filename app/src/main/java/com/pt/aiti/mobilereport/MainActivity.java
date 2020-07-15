package com.pt.aiti.mobilereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.pt.aiti.mobilereport.Utility.Constanta;
import com.pt.aiti.mobilereport.Utility.SessionManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayer ring= MediaPlayer.create(context,R.raw.welcome);
        ring.start();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //cek flag login
                if(SessionManager.cekLoginFlag(context)){
                    //sudah login
                    skipKeHomeActivity();
//                    pindahKeLoginActivity();
                }else {

                    pindahKeLoginActivity();
                }
                finish();
            }
        };

        //timer
        Timer timer = new Timer();
        timer.schedule(timerTask, Constanta.SPLASH_DELAY_TIME);
    }

    private void pindahKeLoginActivity(){
        Intent intent = new Intent(context, LoginActivity.class );
        startActivity(intent);
    }

    private void skipKeHomeActivity(){
//        if (SessionManager.getNIP(context).equals("cmo@bcaf.co.id")){
//            Intent intent = new Intent(context, HomeCMOActivity.class);
//            startActivity(intent);
//        }else if (SessionManager.getNIP(context).equals("bm@bcaf.co.id")){
//            Intent intent = new Intent(context, HomeBMActivity.class);
//            startActivity(intent);
//        }else if (SessionManager.getNIP(context).equals("rm@bcaf.co.id")){
//            Intent intent = new Intent(context, HomeRMActivity.class);
//            startActivity(intent);
        }
//        Intent intent = new Intent(context, HomeMenuActivity.class );
//        startActivity(intent);
//    }
}
