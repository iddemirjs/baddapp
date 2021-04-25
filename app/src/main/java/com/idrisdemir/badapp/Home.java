package com.idrisdemir.badapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idrisdemir.badapp.Adapters.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    public SliderView home_sliderView;
    public int[] images = {
            R.drawable.sil1,
            R.drawable.sil2,
            R.drawable.sil3,
            R.drawable.sil4,
            R.drawable.sil5,
            R.drawable.sil6
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
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
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        String loginUser = sharedPref.getString("login","nologin");

        TextView usernameTV = (TextView) view.findViewById(R.id.home_top_user_name);
        usernameTV.setText(loginUser);

        Button logoutButton = (Button) view.findViewById(R.id.home_logout_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("login" , "nologin");
                editor.commit();
                Intent goLogin = new Intent(getActivity(),LoginActivity.class);
                startActivity(goLogin);
            }
        });

        // Slider Codes
        home_sliderView = view.findViewById(R.id.home_image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);
        home_sliderView.setSliderAdapter(sliderAdapter);
        home_sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        home_sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        home_sliderView.startAutoCycle();


        return view;
    }
}