package com.idrisdemir.badapp.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Adapters.DuelListAdapter;
import com.idrisdemir.badapp.Adapters.SliderAdapter;
import com.idrisdemir.badapp.AdministratorActivities.AddQuestionActivity;
import com.idrisdemir.badapp.Entity.BadGame;
import com.idrisdemir.badapp.Entity.Category;
import com.idrisdemir.badapp.Entity.CoinTrade;
import com.idrisdemir.badapp.Entity.Member;
import com.idrisdemir.badapp.Entity.New;
import com.idrisdemir.badapp.LoginActivity;
import com.idrisdemir.badapp.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private SliderView home_sliderView;
    private Button addQuestionButton;
    private DatabaseReference database;
    private ArrayList<New> newsList =new ArrayList<New>();
    private New news;
    private TextView braincoin;
    private Member member;
    private int player_coin,totalDuel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String loginUser = sharedPref.getString("login", "nologin");
        TextView usernameTV = (TextView) view.findViewById(R.id.home_top_user_name);
        usernameTV.setText(loginUser);
        braincoin=view.findViewById(R.id.home_brain_coin);
        database= FirebaseDatabase.getInstance().getReference();
        coinTradeControl(database,loginUser);
        Query query = database.child("news");
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                news=new New();
                newsList=new ArrayList<New>();
                for (DataSnapshot ss: snapshot.getChildren())
                {
                    news=ss.getValue(New.class);
                    newsList.add(news);
                }
                fillSliderView(newsList,view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


        Button logoutButton = (Button) view.findViewById(R.id.home_logout_button);
        final MediaPlayer buttonSound=MediaPlayer.create(getActivity(),R.raw.buttonclick2);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("login", "nologin");
                editor.commit();
                Intent goLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(goLogin);
            }
        });

        // Slider Codes

        addQuestionButton = (Button) view.findViewById(R.id.add_question);
        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                showAddItemDialog(view.getContext());
            }
        });
        return view;
    }

    private void fillSliderView(ArrayList<New> newsList, View view)
    {
        home_sliderView = view.findViewById(R.id.home_image_slider);
        SliderAdapter sliderAdapter = new SliderAdapter(newsList);
        home_sliderView.setSliderAdapter(sliderAdapter);
        home_sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        home_sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        home_sliderView.startAutoCycle();
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        final MediaPlayer buttonSound=MediaPlayer.create(getActivity(),R.raw.buttonclick2);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Authorization")
                .setMessage("Please enter administor password?")
                .setView(taskEditText)
                .setPositiveButton("Authorize", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password = String.valueOf(taskEditText.getText());
                        if (password.equals("123456"))
                        {
                            buttonSound.start();
                            Intent intent = new Intent(c, AddQuestionActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
    public void coinTradeControl(DatabaseReference databaseReference,String oldName)
    {
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
                coinTradeDecreaseControl(databaseReference);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void coinTradeDecreaseControl(DatabaseReference databaseReference)
    {
        Query coinQuery = databaseReference.child("coinTrades").orderByChild("receiverUserName").equalTo("BadAppCash");
        coinQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int duelCoin = 0;
                CoinTrade temp=new CoinTrade();
                for (DataSnapshot ss:snapshot.getChildren())
                {
                    temp = ss.getValue(CoinTrade.class);
                    duelCoin+=temp.getAmount();
                    totalDuel+=duelCoin;
                }
                player_coin=player_coin-duelCoin;
                braincoin.setText(String.valueOf(player_coin));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

}