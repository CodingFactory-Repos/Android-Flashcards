/*
 * LeaderboardActivity.java - LeaderboardActivity
 *
 * Created on 24/02/2023 16:13:25 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import me.loule.vroomcards.R;
import me.loule.vroomcards.classes.Stat;
import okhttp3.*;

import java.io.IOException;

public class LeaderboardActivity extends AppCompatActivity {

    private static final String TAG = "LeaderboardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        loadStatsFromApi();
    }

    private void loadStatsFromApi() {
        OkHttpClient client = new OkHttpClient(); //Create a new OkHttpClient
        Request request = new Request.Builder()
                .url("https://flint-tar-shovel.glitch.me/stats")
                .build(); //Create a new request to the API

        // Make the request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) { //If the request failed
                Log.e("APPEL API", "onFailure", e);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://flint-tar-shovel.glitch.me/stats")
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

                Stat stats = gson.fromJson(body, Stat.class); //Parse the JSON to a Stat object

                TextView numberOfPlayedGames = findViewById(R.id.numberPlayedTextView);
                TextView numberWonGames = findViewById(R.id.numberWonTextView);
                TextView numberPourcentTextView = findViewById(R.id.numberPourcentTextView);
                int Average = stats.getNumberOfWonGames() * 100 / stats.getNumberOfPlayedGames();

                Handler handler = new Handler(getMainLooper());
                handler.post(() -> {
                    numberOfPlayedGames.setText("Depuis la création du jeu, " + String.valueOf(stats.getNumberOfPlayedGames()) + " parties ont été jouées.");
                    numberWonGames.setText("Dont " + String.valueOf(stats.getNumberOfWonGames()) + " parties ont été gagnées.");
                    numberPourcentTextView.setText("La moyenne de réussite est de " + String.valueOf(Average) + "%.");
                });
            }
        });
    }
}
