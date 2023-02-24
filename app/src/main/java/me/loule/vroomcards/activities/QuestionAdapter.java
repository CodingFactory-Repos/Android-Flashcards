/*
 * QuestionAdapter.java - QuestionAdapter
 *
 * Created on 22/02/2023 15:00:04 by vahekrikorian
 *
 * Copyright (c) 2023. vahekrikorian (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import me.loule.vroomcards.classes.Flashcard;

import java.util.ArrayList;
import java.util.stream.Collectors;

import me.loule.vroomcards.R;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private ArrayList<Flashcard> questions;
    private static final String TAG = "QuestionAdapter";
    public QuestionAdapter(ArrayList<Flashcard> questions) {
        this.questions = questions;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView questionImageView;
        final TextView titleQuestionTextView;
        final TextView proposedResultTextView;
        final View.OnClickListener onClickListener;

        /**
         * @param itemView The view of the item
         * @param onClickListener The onClickListener of the item
         */
        public ViewHolder(@NonNull View itemView, View.OnClickListener onClickListener) {
            super(itemView);

            // Get the views
            questionImageView = itemView.findViewById(R.id.questionImageView);
            titleQuestionTextView = itemView.findViewById(R.id.titleQuestionTextView);
            proposedResultTextView = itemView.findViewById(R.id.proposedResultTextView);

            // Set the onClickListener to the itemView
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this.onClickListener);
        }

        /**
         * @param v The view that was clicked. 
         */
        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }


    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext()); // Get the LayoutInflater
        View view = inflater.inflate(R.layout.item_question, parent, false); // Inflate the view

        return new ViewHolder(view, v -> {
            // Start the LoadingActivity with the question
            int position = (int) v.getTag(); // Get the position of the item

            Intent intent = new Intent(v.getContext(), LoadingGameActivity.class); // Create a new intent
            intent.putExtra("difficulty", questions.get(position).getDifficulty()); // Put the difficulty in the intent
            intent.putExtra("question", questions.get(position)); // Put the question in the intent
            v.getContext().startActivity(intent); // Start the LoadingActivity
        });
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flashcard question = questions.get(position); // Get the question
        Picasso.get().load(question.getRessource().getMedia()).into(holder.questionImageView); // Load the image with Picasso
        holder.titleQuestionTextView.setText(question.getQuestion()); // Set the question
        holder.proposedResultTextView.setText(question.getAnswers().stream().map(obj -> obj.getAnswer()).collect(Collectors.joining(", "))); // Set the answers

        // Set the tag of the itemView to the position
        holder.itemView.setTag(position);
    }

    /**
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return questions.size();
    }
}
