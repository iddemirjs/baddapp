package com.idrisdemir.badapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Entity.Question;
import com.idrisdemir.badapp.Entity.QuizResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class QuizActivity extends AppCompatActivity {

    private Timer totalTimer;
    private Handler timeHandler;
    private int totalTime = 0;
    private Button exitButton;

    private ArrayList<Question> questionArrayList;
    private TextView cardQuestion, optionA, optionB, optionC, optionD, questcount, timeTV;
    private TextView[] optionsTextViewArray = new TextView[4];
    private CardView card0A, card0B, card0C, card0D;
    private Button nextQuestionButton;
    private int index = 0;
    private String[] options = new String[4];
    private String lastClicked = null;
    private DatabaseReference databaseReference;
    private ArrayList<Question> selectedQuestion = new ArrayList<Question>();
    private Queue<Question> selectedQuestionQueue = new LinkedList<Question>();
    private String categoryName;
    private QuizResult examResult = new QuizResult();
    private CountDownTimer countDownTimer;
    private ProgressBar progressBar;

    private int currentQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        connectAllElements();
        exitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                exitFromQuizDialog(view.getContext());
            }
        });
        enableButtons();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            categoryName = bundle.getString("category_name");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("questions")
                .orderByChild("categoryName").equalTo(categoryName);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Question> tempList = new ArrayList<Question>();
                for (DataSnapshot ss : snapshot.getChildren()) {
                    tempList.add(ss.getValue(Question.class));
                }
                startGame(tempList);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void startGame(ArrayList<Question> questions) {
        int i = 1;
        Collections.shuffle(questions);
        for (Question q : questions) {
            selectedQuestion.add(q);
            selectedQuestionQueue.add(q);
            if (i == 10) break;
            i++;
        }
        startTotalTimer();
        setQuestion(selectedQuestionQueue.poll());
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
                int randomLocation = random.nextInt(4);
                if (!added.contains(randomLocation)) {
                    optionsTextViewArray[randomLocation].setText(answers.get(i));
                    added.add(randomLocation);
                    break;
                }
            }
        }
        cardQuestion.setText(question.getQuestion());
        progressBar.setMax(question.getTime());
        counterManage(question.getTime() + 1);

        questcount.setText(String.valueOf(currentQuestionNumber + 1) + " / " + String.valueOf(selectedQuestion.size()));

    }

    public void nextButtonClicked(View view) {
        countDownTimer.cancel();
        resetColorToBlueWhite();
        if (lastClicked != null) {
            if (getOptionAnswer().equalsIgnoreCase(selectedQuestion.get(currentQuestionNumber).getOption1())) {
                this.examResult.increaseCorrectAnswerNumber();
                this.examResult.addToProfit(selectedQuestion.get(currentQuestionNumber).getValue());
            } else {
                this.examResult.increaseWrongAnswerNumber();
            }
        }
        lastClicked = null;

        currentQuestionNumber++;
        if (selectedQuestionQueue.isEmpty()) {
            endGame();
            return;
        }

        setQuestion(selectedQuestionQueue.poll());
    }

    public void enableButtons() {
        card0A.setClickable(true);
        card0B.setClickable(true);
        card0C.setClickable(true);
        card0D.setClickable(true);
    }

    public void resetColorToBlueWhite() {
        card0A.setCardBackgroundColor(getResources().getColor(R.color.white));
        card0B.setCardBackgroundColor(getResources().getColor(R.color.white));
        card0C.setCardBackgroundColor(getResources().getColor(R.color.white));
        card0D.setCardBackgroundColor(getResources().getColor(R.color.white));
    }


    private void counterManage(int timerValue) {
        countDownTimer = new CountDownTimer(timerValue * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int currentSecond = (int) (millisUntilFinished / 1000);
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
    private void exitFromQuizDialog(Context c)
    {

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Exit")
                .setMessage("If you quit the test your energy will be wasted.Are you sure you want to continue?")
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(c, DashboardActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }



    public void endGame() {
        stopTotalTimer();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String loginUser = sharedPref.getString("login", "nologin");
        this.examResult.setPlayerName(loginUser);
        this.examResult.setElapsedTime(this.totalTime);
        this.examResult.setTotalQuestionSize(this.selectedQuestion.size());
        String uniqueId = UUID.randomUUID().toString();
        this.examResult.setUuid(uniqueId);
        databaseReference.child("quizResults").child(uniqueId).setValue(this.examResult);
        Intent intent = new Intent(QuizActivity.this, ScoreScreenActivity.class);
        startActivity(intent);
    }

    public void optionA_clicked(View view) {
        if (lastClicked == "A") {
            resetColorToBlueWhite();
            lastClicked = null;
        } else {
            resetColorToBlueWhite();
            card0A.setCardBackgroundColor(getResources().getColor(R.color.libYellow4x));
            lastClicked = "A";
        }

    }

    public void optionB_clicked(View view) {
        if (lastClicked == "B") {
            resetColorToBlueWhite();
            lastClicked = null;
        } else {
            resetColorToBlueWhite();
            card0B.setCardBackgroundColor(getResources().getColor(R.color.libYellow4x));
            lastClicked = "B";
        }
    }

    public void optionC_clicked(View view) {
        if (lastClicked == "C") {
            resetColorToBlueWhite();
            lastClicked = null;
        } else {
            resetColorToBlueWhite();
            card0C.setCardBackgroundColor(getResources().getColor(R.color.libYellow4x));
            lastClicked = "C";
        }
    }

    public void optionD_clicked(View view) {
        if (lastClicked == "D") {
            resetColorToBlueWhite();
            lastClicked = null;
        } else {
            resetColorToBlueWhite();
            card0D.setCardBackgroundColor(getResources().getColor(R.color.libYellow4x));
            lastClicked = "D";
        }
    }

    public String getOptionAnswer() {
        if (lastClicked == null) return null;
        if (lastClicked.equalsIgnoreCase("A")) return (String) optionA.getText();
        else if (lastClicked.equalsIgnoreCase("B")) return (String) optionB.getText();
        else if (lastClicked.equalsIgnoreCase("C")) return (String) optionC.getText();
        else return (String) optionD.getText();
    }

    private void connectAllElements() {
        exitButton=findViewById(R.id.exit_quiz_screen);
        questcount = findViewById(R.id.questionCount);
        progressBar = findViewById(R.id.time_progress);
        cardQuestion = findViewById(R.id.questionCard);
        optionA = findViewById(R.id.card_optionA);
        optionB = findViewById(R.id.card_optionB);
        optionC = findViewById(R.id.card_optionC);
        optionD = findViewById(R.id.card_optionD);
        card0A = findViewById(R.id.cardA);
        card0B = findViewById(R.id.cardB);
        card0C = findViewById(R.id.cardC);
        card0D = findViewById(R.id.cardD);
        timeTV = findViewById(R.id.timeText);
        nextQuestionButton = findViewById(R.id.next_question);
        optionsTextViewArray[0] = optionA;
        optionsTextViewArray[1] = optionB;
        optionsTextViewArray[2] = optionC;
        optionsTextViewArray[3] = optionD;
    }

    private void stopTotalTimer() {

        // Timer çalışıyorsa öncelikle ondan çıkış yapıyoruz
        if (totalTimer != null) totalTimer.cancel();

        // Ardından Handler ve Timerı sıfıra eşitliyoruz
        timeHandler = null;
        totalTimer = null;

    }

    // Süreyi sayan sayacımız
    private void startTotalTimer() {

        // Eğer çalışan bir süre sayacı varsa onu kapatıyoruz
        stopTotalTimer();

        timeHandler = new Handler();
        totalTimer = new Timer();

        TimerTask sureTimerTask = new TimerTask() {
            @Override
            public void run() {

                timeHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        totalTime++;
                    }
                });

            }
        };

        totalTimer.schedule(sureTimerTask, 0, 1000);

    }
}