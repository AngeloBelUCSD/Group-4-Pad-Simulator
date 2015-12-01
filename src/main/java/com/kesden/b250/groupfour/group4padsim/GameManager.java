package com.kesden.b250.groupfour.group4padsim;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
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

    public GameManager(OrbMatcher inputMatcher, int inputMode){
        matcher = inputMatcher;
        mode = inputMode;
        gameOver = false;
        timeRemaining = 60;
    }

    public int getScore(){
        return matcher.total;
    }

    public void resetScore(){
        matcher.total = 0;
    }

    public void startTimer(){
        endTimer = false;
        if(dragTimer != null)
            dragTimer.cancel();
        dragTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
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
}
