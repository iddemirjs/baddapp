package com.idrisdemir.badapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Entity.Member;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity {

    TextView singUpTB,tvUsername,tvPassword,tvForgotPassword;
    String username,password;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginbutton);
        ConstraintLayout loginContainer = (ConstraintLayout) findViewById(R.id.login_container);
        singUpTB = (TextView) findViewById(R.id.signup_textbutton);
        tvUsername = (TextView) findViewById(R.id.username);
        tvPassword = (TextView) findViewById(R.id.password);
        tvForgotPassword = (TextView) findViewById(R.id.forgotpassword_textbutton);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final MediaPlayer buttonSound=MediaPlayer.create(this,R.raw.buttonclick2);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        loginContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(LoginActivity.this);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = tvUsername.getText().toString();
                password = tvPassword.getText().toString();

                if (!username.equals("") && !password.equals("")){
                    Query query = databaseReference.child("users")
                            .orderByChild("username").equalTo(username);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                Toast.makeText(LoginActivity.this, "kayıt yok", Toast.LENGTH_SHORT).show();
                            }
                            Member member=null;
                            for (DataSnapshot ss:snapshot.getChildren()) {
                                member = ss.getValue(Member.class);
                            }
                            if (member != null && member.getPassword().equals(password)){
                                // Local Depolayıcımızı oluşturalım.
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("login" , member.getUsername());
                                editor.commit();
                                // Local depolayıcımıza kaydettik.
                                Toast.makeText(LoginActivity.this,  " Login action has been successfully with " + member.getUsername(), Toast.LENGTH_SHORT).show();
                                buttonSound.start();
                                Intent goMain = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(goMain);
                            }else{
                                tvForgotPassword.setText("Username or password is wrong.");
                                tvForgotPassword.setTextColor(getResources().getColor(R.color.pinkColor));
                                tvForgotPassword.setVisibility(View.VISIBLE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else{
                    Toast.makeText(LoginActivity.this, "Please fill all area.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        singUpTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent goRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goRegister);
            }
        });
    }
    public static void hideSoftKeyboard(Activity activity) {
    InputMethodManager inputMethodManager =
        (InputMethodManager) activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE);
    if(inputMethodManager.isAcceptingText()){
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(),
                0
        );
    }
}
}