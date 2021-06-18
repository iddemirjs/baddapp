package com.idrisdemir.badapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Entity.Question;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private ArrayList<Question> questionArrayList;
    private TextView cardQuestion,optionA,optionB,optionC,optionD,questcount,timeTV;
    private TextView[] optionsTextViewArray = new TextView[4];
    private CardView card0A,card0B,card0C,card0D;
    private Button nextQuestionButton;
    private int index=0;
    private String[] options=new String[4];
    private String lastClicked=null;
    private DatabaseReference databaseReference;
    private ArrayList<Question> selectedQuestion = new ArrayList<Question>();

    private CountDownTimer countDownTimer;
    private ProgressBar progressBar;

    private int currentQuestion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        connectAllElements();
        enableButtons();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.child("questions")
                            .orderByChild("categoryName").equalTo("Tarih");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Question> tempList = new ArrayList<Question>();
                for (DataSnapshot ss:snapshot.getChildren()) {
                    tempList.add(ss.getValue(Question.class));
                }
                startGame(tempList);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void startGame(ArrayList<Question> questions){
        int i = 1;
        Collections.shuffle(questions);
        for (Question q:questions){
            selectedQuestion.add(q);
            if (i==10) break;
            i++;
        }
        setQuestion(selectedQuestion.get(0));
    }

    private void setQuestion(Question question) {
        ArrayList<Integer> added = new ArrayList<Integer>();
        ArrayList<String> answers = new ArrayList<String>();
        answers.add(question.getOption1());
        answers.add(question.getOption2());
        answers.add(question.getOption3());
        answers.add(question.getOption4());
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            while (true) {
                int randomLocation = random.nextInt(4) ;
                if (!added.contains(randomLocation)){
                    optionsTextViewArray[randomLocation].setText(answers.get(i));
                    added.add(randomLocation);
                    break;
                }
            }
        }
        cardQuestion.setText(question.getQuestion());
        progressBar.setMax(question.getTime());
        counterManage(question.getTime() + 1);
        questcount.setText(String.valueOf(currentQuestion + 1) + " / " + String.valueOf(selectedQuestion.size()));

    }

    public void nextButtonClicked(View view){
        countDownTimer.cancel();
        resetColorToBlueWhite();

        currentQuestion++;
        if (currentQuestion == selectedQuestion.size()) {
            endGame();
            return;
        }

        setQuestion(selectedQuestion.get( currentQuestion ));
    }

    public void enableButtons(){
        card0A.setClickable(true);
        card0B.setClickable(true);
        card0C.setClickable(true);
        card0D.setClickable(true);
    }
    public void resetColorToBlueWhite(){
        card0A.setCardBackgroundColor(getResources().getColor(R.color.white));
        card0B.setCardBackgroundColor(getResources().getColor(R.color.white));
        card0C.setCardBackgroundColor(getResources().getColor(R.color.white));
        card0D.setCardBackgroundColor(getResources().getColor(R.color.white));
    }


    private void counterManage(int timerValue){
        countDownTimer=new CountDownTimer(timerValue*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int currentSecond = (int) (millisUntilFinished/1000);
                timeTV.setText(String.valueOf(currentSecond));
                progressBar.setProgress(currentSecond);
            }
            @Override
            public void onFinish() {
                timeIsUp();
            }
        }.start();
    }

    private void timeIsUp() {
        nextButtonClicked(nextQuestionButton);
    }

    public void endGame() {
        Intent intent=new Intent(QuizActivity.this,ScoreScreenActivity.class);
        startActivity(intent);
    }
    public void optionA_clicked(View view) {
        if(lastClicked=="A")
        {
            resetColorToBlueWhite();
            lastClicked=null;
        }else{
            resetColorToBlueWhite();
            card0A.setCardBackgroundColor(getResources().getColor(R.color.libYellow2x));
            lastClicked="A";
        }

    }
    public void optionB_clicked(View view) {
        if(lastClicked=="B")
        {
            resetColorToBlueWhite();
            lastClicked=null;
        }else{
            resetColorToBlueWhite();
            card0B.setCardBackgroundColor(getResources().getColor(R.color.libYellow2x));
            lastClicked="B";
        }
    }
    public void optionC_clicked(View view) {
        if(lastClicked=="C") {
            resetColorToBlueWhite();
            lastClicked=null;
        }else {
            resetColorToBlueWhite();
            card0C.setCardBackgroundColor(getResources().getColor(R.color.libYellow2x));
            lastClicked="C";
        }
    }
    public void optionD_clicked(View view) {
        if(lastClicked=="D") {
            resetColorToBlueWhite();
            lastClicked=null;
        }else{
            resetColorToBlueWhite();
            card0D.setCardBackgroundColor(getResources().getColor(R.color.libYellow2x));
            lastClicked="D";
        }
    }
    private void connectAllElements() {
        questcount=findViewById(R.id.questionCount);
        progressBar=findViewById(R.id.time_progress);
        cardQuestion=findViewById(R.id.questionCard);
        optionA=findViewById(R.id.card_optionA);
        optionB=findViewById(R.id.card_optionB);
        optionC=findViewById(R.id.card_optionC);
        optionD=findViewById(R.id.card_optionD);
        card0A=findViewById(R.id.cardA);
        card0B=findViewById(R.id.cardB);
        card0C=findViewById(R.id.cardC);
        card0D=findViewById(R.id.cardD);
        timeTV=findViewById(R.id.timeText);
        nextQuestionButton=findViewById(R.id.next_question);
        optionsTextViewArray[0]= optionA;
        optionsTextViewArray[1]= optionB;
        optionsTextViewArray[2]= optionC;
        optionsTextViewArray[3]= optionD;
    }
}