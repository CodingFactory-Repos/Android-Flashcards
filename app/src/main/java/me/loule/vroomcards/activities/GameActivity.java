/*
 * GameActivity.java - GameActivity
 *
 * Created on 21/02/2023 15:32:36 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.picasso.Picasso;
import me.loule.vroomcards.R;
import me.loule.vroomcards.classes.Answer;
import me.loule.vroomcards.classes.Flashcard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GameActivity";
    private static boolean isAnswered = false;
    private static int currentQuestion, correctQuestion;
    private ArrayList<Flashcard> questions;

    private int difficulty;
    private ImageView questionImageView;
    private TextView questionTextView, resultTextView;
    private RadioGroup radioGroup;
    private Button nextAndCheckQuestionButton;

    private String indexQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initializeActivity();

        nextAndCheckQuestionButton.setOnClickListener(this);
        try {
            loadGameData(questions.get(currentQuestion));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void initializeActivity() {
        questionImageView = findViewById(R.id.questionImageView);
        questionTextView = findViewById(R.id.questionTextView);
        radioGroup = findViewById(R.id.questionRadioGroup);
        nextAndCheckQuestionButton = findViewById(R.id.questionnextAndCheckQuestionButton);
        resultTextView = findViewById(R.id.questionResultTextView);

        // get the parcelable arraylist from intent
        questions = getIntent().getParcelableArrayListExtra("questions");
        difficulty = getIntent().getIntExtra("difficulty", 0);
        Log.i(TAG, "initializeActivity: " + difficulty);
        isAnswered = false;
        currentQuestion = 0;
        correctQuestion = 0;
    }

    private void loadGameData(Flashcard                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          q) throws IOException {

        if (difficulty != 2){
            Picasso.get().load(q.getImage()).into(questionImageView);
        }else{
            Picasso.get().load(q.getImage()).into(questionImageView);
            MediaPlayer mediaPlayer = new MediaPlayer();
            AudioManager audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
            mediaPlayer.setDataSource("https://cdn.pixabay.com/audio/2022/03/10/audio_f10a962bdc.mp3?filename=065421_nissan-skyline-gtr-brakes-38471.mp3");
            mediaPlayer.prepare();
            mediaPlayer.setVolume(0.7f, 0.8f);
            mediaPlayer.start();
        }
        questionTextView.setText(q.getQuestion());
        // randomize the answers
        Collections.shuffle(q.getAnswers());
        radioGroup.removeAllViews();

        // create radio button for each answer
        for (int i = 0; i < q.getAnswers().size(); i++) {
            radioGroup.addView(new RadioButton(this));
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setText(q.getAnswers().get(i).getAnswer());
        }

        radioGroup.setVisibility(View.VISIBLE);
        indexQuestion = "Questions : " + (currentQuestion + 1) + "/" + questions.size();
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(indexQuestion);

        Log.i(TAG, "loadGameData: " + indexQuestion);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "nextQuestion: " + isAnswered + "" + radioGroup.getCheckedRadioButtonId());
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            switch (v.getId()) {
            case R.id.questionnextAndCheckQuestionButton:
                // if answered and there are more questions to be asked
                if (isAnswered && currentQuestion < questions.size() - 1) {
                    try {
                        nextQuestion();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (isAnswered && currentQuestion == questions.size() - 1) {
                    Intent intent = new Intent(this, EndGameActivity.class);
                    intent.putExtra("correctQuestion", correctQuestion);
                    intent.putExtra("totalQuestion", questions.size());
                    startActivity(intent);

                    finish();
                } else {
                        checkQuestion();
                }
                // toggle answer status
                isAnswered = !isAnswered;
                break;
        }
        } else {
            Toast.makeText(this, "Veuillez sélectionner une réponse", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkQuestion() {
        // disable the button
        nextAndCheckQuestionButton.setEnabled(false);
        radioGroup.setVisibility(View.INVISIBLE);

        // get the selected radio button
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

        // get the index of the selected radio button
        int index = radioGroup.indexOfChild(radioButton);
        // get the selected answer
//        String answer = radioButton.getText().toString();
        if (index >= 0) {
            Answer q = questions.get(currentQuestion).getAnswers().get(index);

            Answer correct_q = null;
            for (Answer a : questions.get(currentQuestion).getAnswers()) {
                if (a.isCorrect()) {
                    correct_q = a;
                    break;
                }
            }

            if (q.isCorrect()) {
                resultTextView.setText("Bonne réponse !");
                correctQuestion++;
            } else {
                resultTextView.setText("Mauvaise réponse ! La bonne réponse était " + correct_q.getAnswer());
            }

            // enable the button
            nextAndCheckQuestionButton.setText("Prochaine question");
            nextAndCheckQuestionButton.setEnabled(true);
        }else{
            Toast.makeText(this, "Veuillez répondre à la question actuelle avant de passer à la suivante", Toast.LENGTH_SHORT).show();
        }
    }

    private void nextQuestion() throws IOException {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            // Verify if the user did select an answer
            Toast.makeText(this, "Veuillez répondre à la question actuelle avant de passer à la suivante", Toast.LENGTH_SHORT).show();
            return; // taost to respond and do not reload the game data
        }
        currentQuestion++;
        resultTextView.setText("");
        radioGroup.clearCheck();
        loadGameData(questions.get(currentQuestion));
        Log.i(TAG, "nextQuestion: " + isAnswered + "" + radioGroup.getCheckedRadioButtonId());
        indexQuestion = "Questions : " + (currentQuestion + 1) + "/" + questions.size();
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(indexQuestion);

    }
}
