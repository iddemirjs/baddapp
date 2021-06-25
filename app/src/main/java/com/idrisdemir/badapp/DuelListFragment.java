package com.idrisdemir.badapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Adapters.DuelListAdapter;
import com.idrisdemir.badapp.Entity.BadGame;
import com.idrisdemir.badapp.Entity.Category;
import com.idrisdemir.badapp.Entity.Member;

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
                initDuelRecylerView(list,view);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
        return view;
    }

    private void initDuelRecylerView(ArrayList<BadGame> duelList, View view)
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
            if(temp.getUUID()==badgame.getUUID())
            {
                startDuelAlert(getContext(),temp.getUUID());
            }
        }
    }

    private void startDuelAlert(Context c, String categorySelected)
    {
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Start Duel")
                .setMessage("Are you ready for the challenge?")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

}