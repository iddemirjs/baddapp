package com.idrisdemir.badapp.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Entity.LevelMapping;
import com.idrisdemir.badapp.Entity.Member;
import com.idrisdemir.badapp.Entity.QuizResult;
import com.idrisdemir.badapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    private DatabaseReference dbReference;
    private String oldName,uniqueID;
    private int userLevel,userExperience,nextExperience;
    private Member member;
    private LevelMapping level;
    private QuizResult result;
    private int wincount=0;
    private int lostcount=0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Statistics_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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

    public void fillProgressBar(Member member,ProgressBar userProgress,TextView tvPercentLevel)
    {
        Query levelquery = dbReference.child("levels").orderByChild("level").equalTo(member.getLevel());
        levelquery.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                level=new LevelMapping();
                for (DataSnapshot ss:snapshot.getChildren())
                {
                    level = ss.getValue(LevelMapping.class);
                }
                nextExperience=level.getNeedNextLevelExperience();
                Double progresscount=Double.valueOf(userExperience)/Double.valueOf(nextExperience);
                progresscount=progresscount*100;
                int percentLevel=Integer.valueOf(progresscount.intValue());
                String textLevel="% "+String.valueOf(percentLevel);
                userProgress.setMax(100);
                tvPercentLevel.setText(textLevel);
                userProgress.setProgress(percentLevel);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_statistics_,container,false);
        TextView levelText = (TextView) view.findViewById(R.id.levelNumber);
        TextView tvPercentLevel = (TextView) view.findViewById(R.id.percent_level);
        ProgressBar userProgress=(ProgressBar) view.findViewById(R.id.userExperienceProgressBar);
        TextView winCount=(TextView) view.findViewById(R.id.winCount);
        TextView loseCount=(TextView) view.findViewById(R.id.lostCount);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbReference  = database.getReference();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        oldName = sharedPref.getString("login","nologin");
        Query query = dbReference.child("users").orderByChild("username").equalTo(oldName);
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                member = new Member();
                for (DataSnapshot ss:snapshot.getChildren())
                {
                    member = ss.getValue(Member.class);
                }
                fillProgressBar(member,userProgress,tvPercentLevel);
                userLevel=member.getLevel();
                userExperience=member.getExperience();
                levelText.setText(String.valueOf(userLevel));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        Query query2 = dbReference.child("quizResults").orderByChild("playerName").equalTo(oldName);
        query2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                result=new QuizResult();
                for (DataSnapshot ss:snapshot.getChildren())
                {
                    result= ss.getValue(QuizResult.class);
                    if(result.isSuccess())
                    {
                        wincount++;
                    }
                    else {
                        lostcount++;
                    }
                    winCount.setText(String.valueOf(wincount));
                    loseCount.setText(String.valueOf(lostcount));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });





        return view;
    }
}