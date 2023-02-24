/*
 * LoadingGameActivity.java - LoadingGameActivity
 *
 * Created on 22/02/2023 16:16:44 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import me.loule.vroomcards.R;
import me.loule.vroomcards.classes.Flashcard;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LoadingGameActivity extends AppCompatActivity {

    private ArrayList<Flashcard> questions = new ArrayList<>();
    private int difficulty;
    private Flashcard question;
    private static final String TAG = "GameActivity";

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_game);

        difficulty = getIntent().getIntExtra("difficulty", 999); //Get the difficulty from the intent or set it to 999 if it's not set
        question = getIntent().getParcelableExtra("question") != null ? (Flashcard) getIntent().getParcelableExtra("question") : null; //Get the question from the intent or set it to null if it's not set

        loadQuestionsFromApi(); //Load the questions from the API
    }

    /**
     * Load the questions from the API
     */
    private void loadQuestionsFromApi() {
        OkHttpClient client = new OkHttpClient(); //Create a new OkHttpClient
        Request request = new Request.Builder()
                .url("https://flint-tar-shovel.glitch.me/cars" + (question != null ? "/" + question.getId() : ""))
                .build(); //Create a new request to the API

        // Make the request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) { //If the request failed
                Log.e("APPEL API", "onFailure", e);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://flint-tar-shovel.glitch.me/cars" + (question != null ? "/" + question.getId() : ""))
                        .build();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException { //If the request succeeded
                assert response.body() != null; //Assert that the response body is not null
                String body = response.body().string(); //Get the response body as a string
                Gson gson = new Gson(); //Create a new Gson object

                // If question is not null, it means that we are in the question activity
                if (question != null) {
                    questions.add(question); //Add the question to the questions array
                } else {
                    for (Flashcard question : gson.fromJson(body, Flashcard[].class)) { //For each question in the response body
                        if (question.getDifficulty() == difficulty || difficulty == 999) { //If the question difficulty is the same as the difficulty or if the difficulty is 999
                            questions.add(question); //Add the question to the questions array
                        }
                    }
                }

                Handler handler = new Handler(getMainLooper()); //Create a new handler
                handler.post(() -> { //Post the code to the main thread
                    Intent intent; //Create a new intent
                    if (difficulty != 999) { //If the difficulty is not 999
                        Collections.shuffle(questions); //Shuffle the questions
                        questions = question != null ? questions : new ArrayList<>(questions.subList(0, 3)); //If the question is not null, set the questions array to the questions array, else set the questions array to a sublist of the questions array

                        intent = startGameActivity(); //Set the intent to the game activity
                    } else {
                        intent = startQuestionActivity(); //Set the intent to the question activity
                    }

                    intent.putExtra("questions", questions); //Put the questions array in the intent
                    startActivity(intent); //Start the activity
                    finish(); // Kill the actual activity
                });
            }
        });
    }

    private Intent startQuestionActivity() {
        Intent intent = new Intent(this, QuestionActivity.class);
        return intent;
    }

    private Intent startGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty", difficulty);
        return intent;
    }
}
