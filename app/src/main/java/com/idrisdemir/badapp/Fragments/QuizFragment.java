package com.idrisdemir.badapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idrisdemir.badapp.Adapters.RecyclerAdapter;
import com.idrisdemir.badapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    public RecyclerView quiz_recyclerView;
    public int [] button_images= {
                    R.drawable.buttonimage_geography,
                    R.drawable.buttonimage_history,
                    R.drawable.buttonimage_science,
                    R.drawable.buttonimage_movie,
                    R.drawable.buttonimage_space,
                    R.drawable.buttonimage_mathematics,
                    R.drawable.buttonimage_sports,
                    R.drawable.buttonimage_game,
                    R.drawable.buttonimage_music,
                    R.drawable.buttonimage_literature
            };
    public String bCount="235";
    public String eCount="5";

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
        quiz_recyclerView=view.findViewById(R.id.quizfragment_recycler);
        quiz_recyclerView.setHasFixedSize(true);
        quiz_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        quiz_recyclerView.setAdapter(new RecyclerAdapter(button_images));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        TextView braincoin_count =(TextView) view.findViewById(R.id.brain_count);
        TextView energy_count =(TextView) view.findViewById(R.id.energy_count);
        braincoin_count.setText(bCount);
        energy_count.setText(eCount);
    }
}