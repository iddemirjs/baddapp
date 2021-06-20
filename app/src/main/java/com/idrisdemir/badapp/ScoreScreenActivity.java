package com.idrisdemir.badapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idrisdemir.badapp.Entity.QuizResult;
import com.idrisdemir.badapp.Fragments.HomeFragment;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;

public class ScoreScreenActivity extends AppCompatActivity {
    private TextView reward, time, result;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QuizResult quizResult = (QuizResult) getIntent().getSerializableExtra("quizResult");

        setContentView(R.layout.activity_score_screen);

        if (!quizResult.isSuccess()) {
            ConstraintLayout scoreScreenContainer = (ConstraintLayout) findViewById(R.id.score_screen_container);
            ImageView rewardImageView = (ImageView) findViewById(R.id.reward_iv);
            ImageView scoreImageView = (ImageView) findViewById(R.id.yourscore_iv);
            ImageView timeImageView = (ImageView) findViewById(R.id.time_iv);
            ImageView centerImageView = (ImageView) findViewById(R.id.center_iv);
            ImageView congratulationsImageView = (ImageView) findViewById(R.id.congratulations_iv);

            scoreScreenContainer.setBackgroundResource(R.drawable.lose_background);
            rewardImageView.setImageDrawable(getDrawable(R.drawable.lose_reward));
            scoreImageView.setImageDrawable(getDrawable(R.drawable.lose_result));
            timeImageView.setImageDrawable(getDrawable(R.drawable.lose_time));
            congratulationsImageView.setImageDrawable(getDrawable(R.drawable.lose_screen_title));
            centerImageView.setImageDrawable(getDrawable(R.drawable.lose_moneys));
        }
        reward = findViewById(R.id.reward_text);
        time = findViewById(R.id.time_text);
        result = findViewById(R.id.result_text);

        reward.setText(String.valueOf(quizResult.getProfit()));
        int minute = quizResult.getElapsedTime() / 60;
        int second = quizResult.getElapsedTime() % 60;
        String minuteStr = String.valueOf(minute), secondStr = String.valueOf(second);
        if (minute < 10) minuteStr = "0" + String.valueOf(minute);
        if (second < 10) secondStr = "0" + String.valueOf(second);
        time.setText(minuteStr + ":" + secondStr);
        result.setText(String.valueOf(quizResult.getCorrectAnswerNumber()));

        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.buttonclick2);

        Button rewardOutButton = (Button) findViewById(R.id.exit_winner_screen);
        rewardOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent goHomeFragment = new Intent(ScoreScreenActivity.this, DashboardActivity.class);
                startActivity(goHomeFragment);
            }
        });
    }

}