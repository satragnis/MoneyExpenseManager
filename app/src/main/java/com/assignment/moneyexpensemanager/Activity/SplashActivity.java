package com.assignment.moneyexpensemanager.Activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.assignment.moneyexpensemanager.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startTimer();
    }


    public void startTimer() {
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.d("seconds remaining: ", ">>>> " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                moveToHome();
                //  getPrefetchData();

            }
        }.start();
    }

    private void moveToHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
}
