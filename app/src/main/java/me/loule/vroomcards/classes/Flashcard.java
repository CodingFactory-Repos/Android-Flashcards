/*
 * Question.java - Question
 *
 * Created on 22/02/2023 14:03:55 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Flashcard implements Parcelable {
    private final int id;
    private final String question;
    private final Ressource ressource;
    private final int difficulty;
    private final List<Answer> answers;

    protected Flashcard(Parcel in) {
        id = in.readInt();
        question = in.readString();
        ressource = in.readParcelable(Ressource.class.getClassLoader());
        difficulty = in.readInt();
        answers = in.createTypedArrayList(Answer.CREATOR);
    }

    public Flashcard(int id, String question, Ressource ressource, int difficulty, List<Answer> answers) {
        this.id = id;
        this.question = question;
        this.ressource = ressource;
        this.difficulty = difficulty;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Ressource getRessource() {
        return ressource;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeParcelable(ressource, flags);
        dest.writeInt(difficulty);
        dest.writeTypedList(answers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Flashcard> CREATOR = new Creator<Flashcard>() {
        @Override
        public Flashcard createFromParcel(Parcel in) {
            return new Flashcard(in);
        }

        @Override
        public Flashcard[] newArray(int size) {
            return new Flashcard[size];
        }
    };
}
