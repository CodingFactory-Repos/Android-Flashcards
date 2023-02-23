/*
 * About.java - About
 *
 * Created on 22/02/2023 12:18:01 by HP
 *
 * Copyright (c) 2023. HP (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


            // Récupérer l'intent
            Intent srcIntent = getIntent();

            // je lis les infos qui sont dedans
            String name = srcIntent.getStringExtra("name de l'app");
            String groupe = srcIntent.getStringExtra("name de groupe");


            TextView vroomCards = findViewById(R.id.vroomCards);
            vroomCards.setText(name);

            TextView lefevre_g = findViewById(R.id.lefevre_g);
             vroomCards.setText(name);




        TextView VersionEntry = findViewById(R.id.versionText);

        try
        {
            PackageManager pm = getPackageManager();
            PackageInfo pi;
            // Version
            pi = pm.getPackageInfo( getPackageName(), 0);
            VersionEntry.setText( pi.versionName );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}