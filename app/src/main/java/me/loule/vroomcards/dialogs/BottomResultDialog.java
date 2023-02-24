/*
 * BottomResultDialog.java - BottomResultDialog
 *
 * Created on 24/02/2023 12:54:29 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.dialogs;

import android.widget.TextView;
import android.os.Bundle;
import me.loule.vroomcards.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomResultDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private Boolean won;
    private String CorrectAnswer;

    /**
     * @param won           true if the user won, false if he lost
     * @param correctAnswer the correct answer
     */
    public BottomResultDialog(Boolean won, String correctAnswer) {
        this.won = won;
        CorrectAnswer = correctAnswer;
    }

    /**
     * @param won true if the user won, false if he lost
     */
    public void setWon(Boolean won) {
        this.won = won;
    }

    /**
     * @param correctAnswer the correct answer
     */
    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
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
        View v = inflater.inflate(R.layout.activity_bottom_result_dialog, container, false); // Inflate the view

        // Import all the views
        TextView resultDialog = v.findViewById(R.id.resultDialog);
        Button next_button = v.findViewById(R.id.next_button);
        next_button.setOnClickListener(this);

        // Set the text of the result dialog depending on the result
        resultDialog.setText(won ? "Félicitations ! " + CorrectAnswer + " est la bonne réponse !" : "Dommage ! " + CorrectAnswer + " est la bonne réponse !");

        return v; // Return the view
    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) { // Switch the view id
            case R.id.next_button: // If the next button is clicked
                dismiss(); // Dismiss the dialog
                break;
        }
    }
}
