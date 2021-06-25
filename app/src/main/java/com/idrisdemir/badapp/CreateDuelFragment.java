package com.idrisdemir.badapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.idrisdemir.badapp.Entity.BadGame;
import com.idrisdemir.badapp.Entity.Category;
import com.idrisdemir.badapp.Entity.CoinTrade;
import com.idrisdemir.badapp.Entity.Member;
import com.idrisdemir.badapp.Fragments.DuelFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateDuelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateDuelFragment extends Fragment {
    private EditText coin_amount;
    private TextView player_count_text,quize_count_text,coin_warning_text,cost_duel_text;
    private SeekBar seekbar_player_count,seekbar_quize_count;
    private Button make_match;
    private String oldName;
    private Spinner spinner2;
    private int max_player=10,typed_coin=0,player_coin=0;
    //private int max_player=10;
    private DatabaseReference databaseReference;
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
        View view= inflater.inflate(R.layout.fragment_create_duel, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference  = database.getReference();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        oldName = sharedPref.getString("login","nologin");
        Query query = databaseReference.child("category");
        coin_amount=view.findViewById(R.id.coin_amount);
        player_count_text=view.findViewById(R.id.seek_bar_text);
        quize_count_text=view.findViewById(R.id.seekbar_question_text);
        coin_warning_text=view.findViewById(R.id.warning);
        seekbar_player_count=view.findViewById(R.id.seekbar_player_size);
        seekbar_quize_count=view.findViewById(R.id.seekbar_question_count);
        cost_duel_text=view.findViewById(R.id.cost_of_duel);
        make_match=view.findViewById(R.id.make_duel_button);


        Query coinQuery = databaseReference.child("coinTrades").orderByChild("receiverUserName").equalTo(oldName);
        coinQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CoinTrade temp=new CoinTrade();
                int player_coin_sum=0;
                for (DataSnapshot ss:snapshot.getChildren()) {
                    temp = ss.getValue(CoinTrade.class);
                    player_coin_sum+=temp.getAmount();
                }
                System.out.println();
                //braincoin.setText(String.valueOf(player_coin_sum));
                player_coin=player_coin_sum;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        make_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typed_coin<=player_coin && seekbar_player_count.getProgress()>0){

                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Start Duello")
                            .setMessage("Are sure about create a Duello? ")
                            .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String duelUid= UUID.randomUUID().toString();
                                    int questionCount=seekbar_quize_count.getProgress();
                                    BadGame duello=new BadGame(duelUid,oldName,spinner2.getSelectedItem().toString(),seekbar_player_count.getProgress(),0,questionCount,typed_coin);
                                    databaseReference.child("badgames").child(duello.getUuid()).setValue(duello);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
                }
            }
        });
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
               String total=String.valueOf(typed_coin*seekbar_player_count.getProgress());
               cost_duel_text.setText(total);

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
                spinner2 = (Spinner) view.findViewById(R.id.category_spinner_duel);
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