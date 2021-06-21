package com.idrisdemir.badapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.AdministratorActivities.AddQuestionActivity;
import com.idrisdemir.badapp.Entity.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateDuelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateDuelFragment extends Fragment {
    private EditText coin_amount;
    private TextView player_count_text,quize_count_text,coin_warning_text;
    private SeekBar seekbar_player_count,seekbar_quize_count;
    int max_player=10,typed_coin=0,player_coin=100;
    //private int max_player=10;
    DatabaseReference databaseReference;
    Activity currentActivity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateDuelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateDuelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateDuelFragment newInstance(String param1, String param2) {
        CreateDuelFragment fragment = new CreateDuelFragment();
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
    public int calculateMaxPlayer(int typed_coin,int player_coin)
    {
        return player_coin/typed_coin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        databaseReference=FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("category");
        View view= inflater.inflate(R.layout.fragment_create_duel, container, false);
        coin_amount=view.findViewById(R.id.coin_amount);
        player_count_text=view.findViewById(R.id.seek_bar_text);
        quize_count_text=view.findViewById(R.id.seekbar_question_text);
        coin_warning_text=view.findViewById(R.id.warning);
        seekbar_player_count=view.findViewById(R.id.seekbar_player_size);
        seekbar_quize_count=view.findViewById(R.id.seekbar_question_count);
        coin_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                seekbar_player_count.setProgress(0);
                if(s.length()>0){
                    typed_coin=Integer.parseInt(coin_amount.getText().toString());
                    if(typed_coin>player_coin){
                        coin_warning_text.setText("You don't have enough money.");
                        seekbar_player_count.setEnabled(false);
                        coin_warning_text.setTextColor(Color.RED);
                    }
                    else {
                        seekbar_player_count.setMax(calculateMaxPlayer(typed_coin,player_coin));
                        seekbar_player_count.setEnabled(true);
                        coin_warning_text.setText("");

                    }
                }
                else if(s.length()==0){
                    seekbar_player_count.setEnabled(false);
                    coin_warning_text.setText("Please type a mount of money");
                }
                else
                    seekbar_player_count.setEnabled(false);


            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        seekbar_player_count.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               player_count_text.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar_quize_count.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quize_count_text.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        this.currentActivity=getActivity() ;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Category category = new Category();
                List<String> categoryList = new ArrayList<String>();
                for (DataSnapshot ss : snapshot.getChildren())
                {
                    category = ss.getValue(Category.class);
                    categoryList.add(category.getCategoryName());
                }
                Spinner spinner2 = (Spinner) view.findViewById(R.id.category_spinner_duel);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(currentActivity, android.R.layout.simple_spinner_item, categoryList);
                spinner2.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}