package org.tup.safeplace;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.tup.safeplace.Authentication.UserInfoRegisterActivity;
import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.Onboarding.OnboardingActivity;


public class MainActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashScreenTheme);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);
                boolean isRegistered = userPref.getBoolean("isRegistered", false);


                if (isLoggedIn) {
                    if(isRegistered){
                        startActivity(new Intent(MainActivity.this, SafePlaceHomeScreenActivity.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(MainActivity.this, UserInfoRegisterActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(MainActivity.this, OnboardingActivity.class));
                    finish();
                }







            }
        }, 1000);


    }


}