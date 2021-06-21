package com.idrisdemir.badapp.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Adapters.ViewPagerAdapter;
import com.idrisdemir.badapp.Entity.CoinTrade;
import com.idrisdemir.badapp.Entity.EnergyTrade;
import com.idrisdemir.badapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DuelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DuelFragment extends Fragment {
    TabLayout tab_layout_duello;
    ViewPager view_pager_duello;
    TextView braincoin,energycount;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DuelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Duel.
     */
    // TODO: Rename and change types and number of parameters
    public static DuelFragment newInstance(String param1, String param2) {
        DuelFragment fragment = new DuelFragment();
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
        View view=inflater.inflate(R.layout.fragment_duel, container, false);
        view_pager_duello=view.findViewById(R.id.view_pager_duello);
        setUpViewPager(view_pager_duello);
        tab_layout_duello=view.findViewById(R.id.tab_layout_duello);
        tab_layout_duello.setupWithViewPager(view_pager_duello);
        braincoin=view.findViewById(R.id.brain_count);
        energycount=view.findViewById(R.id.energy_count);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String oldName = sharedPref.getString("login","nologin");
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

        Query coinQuery = databaseReference.child("coinTrades").orderByChild("receiverUserName").equalTo(oldName);
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

        Query energyQuery = databaseReference.child("energyTrades").orderByChild("username").equalTo(oldName);
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

    private void setUpViewPager(ViewPager view_pager_duello) {
        ViewPagerAdapter adapter_duello=new ViewPagerAdapter(getChildFragmentManager());
        adapter_duello.addFragment(new DuelListFragment(), "Active Duels");
        adapter_duello.addFragment(new DuelHistoryFragment(), "Duel History");
        adapter_duello.addFragment(new CreateDuelFragment(), "Create Duel");
        view_pager_duello.setAdapter(adapter_duello);

    }
}