/*
 * QuestionActivity.java - QuestionActivity
 *
 * Created on 21/02/2023 15:35:26 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import me.loule.vroomcards.R;
import me.loule.vroomcards.classes.Flashcard;

public class QuestionActivity extends AppCompatActivity {

    private ArrayList<Flashcard> questions;

    private static final String TAG = "QuestionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questions = getIntent().getParcelableArrayListExtra("questions");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        QuestionAdapter adapter = new QuestionAdapter(questions);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
