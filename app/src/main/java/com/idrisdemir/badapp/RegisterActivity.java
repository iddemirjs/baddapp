package com.idrisdemir.badapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.idrisdemir.badapp.Entity.Member;

import org.w3c.dom.Text;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    String username,password,againPassword,sex;
    TextView tvUsername,tvPassword,tvAgainPassword;
    RadioGroup rgSex;
    RadioButton rbSex;
    Button buttonRegister;
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
        rgSex = (RadioGroup) findViewById(R.id.radio_sex);

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbSex = (RadioButton) findViewById(checkedId);
                sex = rbSex.getText().toString();
            }
        });


        buttonRegister = (Button) findViewById(R.id.register_button);



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = tvUsername.getText().toString();
                password = tvPassword.getText().toString();
                againPassword = tvAgainPassword.getText().toString();

                Member user = new Member(username,password ,sex ,0,0);
                Query query = dbReference.child("users").orderByChild("username")
                                                        .equalTo(user.getUsername());

                final boolean[] canCreatable = {true};

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long count = snapshot.getChildrenCount();
                        if (count==0){
                            if (againPassword.equals(password)){
                                // Userin benzersiz kimliğini oluşturduk.
                                String uniqueId = UUID.randomUUID().toString();
                                user.setUuid(uniqueId);
                                // User veritabanına eklenmeye hazır.
                                dbReference.child("users").child(uniqueId).setValue(user);
                                Toast.makeText(RegisterActivity.this, "Üyelik Başarılı", Toast.LENGTH_SHORT).show();
                                Intent returnRegister = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(returnRegister);
                            }else{
                                // Kullanıcı Şifrelerin Eşleşmesi için Uyarılmaslı.
                                Toast.makeText(RegisterActivity.this, "Şifreler Eşleşmeli", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            // Bu usernamenin veritabanında kayıtlı oldugu konusunda uyarılmalı.
                            Toast.makeText(RegisterActivity.this, "Bu kullanıcı adı uygun değil.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}