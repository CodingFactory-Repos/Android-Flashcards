/*
 * AboutActivity.java - AboutActivity
 *
 * Created on 23/02/2023 16:02:45 by vahekrikorian
 *
 * Copyright (c) 2023. vahekrikorian (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.loule.vroomcards.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView VersionEntry = findViewById(R.id.versionText); //Get the version text view

        //Set the click listener for the image views
        findViewById(R.id.louisLImageView).setOnClickListener(this);
        findViewById(R.id.louisYImageView).setOnClickListener(this);
        findViewById(R.id.vaheKImageVIew).setOnClickListener(this);
        findViewById(R.id.abedAImageView).setOnClickListener(this);

        //Get version of the app dynamically
        try { // Try to get the version of the app
            PackageManager pm = getPackageManager(); //Get the package manager
            PackageInfo pi; //Create a package info object
            pi = pm.getPackageInfo(getPackageName(), 0); //Get the package info
            VersionEntry.setText(pi.versionName); //Set the version text view
        } catch (Exception e) { //If an error occurs
            e.printStackTrace(); //Print the stack trace
        }
    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(); //Create a new intent
        intent.setAction(Intent.ACTION_VIEW); //Set the action to view
        intent.addCategory(Intent.CATEGORY_BROWSABLE); //Add the category browsable

        switch (v.getId()) { //Switch on the id of the view
            // Set the data of the intent to the url of the github profile
            case R.id.louisLImageView:
                intent.setData(Uri.parse("https://github.com/Loule95450"));
                break;
            case R.id.louisYImageView:
                intent.setData(Uri.parse("https://github.com/LouisYang95"));
                break;
            case R.id.vaheKImageVIew:
                intent.setData(Uri.parse("https://github.com/VaheKri"));
                break;
            case R.id.abedAImageView:
                intent.setData(Uri.parse("https://github.com/Abed-Nego28"));
                break;
        }

        startActivity(intent); //Start the activity
    }
}
