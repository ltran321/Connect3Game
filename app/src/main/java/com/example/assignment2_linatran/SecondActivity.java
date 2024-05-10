package com.example.assignment2_linatran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

public class SecondActivity extends AppCompatActivity {

    int active = 1; // 1 means fire, 2 means ice
    GridLayout gridLayout;
    RelativeLayout relativeLayout;
    TextView winner;
    Intent intent;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        gridLayout = findViewById(R.id.gridLayout);
        relativeLayout = findViewById(R.id.winnerLayout);
        relativeLayout.setVisibility(View.INVISIBLE);
        winner = findViewById(R.id.winnerMessage);
        intent = getIntent();

        Button btnQuit = (Button) findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // method to animate pieces falling into board
    public void drop(View view){
        if(relativeLayout.getVisibility() == View.INVISIBLE){
            ImageView piece = (ImageView) view;
            piece.setTranslationY(-1000);

            if (active == 1){
                piece.setImageResource(R.drawable.fire);
                checkWinner(active);
                active = 2;
            }
            else{
                piece.setImageResource(R.drawable.ice);
                checkWinner(active);
                active = 1;
            }

            piece.animate().translationYBy(1000).setDuration(300);

            piece.setClickable(false);
        }
    }

    // method to check for winner based on the who's turn it is
    public void checkWinner(int active){
        String winnerMessage = "";

        // Check rows for winner
        for (int row = 0; row < 3; row++) {
            if (checkRow(row)) {
                if (active == 1){
                    userName = intent.getStringExtra("userName");
                    winnerMessage = "Congratulations " + userName + " Fire won!";
                    winner.setText(winnerMessage);
                    relativeLayout.setVisibility(View.VISIBLE);
                }
                else{
                    userName = intent.getStringExtra("userName");
                    winnerMessage = "Congratulations " + userName + " Ice won!";
                    winner.setText(winnerMessage);
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        }

        // Check columns for winner
        for (int col = 0; col < 3; col++) {
            if (checkColumn(col)) {
                if (active == 1){
                    userName = intent.getStringExtra("userName");
                    winnerMessage = "Congratulations " + userName + " Fire won!";
                    winner.setText(winnerMessage);
                    relativeLayout.setVisibility(View.VISIBLE);
                }
                else{
                    userName = intent.getStringExtra("userName");
                    winnerMessage = "Congratulations " + userName + " Ice won!";
                    winner.setText(winnerMessage);
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        }

        // Check diagonals for winner
        if (checkDiagonal(0, 1) || checkDiagonal(2, -1)) {
            if (active == 1){
                userName = intent.getStringExtra("userName");
                winnerMessage = "Congratulations " + userName + " Fire won!";
                winner.setText(winnerMessage);
                relativeLayout.setVisibility(View.VISIBLE);
            }
            else{
                userName = intent.getStringExtra("userName");
                winnerMessage = "Congratulations " + userName + " Ice won!";
                winner.setText(winnerMessage);
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }

        if(isTie()){
            userName = intent.getStringExtra("userName");
            winnerMessage = "Congratulations " + userName + " it's a tie";
            winner.setText(winnerMessage);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    // method to check images in the rows
    private boolean checkRow(int row) {
        ImageView image1 = (ImageView) gridLayout.getChildAt(row * 3);
        ImageView image2 = (ImageView) gridLayout.getChildAt(row * 3 + 1);
        ImageView image3 = (ImageView) gridLayout.getChildAt(row * 3 + 2);

        return checkImagesForWinner(image1, image2, image3);
    }

    // method to check images in column
    private boolean checkColumn(int col) {
        ImageView image1 = (ImageView) gridLayout.getChildAt(col);
        ImageView image2 = (ImageView) gridLayout.getChildAt(col + 3);
        ImageView image3 = (ImageView) gridLayout.getChildAt(col + 6);

        return checkImagesForWinner(image1, image2, image3);
    }

    // method to check images in diagonal
    private boolean checkDiagonal(int startCol, int colIncrement) {
        ImageView image1 = (ImageView) gridLayout.getChildAt(startCol);
        ImageView image2 = (ImageView) gridLayout.getChildAt(3 + (startCol + colIncrement));
        ImageView image3 = (ImageView) gridLayout.getChildAt((2) * 3 + (startCol + 2 * colIncrement));

        return checkImagesForWinner(image1, image2, image3);
    }

    // method determining whether images are the same
    private boolean checkImagesForWinner(ImageView image1, ImageView image2, ImageView image3) {
        if (image1.getDrawable() == null || image2.getDrawable() == null || image3.getDrawable() == null) {
            return false;
        }
        return Objects.equals(image1.getDrawable().getConstantState(), image2.getDrawable().getConstantState()) &&
                Objects.equals(image1.getDrawable().getConstantState(), image3.getDrawable().getConstantState());
    }

    private boolean isTie() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            if (imageView.getDrawable() == null) {
                return false;
            }
        }
        return true;
    }

    public void restart(View view){
        relativeLayout.setVisibility(View.INVISIBLE);
        active = 1;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setClickable(true);
        }

    }
}