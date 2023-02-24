/*
 * MainActivity.java - MainActivity
 *
 * Created on 21/02/2023 15:31:23 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import me.loule.vroomcards.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String selectedDifficulty = "Facile";
    private static final String TAG = "MainActivity";
    private int indexDifficulty = 0;

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Import all the buttons
        FloatingActionButton quizButton = findViewById(R.id.quizButton);
        FloatingActionButton questionsButton = findViewById(R.id.questionsButton);
        Button aboutButton = findViewById(R.id.aboutButton);

        // Set the click listener
        quizButton.setOnClickListener(this);
        questionsButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent; // Create a new intent
        switch (v.getId()) {
            case R.id.quizButton: // If the quiz button is clicked
                showOptionsDialog(); // Show the options dialog
                break;
            case R.id.questionsButton: // If the questions button is clicked
                intent = new Intent(MainActivity.this, LoadingGameActivity.class); // Create a new intent to the loading game activity
                startActivity(intent); // Start the activity
                break;
            case R.id.aboutButton: // If the about button is clicked
                intent = new Intent(MainActivity.this, AboutActivity.class); // Create a new intent to the about activity
                startActivity(intent);
                break;
        }
    }

    /**
     * Show the options dialog
     */
    private void showOptionsDialog() {
        final String[] difficulty = {"Facile", "Moyen", "Difficile", "Expert"}; // Create a new array of difficulty
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); // Create a new alert dialog builder
        builder.setTitle("Choisissez une difficultée :"); // Set the title of the dialog

        // Set the single choice items
        builder.setSingleChoiceItems(difficulty, 0, new DialogInterface.OnClickListener() {
            // Display with a notification the level of difficulty choose
            @Override
            public void onClick(DialogInterface dialog, int which) { // When an item is clicked
                indexDifficulty = which; // Set the index of the difficulty
                selectedDifficulty = difficulty[which]; // Set the selected difficulty
                Toast.makeText(MainActivity.this, "La difficulté choisie est : " + selectedDifficulty, Toast.LENGTH_SHORT).show(); // Display a toast with the difficulty
            }
        });

        // When the difficulty has been chosen, click on the play button switch to the gameActivity
        builder.setPositiveButton("Jouer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Hide the dialog
                Intent intent = new Intent(MainActivity.this, LoadingGameActivity.class); // Create a new intent to the loading game activity
                intent.putExtra("difficulty", indexDifficulty); // Put the difficulty in the intent
                startActivity(intent); // Start the activity
            }
        });

        // If the user cancel the dialog, the dialog is hidden
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() { // When the cancel button is clicked
            @Override
            public void onClick(DialogInterface dialog, int which) { // When the cancel button is clicked
                dialog.dismiss(); // Hide the dialog
            }
        });

        builder.show(); // Show the dialog
    }
}
