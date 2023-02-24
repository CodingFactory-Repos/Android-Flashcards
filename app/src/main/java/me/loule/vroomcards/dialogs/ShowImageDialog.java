/*
 * ShowImageDialog.java - ShowImageDialog
 *
 * Created on 24/02/2023 12:54:29 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.dialogs;

import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import me.loule.vroomcards.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShowImageDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private String image;

    /**
     * @param image the image to show
     */
    public ShowImageDialog(String image) {
        this.image = image;
    }

    /**
     * @param image the image to show
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_show_image_dialog, container, false); // Inflate the view

        // Import all the views
        ImageView showImageView = v.findViewById(R.id.showImageView);
        Button quitButton = v.findViewById(R.id.quitButton);

        // Set the image
        Picasso.get().load(image).into(showImageView);

        quitButton.setOnClickListener(this);

        return v; // Return the view
    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) { // Switch the view id
            case R.id.quitButton: // If the next button is clicked
                dismiss(); // Dismiss the dialog
                break;
        }
    }
}
