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
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;

import com.squareup.picasso.Picasso;
import me.loule.vroomcards.R;
import me.loule.vroomcards.classes.Answer;
import me.loule.vroomcards.classes.Flashcard;
import me.loule.vroomcards.dialogs.BottomResultDialog;
import me.loule.vroomcards.dialogs.ShowImageDialog;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GameActivity";
    private static boolean isAnswered = false;
    private static int currentQuestion, correctQuestion;
    private ArrayList<Flashcard> questions;
    private MediaPlayer mediaPlayer = new MediaPlayer();


    private int difficulty;
    private ImageView questionImageView;
    private TextView questionTextView, resultTextView;
    private RadioGroup radioGroup;
    private Button nextAndCheckQuestionButton;

    private String indexQuestion;

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initializeActivity(); //Initialize the activity

        nextAndCheckQuestionButton.setOnClickListener(this); //Set the click listener
        questionImageView.setOnClickListener(this); //Set the click listener

        try { //Load the first question
            loadGameData(questions.get(currentQuestion)); //Load the first question
        } catch (IOException e) { //If an error occurs
            throw new RuntimeException(e); //Throw a runtime exception
        }
    }

    /**
     * Initialize the activity
     */
    private void initializeActivity() {
        // Get all required views
        questionImageView = findViewById(R.id.questionImageView);
        questionTextView = findViewById(R.id.questionTextView);
        radioGroup = findViewById(R.id.questionRadioGroup);
        nextAndCheckQuestionButton = findViewById(R.id.questionnextAndCheckQuestionButton);

        // get the parcelable arraylist from intent
        questions = getIntent().getParcelableArrayListExtra("questions");
        difficulty = getIntent().getIntExtra("difficulty", 0);

        // Set variables to default values
        isAnswered = false;
        currentQuestion = 0;
        correctQuestion = 0;
    }

    /**
     * @param q The question to load
     * @throws IOException If an error occurs
     */
    private void loadGameData(Flashcard q) throws IOException {
        if (q.getRessource().getType().equals("image")) { //If the question is an image
            Picasso.get().load(q.getRessource().getMedia()).into(questionImageView); // Load the image
        } else if (q.getRessource().getType().equals("sound")) { //If the question is a sound
            questionImageView.setImageResource(R.drawable.play_button); //Set the image to the play button

            AudioManager audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);

            // Set the volume to the max
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
            mediaPlayer.setDataSource(this, Uri.parse(q.getRessource().getMedia())); //Set the data source
            mediaPlayer.prepare(); //Prepare the MediaPlayer
            mediaPlayer.setVolume(0.7f, 0.8f); //Set the volume
            mediaPlayer.start(); //Start the MediaPlayer
        }

        questionTextView.setText(q.getQuestion()); // Set the question text
        Collections.shuffle(q.getAnswers()); // Shuffle the answers
        radioGroup.removeAllViews(); // Remove all views from the RadioGroup

        // Add a RadioButton for each answer
        for (int i = 0; i < q.getAnswers().size(); i++) {
            radioGroup.addView(new RadioButton(this));
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setText(q.getAnswers().get(i).getAnswer());
        }

        radioGroup.setVisibility(View.VISIBLE); //Set the RadioGroup to visible
        indexQuestion = "Questions : " + (currentQuestion + 1) + "/" + questions.size(); //Set the index question
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(indexQuestion); //Set the subtitle
    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.questionnextAndCheckQuestionButton: //If the next and check question button is clicked
                    if (radioGroup.getCheckedRadioButtonId() != -1) { //If a RadioButton is checked
                        if (isAnswered && currentQuestion < questions.size() - 1) { //If the question is answered and the current question is less than the total number of questions
                            try {
                                nextQuestion(); //Load the next question
                            } catch (IOException e) {
                                throw new RuntimeException(e); //Throw a runtime exception
                            }
                        } else if (isAnswered && currentQuestion == questions.size() - 1) { //If the question is answered and the current question is equal to the total number of questions
                            Intent intent = new Intent(this, EndGameActivity.class); //Create a new Intent
                            intent.putExtra("correctQuestion", correctQuestion); //Put the number of correct questions in the Intent
                            intent.putExtra("totalQuestion", questions.size()); //Put the total number of questions in the Intent

                            startActivity(intent); //Start the activity

                            finish(); //Finish the activity
                        } else {
                            checkQuestion(); //Check the question
                        }
                        isAnswered = !isAnswered; //Toggle the answer status
                    } else { //If no RadioButton is checked
                        Toast.makeText(this, "Veuillez sélectionner une réponse", Toast.LENGTH_SHORT).show(); //Show a Toast
                    }
                    break;

                case R.id.questionImageView:
                    Flashcard q = questions.get(currentQuestion);

                    if (q.getRessource().getType().equals("sound")) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        } else {
                            try {
                                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
                                mediaPlayer.setDataSource(GameActivity.this, Uri.parse(q.getRessource().getMedia()));
                                mediaPlayer.prepare();
                                mediaPlayer.setVolume(0.7f, 0.7f);
                                mediaPlayer.start();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else if (q.getRessource().getType().equals("image")) {
                        ShowImageDialog showZoomedImage = new ShowImageDialog(q.getRessource().getMedia());
                        showZoomedImage.show(getSupportFragmentManager(), "showZoomedImage");
                    }
                    break;

            }
        }


    /**
     * Check the question
     */
    private void checkQuestion() {
        // disable the button and hide the radio group
        nextAndCheckQuestionButton.setEnabled(false);
        radioGroup.setVisibility(View.INVISIBLE);

        // get the selected radio button
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

        // get the index of the selected radio button
        int index = radioGroup.indexOfChild(radioButton);
        if (index >= 0) { //If the index is greater than or equal to 0
            Answer q = questions.get(currentQuestion).getAnswers().get(index); //Get the answer

            Answer correct_q = null; //Create a new Answer
            for (Answer a : questions.get(currentQuestion).getAnswers()) {  //For each answer
                if (a.isCorrect()) { //If the answer is correct
                    correct_q = a; //Set the correct answer
                    break;
                }
            }

            boolean won = q.getAnswer().equals(correct_q.getAnswer()); //Check if the answer is correct
            correctQuestion = (won) ? correctQuestion + 1 : correctQuestion; //Increment the number of correct questions (ternary operator

            if (won) { //If the answer is correct
                final KonfettiView viewKonfetti = findViewById(R.id.viewKonfetti); // Add Konfetti to the view

                // Set confetti to top left and right of screen during 1 seconds and then stop
                viewKonfetti.build()
                        .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.RECT, Shape.CIRCLE)
                        .addSizes(new Size(12, 5))
                        .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                        .streamFor(300, 5000L);
            }

            BottomResultDialog bottomSheet = new BottomResultDialog(won, correct_q.getAnswer()); //Create a new BottomResultDialog
            bottomSheet.show(getSupportFragmentManager(), "bottomSheet"); //Show the BottomResultDialog

            nextAndCheckQuestionButton.setText(currentQuestion < questions.size() - 1 ? "Prochaine question" : "Voir les résultats"); //Set the text of the next and check question button
            nextAndCheckQuestionButton.setEnabled(true); //Enable the next and check question button
        } else { //If the index is less than 0
            Toast.makeText(this, "Veuillez répondre à la question actuelle avant de passer à la suivante", Toast.LENGTH_SHORT).show(); //Show a Toast
        }
    }

    /**
     * Load the next question
     *
     * @throws IOException If an I/O error occurs
     */
    private void nextQuestion() throws IOException { // load the next question
        // Verify if the user did select an answer
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Veuillez répondre à la question actuelle avant de passer à la suivante", Toast.LENGTH_SHORT).show();
            return; // taost to respond and do not reload the game data
        }

        currentQuestion++; //Increment the current question
        radioGroup.clearCheck(); //Clear the checked radio button
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        loadGameData(questions.get(currentQuestion)); //Load the game data
        indexQuestion = "Questions : " + (currentQuestion + 1) + "/" + questions.size(); //Set the index question
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(indexQuestion); //Set the subtitle
    }
}
