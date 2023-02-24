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
import nl.dionsegijn.konfetti.KonfettiView;

public class MainActivity extends AppCompatActivity {

    String selectedDifficulty = "Facile";

    private static final String TAG = "MainActivity";

    private int indexDifficulty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton quizButton = findViewById(R.id.quizButton);
        FloatingActionButton questionsButton = findViewById(R.id.questionsButton);
        Button aboutButton = findViewById(R.id.aboutButton);

        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptionsDialog();
            }

            private void showOptionsDialog() {
                final String[] difficulty = {"Facile", "Moyen", "Difficile"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choisissez une difficultée :");
                builder.setSingleChoiceItems(difficulty, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        indexDifficulty = which;
                        selectedDifficulty = difficulty[which];
                        Log.i(TAG, "onClick: " + which);
                        Toast.makeText(MainActivity.this, "La difficulté choisie est : " + selectedDifficulty, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setPositiveButton("Jouer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "onClick 2 : " + indexDifficulty);

                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, LoadingGameActivity.class);
                        intent.putExtra("difficulty", indexDifficulty);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        questionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoadingGameActivity.class);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
