package com.example.triqui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private String Board[] = {"button_00","button_01","button_02","button_10","button_11","button_12","button_20","button_21","button_22"};

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int androidPoints;

    private TextView textViewPlayer1;
    private TextView textViewandroid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewandroid = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }



        ((Button) v).setText("X");
        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                androidWins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

        if (roundCount < 8) {
            getMove();
            if (checkForWin()) {
                if (player1Turn) {
                    player1Wins();
                } else {
                    androidWins();
                }
            }

        }



    }
    private void getMove() {
        String btn = new String(Board[new Random().nextInt(9)]);
        int resID = getResources().getIdentifier(btn, "id", getPackageName());
        Button next = findViewById(resID);
        if (next.getText().toString().equals("")) {
            player1Turn = !player1Turn;
            roundCount++;
            next.setText("O");
        }else{
         getMove();
        }
    }
    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("") )  {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("") )  {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("") )  {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("") ) {
            return true;
        }

        return false;
    }



    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
       endBoard();
    }

    private void androidWins() {
        androidPoints++;
        Toast.makeText(this, "Android wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
       endBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        endBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewandroid.setText("Andorid: " + androidPoints);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }
    private void endBoard() {

        for (int i = 0; i < 9; i++) {
            String btn = new String(Board[i]);
            int resID = getResources().getIdentifier(btn, "id", getPackageName());
            Button next = findViewById(resID);
            if (next.getText().toString().equals("")) {
                next.setText("-");
            }

       }

    }
}