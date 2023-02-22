/*
 * Question.java - Question
 *
 * Created on 22/02/2023 09:22:03 by tropt
 *
 * Copyright (c) 2023. tropt (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Question implements Parcelable {
    private final String question;
    private final String image;
    private final List<String> answers;


    public Question(String question, String image, List<String> answers) {
        this.question = question;
        this.image = image;
        this.answers = answers;
    }

    protected Question(Parcel in) {
        question = in.readString();
        image = in.readString();
        answers = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(image);
        dest.writeStringList(answers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
