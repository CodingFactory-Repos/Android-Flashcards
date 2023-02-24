/*
 * QuestionAdapter.java - QuestionAdapter
 *
 * Created on 22/02/2023 15:00:04 by vahekrikorian
 *
 * Copyright (c) 2023. vahekrikorian (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

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

        public ViewHolder(@NonNull View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            questionImageView = itemView.findViewById(R.id.questionImageView);
            titleQuestionTextView = itemView.findViewById(R.id.titleQuestionTextView);
            proposedResultTextView = itemView.findViewById(R.id.proposedResultTextView);

            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this.onClickListener);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_question, parent, false);

        return new ViewHolder(view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + v.getTag());
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flashcard question = questions.get(position);
        Picasso.get().load(question.getImage()).into(holder.questionImageView);
        holder.titleQuestionTextView.setText(question.getQuestion());
        holder.proposedResultTextView.setText(question.getAnswers().stream().map(obj -> obj.getAnswer()).collect(Collectors.joining(", ")));

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
