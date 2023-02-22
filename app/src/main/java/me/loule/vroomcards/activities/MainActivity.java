/*
 * MainActivity.java - MainActivity
 *
 * Created on 21/02/2023 15:31:23 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import me.loule.vroomcards.R;

public class MainActivity extends AppCompatActivity {

    String selectedDifficulty = "Facile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button quizButton = findViewById(R.id.quizButton);
        Button questionsButton = findViewById(R.id.questionsButton);
        Button aboutButton = findViewById(R.id.aboutButton);

        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptionsDialog();
            }

            private void showOptionsDialog() {
                final String[] difficulty = {"Facile","Moyen", "Difficile"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choisissez une difficultée :");
                builder.setSingleChoiceItems(difficulty, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedDifficulty = difficulty[which];
                        Toast.makeText(MainActivity.this, "La difficulté choisie est :" + selectedDifficulty, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Jouer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
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
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
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
