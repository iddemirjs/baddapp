package com.idrisdemir.badapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.idrisdemir.badapp.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Welcome extends AppCompatActivity {
    private int loginState = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView iv= (ImageView) findViewById(R.id.imageView);
        Animation uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        iv.setAnimation(uptodown);

        TextView tv = (TextView) findViewById(R.id.logo);
        Animation downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        tv.setAnimation(downtoup);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextStage;
                if (loginState == 0){
                    nextStage = new Intent(Welcome.this,LoginActivity.class);
                }else{
                    nextStage = new Intent(Welcome.this,MainActivity.class);
                }
                startActivity(nextStage);
            }
        },2500);
    }

}