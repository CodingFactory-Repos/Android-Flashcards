/*
 * QuestionAdapter.java - QuestionAdapter
 *
 * Created on 22/02/2023 15:00:04 by vahekrikorian
 *
 * Copyright (c) 2023. vahekrikorian (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import me.loule.vroomcards.R;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private ArrayList<Question> questions;

    public QuestionAdapter(ArrayList<Question> questions) {
        this.questions = questions;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView difficultyColor;
        final TextView titleQuestionTextView;
        final TextView proposedResultTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            difficultyColor = itemView.findViewById(R.id.difficultyColor);
            titleQuestionTextView = itemView.findViewById(R.id.titleQuestionTextView);
            proposedResultTextView = itemView.findViewById(R.id.proposedResultTextView);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_question, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.difficultyColor.setImageResource(question.colorId);
        holder.titleQuestionTextView.setText(question.titleQuestion);
        holder.proposedResultTextView.setText(question.result);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
