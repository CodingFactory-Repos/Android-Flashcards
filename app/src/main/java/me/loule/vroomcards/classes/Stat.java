/*
 * Stat.java - Stat
 *
 * Created on 24/02/2023 16:23:08 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Stat implements Parcelable {
    private final int numberOfPlayedGames;
    private final int numberOfWonGames;

    protected Stat(Parcel in) {
        numberOfPlayedGames = in.readInt();
        numberOfWonGames = in.readInt();
    }

    public Stat(int numberOfPlayedGames, int numberOfWonGames) {
        this.numberOfPlayedGames = numberOfPlayedGames;
        this.numberOfWonGames = numberOfWonGames;
    }

    public int getNumberOfPlayedGames() {
        return numberOfPlayedGames;
    }

    public int getNumberOfWonGames() {
        return numberOfWonGames;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numberOfPlayedGames);
        dest.writeInt(numberOfWonGames);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Stat> CREATOR = new Creator<Stat>() {
        @Override
        public Stat createFromParcel(Parcel in) {
            return new Stat(in);
        }

        @Override
        public Stat[] newArray(int size) {
            return new Stat[size];
        }
    };
}
