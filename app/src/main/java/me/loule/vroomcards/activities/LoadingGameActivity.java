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
import java.util.List;

public class LoadingGameActivity extends AppCompatActivity {

    private ArrayList<Flashcard> questions = new ArrayList<>();
    private int difficulty;

    private static final String TAG = "GameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_game);

        difficulty = getIntent().getIntExtra("difficulty", 0);

        loadQuestionsFromApi();
    }

    private void loadQuestionsFromApi() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://flint-tar-shovel.glitch.me/cars")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("APPEL API", "onFailure", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String body = response.body().string();
                Gson gson = new Gson();

                for (Flashcard question : gson.fromJson(body, Flashcard[].class)) {
                    if (question.getDifficulty() == difficulty) {
                        questions.add(question);
                    }
                }

                Collections.shuffle(questions);
                questions = new ArrayList<>(questions.subList(0, 3));
                Handler handler = new Handler(getMainLooper());
                handler.post(() -> {
                    startGameActivity();
                });
            }
        });
    }

    private void startGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("questions", questions);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
        finish();
    }
}

