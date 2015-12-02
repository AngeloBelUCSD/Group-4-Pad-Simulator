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

    private float time_mod;
    private float score_mod;
    private int difficulty;
    private int mode;
    private int score;

    public GameManager(OrbMatcher inputMatcher, int inputDifficulty, int inputMode, ProgressBar progressBar){
        matcher = inputMatcher;
        mode = inputMode;
        difficulty = inputDifficulty;
        gameOver = false;
        pBar = progressBar;

        if(mode == 0) difficulty = -1;

        //Values that adjust time extensions/score modifiers depending on difficulty in time attack
        switch (difficulty){
            case 0: time_mod = (float) 3;
                    score_mod = 1;
                    break;

            case 1: time_mod = (float) 6.5;
                    score_mod = 2;
                    break;

            case 2: time_mod = (float) 9;
                    score_mod = 5;
                    break;

            case -1:
            default: time_mod = 1;
                     score_mod = 1;
                     break;
        }
    }

    public int getScore(){

        int totalSize = totalOrbs();

        score = totalSize * 100;
        if(totalSize > 5 && totalSize <= 10){

            score = (int) (score * 1.2);
        }else if(totalSize > 10 && totalSize <= 15){

            score = (int) (score * 1.75);
        }else if(totalSize > 15 && totalSize <= 20){

            score = (int) (score * 2.5);
        }else if(totalSize > 20){

            score = (int) (score * 4.5);
        }

        score = (int) (score * score_mod);

        Log.d("GameManager", "This round score: " + score);

        return score;
    }

    public int time_bonus(){

        //Bonus time depending on orbs cleared.
        int totalOrbs = totalOrbs();
        int time_bonus = 0;

        if(totalOrbs >= 5){
            time_bonus += 1;
        }
        if(totalOrbs >= 10){
            time_bonus += 2;
        }
        if(totalOrbs >= 15){
            time_bonus += 3;
        }

        if(difficulty == 2){

            time_bonus = (int) Math.round(time_bonus/1.5);
        }

        return time_bonus;
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
        for(int i = 0; i < matcher.comboSize.length; i++) {
            matcher.comboSize[i] = 0;
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

        time += (int) Math.ceil(time_mod);

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

        int time_add = Math.round(time/time_mod) + time_bonus();
        time_add = (time_add < 0) ? 0 : time_add;

        Log.d("GameManager", "Time added: " + time/time_mod + " + " +time_bonus() + " = " + time_add);

        timeAccumulated += time_add;
    }

    public void stopDragTimer() {
        if (dragTimer != null)
            dragTimer.cancel();
    }

    public boolean isGameOver(){
        return gameOver;
    }

}
