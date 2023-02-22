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
    private final String image;
    private final int difficulty;
    private final List<Answer> answers;

    protected Flashcard(Parcel in) {
        id = in.readInt();
        question = in.readString();
        image = in.readString();
        difficulty = in.readInt();
        answers = in.createTypedArrayList(Answer.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(image);
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
