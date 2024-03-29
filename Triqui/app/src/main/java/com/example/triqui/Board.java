package com.example.triqui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Board extends View {
    // Width of the board grid lines
    public static final int GRID_WIDTH = 6;
    private Bitmap mHumanBitmap;
    private Bitmap mComputerBitmap;
    private Bitmap mHumanBitmapWin;
    private Bitmap mComputerBitmapWin;
    private Paint mPaint;
    private TicTacToeGame mGame;

    public Board(Context context) {
        super(context);
        initialize();
    }

    public Board(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mHumanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        mComputerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android);
        mHumanBitmapWin = BitmapFactory.decodeResource(getResources(), R.drawable.player_win);
        mComputerBitmapWin = BitmapFactory.decodeResource(getResources(), R.drawable.android_win);

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
// Determine the width and height of the View
        int boardWidth = getWidth();
        int boardHeight = getHeight();
// Make thick, light gray lines
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(GRID_WIDTH);
// Draw the two vertical board lines
        int cellWidth = boardWidth / 3;
        canvas.drawLine(cellWidth, 0, cellWidth, boardHeight, mPaint);
        canvas.drawLine(cellWidth * 2, 0, cellWidth * 2, boardHeight, mPaint);
        //TODO
        canvas.drawLine(0, cellWidth, boardWidth, cellWidth, mPaint);
        canvas.drawLine(0, cellWidth*2, boardWidth,cellWidth*2, mPaint);


        // Draw all the X and O images
        for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
            int col = i % 3;
            int row = i / 3;
// Define the boundaries of a destination rectangle for the image
            int left = col * cellWidth;
            int top = row * cellWidth;
            int right = left + cellWidth;
            int bottom = top + cellWidth;


            if (mGame != null && mGame.getBoardOccupant(i) == TicTacToeGame.HUMAN_PLAYER) {
                canvas.drawBitmap(mHumanBitmap,
                        null, // src
                        new Rect(left, top, right, bottom), // dest
                        mPaint);

            } else if (mGame != null && mGame.getBoardOccupant(i) == TicTacToeGame.COMPUTER_PLAYER) {
                canvas.drawBitmap(mComputerBitmap,
                        null, // src
                        new Rect(left, top, right, bottom), // dest
                        mPaint);

            } else if (mGame != null && mGame.getBoardOccupant(i) == TicTacToeGame.HUMAN_PLAYER_WIN) {
                canvas.drawBitmap(mHumanBitmapWin,
                        null, // src
                        new Rect(left, top, right, bottom), // dest
                        mPaint);

            } else if (mGame != null && mGame.getBoardOccupant(i) == TicTacToeGame.COMPUTER_PLAYER_WIN) {
                canvas.drawBitmap(mComputerBitmapWin,
                        null, // src
                        new Rect(left, top, right, bottom), // dest
                        mPaint);
            }
        }
    }

    public void setGame(TicTacToeGame game) {
        this.mGame = game;
    }
    public int getBoardCellWidth() {
        return getWidth() / 3;
    }
    public int getBoardCellHeight() {
        return getHeight() / 3;
    }
}
