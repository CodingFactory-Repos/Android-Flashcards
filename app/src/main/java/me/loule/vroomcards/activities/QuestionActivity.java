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
import me.loule.vroomcards.classes.Flashcard;

public class QuestionActivity extends AppCompatActivity {

    private ArrayList<Flashcard> questions;
    private static final String TAG = "QuestionActivity";

    /**
     * @param savedInstanceState If the activity is being re-initialized after 
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questions = getIntent().getParcelableArrayListExtra("questions"); //Get the questions from the intent

        RecyclerView recyclerView = findViewById(R.id.recyclerView); //Get the RecyclerView
        QuestionAdapter adapter = new QuestionAdapter(questions); //Create a new QuestionAdapter

        recyclerView.setAdapter(adapter); //Set the adapter to the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //Set the layout manager to the RecyclerView
    }
}
