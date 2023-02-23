/*
 * GameActivity.java - GameActivity
 *
 * Created on 21/02/2023 15:32:36 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.picasso.Picasso;
import me.loule.vroomcards.R;
import me.loule.vroomcards.classes.Answer;
import me.loule.vroomcards.classes.Flashcard;

import java.util.ArrayList;
import java.util.Collections;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GameActivity";
    private static boolean isAnswered = false;
    private static int currentQuestion = 0;
    private static int correctQuestion = 0;
    private ArrayList<Flashcard> questions;

    private ImageView questionImageView;
    private TextView questionTextView;
    private RadioGroup radioGroup;
    private Button nextAndCheckQuestionButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        questionImageView = findViewById(R.id.questionImageView);
        questionTextView = findViewById(R.id.questionTextView);
        radioGroup = findViewById(R.id.questionRadioGroup);
        nextAndCheckQuestionButton = findViewById(R.id.questionnextAndCheckQuestionButton);
        resultTextView = findViewById(R.id.questionResultTextView);

        // get the parcelable arraylist from intent
        questions = getIntent().getParcelableArrayListExtra("questions");
        nextAndCheckQuestionButton.setOnClickListener(this);
        loadGameData(questions.get(currentQuestion));
    }

    private void loadGameData(Flashcard q) {
        Picasso.get().load(q.getImage()).into(questionImageView);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.questionnextAndCheckQuestionButton:
                // if answered and there are more questions to be asked
                if (isAnswered && currentQuestion < questions.size() - 1) {
                    nextQuestion();
                } else if (isAnswered && currentQuestion == questions.size() - 1) {
                    Intent intent = new Intent(this, EndGameActivity.class);
                    intent.putExtra("correctQuestion", correctQuestion);
                    intent.putExtra("totalQuestion", questions.size());
                    startActivity(intent);

                    finish();
                } else {
                    // check the current question
                    checkQuestion();
                }
                // toggle answer status
                isAnswered = !isAnswered;
                break;
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
        String answer = radioButton.getText().toString();
        Answer q = questions.get(currentQuestion).getAnswers().get(index);

        if (q.isCorrect()) {
            resultTextView.setText("Bonne réponse !");
            correctQuestion++;
        } else {
            resultTextView.setText("Mauvaise réponse ! La bonne réponse était " + q.getAnswer());
        }

        // enable the button
        nextAndCheckQuestionButton.setText("Prochaine question");
        nextAndCheckQuestionButton.setEnabled(true);
    }

    private void nextQuestion() {
        currentQuestion++;
        resultTextView.setText("");
        loadGameData(questions.get(currentQuestion));
    }
}
