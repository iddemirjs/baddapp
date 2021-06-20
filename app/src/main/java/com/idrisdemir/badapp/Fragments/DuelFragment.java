package com.idrisdemir.badapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.idrisdemir.badapp.Adapters.ViewPagerAdapter;
import com.idrisdemir.badapp.CreateDuelFragment;
import com.idrisdemir.badapp.DuelHistoryFragment;
import com.idrisdemir.badapp.DuelListFragment;
import com.idrisdemir.badapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DuelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DuelFragment extends Fragment {
    TabLayout tab_layout_duello;
    ViewPager view_pager_duello;
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
        return view;
    }

    private void setUpViewPager(ViewPager view_pager_duello) {
        System.out.println("asdasdsaadsadsasdsad");System.out.println("asdasdsaadsadsasdsad");
        System.out.println("asdasdsaadsadsasdsad");System.out.println("asdasdsaadsadsasdsad");
        System.out.println("asdasdsaadsadsasdsad");
        System.out.println("asdasdsaadsadsasdsad");System.out.println("asdasdsaadsadsasdsad");System.out.println("asdasdsaadsadsasdsad");


        ViewPagerAdapter adapter_duello=new ViewPagerAdapter(getChildFragmentManager());
        adapter_duello.addFragment(new DuelListFragment(), "Active Duels");
        adapter_duello.addFragment(new DuelHistoryFragment(), "Duel History");
        adapter_duello.addFragment(new CreateDuelFragment(), "Create Duel");
        view_pager_duello.setAdapter(adapter_duello);

    }
}