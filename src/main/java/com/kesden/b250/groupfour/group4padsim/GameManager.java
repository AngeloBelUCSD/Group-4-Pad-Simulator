package com.kesden.b250.groupfour.group4padsim;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Jassa on 11/19/2015.
 */
public class GameManager {
    private OrbMatcher matcher;
    private CountDownTimer dragTimer;
    private CountDownTimer finalTimer;
    private ProgressBar pBar;
    private boolean endTimer = false;
    private boolean gameOver;
    private int timeRemaining;
    private int timeAccumulated;
    private int mode;
    private int score;

    public GameManager(OrbMatcher inputMatcher, int inputMode, ProgressBar progressBar){
        matcher = inputMatcher;
        mode = inputMode;
        gameOver = false;
        pBar = progressBar;

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

    public void startTimer(){
        endTimer = false;

        if(dragTimer != null)
            dragTimer.cancel();

        dragTimer = new CountDownTimer(5000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                pBar.setProgress(pBar.getProgress()+1);
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

    public void startGlobalTimer(int time, final TextView timeText) {


        if(mode == 0){
            timeText.setText("Endless");
        }
        else{
            timeAccumulated = 0;
            timeText.setText("Time:" + time);
            timeRemaining += time;

            finalTimer = new CountDownTimer(timeRemaining*1000, 1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    timeRemaining--;
                    timeText.setText("Time:" + (timeRemaining+timeAccumulated));
                }

                @Override
                public void onFinish() {
                    timeRemaining = 0;
                    if (timeAccumulated > 0) {
                        startGlobalTimer(timeAccumulated, timeText);
                    } else {
                        timeText.setText("Game Over!");
                        gameOver = true;
                    }
                }
            }.start();
        }
    }

    public void updateGlobalTimer(int time){
        timeAccumulated += time;
    }

    public void stopDragTimer() {
        if (dragTimer != null)
            dragTimer.cancel();
    }

    public boolean isGameOver(){
        return gameOver;
    }

}
