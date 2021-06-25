package com.idrisdemir.badapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Entity.EnergyTrade;
import com.idrisdemir.badapp.Entity.Member;

import org.w3c.dom.Text;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    String username,password,againPassword,sex,email;
    TextView tvUsername,tvPassword,tvAgainPassword, tvEmail,emailError,passwordError,usernameError;
    RadioGroup rgSex;
    RadioButton rbSex;
    Button buttonRegister;
    TextView buttonRegisterToLogin;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbReference  = database.getReference();

        tvUsername = (TextView) findViewById(R.id.register_username);
        tvPassword = (TextView) findViewById(R.id.register_password);
        tvAgainPassword =  (TextView) findViewById(R.id.register_passwordagain);
        tvEmail = (TextView) findViewById(R.id.register_email);
        rgSex = (RadioGroup) findViewById(R.id.radio_sex);
        emailError=(TextView) findViewById(R.id.email_error);
        passwordError=(TextView) findViewById(R.id.password_error);
        usernameError=(TextView) findViewById(R.id.username_error);

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbSex = (RadioButton) findViewById(checkedId);
                sex = rbSex.getText().toString();
            }
        });


        buttonRegister = (Button) findViewById(R.id.register_button);
        buttonRegisterToLogin = (TextView) findViewById(R.id.register_signin);
        final MediaPlayer buttonSound=MediaPlayer.create(this,R.raw.buttonclick2);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = tvUsername.getText().toString();
                password = tvPassword.getText().toString();
                againPassword = tvAgainPassword.getText().toString();
                email = tvEmail.getText().toString();

                Member user = new Member(username,email,password ,sex ,0,1);
                Query query = dbReference.child("users").orderByChild("username")
                                                        .equalTo(user.getUsername());

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Gelen verilerin sayısını veren kod.
                        // long count = snapshot.getChildrenCount();
                        if (!snapshot.exists()){
                            usernameError.setVisibility(View.INVISIBLE);
                            if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                                emailError.setVisibility(View.INVISIBLE);
                                if (againPassword.equals(password)){
                                    emailError.setVisibility(View.INVISIBLE);
                                    // Userin benzersiz kimliğini oluşturduk.
                                    String uniqueId = UUID.randomUUID().toString();
                                    user.setUuid(uniqueId);
                                    // User veritabanına eklenmeye hazır.
                                    dbReference.child("users").child(uniqueId).setValue(user);
                                    EnergyTrade energyTrade = new EnergyTrade();
                                    energyTrade.setUuid(UUID.randomUUID().toString());
                                    energyTrade.setUsername(user.getUsername());
                                    energyTrade.setEnergyPiece(5);
                                    energyTrade.setTradeType("profit");
                                    dbReference.child("energyTrades").child(energyTrade.getUuid()).setValue(energyTrade);
                                    Toast.makeText(RegisterActivity.this, "Membership Successfull", Toast.LENGTH_SHORT).show();
                                    buttonSound.start();
                                    Intent returnRegister = new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(returnRegister);
                                }else{
                                    // Kullanıcı Şifrelerin Eşleşmesi için Uyarılmaslı.
                                    passwordError.setVisibility(View.VISIBLE);
                                }
                            }
                            else
                            {
                                emailError.setVisibility(View.VISIBLE);
                            }

                        }else{
                            // Bu usernamenin veritabanında kayıtlı oldugu konusunda uyarılmalı.
                            usernameError.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        buttonRegisterToLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                Intent goLogin=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(goLogin);
            }
        });


    }
}