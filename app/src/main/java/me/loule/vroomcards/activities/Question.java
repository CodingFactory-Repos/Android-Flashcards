/*
 * Question.java - Question
 *
 * Created on 22/02/2023 16:19:38 by vahekrikorian
 *
 * Copyright (c) 2023. vahekrikorian (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
        public String titleQuestion;
        public String result;
        public int colorId;

        public Question(String titleQuestion, String result, int colorId) {
            this.titleQuestion = titleQuestion;
            this.result = result;
            this.colorId = colorId;
        }

        protected Question(Parcel in) {
            titleQuestion = in.readString();
            result = in.readString();
            colorId = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(titleQuestion);
            dest.writeString(result);
            dest.writeInt(colorId);
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

        public String getTitleQuestion() {
            return titleQuestion;
        }

        public String getResult() {
            return result;
        }

        public int getColorId() {
            return colorId;
        }
    }
