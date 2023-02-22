/*
 * QuestionActivity.java - QuestionActivity
 *
 * Created on 21/02/2023 15:35:26 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import me.loule.vroomcards.R;

public class QuestionActivity extends AppCompatActivity {

    ArrayList<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        for (int i = 0; i < 500; i++) {
            questions.add(new Question("De quelle marque est cette voiture ?", "Wolswagen, Audi, peugeot", R.drawable.green ));
            questions.add(new Question("Quelle est le modele de cette voiture ?", "Audi A1, Peugeot 308, BMW M4", R.drawable.orange ));
            questions.add(new Question("Quelle est le moteur de cette voiture ?", "V4, V6, V8", R.drawable.red ));


        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        QuestionAdapter adapter = new QuestionAdapter(questions);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
