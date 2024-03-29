package com.example.triqui;
import java.util.Random;

public class   TicTacToeGame {
    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char HUMAN_PLAYER_WIN = 'x';
    public static final char COMPUTER_PLAYER_WIN = 'o';

    public static final char OPEN_SPOT = ' ';

    private char mBoard[] = {'1','2','3','4','5','6','7','8','9'};
    public static int BOARD_SIZE = 9;
    private int[] score;
    private int winline;

    private Random mRand;

    public char getBoardOccupant(int i) {
        return mBoard[i];
    }

    // The computer&#39;s difficulty levels
    public enum DifficultyLevel {Easy, Harder, Expert};
    // Current difficulty level
    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Expert;

    public TicTacToeGame(){
        mRand = new Random();
        score = new int[]{0, 0, 0};
    }

    public void setScore(int kind) {
        this.score[kind] += 1;
    }

    public int[] getScore() {
        return score;
    }

    /** Clear the board of all X's and O's by setting all spots to OPEN_SPOT. */
    public void clearBoard(){
        for (int i =0 ; i<BOARD_SIZE;i++){
            mBoard[i] = OPEN_SPOT;
        }
    }

    /** Set the given player at the given location on the game board.
     *  The location must be available, or the board will not be changed.
     *
     * @param player - The HUMAN_PLAYER or COMPUTER_PLAYER
     * @param location - The location (0-8) to place the move
     */
    public boolean setMove(char player, int location){
        if(player == OPEN_SPOT || mBoard[location] == OPEN_SPOT || player == COMPUTER_PLAYER_WIN || player == HUMAN_PLAYER_WIN){
            mBoard[location] = player;
            return true;
        }
        return false;
    }

    public int getComputerMove() {
        int move = -1;
        if (mDifficultyLevel == DifficultyLevel.Easy)
            move = getRandomMove();
        else if (mDifficultyLevel == DifficultyLevel.Harder) {
            move = getWinningMove();
            if (move == -1)
                move = getRandomMove();
        }
        else if (mDifficultyLevel == DifficultyLevel.Expert) {
            // Try to win, but if that's not possible, block.
            // If that's not possible, move anywhere.
            move = getWinningMove();
            if (move == -1)
                move = getBlockingMove();
            if (move == -1)
                move = getRandomMove();
        }
        return move;
    }
    /** Return the best move for the computer to make. You must call setMove()
     * to actually make the computer move to that location.
     * @return The best move for the computer to make (0-8).
     */
    public int getWinningMove() {

        // First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                setMove(COMPUTER_PLAYER, i);
                if (checkForWinner() == 3) {
                    //System.out.println("Computer is moving to " + (i + 1));
                    setMove(OPEN_SPOT, i);
                    return i;
                }
                setMove(OPEN_SPOT, i);
            }
        }
        return -1;
    }
    public int getBlockingMove() {

        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                setMove(HUMAN_PLAYER, i);
                if (checkForWinner() == 2) {
                    setMove(OPEN_SPOT, i);
                    //System.out.println("Computer is moving to " + (i + 1));
                    return i;
                }
                setMove(OPEN_SPOT, i);
            }
        }
        return -1;
    }

    public int getRandomMove(){
        int move;

        // Generate random move
        do
        {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] != OPEN_SPOT);

        //System.out.println("Computer is moving to " + (move + 1));
        //setMove(COMPUTER_PLAYER,move);
        return move;
    }


    /**
     * Check for a winner and return a status value indicating who has won.
     * @return Return 0 if no winner or tie yet, 1 if it's a tie, 2 if X won,
     * or 3 if O won.
     */
    public int checkForWinner(){
        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3)	{
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+1] == HUMAN_PLAYER &&
                    mBoard[i+2]== HUMAN_PLAYER) {
                winline = i / 3 + 1;
                return 2;
            }
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+1]== COMPUTER_PLAYER &&
                    mBoard[i+2] == COMPUTER_PLAYER) {
                winline = i / 3 + 1;
                return 3;
            }
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+3] == HUMAN_PLAYER &&
                    mBoard[i+6]== HUMAN_PLAYER) {
                winline = i + 4;
                return 2;
            }
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+3] == COMPUTER_PLAYER &&
                    mBoard[i+6]== COMPUTER_PLAYER) {
                winline = i + 4;
                return 3;
            }
        }

        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER)){
            winline = 7;
            return 2;
        }
        if ((mBoard[2] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[6] == HUMAN_PLAYER)) {
            winline = 8;
            return 2;
        }
        if ((mBoard[0] == COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER)){
            winline = 7;
            return 3;
        }
        if((mBoard[2] == COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[6] == COMPUTER_PLAYER)) {
            winline = 8;
            return 3;
        }

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        winline = 0;
        return 1;
    }

    public int getWinline() {
        return winline;
    }

    public DifficultyLevel getDifficultyLevel() {
        return mDifficultyLevel;
    }
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        mDifficultyLevel = difficultyLevel;
    }
}
