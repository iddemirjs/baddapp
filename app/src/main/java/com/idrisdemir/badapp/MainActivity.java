package com.idrisdemir.badapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.idrisdemir.badapp.Entity.Member;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cu = (Button) findViewById(R.id.createUser);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        cu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser();
            }
        });


    }

    public void writeNewUser() {

        Member user = new Member("idris","demir",403,1100);

        mDatabase.child("users").child("1").setValue(user);
    }

}