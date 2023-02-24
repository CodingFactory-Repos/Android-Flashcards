/*
 * EndGameActivity.java - EndGameActivity
 *
 * Created on 21/02/2023 15:35:56 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.loule.vroomcards.R;

public class EndGameActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        initalizeActivity(); //Initialize the activity

        Button returnHomeButton = findViewById(R.id.returnHomeButton); //Get the return home button
        returnHomeButton.setOnClickListener(this); //Set the click listener
    }

    /**
     * Initialize the activity
     */
    private void initalizeActivity() {
        // Get all the data from the intent
        int correctQuestion = getIntent().getIntExtra("correctQuestion", 0);
        int totalQuestion = getIntent().getIntExtra("totalQuestion", 0);

        int percentage = Math.round((float) correctQuestion / totalQuestion * 100); //Calculate the percentage

        // Set the text views
        TextView goodResponseTextView = findViewById(R.id.goodResponseTextView);
        TextView parcentSucessTextView = findViewById(R.id.parcentSucessTextView);

        // Set the text with the results
        goodResponseTextView.setText(String.format("Vous avez répondu correctement à %d/%d questions", correctQuestion, totalQuestion));
        parcentSucessTextView.setText(String.format("Votre pourcentage de réussite est de %d%%", percentage));
    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(EndGameActivity.this, MainActivity.class); // Create a new intent
        startActivity(intent); // Start the activity
        finish(); // Kill the activity
    }
}
