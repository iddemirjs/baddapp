package com.idrisdemir.badapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ScoreScreenActivity extends AppCompatActivity {
    private TextView score,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        score=findViewById(R.id.score_text);
        time=findViewById(R.id.time_text);
    }
}