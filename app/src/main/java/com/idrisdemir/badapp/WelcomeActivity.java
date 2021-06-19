package com.idrisdemir.badapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {
    private int loginState = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView iv= (ImageView) findViewById(R.id.logo);
        Animation uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        iv.setAnimation(uptodown);

        ImageView title=(ImageView) findViewById(R.id.title_splash);
        Animation downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        title.setAnimation(downtoup);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String loginUser = sharedPref.getString("login","nologin");
        if (loginUser.equals("nologin")) loginState = 0;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextStage;
                if (loginState == 0){
                    nextStage = new Intent(WelcomeActivity.this,LoginActivity.class);
                }else{
                    nextStage = new Intent(WelcomeActivity.this,DashboardActivity.class);
                }
                startActivity(nextStage);
            }
        },2500);
    }

}