package com.idrisdemir.badapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.idrisdemir.badapp.Fragments.HomeFragment;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;

public class ScoreScreenActivity extends AppCompatActivity {
    private TextView score,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        score=findViewById(R.id.score_text);
        time=findViewById(R.id.time_text);
        Button rewardOutButton = (Button) findViewById(R.id.exit_winner_screen);
        rewardOutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent goHomeFragment=new Intent(ScoreScreenActivity.this, HomeFragment.class);
                startActivity(goHomeFragment);
            }
        });
    }
}