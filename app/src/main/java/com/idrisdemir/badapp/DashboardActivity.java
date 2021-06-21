package com.idrisdemir.badapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNavigationView = findViewById(R.id.dervisComlek);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        //Log.d("resim ids", String.valueOf(R.drawable.buttonimage_sports));
       // Log.d("resim ids", String.valueOf(R.drawable.buttonimage_geography));
        Log.d("resim ids", String.valueOf(R.drawable.buttonimage_sports));
        Log.d("resim ids", String.valueOf(R.drawable.buttonimage_movie));
        Log.d("resim ids", String.valueOf(R.drawable.buttonimage_science));
        Log.d("resim ids", String.valueOf(R.drawable.buttonimage_game));
        Log.d("resim ids", String.valueOf(R.drawable.buttonimage_music));
        Log.d("resim ids", String.valueOf(R.drawable.buttonimage_literature));



    }


}