/*
 * Ressource.java - Ressource
 *
 * Created on 24/02/2023 10:59:11 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Ressource implements Parcelable {
    private final String type;
    private final String media;

    protected Ressource(Parcel in) {
        type = in.readString();
        media = in.readString();
    }

    public String getType() {
        return type;
    }

    public String getMedia() {
        return media;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(media);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ressource> CREATOR = new Creator<Ressource>() {
        @Override
        public Ressource createFromParcel(Parcel in) {
            return new Ressource(in);
        }

        @Override
        public Ressource[] newArray(int size) {
            return new Ressource[size];
        }
    };
}
