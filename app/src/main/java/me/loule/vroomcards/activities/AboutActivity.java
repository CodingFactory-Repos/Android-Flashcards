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
import android.widget.ImageView;
import android.widget.TextView;

import me.loule.vroomcards.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImageView imgLouisL = findViewById(R.id.louisLImageView);
        ImageView imgLouisY = findViewById(R.id.louisYImageView);
        ImageView imgVaheK = findViewById(R.id.vaheKImageVIew);
        ImageView imgAbedA = findViewById(R.id.abedAImageView);
        TextView VersionEntry = findViewById(R.id.versionText);

        //Click Event to open github link when click on the imageView
        imgLouisL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/Loule95450"));
                startActivity(intent);
            }
        });
        //Click Event to open github link when click on the imageView
        imgLouisY.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/LouisYang95"));
                startActivity(intent);
            }
        });
        //Click Event to open github link when click on the imageView
        imgVaheK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/VaheKri"));
                startActivity(intent);
            }
        });
        //Click Event to open github link when click on the imageView
        imgAbedA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/Abed-Nego28"));
                startActivity(intent);
            }
        });
            //Get version of the app dynamically
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi;
            // Version
            pi = pm.getPackageInfo(getPackageName(), 0);
            VersionEntry.setText(pi.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}