package com.idrisdemir.badapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.idrisdemir.badapp.Entity.Question;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    private ArrayList<Question> questionArrayList;
    private Question quest;
    TextView cardQuestion,optionA,optionB,optionC,optionD,questcount;
    CardView card0A,card0B,card0C,card0D;
    Button nextQuestionButton;
    int index=0;
    private String[] options=new String[4];
    String lastClicked=null;

    private CountDownTimer countDownTimer;
    private ProgressBar progressBar;
    int timerValue=20;

    private  String[] optionMaker(String[] options,String a,String b,String c,String d)
    {
        options[0]=a;
        options[1]=b;
        options[2]=c;
        options[3]=d;
        return options;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Hooks();
        questionArrayList=new ArrayList<Question>();
        options=optionMaker(options,"tr1","eng1","brit1","sivas1");
        questionArrayList.add(new Question("1ankara nerenin baskenti",10,options,111,0));
        options=optionMaker(options,"tr2","eng2","brit2","sivas2");
        questionArrayList.add(new Question("2ankara nerenin baskenti",10,options,111,0));
        options=optionMaker(options,"tr3","eng3","brit3","sivas3");
        questionArrayList.add(new Question("3ankara nerenin baskenti",10,options,111,0));
        options=optionMaker(options,"tr4","eng4","brit4","sivas4");
        questionArrayList.add(new Question("4ankara nerenin baskenti",10,options,111,0));
        questcount.setText(index+1 +"/"+questionArrayList.size());
        //Collections.shuffle(questionArrayList);
        quest=questionArrayList.get(index);
        timerValue=quest.getTime();
        setQuestion();
        enableButtons();
        //Timer
        TextView time=findViewById(R.id.timeText);
        progressBar.setMax(timerValue);


        countDownTimer=new CountDownTimer(timerValue*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerValue=timerValue-1;
                time.setText(String.valueOf(timerValue));
                progressBar.setProgress(timerValue);
            }
            @Override
            public void onFinish() {
                if(index<questionArrayList.size()-1) {
                    index++;
                    questcount.setText(index+1 +"/"+questionArrayList.size());
                    quest = questionArrayList.get(index);
                    timerValue = quest.getTime();
                    resetColor();
                    setQuestion();
                    countDownTimer.start();
                }
                else
                    EndGame();

            }
        }.start();

    }

    private void Hooks() {
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
        nextQuestionButton=findViewById(R.id.next_question);
    }
    private void setQuestion() {
        cardQuestion.setText(quest.getQuestion());
        optionA.setText(quest.getOptions(0));
        optionB.setText(quest.getOptions(1));
        optionC.setText(quest.getOptions(2));
        optionD.setText(quest.getOptions(3));
    }


    /*
    public void Answer()
    {
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index<questionArrayList.size()-1)
                {
                    index++;
                    quest=questionArrayList.get(index);
                    setQuestion();
                }
                else
                    EndGame();
            }
        });
    }
    */
    public void  nextclicked(View view)
    {
        if(index<questionArrayList.size()-1)
        {
            index++;
            questcount.setText(index+1 +"/"+questionArrayList.size());
            quest=questionArrayList.get(index);
            timerValue=quest.getTime();
            resetColor();
            setQuestion();

        }
        else
            EndGame();
    }
    public void EndGame()
    {
        Intent intent=new Intent(QuizActivity.this,ScoreScreenActivity.class);
        startActivity(intent);
    }
    public void enableButtons(){
        card0A.setClickable(true);
        card0B.setClickable(true);
        card0C.setClickable(true);
        card0D.setClickable(true);
    }
    public void resetColor(){
        card0A.setCardBackgroundColor(getResources().getColor(R.color.white));
        card0B.setCardBackgroundColor(getResources().getColor(R.color.white));
        card0C.setCardBackgroundColor(getResources().getColor(R.color.white));
        card0D.setCardBackgroundColor(getResources().getColor(R.color.white));
    }
    public void optionA_clicked(View view)
    {
        if(lastClicked=="A")
        {
            resetColor();
            lastClicked=null;
        }
        else
        {
            resetColor();
            card0A.setCardBackgroundColor(getResources().getColor(R.color.libYellow2x));
            lastClicked="A";
        }

    }
    public void optionB_clicked(View view)
    {
        if(lastClicked=="B")
        {
            resetColor();
            lastClicked=null;
        }
        else
        {
            resetColor();
            card0B.setCardBackgroundColor(getResources().getColor(R.color.libYellow2x));
            lastClicked="B";
        }
    }
    public void optionC_clicked(View view)
    {
        if(lastClicked=="C")
        {
            resetColor();
            lastClicked=null;
        }
        else
        {
            resetColor();
            card0C.setCardBackgroundColor(getResources().getColor(R.color.libYellow2x));
            lastClicked="C";
        }
    }
    public void optionD_clicked(View view)
    {
        if(lastClicked=="D")
        {
            resetColor();
            lastClicked=null;
        }
        else
        {
            resetColor();
            card0D.setCardBackgroundColor(getResources().getColor(R.color.libYellow2x));
            lastClicked="D";
        }
    }


}