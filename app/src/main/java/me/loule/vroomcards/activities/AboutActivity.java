/*
 * AboutActivity.java - AboutActivity
 *
 * Created on 21/02/2023 15:35:23 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import me.loule.vroomcards.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
