/*
 * EndGameActivity.java - EndGameActivity
 *
 * Created on 21/02/2023 15:35:56 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import me.loule.vroomcards.R;
import me.loule.vroomcards.classes.Stat;
import okhttp3.*;

import java.io.IOException;

public class EndGameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EndGameActivity";

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

        addToStats(correctQuestion, totalQuestion); // Add the results to the stats
    }

    private void addToStats(int correctQuestion, int totalQuestion) {
        OkHttpClient client = new OkHttpClient(); //Create a new OkHttpClient
        Request request = new Request.Builder()
                .url("https://flint-tar-shovel.glitch.me/stats")
                .build(); //Create a new request to the API

        // Make a PUT request to the API
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Error while making the request", e); // Log the error
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) { // If the response is successful
                    String body = response.body().string(); // Get the body of the response
                    Gson gson = new Gson(); // Create a new Gson object
                    Stat stat = gson.fromJson(body, Stat.class); // Create a new Stat object from the body

                    int numberOfPlayedGames = stat.getNumberOfPlayedGames() + 1; // Add 1 to the number of played games
                    int numberOfWonGames = stat.getNumberOfWonGames() + (correctQuestion == totalQuestion ? 1 : 0); // Add 1 to the number of won games if the user won

                    Stat newStat = new Stat(numberOfPlayedGames, numberOfWonGames); // Create a new Stat object with the new values

                    // Create a new request to the API
                    Request request = new Request.Builder()
                            .url("https://flint-tar-shovel.glitch.me/stats")
                            .put(RequestBody.create(gson.toJson(newStat), MediaType.parse("application/json")))
                            .build();

                    // Make a PUT request to the API
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.e(TAG, "Error while making the request", e); // Log the error
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) { // If the response is successful
                                Log.i(TAG, "Stats updated"); // Log the success
                            } else {
                                Log.e(TAG, "Error while making the request: " + response.code()); // Log the error
                            }
                        }
                    });
                } else {
                    Log.e(TAG, "Error while making the request: " + response.code()); // Log the error
                }
            }
        });
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
