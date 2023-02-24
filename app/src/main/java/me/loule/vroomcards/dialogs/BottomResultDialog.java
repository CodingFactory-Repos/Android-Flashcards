/*
 * BottomResultDialog.java - BottomResultDialog
 *
 * Created on 24/02/2023 12:54:29 by loule
 *
 * Copyright (c) 2023. loule (https://loule.me) & CodingFactory (https://codingfactory.fr) @ All rights reserved.
 */

package me.loule.vroomcards.dialogs;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import me.loule.vroomcards.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomResultDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private Boolean won;
    private String CorrectAnswer;

    public BottomResultDialog(Boolean won, String correctAnswer) {
        this.won = won;
        CorrectAnswer = correctAnswer;
    }

    public void setWon(Boolean won) {
        this.won = won;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bottom_result_dialog, container, false);

        TextView resultDialog = v.findViewById(R.id.resultDialog);
        Button next_button = v.findViewById(R.id.next_button);
        next_button.setOnClickListener(this);

        if (won) {
            resultDialog.setText("Félicitations ! " + CorrectAnswer + " est la bonne réponse !");
        } else {
            resultDialog.setText("Dommage ! " + CorrectAnswer + " est la bonne réponse !");
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button:
                dismiss();
                break;
        }
    }
}
