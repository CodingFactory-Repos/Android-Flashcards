/*
 * GameActivity.java - GameActivity
 *
 * Created on 21/02/2023 15:32:36 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import me.loule.vroomcards.classes.Answer;
import me.loule.vroomcards.classes.Flashcard;

import java.io.IOException;
import java.util.*;

import me.loule.vroomcards.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameActivity extends AppCompatActivity {

    private List<Flashcard> questions = new ArrayList<>();

    private static final String TAG = "GameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        int difficulty = 0;

        loadQuestionsFromApi(difficulty);
    }

    private void loadQuestionsFromApi(int difficulty) {
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
                questions = questions.subList(0, 3);

                Log.i(TAG, "onResponse: " + questions.size() + " questions loaded : \n" + questions.get(0).getAnswers().get(0).getAnswer() + "\n" + questions.get(1).getAnswers().get(0).getAnswer() + "\n" + questions.get(2).getAnswers().get(0).getAnswer());
            }
        });
    }
}
