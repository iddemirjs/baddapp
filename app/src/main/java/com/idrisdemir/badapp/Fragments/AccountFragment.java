package com.idrisdemir.badapp.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Entity.Member;
import com.idrisdemir.badapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    DatabaseReference dbReference;
    String gender,password,email;
    TextView accountGender,accountPassword,accountEmail;
    String oldGender,oldEmail,oldPassword,oldName,uniqueID;
    Member member;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbReference  = database.getReference();
        View view=inflater.inflate(R.layout.fragment_account_,container,false);
        accountGender=(TextView) view.findViewById(R.id.newGender);
        accountEmail=(TextView) view.findViewById(R.id.newEmail);
        accountPassword=(TextView) view.findViewById(R.id.newPassword);
        Button editButton = (Button) view.findViewById(R.id.editButton);
        Button cancelButton = (Button) view.findViewById(R.id.profileCancelButton);
        Button saveButton = (Button) view.findViewById(R.id.profileSaveButton);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        oldName = sharedPref.getString("login","nologin");
        LinearLayout informationsLayout = (LinearLayout) view.findViewById(R.id.accountInformationsLayout);
        LinearLayout edittableLayout = (LinearLayout ) view.findViewById(R.id.accountEdittableLayout);
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
                uniqueID=member.getUuid();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editButton.setVisibility(View.GONE);
                informationsLayout.setVisibility(View.GONE);
                edittableLayout.setVisibility(View.VISIBLE);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editButton.setVisibility(View.VISIBLE);
                informationsLayout.setVisibility(View.VISIBLE);
                edittableLayout.setVisibility(View.GONE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                gender=accountGender.getText().toString();
                email=accountEmail.getText().toString();
                password=accountPassword.getText().toString();
                if(!(member.getPassword().equalsIgnoreCase(password)) && password.length()!=0)
                {
                    member.setPassword(password);
                    dbReference.child("users").child(uniqueID).setValue(member);
                    Toast.makeText(getContext(), "Password has been resetted", Toast.LENGTH_SHORT).show();
                    accountPassword.setText("");
                }
                if(!(member.getGender().equalsIgnoreCase(gender)))
                {
                    if((gender.equalsIgnoreCase("Male") || (gender.equalsIgnoreCase("Female"))))
                    {
                        member.setGender(gender);
                        accountGender.setText("");
                        dbReference.child("users").child(uniqueID).setValue(member);
                        Toast.makeText(getContext(), "Gender has been resetted", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(getContext(), "Please Enter valid gender(Male or Female)", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        return view;
    }


}