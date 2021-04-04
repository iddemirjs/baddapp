package com.idrisdemir.badapp.Entity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }

}