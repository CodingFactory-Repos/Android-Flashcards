/*
 * MainActivity.java - MainActivity
 *
 * Created on 21/02/2023 15:31:23 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
        ImageView difficultyCloseImageView = findViewById(R.id.difficultyCloseImageView);
        difficultyCloseImageView.setVisibility(View.INVISIBLE);
        difficultyRadioGroup.setVisibility(View.INVISIBLE);
        Button difficultyButton = findViewById(R.id.difficultyButton);
        Button quizButton = findViewById(R.id.quizButton);
        Button questionsButton = findViewById(R.id.questionsButton);
        Button aboutButton = findViewById(R.id.aboutButton);

        difficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficultyRadioGroup.setVisibility(View.VISIBLE);
                difficultyCloseImageView.setVisibility(View.VISIBLE);
            }
        });

        difficultyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                Toast.makeText(MainActivity.this, "La difficulté choisie est : " + radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        difficultyCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficultyRadioGroup.setVisibility(View.INVISIBLE);
                difficultyCloseImageView.setVisibility(View.INVISIBLE);
            }
        });

        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
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
