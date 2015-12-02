package com.kesden.b250.groupfour.group4padsim;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Jassa on 11/19/2015.
 */
public class GameManager {
    private OrbMatcher matcher;
    private CountDownTimer dragTimer;
    private CountDownTimer finalTimer;
    private boolean endTimer = false;
    private boolean gameOver;
    private int timeRemaining;
    private int mode;
    private int score;
    private int dragTimeRemaining;

    public GameManager(OrbMatcher inputMatcher, int inputMode){
        matcher = inputMatcher;
        mode = inputMode;
        gameOver = false;
        timeRemaining = 60;
        dragTimeRemaining = 5;
    }

    public int getScore(){

        int totalSize = totalOrbs();

        score = totalSize * 100;
        if(totalSize > 10 && totalSize <= 15){

            score = (int) (score * 1.2);
        }else if(totalSize > 15 && totalSize <= 20){

            score = (int) (score * 1.4);
        }else if(totalSize > 20 && totalSize <= 25){

            score = (int) (score * 1.7);
        }else if(totalSize > 25){

            score = (int) (score * 2.1);
        }

        Log.d("GameManager", "This round score: " + score);

        return score;
    }

    public int totalOrbs(){

        int totalSize = 0;

        int[] comboList = matcher.comboSize;

        for (int size:comboList){

            totalSize += size;
        }

        return totalSize;
    }

    public void resetScore(){
        for(int i:matcher.comboSize) {
            i = 0;
        }
    }

    public void startTimer(final TextView dragTime){
        endTimer = false;
        dragTimeRemaining = 5;
        if(dragTimer != null)
            dragTimer.cancel();
        dragTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                dragTime.setText("Drag: " + dragTimeRemaining);
                dragTimeRemaining--;
            }

            @Override
            public void onFinish() {
                dragTime.setText("");
                endTimer = true;
            }
        }.start();
    }

    public boolean getEndTimer(){
        return endTimer;
    }

    public void setEndTimer(boolean setTimerTo){
        endTimer = setTimerTo;
    }

    public void startUpdateTimer(int time, final TextView timeText){
        if(finalTimer != null)
            finalTimer.cancel();
        if(mode == 0){
            timeText.setText("Endless");
        }
        else{
            timeRemaining += time;
            timeText.setText("Time:" + timeRemaining);
            finalTimer = new CountDownTimer((timeRemaining+1)*1000, 1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    timeRemaining--;
                    timeText.setText("Time:" + timeRemaining);
                }

                @Override
                public void onFinish() {
                    timeText.setText("Game Over!");
                    gameOver = true;
                }
            }.start();
        }
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void cancelTimer(){
        if(dragTimer != null)
            dragTimer.cancel();
    }
}
