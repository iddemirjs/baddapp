package com.idrisdemir.badapp.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Adapters.RecyclerAdapter;
import com.idrisdemir.badapp.Entity.Category;
import com.idrisdemir.badapp.Entity.CoinTrade;
import com.idrisdemir.badapp.Entity.EnergyTrade;
import com.idrisdemir.badapp.Entity.Question;
import com.idrisdemir.badapp.QuizActivity;
import com.idrisdemir.badapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment implements RecyclerAdapter.OnCategoryListener {

    private ArrayList <Category>categoryList;
    public DatabaseReference database;
    public RecyclerView quiz_recyclerView;
    public ArrayList<Integer> button_images=new ArrayList<Integer>();
    TextView braincoin,energycount;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Quiz.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_quiz,container,false);
        database= FirebaseDatabase.getInstance().getReference();
        braincoin=view.findViewById(R.id.brain_count);
        energycount=view.findViewById(R.id.energy_count);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String oldName = sharedPref.getString("login","nologin");
        Query query = database.child("category");
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                categoryList=new ArrayList<Category>();
                for (DataSnapshot tempCategory: snapshot.getChildren())
                {
                    categoryList.add(tempCategory.getValue(Category.class));
                }
                startFillingRecyclerView(categoryList,view);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        Query coinQuery = database.child("coinTrades").orderByChild("receiverUserName").equalTo(oldName);
        coinQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CoinTrade temp=new CoinTrade();
                int player_coin=0;
                for (DataSnapshot ss:snapshot.getChildren()) {
                    temp = ss.getValue(CoinTrade.class);
                    player_coin+=temp.getAmount();
                }
                System.out.println();
                braincoin.setText(String.valueOf(player_coin));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query energyQuery = database.child("energyTrades").orderByChild("username").equalTo(oldName);
        energyQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EnergyTrade temp1=new EnergyTrade();
                int player_energy=0;
                for (DataSnapshot ss:snapshot.getChildren()) {
                    temp1 = ss.getValue(EnergyTrade.class);
                    player_energy+=temp1.getEnergyPiece();
                }
                System.out.println();
                energycount.setText(String.valueOf(player_energy));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void startFillingRecyclerView(ArrayList<Category> categoryList,View view)
    {
        for (Category temp:categoryList)
        {
            button_images.add(temp.getCategoryImageId());
            quiz_recyclerView=view.findViewById(R.id.quizfragment_recycler);
            quiz_recyclerView.setHasFixedSize(true);
            quiz_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            quiz_recyclerView.setAdapter(new RecyclerAdapter(button_images,this));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        TextView braincoin_count =(TextView) view.findViewById(R.id.brain_count);
        TextView energy_count =(TextView) view.findViewById(R.id.energy_count);
    }

    @Override
    public void onCategoryClick(int position)
    {
        for (Category tempCategory:categoryList) {
            if(tempCategory.getCategoryImageId()==position){
                startQuizAlert(getContext(),tempCategory.getCategoryName());
            }
        }
    }
    private void startQuizAlert(Context c, String categorySelected) {
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Start Quiz")
                .setMessage("Are you ready to start the test? ")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getContext(), QuizActivity.class);
                        intent.putExtra("category_name",categorySelected);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}