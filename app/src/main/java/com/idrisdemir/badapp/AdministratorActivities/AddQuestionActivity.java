package com.idrisdemir.badapp.AdministratorActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Entity.Category;
import com.idrisdemir.badapp.Entity.Question;
import com.idrisdemir.badapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddQuestionActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button addNewQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("category");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Category category = new Category();
                List<String> categoryList = new ArrayList<String>();
                for (DataSnapshot ss : snapshot.getChildren())
                {
                    category = ss.getValue(Category.class);
                    Toast.makeText(AddQuestionActivity.this, category.getCategoryName(), Toast.LENGTH_SHORT).show();
                    categoryList.add(category.getCategoryName());
                }
                Spinner spinner2 = (Spinner) findViewById(R.id.category_spinner);

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, categoryList);
                spinner2.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setContentView(R.layout.activity_add_question);
        addNewQuestionButton = (Button) findViewById(R.id.add_question_form_btn);
        addNewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText questionEditText = (EditText) findViewById(R.id.question_et);
                EditText answerEditText = (EditText) findViewById(R.id.answer_et);
                EditText option1EditText = (EditText) findViewById(R.id.option1_et);
                EditText option2EditText = (EditText) findViewById(R.id.option2_et);
                EditText option3EditText = (EditText) findViewById(R.id.option3_et);
                EditText questionTime = (EditText) findViewById(R.id.time_et);
                Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);

                String selectedCategory = categorySpinner.getSelectedItem().toString();
                String questionText = questionEditText.getText().toString();
                String answerText = answerEditText.getText().toString();
                String option1Text = option1EditText.getText().toString();
                String option2Text = option2EditText.getText().toString();
                String option3Text = option3EditText.getText().toString();
                int time = Integer.valueOf(questionTime.getText().toString());

                String[] options = {answerText, option1Text, option2Text, option3Text};

                Question newQuestion = new Question(questionText, time, options, 111, selectedCategory);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                String uniqueId = UUID.randomUUID().toString();

                databaseReference.child("questions").child(uniqueId).setValue(newQuestion);

                Toast.makeText(AddQuestionActivity.this, "Succesfully", Toast.LENGTH_SHORT).show();

                questionEditText.setText("");
                answerEditText.setText("");
                option1EditText.setText("");
                option2EditText.setText("");
                option3EditText.setText("");

            }
        });
    }
}