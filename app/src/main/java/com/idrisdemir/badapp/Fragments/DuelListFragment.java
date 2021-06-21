package com.idrisdemir.badapp.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Adapters.DuelListAdapter;
import com.idrisdemir.badapp.Entity.BadGame;
import com.idrisdemir.badapp.Entity.EnergyTrade;
import com.idrisdemir.badapp.QuizActivity;
import com.idrisdemir.badapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DuelListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DuelListFragment extends Fragment  implements  DuelListAdapter.ItemClickListener{

    private ArrayList<BadGame> list;
    private DatabaseReference database;
    private BadGame duel;
    private RecyclerView recyclerView;
    private int player_energy;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DuelListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DuelListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DuelListFragment newInstance(String param1, String param2) {
        DuelListFragment fragment = new DuelListFragment();
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
        View view=inflater.inflate(R.layout.fragment_duel_list,container,false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String oldName = sharedPref.getString("login","nologin");
        database= FirebaseDatabase.getInstance().getReference();
        Query query = database.child("badgames");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                duel=new BadGame();
                list=new ArrayList<BadGame>();
                for (DataSnapshot ss:snapshot.getChildren())
                {
                    duel = ss.getValue(BadGame.class);
                    list.add(duel);
                }
                initDuelRecyclerView(list,view);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }

        });

        Query energyQuery = database.child("energyTrades").orderByChild("username").equalTo(oldName);
        energyQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EnergyTrade temp1=new EnergyTrade();
                player_energy=0;
                for (DataSnapshot ss:snapshot.getChildren()) {
                    temp1 = ss.getValue(EnergyTrade.class);
                    player_energy+=temp1.getEnergyPiece();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void initDuelRecyclerView(ArrayList<BadGame> duelList, View view)
    {
            recyclerView = view.findViewById(R.id.duel_list_recycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            DuelListAdapter adapter = new DuelListAdapter(duelList, this);
            recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(BadGame badgame)
    {
        for (BadGame temp:list)
        {
            if(temp.getUuid()==badgame.getUuid() && player_energy>0)
            {
                startDuelAlert(getContext(), temp);
            }
            else if(player_energy<=0)
            {
                Toast.makeText(getContext(), "You don't have energy enough", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startDuelAlert(Context c, BadGame badGame)
    {
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Start Duel")
                .setMessage("Are you ready for the challenge?")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent=new Intent(getContext(), QuizActivity.class);
                        intent.putExtra("category_name", badGame.getCategoryName());
                        intent.putExtra("bad_game", badGame);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

}